package jp.groupsession.v2.api.schedule.edit;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.api.IUseTempdirApi;
import jp.groupsession.v2.api.schedule.AbstractApiSchAction;
import jp.groupsession.v2.api.schedule.delete.ApiSchDeleteAction;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] スケジュール編集WEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchEditAction extends AbstractApiSchAction
implements IUseTempdirApi {

    /** ログ */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");

        RequestModel reqMdl = getRequestModel(req);
        ApiSchEditForm form = (ApiSchEditForm) aForm;
        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig defpconfig
            = getPluginConfigForMain(getPluginConfig(req), con, umodel.getUsrsid());

        int scdSid = NullDefault.getInt(form.getSchSid(), -1);
        if (defpconfig.getPlugin("reserve") == null
                && scdSid > 0) {
            //編集モードかつ施設予約用権限がない場合はデータを上書きしないよう、保存済みの情報を読み込む
            ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);

            ArrayList<Integer> reservs = schRsvDao.getScheduleReserveData(scdSid);
            String[] reservsArr = new String[reservs.size()];
            for (int i = 0; i < reservsArr.length; i++) {
                reservsArr[i] = String.valueOf(reservs.get(i));
            }
            form.setReserves(reservsArr);
        }
        if (defpconfig.getPlugin("address") == null
                && scdSid > 0) {
            //編集モードかつアドレス帳利用権限がない場合はデータを上書きしないよう、保存済みの情報を読み込む
            SchAddressDao addressDao = new SchAddressDao(con);
            List<SchAddressModel> addressList = addressDao.select(scdSid);


            String[] adrArr = new String[addressList.size()];
            for (int i = 0; i < adrArr.length; i++) {
                SchAddressModel adr = addressList.get(i);
                adrArr[i] = String.valueOf(adr.getAdrSid());
                if (adr.getAdcSid() > 0) {
                    form.setContactFlg("1");
                }
            }
            form.setAdress(adrArr);
        }

        ScheduleSearchModel oldMdl = null;
        //編集時、編集前データ取得
        if (scdSid > 0) {
            SchCommonBiz commonBiz = new SchCommonBiz(reqMdl);
            SchAdmConfModel adminConf = commonBiz.getAdmConfModel(con);
            Sch040Biz sch040biz = new Sch040Biz(con, reqMdl);
            oldMdl = sch040biz.getSchData(scdSid, adminConf, con);
        }

        int sessionUserSid = getSessionUserSid(req);
        form.init(con, umodel.getUsrsid());
        ActionErrors err = form.validateCheck(con, reqMdl, sessionUserSid, oldMdl);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        //重複警告確認
        if (NullDefault.getInt(form.getWornCommit(), 0) == 0) {
            err = form.validateSchRepeatCheck(reqMdl, err, con,
                    umodel.getUsrsid(), GSConstSchedule.SCH_REPEAT_KBN_WARNING);
            if (!err.isEmpty()) {
                MessageResources mres = getResources(req);
                //ルートエレメントResultSet
                Element oerrors = new Element("Warnings");
                Document doc = new Document(oerrors);

                @SuppressWarnings("unchecked")
                Iterator<ActionMessage> it = (Iterator<ActionMessage>) err.get();
                while (it.hasNext()) {
                    ActionMessage error = (ActionMessage) it.next();
                    String mes = mres.getMessage(error.getKey(), error.getValues());
                    //エラー内容の出力
                    Element oMessage = new Element("Warning");
                    oMessage.addContent(mes);
                    oerrors.addContent(oMessage);
                }
                return doc;
            }
        }
        MlCountMtController cntCon = getCountMtController(req);
        ApiSchEditBiz biz = new ApiSchEditBiz(con, reqMdl, cntCon);
        ApiSchEditParamModel formParam = new ApiSchEditParamModel();
        formParam.setParam(form);

        BaseUserModel usModel =
            umodel;
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        //アプリケーションRoot
        String appRootPath = getAppRootPath();
        //プラグイン設定
        PluginConfig plconf = getPluginConfig(req);
        PluginConfig pconfig = getPluginConfigForMain(plconf, con);
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig);

        String opLogBefore = "";
        String action;
        int beforeFlg = 0;
        int editFlg = -1;
        boolean commitFlg = false;
        GsMessage gsMsg = new GsMessage(reqMdl);
        con.setAutoCommit(false);
        try {

            biz.setFormParam(formParam);
            //新規登録
            if (NullDefault.getInt(form.getSchSid(), -1) == -1) {
                action = gsMsg.getMessage("cmn.add");
                biz.insertSchedule(
                        sessionUsrSid, appRootPath, plconf, smailPluginUseFlg);
            } else {
                action = gsMsg.getMessage("cmn.change");
                if (formParam.getBatchRef() == null) {
                    editFlg = 1;
                } else {
                    editFlg = Integer.parseInt(formParam.getBatchRef());
                }
                beforeFlg = 2;
                opLogBefore = biz.opLogBefore(Integer.parseInt(formParam.getSchSid()),
                        false, 0, oldMdl);
                biz.updateSchedule(
                        sessionUsrSid, appRootPath, plconf, smailPluginUseFlg, oldMdl);
            }

            commitFlg = true;
        } catch (Exception e) {
            log__.error("スケジュール登録に失敗しました" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, getRequestModel(req));
        ApiSchEditParamModel paramMdl = new ApiSchEditParamModel();
        paramMdl.setParam(formParam);
        String opLogAfter =  biz.opLogCreate(paramMdl, beforeFlg, editFlg);
        schBiz.outPutApiLog(reqMdl, con, sessionUsrSid, ApiSchDeleteAction.class.getName(),
                action, GSConstLog.LEVEL_TRACE, opLogBefore + opLogAfter);

        log__.debug("createXml end");
        return new Document(_createElement("SchSid", formParam.getSchSid()));
    }
}

