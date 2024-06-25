package jp.groupsession.v2.cir.cir220kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir220.Cir220Biz;
import jp.groupsession.v2.cir.dao.CirAconfDao;
import jp.groupsession.v2.cir.dao.CirArestDao;
import jp.groupsession.v2.cir.model.CirAconfModel;
import jp.groupsession.v2.cir.model.CirArestModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 管理者設定 回覧板登録制限設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir220knBiz extends Cir220Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir220knBiz.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Cir220knBiz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cir220knModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Cir220knParamModel cir220knModel,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報を取得");

        // 送信対象者区分
        cir220knModel.setCir220knTaisyoKbn(cir220knModel.getCir220TaisyoKbn());
        // 送信対象者
        cir220knModel.setCir220knAddSenderLabel(_createSenderLabel(cir220knModel, con));
    }

    /**
     * <br>[機  能] 回覧板登録許可対象者を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void doCommit(Cir220knParamModel paramMdl,
                         RequestModel reqMdl,
                         Connection con) throws SQLException {

        boolean commitFlg = false;
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        UDate now = new UDate();

        try {
            //管理者設定 作成権限者制限区分を更新
            CirAconfModel aconfMdl = new CirAconfModel();
            if (paramMdl.getCir220TaisyoKbn() == GSConstCircular.CAF_AREST_KBN_SELECT) {
                aconfMdl.setCafArestKbn(GSConstCircular.CAF_AREST_KBN_SELECT);
            } else {
                aconfMdl.setCafArestKbn(GSConstCircular.CAF_AREST_KBN_ALL);
            }
            aconfMdl.setCafEuid(sessionUsrSid);
            aconfMdl.setCafEdate(now);

            CirAconfDao aconfDao = new CirAconfDao(con);
            aconfDao.updateArest(aconfMdl);

            //回覧板登録制限設定を更新
            CirArestDao restDao = new CirArestDao(con);
            restDao.deleteAll();

            if (paramMdl.getCir220TaisyoKbn() == GSConstCircular.CAF_AREST_KBN_SELECT) {
                String[] senders = paramMdl.getCir220MakeSenderList();
                if (senders == null || senders.length < 1) {
                    return;
                }

                List<CirArestModel> restList = new ArrayList<CirArestModel>();
                for (String sids : senders) {
                    CirArestModel restMdl = new CirArestModel();
                    String str = NullDefault.getString(sids, "-1");

                    // グループ
                    if (str.contains(new String(GROUP_PREFIX).subSequence(0, 1))) {
                        restMdl.setGrpSid(
                                Integer.parseInt(
                                        StringUtil.substitute(
                                        str, GROUP_PREFIX, "")));
                        restMdl.setUsrSid(-1);
                    // ユーザ
                    } else {
                        restMdl.setGrpSid(-1);
                        restMdl.setUsrSid(NullDefault.getInt(str, -1));
                    }

                    restList.add(restMdl);
                }

                restDao.insert(restList);
            }

            con.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("回覧板登録制限設定の更新に失敗しました。" + e);
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
            con.setAutoCommit(true);
        }
    }
}
