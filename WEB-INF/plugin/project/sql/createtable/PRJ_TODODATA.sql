create table PRJ_TODODATA
(
        PRJ_SID          integer    not null,
        PTD_SID          integer    not null,
        PTD_NO           integer    not null,
        PTD_CATEGORY     integer    not null,
        PTD_TITLE        varchar(100)  not null,
        PTD_DATE_PLAN    timestamp,
        PRJ_DATE_LIMIT   timestamp,
        PTD_DATE_START   timestamp,
        PTD_DATE_END     timestamp,
        PTD_PLAN_KOSU    decimal(4, 1),
        PTD_RESULTS_KOSU decimal(4, 1),
        PTD_ALARM_KBN    integer    not null,
        PTD_IMPORTANCE   integer    not null,
        PSH_SID          integer    not null,
        PTS_SID          integer    not null,
        PTD_CONTENT      varchar(1000)  ,
        PTD_AUID         integer    not null,
        PTD_ADATE        timestamp  not null,
        PTD_EUID         integer    not null,
        PTD_EDATE        timestamp  not null,
        primary key (PRJ_SID, PTD_SID)
) ;