create table RSV_SIS_GRP
(
  RSG_SID           integer not null,
  RSK_SID           integer not null,
  RSG_ID            varchar(15) not null,
  RSG_NAME          varchar(20) not null,
  RSG_ADM_KBN       integer not null,
  RSG_SORT          integer not null,
  RSG_ACS_LIMIT_KBN integer not null default -1,
  RSG_APPR_KBN      integer not null default 0,
  RSG_AUID          integer not null,
  RSG_ADATE         timestamp not null,
  RSG_EUID          integer not null,
  RSG_EDATE         timestamp not null,
  primary key       (RSG_SID)
);