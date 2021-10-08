# -*- coding: utf-8 -*-

#NOTE: you are NOT allowed to use the "requests" library in python in the assignment
from socket import *

#default IP and PORT
IP = '127.0.0.1'
PORT = 8080

# Create a server socket
tcpSerSock = socket(AF_INET, SOCK_STREAM)
#bind it to the IP and Port,start listening and work
# Fill in start.
# Fill in end.
while 1:
    # Strat receiving data from the client
    print('Ready to serve...')
    tcpCliSock, addr = tcpSerSock.accept()
    print('Received a connection from:', addr)
    # Proxy the request from the clinet
    # Fill in start.
    # Fill in end.
    tcpCliSock.close()

# Fill in start.
# Fill in end.
