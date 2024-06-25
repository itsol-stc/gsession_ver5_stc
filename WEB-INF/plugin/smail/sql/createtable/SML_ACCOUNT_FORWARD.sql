create table SML_ACCOUNT_FORWARD
(
        SAC_SID       integer      not null,
        USR_SID       integer      not null,
        SAF_MAILFW    integer      not null,
        SAF_SMAIL_OP  integer      not null,
        SAF_MAIL_DF   varchar(50),
        SAF_HURIWAKE  integer,
        SAF_ZMAIL1    varchar(50),
        SAF_ZMAIL2    varchar(50),
        SAF_ZMAIL3    varchar(50),
        SAF_AUID      integer      not null,
        SAF_ADATE     timestamp    not null,
        SAF_EUID      integer      not null,
        SAF_EDATE     timestamp    not null,
        primary key (SAC_SID, USR_SID)
) ;