create table user(

    id bigint not null auto_increment,
    username varchar(100) not null unique,
    password varchar(255) not null,
    primary key(id)

);