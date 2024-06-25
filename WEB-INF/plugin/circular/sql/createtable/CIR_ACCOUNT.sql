create table CIR_ACCOUNT
(
  CAC_SID                integer       not null,
  CAC_TYPE               integer       not null,
  USR_SID                integer,
  CAC_NAME               varchar(100)  not null,
  CAC_BIKO               varchar(1000),
  CAC_JKBN               integer       not null,
  CAC_THEME              integer       not null,
  CAC_SML_NTF            integer       not null,
  CAC_MEMO_KBN           integer       not null,
  CAC_MEMO_DAY           integer       not null,
  CAC_KOU_KBN            integer       not null,
  CAC_INIT_KBN           integer       not null,
  CAC_SML_MEMO           integer       not null,
  CAC_SML_EDIT           integer       not null,
  primary key(CAC_SID)
);