create table SCH_SPACCESS
(
  SSA_SID    integer        not null,
  SSA_NAME   varchar(50)    not null,
  SSA_BIKO   varchar(1000),
  SSA_AUID   integer        not null,
  SSA_ADATE  timestamp      not null,
  SSA_EUID   integer        not null,
  SSA_EDATE  timestamp      not null,
  primary key(SSA_SID)
) ;