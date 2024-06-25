package jp.groupsession.v2.bmk.bmk160kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkAconfDao;
import jp.groupsession.v2.bmk.model.BmkAconfModel;

/**
 * <br>[機  能] 管理者設定 セキュリティ設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk160knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk160knBiz.class);

    /**
     * <br>[機  能] 権限設定をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk110knParamModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return addEditFlg 登録モード(0:登録 1:編集)
     */
    public int setBmkAconf(Bmk160knParamModel paramMdl,
            int usrSid, Connection con) throws SQLException {

      boolean commitFlg = false;

      int addEditFlg = 1;
      try {
          //DBの存在確認
          BmkAconfDao aConDao = new BmkAconfDao(con);

          //画面値セット
          BmkAconfModel aConMdl = createBmkAconfData(paramMdl, usrSid);

          if (aConDao.updateInfoLimit(aConMdl) <= 0) {
              aConDao.insert(aConMdl);
              addEditFlg = 0;
          }

          con.commit();
          commitFlg = true;

      } catch (SQLException e) {
          log__.error("", e);
          throw e;
      } finally {
          if (!commitFlg) {
              con.rollback();
          }
      }
      return addEditFlg;
    }

    /**
     * <br>[機  能] 管理者設定情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk110knParamModel
     * @param usrSid ユーザSID
     * @return BmkAconfModel
     */
    public BmkAconfModel createBmkAconfData(
            Bmk160knParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        BmkAconfModel mdl = new BmkAconfModel();

        mdl.setBacPubEdit(GSConstBookmark.EDIT_POW_ADMIN);
        mdl.setBacGrpEdit(GSConstBookmark.GROUP_EDIT_ADMIN);
        mdl.setBacLimit(paramMdl.getBmk160LimitKbn());
        mdl.setBacAuid(usrSid);
        mdl.setBacAdate(nowDate);
        mdl.setBacEuid(usrSid);
        mdl.setBacEdate(nowDate);

        return mdl;
    }
}
