insert into NTP_SHOHIN_CATEGORY
 (
  NSC_SID,
  NSC_NAME,
  NSC_BIKO,
  NSC_SORT,
  NSC_AUID,
  NSC_ADATE,
  NSC_EUID,
  NSC_EDATE
 )
 values (1, '未設定', null, 1, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP);





insert  into
  CMN_SAIBAN(
    SBN_SID,
    SBN_SID_SUB,
    SBN_NUMBER,
    SBN_STRING,
    SBN_AID,
    SBN_ADATE,
    SBN_EID,
    SBN_EDATE
  )
   values
  (
    'nippou',
    'shohincategory',
    1,
    'shohincategory',
    0,
    current_timestamp,
    0,
    current_timestamp
  );