create table SCH_PUSH_LIST
(
        SCD_SID           integer         not null,
        USR_SID           integer         not null,
        SPL_REMINDER           timestamp         not null,
        SPL_REMINDER_KBN           integer         not null,
        primary key (SCD_SID, USR_SID)
);