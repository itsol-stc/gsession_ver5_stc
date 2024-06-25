package jp.groupsession.v2.rsv.rsv040;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.rsv.rsv010.Rsv010ParamModel;

/**
 * <br>[機  能] 施設予約 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv040ParamModel extends Rsv010ParamModel {

    /**
     * <br>[機  能] 管理者設定[施設予約]画面のパラメータを共通メッセージ画面フォームへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージ画面フォーム
     * @return 共通メッセージ画面フォーム
     */
    public Cmn999Form setParamValue(Cmn999Form cmn999Form) {
        cmn999Form.addHiddenParam("backScreen", getBackScreen());
        cmn999Form.addHiddenParam("rsvBackPgId", getRsvBackPgId());
        cmn999Form.addHiddenParam("rsvDspFrom", getRsvDspFrom());
        cmn999Form.addHiddenParam("rsvSelectedGrpSid", getRsvSelectedGrpSid());
        cmn999Form.addHiddenParam("rsvSelectedSisetuSid", getRsvSelectedSisetuSid());
        cmn999Form.addHiddenParam("rsv100InitFlg",
                                String.valueOf(isRsv100InitFlg()));
        cmn999Form.addHiddenParam("rsv100SearchFlg",
                                String.valueOf(isRsv100SearchFlg()));
        cmn999Form.addHiddenParam("rsv100SortKey", getRsv100SortKey());
        cmn999Form.addHiddenParam("rsv100OrderKey", getRsv100OrderKey());
        cmn999Form.addHiddenParam("rsv100PageTop", getRsv100PageTop());
        cmn999Form.addHiddenParam("rsv100PageBottom", getRsv100PageBottom());
        cmn999Form.addHiddenParam("rsv100selectedFromYear", getRsv100selectedFromYear());
        cmn999Form.addHiddenParam("rsv100selectedFromMonth", getRsv100selectedFromMonth());
        cmn999Form.addHiddenParam("rsv100selectedFromDay", getRsv100selectedFromDay());
        cmn999Form.addHiddenParam("rsv100selectedToYear", getRsv100selectedToYear());
        cmn999Form.addHiddenParam("rsv100selectedToMonth", getRsv100selectedToMonth());
        cmn999Form.addHiddenParam("rsv100selectedToDay", getRsv100selectedToDay());
        cmn999Form.addHiddenParam("rsv100KeyWord", getRsv100KeyWord());
        cmn999Form.addHiddenParam("rsv100SearchCondition", getRsv100SearchCondition());
        cmn999Form.addHiddenParam("rsv100TargetMok", getRsv100TargetMok());
        cmn999Form.addHiddenParam("rsv100TargetNiyo", getRsv100TargetNiyo());
        cmn999Form.addHiddenParam("rsv100CsvOutField", getRsv100CsvOutField());
        cmn999Form.addHiddenParam("rsv100SelectedKey1", getRsv100SelectedKey1());
        cmn999Form.addHiddenParam("rsv100SelectedKey2", getRsv100SelectedKey2());
        cmn999Form.addHiddenParam("rsv100SelectedKey1Sort", getRsv100SelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100SelectedKey2Sort", getRsv100SelectedKey2Sort());
        cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", getRsvIkkatuTorokuKey());
        cmn999Form.addHiddenParam("rsv100svFromYear", getRsv100svFromYear());
        cmn999Form.addHiddenParam("rsv100svFromMonth", getRsv100svFromMonth());
        cmn999Form.addHiddenParam("rsv100svFromDay", getRsv100svFromDay());
        cmn999Form.addHiddenParam("rsv100svToYear", getRsv100svToYear());
        cmn999Form.addHiddenParam("rsv100svToMonth", getRsv100svToMonth());
        cmn999Form.addHiddenParam("rsv100svToDay", getRsv100svToDay());
        cmn999Form.addHiddenParam("rsv100svGrp1", getRsv100svGrp1());
        cmn999Form.addHiddenParam("rsv100svGrp2", getRsv100svGrp2());
        cmn999Form.addHiddenParam("rsv100svKeyWord", getRsv100svKeyWord());
        cmn999Form.addHiddenParam("rsv100svSearchCondition", getRsv100svSearchCondition());
        cmn999Form.addHiddenParam("rsv100svTargetMok", getRsv100svTargetMok());
        cmn999Form.addHiddenParam("rsv100svTargetNiyo", getRsv100svTargetNiyo());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1", getRsv100svSelectedKey1());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2", getRsv100svSelectedKey2());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1Sort", getRsv100svSelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2Sort", getRsv100svSelectedKey2Sort());
        cmn999Form.addHiddenParam("rsv100SearchSvFlg",
                String.valueOf(isRsv100SearchSvFlg()));

        cmn999Form.addHiddenParam("rsv100dateKbn", getRsv100dateKbn());
        cmn999Form.addHiddenParam("rsv100apprStatus", getRsv100apprStatus());
        cmn999Form.addHiddenParam("rsv100svDateKbn", getRsv100svDateKbn());
        cmn999Form.addHiddenParam("rsv100svApprStatus", getRsv100svApprStatus());

        return cmn999Form;
    }
}