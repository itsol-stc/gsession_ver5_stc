create table ANP_JDATA
(
        APH_SID           integer         not null,
        USR_SID           integer         not null,
        APD_MAILADR       varchar(768)            ,
        APD_JOKYO_FLG     integer         not null,
        APD_PLACE_FLG     integer         not null,
        APD_SYUSYA_FLG    integer         not null,
        APD_COMMENT       varchar(3000)           ,
        APD_HDATE         timestamp,
        APD_SCOUNT        integer         not null,
        APD_CDATE         timestamp               ,
        APD_RDATE         timestamp               ,
        APD_HAISIN_FLG    integer         not null,
        APD_AUID          integer         not null,
        APD_ADATE         timestamp       not null,
        APD_EUID          integer         not null,
        APD_EDATE         timestamp       not null,
        primary key (APH_SID, USR_SID)
);