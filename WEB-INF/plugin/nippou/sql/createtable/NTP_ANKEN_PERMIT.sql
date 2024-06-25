create table NTP_ANKEN_PERMIT(
NAN_SID INTEGER not null,
USR_SID INTEGER not null,
GRP_SID INTEGER not null,
NAP_KBN INTEGER not null,
primary key (NAN_SID,USR_SID,GRP_SID,NAP_KBN)
);