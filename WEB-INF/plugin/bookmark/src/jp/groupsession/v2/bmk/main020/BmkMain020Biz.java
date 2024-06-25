package jp.groupsession.v2.bmk.main020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk010.dao.Bmk010Dao;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010InfoModel;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.model.BmkUconfModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 新着ブックマーク（メイン）画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BmkMain020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkMain020Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public BmkMain020Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl BmkMain020ParamModel
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @throws Exception 実行例外
     */
    public void setInitData(BmkMain020ParamModel paramMdl, Connection con, BaseUserModel userMdl)
    throws Exception {
        log__.debug("初期表示処理");

        if (userMdl == null) {
            paramMdl.setBmkmain020NewList(new ArrayList<Bmk010InfoModel>());
            return;
        }

        //個人設定情報取得
        BmkUconfDao ucDao = new BmkUconfDao(con);
        BmkUconfModel ucMdl = ucDao.select(userMdl.getUsrsid());

        //新着ブックマーク表示日数を設定
        int newBmkDspCnt = GSConstBookmark.NEW_DEFO_DSP_COUNT;
        if (ucMdl != null) {
            newBmkDspCnt = ucMdl.getBucNewCnt();
        }
        Bmk010Dao bmkDao = new Bmk010Dao(con, reqMdl__);
        paramMdl.setBmkmain020NewList(bmkDao.selectNewBmk(userMdl.getUsrsid(), newBmkDspCnt));
    }

    /**
     * <br>[機  能] メイン画面の表示・非表示を判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl BmkMain020ParamModel
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return 1:表示 0:非表示
     * @throws SQLException SQL実行例外
     */
    public int isMainDsp(BmkMain020ParamModel paramMdl, Connection con, int usrSid)
    throws SQLException {
        BmkUconfDao dao = new BmkUconfDao(con);
        BmkUconfModel model = dao.select(usrSid);
        if (model == null) {
            return GSConstBookmark.DSP_YES;
        }
        return model.getBucMainNew();
    }
}