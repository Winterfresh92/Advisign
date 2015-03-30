drop table student;
drop table advisor;
drop table secretary;
drop table student_group;
drop table advisor_group;
drop table secretary_group;
drop table major_info;

create table student (
    student_id integer primary key,
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    email varchar(255),
    major varchar(255)
);

create table major_info (
    major_code varchar(4) primary key,
    major_title varchar(255)
);

create table advisor (
    advisor_id integer primary key,
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    email varchar(255)
);

create table secretary(
    secretary_id integer primary key,
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    email varchar(255)
);

/*create table student_group();

create table advisor_group();

create table secretary_group();*/


/* The following puts all the major info into the database */
insert into major_info(major_code, major_title) values ('6100', 'CS');
insert into major_info(major_code, major_title) values ('6101', 'CS Applied');
insert into major_info(major_code, major_title) values ('6102', 'CS Information Science');
insert into major_info(major_code, major_title) values ('6110', 'SE');
insert into advisor(password, email) values ('advisor', 'advisor@uco.edu');