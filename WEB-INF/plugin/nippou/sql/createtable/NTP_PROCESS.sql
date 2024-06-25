create table NTP_PROCESS
(
      NGP_SID               integer           not null,
      NGY_SID               integer           not null,
      NGP_CODE              varchar(8)        not null,
      NGP_NAME              varchar(50)       not null,
      NGP_ACCOUNT           varchar(1000)             ,
      NGP_SORT              integer                   ,
      NGP_AUID              integer                   ,
      NGP_ADATE             timestamp                 ,
      NGP_EUID              integer                   ,
      NGP_EDATE             timestamp                 ,
      primary key (NGP_SID)
);