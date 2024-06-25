package jp.groupsession.v2.ptl.ptl070;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.lang.ClassUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.information.CmnInfoMsgBiz;
import jp.groupsession.v2.cmn.config.MainScreenInfo;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.PortletInfo;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;
import jp.groupsession.v2.ptl.dao.PtlPortletDao;
import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.ptl.ptl070.model.Ptl070Model;
import jp.groupsession.v2.ptl.ptl100.Ptl100Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル プレビュー(ポップアップ)画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl070Biz.class);
    /** メインインフォメーション詳細POPUPURL */
    public static final String INFO_POPUP_URL = "return openMainInfoWindow";

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param context ServletContext
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @throws Exception 実行例外
     */
    public void setInitData(Ptl070ParamModel paramMdl, Connection con,
                            PluginConfig pconfig, ServletContext context,
                            RequestModel reqMdl, String appRootPath)
    throws Exception {
        log__.debug("START");

        int ptlSid = paramMdl.getPtlPortalSid();

        PtlPortalLayoutDao ptlLayoutDao = new PtlPortalLayoutDao(con);

        //レイアウト情報一覧を取得する。
        List<PtlPortalLayoutModel> layoutList = ptlLayoutDao.select(ptlSid);

        //レイアウトの表示区分を設定する。
        for (PtlPortalLayoutModel layoutModel : layoutList) {

            switch (layoutModel.getPlyPosition()) {
                case GSConstPortal.LAYOUT_POSITION_TOP :
                    paramMdl.setPtl070areaTop(layoutModel.getPtsView());
                case GSConstPortal.LAYOUT_POSITION_BOTTOM :
                    paramMdl.setPtl070areaBottom(layoutModel.getPtsView());
                case GSConstPortal.LAYOUT_POSITION_LEFT :
                    paramMdl.setPtl070areaLeft(layoutModel.getPtsView());
                case GSConstPortal.LAYOUT_POSITION_CENTER :
                    paramMdl.setPtl070areaCenter(layoutModel.getPtsView());
                case GSConstPortal.LAYOUT_POSITION_RIGHT :
                    paramMdl.setPtl070areaRight(layoutModel.getPtsView());
                default :
                    break;
            }
        }

        //表示一覧を設定する。
        __setDspList(paramMdl, con, pconfig, context, reqMdl, appRootPath);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param context コンテキスト
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @throws Exception 実行例外
     */
    private void __setDspList(Ptl070ParamModel paramMdl, Connection con,
                              PluginConfig pconfig,
                              ServletContext context,
                              RequestModel reqMdl,
                              String appRootPath)
    throws Exception {
        log__.debug("START");

        int ptlSid = paramMdl.getPtlPortalSid();

        List<String> pluginIdList = new ArrayList<String>();

        List<String> menuPluginIdList = null;
        CmnTdispDao tdispDao = new CmnTdispDao(con);
        menuPluginIdList =
            tdispDao.getMenuPluginIdList(GSConst.SYSTEM_USER_ADMIN);
        if (menuPluginIdList == null || menuPluginIdList.isEmpty()) {
            //トップ表示設定が登録されていない場合はプラグイン設定を使用する
            pluginIdList.addAll(pconfig.getPluginIdList());
        } else {
            pluginIdList.addAll(menuPluginIdList);
            List<String> confIdList = pconfig.getPluginIdList();

            for (String confId : confIdList) {
                if (pluginIdList.indexOf(confId) < 0
                && !pconfig.getPlugin(confId).isMenuPlugin()
                && pconfig.getPlugin(confId).isMainScreenPlugin()) {
                    pluginIdList.add(confId);
                }
            }
        }

        //インフォーメーション情報を取得
        __setMainInfoMessage(con, reqMdl.getSmodel(), pconfig, paramMdl, context, reqMdl);

        //ポートレット情報を取得
        __setPluginPortlet(paramMdl, con, pconfig, ptlSid, pluginIdList, reqMdl, appRootPath);

    }

    /**
     * <br>[機  能] メインインフォーメーションメッセージをセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usModel ユーザ情報
     * @param pconfig プラグイン設定情報
     * @param paramMdl パラメータ情報
     * @param context コンテキスト
     * @param reqMdl リクエストモデル
     * @throws Exception インフォーメーション取得クラスの設定ミスの場合にスロー
     */
    private void __setMainInfoMessage(Connection con, BaseUserModel usModel,
                                    PluginConfig pconfig, Ptl070ParamModel paramMdl,
                                    ServletContext context,
                                    RequestModel reqMdl) throws Exception {
        //        __setMainInfoMessage(con, usModel, pconfig, form);
        String [] pifclss = pconfig.getMainInfoMessageImpl();
        MainInfoMessage[] info = null;
        try {
            info = __getMainInfoMessages(pifclss);
        } catch (ClassNotFoundException e) {
            log__.error("クラスが見つかりません。設定を見直してください。", e);
            throw e;
        } catch (IllegalAccessException e) {
            log__.error("クラスの設定が間違っています。設定を見直してください。", e);
            throw e;
        } catch (InstantiationException e) {
            log__.error("クラスの設定が間違っています。設定を見直してください。", e);
            throw e;
        }

        //
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("realPath", context.getRealPath("/"));

        ArrayList<MainInfoMessageModel> msgs = new ArrayList<MainInfoMessageModel>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (MainInfoMessage imsgCls : info) {
            List<MainInfoMessageModel> plist
                    = imsgCls.getMessage(paramMap, usModel.getUsrsid(), con, gsMsg, reqMdl);
            if (plist != null) {
                msgs.addAll(plist);
            }
        }

        //手動登録インフォメーションを取得
        CmnInfoMsgBiz biz = new CmnInfoMsgBiz();
        ArrayList<CmnInfoMsgModel> infoList = biz.getActiveInformationMsg(
                usModel.getUsrsid(), new UDate(), con);
        MainInfoMessageModel model = null;
        for (CmnInfoMsgModel infMdl : infoList) {
            model = new MainInfoMessageModel();
            model.setPluginId(GSConst.PLUGINID_MAIN);
            model.setLinkUrl(INFO_POPUP_URL + "(" + infMdl.getImsSid() + ")");
            model.setMessage(infMdl.getImsMsg());
            model.setOriginalMessage(infMdl.getImsMsg());
            model.setPluginName(gsMsg.getMessage("main.man031.4"));
            model.setPopupDsp(true);
            model.setIcon("../main/images/classic/menu_icon_single_info.gif");
            msgs.add(model);
        }
        paramMdl.setInfoMsgs(msgs);
    }

    /**
     * <br>[機  能] メッセージのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param classNames プラグインクラス名
     * @throws ClassNotFoundException 指定されたクラスが存在しない
     * @throws IllegalAccessException 実装クラスのインスタンス生成に失敗
     * @throws InstantiationException 実装クラスのインスタンス生成に失敗
     * @return バッチリスナー
     */
    private MainInfoMessage[] __getMainInfoMessages(String[] classNames)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MainInfoMessage[] iclasses = new MainInfoMessage[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            Object obj = ClassUtil.getObject(classNames[i]);
            iclasses[i] = (MainInfoMessage) obj;
        }
        return iclasses;
    }

    /**
     * <br>[機  能] ポートレット情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグイン情報
     * @param ptlSid ポータルSID
     * @param pluginIdList プラグイン
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行例外
     * @throws UnsupportedEncodingException 文字エンコード時例外
     * @throws NoSuchAlgorithmException SHA-512アルゴリズムが使用不可
     * @throws EncryptionException 暗号化に失敗時例外
     */
    private void __setPluginPortlet(Ptl070ParamModel paramMdl, Connection con,
                                    PluginConfig pconfig, int ptlSid,
                                    List<String> pluginIdList, RequestModel reqMdl,
                                    String appRootPath)
                                            throws SQLException, EncryptionException,
                                            NoSuchAlgorithmException, UnsupportedEncodingException {

        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);

        List<Ptl070Model> topList = new ArrayList<Ptl070Model>();
        List<Ptl070Model> bottomList = new ArrayList<Ptl070Model>();
        List<Ptl070Model> leftList = new ArrayList<Ptl070Model>();
        List<Ptl070Model> centerList = new ArrayList<Ptl070Model>();
        List<Ptl070Model> rightList = new ArrayList<Ptl070Model>();
        //位置情報を取得する。
        List<PtlPortalPositionModel> allList = ptlPositionDao.getViewPtlPosition(ptlSid);
        Ptl070Model partsModel = null;
        int num = 0;
        CommonBiz cmnBiz = new CommonBiz();

        List<Ptl070Model> urlList = new ArrayList<Ptl070Model>();
        List<String> jsList = new ArrayList<String>();
        List<String> cssList = new ArrayList<String>();

        for (PtlPortalPositionModel positionModel : allList) {

            partsModel = new Ptl070Model();

            //プラグイン情報を取得
            if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PLUGIN) {

                //プラグインの使用可否
                if (!cmnBiz.isCanUsePlugin(positionModel.getPctPid(), pconfig)) {
                    continue;
                }
                for (String pluginId : pluginIdList) {

                    log__.debug("プラグイン読込み = " + pluginId);
                    Plugin plugin = pconfig.getPlugin(pluginId);
                    if (plugin == null) {
                        continue;
                    }
                    partsModel.setPartsKbn(GSConstPortal.PTP_TYPE_PLUGIN);

                    ArrayList<MainScreenInfo> screenList = plugin.getMainScreenInfo();

                    if (screenList != null && !screenList.isEmpty()) {
                        for (int i = 0; i < screenList.size(); i++) {
                            MainScreenInfo screenInfo = screenList.get(i);

                            if (!StringUtil.isNullZeroString(screenInfo.getId())
                                && screenInfo.getId().equals(positionModel.getMscId())) {
                                String id = screenInfo.getId();
                                partsModel.setId(id);
                                partsModel.setPluginId(pluginId);
                                partsModel.setPluginName(plugin.getName(reqMdl));

                                Ptl070Model model = new Ptl070Model();
                                model.setScreenUrl("../" + pluginId + "/" + id + ".do");

                                //JavaScriptを設定
                                String jsPath = "../" + pluginId + "/js/" + id + ".js?"
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
                                model.setPtpItemid(positionModel.getPtpItemid());
                                model.setPartsKbn(positionModel.getPtpType());
                                model.setPluginId(pluginId);
                                model.setId(positionModel.getMscId());
                                urlList.add(model);
                            }
                        }
                    }
                }

            //ポートレット情報を取得
            } else if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PORTLET) {
                partsModel.setPartsKbn(GSConstPortal.PTP_TYPE_PORTLET);

                PtlPortletDao ptlDao = new PtlPortletDao(con);
                PtlPortletModel ptlMdl = ptlDao.select(positionModel.getPltSid());

                if (ptlMdl != null) {
                    partsModel.setPtlTitle(ptlMdl.getPltName());

                    //Aタグクリック時に${TIME}の時間取得のため、${TIME}と${HASH_UID_TM_KW}はそのまま画面に設定する
                    CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
                    CmnUsrmInfModel usrInfMdl = usrInfDao.select(reqMdl.getSmodel().getUsrsid());
                    //GS予約語が含まれていた場合、置換する
                    String content = cmnBiz.replaceGSReservedWordNoTime(
                            reqMdl, con, appRootPath, ptlMdl.getPltContent(), usrInfMdl, true);

                    if (ptlMdl.getPltContentType() == Ptl100Form.PTL100_CONTENTTYPE_HTML) {
                        partsModel.setPtlContent(content);
                    } else {
                        partsModel.setPtlContent(ptlMdl.getPltContent());
                    }
                    partsModel.setPtlType(ptlMdl.getPltContentType());

                    partsModel.setPtlBorderKbn(ptlMdl.getPltBorder());
                }

            } else if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PLUGINPORTLET) {
                //プラグインの使用可否
                if (!cmnBiz.isCanUsePlugin(positionModel.getPctPid(), pconfig)) {
                    continue;
                }
                //プラグインポートレット場合
                partsModel.setPartsKbn(GSConstPortal.PTP_TYPE_PLUGINPORTLET);

                //プラグインポートレット情報を設定する。
                __setPluginPortletInfo(positionModel, partsModel,
                                       con, pconfig, urlList, jsList, cssList, reqMdl);

            } else if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_INFORMATION) {
                //インフォメーション
                partsModel.setPartsKbn(GSConstPortal.PTP_TYPE_INFORMATION);

            }
            partsModel.setPtpItemid(positionModel.getPtpItemid());
            partsModel.setNum(String.valueOf(num));
            partsModel.setPtpView(positionModel.getPtpView());

            //各areaのリストに格納
            if (partsModel.getPtpView() == GSConstPortal.LAYOUT_VIEW_ON) {
                switch (positionModel.getPlyPosition()) {
                case GSConstPortal.LAYOUT_POSITION_TOP :
                    topList.add(partsModel);
                    break;
                case GSConstPortal.LAYOUT_POSITION_BOTTOM :
                    bottomList.add(partsModel);
                    break;
                case GSConstPortal.LAYOUT_POSITION_LEFT :
                    leftList.add(partsModel);
                    break;
                case GSConstPortal.LAYOUT_POSITION_CENTER :
                    centerList.add(partsModel);
                    break;
                case GSConstPortal.LAYOUT_POSITION_RIGHT :
                    rightList.add(partsModel);
                    break;
                default:
                    break;
                }
                num++;
            }
        }

        paramMdl.setPtl070topList(topList);
        paramMdl.setPtl070bottomList(bottomList);
        paramMdl.setPtl070leftList(leftList);
        paramMdl.setPtl070centerList(centerList);
        paramMdl.setPtl070rightList(rightList);
        paramMdl.setUrlList(urlList);
        paramMdl.setJsList(jsList);
        paramMdl.setCssList(cssList);
    }

    /**
     * <br>[機  能] プラグインポートレット情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param positionModel ポータル位置情報モデル
     * @param dspModel 表示用モデル
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param urlList url表示リスト
     * @param jsList javascript表示リスト
     * @param cssList css表示リスト
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    private void __setPluginPortletInfo(PtlPortalPositionModel positionModel,
                                        Ptl070Model dspModel,
                                        Connection con, PluginConfig pconfig,
                                        List<Ptl070Model> urlList,
                                        List<String> jsList, List<String> cssList,
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
        dspModel.setPluginName(pconfig.getPlugin(pluginId).getName(reqMdl));
        Ptl070Model model = new Ptl070Model();
        model.setScreenUrl("../" + pluginId + "/" + screenId + ".do" + param);

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

        model.setPtpItemid(positionModel.getPtpItemid());
        model.setPartsKbn(positionModel.getPtpType());
        model.setId(positionModel.getMscId());
        urlList.add(model);
    }

}
