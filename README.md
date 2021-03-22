# Тестовое задание Конвертер валют
### Скрипт создания БД
```<>
create table conversion
(
    id             int4 not null,
    date           date,
    value_from     float8,
    value_to       float8,
    valute_from_id int4,
    valute_to_id   int4,
    primary key (id)
);

create table usr
(
    id       int4 not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
);
create table valute
(
    id          int4 not null,
    code        varchar(255),
    last_update date,
    name        varchar(255),
    nominal     int4,
    value       float8,
    write_date  date,
    primary key (id)
);
alter table if exists conversion
    add constraint FK_valute_from_id foreign key (valute_from_id) references valute;
alter table if exists conversion
    add constraint FK_valute_to_id foreign key (valute_to_id) references valute;
  ```
 Приложен файл application.properties, который ожидает ссылку, логин и пароль к БД. 
