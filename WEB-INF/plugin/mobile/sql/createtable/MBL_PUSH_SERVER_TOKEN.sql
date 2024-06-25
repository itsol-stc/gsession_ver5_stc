create table MBL_PUSH_SERVER_TOKEN
(
     PST_TOKEN         varchar(256)        not null,
     PST_LIMIT         timestamp          not null,
     primary key (PST_TOKEN)
) ;
