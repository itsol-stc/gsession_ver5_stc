create table ZAI_INFO
(
    ZIF_SID          integer         not null,
    ZIF_NAME         varchar(50)     not null,
    BIN_SID          bigint          not null,
    ZIF_SORT         integer         not null,
    ZIF_MSG          varchar(1000)           ,
    ZIF_AUID         integer         not null,
    ZIF_ADATE        timestamp       not null,
    ZIF_EUID         integer         not null,
    ZIF_EDATE        timestamp       not null,
    primary key (ZIF_SID)
);
