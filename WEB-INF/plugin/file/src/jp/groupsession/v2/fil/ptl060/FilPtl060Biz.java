package jp.groupsession.v2.fil.ptl060;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>ポータル ファイル一覧管理 フォルダ情報画面Biz
 * @author JTS
 */
public class FilPtl060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl060Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con コネクション
     * @param reqMdl リクエストモデル
     */
    public FilPtl060Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /** ポートレットパラメータ ディレクトリSID */
    public static final String FILE_PORTLET_PARAM1 = "dspDirSid";


    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl FilPtl060ParamModel
     * @param con コネクション
     * @return errorFlg ディレクトリが存在しない場合false
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     *
     */
    public boolean setInitData(FilPtl060ParamModel paramMdl, Connection con)
    throws SQLException, IOToolsException, IOException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        FilCommonBiz filcmnBiz = new FilCommonBiz(reqMdl__, con);

        int cabSid = NullDefault.getInt(paramMdl.getFilptl050selectFcbSid(), -1);
        int dspDirSid = NullDefault.getInt(paramMdl.getFilptl050selectDirSid(), -1);

        //キャビネット情報を取得
        FileCabinetDao fcbDao = new FileCabinetDao(con);
        FileCabinetModel fcbMdl = fcbDao.select(cabSid);


        //表示するカレントディレクトリSIDを取得
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirMdl = null;
        //指定したディレクトリの最新の情報を取得する
        dirMdl = dirDao.getNewDirectory(dspDirSid);
        if (dirMdl == null || dirMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            //ディレクトリが情報が存在しない 又は論理削除されている場合
            return false;
        }

        if (dirMdl != null) {
            log__.debug("表示するカレントディレクトリSID=>" + dirMdl.getFdrSid());
            //キャビネット名称
            paramMdl.setFilptl060CabinetName(filcmnBiz.getCabinetName(cabSid));
            //タイトル用パス情報
            paramMdl.setFilptl060DirectoryPathList(
                    filcmnBiz.getRootToCurrentDirctoryList(fcbMdl, dirMdl));

            //ソート：名前順
            int sort = GSConstFile.SORT_NAME;
            //オーダー：昇順
            int order = GSConst.ORDER_KEY_ASC;

            paramMdl.setFilptl060DirectoryList(dirDao.getNewLowDirectory(
                  dirMdl.getFdrSid(), sessionUsrSid, sort, order, reqMdl__));
        }

        CommonBiz cmnBiz = new CommonBiz();

        //プラグインポートレットコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> comboList = cmnBiz.getPluginPortletCombo(con__, gsMsg,
                reqMdl__.getDomain());
        paramMdl.setFilptl050PluginPortletList(comboList);

        return true;
    }

    /**
     * <br>[機  能] プラグイン追加処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl FilPtl060ParamModel
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行例外
     */
    public void insertData(FilPtl060ParamModel paramMdl,
                                 PluginConfig pconfig) throws SQLException {

        PtlPortalPositionParamDao positionParamDao = new PtlPortalPositionParamDao(con__);
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con__);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con__);
        int ptlSid = paramMdl.getPtlPortalSid();
        int directorySid = NullDefault.getInt(paramMdl.getFilptl050selectDirSid(), -1);
        UDate now = new UDate();
        String itemId = now.getTimeStamp();
        CommonBiz cmnBiz = new CommonBiz();

        //プラグイン選択済フラグ
        paramMdl.setFilptl060selectFlg(true);

        //ptlSid=1以下：mainポータル、directorySid=1以下：存在しない
        if (ptlSid < 1 || directorySid < 1) {
            return;
        }

        //指定したディレクトリの最新バージョンを取得
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileDirectoryModel dirMdl = dirDao.getNewDirectory(directorySid);
        if (dirMdl == null || dirMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            return; //ディレクトリ情報が存在しない、または論理削除されている場合
        }

        // キャビネット情報取得
        FileCabinetDao   fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(dirMdl.getFcbSid());
        if (fcbMdl == null || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE
        || fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            return; // キャビネットなし(または論理削除) or 個人キャビネットの場合
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
        String screenId = "filptl050";

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
        positionParamModel.setPpmParamValue(String.valueOf(directorySid));
        positionParamDao.insert(positionParamModel);

    }

}
