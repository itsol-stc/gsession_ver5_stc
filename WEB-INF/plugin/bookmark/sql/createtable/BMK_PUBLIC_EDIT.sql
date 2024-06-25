create table BMK_PUBLIC_EDIT
(
        GRP_SID     integer not null,
        USR_SID     integer not null,
        BPE_AUID    integer not null,
        BPE_ADATE   timestamp   not null,
        BPE_EUID    integer not null,
        BPE_EDATE   timestamp   not null,
        primary key (
                GRP_SID,
                USR_SID
        ) 
) ;