create table ZAI_ADM_CONF
(
    ZAC_NAISEN_KBN   integer         not null default 1,
    ZAC_AID          integer         not null,
    ZAC_ADATE        timestamp      not null,
    ZAC_EID          integer         not null,
    ZAC_EDATE        timestamp      not null,
    ZAC_SORT_KBN     integer         not null default 0,
    ZAC_SORT_KEY1    integer         not null default 1,
    ZAC_SORT_ORDER1  integer         not null default 0,
    ZAC_SORT_KEY2    integer         not null default 1,
    ZAC_SORT_ORDER2  integer         not null default 0
);