package jp.groupsession.v2.cht.cht080;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] チャット 特例アクセス情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht080SpAccessModel extends AbstractModel {

    /** 特例アクセスSID */
    private int ssaSid__ = 0;
    /** 名称 */
    private String name__ = null;
    /** 備考 */
    private String biko__ = null;
    /**
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }
    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }
    /**
     * <p>ssaSid を取得します。
     * @return ssaSid
     */
    public int getSsaSid() {
        return ssaSid__;
    }
    /**
     * <p>ssaSid をセットします。
     * @param ssaSid ssaSid
     */
    public void setSsaSid(int ssaSid) {
        ssaSid__ = ssaSid;
    }
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <br>[機  能] 備考(表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 備考(表示用)
     */
    public String getViewBiko() {
        return StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(biko__, ""));
    }
}
