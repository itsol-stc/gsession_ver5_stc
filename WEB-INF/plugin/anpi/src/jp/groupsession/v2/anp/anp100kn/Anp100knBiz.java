package jp.groupsession.v2.anp.anp100kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpDatausedSumDao;
import jp.groupsession.v2.anp.dao.AnpMtempDao;
import jp.groupsession.v2.anp.dao.AnpMtempSortDao;
import jp.groupsession.v2.anp.model.AnpMtempModel;
import jp.groupsession.v2.anp.model.AnpMtempSortModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;


/**
 * <br>[機  能] 管理者設定・基本設定確認画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp100knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp100knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp100knModel パラメータモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp100knParamModel anp100knModel, Connection con)
                        throws Exception {
        log__.debug("初期表示");

        //表示用テキスト変換
        anp100knModel.setAnp100knDspText1(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(anp100knModel.getAnp100Text1()), ""));
        anp100knModel.setAnp100knDspText2(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(anp100knModel.getAnp100Text2()), ""));
    }

    /**
     * <br>[機  能] テンプレート 登録・更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param anp100knModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @param cntCon 採番コントローラ
     * @throws Exception 実行例外
     */
    public void doRegist(Anp100knParamModel anp100knModel, RequestModel reqMdl, Connection con,
                            MlCountMtController cntCon)
                        throws Exception {

        boolean commitFlg = false;

        try {
            con.setAutoCommit(false);

            //セッション情報を取得
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

            if (StringUtil.isNullZeroStringSpace(anp100knModel.getAnp090SelectSid())) {
                //追加
                __doInsert(anp100knModel, con, sessionUsrSid, cntCon);
            } else {
                //修正
                __doUpdate(anp100knModel, con, sessionUsrSid);
            }

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
     * <br>[機  能] 新規配信を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param anp100knModel パラメータモデル
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @param cntCon 採番コントローラ
     * @throws Exception 実行例外
     */
    private void __doInsert(Anp100knParamModel anp100knModel, Connection con, int usrSid,
                                MlCountMtController cntCon)
                        throws Exception {

        //SID採番
        int mSid = (int) cntCon.getSaibanNumber(
                                GSConstAnpi.SBNSID_ANPI,
                                GSConstAnpi.SBNSID_SUB_MAILTEMPLATE,
                                usrSid);

        AnpMtempDao dao = new AnpMtempDao(con);
        AnpMtempModel bean = __getMtemp(anp100knModel, mSid, usrSid);
        dao.insert(bean);

        //ソートテーブルの追加
        AnpMtempSortModel sortMdl = new AnpMtempSortModel();
        AnpMtempSortDao sortDao = new AnpMtempSortDao(con);
        sortMdl.setApmSid(mSid);
        sortMdl.setAmsSort(sortDao.getMaxSort());
        sortDao.insert(sortMdl);
        
        AnpDatausedSumDao adsDao = new AnpDatausedSumDao(con);
        adsDao.insertMailAddDiff(Arrays.asList(mSid));
    }

    /**
     * <br>[機  能] 更新処理行う
     * <br>[解  説]
     * <br>[備  考]
     * @param anp100knModel パラメータモデル
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @throws Exception 実行例外
     */
    private void __doUpdate(Anp100knParamModel anp100knModel, Connection con, int usrSid)
                        throws Exception {

        AnpMtempDao dao = new AnpMtempDao(con);
        AnpDatausedSumDao adsDao = new AnpDatausedSumDao(con);
        adsDao.insertMailDelDiff(Arrays.asList(
                Integer.parseInt(anp100knModel.getAnp090SelectSid())));
        AnpMtempModel bean = __getMtemp(
                anp100knModel, Integer.valueOf(anp100knModel.getAnp090SelectSid()), usrSid);
        dao.update(bean);
        adsDao.insertMailAddDiff(Arrays.asList(
                Integer.parseInt(anp100knModel.getAnp090SelectSid())));
    }

    /**
     * <br>[機  能] テンプレートデータの登録内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param anp100knModel パラメータモデル
     * @param mSid テンプレートSID
     * @param usrSid 更新ユーザSID
     * @return テンプレートデータ
     */
    private AnpMtempModel __getMtemp(Anp100knParamModel anp100knModel, int mSid, int usrSid) {

        AnpMtempModel mModel = new AnpMtempModel();
        mModel.setApmSid(mSid);
        mModel.setApmTitle(anp100knModel.getAnp100Title());
        mModel.setApmSubject(anp100knModel.getAnp100Subject());
        mModel.setApmText1(anp100knModel.getAnp100Text1());
        mModel.setApmText2(anp100knModel.getAnp100Text2());
        mModel.setApmAuid(usrSid);
        mModel.setApmAdate(new UDate());
        mModel.setApmEuid(usrSid);
        mModel.setApmEdate(new UDate());

        return mModel;
    }
}