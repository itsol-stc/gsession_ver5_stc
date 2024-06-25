insert into ANP_MTEMP(
       APM_SID,
       APM_TITLE,
       APM_SUBJECT,
       APM_TEXT1,
       APM_TEXT2,
       APM_AUID,
       APM_ADATE,
       APM_EUID,
       APM_EDATE
     )
     values
     (
       1,
       '災害に伴う安否状況の確認',
       '災害に伴う安否状況の確認',
       '社員各位

先ほど発生した災害に伴い安否状況を確認しております。
本メールが届いた方は、下記URLより速やかに安否状況の登録をお願いします。',
       '',
       0,
       current_timestamp,
       0,
       current_timestamp
     );


insert into CMN_SAIBAN(
            SBN_SID,
            SBN_SID_SUB,
            SBN_NUMBER,
            SBN_STRING,
            SBN_AID,
            SBN_ADATE,
            SBN_EID,
            SBN_EDATE)
        values
            ('anpi',
            'mailtemplate',
            1,
            'mailtemplate',
            0,
            current_timestamp,
            0,
            current_timestamp);