create table SML_ACCOUNT
(
  SAC_SID                integer       not null,
  SAC_TYPE               integer       not null,
  USR_SID                integer,
  SAC_NAME               varchar(100)  not null,
  SAC_BIKO               varchar(1000),
  SAC_SEND_MAILTYPE      integer       not null,
  SAC_JKBN               integer       not null,
  SAC_THEME              integer       not null,
  SAC_QUOTES             integer       not null,
  primary key(SAC_SID)
);