create table RSS_POSITION (
  RSS_SID       integer not null,
  USR_SID       integer not null,
  RSP_POSITION  integer not null,
  RSP_ORDER     integer not null,
  RSP_AUID      integer not null,
  RSP_ADATE     timestamp not null,
  RSP_EUID      integer not null,
  RSP_EDATE     timestamp not null,
  primary key (RSS_SID, USR_SID)
);