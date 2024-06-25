create table NTP_SANKA
(
      NSN_SID               integer           not null,
      NIP_SID               integer           not null,
      USR_SID               integer           not null,
      NTA_AUID              integer                   ,
      NTA_ADATE             timestamp         not null,
      NTA_EUID              integer                   ,
      NTA_EDATE             timestamp         not null,
      primary key (NSN_SID)
);
