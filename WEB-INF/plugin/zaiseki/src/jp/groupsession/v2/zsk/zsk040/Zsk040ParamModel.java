package jp.groupsession.v2.zsk.zsk040;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.GSValidateZaiseki;
import jp.groupsession.v2.zsk.zsk010.Zsk010ParamModel;

/**
 * <br>[機  能] 在席管理 座席表登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk040ParamModel extends Zsk010ParamModel {

    /** 名前 */
    private String zsk040name__ = null;
    /** 座席表表示順 */
    private String zasekiSortNum__ = "0";
    /** 初期表示フラグ */
    private int zsk040initFlg__ = 0;

    /** 添付ファイル */
    private String[] zsk040file__ = null;
    /** ファイルコンボ */
    private List < LabelValueBean > zsk040FileLabelList__ = null;

    /**
     * <p>zasekiSortNum を取得します。
     * @return zasekiSortNum
     */
    public String getZasekiSortNum() {
        return zasekiSortNum__;
    }
    /**
     * <p>zasekiSortNum をセットします。
     * @param zasekiSortNum zasekiSortNum
     */
    public void setZasekiSortNum(String zasekiSortNum) {
        zasekiSortNum__ = zasekiSortNum;
    }
    /**
     * <p>zsk040FileLabelList を取得します。
     * @return zsk040FileLabelList
     */
    public List<LabelValueBean> getZsk040FileLabelList() {
        return zsk040FileLabelList__;
    }
    /**
     * <p>zsk040FileLabelList をセットします。
     * @param zsk040FileLabelList zsk040FileLabelList
     */
    public void setZsk040FileLabelList(List<LabelValueBean> zsk040FileLabelList) {
        zsk040FileLabelList__ = zsk040FileLabelList;
    }
    /**
     * <p>zsk040file を取得します。
     * @return zsk040file
     */
    public String[] getZsk040file() {
        return zsk040file__;
    }
    /**
     * <p>zsk040file をセットします。
     * @param zsk040file zsk040file
     */
    public void setZsk040file(String[] zsk040file) {
        zsk040file__ = zsk040file;
    }
    /**
     * <p>zsk040name を取得します。
     * @return zsk040name
     */
    public String getZsk040name() {
        return zsk040name__;
    }
    /**
     * <p>zsk040name をセットします。
     * @param zsk040name zsk040name
     */
    public void setZsk040name(String zsk040name) {
        zsk040name__ = zsk040name;
    }
    /**
     * <p>zsk040initFlg を取得します。
     * @return zsk040initFlg
     */
    public int getZsk040initFlg() {
        return zsk040initFlg__;
    }
    /**
     * <p>zsk040initFlg をセットします。
     * @param zsk040initFlg zsk040initFlg
     */
    public void setZsk040initFlg(int zsk040initFlg) {
        zsk040initFlg__ = zsk040initFlg;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fileName ファイル名
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(String fileName, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        String message = gsMsg.getMessage(req, "zsk.08");
        String message2 = gsMsg.getMessage(req, "cmn.sort");
        String message3 = gsMsg.getMessage(req, "zsk.27");

        //表タイトル
        GSValidateZaiseki.validateName(errors,
                zsk040name__,
                message,
                GSConstZaiseki.MAX_LENGTH_MAPTITLE);
        //表示順
        GSValidateZaiseki.validateSortNumber(errors,
                zasekiSortNum__,
                message2,
                GSConstZaiseki.MAX_LENGTH_SORTNUM,
                req);
        //添付ファイル
        GSValidateZaiseki.validateFile(errors,
                fileName,
                message3);

        return errors;
    }
}
