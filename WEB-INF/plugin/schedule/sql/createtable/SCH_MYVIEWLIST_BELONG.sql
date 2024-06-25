create table SCH_MYVIEWLIST_BELONG
(
    SMY_SID        integer         not null,
    USR_SID        integer         not null,
    GRP_SID        integer         not null,
    SMV_ORDER      integer         not null,
    primary key (SMY_SID, USR_SID, GRP_SID, SMV_ORDER)
);