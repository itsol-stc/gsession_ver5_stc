 create table FILE_DIRECTORY_BIN
   (
     FDR_SID          integer      not null,
     FDR_VERSION      integer      not null,
     BIN_SID          bigint       not null,
     primary key (FDR_SID, FDR_VERSION, BIN_SID)
   );