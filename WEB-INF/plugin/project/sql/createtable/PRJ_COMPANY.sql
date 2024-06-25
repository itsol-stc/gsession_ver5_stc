create table PRJ_COMPANY
(
  PRJ_SID         integer         not null,
  ACO_SID         integer         not null,
  ABA_SID         integer                  ,
  PRC_AUID        integer         not null,
  PRC_ADATE       timestamp       not null,
  PRC_EUID        integer         not null,
  PRC_EDATE       timestamp       not null,
  primary key (PRJ_SID, ACO_SID, ABA_SID)
);