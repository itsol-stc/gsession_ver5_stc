create table PRJ_TODOSTATUS
(
        PRJ_SID         integer         not null,
        PTS_SID         integer         not null,
        PTS_NAME        varchar(20)     not null,
        PTS_RATE        integer         not null,
        PTS_SORT        integer         not null,
        PTS_AUID        integer         not null,
        PTS_ADATE       timestamp       not null,
        PTS_EUID        integer         not null,
        PTS_EDATE       timestamp       not null,
        primary key (PRJ_SID, PTS_SID) 
) ;
