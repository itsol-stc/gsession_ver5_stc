create table SML_JMEIS_LABEL
(
  SMJ_SID      integer      not null,
  SLB_SID      integer      not null,
  SAC_SID      integer      not null,
  primary key(SMJ_SID, SLB_SID)
);