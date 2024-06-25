create table NTP_SHOHIN_CATEGORY
(
 NSC_SID     integer       not null,
 NSC_NAME    varchar(20)   not null,
 NSC_BIKO    varchar(300),
 NSC_SORT    integer       not null,
 NSC_AUID    integer       not null,
 NSC_ADATE   timestamp     not null,
 NSC_EUID    integer       not null,
 NSC_EDATE   timestamp     not null,
 primary key (NSC_SID)
);