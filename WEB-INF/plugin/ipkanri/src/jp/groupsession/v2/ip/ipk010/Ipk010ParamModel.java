package jp.groupsession.v2.ip.ipk010;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.ip.model.IpkNetModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] IP管理 ネットワーク一覧画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk010ParamModel extends AbstractParamModel {
    /** IP管理のネットワーク一覧 */
    private ArrayList<IpkNetModel> ipkNetList__ = null;
    /** IP管理のネットワーク管理者リスト */
    private ArrayList<IpkNetModel> ipkAdminList__ = null;
    /** ネットワークSID */
    private String netSid__;
    /** ページ遷移コマンド */
    private String CMD__;
    /** 全ネットワーク管理者 */
    private boolean allAdm__ = false;
    /** ネットワークアドレス */
    private String netad__;
    /** 変更削除ボタン列表示フラグ */
    private String dspFlg__;
    /** キーワード */
    private String ipk070KeyWord__ = null;

    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /**
     * <p>dspFlg を取得します。
     * @return dspFlg
     */
    public String getDspFlg() {
        return dspFlg__;
    }
    /**
     * <p>dspFlg をセットします。
     * @param dspFlg dspFlg
     */
    public void setDspFlg(String dspFlg) {
        dspFlg__ = dspFlg;
    }
    /**
     * <p>allAdm を取得します。
     * @return allAdm
     */
    public boolean getAllAdm() {
        return allAdm__;
    }
    /**
     * <p>allAdm をセットします。
     * @param allAdm allAdm
     */
    public void setAllAdm(boolean allAdm) {
        allAdm__ = allAdm;
    }
    /**
     * <p>ipkAdminList を取得します。
     * @return ipkAdminList
     */
    public ArrayList<IpkNetModel> getIpkAdminList() {
        return ipkAdminList__;
    }
    /**
     * <p>ipkAdminList をセットします。
     * @param ipkAdminList ipkAdminList
     */
    public void setIpkAdminList(ArrayList<IpkNetModel> ipkAdminList) {
        ipkAdminList__ = ipkAdminList;
    }
    /**
     * <p>ipkNetList を取得します。
     * @return ipkNetList
     */
    public ArrayList<IpkNetModel> getIpkNetList() {
        return ipkNetList__;
    }
    /**
     * <p>ipkNetList をセットします。
     * @param ipkNetList ipkNetList
     */
    public void setIpkNetList(ArrayList<IpkNetModel> ipkNetList) {
        ipkNetList__ = ipkNetList;
    }
    /**
     * <p>netSid を取得します。
     * @return netSid
     */
    public String getNetSid() {
        return netSid__;
    }
    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(String netSid) {
        netSid__ = netSid;
    }
    /**
     * <p>CMD を取得します。
     * @return CMD
     */
    public String getCMD() {
        return CMD__;
    }
    /**
     * <p>CMD をセットします。
     * @param CMD CMD
     */
    public void setCMD(String CMD) {
        CMD__ = CMD;
    }
    /**
     * <p>netad を取得します。
     * @return netad
     */
    public String getNetad() {
        return netad__;
    }
    /**
     * <p>netad をセットします。
     * @param netad netad
     */
    public void setNetad(String netad) {
        netad__ = netad;
    }
    /**
     * <p>ipk070KeyWord を取得します。
     * @return ipk070KeyWord
     */
    public String getIpk070KeyWord() {
        return ipk070KeyWord__;
    }
    /**
     * <p>ipk070KeyWord をセットします。
     * @param ipk070KeyWord ipk070KeyWord
     */
    public void setIpk070KeyWord(String ipk070KeyWord) {
        ipk070KeyWord__ = ipk070KeyWord;
    }
    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }
    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
}