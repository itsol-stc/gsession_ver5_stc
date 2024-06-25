/***** Object:  Table PRJ_TODOCOMMENT   Script Date: 2007/08/29 9:49:18 *****/
create table PRJ_TODOCOMMENT
(
        PRJ_SID         integer         not null,
        PTD_SID         integer         not null,
        PCM_SID         integer         not null,
        PCM_COMMENT     varchar(1000)    not null,
        PCM_AUID        integer         not null,
        PCM_ADATE       timestamp       not null,
        PCM_EUID        integer         not null,
        PCM_EDATE       timestamp       not null,
        primary key (
                PRJ_SID,
                PTD_SID,
                PCM_SID
        )
) ;
