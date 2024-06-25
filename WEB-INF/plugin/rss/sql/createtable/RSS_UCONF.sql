create table RSS_UCONF
(
    USR_SID             integer      not null,
    RUC_NEW_CNT         integer      not null,
    RUC_AUID            integer      not null,
    RUC_ADATE           timestamp    not null,
    RUC_EUID            integer      not null,
    RUC_EDATE           timestamp    not null,
    primary key (
       USR_SID
    ) 
);