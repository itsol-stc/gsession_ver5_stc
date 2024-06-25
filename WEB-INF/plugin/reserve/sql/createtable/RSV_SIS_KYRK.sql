create table RSV_SIS_KYRK
(
  RSY_SID           integer not null,
  RKY_BUSYO          varchar(50),
  RKY_NAME          varchar(70),
  RKY_NUM           varchar(5),
  RKY_USE_KBN           integer,
  RKY_CONTACT          varchar(50),
  RKY_GUIDE          varchar(50),
  RKY_PARK_NUM           varchar(5),
  RKY_PRINT_KBN           integer,
  RKY_DEST          varchar(50),
  RKY_AUID          integer not null,
  RKY_ADATE         timestamp not null,
  RKY_EUID          integer not null,
  RKY_EDATE         timestamp not null,
  primary key  (RSY_SID)
);
