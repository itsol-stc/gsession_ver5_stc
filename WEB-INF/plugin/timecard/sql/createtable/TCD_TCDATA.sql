  create
  table
    TCD_TCDATA (
      USR_SID integer not null
      ,TCD_DATE date not null
      ,TCD_INTIME time
      ,TCD_OUTTIME time
      ,TCD_STRIKE_INTIME time
      ,TCD_STRIKE_OUTTIME time
      ,TCD_BIKO varchar (100)
      ,TCD_STATUS integer not null
      ,THI_SID integer not null
      ,TCD_HOLOTHER varchar (10)
      ,TCD_HOLCNT decimal(6, 3)
      ,TCD_CHKKBN integer not null
      ,TCD_SOUKBN integer not null
      ,TCD_LOCK_FLG integer not null
      ,TCD_AUID integer not null
      ,TCD_ADATE timestamp not null
      ,TCD_EUID integer not null
      ,TCD_EDATE timestamp not null
      ,TTI_SID integer not null
      ,primary key (
        USR_SID
        ,TCD_DATE
      )
    );