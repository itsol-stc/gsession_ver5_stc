create table ENQ_CRT_USER
(
        ECU_KBN         integer         not null,
        ECU_SID         bigint          not null,
        ECU_AUID        integer         not null,
        ECU_ADATE       timestamp       not null,
        ECU_EUID        integer         not null,
        ECU_EDATE       timestamp       not null,

        primary key(ECU_KBN, ECU_SID)
);