create table NTP_KTBUNRUI
(
      NKB_SID               integer           not null,
      NKB_CODE              varchar(5)        not null,
      NKB_NAME              varchar(50)       not null,
      NKB_TIEUP_KBN         integer           not null,
      NKB_AUID              integer                   ,
      NKB_ADATE             timestamp                 ,
      NKB_EUID              integer                   ,
      NKB_EDATE             timestamp                 ,
      primary key (NKB_SID)
);
