create table SML_USER
(
        USR_SID       integer      not null,
        SML_MAX_DSP   integer      not null,
        SML_AUID      integer      not null,
        SML_ADATE     timestamp    not null,
        SML_EUID      integer      not null,
        SML_EDATE     timestamp    not null,
        SML_RELOAD    integer      not null,
        SML_MAIN_KBN  integer      not null,
        SML_MAIN_CNT  integer      not null,
        SML_MAIN_SORT integer      not null,
        SML_PHOTO_DSP integer      not null,
        SML_TEMP_DSP integer      not null,
        primary key (USR_SID)
) ;