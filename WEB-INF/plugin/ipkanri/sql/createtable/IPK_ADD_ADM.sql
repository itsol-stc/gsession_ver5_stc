create table IPK_ADD_ADM
(
      IAD_SID        integer not null,
      USR_SID        integer not null,
      IAC_AUID       integer not null,
      IAC_ADATE      timestamp not null,
      IAC_EUID       integer not null,
      IAC_EDATE      timestamp not null,
      primary key (IAD_SID, USR_SID)
) ;