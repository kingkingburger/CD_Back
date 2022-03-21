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
    
alter table usersdata add delete_yn enum('Y', 'N') not null comment '삭제 여부';
alter table usersdata change modified_date modified_date  datetime comment '수정일' after created_date ;
