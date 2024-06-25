 create table SML_ASAK
 (
     SAC_SID      integer         not null,
     SMS_SID      integer         not null,
     SMJ_SENDKBN  integer         not null,
     SMS_AUID     integer         not null,
     SMS_ADATE    timestamp       not null,
     SMS_EUID     integer         not null,
     SMS_EDATE    timestamp       not null,
     primary key (SAC_SID, SMS_SID, SMJ_SENDKBN)
 ) ;