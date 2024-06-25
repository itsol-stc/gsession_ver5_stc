create table SML_SMEIS_LABEL
(
  SMS_SID      integer      not null,
  SLB_SID      integer      not null,
  SAC_SID      integer      not null,
  primary key(SMS_SID, SLB_SID)
);