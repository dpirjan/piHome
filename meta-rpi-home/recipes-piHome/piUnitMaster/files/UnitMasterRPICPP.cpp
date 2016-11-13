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
#include <unistd.h>
#include <pthread.h>
#include <signal.h>

#include <RF24/RF24.h>
#include <RF24Network/RF24Network.h>
#include "RF24Mesh/RF24Mesh.h"

using namespace std;

#define USE_DEBUG
#define pinRF 17 // pin 11 - GPIO_17 for Broadcom

static volatile int keepRunning = 1;

void intHandler(int sgn)
{
        printf("Signal: %d", sgn);
        keepRunning = 0;
}

class RF24Functions
{
public:
        RF24Functions()
        {
                attachInterrupt(pinRF, INT_EDGE_FALLING, interruptHandler);

                // Set the nodeID to 0 for the master node
                RF24Functions::mesh.setNodeID(0);

                // Connect to the mesh
                RF24Functions::mesh.begin();

                RF24Functions::radio.printDetails();

                //mask interrupts in order to have only on receive package
                RF24Functions::radio.maskIRQ(1,1,0);

                pthread_create(&thread, NULL, &loop, NULL);
        }

        virtual ~RF24Functions() {}


        static void* loop(void *)
        {
                // Payload used on RF
                t_payload pp;

                while(1)
                {
                        // Keep the network updated
                        RF24Functions::mesh.update();

                        // DHCP service runs on the master node and assign addresses to the unit nodes
                        RF24Functions::mesh.DHCP();

                        // MASTER never sleeps as it is in receive and has to assign addresses
                        if(RF24Functions::network.available()) {
                                RF24NetworkHeader hdr;
                                size_t dataSize = RF24Functions::network.peek(hdr);
                                int tmpID;
                                float *value = NULL;

                                RF24Functions::network.read(hdr,&pp, dataSize);

                                if(hdr.type == 'T' || hdr.type == 'H' || hdr.type == 'P' || hdr.type == 'G')
                                {
                                        printf("Got data size =%d from nodeID=0x%02X(0x%02X) msgID=0x%X\n",
                                               dataSize, RF24Functions::mesh.getNodeID(hdr.from_node), hdr.from_node, hdr.id);
                                        value = (float *) pp.val;

                                        printf("<%c> from RF24Network (addr:0x%02X nodeID:0x%02X) value= %f\n",
                                               hdr.type, hdr.from_node, pp.IDnode, *value);
                                        printf("array: 0x%02X 0x%02X 0x%02X 0x%02X\n\n",
                                               pp.val[0], pp.val[1], pp.val[2], pp.val[3]);

                                        // ############ To DO #########################
                                        // # add interface to homePI HMI and DataBase #
                                        // ############################################
                                }
                                else if(hdr.type == 'A')
                                {
                                        printf(" == ACTUATOR !!! ==\n");
                                        // Master is asked about any task for actuator(s)
                                        // Keep pp.IDnode for atendee
                                        tmpID = pp.IDnode;
                                        // For demo purpose: actuatorNo=10 has to fulfil taskNo=1
                                        if ((int) *value == 10) {
                                                // Prepare task and send it
                                                printf("ISR counter =%i\n", RF24Functions::m_isrRFcntr);
                                                *value = RF24Functions::m_isrRFcntr;
                                        } else {
                                                //wrong actuator !!!
                                                printf("Wrong actuator no=%f!\n", *value);
                                                //Don't break for debug purposes
                                        }
                                        // Send task to <tmpID> UnitSensor with the same nodeID for sender (even the sender is the Master)
                                        if (!RF24Functions::mesh.write(&pp, 'S', sizeof(pp), tmpID)) {
                                                printf("Send failed!\n");
                                        }
                                }
                                else
                                {
                                        RF24Functions::network.read(hdr,0,0);
                                        printf("Header (0x%02X) <%c> from nodeID:0x%02X unknown!!!\n",
                                               hdr.type, hdr.type, hdr.from_node);
                                }
                        }
                        delayMicroseconds(250000);
                }
        }

private:
        static void interruptHandler()
        {
                printf("Interrupt triggered %d\n\n", ++RF24Functions::m_isrRFcntr);
        }

        // RF payload transmition:
        struct t_payload {
                unsigned short int    IDnode; // node ID - redundant information
                unsigned char         val[4];
        };

        static int m_isrRFcntr;
        static RF24 radio;
        static RF24Network network;
        static RF24Mesh mesh;
        pthread_t thread;
};

int RF24Functions::m_isrRFcntr = 0;
RF24 RF24Functions::radio(RPI_V2_GPIO_P1_15, BCM2835_SPI_CS0, BCM2835_SPI_SPEED_8MHZ);
RF24Network RF24Functions::network(RF24Functions::radio);
RF24Mesh RF24Functions::mesh(RF24Functions::radio, RF24Functions::network);

int main(void)
{
        signal(SIGINT, intHandler);
        RF24Functions rf24;

        while (keepRunning) {
                delay(1);
        }

        exit(0);
}
