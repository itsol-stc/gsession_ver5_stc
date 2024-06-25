create table FILE_FILE_TEXT
(
  FDR_SID    integer  not null,
  FDR_VERSION    integer  not null,
  FCB_SID    integer  not null,
  FFT_SEC_NO    integer  not null,
  FFT_TEXT    text,
  FFT_BIKO    varchar(1000),
  FFT_READ_KBN    integer  not null
);