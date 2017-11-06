#include <iostream>
#include <unistd.h>
#include <stdlib.h>
#include <string>

#include "../../CustomLibs/Peripheral/RS232_GenericController.h"
#include "opencm_cmd_listing.h"

#define BROADCAST_ID 254

using namespace std;

int main(int argc, char* argv[]) {

	if(argc != 3) {
		cout << "Usage: " << argv[0] << " <port name> <new servo id>\n";
		return 1;
	}

	SerialController sc(argv[1]);
	sc.set_BaudRate(B57600);
	sc.set_WordSize(WordSize_8);
	sc.set_Parity(Parity_OFF);
	sc.set_StopBits(StopBits_1);
	sc.start();

    int color = 0;
    int new_servo_id = atoi(argv[2]);
    cout << "Setting new servo id: " << new_servo_id << endl;

//    OpenCM_setServoAddress(sc, 254, new_servo_id);

    for(;;) {
        OpenCM_setServoLed(sc, 10, 6);
        OpenCM_setServoLed(sc, 20, 3);
        usleep(200000);

        OpenCM_setServoLed(sc, 10, 3);
        OpenCM_setServoLed(sc, 20, 6);
        usleep(200000);
    }
}
