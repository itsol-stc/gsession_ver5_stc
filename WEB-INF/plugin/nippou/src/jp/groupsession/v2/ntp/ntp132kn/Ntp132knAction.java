package jp.groupsession.v2.ntp.ntp132kn;

import java.sql.Connection;
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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ntp.AbstractNippouMstmntAction;
import jp.groupsession.v2.ntp.dao.NtpShohinCategoryDao;
import jp.groupsession.v2.ntp.model.NtpShohinCategoryModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import jp.groupsession.v2.ntp.ntp132.Ntp132Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 商品インポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp132knAction  extends AbstractNippouMstmntAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp132knAction.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Ntp132knForm thisForm = (Ntp132knForm) form;

        //戻るボタン押下
        if (cmd.equals("backNtp132kn")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("ntp132");
        //実行ボタン押下
        } else if (cmd.equals("doImp")) {
            log__.debug("実行ボタン押下");
            forward = __doImport(map, thisForm, req, res, con);
        //初期表示
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, thisForm, req, res, con);
        }
        return forward;
    }


    /**
     * <br>[機  能] 確認画面の表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Ntp132knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //テンポラリディレクトリパスを取得
        Ntp132Biz biz132 = new Ntp132Biz(getRequestModel(req));
        String tempDir = biz132.getTempDir();

        //再入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(map, req, tempDir, con);
        con.setAutoCommit(false);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        NtpShohinCategoryDao cdao = new NtpShohinCategoryDao(con);
        NtpShohinCategoryModel mdl = null;
        mdl = cdao.select(form.getNtp132CatSid());
        if (mdl != null) {
            form.setNtp132knCategoryName(mdl.getNscName());
        } else {
            form.setNtp132knCategoryName("未設定");
        }


        String fileName = __getFileName(tempDir);
        form.setNtp132knFileName(fileName);

        //取込み予定情報を取得
        ShohinCsvImport csvImport = new ShohinCsvImport(req, 1);
        form.setNtp132knImpList(csvImport.importCsv(tempDir));

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 添付ファイルの名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @return String ファイル名
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    private String __getFileName(String tempDir) throws IOToolsException {
        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                ret = fMdl.getFileName();
                if (ret != null) {
                    return ret;
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] ユーザインポート処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doImport(ActionMapping map,
                                      Ntp132knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //テンポラリディレクトリパスを取得
        Ntp132Biz biz132 = new Ntp132Biz(getRequestModel(req));
        String tempDir = biz132.getTempDir();

        //再入力チェック
        con.setAutoCommit(true);
        ActionErrors errors = form.validateCheck(map, req, tempDir, con);
        con.setAutoCommit(false);
        if (errors.size() > 0) {
            log__.debug("再チェックNG");
            addErrors(req, errors);
            return map.getInputForward();
        }

        //取込み処理
        log__.debug("商品取り込み開始");
        boolean commit = false;
        try {
            con.setAutoCommit(false);
            MlCountMtController cntCon = null;
            //SID採番
            cntCon = getCountMtController(req);
            ShohinCsvImport csvImport =
                new ShohinCsvImport(req, cntCon, userSid, 0, con, form.getNtp132CatSid());

            ArrayList<NtpShohinModel> shohinList = csvImport.importCsv(tempDir);
            long num = 0;
            if (shohinList != null) {
                num = shohinList.size();
            }

            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ 削除 **/
            String strImport = gsMsg.getMessage("cmn.import");

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                    strImport, GSConstLog.LEVEL_INFO,
                    "[count]" + num);

            commit = true;
        } catch (Exception e) {
            log__.error("商品CSVの取り込みに失敗しました。" + e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }

            //テンポラリディレクトリを削除する
            biz132.deleteTempDir();
        }

        //完了画面遷移
        __setKanryou(map, req, form, "touroku.kanryo.object");
        return map.findForward("gf_msg");
    }

    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param form フォーム
     * @param msgState 完了画面に表示するメッセージのキー
     */
    private void __setKanryou(ActionMapping map,
                               HttpServletRequest req,
                               Ntp132knForm form,
                               String msgState) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        //商品
        String textShohin = "商品";

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("ntp130");
        cmn999Form.setUrlOK(urlForward.getPath());

        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textShohin));
        form.setNtp130HiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);
    }

}
