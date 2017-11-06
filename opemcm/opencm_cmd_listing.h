#ifndef __JJC__OPENCM__CMD__LISTING__H__
#define __JJC__OPENCM__CMD__LISTING__H__

#include "../../CustomLibs/Peripheral/RS232_GenericController.h"

// fill this buffer with appropiate command data
static char buffer[256];

void OpenCM_setBoardLed(SerialController& sc, int new_led_value) {
    buffer[0] = (char)0x01;
    buffer[1] = (char)new_led_value;
    sc.writeChunk(buffer, 2);
}

void OpenCM_setServoLed(SerialController& sc, int servo_addr, int rgb_value) {
    buffer[0] = (char)0x03;
    buffer[1] = (char)servo_addr;
    buffer[2] = (char)rgb_value;
    sc.writeChunk(buffer, 3);
}

void OpenCM_setServoPosition(SerialController& sc, int servo_addr, int pos_value) {

    // quick invalid value check
    if(pos_value < 0 || pos_value > 1023)
        return;

    buffer[0] = (char)0x04;
    buffer[1] = (char)servo_addr;
    buffer[2] = (char)(pos_value & 0xFF);        // multi-byte values sent in little-endian format
    buffer[3] = (char)((pos_value >> 8) & 0xFF);

    sc.writeChunk(buffer, 4);
}

int OpenCM_getServoPosition(SerialController& sc, int servo_addr) {
    buffer[0] = (char)0x05;
    buffer[1] = (char)servo_addr;

    sc.writeChunk(buffer, 2);
    sc.readChunk(buffer, 2);

    int ret_val = buffer[1];
    ret_val = ret_val << 8;
    ret_val = ret_val | buffer[0];

    return ret_val;
}

int OpenCM_getServoAddress(SerialController& sc) {
    buffer[0] = (char)0x06;
    sc.writeChunk(buffer, 1);
    sc.readChunk(buffer, 1);

    return (int)buffer[0];
}

void OpenCM_setServoAddress(SerialController& sc, int old_servo_addr, int new_servo_addr) {
    buffer[0] = (char)0x07;
    buffer[1] = (char)old_servo_addr;
    buffer[2] = (char)new_servo_addr;

    sc.writeChunk(buffer, 3);
}

#endif // __JJC__OPENCM__CMD__LISTING__H__
