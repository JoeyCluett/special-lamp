#include <stdio.h>      // printf
#include <stdlib.h>     // ...?
#include <string.h>     // strlen
#include <unistd.h>     // usleep
#include <sys/types.h>  // intx_t and friends
#include <sys/socket.h> // socket, setsockopt
#include <netinet/in.h> // for internet
#include <errno.h>      // errno, strerror
#include <pthread.h>    // pthread and friends
#include <signal.h>     // signal handling facilities

// helper functions: guaranteeWrite(), guaranteeRead()
#include "comm_help.h"

void* client_thread(void* args);

void ctrl_c(int signum) {
	printf("Exiting server program...\n");
	exit(signum);
}

int main(int argc, char *argv[]) {
 	int sockfd, portno;
 
 	socklen_t clilen; 
 	struct sockaddr_in serv_addr, cli_addr;
  
 	if (argc != 2) {
 		fprintf(stderr, "Usage: %s <port number>\n", argv[0]);
 		exit(1);
 	}

 	// register the sig-term callback
    signal(SIGINT, ctrl_c);

 	int option = 1;
 	sockfd = socket(AF_INET, SOCK_STREAM, 0);
 	
 	// reuse port numbers even if previous server exited abnoramlly:
 	setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option)); 

	if(sockfd < 0) {
 		printf("ERROR opening socket: %s\n", strerror(errno));
 		exit(1);
	}
 	
 	bzero((char *) &serv_addr, sizeof(serv_addr));
 
 	portno = atoi(argv[1]);
 	serv_addr.sin_family      = AF_INET;
 	serv_addr.sin_addr.s_addr = INADDR_ANY;
  	serv_addr.sin_port        = htons(portno);
 
 	if (bind(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
		printf("ERROR in binding: %s\n", strerror(errno));
		exit(1);
 	}
 
 	listen(sockfd, 5); // accept 5 clients at most
 	clilen = sizeof(cli_addr);

 	while(1) {
 		int* newsockfd = (int*)malloc(sizeof(int));

 		// blocks until new client connects
	 	*newsockfd = accept(sockfd, (struct sockaddr*)&cli_addr, &clilen);
	 
	 	if(*newsockfd < 0) {
	 		printf("ERROR on accept: %s\n", strerror(errno));
	 		//exit(1);
	 	} else {
	 		// fork pthreads thread for each client
	 		pthread_t* new_thread = (pthread_t*)malloc(sizeof(pthread_t));
	 		pthread_create(new_thread, NULL, client_thread, (void*)newsockfd);
	 	}
 	}

 	// close the server file descriptor
 	close(sockfd);
 	return 0;
}

void* client_thread(void* args) {
	// for now, only argument is socket file descriptor
	int sockfd = *(int*)args;
	printf("Socket file descriptor: %d\n", sockfd);

	// for receiving data
 	char buffer[256]; 

 	char* msg = "Type EXIT to close your connection\n";
 	write(sockfd, msg, strlen(msg)+1); // include null terminator

 	while(1) {

	 	bzero(buffer, 256);
	 	int n = read(sockfd, buffer, 255);

	 	if (n < 0) { 
	 		printf("ERROR reading from socket: %s\n", strerror(errno));
	 		close(sockfd);
	 		return 0; // null
	 	}

 		if(strcmp(buffer, "EXIT") == 0) {
 			printf("Client has requested EXIT\n");
 			close(sockfd); // close specific client connection
 			return NULL;   // client thread exits
 		} else if(strcmp(buffer, "HALT\n") == 0) {
 			printf("Client has requested HALT\n");
 			close(sockfd);
 			ctrl_c(SIGINT);
 			return NULL;
 		}
	 	

	 	printf("\nHere is the message: %s\n", buffer);
//	 	printf("Length of client message: %d\n", strlen(buffer));

	 	char* ret_msg = "I got your message";
	 	n = write(sockfd, ret_msg, strlen(ret_msg));

	 	if (n < 0) {
	 		printf("ERROR writing to socket: %s", strerror(errno));
	 		close(sockfd);
	 		return NULL; // null
	 	}

 	}	

 	close(sockfd);
 	return 0; // null
}
