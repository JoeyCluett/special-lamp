#define DXL_BUS_SERIAL1 1  //Dynamixel on Serial1(USART1)  <-OpenCM9.04
#define ID_NUM 254

Dynamixel Dxl(DXL_BUS_SERIAL1);

void guaranteeRead(byte* buf, int len) {
  for(int i = 0; i < len; i++) {
    while(!SerialUSB.available()) ;
    buf[i] = SerialUSB.read(); // read next available byte
  } 
}

void setup() {
  Dxl.begin(3); // mode 3 is 1 Mbps  
  Dxl.jointMode(ID_NUM); //jointMode() is to use position mode
}

void loop() {
  
  // wait for two bytes to show up
  uint16 new_setpoint;
  guaranteeRead((byte*)&new_setpoint, 2); // PLEASE BE LITTLE ENDIAN!!
  
  Dxl.ledOn(ID_NUM, 6); // LEDs on
  Dxl.goalPosition(ID_NUM, new_setpoint); // ID 254(broadcast) dynamixel moves to position 
  delay(50); // give a lil time to move, ~20Hz update rate
  Dxl.ledOn(ID_NUM, 0); // LEDs Off
}



