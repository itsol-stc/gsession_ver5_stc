 create table FILE_ACCESS_CONF
   (
     FCB_SID          integer      not null,
     USR_SID          integer      not null,
     USR_KBN          integer      not null,
     FAA_AUTH         integer      not null,
     primary key (FCB_SID, USR_SID, USR_KBN)
   );
