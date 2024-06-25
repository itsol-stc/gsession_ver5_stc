  create
  table
    TCD_PRI_CONF (
      USR_SID       integer   not null
     ,TPC_IN_HOUR   integer   not null
     ,TPC_IN_MIN    integer   not null
     ,TPC_OUT_HOUR  integer   not null
     ,TPC_OUT_MIN   integer   not null
     ,TPC_MAIN_DSP  integer   not null
     ,TPC_ZSK_STS   integer
     ,TPC_KINMU_OUT integer   not null
     ,primary key (
      USR_SID
      )
   );