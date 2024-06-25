package jp.groupsession.v2.fil.ptl020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <p>ポータル キャビネットツリー管理画面BIZ
 * @author JTS
 */
public class FilPtl020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl020Biz.class);

    /** ポートレットパラメータ キャビネットSID */
    public static final String FILE_PORTLET_PARAM1 = "dspFcbSid";

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public FilPtl020Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl FilPtl020ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(FilPtl020ParamModel paramMdl)
    throws SQLException {
        log__.debug("START");

        CommonBiz cmnBiz = new CommonBiz();

        //プラグインポートレットコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> comboList = cmnBiz.getPluginPortletCombo(con__, gsMsg,
                reqMdl__.getDomain());
        paramMdl.setFilPtl020PluginPortletList(comboList);

        //キャビネット一覧を設定する。
        setCabinetList(paramMdl);
        log__.debug("End");
    }

    /**
     * <br>[機  能] プラグイン追加処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl FilPtl020ParamModel
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行例外
     */
    public void insertData(FilPtl020ParamModel paramMdl,
                                 PluginConfig pconfig) throws SQLException {

        PtlPortalPositionParamDao positionParamDao = new PtlPortalPositionParamDao(con__);
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con__);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con__);
        int ptlSid = paramMdl.getPtlPortalSid();
        int cabinetSid = paramMdl.getFilptl020selectFcbSid();
        UDate now = new UDate();
        String itemId = now.getTimeStamp();
        CommonBiz cmnBiz = new CommonBiz();

        //プラグイン選択済フラグ
        paramMdl.setFilptl020selectFlg(true);

        if (ptlSid < 1 || cabinetSid < 1) {
            return;
        }

        // キャビネット情報取得
        FileCabinetDao   fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(cabinetSid);
        if (fcbMdl == null
                || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE
                || fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            return; // キャビネットなし or 個人キャビネット → 保存しない
        }

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

        //選択画面ID
        String screenId = GSConstFile.SCREENID_FILPTL020;

        //ポートレット画面IDを取得する。
        String dspScreenId =
            cmnBiz.getPluginPortletScreenId(pconfig, GSConstFile.PLUGIN_ID_FILE, screenId);

        //ポータル位置情報を登録する。
        PtlPortalPositionModel posiModel = new PtlPortalPositionModel();
        posiModel.setPtlSid(ptlSid);
        posiModel.setPtpItemid(itemId);
        posiModel.setPlyPosition(plyPosition);
        posiModel.setPtpSort(maxSort + 1);
        posiModel.setPtpType(GSConstPortal.PTP_TYPE_PLUGINPORTLET);
        posiModel.setPltSid(-1);
        posiModel.setPctPid(GSConstFile.PLUGIN_ID_FILE);
        posiModel.setMscId(dspScreenId);
        posiModel.setPtpView(GSConstPortal.PTL_OPENKBN_OK);
        posiModel.setPtpParamkbn(GSConstPortal.PTP_PARAMKBN_ON);
        ptlPositionDao.insert(posiModel);

        //ポータル_位置設定_パラメータを登録する。
        PtlPortalPositionParamModel positionParamModel = new PtlPortalPositionParamModel();
        positionParamModel.setPtlSid(ptlSid);
        positionParamModel.setPtpItemid(itemId);
        positionParamModel.setPpmParamNo(1);
        positionParamModel.setPpmParamName(FILE_PORTLET_PARAM1);
        positionParamModel.setPpmParamValue(String.valueOf(cabinetSid));
        positionParamDao.insert(positionParamModel);

    }

    /**
     * <br>[機  能] 表示するキャビネット一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl FilPtl020ParamModel
     * @throws SQLException SQL実行例外
     */
    private void setCabinetList(FilPtl020ParamModel paramMdl) throws SQLException {

        FileCabinetDao cabinetDao = new FileCabinetDao(con__);
        PtlPortalPositionParamDao paramDao = new PtlPortalPositionParamDao(con__);
        int ptlSid = paramMdl.getPtlPortalSid();

        //既に設定しているキャビネット情報を取得する。
        List<PtlPortalPositionParamModel> paramList
             = paramDao.getParamList(ptlSid, FILE_PORTLET_PARAM1);

        //キャビネット一覧を取得する。
        List<FileCabinetModel> cabinetList = cabinetDao.select();

        List<Integer> delCabinetSidList = new ArrayList<Integer>();
        for (PtlPortalPositionParamModel model : paramList) {
            delCabinetSidList.add(NullDefault.getInt(model.getPpmParamValue(), 0));
        }

        List<FileCabinetModel> dspList = new ArrayList<FileCabinetModel>();
        for (FileCabinetModel cabinetMdl : cabinetList) {
            if (!delCabinetSidList.contains(cabinetMdl.getFcbSid())
              && cabinetMdl.getFcbPersonalFlg() != GSConstFile.CABINET_KBN_PRIVATE
              && cabinetMdl.getFcbJkbn() != GSConstFile.JTKBN_DELETE) {
                // 既に選択済みキャビネット or 個人キャビネット or 論理削除済み→ 一覧から除外
                dspList.add(cabinetMdl);
            }
        }

        paramMdl.setFilptl020dspList(dspList);
    }
}