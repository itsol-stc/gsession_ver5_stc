   create
   table
     TCD_TIMEZONE_INFO(
       TTI_SID integer not null,
       TTI_NAME varchar(100) not null,
       TTI_RYAKU varchar(5) not null,
       TTI_SORT integer not null,
       TTI_USE integer not null,
       TTI_HOLIDAY integer not null,
       TTI_AUID integer not null,
       TTI_ADATE timestamp not null,
       TTI_EUID integer not null,
       TTI_EDATE timestamp not null,
       primary key (TTI_SID)
     );