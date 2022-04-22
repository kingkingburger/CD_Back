## Mysql 버전 8

#### 테이블 생성 - Member

22/04/07(최신)

```sql
create table member_table(
    memberid int not null auto_increment comment 'PK',
    member_loginid varchar(20) not null comment '로그인id',
    member_name varchar(20) not null comment '회원이름',
    member_password varchar(20) not null comment '비밀번호',
    member_phone varchar(20) not null comment '전화번호',
    member_rank int default 1 not null comment '회원등급',
    member_perchase int default 0 not null comment '누적구매수',
    created_date timeStamp not null comment '생성일자',
    primary key(memberId)
);
```

jpa에서 쿼리를 짤 때 첫 번째 만나는 대문자는 앞에 _ 붙히고 소문자로 바꾼다.

ex) entity가 MemberLoginid 라면 쿼리를 날릴 때 member_loginid로 바꿔서 날린다. 그러니 db컬럼의 이름을 바꿔줘야 한다.

출처: https://programmerah.com/how-to-fix-sql-error-1054-sqlstate-42s22-unknown-column-markcardex0_-art_service_time-in-field-list-3667/



#### 테이블 생성 - Product (22/04/22)

```sql
create table product(
    productid int not null auto_increment comment 'PK',
    memberid int not null comment '회원id',
    categoryid int not null comment '카테고리',
    product_name varchar(50) not null comment '물품 이름', 
    auction_price int default 0 comment '경매가격',
    product_price int not null comment '즉시거래가격',
    image_namesrc varchar(100) comment '이미지',
    product_explanation varchar(1000) default '설명이 없습니다.' comment '물품상세설명',
    created_date timeStamp not null comment'생성일자',
    primary key(productid),
    foreign key(memberid) references member_table (memberid) on update cascade on delete cascade,
    foreign key(categoryid) references category (categoryid) on update cascade on delete cascade
);
```

foreign key로 memberid, categoryid를 받습니다. 

on update, on delete 를 cascade로 설정해놔서 member_table에 있는 member가 수정, 삭제되면 자동으로 product 테이블도 수정이 됩니다.

**jpa에서 객체다루는 법**

https://junghwanta.tistory.com/5

https://cjw-awdsd.tistory.com/47



#### 테이블 생성 - category

```sql
create table category(
    categoryid int not null auto_increment comment 'Pk',
    `first`  varchar(30) not null comment '1차 카테고리',
    `second` varchar(30) not null comment '2차 카테고리',
    primary key(categoryid)
);
```

product 테이블의 category들을 저장합니다. 



## spring - db 설정

resources 파일 -> application.properties 설정

```
# 데이터 소스 (Data Source)
spring.datasource.hikari.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.hikari.jdbc-url=jdbc:mysql://localhost:3306/userdata?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.hikari.username=develop
spring.datasource.hikari.password=1234

# Resource and Thymeleaf Refresh
spring.devtools.livereload.enabled=true
spring.thymeleaf.cache=false

# JPA Properties
spring.jpa.database=mysql
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.generate-ddl=false
spring.jpa.hibernate.ddl-auto=none
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
```

#### 설정1

spring.datasource.hikari.jdbc-url=jdbc:mysql://localhost:3306/

**userdata(내 테이블 이름이 userdata 이다, 본인 db의 테이블 이름을 적어줘야함**?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true



#### 설정2

**spring.datasource.hikari.username=develop**
**spring.datasource.hikari.password=1234**

이 부분에 자기 db이름과 password를 넣으면 연결



#### JPA로 쿼리쓰는법

https://sundries-in-myidea.tistory.com/91


