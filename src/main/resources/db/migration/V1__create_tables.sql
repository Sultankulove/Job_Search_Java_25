-- create schema if not exists jobsearch;
-- drop schema PUBLIC;
-- set schema jobSearch;

-- =======================================
-- Table: users (1)
-- =======================================
CREATE TABLE IF NOT EXISTS users (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(64),
    surname      VARCHAR(64),
    age          SMALLINT,
    email        VARCHAR(64) UNIQUE   NOT NULL,
    password     VARCHAR(255)         NOT NULL,
    phone_number VARCHAR(55) UNIQUE   NOT NULL,
    avatar       VARCHAR(255),
    account_type VARCHAR(50)          NOT NULL CHECK (account_type IN ('ADMIN', 'EMPLOYER', 'APPLICANT')),
    enabled      BOOLEAN DEFAULT TRUE NOT NULL
);



-- =======================================
-- Table: categories (2)
-- =======================================
create table if not exists categories
(
    id        bigint primary key auto_increment,
    name      varchar(128) unique not null,
    parent_id bigint);

-- =======================================
-- Table: resumes (3)
-- =======================================
create table if not exists resumes
(
    id           bigint primary key auto_increment,
    applicant_id bigint not null,
    name         varchar(64),
    category_id  bigint,
    salary       real,
    is_active    boolean   default true,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- =======================================
-- Table: contact_type (4)
-- =======================================
create table if not exists contact_type
(
    id   bigint primary key auto_increment,
    type varchar(64) unique
);


-- =======================================
-- Table: work_experience_info (5)
-- =======================================
create table if not exists work_experience_info
(
    id               bigint primary key auto_increment,
    resume_id        bigint not null,
    years            int,
    company_name     varchar(128),
    position         varchar(128),
    responsibilities varchar(128)
);


-- =======================================
-- Table: education_info (6)
-- =======================================
create table if not exists education_info
(
    id          bigint PRIMARY KEY auto_increment,
    resume_id   BIGINT NOT NULL,
    institution VARCHAR(128),
    program     VARCHAR(128),
    start_date  DATE,
    end_date    DATE,
    degree      VARCHAR(64)
);

-- =======================================
-- Table: contacts_info (7)
-- =======================================
create table if not exists contacts_info
(
    id            bigint PRIMARY KEY auto_increment,
    type_id       BIGINT NOT NULL,
    resume_id     BIGINT NOT NULL,
    contact_value VARCHAR(128)
);

-- =======================================
-- Table: vacancies (8)
-- =======================================
create table if not exists vacancies
(
    id           bigint PRIMARY KEY auto_increment,
    name         VARCHAR(64),
    description  VARCHAR(512),
    category_id  BIGINT,
    salary       DOUBLE PRECISION,
    exp_from     INTEGER,
    exp_to       INTEGER,
    is_active    BOOLEAN   DEFAULT TRUE,
    author_id    BIGINT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =======================================
-- Table: responded_applicants (9)
-- =======================================
CREATE TABLE IF NOT EXISTS responded_applicants
(
    id           bigint PRIMARY KEY auto_increment,
    resume_id    BIGINT NOT NULL,
    vacancy_id   BIGINT NOT NULL,
    confirmation BOOLEAN DEFAULT FALSE
);

-- =======================================
-- Table: messages (10)
-- =======================================
CREATE TABLE IF NOT EXISTS messages
(
    id                     bigint PRIMARY KEY auto_increment,
    responded_applicant_id BIGINT NOT NULL,
    content                VARCHAR(2048),
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




ALTER TABLE categories
    add constraint fk_categories_parent
        foreign key (parent_id) references categories(id) on DELETE cascade;

ALTER TABLE resumes
    ADD CONSTRAINT fk_resumes_applicant
        FOREIGN KEY (applicant_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE resumes
  ADD CONSTRAINT fk_resumes_categories
    FOREIGN KEY (category_id)  REFERENCES categories(id) ON DELETE SET NULL;

ALTER TABLE work_experience_info
    ADD CONSTRAINT fk_resumes_work_experience_info
        FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE;

ALTER TABLE education_info
    ADD CONSTRAINT fk_resume_education_info
        FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE;

ALTER TABLE contacts_info
    ADD CONSTRAINT fk_contacts_info_types
        FOREIGN KEY (type_id)           REFERENCES contact_type(id) ON DELETE CASCADE;

ALTER TABLE contacts_info
  ADD CONSTRAINT fk_resumes_contacts_info
    FOREIGN KEY (resume_id)         REFERENCES resumes(id)      ON DELETE CASCADE;

ALTER TABLE vacancies
    ADD CONSTRAINT fk_vacancies_category
        FOREIGN KEY (category_id)  REFERENCES categories(id) ON DELETE SET NULL;

ALTER TABLE vacancies
  ADD CONSTRAINT fk_vacancies_author
    FOREIGN KEY (author_id)     REFERENCES users(id)       ON DELETE CASCADE;

ALTER TABLE responded_applicants
    ADD CONSTRAINT fk_resumes_responded_applicants
        FOREIGN KEY (resume_id)   REFERENCES resumes(id)    ON DELETE CASCADE;

ALTER TABLE responded_applicants
  ADD CONSTRAINT fk_vacancies_responded_applicants
    FOREIGN KEY (vacancy_id)  REFERENCES vacancies(id)  ON DELETE CASCADE;

ALTER TABLE messages
    ADD CONSTRAINT fk_message_responded_applicants
        FOREIGN KEY (responded_applicant_id) REFERENCES responded_applicants(id) ON DELETE CASCADE;

-- =======================================
-- Indexes for faster FK lookups
-- =======================================

CREATE INDEX IF NOT EXISTS idx_resumes_applicant_id    ON resumes(applicant_id);
CREATE INDEX IF NOT EXISTS idx_resumes_category_id     ON resumes(category_id);

CREATE INDEX IF NOT EXISTS idx_we_info_resume_id       ON work_experience_info(resume_id);
CREATE INDEX IF NOT EXISTS idx_edu_info_resume_id      ON education_info(resume_id);

CREATE INDEX IF NOT EXISTS idx_contacts_info_type_id   ON contacts_info(type_id);
CREATE INDEX IF NOT EXISTS idx_contacts_info_resume_id ON contacts_info(resume_id);

CREATE INDEX IF NOT EXISTS idx_vacancies_category_id   ON vacancies(category_id);
CREATE INDEX IF NOT EXISTS idx_vacancies_author_id     ON vacancies(author_id);

CREATE INDEX IF NOT EXISTS idx_resp_app_resume_id      ON responded_applicants(resume_id);
CREATE INDEX IF NOT EXISTS idx_resp_app_vacancy_id     ON responded_applicants(vacancy_id);

CREATE INDEX IF NOT EXISTS idx_messages_resp_app_id    ON messages(responded_applicant_id);
CREATE INDEX IF NOT EXISTS idx_categories_parent_id    ON categories(parent_id);