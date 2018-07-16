TRUNCATE table meals;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id) values
  (TO_TIMESTAMP('30.05.2015 13:00:00', 'DD/MM/YYYY HH24:MI:SS'), 'Обед', 1000, 100000),
  (TO_TIMESTAMP('30.05.2015 10:00:00', 'DD/MM/YYYY HH24:MI:SS'), 'Завтрак', 500, 100000)



