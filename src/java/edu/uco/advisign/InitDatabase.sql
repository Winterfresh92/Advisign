drop table student;
drop table advisor;
drop table secretary;
drop table major_info;
drop table user_group;
drop table user_table;
drop table appointment;
drop table courses;
drop table prereqs;
drop table completed_course;

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

create table appointment (
    appt_id integer,
    advisor_id integer,
    student_id integer,
    appt_date date,
    appt_time time
);

create table courses (
    course_prefix varchar(4),
    course_id integer,
    course_name varchar(255)
);

create table prereqs (
    course_prefix varchar(4),
    course_id integer,
    prereq_prefix varchar(4),
    prereq_id integer
);

create table completed_course (
    com_course_id INTEGER NOT NULL GENERATED ALWAYS 
        AS IDENTITY (START WITH 1, INCREMENT BY 1),
    course_prefix varchar(4),
    course_id integer,
    course_name varchar(255),
    student_id integer,
    semester varchar(5),
    credit_hours integer
);

insert into completed_course(course_prefix, course_id, course_name, student_id, semester, credit_hours) 
    values ('CMSC', 1513, 'Beginning Programming', 20292047, 'F2011', 3);

/* The following puts all the major info into the database */
insert into major_info(major_code, major_title) values ('6100', 'CS');
insert into major_info(major_code, major_title) values ('6101', 'CS Applied');
insert into major_info(major_code, major_title) values ('6102', 'CS Information Science');
insert into major_info(major_code, major_title) values ('6110', 'SE');

insert into user_table(user_id, email, password, first_name, last_name) values (11111111, 'kstubblefield1@uco.edu', 'c4289629b08bc4d61411aaa6d6d4a0c3c5f8c1e848e282976e29b6bed5aeedc7',
        'Hong', 'Sung');
insert into advisor(advisor_id) values (11111111);
insert into user_group(group_name, user_email) values ('advisorgroup', 'kstubblefield1@uco.edu');

insert into user_table(user_id, email, password, first_name, last_name) values (20292047, 'kstubblefield1@uco.edu', 'c4289629b08bc4d61411aaa6d6d4a0c3c5f8c1e848e282976e29b6bed5aeedc7',
        'Kevin', 'Stubblefield');
insert into student(student_id, major) values (20292047, '6100 - CS');
insert into user_group(group_name, user_email) values ('studentgroup', 'kstubblefield1@uco.edu');

insert into courses(course_prefix, course_id, course_name) values ('CMSC', 1513, 'Beginning Programming');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 1521, 'Beginning Programming Lab');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 1613, 'Programming I');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 1621, 'Programming I Lab');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 2413, 'Visual Programming');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 2123, 'Discrete Structures');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 2613, 'Programming II');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 2833, 'Computer Organization I');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 3103, 'Object Oriented Software Design and Construction');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 3833, 'Computer Organization II');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 3613, 'Data Structures and Algorithms');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4003, 'Applications Database Management');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4023, 'Programming Languages');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4173, 'Translator Design');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4153, 'Operating Systems');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4273, 'Theory of Computing');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4283, 'Software Engineering I');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4401, 'Ethics in Computing');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4513, 'Software Design and Development');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 3413, 'Advanced Visual Programming');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4063, 'Networks');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4133, 'Concepts of Artificial Intelligence');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4303, 'Mobile Apps Programming');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4323, 'Computer Security');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4373, 'Web Server Programming');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4910, 'Robotics');
insert into courses(course_prefix, course_id, course_name) values ('CMSC', 4930, 'Individual Study');
insert into courses(course_prefix, course_id, course_name) values ('MATH', 1513, 'College Algebra');
insert into courses(course_prefix, course_id, course_name) values ('MATH', 1593, 'Plane Trigonometry');
insert into courses(course_prefix, course_id, course_name) values ('MATH', 2313, 'Calculus 1');
insert into courses(course_prefix, course_id, course_name) values ('MATH', 2323, 'Calculus 2');
insert into courses(course_prefix, course_id, course_name) values ('MATH', 2333, 'Calculus 3');
insert into courses(course_prefix, course_id, course_name) values ('MATH', 3143, 'Linear Algebra');
insert into courses(course_prefix, course_id, course_name) values ('STAT', 2113, 'Statistical Methods');
insert into courses(course_prefix, course_id, course_name) values ('STAT', 4113, 'Mathematical Statistics I');
insert into courses(course_prefix, course_id, course_name) values ('PHY', 2014, 'Physics for Scientists and Engineers I and Lab');
insert into courses(course_prefix, course_id, course_name) values ('PHY', 2114, 'Physics for Scientists and Engineers II and Lab');

/* Programming I */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 1613, 'MATH', 1513);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 1613, 'CMSC', 1513);

/* Programming II */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 2613, 'CMSC', 1613);

/* Visual Programming */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 2413, 'CMSC', 1513);

/* Discrete Structures */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 2123, 'CMSC', 1613);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 2123, 'MATH', 2313);

/* Comp Org I */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 2833, 'CMSC', 1613);

/* Comp Org II */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 3833, 'CMSC', 2833);

/* AVP */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 3413, 'CMSC', 2613);

/* OOP */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 3103, 'CMSC', 2613);

/* Software Engineering I */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4283, 'CMSC', 2613);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4283, 'MATH', 2323);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4283, 'STAT', 2103);

/* Database */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4003, 'CMSC', 2613);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4003, 'STAT', 2103);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4003, 'MATH', 2323);

/* DSA */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 3613, 'CMSC', 2613);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 3613, 'CMSC', 2123);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 3613, 'MATH', 2323);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 3613, 'STAT', 2103);

/* Mobile Apps */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4303, 'CMSC', 3103);

/* Web Server Programming */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4373, 'CMSC', 3103);

/* Robotics */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4910, 'CMSC', 3613);

/* Individual Study */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4930, 'CMSC', 3613);

/* OS */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4153, 'CMSC', 3613);

/* File Structures */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4383, 'CMSC', 3613);

/* Security */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4323, 'CMSC', 3613);

/* Networks */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4063, 'CMSC', 3613);

/* Ethics */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4401, 'CMSC', 3613);

/* AI */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4133, 'CMSC', 3613);

/* Programming Languages */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4023, 'CMSC', 3613);

/* Translator Design */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4173, 'CMSC', 3613);

/* Theory of Computing */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4273, 'CMSC', 3613);

/* SE II */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('SE', 4423, 'CMSC', 4283);

/* Software Architecture */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('SE', 4433, 'CMSC', 4283);

/* Senior Project SE */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('SE', 4513, 'CMSC', 4003);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('SE', 4513, 'SE', 4423);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('SE', 4513, 'SE', 4433);

/* SDD */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4513, 'CMSC', 4283);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('CMSC', 4513, 'CMSC', 4003);

/* Calculus I */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('MATH', 2313, 'MATH', 1513);
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('MATH', 2313, 'MATH', 1593);

/* Calculus II */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('MATH', 2323, 'MATH', 2313);

/* Statistical Methods */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('MATH', 2103, 'MATH', 1513);

/* Calculus III */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('MATH', 2333, 'MATH', 2323);

/* Linear Algebra */
insert into prereqs(course_prefix, course_id, prereq_prefix, prereq_id) values ('MATH', 3143, 'MATH', 2333);