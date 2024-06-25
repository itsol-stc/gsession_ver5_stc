package jp.groupsession.v2.rsv.rsv040;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.rsv.rsv010.Rsv010Form;

/**
 * <br>[機  能] 施設予約 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv040Form extends Rsv010Form {
    /**ショートメールプラグイン利用権限*/
    private int canUseSmlKbn__;
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
        cmn999Form.addHiddenParam("rsv010LearnMoreFlg", getRsv010LearnMoreFlg());
        cmn999Form.addHiddenParam("rsv010sisetuKeyword", getRsv010sisetuKeyword());
        cmn999Form.addHiddenParam("rsv010KeyWordkbn", getRsv010KeyWordkbn());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", getRsv010sisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu", getRsv010sisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordBiko", getRsv010sisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordNo", getRsv010sisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordIsbn", getRsv010sisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010sisetuFree", getRsv010sisetuFree());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromY", getRsv010sisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMo", getRsv010sisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromD", getRsv010sisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromH", getRsv010sisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMi", getRsv010sisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToY", getRsv010sisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMo", getRsv010sisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToD", getRsv010sisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToH", getRsv010sisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMi", getRsv010sisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010sisetuKbn", getRsv010sisetuKbn());
        cmn999Form.addHiddenParam("rsv010grpNarrowDown", getRsv010grpNarrowDown());
        cmn999Form.addHiddenParam("rsv010sisetuSmoky", getRsv010sisetuSmoky());
        cmn999Form.addHiddenParam("rsv010sisetuChere", getRsv010sisetuChere());
        cmn999Form.addHiddenParam("rsv010sisetuTakeout", getRsv010sisetuTakeout());
        cmn999Form.addHiddenParam("rsv010svSisetuKeyword", getRsv010svSisetuKeyword());
        cmn999Form.addHiddenParam("rsv010svKeyWordkbn", getRsv010svKeyWordkbn());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisan",
                getRsv010svSisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisetu",
                getRsv010svSisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko", getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn", getRsv010svSisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010svSisetuFree", getRsv010svSisetuFree());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromY", getRsv010svSisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMo", getRsv010svSisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromD", getRsv010svSisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromH", getRsv010svSisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMi", getRsv010svSisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToY", getRsv010svSisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMo", getRsv010svSisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToD", getRsv010svSisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToH", getRsv010svSisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMi", getRsv010svSisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010svSisetuKbn", getRsv010svSisetuKbn());
        cmn999Form.addHiddenParam("rsv010svGrpNarrowDown", getRsv010svGrpNarrowDown());
        cmn999Form.addHiddenParam("rsv010svSisetuSmoky", getRsv010svSisetuSmoky());
        cmn999Form.addHiddenParam("rsv010svSisetuChere", getRsv010svSisetuChere());
        cmn999Form.addHiddenParam("rsv010svSisetuTakeout", getRsv010svSisetuTakeout());
        cmn999Form.addHiddenParam("rsv010InitFlg", getRsv010InitFlg());
        cmn999Form.addHiddenParam("rsv010SiborikomiFlg", getRsv010SiborikomiFlg());

        return cmn999Form;
    }
    /**
     * <p>canUseSmlKbn を取得します。
     * @return canUseSmlKbn
     */
    public int getCanUseSmlKbn() {
        return canUseSmlKbn__;
    }
    /**
     * <p>canUseSmlKbn をセットします。
     * @param canUseSmlKbn canUseSmlKbn
     */
    public void setCanUseSmlKbn(int canUseSmlKbn) {
        canUseSmlKbn__ = canUseSmlKbn;
    }
}