create table NTP_KAKUNIN
(
      NKK_SID               integer           not null,
      NIP_SID               integer           not null,
      USR_SID               integer           not null,
      NKK_CHECK             integer           not null,
      NKK_DATE_CHECK        timestamp                 ,
      NKK_AUID              integer                   ,
      NKK_ADATE             timestamp         not null,
      NKK_EUID              integer                   ,
      NKK_EDATE             timestamp         not null,
      primary key (NKK_SID)
);
