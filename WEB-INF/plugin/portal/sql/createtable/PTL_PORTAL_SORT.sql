create table PTL_PORTAL_SORT
(
  PTL_SID          integer           not null,
  PTS_KBN          smallint          not null,
  USR_SID          integer           not null,
  PTS_SORT         integer           not null,
  primary key (PTL_SID, PTS_KBN, USR_SID)
);