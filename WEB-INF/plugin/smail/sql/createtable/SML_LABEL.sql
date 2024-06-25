create table SML_LABEL
(
  SLB_SID   integer      not null,
  USR_SID   integer      not null,
  SLB_NAME  varchar(100) not null,
  SLB_TYPE  integer      not null,
  SAC_SID   integer      not null,
  SLB_ORDER integer      not null,
  primary key(SLB_SID)
);