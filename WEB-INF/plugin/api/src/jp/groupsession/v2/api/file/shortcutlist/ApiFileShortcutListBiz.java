package jp.groupsession.v2.api.file.shortcutlist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;

/**
 * <br>[機  能] ショートカット一覧を取得するWEBAPIビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileShortcutListBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiFileShortcutListBiz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public ApiFileShortcutListBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * ショートカット一覧を取得します
     * @return ショートカット一覧
     * @param umodel ユーザ情報
     * @param errlKbn 電帳法対応区分 0:電帳法に対応していない, 1:電帳法に対応している
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<ApiFileShortcutListModel> getShortcutInfoList(
            BaseUserModel umodel, int errlKbn) throws SQLException {

        log__.debug("WEBAPI_FILE GET SHORTCUT_LIST START");
        ArrayList<ApiFileShortcutListModel> ret = new ArrayList<ApiFileShortcutListModel>();
        ApiFileShortcutListDao dao = new ApiFileShortcutListDao(con__);
        //ショートカット情報を取得
        ArrayList<ApiFileShortcutListModel> shortCutList
                    = new ArrayList<ApiFileShortcutListModel>();
        shortCutList.addAll(
                dao.getApiShortCutList(umodel, GSConstFile.CABINET_KBN_PUBLIC, errlKbn));
        for (ApiFileShortcutListModel bean : shortCutList) {
            ApiFileShortcutListModel addMdl = new ApiFileShortcutListModel();
            int fdrSid = bean.getFdrSid();
            addMdl.setFdrSid(fdrSid);
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
            addMdl.setCabinetKbn(bean.getCabinetKbn());
            FilCommonBiz fileBiz = new FilCommonBiz(reqMdl__, con__);
            addMdl.setFdrPath(fileBiz.getDirctoryPath(bean.getFdrSid()));
            ret.add(addMdl);
        }
        log__.debug("WEBAPI_FILE GET SHORTCUT_LIST END");
        return ret;
    }


}