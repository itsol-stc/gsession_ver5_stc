/***** Object:  Table PRJ_TODO_BIN   Script Date: 2007/08/29 9:50:27 *****/
create table PRJ_TODO_BIN
(
        PRJ_SID         integer         not null,
        PTD_SID         integer         not null,
        BIN_SID         bigint          not null,
        USR_SID         integer         not null,
        primary key (
                PRJ_SID,
                PTD_SID,
                BIN_SID
        )
) ;