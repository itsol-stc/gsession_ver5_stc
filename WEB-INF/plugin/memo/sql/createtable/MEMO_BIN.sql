create table MEMO_BIN
(
  MEM_SID  bigint  not null,
  BIN_SID  bigint  not null,
  primary key (MEM_SID, BIN_SID)
);