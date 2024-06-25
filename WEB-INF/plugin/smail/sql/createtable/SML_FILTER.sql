create table SML_FILTER
(
  SFT_SID               integer not null,
  USR_SID               integer not null,
  SFT_NAME              varchar(100) not null,
  SFT_TYPE              integer not null,
  SAC_SID               integer,
  SFT_TEMPFILE          integer not null,
  SFT_ACTION_LABEL      integer not null,
  SLB_SID               integer,
  SFT_ACTION_READ       integer not null,
  SFT_ACTION_DUST       integer not null,
  SFT_CONDITION         integer not null,
  primary key(SFT_SID)
);