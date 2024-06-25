create table CIR_USER
(
        USR_SID       integer      not null,
        CUR_MAX_DSP   integer      not null,
        CUR_RELOAD    integer      not null,
        CUR_AUID      integer      not null,
        CUR_ADATE     timestamp    not null,
        CUR_EUID      integer      not null,
        CUR_EDATE     timestamp    not null,
        primary key (USR_SID)
) ;