package jp.groupsession.v2.ntp.ntp030;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] 日報 日間画面で使用する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp030GoodMdl {
    /** rowId */
    private CmnUsrmInfModel usrMdl__;
    /** 削除フラグ  0:削除不可  1:削除可*/
    private int goodDelFlg__ = 0;
    /**
     * <p>usrMdl を取得します。
     * @return usrMdl
     */
    public CmnUsrmInfModel getUsrMdl() {
        return usrMdl__;
    }
    /**
     * <p>usrMdl をセットします。
     * @param usrMdl usrMdl
     */
    public void setUsrMdl(CmnUsrmInfModel usrMdl) {
        usrMdl__ = usrMdl;
    }
    /**
     * <p>goodDelFlg を取得します。
     * @return goodDelFlg
     */
    public int getGoodDelFlg() {
        return goodDelFlg__;
    }
    /**
     * <p>goodDelFlg をセットします。
     * @param goodDelFlg goodDelFlg
     */
    public void setGoodDelFlg(int goodDelFlg) {
        goodDelFlg__ = goodDelFlg;
    }

}
