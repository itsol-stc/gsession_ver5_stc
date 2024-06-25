create table CIR_VIEW
(
         CIF_SID         integer      not null,
         CAC_SID         integer      not null,
         CVW_MEMO        varchar(1000),
         CVW_CONF        integer      not null,
         CVW_CONF_DATE   timestamp,
         CVW_DSP         integer      not null,
         CVW_AUID        integer      not null,
         CVW_ADATE       timestamp       not null,
         CVW_EUID        integer      not null,
         CVW_EDATE       timestamp       not null,
        primary key (CIF_SID, CAC_SID)
);