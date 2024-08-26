create schema if not exists dev_schema;
set schema 'dev_schema';

create table if not exists users (
    id int8 not null,
    name varchar(50) not null,
    surname varchar(50) not null,
    primary key (id)
);

create table if not exists rented_books (
    id int8 not null,
    user_id int8 not null,
    book_id int8 not null,
    start_date timestamp not null,
    due_date timestamp not null,
    primary key(id)
);

create table if not exists returned_books (
    id int8 not null,
    user_id int8 not null,
    book_id int8 not null,
    start_date timestamp not null,
    due_date timestamp not null,
    returned_date timestamp not null,
    primary key (id)
);

create table if not exists books (
    id int8 not null,
    name varchar(100) not null,
    author varchar(50) not null,
    publisher varchar(100) not null,
    stock int8 not null,
    page_count int8 not null,
    primary key (id),
    constraint unique_name_publisher unique (name, publisher)
);

create sequence if not exists users_seq start 1 increment 1;
create sequence if not exists books_seq start 1 increment 1;
create sequence if not exists rented_books_seq start 1 increment 1;
create sequence if not exists returned_books_seq start 1 increment 1;
