create table ENQ_PRI_CONF
(
        USR_SID           integer         not null,
        EPC_MAIN_DSP      integer                 ,
        EPC_LIST_CNT      integer                 ,
        EPC_AUID          integer         not null,
        EPC_ADATE         timestamp       not null,
        EPC_EUID          integer         not null,
        EPC_EDATE         timestamp       not null,
        EPC_FOLDER_SELECT integer                 ,
        EPC_CAN_ANSWER    integer                 ,
        EPC_DSPCNT_MAIN   integer     not null  ,
        EPC_DSP_CHECKED   integer     not null  ,
        primary key (USR_SID)
);