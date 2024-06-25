package jp.groupsession.v2.bmk.bmk110kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkAconfDao;
import jp.groupsession.v2.bmk.dao.BmkPublicEditDao;
import jp.groupsession.v2.bmk.model.BmkAconfModel;
import jp.groupsession.v2.bmk.model.BmkPublicEditModel;

/**
 * <br>[機  能] 管理者設定 権限設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk110knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk110knBiz.class);

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
    public int setBmkAconf(Bmk110knParamModel paramMdl,
            int usrSid, Connection con) throws SQLException {

      boolean commitFlg = false;

      int addEditFlg = 0;
      try {
          //DBの存在確認
          BmkAconfDao aConDao = new BmkAconfDao(con);
          BmkAconfModel aConModel = aConDao.selectAConf();
          //画面値セット
          BmkAconfModel aConMdl = createBmkAconfData(paramMdl, usrSid);

          if (aConModel == null) {
              aConMdl.setBacLimit(GSConstBookmark.LIMIT_YES);
              aConDao.insert(aConMdl);
          } else {
              aConDao.updateAuthority(aConMdl);
              addEditFlg = 1;
          }
          BmkPublicEditDao pubDao = new BmkPublicEditDao(con);
          //フィールド削除
          pubDao.delete();

          if (paramMdl.getBmk110PubEditKbn() == GSConstBookmark.EDIT_POW_GROUP) {
              //共有ブックマーク編集権限：グループ指定
              BmkPublicEditModel pubMdl = createBmkPublicEditData(paramMdl, usrSid);
              if (paramMdl.getBmk110GrpSid() != null) {
                  for (int i = 0; i < paramMdl.getBmk110GrpSid().length; i++) {
                      pubMdl.setGrpSid(Integer.parseInt(paramMdl.getBmk110GrpSid()[i]));
                      pubDao.insert(pubMdl);
                  }
              }
          } else if (paramMdl.getBmk110PubEditKbn() == GSConstBookmark.EDIT_POW_USER) {
              //共有ブックマーク編集権限：ユーザ指定
              BmkPublicEditModel pubMdl = createBmkPublicEditData(paramMdl, usrSid);
              if (paramMdl.getBmk110UserSid() != null) {
                  for (int i = 0; i < paramMdl.getBmk110UserSid().length; i++) {
                      pubMdl.setUsrSid(Integer.parseInt(paramMdl.getBmk110UserSid()[i]));
                      pubDao.insert(pubMdl);
                  }
              }
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
            Bmk110knParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        BmkAconfModel mdl = new BmkAconfModel();

        mdl.setBacPubEdit(paramMdl.getBmk110PubEditKbn());
        mdl.setBacGrpEdit(paramMdl.getBmk110GrpEditKbn());
        mdl.setBacLimit(GSConstBookmark.LIMIT_NO);
        mdl.setBacAuid(usrSid);
        mdl.setBacAdate(nowDate);
        mdl.setBacEuid(usrSid);
        mdl.setBacEdate(nowDate);

        return mdl;
    }
    /**
     * <br>[機  能] 共有ブックマーク編集権限情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk110knParamModel
     * @param usrSid ユーザSID
     * @return BmkPublicEditModel
     */
    public BmkPublicEditModel createBmkPublicEditData(
            Bmk110knParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        BmkPublicEditModel mdl = new BmkPublicEditModel();
        mdl.setGrpSid(-1);
        mdl.setUsrSid(-1);
        mdl.setBpeAuid(usrSid);
        mdl.setBpeAdate(nowDate);
        mdl.setBpeEuid(usrSid);
        mdl.setBpeEdate(nowDate);

        return mdl;
    }
}
