

if __name__ == '__main__':
    s = "我"
    print(s.encode())
    print(s.encode("gbk"))
    print(bytes(2))
    print(bytes(s, encoding="utf-8"))
