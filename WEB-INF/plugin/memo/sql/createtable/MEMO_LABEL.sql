create table MEMO_LABEL
(
  MML_SID    integer      not null,
  MML_NAME   varchar(20)  not null,
  USR_SID    integer      not null,
  MML_SORT   integer      not null,
  MML_AUID   integer      not null,
  MML_ADATE  timestamp    not null,
  MML_EUID   integer      not null,
  MML_EDATE  timestamp    not null,
  primary key (MML_SID)
);