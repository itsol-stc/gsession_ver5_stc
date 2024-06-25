create table RSS_INFOM (
  RSS_SID           integer not null,
  RSM_URL_FEED      varchar(6000) not null,
  RSM_FEEDDATA      BLOB,
  RSM_UPDATE_TIME   timestamp not null,
  RSM_AUTH          integer not null,
  RSM_AUTH_ID       varchar(20),
  RSM_AUTH_PSWD     varchar(44),
  RSM_AUID          integer not null,
  RSM_ADATE         timestamp not null,
  RSM_EUID          integer not null,
  RSM_EDATE         timestamp not null,
  primary key (RSS_SID)
);