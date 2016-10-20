/*
Wiring diagram NRF24L01 - Raspberry PI (all models)

PINS 	NRF24L01		|		PINS RPi 	P1 Connector
========================+=================================
1		GND				|		Ground			(25)
2		VCC				|		3v3				(1) 	sau (17)
3		CE				|		GPIO22			(15)
4		CSN				|		GPIO08			(24)
5		SCK				|		SPI-CLK			(23)
6		MOSI			|		SPI-MOSI		(19)
7		MISO			|		SPI-MISO		(21)
8		IRQ				|		-				 -

Remark:
RaspberryPI versions 1, 2, 3 are pins compatible for pins no 01 .. 26
*/

#include <cstdlib>
#include <iostream>
#include <sstream>
#include <string>

#include <RF24/RF24.h>
#include <RF24Network/RF24Network.h>
#include "RF24Mesh/RF24Mesh.h"  

#define USE_DEBUG

// RF payload transmition:
struct t_payload {
  unsigned short int    IDnode;   // node ID - redundant information
  unsigned char         val[4];
//  float                 value;    // the sensor value sent to Master (main unit)
};

// Payload used on RF
t_payload pp;

int stat = 0;


// Set up NRF24L01+ radio on SPI bus (see the above wiring)
RF24 radio(RPI_V2_GPIO_P1_15, BCM2835_SPI_CS0, BCM2835_SPI_SPEED_8MHZ);  
RF24Network network(radio);
RF24Mesh mesh(radio,network);



int check_for_incoming_data(void) {
  RF24NetworkHeader hdr;
  size_t            dataSize = network.peek(hdr);
  int				tmpStat = 0;
  int               tmpID;
  float             *value = NULL;
  
  network.read(hdr,&pp, dataSize);
  
  #ifdef USE_DEBUG
    printf("Got data size =%d from nodeID=0x%02X(0x%02X) msgID=0x%X\n", dataSize, 
		mesh.getNodeID(hdr.from_node), hdr.from_node, hdr.id);
  #endif
  
  value = (float *) pp.val;
  
  switch(hdr.type){
    case 'T':
    case 'H':
    case 'P':
    case 'G': 
      #ifdef USE_DEBUG
        printf("<%c> from RF24Network (addr:0x%02X nodeID:0x%02X) value= %f\n", 
			hdr.type, hdr.from_node, pp.IDnode, *value);
        printf("array: 0x%02X 0x%02X 0x%02X 0x%02X\n\n", pp.val[0], pp.val[1], pp.val[2], pp.val[3]);
      #endif
      
      // ############ To DO ######################### 
      // # add interface to homePI HMI and DataBase #
      // ############################################ 
	  
      tmpStat = 1;
      break;
	  
    case 'A':
      #ifdef USE_DEBUG
        printf(" == ACTUATOR !!! ==\n");
      #endif  
      // Master is asked about any task for actuator(s)
	  // Keep pp.IDnode for atendee
      tmpID = pp.IDnode;
	  // For demo purpose: actuatorNo=10 has to fulfil taskNo=1
	  if ((int) *value == 10) {
		// Prepare task and send it
		*value = 1.0;		  
	  } else {
	    //wrong actuator !!!
		#ifdef USE_DEBUG
          printf("Wrong actuator no=%f!\n", *value);
          //Don't break for debug purposes
        #else
          break;
        #endif
	  }
	  // Send task to <tmpID> UnitSensor with the same nodeID for sender (even the sender is the Master)
      if (!mesh.write(&pp, 'S', sizeof(pp), tmpID)) {
        #ifdef USE_DEBUG
          printf("Send failed!\n");
        #endif
      } else {
        tmpStat = 1;
      }
      break;
	  
    default: 
      network.read(hdr,0,0); 
	  #ifdef USE_DEBUG
		printf("Header (0x%02X) <%c> from nodeID:0x%02X unknown!!!\n", 
			hdr.type, hdr.type, hdr.from_node);
	  #endif
      break;
    }	
	return tmpStat;
}

int main(int argc, char** argv) {
  // Set the nodeID to 0 for the master node
  mesh.setNodeID(0);
  
  // Connect to the mesh
  mesh.begin();
  #ifdef USE_DEBUG
	radio.printDetails();
  #endif
  
  while (1) {
	// Keep the network updated
	mesh.update();
	// DHCP service runs on the master node and assign addresses to the unit nodes
	mesh.DHCP();

	// MASTER never sleeps as it is in receive and has to assign addresses
    if(network.available()) {
	  #ifdef USE_DEBUG
        printf("\nPaket received.\n");
      #endif
      stat = check_for_incoming_data();
	}
  }
}	