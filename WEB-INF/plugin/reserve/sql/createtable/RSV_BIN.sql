create table RSV_BIN
(
        RSD_SID        integer not null,
        BIN_SID        bigint  not null,
        RSD_IMG_KBN    integer not null,
        RSD_DSP_KBN    integer not null,
        primary key (RSD_SID, BIN_SID)
);