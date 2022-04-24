## Mysql 버전 8



## 테이블 생성 - Member

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



## 테이블 생성 - Product (22/04/22)

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





## 테이블 생성 - category

```sql
create table category(
    categoryid int not null auto_increment comment 'Pk',
    `first`  varchar(30) not null comment '1차 카테고리',
    `second` varchar(30) not null comment '2차 카테고리',
    primary key(categoryid)
);
```

product 테이블의 category들을 저장합니다. 



## 폴더구조

| 위치                                  | 설명                                 |
| ------------------------------------- | ------------------------------------ |
| src/main/com/example/demo/controller  | 컨트롤러 처리하는 곳                 |
| src/main/com/example/demo/config      | WebConfig, Interceptor 페이지를 관리 |
| src/main/com/example/demo/dto         | 데이터 오브젝트로 받는 곳            |
| src/main/com/example/demo/entity      | mysql table이 있는 곳                |
| src/main/com/example/demo/interceptor | interceptor 상새 설정                |
| src/main/com/example/demo/repository  | JPA문 쓰는 곳                        |
| src/main/com/example/demo/service     | DB에서 넘어온 데이터를 가공하는 곳   |





#### JPA로 쿼리쓰는법

https://sundries-in-myidea.tistory.com/91



#### **jpa에서 객체다루는 법**

https://junghwanta.tistory.com/5

https://cjw-awdsd.tistory.com/47
