 create table SML_WMEIS
 (
     SAC_SID         integer         not null,
     SMW_SID         integer         not null,
     SMW_TITLE       varchar(100),
     SMW_MARK        integer,
     SMW_BODY        text,
     SMW_BODY_PLAIN  text,
     SMW_JKBN        integer,
     SMW_SIZE        bigint          not null,
     SMW_TYPE        integer         not null,
     SMW_AUID        integer         not null,
     SMW_ADATE       timestamp       not null,
     SMW_EUID        integer         not null,
     SMW_EDATE       timestamp       not null,
     SMW_ORIGIN      integer,
     SMW_EDIT_KBN    integer,
     primary key (SMW_SID)
 ) ;