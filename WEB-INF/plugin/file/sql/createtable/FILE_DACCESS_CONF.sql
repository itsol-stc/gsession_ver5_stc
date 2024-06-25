 create table FILE_DACCESS_CONF
   (
     FDR_SID          integer      not null,
     USR_SID          integer      not null,
     USR_KBN          integer      not null,
     FDA_AUTH         integer      not null,
     primary key (FDR_SID, USR_SID, USR_KBN)
   );