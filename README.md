## Mysql 버전 8

#### 테이블 생성

22/04/07(최신)

```sql
create table member(
    memberId int not null auto_increment comment 'PK',
    memberLoginId varchar(20) not null comment '로그인id',
    memberName varchar(20) not null comment '회원이름',
    memberPassword varchar(20) not null comment '비밀번호',
    memberRank int default 1 not null comment '회원등급',
    numberPerchase int default 0 not null comment '누적구매수',
    createdDate timeStamp not null comment '생성일자',
    primary key(memberId)
);
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
