create table MBL_PUSH_UID
(
     CMU_UID           varchar(50)         not null,
     USR_SID           integer             not null,
     MPI_LOGINDATE     timestamp          not null,
     primary key (CMU_UID)
) ;
