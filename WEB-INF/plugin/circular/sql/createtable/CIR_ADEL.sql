 create table CIR_ADEL
   (
     CAC_SID          integer    not null,
     CAD_USR_KBN      integer    not null,
     CAD_DEL_KBN      integer    not null,
     CAD_JDEL_KBN     integer    not null,
     CAD_JDEL_YEAR    integer    not null,
     CAD_JDEL_MONTH   integer    not null,
     CAD_SDEL_KBN     integer    not null,
     CAD_SDEL_YEAR    integer    not null,
     CAD_SDEL_MONTH   integer    not null,
     CAD_DDEL_KBN     integer    not null,
     CAD_DDEL_YEAR    integer    not null,
     CAD_DDEL_MONTH   integer    not null,
     CAD_AUID         integer    not null,
     CAD_ADATE        timestamp  not null,
     CAD_EUID         integer    not null,
     CAD_EDATE        timestamp  not null,
     primary key (CAC_SID)
   );
