import socket


def echo():
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind(('127.0.0.1', 5555))
    sock.listen(10)
    bts = bytes('', 'utf-8')
    while True:
        conn, address = sock.accept()
        while True:
            data = conn.recv(2048)
            bts += data
            if data and data == b'\r\n' and bts != b'exit\r\n':
                conn.send(bts)
                print(bts)
                bts = bytes('', 'utf-8')
            elif bts == b'exit\r\n':
                conn.close()
                break


if __name__ == "__main__":
    try:
        echo()
    except KeyboardInterrupt:
        pass
