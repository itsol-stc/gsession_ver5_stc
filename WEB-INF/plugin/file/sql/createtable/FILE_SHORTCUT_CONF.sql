 create table FILE_SHORTCUT_CONF
   (
     FDR_SID          integer      not null,
     USR_SID          integer      not null,
     FSC_ADATE        timestamp    not null,
     primary key (FDR_SID, USR_SID)
   );
