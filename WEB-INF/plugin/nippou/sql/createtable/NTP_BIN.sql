create table NTP_BIN
(
        NTP_SID        integer      not null,
        BIN_SID        bigint       not null,
        primary key (NTP_SID, BIN_SID)
);