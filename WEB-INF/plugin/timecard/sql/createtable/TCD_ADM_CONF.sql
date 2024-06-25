  create
  table
    TCD_ADM_CONF (
      TAC_INTERVAL     integer   not null
     ,TAC_KANSAN       integer   not null
     ,TAC_SIMEBI       integer   not null
     ,TAC_HOL_SUN      integer   not null
     ,TAC_HOL_MON      integer   not null
     ,TAC_HOL_TUE      integer   not null
     ,TAC_HOL_WED      integer   not null
     ,TAC_HOL_THU      integer   not null
     ,TAC_HOL_FRI      integer   not null
     ,TAC_HOL_SAT      integer   not null
     ,TAC_LOCK_FLG     integer   not null
     ,TAC_LOCK_STRIKE  integer   not null
     ,TAC_LOCK_BIKO    integer   not null
     ,TAC_LOCK_LATE    integer   not null
     ,TAC_LOCK_HOLIDAY integer   not null
     ,TAC_LOCK_TIMEZONE integer   not null
     ,TAC_DEF_TIMEZONE integer   not null
     ,TAC_AUID         integer   not null
     ,TAC_ADATE        timestamp not null
     ,TAC_EUID         integer   not null
     ,TAC_EDATE        timestamp not null
     ,TAC_WORKREPORT_KBN integer not null
     ,TAC_WORKREPORT_SID bigint
   );