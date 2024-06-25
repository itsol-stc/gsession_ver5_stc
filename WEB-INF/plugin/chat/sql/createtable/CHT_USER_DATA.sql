create table CHT_USER_DATA
(
  CUD_SID		bigint not null,
  CUP_SID		integer not null,
  CUD_TEXT		VARCHAR(3000),
  BIN_SID		bigint,
  CUD_SEND_UID		integer not null,
  CUD_STATE_KBN		integer not null,
  CUD_AUID		integer not null,
  CUD_ADATE		timestamp not null,
  CUD_EUID		integer not null,
  CUD_EDATE		timestamp not null,
  primary key (CUD_SID)
);