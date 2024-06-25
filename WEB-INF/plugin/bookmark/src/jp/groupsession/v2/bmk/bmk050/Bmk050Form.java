package jp.groupsession.v2.bmk.bmk050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.bmk010.Bmk010Form;
import jp.groupsession.v2.bmk.model.BmkLabelDataModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ラベル管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk050Form extends Bmk010Form {
    /** ラベルリスト */
    private List<BmkLabelDataModel> bmk050LabelList__ = null;
    /** ラベルSID */
    private int bmk050LblSid__ = -1;
    /** 処理モード */
    private int bmk050ProcMode__ = GSConstBookmark.LABEL_MODE_TOUROKU;
    /** チェックボックスリスト */
    private String[] bmk050DelSidList__ = null;
    /** グループ名 */
    private String bmk050GrpName__ = null;
    /**
     * <p>bmk050LabelList を取得します。
     * @return bmk050LabelList
     */
    public List<BmkLabelDataModel> getBmk050LabelList() {
        return bmk050LabelList__;
    }
    /**
     * <p>bmk050LabelList をセットします。
     * @param bmk050LabelList bmk050LabelList
     */
    public void setBmk050LabelList(List<BmkLabelDataModel> bmk050LabelList) {
        bmk050LabelList__ = bmk050LabelList;
    }

    /**
     * <p>bmk050lblSid を取得します。
     * @return bmk050lblSid
     */
    public int getBmk050LblSid() {
        return bmk050LblSid__;
    }
    /**
     * <p>bmk050LblSid をセットします。
     * @param bmk050LblSid bmk050lblSid
     */
    public void setBmk050LblSid(int bmk050LblSid) {
        bmk050LblSid__ = bmk050LblSid;
    }
    /**
     * <p>bmk050ProcMode を取得します。
     * @return bmk050ProcMode
     */
    public int getBmk050ProcMode() {
        return bmk050ProcMode__;
    }
    /**
     * <p>bmk050ProcMode をセットします。
     * @param bmk050ProcMode bmk050ProcMode
     */
    public void setBmk050ProcMode(int bmk050ProcMode) {
        bmk050ProcMode__ = bmk050ProcMode;
    }
    /**
     * <p>bmk050DelSidList を取得します。
     * @return bmk050DelSidList
     */
    public String[] getBmk050DelSidList() {
        return bmk050DelSidList__;
    }
    /**
     * <p>bmk050DelSidList をセットします。
     * @param bmk050DelSidList bmk050DelSidList
     */
    public void setBmk050DelSidList(String[] bmk050DelSidList) {
        bmk050DelSidList__ = bmk050DelSidList;
    }
    /**
     * <p>bmk050GrpName を取得します。
     * @return bmk050GrpName
     */
    public String getBmk050GrpName() {
        return bmk050GrpName__;
    }
    /**
     * <p>bmk050GrpName をセットします。
     * @param bmk050GrpName bmk050GrpName
     */
    public void setBmk050GrpName(String bmk050GrpName) {
        bmk050GrpName__ = bmk050GrpName;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage();
        String label = gsMsg.getMessage(req, "cmn.label");

        if (bmk050DelSidList__ == null || bmk050DelSidList__.length == 0) {
            String msgKey = "error.select.required.text";
            ActionMessage msg = new ActionMessage(msgKey, label);
            StrutsUtil.addMessage(
                    errors, msg, "bmk050DelSidList." + msgKey);
        }

        return errors;
    }

    /**
     * <br>[機  能] 削除対象の権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param umodel セッションユーザ情報
     * @return true:権限あり/false:権限なし
     * @throws SQLException 
     */
    public boolean validateCheckPower(
            Connection con, RequestModel reqMdl, BaseUserModel umodel) throws SQLException {

        //配列内に数値以外が存在しないか確認
        boolean numCheck = Stream.of(bmk050DelSidList__)
                .allMatch(str -> ValidateUtil.isNumber(str));
        if (!numCheck) {
            return false;
        }
        BookmarkBiz bmkBiz = new BookmarkBiz(con, reqMdl);
        List<Integer> delSidList = Stream.of(bmk050DelSidList__)
                .map(Integer::valueOf).collect(Collectors.toList());

        return bmkBiz.isEditLabel(con, umodel, -1, -1,
                GSConstBookmark.BMK_MODE_EDIT, delSidList);
    }
}