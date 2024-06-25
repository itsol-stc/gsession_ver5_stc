 create table API_CONF
   (
    APC_TOAKEN_USE integer not null,
    APC_TOAKEN_IP varchar(256),
    APC_TOAKEN_LIFE integer not null,
    APC_BASIC_USE integer not null,
    APC_BASIC_IP varchar(256),
    APC_AUTO_DEL integer not null
   );
