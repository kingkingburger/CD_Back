from ast import Bytes
from itertools import product
import time
import pymysql
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# def make_dataset(products):
#     for product in products:
#         name = product.find_element(by=By.CLASS_NAME, value='hmkmpv')
#         price = product.find_element(by=By.CLASS_NAME, value='kwIxAx')
#         price = WebDriverWait(driver, 10). until(EC.presence_of_element_located(By.CLASS_NAME, 'kwIxAx'))
#         prod_link = product.get_attribute('href')
#         img_link = product.find_element(by=By.TAG_NAME, value='img')
#         img_link = img_link.get_attribute('src')

#         data = []
#         data.append(category)
#         data.append(name.text)
#         data.append(price.text)
#         data.append(prod_link)
#         data.append(img_link)
#         dataset.append(data)
    
#     return dataset

options = webdriver.ChromeOptions()
options.add_argument('headless')

driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
driver.implicitly_wait(30)

category_list = ["310", "320", "405", "430", "420", "400", "600", "700", "750", "910", "930", "990", "920", "900", "410", "810", "800", "500", "980", "999"]

dataset = []

for category_num in category_list:
    url = "https://m.bunjang.co.kr/categories/"+category_num
    print(url)

    driver.get(url)

    products = driver.find_elements(by=By.CLASS_NAME, value='hjcqIZ')

    if category_num == "310":
        category = "여성의류"
    elif category_num == "320":
        category = "남성의류"
    elif category_num == "405":
        category = "신발"
    elif category_num == "430":
        category = "가방"
    elif category_num == "420":
        category = "시계/쥬얼리"
    elif category_num == "400":
        category = "패션 액세서리"
    elif category_num == "600":
        category = "디지털/가전"
    elif category_num == "700":
        category = "스포츠/레저"
    elif category_num == "750":
        category = "차량/오토바이"
    elif category_num == "910":
        category = "스타굿즈"
    elif category_num == "930":
        category = "키덜트"
    elif category_num == "990":
        category = "예술/희귀/수집품"
    elif category_num == "920":
        category = "음반/악기"
    elif category_num == "900":
        category = "도서/티켓/문구"
    elif category_num == "410":
        category = "뷰티/미용"
    elif category_num == "810":
        category = "가구/인테리어"
    elif category_num == "800":
        category = "생활/가공식품"
    elif category_num == "500":
        category = "유아동/출산"
    elif category_num == "980":
        category = "반려동물용품"
    else:
        category = "기타"    

    for product in products:
        name = product.find_element(by=By.CLASS_NAME, value='hmkmpv')
        # price = product.find_element(by=By.CLASS_NAME, value='kwIxAx')
        price = WebDriverWait(driver, 30).until(EC.presence_of_element_located((By.CLASS_NAME, 'kwIxAx')))
        prod_link = product.get_attribute('href')
        img_link = product.find_element(by=By.TAG_NAME, value='img')
        img_link = img_link.get_attribute('src')

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