create table CHT_LOG_COUNT_SUM
(
  CLS_SEND_CNT         integer not null,
  CLS_SEND_UCNT         integer not null,
  CLS_SEND_GCNT         integer not null,
  CLS_USER_CNT         integer not null,
  CLS_DATE         date not null,
  CLS_YEAR_MONTH         integer not null,
  CLS_EDATE        timestamp not null
);
