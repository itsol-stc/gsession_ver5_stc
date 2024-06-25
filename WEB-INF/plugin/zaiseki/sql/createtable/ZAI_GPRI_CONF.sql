create table ZAI_GPRI_CONF
(
    USR_SID           integer         not null,
    ZGC_GRP           integer         not null,
    ZGC_GKBN          integer         not null,
    ZGC_VIEW_KBN      integer         not null,
    ZGC_SORT_KEY1     integer         not null,
    ZGC_SORT_ORDER1   integer         not null,
    ZGC_SORT_KEY2     integer         not null,
    ZGC_SORT_ORDER2   integer         not null,
    ZGC_SCH_VIEW_DF   integer         not null,
    ZGC_AUID          integer         not null,
    ZGC_ADATE         timestamp       not null,
    ZGC_EUID          integer         not null,
    ZGC_EDATE         timestamp       not null,
    primary key (USR_SID)
);