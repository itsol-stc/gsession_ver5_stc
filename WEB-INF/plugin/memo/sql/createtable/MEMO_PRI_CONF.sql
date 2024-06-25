create table MEMO_PRI_CONF
(
  USR_SID       integer    not null,
  MPC_DSP_MODE  integer    not null,
  MPC_AUID      integer    not null,
  MPC_ADATE     timestamp  not null,
  MPC_EUID      integer    not null,
  MPC_EDATE     timestamp  not null,
  primary key (USR_SID)
);