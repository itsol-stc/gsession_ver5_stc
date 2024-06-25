create table ZAI_PRI_CONF
(
    USR_SID          integer         not null,
    ZIF_SID          integer         not null,
    ZPC_RELOAD       integer         not null,
    ZPC_AID          integer         not null,
    ZPC_ADATE        timestamp       not null,
    ZPC_EID          integer         not null,
    ZPC_EDATE        timestamp       not null,
    ZPC_SORT_KEY1    integer         not null,
    ZPC_SORT_ORDER1  integer         not null,
    ZPC_SORT_KEY2    integer         not null,
    ZPC_SORT_ORDER2  integer         not null,
    primary key (USR_SID)
);