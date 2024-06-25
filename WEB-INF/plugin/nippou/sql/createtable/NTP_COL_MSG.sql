create table NTP_COL_MSG
(
      NCM_ID                integer           not null,
      NCM_MSG               varchar(50)               ,
      NCM_AUID              integer                   ,
      NCM_ADATE             timestamp         not null,
      NCM_EUID              integer                   ,
      NCM_EDATE             timestamp         not null,
      primary key (NCM_ID)
);
 