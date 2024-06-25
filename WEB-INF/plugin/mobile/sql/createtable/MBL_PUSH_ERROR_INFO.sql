create table MBL_PUSH_ERROR_INFO
(
     PEI_ECODE        varchar(75)        not null,
     PEI_MESSAGE      varchar(150)       not null,
     PEI_AUID         integer            not null,
     PEI_ADATE        timestamp          not null
) ;
