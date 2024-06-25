create table PTL_PORTLET_IMAGE
(
  PLT_SID         integer           not null,
  PLI_SID         bigint            not null,
  BIN_SID         bigint            not null,
  PLI_NAME        varchar(100)      not null,
  primary key (PLT_SID, PLI_SID)
);

