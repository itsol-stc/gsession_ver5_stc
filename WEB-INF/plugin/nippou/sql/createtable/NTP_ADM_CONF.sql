create table NTP_ADM_CONF
(
      NAC_CRANGE            integer           not null,
      NAC_ATDEL_FLG         integer           not null,
      NAC_ATDEL_Y           integer                   ,
      NAC_ATDEL_M           integer                   ,
      NAC_HOUR_DIV          integer                   ,
      NAC_SML_KBN           integer                   ,
      NAC_SML_NOTICE_KBN    integer                   ,
      NAC_SML_NOTICE_GRP    integer                   ,
      NAC_CSML_KBN          integer                   ,
      NAC_CSML_NOTICE_KBN   integer                   ,
      NAC_GSML_KBN          integer                   ,
      NAC_GSML_NOTICE_KBN   integer                   ,
      NAC_AUID              integer           not null,
      NAC_ADATE             timestamp         not null,
      NAC_EUID              integer           not null,
      NAC_EDATE             timestamp         not null
);

