package jp.groupsession.v2.api.smail.mail;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 *
 * <br>[機  能] Apiショートメール検索用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlMailBodyModel {

    /** メール形式(0:テキスト形式 / 1:HTML形式)*/
    private int    mailType__ = GSConstSmail.SAC_SEND_MAILTYPE_NORMAL;

    /** メール本文*/
    private String mailBody__;

    /** 添付ファイル一覧*/
    private ArrayList<CmnBinfModel> binList__;

    /**
     * <p>mailType を取得します。
     * @return mailType
     */
    public int getMailType() {
        return mailType__;
    }

    /**
     * <p>mailType をセットします。
     * @param mailType mailType
     */
    public void setMailType(int mailType) {
        mailType__ = mailType;
    }

    /**
     * <p>mailBody を取得します。
     * @return mailBody
     */
    public String getMailBody() {
        return mailBody__;
    }

    /**
     * <p>mailBody をセットします。
     * @param mailBody mailBody
     */
    public void setMailBody(String mailBody) {
        mailBody__ = mailBody;
    }

    /**
     * <p>binList を取得します。
     * @return binList
     */
    public ArrayList<CmnBinfModel> getBinList() {
        return binList__;
    }

    /**
     * <p>binMdl を配列へ追加します。
     * @param binMdl binMdl
     */
    public void addBinModel(CmnBinfModel binMdl) {
        if (binMdl != null) {
            if (binList__ == null) {
                binList__ = new ArrayList<CmnBinfModel>();
            }
            binList__.add(binMdl);
        }
    }
}
