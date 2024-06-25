create table NTP_PRI_KAKUNIN
(
      USR_SID               integer           not null,
      NPK_DFT_USR           integer           not null, 
      NPK_AUID              integer                   ,
      NPK_ADATE             timestamp         not null,
      NPK_EUID              integer                   ,
      NPK_EDATE             timestamp         not null,
      primary key (USR_SID, NPK_DFT_USR)
);
 