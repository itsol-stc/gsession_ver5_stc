create table RSS_DATA (
  RSS_SID         integer not null,
  USR_SID         integer not null,
  RSD_TITLE       varchar(150) not null,
  RSD_URL_FEED    varchar(6000) not null,
  RSD_URL         varchar(6000) not null,
  RSD_VIEW        integer not null,
  RSD_MAIN_VIEW   integer not null,
  RSD_FEED_COUNT  integer not null,
  RSD_AUTH          integer not null,
  RSD_AUTH_ID       varchar(20),
  RSD_AUTH_PSWD     varchar(44),
  RSD_AUID        integer not null,
  RSD_ADATE       timestamp not null,
  RSD_EUID        integer not null,
  RSD_EDATE       timestamp not null,
  primary key (RSS_SID, USR_SID)
);