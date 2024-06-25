create table ANP_HDATA
(
        APH_SID         integer         not null,
        APH_SUBJECT     varchar(60)             ,
        APH_TEXT1       varchar(3000)            ,
        APH_TEXT2       varchar(3000)            ,
        APH_HUID        integer         not null,
        APH_HDATE       timestamp       not null,
        APH_SUID        integer                 ,
        APH_SDATE       timestamp               ,
        APH_SCOUNT      integer         not null,
        APH_END_FLG     integer         not null,
        APH_EUID        integer                 ,
        APH_EDATE       timestamp               ,
        APH_KNREN_FLG   integer         not null,
        APH_VIEW_MAIN   integer         not null,
        primary key (APH_SID)
);