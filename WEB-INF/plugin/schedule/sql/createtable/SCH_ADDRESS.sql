create table SCH_ADDRESS
(
  SCD_SID         integer         not null,
  ADR_SID         integer         not null,
  ADC_SID         integer,
  SCA_AUID        integer         not null,
  SCA_ADATE       timestamp      not null,
  SCA_EUID        integer         not null,
  SCA_EDATE       timestamp      not null,
  primary key (SCD_SID, ADR_SID)
);