create table SCH_BIN
(
        SCD_SID        integer        not null,
        BIN_SID        bigint         not null,
        primary key (SCD_SID, BIN_SID)
);