drop table student;
drop table advisor;
drop table secretary;
drop table major_info;
drop table user_group;
drop table user_table;

create table user_table (
    user_id integer primary key,
    email varchar(255),
    password char(64),
    first_name varchar(255),
    last_name varchar(255)
);

create table student (
    student_id integer,
    major varchar(255),
    foreign key (student_id) references user_table (user_id)
);

create table major_info (
    major_code char(4) primary key,
    major_title varchar(255)
);

create table advisor (
    advisor_id integer,
    foreign key (advisor_id) references user_table (user_id)
);

create table secretary (
    secretary_id integer,
    foreign key (secretary_id) references user_table (user_id)
);

create table user_group (
    group_name varchar(255),
    user_email varchar(255)
);

/* The following puts all the major info into the database */
insert into major_info(major_code, major_title) values ('6100', 'CS');
insert into major_info(major_code, major_title) values ('6101', 'CS Applied');
insert into major_info(major_code, major_title) values ('6102', 'CS Information Science');
insert into major_info(major_code, major_title) values ('6110', 'SE');

insert into user_table(user_id, email, password, first_name, last_name) values (11111111, 'advisor@advisign.com', 'c4289629b08bc4d61411aaa6d6d4a0c3c5f8c1e848e282976e29b6bed5aeedc7',
        'Hong', 'Sung');
insert into advisor(advisor_id) values (11111111);
insert into user_group(group_name, user_email) values ('advisorgroup', 'advisor@advisign.com');