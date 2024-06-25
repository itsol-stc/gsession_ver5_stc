package jp.groupsession.v2.zsk.zsk050;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.zsk.AbstractZaisekiAdminAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.GSValidateZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.dao.WkZaiIndexDao;
import jp.groupsession.v2.zsk.model.WkZaiIndexModel;
import jp.groupsession.v2.zsk.model.ZaiInfoPlusModel;
import jp.groupsession.v2.zsk.zsk010.Zsk010Form;

/**
 * <br>[機  能] 在席管理 座席表編集画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk050Action extends AbstractZaisekiAdminAction {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk050Action.class);

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

        Zsk050Form zskForm = (Zsk050Form) form;
        if (cmd.equals("zsk030")) {
            //戻る
            Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));
            biz.deleteTempDir();
            forward = map.findForward("zsk030");
        } else if (cmd.equals("zsk050delete")) {
            //削除確認
            forward = __setKakuninPageParam(map, req, zskForm, con);
        } else if (cmd.equals("zsk050deleteCommit")) {
            //削除処理実行
            forward = __doDeleteZaiInfo(map, zskForm, req, res, con);
        } else if (cmd.equals("zsk050kn")) {
            //編集確認
            forward = __doUpdateZaiIndex(map, zskForm, req, res, con);
        } else if (cmd.equals("imageDownLord")) {
            //イメージダウンロード
            forward = __doGetImageFile(map, zskForm, req, res, con);
        } else if (cmd.equals("getElmInfo")) {
            //非同期通信でエレメントの配置座標を取得する
            __setResponseIndexInfo(zskForm, req, res, con);
            return null;
        } else if (cmd.equals("setElmInfo")) {
            //非同期通信でエレメントの配置座標を一時保存する
            __doSaveUpdateZaiIndex(zskForm, req, res, con);
            return null;
        } else if (cmd.equals("addElmInfo")) {
            //非同期通信で追加されたエレメントの配置座標を一時保存する
            __doSaveAddZaiIndex(zskForm, req, res, con);
        } else if (cmd.equals("getElmName")) {
            //非同期通信でエレメントの名称を取得する
            __setResponseName(zskForm, req, res, con);
            return null;
        } else if (cmd.equals("delElmInfo")) {
            //非同期通信で削除されたエレメントの配置座標を一時保存から削除する
            __doDelZaiIndex(zskForm, req, res, con);
            return null;
        } else if (cmd.equals("getElmIndex")) {
            //非同期通信で移動されたエレメントの移動前座標を一時保存から取得する
            __setResponseIndex(zskForm, req, res, con);
            return null;
        } else if (cmd.equals("changeImage")) {
            //画像入れ替え
            forward = __doChangeImage(map, zskForm, req, res, con);
        } else {
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
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doInit(ActionMapping map, Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        Zsk050Biz biz = new Zsk050Biz(reqMdl);

        //初期表示の場合、テンポラリディレクトリを初期化
        String initFlg = NullDefault.getString(form.getInitFlg(), "");
        if (!initFlg.contentEquals(GSConstZaiseki.INIT_FLG_OFF)) {
            biz.deleteTempDir();

            GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
            tempPathUtil.createTempDir(reqMdl, GSConstZaiseki.PLUGIN_ID_ZAISEKI, "zsk050");
        }
        resetToken(req);
        String tempPath = biz.getTempDir();
        String appRoot = getAppRootPath();

        Zsk050ParamModel paramMdl = new Zsk050ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con, appRoot, tempPath,
                GroupSession.getResourceManager().getDomain(req));
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * エラーチェックを行い登録確認画面へ遷移する
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doUpdateZaiIndex(ActionMapping map, Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));
        //TEMPファイル名を取得
        String tempDir = biz.getTempDir();
        String fileName = biz.getTempImageFileName(tempDir);

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(fileName, req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Zsk050ParamModel paramMdl = new Zsk050ParamModel();
        paramMdl.setParam(form);
        biz.saveImageFile(paramMdl, tempDir);
        paramMdl.setFormData(form);

        //Token Save
        saveToken(req);
        return map.findForward("zsk050kn");
    }

    /**
     * 座席表情報を削除します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException 例外
     * @throws IOToolsException 例外
     * @return Forward
     */
    private ActionForward __doDeleteZaiInfo(ActionMapping map, Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, IOToolsException {
        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }
        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        int zifSid = NullDefault.getInt(form.getEditZifSid(), 0);
        Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));

        //ログ用 退避
        String targetName = biz.getLogMessage(zifSid, con);

        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            biz.deleteZaiInfoData(zifSid, sessionUsrSid, con);
            commitFlg = true;

        } catch (jp.co.sjts.util.io.IOToolsException e) {
            e.printStackTrace();
            log__.error("座席表の削除に失敗しました。");
        } catch (SQLException e) {
            e.printStackTrace();
            log__.error("座席表の削除に失敗しました。");
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
            //テンポラリディレクトリ削除
            biz.deleteTempDir();
        }

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.delete");

        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));

        cmnBiz.outPutLog(con,
                         msg, GSConstLog.LEVEL_INFO,
                         "[" + gsMsg.getMessage("zsk.29") + "]" + targetName, map.getType());

        return __doDeleteCompDsp(map, req, form);
    }
    /**
     * レスポンスにJSON形式の文字列を設定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws UnsupportedEncodingException エンコードエラー
     * @throws SQLException SQLエラー
     * @throws IOException JSON出力エラー
     * @throws UnsupportedEncodingException JSONエンコードエラー
     */
    private void __setResponseIndexInfo(Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IOException, UnsupportedEncodingException {
        log__.debug("非同期通信でエレメントの配置座標を取得する");

        con.setAutoCommit(true);
        HttpSession session = req.getSession(true);
        String sessionId = session.getId();
        //ワークテーブルから画面項目情報を取得しJSON形式でレスポンスを返す
        int dspZifSid = NullDefault.getInt(form.getEditZifSid(), 0);
        WkZaiIndexDao wkDao = new WkZaiIndexDao(con);
        ArrayList<Zsk050ElementModel> wkIndexList = wkDao.getEdittingIndex(sessionId, dspZifSid);

        log__.debug("jsessionid = " + sessionId);
        int index = 0;

        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append("\"zasekielement\":[");
        if (wkIndexList.size() > 0) {
            for (Zsk050ElementModel mdl : wkIndexList) {
                if (index > 0) {
                    buf.append(",");
                }
                index++;
                buf.append("{");
                buf.append("\"linkKey\":\"" + mdl.getWziKey() + "\",");
                buf.append("\"linkKbn\":\"" + mdl.getWziLinkkbn() + "\",");
                buf.append("\"linkSid\":\"" + mdl.getWziLinksid() + "\",");
                if (mdl.getWziLinkkbn() == GSConstZaiseki.ELEMENT_KBN_ETC) {
                    buf.append("\"linkName\":" + "\"" + mdl.getWziOtherValue() + "\",");
                } else {
                    buf.append("\"linkName\":" + "\"" + mdl.getWziName() + "\",");
                }
                buf.append("\"linkNameClass\":" + "\""
                + mdl.getCSSClassNameNormal() + "\",");

                buf.append("\"linkX\":\"" + mdl.getWziXindex() + "\",");
                buf.append("\"linkY\":\"" + mdl.getWziYindex() + "\"");
                buf.append("}");
            }

        }
        buf.append("]");
        buf.append("}");
        log__.debug("<==JSON==>");
        log__.debug(buf.toString());
        res.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(buf.toString());
        out.flush();
        out.close();

    }
    /**
     * 追加したエレメント名称をレスポンスにJSON形式の文字列を設定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws UnsupportedEncodingException エンコードエラー
     * @throws SQLException SQLエラー
     * @throws IOException JSON出力エラー
     * @throws UnsupportedEncodingException JSONエンコードエラー
     */
    private void __setResponseName(Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IOException, UnsupportedEncodingException {
        log__.debug("非同期通信でエレメントの名前を取得する");

        con.setAutoCommit(true);
        HttpSession session = req.getSession(true);
        String sessionId = session.getId();
        //ワークテーブルから画面項目情報を取得しJSON形式でレスポンスを返す
        int linkKbn = NullDefault.getInt(form.getAddElKbn(), 0);
        int linkSid = NullDefault.getInt(form.getAddElSid(), 0);
        ZsjCommonBiz biz = new ZsjCommonBiz(getRequestModel(req));
        UsrLabelValueBean label = biz.getDspElementName(linkKbn, linkSid, con);

        log__.debug("jsessionid = " + sessionId);

        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append("\"zasekielement\":[");
        buf.append("{");
        buf.append("\"linkKbn\":\"" + linkKbn + "\",");
        buf.append("\"linkSid\":\"" + linkSid + "\",");
        buf.append("\"linkName\":" + "\"" + label.getLabel() + "\",");
        buf.append("\"linkNameClass\":" + "\""
                + label.getCSSClassNameNormal() + "\",");
        buf.append("}");
        buf.append(",");
        buf.append("{");
        buf.append("\"linkKbn\":\"" + linkKbn + "\",");
        buf.append("\"linkSid\":\"" + linkSid + "\",");
        buf.append("\"linkName\":" + "\"" + label.getLabel() + "\",");
        buf.append("\"linkNameClass\":" + "\""
                + label.getCSSClassNameNormal() + "\",");
        buf.append("}");
        buf.append("]");
        buf.append("}");

        log__.debug("<==JSON==>");
        log__.debug(buf.toString());
        res.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(buf.toString());
        out.flush();
        out.close();

    }


    /**
     * 移動したエレメントの移動前座標をレスポンスにJSON形式の文字列を設定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws UnsupportedEncodingException エンコードエラー
     * @throws SQLException SQLエラー
     * @throws IOException JSON出力エラー
     * @throws UnsupportedEncodingException JSONエンコードエラー
     */
    private void __setResponseIndex(Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IOException, UnsupportedEncodingException {
        log__.debug("非同期通信でエレメントの移動前表示座標を取得する");

        con.setAutoCommit(true);
        HttpSession session = req.getSession(true);
        String sessionId = session.getId();
        //ワークテーブルから画面項目情報を取得しJSON形式でレスポンスを返す
        int dspZifSid = NullDefault.getInt(form.getEditZifSid(), 0);
        String linkKey = NullDefault.getString(form.getElKey(), "");
        WkZaiIndexDao wkDao = new WkZaiIndexDao(con);
        Zsk050ElementModel mdl = wkDao.getWkZasekiIndex(sessionId, dspZifSid, linkKey);

        log__.debug("jsessionid = " + sessionId);

        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append("\"zasekielement\":[");
        if (mdl != null) {

            buf.append("{");
            buf.append("\"linkKey\":\"" + mdl.getWziKey() + "\",");
            buf.append("\"linkKbn\":\"" + mdl.getWziLinkkbn() + "\",");
            buf.append("\"linkSid\":\"" + mdl.getWziLinksid() + "\",");
            if (mdl.getWziLinkkbn() == GSConstZaiseki.ELEMENT_KBN_ETC) {
                buf.append("\"linkName\":" + "\"" + mdl.getWziOtherValue() + "\",");
            } else {
                buf.append("\"linkName\":" + "\"" + mdl.getWziName() + "\",");
            }
            buf.append("\"linkNameClass\":" + "\""
            + mdl.getCSSClassNameNormal() + "\",");
            buf.append("\"linkX\":\"" + mdl.getWziXindex() + "\",");
            buf.append("\"linkY\":\"" + mdl.getWziYindex() + "\"");
            buf.append("}");


        }
        buf.append("]");
        buf.append("}");
        log__.debug("<==JSON==>");
        log__.debug(buf.toString());
        res.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.print(buf.toString());
        out.flush();
        out.close();

    }

    /**
     * 画面項目値を一時保存します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL更新時例外
     */
    private void __doSaveUpdateZaiIndex(Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        HttpSession session = req.getSession(true);
        String sessionId = session.getId();
        int dspZifSid = NullDefault.getInt(form.getEditZifSid(), 0);
        int xIndex = NullDefault.getInt(form.getIndexx(), 0);
        int yIndex = NullDefault.getInt(form.getIndexy(), 0);
        String elKey = NullDefault.getString(form.getElKey(), "");
        log__.debug("一時保存データ内容==>");
        log__.debug("dspZifSid==>" + dspZifSid);
        log__.debug("xIndex==>" + xIndex);
        log__.debug("yIndex==>" + yIndex);
        log__.debug("elKey==>" + elKey);
        Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));
        WkZaiIndexModel bean = new WkZaiIndexModel();
        bean.setWziXindex(xIndex);
        bean.setWziYindex(yIndex);
        con.setAutoCommit(false);
        boolean commitFlg = false;
        try {
            biz.updateWkElementData(sessionId, dspZifSid, elKey, bean, con);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ワークテーブルの更新に失敗しました。" + e);
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
    }
    /**
     * 画面項目値を一時保存します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL更新時例外
     */
    private void __doSaveAddZaiIndex(Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        HttpSession session = req.getSession(true);
        String sessionId = session.getId();
        int dspZifSid = NullDefault.getInt(form.getEditZifSid(), 0);
        int xIndex = NullDefault.getInt(form.getIndexx(), 0);
        int yIndex = NullDefault.getInt(form.getIndexy(), 0);
        String elKey = NullDefault.getString(form.getElKey(), "");
        //
        int kbn = NullDefault.getInt(form.getAddElKbn(), 0);
        int sid = NullDefault.getInt(form.getAddElSid(), 0);
        String msg = NullDefault.getString(form.getAddElMsg(), "");

        log__.debug("一時保存データ内容==>");
        log__.debug("dspZifSid==>" + dspZifSid);
        log__.debug("xIndex==>" + xIndex);
        log__.debug("yIndex==>" + yIndex);
        log__.debug("elKey==>" + elKey);
        log__.debug("addElKbn==>" + kbn);
        log__.debug("addElSid==>" + sid);
        log__.debug("addElMsg==>" + msg);

        Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));
        WkZaiIndexModel bean = new WkZaiIndexModel();
        //表示名を取得する
        ZsjCommonBiz cBiz = new ZsjCommonBiz(getRequestModel(req));
        UsrLabelValueBean label = cBiz.getDspElementName(kbn, sid, con);
        //新規登録
        bean.setWziSessionSid(sessionId);
        bean.setWziKey(elKey);
        bean.setWziSid(dspZifSid);
        bean.setWziLinkkbn(kbn);
        bean.setWziLinksid(sid);
        bean.setWziName(label.getLabel());
        bean.setWziBgcolor(0);

        bean.setWziXindex(xIndex);
        bean.setWziYindex(yIndex);
        bean.setWziOtherValue(msg);

        con.setAutoCommit(false);
        boolean commitFlg = false;
        try {
            biz.insertWkElementData(bean, con);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ワークテーブルへのレコード追加に失敗しました。" + e);
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
    }

    /**
     * 画面項目値を一時保存から削除します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL更新時例外
     */
    private void __doDelZaiIndex(Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        HttpSession session = req.getSession(true);
        String sessionId = session.getId();
        int dspZifSid = NullDefault.getInt(form.getEditZifSid(), 0);
        String elKey = NullDefault.getString(form.getElKey(), "");

        log__.debug("削除する一時保存データ内容==>");
        log__.debug("dspZifSid==>" + dspZifSid);
        log__.debug("elKey==>" + elKey);

        Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));

        con.setAutoCommit(false);
        boolean commitFlg = false;
        try {
            biz.deleteWkElementData(sessionId, dspZifSid, elKey, con);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("ワークテーブルへのレコード追加に失敗しました。" + e);
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            Zsk010Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        //テンポラリディレクトリパス
        Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));
        String tempDir = biz.getTempDir();
        String imageDir = biz.getDspImageFilePath(tempDir);
        
        File files = new File(imageDir);
        File file = files.listFiles()[0];
        String fileName = file.getName();
        TempFileUtil.downloadInline(req, res, imageDir + fileName, fileName, Encoding.UTF_8);

        return null;
    }
    /**
     * <br>[機  能] 削除確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Zsk050Form form,
        Connection con) throws SQLException {

        con.setAutoCommit(true);
        String mapName = "";
        int zifSid = NullDefault.getInt(
                form.getEditZifSid(), -1);
        ZsjCommonBiz biz = new ZsjCommonBiz(getRequestModel(req));
        ZaiInfoPlusModel infoMdl = biz.getZaiInfoPlusModel(zifSid, con);
        if (infoMdl != null) {
            mapName = NullDefault.getString(infoMdl.getZifName(), "");
        }

        // トランザクショントークン設定
        this.saveToken(req);

        //確認画面へ
        log__.debug("削除確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        urlForward = map.findForward("zsk050deleteCommit");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("zsk050deleteCancel");
        cmn999Form.setUrlCancel(urlForward.getPath());

        //メッセージセット
        urlForward = map.findForward("zasekiList");

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "zsk.29");

        cmn999Form.setMessage(msgRes.getMessage("sakujo.kakunin.list",
                msg,
                StringUtilHtml.transToHTmlPlusAmparsant(mapName)));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("initFlg", form.getInitFlg());
        cmn999Form.addHiddenParam("editZifSid", form.getEditZifSid());

        cmn999Form.addHiddenParam("zasekiMapName", form.getZasekiMapName());
        cmn999Form.addHiddenParam("selectGroup", form.getSelectGroup());
        cmn999Form.addHiddenParam("selectRsvGroup", form.getSelectRsvGroup());
        cmn999Form.addHiddenParam("commentValue", form.getCommentValue());
        cmn999Form.addHiddenParam("selectZifSid", form.getSelectZifSid());
        cmn999Form.addHiddenParam("uioStatus", form.getUioStatus());
        cmn999Form.addHiddenParam("uioStatusBiko", form.getUioStatusBiko());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * 削除完了メッセージ画面へ遷移する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param form フォーム
     * @return ActionForward
     */
    private ActionForward __doDeleteCompDsp(ActionMapping map,
            HttpServletRequest req,
            Zsk050Form form) {
        ActionForward forward = null;

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "zsk.29");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;
        urlForward = map.findForward("zsk030");
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage("sakujo.kanryo.object",
                msg));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("initFlg", form.getInitFlg());
        cmn999Form.addHiddenParam("editZifSid", form.getEditZifSid());

        cmn999Form.addHiddenParam("selectZifSid", form.getSelectZifSid());
        cmn999Form.addHiddenParam("uioStatus", form.getUioStatus());
        cmn999Form.addHiddenParam("uioStatusBiko", form.getUioStatusBiko());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());
        req.setAttribute("cmn999Form", cmn999Form);
        forward = map.findForward("gf_msg");

        return forward;
    }

    /**
     * 画像入れ替え処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doChangeImage(ActionMapping map, Zsk050Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //テンポラリディレクトリ取得
        Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));
        String tempDir = biz.getTempDir();
        //添付画像チェック
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
        resetToken(req);
        //エラー無し
        //座席表画像を表示用テンポラリへ保存
        Zsk050ParamModel paramMdl = new Zsk050ParamModel();
        paramMdl.setParam(form);
        biz.saveImageFile(paramMdl, tempDir);
        String appRoot = getAppRootPath();
        biz.getInitData(paramMdl, con, appRoot, tempDir,
                GroupSession.getResourceManager().getDomain(req));
        paramMdl.setFormData(form);

        //画像表示用乱数
        Random rnd = new Random();
        int ran = rnd.nextInt(100);
        form.setZsk050RndNum(ran);

        return map.getInputForward();
    }

}
