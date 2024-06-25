create table ENQ_ADM_CONF
(
        EAC_KBN_TAISYO      integer                 ,
        EAC_MAIN_DSP_USE    integer                 ,
        EAC_MAIN_DSP        integer                 ,
        EAC_LIST_CNT_USE    integer                 ,
        EAC_LIST_CNT        integer                 ,
        EAC_AUID            integer         not null,
        EAC_ADATE           timestamp       not null,
        EAC_EUID            integer         not null,
        EAC_EDATE           timestamp       not null,
        EAC_FOLDER_USE      integer                 ,
        EAC_FOLDER_SELECT   integer                 ,
        EAC_CAN_ANSWER      integer                 ,
        EAC_DSPCNT_MAIN     integer     not null ,
        EAC_DSP_CHECKED     integer     not null
);