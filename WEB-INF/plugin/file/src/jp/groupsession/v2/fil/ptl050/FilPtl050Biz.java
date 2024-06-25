package jp.groupsession.v2.fil.ptl050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>ポータル ファイル一覧管理 キャビネット選択画面Biz
 * @author JTS
 */
public class FilPtl050Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl050Biz.class);

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
    public FilPtl050Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl FilPtl050ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(FilPtl050ParamModel paramMdl)
    throws SQLException {
        log__.debug("START");

        CommonBiz cmnBiz = new CommonBiz();

        //プラグインポートレットコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> comboList = cmnBiz.getPluginPortletCombo(con__, gsMsg,
                reqMdl__.getDomain());
        paramMdl.setFilptl050PluginPortletList(comboList);

        //キャビネット一覧を設定する。
        setCabinetList(paramMdl);

        log__.debug("End");
    }


    /**
     * <br>[機  能] 表示するキャビネット一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl FilPtl050ParamModel
     * @throws SQLException SQL実行例外
     */
    private void setCabinetList(FilPtl050ParamModel paramMdl) throws SQLException {

        FileCabinetDao cabinetDao = new FileCabinetDao(con__);
        FileDirectoryDao directoryDao = new FileDirectoryDao(con__);

        //キャビネット一覧を取得する。
        List<FileCabinetInfoModel> cabinetList = cabinetDao.selectPtlCabinet();

        //キャビネット情報一覧
        List<FileCabinetInfoModel> cabList = new ArrayList<FileCabinetInfoModel>();

        //取得したキャビネット一覧をセット
        for (FileCabinetInfoModel cabinetInfoMdl: cabinetList) {
            // 個人キャビネットと論理削除済みキャビネットは一覧から除外
            if (cabinetInfoMdl.getFcbPersonalFlg() != GSConstFile.CABINET_KBN_PRIVATE
                    && cabinetInfoMdl.getFcbJkbn() != GSConstFile.JTKBN_DELETE) {
                cabList.add(cabinetInfoMdl);

                //キャビネットSIDをもとにディレクトリ情報を取得
                FileDirectoryModel directryMdl =
                        directoryDao.getRootDirectory(cabinetInfoMdl.getFcbSid());
                cabinetInfoMdl.setRootDirSid(directryMdl.getFdrSid());
            }

        }
        //キャビネット情報一覧
        paramMdl.setFilptlCabinetInfoList(cabList);

    }

}