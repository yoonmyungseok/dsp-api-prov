create table if not exists public.test
(
  id      serial       primary key,
  host    varchar(20)  not null,
  seq     varchar(10)  not null,
  service varchar(50)  not null,
  inst    varchar(50)  not null
  );

create table if not exists public.deploy_script
(
  id          serial        primary key,
  inst_cd     varchar(8)    not null,
  svc_cd      varchar(6)    not null,
  seq         varchar(10)   not null,
  agent_cd    varchar(6)    not null,
  updt        timestamp     default now(),
  exec_sh     varchar(2000) not null,
  result_sh   varchar(5000) not null
);

create table if not exists public.deploy_file
(
  id          serial        primary key,
  inst_cd     varchar(8)    not null,
  svc_cd      varchar(6)    not null,
  seq         varchar(10)   not null,
  agent_cd    varchar(6)    not null,
  file_name   varchar(100)  not null,
  updt        timestamp     default now(),
  version     varchar(10)   not null,
  fileSize    varchar(10)   not null
)