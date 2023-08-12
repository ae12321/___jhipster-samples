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