create table NTP_DATA
(
      NIP_SID               integer           not null,
      USR_SID               integer           not null,
      NIP_DATE              timestamp         not null,
      NIP_FR_TIME           timestamp         not null,
      NIP_TO_TIME           timestamp         not null,
      NIP_KADO_HH           integer           not null,
      NIP_KADO_MM           integer           not null,
      NIP_MGY_SID           integer           not null,
      NAN_SID               integer           not null,
      ACO_SID               integer                   ,
      ABA_SID               integer                   ,
      NIP_TITLE             varchar(100)       not null,
      NIP_TITLE_CLO         integer                   ,
      MPR_SID               integer           not null,
      MKB_SID               integer           not null,
      MKH_SID               integer           not null,
      NIP_TIEUP_SID         integer                   ,
      NIP_KEIZOKU           integer           not null,
      NIP_ACTEND            timestamp                 ,
      NIP_DETAIL            varchar(1000)     not null,
      NIP_ACTION_DATE       timestamp                 ,
      NIP_ACTION            varchar(1000)             ,
      NIP_ACTDATE_KBN       integer                   ,
      NIP_ASSIGN            varchar(1000)             ,
      NIP_KINGAKU           integer                   ,
      NIP_MIKOMI            integer                   ,
      NIP_SYOKAN            varchar(1000)             ,
      NIP_PUBLIC            integer                   ,
      NIP_EDIT              integer                   ,
      NEX_SID               integer                   ,
      NIP_AUID              integer                   ,
      NIP_ADATE             timestamp         not null,
      NIP_EUID              integer                   ,
      NIP_EDATE             timestamp         not null,
      primary key (NIP_SID)
);
