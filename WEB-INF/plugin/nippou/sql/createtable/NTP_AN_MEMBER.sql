create table NTP_AN_MEMBER
(
      NAN_SID          integer           not null,
      USR_SID          integer           not null,
      NAM_AUID         integer                   ,
      NAM_ADATE        timestamp         not null,
      NAM_EUID         integer                   ,
      NAM_EDATE        timestamp         not null,
      primary key (NAN_SID, USR_SID)
);
