 create table PRJ_FILE_BIN
   (
     PDR_SID         integer      not null,
     BIN_SID         bigint       not null,
     PFL_EXT         varchar(50),
     PFL_FILE_SIZE   integer      not null,
     primary key (PDR_SID, BIN_SID)
   );
