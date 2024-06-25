package jp.groupsession.v2.rsv.rsv130kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvDataPubDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;

/**
 * <br>[機  能] 施設予約 管理者設定 手動データ削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv130knBiz {

  /** コネクション */
  protected Connection con_ = null;
  /** リクエスト情報 */
  protected RequestModel reqMdl_ = null;

  /**
   * <br>[機  能] コンストラクタ
   * <br>[解  説]
   * <br>[備  考]
   *
   * @param con コネクション
   * @param reqMdl RequestModel
   */
  public Rsv130knBiz(Connection con, RequestModel reqMdl) {
      con_ = con;
      reqMdl_ = reqMdl;
  }


  /**
   * <br>[機  能] 初期表示処理を行います
   * <br>[解  説]
   * <br>[備  考]
   * @param paramMdl Rsv130knParamModel
   * @throws SQLException 例外
   */
  public void initDsp(Rsv130knParamModel paramMdl) throws SQLException {

  }

  /**
   * <br>[機  能] 削除処理を行います
   * <br>[解  説]
   * <br>[備  考]
   * @param paramMdl Rsv130knParamModel
   * @param userSid ログインユーザSID
   * @return count
   * @throws SQLException 例外
   */
  public int delete(Rsv130knParamModel paramMdl, int userSid) throws SQLException {
      UDate delDate = new UDate();
      delDate.addYear(-paramMdl.getRsv130year());
      delDate.addMonth(-paramMdl.getRsv130month());
      delDate.setMaxHhMmSs();

      RsvSisYrkDao dao = new RsvSisYrkDao(con_);
      ArrayList<Integer> deleteSidList = dao.getRsySidsDeleteForAdmin(delDate);
      int count = dao.deleteForAdmin(delDate);

      RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con_);
      kyrkDao.delete(deleteSidList);

      RsvDataPubDao pubDao = new RsvDataPubDao(con_);
      pubDao.deleteList(deleteSidList);

      return count;
  }
}
