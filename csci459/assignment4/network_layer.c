#include <stdio.h>
#include "net-structs.h"

int is_exit(char* word) {
    if(strlen(word) < 4)
        return 0;

    if((word[0] == 'E') && (word[1] == 'X') && (word[2] == 'I') && (word[3] == 'T'))
        return 1;

    return 0;
}

int main(int argc, char* argv[]) {
    // quick error check
    if(argc != 4) {
        printf("Usage:\n    %s <data addr> <data port> <nickname>\n", argv[0]);
        exit(1);
    }

    printf("Connecting to %s at %d\n", argv[1], atoi(argv[2]));

    SocketData* sd = SD_StartClient("localhost", (int)atoi(argv[2]));
    SD_SetNickname(sd, argv[3]);

    // GO!
    puts("Ready to communicate...\n");

    for(;;) {
        Packet p;
        bzero(p.message, 256);

        puts("Message: ");
        do {
            fgets(p.message, 255, stdin);
        } while(p.message[0] == '\n');

        SD_SetNickname(sd, argv[3]);
        SD_WritePacket(sd, &p); // client deals exclusively with Packets

        if(is_exit(p.message)) {
            SD_CloseConnection(sd);
            puts("Closing server connection...\n");
            return 0;
        }

        if(SD_HasAvaialbleData(sd) > 0) {
            SD_ReadPacket(sd, &p); // again... only Packets
            printf("\nRecv: %s\n    from %s\n", p.message, p.nickname);
        }

    }

    return 0;
}
