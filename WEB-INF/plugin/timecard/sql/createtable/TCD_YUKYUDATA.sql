 create
 table
   TCD_YUKYUDATA (
     USR_SID integer not null,
     TCY_NENDO integer not null,
     TCY_YUKYU decimal(6, 3) not null,
     TCY_AUID integer not null,
     TCY_ADATE timestamp not null,
     TCY_EUID integer not null,
     TCY_EDATE timestamp not null,
     primary key (USR_SID, TCY_NENDO)
   );