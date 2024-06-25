 create table PRJ_TODOCATEGORY_TMP
 (
   PRT_SID           integer         not null,
   PCT_CATEGORY_SID  integer         not null,
   PCT_SORT          integer         not null,
   PCT_NAME          varchar(20)     not null,
   PCT_AUID          integer         not null,
   PCT_ADATE         timestamp       not null,
   PCT_EUID          integer         not null,
   PCT_EDATE         timestamp       not null,
   primary key (PRT_SID, PCT_CATEGORY_SID)
 );
