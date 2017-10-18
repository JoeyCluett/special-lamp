GXX = gcc
OPTS = -lpthread -lm -Wall

all: server client

server: server.c
	$(GXX) -o server server.c $(OPTS)

client: client.c
	$(GXX) -o client client.c $(OPTS)

clean: 
	rm client
	rm server