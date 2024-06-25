package jp.groupsession.v2.fil.ptl010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.PortletBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.ptl020.FilPtl020Biz;

/**
 * ポータル ファイル管理メインツリー画面で使用するビジネスロジッククラス
 * @author JTS
 */
public class FilPtl010Biz implements PortletBiz {

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * デフォルトコンストラクタ
     */
    public FilPtl010Biz() {
    }

    /**
     * デフォルトコンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public FilPtl010Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl FilPtl010ParamModel
     * @param umodel BaseUserModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(FilPtl010ParamModel paramMdl,
            BaseUserModel umodel) throws SQLException {

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        //表示キャビネットSIDを取得
        int cabinetSid = paramMdl.getDspFcbSid();
        if (cabinetSid < 1) {
            paramMdl.setDspFcbSid(0);
            return;
        }

        //キャビネットへのアクセス権限があるか判定する。
        boolean errorFlg = filBiz.isAccessAuthUser(cabinetSid);
        if (!errorFlg) {
            //アクセス権限なし
            paramMdl.setDspFcbSid(0);
            return;
        }

        FileCabinetDao   fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(cabinetSid);
        if (fcbMdl == null || fcbMdl.getFcbJkbn() == GSConst.JTKBN_DELETE) {
            paramMdl.setDspFcbSid(0);
            return;
        }

        //ユーザSID
        int sessionUsrSid = umodel.getUsrsid();

        if (fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            // 個人キャビネットは常に非表示
            paramMdl.setDspFcbSid(0);
            return;

            /*
            FileDirectoryDao dirDao = new FileDirectoryDao(con__);
            ArrayList<FileDirectoryModel> dirList =
                    dirDao.getAuthDirectoryList(fcbMdl, sessionUsrSid);

            // キャビネット内の閲覧可能なディレクトリ一覧を取得
            if (dirList != null && dirList.size() > 0) {
                // 最上位階層のディレクトリ名をキャビネット名へ変更
                for (FileDirectoryModel dirMdl : dirList) {
                    if (dirMdl.getFdrLevel() == GSConstFile.DIRECTORY_LEVEL_0) {
                        dirMdl.setFdrName(cabinetName);
                    }
                }
                paramMdl.setTreeFormLv0(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_0, dirList, null));

                paramMdl.setTreeFormLv1(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_1, dirList, null));

                paramMdl.setTreeFormLv2(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_2, dirList, null));

                paramMdl.setTreeFormLv3(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_3, dirList, null));

                paramMdl.setTreeFormLv4(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_4, dirList, null));

                paramMdl.setTreeFormLv5(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_5, dirList, null));

                paramMdl.setTreeFormLv6(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_6, dirList, null));

                paramMdl.setTreeFormLv7(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_7, dirList, null));

                paramMdl.setTreeFormLv8(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_8, dirList, null));

                paramMdl.setTreeFormLv9(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_9, dirList, null));

                paramMdl.setTreeFormLv10(dirDao.getKeyStringArray(
                        GSConstFile.DIRECTORY_LEVEL_10, dirList, null));
            }
            */
        } else {
            //Tree情報取得
            FilTreeBiz treeBiz = new FilTreeBiz(con__);

            //特権ユーザ判定
            boolean superUser = filBiz.isEditCabinetUser(cabinetSid);

            paramMdl.setTreeFormLv0(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_0,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv1(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_1,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv2(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_2,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv3(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_3,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv4(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_4,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv5(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_5,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv6(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_6,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv7(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_7,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv8(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_8,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv9(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_9,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
            paramMdl.setTreeFormLv10(
                    treeBiz.getFileTree(cabinetSid,
                                        GSConstFile.DIRECTORY_LEVEL_10,
                                        sessionUsrSid,
                                        -1,
                                        superUser));
        }

        //キャビネット名
        String cabinetName = filBiz.getCabinetName(cabinetSid);
        paramMdl.setFilPtl010FcbName(cabinetName);

        //TREE OPEN設定
        paramMdl.setSelectDir("-1");
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

        String title = "";

        if (paramMap == null) {
            return title;
        }

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con);

        //マップからパラメータを取得
        String paramFcbSid = paramMap.get(FilPtl020Biz.FILE_PORTLET_PARAM1);

        //キャビネット名
        title = filBiz.getCabinetName(NullDefault.getInt(paramFcbSid, 0));

        return title;
    }

}
