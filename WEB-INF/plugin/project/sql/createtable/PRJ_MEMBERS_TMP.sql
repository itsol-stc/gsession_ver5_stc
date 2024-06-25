 create table PRJ_MEMBERS_TMP
 (
   PRT_SID           integer         not null,
   USR_SID           integer         not null,
   PMT_EMPLOYEE_KBN  integer         not null,
   PMT_ADMIN_KBN     integer         not null,
   PMT_AUID          integer         not null,
   PMT_ADATE         timestamp       not null,
   PMT_EUID          integer         not null,
   PMT_EDATE         timestamp       not null,
   PMT_MEM_KEY       varchar(20),
   primary key (PRT_SID, USR_SID, PMT_EMPLOYEE_KBN)
 );
