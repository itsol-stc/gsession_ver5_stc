package jp.groupsession.v2.bmk.bmk060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkBelongLabelDao;
import jp.groupsession.v2.bmk.dao.BmkLabelDao;
import jp.groupsession.v2.bmk.model.BmkBelongLabelModel;
import jp.groupsession.v2.bmk.model.BmkLabelModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;

/**
 * <br>[機  能] ラベル登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Bmk060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk060Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk060ParamModel
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException 実行例外
     */
    public void setInitData(Bmk060ParamModel paramMdl, int userSid, Connection con)
    throws SQLException {
        log__.debug("START");

        if (paramMdl.getBmk010mode() == GSConstBookmark.BMK_KBN_GROUP) {
            //グループブックマーク
            //グループ名の設定
            GroupDao grpDao = new GroupDao(con);
            CmnGroupmModel grpMdl = grpDao.getGroup(paramMdl.getBmk010groupSid());
            paramMdl.setBmk050GrpName(
                    StringUtilHtml.transToHTmlPlusAmparsant(grpMdl.getGrpName()));
        }

        if (paramMdl.getBmk060LblName() == null
            && paramMdl.getBmk050ProcMode() == GSConstBookmark.LABEL_MODE_EDIT) {
            //処理モード：編集モード
            BmkLabelDao dao = new BmkLabelDao(con);
            BmkLabelModel model = dao.select(paramMdl.getBmk050LblSid());
            //ラベル名の設定
            paramMdl.setBmk060LblName(model.getBlbName());
        }

        log__.debug("End");
    }
    
    /**
     * <br>[機  能] 権限設定をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk060ParamModel
     * @param cntCon 採番コントロール
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setBmkLabel(Bmk060ParamModel paramMdl,
        MlCountMtController cntCon,
            int usrSid, Connection con) throws SQLException {

          boolean commitFlg = false;

          try {

              BmkLabelDao lblDao = new BmkLabelDao(con);
              BmkLabelModel lblMdl = createBmkLabelData(paramMdl, usrSid);

              BmkBelongLabelDao blgDao = new BmkBelongLabelDao(con);
              BmkBelongLabelModel blgMdl = createBmkBelongLabelData(paramMdl, usrSid);
              int blbSid = -1;

              if (paramMdl.getBmk050ProcMode() == GSConstBookmark.LABEL_MODE_TOUROKU) {
                  //SID採番
                  blbSid = (int) cntCon.getSaibanNumber(GSConstBookmark.SBNSID_BOOKMARK,
                      GSConstBookmark.SBNSID_SUB_LABEL, usrSid);

                  //処理モード：登録
                  lblMdl.setBlbSid(blbSid);
                  lblMdl.setBlbKbn(paramMdl.getBmk010mode());
                  lblMdl.setBlbName(paramMdl.getBmk060LblName());

                  if (paramMdl.getBmk010mode() == GSConstBookmark.BMK_KBN_KOJIN) {
                      //ブックマーク区分：個人ブックマーク
                      lblMdl.setUsrSid(usrSid);
                  } else if (paramMdl.getBmk010mode() == GSConstBookmark.BMK_KBN_GROUP) {
                      //ブックマーク区分：グループブックマーク
                      lblMdl.setGrpSid(paramMdl.getBmk010groupSid());
                  }
                  lblDao.insert(lblMdl);
              } else if (paramMdl.getBmk050ProcMode() == GSConstBookmark.LABEL_MODE_EDIT) {
                  blbSid = paramMdl.getBmk050LblSid();
                  //処理モード：編集モード
                  lblMdl.setBlbName(paramMdl.getBmk060LblName());
                  lblDao.update(lblMdl, blbSid);
              }

              if (paramMdl.getBmk060LblKbn() == GSConstBookmark.LABEL_TOGO_YES) {
                  //ラベル統合区分：統合する
                  blgMdl.setBlbSid(blbSid);

                  ArrayList<String> labelList = new ArrayList<String>();

                  for (int i = 0; i < paramMdl.getBmk060LabelList().length; i++) {
                      labelList.add(paramMdl.getBmk060LabelList()[i]);
                  }
                  labelList.add(String.valueOf(blbSid));

                  ArrayList<String> bmkSidList = blgDao.select(labelList.toArray(new String[0]));
                  //既存レコードの削除
                  blgDao.delete(labelList.toArray(new String[0]));
                  lblDao.delete(paramMdl.getBmk060LabelList());

                  //統合・追加・変更ラベルを使用していたブックマークを新規登録
                  blgDao.insert(blgMdl, bmkSidList.toArray(new String[0]));
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
    
    /**
     * <br>[機  能] ラベル情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk060ParamModel
     * @param usrSid ユーザSID
     * @return BmkGconfModel
     */
    public BmkLabelModel createBmkLabelData(
            Bmk060ParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        BmkLabelModel mdl = new BmkLabelModel();

        mdl.setUsrSid(-1);
        mdl.setGrpSid(-1);
        mdl.setBlbAuid(usrSid);
        mdl.setBlbAdate(nowDate);
        mdl.setBlbEuid(usrSid);
        mdl.setBlbEdate(nowDate);

        return mdl;
    }
    /**
     * <br>[機  能] ラベル付加情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk060ParamModel
     * @param usrSid ユーザSID
     * @return BmkGconfModel
     */
    public BmkBelongLabelModel createBmkBelongLabelData(
            Bmk060ParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        BmkBelongLabelModel mdl = new BmkBelongLabelModel();

        mdl.setBblAuid(usrSid);
        mdl.setBblAdate(nowDate);
        mdl.setBblEuid(usrSid);
        mdl.setBblEdate(nowDate);

        return mdl;
    }
}
