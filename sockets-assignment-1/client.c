#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <errno.h>

void error(const char *msg) {
 	perror(msg);
 	exit(0);
}

int main(int argc, char *argv[]) {
 	int sockfd, portno, n;
 	struct sockaddr_in serv_addr;
 	struct hostent *server;

 	char buffer[256];
 	if (argc != 3) {
 		fprintf(stderr,"usage %s <hostname> <port>\n", argv[0]);
 		exit(1); // exit error condition
 	}

 	// generate usable hostname and port number
 	portno = atoi(argv[2]);
 	sockfd = socket(AF_INET, SOCK_STREAM, 0); // indicate IPv4 and TCP connection
 	if (sockfd < 0) {
 		printf("Error opening socket: %s\n", strerror(errno));
 	}

 	server = gethostbyname(argv[1]);
	if (server == NULL) {
		fprintf(stderr,"ERROR, no such host\n");
		exit(1); // exit error condition
 	}

 	bzero((char *) &serv_addr, sizeof(serv_addr));
 	serv_addr.sin_family = AF_INET;
 	bcopy((char *)server->h_addr,
 	(char *)&serv_addr.sin_addr.s_addr,
 	server->h_length);
 	serv_addr.sin_port = htons(portno);

 	if (connect(sockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0) {
 		printf("ERROR connecting: %s\n", strerror(errno));
 		return 1;
 	}

 	bzero(buffer, 256);
 	n = read(sockfd, buffer, 256);
 	printf("Server msg:\n%s\n", buffer);

 	while(1) {
 		puts("Please enter message: ");

 		bzero(buffer, 256);
 		fgets(buffer, 255, stdin);
// 		buffer[(int)strlen(buffer)] = '\0';

 		if(strcmp(buffer, "EXIT\n") == 0) {
 			// message server to close connection
 			printf("Requesting EXIT\n");

 			write(sockfd, "EXIT", 4); // EXIT is 4 characters
 			usleep(1500000); // wait a lil before exiting
 			exit(1);

 		} else if(strcmp(buffer, "HALT\n") == 0) {
 			// message server to close all connections
 			printf("Requesting HALT\n");

 			write(sockfd, "HALT", 4);
 			usleep(1500000); // wait for server to close connection
 			exit(1);
 		}

 		n = write(sockfd, buffer, strlen(buffer)); // write the contents to the buffer
 	
 		if(n < 0)
 			printf("ERROR writing to socket: %s\n", strerror(errno));

 		bzero(buffer, 256);
 		n = read(sockfd, buffer, 255);

 		if(n < 0)
 			printf("ERROR read from socket: %s\n", strerror(errno));
 		else
 			printf("Server replied: %s\n", buffer);
 	}

 	return 1;
}