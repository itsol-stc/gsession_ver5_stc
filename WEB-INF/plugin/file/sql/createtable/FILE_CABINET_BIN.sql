 create table FILE_CABINET_BIN
   (
     FCB_SID          integer      not null,
     BIN_SID          bigint       not null,
     primary key (FCB_SID, BIN_SID)
   );