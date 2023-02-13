create table board(
idx number not null,
title varchar2(100) not null,
content varchar2(2000) not null,
writer varchar2(30) not null,
indate date default sysdate,
count number default 0,
primary key(idx)
);
create sequence board_idx;

--시퀸스 조회 및 삭제
SELECT * FROM USER_SEQUENCES;
DROP SEQUENCE board_idx;

insert into board(idx, title, content, writer)
values(board_idx.nextval, '스프링게시판','스프링게시판','관리자');
insert into board(idx, title, content, writer)
values(board_idx.nextval, '스프링게시판','스프링게시판','배민재');
insert into board(idx, title, content, writer)
values(board_idx.nextval, '스프링게시판','스프링게시판','손님');
select * from board order by idx desc



create table member(
  memIdx int, 
  memID varchar(20) not null,  
  memPassword varchar(20) not null,
  memName varchar(20) not null,
  memAge int,
  memGender varchar(20),
  memEmail varchar(50),
  memProfile varchar(50),
  primary key(memIdx)
);

create sequence member_idx;

delete from member;

select * from member;

