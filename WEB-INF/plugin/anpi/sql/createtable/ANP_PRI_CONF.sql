create table ANP_PRI_CONF
(
        USR_SID           integer         not null,
        APP_MAIN_KBN      integer         not null,
        APP_LIST_COUNT    integer         not null,
        APP_DSP_GROUP     integer         not null,
        APP_DSP_MYGROUP   integer                 ,
        APP_ALLGROUP_FLG     integer         not null,
        APP_MAILADR       varchar(768)            ,
        APP_TELNO         varchar(60)             ,
        APP_AUID          integer         not null,
        APP_ADATE         timestamp       not null,
        APP_EUID          integer         not null,
        APP_EDATE         timestamp       not null,
        primary key (USR_SID)
);