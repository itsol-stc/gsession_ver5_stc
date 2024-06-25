 create table FILE_ERRL_ADDDATA
   (
     FEA_SID          integer      not null,
     BIN_SID          bigint       not null,
     FFL_EXT          varchar(50),
     FFL_FILE_SIZE    bigint       not null,
     FCB_SID          integer      not null,
     FDR_PARENT_SID   integer      not null,
     FDR_NAME         varchar(255) not null,
     FDR_BIKO         varchar(1000),
     FFR_UP_CMT       varchar(1000),
     FDR_AUID         integer      not null,
     FDR_ADATE        timestamp    not null,
     FEA_DEFGRP_NAME  varchar(50)  not null,
     primary key (FEA_SID)
   );