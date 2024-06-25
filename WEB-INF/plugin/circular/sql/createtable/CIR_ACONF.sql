create table CIR_ACONF(
    CAF_SMAIL_SEND_KBN      integer   not null,
    CAF_SMAIL_SEND      integer   not null,
    CAF_AUID      integer   not null,
    CAF_ADATE      timestamp not null,
    CAF_EUID        integer   not null,
    CAF_EDATE      timestamp not null,
    CAF_AREST_KBN      integer not null,
    CAF_SMAIL_SEND_MEMO      integer   not null,
    CAF_SMAIL_SEND_EDIT      integer   not null
);