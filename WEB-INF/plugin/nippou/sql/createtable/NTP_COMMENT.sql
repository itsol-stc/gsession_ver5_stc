create table NTP_COMMENT
(
      NPC_SID               integer           not null,
      NIP_SID               integer           not null,
      USR_SID               integer           not null,
      NPC_COMMENT           varchar(1000)     not null,
      NPC_VIEW_KBN          integer           not null,
      NPC_AUID              integer                   ,
      NPC_ADATE             timestamp         not null,
      NPC_EUID              integer                   ,
      NPC_EDATE             timestamp         not null,
      primary key (NPC_SID)
);
