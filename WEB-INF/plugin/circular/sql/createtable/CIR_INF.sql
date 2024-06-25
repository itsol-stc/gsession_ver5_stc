create table CIR_INF
(
         CIF_SID         integer        not null,
         CIF_TITLE       varchar(100),
         GRP_SID         integer,
         CIF_VALUE       text,
         CIF_AUID        integer        not null,
         CIF_ADATE       timestamp      not null,
         CIF_EUID        integer        not null,
         CIF_EDATE       timestamp      not null,
         CIF_EKBN        integer        not null,
         CIF_JKBN        integer        not null,
         CIF_SHOW        integer        not null,
         CIF_MEMO_FLG    integer        not null,
         CIF_MEMO_DATE   timestamp,
         CIF_EDIT_DATE   timestamp,
        primary key (CIF_SID)
);