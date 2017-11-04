#define DXL_BUS_SERIAL1 1 // Dynamixel XL-320

Dynamixel Dxl(DXL_BUS_SERIAL1);

// every command has a one-byte identifier
const byte COMMAND_led  = 1; // set the on-board LED to specific state
const byte COMMAND_ping = 2; // write back sent character
const byte 

// states used internally by the MCU
const int STATE_default  = 1; // waiting for command
const int STATE_led_cmd  = 2; // incoming led command
const int STATE_ping_cmd = 3; // echo character

// start in default state (waiting for incoming command)
int current_state = STATE_default;

char s_char;
char cmd_buffer[256];

// called every loop to carry out action based on current_state
void applyCurrentState(void);

// state callbacks
void setLed(int new_state);

void readChunk(char* buffer, int bytes);
void writeChunk(char* buffer, int bytes);

void setup() {
  Dxl.begin(3); // mode 3 is 1 Mbps
  pinMode(BOARD_LED_PIN, OUTPUT);
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
      default:
        break;
    }    
  }
  
  // carry out different actions based on the current state of the internal FSM
  // current state is always reset after this function returns
  applyCurrentState();
}

void applyCurrentState(void) {
  if(current_state == STATE_default) // nothing to do
    return;
    
  switch(current_state) {
    case STATE_led_cmd:
      readChunk(cmd_buffer, 1); // next byte is the new requested state
      setLed((int)cmd_buffer[0]);
      break;
    case STATE_ping_cmd:
      readChunk(cmd_buffer, 1);
      SerialUSB.write(cmd_buffer, 1);
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
    } // dont toggle unles LED is in a valid state
  }
}

void readChunk(char* buffer, int bytes) {
  int bytes_read = 0;
 
  while(bytes_read != bytes) {
    buffer[bytes_read] = SerialUSB.read(); // read call blocks
    bytes_read++;
  }
}
