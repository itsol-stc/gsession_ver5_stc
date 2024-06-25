/***** Object:  Table PRJ_STATUS_HISTORY   Script Date: 2007/08/29 9:45:01 *****/
create table PRJ_STATUS_HISTORY
(
        PRJ_SID         integer         not null,
        PTD_SID         integer         not null,
        PSH_SID         integer         not null,
        PTS_SID         integer         not null,
        PSH_REASON      varchar(500)      not null,
        PSH_AUID        integer         not null,
        PSH_ADATE       timestamp       not null,
        PSH_EUID        integer         not null,
        PSH_EDATE       timestamp       not null,
        primary key (
                PRJ_SID,
                PTD_SID,
                PSH_SID
        ) 
) ;
