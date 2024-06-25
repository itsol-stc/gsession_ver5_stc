create table CHT_GROUP_INF
(
  CGI_SID         integer      not null,
  CGI_ID          VARCHAR(20)  not null,
  CGI_NAME        VARCHAR(100) not null,
  CGI_CONTENT     VARCHAR(500),
  CGI_COMP_FLG    integer      not null,
  CGI_DEL_FLG     integer      not null,
  CHC_SID         integer              ,
  CGI_AUID        integer      not null,
  CGI_ADATE       timestamp    not null,
  CGI_EUID        integer      not null,
  CGI_EDATE       timestamp    not null,
  primary key (CGI_SID)
) ;