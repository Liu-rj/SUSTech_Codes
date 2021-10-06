# -*- coding: utf-8 -*-

import threading
import os
import glob
import time
from socket import *


class ProxyServer(threading.Thread):
    cache = {}

    def __init__(self, conn, address):
        super().__init__()
        self.conn = conn
        self.address = address

    def run(self):
        request_raw = self.conn.recv(2048)
        data = request_raw.decode().split('\r\n')
        host = data[1].split(' ')[1]
        req_type = data[0].split(' ')[0]
        print(host, 'request type: ', req_type)
        print(request_raw.decode().split('\r\n'))
        if host not in ProxyServer.cache.keys():
            sock = socket()
            sock.connect((host, 80))
            sock.send(request_raw)
            response_raw = receive_all(sock)
            sock.close()
            self.conn.send(response_raw)
            cache_name = host[host.index('.') + 1:host.rindex('.')]
            ProxyServer.cache[host] = cache_name
            with open('./{}.txt'.format(cache_name), 'w', encoding='utf-8') as file:
                file.write(response_raw.decode().replace('\r\n', '\n'))
        else:
            print('Read From Cache')
            with open('./{}.txt'.format(ProxyServer.cache[host]), 'r', encoding='utf-8') as file:
                send_back = file.read()
            # send_back = send_back[send_back.index('<!'):]
            self.conn.send(send_back.encode())
        self.conn.close()


def receive_all(the_socket, time_limit=1):
    the_socket.setblocking(0)
    total_data = b''
    begin = time.time()
    while 1:
        if total_data and time.time() - begin > time_limit:
            break
        elif time.time() - begin > time_limit * 2:
            break
        try:
            data = the_socket.recv(2048)
            if data:
                total_data += data
                begin = time.time()
            else:
                time.sleep(0.1)
        except error:
            pass
    return total_data


def start_proxy(ip='127.0.0.1', port=8080):
    print(ip, ':', port)
    for file in glob.glob(os.path.join('.', '*.txt')):
        os.remove(file)
    # Create a server socket, bind it to a port and start listening
    tcp_ser_sock = socket(AF_INET, SOCK_STREAM)
    # Fill in start.
    tcp_ser_sock.bind((ip, port))
    tcp_ser_sock.listen(10)
    # Fill in end.
    while True:
        # Start receiving data from the client
        print('Ready to serve...')
        tcp_cli_sock, address = tcp_ser_sock.accept()
        print('Received a connection from:', address)
        # Proxy the request from the client
        ProxyServer(tcp_cli_sock, address).start()


if __name__ == "__main__":
    try:
        start_proxy()
    except KeyboardInterrupt:
        pass
