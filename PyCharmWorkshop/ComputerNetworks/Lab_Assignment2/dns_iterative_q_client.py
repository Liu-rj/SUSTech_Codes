# -*- coding: utf-8 -*-

import argparse
import dns.query
import re

ip_pattern = re.compile(r'((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(\.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}')


def init_args():
    # parse args
    arg_parser = argparse.ArgumentParser(description='DNS Query Description')
    arg_parser.add_argument('-q', type=str, required=True, help='the name of the query')
    arg_parser.add_argument('-s', type=str, required=True, help='source ip address of query')
    arg_parser.add_argument('-p', type=int, required=True, help='source port number of query')
    arg_parser.add_argument('-server', type=str, required=True, help='IP address of local DNS server')
    arg_parser.add_argument('-display', action='store_true', help='information about every DNS query and response')
    rtn_args = arg_parser.parse_args()
    return rtn_args


def dns_query(sname, qname, server, ip, port, answer):
    if display:
        print(f'({source_ip}#{source_port}) send query({qname} A IN rd=0) to DNS Server: ({sname} {server}#53)')
    query = dns.message.make_query(qname=qname, rdtype=dns.rdatatype.A)
    query.flags = 0x0020
    response = dns.query.udp(query, server, source=ip, source_port=port)
    if display:
        print(response.to_text(), '\n')
    if len(response.answer) != 0:
        cname = ''
        for ele in response.answer:
            if 'CNAME' in str(ele):
                for item in ele.items:
                    cname = str(item)
                    answer['Name'] = cname
            else:
                for item in ele.items:
                    addr = item.address
                    answer['Addresses'].append(addr)
        if cname != '':
            answer['Aliases'] = name
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
            else:
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

                dns_query(primary_ns, cname, primary_ip, source_ip, source_port, answer)
    elif len(response.additional) != 0:
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
        dns_query(server_name, qname, addr, ip, port, answer)
    elif len(response.authority) != 0:
        server_name = str(list(response.authority[0].items)[0]).split(' ')[0]
        inter = {'Name': server_name, 'Addresses': []}

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

        dns_query(primary_ns, server_name, primary_ip, source_ip, source_port, inter)
        server_ip = inter['Addresses'][0]
        dns_query(inter['Name'], qname, server_ip, ip, port, answer)


def output(answer):
    print('Final Answer:')
    print('Name:', answer['Name'])
    print('Addresses:')
    for addr in answer['Addresses']:
        print(addr)
    if 'Aliases' in answer.keys():
        print('Aliases:', answer['Aliases'])


if __name__ == '__main__':
    args = init_args()
    name = args.q
    source_ip = args.s
    source_port = args.p
    lds_ip = args.server
    display = args.display
    print(name, source_ip, source_port, lds_ip, display)

    # make a query about primary name server
    if display:
        print(f'({source_ip}#{source_port}) send query(<Root> NS IN rd=0) to DNS Server: (Local DNS server {lds_ip}#53)')
    query = dns.message.make_query(qname='<Root>', rdtype=dns.rdatatype.NS)
    query.flags = 0x0020
    response = dns.query.udp(query, lds_ip, source=source_ip, source_port=source_port)
    primary_ns = str(list(response.authority[0].items)[0]).split(' ')[0]
    if display:
        print(response.to_text(), '\n')

    # make a query about the ip of primary name server
    if display:
        print(f'({source_ip}#{source_port}) send query({primary_ns} A IN rd=0) to DNS Server: (Local DNS server {lds_ip}#53)')
    query = dns.message.make_query(qname=primary_ns, rdtype=dns.rdatatype.A)
    query.flags = 0x0020
    response = dns.query.udp(query, lds_ip, source=source_ip, source_port=source_port)
    primary_ip = str(list(response.answer[0].items)[0]).split(' ')[0]
    if display:
        print(response.to_text(), '\n')

    # iteratively get the answer and the cname if exists
    answer = {'Name': name, 'Addresses': []}
    dns_query(primary_ns, name, primary_ip, source_ip, source_port, answer)
    output(answer)
