create table SCH_EXDATA_PUB
(
    SCE_SID        integer         not null,
    SEP_TYPE       integer         not null,
    SEP_PSID       integer         not null,
    primary key (SCE_SID, SEP_TYPE, SEP_PSID)
);