
insert into NTP_CONTACT(
   NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE
 )
 values
 (1,'1','既存',0,current_timestamp,0,current_timestamp);

insert into NTP_CONTACT(
   NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE
 )
 values
 (2,'2','紹介',0,current_timestamp,0,current_timestamp);

insert into NTP_CONTACT(
   NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE
 )
 values
 (3,'3','セミナー',0,current_timestamp,0,current_timestamp);

insert into NTP_CONTACT(
   NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE
 )
 values
 (4,'4','広告',0,current_timestamp,0,current_timestamp);

insert into NTP_CONTACT(
   NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE
 )
 values
 (5,'5','ホームページ',0,current_timestamp,0,current_timestamp);

insert into NTP_CONTACT(
   NCN_SID, NCN_CODE, NCN_NAME, NCN_AUID, NCN_ADATE, NCN_EUID, NCN_EDATE
 )
 values
 (6,'6','SNS',0,current_timestamp,0,current_timestamp);

 insert into CMN_SAIBAN(
   SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING, SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE
 )
 values
 ('nippou','contact',6,'contact',0,current_timestamp,0,current_timestamp);