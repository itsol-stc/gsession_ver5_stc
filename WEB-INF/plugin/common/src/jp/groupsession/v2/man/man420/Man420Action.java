package jp.groupsession.v2.man.man420;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.AutoImportListnerImpl;

/**
 *
 * <br>[機  能]ユーザ連携自動インポート機能設定画面
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man420Action extends AdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man420Action.class);

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        //ダウンロードフラグ
        String downLoadFlg = NullDefault.getString(req.getParameter("sample"), "");
        downLoadFlg = downLoadFlg.trim();

        //sample01:ユーザ自動インポートサンプル
        if (cmd.equals("man420_sample01")) {
            if (downLoadFlg.equals("1")) {
                log__.debug("サンプルCSVファイルダウンロード");
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
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
        Man420Form thisForm = (Man420Form) form;

        //クラウド版の場合は不正なアクセス
        if (!GroupSession.getResourceManager().getDomain(req).equals(GSConst.GS_DOMAIN)) {
            forward = map.findForward("gf_submit");
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("420_back")) {
            //メイン管理者設定に戻る
            forward = map.findForward("420_back");
        } else if (cmd.equals("setting")) {
            //確認画面へ遷移する
            forward = __doSetting(thisForm, req, map, con);
        } else if (cmd.equals("usrExeConf")) {
            //ユーザインポート強制実行確認画面
            forward = __confUsrImport(map, req, thisForm);
        } else if (cmd.equals("batchJobOk")) {
            forward = executeUsrImp(con, req, map, thisForm);
        } else if (cmd.equals("man420_sample01")) {
            //通常sample_user_auto_import.xlsリンククリック
            log__.debug("sample_user_auto_import.xlsダウンロード");
            __doSampleDownLoad(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doDsp(thisForm, map, req, con);
        }

        return forward;
    }

    /**
     * <br>[機  能]表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param thisForm アクションフォーム
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOTools実行例外
     */
    private ActionForward __doDsp(Man420Form thisForm,
            ActionMapping map,
            HttpServletRequest req,
            Connection con) throws SQLException, IOToolsException {

        ActionForward forward = null;
        Man420ParamModel paramMdl = new Man420ParamModel();
        Man420Biz biz = new Man420Biz();

        //初期表示用のデータを取得
        paramMdl.setParam(thisForm);
        biz.setInitData(getRequestModel(req), paramMdl, con, this.getAppRootPath());
        paramMdl.setFormData(thisForm);

        forward = map.getInputForward();
        return forward;
    }

    /**
     *
     * <br>[機  能]インポート時間確認画面へ遷移するに伴い値のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param thisForm アクションフォーム
     * @param req リクエスト
     * @param map アクションマッピング
     * @param con コネクション
     * @return アクションフォワード
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOTools実行時例外
     */
    private ActionForward __doSetting(Man420Form thisForm,
            HttpServletRequest req, ActionMapping map, Connection con)
                    throws SQLException, IOToolsException {

        //登録データチェック
        ActionErrors errors = thisForm.validateForm(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(thisForm, map, req, con);
        }

        return map.findForward("setting");
    }

    /**
     *
     * <br>[機  能]ユーザの強制インポート確認画面を表示する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param thisForm アクションフォーム
     * @return アクションフォワード
     */
    private ActionForward __confUsrImport(ActionMapping map, HttpServletRequest req,
            Man420Form thisForm) {
        Cmn999Form cmn999Form = new Cmn999Form();

        //表示件数設定完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage(getRequestModel(req));

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setMessage(
                msgRes.getMessage("execute.kakunin.once", gsMsg.getMessage("main.man420.1")));

        cmn999Form.setUrlOK(map.findForward("executeUsr").getPath());
        cmn999Form.setUrlCancel(map.findForward("420_init").getPath());

        //パラメータをセットする
        cmn999Form.addHiddenParam("man420UsrImpFlg", thisForm.getMan420UsrImpFlg());
        cmn999Form.addHiddenParam("man420UsrFrHour", thisForm.getMan420UsrFrHour());
        cmn999Form.addHiddenParam("man420InitFlg", thisForm.getMan420InitFlg());
        cmn999Form.addHiddenParam("man420UsrImpTimeSelect", thisForm.getMan420UsrImpTimeSelect());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     *
     * <br>[機  能]ユーザの強制インポートを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param map アクションマッピング
     * @param thisForm アクションフォーム
     * @return アクションフォワード
     * @throws Exception 実行時例外
     */
    private ActionForward executeUsrImp(Connection con, HttpServletRequest req,
            ActionMapping map, Man420Form thisForm) throws Exception {

        if (AutoImportListnerImpl.isImportActive()) {
            //ユーザ自動連係処理実行中
            return __errImportActive(map, req, thisForm);
        }
        AutoImportListnerImpl impLis = new AutoImportListnerImpl();
        //0:失敗 1:完了 2:インポートファイルなし 3:ユーザ自動連係処理実行中
        int finishFlg = impLis.autoUsrImport(
                con, this.getAppRootPath(), getRequestModel(req).getDomain(), false);

        if (finishFlg == 3) {
            //ユーザ自動連係処理実行中
            return __errImportActive(map, req, thisForm);
        }

        if (finishFlg == 1) {
            __outPutLog(req, con, map, thisForm);
        } else if (finishFlg == 0) {
            __outPutFailLog(req, con, map, thisForm);
        }

        return __compUsrImport(map, req, thisForm, finishFlg);

    }
    /**
     *
     * <br>[機  能] ユーザ自動連係処理 二重実行防止エラーメッセージ画面を表示する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param thisForm アクションフォーム
     * @return メッセージ画面への遷移
     */
    private ActionForward __errImportActive(ActionMapping map,
            HttpServletRequest req, Man420Form thisForm) {
        Cmn999Form cmn999Form = new Cmn999Form();

        //表示件数設定完了画面パラメータの設定
        GsMessage gsMsg = new GsMessage(getRequestModel(req));

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);

        cmn999Form.setMessage(gsMsg.getMessage("main.man420.17"));

        cmn999Form.setUrlOK(map.findForward("420_back").getPath());

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
    *
    * <br>[機  能]ユーザの強制インポート完了画面を表示する
    * <br>[解  説]
    * <br>[備  考]
    * @param map アクションマッピング
    * @param req リクエスト
    * @param thisForm アクションフォーム
    * @param finishFlg ユーザインポート完了フラグ(0:失敗 1:完了 2:インポートファイルなし)
    * @return アクションフォワード
    */
   private ActionForward __compUsrImport(ActionMapping map, HttpServletRequest req,
           Man420Form thisForm, int finishFlg) {
       Cmn999Form cmn999Form = new Cmn999Form();

       //表示件数設定完了画面パラメータの設定
       MessageResources msgRes = getResources(req);
       GsMessage gsMsg = new GsMessage(getRequestModel(req));

       cmn999Form.setType(Cmn999Form.TYPE_OK);
       cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
       if (finishFlg == 1) {
           cmn999Form.setIcon(Cmn999Form.ICON_INFO);
           cmn999Form.setMessage(
                   msgRes.getMessage("comp.todo.import", gsMsg.getMessage("main.man420.1")));
       } else if (finishFlg == 0) {
           cmn999Form.setIcon(Cmn999Form.ICON_WARN);
           cmn999Form.setMessage(
                   msgRes.getMessage("error.fail", gsMsg.getMessage("main.man420.1")));
       } else if (finishFlg == 2) {
           cmn999Form.setIcon(Cmn999Form.ICON_WARN);
           cmn999Form.setMessage(gsMsg.getMessage("main.man420.3"));
       }

       cmn999Form.setUrlOK(map.findForward("420_back").getPath());

       req.setAttribute("cmn999Form", cmn999Form);

       return map.findForward("gf_msg");
   }

   /**
   *
   * <br>[機  能]オペレーションログを出力する
   * <br>[解  説]
   * <br>[備  考]
   * @param req リクエスト
   * @param con コネクション
   * @param map アクションマッピング
   * @param thisForm アクションフォーム
   */
   private void __outPutLog(
           HttpServletRequest req,
           Connection con,
           ActionMapping map,
           Man420Form thisForm) {

           //ログ出力
           RequestModel reqMdl = getRequestModel(req);
           CommonBiz cmnBiz = new CommonBiz();
           MessageResources msgRes = getResources(req);
           GsMessage gsMsg = new GsMessage(reqMdl);

           //ログの本文を作成
           Man420ParamModel paramMdl = new Man420ParamModel();
           paramMdl.setParam(thisForm);
           Man420Biz biz = new Man420Biz();
           String logValue = biz.setLogValue(paramMdl, msgRes, gsMsg);
           String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                               + gsMsg.getMessage("main.man420.1");
           cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                   gsMsg.getMessage("cmn.change"),
                   GSConstLog.LEVEL_INFO, logValue, dspName);

   }

   /**
   *
   * <br>[機  能]強制実行失敗時のオペレーションログを出力する
   * <br>[解  説]
   * <br>[備  考]
   * @param req リクエスト
   * @param con コネクション
   * @param map アクションマッピング
   * @param thisForm アクションフォーム
   */
   private void __outPutFailLog(
           HttpServletRequest req,
           Connection con,
           ActionMapping map,
           Man420Form thisForm) {

           //ログ出力
           RequestModel reqMdl = getRequestModel(req);
           CommonBiz cmnBiz = new CommonBiz();
           MessageResources msgRes = getResources(req);
           GsMessage gsMsg = new GsMessage(reqMdl);

           //ログの本文を作成
           Man420ParamModel paramMdl = new Man420ParamModel();
           paramMdl.setParam(thisForm);
           Man420Biz biz = new Man420Biz();
           String logValue = biz.setFailLogValue(paramMdl, msgRes, gsMsg);
           String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                   + gsMsg.getMessage("main.man420.1");
           cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                   gsMsg.getMessage("cmn.change"),
                   GSConstLog.LEVEL_INFO, logValue, dspName);

   }

   /**
    *
    * <br>[機  能]サンプルファイルのダウンロードを行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map アクションマッピング
    * @param thisForm アクションフォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @throws Exception 実行時例外
    */
   private void __doSampleDownLoad(
           ActionMapping map,
           Man420Form thisForm,
           HttpServletRequest req,
           HttpServletResponse res,
           Connection con) throws Exception {

       String fileName = null;

       // モードを取得
       String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
       if (cmd.equals("man420_sample01")) {
               fileName = GSConst.AUTO_IMPORT_SAMPLE;
       }

       StringBuilder buf = new StringBuilder();
       buf.append(getAppRootPath());
       buf.append(File.separator);
       buf.append(GSConst.PLUGINID_MAIN);
       buf.append(File.separator);
       buf.append("doc");
       buf.append(File.separator);
       buf.append(fileName);
       String fullPath = buf.toString();
       log__.debug("FULLPATH=" + fullPath);
       TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

       GsMessage gsMsg = new GsMessage(req);
       /** メッセージ ダウンロード **/
       String download = gsMsg.getMessage("cmn.download");

       //ログ出力
       CommonBiz cmnBiz = new CommonBiz();
       String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
               + gsMsg.getMessage("main.man420.1");
       cmnBiz.outPutLogNoDspName(map, getRequestModel(req), gsMsg, con,
               download, GSConstLog.LEVEL_INFO, fileName, dspName);

   }
}
