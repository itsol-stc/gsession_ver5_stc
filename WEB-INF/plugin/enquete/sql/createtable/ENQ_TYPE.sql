create table ENQ_TYPE
(
        ETP_SID         bigint          not null,
        ETP_DSP_SEQ     integer                 ,
        ETP_NAME        varchar(100)            ,
        ETP_AUID        integer         not null,
        ETP_ADATE       timestamp       not null,
        ETP_EUID        integer         not null,
        ETP_EDATE       timestamp       not null,

        primary key(ETP_SID)
);