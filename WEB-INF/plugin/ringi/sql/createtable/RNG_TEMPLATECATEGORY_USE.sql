CREATE TABLE RNG_TEMPLATECATEGORY_USE
(
    RTC_SID                     INTEGER NOT NULL,
    USR_SID                     INTEGER NOT NULL,
    GRP_SID                     INTEGER NOT NULL,
    PRIMARY KEY (RTC_SID, USR_SID, GRP_SID)
);
