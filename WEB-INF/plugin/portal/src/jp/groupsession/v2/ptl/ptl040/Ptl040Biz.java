package jp.groupsession.v2.ptl.ptl040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.biz.PortletBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.PortletInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalDao;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.ptl.dao.PtlPortletDao;
import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.ptl.ptl040.model.Ptl040Model;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル ポータル詳細画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl040Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     * @throws ClassNotFoundException 指定されたユーザリスナークラスが存在しない
     * @throws IllegalAccessException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws Exception 実行例外
     */
    public void setInitData(Ptl040ParamModel paramMdl,
            Connection con, PluginConfig pconfig, RequestModel reqMdl)
    throws SQLException,
    ClassNotFoundException,
    IllegalAccessException,
    InstantiationException,
    Exception {
        log__.debug("START");

        int ptlSid = paramMdl.getPtlPortalSid();

        PtlPortalDao ptlDao = new PtlPortalDao(con);
        PtlPortalLayoutDao ptlLayoutDao = new PtlPortalLayoutDao(con);

        //ポータル名を取得する。
        PtlPortalModel ptlModel = ptlDao.select(ptlSid);
        if (ptlModel != null) {
            paramMdl.setPtl040portalName(ptlModel.getPtlName());
        }


        //レイアウト情報一覧を取得する。
        List<PtlPortalLayoutModel> layoutList = ptlLayoutDao.select(ptlSid);

        //レイアウトの表示区分を設定する。
        for (PtlPortalLayoutModel layoutModel : layoutList) {

            switch (layoutModel.getPlyPosition()) {
                case GSConstPortal.LAYOUT_POSITION_TOP :
                    paramMdl.setPtl040areaTop(layoutModel.getPtsView());
                    break;
                case GSConstPortal.LAYOUT_POSITION_BOTTOM :
                    paramMdl.setPtl040areaBottom(layoutModel.getPtsView());
                    break;
                case GSConstPortal.LAYOUT_POSITION_LEFT :
                    paramMdl.setPtl040areaLeft(layoutModel.getPtsView());
                    break;
                case GSConstPortal.LAYOUT_POSITION_CENTER :
                    paramMdl.setPtl040areaCenter(layoutModel.getPtsView());
                    break;
                case GSConstPortal.LAYOUT_POSITION_RIGHT :
                    paramMdl.setPtl040areaRight(layoutModel.getPtsView());
                    break;
                default:
                    break;
            }
        }


        //表示パーツ情報を設定する。
        __setDspList(paramMdl, con, pconfig, reqMdl);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 表示するパーツ情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     * @throws ClassNotFoundException 指定されたユーザリスナークラスが存在しない
     * @throws IllegalAccessException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws Exception 実行例外
     */
    private void __setDspList(
            Ptl040ParamModel paramMdl, Connection con, PluginConfig pconfig, RequestModel reqMdl)
    throws SQLException,
    ClassNotFoundException,
    IllegalAccessException,
    InstantiationException,
    Exception {
        log__.debug("START");

        GsMessage gsMsg = new GsMessage(reqMdl);
        int ptlSid = paramMdl.getPtlPortalSid();

        PtlPortletDao portletDao = new PtlPortletDao(con);
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);

        List<Ptl040Model> topList = new ArrayList<Ptl040Model>();
        List<Ptl040Model> bottomList = new ArrayList<Ptl040Model>();
        List<Ptl040Model> leftList = new ArrayList<Ptl040Model>();
        List<Ptl040Model> centerList = new ArrayList<Ptl040Model>();
        List<Ptl040Model> rightList = new ArrayList<Ptl040Model>();

        //位置情報を取得する。
        List<PtlPortalPositionModel> allList = ptlPositionDao.select(ptlSid);
        String msgKey = null;
        String dspName = null;
        Ptl040Model partsModel = null;
        int num = 0;

        for (PtlPortalPositionModel positionModel : allList) {

            if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PLUGIN
                    || positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PLUGINPORTLET) {

                //プラグイン有効チェック
                Plugin plagin = pconfig.getPlugin(positionModel.getPctPid());
                if (plagin == null) {
                    continue;
                }
            }


            if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PLUGIN) {
                //プラグイン
                msgKey = "mainscreeninfo" + "."  + positionModel.getPctPid()
                       + "." + positionModel.getMscId();
                dspName = gsMsg.getMessage(msgKey);

            } else if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PORTLET) {
                //ポートレット
                PtlPortletModel pltMdl = portletDao.select(positionModel.getPltSid());
                if (pltMdl != null) {
                    dspName = pltMdl.getPltName();
                }

            } else if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_PLUGINPORTLET) {
                //プラグインポートレット
                msgKey = "mainscreeninfo" + "."  + positionModel.getPctPid()
                         + "." + positionModel.getMscId();
                dspName = gsMsg.getMessage(msgKey);

                String  title = __getPluginPortletTitle(positionModel, con, pconfig);
                if (!StringUtil.isNullZeroStringSpace(title)) {
                    dspName += " [" + title + "]";
                }

            } else if (positionModel.getPtpType() == GSConstPortal.PTP_TYPE_INFORMATION) {
                //インフォメーション
                dspName = gsMsg.getMessage("cmn.information");
            }
            partsModel = new Ptl040Model();
            partsModel.setDspName(dspName);
            partsModel.setPtpItemid(positionModel.getPtpItemid());
            partsModel.setNum(String.valueOf(num));
            partsModel.setPtpView(positionModel.getPtpView());
            partsModel.setPtpType(positionModel.getPtpType());

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

        paramMdl.setPtl040topList(topList);
        paramMdl.setPtl040bottomList(bottomList);
        paramMdl.setPtl040leftList(leftList);
        paramMdl.setPtl040centerList(centerList);
        paramMdl.setPtl040rightList(rightList);
    }

    /**
     * <br>[機  能] プラグイン・ポータルの位置設定の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void savePosition(Connection con, int userSid,
                            Ptl040ParamModel paramMdl)
    throws SQLException {

        log__.debug("-- savePosition START --");

        PtlPortalPositionDao positionDao = new PtlPortalPositionDao(con);

        int ptlSid = paramMdl.getPtlPortalSid();

        //ポータル位置情報一覧を取得する。
        List<PtlPortalPositionModel> beforeList = positionDao.select(ptlSid);

        //ポータル位置情報マップを作成する。
        HashMap<String, PtlPortalPositionModel> beforeMap
                = new HashMap<String, PtlPortalPositionModel>();
        if (beforeList == null || beforeList.size() < 1) {
            return;
        }
        for (PtlPortalPositionModel model : beforeList) {
            beforeMap.put(model.getPtpItemid(), model);
        }

        //ポータル位置情報を削除する。
        positionDao.delete(ptlSid);

        String[] headItems = paramMdl.getPtl040PortalItemHead();
        String[] bottomItems = paramMdl.getPtl040PortalItemBottom();
        String[] leftItems = paramMdl.getPtl040PortalItemLeft();
        String[] centerItems = paramMdl.getPtl040PortalItemCenter();
        String[] rightItems = paramMdl.getPtl040PortalItemRight();

        if ((headItems == null || headItems.length < 1)
                && (bottomItems == null || bottomItems.length < 1)
                && (leftItems == null || leftItems.length < 1)
                && (centerItems == null || centerItems.length < 1)
                && (rightItems == null || rightItems.length < 1)) {
            return;
        }

        PtlPortalPositionModel model = null;

        int sort = 0;
        //上部リストを登録
        if (headItems != null && headItems.length > 0) {
            for (int i = 0; i < headItems.length; i++) {
                if (beforeMap.containsKey(headItems[i])) {
                    sort++;
                    model = beforeMap.get(headItems[i]);
                    model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_TOP);
                    model.setPtpSort(sort);
                    positionDao.insert(model);
                }
            }
        }

        sort = 0;
        //下部リストを登録
        if (bottomItems != null && bottomItems.length > 0) {
            for (int i = 0; i < bottomItems.length; i++) {
                if (beforeMap.containsKey(bottomItems[i])) {
                    sort++;
                    model = beforeMap.get(bottomItems[i]);
                    model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_BOTTOM);
                    model.setPtpSort(sort);
                    positionDao.insert(model);
                }
            }
        }

        sort = 0;
        //左部リストを登録
        if (leftItems != null && leftItems.length > 0) {
            for (int i = 0; i < leftItems.length; i++) {
                if (beforeMap.containsKey(leftItems[i])) {
                    sort++;
                    model = beforeMap.get(leftItems[i]);
                    model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_LEFT);
                    model.setPtpSort(sort);
                    positionDao.insert(model);
                }
            }
        }

        sort = 0;
        //中部リストを登録
        if (centerItems != null && centerItems.length > 0) {
            for (int i = 0; i < centerItems.length; i++) {
                if (beforeMap.containsKey(centerItems[i])) {
                    sort++;
                    model = beforeMap.get(centerItems[i]);
                    model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_CENTER);
                    model.setPtpSort(sort);
                    positionDao.insert(model);
                }
            }
        }

        sort = 0;
        //右部リストを登録
        if (rightItems != null && rightItems.length > 0) {
            for (int i = 0; i < rightItems.length; i++) {
                if (beforeMap.containsKey(rightItems[i])) {
                    sort++;
                    model = beforeMap.get(rightItems[i]);
                    model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_RIGHT);
                    model.setPtpSort(sort);
                    positionDao.insert(model);
                }
            }
        }

        //削除する画面に表示されていないポートレットを削除する。
        __delPositionParam(beforeList, paramMdl, con);

        log__.debug("-- savePosition END --");
    }

    /**
     * <br>[機  能] 表示されていないプラグインポートレットの位置パラメータ情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param beforeList DB登録リスト
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __delPositionParam(
            List<PtlPortalPositionModel> beforeList, Ptl040ParamModel paramMdl, Connection con)
    throws SQLException {

        List<String> headItems = __getList(paramMdl.getPtl040PortalItemHead());
        List<String> bottomItems = __getList(paramMdl.getPtl040PortalItemBottom());
        List<String> leftItems = __getList(paramMdl.getPtl040PortalItemLeft());
        List<String> centerItems = __getList(paramMdl.getPtl040PortalItemCenter());
        List<String> rightItems = __getList(paramMdl.getPtl040PortalItemRight());
        List<String> allItems = new ArrayList<String>();
        allItems.addAll(headItems);
        allItems.addAll(bottomItems);
        allItems.addAll(leftItems);
        allItems.addAll(centerItems);
        allItems.addAll(rightItems);

        PtlPortalPositionParamDao positionParamDao = new PtlPortalPositionParamDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();
        List<String> delItemIdList = new ArrayList<String>();

        for (PtlPortalPositionModel model : beforeList) {
            if (!allItems.contains(model.getPtpItemid())) {
                delItemIdList.add(model.getPtpItemid());
            }
        }

        //プラグインポートレットのパラメータ情報を削除する。
        positionParamDao.delete(ptlSid, delItemIdList);
    }

    /**
     * <br>[機  能] 配列をリストに変換する。
     * <br>[解  説]
     * <br>[備  考]
     * @param array Stringの配列
     * @return list List<String>
     */
    private List<String> __getList(String[] array) {

        List<String> retList = new ArrayList<String>();
        if (array == null || array.length < 1) {
            return retList;
        }
        for (String val :  array) {
            retList.add(val);
        }
        return retList;
    }

    /**
     * <br>[機  能] プラグイン・ポータルの位置設定の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void delPosition(Connection con, Ptl040ParamModel paramMdl)
    throws SQLException {

        log__.debug("-- delPosition START --");

        int ptlSid = paramMdl.getPtlPortalSid();
        String delItemid = paramMdl.getPtl040PortalItemId();
        if (ptlSid < 1 || delItemid == null || delItemid.length() < 1) {
            return;
        }

        //ポータル位置情報を削除する。
        PtlPortalPositionDao posiDao = new PtlPortalPositionDao(con);
        posiDao.delete(ptlSid, delItemid);

        //ポータル位置パラメータ情報を削除する。
        PtlPortalPositionParamDao paramDao = new PtlPortalPositionParamDao(con);
        paramDao.delete(ptlSid, delItemid);

        log__.debug("-- delPosition END --");
    }

    /**
     * <br>[機  能] プラグイン・ポータルの表示区分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void updateView(Connection con, Ptl040ParamModel paramMdl)
    throws SQLException {

        log__.debug("-- updateView START --");

        int ptlSid = paramMdl.getPtlPortalSid();
        String upItemid = paramMdl.getPtl040PortalItemId();
        if (ptlSid < 1 || upItemid == null || upItemid.length() < 1) {
            return;
        }

        //ポータル位置情報を削除する。
        PtlPortalPositionDao posiDao = new PtlPortalPositionDao(con);
        PtlPortalPositionModel model = new PtlPortalPositionModel();
        model.setPtlSid(ptlSid);
        model.setPtpItemid(upItemid);
        model.setPtpView(paramMdl.getPtl040view());
        posiDao.updateView(model);

        log__.debug("-- updateView END --");
    }

    /**
     * <br>[機  能] インフォメーションの追加処理を行う
     * <br>[解  説] インフォメーション位置情報が登録されいない場合に登録します。
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void insertInfomation(Ptl040ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);

        int ptlSid = paramMdl.getPtlPortalSid();

        //インフォメーションの位置情報を取得
        List<PtlPortalPositionModel> infoPosition
            = ptlPositionDao.getPtlPosition(ptlSid, GSConstPortal.PTP_TYPE_INFORMATION);

        if (infoPosition != null && infoPosition.size() > 0) {
            //インフォメーション位置情報あり
            return;
        }

        //レイアウト情報を取得する。
        List<PtlPortalLayoutModel> layoutList =
            layoutDao.getLayoutList(ptlSid, GSConstPortal.LAYOUT_VIEW_ON);

        //ポートレットを追加するポジションを取得する。
        List<Integer> positionSidList = new ArrayList<Integer>();
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_TOP);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_LEFT);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_CENTER);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_RIGHT);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_BOTTOM);

        int plyPosition = GSConstPortal.LAYOUT_POSITION_TOP;
        boolean endFlg = false;
        for (Integer position : positionSidList) {

            for (PtlPortalLayoutModel model : layoutList) {
                if (model.getPlyPosition() == position) {
                    plyPosition = position;
                    endFlg = true;
                    break;
                }
            }
            if (endFlg) {
                break;
            }
        }

        //ポータル位置情報の最大値を取得する。
        int maxSort = ptlPositionDao.getMaxSort(ptlSid, plyPosition);

        ptlBiz.insertInfomation(ptlSid, userSid, plyPosition, maxSort + 1);
    }

    /**
     * <br>[機  能] プラグインポートレットのタイトルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param positionModel PtlPortalPositionModel
     * @param con コネクション
     * @param pconfig PluginConfig
     * @return title タイトル
     * @throws SQLException SQL実行例外
     * @throws ClassNotFoundException 指定されたユーザリスナークラスが存在しない
     * @throws IllegalAccessException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException ユーザリスナー実装クラスのインスタンス生成に失敗
     * @throws Exception 実行例外
     */
    private String __getPluginPortletTitle(
            PtlPortalPositionModel positionModel, Connection con, PluginConfig pconfig)
    throws SQLException,
           ClassNotFoundException,
           IllegalAccessException,
           InstantiationException,
           Exception {

        String title = "";
        Plugin plgin = pconfig.getPlugin(positionModel.getPctPid());

        ArrayList<PortletInfo> pinfoList = plgin.getPortletInfo();
        String bizClass = "";
        for (PortletInfo portletInfo : pinfoList) {

            if (portletInfo.getId().equals(positionModel.getMscId())) {
                bizClass = portletInfo.getBizClass();
            }

        }

        if (StringUtil.isNullZeroStringSpace(bizClass)) {
            return bizClass;
        }

        //パラメータマップを作成する。
        HashMap <String, String> paramMap = new HashMap<String, String>();

        if (positionModel.getPtpParamkbn() == GSConstPortal.PTP_PARAMKBN_ON) {
            PtlPortalPositionParamDao paramDao = new PtlPortalPositionParamDao(con);

            //パラメータ情報を取得する。
            List<PtlPortalPositionParamModel> paramList
                = paramDao.select(positionModel.getPtlSid(), positionModel.getPtpItemid());

            for (PtlPortalPositionParamModel paramModel : paramList) {
                paramMap.put(paramModel.getPpmParamName(), paramModel.getPpmParamValue());
            }

        }

        //プラグインポートレットのbizクラス取得
        @SuppressWarnings("all")
        Class lisClass = Class.forName(bizClass);
        PortletBiz ptlBiz = (PortletBiz) lisClass.newInstance();

        //プラグイポートレットのタイトル取得
        title = ptlBiz.getPortletTitle(con, paramMap);

        return title;
    }
}