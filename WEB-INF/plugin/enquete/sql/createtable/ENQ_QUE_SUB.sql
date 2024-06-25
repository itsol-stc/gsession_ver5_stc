create table ENQ_QUE_SUB
(
        EMN_SID         bigint          not null,
        EQM_SEQ         integer         not null,
        EQS_SEQ         integer         not null,
        EQS_DSP_SEC     integer                 ,
        EQS_DSP_NAME    varchar(30)             ,
        EQS_DEF_TXT     varchar(1000)           ,
        EQS_DEF_NUM     decimal(15, 5)          ,
        EQS_DEF_DAT     date                    ,
        EQS_DEF         varchar(1000)           ,
        EQS_RNG_STR_NUM decimal(15, 5)          ,
        EQS_RNG_END_NUM decimal(15, 5)          ,
        EQS_RNG_STR_DAT date                    ,
        EQS_RNG_END_DAT date                    ,
        EQS_UNIT_NUM    varchar(10)             ,
        EQS_AUID        integer         not null,
        EQS_ADATE       timestamp       not null,
        EQS_EUID        integer         not null,
        EQS_EDATE       timestamp       not null,

        primary key (EMN_SID, EQM_SEQ, EQS_SEQ)
);