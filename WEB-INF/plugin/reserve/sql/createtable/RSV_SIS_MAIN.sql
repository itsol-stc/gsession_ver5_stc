create table RSV_SIS_MAIN
(
  USR_SID       integer not null,
  RSG_SID       integer not null,
  RSM_DSP_KBN   integer not null,
  RSM_AUID      integer not null,
  RSM_ADATE     timestamp not null,
  RSM_EUID      integer not null,
  RSM_EDATE     timestamp not null,
  primary key   (USR_SID, RSG_SID)
);