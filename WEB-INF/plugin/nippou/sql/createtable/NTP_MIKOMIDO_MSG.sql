create table NTP_MIKOMIDO_MSG
(
      NMM_ID                integer           not null,
      NMM_MSG               varchar(1000)             ,
      NMM_AUID              integer                   ,
      NMM_ADATE             timestamp         not null,
      NMM_EUID              integer                   ,
      NMM_EDATE             timestamp         not null,
      primary key (NMM_ID)
);
