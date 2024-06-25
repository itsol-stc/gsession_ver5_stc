 create table PRJ_PRJDATA_TMP
 (
   PRT_SID         integer        not null,
   PRT_KBN         integer        not null,
   PRT_TMP_NAME    varchar(50)    not null,
   PRT_USR_SID     integer        not null,
   PRT_ID          varchar(20),
   PRT_NAME        varchar(100),
   PRT_NAME_SHORT  varchar(20),
   PRT_YOSAN       integer,
   PRT_KOUKAI_KBN  integer        not null,
   PRT_DATE_START  timestamp,
   PRT_DATE_END    timestamp,
   PRT_STATUS_SID  integer        not null,
   PRT_TARGET      varchar(300),
   PRT_CONTENT     varchar(1000),
   PRT_MAIL_KBN    integer        not null,
   PRT_EDIT        integer        not null,
   PRT_AUID        integer        not null,
   PRT_ADATE       timestamp      not null,
   PRT_EUID        integer        not null,
   PRT_EDATE       timestamp      not null,
   primary key (PRT_SID)
 );
