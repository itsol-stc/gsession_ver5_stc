create table IPK_NET
(
    INT_SID integer not null,
    INT_NAME varchar (50) not null,
    INT_NETAD varchar (15) not null,
    INT_SABNET varchar (15) not null,
    INT_DSP integer not null,
    INT_MSG varchar (1000),
    INT_SORT integer not null,
    INT_AUID integer not null,
    INT_ADATE timestamp not null,
    INT_EUID integer not null,
    INT_EDATE timestamp not null,
    primary key (INT_SID)
);