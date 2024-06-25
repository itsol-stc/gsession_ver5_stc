create table BMK_BELONG_LABEL
(
        BMK_SID     integer not null,
        BLB_SID     integer not null,
        BBL_AUID    integer not null,
        BBL_ADATE   timestamp   not null,
        BBL_EUID    integer not null,
        BBL_EDATE   timestamp   not null,
        primary key (
                BMK_SID,
                BLB_SID
        ) 
) ;