
insert into NTP_KTHOUHOU(
   NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE
 )
 values
 (1,'1','訪問',0,current_timestamp,0,current_timestamp);

insert into NTP_KTHOUHOU(
   NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE
 )
 values
 (2,'2','来社',0,current_timestamp,0,current_timestamp);

insert into NTP_KTHOUHOU(
   NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE
 )
 values
 (3,'3','電話',0,current_timestamp,0,current_timestamp);

insert into NTP_KTHOUHOU(
   NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE
 )
 values
 (4,'4','FAX',0,current_timestamp,0,current_timestamp);

insert into NTP_KTHOUHOU(
   NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE
 )
 values
 (5,'5','メール',0,current_timestamp,0,current_timestamp);

insert into NTP_KTHOUHOU(
   NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE
 )
 values
 (6,'6','郵送',0,current_timestamp,0,current_timestamp);

insert into NTP_KTHOUHOU(
   NKH_SID, NKH_CODE, NKH_NAME, NKH_AUID, NKH_ADATE, NKH_EUID, NKH_EDATE
 )
 values
 (7,'7','社内作業',0,current_timestamp,0,current_timestamp);

 insert into CMN_SAIBAN(
   SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING, SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE
 )
 values
 ('nippou','kthouhou',7,'kthouhou',0,current_timestamp,0,current_timestamp);