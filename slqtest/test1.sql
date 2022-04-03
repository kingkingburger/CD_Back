create table member_table(
	userid int not null auto_increment,
    email varchar(50) not null,
    passwd varchar(50) not null,
    join_date datetime ,
    primary key (userid)
);
alter table member_table add name varchar(50) not null;
alter table member_table add unique index (name);
desc member_table;
select * from member_table where email="dnjsalsgh123@gmail.com" and passwd="1234";
delete from member_table ;
