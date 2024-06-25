create table CIR_VIEW_LABEL
(
  CIF_SID                integer       not null,
  CAC_SID                integer       not null,
  CLA_SID                integer       not null,
  primary key(CIF_SID,CAC_SID,CLA_SID)
);