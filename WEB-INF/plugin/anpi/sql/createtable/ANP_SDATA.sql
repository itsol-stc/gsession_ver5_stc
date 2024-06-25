create table ANP_SDATA
(
        APH_SID           integer         not null,
        APS_TYPE           integer         not null,
        USR_SID           integer         not null,
        GRP_SID           integer         not null,
        primary key (APH_SID, APS_TYPE, USR_SID, GRP_SID)
);