create table ANP_CMN_CONF
(
        APC_URL_BASE      varchar(600)    not null,
        APC_URL_KBN     integer         not null,
        APC_ADDRESS       varchar(768)    not null,
        APC_SEND_HOST     varchar(300)            ,
        APC_SEND_PORT     integer         not null,
        APC_SEND_USER     varchar(300)            ,
        APC_SEND_PASS     varchar(300)            ,
        APC_SEND_SSL      integer         not null,
        APC_SMTP_AUTH     integer         not null,
        APC_AUID          integer         not null,
        APC_ADATE         timestamp       not null,
        APC_EUID          integer         not null,
        APC_EDATE         timestamp       not null,
        APC_AUTH_TYPE     integer       not null,
        COT_SID           integer       not null
);