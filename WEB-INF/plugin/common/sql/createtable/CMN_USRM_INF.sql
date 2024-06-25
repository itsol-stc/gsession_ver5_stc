create table CMN_USRM_INF
(
        USR_SID         integer      not null,
        USI_SEI         varchar(30),
        USI_MEI         varchar(30),
        USI_SEI_KN      varchar(60),
        USI_MEI_KN      varchar(60),
        USI_SINI        varchar(3),
        USI_BDATE       date,
        USI_ZIP1        varchar(3),
        USI_ZIP2        varchar(4),
        TDF_SID         integer,
        USI_ADDR1       varchar(100),
        USI_ADDR2       varchar(100),
        USI_TEL1        varchar(20),
        USI_TEL_NAI1        varchar(15),
        USI_TEL_CMT1        varchar(10),
        USI_TEL2        varchar(20),
        USI_TEL_NAI2        varchar(15),
        USI_TEL_CMT2        varchar(10),
        USI_TEL3        varchar(20),
        USI_TEL_NAI3        varchar(15),
        USI_TEL_CMT3        varchar(10),
        USI_FAX1        varchar(20),
        USI_FAX_CMT1        varchar(10),
        USI_FAX2        varchar(20),
        USI_FAX_CMT2        varchar(10),
        USI_FAX3        varchar(20),
        USI_FAX_CMT3        varchar(10),
        USI_MAIL1       varchar(256),
        USI_MAIL_CMT1        varchar(10),
        USI_MAIL2       varchar(256),
        USI_MAIL_CMT2        varchar(10),
        USI_MAIL3       varchar(256),
        USI_MAIL_CMT3        varchar(10),
        USI_SYAIN_NO    varchar(20),
        USI_SYOZOKU     varchar(60),
        USI_YAKUSYOKU   varchar(30),
        USI_SEIBETU    integer      not null,
        USI_ENTRANCE_DATE   timestamp,
        USI_SORTKEY1        varchar(10),
        USI_SORTKEY2        varchar(10),
        POS_SID        integer,
        USI_BIKO        varchar(1000),
        BIN_SID         bigint,
        USI_PICT_KF     integer,
        USI_BDATE_KF    integer,
        USI_MAIL1_KF    integer,
        USI_MAIL2_KF    integer,
        USI_MAIL3_KF    integer,
        USI_ZIP_KF      integer,
        USI_TDF_KF      integer,
        USI_ADDR1_KF    integer,
        USI_ADDR2_KF    integer,
        USI_TEL1_KF     integer,
        USI_TEL2_KF     integer,
        USI_TEL3_KF     integer,
        USI_FAX1_KF     integer,
        USI_FAX2_KF     integer,
        USI_FAX3_KF     integer,
        USI_LTLGIN      timestamp,
        USI_AUID        integer      not null,
        USI_ADATE       timestamp       not null,
        USI_EUID        integer      not null,
        USI_EDATE       timestamp       not null,
        USI_MBL_USE     integer,
        USI_NUM_CONT    integer,
        USI_NUM_AUTADD  integer,
        USI_OTPSEND_ADDRESS varchar(256),
        primary key (USR_SID)
);