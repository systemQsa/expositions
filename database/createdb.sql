use expositions;


drop table if exists roles;
drop table if exists users;



create table roles(
    id_role INT PRIMARY KEY AUTO_INCREMENT,
    role VARCHAR(35)
);

INSERT INTO roles (role) VALUES ('admin');
INSERT INTO roles(role) VALUES ('user');
INSERT INTO roles(role) VALUES ('guest');

create table users(
  id_user BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL ,
  surname VARCHAR(100) NOT NULL ,
  email VARCHAR(255) UNIQUE NOT NULL ,
  password TEXT NOT NULL ,
  phone VARCHAR(30) NOT NULL ,
  balance DECIMAL(10,2) DEFAULT 0.0,
  role_id INT DEFAULT 2,
  FOREIGN KEY (role_id) REFERENCES roles(id_role)

);





#=================================
select *
from users;
drop table if exists roles;

drop table if exists users;
drop table if exists theme;
drop table if exists hall;
drop table if exists exposition;
drop table if exists exposition_users;
drop table if exists hall_exposition;


create table roles
(
    id_role INT PRIMARY KEY AUTO_INCREMENT,
    role    VARCHAR(35) UNIQUE
);

INSERT INTO roles (role)
VALUES ('admin');
INSERT INTO roles(role)
VALUES ('user');
INSERT INTO roles(role)
VALUES ('guest');

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
update users
set role_id=1
where id_user = 1;

create table theme
(
    id_theme INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(100) UNIQUE NOT NULL
);

create table hall
(
    id_hall      INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(150) UNIQUE,
    theme_id_ref INT,
    FOREIGN KEY (theme_id_ref) REFERENCES theme (id_theme)
);

create table exposition
(
    id_expo      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) UNIQUE NOT NULL,
    expo_date    DATE,
    expo_time    TIME,
    price        DECIMAL(10, 2) DEFAULT 0.0,
    description  TEXT,
    sold         INT,
    theme_id_ref INT,
    FOREIGN KEY (theme_id_ref) REFERENCES theme (id_theme)
);

create table exposition_users
(
    expo_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (expo_id) REFERENCES exposition (id_expo) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id_user) ON DELETE CASCADE

);

create table hall_exposition
(
    hall_id INT,
    expo_id BIGINT,
    FOREIGN KEY (hall_id) REFERENCES hall (id_hall),
    FOREIGN KEY (expo_id) REFERENCES exposition (id_expo)
);

#=================================== 3 version

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

create table theme
(
    id_theme BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(100) UNIQUE NOT NULL
);

create table hall
(
    id_hall      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(150) UNIQUE NOT NULL
);

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

create table expo_hall(
  id_expo BIGINT,
  id_hall BIGINT,
  FOREIGN KEY (id_expo) REFERENCES  exposition(id_expo) ON DELETE CASCADE ,
  FOREIGN KEY (id_hall) REFERENCES hall(id_hall)
);

create table exposition_users
(
    expo_id  BIGINT,
    user_id  BIGINT,
    FOREIGN KEY (expo_id) REFERENCES exposition (id_expo),
    FOREIGN KEY (user_id) REFERENCES users (id_user) ON DELETE CASCADE
);