package jp.groupsession.v2.ntp.ntp030.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 ユーザラベル情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp030UsrLabelModel extends LabelValueBean {

    /** ユーザ情報  */
    private CmnUsrmInfModel usrInfMdl__ = null;

    /**
     * <p>usrInfMdl を取得します。
     * @return usrInfMdl
     */
    public CmnUsrmInfModel getUsrInfMdl() {
        return usrInfMdl__;
    }

    /**
     * <p>usrInfMdl をセットします。
     * @param usrInfMdl usrInfMdl
     */
    public void setUsrInfMdl(CmnUsrmInfModel usrInfMdl) {
        usrInfMdl__ = usrInfMdl;
    }

}
