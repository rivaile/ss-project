drop table if exists city;
drop table if exists hotel;

create table city
(
    id      int primary key auto_increment,
    name    varchar(50),
    state   varchar(20),
    country varchar(50)
);
create table hotel
(
    city    int,
    name    varchar(50),
    address varchar(50),
    zip     varchar(50)
);


