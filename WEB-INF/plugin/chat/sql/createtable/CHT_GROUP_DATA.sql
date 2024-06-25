create table CHT_GROUP_DATA
(
  CGD_SID       bigint      not null,
  CGI_SID       integer     not null,
  CGD_TEXT      VARCHAR(3000),
  BIN_SID       bigint,
  CGD_SEND_UID  integer     not null,
  CGD_STATE_KBN integer     not null,
  CGD_AUID      integer     not null,
  CGD_ADATE     timestamp   not null,
  CGD_EUID      integer     not null,
  CGD_EDATE     timestamp   not null,
  primary key   (CGD_SID)
);