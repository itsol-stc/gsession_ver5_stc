package jp.groupsession.v2.sch.sch082;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAdmConfDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] スケジュール 自動データ削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch082Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch082Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch082Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Sch082ParamModel paramMdl,
            Connection con) throws SQLException {
        //DBより現在の設定を取得する。(なければデフォルト)
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel conf = biz.getAdmConfModel(con);
        paramMdl.setSch082AtdelFlg(conf.getSadAtdelFlg());
        paramMdl.setSch082AtdelYear(conf.getSadAtdelY());
        paramMdl.setSch082AtdelMonth(conf.getSadAtdelM());
    }

    /**
     * <br>[機  能] 表示用データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setShowData(Sch082ParamModel paramMdl, Connection con) throws SQLException  {

        //バッチ処理実行時間を取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();
        String batchTime = "";
        if (batMdl != null) {
            batchTime = String.valueOf(batMdl.getBatFrDate());
        }
        paramMdl.setBatchTime(batchTime);
    }

    /**
     * <br>[機  能] 共有範囲設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return SchAdmConfModel
     */
    public SchAdmConfModel setAutoDeleteSetting(Sch082ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //既存のデータを取得
        //DBより現在の設定を取得する。(なければデフォルト)
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel conf = biz.getAdmConfModel(con);
        //データを設定
        conf.setSadAtdelFlg(paramMdl.getSch082AtdelFlg());
        if (paramMdl.getSch082AtdelFlg() == GSConstSchedule.AUTO_DELETE_OFF) {
            conf.setSadAtdelY(-1);
            conf.setSadAtdelM(-1);
        } else {
            conf.setSadAtdelY(paramMdl.getSch082AtdelYear());
            conf.setSadAtdelM(paramMdl.getSch082AtdelMonth());
        }
        conf.setSadEuid(umodel.getUsrsid());
        UDate now = new UDate();
        conf.setSadEdate(now);
        //DB更新
        boolean commitFlg = false;
        try {
            SchAdmConfDao dao = new SchAdmConfDao(con);
            int count = dao.updateAutoDelete(conf);
            if (count <= 0) {
                conf.setSadHourDiv(GSConstSchedule.DF_HOUR_DIVISION);
                conf.setSadAuid(umodel.getUsrsid());
                conf.setSadAdate(now);
                dao.insert(conf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("共有範囲設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
        return conf;
    }
}
