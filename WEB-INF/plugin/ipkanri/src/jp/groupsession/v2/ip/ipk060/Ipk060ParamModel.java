package jp.groupsession.v2.ip.ipk060;

import java.util.List;

import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.ipk040.Ipk040ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] IP管理 IPアドレスインポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk060ParamModel extends Ipk040ParamModel {
    /** 添付ファイル */
    private String[] ipk060Files__ = null;
    /** ファイルコンボ */
    private List < LabelValueBean > ipk060FileLabelList__ = null;
    /** インポートモード */
    private String importMode__ = IpkConst.IMPORT_MODE_TUIKA;

    /**
     * <p>importMode を取得します。
     * @return importMode
     */
    public String getImportMode() {
        return importMode__;
    }
    /**
     * <p>importMode をセットします。
     * @param importMode importMode
     */
    public void setImportMode(String importMode) {
        importMode__ = importMode;
    }
    /**
     * <p>ipk060FileLabelList を取得します。
     * @return ipk060FileLabelList
     */
    public List<LabelValueBean> getIpk060FileLabelList() {
        return ipk060FileLabelList__;
    }
    /**
     * <p>ipk060FileLabelList をセットします。
     * @param ipk060FileLabelList ipk060FileLabelList
     */
    public void setIpk060FileLabelList(List<LabelValueBean> ipk060FileLabelList) {
        ipk060FileLabelList__ = ipk060FileLabelList;
    }
    /**
     * <p>ipk060Files を取得します。
     * @return ipk060Files
     */
    public String[] getIpk060Files() {
        return ipk060Files__;
    }
    /**
     * <p>ipk060Files をセットします。
     * @param ipk060Files ipk060Files
     */
    public void setIpk060Files(String[] ipk060Files) {
        ipk060Files__ = ipk060Files;
    }
}