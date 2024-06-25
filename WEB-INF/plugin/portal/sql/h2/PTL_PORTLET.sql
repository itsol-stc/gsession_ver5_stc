create table PTL_PORTLET
(
  PLT_SID          integer           not null,
  PLT_NAME         varchar(100)      not null,
  PLT_CONTENT      CLOB              not null,
  PLT_CONTENT_TYPE smallint          not null,
  PLT_CONTENT_URL  varchar(1000),
  PLT_DESCRIPTION  varchar(1000),
  PLC_SID          integer           not null,
  PLT_BORDER       smallint          not null,
  PLT_AUID         integer           not null,
  PLT_ADATE        timestamp        not null,
  PLT_EUID         integer           not null,
  PLT_EDATE        timestamp        not null,
  primary key (PLT_SID)
);
