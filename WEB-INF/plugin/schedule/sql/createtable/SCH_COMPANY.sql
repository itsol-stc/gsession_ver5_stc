create table SCH_COMPANY
(
  SCD_SID         integer         not null,
  ACO_SID         integer         not null,
  ABA_SID         integer         not null,
  SCC_AUID        integer         not null,
  SCC_ADATE       timestamp      not null,
  SCC_EUID        integer         not null,
  SCC_EDATE       timestamp      not null,
  primary key (SCD_SID, ACO_SID, ABA_SID)
);