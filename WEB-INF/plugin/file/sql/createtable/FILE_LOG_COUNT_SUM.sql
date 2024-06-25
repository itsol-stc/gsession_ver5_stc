 create table FILE_LOG_COUNT_SUM
 (
  FLS_KBN      integer   not null,
  FLS_DATE     date      not null,
  FLS_MONTH    integer   not null,
  FLS_CNT      bigint    not null,
  FLS_EDATE    timestamp not null
 );