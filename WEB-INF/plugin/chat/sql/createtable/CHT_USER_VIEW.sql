create table CHT_USER_VIEW
(
  CUP_SID integer not null,
  CUV_UID integer not null,
  CUD_SID bigint not null,
  CUV_VIEWCNT integer   not null,
  primary key (CUP_SID, CUV_UID)
);