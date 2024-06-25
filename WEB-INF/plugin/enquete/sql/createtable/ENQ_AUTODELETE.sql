create table ENQ_AUTODELETE
(
        EAD_SEND_KBN        integer         not null,
        EAD_SEND_YEAR       integer         not null,
        EAD_SEND_MONTH      integer         not null,
        EAD_DRAFT_KBN       integer         not null,
        EAD_DRAFT_YEAR       integer        not null,
        EAD_DRAFT_MONTH     integer         not null,
        EAD_AUID            integer         not null,
        EAD_ADATE           timestamp       not null,
        EAD_EUID            integer         not null,
        EAD_EDATE           timestamp       not null
);