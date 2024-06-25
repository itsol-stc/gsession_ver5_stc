create table MEMO_DATA
(
    MEM_SID       bigint         not null,
    USR_SID       integer        not null,
    MMD_CONTENT   varchar(1000)  not null,
    MMD_DEL_CONF  integer        not null,
    MMD_AUID      integer        not null,
    MMD_ADATE     timestamp      not null,
    MMD_EUID      integer        not null,
    MMD_EDATE     timestamp      not null,
    primary key (MEM_SID)
);