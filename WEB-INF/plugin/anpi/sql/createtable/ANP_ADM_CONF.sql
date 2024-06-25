create table ANP_ADM_CONF
(
        USR_SID           integer         not null,
        GRP_SID           integer         not null,
        APA_AUID          integer         not null,
        APA_ADATE         timestamp       not null,
        APA_EUID          integer         not null,
        APA_EDATE         timestamp       not null,
        primary key (USR_SID, GRP_SID)
);