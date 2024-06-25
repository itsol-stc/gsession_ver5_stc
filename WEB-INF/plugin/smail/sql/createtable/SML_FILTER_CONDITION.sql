create table SML_FILTER_CONDITION
(
  SFT_SID         integer      not null,
  SFC_NUM         integer      not null,
  SFC_TYPE        integer      not null,
  SFC_EXPRESSION  integer      not null,
  SFC_TEXT        varchar(256) not null,
  primary key(SFT_SID, SFC_NUM)
);