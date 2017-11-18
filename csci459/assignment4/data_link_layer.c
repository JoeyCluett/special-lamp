#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include "net-structs.h"

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

 	if (argc != 4) {
 		fprintf(stderr, "Usage:\n    %s <wire addr> <wire port> <data port>\n", argv[0]);
 		exit(1);
 	}

    // connect to "physical wire" server
    SocketData* wire_sd = SD_StartClient(argv[1], atoi(argv[2]));

 	int option = 1;
 	sockfd = socket(AF_INET, SOCK_STREAM, 0);

 	// reuse port numbers even if previous server exited abnoramlly:
 	setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option));

	if(sockfd < 0) {
 		printf("ERROR opening socket:\n    %s\n", strerror(errno));
 		exit(1);
	}

 	bzero((char *) &serv_addr, sizeof(serv_addr));

 	portno = atoi(argv[3]);
 	serv_addr.sin_family      = AF_INET;
 	serv_addr.sin_addr.s_addr = INADDR_ANY;
  	serv_addr.sin_port        = htons(portno);

 	if (bind(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
		printf("ERROR in binding:\n    %s\n", strerror(errno));
		exit(1);
 	}

 	listen(sockfd, 20); // accept 20 clients at most
 	clilen = sizeof(cli_addr);

 	puts("Waiting for client connection to data link layer...");
    SocketData* sd_client = SD_GenerateSocketData(accept(sockfd, (struct sockaddr*)&cli_addr, &clilen));
    puts("Client accepted...");

    Frame f; // reuse Packet in Frame

    for(;;) {
        // generate a Frame from Packet
        if(SD_HasAvaialbleData(sd_client) > 0) {
            puts("New message from network layer...");

            SD_ReadPacket(sd_client, &f.my_packet);
            printf("%s: %s\n", f.my_packet.nickname, f.my_packet.message);

            f.seq_num = ++sd_client->mru_seq_num; // advance the sequence number every time
            f.type = 0;                           // type: data

            SD_WriteFrame(wire_sd, &f);

            if(is_exit(f.my_packet.message)) {
                SD_CloseConnection(sd_client);
                SD_CloseConnection(wire_sd);
                return 0;
            }

        }

        if(SD_HasAvaialbleData(wire_sd) > 0) {
            puts("New message from physical layer...");


            SD_ReadFrame(wire_sd, &f);
            puts("Frame read from physical layer...");


            SD_ForwardPacket(sd_client, &(f.my_packet)); // get Packet out of Frame
            puts("Packet written to network layer...");
        }

    }

}

