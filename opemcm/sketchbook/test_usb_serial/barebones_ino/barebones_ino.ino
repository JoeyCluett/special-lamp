/* Minimum_Source*/

Dynamixel Dxl(1); // USART1 configuration

void setup() {
  Dxl.begin(3); // Mode 3: 1 Mbps
  pinMode(BOARD_LED_PIN, OUTPUT);
  
  for(int i = 0; i < 10; i++) {
    digitalWrite(BOARD_LED_PIN, HIGH);
    delay(100);
    digitalWrite(BOARD_LED_PIN, LOW);
    delay(100);
  }
}

void loop() {
  Dxl.goalPosition(254, 0);
  delay(1500);
  Dxl.goalPosition(254, 1023);
  delay(1500);
}

