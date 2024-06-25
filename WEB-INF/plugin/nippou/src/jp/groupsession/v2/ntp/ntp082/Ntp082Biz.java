package jp.groupsession.v2.ntp.ntp082;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpAdmConfDao;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;

/**
 * <br>[機  能] 日報 自動データ削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp082Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp082Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Ntp082Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp082ParamModel
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Ntp082ParamModel paramMdl) throws SQLException {
        //DBより現在の設定を取得する。(なければデフォルト)
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        NtpAdmConfModel conf = biz.getAdminConfiModel(con__);
        paramMdl.setNtp082AtdelFlg(conf.getNacAtdelFlg());
        paramMdl.setNtp082AtdelYear(conf.getNacAtdelY());
        paramMdl.setNtp082AtdelMonth(conf.getNacAtdelM());
    }

    /**
     * <br>[機  能] 表示用データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp082ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setShowData(Ntp082ParamModel paramMdl, Connection con) throws SQLException  {

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
     * @param paramMdl Ntp082ParamModel
     * @param umodel ユーザモデル
     * @throws SQLException SQL実行エラー
     */
    public void setAutoDeleteSetting(Ntp082ParamModel paramMdl,
            BaseUserModel umodel) throws SQLException {

        //既存のデータを取得
        //DBより現在の設定を取得する。(なければデフォルト)
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        NtpAdmConfModel conf = biz.getAdminConfiModel(con__);
        //データを設定
        conf.setNacAtdelFlg(paramMdl.getNtp082AtdelFlg());
        if (paramMdl.getNtp082AtdelFlg() == GSConstNippou.AUTO_DELETE_OFF) {
            conf.setNacAtdelY(-1);
            conf.setNacAtdelM(-1);
        } else {
            conf.setNacAtdelY(paramMdl.getNtp082AtdelYear());
            conf.setNacAtdelM(paramMdl.getNtp082AtdelMonth());
        }
        conf.setNacEuid(umodel.getUsrsid());
        UDate now = new UDate();
        conf.setNacEdate(now);
        //DB更新
        boolean commitFlg = false;
        try {
            NtpAdmConfDao dao = new NtpAdmConfDao(con__);
            int count = dao.updateAutoDelete(conf);
            if (count <= 0) {
                conf.setNacHourDiv(GSConstNippou.DF_HOUR_DIVISION);
                conf.setNacAuid(umodel.getUsrsid());
                conf.setNacAdate(now);
                dao.insert(conf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("共有範囲設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            }
        }
    }
}