create table NTP_NI_SHOHIN
(
      NSH_SID               integer           not null,
      NIP_SID               integer           not null,
      MHN_SID               integer           not null,
      NSH_NAME              varchar(50)               ,
      NSH_PRICE_SALE        integer                   ,
      NSH_PRICE_COST        integer                   ,
      NSH_AUID              integer                   ,
      NSH_ADATE             timestamp         not null,
      NSH_EUID              integer                   ,
      NSH_EDATE             timestamp         not null,
      primary key (NSH_SID)
);
