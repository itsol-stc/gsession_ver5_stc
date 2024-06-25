package jp.groupsession.v2.anp.anp020kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.anp.dao.AnpDatausedSumDao;
import jp.groupsession.v2.anp.dao.AnpJdataDao;
import jp.groupsession.v2.anp.model.AnpJdataModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;


/**
 * <br>[機  能] 安否状況入力確認画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp020knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp020knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp020knModel パラメータモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp020knParamModel anp020knModel, Connection con)
                throws Exception {

        anp020knModel.setAnp020knDspComment(StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(anp020knModel.getAnp020Comment(), "")));
    }

    /**
     * <br>[機  能] 更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param anp020knModel パラメータモデル
     * @param con DBコネクション
     * @param usrSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void doUpdate(Anp020knParamModel anp020knModel, Connection con, int usrSid)
                        throws Exception {

        boolean commitFlg = false;
        UDate now = new UDate();

        try {
            con.setAutoCommit(false);

            AnpJdataModel bean = new AnpJdataModel();
            bean.setAphSid(Integer.valueOf(anp020knModel.getAnpiSid()));
            bean.setUsrSid(Integer.valueOf(anp020knModel.getUserSid()));
            bean.setApdJokyoFlg(Integer.valueOf(anp020knModel.getAnp020JokyoFlg()));
            bean.setApdPlaceFlg(Integer.valueOf(anp020knModel.getAnp020PlaceFlg()));
            bean.setApdSyusyaFlg(Integer.valueOf(anp020knModel.getAnp020SyusyaFlg()));
            bean.setApdComment(anp020knModel.getAnp020Comment());
            bean.setApdRdate(now);
            bean.setApdEuid(usrSid);
            bean.setApdEdate(now);

            AnpJdataDao dao = new AnpJdataDao(con);
            AnpDatausedSumDao adsDao = new AnpDatausedSumDao(con);
            adsDao.insertJdataDelDiff(Arrays.asList(bean.getUsrSid()), bean.getAphSid());
            dao.updateUserInput(bean);
            adsDao.insertJdataAddDiff(Arrays.asList(bean.getUsrSid()), bean.getAphSid());
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] アカウント名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid セッションユーザSID
     * @return アカウント名
     * @throws Exception 実行例外
     */
    public String getAccountName(Connection con, int usrSid)
                        throws Exception {

        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel udata = usiDao.getUsersInf(usrSid);
        return udata.getUsiSei() + " " + udata.getUsiMei();
    }
}