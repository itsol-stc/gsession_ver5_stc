package jp.groupsession.v2.cir.cir010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir180.Cir180Dao;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirInfLabelDao;
import jp.groupsession.v2.cir.dao.CirLabelDao;
import jp.groupsession.v2.cir.dao.CirUserDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.AccountDataModel;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirInfLabelModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CirLabelModel;
import jp.groupsession.v2.cir.model.CirSearchModel;
import jp.groupsession.v2.cir.model.CirUserModel;
import jp.groupsession.v2.cir.model.CirViewLabelModel;
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cir.model.LabelDataModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir010Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ銃砲
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Cir010ParamModel paramMdl, Connection con,
                            int userSid, RequestModel reqMdl)
    throws SQLException {

        CirCommonBiz ccBiz = new CirCommonBiz();
        int cacSid = paramMdl.getCirViewAccount();

        //アカウント情報を設定
        __setAccountInf(paramMdl, con, reqMdl);

        //ラベルリストを設定
        paramMdl.setCir010LabelList(
                __getLabelListInf(con, cacSid));

        //受信未読件数
        paramMdl.setCir010JusinMidokuCnt(ccBiz.getUnopenedCirCnt(cacSid, con));
        //ゴミ箱未読件数
        paramMdl.setCir010GomiMidokuCnt(ccBiz.getUnopenedGomiCirCnt(cacSid, con));

        //回覧板個人設定を取得する
        int limit = ccBiz.getCountLimit(userSid, con);
        //回覧板作成可能ユーザ区分を設定
        paramMdl.setCirCreateFlg(ccBiz.canCreateUserCir(con, reqMdl));

        List < CircularDspModel > cList = null;

        //処理モードで処理を分岐

        String cmdMode = NullDefault.getString(paramMdl.getCir010cmdMode(), "");
        if (cmdMode.equals(GSConstCircular.MODE_JUSIN)) {
            //受信
            cList = __getJusinList(paramMdl, con, limit, cacSid);

        } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
            //送信済み
            cList = __getSousinList(paramMdl, con, limit, cacSid);

        } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱
            cList = __getGomiList(paramMdl, con, limit, cacSid);
        } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {
            // ラベルフォルダ
            cList = __getEncLabelList(paramMdl, con, limit, cacSid);
        }

        // 回覧板情報を取得できない場合、受信モード選択
        if (cList == null) {
            cList = __getJusinList(paramMdl, con, limit, cacSid);
            paramMdl.setCir010cmdMode(GSConstCircular.MODE_JUSIN);
        }

        CirInfLabelDao infDao = new CirInfLabelDao(con);
        CirViewLabelDao viewDao = new CirViewLabelDao(con);
        CirLabelDao labelDao = new CirLabelDao(con);
        List < CircularDspModel > cInfList = new ArrayList< CircularDspModel >();
        for (CircularDspModel clMdl: cList) {
            String strAdate =
                    UDateUtil.getSlashYYMD(clMdl.getCifAdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(clMdl.getCifAdate());
                clMdl.setDspCifAdate(strAdate);
                // ラベル情報を付与
                int cifSid = clMdl.getCifSid();
                List<Integer> labelSids = new ArrayList<Integer>();
                if (clMdl.getJsFlg().equals(GSConstCircular.MODE_JUSIN)) {
                    List<CirViewLabelModel> viewLabels = viewDao.selectLabelList(cacSid, cifSid);
                    if (viewLabels != null) {
                        for (CirViewLabelModel viwMdl:viewLabels) {
                            labelSids.add(viwMdl.getClaSid());
                        }
                    }
                } else if (clMdl.getJsFlg().equals(GSConstCircular.MODE_SOUSIN)) {
                    List<CirInfLabelModel> infLabels = infDao.selectLabelList(cacSid, cifSid);
                    if (infLabels != null) {
                        for (CirInfLabelModel viwMdl:infLabels) {
                            labelSids.add(viwMdl.getClaSid());
                        }
                    }
                }
                // ラベルSIDからラベル名を取得
                if (!labelSids.isEmpty()) {
                    List<LabelValueBean> labelNames
                    = labelDao.selectLabelNameList(cacSid, labelSids);
                    if (labelNames != null && !labelSids.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (LabelValueBean bean: labelNames) {
                            if (sb.length() > 0) {
                                sb.append(",");
                                }
                                sb.append(bean.getLabel());
                        }
                        clMdl.setLabelName(sb.toString());
                    }
                }
                cInfList.add(clMdl);

        }

        String[] delInfSid = paramMdl.getCir010delInfSid();
        ArrayList<String> saveList = new ArrayList<String>();

        if (delInfSid != null) {

            log__.debug("delInfSid.length = " + delInfSid.length);

            for (int i = 0; i < delInfSid.length; i++) {
                String cirSid = NullDefault.getString(delInfSid[i], "");

                boolean addFlg = true;
                for (int j = 0; j < cList.size(); j++) {
                    CircularDspModel clMdl = cList.get(j);
                    if (cirSid.equals(clMdl.getCifSid() + "-" + clMdl.getJsFlg())) {
                        addFlg = false;
                        break;
                    }
                }
                if (addFlg) {
                    saveList.add(String.valueOf(delInfSid[i]));
                    log__.debug("save SID = " + delInfSid[i]);
                }
            }
        }
        //saveリスト(現在ページ以外でチェックされている値)
        paramMdl.setCir010saveList(saveList);

        //一覧をフォームへセット
        paramMdl.setCir010CircularList(cInfList);

        //自動リロード時間を取得
        paramMdl.setCir010Reload(__getReloadTime(con, userSid));

        //管理者権限
        BaseUserModel baseMdl = reqMdl.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();

        if (baseMdl != null) {
            boolean adminUser = cmnBiz.isPluginAdmin(con, baseMdl,
                    GSConstCircular.PLUGIN_ID_CIRCULAR);
            if (adminUser) {
                paramMdl.setAdminFlg(GSConst.USER_ADMIN);
            } else {
                paramMdl.setAdminFlg(GSConst.USER_NOT_ADMIN);
            }
        }
    }

    /**
     * <br>[機  能] 回覧情報(受信)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param limit 1ページの表示件数
     * @param cacSid アカウントSID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    private List < CircularDspModel > __getJusinList(
        Cir010ParamModel paramMdl,
        Connection con,
        int limit,
        int cacSid) throws SQLException {

        int nowPage = paramMdl.getCir010pageNum1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //検索用Modelを作成
        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);
        bean.setLimit(limit);
        bean.setOrderKey(paramMdl.getCir010orderKey());
        bean.setSortKey(paramMdl.getCir010sortKey());

        //件数カウント
        CircularDao cDao = new CircularDao(con);
        long maxCount = cDao.getJusinCount(bean);
        log__.debug("件数 = " + maxCount);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setCir010pageNum1(nowPage);
        paramMdl.setCir010pageNum2(nowPage);
        paramMdl.setCir010PageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<CircularDspModel>();
        }

        return cDao.getJusinList(bean);
    }

    /**
     * <br>[機  能] 回覧情報(送信済み)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param limit 1ページの表示件数
     * @param cacSid アカウントSID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    private List < CircularDspModel > __getSousinList(
            Cir010ParamModel paramMdl,
        Connection con,
        int limit,
        int cacSid) throws SQLException {

        int nowPage = paramMdl.getCir010pageNum1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //検索用Modelを作成
        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);
        bean.setLimit(limit);
        bean.setOrderKey(paramMdl.getCir010orderKey());
        bean.setSortKey(paramMdl.getCir010sortKey());

        //件数カウント
        CircularDao cDao = new CircularDao(con);
        long maxCount = cDao.getSousinCount(bean);
        log__.debug("件数 = " + maxCount);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setCir010pageNum1(nowPage);
        paramMdl.setCir010pageNum2(nowPage);
        paramMdl.setCir010PageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<CircularDspModel>();
        }

        return cDao.getSousinList(bean);
    }

    /**
     * <br>[機  能] 回覧情報(ゴミ箱)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param limit 1ページの表示件数
     * @param cacSid アカウントSID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    private List < CircularDspModel > __getGomiList(
            Cir010ParamModel paramMdl,
        Connection con,
        int limit,
        int cacSid) throws SQLException {

        int nowPage = paramMdl.getCir010pageNum1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //検索用Modelを作成
        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);
        bean.setLimit(limit);
        bean.setOrderKey(paramMdl.getCir010orderKey());
        bean.setSortKey(paramMdl.getCir010sortKey());

        //件数カウント
        CircularDao cDao = new CircularDao(con);
        long maxCount = cDao.getGomiCount(bean);
        log__.debug("件数 = " + maxCount);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);

        paramMdl.setCir010pageNum1(nowPage);
        paramMdl.setCir010pageNum2(nowPage);
        paramMdl.setCir010PageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<CircularDspModel>();
        }

        return cDao.getGomiList(bean);
    }

    /**
     * <br>[機  能] 回覧情報(ラベル)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param limit 1ページの表示件数
     * @param cacSid アカウントSID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    private List < CircularDspModel > __getEncLabelList(
            Cir010ParamModel paramMdl,
        Connection con,
        int limit,
        int cacSid) throws SQLException {
        //ラベル名取得
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        String labelName = cirBiz.getLabelName(paramMdl.getCir010SelectLabelSid(), cacSid);
        if (labelName == null) {
            return null;
        }
        paramMdl.setCir010TitleLabelName(labelName);

        int nowPage = paramMdl.getCir010pageNum1();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //検索用Modelを作成
        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);
        bean.setLimit(limit);
        bean.setOrderKey(paramMdl.getCir010orderKey());
        bean.setSortKey(paramMdl.getCir010sortKey());
        bean.setLabelSid(paramMdl.getCir010SelectLabelSid());

        //件数カウント
        CircularDao cDao = new CircularDao(con);
        long maxCount = cDao.getEncLabelCount(bean);
        log__.debug("件数 = " + maxCount);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        bean.setStart(start);
        paramMdl.setCir010pageNum1(nowPage);
        paramMdl.setCir010pageNum2(nowPage);
        paramMdl.setCir010PageLabel(PageUtil.createPageOptions(maxCount, limit));

        if (maxCount < 1) {
            return new ArrayList<CircularDspModel>();
        }

        return cDao.getEncLabelList(bean);
    }


    /**
     * <br>[機  能] 削除する回覧板のタイトルを取得する
     * <br>[解  説] 複数存在する場合は改行を挿入する
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cacSid アカウントSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return String 削除する回覧板名称
     * @throws SQLException SQL実行例外
     */
    public String getDeleteCirName(Cir010ParamModel paramMdl, int cacSid, Connection con,
                                RequestModel reqMdl)
    throws SQLException {

        List < CircularDspModel > cList = __getConfList(paramMdl, cacSid, con);
        int mikakkuCount = 0;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //回覧板名称取得
        StringBuilder deleteCir = new StringBuilder();
        for (int i = 0; i < cList.size(); i++) {
            CircularDspModel ciMdl = cList.get(i);

            String cmdMode = NullDefault.getString(paramMdl.getCir010cmdMode(), "");
            if (cmdMode.equals(GSConstCircular.MODE_JUSIN)) {
                //受信
                if (ciMdl.getCvwConf() == GSConstCircular.CONF_UNOPEN) {
                    //未確認
                    mikakkuCount++;
                    deleteCir.append("・");
                    deleteCir.append(NullDefault.getString(
                            StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
                    deleteCir.append(gsMsg.getMessage("schedule.44") + gsMsg.getMessage("cir.49"));

                } else {
                    //確認済み
                    deleteCir.append("・");
                    deleteCir.append(NullDefault.getString(
                            StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
                }

            } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
                //送信済み
                deleteCir.append("・");
                deleteCir.append(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));

            } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
                //ゴミ箱
                deleteCir.append("・");

                if (ciMdl.getJsFlg().equals(GSConstCircular.MODE_JUSIN)) {
                    String textJusin = gsMsg.getMessage("cmn.receive2");
                    deleteCir.append("[ " + textJusin + " ] ");
                } else if (ciMdl.getJsFlg().equals(GSConstCircular.MODE_SOUSIN)) {
                    String textSosin = gsMsg.getMessage("cmn.sent2");
                    deleteCir.append("[ " + textSosin + " ] ");
                }
                deleteCir.append(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
            } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {
                //ラベル
                if (ciMdl.getJsFlg().equals(GSConstCircular.MODE_JUSIN)) {
                    if (ciMdl.getCvwConf() == GSConstCircular.CONF_UNOPEN) {
                        //未確認
                        mikakkuCount++;
                        deleteCir.append("・");
                        String textJusin = gsMsg.getMessage("cmn.receive2");
                        deleteCir.append("[ " + textJusin + " ] ");
                        deleteCir.append(NullDefault.getString(
                                StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
                        deleteCir.append(
                                gsMsg.getMessage("schedule.44") + gsMsg.getMessage("cir.49"));

                    } else {
                        //確認済み
                        deleteCir.append("・");
                        String textJusin = gsMsg.getMessage("cmn.receive2");
                        deleteCir.append("[ " + textJusin + " ] ");
                        deleteCir.append(NullDefault.getString(
                                StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
                    }
                } else if (ciMdl.getJsFlg().equals(GSConstCircular.MODE_SOUSIN)) {
                    deleteCir.append("・");
                    String textSosin = gsMsg.getMessage("cmn.sent2");
                    deleteCir.append("[ " + textSosin + " ] ");
                    deleteCir.append(NullDefault.getString(
                            StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
                }
            }

            //改行を挿入
            if (i < cList.size() - 1) {
                deleteCir.append(GSConst.NEW_LINE_STR);
            }
        }

        paramMdl.setMikakkuCount(mikakkuCount);
        return deleteCir.toString();
    }



    /**
     * <br>[機  能] ログで出力する回覧板のタイトルを取得する
     * <br>[解  説] 複数存在する場合は改行を挿入する
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cacSid アカウントSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return String 回覧板名称
     * @throws SQLException SQL実行例外
     */
    public String getLogSelectCirName(
            Cir010ParamModel paramMdl,
            int cacSid,
            Connection con,
            RequestModel reqMdl)
    throws SQLException {

        //回覧板SIDを取得
        String[] delInfSidBase = paramMdl.getCir010delInfSid();
        String[] delInfSid = new String[delInfSidBase.length];
        ArrayList<String> juList = new ArrayList<String>();
        ArrayList<String> soList = new ArrayList<String>();

        for (int i = 0; i < delInfSidBase.length; i++) {
            String[] splitSid = delInfSidBase[i].split("-");
            //回覧板SIDを配列にセット
            delInfSid[i] = splitSid[0];

            if (splitSid[1].equals(GSConstCircular.MODE_JUSIN)) {
                //受信リストに追加
                juList.add(splitSid[0]);

            } else if (splitSid[1].equals(GSConstCircular.MODE_SOUSIN)) {
                //送信リストに追加
                soList.add(splitSid[0]);
            }
        }

        //受信回覧板SID
        String[] juSid = juList.toArray(new String[juList.size()]);
        //送信回覧板SID
        String[] soSid = soList.toArray(new String[soList.size()]);

        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);

        List < CircularDspModel > cList = null;

        String cmdMode = NullDefault.getString(paramMdl.getCir010cmdMode(), "");
        if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱
            CircularDao ciDao = new CircularDao(con);
            cList = ciDao.getGomiConfList(bean, juSid, soSid);
        } else {
            CircularDao ciDao = new CircularDao(con);
            cList = ciDao.getLabelConfList(bean, juSid, soSid);
        }

        //回覧板名称取得
        StringBuilder cir = new StringBuilder();
        for (int i = 0; i < cList.size(); i++) {
            CircularDspModel ciMdl = cList.get(i);
            GsMessage gsMsg = new GsMessage(reqMdl);
            if (ciMdl.getJsFlg().equals(GSConstCircular.MODE_JUSIN)) {
                cir.append("・");
                String textJusin = gsMsg.getMessage("cmn.receive2");
                    cir.append("[ " + textJusin + " ] ");
                cir.append(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
            } else if (ciMdl.getJsFlg().equals(GSConstCircular.MODE_SOUSIN)) {
                cir.append("・");
                String textSosin = gsMsg.getMessage("cmn.sent2");
                cir.append("[ " + textSosin + " ] ");
                cir.append(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
            }
            //改行を挿入
            if (i < cList.size() - 1) {
                cir.append(System.getProperty("line.separator"));
            }
        }
        return cir.toString();
    }


    /**
     * <br>[機  能] 選択した回覧板SID(複数)から回覧板情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cacSid アカウントSID
     * @param con コネクション
     * @return List in CircularDspModel
     * @throws SQLException SQL実行例外
     */
    private List<CircularDspModel>  __getConfList(
            Cir010ParamModel paramMdl,
            int cacSid,
            Connection con) throws SQLException {

        //回覧板SIDを取得
        String[] delInfSidBase = paramMdl.getCir010delInfSid();
        String[] delInfSid = new String[delInfSidBase.length];
        ArrayList<String> juList = new ArrayList<String>();
        ArrayList<String> soList = new ArrayList<String>();

        for (int i = 0; i < delInfSidBase.length; i++) {
            String[] splitSid = delInfSidBase[i].split("-");
            //回覧板SIDを配列にセット
            delInfSid[i] = splitSid[0];

            if (splitSid[1].equals(GSConstCircular.MODE_JUSIN)) {
                //受信リストに追加
                juList.add(splitSid[0]);

            } else if (splitSid[1].equals(GSConstCircular.MODE_SOUSIN)) {
                //送信リストに追加
                soList.add(splitSid[0]);
            }
        }

        //受信回覧板SID
        String[] juSid = juList.toArray(new String[juList.size()]);
        //送信回覧板SID
        String[] soSid = soList.toArray(new String[soList.size()]);

        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);

        List < CircularDspModel > cList = null;

        //処理モードで処理を分岐
        //回覧板SID(複数)から回覧板情報を取得する
        String cmdMode = NullDefault.getString(paramMdl.getCir010cmdMode(), "");
        if (cmdMode.equals(GSConstCircular.MODE_JUSIN)) {
            //受信
            CircularDao ciDao = new CircularDao(con);
            cList = ciDao.getJusinConfList(bean, delInfSid);

        } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
            //送信済み
            CirInfDao ciDao = new CirInfDao(con);
            cList = ciDao.getCirListFromCirSid(delInfSid);

        } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱
            CircularDao ciDao = new CircularDao(con);
            cList = ciDao.getGomiConfList(bean, juSid, soSid);
        } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {
            //ラベル
            CircularDao ciDao = new CircularDao(con);
            cList = ciDao.getLabelConfList(bean, juSid, soSid);
        }

        return cList;
    }

    /**
     * <br>[機  能] 選択された回覧板を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void deleteCir(Cir010ParamModel paramMdl, Connection con, int cacSid)
    throws SQLException {

        //回覧板SIDを取得
        String[] delInfSidBase = paramMdl.getCir010delInfSid();
        String[] delInfSid = new String[delInfSidBase.length];
        ArrayList<String> juList = new ArrayList<String>();
        ArrayList<String> soList = new ArrayList<String>();

        for (int i = 0; i < delInfSidBase.length; i++) {
            String[] splitSid = delInfSidBase[i].split("-");
            //回覧板SIDを配列にセット
            delInfSid[i] = splitSid[0];

            if (splitSid[1].equals(GSConstCircular.MODE_JUSIN)) {
                //受信リストに追加
                juList.add(splitSid[0]);

            } else if (splitSid[1].equals(GSConstCircular.MODE_SOUSIN)) {
                //送信リストに追加
                soList.add(splitSid[0]);
            }
        }

        //受信回覧板SID
        String[] juSid = juList.toArray(new String[juList.size()]);
        //送信回覧板SID
        String[] soSid = soList.toArray(new String[soList.size()]);
        UDate now = new UDate();

        try {
            con.setAutoCommit(false);

            //処理モードで処理を分岐
            String cmdMode = NullDefault.getString(paramMdl.getCir010cmdMode(), "");
            if (cmdMode.equals(GSConstCircular.MODE_JUSIN)) {
                //受信
                CirViewModel bean = new CirViewModel();
                bean.setCvwDsp(GSConstCircular.DSPKBN_DSP_NG);
                bean.setCvwEuid(cacSid);
                bean.setCvwEdate(now);
                bean.setCacSid(cacSid);

                //選択された回覧板の状態区分を更新する(論理削除)
                CirViewDao cvDao = new CirViewDao(con);
                cvDao.updateDspFlg(bean, delInfSid);

            } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
                //送信済み
                CirInfModel bean = new CirInfModel();
                bean.setCifJkbn(GSConstCircular.DSPKBN_DSP_NG);
                bean.setCifEuid(cacSid);
                bean.setCifEdate(now);

                //選択された回覧板の状態区分を更新する(論理削除)
                CirInfDao cDao = new CirInfDao(con);
                cDao.updateJkbn(bean, delInfSid);

                //回覧先の状態区分を更新する(削除)
                CirViewDao cirViewDao = new CirViewDao(con);
                CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
                for (String soCifSid : soList) {
                    cirViewDao.deleteAllView(Integer.parseInt(soCifSid), cacSid, now);
                    labelViewDao.deleteCircularLabel(Integer.parseInt(soCifSid));
                }

            } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
                //ゴミ箱
                CirViewModel juBean = new CirViewModel();
                juBean.setCvwDsp(GSConstCircular.DSPKBN_DSP_DEL);
                juBean.setCvwEuid(cacSid);
                juBean.setCvwEdate(now);
                juBean.setCacSid(cacSid);
                //選択された受信回覧板の状態区分を更新する(論理削除)
                CirViewDao cvDao = new CirViewDao(con);
                cvDao.updateDspFlg(juBean, juSid);

                CirInfModel soBean = new CirInfModel();
                soBean.setCifJkbn(GSConstCircular.DSPKBN_DSP_DEL);
                soBean.setCifEuid(cacSid);
                soBean.setCifEdate(now);
                //選択された送信回覧板の状態区分を更新する(論理削除)
                CirInfDao cDao = new CirInfDao(con);
                cDao.updateJkbn(soBean, soSid);
                // ラベル情報削除
                // 回覧板送信ラベル削除
                CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
                labelInfDao.deleteCircularLabel(soSid);
                // 回覧板受信ラベル削除
                CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
                labelViewDao.deleteCircularLabel(juSid);

            } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {
                if (juSid != null && juSid.length >= 1) {
                    //受信
                    CirViewModel viBean = new CirViewModel();
                    viBean.setCvwDsp(GSConstCircular.DSPKBN_DSP_NG);
                    viBean.setCvwEuid(cacSid);
                    viBean.setCvwEdate(now);
                    viBean.setCacSid(cacSid);
                    //選択された回覧板の状態区分を更新する(論理削除)
                    CirViewDao cvDao = new CirViewDao(con);
                    cvDao.updateDspFlg(viBean, juSid);
                }
                if (soSid != null && soSid.length >= 1) {
                    //送信済み
                    CirInfModel bean = new CirInfModel();
                    bean.setCifJkbn(GSConstCircular.DSPKBN_DSP_NG);
                    bean.setCifEuid(cacSid);
                    bean.setCifEdate(now);

                    //選択された回覧板の状態区分を更新する(論理削除)
                    CirInfDao cDao = new CirInfDao(con);
                    cDao.updateJkbn(bean, soSid);

                    //回覧先の状態区分を更新する(削除)
                    CirViewDao cirViewDao = new CirViewDao(con);
                    for (String soCifSid : soList) {
                        cirViewDao.deleteAllView(Integer.parseInt(soCifSid), cacSid, now);
                    }
                }
            }

            con.commit();
        } catch (SQLException e) {
            log__.warn("回覧板削除に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        }
    }

    /**
     * <br>[機  能] 選択された回覧板を元に戻す
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void comeBackCir(Cir010ParamModel paramMdl, Connection con, int cacSid)
    throws SQLException {

        //回覧板SIDを取得
        String[] delInfSidBase = paramMdl.getCir010delInfSid();
        String[] delInfSid = new String[delInfSidBase.length];
        ArrayList<String> juList = new ArrayList<String>();
        ArrayList<String> soList = new ArrayList<String>();

        for (int i = 0; i < delInfSidBase.length; i++) {
            String[] splitSid = delInfSidBase[i].split("-");
            //回覧板SIDを配列にセット
            delInfSid[i] = splitSid[0];

            if (splitSid[1].equals(GSConstCircular.MODE_JUSIN)) {
                //受信リストに追加
                juList.add(splitSid[0]);

            } else if (splitSid[1].equals(GSConstCircular.MODE_SOUSIN)) {
                //送信リストに追加
                soList.add(splitSid[0]);
            }
        }

        //受信回覧板SID
        String[] juSid = juList.toArray(new String[juList.size()]);
        //送信回覧板SID
        String[] soSid = soList.toArray(new String[soList.size()]);
        UDate now = new UDate();

        try {
            con.setAutoCommit(false);

            //ゴミ箱
            CirViewModel juBean = new CirViewModel();
            juBean.setCvwDsp(GSConstCircular.DSPKBN_DSP_OK);
            juBean.setCvwEuid(cacSid);
            juBean.setCvwEdate(now);
            juBean.setCacSid(cacSid);
            //選択された受信回覧板の状態区分を更新する(復帰)
            CirViewDao cvDao = new CirViewDao(con);
            cvDao.updateDspFlg(juBean, juSid);

            CirInfModel soBean = new CirInfModel();
            soBean.setCifJkbn(GSConstCircular.DSPKBN_DSP_OK);
            soBean.setCifEuid(cacSid);
            soBean.setCifEdate(now);
            //選択された送信回覧板の状態区分を更新する(復帰)
            CirInfDao cDao = new CirInfDao(con);
            cDao.updateJkbn(soBean, soSid);

            con.commit();
        } catch (SQLException e) {
            log__.warn("回覧板復帰に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        }
    }

    /**
     * <br>[機  能] ゴミ箱のデータ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param cacSid アカウントSID
     * @param con コネクション
     * @return cnt 件数
     * @throws SQLException SQL実行時例外
     */
    public int getGomibakoCnt(Cir010ParamModel paramMdl, int cacSid, Connection con)
        throws SQLException {

        //検索用Modelを作成
        CirSearchModel bean = new CirSearchModel();
        bean.setCacSid(cacSid);

        CircularDao cDao = new CircularDao(con);
        int cnt = cDao.getGomiCount(bean);

        return cnt;
    }

    /**
     * <br>[機  能] 全ての回覧板を削除する(ゴミ箱内)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void deleteAllCir(Cir010ParamModel paramMdl, Connection con, int cacSid)
    throws SQLException {

        UDate now = new UDate();

        try {
            con.setAutoCommit(false);

            CirViewModel juBean = new CirViewModel();
            juBean.setCvwDsp(GSConstCircular.DSPKBN_DSP_DEL);
            juBean.setCvwEuid(cacSid);
            juBean.setCvwEdate(now);
            juBean.setCacSid(cacSid);
            //受信ラベルを削除する
            CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
            labelViewDao.deleteGomiCircularLabel(juBean);


            //受信回覧板の状態区分を更新する(論理削除)
            CirViewDao cvDao = new CirViewDao(con);
            cvDao.updateAllView(juBean);

            CirInfModel soBean = new CirInfModel();
            soBean.setCifJkbn(GSConstCircular.DSPKBN_DSP_DEL);
            soBean.setCifEuid(cacSid);
            soBean.setCifEdate(now);
            soBean.setCifAuid(cacSid);
            // 回覧板送信ラベル削除
            CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
            labelInfDao.deleteGomiCircularLabel(soBean);

            //送信回覧板の状態区分を更新する(論理削除)
            CirInfDao cDao = new CirInfDao(con);
            cDao.updateAllUserCir(soBean);


            con.commit();
        } catch (SQLException e) {
            log__.warn("回覧板削除に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        }
    }

    /**
     * <br>[機  能] 自動リロード時間を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @return int 自動リロード時間
     * @throws SQLException SQL実行例外
     */
    private int __getReloadTime(Connection con, int userSid)
    throws SQLException {

        int reloadTime = GSConstCircular.AUTO_RELOAD_10MIN;

        CirUserDao dao = new CirUserDao(con);
        CirUserModel model = dao.getCirUserInfo(userSid);
        if (model != null) {
            reloadTime = model.getCurReload();
        }

        return reloadTime;
    }

    /**
     * アカウント情報を設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setAccountInf(
                                Cir010ParamModel paramMdl,
                                Connection con,
                                RequestModel reqMdl)
    throws SQLException {

        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = null;

        //アカウントを取得
        if (paramMdl.getCirViewAccount() <= 0) {
            //デフォルトのアカウントを取得
            cacMdl = cacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        } else {
            //選択されたアカウントを取得
            cacMdl = cacDao.select(paramMdl.getCirViewAccount());
        }

        if (cacMdl != null) {
            //アカウント
            paramMdl.setCirViewAccount(cacMdl.getCacSid());
            //アカウント名
            paramMdl.setCirViewAccountName(cacMdl.getCacName());

            if (cacMdl.getCacTheme() !=  GSConstCircular.CAC_THEME_NOSET) {
                CmnThemeDao dao = new CmnThemeDao(con);
                CmnThemeModel model = dao.select(cacMdl.getCacTheme());
            if (model.getCtmPath() != null) {
                //アカウントテーマ
                paramMdl.setCir010AccountTheme(model.getCtmPath());
                }
            }
        }
        //使用可能なアカウントの一覧を取得する
        Cir180Dao dao = new Cir180Dao(con);
        List<AccountDataModel> acList
          = dao.getAccountList(reqMdl.getSmodel().getUsrsid());
        List<AccountDataModel> acInfList = new ArrayList<AccountDataModel>();
        CirCommonBiz cirBiz = new CirCommonBiz();
        for (AccountDataModel acMdl:acList) {
            int cacSid = acMdl.getAccountSid();
            int mdkCnt = cirBiz.getUnopenedCirCnt(cacSid, con);
            acMdl.setAccountMidokuCount(mdkCnt);
            acInfList.add(acMdl);
        }
        paramMdl.setCir010AccountList(acInfList);
    }


    /**
     * <br>[機  能] ラベルを付加する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエスト情報
     * @param res レスポンス
     * @param con コネクション
     * @param paramMdl Cir010ParamModel
     * @param mtCon 採番コントローラ
     * @param userSid ユーザSID
     * @return 処理結果
     * @throws SQLException SQL実行時例外
     */
    public boolean setLabelForCircular(ActionMapping map,
                                    RequestModel reqMdl,
                                    HttpServletResponse res,
                                    Connection con,
                                    Cir010ParamModel paramMdl,
                                    MlCountMtController mtCon, int userSid)
    throws SQLException {
        boolean ret = true;
        int type = paramMdl.getCir010addLabelType();
        int cacSid = paramMdl.getCirViewAccount();
        int claSid = paramMdl.getCir010addLabel();
        try {
            // ラベルを新規追加する場合
            if (type == GSConstCircular.ADDLABEL_NEW) {
                //ラベルSID採番
                claSid = (int) mtCon.getSaibanNumber(
                        GSConstCircular.SBNSID_CIRCULAR,
                        GSConstCircular.SBNSID_SUB_LABEL,
                        userSid);
                //ラベル登録Model
                UDate now = new UDate();
                CirLabelModel cirMdl = new CirLabelModel();
                CirLabelDao cirDao = new CirLabelDao(con);
                cirMdl.setClaSid(claSid);
                cirMdl.setCacSid(cacSid);
                cirMdl.setClaName(paramMdl.getCir010addLabelName());
                cirMdl.setClaOrder(cirDao.maxSortNumber(cacSid) + 1);
                cirMdl.setClaAuid(userSid);
                cirMdl.setClaAdate(now);
                cirMdl.setClaEuid(userSid);
                cirMdl.setClaEdate(now);
                //登録
                cirDao.insert(cirMdl);
            }

            // ラベル付与
            CirInfLabelDao infDao = new CirInfLabelDao(con);
            CirViewLabelDao viewDao = new CirViewLabelDao(con);
            for (String strCirSid: paramMdl.getCir010delInfSid()) {
                String[] splitSid = strCirSid.split("-");
                int cirSid = NullDefault.getInt(splitSid[0], -1);
                String kbn = splitSid[1];

                if (kbn.equals(GSConstCircular.MODE_JUSIN)) {
                    CirViewLabelModel mdl = new CirViewLabelModel();
                    mdl.setCacSid(cacSid);
                    mdl.setClaSid(claSid);
                    mdl.setCifSid(cirSid);
                    //受信
                    viewDao.delete(mdl);
                    viewDao.insert(mdl);
                } else if (splitSid[1].equals(GSConstCircular.MODE_SOUSIN)) {
                    CirInfLabelModel mdl = new CirInfLabelModel();
                    mdl.setCacSid(cacSid);
                    mdl.setClaSid(claSid);
                    mdl.setCifSid(cirSid);
                    //受信
                    infDao.delete(mdl);
                    infDao.insert(mdl);
                }

            }

        } catch (Exception e) {
            ret = false;
            log__.error("ラベル追加に失敗しました。", e);
        }

        return ret;
    }

    /**
     * <br>[機  能] ラベルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl リクエスト情報
     * @param reqMdl リクエスト情報
     * @return 処理結果
     * @throws SQLException SQL実行時例外
     */
    public boolean deleteLabelForCircular(
            Connection con,
            Cir010ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {
        boolean ret = true;
        int cacSid = paramMdl.getCirViewAccount();
        int claSid = paramMdl.getCir010delLabel();
        try {
            // ラベル削除
            CirInfLabelDao infDao = new CirInfLabelDao(con);
            CirViewLabelDao viewDao = new CirViewLabelDao(con);
            for (String strCirSid: paramMdl.getCir010delInfSid()) {
                String[] splitSid = strCirSid.split("-");
                int cirSid = NullDefault.getInt(splitSid[0], -1);
                String kbn = splitSid[1];

                if (kbn.equals(GSConstCircular.MODE_JUSIN)) {
                    CirViewLabelModel mdl = new CirViewLabelModel();
                    mdl.setCacSid(cacSid);
                    mdl.setClaSid(claSid);
                    mdl.setCifSid(cirSid);
                    //受信
                    viewDao.delete(mdl);
                } else if (splitSid[1].equals(GSConstCircular.MODE_SOUSIN)) {
                    CirInfLabelModel mdl = new CirInfLabelModel();
                    mdl.setCacSid(cacSid);
                    mdl.setClaSid(claSid);
                    mdl.setCifSid(cirSid);
                    //受信
                    infDao.delete(mdl);
                }
            }
        } catch (Exception e) {
            ret = false;
            log__.error("ラベル削除に失敗しました。", e);
        }
        return ret;
    }

    /**
     * ラベルリストを設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return ラベルリスト
     */
    private List<LabelDataModel> __getLabelListInf(
                                Connection con,
                                int cacSid)
    throws SQLException {
        CirLabelDao cirDao = new CirLabelDao(con);
        List<LabelDataModel> labels = cirDao.selectLabelWidthCountList(cacSid);
        List<LabelDataModel> ret =  new ArrayList<LabelDataModel>();
        for (LabelDataModel mdl:labels) {
            LabelDataModel label = new LabelDataModel();
            label.setLabelSid(mdl.getLabelSid());
            String labelName = mdl.getLabelName();
            if (labelName != null && labelName.length() > 9) {
                labelName = labelName.substring(0, 9) + "…";
            }
            label.setLabelName(labelName);
            label.setMidokuCount(mdl.getMidokuCount());
            ret.add(label);
        }
        return ret;

    }

    /**
     * ラベルリストを設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return ラベルリスト
     */
    public List<LabelDataModel> getJsonLabelList(
                                Connection con,
                                int cacSid)
    throws SQLException {
        CirLabelDao cirDao = new CirLabelDao(con);
        List<LabelDataModel> labels = cirDao.selectLabelList(cacSid);
        List<LabelDataModel> ret =  new ArrayList<LabelDataModel>();
        for (LabelDataModel mdl:labels) {
            LabelDataModel label = new LabelDataModel();
            label.setLabelSid(mdl.getLabelSid());
            String labelName = StringUtilHtml.transToHTmlPlusAmparsant(mdl.getLabelName());
            label.setLabelName(labelName);
            ret.add(label);
        }
        return ret;

    }



}
