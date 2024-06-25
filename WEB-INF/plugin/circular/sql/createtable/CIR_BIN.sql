create table CIR_BIN
(
        CIF_SID        integer      not null,
        BIN_SID        bigint       not null,
        primary key (CIF_SID, BIN_SID)
) ;