package jp.groupsession.v2.fil.fil330kn;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.AbstractFileAction;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.fil300.FilErrlAdddataException;
import jp.groupsession.v2.fil.fil330.Fil330Action;
import jp.groupsession.v2.fil.fil330.Fil330Form;
import jp.groupsession.v2.fil.fil330.Fil330ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

public class Fil330knAction extends AbstractFileAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil330Action.class);

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        ActionForward forward = null;
        Fil330knForm thisForm = (Fil330knForm) form;
        try {

            if (cmd.equals("fil330knDelComp")) {
                //削除確認完了ボタンクリック
                forward = __doCompDelete(map, thisForm, req, res, con);
            } else  if (cmd.equals("fil330knCancel")) {
                forward = map.findForward("cancel");
            } else {
                //削除ボタンクリック
                forward = __doDeleteCheck(map, thisForm, req, res, con);
            }
        } catch (FilErrlAdddataException e) {
            //一括削除画面実行時例外ハンドリング
            forward = __doException(map, thisForm, req, res, con, e);
        }

        return forward;
    }

    /**
     * <br>[機  能] 削除確認表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException 実行時例外
     */
    private ActionForward __doDeleteCheck(ActionMapping map,
            Fil330knForm thisForm, HttpServletRequest req,
            HttpServletResponse res, Connection con) throws SQLException, FilErrlAdddataException {
        ActionForward forward = null;
        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);
        con.setAutoCommit(true);
        ActionErrors errors = thisForm.validateDelete(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            //直接アクセスしてきた場合
            if (req.getAttribute("fil330Form") == null) {
                addErrors(req, errors);
            }
            return map.findForward("cancel");
        }

        // トランザクショントークン設定
        this.saveToken(req);

        Fil330knBiz biz = new Fil330knBiz(reqMdl, con);
        Fil330knParamModel paramMdl = new Fil330knParamModel();



        paramMdl.setParam(thisForm);
        biz.setInitDsp(paramMdl);
        paramMdl.setFormData(thisForm);

        StrutsUtil.addMessage(errors,
                new ActionMessage("sakujo.kakunin.once",
                        gsMsg.getMessage("fil.187")
                        ),
                "sakujo.kakunin.once");
        addErrors(req, errors);
        forward = map.getInputForward();
        return forward;
    }

    /**
     * <br>[機  能] 削除実行表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws SQLException, FilErrlAdddataException
     * @throws Exception 実行時例外
     */
    private ActionForward __doCompDelete(ActionMapping map, Fil330Form thisForm,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            ) throws SQLException, FilErrlAdddataException {

        ActionErrors errors = thisForm.validateDelete(con, getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.findForward("cancel");
        }

        RequestModel reqMdl = getRequestModel(req);
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        Fil330knBiz biz = new Fil330knBiz(reqMdl, con);

        List<String> filDirList =  new ArrayList<String>();
        boolean commitFlg = false;
        con.setAutoCommit(false);
        Fil330ParamModel paramMdl = new Fil330ParamModel();

        //削除処理
        try {
            paramMdl.setParam(thisForm);
            filDirList = biz.deleteDirectory(paramMdl);
            paramMdl.setFormData(thisForm);

            commitFlg = true;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage(req);

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        String delete = gsMsg.getMessage("cmn.delete");
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(gsMsg.getMessage(req, "cmn.target"));
        sb.append("] ");

        //listに格納したディレクトリ名を削除するディレクトリ名に入れていく
        for (String fName : filDirList) {
            sb.append("\r\n ");
            sb.append(fName);
        }
        //ログ出力処理
        filBiz.outPutLog(
                delete, GSConstLog.LEVEL_INFO,
                sb.toString(),
                map.getType());


        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("fil330knComp");
        cmn999Form.setUrlOK(forwardOk.getPath());


        //削除完了メッセージを取得
        String msg = msgRes.getMessage(
                "sakujo.kanryo.object",
                gsMsg.getMessage("fil.187")
                );
        cmn999Form.setMessage(msg);

        try {
            cmn999Form.addHiddenAll(paramMdl, Fil330ParamModel.class, "");
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | IntrospectionException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 画面独自例外処理をハンドリングする
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param thisForm フォーム
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @param e ハンドリングする例外
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doException(ActionMapping map, Fil330knForm thisForm,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            FilErrlAdddataException e) {
        return getSubmitErrorPage(map, req);
    }


}
