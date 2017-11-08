#define DXL_BUS_SERIAL1 1 // Dynamixel XL-320

// all motor control commands are made through this object
Dynamixel Dxl(DXL_BUS_SERIAL1);

// every command has a one-byte identifier
const byte COMMAND_led       = 1; // set the on-board LED to specific state
const byte COMMAND_ping      = 2; // write back sent character
const byte COMMAND_servo_led = 3; // set the LED on a given servo to a specific value
const byte COMMAND_set_pos   = 4; // set a goal position for a servo
const byte COMMAND_get_pos   = 5; // get the current position of the servo
const byte COMMAND_get_addr  = 6; // get address of (only) connected servo
const byte COMMAND_set_addr  = 7; // set address for given dynamixel

// states used internally by the MCU
const int STATE_default        = 1; // waiting for command
const int STATE_led_cmd        = 2; // incoming led command
const int STATE_ping_cmd       = 3; // echo character
const int STATE_servo_led_cmd  = 4; // write to servo LED
const int STATE_servo_pos_cmd  = 5; // set servo position
const int STATE_servo_get_pos  = 6; // get the current position
const int STATE_servo_get_addr = 7; // get address of connected dynamixel
const int STATE_servo_set_addr = 8; // set address of given dyanmixel

// start in default state (waiting for incoming command)
int current_state = STATE_default;

char s_char;
char cmd_buffer[256];

// called every loop to carry out action based on current_state
void applyCurrentState(void);

// state callbacks
void setLed(int new_state);
void setServoLed(int servo_id, int led_value);
void setServoPos(int servo_id, int pos_value);
void getServoPos(int servo_id);
void getServoAddr(void);
void setServoAddr(int servo_id, int new_servo_id);

void readChunk(char* buffer, int bytes);
void writeChunk(char* buffer, int bytes);

void setup() {
  Dxl.begin(3); // mode 3 is 1 Mbps
  pinMode(BOARD_LED_PIN, OUTPUT);
  
  digitalWrite(BOARD_LED_PIN, HIGH);
  delay(2000);
  
  while(SerialUSB.available()) {
    // clear internal buffer of data
    byte tmp = SerialUSB.read(); 
  }
}

void loop() {
  if(SerialUSB.available()) {
    s_char = SerialUSB.read(); 
    switch(s_char) {
      case COMMAND_led:
        current_state = STATE_led_cmd;
        break;
      case COMMAND_ping:
        current_state = STATE_ping_cmd;
        break;
      case COMMAND_servo_led:
        current_state = STATE_servo_led_cmd;
        break;
      case COMMAND_set_pos:
        current_state = STATE_servo_pos_cmd;
        break;
      case COMMAND_get_pos:
        current_state = STATE_servo_get_pos;
        break;
      case COMMAND_get_addr:
        current_state = STATE_servo_get_addr;
        break;
      case COMMAND_set_addr:
        current_state = STATE_servo_set_addr;
        break;
      default:
        break;
    }    
  }
  
  // carry out different actions based on the current state of the internal FSM
  // current state is always reset after this function returns
  applyCurrentState();
}

void applyCurrentState(void) {
  switch(current_state) {
    case STATE_default:
      break;
    case STATE_led_cmd:
      readChunk(cmd_buffer, 1); // next byte is the new requested state
      digitalWrite(BOARD_LED_PIN, cmd_buffer[0]);
      break;
    case STATE_ping_cmd:
      readChunk(cmd_buffer, 1);
      SerialUSB.write(cmd_buffer, 1);
      break;
    case STATE_servo_led_cmd:
      { // stackify temp values
        byte servo_num = SerialUSB.read(); // next byte is the servo address to use
        byte led_state = SerialUSB.read(); // next byte is new led value
        setServoLed(servo_num, led_state);
      }
      break;
    case STATE_servo_pos_cmd:
      {
        readChunk(cmd_buffer, 3);
        byte servo_num = cmd_buffer[0];
        int servo_position_value = cmd_buffer[2];
        servo_position_value = (servo_position_value << 8);
        servo_position_value |= cmd_buffer[1];
        setServoPos(servo_num, servo_position_value);
      }
      break;
    case STATE_servo_get_pos:
      {
        byte servo_num = SerialUSB.read(); // next byte is servo address
        int servo_position = Dxl.getPosition(servo_num);
        cmd_buffer[0] = servo_position & 0xFF;
        cmd_buffer[1] = (servo_position >> 8) & 0xFF;
        SerialUSB.write(cmd_buffer, 2);
      }
      break;
    case STATE_servo_get_addr: // get address of only connected servo
      {
        cmd_buffer[0] = Dxl.readByte(254, 3);
        SerialUSB.write(cmd_buffer, 1);
      }
      break;
    case STATE_servo_set_addr:
      {
        byte old_id = SerialUSB.read();
        byte new_id = SerialUSB.read();
        Dxl.setID(old_id, new_id);      
      }
      break;
    default:
      break;
  } 
  
  // reset the current state
  current_state = STATE_default;
}

void setLed(int new_state) {
  static int led_state = -1;
  
  if(new_state == 0) { // set low
    digitalWrite(BOARD_LED_PIN, LOW);
    led_state = 0;
  } else if(new_state == 1) { // set high
    digitalWrite(BOARD_LED_PIN, HIGH);      
    led_state = 1;    
  } else if(new_state == 2) { // toggle
    if(led_state == 0) {
      setLed(1);
    } else if(led_state == 1) {
      setLed(0); 
    } // dont toggle unless LED is in a valid state
  }
}

void setServoLed(int servo_id, int led_value) {
   Dxl.ledOn(servo_id, led_value);
}

void setServoPos(int servo_id, int pos_value) {
  Dxl.goalPosition(servo_id, pos_value);
}

void readChunk(char* buffer, int bytes) {  
  for(int i = 0; i < bytes; i++)
    buffer[i] = SerialUSB.read();
}

