-- заполнение таблиц

delete from users where email = 'qwe@qwe.qwe';

insert into users(name, surname, age, email, password, phone_number, avatar, account_type, enabled) values
                      ('Elturan', 'Sultankulov', 22, 'qwe@qwe.qwe', 'qwerty', '+996771653653', '', 'EMPLOYER', true);