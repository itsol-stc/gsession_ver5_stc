create table SML_ACCOUNT_SORT
(
  SAC_SID         integer      not null,
  USR_SID         integer      not null,
  SAS_SORT        BIGINT       not null,
  primary key (SAC_SID, USR_SID)
);