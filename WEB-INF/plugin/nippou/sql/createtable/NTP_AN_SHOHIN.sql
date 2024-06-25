create table NTP_AN_SHOHIN
(
      NAN_SID          integer           not null,
      NHN_SID          integer           not null,
      NAS_AUID         integer                   ,
      NAS_ADATE        timestamp         not null,
      NAS_EUID         integer                   ,
      NAS_EDATE        timestamp         not null,
      primary key (NAN_SID, NHN_SID)
);
