create table NTP_SML_MEMBER (
   NSM_USR_SID integer not null,
   GRP_SID     integer not null,
   USR_SID     integer not null,
   NSM_GRP     integer not null default 0,
   primary key (NSM_USR_SID,GRP_SID,USR_SID,NSM_GRP)
 );
