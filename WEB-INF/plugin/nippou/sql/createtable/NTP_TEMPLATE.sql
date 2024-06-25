create table NTP_TEMPLATE
(
      NTT_SID               integer           not null,
      NTT_NAME              varchar(50)       not null,
      NTT_ANKEN             integer           not null,
      NTT_COMP              integer           not null,
      NTT_KATUDO            integer           not null,
      NTT_MIKOMI            integer           not null,
      NTT_TEMP              integer           not null,
      NTT_ACTION            integer           not null,
      NTT_DETAIL            varchar(1000)             ,
      NTT_AUID              integer                   ,
      NTT_ADATE             timestamp                 ,
      NTT_EUID              integer                   ,
      NTT_EDATE             timestamp                 ,
      primary key (NTT_SID)
);
