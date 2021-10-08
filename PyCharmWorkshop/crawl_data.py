from bs4 import BeautifulSoup
import requests
import requests.exceptions
from urllib.parse import urlsplit
from collections import deque
import re
import os
import csv
from requests.packages.urllib3.exceptions import InsecureRequestWarning

requests.packages.urllib3.disable_warnings(InsecureRequestWarning)


class EmailCrawler:
    # email regular expression
    __email_addr_pattern = r"[a-z0-9\.\-+_]+@[a-z0-9\.\-+_]+\.[a-z]+"

    def crawl(self, urls):

        new_urls = deque()  # web list
        processed_urls = set()  # webs crawled
        emails = set()  # email set

        new_urls = deque(urls)

        # iteration until end
        while len(new_urls):
            # pop a web from queue top
            url = new_urls.popleft()
            processed_urls.add(url)

            # extract base_url to deal with relative path
            parts = urlsplit(url)
            base_url = "{0.scheme}://{0.netloc}".format(parts)
            path = url[:url.rfind('/') + 1] if '/' in parts.path else url

            # url content
            #             print("Processing %s" %url)
            try:
                response = requests.get(url, verify=False)
            #                 print("reading web success")
            #                 print(response.text)
            except (requests.exceptions.MissingSchema, requests.exceptions.ConnectionError):
                #                 print("error reading web")
                continue

            # find email using regular expression
            new_emails = re.search(self.__email_addr_pattern, response.text, re.I)

            # open a soup for current url text
            soup = BeautifulSoup(response.text, features="lxml")

            # add name and url to email set
            if new_emails:
                emails.add(soup.title.string.split('|', 1)[0].strip() + ',' + new_emails.group(0))

            # find anchor
            for anchor in soup.find_all('a'):
                # extract link from anchor
                link = anchor.attrs['href'] if 'href' in anchor.attrs else ''
                # deal with internal link
                if link.startswith('/'):
                    link = base_url + link
                elif not link.startswith('http'):
                    link = path + link
                if link.startswith('https://www.sustech.edu.cn/zh/') and len(
                        link) < 100 and not link in new_urls and not link in processed_urls:
                    new_urls.append(link)

        return emails


if __name__ == '__main__':
    urls = ['https://www.sustech.edu.cn/zh/shizihuancunyemian.html']
    emailCrawl = EmailCrawler()
    emails = emailCrawl.crawl(urls)
    with open('crawl_data.csv', 'w')as file:
        f = csv.writer(file)
        for item in emails:
            f.writerow(item)
    print('processing end')