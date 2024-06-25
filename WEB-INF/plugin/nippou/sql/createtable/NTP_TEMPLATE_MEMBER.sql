create table NTP_TEMPLATE_MEMBER (
   NTM_TMP_SID integer not null,
   GRP_SID     integer not null,
   USR_SID     integer not null,
   primary key (NTM_TMP_SID,GRP_SID,USR_SID)
 );
