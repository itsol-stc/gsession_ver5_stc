package jp.groupsession.v2.sch.sch088;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAdmConfDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;

/**
 * <br>[機  能] スケジュール ショートメール通知設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch088Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch088Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch088Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch088ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Sch088ParamModel paramMdl, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel admconf = biz.getAdmConfModel(con);

        //ショートメール通知区分
        paramMdl.setSch088smlSendKbn(NullDefault.getString(
                   paramMdl.getSch088smlSendKbn(), String.valueOf(admconf.getSadSmailSendKbn())));
        //ショートメール通知
        paramMdl.setSch088Smail(
                NullDefault.getString(
                   paramMdl.getSch088Smail(), String.valueOf(admconf.getSadSmail())));
        //グループスケジュールショートメール通知
        paramMdl.setSch088SmailGroup(
                NullDefault.getString(
                   paramMdl.getSch088SmailGroup(), String.valueOf(admconf.getSadSmailGroup())));
        //出欠確認時ショートメール通知
        paramMdl.setSch088SmailAttend(
                NullDefault.getString(
                    paramMdl.getSch088SmailAttend(), String.valueOf(admconf.getSadSmailAttend())));

    }

    /**
     * <br>[機  能] 設定された管理者設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch088ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Sch088ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        
        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel admconf = biz.getAdmConfModel(con);

        admconf.setSadSmailSendKbn(
          NullDefault.getInt(paramMdl.getSch088smlSendKbn(), GSConstSchedule.SMAIL_SEND_KBN_USER));
        admconf.setSadSmail(
          NullDefault.getInt(paramMdl.getSch088Smail(), GSConstSchedule.SMAIL_NOT_USE));
        admconf.setSadSmailGroup(
          NullDefault.getInt(paramMdl.getSch088SmailGroup(), GSConstSchedule.SMAIL_NOT_USE));
        admconf.setSadSmailAttend(
                NullDefault.getInt(paramMdl.getSch088SmailAttend(), GSConstSchedule.SMAIL_NOT_USE));
        admconf.setSadEuid(umodel.getUsrsid());
        admconf.setSadEdate(new UDate());

        boolean commitFlg = false;
        try {
            SchAdmConfDao dao = new SchAdmConfDao(con);
            int count = dao.updateSmailKbn(admconf);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(admconf);
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
