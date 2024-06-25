create table BMK_URL
(
        BMU_SID       integer       not null,
        BMU_URL       varchar(1000) not null,
        BMU_TITLE     varchar(150)  not null,
        BMU_PUB_DATE  timestamp,
        BMU_AUID      integer       not null,
        BMU_ADATE     timestamp    not null,
        BMU_EUID      integer       not null,
        BMU_EDATE     timestamp    not null,
        primary key (
                BMU_SID
        ) 
) ;