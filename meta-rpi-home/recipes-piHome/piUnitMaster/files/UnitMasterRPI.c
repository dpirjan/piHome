/**
Wiring diagram NRF24L01 - Raspberry PI (all models)

PINS | NRF24L01 | PINS RPi | P1 Connector  |
=====|==========|==========|===============|
1    | GND      | Ground   | (25)          |
2    | VCC      | 3v3      | (01) or (17)  |
3    | CE       | GPIO_22  | (15)          |
4    | CSN      | GPIO_08  | (24)          |
5    | SCK      | SPI-CLK  | (23)          |
6    | MOSI     | SPI-MOSI | (19)          |
7    | MISO     | SPI-MISO | (21)          |
8    | IRQ      | GPIO_00  | (11)          | BCM GPIO_17

Remark:
RaspberryPI versions 1, 2, 3 are pins compatible for pins no 01 .. 26
*/

#include <cstdlib>
#include <iostream>
#include <sstream>
#include <string>

#include <RF24/RF24.h>
#include <RF24Network/RF24Network.h>

#include <wiringPi.h>

#include <unistd.h>

using namespace std;

// ********************
// *  DEFINE SECTION  *
// ********************
// Sensors IDs <sensorIDs>
#define ID_PIR              0x00000100UL    // (bit_8)  PIR motion detection
#define ID_Contact          0x00000200UL    // (bit_9) 
#define ID_Light            0x00000400UL    // (bit_10) Light intensity
#define ID_Temperature      0x00000800UL    // (bit_11) DHT22 Temperature
#define ID_Humidity         0x00001000UL    // (bit_12) DHT22 Humidity
#define ID_CO               0x00002000UL    // (bit_13) Carbon Monoxid
#define ID_CH4              0x00004000UL    // (bit_14) Methane
#define ID_Vibration        0x00008000UL    // (bit_15) Knock sensor
#define ID_Flood            0x00010000UL    // (bit_16) Water presence
#define ID_WaterLevel       0x00020000UL    // (bit_17) Water level
#define ID_DoorLockedState  0x00040000UL    // (bit_18) Door locked
#define ID_BatteryLevel     0x00080000UL    // (bit_19) Read battery voltage on Analog

//Actuators IDs <actuatorIDs>
#define ID_Siren            0x00100000UL    // (bit_20)
#define ID_Buzzer           0x00200000UL    // (bit_21)
#define ID_Relay            0x00400000UL    // (bit_22)
#define ID_DoorBell         0x00800000UL    // (bit_23)
#define ID_IR               0x01000000UL    // (bit_24)
#define ID_Pump             0x02000000UL    // (bit_25) Water pump
#define ID_LockDoor         0x04000000UL    // (bit_26) bolt or whatever else used for locking the door

#define ID_Watchdog         0x08000000UL    // (bit_27) To read/configure watchdog

#define ID_GET_TASK         0x80000000UL    

// Default value to wake-up [ms] (watchdog timestamp)
#define WDT_DT              4000

// Size of the RX ring buffer (LAST_RX+1)
#define LAST_RX              7

// Define messages types
/* Arduino Units are only "slaves" (nodeID > 0)
 * Types of available messages:
 *  - GET_ALL : From Master to Unit, it requests to forward all
 *              sensors values and actuators status.
 *              network.write msg_type="A" 0x41
 *  - GET_DEV : From Master to Unit, it requests to get the value for 
 *              a given sensor or the status for actuator
 *              network.write msg_type="D" 0x44
 *  - SET_ACT : From Master to Unit, it requests to drive an actuator
 *              network.write msg_type="C" 0x43
 *  - GET_DLT : From Master to Unit, read the time step for a sensor/actuator
 *              network.write msg_type="R" 0x52
 *  - SND_DLT : From Unit to Master, send the time step for a sensor/actuator
 *              network.write msg_type="t" 0x74
 *  - SET_DLT : From Master to Unit, set the time step for a sensor/actuator
 *              network.write msg_type="T" 0x54
 *  - GET_MSK : From Master to Unit, it requests the sensor mask
 *              (if sensor value will be delivered)
 *              network.write msg_type="G" 0x47
 *  - SND_MSK : From Unit to Master, return mask value for sensor
 *              network.write msg_type="m" 0x6D
 *  - CNF_MSK : From Master to Unit, configure mask value:
 *              val = 0 Sensor value isn't delivered (even when interrupt occours)
 *                      Remark:
 *                        When forced, the sensor value is sent to Master, means if
 *                        explicitly is requested then this task is prioritar
 *              val = 1 Sensor value is deliver without restriction
 *              network.write msg_type="M" 0x4D
 *  -SEND_VAL : From Unit to Master, respond with the sensor/actuator value
 *              network.write msg_type="V" 0x56
 *  -SEND_ERR : From Unit to Master, return SUCCESS or ERROR No
 *              network.write msg_type="E" 0x45
 */
#define SEND_VAL             0x56   // "V"
#define SEND_ERR             0x45   // "E"
#define GET_ALL              0x41   // "A"
#define GET_DEV              0x44   // "D"
#define SET_ACT              0x43   // "C"
#define GET_DLT              0x52   // "R"
#define SND_DLT              0x74   // "t"
#define SET_DLT              0x54   // "T"
#define GET_MSK              0x47   // "G"
#define SND_MSK              0x6D   // "m"
#define SET_MSK              0x4D   // "M"

// Maximum number of retries when send a sensor value
#define MAX_RETRIES          5

// Define Errors
#define RET_SUCCESS          0
#define READ_VAL_FAILED     -1
#define SEND_RETR_FAILED    -2
#define GET_ADDR_FAILED     -3
#define SEND_TEMP_FAILED    -4
#define SEND_HUMD_FAILED    -5
#define SEND_PIR_FAILED     -6
#define DETECT_PIR_FALSE    -7
#define GET_DHT22_FAILED    -8
#define SEND_CH4_FAILED     -9
#define SEND_CO_FAILED      -10
#define SEND_LIGHT_FAILED   -11
#define SEND_WATER_FAILED   -12

#define WRONG_ACTUATOR_ID   -20

#define WRONG_SENSOR_ID     -30

#define WRONG_UNIT_ID       -40

#define WRONG_HEADER_TYPE   -50

#define WRONG_CONFIG        -60

#define UNKNOWN_MSG_TYPE    -70

#define RX_RING_FULL        -80
#define RX_NO_DATA_AVAIL    -81

#define NO_ALARM_AVAIL      -90

// Aditional information when an error occours
#define RETRIES_FAILED    0xFF100000UL
#define ASSIGNED_TASK     0xFF200000UL


// RPI private defines
#define USE_DEBUG
#define pinRF 17 // pin 0 - GPIO_17 - for Broadcom

// ********************
// *  GLOBAL SECTION  *
// ********************

// RF payload transmition:
struct t_payload {
  uint32_t  IDs;              // sensor or actuator ID				(32 bits)
  float     value;            // a) the value read from the sensor	(32 bits)
                              // b) preset value for actuator
  uint16_t  IDnode;           // node ID - redundant information	(16 bits)
} __attribute__((packed)) 
  pp;                         // RF global buffer


struct t_RX {
  int            newD;        // data is availlable to process
  RF24NetworkHeader h;        // data header
  uint8_t p[sizeof(t_payload)]; // data payload
} __attribute__((packed))
  rx[LAST_RX + 1];            // receive queue (ring buffer)

int head  = 0;                // current RX index to be filled
int posRX = 0;                // message to be handled

// Output status (error handling)
int g_stat = RET_SUCCESS;

// RF ISR configuration status
int rfIRQ = 1;
volatile int isrRFcntr  = 0;  // only for debugging purposes [ToBeRemoved]
int g_isrRF_ON = 0;           // RF packets available

unsigned int  g_cntr = 0;     // only for debugging purposes [ToBeRemoved]


// Set up NRF24L01+ radio on SPI bus (see the above wiring)
//RF24 radio(RPI_V2_GPIO_P1_15, BCM2835_SPI_CS0, BCM2835_SPI_SPEED_8MHZ); //RPi
RF24 radio(22, 8); // wiringPi
RF24Network network(radio);

/**
 * RF interrupt function.
 */
void f_rfISR(void *arg)       // wiringPi
//void f_rfISR(void) // RPi
{
  g_stat = RET_SUCCESS;
  do {
    // Avoid overwrite current data when the ring queue is full!!
    if (rx[head].newD == 0){
      network.peek(rx[head].h);
      network.read(rx[head].h, rx[head].p, sizeof(t_payload));
      if (rx[posRX].h.type != 0) { //discard data when dummy
        rx[head].newD = 1;
        head += 1;  // Go to next buffer
        head &= LAST_RX;
      } else {
        g_stat = WRONG_HEADER_TYPE;  
      }
    } else {
      // Ring full discard data !!!
      g_stat = RX_RING_FULL;
      break;
    }
  } while (network.available());
  g_isrRF_ON = 1; 
  isrRFcntr += 1;
}


/*
 * Process the received RF messages 
 * 
 * @return the status (success or error)
 */
int check_for_incoming_data(void) {
int       tmpStat = RET_SUCCESS;
uint8_t   task;
t_payload *g_pp;

#ifdef USE_DEBUG
  printf("Current RX stack: posRX=%d  head=%d\n", posRX, head);
#endif
  
  // Assume head is always in front of posRX
  while (rx[posRX].newD != 0) {
    task = rx[posRX].h.type;
    g_pp = (t_payload *) rx[posRX].p;
    // prepare task payload

#ifdef USE_DEBUG
    printf("Got data msgID=0x%X from nodeID=0x%02X type='%c'  sensor=0x%04X val=%f\n", 
      rx[posRX].h.id, rx[posRX].h.from_node, (char) task, g_pp->IDs, g_pp->value);
#endif

    // data was succesful extracted
    rx[posRX].newD = 0;
    // next buffer
    posRX += 1;
    posRX &= LAST_RX;
  }
  return tmpStat;
}


/**
 * Local function for network.write. Retries are limited to maxR 
 * 
 * @param [global] pp     payload
 * @param [in]   header   the RF24 header to use for transfer
 * @param [in] maxR       maximum number of retries when RF transfer fails
 * @return the status of RF transmition (success or error)
 */
int send_loop( RF24NetworkHeader header, int maxR) {
int  stat     = SEND_RETR_FAILED;
int  retries  = 0;
  
#ifdef USE_DEBUG
  printf("Send task to node %d: SensorID=0x%04X  Task/Value=%f\n", pp.IDnode, pp.IDs, pp.value);
#endif

  radio.stopListening();
  delay(1);
  while (retries < maxR){
    // send sensors value to the master
    if (!network.write(header, &pp, sizeof(t_payload))) {
#ifdef USE_DEBUG
      printf("Send failed! retry ...\n");
#endif
      retries++;
      delay(100);
      stat = GET_ADDR_FAILED;
    } else {
#ifdef USE_DEBUG
      printf("Paket retries=%d\n", retries);
#endif
      stat = RET_SUCCESS;
      break;
    }
  }
  delay(1);
  radio.startListening();
  return stat;
}


/**
 * Send a task from current Unit to the <node> Unit through the RF network
 * 
 * @param [in] tskID        the task ID {GET_ALL, GET_DEV, SET_ACT, SET_DLT, GET_MSK, CNF_MSK}
 * @param [in] sensorID     the sensor or actuator code. See <sensorIDs> from common.h  
 * @param [in] tskval       the additional value needed to fulfil task  
 * @param [in] maxR         maximum number of retries when RF transfer fails
 * @param [in] node         ID for the destination node
 * @return the status of RF transmition (success or error)
 */
int sendTask(uint16_t taskID, uint32_t sensorID, float taskval, int maxR, uint16_t node) {
int  stat     = SEND_RETR_FAILED;
int  retries  = 0;

  RF24NetworkHeader header(node, taskID); // Send <taskID> to <node> Unit
  
  pp.IDnode = node;
  pp.IDs = sensorID;
  pp.value = taskval;
  stat = send_loop(header, maxR);
  return stat;
}


int main(void) {
t_payload *g_pp;
int  l_stat;
int  isr_n = -1;

  printf("[__DAPI__] sizeof(t_payload)=%d   sizeof(t_RX)=%d\n", sizeof(t_payload), sizeof(t_RX));

  // CLear RX ring
  memset(rx, 0, (LAST_RX + 1) * sizeof(t_RX));
  // Configure RF ISR
  if(wiringPiSetupGpio() < 0)
    printf("Unable to setup wiringPi: %s\n", strerror(errno));
  else
    printf("wiringPiSetup() complete\n");
  printf("\nSet RF ISR on pin %i\n", pinRF);
  // attachInterrupt(pinRF, INT_EDGE_FALLING, f_rfISR); // RPi
  if(wiringPiISR(pinRF, INT_EDGE_FALLING, f_rfISR, NULL) < 0) { // wiringPi
    printf("Cannot setup interrupt on %d!\n", pinRF);
    return EXIT_FAILURE;
  } else
    printf("Interrupt setup ok!\n");

  radio.begin();
  radio.setChannel(90);
  radio.maskIRQ(1,1,0);   // maskIRQ(bool_tx_ok, bool_tx_fail, bool_rx_ready) where 1 means disable (mask)
  radio.setPALevel(RF24_PA_MAX);
  //radio.setDataRate(RF24_250KBPS);
  radio.setCRCLength(RF24_CRC_16);
  radio.setRetries(15,15);
  radio.setAutoAck(1);
  radio.enableAckPayload();
  network.returnSysMsgs = 1;

  network.begin(/*channel 90, */ /*node address*/ 0);
#ifdef USE_DEBUG
  radio.printDetails();
#endif

  radio.startListening();
  delay(1);

  while (1) {
    g_cntr+=1;

    /* 
    l_stat = checkSensors();
    if (l_stat != RET_SUCCESS) {
      Do what?
      process sensor(s) info ...
    }
    */

    // Keep the network updated
    network.update();
      
    if (isr_n != isrRFcntr) {
      printf("ISR cntr = %d\n  status = 0x%04X", isrRFcntr, g_stat);
      isr_n = isrRFcntr;
    }

    // MASTER never sleeps as it is in receive and has to assign addresses
    if(g_isrRF_ON) {
#ifdef USE_DEBUG
      printf("\nPacket received.\n");
#endif
      l_stat = check_for_incoming_data();
      g_isrRF_ON = 0;
    }

    if (g_cntr == 400) {
      l_stat = sendTask((uint16_t) SET_DLT, (uint32_t) ID_Temperature, (float) 60000, MAX_RETRIES, 01);
    }
    if (g_cntr == 4000) {
      l_stat = sendTask((uint16_t) SET_DLT, (uint32_t) ID_Temperature, (float) 32000, MAX_RETRIES, 01);
    }   
    if (g_cntr == 6000) {
      l_stat = sendTask((uint16_t) GET_MSK, (uint32_t) ID_Temperature, (float) 0, MAX_RETRIES, 01);
    }       
    if (g_cntr == 6100) {
      g_cntr = 0;
    }
    delayMicroseconds(250000); //250ms @TODO check with usleep
  }
}
