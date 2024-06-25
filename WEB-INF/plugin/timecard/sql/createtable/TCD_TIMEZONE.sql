  create
  table
    TCD_TIMEZONE (
      TTZ_SID    integer   not null
     ,TTZ_KBN    integer   not null
     ,TTZ_FRTIME time not null
     ,TTZ_TOTIME time not null
     ,TTI_SID    integer not null
     ,primary key (
      TTZ_SID
      )
   );