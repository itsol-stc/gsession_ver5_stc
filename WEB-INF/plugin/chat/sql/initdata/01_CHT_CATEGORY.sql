 insert 
 into 
 CHT_CATEGORY(
   CHC_SID,
   CHC_NAME,
   CHC_SORT,
   CHC_AUID,
   CHC_ADATE,
   CHC_EUID,
   CHC_EDATE
 )
 values
 (
   -1,
   'カテゴリなし',
   -1,
   0,
   current_timestamp,
   0,
   current_timestamp
 );

 insert 
 into 
 CHT_CATEGORY(
   CHC_SID,
   CHC_NAME,
   CHC_SORT,
   CHC_AUID,
   CHC_ADATE,
   CHC_EUID,
   CHC_EDATE
 )
 values
 (
   0,
   '一般ユーザ',
   0,
   0,
   current_timestamp,
   0,
   current_timestamp
 );