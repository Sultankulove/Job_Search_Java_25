

INSERT INTO categories (name) VALUES

  ('IT'),
  ('Finance'),
  ('Marketing'),
  ('Sales'),
  ('HR');



INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type) VALUES

  ('Иван', 'Иванов', 28, 'applicant1@example.com', '$2a$10$SrlY4xU3VPheN.GniYqmVOnl9XHzAU1XTNEW.Pd7zcULo7Z/8.C5u', '+996706070891', NULL, 'APPLICANT'),
  ('Петр', 'Петров', 35, 'employer1@example.com', '$2a$10$SrlY4xU3VPheN.GniYqmVOnl9XHzAU1XTNEW.Pd7zcULo7Z/8.C5u', '+996705060066', NULL, 'EMPLOYER');



INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES

  ('Java Developer', 'Разработка бэкенд-сервисов', (SELECT id FROM categories WHERE name='IT'), 1500.0, 2, 5, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Frontend Developer', 'Разработка пользовательских интерфейсов', (SELECT id FROM categories WHERE name='IT'), 1200.0, 1, 3, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('QA Engineer', 'Тестирование приложений', (SELECT id FROM categories WHERE name='IT'), 900.0, 0, 2, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Business Analyst', 'Анализ требований', (SELECT id FROM categories WHERE name='Finance'), 1000.0, 1, 4, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Financial Manager', 'Управление финансами', (SELECT id FROM categories WHERE name='Finance'), 1600.0, 3, 6, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Marketing Specialist', 'Проведение маркетинговых кампаний', (SELECT id FROM categories WHERE name='Marketing'), 800.0, 1, 3, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('SEO Analyst', 'Оптимизация поисковой выдачи', (SELECT id FROM categories WHERE name='Marketing'), 850.0, 1, 2, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Sales Manager', 'Управление продажами', (SELECT id FROM categories WHERE name='Sales'), 700.0, 0, 2, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('HR Specialist', 'Поиск и подбор персонала', (SELECT id FROM categories WHERE name='HR'), 900.0, 1, 3, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Accountant', 'Ведение бухгалтерии', (SELECT id FROM categories WHERE name='Finance'), 1100.0, 2, 5, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Project Manager', 'Управление проектами', (SELECT id FROM categories WHERE name='IT'), 1800.0, 4, 7, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Support Engineer', 'Поддержка пользователей', (SELECT id FROM categories WHERE name='IT'), 700.0, 0, 1, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Graphic Designer', 'Создание графического контента', (SELECT id FROM categories WHERE name='Marketing'), 750.0, 1, 3, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Content Manager', 'Создание контента', (SELECT id FROM categories WHERE name='Marketing'), 650.0, 1, 2, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Sales Representative', 'Продажа продуктов', (SELECT id FROM categories WHERE name='Sales'), 600.0, 0, 1, TRUE, (SELECT id FROM users WHERE email='employer1@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
VALUES

  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Разработчик Java', (SELECT id FROM categories WHERE name='IT'), 1200.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Фронтенд разработчик', (SELECT id FROM categories WHERE name='IT'), 1000.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Инженер по тестированию', (SELECT id FROM categories WHERE name='IT'), 900.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Бизнес-аналитик', (SELECT id FROM categories WHERE name='Finance'), 1100.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Финансовый аналитик', (SELECT id FROM categories WHERE name='Finance'), 1300.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Маркетолог', (SELECT id FROM categories WHERE name='Marketing'), 800.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'SEO-специалист', (SELECT id FROM categories WHERE name='Marketing'), 750.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Менеджер по продажам', (SELECT id FROM categories WHERE name='Sales'), 700.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Рекрутер', (SELECT id FROM categories WHERE name='HR'), 850.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Бухгалтер', (SELECT id FROM categories WHERE name='Finance'), 950.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Проектный менеджер', (SELECT id FROM categories WHERE name='IT'), 1400.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Служба поддержки', (SELECT id FROM categories WHERE name='IT'), 650.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Графический дизайнер', (SELECT id FROM categories WHERE name='Marketing'), 780.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Контент-менеджер', (SELECT id FROM categories WHERE name='Marketing'), 700.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ((SELECT id FROM users WHERE email='applicant1@example.com'), 'Торговый представитель', (SELECT id FROM categories WHERE name='Sales'), 650.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

