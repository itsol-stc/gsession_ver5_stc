create table NTP_GYOMU
(
      NGY_SID               integer           not null,
      NGY_CODE              varchar(5)        not null,
      NGY_NAME              varchar(50)       not null,
      NGY_AUID              integer                   ,
      NGY_ADATE             timestamp                 ,
      NGY_EUID              integer                   ,
      NGY_EDATE             timestamp                 ,
      primary key (NGY_SID)
);
