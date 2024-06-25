package jp.groupsession.v2.zsk.zsk080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.zsk.dao.ZaiInfoDao;
import jp.groupsession.v2.zsk.dao.ZaiPriConfDao;
import jp.groupsession.v2.zsk.model.ZaiPriConfModel;
import jp.groupsession.v2.zsk.zsk030.Zsk030Model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 在席管理 個人設定 初期表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk080Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk080Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Zsk080Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Zsk080ParamModel
     * @param con コネクション
     * @return Sch010Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Zsk080ParamModel getInitData(
            Zsk080ParamModel paramMdl,
            Connection con)
    throws SQLException {
        log__.debug("START_Zsk080Biz.getInitData");
        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        ZaiPriConfDao priDao = new ZaiPriConfDao(con);
        ZaiPriConfModel priModel = priDao.select(sessionUsrSid);
        if (priModel != null) {
            paramMdl.setDfZifSid(
                    NullDefault.getString(
                            paramMdl.getDfZifSid(),
                            String.valueOf(priModel.getZifSid())));
        }
        //表示座席表リスト
        paramMdl.setZasekiList(__getZasekiList(con));

        return paramMdl;
    }

    /**
     * 在席一覧を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList 在席一覧
     * @throws SQLException SQL実行時エラー
     */
    private ArrayList<Zsk030Model> __getZasekiList(Connection con)
    throws SQLException {
        ArrayList<Zsk030Model> ret = null;
        ZaiInfoDao infoDao = new ZaiInfoDao(con);
        ret = infoDao.getZsk030ModelList();
        return ret;
    }
}
