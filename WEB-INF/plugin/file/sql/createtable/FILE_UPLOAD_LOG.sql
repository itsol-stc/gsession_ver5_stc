 create table FILE_UPLOAD_LOG
 (
     USR_SID        integer         not null,
     GRP_SID        integer         not null,
     FUL_REG_KBN        integer         not null,
     FCB_SID        integer         not null,
     BIN_SID        bigint         not null,
     FUL_DATE      timestamp       not null
 ) ;