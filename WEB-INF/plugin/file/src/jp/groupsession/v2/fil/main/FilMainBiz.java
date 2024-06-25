package jp.groupsession.v2.fil.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCallDataDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileUconfDao;
import jp.groupsession.v2.fil.fil010.Fil010Biz;
import jp.groupsession.v2.fil.fil010.FileLinkSimpleModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileUconfModel;

/**
 * ファイル管理メイン画面で使用するビジネスロジッククラス
 * @author JTS
 */
public class FilMainBiz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * デフォルトコンストラクタ
     * @param reqMdl RequestModel
     */
    public FilMainBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramModel FilMainParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(FilMainParamModel paramModel, Connection con) throws SQLException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid();

        FileUconfDao uconfDao = new FileUconfDao(con);
        FileUconfModel model = uconfDao.select(sessionUsrSid);


        if (model == null || model.getFucMainOkini() == GSConstFile.MAIN_OKINI_DSP_ON) {
            //ショートカット一覧を取得
            Fil010Biz filBiz = new Fil010Biz(reqMdl__);
            CommonBiz  commonBiz = new CommonBiz();
            boolean adminUser = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);
            paramModel.setShortcutList(filBiz.getShortcutInfoList(
                    sessionUsrSid, -1, con, adminUser));
        } else {
            paramModel.setShortcutList(null);
        }

        if (model == null || model.getFucMainCall() > 0) {
            //更新通知情報を取得
            paramModel.setCallList(getCallInfoList(sessionUsrSid, con));
        } else {
            paramModel.setCallList(null);
        }

    }

    /**
     * 更新通知一覧を取得します
     * @param userSid ユーザSID
     * @param con コネクション
     * @return 更新通知一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<FileLinkSimpleModel> getCallInfoList(int userSid, Connection con)
    throws SQLException {
        ArrayList<FileLinkSimpleModel> ret = new ArrayList<FileLinkSimpleModel>();
        FileCallDataDao dao = new FileCallDataDao(con);

        //個人設定を取得
        FileUconfDao uconfDao = new FileUconfDao(con);
        FileUconfModel uconfModel = uconfDao.select(userSid);
        int limit = 0;
        if (uconfModel == null) {
            limit = GSConstFile.CALL_DSP_CNT;
        } else {
            limit = uconfModel.getFucMainCall();
        }
        int offset = 1;

        //更新通知情報を取得
        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(con, reqMdl__.getSmodel(),
                                                    GSConstFile.PLUGIN_ID_FILE);
        ArrayList<FileDirectoryModel> updateList
            = dao.getUpdateCallData(userSid, adminUser, offset, limit);
        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con);
        FileLinkSimpleModel dspModel = null;
        String path = "";
        List<Integer> fileSidList = updateList.stream()
                .filter(mdl -> mdl.getFdrKbn() == GSConstFile.DIRECTORY_KBN_FILE)
                .filter(mdl -> mdl.getFdrName().toLowerCase()
                                    .matches(".*\\.png$|.*\\.jpg$|.*\\.jpeg$|.*\\.pdf$"))
                .map(mdl -> mdl.getFdrSid())
                .collect(Collectors.toList());
        
        FileFileBinDao binDao = new FileFileBinDao(con);
        Map<Integer, Long> binSidMap = binDao.getBinSidsMap(fileSidList);
        
        for (FileDirectoryModel bean : updateList) {
            if (bean.getFdrJtkbn() == GSConst.JTKBN_DELETE) {
                continue;
            }
            int fdrSid = bean.getFdrSid();
            path = filCmnBiz.getDirctoryPath(fdrSid);
            dspModel = new FileLinkSimpleModel();
            dspModel.setDirectoryFullPathName(path);
            dspModel.setDirectoryKbn(bean.getFdrKbn());
            dspModel.setDirectoryName(bean.getFdrName());
            dspModel.setCabinetSid(bean.getFcbSid());
            dspModel.setDirectorySid(bean.getFdrSid());
            dspModel.setDirectoryUpdateStr(
                    UDateUtil.getSlashYYMD(bean.getFdrEdate())
                    + " "
                    + UDateUtil.getSeparateHMS(bean.getFdrEdate()));
            dspModel.setFcbMark(bean.getFcbMark());
            
            if (binSidMap.get(fdrSid) != null) {
                dspModel.setBinSid(binSidMap.get(fdrSid));
            }
            
            ret.add(dspModel);
        }
        return ret;
    }
}
