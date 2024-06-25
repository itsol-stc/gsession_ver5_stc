create table SML_PUSH_UCONF
(
  USR_SID               integer       not null,
  SAC_SID               integer       not null,
  SPU_PUSHUSE           integer       not null,
  primary key(USR_SID, SAC_SID)
);