 create table SML_SMEIS
 (
     SAC_SID        integer         not null,
     SMS_SID        integer         not null,
     SMS_SDATE      timestamp,
     SMS_TITLE      varchar(100),
     SMS_MARK       integer,
     SMS_BODY       text,
     SMS_BODY_PLAIN text,
     SMS_JKBN       integer,
     SMS_SIZE       bigint          not null,
     SMS_TYPE       integer         not null,
     SMS_AUID       integer         not null,
     SMS_ADATE      timestamp       not null,
     SMS_EUID       integer         not null,
     SMS_EDATE      timestamp       not null,
     primary key (SMS_SID)
 ) ;