create table SCH_EXDATA_BIN
(
        SCE_SID        integer        not null,
        BIN_SID        bigint         not null,
        primary key (SCE_SID, BIN_SID)
);