-- Создание таблиц

create table if not exists users
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name varchar(64),
    surname varchar(64),
    age tinyint,
    email varchar(64) not null unique,
    password varchar(255) not null,
    phone_number varchar(55) not null unique,
    avatar varchar(255),
    account_type varchar(50) check (account_type IN ('ADMIN', 'EMPLOYER', 'APPLICANT')),
    enabled boolean default true
);

create table if not exists categories
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name varchar(128) unique ,
    parent_id BIGINT references categories(id) on DELETE cascade
);


create table if not exists resumes
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    applicant_id BIGINT not null references users(id) on delete cascade ,
    name varchar(64),
    category_id BIGINT references categories(id) on delete set null,
    salary real,
    is_active boolean default true,
    created_date timestamp default current_timestamp(),
    update_time timestamp default current_timestamp()
);

create table if not exists contact_type
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    type varchar(64) unique
);

create table if not exists work_experience_info
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    resume_id bigint not null references resumes(id) on delete cascade,
    years int,
    company_name varchar(128),
    position varchar(128),
    responsibilities varchar(128)
);

create table if not exists education_info
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    resume_id BIGINT not null references resumes(id) on delete cascade,
    institution varchar(128),
    program varchar(128),
    start_date date,
    end_date date,
    degree varchar(64)
);




create table if not exists contacts_info
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    type_id BIGINT not null references contact_type(id) on delete cascade ,
    resume_id bigint not null references resumes(id) on delete cascade,
    contact_value varchar(128)
);

create table if not exists vacancies
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name varchar(64),
    description varchar(512),
    category_id BIGINT references categories(id) on delete set null,
    salary float,
    exp_from integer,
    exp_to integer,
    is_active boolean default true,
    author_id BIGINT not null references users(id) on DELETE cascade ,
    created_date timestamp default current_timestamp(),
    update_time timestamp default current_timestamp()
);

create table if not exists responded_applicants
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    resume_id BIGINT not null references resumes(id) on delete cascade ,
    vacancy_id BIGINT not null references vacancies(id) on delete cascade ,
    confirmation boolean default false
);

create table if not exists messages
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    responded_applicants BIGINT not null references responded_applicants(id) on delete cascade ,
    content varchar(2048),
    timestamp timestamp default current_timestamp()
);
