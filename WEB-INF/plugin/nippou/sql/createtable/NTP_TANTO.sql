create table NTP_TANTO
(
      NTA_SID               integer           not null,
      NIP_SID               integer           not null,
      ADR_SID               integer           not null,
      NTA_AUID              integer                   ,
      NTA_ADATE             timestamp         not null,
      NTA_EUID              integer                   ,
      NTA_EDATE             timestamp         not null,
      primary key (NTA_SID)
);
