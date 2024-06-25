package jp.groupsession.v2.sch.sch097;

import java.sql.Connection;
import java.sql.SQLException;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.model.SchPriConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] スケジュール 個人設定 重複登録設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch097Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch097Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch097Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch097ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Sch097ParamModel paramMdl, BaseUserModel umodel, Connection con)
    throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, umodel.getUsrsid());

        //重複登録
        paramMdl.setSch097RepeatKbn(
                NullDefault.getString(
                        paramMdl.getSch097RepeatKbn(), String.valueOf(pconf.getSccRepeatKbn())));

        //重複登録=NGの場合、自身による登録のみ重複登録を可否
        paramMdl.setSch097RepeatMyKbn(
                NullDefault.getString(
                        paramMdl.getSch097RepeatMyKbn(),
                        String.valueOf(pconf.getSccRepeatMyKbn())));
    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch097ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Sch097ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, umodel.getUsrsid());

        //重複登録区分
        pconf.setSccRepeatKbn(NullDefault.getInt(
                paramMdl.getSch097RepeatKbn(), GSConstSchedule.SCH_REPEAT_KBN_OK));
        pconf.setSccRepeatMyKbn(NullDefault.getInt(
                paramMdl.getSch097RepeatMyKbn(), GSConstSchedule.SCH_REPEAT_MY_KBN_NG));
        pconf.setSccEuid(umodel.getUsrsid());
        pconf.setSccEdate(new UDate());

        boolean commitFlg = false;
        try {
            SchPriConfDao dao = new SchPriConfDao(con);
            int count = dao.updateRepeatKbn(pconf);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(pconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
}
