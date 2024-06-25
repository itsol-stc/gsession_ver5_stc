 create table FILE_CABINET_ADMIN
   (
     FCB_SID          integer      not null,
     USR_SID          integer      not null,
     primary key (FCB_SID, USR_SID)
   );
