create table WK_ZAI_INDEX
(
    WZI_SESSION_SID  varchar(50)     not null,
    WZI_KEY          varchar(50)     not null,
    WZI_SID          integer         not null,
    WZI_LINKKBN      integer         not null,
    WZI_LINKSID      integer         not null,
    WZI_NAME         varchar(50)     not null,
    WZI_BGCOLOR      integer         not null,
    WZI_MSG          varchar(1000)           ,
    WZI_XINDEX       integer         not null,
    WZI_YINDEX       integer         not null,
    WZI_OTHER_VALUE  varchar(20)     not null
);