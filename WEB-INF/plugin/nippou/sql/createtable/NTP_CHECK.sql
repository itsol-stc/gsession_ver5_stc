create table NTP_CHECK
(
  NTP_SID        integer      not null,
  USR_SID        integer      not null,
  primary key (NTP_SID, USR_SID)
);
