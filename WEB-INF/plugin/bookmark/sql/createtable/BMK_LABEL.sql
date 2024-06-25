create table BMK_LABEL
(
        BLB_SID     integer not null,
        BLB_KBN     integer not null,
        USR_SID     integer not null,
        GRP_SID     integer not null,
        BLB_NAME    varchar(20) not null,
        BLB_AUID    integer not null,
        BLB_ADATE   timestamp   not null,
        BLB_EUID    integer not null,
        BLB_EDATE   timestamp   not null,
        primary key (
                BLB_SID
        ) 
) ;