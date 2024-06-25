 create table SML_LOG_COUNT
 (
     SAC_SID        integer         not null,
     SLC_KBN        integer         not null,
     SLC_SYS_KBN        integer         not null,
     SLC_CNT_TO        integer         not null,
     SLC_CNT_CC        integer         not null,
     SLC_CNT_BCC        integer         not null,
     SLC_DATE      timestamp       not null
 ) ;