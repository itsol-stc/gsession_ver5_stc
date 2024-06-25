package jp.groupsession.v2.sch.sch080;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sch.sch100.Sch100ParamModel;

/**
 * <br>[機  能] スケジュール 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch080ParamModel extends Sch100ParamModel {

    /**
     * <br>[機  能] 共通メッセージ画面Formに画面パラメータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージ画面Form
     */
    public void setMsgFormParam(Cmn999Form cmn999Form) {

        cmn999Form.addHiddenParam("backScreen", getBackScreen());
        cmn999Form.addHiddenParam("dspMod", getDspMod());
        cmn999Form.addHiddenParam("listMod", getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", getSch010SelectDate());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", getSch020SelectUsrSid());
        cmn999Form.addHiddenParam("sch030FromHour", getSch030FromHour());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100PageNum", getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", getSch100SvSltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", getSch100SvSortKey2());

        cmn999Form.addHiddenParam("sch100SltGroup", getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch010searchWord", getSch010searchWord());
        cmn999Form.addHiddenParam("sch100SvSearchTarget", getSch100SvSearchTarget());
        cmn999Form.addHiddenParam("sch100SearchTarget", getSch100SearchTarget());
        cmn999Form.addHiddenParam("sch100SvBgcolor", getSch100SvBgcolor());
        cmn999Form.addHiddenParam("sch100Bgcolor", getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", getSch100CsvOutField());
    }
}
