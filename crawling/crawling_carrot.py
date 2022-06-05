import time
import pymysql
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from bs4 import BeautifulSoup
import schedule

def get_category(url):
    options = webdriver.ChromeOptions()
    options.add_argument('headless')

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
    driver.implicitly_wait(30)

    driver.get(url)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    category = soup.find('p', {'id': 'article-category'}).text.split()

    if category[0] == "디지털기기":
        category[0] = "디지털/가전"
    elif category[0] == "생활가전":
        category[0] = "디지털/가전"
    elif category[0] == "유아동":
        category[0] = "유아동/출산"
    elif category[0] == "유아도서":
        category[0] = "도서/티켓/문구/음악"
    elif category[0] == "남성패션/잡화":
        category[0] = "남성의류"
    elif category[0] == "게임/취미":
        category[0] = "키덜트"
    elif category[0] == "식물" or category[0] == "기타중고물품":
        category[0] = "기타"

    return category[0]

def crawling():
    options = webdriver.ChromeOptions()
    options.add_argument('headless')

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
    driver.implicitly_wait(30)

    dataset = []

    url = "https://www.daangn.com/hot_articles"

    driver.get(url)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    name = soup.find_all('h2', 'card-title')
    price = soup.find_all('div', 'card-price')
    prod_link = soup.find_all('a', 'card-link')
    img_link = soup.find_all('div', 'card-photo')

    connect = pymysql.connect(host='localhost', user='root', password='1234', db='silkload', charset='utf8mb4')
    cursor = connect.cursor()

    for data in zip(name, price, prod_link, img_link):
        name = str(data[0].text)
        price = str(data[1].text)
        prod_link = str('https://www.daangn.com'+data[2]['href'])
        img_link = str(data[3].find('img')['src'])
        category = get_category(prod_link)
                
        sql = """insert into crawling 
        (category, name, price, link, img_link) 
        values ('%s', '%s', '%s', '%s', '%s')
        """ % (category, connect.escape_string(name), price, prod_link, img_link)

        cursor.execute(sql)
        connect.commit()
        
    connect.close()

crawling()

#schedule.every(24).hours.do(crawling)

#while True:
    #schedule.run_pending()
    #time.sleep(1)