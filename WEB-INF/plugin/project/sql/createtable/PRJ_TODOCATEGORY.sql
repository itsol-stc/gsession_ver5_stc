/***** Object:  Table PRJ_TODOCATEGORY   Script Date: 2007/08/29 9:45:37 *****/
create table PRJ_TODOCATEGORY
(
        PRJ_SID          integer         not null,
        PTC_CATEGORY_SID integer         not null,
        PTC_SORT         integer         not null,
        PTC_NAME         varchar(20)     not null,
        PTC_AUID         integer         not null,
        PTC_ADATE        timestamp       not null,
        PTC_EUID         integer         not null,
        PTC_EDATE        timestamp       not null,
        primary key (
                PRJ_SID,
                PTC_CATEGORY_SID
        ) 
) ;
