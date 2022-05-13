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

def get_prod(url):
    options = webdriver.ChromeOptions()
    options.add_argument('headless')

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
    driver.implicitly_wait(30)

    driver.get(url)

    name = WebDriverWait(driver, 30).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'p.ProductName'))).text
    # price = product.find_element(by=By.CLASS_NAME, value='card-price ')
    price = WebDriverWait(driver, 30).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'div.ProductPrice'))).text
    prod_link = url  
    img_link = product.find_element(by=By.TAG_NAME, value='img')
    img_link = img_link.get_attribute('src')
    category = WebDriverWait(driver, 30).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'div.ProductCategory'))).text.split()

    data = []
    data.append(category)
    data.append(name)
    data.append(price)
    data.append(prod_link)
    data.append(img_link)
    dataset.append(data)

    return dataset


options = webdriver.ChromeOptions()
options.add_argument('headless')

driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
driver.implicitly_wait(30)

dataset = []
url = "https://cafe.naver.com/joonggonara.cafe?iframe_url=/ArticleList.nhn"

driver.get(url)
print(driver)

products = driver.find_elements(by=By.CLASS_NAME, value='article')
print(products)

for product in products:
    prod_link = product.get_attribute('href') 
    dataset = get_prod(prod_link)
    ############### click() 함수 사용시 오류 ######################
    # prod_desc = product.find_element(by=By.XPATH, value='//*[@id="content"]/section[1]/article[1]/a')
    # prod_desc.send_keys(Keys.ENTER) 


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