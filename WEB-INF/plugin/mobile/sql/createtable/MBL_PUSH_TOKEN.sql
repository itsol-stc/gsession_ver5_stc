create table MBL_PUSH_TOKEN
(
     MPT_PUSH_TOKEN    varchar(256)        not null,
     USR_SID           integer             not null,
     CMU_UID           varchar(50)         not null,
     MPT_APP_ID        varchar(256)        not null,
     primary key (MPT_PUSH_TOKEN)
) ;
