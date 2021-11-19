import threading
import time
import json
import sys
from socket import *


class PacmanServer(threading.Thread):
    shared_lock = threading.Lock()

    def __init__(self, conn, address):
        super().__init__()
        self.conn = conn
        self.address = address

    def run(self):
        request_raw = self.conn.recv(2048)
        data = request_raw.decode()
        print(data)
        # host = data[1].split(' ')[1]
        # req_type = data[0].split(' ')[0]
        # sock = socket()
        # sock.connect((host, 80))
        # sock.send(request_raw)
        # response_raw = receive_all(sock)
        # sock.close()
        # self.conn.send(response_raw)
        # with ProxyServer.shared_lock:
        #     try:
        #         with open('./proxy_cache.json', 'r', encoding='utf-8') as file:
        #             data = json.load(file)
        #     except FileNotFoundError:
        #         data = {}
        # if host not in ProxyServer.cache.keys():
        #     ProxyServer.cache[host] = [req_type]
        #     data[host] = {req_type: (response_raw.decode(), datetime.utcnow().strftime(ProxyServer.GMT_FORMAT))}
        # elif req_type not in ProxyServer.cache[host]:
        #     ProxyServer.cache[host].append(req_type)
        #     data[host][req_type] = (response_raw.decode(), datetime.utcnow().strftime(ProxyServer.GMT_FORMAT))
        # with ProxyServer.shared_lock:
        #     with open('./proxy_cache.json', 'w', encoding='utf-8') as file:
        #         json.dump(data, file)
        # self.conn.close()


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


def start_listen(ip, port):
    users = {}
    try:
        with open('./user_data.json', 'r', encoding='utf-8') as file:
            users = json.load(file)
    except FileNotFoundError:
        pass
    # Create a server socket, bind it to a port and start listening
    listen_sock = socket(AF_INET, SOCK_STREAM)
    listen_sock.bind((ip, port))
    listen_sock.listen(10)
    while True:
        print('Ready to serve...')
        new_sock, address = listen_sock.accept()
        print('Received a connection from:', address)
        PacmanServer(new_sock, address).start()


if __name__ == "__main__":
    start_listen('127.0.0.1', 5000)
