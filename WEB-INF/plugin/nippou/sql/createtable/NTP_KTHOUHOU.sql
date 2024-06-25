create table NTP_KTHOUHOU
(
      NKH_SID               integer           not null,
      NKH_CODE              varchar(5)        not null,
      NKH_NAME              varchar(50)       not null,
      NKH_AUID              integer                   ,
      NKH_ADATE             timestamp                 ,
      NKH_EUID              integer                   ,
      NKH_EDATE             timestamp                 ,
      primary key (NKH_SID)
);
