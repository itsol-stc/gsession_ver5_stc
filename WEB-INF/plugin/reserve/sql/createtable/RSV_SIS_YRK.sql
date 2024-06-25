create table RSV_SIS_YRK
(
  RSY_SID          integer not null,
  RSD_SID          integer not null,
  RSY_YGRP_SID     integer,
  RSY_MOK          varchar(50) not null,
  RSY_FR_DATE      timestamp not null,
  RSY_TO_DATE      timestamp not null,
  RSY_BIKO         varchar(1000),
  RSY_AUID         integer not null,
  RSY_ADATE        timestamp not null,
  RSY_EUID         integer not null,
  RSY_EDATE        timestamp not null,
  SCD_RSSID        integer,
  RSY_EDIT         integer not null,
  RSR_RSID         integer,
  RSY_APPR_STATUS  integer not null default 0,
  RSY_APPR_KBN     integer not null default 0,
  RSY_APPR_UID     integer,
  RSY_APPR_DATE    timestamp,
  RSY_PUBLIC integer default 0 not null,
  primary key 	(RSY_SID)
);