package jp.groupsession.v2.api.schedule.delete;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.api.schedule.edit.ApiSchEditBiz;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能]  スケジュール削除するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-delete",
plugin = "schedule", name = "スケジュール削除",
url = "/api/schedule/delete.do", reqtype = "DELETE")
public class ApiSchDeleteForm extends AbstractApiForm {

    /**Schsid スケジュールSID*/
    @ApiParam(name = "schSid", viewName = "スケジュールSID")
    private String schSid__;
    /** 同時登録スケジュール編集*/
    @ApiParam(name = "batchRef", viewName = "同時登録スケジュール", required = false, confRequired = true)
    private String batchRef__;
    /** 施設予約編集*/
    @ApiParam(name = "batchResRef", viewName = "施設予約編集", required = false, confRequired = true)
    private String batchResRef__;

    /**
     * <p>schSid を取得します。
     * @return schSid
     */
    public String getSchSid() {
        return schSid__;
    }

    /**
     * <p>schSid をセットします。
     * @param schSid schSid
     */
    public void setSchSid(String schSid) {
        schSid__ = schSid;
    }

    /**
     * <p>batchRef を取得します。
     * @return batchRef
     */
    public String getBatchRef() {
        return batchRef__;
    }

    /**
     * <p>batchRef をセットします。
     * @param batchRef batchRef
     */
    public void setBatchRef(String batchRef) {
        batchRef__ = batchRef;
    }

    /**
     * <p>batchResRef を取得します。
     * @return batchResRef
     */
    public String getBatchResRef() {
        return batchResRef__;
    }

    /**
     * <p>batchResRef をセットします。
     * @param batchResRef batchResRef
     */
    public void setBatchResRef(String batchResRef) {
        batchResRef__ = batchResRef;
    }
    /**
     *
     * <br>[機  能]入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GsMessage
     * @return エラー
     */
    public ActionErrors validateCheck(
            GsMessage gsMsg) {
        ActionErrors errors = new ActionErrors();
        GSValidateApi.validateSid(errors, schSid__, "schSid",
                gsMsg.getMessage("api.sch.sid"), true);
        return errors;
    }
    /**
     * <br>[機  能] 編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * TODO RelationBetweenScdAndRsvChkBizリファクタリング後 引数reqの除去を検討
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validatePowerCheck(
            HttpServletRequest req,
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ApiSchEditBiz eBiz = new ApiSchEditBiz(con, reqMdl, null);
        //同時登録スケジュールの編集権限チェック
        errors = eBiz.validateSchPowerCheck(errors,
                NullDefault.getInt(schSid__, -1),
                NullDefault.getInt(batchRef__, -1), 1);
        //同時登録施設予約の編集権限チェック
        errors = eBiz.validateResPowerCheck(errors,
                NullDefault.getInt(schSid__, -1),
                NullDefault.getInt(batchResRef__, -1));
        return errors;
    }



}
