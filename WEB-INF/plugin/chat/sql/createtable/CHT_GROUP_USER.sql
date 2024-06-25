create table CHT_GROUP_USER
(
  CGI_SID         integer   not null,
  CGU_KBN   integer   not null,
  CGU_SELECT_SID   integer   not null,
  CGU_ADMIN_FLG   integer   not null,
  CGU_AUID        integer   not null,
  CGU_ADATE       timestamp not null,
  CGU_EUID        integer   not null,
  CGU_EDATE       timestamp not null,
  primary key (CGI_SID, CGU_KBN, CGU_SELECT_SID)
) ;