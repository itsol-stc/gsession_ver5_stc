create table PTL_PORTAL
(
  PTL_SID          integer           not null,
  PTL_NAME         varchar(100)      not null,
  PTL_DESCRIPTION  varchar(1000),
  PTL_OPEN         smallint          not null,
  PTL_ACCESS       smallint          not null,
  PTL_AUID         integer           not null,
  PTL_ADATE        timestamp        not null,
  PTL_EUID         integer           not null,
  PTL_EDATE        timestamp        not null,
  primary key (PTL_SID)
);