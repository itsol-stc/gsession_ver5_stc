create table SCH_COL_MSG
(
        SCM_ID      integer    not null,
        SCM_MSG     varchar(30),
        SCM_AUID    integer    not null,
        SCM_ADATE   timestamp  not null,
        SCM_EUID    integer    not null,
        SCM_EDATE   timestamp  not null,
        primary key (SCM_ID)
) ;