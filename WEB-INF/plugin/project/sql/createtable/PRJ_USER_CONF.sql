create table PRJ_USER_CONF
(
        USR_SID           integer     not null,
        PUC_PRJ_CNT       integer     not null,
        PUC_TODO_CNT      integer     not null,
        PUC_AUID          integer     not null,
        PUC_ADATE         timestamp   not null,
        PUC_EUID          integer     not null,
        PUC_EDATE         timestamp   not null,
        PUC_TODO_DATE     integer     default 2,
        PUC_TODO_PROJECT  integer     default 0,
        PUC_TODO_STATUS   integer     default -4,
        PUC_PRJ_PROJECT   integer     default 1,
        PUC_MAIN_DATE     integer     default 2,
        PUC_MAIN_STATUS   integer     default -4,
        PUC_MAIN_MENBER   integer     default 0,
        PUC_DEF_DSP       integer     default 0,
        PUC_SCH_KBN       integer     default 0,
        PUC_TODO_DSP      integer     default 0,
        primary key (USR_SID)
) ;