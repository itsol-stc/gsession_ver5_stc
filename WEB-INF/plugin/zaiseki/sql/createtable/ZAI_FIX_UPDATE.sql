create table ZAI_FIX_UPDATE
(
    ZFU_UPDATE_KBN         integer         not null,
    ZFU_FIX_UPDATE_TIME    integer         not null,
    ZFU_STATUS             integer         not null,
    ZFU_MSG                varchar(30)     not null,
    ZFU_AUID               integer         not null,
    ZFU_ADATE              timestamp       not null,
    ZFU_EUID               integer         not null,
    ZFU_EDATE              timestamp       not null
);