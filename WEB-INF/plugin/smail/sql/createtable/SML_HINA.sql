 create table SML_HINA
 (
     SAC_SID      integer         not null,
     SHN_SID      integer         not null,
     SHN_HNAME    varchar(50),
     SHN_TITLE    varchar(50),
     SHN_MARK     integer,
     SHN_BODY     varchar(5000),
     SHN_JKBN     integer,
     SHN_CKBN     integer,
     SHN_AUID     integer         not null,
     SHN_ADATE    timestamp       not null,
     SHN_EUID     integer         not null,
     SHN_EDATE    timestamp       not null,
     primary key (SHN_SID)
 ) ;