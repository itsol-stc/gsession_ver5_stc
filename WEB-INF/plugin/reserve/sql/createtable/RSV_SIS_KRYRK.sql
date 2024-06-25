create table RSV_SIS_KRYRK
(
  RSR_RSID           integer not null,
  RKR_BUSYO          varchar(50),
  RKR_NAME          varchar(70),
  RKR_NUM           varchar(5),
  RKR_USE_KBN           integer,
  RKR_CONTACT          varchar(50),
  RKR_GUIDE          varchar(50),
  RKR_PARK_NUM           varchar(5),
  RKR_PRINT_KBN           integer,
  RKR_DEST          varchar(50),
  RKR_AUID          integer not null,
  RKR_ADATE         timestamp not null,
  RKR_EUID          integer not null,
  RKR_EDATE         timestamp not null,
  primary key  (RSR_RSID)
);
