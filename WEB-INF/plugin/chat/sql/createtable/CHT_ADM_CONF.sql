create table CHT_ADM_CONF
( 
  CAC_CHAT_FLG         integer not null,
  CAC_GROUP_FLG         integer not null,
  CAC_GROUP_KBN         integer not null,
  CAC_READ_FLG         integer not null,
  CAC_ATDEL_FLG         integer not null,
  CAC_ATDEL_Y         integer,
  CAC_ATDEL_M         integer,
  CAC_AUID         integer not null,
  CAC_ADATE         timestamp not null,
  CAC_EUID         integer not null,
  CAC_EDATE         timestamp not null
);