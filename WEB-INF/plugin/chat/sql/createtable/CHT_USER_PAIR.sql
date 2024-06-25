create table CHT_USER_PAIR
(
  CUP_SID  integer not null,
  CUP_UID_F  integer not null,
  CUP_UID_S  integer not null,
  CUP_COMP_FLG  integer not null,
  primary key   (CUP_SID)
);