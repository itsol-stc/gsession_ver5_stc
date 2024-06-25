create table RSV_SIS_KBN
(
  RSK_SID          integer not null,
  RSK_NAME         varchar(10) not null,
  RSK_SORT         integer not null,
  RSK_AUID         integer not null,
  RSK_ADATE        timestamp not null,
  RSK_EUID         integer not null,
  RSK_EDATE        timestamp not null,
  primary key 	(RSK_SID)
);