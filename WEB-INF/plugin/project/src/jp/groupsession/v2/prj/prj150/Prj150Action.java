package jp.groupsession.v2.prj.prj150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.prj.AbstractProjectTemplateAction;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.prj020.Prj020Action;
import jp.groupsession.v2.prj.prj140.Prj140Action;
import jp.groupsession.v2.prj.prj240.Prj240Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトメンバー設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj150Action extends AbstractProjectTemplateAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj150Action.class);
    /** CMD:OKボタンクリック */
    public static final String CMD_OK_CLICK = Prj020Action.CMD_EDIT_CLICK;
    /** CMD:追加ボタンクリック */
    public static final String CMD_ADD_CLICK = "addMember";
    /** CMD:削除ボタン(内部)クリック */
    public static final String CMD_DEL_CLICK = "delMember";
    /** CMD:削除ボタン(外部)クリック */
    public static final String CMD_DEL_OUTER_CLICK = "delMemberOuter";
    /** CMD:OKボタンクリック(テンプレート) */
    public static final String CMD_OK_TMP_CLICK = Prj140Action.CMD_EDIT_CLICK;
    /** CMD:戻るボタンクリック */
    public static final String CMD_BACK_CLICK = Prj020Action.CMD_BACK_REDRAW;
    /** CMD:内部タブクリック */
    public static final String CMD_TAB_NAIBU = "tabNaibuClick";
    /** CMD:外部タブクリック */
    public static final String CMD_TAB_GAIBU = "tabGaibuClick";
    /** CMD:上へボタンクリック(内部) */
    public static final String CMD_UP_CLICK = "prj150up";
    /** CMD:下へボタンクリック （内部）*/
    public static final String CMD_DOWN_CLICK = "prj150down";
    /** CMD:上へボタンクリック(外部) */
    public static final String CMD_UP_CLICK_GAIBU = "prj150gaibuUp";
    /** CMD:下へボタンクリック （外部）*/
    public static final String CMD_DOWN_CLICK_GAIBU = "prj150gaibuDown";
    /** 並び順分割文字列 */
    private static final String SORT_SEPARATE = "-";

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Prj150Form thisForm = (Prj150Form) form;

        //コマンドパラメータ取得
        String cmd = PrjCommonBiz.getCmdProperty(req);

        if (CMD_OK_CLICK.equals(cmd) || CMD_OK_TMP_CLICK.equals(cmd)) {
            log__.debug("OKボタンクリック");
            forward = __doUpdate(map, thisForm, req, res, con);

        } else if (CMD_BACK_CLICK.equals(cmd)) {
            log__.debug("戻るボタンクリック");
            forward = __doBack(map, thisForm, req, res, con);

        } else if (CMD_TAB_NAIBU.equals(cmd)) {
            log__.debug("内部タブクリック");
            thisForm.setPrj150cmdMode(GSConstProject.MODE_NAIBU);
            forward = __doNaibuTabClick(map, thisForm, req, res, con);

        } else if (CMD_TAB_GAIBU.equals(cmd)) {
            log__.debug("外部タブクリック");
            thisForm.setPrj150cmdMode(GSConstProject.MODE_GAIBU);
            forward = __doGaibuTabClick(map, thisForm, req, res, con);
        } else if (cmd.equals("selectedCompany")) {
            //会社選択
            log__.debug("会社を選択");
            forward = __doSelectCompany(map, thisForm, req, res, con);
        } else if (cmd.equals("deleteCompany")) {
            //会社削除
            log__.debug("会社削除");
            forward = __doDeleteCompany(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_ADD_CLICK)) {
            //追加ボタンクリック
            forward = __doAddNaibuMember(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_DEL_CLICK)) {
            //削除ボタン(内部)クリック
            forward = __doDelNaibuMember(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_DEL_OUTER_CLICK)) {
            //削除ボタン(外部)クリック
            forward = __doDelGaibuMember(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_UP_CLICK)) {
            log__.debug("上へボタンクリック(内部)");
            forward = __doUp(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_DOWN_CLICK)) {
            log__.debug("下へボタンクリック(内部)");
            forward = __doDown(map, thisForm, req, res, con);
        } else if (cmd.equals(CMD_UP_CLICK_GAIBU)) {
            log__.debug("上へボタンクリック(外部)");
            forward = __doUpGaibu(map, thisForm, req, res, con);

        } else if (cmd.equals(CMD_DOWN_CLICK_GAIBU)) {
            log__.debug("下へボタンクリック(外部)");
            forward = __doDownGaibu(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Prj150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws SQLException {

        con.setAutoCommit(true);
        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //初期表示情報を画面にセットする
        Prj150Biz biz = new Prj150Biz(con);

        //内部タブ
        String cmdMode = NullDefault.getString(form.getPrj150cmdMode(), GSConstProject.MODE_NAIBU);
        if (cmdMode.equals(GSConstProject.MODE_NAIBU)) {

            Prj150ParamModel paramMdl = new Prj150ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, pconfig);
            paramMdl.setFormData(form);

        //外部タブ
        } else {

            Prj150ParamModel paramMdl = new Prj150ParamModel();
            paramMdl.setParam(form);
            biz.setCompanyData(paramMdl, con, getSessionUserModel(req).getUsrsid());
            biz.setAddressUse(paramMdl, pconfig);
            paramMdl.setFormData(form);

        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタン押下時
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQL実行例外
     */
    private ActionForward __doUpdate(ActionMapping map,
                                      Prj150Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws SQLException {

        Prj150Biz biz = new Prj150Biz(con);
        ActionErrors errors = form.validate150(req);

        if (!errors.isEmpty()) {
            addErrors(req, errors);

            //管理者設定を反映したプラグイン設定情報を取得
            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

            Prj150ParamModel paramMdl = new Prj150ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, pconfig);
            paramMdl.setFormData(form);

            return map.getInputForward();
        }

        ActionForward forward = null;
        String movedDspId = form.getMovedDspId();

        //ログメッセージ生成
        String message = "";
        boolean bCheck = false;
        if (form.getMemberFormList() != null && form.getMemberFormList().size() != 0) {
            ArrayList<String> userSidList = new ArrayList<String>();
            for (Prj150MemberForm mdl : form.getMemberFormList()) {
                userSidList.add(String.valueOf(mdl.getUsrSid()));
            }
            String[] usrList = userSidList.toArray(new String[userSidList.size()]);
            bCheck = true;
            List<String> userNameList = new ArrayList<String>();
            userNameList = biz.getTargetName(con, usrList, GSConstProject.MODE_NAIBU,
                    getRequestModel(req));
            message = "内部\r\n";
            for (int idx = 0; idx < userNameList.size(); idx++) {
                if (idx != 0) {
                    message += "\r\n";
                }
                message += "[" + getInterMessage(req, "cmn.name") + "]" + userNameList.get(idx);
            }
        }
        if (form.getPrj150AddressId() != null && form.getPrj150AddressId().length != 0) {

            List<String> gaibuNameList = new ArrayList<String>();
            gaibuNameList = biz.getTargetName(con,
                    form.getPrj150AddressId(), GSConstProject.MODE_GAIBU,
                    getRequestModel(req));
            if (bCheck) {
                message += "\r\n外部\r\n";
            } else {
                message += "外部\r\n";
            }
            for (int idx = 0; idx < gaibuNameList.size(); idx++) {
                if (idx != 0) {
                    message += "\r\n";
                }
                message += "[" + getInterMessage(req, "cmn.name") + "]" + gaibuNameList.get(idx);
            }
        }

        //外部タブの登録
        if (form.getPrj150cmdMode().equals(GSConstProject.MODE_GAIBU)) {

            //登録画面からの遷移
            if (movedDspId.equals(GSConstProject.SCR_ID_PRJ020)) {
                forward = map.findForward("backPrj020");
                return forward;
            }

            boolean commitFlg = false;
            con.setAutoCommit(false);

            try {

                //ログインユーザSIDを取得
                int userSid = 0;
                BaseUserModel buMdl = getSessionUserModel(req);
                if (buMdl != null) {
                    userSid = buMdl.getUsrsid();
                }

                //更新
                Prj150ParamModel paramMdl = new Prj150ParamModel();
                paramMdl.setParam(form);
                biz.addPrjAddDB(paramMdl, userSid);
                biz.addPrjComDB(paramMdl, userSid);
                paramMdl.setFormData(form);

                //ログ出力処理
                GsMessage gsMsg = new GsMessage(req);
                PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
                String opCode = getInterMessage(req, "cmn.change");

                prjBiz.outPutLog(
                        map, req, res, opCode,
                        GSConstLog.LEVEL_TRACE, message);

                commitFlg = true;
                forward = __setKanryoDsp(map, form, req);

            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }
            }
            return forward;
        }

        //登録画面からの遷移
        if (movedDspId.equals(GSConstProject.SCR_ID_PRJ020)) {

            forward = map.findForward("backPrj020");

        //プロジェクト画面からの遷移
        } else if (movedDspId.equals(GSConstProject.SCR_ID_PRJ030)) {

            boolean commitFlg = false;
            con.setAutoCommit(false);

            try {

                //ログインユーザSIDを取得
                int userSid = 0;
                BaseUserModel buMdl = getSessionUserModel(req);
                if (buMdl != null) {
                    userSid = buMdl.getUsrsid();
                }

                //更新
                Prj150ParamModel paramMdl = new Prj150ParamModel();
                paramMdl.setParam(form);
                biz.updateUserKeyToDB(paramMdl, userSid);
                paramMdl.setFormData(form);

                //ログ出力処理
                GsMessage gsMsg = new GsMessage(req);
                PrjCommonBiz prjBiz = new PrjCommonBiz(con, gsMsg, getRequestModel(req));
                String opCode = getInterMessage(req, "cmn.change");

                prjBiz.outPutLog(
                        map, req, res, opCode,
                        GSConstLog.LEVEL_TRACE, message);

                commitFlg = true;
                forward = __setKanryoDsp(map, form, req);

            } catch (SQLException e) {
                log__.error("SQLException", e);
                throw e;
            } finally {
                if (commitFlg) {
                    con.commit();
                } else {
                    JDBCUtil.rollback(con);
                }
            }

        //プロジェクトテンプレート登録画面からの遷移
        } else if (movedDspId.equals(GSConstProject.SCR_ID_PRJ140)) {

            forward = map.findForward("backPrj140");
        }

        return forward;
    }

    /**
     * <br>[機  能] 戻るボタン押下時
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws IOToolsException IOエラー
     */
    private ActionForward __doBack(ActionMapping map,
                                    Prj150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws IOToolsException {

        ActionForward forward = null;

        String movedDspId = form.getMovedDspId();

        //登録画面からの遷移
        if (movedDspId.equals(GSConstProject.SCR_ID_PRJ020)) {

            log__.debug("プロジェクト登録画面からの遷移");
            forward = map.findForward("backPrj020");

        //プロジェクト画面からの遷移
        } else if (movedDspId.equals(GSConstProject.SCR_ID_PRJ030)) {

            log__.debug("プロジェクト画面からの遷移");
            forward = map.findForward("backPrj030");

        //プロジェクトテンプレート登録画面からの遷移
        } else if (movedDspId.equals(GSConstProject.SCR_ID_PRJ140)) {

            log__.debug("プロジェクトテンプレート画面からの遷移");
            forward = map.findForward("backPrj140");
        }

        return forward;
    }

    /**
     * <br>[機  能] 内部タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doNaibuTabClick(
        ActionMapping map,
        Prj150Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        form.setPrj150cmdMode(GSConstProject.MODE_NAIBU);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 外部タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doGaibuTabClick(
        ActionMapping map,
        Prj150Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        form.setPrj150cmdMode(GSConstProject.MODE_GAIBU);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 会社を選択処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doSelectCompany(ActionMapping map,
                                            Prj150Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
    throws Exception {

        //重複したパラメータを除外する
        String[] acoSidArray = form.getPrj150CompanySid();
        if (acoSidArray != null) {
            String[] abaSidArray = form.getPrj150CompanyBaseSid();
            List<String> companyIdList = new ArrayList<String>();

            for (int index = 0; index < acoSidArray.length; index++) {
                String companyId = acoSidArray[index] + ":" + abaSidArray[index];
                if (companyIdList.indexOf(companyId) < 0) {
                    companyIdList.add(companyId);
                }
            }

            acoSidArray = new String[companyIdList.size()];
            abaSidArray = new String[companyIdList.size()];
            for (int index = 0; index < companyIdList.size(); index++) {
                String companyId = companyIdList.get(index);
                acoSidArray[index] = companyId.split(":")[0];
                abaSidArray[index] = companyId.split(":")[1];
            }

            form.setPrj150CompanySid(acoSidArray);
            form.setPrj150CompanyBaseSid(abaSidArray);
        }

        String[] addressIdArray = form.getPrj150AddressId();
        if (addressIdArray != null) {
            List<String> addressIdList = new ArrayList<String>();

            for (int index = 0; index < addressIdArray.length; index++) {
                if (addressIdList.indexOf(addressIdArray[index]) < 0) {
                    addressIdList.add(addressIdArray[index]);
                }
            }

            form.setPrj150AddressId(
                    addressIdList.toArray(new String[addressIdList.size()]));
        }

        form.setPrj150cmdMode(GSConstProject.MODE_GAIBU);

        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 会社を削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDeleteCompany(ActionMapping map,
                                            Prj150Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
                                            throws Exception {

        int delCompanyId = NullDefault.getInt(form.getPrj150delCompanyId(), -1);
        int delCompanyBaseId = NullDefault.getInt(form.getPrj150delCompanyBaseId(), -1);
        int delAddSid = form.getPrj150UsrDelFlg();
        String[] selectMem = {delCompanyId + ":" + delCompanyBaseId + ":" + delAddSid};
        form.setPrj150gaibuSelectMemberSid(selectMem);
        return __doDelGaibuMember(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 追加ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doAddNaibuMember(ActionMapping map,
                                            Prj150Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
    throws Exception {
        GsMessage gsMsg = new GsMessage();
        //メンバー
        String textMember = gsMsg.getMessage(req, "cmn.member");
        Prj240Form prj240Form = new Prj240Form();

        //「戻る」ボタンURLセット
        ActionForward forward = map.findForward("redraw");
        prj240Form.setPrj240BackUrl(forward.getPath() + "?" + GSConst.P_CMD + "=dsp");

        //機能名称セット
        prj240Form.setPrj240FunctionName(textMember);

        //フォーム識別子
        prj240Form.setPrj240FormKey("prj150Form");

        prj240Form.setPrj240paramName("prj240User");
        prj240Form.setPrj240userSid(new String[0]);

        req.setAttribute("prj240Form", prj240Form);
        return map.findForward("addNaibuMember");
    }

    /**
     * <br>[機  能] 削除ボタン(内部)クリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDelNaibuMember(ActionMapping map,
                                            Prj150Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
                                            throws Exception {

        //ソートで選択したユーザSID取得
        String [] sort = form.getPrj150SortRadio().split(SORT_SEPARATE);
        String userSid = sort[0];

        //入力チェック
        ActionErrors errors = form.validateDelete(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {
            List<String> selectMemList = Arrays.asList(form.getPrj150naibuSelectMemberSid());
            ArrayList<Prj150MemberForm> afterMemDataList = new ArrayList<Prj150MemberForm>();

            int rowNumber = 1;
            for (Prj150MemberForm memForm : form.getMemberFormList()) {
                if (!selectMemList.contains(String.valueOf(memForm.getUsrSid()))) {
                    memForm.setRowNumber(rowNumber++);
                    afterMemDataList.add(memForm);
                }
            }

            form.setMemberFormList(afterMemDataList);

            //ソートで選択したユーザが削除ユーザに入っているか
            if (selectMemList.contains(userSid)
                    && afterMemDataList.size() > 0) {

                Prj150Biz biz = new Prj150Biz(con);
                form.setPrj150SortRadio(
                        biz.getRadioValueStr(afterMemDataList.get(0).getUsrSid(), 0));
            }
        }

        //初期表示処理
        return __doInit(map, form, req, res, con);

    }

    /**
     * <br>[機  能] 削除ボタン(外部)クリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDelGaibuMember(ActionMapping map,
                                            Prj150Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
                                            throws Exception {

        Prj150Biz biz = new Prj150Biz(con);

        //入力チェック
        ActionErrors errors = form.validateDeleteGaibu(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
        } else {
            List<String> selMemList = Arrays.asList(form.getPrj150gaibuSelectMemberSid());
            List<String> selectMemList = new ArrayList<String>();
            List<String> newAddressList = new ArrayList<String>();
            ArrayList<Prj150DspModel> afterMemDataList = new ArrayList<Prj150DspModel>();

            for (String a : selMemList) {
                StringTokenizer st = new StringTokenizer(a, ":");
                NullDefault.getInt(st.nextToken(), -1);
                NullDefault.getInt(st.nextToken(), -1);
                int delAddSid = NullDefault.getInt(st.nextToken(), -1);
                selectMemList.add(String.valueOf(delAddSid));
            }

            int rowNumber = 1;
            int count = 0;
            for (Prj150DspModel memForm : form.getDspList()) {
                if (!selectMemList.contains(String.valueOf(memForm.getAdrSid()))) {
                    memForm.setGaibuRowNumber(rowNumber++);
                    memForm.setGaibuSort(biz.getRadioValueStr(memForm.getAdrSid(), count++));
                    afterMemDataList.add(memForm);
                    newAddressList.add(String.valueOf(memForm.getAdrSid()));
                }
            }
            if (!newAddressList.isEmpty()) {
                String[] newAdrArray = (String[]) newAddressList.toArray(new String[0]);
                form.setPrj150AddressId(newAdrArray);
            } else {
                String[] newAdrArray = new String[0];
                form.setPrj150AddressId(newAdrArray);
            }

            form.setDspList(afterMemDataList);

            //ソートで選択したユーザが削除ユーザに入っているか
            if (afterMemDataList.size() > 0) {

                form.setPrj150SortGaibuRadio(
                        biz.getRadioValueStr(afterMemDataList.get(0).getAdrSid(), 0));
            }
        }

        //初期表示処理
        return __doInit(map, form, req, res, con);

    }

    /**
     * <br>[機  能] ラジオで選択したユーザを１つ上にあげる（内部）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doUp(ActionMapping map,
                                    Prj150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //メンバーリスト取得
        log__.debug("リストの中！" + form.getMemberFormList().size());
        Prj150Biz biz = new Prj150Biz(con);

        Prj150ParamModel paramMdl = new Prj150ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, pconfig);
        paramMdl.setFormData(form);

        if (form.getMemberFormList().size() > 0) {
            String[] sort = form.getPrj150SortRadio().split(SORT_SEPARATE);

            if (!(NullDefault.getInt(sort[1], 0) == 0)) {
                //並び替え
                paramMdl = new Prj150ParamModel();
                paramMdl.setParam(form);
                biz.getSortUpUsrList(paramMdl);
                paramMdl.setFormData(form);
            }
        }

        //初期表示処理
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] ラジオで選択したユーザを１つ上にあげる（外部）
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doUpGaibu(ActionMapping map,
                                    Prj150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //メンバーリスト取得
        Prj150Biz biz = new Prj150Biz(con);

        Prj150ParamModel paramMdl = new Prj150ParamModel();
        paramMdl.setParam(form);
        biz.setCompanyData(paramMdl, con, getSessionUserModel(req).getUsrsid());
        biz.setAddressUse(paramMdl, pconfig);
        paramMdl.setFormData(form);


        if (form.getDspList().size() > 0) {
            String[] sort = form.getPrj150SortGaibuRadio().split(SORT_SEPARATE);

            if (!(NullDefault.getInt(sort[1], 0) == 0)) {
                //並び替え
                paramMdl = new Prj150ParamModel();
                paramMdl.setParam(form);
                biz.getSortUpGaibuUsrList(paramMdl);
                paramMdl.setFormData(form);
            }
        }

        //初期表示処理
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] ラジオで選択したユーザを１つ下に下げる(内部)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDown(ActionMapping map,
                                    Prj150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //メンバーリスト取得
        Prj150Biz biz = new Prj150Biz(con);


        Prj150ParamModel paramMdl = new Prj150ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, pconfig);
        paramMdl.setFormData(form);

        int memberListSize = form.getMemberFormList().size();

        if (memberListSize > 0) {
            String[] sort = form.getPrj150SortRadio().split(SORT_SEPARATE);

            if (!(NullDefault.getInt(sort[1], memberListSize - 1) == memberListSize - 1)) {
                //並び替え
                paramMdl = new Prj150ParamModel();
                paramMdl.setParam(form);
                biz.getSortDownUsrList(paramMdl);
                paramMdl.setFormData(form);
            }
        }

        //初期表示処理
        return __doInit(map, form, req, res, con);

    }
    /**
     * <br>[機  能] ラジオで選択したユーザを１つ下に下げる(外部)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doDownGaibu(ActionMapping map,
                                    Prj150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //メンバーリスト取得
        Prj150Biz biz = new Prj150Biz(con);

        Prj150ParamModel paramMdl = new Prj150ParamModel();
        paramMdl.setParam(form);
        biz.setCompanyData(paramMdl, con, getSessionUserModel(req).getUsrsid());
        biz.setAddressUse(paramMdl, pconfig);
        paramMdl.setFormData(form);
        int memberListSize = form.getDspList().size();

        if (memberListSize > 0) {
            String[] sort = form.getPrj150SortGaibuRadio().split(SORT_SEPARATE);

            if (!(NullDefault.getInt(sort[1], memberListSize - 1) == memberListSize - 1)) {
                //並び替え
                paramMdl.setParam(form);
                biz.getSortDownGaibuUsrList(paramMdl);
                paramMdl.setFormData(form);
            }
        }

        //初期表示処理
        return __doInit(map, form, req, res, con);

    }
    /**
     * [機  能] 登録・更新完了画面のパラメータセット
     * [解  説]
     * [備  考]
     *
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(ActionMapping map,
                                          Prj150Form form,
                                          HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();
        //プロジェクトメンバー
        String textPrjMem = gsMsg.getMessage(req, "project.src.29");
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        cmn999Form.setUrlOK(map.findForward("backPrj030").getPath());

        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("hensyu.kanryo.object", textPrjMem));

        //画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setcmn999FormParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}