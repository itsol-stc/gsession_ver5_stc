 create
   table
     TCD_TIMEZONE_PRI(
       USR_SID integer not null,
       TTI_SID integer not null,
       TTP_DEFAULT integer not null,
       TTP_AUID integer not null,
       TTP_ADATE timestamp not null,
       primary key (USR_SID, TTI_SID)
     );