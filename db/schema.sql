create table body (
    id serial primary key,
    name varchar(255) not null unique
);

create table mark (
    id serial primary key,
    name varchar(255) not null unique
);

insert into body (name) values ('хечбек'),( 'седан'), ('купэ'), ('универсал' );
insert into mark (name) values ('mercedes'),( 'BMW'), ('LADA'), ('TOYOTA');

create table car (
    id serial primary key,
    mark_id int references mark (id) not null,
    model varchar(255) not null,
    body_id int references body (id) not null,
    price double precision not null
);

create table photo (
    id serial primary key,
    path varchar(255) not null
);

create table users (
    id serial primary key,
    name varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null
);

create table ad (
    id serial primary key,
    description text not null,
    car_id  int references car (id) not null,
    photo_id int references photo(id) not null,
    user_id int references users (id) not null,
    created timestamp not null,
    sold boolean default false not null
);