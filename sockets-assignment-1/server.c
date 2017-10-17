#include <stdio.h>
#include <stdlib.h>     // for IOs
#include <string.h>
#include <unistd.h>     // usleep
#include <sys/types.h>  // for system calls
#include <sys/socket.h> // for sockets
#include <netinet/in.h> // for internet
#include <pthread.h>

void* client_thread(void* args);

int main(int argc, char *argv[]) {
 	int sockfd, portno;
 
 	socklen_t clilen; 
 	char buffer[256]; 
 	struct sockaddr_in serv_addr, cli_addr;
  
 	if (argc != 2) {
 		fprintf(stderr, "Usage: %s <port number>\n", argv[0]);
 		exit(1);
 	}

 	sockfd = socket(AF_INET, SOCK_STREAM, 0);
 
	if (sockfd < 0) {
 		printf("ERROR opening socket: %s\n", strerror(errno));
 		exit(1);
	}
 	
 	bzero((char *) &serv_addr, sizeof(serv_addr));
 
 	portno = atoi(argv[1]);
 	serv_addr.sin_family      = AF_INET;
 	serv_addr.sin_addr.s_addr = INADDR_ANY;
  	serv_addr.sin_port        = htons(portno);
 
 	if (bind(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
		printf("ERROR in binding: %S\n", strerror(errno));
		exit(1);
 	}
 
 	listen(sockfd,5); // accept 5 clients at most
 	clilen = sizeof(cli_addr);

 	while(1) {
 		int* newsockfd = (int*)malloc(sizeof(int));
	 	*newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen);
	 
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
	int sockfd = *(int*)args;

 	bzero(buffer,256);
 	int n = read(newsockfd,buffer,255);
 	if (n < 0) error("ERROR reading from socket");
 	printf("Here is the message: %s\n",buffer);
 
 	n = write(newsockfd,"I got your message",18);
 	if (n < 0) 
 		error("ERROR writing to socket");
 
 	close(newsockfd);

}
