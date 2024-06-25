package jp.groupsession.v2.man.man420kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.man.dao.base.CmnImpTimeDao;
import jp.groupsession.v2.man.man420.Man420ParamModel;
import jp.groupsession.v2.man.model.CmnImpTimeModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;


/**
 *
 * <br>[機  能]ユーザ連携自動インポート機能設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man420knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man420knBiz.class);

    /**
     *
     * <br>[機  能]自動インポートが設定されていない場合は値を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param citDao CmnImpTimeDao
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    public void insertImpTime(Connection con,
            CmnImpTimeDao citDao,
            Man420knParamModel paramMdl) throws SQLException {

        CmnImpTimeModel cmiMdl = new CmnImpTimeModel();
        setRegisterData(cmiMdl, paramMdl);

        boolean commit = false;
        try {
            citDao.insert(cmiMdl);
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("データ登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }


    }

    /**
    *
    * <br>[機  能]既に自動インポート設定がある場合は編集を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param citDao CmnImpTimeDao
    * @param paramMdl パラメータモデル
    * @throws SQLException SQL実行時例外
    */
    public void updateImpTime(Connection con,
            CmnImpTimeDao citDao,
            Man420knParamModel paramMdl) throws SQLException {

        con.setAutoCommit(true);

        CmnImpTimeModel cmiMdl = new CmnImpTimeModel();
        setRegisterData(cmiMdl, paramMdl);
        boolean commit = false;
        try {
            citDao.update(cmiMdl);
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("データアップデートに失敗", e);
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        con.setAutoCommit(false);

    }

    /**
     *
     * <br>[機  能]自動インポート設定の登録データをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param cmiMdl CmnImpTimeModel
     * @param paramMdl パラメータモデル
     */
    public void setRegisterData(CmnImpTimeModel cmiMdl, Man420knParamModel paramMdl) {

        UDate day = new UDate();
        day.setHour(NullDefault.getInt(paramMdl.getMan420UsrFrHour(), 0));

        cmiMdl.setCitUsrFlg(paramMdl.getMan420UsrImpFlg());
        cmiMdl.setCitUsrTimeFlg(paramMdl.getMan420UsrImpTimeSelect());
        cmiMdl.setCitUsrTime(day);

        cmiMdl.setCitGrpFlg(0);
        cmiMdl.setCitGrpTimeFlg(0);
        cmiMdl.setCitGrpTime(day);

        cmiMdl.setCitBegFlg(0);
        cmiMdl.setCitBegTimeFlg(0);
        cmiMdl.setCitBegTime(day);

    }

    /**
     *
     * <br>[機  能]オペレーションログに表示する本文を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param gsMsg メッセージリソース
     * @return ログ本文
     */
    public String settingLogValue(Man420ParamModel paramMdl, GsMessage gsMsg) {
        StringBuilder logValue = new StringBuilder();

        //インポート設定・設定時間を表示
        if (paramMdl.getMan420UsrImpFlg() == 1) {
            logValue.append(gsMsg.getMessage("main.man420.1"));
            logValue.append(":");
            logValue.append(gsMsg.getMessage("main.man420.8"));
            logValue.append("\n");

            logValue.append(gsMsg.getMessage("cmn.import"));
            logValue.append(gsMsg.getMessage("main.man080.1"));
            logValue.append(":");

            if (paramMdl.getMan420UsrImpTimeSelect() == GSConstUser.IMPORT_TIMING_MIN) {
                logValue.append(gsMsg.getMessage("main.man420.4"));
            } else if (paramMdl.getMan420UsrImpTimeSelect() == GSConstUser.IMPORT_TIMING_HOUR) {
                logValue.append(gsMsg.getMessage("main.man420.5"));
            } else if (paramMdl.getMan420UsrImpTimeSelect() == GSConstUser.IMPORT_TIMING_SELECT) {
                logValue.append(gsMsg.getMessage("main.man420.6"));
                logValue.append("\n");
                logValue.append(gsMsg.getMessage("cmn.starttime"));
                logValue.append(":");
                logValue.append(paramMdl.getMan420UsrFrHour());
                logValue.append(gsMsg.getMessage("cmn.hour.input"));
            }

        } else {
            logValue.append(gsMsg.getMessage("main.man420.1"));
            logValue.append(":");
            logValue.append(gsMsg.getMessage("main.man420.7"));
        }

        return logValue.toString();
    }
}
