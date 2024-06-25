create table SCH_DATA_PUB
(
  SCD_SID         integer         not null,
  SDP_TYPE        integer         not null,
  SDP_PSID        integer         not null,
  primary key (SCD_SID, SDP_TYPE, SDP_PSID)
);