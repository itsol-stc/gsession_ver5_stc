 create table SML_BAN_DEST_CONF
 (
     SBC_SID        integer         not null,
     SBC_NAME       varchar(50)     not null,
     SBC_BIKO       varchar(1000),
     SBC_AUID       integer         not null,
     SBC_ADATE      timestamp       not null,
     SBC_EUID       integer         not null,
     SBC_EDATE      timestamp       not null,
     primary key(SBC_SID)
 );