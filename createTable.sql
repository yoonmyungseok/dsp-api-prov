create table if not exists public.test
(
  id      serial
  primary key,
  host    varchar(20)  not null
  unique,
  seq     integer      not null,
  service varchar(50)  not null,
  inst    varchar(50)  not null,
  dir     varchar(200) not null
  );