import time
import pymysql
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from bs4 import BeautifulSoup
import schedule

def crawling():
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
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')

        if category_num == "310":
            category = "여성의류"
        elif category_num == "320":
            category = "남성의류"
        elif category_num == "405" or category_num == "430" or category_num == "420" or category_num == "400": # 신발, 가방, 시계/쥬얼리, 패션 악세서리
            category = "패션잡화"
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
        elif category_num == "900" or category_num == "920":
            category = "도서/티켓/문구/음악"
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
        
        name = soup.find_all('div', 'hmkmpv')
        price = soup.find_all('div', 'kwIxAx')
        prod_link = soup.find_all('a', 'hjcqIZ')
        img_link = soup.find_all('div', 'kSFMjp')
        
        i = 0
        for data in zip(name, price, prod_link, img_link):
            if i > 10:
                break
            data = list(data)
            data.insert(0, category)
            dataset.append(data)
            i += 1

    connect = pymysql.connect(host='localhost', user='junho', password='1234', db='silkload', charset='utf8mb4')
    cursor = connect.cursor()
    
    # delete = """delete from crawling where exists(select * from (select * from crawling) c)"""
    # cursor.execute(delete)
        
    alter = """alter table crawling auto_increment=1"""
    cursor.execute(alter)

    connect.commit()

    for data in dataset:
        category = str(data[0])
        name = str(data[1].text)
        price = str(data[2].text)
        prod_link = str('https://m.bunjang.co.kr'+data[3]['href'])
        img_link = str(data[4].find('img')['src'])
                
        insert = """insert into crawling 
        (category, name, price, link, img_link) 
        values ('%s', '%s', '%s', '%s', '%s')
        """ % (category, connect.escape_string(name), price, prod_link, img_link)

        cursor.execute(insert)
        
    connect.commit()
    connect.close()

crawling()

schedule.every(24).hours.do(crawling)

while True:
    schedule.run_pending()
    time.sleep(1)