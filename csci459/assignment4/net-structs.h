#ifndef __JJC__NET__STRUCTS__H__
#define __JJC__NET__STRUCTS__H__

// Pointers! Pointers everywhere!!

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>     // read, write
#include <string.h>     // strlen
#include <sys/types.h>
#include <sys/socket.h> // socket
#include <sys/ioctl.h>  // ioctl
#include <netinet/in.h>
#include <netdb.h>
#include <errno.h>      // strerror, errno

typedef struct {
    char nickname[10];
    char message[256];
} __attribute__((packed)) Packet;

typedef struct {
    int seq_num;
    int type;
    Packet my_packet;
} __attribute__((packed)) Frame;

/*
    SocketData is where i store
    ALL data associated with a
    given TCP socket connection
*/
typedef struct {
    int sockfd, portno;
 	socklen_t clilen;
 	struct sockaddr_in serv_addr, cli_addr;
 	struct hostent* server; // only needed for clients
    char* nickname; // only needed for clients
    int mru_seq_num;
} SocketData;

int SD_HasAvaialbleData(SocketData* sd) {
    int iocount;
    ioctl(sd->sockfd, FIONREAD, &iocount);
    return iocount;
}

void SD_CloseConnection(SocketData* sd) {
    close(sd->sockfd);
}

void SD_SetNickname(SocketData* sd, char* nickname) {
    // quick error check
    if(strlen(nickname) > 9) {
        printf("Nickname too long: %s\n", nickname);
        exit(1);
    }

    sd->nickname = nickname;

}

SocketData* SD_GenerateSocketData(int fd) {
    SocketData* sd = (SocketData*)malloc(sizeof(SocketData));
    sd->sockfd = fd;
    return sd;
}

SocketData* SD_StartClient(const char* hostname, int port_number) {
    SocketData* sd = (SocketData*)malloc(sizeof(SocketData));
    sd->portno = port_number;

    sd->sockfd = socket(AF_INET, SOCK_STREAM, 0); // indicate IPv4 and TCP connection
 	if(sd->sockfd < 0) {
 		printf("Error opening socket:\n    %s\n", strerror(errno));
 		exit(1);
 	}

    sd->server = gethostbyname(hostname);
    if(sd->server == NULL) {
		fprintf(stderr,"ERROR, no such host\n");
		exit(1); // exit error condition
 	}

 	bzero((char*)&sd->serv_addr, sizeof(sd->serv_addr));
 	sd->serv_addr.sin_family = AF_INET;
 	bcopy((char*)sd->server->h_addr, (char*)&sd->serv_addr.sin_addr.s_addr, sd->server->h_length);
 	sd->serv_addr.sin_port = htons(sd->portno);

 	if(connect(sd->sockfd,(struct sockaddr*)&sd->serv_addr, sizeof(sd->serv_addr)) < 0) {
 		printf("ERROR connecting:\n    %s\n", strerror(errno));
 		exit(1);
 	}

    sd->mru_seq_num = 0;
    return sd;
}

// how server interfaces with clients
static int writeChunk(SocketData* sd, char* buffer, int s) {
    int bytes_writ = 0;

    while(bytes_writ < s) {
        bytes_writ += write(sd->sockfd, buffer+bytes_writ, s-bytes_writ);
    }

    return bytes_writ;
}

static int readChunk(SocketData* fd, char* buffer, int s) {
    int bytes_read = 0;

    while(bytes_read < s) {
        bytes_read += read(fd->sockfd, buffer+bytes_read, s-bytes_read);
    }

    return bytes_read;
}

void SD_WriteFrame(SocketData* sd, Frame* f) {
    writeChunk(sd, (char*)f, sizeof(Frame));
}

void SD_ReadFrame(SocketData* sd, Frame* f) {
    readChunk(sd, (char*)f, sizeof(Frame));
}

// how client interfaces with data link layer
void SD_WritePacket(SocketData* sd, Packet* p) {
    memcpy(p->nickname, sd->nickname, strlen(sd->nickname));
    writeChunk(sd, (char*)p, sizeof(Packet));
}

void SD_ReadPacket(SocketData* sd, Packet* p) {
    // read a full frame before unpacking data
    readChunk(sd, (char*)p, sizeof(Packet)); // read from data link layer
}

// data link layer simply forwards the data
void SD_ForwardPacket(SocketData* sd, Packet* p) {
    writeChunk(sd, (char*)p, sizeof(Packet));
}

#endif // __JJC__NET__STRUCTS__H__








