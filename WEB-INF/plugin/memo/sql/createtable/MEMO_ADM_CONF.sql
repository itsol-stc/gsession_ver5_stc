create table MEMO_ADM_CONF
(
  MAC_ATDEL_KBN  integer    not null,
  MAC_ATDEL_Y    integer            ,
  MAC_ATDEL_M    integer            ,
  MAC_AUID       integer    not null,
  MAC_ADATE      timestamp  not null,
  MAC_EUID       integer    not null,
  MAC_EDATE      timestamp  not null
);