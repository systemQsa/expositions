drop table if exists exposition_users;
drop table if exists expo_hall;
drop table if exists exposition;
drop table if exists status;
drop table if exists hall;
drop table if exists theme;
drop table if exists users;
drop table if exists roles;


create table roles
(
    id_role INT PRIMARY KEY AUTO_INCREMENT,
    role    VARCHAR(35)
);

INSERT INTO roles (role) VALUES ('admin');
INSERT INTO roles(role) VALUES ('user');
INSERT INTO roles(role) VALUES ('guest');

create table users
(
    id_user  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(100)        NOT NULL,
    surname  VARCHAR(100)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password TEXT                NOT NULL,
    phone    VARCHAR(30)         NOT NULL,
    balance  DECIMAL(10, 2) DEFAULT 0.0,
    role_id  INT            DEFAULT 2,
    FOREIGN KEY (role_id) REFERENCES roles (id_role)

);
insert into users(name, surname, email, password, phone,balance,role_id) VALUES ('Peter','Parker','peter@gmail.com','1234','+32403567667',500.0,2);
insert into users(name, surname, email, password, phone,balance,role_id) VALUES ('Jessica','Thompson','jess@gmail.com','12324','+1234567888',500.0,2);
insert into users(name, surname, email, password, phone,balance,role_id) VALUES ('Artur','Cold','art@gmail.com','12324','+56234567888',500.0,2);
update users set role_id=1 where id_user = 1;

create table theme
(
    id_theme BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(100) UNIQUE NOT NULL
);
insert into theme(name) values ('Space');
insert into theme(name) values ('Job');
insert into theme(name) values ('Art');
insert into theme(name) values ('Music');

create table hall
(
    id_hall      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(150) UNIQUE NOT NULL
);
insert into hall(name) values ('A1');
insert into hall(name) values ('B2');
insert into hall(name) values ('C3');

create table status(
 status_id INT PRIMARY KEY AUTO_INCREMENT,
 status_name VARCHAR(40)
);
insert into status(status_name) values ('active');
insert into status(status_name) values ('canceled');


create table exposition
(
    id_expo      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) UNIQUE NOT NULL,
    expo_date    DATE,
    expo_time    TIME,
    price        DECIMAL(10, 2) DEFAULT 0.0,
    sold         BIGINT DEFAULT 0,
    id_theme_ref BIGINT,
    tickets      BIGINT,
    status_id    INT DEFAULT 1,
    FOREIGN KEY (id_theme_ref) REFERENCES theme(id_theme),
    FOREIGN KEY (status_id) REFERENCES status(status_id)

);


INSERT INTO exposition(name, expo_date, expo_time, price, sold, id_theme_ref, tickets)
VALUES ('weather','2022-05-13','18:44:23',245.50,10,2,25);

INSERT INTO exposition(name, expo_date, expo_time, price, sold, id_theme_ref, tickets)
VALUES ('space','2022-05-13','19:24:23',300.50,15,1,45);

INSERT INTO exposition(name, expo_date, expo_time, price, sold, id_theme_ref, tickets)
VALUES ('books','2022-05-13','19:24:23',300.50,15,3,55);

create table expo_hall(
   id_expo BIGINT,
   id_hall BIGINT,
   FOREIGN KEY (id_expo) REFERENCES  exposition(id_expo) ON DELETE CASCADE ,
   FOREIGN KEY (id_hall) REFERENCES hall(id_hall)
);

insert into expo_hall(id_expo, id_hall) VALUES (1,2);
insert into expo_hall(id_expo, id_hall) VALUES (2,2);
insert into expo_hall(id_expo, id_hall) VALUES (3,3);

create table exposition_users
(
    expo_id             BIGINT,
    user_id             BIGINT,
    FOREIGN KEY (expo_id) REFERENCES exposition (id_expo),
    FOREIGN KEY (user_id) REFERENCES users (id_user) ON DELETE CASCADE
);
