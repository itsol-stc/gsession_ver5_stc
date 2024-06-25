create table BMK_BOOKMARK
(
        BMK_SID     integer     not null,
        BMK_KBN     integer     not null,
        USR_SID     integer     not null,
        GRP_SID     integer,
        BMU_SID     integer,
        BMK_TITLE   varchar(150)    not null,
        BMK_CMT     varchar(1000)   not null,
        BMK_SCORE   integer     not null,
        BMK_PUBLIC  integer     not null,
        BMK_MAIN    integer     not null,
        BMK_SORT    integer     not null,
        BMK_AUID    integer     not null,
        BMK_ADATE   timestamp       not null,
        BMK_EUID    integer     not null,
        BMK_EDATE   timestamp       not null,
        primary key (
                BMK_SID
        ) 
) ;