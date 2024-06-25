package jp.groupsession.v2.sch.sch095;

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
 * <br>[機  能] スケジュール ショートメール通知設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch095Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch095Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch095Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch095ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Sch095ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, umodel.getUsrsid());

        //ショートメール通知
        paramMdl.setSch095Smail(
                NullDefault.getString(
                        paramMdl.getSch095Smail(), String.valueOf(pconf.getSccSmail())));
        //グループスケジュールショートメール通知
        paramMdl.setSch095SmailGroup(
                NullDefault.getString(
                        paramMdl.getSch095SmailGroup(), String.valueOf(pconf.getSccSmailGroup())));
        //出欠確認時ショートメール通知
        paramMdl.setSch095SmailAttend(
                NullDefault.getString(
                       paramMdl.getSch095SmailAttend(), String.valueOf(pconf.getSccSmailAttend())));

    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch095ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Sch095ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, umodel.getUsrsid());

        pconf.setSccSmail(
                NullDefault.getInt(paramMdl.getSch095Smail(), GSConstSchedule.SMAIL_NOT_USE));
        pconf.setSccSmailGroup(
                NullDefault.getInt(paramMdl.getSch095SmailGroup(), GSConstSchedule.SMAIL_NOT_USE));
        pconf.setSccSmailAttend(
                NullDefault.getInt(paramMdl.getSch095SmailAttend(), GSConstSchedule.SMAIL_NOT_USE));
        pconf.setSccEuid(umodel.getUsrsid());
        pconf.setSccEdate(new UDate());

        boolean commitFlg = false;
        try {
            SchPriConfDao dao = new SchPriConfDao(con);
            int count = dao.updateSmail(pconf);
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
