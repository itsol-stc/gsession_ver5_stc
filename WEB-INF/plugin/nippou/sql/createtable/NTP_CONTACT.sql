create table NTP_CONTACT
(
      NCN_SID               integer           not null,
      NCN_CODE              varchar(5)        not null,
      NCN_NAME              varchar(50)       not null,
      NCN_AUID              integer                   ,
      NCN_ADATE             timestamp                 ,
      NCN_EUID              integer                   ,
      NCN_EDATE             timestamp                 ,
      primary key (NCN_SID)
);
