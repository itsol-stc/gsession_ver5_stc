 create table SML_ADEL
   (
     SAC_SID          integer    not null,
     SAD_USR_KBN      integer    not null,
     SAD_DEL_KBN      integer    not null,
     SAD_JDEL_KBN     integer    not null,
     SAD_JDEL_YEAR    integer    not null,
     SAD_JDEL_MONTH   integer    not null,
     SAD_SDEL_KBN     integer    not null,
     SAD_SDEL_YEAR    integer    not null,
     SAD_SDEL_MONTH   integer    not null,
     SAD_WDEL_KBN     integer    not null,
     SAD_WDEL_YEAR    integer    not null,
     SAD_WDEL_MONTH   integer    not null,
     SAD_DDEL_KBN     integer    not null,
     SAD_DDEL_YEAR    integer    not null,
     SAD_DDEL_MONTH   integer    not null,
     SAD_AUID         integer    not null,
     SAD_ADATE        timestamp  not null,
     SAD_EUID         integer    not null,
     SAD_EDATE        timestamp  not null,
     primary key (SAC_SID)
   );
