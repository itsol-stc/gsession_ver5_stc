create table PTL_PORTAL_LAYOUT
(
  PTL_SID          integer           not null,
  PLY_POSITION     smallint          not null,
  PTS_VIEW         integer           not null,
  PLY_AUID         integer           not null,
  PLY_ADATE        timestamp         not null,
  PLY_EUID         integer           not null,
  PLY_EDATE        timestamp         not null,
  primary key (PTL_SID, PLY_POSITION)
);