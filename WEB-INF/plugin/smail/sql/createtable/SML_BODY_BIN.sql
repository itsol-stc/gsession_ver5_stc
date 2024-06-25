create table SML_BODY_BIN(
   SML_SID            integer        not null,
   SBB_SID            integer        not null,
   BIN_SID            bigint         not null,
   primary key (SML_SID,  SBB_SID)
);