create table BMK_GCONF
(
        GRP_SID     integer not null,
        BGC_EDIT    integer not null,
        BGC_AUID    integer not null,
        BGC_ADATE   timestamp   not null,
        BGC_EUID    integer not null,
        BGC_EDATE   timestamp   not null,
        primary key (
                GRP_SID
        ) 
) ;