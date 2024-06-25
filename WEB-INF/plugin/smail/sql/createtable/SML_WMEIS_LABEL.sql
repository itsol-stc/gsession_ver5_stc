create table SML_WMEIS_LABEL
(
  SMW_SID      integer      not null,
  SLB_SID      integer      not null,
  SAC_SID      integer      not null,
  primary key(SMW_SID, SLB_SID)
);