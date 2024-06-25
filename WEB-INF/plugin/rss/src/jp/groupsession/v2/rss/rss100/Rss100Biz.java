package jp.groupsession.v2.rss.rss100;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.dao.RssUconfDao;
import jp.groupsession.v2.rss.model.RssUconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] RSSリーダー 新着RSS表示日数設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss100Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rss100Biz.class);
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Rss100Biz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Rss100Biz(Connection con) {
        con_ = con;
    }

    /**
     * <br>[機  能] 新着RSS表示日数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void setInitData(Rss100ParamModel paramMdl, Connection con,
                            int userSid, RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        //DBより現在の設定を取得する。(なければデフォルト)
        RssUconfDao uConfdao = new RssUconfDao(con);
        RssUconfModel uConfmodel = uConfdao.select(userSid);

        if (uConfmodel == null) {
            paramMdl.setRss100newCnt(GSConstRss.NEW_DEFO_DSP_COUNT);
        } else {
            paramMdl.setRss100newCnt(uConfmodel.getRucNewCnt());
        }

        paramMdl.setNewCntLabel(reqMdl);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 新着RSS表示日数設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param sessionUserSid ユーザSID
     * @throws SQLException SQL実行エラー
     * @return addEditFlg 登録モード(0:登録 1:編集)
     */
    public int setRssUconfSetting(
            Rss100ParamModel paramMdl,
            RequestModel reqMdl,
            int sessionUserSid) throws SQLException {

        boolean commitFlg = false;
        int addEditFlg = GSConstRss.RSSCMDMODE_ADD;

        try {
            //DBの存在確認
            RssUconfDao dao = new RssUconfDao(con_);
            RssUconfModel model = dao.select(sessionUserSid);
            //画面値セット
            RssUconfModel crtMdl = createRssUconfData(reqMdl, paramMdl, sessionUserSid);

            if (model == null) {
                //新規登録
                dao.insert(crtMdl);
            } else {
                //編集
                dao.updateUconf(crtMdl);
                addEditFlg = GSConstRss.RSSCMDMODE_EDIT;
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con_.commit();
            }
        }
        return addEditFlg;
    }

    /**
     * <br>[機  能] RSS個人設定情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param usrSid ユーザSID
     * @return BmkUconfModel 個人設定情報
     */
    public RssUconfModel createRssUconfData(RequestModel reqMdl,
                                            Rss100ParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        RssUconfModel mdl = new RssUconfModel();
        mdl.setUsrSid(usrSid);
        mdl.setRucNewCnt(paramMdl.getRss100newCnt());
        mdl.setRucAuid(usrSid);
        mdl.setRucAdate(nowDate);
        mdl.setRucEuid(usrSid);
        mdl.setRucEdate(nowDate);

        return mdl;
    }
}
