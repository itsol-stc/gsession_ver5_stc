create table CHT_PRI_CONF
(
  CPC_PRI_UID         integer not null,
  CPC_PUSH_FLG         integer not null,
  CPC_PUSH_TIME         integer not null,
  CPC_DEF_FLG         integer not null,
  CPC_CHAT_KBN         integer not null,
  CPC_SEL_SID         integer not null,
  CPC_ENTER_FLG         integer not null,
  CPC_AUID         integer not null,
  CPC_ADATE         timestamp not null,
  CPC_EUID         integer not null,
  CPC_EDATE         timestamp not null,
  CPC_SEL_TAB      integer not null,
  primary key   (CPC_PRI_UID)
);