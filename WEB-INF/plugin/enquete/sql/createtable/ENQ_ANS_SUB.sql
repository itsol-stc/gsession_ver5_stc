create table ENQ_ANS_SUB
(
        EMN_SID         bigint          not null,
        USR_SID         integer         not null,
        EQM_SEQ         integer         not null,
        EQS_SEQ         integer         not null,
        EAS_ANS_TXT     varchar(1000)           ,
        EAS_ANS_NUM     decimal(15, 5)          ,
        EAS_ANS_DAT     date                    ,
        EAS_ANS         varchar(1000)           ,
        EQM_AUID        integer         not null,
        EQM_ADATE       timestamp       not null,
        EQM_EUID        integer         not null,
        EQM_EDATE       timestamp       not null,

        primary key(EMN_SID, USR_SID, EQM_SEQ, EQS_SEQ)
);