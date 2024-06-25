create table CHT_GROUP_DATA_SUM
(
  CGI_SID  integer not null,
  CGS_CNT  integer not null,
  CGS_LASTSID bigint not null,
  CGS_LASTDATE timestamp,
  primary key(CGI_SID)
);