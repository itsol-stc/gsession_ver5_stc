
insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (1, 1, '1','アプローチ','',1,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (2, 1, '2','ヒアリング','',2,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (3, 1, '3','企画提案','',3,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (4, 1, '4','プレゼン','',4,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (5, 1, '5','クロージング','',5,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (6, 1, '6','アフターフォロー','',6,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (7, 2, '7','調査','',1,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (8, 2, '8','資料作成','',2,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (9, 2, '9','製造・開発','',3,0,current_timestamp,0,current_timestamp);

insert into NTP_PROCESS(
   NGP_SID, NGY_SID, NGP_CODE, NGP_NAME, NGP_ACCOUNT, NGP_SORT, NGP_AUID, NGP_ADATE, NGP_EUID, NGP_EDATE
 )
 values
 (10,2,'10','テスト','',4,0,current_timestamp,0,current_timestamp);

 insert into CMN_SAIBAN(
   SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING, SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE
 )
 values
 ('nippou','process',10,'process',0,current_timestamp,0,current_timestamp);