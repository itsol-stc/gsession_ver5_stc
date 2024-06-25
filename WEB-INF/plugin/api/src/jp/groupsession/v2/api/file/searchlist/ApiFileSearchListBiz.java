package jp.groupsession.v2.api.file.searchlist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FilChildTreeModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <br>[機  能] キーワード検索ディレクトリ一覧を取得するWEBAPIビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileSearchListBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileSearchListBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiFileSearchListBiz(Connection con) {
        con__ = con;
    }




    /**
     * 検索結果一覧を取得します
     * @param keyword キーワード
     * @param fcbSid キャビネットSID
     * @param fdrSid ディレクトリSID
     * @param fcbKbn キャビネット区分
     * @param usModel usModel
     * @return 検索結果一覧
     * @throws SQLException SQL実行時例外
     */
    public List<ApiFileSearchModel> getSearchResultList(String keyword,
               String fcbSid, String fdrSid, String fcbKbn, BaseUserModel usModel)
    throws SQLException {
        log__.debug("WEBAPI_FILE_SEARCH START");
        List<ApiFileSearchModel> ret = new ArrayList<ApiFileSearchModel>();
        ApiFileSearchListDao dao = new ApiFileSearchListDao(con__);
        ArrayList<Integer> fdrSidList = new ArrayList<Integer>();
        if (!StringUtil.isNullZeroString(fdrSid)) {
            FileDirectoryDao dirDao = new FileDirectoryDao(con__);
            FileDirectoryModel dirModel = dirDao.select(Integer.parseInt(fdrSid), -1);
            //Actionのvalidateチェックにて存在確認できているためここでは行わない
            FilTreeBiz ftBiz = new FilTreeBiz(con__);
            FilChildTreeModel fctMdl = ftBiz.getChildOfTarget(dirModel);
            for (int idx = 0; idx < fctMdl.getListOfDir().size(); idx++) {
                if (Integer.parseInt(fdrSid) != fctMdl.getListOfDir().get(idx).getFdrSid()) {
                    fdrSidList.add(fctMdl.getListOfDir().get(idx).getFdrSid());
                }
            }
            for (int idx = 0; idx < fctMdl.getListOfFile().size(); idx++) {
                fdrSidList.add(fctMdl.getListOfFile().get(idx).getFdrSid());
            }
        }
        // 検索結果一覧情報を取得
        List<ApiFileSearchModel> resultList
                    = dao.getSearchData(keyword, fcbSid, fdrSidList, fcbKbn, usModel);
        if (resultList == null || resultList.isEmpty()) {
            return ret;
        }
        for (ApiFileSearchModel bean : resultList) {
            ApiFileSearchModel addMdl = new ApiFileSearchModel();
            addMdl.setFcbSid(bean.getFcbSid());
            addMdl.setFdrSid(bean.getFdrSid());
            addMdl.setFdrName(bean.getFdrName());
            addMdl.setFdrParentSid(bean.getFdrParentSid());
            addMdl.setFdrKbn(bean.getFdrKbn());
            addMdl.setBinSid(bean.getBinSid());
            addMdl.setFileSize(Long.toString(bean.getFflFileSizeLg()));
            // 日時yyyy/MM/dd hh:mm:ss形式に変換
            String strSdate = null;
            if (bean.getFdrEdate() != null) {
                strSdate =
                    UDateUtil.getSlashYYMD(bean.getFdrEdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(bean.getFdrEdate());
            }
            addMdl.setEditDate(strSdate);
            addMdl.setFflLockKbn(bean.getFflLockKbn());
            // ロック確認
            if (bean.getFflLockKbn() == GSConstFile.LOCK_KBN_OFF) {
                addMdl.setFflLockUser(-1);
            } else {
                addMdl.setFflLockUser(bean.getFflLockUser());
            }
            addMdl.setWriteKbn(bean.getWriteKbn());
            // 編集区分確認
            if (bean.getFdrEgid() > 0) {
               // グループ
                addMdl.setEditUsrKbn(GSConstFile.USER_KBN_GROUP);
                addMdl.setEditUsrName(bean.getGrpName());
                addMdl.setEditUsrUkoFlg(GSConst.YUKOMUKO_YUKO);
                addMdl.setEditUsrSid(bean.getFdrEgid());
            } else {
               // ユーザ
                addMdl.setEditUsrKbn(GSConstFile.USER_KBN_USER);
                addMdl.setEditUsrName(bean.getUsrSei() + " " + bean.getUsrMei());
                addMdl.setEditUsrUkoFlg(bean.getUsrUkoFlg());
                addMdl.setEditUsrSid(bean.getFdrEuid());
            }
            
            addMdl.setFdrTradeDate(bean.getFdrTradeDate());
            addMdl.setFdrTradeTarget(bean.getFdrTradeTarget());
            addMdl.setFdrTradeMoney(bean.getFdrTradeMoney());
            addMdl.setFdrErrlMoneyType(bean.getFdrErrlMoneyType());
            addMdl.setFdrTradeMoneyKbn(bean.getFdrTradeMoneyKbn());
            addMdl.setErrlKbn(bean.getErrlKbn());
            ret.add(addMdl);
        }
        log__.debug("WEBAPI_FILE_SEARCH END");
        return ret;
    }


}