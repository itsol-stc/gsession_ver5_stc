create table SCH_EXADDRESS
(
  SCE_SID         integer         not null,
  ADR_SID         integer         not null,
  SEA_AUID        integer         not null,
  SEA_ADATE       timestamp      not null,
  SEA_EUID        integer         not null,
  SEA_EDATE       timestamp      not null,
  primary key (SCE_SID, ADR_SID)
);