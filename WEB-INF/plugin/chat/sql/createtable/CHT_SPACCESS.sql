create table CHT_SPACCESS
(
  CHS_SID         integer        not null,
  CHS_NAME      varchar(50)    not null,
  CHS_BIKO      varchar(1000),
  CHS_AUID         integer        not null,
  CHS_ADATE         timestamp         not null,
  CHS_EUID         integer        not null,
  CHS_EDATE         timestamp         not null,
  primary key      (CHS_SID)
);