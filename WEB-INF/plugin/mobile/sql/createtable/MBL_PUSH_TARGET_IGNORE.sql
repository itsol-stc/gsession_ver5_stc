create table MBL_PUSH_TARGET_IGNORE
(
    USR_SID             integer         not null,
    APP_ID              varchar(100)    not null,
    PLUGIN_ID           varchar(20)     not null,
    PUC_SUBKBN          varchar(20)     not null,
    PUC_SUBTARGET_SID   integer         not null,
    primary key (USR_SID, APP_ID, PLUGIN_ID, PUC_SUBKBN, PUC_SUBTARGET_SID)
) ;
