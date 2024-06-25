 create table API_TOKEN
   (
    APT_TOKEN varchar(256),
    USR_SID integer not null,
    APT_CLIENT integer not null,
    APT_CLIENT_ID varchar(256),
    APT_IP varchar(40),
    APT_JKBN integer not null,
    APT_LIMIT_DATE timestamp not null,
    APT_AUID integer not null,
    APT_ADATE timestamp not null,
    APT_EUID integer not null,
    APT_EDATE timestamp not null,
    primary key (APT_TOKEN)
   );
