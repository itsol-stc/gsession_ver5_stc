create table NTP_SHOHIN
(
      NHN_SID               integer           not null,
      NHN_CODE              varchar(13)       not null,
      NHN_NAME              varchar(50)       not null,
      NHN_PRICE_SALE        integer                   ,
      NHN_PRICE_COST        integer                   ,
      NHN_HOSOKU            varchar(1000)             ,
      NHN_AUID              integer                   ,
      NHN_ADATE             timestamp                 ,
      NHN_EUID              integer                   ,
      NHN_EDATE             timestamp                 ,
      NSC_SID               integer           not null,
      primary key (NHN_SID)
);
