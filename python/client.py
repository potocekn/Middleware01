#!/usr/bin/env python3

import socket
from datetime import datetime
from shared import *

class CurrentServerTime:
    year = None
    month = None
    day = None
    hour = None
    minute = None
    second = None

    def __init__(self, year, month, day, hour, minute, second):
        self.year = year
        self.month = month
        self.day = day
        self.hour = hour
        self.minute = minute
        self.second = second


def server_time (date_time_str):
    date_time = datetime.strptime(date_time_str, '%Y-%m-%d %H:%M:%S')
    return CurrentServerTime(date_time.year, date_time.month, date_time.day, date_time.hour, date_time.minute, date_time.second)

# Safe handling of resources that require releasing on exceptions.
with socket.socket (socket.AF_INET, socket.SOCK_STREAM) as client_socket:

    client_socket.connect ((SERVER_ADDR, SERVER_PORT))
    client_socket.send ("H".encode ('utf-8'))
    recieved = server_time(client_socket.recv (SOCKET_BUFFER_SIZE).decode ('utf-8'))
    print ('Received message:', recieved.day, recieved.month, recieved.year)

    # Shutdown precedes close to make sure protocol level shutdown is executed completely.
    # Close without shutdown may use RST instead of FIN to terminate connection, dropping data that is in flight.
    #
    # It is also possible to use shutdown to close input and output streams independently.
    client_socket.shutdown (socket.SHUT_RDWR)
