create table PRJ_PRJDATA
(
        PRJ_SID         integer    not null,
        PRJ_MY_KBN      integer    not null,
        PRJ_ID          varchar(20)  not null,
        PRJ_NAME        varchar(100)  not null,
        PRJ_NAME_SHORT  varchar(20)  not null,
        PRJ_YOSAN       bigint,
        PRJ_KOUKAI_KBN  integer    not null,
        PRJ_DATE_START  timestamp  ,
        PRJ_DATE_END    timestamp  ,
        PRJ_STATUS_SID  integer    not null,
        PRJ_TARGET      varchar(300)  ,
        PRJ_CONTENT     varchar(1000)  ,
        PRJ_EDIT        integer    not null,
        PRJ_MAIL_KBN    integer    not null,
        PRJ_AUID        integer    not null,
        PRJ_ADATE       timestamp  not null,
        PRJ_EUID        integer    not null,
        PRJ_EDATE       timestamp  not null,
        BIN_SID         bigint     not null,
        primary key (PRJ_SID)
) ;