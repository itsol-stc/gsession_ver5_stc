create table PTL_PORTAL_POSITION_PARAM
(
  PTL_SID           integer           not null,
  PTP_ITEMID        varchar(17)       not null,
  PPM_PARAM_NO      integer          not null,
  PPM_PARAM_NAME    varchar(50)       not null,
  PPM_PARAM_VALUE   varchar(1000)       not null,
  primary key (PTL_SID, PTP_ITEMID, PPM_PARAM_NO)
);
