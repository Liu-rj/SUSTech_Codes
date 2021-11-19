# -*- coding: utf-8 -*-

import argparse
import dns.query


def init_args():
    # parse args 初始化和解析参数
    arg_parser = argparse.ArgumentParser(description='DNS Query Description')
    arg_parser.add_argument('-q', type=str, required=True, help='the name of the query')
    arg_parser.add_argument('-s', type=str, required=True, help='source ip address of query')
    arg_parser.add_argument('-p', type=int, required=True, help='source port number of query')
    arg_parser.add_argument('-server', type=str, required=True, help='IP address of local DNS server')
    arg_parser.add_argument('-display', action='store_true', help='information about every DNS query and response')
    rtn_args = arg_parser.parse_args()
    return rtn_args


# 传入参数：当前访问的server name，当前要查询的内容，当前访问的server ip，本机ip，本机开放端口，最终结果集
def dns_query(sname, qname, server, ip, port, answer):
    if display:
        print(f'({source_ip}#{source_port}) send query({qname} A IN rd=0) to DNS Server: ({sname} {server}#53)')
    # 首先对当前访问的server做一次手动查询
    query = dns.message.make_query(qname=qname, rdtype=dns.rdatatype.A)
    query.flags = 0x0020
    # 拿到本次查询的结果
    response = dns.query.udp(query, server, source=ip, source_port=port)
    if display:
        print(response.to_text(), '\n')
    # 下面开始分情况讨论：
    # 如果response中有answer
    if len(response.answer) != 0:
        cname = ''
        # 遍历answer查看结果
        for ele in response.answer:
            # 如果answer的类型是CNAME，将CNAME提取出来存入cname变量
            if 'CNAME' in str(ele):
                for item in ele.items:
                    cname = str(item)
                    answer['Name'] = cname
            # 如果不是CNAME，则把A类型地址提取出来存入最终答案
            else:
                for item in ele.items:
                    addr = item.address
                    answer['Addresses'].append(addr)
        # 如果answer是CNAME，则还需要根据得到的cname进一步做迭代查询
        if cname != '':
            answer['Aliases'] = name
            # 如果response中有additional rr，里面存储了canonical name server的地址，
            # 则拿到server的地址向其发起请求，请求内容是CNAME
            if len(response.additional) != 0:
                find = False
                addr = ''
                server_name = ''
                i = 0
                for ele in response.additional:
                    for item in ele.items:
                        if item.rdtype == dns.rdatatype.A:
                            addr = item.address
                            server_name = ele.name.to_text()
                            find = True
                    if find:
                        break
                    i += 1
                dns_query(server_name, cname, addr, ip, port, answer)
            # 如果response中没有additional rr，
            # 那就需要从local dns server重新开始迭代询问CNAME name server的IP地址
            else:
                # make a query of primary name server
                if display:
                    print(
                        f'({source_ip}#{source_port}) send query(<Root> NS IN rd=0) to DNS Server: (Local DNS server {lds_ip}#53)')
                query = dns.message.make_query(qname='<Root>', rdtype=dns.rdatatype.NS)
                query.flags = 0x0020
                response = dns.query.udp(query, lds_ip, source=source_ip, source_port=source_port)
                primary_ns = str(list(response.authority[0].items)[0]).split(' ')[0]
                if display:
                    print(response.to_text(), '\n')

                # make a query about the ip of primary name server
                if display:
                    print(
                        f'({source_ip}#{source_port}) send query({primary_ns} A IN rd=0) to DNS Server: (Local DNS server {lds_ip}#53)')
                query = dns.message.make_query(qname=primary_ns, rdtype=dns.rdatatype.A)
                query.flags = 0x0020
                response = dns.query.udp(query, lds_ip, source=source_ip, source_port=source_port)
                primary_ip = str(list(response.answer[0].items)[0]).split(' ')[0]
                if display:
                    print(response.to_text(), '\n')

                # make a query about the of cname server
                dns_query(primary_ns, cname, primary_ip, source_ip, source_port, answer)
    # 如果response中没有answer，但是又additional rr，可以从additional rr中找到下一步该访问的server和ip
    elif len(response.additional) != 0:
        find = False
        addr = ''
        server_name = ''
        i = 0
        for ele in response.additional:
            for item in ele.items:
                # 这里要判断是否是additional rr的地址是否是A类型，因为有可能是AAAA类型，即ipv6地址
                if item.rdtype == dns.rdatatype.A:
                    addr = item.address
                    server_name = ele.name.to_text()
                    find = True
            if find:
                break
            i += 1
        dns_query(server_name, qname, addr, ip, port, answer)
    # 如果response中既没有answer rr也没有additional rr，那就只能从authority rr下手，先找到下一步要访问的name server，
    # 然后重新从local dns server开始做迭代查询找到对应name server的ip
    elif len(response.authority) != 0:
        # 拿到authority rr中的name server
        server_name = str(list(response.authority[0].items)[0]).split(' ')[0]
        inter = {'Name': server_name, 'Addresses': []}

        # make a query of primary name server
        if display:
            print(
                f'({source_ip}#{source_port}) send query(<Root> NS IN rd=0) to DNS Server: (Local DNS server {lds_ip}#53)')
        query = dns.message.make_query(qname='<Root>', rdtype=dns.rdatatype.NS)
        query.flags = 0x0020
        response = dns.query.udp(query, lds_ip, source=source_ip, source_port=source_port)
        primary_ns = str(list(response.authority[0].items)[0]).split(' ')[0]
        if display:
            print(response.to_text(), '\n')

        # make a query about the ip of primary name server
        if display:
            print(
                f'({source_ip}#{source_port}) send query({primary_ns} A IN rd=0) to DNS Server: (Local DNS server {lds_ip}#53)')
        query = dns.message.make_query(qname=primary_ns, rdtype=dns.rdatatype.A)
        query.flags = 0x0020
        response = dns.query.udp(query, lds_ip, source=source_ip, source_port=source_port)
        primary_ip = str(list(response.answer[0].items)[0]).split(' ')[0]
        if display:
            print(response.to_text(), '\n')

        # make a query about the ip of name server in authority rr
        dns_query(primary_ns, server_name, primary_ip, source_ip, source_port, inter)
        server_ip = inter['Addresses'][0]

        # 拿到authority rr的ip地址后，继续向该ip地址发起迭代查询
        dns_query(inter['Name'], qname, server_ip, ip, port, answer)


def output(answer):
    # 打印最终结果
    print('Final Answer:')
    print('Name:', answer['Name'])
    print('Addresses:')
    for addr in answer['Addresses']:
        print(addr)
    if 'Aliases' in answer.keys():
        print('Aliases:', answer['Aliases'])


if __name__ == '__main__':
    # 参数接受与赋值
    args = init_args()
    name = args.q
    source_ip = args.s
    source_port = args.p
    lds_ip = args.server
    display = args.display

    # make a query of primary name server
    # 首先发起一个请求，向local dns server询问根服务器name server
    if display:
        print(f'({source_ip}#{source_port}) send query(<Root> NS IN rd=0) to DNS Server: (Local DNS server {lds_ip}#53)')
    query = dns.message.make_query(qname='<Root>', rdtype=dns.rdatatype.NS)
    query.flags = 0x0020
    response = dns.query.udp(query, lds_ip, source=source_ip, source_port=source_port)
    primary_ns = str(list(response.authority[0].items)[0]).split(' ')[0]
    if display:
        print(response.to_text(), '\n')

    # make a query of the ip of primary name server
    # 根据得到的根服务器name server，再次向local dns server发起请求，询问根服务器的地址
    if display:
        print(f'({source_ip}#{source_port}) send query({primary_ns} A IN rd=0) to DNS Server: (Local DNS server {lds_ip}#53)')
    query = dns.message.make_query(qname=primary_ns, rdtype=dns.rdatatype.A)
    query.flags = 0x0020
    response = dns.query.udp(query, lds_ip, source=source_ip, source_port=source_port)
    primary_ip = str(list(response.answer[0].items)[0]).split(' ')[0]
    if display:
        print(response.to_text(), '\n')

    # iteratively get the answer and the cname if exists
    # 接下来做迭代查询， 这里利用递归实现本机的迭代查询
    answer = {'Name': name, 'Addresses': []}
    dns_query(primary_ns, name, primary_ip, source_ip, source_port, answer)
    output(answer)
