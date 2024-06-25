package jp.groupsession.v2.zsk.zsk080kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.dao.ZaiInfoDao;
import jp.groupsession.v2.zsk.dao.ZaiPriConfDao;
import jp.groupsession.v2.zsk.model.ZaiInfoModel;
import jp.groupsession.v2.zsk.model.ZaiPriConfModel;

/**
 * <br>[機  能] 在席管理 個人設定 初期表示設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk080knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk080knBiz.class);

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Zsk080knParamModel
     * @param con コネクション
     * @return Sch010Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Zsk080knParamModel getInitData(
            Zsk080knParamModel paramMdl,
            Connection con)
    throws SQLException {
        log__.debug("START_Zsk080knBiz.getInitData");

        //表示座席表名称
        paramMdl.setDfZifName(__getZasekiName(paramMdl, con));

        return paramMdl;
    }

    /**
     * 在席表名称を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk080knParamModel
     * @param con コネクション
     * @return String
     * @throws SQLException SQL実行時エラー
     */
    private String __getZasekiName(Zsk080knParamModel paramMdl, Connection con)
    throws SQLException {
        String ret = "";
        ZaiInfoDao infoDao = new ZaiInfoDao(con);
        int zifSid = NullDefault.getInt(paramMdl.getDfZifSid(), 0);
        ZaiInfoModel infoMdl = infoDao.select(zifSid);
        if (infoMdl != null) {
            ret = infoMdl.getZifName();
        }
        return ret;
    }
    /**
     * 指定ユーザの在席管理個人設定をフォーム値で更新・登録する。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk080knParamModel
     * @param userSid 更新対象ユーザSID
     * @param con コネクション
     * @throws SQLException 在席管理個人設定更新時エラー
     */
    public void zaisekiPriConfUpdate(Zsk080knParamModel paramMdl, int userSid, Connection con)
    throws SQLException {
        UDate now = new UDate();
        int zifSid = NullDefault.getInt(paramMdl.getDfZifSid(), 0);
        ZaiPriConfDao priDao = new ZaiPriConfDao(con);
        ZaiPriConfModel bean = new ZaiPriConfModel();
        bean.setUsrSid(userSid);
        bean.setZifSid(zifSid);
        bean.setZpcEid(userSid);
        bean.setZpcEdate(now);
        int upCnt = priDao.updateZifSid(bean);
        if (upCnt < 1) {
            bean.setZpcReload(GSConstZaiseki.AUTO_RELOAD_10MIN);
            bean.setZpcAid(userSid);
            bean.setZpcAdate(now);
            bean.setZpcSortKey1(GSConstZaiseki.SORT_KEY_NAME);
            bean.setZpcSortOrder1(GSConst.ORDER_KEY_ASC);
            bean.setZpcSortKey2(GSConstZaiseki.SORT_KEY_NAME);
            bean.setZpcSortOrder2(GSConst.ORDER_KEY_ASC);
            priDao.insert(bean);
        }
    }
    /**
     * <br>[機  能] ログ用メッセージ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param zifSid 座席表SID
     * @param con コネクション
     * @return ログメッセージ
     * @throws IOToolsException ファイルアクセス時例外
     * @throws SQLException SQLエラー
     */
    public String getLogMessage(int zifSid, Connection con)
            throws IOToolsException, SQLException {

        ZaiInfoDao dao = new ZaiInfoDao(con);
        ZaiInfoModel mdl = dao.select(zifSid);
        return mdl.getZifName();

    }
}
