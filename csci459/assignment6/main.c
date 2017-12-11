#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "TcpClient.h"  // TCP socket connection library (custom)
#include "CharVector.h" // dynamically sized c string library (custom)

// if the block is wrong size, the rest of the web page may not get loaded
#define BLOCK_SIZE 128

int blockHasTail(char* str) {
	int len = strlen(str);
	char CR = 13, LF = 10;
	if( (str[len-1]==LF) && (str[len-2]==CR) && (str[len-3]==LF) && (str[len-4]==CR) ) 
		return 1;
	else
		return 0;
}

int main(int argc, char* argv[]) {
	if(argc != 4) {
		printf("usage:\n    %s <url> <file> <port number>\n", argv[0]);
		return 1;
	}

	// TCP socket connection
	TcpClient* tcpc = TcpClient_NewEmpty();
	TcpClient_SetHostPort(tcpc, argv[1], atoi(argv[3]));

	// dynamic c-string, for the server request/response
	CharVector* GET_res = CharVector_NewEmpty(); // response
	CharVector* GET_req = CharVector_NewEmpty(); // request

	// construct GET request string as per the http GET protocol
	CharVector_AppendString(GET_req, "GET ");
	CharVector_AppendString(GET_req, argv[2]);
	CharVector_AppendString(GET_req, " HTTP/1.1\r\nHost: ");
	CharVector_AppendString(GET_req, argv[1]);
	CharVector_AppendString(GET_req, "\n\r\n");

	printf("Request:\n%s\n", GET_req->cPtr);
	printf("Writing to socket...\n");
	TcpClient_Write(tcpc, GET_req->cPtr, GET_req->length);
	printf("Reading from socket...\n");

	char cBuf[BLOCK_SIZE];
	bzero(cBuf, BLOCK_SIZE);
	int stop = 0;

	while(!stop) {
		TcpClient_Read(tcpc, cBuf, BLOCK_SIZE-1); // read as many characters as possible
		
		// searching for the CR-LF-CR-LF string that signifies the end of the http response
		stop = blockHasTail(cBuf);
		
		CharVector_AppendString(GET_res, cBuf);
		bzero(cBuf, BLOCK_SIZE); // clear tmp buffer for next read
	}

	printf("Response:\n%s\n", GET_res->cPtr);
	printf("Response size: %d bytes\n", GET_res->length);

	return 0;
}