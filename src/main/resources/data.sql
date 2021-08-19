INSERT INTO users(`username`, `email`, `password`, `role`, `karma`)
VALUES ('admin', '', '$2a$10$3H4XFVPlWhG6Fh5ICAFULuJvdpLHrnvAcpP/TJGJ.CZrtPhkD/s36', 'ADMIN', 2147483647),
       ('test', '', '$2a$10$fSrYS1359cEvLfTlc5LIDe73K6kzRopeMXDHgeDWm.ToDhIrBXQfe', 'USER', 1);

INSERT INTO tasks(`name`, `bounty`, `user_id`)
VALUES ('2 стакана воды', 1, 2),
       ('Зарядка', 2, 2),
       ('Здоровый завтрак', 2, 2),
       ('1 час обучение', 3, 2),
       ('1 час работы', 1, 2);