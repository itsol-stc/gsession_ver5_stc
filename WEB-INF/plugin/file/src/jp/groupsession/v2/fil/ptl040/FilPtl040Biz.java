package jp.groupsession.v2.fil.ptl040;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.PortletBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.ptl060.FilPtl060Biz;

/**
 * <p>ポータル ファイル一覧画面Biz
 * @author JTS
 */
public class FilPtl040Biz implements PortletBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl040Biz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * デフォルトコンストラクタ
     */
    public FilPtl040Biz() {
    }

    /**
     * <p>コンストラクタ
     * @param reqMdl RequestModel
     */
    public FilPtl040Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl filptl040ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     */
    public void setInitData(FilPtl040ParamModel paramMdl, Connection con)
    throws SQLException, IOToolsException, IOException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con);

        //ディレクトリSid
        int dspDirSid = paramMdl.getDspDirSid();

        //表示するカレントディレクトリSIDを取得
        FileDirectoryDao dirDao = new FileDirectoryDao(con);

        //指定したディレクトリの最新バージョンを取得
        FileDirectoryModel dirMdl = null;
        dirMdl = dirDao.getNewDirectory(dspDirSid);
        if (dirMdl == null) {
            //ディレクトリが情報が存在しない場合
            paramMdl.setDspDirSid(-1);
            return;
        }
        
        if (dirMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            //論理削除済みの場合
            paramMdl.setDspDirSid(-1);
            return;
        }

        //キャビネットSid
        int cabSid = dirMdl.getFcbSid();
        FileCabinetDao cabDao = new FileCabinetDao(con);
        FileCabinetModel cabMdl = cabDao.select(cabSid);

        if (cabMdl == null || cabMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE
                || cabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            // キャビネット情報なし or 個人キャビネット or 論理削除済み→ アクセス権限なし
            paramMdl.setDspDirSid(-1);
            return;
        }

        paramMdl.setDspFcbSid(cabSid);

        //特権ユーザ判定(管理者ユーザ含む)
        boolean superUser = filBiz.isEditCabinetUser(cabMdl, -1);

        //管理者設定ロック区分を設定する。(ロック有効/無効)
        paramMdl.setAdmLockKbn(filBiz.getLockKbnAdmin());

        //キャビネットアクセス権限チェック
        boolean fcbErrorFlg = filBiz.isAccessAuthUser(cabMdl, -1);
        if (!fcbErrorFlg) {
            //アクセス権限なし
            paramMdl.setDspDirSid(-1);
            return;
        }

        //ディレクトリアクセス権限チェック
        boolean fdirErrorFlg = filBiz.isDirAccessAuthUser(cabSid, dspDirSid,
                                                          -1);
        if (!fdirErrorFlg) {
            //アクセス権限なし
            paramMdl.setDspDirSid(-1);
            return;
        }

        paramMdl.setFilPtlDirName(dirMdl.getFdrName());

        //選択されたディレクトリがファイルだった場合、親ディレクトリ取得
        if (dirMdl.getFdrKbn() == GSConstFile.DIRECTORY_FILE) {
            dspDirSid = dirMdl.getFdrParentSid();
            dirMdl = null;
            dirMdl = dirDao.getNewDirectory(dspDirSid);
        }

        if (dirMdl != null) {
            log__.debug("表示するカレントディレクトリSID=>" + dirMdl.getFdrSid());
            //キャビネット名称
            paramMdl.setFil040CabinetName(filBiz.getCabinetName(cabSid));

            //ソート：名前順
            int sort = GSConstFile.SORT_NAME;
            //オーダー：昇順
            int order = GSConst.ORDER_KEY_ASC;

            //カレント情報取得
            if (superUser) {
                //特権ユーザ、管理者ユーザの場合全てのフォルダ・ファイルを取得
                paramMdl.setDirectoryList(
                dirDao.getNewLowDirectory(
                                dirMdl.getFdrSid(), sessionUsrSid, sort, order, reqMdl__));
            } else {
                //特権ユーザ、管理者ユーザでない場合閲覧可能なフォルダ・ファイルを取得
                paramMdl.setDirectoryList(
                        dirDao.getNewLowDirectoryAccessLimit(
                                dirMdl.getFdrSid(), sessionUsrSid, sort, order, reqMdl__));
            }

        }

    }

    /**
     * <br>プラグインポートレットタイトルを取得する。
     * @param con コネクション
     * @param paramMap パラメータマップ
     * @return title ポートレットプラグインタイトル
     * @throws Exception 実行時例外
     */
    public String getPortletTitle(Connection con, HashMap<String, String> paramMap)
    throws Exception {

        if (paramMap == null) {
            return "";
        }

        //マップからパラメータを取得
        String paramFdirSid = paramMap.get(FilPtl060Biz.FILE_PORTLET_PARAM1);
        
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirModel = dirDao.getNewDirectory(NullDefault.getInt(paramFdirSid, 0));

        if (dirModel == null || dirModel.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
            return "";
        } else {
            FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con);
            if (!filBiz.checkCabinetDeleteKbn(dirModel.getFcbSid())) {
                return "";
            }
        }

        return dirModel.getFdrName();
    }

}
