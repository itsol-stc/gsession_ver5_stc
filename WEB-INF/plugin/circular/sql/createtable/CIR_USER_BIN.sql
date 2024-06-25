create table CIR_USER_BIN
(
        CIF_SID        integer      not null,
        CAC_SID        integer      not null,
        CUB_BIN_SID    bigint       not null,
        primary key (CIF_SID, CAC_SID, CUB_BIN_SID)
) ;