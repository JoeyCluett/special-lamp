#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include "net-structs.h"

void ctrl_c(int signum) {
	printf("\nExiting server program...\n");
	exit(signum);
}

int is_exit(char* word) {
    if(strlen(word) < 4)
        return 0;

    if((word[0] == 'E') && (word[1] == 'X') && (word[2] == 'I') && (word[3] == 'T'))
        return 1;

    return 0;
}

int main(int argc, char* argv[]) {
    int sockfd, portno;
 	socklen_t clilen;
 	struct sockaddr_in serv_addr, cli_addr;

 	if (argc != 2) {
 		fprintf(stderr, "Usage:\n    %s <port number>\n", argv[0]);
 		exit(1);
 	}

 	// register the sig-term callback
    signal(SIGINT, ctrl_c);

 	int option = 1;
 	sockfd = socket(AF_INET, SOCK_STREAM, 0);

 	// reuse port numbers even if previous server exited abnoramlly:
 	setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option));

	if(sockfd < 0) {
 		printf("ERROR opening socket:\n    %s\n", strerror(errno));
 		exit(1);
	}

 	bzero((char *) &serv_addr, sizeof(serv_addr));

 	portno = atoi(argv[1]);
 	serv_addr.sin_family      = AF_INET;
 	serv_addr.sin_addr.s_addr = INADDR_ANY;
  	serv_addr.sin_port        = htons(portno);

 	if (bind(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
		printf("ERROR in binding:\n    %s\n", strerror(errno));
		exit(1);
 	}

 	listen(sockfd, 5); // accept 5 clients at most
 	clilen = sizeof(cli_addr);

 	SocketData* sd_arr[2];

 	puts("Waiting for client connections to physical layer...");
    sd_arr[0] = SD_GenerateSocketData(accept(sockfd, (struct sockaddr*)&cli_addr, &clilen));
    puts("Waiting for second client connection to physical layer...");
    sd_arr[1] = SD_GenerateSocketData(accept(sockfd, (struct sockaddr*)&cli_addr, &clilen));
    puts("Both clients connected...");

    Frame f;

    for(;;) {
        usleep(100000); // poll at ~10Hz

        int i;
        for(i = 0; i < 2; i++) {
            if(sd_arr[i]->sockfd != -1 && SD_HasAvaialbleData(sd_arr[i]) > 0) {
                SD_ReadFrame(sd_arr[i], &f); // server deals ONLY with frames
                printf("New message from %s\n    %s\n", f.my_packet.nickname, f.my_packet.message);

                if(is_exit(f.my_packet.message)) {
                    puts("Closing socket connection...");
                    SD_CloseConnection(sd_arr[i]);
                    sd_arr[i]->sockfd = -1; // exit status
                }

                if(sd_arr[(i+1)%2]->sockfd != -1) // only write if other socket is still open
                    SD_WriteFrame(sd_arr[(i+1)%2], &f);
            }
        }

        // exit if both connections are invalid
        if(sd_arr[0]->sockfd == -1 && sd_arr[1]->sockfd == -1) {
            close(sockfd);
            ctrl_c(0); // will exit the program
        }

    }

    return 0; // will never get to this point
}
