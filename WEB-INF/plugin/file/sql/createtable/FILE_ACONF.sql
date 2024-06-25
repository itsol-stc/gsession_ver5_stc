 create table FILE_ACONF
   (
     FAC_CRT_KBN         integer    not null,
     FAC_FILE_SIZE       integer    not null,
     FAC_SAVE_DAYS       integer    not null,
     FAC_LOCK_KBN        integer    not null,
     FAC_VER_KBN         integer    not null,
     FAC_AUID            integer    not null,
     FAC_ADATE           timestamp  not null,
     FAC_EUID            integer    not null,
     FAC_EDATE           timestamp  not null,
     FAC_SMAIL_SEND_KBN  integer    not null,
     FAC_SMAIL_SEND      integer    not null,
     FAC_WARN_CNT        integer    not null,
     FAC_PERSONAL_KBN    integer    not null,
     FAC_USE_KBN         integer    not null,
     FAC_PERSONAL_CAPA   integer    not null,
     FAC_PERSONAL_SIZE   integer,
     FAC_PERSONAL_WARN   integer,
     FAC_PERSONAL_VER    integer    not null,
     FAC_PERSONAL_EDATE  timestamp
   );
