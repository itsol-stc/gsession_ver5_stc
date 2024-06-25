create table BMK_GROUP_EDIT
(
        GRP_SID     integer not null,
        BGE_GRP_SID integer not null,
        BGE_USR_SID integer not null,
        BGE_AUID    integer not null,
        BGE_ADATE   timestamp   not null,
        BGE_EUID    integer not null,
        BGE_EDATE   timestamp   not null,
        primary key (
                GRP_SID,
                BGE_GRP_SID,
                BGE_USR_SID
        ) 
) ;