create table IPK_SPECM
(
      ISM_SID        integer not null,
      ISM_KBN        integer not null,
      ISM_NAME       varchar (50) not null,
      ISM_LEVEL      integer not null,
      ISM_BIKO       varchar(1000),
      ISM_AUID       integer not null,
      ISM_ADATE      timestamp not null,
      ISM_EUID       integer not null,
      ISM_EDATE      timestamp not null,
      primary key (ISM_SID)
) ;