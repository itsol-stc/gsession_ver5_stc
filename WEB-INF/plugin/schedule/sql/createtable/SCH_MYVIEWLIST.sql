create table SCH_MYVIEWLIST
(
    SMY_SID        integer         not null,
    USR_SID        integer         not null,
    SMY_NAME       varchar(50)     not null,
    SMY_BIKO       varchar(1000)            ,
    SMY_SORT       integer         not null,
    MGP_AUID       integer         not null,
    MGP_ADATE      timestamp       not null,
    MGP_EUID       integer         not null,
    MGP_EDATE      timestamp       not null,
    primary key (SMY_SID)
);