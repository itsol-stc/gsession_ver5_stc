 create table FILE_FILE_BIN
   (
     FDR_SID          integer      not null,
     FDR_VERSION      integer      not null,
     BIN_SID          bigint       not null,
     FFL_EXT          varchar(150) ,
     FFL_FILE_SIZE    bigint       not null,
     FFL_LOCK_KBN     integer      not null,
     FFL_LOCK_USER    integer      not null,
     FFL_LOCK_DATE    timestamp,
     primary key (FDR_SID, FDR_VERSION)
   );
