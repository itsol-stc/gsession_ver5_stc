create table SML_LABEL_SORT
(
  SAC_SID         integer      not null,
  USR_SID         integer      not null,
  SLB_SID         integer      not null,
  SLS_SORTKEY     integer      not null,
  SLS_ORDER       integer      not null,
  primary key (SAC_SID, USR_SID, SLB_SID)
);