package jp.groupsession.v2.zsk.zsk050kn;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.AbstractZaisekiAdminAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.biz.ZskUsedDataBiz;
import jp.groupsession.v2.zsk.dao.WkZaiIndexDao;
import jp.groupsession.v2.zsk.zsk050.Zsk050Biz;
import jp.groupsession.v2.zsk.zsk050.Zsk050ElementModel;

/**
 * <br>[機  能] 在席管理 座席表編集確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk050knAction extends AbstractZaisekiAdminAction {



    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk050knAction.class);

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

        Zsk050knForm zskForm = (Zsk050knForm) form;
        if (cmd.equals("zsk050")) {
            //戻る
            forward = map.findForward("zsk050");
        } else if (cmd.equals("zsk050knCommit")) {
            //登録
            forward = __doUpdateZaiIndex(map, zskForm, req, res, con);
        } else if (cmd.equals("imageDownLord")) {
            //イメージダウンロード
            forward = __doGetImageFile(map, zskForm, req, res, con);
        } else if (cmd.equals("getElmInfo")) {
            //非同期通信でエレメントの配置座標を取得する
            __setResponseIndexInfo(zskForm, req, res, con);
            return null;
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
    private ActionForward __doInit(ActionMapping map, Zsk050knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        Zsk050Biz biz050 = new Zsk050Biz(reqMdl);
        String tempPath = biz050.getTempDir();

        String appRoot = getAppRootPath();
        Zsk050knBiz biz = new Zsk050knBiz(reqMdl);

        Zsk050knParamModel paramMdl = new Zsk050knParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con, appRoot, tempPath);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * 座席表編集情報を更新する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return Forward
     */
    private ActionForward __doUpdateZaiIndex(ActionMapping map, Zsk050knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリパス取得
        RequestModel reqMdl = getRequestModel(req);
        Zsk050Biz biz050 = new Zsk050Biz(reqMdl);
        String tempDir = biz050.getTempDir();

        //更新処理
        Zsk050knBiz biz = new Zsk050knBiz(getRequestModel(req));
        MlCountMtController cntCon = getCountMtController(req);

        boolean commitFlg = false;
        try {

            Zsk050knParamModel paramMdl = new Zsk050knParamModel();
            paramMdl.setParam(form);

            //座席表情報のデータ使用量を登録(変更前のデータ使用量を減算)
            int editZifSid = NullDefault.getInt(paramMdl.getEditZifSid(), 0);
            ZskUsedDataBiz usedDataBiz = new ZskUsedDataBiz(con);
            usedDataBiz.insertZasekiSize(editZifSid, false);

            biz.updateZaiInfo(paramMdl, con, tempDir, this.getAppRootPath(), cntCon);
            biz.updateZaiIndex(paramMdl, con);
            paramMdl.setFormData(form);

            //座席表情報のデータ使用量を登録
            usedDataBiz.insertZasekiSize(editZifSid, true);

            commitFlg = true;
        } catch (SQLException e) {
            e.printStackTrace();
            log__.error("座席表編集情報の更新に失敗しました。" + e);
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "cmn.edit");

        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        cmnBiz.outPutLog(con,
                    msg, GSConstLog.LEVEL_INFO, "[name]" + form.getZasekiMapName(),
                    map.getType());

        //TEMPファイルを削除
        biz050.deleteTempDir();

        //更新完了画面へ遷移
        return __setCompPageParam(map, req, form);
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
                                            Zsk050knForm form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        //テンポラリディレクトリパス取得
        RequestModel reqMdl = getRequestModel(req);
        Zsk050Biz biz050 = new Zsk050Biz(reqMdl);
        String tempDir = biz050.getTempDir();

        Zsk050Biz biz = new Zsk050Biz(getRequestModel(req));
        String imageDir = biz.getDspImageFilePath(tempDir);

        File files = new File(imageDir);
        File file = files.listFiles()[0];
        String fileName = file.getName();
        TempFileUtil.downloadInline(req, res, imageDir + fileName, fileName, Encoding.UTF_8);

        return null;
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
    private void __setResponseIndexInfo(Zsk050knForm form,
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
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Zsk050knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);

        //メッセージセット
        String msgState = null;
        urlForward = map.findForward("zsk030");
        msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "zsk.29");

        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                              msg));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("selectZifSid", form.getSelectZifSid());
        cmn999Form.addHiddenParam("uioStatus", form.getUioStatus());
        cmn999Form.addHiddenParam("uioStatusBiko", form.getUioStatusBiko());
        cmn999Form.addHiddenParam("sortKey", form.getSortKey());
        cmn999Form.addHiddenParam("orderKey", form.getOrderKey());

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
}
