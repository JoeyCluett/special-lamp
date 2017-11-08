#include <iostream>
#include <unistd.h>
#include <stdlib.h>
#include <string>

#include "../../CustomLibs/Peripheral/RS232_GenericController.h"
#include "opencm_cmd_listing.h"

#define BROADCAST_ID 254

using namespace std;

int main(int argc, char* argv[]) {

	if(argc != 4) {
		cout << "Usage: " << argv[0] << " <port name> <servo id> <servo position>\n";
		return 1;
	}

	SerialController sc(argv[1]);
	sc.set_BaudRate(B57600);
	sc.set_WordSize(WordSize_8);
	sc.set_Parity(Parity_OFF);
	sc.set_StopBits(StopBits_1);
	sc.start();

    int color = 0;
    int servo_id       = atoi(argv[2]);
    int servo_position = atoi(argv[3]);
    cout << "Setting position of " << servo_id << " to " << servo_position << endl;

//    OpenCM_setServoAddress(sc, 254, new_servo_id);

    //for(;;) {
        OpenCM_setServoPosition(sc, servo_id, servo_position);
        OpenCM_setBoardLed(sc, 0x00);
        usleep(250000); // ~3 Hz
        OpenCM_setBoardLed(sc, 0x01);
        usleep(250000);
    //}
}
