create table RSV_EXDATA_PUB
(
  RSR_RSID         integer         not null,
  REP_TYPE        integer         not null,
  REP_PSID        integer         not null,
  primary key (RSR_RSID, REP_TYPE, REP_PSID)
);