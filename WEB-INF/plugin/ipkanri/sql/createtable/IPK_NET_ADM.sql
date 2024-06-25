create table IPK_NET_ADM
(
      INT_SID        integer not null,
      USR_SID        integer not null,
      INC_AUID       integer not null,
      INC_ADATE      timestamp not null,
      INC_EUID       integer not null,
      INC_EDATE      timestamp not null,
      primary key (INT_SID, USR_SID)
) ;