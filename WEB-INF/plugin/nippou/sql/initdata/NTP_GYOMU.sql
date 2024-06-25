
insert into  NTP_GYOMU (
     NGY_SID, NGY_CODE, NGY_NAME, NGY_AUID, NGY_ADATE, NGY_EUID, NGY_EDATE
     )
 values
 (1,'1','営業',0,current_timestamp,0,current_timestamp);

insert into  NTP_GYOMU (
     NGY_SID, NGY_CODE, NGY_NAME, NGY_AUID, NGY_ADATE, NGY_EUID, NGY_EDATE
     )
 values
 (2,'2','開発',0,current_timestamp,0,current_timestamp);

 insert into CMN_SAIBAN(
   SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING, SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE
 )
 values
 ('nippou','gyomu',2,'gyomu',0,current_timestamp,0,current_timestamp);