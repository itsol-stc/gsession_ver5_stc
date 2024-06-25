create table CHT_USER_DATA_SUM
(
  CUP_SID  integer not null,
  CUS_CNT  integer not null,
  CUS_LASTSID bigint not null,
  CUS_LASTDATE timestamp,
  primary key(CUP_SID)
);