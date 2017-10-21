#ifndef __COMM__HELP__H__
#define __COMM__HELP__H__

#include <unistd.h>     // usleep
#include <sys/types.h>  // intx_t and friends
#include <sys/socket.h> // socket, setsockopt
#include <netinet/in.h> // for internet

// function will either succeed or cause program to crash
void guaranteeWrite(char* buf, int len, int sockfd) {
	int writ = 0;
	while(writ < len) {
		// write returns number of bytes written
		writ += write(sockfd, buf+writ, len-writ);
	}
}

// ... just like function above, but reads
void guaranteeRead(char* buf, int len, int sockfd) {
	int bytes_read = 0;
	while(bytes_read < len) {
		// read returns the number of bytes read
		bytes_read += read(sockfd, buf+bytes_read, len-bytes_read);
	}
}

#endif // __COMM__HELP__H__