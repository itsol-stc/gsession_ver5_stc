create table SML_BIN
(
        SML_SID        integer      not null,
        BIN_SID        bigint       not null,
        primary key (SML_SID, BIN_SID)
) ;