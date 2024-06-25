 create table PRJ_TODOSTATUS_TMP
 (
   PRT_SID         integer         not null,
   PST_SID         integer         not null,
   PST_NAME        varchar(20)     not null,
   PST_RATE        integer         not null,
   PST_SORT        integer         not null,
   PST_AUID        integer         not null,
   PST_ADATE       timestamp       not null,
   PST_EUID        integer         not null,
   PST_EDATE       timestamp       not null,
   primary key (PRT_SID, PST_SID) 
 );
