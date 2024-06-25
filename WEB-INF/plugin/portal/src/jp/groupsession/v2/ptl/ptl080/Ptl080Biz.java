package jp.groupsession.v2.ptl.ptl080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.MainScreenInfo;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.ptl.ptl080.model.Ptl080Model;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル プラグイン選択画面(ポップアップ)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl080Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl080Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Ptl080ParamModel paramMdl, Connection con,
                            PluginConfig pconfig,
                            RequestModel reqMdl)
    throws SQLException {
        log__.debug("START");

        int ptlSid = paramMdl.getPtlPortalSid();
        GsMessage gsMsg = new GsMessage(reqMdl.getLocale());
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        CommonBiz cmnBiz = new CommonBiz();

        //既に追加済みの画面ID一覧を取得する。
        List<PtlPortalPositionModel> posiList
            = ptlPositionDao.getPtlPosition(ptlSid, GSConstPortal.PTP_TYPE_PLUGIN);

        //既に追加済みの画面ID一覧を生成する。
        List<String> mainInfoIdList = new ArrayList<String>();
        if (posiList != null && posiList.size() > 0) {
            for (PtlPortalPositionModel posiModel : posiList) {
                mainInfoIdList.add(posiModel.getMscId());
            }
        }

        //プラグイン一覧を設定する。
        List<Plugin> pluginList = pconfig.getPluginDataList();
        List<Ptl080Model> dspList = new ArrayList<Ptl080Model>();
        Ptl080Model model = null;
        for (Plugin plugin : pluginList) {
            ArrayList<MainScreenInfo> mainscreenList = plugin.getMainScreenInfo();

            if (mainscreenList == null || mainscreenList.size() < 1) {
                continue;
            }

            for (MainScreenInfo mainInfo : mainscreenList) {

                //メイン画面表示(view)が「false」の場合は除くが、
                //プラグインポートレット表示(puluginPortlet)が「true」の場合は許可する
                if ((mainInfo.getView() == null || !mainInfo.getView().equals("true"))
                        && mainInfo.isPluginPortlet() == false) {
                    //メイン非表示プラグイン
                    continue;
                }

                //メイン画面表示(view)が「true」でも、
                //プラグインポートレット表示(puluginPortlet)が「false」の場合は除く
                if (mainInfo.isPluginPortlet() == false) {
                    //ポートレット非表示プラグイン
                    continue;
                }

                if (mainInfoIdList.contains(mainInfo.getId())) {
                    //既に追加済みのプラグイン
                    continue;
                }

                model = new Ptl080Model();

                //プラグインID
                model.setPluginId(plugin.getId());

                //プラグイン名
                model.setPluginName(plugin.getName(reqMdl));

                //アイコンURL（オリジナル）
                model.setPluginIconUrl(plugin.getAdminSettingInfo().getIcon());

                //アイコンURL（クラシック）
                model.setPluginIconClassicUrl(plugin.getAdminSettingInfo().getIconClassic());

                //画面ID
                String mainInfoId = mainInfo.getId();
                model.setMainscreenInfoId(mainInfoId);

                //画面名を設定
                String msgKey = "mainscreeninfo" + "."  + plugin.getId() + "." + mainInfoId;
                String screenName = gsMsg.getMessage(msgKey);
                model.setMainscreenInfoName(screenName);

                dspList.add(model);
            }
        }

        paramMdl.setPtl080dspList(dspList);

        //プラグインポートレットコンボを設定
        List<LabelValueBean> comboList = cmnBiz.getPluginPortletCombo(con, gsMsg,
                reqMdl.getDomain());
        paramMdl.setPtl080PluginPortletList(comboList);

        log__.debug("End");
    }

    /**
     * <br>[機  能] プラグイン追加処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void insertData(Ptl080ParamModel paramMdl,
                            Connection con) throws SQLException {

        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();
        UDate now = new UDate();

        //レイアウト情報を取得する。
        List<PtlPortalLayoutModel> layoutList
                = layoutDao.getLayoutList(ptlSid, GSConstPortal.LAYOUT_VIEW_ON);

        //プラグインを追加するポジションを取得する。
        List<Integer> positionSidList = new ArrayList<Integer>();
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_LEFT);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_CENTER);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_RIGHT);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_TOP);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_BOTTOM);

        int plyPosition = GSConstPortal.LAYOUT_POSITION_LEFT;
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

        //ポータル位置情報を登録する。
        PtlPortalPositionModel posiModel = new PtlPortalPositionModel();
        posiModel.setPtlSid(ptlSid);
        posiModel.setPtpItemid(now.getTimeStamp());
        posiModel.setPlyPosition(plyPosition);
        posiModel.setPtpSort(maxSort + 1);
        posiModel.setPtpType(GSConstPortal.PTP_TYPE_PLUGIN);
        posiModel.setPltSid(-1);
        posiModel.setPctPid(paramMdl.getPlt080pluginId());
        posiModel.setMscId(paramMdl.getPtl080dspId());
        posiModel.setPtpView(GSConstPortal.PTL_OPENKBN_OK);
        ptlPositionDao.insert(posiModel);

        //プラグイン選択済フラグ
        paramMdl.setPtl080selectFlg(true);
    }

}