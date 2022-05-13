from ast import Bytes
from itertools import product
from math import prod
import time
import pymysql
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def get_category(url):
    options = webdriver.ChromeOptions()
    options.add_argument('headless')

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
    driver.implicitly_wait(30)

    driver.get(url)

    category = driver.find_element(by=By.ID, value='article-category').text.split()
    # category = WebDriverWait(driver, 30).until(EC.presence_of_element_located((By.ID, 'article-category'))).text.split()
    # driver.close()
    return category[0]


options = webdriver.ChromeOptions()
options.add_argument('headless')

driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
driver.implicitly_wait(30)

dataset = []
# category = ""
url = "https://www.daangn.com/hot_articles"

driver.get(url)

products = driver.find_elements(by=By.CLASS_NAME, value='card-link ')

for product in products:

    name = WebDriverWait(product, 30).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'h2.card-title')))
    price = WebDriverWait(product, 30).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'div.card-price ')))
    prod_link = product.get_attribute('href')
    img_link = product.find_element(by=By.TAG_NAME, value='img')
    img_link = img_link.get_attribute('src')
    category = get_category(prod_link)

    ############### click() 함수 사용시 오류 ######################
    # prod_desc = product.find_element(by=By.XPATH, value='//*[@id="content"]/section[1]/article[1]/a')
    # prod_desc.send_keys(Keys.ENTER) 

    data = []
    data.append(category)
    data.append(name.text)
    data.append(price.text)
    data.append(prod_link)
    data.append(img_link)
    dataset.append(data)

connect = pymysql.connect(host='localhost', user='root', password='root', db='silk_road', charset='utf8mb4')
cursor = connect.cursor()

for data in dataset:
    category = str(data[0])
    name = str(data[1])
    price = str(data[2])
    prod_link = str(data[3])
    img_link = str(data[4])
            
    sql = """insert into crawling 
    (category, name, price, link, img_link) 
    values ('%s', '%s', '%s', '%s', '%s')
    """ % (category, connect.escape_string(name), price, prod_link, img_link)

    cursor.execute(sql)
    connect.commit()
    
connect.close()