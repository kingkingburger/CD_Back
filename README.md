## Mysql 버전 8

#### 테이블 생성

```sql
create table usersdata(
	id bigint not null auto_increment comment 'PK',
    created_date datetime not null comment '생성일',
    modified_date datetime comment '수정일',
    category varchar(100) not null comment '카테고리',
    username varchar(20) not null comment '작성자',
    price int not null comment '경매가격',
    immediatelyprice int not null comment '즉시구매가',
    title varchar(100) not null comment '제목',
    location varchar(100) not null comment '위치',
    imgsrc varchar(100) not null comment '이미지경로',
    usersdata enum('Y','N') not null comment '삭제 여부',
    primary key (id)
) comment '유저정보';
```



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



| **속성명**     | **Key**     | **null** **유무** | **Date type(size)** | **설명**   |
| -------------- | ----------- | ----------------- | ------------------- | ---------- |
| memberid       | primary key | not null          | int                 | 회원id     |
| memberLoginid  |             | not null          | varchar(20)         | 로그인id   |
| memberName     |             | not null          | varchar(20)         | 회원이름   |
| memberPassword |             | not null          | varchar(20)         | 비밀번호   |
| memberRank     |             | not null          | int                 | 회원등급   |
| memberPerchase |             | not null          | int                 | 누적구매수 |
| memberPhone    |             | not null          | string              | 핸드폰번호 |
| createdDate    |             | not null          | timestamp           | 생성일자   |
