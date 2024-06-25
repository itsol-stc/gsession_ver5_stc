package jp.groupsession.v2.api.ntp.target.list;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpPriTargetDao;
import jp.groupsession.v2.ntp.model.NtpPriTargetModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;
/**
 * <br>[機  能] WEBAPI 日報目標一覧取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNtpTargetListAction extends AbstractApiAction {
    /** ロガー */
    private static Log log__ = LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        ApiNtpTargetListForm thisForm = (ApiNtpTargetListForm) form;
        ActionErrors err = thisForm.validateCheck(con, req);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        List<NtpPriTargetModel> list = __getTargetList(req, con, thisForm, umodel);

        resultSet.setAttribute("TotalCount", String.valueOf(list.size()));
        for (NtpPriTargetModel ntgModel : list) {
            //Target  目標情報
            Element target = new Element("Result");
            resultSet.addContent(target);
            //目標SID
            target.addContent(_createElement("NtgSid", ntgModel.getNtgSid()));
            //目標SID
            target.addContent(_createElement("UsrSid", ntgModel.getUsrSid()));
            //Year    目標年度
            target.addContent(_createElement("Year", ntgModel.getNpgYear()));
            //Month   目標月
            target.addContent(_createElement("Month", ntgModel.getNpgMonth()));
            //Name    目標タイトル
            target.addContent(_createElement("Name", ntgModel.getNpgTargetName()));
            //Unit    目標単位
            target.addContent(_createElement("Unit", ntgModel.getNpgTargetUnit()));
            //Ratio   目標達成率
            target.addContent(_createElement("Ratio"
                    , String.valueOf(ntgModel.getNpgTargetRatio())));
            //Value   目標値
            target.addContent(_createElement("Value", ntgModel.getNpgTarget()));
            //Record  目標実績値
            target.addContent(_createElement("Record", ntgModel.getNpgRecord()));

        }

        return doc;
    }
    /**
     * <br>[機  能]目標取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @param form form
     * @param umodel ユーザモデル
     * @return errors エラー
     * @throws Exception 実行時例外
     */
    private List<NtpPriTargetModel> __getTargetList(HttpServletRequest req,
                                                  Connection con,
                                                  ApiNtpTargetListForm form,
                                                  BaseUserModel umodel
                                                  ) throws Exception {
        List<NtpPriTargetModel> ret = new ArrayList<NtpPriTargetModel>();
        int userSid = NullDefault.getInt(form.getUsrSid(), umodel.getUsrsid());

        //管理者設定を取得
        NtpCommonBiz biz = new NtpCommonBiz(con, getRequestModel(req));

        //ユーザテンプレートデータ取得
        NtpTemplateModel tmpMdl = new NtpTemplateModel();
        tmpMdl = biz.getUsrTemplate(con, userSid);
        if (tmpMdl != null) {
            List<NtpTargetModel> ntgList = biz.getUsrTmpTarget(
                    con, tmpMdl.getNttSid(), userSid);

            //表示ユーザの目標取得
            if (ntgList != null && !ntgList.isEmpty()) {

                NtpPriTargetModel priTrgMdl = null;
                NtpPriTargetDao priTrgDao = new NtpPriTargetDao(con);

                //画面表示年月

                int dspYear = Integer.valueOf(form.getMonth().substring(0, 4));
                int dspMonth = Integer.valueOf(form.getMonth().substring(5, 7));

                UDate dspDate = new UDate();
                dspDate.setYear(dspYear);
                dspDate.setMonth(dspMonth + 1);
                dspDate.setZeroDdHhMmSs();

                for (int i = 0; i < GSConstNippou.DSP_MONTH_CNT; i++) {



                    for (NtpTargetModel trgMdl : ntgList) {

                        if (trgMdl != null) {

                            //ユーザデータ取得
                            priTrgMdl = priTrgDao.select(
                                    trgMdl.getNtgSid()
                                    , userSid
                                    , dspDate.getYear()
                                    , dspDate.getMonth());

                            if (priTrgMdl == null) {

                                //データがない場合は一番最近のデータの値を設定
                                priTrgMdl = priTrgDao.getLatelyData(trgMdl.getNtgSid(), userSid);

                                if (priTrgMdl == null) {

                                    //データがない場合はデフォルト値を設定
                                    priTrgMdl = new NtpPriTargetModel();
                                    //usrSid
                                    priTrgMdl.setUsrSid(userSid);
                                    //目標SID
                                    priTrgMdl.setNtgSid(trgMdl.getNtgSid());
                                    //デフォルト値
                                    priTrgMdl.setNpgTarget(trgMdl.getNtgDef());
                                    //実績
                                    priTrgMdl.setNpgRecord(new Long(0));
                                } else {
                                    //実績
                                    priTrgMdl.setNpgRecord(new Long(0));
                                }
                            }

                            //名前
                            priTrgMdl.setNpgTargetName(trgMdl.getNtgName());
                            //単位
                            priTrgMdl.setNpgTargetUnit(trgMdl.getNtgUnit());
                            //年
                            priTrgMdl.setNpgYear(dspDate.getYear());
                            //月
                            priTrgMdl.setNpgMonth(dspDate.getMonth());
                            ret.add(priTrgMdl);
                        }
                    }

                    //一月進める
                    dspDate.addMonth(-1);
                }
            }
        }
        return ret;
    }
}
