INSERT INTO tb_user(email, name, username, password) VALUES 
('liano@mail.com', 'Liam Arguedas', 'liano', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm');

INSERT INTO tb_user(email, name, username, password) VALUES 
('liano.user@mail.com', 'Liam Arguedas', 'liano_user', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm');

INSERT INTO tb_user(email, name, username, password) VALUES
('jane.doe@mail.com', 'Jane Doe', 'janedoe', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('john.smith@mail.com', 'John Smith', 'johnsmith', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('emma.watson@mail.com', 'Emma Watson', 'emmawatson', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('bruce.wayne@mail.com', 'Bruce Wayne', 'batman', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('clark.kent@mail.com', 'Clark Kent', 'superman', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('tony.stark@mail.com', 'Tony Stark', 'ironman', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('peter.parker@mail.com', 'Peter Parker', 'spiderman', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('natasha.romanoff@mail.com', 'Natasha Romanoff', 'blackwidow', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('steve.rogers@mail.com', 'Steve Rogers', 'captainamerica', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm'),
('diana.prince@mail.com', 'Diana Prince', 'wonderwoman', '$2a$12$WHy.QQgmmQWvoOWPMURtL./kT65i6.mIHBAIEv/W.dgJW9Sivknbm');

INSERT INTO tb_role (name) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role(name) VALUES ('ROLE_USER');

INSERT INTO tb_user_role (user_id, role_id) VALUES(1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES(2, 2);

INSERT INTO tb_user_role (user_id, role_id) VALUES
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(8, 2),
(9, 2),
(10, 2),
(11, 2),
(12, 2);
