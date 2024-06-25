create table SML_FILTER_SORT
(
  SAC_SID         integer      not null,
  SFT_SID         integer      not null,
  SFS_SORT        bigint       not null,
  primary key (SAC_SID, SFT_SID)
);