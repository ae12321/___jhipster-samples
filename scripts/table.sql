drop table if exists company;
create table company (
    company_id serial not null,
    name varchar(100) not null,
    published_at date not null default current_date,
    primary key (company_id)
);
drop table if exists employee;
create table employee (
    id serial not null,
    name varchar(100) not null,
    age integer not null ,
    company_id bigint,
    primary key (id),
    foreign key (company_id) references company(company_id)
);
-- ----------------------------------------------------------------------

drop table if exists author cascade;
create table author (
    author_id serial not null,
    author_name varchar(100) not null,
    author_detail_id bigint not null,
    primary key (author_id),
    foreign key (author_detail_id) references author_detail(author_detail_id)
);
drop table if exists author_detail cascade;
create table author_detail (
    author_detail_id serial not null,
    author_age integer not null,
    primary key (author_detail_id)
);
