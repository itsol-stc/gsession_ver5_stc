create table ENQ_QUE_MAIN
(
        EMN_SID         bigint          not null,
        EQM_SEQ         integer         not null,
        EQM_DSP_SEC     integer                 ,
        EQM_QUE_SEC     varchar(10)             ,
        EQM_QUESTION    varchar(100)            ,
        EQM_QUE_KBN     integer                 ,
        EQM_REQUIRE     integer                 ,
        EQM_OTHER       integer                 ,
        EQM_DESC        text                    ,
        EQM_DESC_PLAIN  text                    ,
        EQM_ATTACH_KBN  integer                 ,
        EQM_ATTACH_ID   varchar(100)            ,
        EQM_ATTACH_NAME varchar(100)            ,
        EQM_ATTACH_POS  integer                 ,
        EQM_LINE_KBN    integer                 ,
        EQM_GRF_KBN     integer                 ,
        EQM_AUID        integer         not null,
        EQM_ADATE       timestamp       not null,
        EQM_EUID        integer         not null,
        EQM_EDATE       timestamp       not null,

        primary key (EMN_SID, EQM_SEQ)
);