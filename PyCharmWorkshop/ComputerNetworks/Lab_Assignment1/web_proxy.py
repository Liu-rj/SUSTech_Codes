# -*- coding: utf-8 -*-

import threading
import time
import json
from datetime import datetime
import sys
from socket import *


class ProxyServer(threading.Thread):
    cache = {}
    GMT_FORMAT = '%a, %d %b %Y %H:%M:%S GMT'
    shared_lock = threading.Lock()

    def __init__(self, conn, address):
        super().__init__()
        self.conn = conn
        self.address = address

    def run(self):
        request_raw = self.conn.recv(2048)
        data = request_raw.decode().split('\r\n')
        host = data[1].split(' ')[1]
        req_type = data[0].split(' ')[0]
        if host in ProxyServer.cache.keys() and req_type in ProxyServer.cache[host]:
            print('Read From Cache')
            with ProxyServer.shared_lock:
                with open('./proxy_cache.json', 'r', encoding='utf-8') as file:
                    send_back = json.load(file)[host][req_type]
            if req_type == 'GET' or req_type == 'HEAD':
                sock = socket()
                sock.connect((host, 80))
                sock.send(request_raw.decode()[:-2].encode() + (b'If-Modified-Since: %b\r\n\r\n' % send_back[1].encode()))
                response_raw = receive_all(sock)
                sock.close()
                status = response_raw.decode().split('\r\n')[0].split(' ')[1]
                print(response_raw)
                if status == '200':
                    self.conn.send(response_raw)
                    with ProxyServer.shared_lock:
                        with open('./proxy_cache.json', 'r', encoding='utf-8') as file:
                            data = json.load(file)
                    data[host][req_type] = (response_raw.decode(), datetime.utcnow().strftime(ProxyServer.GMT_FORMAT))
                    with ProxyServer.shared_lock:
                        with open('./proxy_cache.json', 'w', encoding='utf-8') as file:
                            json.dump(data, file)
                elif status == '304':
                    self.conn.send(send_back[0].encode())
            else:
                self.conn.send(send_back[0].encode())
        else:
            sock = socket()
            sock.connect((host, 80))
            sock.send(request_raw)
            response_raw = receive_all(sock)
            sock.close()
            self.conn.send(response_raw)
            with ProxyServer.shared_lock:
                try:
                    with open('./proxy_cache.json', 'r', encoding='utf-8') as file:
                        data = json.load(file)
                except FileNotFoundError:
                    data = {}
            if host not in ProxyServer.cache.keys():
                ProxyServer.cache[host] = [req_type]
                data[host] = {req_type: (response_raw.decode(), datetime.utcnow().strftime(ProxyServer.GMT_FORMAT))}
            elif req_type not in ProxyServer.cache[host]:
                ProxyServer.cache[host].append(req_type)
                data[host][req_type] = (response_raw.decode(), datetime.utcnow().strftime(ProxyServer.GMT_FORMAT))
            with ProxyServer.shared_lock:
                with open('./proxy_cache.json', 'w', encoding='utf-8') as file:
                    json.dump(data, file)
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
    try:
        with open('./proxy_cache.json', 'r', encoding='utf-8') as file:
            load_dict = json.load(file)
            for key in load_dict.keys():
                ProxyServer.cache[key] = list(load_dict[key].keys())
    except FileNotFoundError:
        pass
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
    ip = '127.0.0.1'
    port = 8080
    args = sys.argv[1:]
    for arg in args:
        if '.' in arg:
            ip = arg
        else:
            port = int(arg)
    try:
        start_proxy(ip, port)
    except KeyboardInterrupt:
        pass
