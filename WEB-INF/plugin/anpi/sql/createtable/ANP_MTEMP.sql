create table ANP_MTEMP
(
        APM_SID           integer         not null,
        APM_TITLE         varchar(60)     not null,
        APM_SUBJECT       varchar(60)             ,
        APM_TEXT1         varchar(3000)            ,
        APM_TEXT2         varchar(3000)            ,
        APM_AUID          integer         not null,
        APM_ADATE         timestamp       not null,
        APM_EUID          integer         not null,
        APM_EDATE         timestamp       not null,
        primary key (APM_SID)
);