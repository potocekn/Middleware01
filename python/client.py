#!/usr/bin/env python3

import socket
from datetime import datetime
from shared import *

def server_time (date_time_str):
    return datetime.strptime(date_time_str, '%Y-%m-%d %H:%M:%S')


# Safe handling of resources that require releasing on exceptions.
with socket.socket (socket.AF_INET, socket.SOCK_STREAM) as client_socket:

    client_socket.connect ((SERVER_ADDR, SERVER_PORT))
    client_socket.send ("H".encode ('utf-8'))
    print ('Received message:', server_time(client_socket.recv (SOCKET_BUFFER_SIZE).decode ('utf-8')))

    # Shutdown precedes close to make sure protocol level shutdown is executed completely.
    # Close without shutdown may use RST instead of FIN to terminate connection, dropping data that is in flight.
    #
    # It is also possible to use shutdown to close input and output streams independently.
    client_socket.shutdown (socket.SHUT_RDWR)
