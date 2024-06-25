delete from TCD_TIMEZONE;
delete from CMN_SAIBAN where SBN_SID='timecard';
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (1, 1, '09:00:00', '12:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (2, 1, '13:00:00', '18:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (3, 2, '00:00:00', '02:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (4, 2, '03:00:00', '08:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (5, 2, '18:30:00', '22:30:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (6, 2, '23:00:00', '24:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (7, 3, '00:00:00', '05:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (8, 3, '22:00:00', '24:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (9, 4, '02:00:00', '03:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (10, 4, '08:00:00', '09:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (11, 4, '12:00:00', '13:00:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (12, 4, '18:00:00', '18:30:00', 0);
 insert
 into TCD_TIMEZONE(TTZ_SID, TTZ_KBN, TTZ_FRTIME, TTZ_TOTIME, TTI_SID)
           values (13, 4, '22:30:00', '23:00:00', 0);

 insert
 into CMN_SAIBAN(SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING, SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE)
           values ('timecard', 'tcdzone', 13, 'tcdzone', 0, current_timestamp, 0, current_timestamp);

 insert
 into TCD_TIMEZONE_INFO(TTI_SID, TTI_NAME, TTI_RYAKU, TTI_SORT, TTI_USE, TTI_HOLIDAY, TTI_AUID, TTI_ADATE, TTI_EUID, TTI_EDATE)
           values (0, '通常時間帯', '通常', 1, 1, 0, 0, current_timestamp, 0, current_timestamp);
 insert
 into TCD_HOLIDAY_INF(THI_SID, THI_NAME, THI_HOLTOTAL_KBN, THI_LIMIT, THI_ORDER, THI_CONTENT_KBN, THI_AUID, THI_ADATE, THI_EUID, THI_EDATE)
           values (1, '欠勤', 2, 0, 1, 0, 0, current_timestamp, 0, current_timestamp);
 insert
 into TCD_HOLIDAY_INF(THI_SID, THI_NAME, THI_HOLTOTAL_KBN, THI_LIMIT, THI_ORDER, THI_CONTENT_KBN, THI_AUID, THI_ADATE, THI_EUID, THI_EDATE)
           values (2, '慶弔', 0, 0, 2, 0, 0, current_timestamp, 0, current_timestamp);
 insert
 into TCD_HOLIDAY_INF(THI_SID, THI_NAME, THI_HOLTOTAL_KBN, THI_LIMIT, THI_ORDER, THI_CONTENT_KBN, THI_AUID, THI_ADATE, THI_EUID, THI_EDATE)
           values (3, '有休', 1, 0, 3, 0, 0, current_timestamp, 0, current_timestamp);
 insert
 into TCD_HOLIDAY_INF(THI_SID, THI_NAME, THI_HOLTOTAL_KBN, THI_LIMIT, THI_ORDER, THI_CONTENT_KBN, THI_AUID, THI_ADATE, THI_EUID, THI_EDATE)
           values (4, '代休', 0, 0, 4, 0, 0, current_timestamp, 0, current_timestamp);
 insert
 into TCD_HOLIDAY_INF(THI_SID, THI_NAME, THI_HOLTOTAL_KBN, THI_LIMIT, THI_ORDER, THI_CONTENT_KBN, THI_AUID, THI_ADATE, THI_EUID, THI_EDATE)
           values (6, '振休', 0, 0, 5, 0, 0, current_timestamp, 0, current_timestamp);
 insert
 into TCD_HOLIDAY_INF(THI_SID, THI_NAME, THI_HOLTOTAL_KBN, THI_LIMIT, THI_ORDER, THI_CONTENT_KBN, THI_AUID, THI_ADATE, THI_EUID, THI_EDATE)
           values (5, 'その他', 0, 0, 6, 1, 0, current_timestamp, 0, current_timestamp);
 insert
 into CMN_SAIBAN(SBN_SID, SBN_SID_SUB, SBN_NUMBER, SBN_STRING, SBN_AID, SBN_ADATE, SBN_EID, SBN_EDATE)
           values ('timecard', 'tcdHolInf', 6, 'tcdHolInf', 0, current_timestamp, 0, current_timestamp);

