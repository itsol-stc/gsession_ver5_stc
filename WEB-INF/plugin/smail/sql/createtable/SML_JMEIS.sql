 create table SML_JMEIS
 (
     SAC_SID      integer         not null,
     SMJ_SID      integer         not null,
     SMJ_OPKBN    integer,
     SMJ_OPDATE   timestamp,
     SMJ_JKBN     integer,
     SMJ_FWKBN    integer,
     SMJ_SENDKBN  integer,
     SMJ_AUID     integer         not null,
     SMJ_ADATE    timestamp       not null,
     SMJ_EUID     integer         not null,
     SMJ_EDATE    timestamp       not null,
     SMJ_RTN_KBN  integer         not null,
     SMJ_FW_KBN   integer         not null,
     primary key (SAC_SID, SMJ_SID)
 ) ;