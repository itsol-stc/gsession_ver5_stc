create table RSS_POSITION_MAIN (
  RSS_SID       integer not null,
  USR_SID       integer not null,
  RPM_POSITION  integer not null,
  RPM_ORDER     integer not null,
  RPM_AUID      integer not null,
  RPM_ADATE     timestamp not null,
  RPM_EUID      integer not null,
  RPM_EDATE     timestamp not null,
  primary key (RSS_SID, USR_SID)
);