create table ENQ_DESC_BIN(
   EMN_SID            bigint         not null,
   EDB_SID            integer        not null,
   BIN_SID            bigint         not null,
   primary key (EMN_SID, EDB_SID)
);