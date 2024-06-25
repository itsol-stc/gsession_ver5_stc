package jp.groupsession.v2.rsv.rsv120kn;

import java.sql.Connection;
import java.sql.SQLException;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 施設予約 管理者設定 自動データ削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv120knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv120knBiz.class);

    /** コネクション */
    protected Connection con_ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Rsv120knBiz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv120knParamModel
     */
    public void initDsp(Rsv120knParamModel paramMdl) {

    }

    /**
     * <br>[機  能] DB登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv120knParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    public void registKanriBatch(Rsv120knParamModel paramMdl, int userSid) throws SQLException {
        RsvAdmConfModel model = RsvCommonBiz.getDefaultAdmConfModel();
        //モデルを取得
        model = getModel(model, paramMdl, userSid);
        //更新
        int count = updateKanriBatch(model, paramMdl, userSid);
        //更新件数が0なら新規登録を行う
        if (count <= 0) {
            log__.debug("新規登録を行います。count = " + count);
            insertKanriBatch(model, paramMdl, userSid);
        }
    }

    /**
     * <br>[機  能] モデルを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param model RsvAdmConfModel
     * @param paramMdl Rsv120knParamModel
     * @param userSid ログインユーザSID
     * @return model
     */
    private RsvAdmConfModel getModel(
            RsvAdmConfModel model, Rsv120knParamModel paramMdl, int userSid) {
        UDate now = new UDate();
        model.setRacAdlKbn(paramMdl.getRsv120batchKbn());
        model.setRacAdlYear(paramMdl.getRsv120year());
        model.setRacAdlMonth(paramMdl.getRsv120month());
        model.setRacHourDiv(GSConstReserve.DF_HOUR_DIVISION);
        model.setRacAuid(userSid);
        model.setRacAdate(now);
        model.setRacEuid(userSid);
        model.setRacEdate(now);
        return model;
    }

    /**
     * <br>[機  能] 管理者設定を更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param model RsvAdmConfModel
     * @param paramMdl Rsv120knParamModel
     * @param userSid ログインユーザSID
     * @return int 更新件数
     * @throws SQLException 例外
     */
    private int updateKanriBatch(
            RsvAdmConfModel model, Rsv120knParamModel paramMdl, int userSid) throws SQLException {
        RsvAdmConfDao dao = new RsvAdmConfDao(con_);
        return dao.update(model);
    }
    /**
     * <br>[機  能] 管理者設定を登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param model RsvAdmConfModel
     * @param paramMdl Rsv120knParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    private void insertKanriBatch(
            RsvAdmConfModel model, Rsv120knParamModel paramMdl, int userSid) throws SQLException {
        RsvAdmConfDao dao = new RsvAdmConfDao(con_);
        dao.insert(model);
    }
}
