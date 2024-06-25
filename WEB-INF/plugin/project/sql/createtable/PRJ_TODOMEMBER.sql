create table PRJ_TODOMEMBER
(
        PRJ_SID          integer    not null,
        PTD_SID          integer    not null,
        USR_SID          integer    not null,
        PTM_EMPLOYEE_KBN integer    not null,
        PTM_AUID         integer    not null,
        PTM_ADATE        timestamp  not null,
        PTM_EUID         integer    not null,
        PTM_EDATE        timestamp  not null,
        primary key (PRJ_SID, PTD_SID, USR_SID, PTM_EMPLOYEE_KBN)
) ;