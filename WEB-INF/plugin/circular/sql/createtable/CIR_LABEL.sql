create table CIR_LABEL
(
  CLA_SID                integer       not null,
  CAC_SID                integer       not null,
  CLA_NAME               varchar(100)  not null,
  CLA_ORDER              integer       not null,
  CLA_AUID        integer      not null,
  CLA_ADATE       timestamp       not null,
  CLA_EUID        integer      not null,
  CLA_EDATE       timestamp       not null,
  primary key(CLA_SID)
);