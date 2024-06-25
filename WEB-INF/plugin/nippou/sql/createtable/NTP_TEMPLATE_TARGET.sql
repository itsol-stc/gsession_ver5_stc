create table NTP_TEMPLATE_TARGET
(
  NTT_SID       integer      not null,
  NTG_SID       integer      not null,
  primary key (NTT_SID, NTG_SID)
);
