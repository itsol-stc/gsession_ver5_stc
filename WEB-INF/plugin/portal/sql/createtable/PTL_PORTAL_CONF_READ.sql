create table PTL_PORTAL_CONF_READ
(
  PTL_SID         integer           not null,
  USR_SID         integer           not null,
  GRP_SID         integer           not null,
  primary key (PTL_SID, USR_SID, GRP_SID)
);