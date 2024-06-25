 create table SML_LOG_COUNT_SUM
 (
  SLS_KBN      integer   not null,
  SLS_SYS_KBN  integer   not null,
  SLS_CNT      bigint    not null,
  SLS_CNT_TO   bigint    not null,
  SLS_CNT_CC   bigint    not null,
  SLS_CNT_BCC  bigint    not null,
  SLS_DATE     date      not null,
  SLS_MONTH    integer   not null,
  SLS_EDATE    timestamp not null
 );