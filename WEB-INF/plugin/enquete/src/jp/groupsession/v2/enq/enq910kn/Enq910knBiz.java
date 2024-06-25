package jp.groupsession.v2.enq.enq910kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqAdmConfDao;
import jp.groupsession.v2.enq.dao.EnqCrtUserDao;
import jp.groupsession.v2.enq.enq910.Enq910Biz;
import jp.groupsession.v2.enq.model.EnqAdmConfModel;
import jp.groupsession.v2.enq.model.EnqCrtUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 管理者設定 アンケート発信対象者設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq910knBiz extends Enq910Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq910knBiz.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq910knBiz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq910knModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq910knParamModel enq910knModel,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報を取得");

        // 発信対象者区分
        enq910knModel.setEnq910knTaisyoKbn(enq910knModel.getEnq910TaisyoKbn());
        // 発信対象者
        enq910knModel.setEnq910knAddSenderLabel(_createSenderLabel(enq910knModel, con));
    }

    /**
     * <br>[機  能] アンケート発信対象者を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq910knModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void doCommit(Enq910knParamModel enq910knModel,
                         RequestModel reqMdl,
                         Connection con) throws SQLException {

        log__.debug("アンケート発信対象者登録処理");

        EnqCrtUserModel wkList = null;
        boolean commitFlg = false;

        // セッション情報を取得
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();

        // 更新処理
        try {
            con.setAutoCommit(false);

            // 管理者設定のアンケート発信対象者区分を更新
            EnqCommonBiz biz = new EnqCommonBiz();
            biz.getAdmConfData(con, sessionUsrSid);
            EnqAdmConfDao confdao = new EnqAdmConfDao(con);
            confdao.updateKbnTaisyo(__getAdmConfModel(enq910knModel, sessionUsrSid));

            // アンケート発信対象者を更新
            // --既存のアンケート発信対象者を削除する
            EnqCrtUserDao crtDao = new EnqCrtUserDao(con);
            crtDao.deleteAll();

            // --アンケート発信対象者を登録する
            if (enq910knModel.getEnq910TaisyoKbn() == GSConstEnquete.CONF_TAISYO_LIMIT) {

                // 発信対象者
                String[] senders = enq910knModel.getEnq910MakeSenderList();
                if (senders == null || senders.length < 1) {
                    return;
                }

                // 発信対象者登録処理
                for (String sids : senders) {
                    wkList = new EnqCrtUserModel();
                    String str = NullDefault.getString(sids, "-1");

                    // グループ
                    if (str.contains(new String(GROUP_PREFIX).subSequence(0, 1))) {
                        wkList = __getCrtUserModel(
                                GSConstEnquete.TAISYO_KBN_GROUP,
                                NullDefault.getLong(StringUtil.substitute(
                                        str, GROUP_PREFIX, ""), -1),
                                sessionUsrSid);
                    // ユーザ
                    } else {
                        wkList = __getCrtUserModel(GSConstEnquete.TAISYO_KBN_USER,
                                                   NullDefault.getLong(str, -1),
                                                   sessionUsrSid);
                    }
                    crtDao.insert(wkList);
                }
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("アンケートの発信対象者の更新に失敗しました。" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
            con.setAutoCommit(true);
        }
    }

    /**
     * <br>[機  能] 管理者設定 アンケート発信対象者区分更新用のモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq910knModel パラメータモデル
     * @param usrSid ユーザSID
     * @return 管理者設定 アンケート発信対象者区分の更新用モデル
     */
    private EnqAdmConfModel __getAdmConfModel(Enq910knParamModel enq910knModel, int usrSid) {

        log__.debug("管理者設定 アンケート発信対象者区分更新用モデルの取得処理");

        EnqAdmConfModel aconf = new EnqAdmConfModel();

        // アンケート発信対象者区分
        aconf.setEacKbnTaisyo(enq910knModel.getEnq910TaisyoKbn());
        // 更新者ID
        aconf.setEacEuid(usrSid);
        // 更新日
        aconf.setEacEdate(new UDate());

        return aconf;
    }

    /**
     * <br>[機  能] アンケート発信対象者登録モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn ユーザまたはグループの区分
     * @param sid ユーザSID または グループSID
     * @param sessionUsrSid セッションユーザSID
     * @return アンケート発信対象者モデル
     */
    private EnqCrtUserModel __getCrtUserModel(int kbn, long sid, int sessionUsrSid) {

        log__.debug("アンケート発信対象者登録モデルの取得処理");

        EnqCrtUserModel mdl = new EnqCrtUserModel();
        UDate now = new UDate();

        // 区分
        mdl.setEcuKbn(kbn);
        // ユーザSID または グループSIDO
        mdl.setEcuSid(sid);
        // 登録者ID
        mdl.setEcuAuid(sessionUsrSid);
        // 登録日時
        mdl.setEcuAdate(now);
        // 更新者ID
        mdl.setEcuEuid(sessionUsrSid);
        // 更新日時
        mdl.setEcuEdate(now);

        return mdl;
    }
}
