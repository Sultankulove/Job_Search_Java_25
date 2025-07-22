insert into contact_types (type)
values ('Email'),
       ('Phone'),
       ('Telegram'),
       ('Facebook'),
       ('Linkedin');


insert into users (name, surname, age, email, password, phone_number, avatar, account_type)
values ('John', 'Doe', 28, 'john.doe@example.com', 'password123', '1234567890', 'avatar1.png', 'applicant');


insert into users (name, surname, age, email, password, phone_number, avatar, account_type)
values ('Alice', 'Smith', 35, 'alice.smith@example.com', 'password456', '0987654321', 'avatar2.png', 'employer');


insert into categories (name, parent_id)
values ('IT', null),
       ('Marketing', null);

insert into vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date,
                       update_time)
values
    ('Software Developer', 'Develop and maintain software applications.', 1, 5000, 2, 5, true, 2, current_timestamp,
        current_timestamp),
    ('Marketing Specialist', 'Create and execute marketing strategies.', 2, 3500, 1, 3, true, 2, current_timestamp,
        current_timestamp);


insert into resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
values (1, 'John Doe Resume 1', 1, 4000, true, current_timestamp, current_timestamp),
       ( 1, 'John Doe Resume 2', 2, 3000, true, current_timestamp, current_timestamp);


insert into responded_applicants (resume_id, vacancy_id, confirmation)
values (1, 1, true),
       (2, 2, true);

insert into messages (responded_applicants, content, timestamp)
values (1, 'I am very interested in this position and would love to discuss it further.', current_timestamp),
       (2, 'I believe my skills are a great fit for this role, and I am eager to join your team.', current_timestamp);


insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values (1, 'University of Example', 'Computer Science', '2015-09-01', '2019-06-01', 'Bachelor'),
       (2, 'Marketing University', 'Marketing', '2016-09-01', '2020-06-01', 'Bachelor');


insert into contacts_info (type_id, resume_id, contact_value)
values (1, 1, 'john.doe@email.com'),
       (1, 2, 'john.doe2@email.com');


insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values (1, 3, 'Tech Corp', 'Junior Developer', 'Developing web applications.'),
       (2, 2, 'Market Inc.', 'Marketing Assistant', 'Assisting in marketing campaigns.');
