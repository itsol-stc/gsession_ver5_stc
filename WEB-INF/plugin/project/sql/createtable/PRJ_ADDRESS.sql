create table PRJ_ADDRESS
(
  PRJ_SID         integer         not null,
  ADR_SID         integer         not null,
  PRA_AUID        integer         not null,
  PRA_ADATE       timestamp       not null,
  PRA_EUID        integer         not null,
  PRA_EDATE       timestamp       not null,
  PRA_SORT        integer         not null,
  primary key (PRJ_SID, ADR_SID)
);