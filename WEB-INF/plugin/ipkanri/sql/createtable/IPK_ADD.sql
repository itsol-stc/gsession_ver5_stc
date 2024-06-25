create table IPK_ADD
(
      IAD_SID        integer not null,
      INT_SID        integer not null,
      IAD_NAME       varchar (50) not null,
      IAD_IPAD       decimal not null,
      IAD_USED_KBN   integer not null,
      IAD_MSG        varchar (1000),
      IAD_PRTMNG_NUM varchar (50),
      IAD_CPU        integer not null,
      IAD_MEMORY     integer not null,
      IAD_HD         integer not null,     
      IAD_AUID       integer not null,
      IAD_ADATE      timestamp not null,
      IAD_EUID       integer not null,
      IAD_EDATE      timestamp not null,
      primary key (IAD_SID)
) ;