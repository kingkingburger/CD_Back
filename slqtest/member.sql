alter table member_table add phone varchar(50);
select * from member_table;
delete from member_table;


create table member_table(
memberId int not null auto_increment comment 'PK',
memberLoginId varchar(20) not null comment '로그인id',
memberName varchar(20) not null comment '회원이름',
memberPassword varchar(20) not null comment '비밀번호',
memberRank int default 1 not null comment '회원등급',
numberPerchase int default 0 not null comment '누적구매수',
createdDate timeStamp not null comment '생성일자',
primary key(memberId)
);
select * from member_table;
desc member_table;