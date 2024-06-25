create table CHT_GROUP_VIEW
(
  CGI_SID    integer   not null,
  CGV_UID    integer   not null,
  CGD_SID    bigint    not null,
  CGV_VIEWCNT integer   not null,
  primary key (CGI_SID, CGV_UID)
) ;