 create table FILE_CALL_DATA
   (
     FDR_SID          integer      not null,
     USR_SID          integer      not null,
     primary key (FDR_SID, USR_SID)
   );
