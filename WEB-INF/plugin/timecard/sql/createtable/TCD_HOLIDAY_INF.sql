 create
 table
   TCD_HOLIDAY_INF (
     THI_SID integer not null,
     THI_NAME varchar(10) not null,
     THI_HOLTOTAL_KBN integer not null,
     THI_LIMIT integer not null,
     THI_ORDER integer not null,
     THI_CONTENT_KBN integer not null,
     THI_AUID integer not null,
     THI_ADATE timestamp not null,
     THI_EUID integer not null,
     THI_EDATE timestamp not null,
     primary key (THI_SID)
   );