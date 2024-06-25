create table BMK_UCONF
(
        USR_SID         integer not null,
        BUC_COUNT       integer not null,
        BUC_MAIN_MY     integer not null,
        BUC_MAIN_NEW    integer not null,
        BUC_NEW_CNT     integer not null,
        BUC_AUID        integer not null,
        BUC_ADATE       timestamp   not null,
        BUC_EUID        integer not null,
        BUC_EDATE       timestamp   not null,
        primary key (
                USR_SID
        ) 
) ;