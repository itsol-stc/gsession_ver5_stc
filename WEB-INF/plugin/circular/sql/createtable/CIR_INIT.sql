CREATE
    TABLE CIR_INIT(
        CIN_INITSET_KEN INTEGER   NOT null
        ,CIN_MEMO_KBN   INTEGER   NOT null
        ,CIN_MEMO_DAY   INTEGER   NOT null
        ,CIN_KOU_KBN    INTEGER   NOT null
        ,CIN_AUID       INTEGER   NOT null
        ,CIN_ADATE      TIMESTAMP NOT null
        ,CIN_EUID       INTEGER   NOT null
        ,CIN_EDATE      TIMESTAMP NOT null
        ,CIN_ACNT_MAKE    integer      not null
        ,CIN_AUTO_DEL_KBN integer      not null
        ,CIN_ACNT_USER integer      not null
    );