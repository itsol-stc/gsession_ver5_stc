create table ZAI_INDEX
(
    ZIF_SID          integer         not null,
    ZIN_LINKKBN      integer         not null,
    ZIN_LINKSID      integer         not null,
    ZIN_NAME         varchar(50)     not null,
    ZIN_BGCOLOR      integer         not null,
    ZIN_MSG          varchar(1000)           ,
    ZIN_XINDEX       integer         not null,
    ZIN_YINDEX       integer         not null,
    ZIN_OTHER_VALUE  varchar(20)     not null,
    ZIN_AUID         integer         not null,
    ZIN_ADATE        timestamp       not null,
    ZIN_EUID         integer         not null,
    ZIN_EDATE        timestamp       not null
);