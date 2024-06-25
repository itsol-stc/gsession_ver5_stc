create table NTP_TARGET
(
      NTG_SID               integer           not null,
      NTG_NAME              varchar(50)       not null,
      NTG_UNIT              varchar(50)       not null,
      NTG_DEF               bigint            not null,
      NTG_DETAIL            varchar(1000)             ,
      NTG_AUID              integer                   ,
      NTG_ADATE             timestamp                 ,
      NTG_EUID              integer                   ,
      NTG_EDATE             timestamp                 ,
      primary key (NTG_SID)
);
