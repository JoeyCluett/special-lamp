#ifndef __JJC__CHAR__VECTOR__H__
#define __JJC__CHAR__VECTOR__H__

#include <string.h>
#include <stdlib.h>

// every time a string or char is added, the internal c-string is guaranteed to 
// be able to be printed as a normal c-string

typedef struct {
	char* cPtr;
	int length;
	int capacity;
} CharVector;

CharVector* CharVector_NewEmpty(void) {
	CharVector* cv = (CharVector*)malloc(sizeof(CharVector));


	// default internal size is 64 bytes
	cv->capacity = 64;
	cv->cPtr     = (char*)malloc(64);
	cv->length   = 0;

	return cv;
}

// only used internally, user should never have to use this
static void CharVector_Resize(CharVector* cv) {
	char* tmp_ch = (char*)malloc(cv->capacity * 2);
	bzero(tmp_ch, cv->capacity * 2);

	memcpy(tmp_ch, cv->cPtr, cv->capacity);
	cv->capacity *= 2;

	// no memory leaks, plz
	free(cv->cPtr);
	cv->cPtr = tmp_ch;
}

void CharVector_AppendCharacter(CharVector* cv, char c) {
	if(cv->capacity <= cv->length + 1)
		CharVector_Resize(cv);

	cv->cPtr[cv->length++] = c;
}

void CharVector_AppendString(CharVector* cv, char* ch) {
	int i;
	int len = strlen(ch);

	for(i = 0; i < len; i++)
		CharVector_AppendCharacter(cv, ch[i]);
}

#endif // __JJC__CHAR__VECTOR__H__