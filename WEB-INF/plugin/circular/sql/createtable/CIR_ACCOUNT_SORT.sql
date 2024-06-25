create table CIR_ACCOUNT_SORT
(
  CAC_SID         integer      not null,
  USR_SID         integer      not null,
  CAS_SORT        BIGINT       not null,
  primary key (CAC_SID, USR_SID)
);