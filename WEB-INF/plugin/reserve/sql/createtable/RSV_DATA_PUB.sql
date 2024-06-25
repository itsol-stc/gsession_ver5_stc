create table RSV_DATA_PUB
(
  RSY_SID         integer         not null,
  RDP_TYPE        integer         not null,
  RDP_PSID        integer         not null,
  primary key (RSY_SID, RDP_TYPE, RDP_PSID)
);