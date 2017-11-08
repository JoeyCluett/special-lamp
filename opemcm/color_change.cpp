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
		cout << "Usage:\n    " << argv[0] << " <port name> <servo id> <RED|GREEN|BLUE>\n\n";
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
    string servo_color = argv[3];
    int servo_color_id;

    if(servo_color == "RED") {
        servo_color_id = 0x01;
    } else if(servo_color == "GREEN") {
        servo_color_id = 0x02;
    } else if(servo_color == "BLUE") {
        servo_color_id = 0x04;
    } else {
        cerr << "Invalid color: " << servo_color << endl;
        cerr << "    opts: RED, GREEN, BLUE\n";
        return 1;
    }

    cout << "Setting color of " << servo_id << " to " << servo_color << endl;

    OpenCM_setServoLed(sc, servo_id, servo_color_id);
    OpenCM_setBoardLed(sc, 0);
    usleep(250000); // ~2 Hz

    OpenCM_setBoardLed(sc, 1);
    usleep(250000);

    return 0;
}
