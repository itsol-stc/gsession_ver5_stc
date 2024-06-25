create table NTP_NI_KATUDO
(
      NKD_SID      integer           not null,
      NIP_SID      integer           not null,
      NAN_SID      integer           not null,
      ACO_SID      integer           not null,
      ABA_SID      integer           not null,
      NKD_ACEDATE  timestamp         not null,
      NKD_ACDATE   timestamp,
      NKD_AUID     integer           not null,
      NKD_ADATE    timestamp         not null,
      NKD_EUID     integer           not null,
      NKD_EDATE    timestamp         not null,
      primary key (NKD_SID)
);
