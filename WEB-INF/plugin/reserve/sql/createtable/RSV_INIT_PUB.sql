create table RSV_INIT_PUB
(
  USR_SID         integer         not null,
  RIP_TYPE        integer         not null,
  RIP_PSID        integer         not null,
  primary key (USR_SID, RIP_TYPE, RIP_PSID)
);