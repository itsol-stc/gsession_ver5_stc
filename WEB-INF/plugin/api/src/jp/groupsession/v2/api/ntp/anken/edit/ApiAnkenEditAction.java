package jp.groupsession.v2.api.ntp.anken.edit;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenHistoryDao;
import jp.groupsession.v2.ntp.model.NtpAnShohinHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnShohinModel;
import jp.groupsession.v2.ntp.model.NtpAnkenHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.ntp061.Ntp061Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 日報 案件編集をするWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiAnkenEditAction extends AbstractApiAction {
    /**ロガー クラス*/
    private static Log log__ = LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        RequestModel reqMdl = getRequestModel(req);
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        ApiAnkenEditForm thisForm = (ApiAnkenEditForm) form;
        ActionErrors err = thisForm.validateCheck(con, reqMdl);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        int nanSid = Integer.parseInt(thisForm.getNanSid());
        if (nanSid != -1) {
            Ntp061Biz biz = new Ntp061Biz(
                    con, getCountMtController(req), getRequestModel(req));
            if (!biz.isEditable(nanSid)) {
                GsMessage gsMsg = new GsMessage(req);
                ActionMessage msg = new ActionMessage("error.edit.power.user",
                        gsMsg.getMessage("cmn.edit"), gsMsg.getMessage("cmn.edit"));
                StrutsUtil.addMessage(err, msg, "error.edit.power.user");
                addErrors(req, err);
                return null;
            }
        }
        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            int usrSid = this.getSessionUserModel(req).getUsrsid();
            NtpAnkenDao ankenDao = new NtpAnkenDao(con);
            NtpAnkenModel ankenMdl = __createNtpAnken(usrSid);



            UDate date = new UDate();
            date.setZeroHhMmSs();
            date.setDate(Integer.parseInt(thisForm.getDate().substring(0, 4)),
                    Integer.parseInt(thisForm.getDate().substring(5, 7)),
                    Integer.parseInt(thisForm.getDate().substring(8, 10)));
            ankenMdl.setNanDate(date);

            ankenMdl.setNanCode(thisForm.getNanCode());
            ankenMdl.setNanName(thisForm.getNanName());
            ankenMdl.setNanDetial(thisForm.getNanDetail());
            ankenMdl.setAcoSid(Integer.parseInt(thisForm.getAcoSid()));
            ankenMdl.setAbaSid(Integer.parseInt(thisForm.getAbaSid()));
            ankenMdl.setNgpSid(Integer.parseInt(thisForm.getNgpSid()));
            ankenMdl.setNanMikomi(Integer.parseInt(thisForm.getNanMikomi()));
            ankenMdl.setNanKinMitumori(Integer.parseInt(thisForm.getNanKinMitumori()));
            ankenMdl.setNanMitumoriDate(UDate.getInstanceStr(thisForm.getNanDateMitumori()));
            ankenMdl.setNanKinJutyu(Integer.parseInt(thisForm.getNanKinJutyu()));
            ankenMdl.setNanJutyuDate(UDate.getInstanceStr(thisForm.getNanDateJutyu()));
            ankenMdl.setNanSyodan(Integer.parseInt(thisForm.getNanSyodan()));
            ankenMdl.setNcnSid(Integer.parseInt(thisForm.getMcnSid()));
            MlCountMtController cntCon = getCountMtController(req);

            boolean editMode = true;
            if (nanSid == -1) {
                editMode = false;
                //追加モード
                //SID採番
                nanSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_ANKEN, usrSid);
                ankenMdl.setNanSid(nanSid);
                ankenDao.insert(ankenMdl);
            } else {
                //変更モード
                ankenMdl.setNanSid(nanSid);
                ankenDao.update(ankenMdl);
            }

            //案件商品情報の登録
            NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con);
            if (editMode) {
                //変更モード
                anShohinDao.delete(nanSid);
            }

            if (thisForm.getNanShohin() != null
                && thisForm.getNanShohin().length > 0) {
                for (String shohinSid : thisForm.getNanShohin()) {
                    NtpAnShohinModel anShohinMdl = __createNtpAnShohin(usrSid);
                    anShohinMdl.setNhnSid(Integer.parseInt(shohinSid));
                    anShohinMdl.setNanSid(nanSid);
                    anShohinDao.insert(anShohinMdl);
                }
            }
            NtpAnkenHistoryDao hisDao = new NtpAnkenHistoryDao(con);
            NtpAnShohinHistoryDao shohinHisDao = new NtpAnShohinHistoryDao(con);

            //今日の履歴があるかないか
            int nahSid = -1;
            nahSid = hisDao.checkData(nanSid, date);

            NtpAnkenHistoryModel hisMdl = new NtpAnkenHistoryModel();
            BeanUtils.copyProperties(hisMdl, ankenMdl);
            UDate nanMonth = new UDate();
            nanMonth.setYear(hisMdl.getNanDate().getYear());
            nanMonth.setMonth(hisMdl.getNanDate().getMonth());
            nanMonth.setDay(nanMonth.getMaxDayOfMonth());
            nanMonth.setZeroHhMmSs();
            hisMdl.setNanMonth(nanMonth);

            //履歴は見積もり金額、受注金額を0に設定
            hisMdl.setNanKinJutyu(0);
            hisMdl.setNanKinMitumori(0);

            if (nahSid == -1) {
                //履歴の新規登録

                //履歴SID採番
                nahSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
                hisMdl.setNahSid(nahSid);
                hisDao.insert(hisMdl);

                if (thisForm.getNanShohin() != null
                        && thisForm.getNanShohin().length > 0) {
                        for (String shohinSid : thisForm.getNanShohin()) {
                            NtpAnShohinHistoryModel shohinHisMdl =
                                    __createNtpAnShohinHistory(usrSid);
                            shohinHisMdl.setNahSid(nahSid);
                            shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                            shohinHisMdl.setNanSid(nanSid);
                            shohinHisDao.insert(shohinHisMdl);
                        }
                }

            } else {
                //履歴の更新
                hisMdl.setNahSid(nahSid);
                hisDao.update(hisMdl);
                shohinHisDao.delete(nahSid);
                if (thisForm.getNanShohin() != null
                        && thisForm.getNanShohin().length > 0) {
                        for (String shohinSid : thisForm.getNanShohin()) {
                            NtpAnShohinHistoryModel shohinHisMdl =
                                    __createNtpAnShohinHistory(usrSid);
                            shohinHisMdl.setNahSid(nahSid);
                            shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                            shohinHisMdl.setNanSid(nanSid);
                            shohinHisDao.insert(shohinHisMdl);
                        }
                }

            }


            commitFlg = true;


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
        Element result = new Element("Result");
        Document doc = new Document(result);

        result.addContent("OK");

        return doc;
    }
    /**
     * <br>[機  能] 案件情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnkenModel __createNtpAnken(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnkenModel mdl = new NtpAnkenModel();
        mdl.setNanAuid(usrSid);
        mdl.setNanAdate(nowDate);
        mdl.setNanEuid(usrSid);
        mdl.setNanEdate(nowDate);
        return mdl;
    }
    /**
     * <br>[機  能] 案件商品情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnShohinModel __createNtpAnShohin(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnShohinModel mdl = new NtpAnShohinModel();
        mdl.setNasAuid(usrSid);
        mdl.setNasAdate(nowDate);
        mdl.setNasEuid(usrSid);
        mdl.setNasEdate(nowDate);
        return mdl;
    }
    /**
     * <br>[機  能] 案件商品情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnShohinHistoryModel __createNtpAnShohinHistory(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnShohinHistoryModel mdl = new NtpAnShohinHistoryModel();
        mdl.setNasAuid(usrSid);
        mdl.setNasAdate(nowDate);
        mdl.setNasEuid(usrSid);
        mdl.setNasEdate(nowDate);
        return mdl;
    }

}
