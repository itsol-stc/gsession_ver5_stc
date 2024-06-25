create table NTP_PRI_TARGET
(
      NTG_SID               integer           not null,
      USR_SID               integer           not null,
      NPG_YEAR              integer           not null,
      NPG_DATE              timestamp         not null,
      NPG_MONTH             integer           not null,
      NPG_TARGET            bigint            not null,
      NPG_RECORD            bigint            not null,
      NPG_AUID              integer                   ,
      NPG_ADATE             timestamp                 ,
      NPG_EUID              integer                   ,
      NPG_EDATE             timestamp                 ,
      primary key (NTG_SID, USR_SID, NPG_YEAR, NPG_MONTH)
);
