create table NTP_AN_MEMBER_HISTORY
(
      NAH_SID          integer           not null,
      NAN_SID          integer           not null,
      USR_SID          integer           not null,
      NAM_AUID         integer                   ,
      NAM_ADATE        timestamp         not null,
      NAM_EUID         integer                   ,
      NAM_EDATE        timestamp         not null,
      primary key (NAH_SID, NAN_SID, USR_SID)
);