# -*- coding: utf-8 -*-

import argparse
import sys
import socket
import dns.resolver
import dns.flags as flags

if __name__ == '__main__':
    arg_parser = argparse.ArgumentParser(description='DNS Query Description')
    arg_parser.add_argument('-q', type=str, required=True, help='the name of the query')
    arg_parser.add_argument('-s', type=str, required=True, help='source ip address of query')
    arg_parser.add_argument('-p', type=int, required=True, help='source port number of query')
    arg_parser.add_argument('-server', type=str, required=True, help='IP address of local DNS server')
    args = arg_parser.parse_args()
    name = args.q
    source_ip = args.s
    source_port = args.p
    lds_ip = args.server
    print(name, source_ip, source_port, lds_ip)
    dns_resolver = dns.resolver.Resolver()
    dns_resolver.nameservers = [lds_ip]
    dns_resolver.set_flags(0x0020)
    dns_answers = dns_resolver.resolve(name, 'A', source=source_ip, source_port=source_port)
    if dns_answers.rrset is not None:
        print(dns_answers.rrset)
    for i in dns_answers:
        print(i)
