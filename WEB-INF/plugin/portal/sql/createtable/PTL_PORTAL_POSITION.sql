create table PTL_PORTAL_POSITION
(
  PTL_SID           integer           not null,
  PTP_ITEMID        varchar(17)       not null,
  PLY_POSITION      smallint          not null,
  PTP_SORT          integer           not null,
  PTP_VIEW          smallint          not null,
  PTP_TYPE          smallint          not null,
  PLT_SID           integer,
  PCT_PID           varchar(10),
  MSC_ID            varchar(150),
  PTP_PARAMKBN      smallint          not null,
  primary key (PTL_SID, PTP_ITEMID)
);