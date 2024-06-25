create table SCH_INIT_PUB
(
    USR_SID        integer         not null,
    SIP_TYPE       integer         not null,
    SIP_PSID       integer         not null,
    primary key (USR_SID, SIP_TYPE, SIP_PSID)
);