create table if not exists users
(
    id int auto_increment primary key,
    name varchar(100),
    surname varchar(100),
    age int,
    email varchar(100),
    password varchar(255),
    phone_number varchar(55),
    avatar text,
    account_type varchar(55)
);

insert into users(name, surname, age, email, password, phone_number, avatar, account_type)
values ('John','Snow', 39, 'JohnSnow@Gmail.com','qwerty', '+996789123456','', 'employer'),
       ('Janna', 'Dark', 22, 'JannaDark@Gmail.com', 'qwerty', '+996123456789', '','applicant');

create table if not exists categories
(
    id int auto_increment primary key,
    name text,
    parent_id int
);

insert into categories(name, parent_id)
values ('IT', NULL),
       ('Java',1);

create table if not exists resumes
(
    id int auto_increment primary key,
    applicant_id int,
    name varchar(100),
    category_id int,
    salary real,
    is_active boolean,
    created_date timestamp,
    update_time timestamp
);





insert into resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
values ( 2, 'Looking for a job', 1, 20000, true, current_timestamp(), current_timestamp()),
       ( 2, 'Java-Developer', 2, 25000, true, current_timestamp(), current_timestamp());


create table if not exists vacancies
(
    id int auto_increment primary key,
    name varchar(100),
    description text,
    category_id int,
    salary real,
    exp_from int,
    exp_to int,
    is_active boolean,
    author_id int,
    created_date timestamp,
    update_time timestamp
);

insert into vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
values ('Старший разработчик', 'Требуется разработчик с опытом от 5 лет', 1, 30000, 5, 10, TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('Младший разработчик', 'Требуется начинающий разработчик', 2, 15000, 1, 3, TRUE, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

create table if not exists responded_applicants
(
    id int auto_increment primary key,
    resume_id int,
    vacancy_id int,
    confirmation boolean
);

insert into responded_applicants (resume_id, vacancy_id, confirmation)
values
    (1, 1, true),
    (2, 2, true);

create table if not exists education_info
(
    id int auto_increment primary key ,
    resume_id int,
    institution text,
    program text,
    start_date date,
    end_date date,
    degree text
);


insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values
    (1, 'Университет IT', 'Компьютерные науки', '2015-09-01', '2019-06-30', 'Бакалавр'),
    (2, 'Университет Java', 'Программная инженерия', '2016-09-01', '2020-06-30', 'Бакалавр');

CREATE TABLE IF NOT EXISTS work_experience_info
(
    id int auto_increment primary key ,
    resume_id int,
    years int,
    company_name text,
    position text,
    responsibilities text
);

insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values
    (1, 5, 'Tech Corp', 'Старший разработчик', 'Разработка программных решений'),
    (2, 2, 'Java Solutions', 'Младший разработчик', 'Помощь в разработке ПО');

create table if not exists contact_types
(
    id int auto_increment primary key ,
    type text
);

insert into contact_types (type)
values
    ('email'),
    ('phone');

create table if not exists contacts_info
(
    id int auto_increment primary key,
    type_id int,
    resume_id int,
    contact_value varchar(255),
    foreign key (type_id) references contact_types(id),
    foreign key (resume_id) references resumes(id)
);


insert into contacts_info (type_id, resume_id, contact_value)
values
    (1, 1, 'john.snow@techcorp.com'),
    (2, 1, '+996789123456'),
    (1, 2, 'janna.dark@java.com'),
    (2, 2, '+996123456789');

--