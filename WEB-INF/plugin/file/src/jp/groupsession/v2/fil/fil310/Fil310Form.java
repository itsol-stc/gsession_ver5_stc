package jp.groupsession.v2.fil.fil310;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.fil200.Fil200Form;
import jp.groupsession.v2.fil.model.FileMoneyMasterModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル管理 外貨マスタ画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil310Form extends Fil200Form {
    /** 削除対象外貨SID */
    private int filDelGaikaId__ = 0;
    /** 外貨リスト */
    private List<Fil310DisplayModel> gaikaList__ = null;
    /** 並び替え対象の外貨 */
    private String[] fil310SortGaika__ = null;
    /** チェックされている並び順 */
    private String fil310SortRadio__ = null;

    /**
     * <br>[機  能] 削除時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws Exception  実行例外
     * @return エラー
     * @throws SQLException 
     */
    public ActionErrors validateDelCheck(RequestModel reqMdl, Connection con)
            throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String message = gsMsg.getMessage("cmn.cmn310.06");

        //未選択チェック
        if (fil310SortRadio__ == null || fil310SortRadio__.equals("0")) {
            msg = new ActionMessage("error.select.required.text", message);
            StrutsUtil.addMessage(
                    errors, msg, "error.select.required.text");
            return errors;
        }
        message = gsMsg.getMessage("fil.fil310.4");
        FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con);
        List<FileMoneyMasterModel> gaikaList = new ArrayList<FileMoneyMasterModel>();
        gaikaList = fmmDao.select();
        if (gaikaList.size() <= 1) {
            msg = new ActionMessage("error.cant.delete.one.gaika");
            StrutsUtil.addMessage(
                    errors, msg, "error.cant.delete.one.gaika");
        } else if (fmmDao.existUsedGaika(filDelGaikaId__)) {
            msg = new ActionMessage("error.cant.delete.gaika");
            StrutsUtil.addMessage(
                    errors, msg, "error.cant.delete.gaika");
        }
        return errors;
    }

    /**
     * <br>[機  能] 並び替え時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws Exception  実行例外
     * @return エラー
     * @throws SQLException 
     */
    public ActionErrors validateSortCheck(RequestModel reqMdl, Connection con)
            throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String target = gsMsg.getMessage("fil.fil310.2");
        FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con);

        if (fil310SortRadio__ == null || fil310SortRadio__.equals("0")) {
            msg = new ActionMessage("error.select.required.text", target);
            StrutsUtil.addMessage(
                    errors, msg, "error.select.required.text");
        } else if (fmmDao.select(NullDefault.getInt(fil310SortRadio__, -1)) == null) {
            msg = new ActionMessage("search.data.notfound", target);
            StrutsUtil.addMessage(
                    errors, msg, "search.data.notfound");
        }
        
        
        return errors;
    }

        /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setHiddenParamFil310(Cmn999Form msgForm) {

        //遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0
        msgForm.addHiddenParam("backScreen", getBackScreen());
        //遷移元
        msgForm.addHiddenParam("backDsp", getBackDsp());
        //検索キーワード
        msgForm.addHiddenParam("filSearchWd", getFilSearchWd());
        //選択したキャビネットSID
        msgForm.addHiddenParam("fil010SelectCabinet", getFil010SelectCabinet());
        //選択したフォルダーSID(フォルダ一覧のカレントディレクトリ)
        msgForm.addHiddenParam("fil010SelectDirSid", getFil010SelectDirSid());
        //ショートカット選択multibox
        msgForm.addHiddenParam("fil010SelectDelLink", getFil010SelectDelLink());
        //削除用チェックボックス
        msgForm.addHiddenParam("fil040SelectDel", getFil040SelectDel());
        //チェック要素の並び順
        msgForm.addHiddenParam("fil310SortRadio", fil310SortRadio__);
        //削除対象外貨SID
        msgForm.addHiddenParam("filDelGaikaId", filDelGaikaId__);
    }

    /**
     * <p>filDelGaikaId を取得します。
     * @return filDelGaikaId
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#filDelGaikaId__
     */
    public int getFilDelGaikaId() {
        return filDelGaikaId__;
    }
    /**
     * <p>filDelGaikaId をセットします。
     * @param filDelGaikaId filDelGaikaId
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#filDelGaikaId__
     */
    public void setFilDelGaikaId(int filDelGaikaId) {
        filDelGaikaId__ = filDelGaikaId;
    }
    /**
     * <p>gaikaList を取得します。
     * @return gaikaList
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#gaikaList__
     */
    public List<Fil310DisplayModel> getGaikaList() {
        return gaikaList__;
    }
    /**
     * <p>gaikaList をセットします。
     * @param gaikaList gaikaList
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#gaikaList__
     */
    public void setGaikaList(List<Fil310DisplayModel> gaikaList) {
        gaikaList__ = gaikaList;
    }
    /**
     * <p>fil310SortGaika を取得します。
     * @return fil310SortGaika
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#fil310SortGaika__
     */
    public String[] getFil310SortGaika() {
        return fil310SortGaika__;
    }
    /**
     * <p>fil310SortGaika をセットします。
     * @param fil310SortGaika fil310SortGaika
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#fil310SortGaika__
     */
    public void setFil310SortGaika(String[] fil310SortGaika) {
        fil310SortGaika__ = fil310SortGaika;
    }
    /**
     * <p>fil310SortRadio を取得します。
     * @return fil310SortRadio
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#fil310SortRadio__
     */
    public String getFil310SortRadio() {
        return fil310SortRadio__;
    }
    /**
     * <p>fil310SortRadio をセットします。
     * @param fil310SortRadio fil310SortRadio
     * @see jp.groupsession.v2.fil.fil310.Fil310Form#fil310SortRadio__
     */
    public void setFil310SortRadio(String fil310SortRadio) {
        fil310SortRadio__ = fil310SortRadio;
    }
}