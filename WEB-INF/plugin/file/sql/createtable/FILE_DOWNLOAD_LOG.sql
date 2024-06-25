 create table FILE_DOWNLOAD_LOG
 (
     USR_SID        integer         not null,
     FCB_SID        integer         not null,
     BIN_SID        bigint         not null,
     FDL_DATE      timestamp       not null
 ) ;