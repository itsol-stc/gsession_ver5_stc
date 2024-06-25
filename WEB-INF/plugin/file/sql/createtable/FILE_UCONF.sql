 create table FILE_UCONF
   (
     USR_SID          integer    not null,
     FUC_MAIN_OKINI   integer    not null,
     FUC_MAIN_CALL    integer    not null,
     FUC_RIREKI_CNT   integer    not null,
     FUC_SMAIL_SEND   integer    not null,
     FUC_CALL         integer    not null,
     primary key (USR_SID)
   );
