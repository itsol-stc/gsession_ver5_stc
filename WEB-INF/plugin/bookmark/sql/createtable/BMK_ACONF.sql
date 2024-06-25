create table BMK_ACONF
(
        BAC_PUB_EDIT    integer not null,
        BAC_GRP_EDIT    integer not null,
        BAC_LIMIT       integer not null default 1,
        BAC_AUID        integer not null,
        BAC_ADATE       timestamp   not null,
        BAC_EUID        integer not null,
        BAC_EDATE       timestamp   not null
) ;