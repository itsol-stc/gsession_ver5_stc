/***** Object:  Table PRJ_PRJSTATUS   Script Date: 2007/08/29 9:33:12 *****/
create table PRJ_PRJSTATUS
(
        PRJ_SID         integer         not null,
        PRS_SID         integer         not null,
        PRS_SORT        integer         not null,
        PRS_NAME        varchar(20)     not null,
        PRS_RATE        integer         not null,
        PRS_AUID        integer         not null,
        PRS_ADATE       timestamp       not null,
        PRS_EUID        integer         not null,
        PRS_EDATE       timestamp       not null,
        primary key (
                PRJ_SID,
                PRS_SID
        ) 
) ;
