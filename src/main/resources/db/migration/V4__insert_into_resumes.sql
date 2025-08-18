-- INSERTS for resumes table (исправленная структура)

INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'DevOps Engineer',
           1165,
           (SELECT id FROM users WHERE email = 'app1@gmail.com'),
           (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
           '2025-06-17',
           '2025-08-14',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Репетитор по алгебре',
           2514,
           (SELECT id FROM users WHERE email = 'app1@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
           '2025-04-05',
           '2025-07-07',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Хирург общей практики',
           488,
           (SELECT id FROM users WHERE email = 'app1@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Хирург' LIMIT 1),
           '2025-04-09',
           '2025-06-30',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Python Backend Engineer',
           2265,
           (SELECT id FROM users WHERE email = 'app1@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
           '2024-08-02',
           '2024-09-12',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Архитектор жилых зданий',
           977,
           (SELECT id FROM users WHERE email = 'app1@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Архитектор' LIMIT 1),
           '2025-06-14',
           '2025-06-24',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Водитель категории B',
           2005,
           (SELECT id FROM users WHERE email = 'app1@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Водитель' LIMIT 1),
           '2024-07-19',
           '2024-09-01',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Data Scientist',
           2396,
           (SELECT id FROM users WHERE email = 'app1@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
           '2025-04-02',
           '2025-05-17',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Семейный врач',
           2363,
           (SELECT id FROM users WHERE email = 'app1@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
           '2025-05-23',
           '2025-07-13',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бухгалтер по зарплате',
           1285,
           (SELECT id FROM users WHERE email = 'app2@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2024-06-07',
           '2024-09-13',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Spring Boot Developer',
           1842,
           (SELECT id FROM users WHERE email = 'app2@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
           '2025-02-09',
           '2025-04-18',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Адвокат по уголовным делам',
           1647,
           (SELECT id FROM users WHERE email = 'app2@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Адвокат' LIMIT 1),
           '2025-04-05',
           '2025-07-06',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'UX Researcher',
           2555,
           (SELECT id FROM users WHERE email = 'app2@gmail.com'),
           (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
           '2024-11-24',
           '2025-01-01',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Специалист по корпоративному праву',
           2931,
           (SELECT id FROM users WHERE email = 'app2@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2024-08-24',
           '2024-10-17',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектный инженер',
           2263,
           (SELECT id FROM users WHERE email = 'app2@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Инженер ПТО' LIMIT 1),
           '2025-03-05',
           '2025-06-03',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Доцент кафедры физики',
           2738,
           (SELECT id FROM users WHERE email = 'app2@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Преподаватель ВУЗа' LIMIT 1),
           '2024-12-22',
           '2025-02-22',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Водитель категории B',
           2401,
           (SELECT id FROM users WHERE email = 'app2@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Водитель' LIMIT 1),
           '2025-02-18',
           '2025-04-06',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Python Backend Engineer',
           1166,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
           '2025-04-04',
           '2025-06-20',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Логист по доставке',
           1906,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Логист' LIMIT 1),
           '2024-10-07',
           '2024-11-17',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Водитель категории B',
           2784,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Водитель' LIMIT 1),
           '2024-08-01',
           '2024-10-05',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектировщик интерьеров',
           2514,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Архитектор' LIMIT 1),
           '2025-04-30',
           '2025-07-10',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бухгалтер по зарплате',
           2368,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2024-12-31',
           '2025-04-04',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектный инженер',
           1132,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Инженер ПТО' LIMIT 1),
           '2025-04-23',
           '2025-05-28',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'SMM-специалист',
           1755,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'SMM-менеджер' LIMIT 1),
           '2024-10-17',
           '2024-12-04',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Ортопед',
           926,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Хирург' LIMIT 1),
           '2025-01-14',
           '2025-03-04',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Мастер по окрашиванию',
           2642,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2025-02-11',
           '2025-04-06',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Адвокат по уголовным делам',
           1704,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Адвокат' LIMIT 1),
           '2025-06-15',
           '2025-07-23',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'UX Researcher',
           1849,
           (SELECT id FROM users WHERE email = 'app3@gmail.com'),
           (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
           '2024-08-05',
           '2024-11-12',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектный инженер',
           2597,
           (SELECT id FROM users WHERE email = 'app4@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Инженер ПТО' LIMIT 1),
           '2025-02-04',
           '2025-04-02',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'AI Researcher',
           763,
           (SELECT id FROM users WHERE email = 'app4@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
           '2025-05-28',
           '2025-08-17',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'QA Automation Engineer',
           490,
           (SELECT id FROM users WHERE email = 'app4@gmail.com'),
           (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
           '2024-09-26',
           '2024-12-04',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Экономист-аналитик',
           535,
           (SELECT id FROM users WHERE email = 'app4@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Финансовый аналитик' LIMIT 1),
           '2024-10-24',
           '2025-01-21',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектировщик интерьеров',
           2190,
           (SELECT id FROM users WHERE email = 'app4@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Архитектор' LIMIT 1),
           '2025-03-23',
           '2025-06-27',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Логист по доставке',
           465,
           (SELECT id FROM users WHERE email = 'app4@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Логист' LIMIT 1),
           '2025-01-31',
           '2025-03-26',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Специалист по корпоративному праву',
           2059,
           (SELECT id FROM users WHERE email = 'app4@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2025-02-25',
           '2025-03-16',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'UI Designer',
           438,
           (SELECT id FROM users WHERE email = 'app4@gmail.com'),
           (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
           '2024-10-30',
           '2025-02-06',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Ортопед',
           623,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Хирург' LIMIT 1),
           '2024-11-15',
           '2025-01-08',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'UX Researcher',
           2670,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
           '2024-11-07',
           '2024-11-27',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'ML Engineer',
           2365,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
           '2024-11-30',
           '2025-01-31',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'QA Automation Engineer',
           2512,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
           '2024-08-22',
           '2024-11-30',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Водитель категории B',
           744,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Водитель' LIMIT 1),
           '2025-05-21',
           '2025-06-28',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Адвокат по гражданским делам',
           951,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Адвокат' LIMIT 1),
           '2025-04-21',
           '2025-06-04',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Маркетолог-аналитик',
           1408,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
           '2025-03-30',
           '2025-05-12',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Менеджер по снабжению',
           1448,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Логист' LIMIT 1),
           '2024-12-28',
           '2025-01-10',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Инженер ПТО по строительству',
           485,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Инженер ПТО' LIMIT 1),
           '2025-05-14',
           '2025-08-07',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Специалист по корпоративному праву',
           2307,
           (SELECT id FROM users WHERE email = 'app5@gmail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2025-05-12',
           '2025-07-04',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Врач общей практики',
           760,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
           '2024-08-18',
           '2024-11-23',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Адвокат по уголовным делам',
           1086,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Адвокат' LIMIT 1),
           '2025-03-13',
           '2025-05-31',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Водитель категории B',
           1760,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Водитель' LIMIT 1),
           '2025-04-24',
           '2025-06-11',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Специалист по корпоративному праву',
           1954,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2025-02-15',
           '2025-04-30',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Менеджер по снабжению',
           2341,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Логист' LIMIT 1),
           '2024-08-11',
           '2024-10-04',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Мастер по окрашиванию',
           2913,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2025-01-12',
           '2025-04-06',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Ортопед',
           2337,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Хирург' LIMIT 1),
           '2025-03-26',
           '2025-04-06',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бренд-менеджер',
           1085,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
           '2024-08-11',
           '2024-10-30',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Преподаватель информатики',
           1526,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Преподаватель ВУЗа' LIMIT 1),
           '2024-07-23',
           '2024-10-14',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'QA Automation Engineer',
           1251,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
           '2024-09-18',
           '2024-10-16',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Архитектор жилых зданий',
           2027,
           (SELECT id FROM users WHERE email = 'ольга6@mail.com'),
           (SELECT id FROM categories WHERE name = 'Архитектор' LIMIT 1),
           '2024-12-26',
           '2025-03-13',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Менеджер по снабжению',
           612,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Логист' LIMIT 1),
           '2024-07-07',
           '2024-09-29',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Test Analyst',
           2665,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
           '2024-12-01',
           '2025-01-29',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Специалист по корпоративному праву',
           826,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2024-10-08',
           '2024-12-29',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектировщик интерьеров',
           1162,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Архитектор' LIMIT 1),
           '2025-03-23',
           '2025-04-23',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Преподаватель информатики',
           691,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Преподаватель ВУЗа' LIMIT 1),
           '2024-12-30',
           '2025-03-03',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Хирург общей практики',
           2474,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Хирург' LIMIT 1),
           '2024-08-12',
           '2024-09-15',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Парикмахер-стилист',
           2123,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2024-07-29',
           '2024-10-21',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Angular Developer',
           1068,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
           '2025-06-12',
           '2025-07-31',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Главный бухгалтер',
           2199,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2024-11-28',
           '2025-02-23',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Python Backend Engineer',
           2608,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
           '2025-06-17',
           '2025-07-29',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Адвокат по уголовным делам',
           1063,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Адвокат' LIMIT 1),
           '2024-07-20',
           '2024-08-28',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Маркетолог-аналитик',
           2626,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
           '2024-09-06',
           '2024-11-13',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'SMM-специалист',
           2014,
           (SELECT id FROM users WHERE email = 'нурсултан7@mail.com'),
           (SELECT id FROM categories WHERE name = 'SMM-менеджер' LIMIT 1),
           '2024-05-27',
           '2024-09-03',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'QA Automation Engineer',
           1644,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
           '2024-08-03',
           '2024-08-29',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Адвокат по уголовным делам',
           956,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Адвокат' LIMIT 1),
           '2024-07-16',
           '2024-09-22',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектировщик интерьеров',
           2637,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Архитектор' LIMIT 1),
           '2025-02-19',
           '2025-04-23',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бухгалтер по зарплате',
           2421,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2024-07-10',
           '2024-09-18',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Учитель математики',
           1582,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
           '2024-08-12',
           '2024-11-03',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Слесарь-сборщик',
           1604,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Слесарь' LIMIT 1),
           '2025-05-14',
           '2025-06-11',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Юрист-консультант',
           590,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2024-09-19',
           '2024-10-25',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'ML Engineer',
           1013,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
           '2024-11-07',
           '2025-01-01',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'SMM-специалист',
           1621,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'SMM-менеджер' LIMIT 1),
           '2024-09-16',
           '2024-09-26',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектный инженер',
           2195,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Инженер ПТО' LIMIT 1),
           '2024-12-31',
           '2025-02-28',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Маркетолог-аналитик',
           2202,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
           '2024-08-06',
           '2024-10-25',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Семейный врач',
           865,
           (SELECT id FROM users WHERE email = 'ирина8@mail.com'),
           (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
           '2025-02-20',
           '2025-03-08',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бренд-менеджер',
           2572,
           (SELECT id FROM users WHERE email = 'алексей9@mail.com'),
           (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
           '2025-05-06',
           '2025-06-16',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Доцент кафедры физики',
           2580,
           (SELECT id FROM users WHERE email = 'алексей9@mail.com'),
           (SELECT id FROM categories WHERE name = 'Преподаватель ВУЗа' LIMIT 1),
           '2024-11-16',
           '2025-01-29',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Java Backend Developer',
           1506,
           (SELECT id FROM users WHERE email = 'алексей9@mail.com'),
           (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
           '2024-09-17',
           '2024-10-30',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'React Developer',
           548,
           (SELECT id FROM users WHERE email = 'алексей9@mail.com'),
           (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
           '2025-04-28',
           '2025-06-29',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Семейный врач',
           2790,
           (SELECT id FROM users WHERE email = 'алексей9@mail.com'),
           (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
           '2025-04-30',
           '2025-05-27',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Мастер по окрашиванию',
           1727,
           (SELECT id FROM users WHERE email = 'алексей9@mail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2024-08-07',
           '2024-09-06',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Водитель-экспедитор',
           2629,
           (SELECT id FROM users WHERE email = 'алексей9@mail.com'),
           (SELECT id FROM categories WHERE name = 'Водитель' LIMIT 1),
           '2024-11-01',
           '2024-12-12',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектный инженер',
           1604,
           (SELECT id FROM users WHERE email = 'алексей9@mail.com'),
           (SELECT id FROM categories WHERE name = 'Инженер ПТО' LIMIT 1),
           '2024-06-22',
           '2024-09-04',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Java Backend Developer',
           1020,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
           '2025-05-06',
           '2025-06-07',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'AI Researcher',
           532,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
           '2025-05-04',
           '2025-07-03',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бренд-менеджер',
           2704,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
           '2025-02-23',
           '2025-03-23',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Контент-менеджер',
           2240,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'SMM-менеджер' LIMIT 1),
           '2025-06-04',
           '2025-08-14',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'React Developer',
           2794,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
           '2024-07-21',
           '2024-09-11',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Преподаватель информатики',
           2879,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Преподаватель ВУЗа' LIMIT 1),
           '2024-06-20',
           '2024-09-16',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Юрист-консультант',
           466,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2024-12-29',
           '2025-03-04',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Менеджер по снабжению',
           2999,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Логист' LIMIT 1),
           '2024-06-04',
           '2024-08-30',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Семейный врач',
           914,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
           '2025-03-29',
           '2025-05-19',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Экономист-аналитик',
           2927,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Финансовый аналитик' LIMIT 1),
           '2024-12-06',
           '2025-01-27',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бухгалтер по зарплате',
           2547,
           (SELECT id FROM users WHERE email = 'жанара10@mail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2025-03-21',
           '2025-06-25',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бренд-менеджер',
           693,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
           '2025-03-02',
           '2025-04-13',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Слесарь-сборщик',
           1478,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Слесарь' LIMIT 1),
           '2024-09-06',
           '2024-11-25',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Финансовый аналитик',
           1998,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Финансовый аналитик' LIMIT 1),
           '2024-12-25',
           '2025-01-21',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Репетитор по алгебре',
           2376,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
           '2025-05-25',
           '2025-06-18',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектный инженер',
           907,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Инженер ПТО' LIMIT 1),
           '2025-06-28',
           '2025-08-17',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Site Reliability Engineer',
           2292,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
           '2025-03-11',
           '2025-06-19',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бухгалтер по зарплате',
           1314,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2025-02-25',
           '2025-04-29',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Преподаватель информатики',
           1601,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Преподаватель ВУЗа' LIMIT 1),
           '2024-11-09',
           '2024-12-10',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Ортопед',
           2799,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Хирург' LIMIT 1),
           '2024-09-09',
           '2024-10-16',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Архитектор жилых зданий',
           2918,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Архитектор' LIMIT 1),
           '2024-11-29',
           '2025-02-17',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Парикмахер-стилист',
           1684,
           (SELECT id FROM users WHERE email = 'павел11@mail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2024-11-25',
           '2025-02-01',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Проектировщик интерьеров',
           525,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Архитектор' LIMIT 1),
           '2024-08-21',
           '2024-10-23',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Юрист-консультант',
           2631,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2024-11-25',
           '2024-12-08',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Менеджер по снабжению',
           1687,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Логист' LIMIT 1),
           '2024-06-28',
           '2024-10-06',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Репетитор по алгебре',
           1289,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
           '2025-04-18',
           '2025-06-18',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Адвокат по гражданским делам',
           1890,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Адвокат' LIMIT 1),
           '2024-10-26',
           '2025-01-09',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'SMM-специалист',
           1455,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'SMM-менеджер' LIMIT 1),
           '2025-05-31',
           '2025-08-02',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бренд-менеджер',
           2703,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
           '2025-01-31',
           '2025-03-21',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Product Designer',
           1047,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
           '2025-02-07',
           '2025-05-14',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Слесарь-механик',
           1931,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Слесарь' LIMIT 1),
           '2025-04-11',
           '2025-05-29',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Парикмахер-стилист',
           2875,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2025-06-19',
           '2025-07-04',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Врач общей практики',
           1280,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
           '2024-11-12',
           '2024-12-05',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Экономист-аналитик',
           1767,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Финансовый аналитик' LIMIT 1),
           '2024-10-03',
           '2024-11-10',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Главный бухгалтер',
           666,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2024-08-03',
           '2024-10-13',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Преподаватель информатики',
           1359,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Преподаватель ВУЗа' LIMIT 1),
           '2025-01-09',
           '2025-02-17',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Spring Boot Developer',
           2075,
           (SELECT id FROM users WHERE email = 'асем12@mail.com'),
           (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
           '2025-01-07',
           '2025-02-02',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бухгалтер по зарплате',
           1771,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2025-02-21',
           '2025-05-09',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Инженер ПТО по строительству',
           1982,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'Инженер ПТО' LIMIT 1),
           '2024-12-03',
           '2025-03-11',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'React Developer',
           772,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
           '2025-01-01',
           '2025-02-20',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Test Analyst',
           1387,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
           '2024-09-08',
           '2024-09-27',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Юрист-консультант',
           2586,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2024-07-16',
           '2024-10-14',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Врач общей практики',
           1603,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
           '2025-04-08',
           '2025-06-14',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Репетитор по алгебре',
           2053,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
           '2024-10-23',
           '2024-12-01',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Парикмахер-стилист',
           611,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2025-04-24',
           '2025-05-20',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Data Scientist',
           554,
           (SELECT id FROM users WHERE email = 'кирилл13@mail.com'),
           (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
           '2024-06-23',
           '2024-09-07',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Экономист-аналитик',
           645,
           (SELECT id FROM users WHERE email = 'гульмира14@mail.com'),
           (SELECT id FROM categories WHERE name = 'Финансовый аналитик' LIMIT 1),
           '2024-08-04',
           '2024-09-29',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Специалист по корпоративному праву',
           2623,
           (SELECT id FROM users WHERE email = 'гульмира14@mail.com'),
           (SELECT id FROM categories WHERE name = 'Юрист' LIMIT 1),
           '2024-11-30',
           '2024-12-29',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Слесарь-сборщик',
           1352,
           (SELECT id FROM users WHERE email = 'гульмира14@mail.com'),
           (SELECT id FROM categories WHERE name = 'Слесарь' LIMIT 1),
           '2024-05-22',
           '2024-08-27',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Бухгалтер по зарплате',
           1829,
           (SELECT id FROM users WHERE email = 'гульмира14@mail.com'),
           (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
           '2025-04-07',
           '2025-05-06',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Мастер по окрашиванию',
           1175,
           (SELECT id FROM users WHERE email = 'гульмира14@mail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2025-02-06',
           '2025-03-01',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'AI Researcher',
           2010,
           (SELECT id FROM users WHERE email = 'гульмира14@mail.com'),
           (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
           '2025-04-04',
           '2025-04-29',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Хирург общей практики',
           2773,
           (SELECT id FROM users WHERE email = 'гульмира14@mail.com'),
           (SELECT id FROM categories WHERE name = 'Хирург' LIMIT 1),
           '2025-04-16',
           '2025-04-29',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'UX Researcher',
           931,
           (SELECT id FROM users WHERE email = 'гульмира14@mail.com'),
           (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
           '2025-01-10',
           '2025-02-26',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'ML Engineer',
           1118,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
           '2024-10-19',
           '2025-01-04',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Product Designer',
           1381,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
           '2025-01-16',
           '2025-03-17',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Доцент кафедры физики',
           2237,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Преподаватель ВУЗа' LIMIT 1),
           '2024-10-31',
           '2024-12-20',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Адвокат по гражданским делам',
           1449,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Адвокат' LIMIT 1),
           '2024-09-22',
           '2024-11-21',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'QA Manual Tester',
           470,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
           '2024-09-23',
           '2024-12-27',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Vue.js Developer',
           1415,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
           '2024-08-14',
           '2024-09-19',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Учитель математики',
           2764,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
           '2024-08-13',
           '2024-10-11',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Ортопед',
           1753,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Хирург' LIMIT 1),
           '2025-04-19',
           '2025-07-06',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Финансовый аналитик',
           822,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Финансовый аналитик' LIMIT 1),
           '2025-03-26',
           '2025-05-16',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Парикмахер-стилист',
           1425,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Парикмахер' LIMIT 1),
           '2024-09-21',
           '2024-11-26',
           false
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Site Reliability Engineer',
           1085,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
           '2025-03-04',
           '2025-04-07',
           true
       );
INSERT INTO resumes (name, salary, applicant_id, category_id, created_date, update_time, is_active)
VALUES (
           'Логист по доставке',
           2497,
           (SELECT id FROM users WHERE email = 'максим15@mail.com'),
           (SELECT id FROM categories WHERE name = 'Логист' LIMIT 1),
           '2024-11-12',
           '2024-12-27',
           false
       );
