#ifndef __opencm__command__callbacks__h__
#define __opencm__command__callbacks__h__

void setLed(int new_state) {
  if(new_state == 0) {
    digitalWrite(BOARD_LED_PIN, LOW);
  else
    digitalWrite(BOARD_LED_PIN, HIGH);      
}

#endif // __opencm__command__callbacks__h__
