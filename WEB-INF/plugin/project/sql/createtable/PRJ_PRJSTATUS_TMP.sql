 create table PRJ_PRJSTATUS_TMP
 (
   PRT_SID         integer         not null,
   PTT_SID         integer         not null,
   PTT_SORT        integer         not null,
   PTT_NAME        varchar(20)     not null,
   PTT_RATE        integer         not null,
   PTT_AUID        integer         not null,
   PTT_ADATE       timestamp       not null,
   PTT_EUID        integer         not null,
   PTT_EDATE       timestamp       not null,
   primary key (PRT_SID, PTT_SID)
 );
