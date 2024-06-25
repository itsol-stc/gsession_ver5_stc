create table PRJ_MEMBERS
(
        PRJ_SID         integer         not null,
        USR_SID         integer         not null,
        PRM_EMPLOYEE_KBN integer        not null,
        PRM_ADMIN_KBN   integer         not null,
        PRM_AUID        integer         not null,
        PRM_ADATE       timestamp       not null,
        PRM_EUID        integer         not null,
        PRM_EDATE       timestamp       not null,
        PRM_MEM_KEY     varchar(20),
        PRM_SORT        integer         not null,
        primary key (
                PRJ_SID,
                USR_SID,
                PRM_EMPLOYEE_KBN
        ) 
) ;
