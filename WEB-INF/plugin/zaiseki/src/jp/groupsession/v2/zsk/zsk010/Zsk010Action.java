package jp.groupsession.v2.zsk.zsk010;

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

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml010.Sml010Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.zsk.AbstractZaisekiAction;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.dao.ZaiIndexDao;
import jp.groupsession.v2.zsk.model.ZaiIndexPlusModel;

/**
 * <br>[機  能] 在席管理 在席状況画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk010Action extends AbstractZaisekiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk010Action.class);

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

        Zsk010Form zskForm = (Zsk010Form) form;
        if (cmd.equals("zskEdit")) {
            //在席状況修正
            forward = __updateStatus(map, zskForm, req, res, con);
        } else if (cmd.equals("zsk020")) {
            //管理者ツール
            forward = map.findForward("zsk020");
        } else if (cmd.equals("zsk070")) {
            //個人設定
            forward = map.findForward("zsk070");
        } else if (cmd.equals("imageDownLord")) {
            //イメージダウンロード
            forward = __doGetImageFile(map, zskForm, req, res, con);
        } else if (cmd.equals("getElmInfo")) {
            con.setAutoCommit(true);
            //非同期通信でエレメントの配置座標を取得する
            __setResponseIndexInfo(zskForm, req, res, con);
            return null;
        } else if (cmd.equals("month")) {
            //スケジュール月間へ遷移
            forward = map.findForward("month");
        } else if (cmd.equals("newsmail")) {
            //ショートメール
            __doCreateMsg(map, zskForm, req, res, con);
            forward = map.findForward("msg");
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
    private ActionForward __doInit(ActionMapping map, Zsk010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        Zsk010Biz biz = new Zsk010Biz(getRequestModel(req));

        Zsk010ParamModel paramMdl = new Zsk010ParamModel();
        paramMdl.setParam(form);
        biz.getInitData(paramMdl, con,
                GroupSession.getResourceManager().getDomain(req));
        paramMdl.setFormData(form);

        //プラグイン設定を取得する
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        CommonBiz cmnBiz = new CommonBiz();

        //ショートメールは利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSchedule.PLUGIN_ID_SMAIL, pconfig)) {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_USE);
        } else {
            form.setSmailUseOk(GSConstSchedule.PLUGIN_NOT_USE);
        }

        return map.getInputForward();
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
    private void __setResponseIndexInfo(Zsk010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException, IOException, UnsupportedEncodingException {
        log__.debug("非同期通信でエレメントの配置座標を取得する");
        UDate frDate = new UDate();
        log__.debug(frDate.getTimeStamp());

        int dspZifSid = NullDefault.getInt(form.getSelectZifSid(), 0);
        ZaiIndexDao indexDao = new ZaiIndexDao(con);
        ArrayList<ZaiIndexPlusModel> indexList = indexDao.selectUioIndexModel(dspZifSid);
        HttpSession session = req.getSession(true);
        String sessionId = session.getId();

        log__.debug("jsessionid = " + sessionId);
        int index = 0;

        StringBuilder buf = new StringBuilder();
        int uio = GSConst.UIOSTS_IN;
        buf.append("{");
        buf.append("\"zasekielement\":[");
        if (indexList.size() > 0) {
            for (ZaiIndexPlusModel mdl : indexList) {
                uio = __getUioStatus(mdl, con);

                if (index > 0) {
                    buf.append(",");
                }
                index++;
                buf.append("{");
                buf.append("\"linkKey\":\"elKey-" + index + "\",");
                buf.append("\"linkKbn\":\"" + mdl.getZinLinkkbn() + "\",");
                buf.append("\"linkSid\":\"" + mdl.getZinLinksid() + "\",");
//                name = new String(mdl.getZinName().getBytes("UTF-8"));
                if (mdl.getZinLinkkbn() == GSConstZaiseki.ELEMENT_KBN_ETC) {
                    buf.append("\"linkName\":" + "\"" + mdl.getZinOtherValue() + "\",");
                    buf.append("\"linkNameClass\":" + "\"\",");
                } else {
                    buf.append("\"linkName\":" + "\"" + mdl.getZinName() + "\",");
                    buf.append("\"linkNameClass\":" + "\""
                    + UserUtil.getCSSClassNameNormal(mdl.getUsrUkoFlg()) + "\",");
                }
                buf.append("\"linkUio\":\"" + uio + "\",");
                buf.append("\"linkX\":\"" + mdl.getZinXindex() + "\",");
                buf.append("\"linkY\":\"" + mdl.getZinYindex() + "\"");
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
        UDate toDate = new UDate();
        log__.debug(toDate.getTimeStamp());
    }
    /**
     * 座席表項目モデルからユーザ項目か判定しユーザ在席状態を取得する。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl ZaiIndexModel
     * @param con コネクション
     * @return int 在席状態
     * @throws SQLException SQL実行時例外
     */
    private int __getUioStatus(ZaiIndexPlusModel mdl, Connection con) throws SQLException {
        int ret = GSConst.UIOSTS_IN;
        int linkKbn = mdl.getZinLinkkbn();
        if (linkKbn == GSConstZaiseki.ELEMENT_KBN_USR) {
            ret = mdl.getUioStatus();
        } else if (linkKbn == GSConstZaiseki.ELEMENT_KBN_RSV) {
            //使用状況を取得
            RsvSisYrkDao rsvDao = new RsvSisYrkDao(con);
            if (rsvDao.isYrk(mdl.getZinLinksid(), new UDate())) {
                ret = GSConstZaiseki.RSV_USED;
            } else {
                ret = GSConstZaiseki.RSV_NOT_USED;
            }
        }
        return ret;
    }
    /**
     * <br>[機  能] 画像を読み込む
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

        if (form.getZsk010binSid() != 0) {

            ZsjCommonBiz zsjBiz = new ZsjCommonBiz(getRequestModel(req));
            //指定したbinSidが座席票画像かチェックする。
            if (zsjBiz.isCheckZaiImage(con, form.getZsk010binSid())) {

                CommonBiz cmnBiz = new CommonBiz();
                CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, form.getZsk010binSid(),
                        GroupSession.getResourceManager().getDomain(req));

                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
            }
        }
        return null;
    }

    /**
     * <br>[機  能] 在席ステータスを変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __updateStatus(ActionMapping map,
            Zsk010Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws Exception {

        Zsk010Biz biz = new Zsk010Biz(getRequestModel(req));

        Zsk010ParamModel paramMdl = new Zsk010ParamModel();
        paramMdl.setParam(form);
        int status = biz.updateZskStatus(paramMdl, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(req);
        String msg2 = gsMsg.getMessage("cmn.change");

        //ログ出力
        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(getRequestModel(req));
        int usrSid = NullDefault.getInt(paramMdl.getUioUpdateUsrSid(), 0);
        String opLog = cmnBiz.opLogContent(usrSid, status, con);
        cmnBiz.outPutLog(con,
                msg2,
                GSConstLog.LEVEL_TRACE, opLog, map.getType());

        return __doInit(map, form, req, res, con);

    }

    /**
     * <br>プルダウンメニュー ショートメール選択
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __doCreateMsg(ActionMapping map, Zsk010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        log__.debug("プルダウンメニュー ショートメール選択");
        //パラメータ取得
        String selectUserSid = form.getSch010SelectUsrSid();

        Sml010Form msgForm = new Sml010Form();
        msgForm.setSml010scriptFlg(GSConstSmail.SCRIPT_FIG_TRUE);
        msgForm.setSml010scriptKbn(GSConstSmail.SCRIPT_CREATE_MAIL);
        msgForm.setSml010scriptSelUsrSid(selectUserSid);

        req.setAttribute("sml010Form", msgForm);

    }

}