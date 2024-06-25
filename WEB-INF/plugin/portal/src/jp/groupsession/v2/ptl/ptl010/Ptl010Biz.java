package jp.groupsession.v2.ptl.ptl010;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.MainScreenInfo;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.PortletInfo;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnMdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnMdispModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.man.dao.base.PtlAdmConfDao;
import jp.groupsession.v2.man.dao.base.PtlMainConfDao;
import jp.groupsession.v2.man.dao.base.PtlPortalDao;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.dao.base.PtlUconfDao;
import jp.groupsession.v2.man.man001.Man001Form;
import jp.groupsession.v2.man.man320.Man320Biz;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;
import jp.groupsession.v2.man.model.base.PtlMainConfModel;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;
import jp.groupsession.v2.man.model.base.PtlUconfModel;
import jp.groupsession.v2.ptl.dao.PtlPortletDao;
import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.ptl.ptl010.model.Ptl010Model;
import jp.groupsession.v2.ptl.ptl100.Ptl100Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] ポータルトップ画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl010Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param usModel ユーザモデル
     * @param pconfig プラグイン情報
     * @param req リクエスト
     * @param context ServletContext
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @return メイン遷移フラグ
     * @throws Exception SQL実行例外
     */
    public boolean setInitData(
            Ptl010ParamModel paramMdl, Connection con, BaseUserModel usModel,
            PluginConfig pconfig, HttpServletRequest req, ServletContext context,
            RequestModel reqMdl, String appRootPath)
    throws Exception {
        log__.debug("START");

        //ポータル編集区分
        int editKbn = __getAdmConfEditKbn(paramMdl, con);

        //利用可能なポータル一覧を取得する。
        PtlPortalDao ptlDao = new PtlPortalDao(con);
        List<PtlPortalModel> ptlList = ptlDao.getReadPortalList(editKbn, usModel.getUsrsid());

        if (ptlList == null || ptlList.size() <= 1) {
            //ポータルがない場合にはメイン画面に遷移する。
            return true;
        }
        paramMdl.setPtl010ptlList(ptlList);

        //表示するポータルSIDを取得する。パラメータが存在する場合は登録する。
        int ptlSid = __getDspPtlSid(paramMdl, con, usModel.getUsrsid());
        paramMdl.setPtlMainSid(ptlSid);

        if (ptlSid < 1) {
            //メイン画面に遷移する。
            Man001Form man001Form = new Man001Form();
            man001Form.setPtlMainSid(ptlSid);
            req.setAttribute("man001Form", man001Form);
            return true;
        }

        CommonBiz cmnBiz = new CommonBiz();

        //インフォーメーション情報を取得
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<MainInfoMessageModel> infoList = cmnBiz.getInfoMessageList(
                gsMsg, reqMdl, con, usModel, pconfig, context);
        paramMdl.setInfoMsgs(infoList);
        Man320Biz biz = new Man320Biz();
        if (biz.isInfoAdminAuth(reqMdl, con)) {
            paramMdl.setInfoConf(true);
        }

        //プラグインとポートレットを設定する。
        __setDspParts(paramMdl, con, pconfig, ptlSid, reqMdl, appRootPath);

        //ポータル管理フラグを設定する。
        paramMdl.setPtl010ptlAdminFlg(cmnBiz.isPortalAdmin(con, usModel, pconfig));

        //現在表示ポータルSID
        paramMdl.setDspPtlSid(ptlSid);

        //現在表示ポータル名
        for (PtlPortalModel mdl : ptlList) {
            if (mdl.getPtlSid() == ptlSid) {
                paramMdl.setDspPtlName(mdl.getPtlName());
                break;
            }
        }

        log__.debug("End");
        return false;
    }

    /**
     * <br>[機  能] 管理者設定よりポータル編集区分を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return int 区分
     */
    private int __getAdmConfEditKbn(Ptl010ParamModel paramMdl, Connection con)
    throws SQLException {

        int editKbn = GSConstPortal.EDIT_KBN_PUBLIC;

        PtlAdmConfDao admConfDao = new PtlAdmConfDao(con);

        //ポータル管理者設定を取得する。
        PtlAdmConfModel admConfModel = admConfDao.select();
        if (admConfModel != null) {
            editKbn = admConfModel.getPacPtlEditkbn();
        }

        return editKbn;
    }

    /**
     * <br>[機  能] 表示するポータル情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグイン情報
     * @param ptlSid ポータルSID
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行例外
     * @throws UnsupportedEncodingException 文字エンコード時例外
     * @throws NoSuchAlgorithmException SHA-512アルゴリズムが使用不可
     * @throws EncryptionException 暗号化に失敗時例外
     */
    private void __setDspParts(Ptl010ParamModel paramMdl, Connection con,
                            PluginConfig pconfig, int ptlSid,
                            RequestModel reqMdl, String appRootPath)
                                    throws SQLException, EncryptionException,
                                    NoSuchAlgorithmException, UnsupportedEncodingException {

        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);
        PtlPortalPositionDao positionDao = new PtlPortalPositionDao(con);
        PtlPortletDao ptlDao = new PtlPortletDao(con);
        CommonBiz cmnBiz = new CommonBiz();

        //レイアウト情報を取得する。
        List<PtlPortalLayoutModel> layoutList = layoutDao.select(ptlSid);

        //レイアウトの表示区分を設定する。
        for (PtlPortalLayoutModel layoutModel : layoutList) {

            switch (layoutModel.getPlyPosition()) {
                case GSConstPortal.LAYOUT_POSITION_TOP :
                    paramMdl.setPtl010areaTop(layoutModel.getPtsView());
                    break;
                case GSConstPortal.LAYOUT_POSITION_BOTTOM :
                    paramMdl.setPtl010areaBottom(layoutModel.getPtsView());
                    break;
                case GSConstPortal.LAYOUT_POSITION_LEFT :
                    paramMdl.setPtl010areaLeft(layoutModel.getPtsView());
                    break;
                case GSConstPortal.LAYOUT_POSITION_CENTER :
                    paramMdl.setPtl010areaCenter(layoutModel.getPtsView());
                    break;
                case GSConstPortal.LAYOUT_POSITION_RIGHT :
                    paramMdl.setPtl010areaRight(layoutModel.getPtsView());
                    break;
                default:
                    break;
            }
        }

        //位置情報を取得する。
        List<PtlPortalPositionModel> positionList = positionDao.getViewPtlPosition(ptlSid);
        List<String> jsList = new ArrayList<String>();
        List<String> cssList = new ArrayList<String>();
        List<Ptl010Model> dspAllList = new ArrayList<Ptl010Model>();
        List<Ptl010Model> dspTopList = new ArrayList<Ptl010Model>();
        List<Ptl010Model> dspBottomList = new ArrayList<Ptl010Model>();
        List<Ptl010Model> dspLeftList = new ArrayList<Ptl010Model>();
        List<Ptl010Model> dspCenterList = new ArrayList<Ptl010Model>();
        List<Ptl010Model> dspRightList = new ArrayList<Ptl010Model>();

        Ptl010Model dspModel = null;
        PtlPortletModel ptlMdl = null;

        for (PtlPortalPositionModel positionModel : positionList) {
            if (positionModel.getPtpView() == GSConstPortal.LAYOUT_VIEW_OFF) {
                continue;
            }
            dspModel = new Ptl010Model();
            dspModel.setPtpType(positionModel.getPtpType());
            dspModel.setItemId(positionModel.getPtpItemid());

            if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PLUGIN) {
                //プラグインの場合

                String pluginId = positionModel.getPctPid();
                String screenId = positionModel.getMscId();

                //プラグインの使用可否
                if (!cmnBiz.isCanUsePlugin(pluginId, pconfig)) {
                    continue;
                }

                dspModel.setPluginId(pluginId);
                dspModel.setScreenId(screenId);
                dspModel.setPluginName(pconfig.getPlugin(pluginId).getName(reqMdl));
                dspModel.setScreenUrl("../" + pluginId + "/" + screenId + ".do");

                Plugin pluginData = pconfig.getPlugin(pluginId);
                if (pluginData != null) {
                    ArrayList<MainScreenInfo> screenList = pluginData.getMainScreenInfo();
                    if (screenList != null && !screenList.isEmpty()) {
                        for (MainScreenInfo screenInfo : screenList) {
                            if (screenInfo.getId().equals(screenId)) {
                                dspModel.setLoadScript(screenInfo.isLoadScript());
                                break;
                            }
                        }
                    }
                }

                //JavaScriptを設定
                String jsPath = "../" + pluginId + "/js/" + screenId + ".js?"
                                + GSConst.VERSION_PARAM;
                if (!jsList.contains(jsPath)) {
                    jsList.add(jsPath);
                }

                //CSSを設定
                String cssPath = "../" + pluginId + "/css/" + pluginId + ".css?"
                                 + GSConst.VERSION_PARAM;
                if (!cssList.contains(cssPath)) {
                    cssList.add(cssPath);
                }

            } else if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PORTLET) {
                //ポートレットの場合

                ptlMdl = ptlDao.select(positionModel.getPltSid());
                if (ptlMdl != null) {
                    dspModel.setPtlTitle(ptlMdl.getPltName());

                    //Aタグクリック時に${TIME}の時間取得のため、${TIME}と${HASH_UID_TM_KW}はそのまま画面に設定する
                    CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
                    CmnUsrmInfModel usrInfMdl = usrInfDao.select(reqMdl.getSmodel().getUsrsid());
                    //GS予約語が含まれていた場合、置換する
                    String content = cmnBiz.replaceGSReservedWordNoTime(
                            reqMdl, con, appRootPath, ptlMdl.getPltContent(), usrInfMdl, true);

                    if (ptlMdl.getPltContentType() == Ptl100Form.PTL100_CONTENTTYPE_HTML) {
                        dspModel.setPtlContent(content);
                    } else {
                        dspModel.setPtlContent(ptlMdl.getPltContent());
                    }
                    dspModel.setPtlType(ptlMdl.getPltContentType());

                    dspModel.setPtlBorderKbn(ptlMdl.getPltBorder());
                }

            } else if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PLUGINPORTLET) {
                //プラグインポートレット場合
                //プラグインの使用可否
                if (!cmnBiz.isCanUsePlugin(positionModel.getPctPid(), pconfig)) {
                    continue;
                }

                //プラグインポートレット情報を設定する。
                __setPluginPortletInfo(positionModel, dspModel,
                        con, pconfig, jsList, cssList, reqMdl);

            }
            dspAllList.add(dspModel);

            switch (positionModel.getPlyPosition()) {
                case GSConstPortal.LAYOUT_POSITION_TOP :
                    dspTopList.add(dspModel);
                    break;
                case GSConstPortal.LAYOUT_POSITION_BOTTOM :
                    dspBottomList.add(dspModel);
                    break;
                case GSConstPortal.LAYOUT_POSITION_LEFT :
                    dspLeftList.add(dspModel);
                    break;
                case GSConstPortal.LAYOUT_POSITION_CENTER :
                    dspCenterList.add(dspModel);
                    break;
                case GSConstPortal.LAYOUT_POSITION_RIGHT :
                    dspRightList.add(dspModel);
                    break;
                default:
                    break;
            }
        }

        paramMdl.setPtl010allList(dspAllList);
        paramMdl.setPtl010topList(dspTopList);
        paramMdl.setPtl010bottomList(dspBottomList);
        paramMdl.setPtl010leftList(dspLeftList);
        paramMdl.setPtl010centerList(dspCenterList);
        paramMdl.setPtl010rightList(dspRightList);

        paramMdl.setPtl010stylePath(cssList);
        paramMdl.setPtl010scriptPathList(jsList);
    }

    /**
     * <br>[機  能] 表示するポータルSIDを取得する。
     * <br>[解  説] パラメータのポータルSIDがある場合には登録する。
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @return 表示ポータルSID
     * @throws SQLException SQL実行例外
     */
    private int __getDspPtlSid(Ptl010ParamModel paramMdl, Connection con, int sessionUsrSid)
    throws SQLException {

        int ptlSid = paramMdl.getPtlMainSid();

        PtlMainConfDao mainConfDao = new PtlMainConfDao(con);

        if (ptlSid > -1) {
            //登録
            PtlMainConfModel mainConfModel = new PtlMainConfModel();
            mainConfModel.setUsrSid(sessionUsrSid);
            mainConfModel.setPtlSid(paramMdl.getPtlMainSid());
            int upCnt = mainConfDao.update(mainConfModel);
            if (upCnt < 1) {
                mainConfDao.insert(mainConfModel);
            }
        } else {

            //ポータル初期値設定(管理者)取得
            PtlAdmConfDao aConfDao = new PtlAdmConfDao(con);
            PtlAdmConfModel aModel = aConfDao.select();
            if (aModel == null) {
                aModel = new PtlAdmConfModel();
            }
            int pacKbn = aModel.getPacDefKbn();
            int pacType = aModel.getPacDefType();

            if (pacKbn == GSConstPortal.EDIT_KBN_ADM) {
                //管理者のみ設定可 の場合

                if (pacType != 0) {
                    //最後に開いたポータルを表示
                    //DBからポータルSIDを取得する。
                    PtlMainConfModel mainConfModel = mainConfDao.select(sessionUsrSid);
                    if (mainConfModel == null || mainConfModel.getPtlSid() < 1) {
                        ptlSid = GSConstPortal.PORTAL_SID_MAIN;
                    } else {
                        ptlSid = mainConfModel.getPtlSid();
                    }
                } else {
                    //最初のタブを表示
                    List<PtlPortalModel> list = paramMdl.getPtl010ptlList();
                    ptlSid = list.get(0).getPtlSid();
                }

            } else {
                //制限なし の場合

                PtlUconfDao uconfDao = new PtlUconfDao(con);
                PtlUconfModel uconfModel = new PtlUconfModel();
                uconfModel = uconfDao.select(sessionUsrSid);
                if (uconfModel == null) {
                    uconfModel = new PtlUconfModel();
                }

                int pucType = uconfModel.getPucDefType();
                if (pucType != 0) {
                    //最後に開いたポータルを表示
                    //DBからポータルSIDを取得する。
                    PtlMainConfModel mainConfModel = mainConfDao.select(sessionUsrSid);
                    if (mainConfModel == null || mainConfModel.getPtlSid() < 1) {
                        ptlSid = GSConstPortal.PORTAL_SID_MAIN;
                    } else {
                        ptlSid = mainConfModel.getPtlSid();
                    }

                } else {
                    //最初のタブを表示
                    List<PtlPortalModel> list = paramMdl.getPtl010ptlList();
                    ptlSid = list.get(0).getPtlSid();
                }
            }
        }

        return ptlSid;
    }

    /**
     * <br>[機  能] 自動リロード時間を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL例外発生
     */
    public void setReloadTime(Ptl010ParamModel paramMdl,
                            Connection con,
                            int userSid)
    throws SQLException {

        CmnMdispModel model = new CmnMdispModel();
        CmnMdispDao dao = new CmnMdispDao(con);
        UDate now = new UDate();

        model.setUsrSid(userSid);
        model.setMdpPid(GSConstCommon.MAIN_DSPVALUE[2]);

        CmnMdispModel mdispModel = dao.select(model);

        if (mdispModel != null) {
            paramMdl.setPtl010Reload(mdispModel.getMdpReload());
        } else {
            model.setMdpDsp(GSConstCommon.MAIN_RELOAD);
            model.setMdpReload(paramMdl.getPtl010Reload());
            model.setMdpAuid(userSid);
            model.setMdpAdate(now);
            model.setMdpEuid(userSid);
            model.setMdpEdate(now);
            //リロード時間の設定がされていない場合は登録を行う。
            dao.insert(model);
        }
    }

    /**
     * <br>[機  能] プラグインポートレット情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param positionModel ポータル位置情報モデル
     * @param dspModel 表示用モデル
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param jsList JSパスリスト
     * @param cssList CSSパスリスト
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    private void __setPluginPortletInfo(PtlPortalPositionModel positionModel, Ptl010Model dspModel,
            Connection con, PluginConfig pconfig, List<String> jsList, List<String> cssList,
            RequestModel reqMdl)
    throws SQLException {

        //パラメータを取得する。
        String param = "";
        if (positionModel.getPtpParamkbn() == GSConstPortal.PTP_PARAMKBN_ON) {
            PtlPortalPositionParamDao paramDao = new PtlPortalPositionParamDao(con);
            List<PtlPortalPositionParamModel> paramList = paramDao.select(
                                                              positionModel.getPtlSid(),
                                                              positionModel.getPtpItemid());
            if (paramList != null && paramList.size() > 0) {
                param = "?";
                int i = 0;
                for (PtlPortalPositionParamModel model : paramList) {
                    if (i > 0) {
                        param += "&";
                    }
                    param += model.getPpmParamName() + "=" + model.getPpmParamValue();
                    i++;
                }
            }
        }

        String pluginId = positionModel.getPctPid();
        String screenId = positionModel.getMscId();
        Plugin plug = pconfig.getPlugin(pluginId);
        dspModel.setPluginId(pluginId);
        dspModel.setScreenId(screenId);
        dspModel.setPluginName(plug.getName(reqMdl));
        dspModel.setScreenUrl("../" + pluginId + "/" + screenId + ".do" + param);

        //JavaScript使用判定
        ArrayList<PortletInfo> portletInfoList = plug.getPortletInfo();
        boolean isUseJs = true;
        for (PortletInfo pltInfo : portletInfoList) {
            if (pltInfo.getId().equals(screenId)) {
                if (!StringUtil.isNullZeroStringSpace(pltInfo.getScript())
                        && pltInfo.getScript().equals("false")) {
                    isUseJs = false;
                    break;
                }
            }
        }

        //JavaScriptを設定
        if (isUseJs) {
            String jsPath = "../" + pluginId + "/js/" + screenId + ".js?" + GSConst.VERSION_PARAM;
            if (!jsList.contains(jsPath)) {
                jsList.add(jsPath);
            }
        }

        //CSSを設定
        String cssPath = "../" + pluginId + "/css/" + pluginId + ".css?" + GSConst.VERSION_PARAM;
        if (!cssList.contains(cssPath)) {
            cssList.add(cssPath);
        }

    }

    /**
     * <br>[機  能] HTML入力ポートレットのリンククリック時のURLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param url url
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行例外
     * @return jsonData jsonコメントリスト
     * @throws EncryptionException 暗号化に失敗時例外
     * @throws UnsupportedEncodingException リンク先URLの取得に失敗
     * @throws NoSuchAlgorithmException SHA-512アルゴリズムが使用不可
     */
    public JSONObject getClickUrl(
            Connection con, RequestModel reqMdl, String url, String appRootPath)
            throws SQLException, EncryptionException,
            UnsupportedEncodingException, NoSuchAlgorithmException {

        JSONObject jsonData = new JSONObject();
        CommonBiz cmnBiz = new CommonBiz();

        //GS予約語が含まれていた場合、置換する
        url = cmnBiz.replaceGSReservedWordOnlyTime(
                reqMdl, con, appRootPath, url);

        log__.debug("生成URL=> " + url);
        jsonData.put("url", url);

        return jsonData;
    }
}
