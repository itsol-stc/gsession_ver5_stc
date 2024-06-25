create table ENQ_ANS_MAIN
(
        EMN_SID         bigint          not null,
        USR_SID         integer         not null,
        EAM_STS_KBN     integer                 ,
        EQM_ANS_DATE    timestamp               ,
        EAM_AUID        integer         not null,
        EAM_ADATE       timestamp       not null,
        EAM_EUID        integer         not null,
        EAM_EDATE       timestamp       not null,

        primary key(EMN_SID, USR_SID)
);