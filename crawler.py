import requests
import json
from bs4 import BeautifulSoup
import ast
import os
cwd = os.getcwd()

path_name = cwd + "/Active_Tidal_Stations.txt"

def NOAA_Crawler():
        url = "https://tidesandcurrents.noaa.gov/stations.html?type=All%20Stations&sort=0"
        source_code = requests.get(url)
        plain_text = source_code.text
        soup = BeautifulSoup(plain_text, "html.parser")
         
        ids = []
        dates = [] 
        dates_strip = []
        ids_strip = []

        f_all = soup.find_all("div", {"class": "container-fluid"})[1].find_all("div", {"class": "row-fluid"})[1].find_all("div", {"class": "span9"})[0].find_all("div", {"class": "row-fluid"})[0].find_all("a")
        
        f_all2 = soup.find_all("div", {"class": "container-fluid"})[1].find_all("div", {"class": "row-fluid"})[1].find_all("div", {"class": "span9"})[0].find_all("div", {"class": "row-fluid"})[0].find_all("span", {"class": "datefield"})
        
        for a in f_all:
            b = a.get_text(strip=True)
            c = b.split()
            if len(c) > 0:
                ids.append(c)
        
        for a in f_all2:
            b = a.get_text(strip=True)
            c = b.split()
            if len(c) > 0:
                dates.append(c)

        for index, a in enumerate(dates):
            if (len(a) > 4 and len(a) < 7):
                ids_strip.append(ids[index + 1])

        with open("%s" % path_name, "w") as f:
            for i in enumerate(ids_strip):
                print i[1][0]
                f.write("%s\n" % i[1][0])
NOAA_Crawler()