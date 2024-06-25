package jp.groupsession.v2.zsk.zsk040;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.zsk.AbstractZaisekiAdminAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.GSValidateZaiseki;

/**
 * <br>[機  能] 在席管理 座席表登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk040Action extends AbstractZaisekiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk040Action.class);

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Zsk040Form zskForm = (Zsk040Form) form;
        Zsk040Biz biz = new Zsk040Biz();
        if (cmd.equals("zsk030")) {
            //戻る
            biz.deleteTempDir(getRequestModel(req));
            forward = map.findForward("zsk030");
        } else if (cmd.equals("zsk040kn")) {
            //在席表追加
            forward = __doInsertZasekiMap(map, zskForm, req, res, con);
        } else if (cmd.equals("delTempFile")) {
            //添付ファイル削除
            forward = __doDelete(map, zskForm, req, res, con);
        } else {
            if (zskForm.getZsk040initFlg() != 1) {
                biz.deleteTempDir(getRequestModel(req));
            }
            //初期表示
            forward = __doInit(map, zskForm, req, res, con);
        }
        return forward;
    }

    /**
     * 初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException 例外
     * @throws IOException 例外
     * @throws IOToolsException 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, Zsk040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IOException, IOToolsException {
        con.setAutoCommit(true);

        Zsk040Biz biz = new Zsk040Biz();
        RequestModel reqMdl = getRequestModel(req);

        //初期表示の場合、テンポラリディレクトリを初期化
        if (form.getZsk040initFlg() != 1) {
            biz.deleteTempDir(reqMdl);

            GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
            tempPathUtil.createTempDir(reqMdl, GSConstZaiseki.PLUGIN_ID_ZAISEKI, "zsk040");
        }

        Zsk040ParamModel paramMdl = new Zsk040ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con, getAppRootPath(),
                        biz.getTempDir(reqMdl));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * 座席表登録チェックを行いエラーが無い場合は確認画面へ遷移する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return Forward
     * @throws SQLException 例外
     * @throws IOException ファイル書込み時例外
     * @throws IOToolsException ファイル書込み時例外
     */
    private ActionForward __doInsertZasekiMap(ActionMapping map, Zsk040Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IOException, IOToolsException {

        //テンポラリディレクトリ取得
        Zsk040Biz biz = new Zsk040Biz();
        String tempDir = biz.getTempDir(getRequestModel(req));

        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        GSValidateZaiseki valZsk = new GSValidateZaiseki();
        ArrayList<String> list = valZsk.getTempFileName(tempDir);
        String fileName = "";
        if (list.size() > 0) {
            fileName = list.get(0);
        }

        errors = form.validateCheck(fileName, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);

        forward = map.findForward("zsk040kn");
        return forward;
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws SQLException 実行例外
     * @return ActionForward
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Zsk040Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws IOException, IOToolsException, SQLException {

        //選択された添付ファイルを削除する
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteFile(form.getZsk040file(),
                            getRequestModel(req),
                            GSConstZaiseki.PLUGIN_ID_ZAISEKI,
                            "zsk040");

        return __doInit(map, form, req, res, con);
    }

}
