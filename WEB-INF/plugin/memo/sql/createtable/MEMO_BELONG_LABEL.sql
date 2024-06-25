create table MEMO_BELONG_LABEL
(
  MEM_SID  bigint   not null,
  MML_SID  integer  not null,
  primary key (MEM_SID, MML_SID)
);