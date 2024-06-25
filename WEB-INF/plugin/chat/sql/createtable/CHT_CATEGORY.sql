create table CHT_CATEGORY
(
  CHC_SID         integer not null,
  CHC_NAME        VARCHAR(20) not null,
  CHC_SORT        integer not null,
  CHC_AUID        integer not null,
  CHC_ADATE       timestamp not null,
  CHC_EUID        integer not null,
  CHC_EDATE       timestamp not null,
  primary key (CHC_SID)
) ;