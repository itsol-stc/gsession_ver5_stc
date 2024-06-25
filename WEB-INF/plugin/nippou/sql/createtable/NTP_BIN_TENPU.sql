create table NTP_BIN_TEMPU
(
      NBT_SID               integer           not null,
      NIP_SID               integer           not null,
      BIN_SID               integer           not null,
      NBT_AUID              integer                   ,
      NBT_ADATE             timestamp         not null,
      NBT_EUID              integer                   ,
      NBT_EDATE             timestamp         not null,
      primary key (NBT_SID)
);
