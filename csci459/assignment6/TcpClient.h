#ifndef __JJC__TCP__CLIENT__H__
#define __JJC__TCP__CLIENT__H__

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>

// structure containing all information needed to 
// connect to server, local or remote
typedef struct {
	int socketfd, portno;
	char* hostname;
	struct sockaddr_in serv_addr;
	struct hostent* server;
} TcpClient;

// needed function signatures
TcpClient* TcpClient_NewEmpty(void);
TcpClient* TcpClient_NewHostPort(char*, int);
void TcpClient_SetHostPort(TcpClient*, char*, int);
int TcpClient_Read(TcpClient*, char*, int);
int TcpClient_Write(TcpClient*, char*, int);

int TcpClient_Read(TcpClient* client, char* buf, int sz) {
	return read(client->socketfd, buf, sz);
}

int TcpClient_Write(TcpClient* client, char* buf, int sz) {
	return write(client->socketfd, buf, sz);
}

TcpClient* TcpClient_NewEmpty(void) {
	TcpClient* _client = (TcpClient*)malloc(sizeof(TcpClient));
	return _client;
}

TcpClient* TcpClient_NewHostPort(char* hostname, int port_number) {
	TcpClient* _client = TcpClient_NewEmpty();
	TcpClient_SetHostPort(_client, hostname, port_number);
	return _client;
}

void TcpClient_SetHostPort(TcpClient* client, char* _hostname, int port_number) {
	// assign for struct vars
	client->hostname = _hostname;
	client->portno = port_number;

	// specify type of socket (TCP in this case)
	client->socketfd = socket(AF_INET, SOCK_STREAM, 0);
	if(client->socketfd < 0) {
		int tmp_err = errno;
		printf("ERROR opening socket client: \n    %s\n", strerror(tmp_err));
		exit(1);
	}

	// evaluate hostname to actual ip address
	client->server = gethostbyname(client->hostname);
	if(client->server == NULL) {
		int tmp_err = errno;
		printf("ERROR no such host\n    %s\n", strerror(tmp_err));
		exit(1);
	}

	// configure server information
	bzero((char*)&client->serv_addr, sizeof(client->serv_addr));
	client->serv_addr.sin_family = AF_INET;
	bcopy((char*)client->server->h_addr, (char*)&client->serv_addr.sin_addr.s_addr, client->server->h_length);
	client->serv_addr.sin_port = htons(port_number);

	// the big boy right here
	// connect to the server, quit if fails
	if(connect(client->socketfd, (struct sockaddr*)&client->serv_addr, sizeof(client->serv_addr)) < 0) {
		int tmp_err = errno;
		printf("ERROR connecting to [%s:%d]\n    %s\n", client->hostname, port_number, strerror(tmp_err));
		exit(1);
	}
}

#endif // __JJC__TCP__CLIENT__H__