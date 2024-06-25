 create table CIR_LOG_COUNT_SUM
 (
  CLS_KBN      integer   not null,
  CLS_CNT      bigint    not null,
  CLS_CNT_SUM  bigint    not null,
  CLS_DATE     date      not null,
  CLS_MONTH    integer   not null,
  CLS_EDATE    timestamp not null
 );