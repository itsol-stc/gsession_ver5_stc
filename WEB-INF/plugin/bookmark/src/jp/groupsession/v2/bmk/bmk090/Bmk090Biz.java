package jp.groupsession.v2.bmk.bmk090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkGconfDao;
import jp.groupsession.v2.bmk.dao.BmkGroupEditDao;
import jp.groupsession.v2.bmk.model.BmkGconfModel;
import jp.groupsession.v2.bmk.model.BmkGroupEditModel;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;

/**
 * <br>[機  能] 権限設定(グループブックマーク)画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk090Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk090Biz.class);

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk090ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Bmk090ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getBmk090GrpName() == null) {
            //グループ名の設定
            GroupDao grpDao = new GroupDao(con);
                CmnGroupmModel grpMdl = grpDao.getGroup(paramMdl.getBmk010groupSid());
                paramMdl.setBmk090GrpName(
                        StringUtilHtml.transToHTmlPlusAmparsant(grpMdl.getGrpName()));
        }

        if (paramMdl.getBmk090initFlg() != 1) {
            BmkGconfDao dao = new BmkGconfDao(con);
            BmkGconfModel model = dao.selectGConf(paramMdl.getBmk010groupSid());

            int grpEditKbn = GSConstBookmark.EDIT_POW_ADMIN;

            if (model != null) {
                grpEditKbn = model.getBgcEdit();
            }

            if (paramMdl.getBmk090GrpEditKbn() == -1) {
                //グループブックマーク編集権限の初期化
                paramMdl.setBmk090GrpEditKbn(grpEditKbn);
            }

            if (paramMdl.getBmk090GrpEditKbn() == GSConstBookmark.EDIT_POW_USER) {
                //共有ブックマーク編集権限：ユーザ指定

                //デフォルトグループの設定
                GroupBiz grpBiz = new GroupBiz();
                paramMdl.setBmk090GroupSid(
                        grpBiz.getDefaultGroupSid(sessionUserSid, con));

                ArrayList<String> sidList = new ArrayList<String>();

                if (model != null && model.getBgcEdit() == GSConstBookmark.EDIT_POW_USER) {

                    //編集ユーザ取得
                    BmkGroupEditDao daoGrp = new BmkGroupEditDao(con);
                    List<BmkGroupEditModel> modelList = daoGrp.select(paramMdl.getBmk010groupSid());

                    if (modelList != null) {
                        for (BmkGroupEditModel addModel : modelList) {
                            sidList.add(Integer.toString(addModel.getBgeUsrSid()));
                        }
                    }
                }
                paramMdl.setBmk090UserSid(sidList.toArray(new String[0]));

            } else if (paramMdl.getBmk090GrpEditKbn() == GSConstBookmark.EDIT_POW_GROUP) {
                //共有ブックマーク編集権限：グループ指定

                //編集グループ取得
                ArrayList<String> sidList = new ArrayList<String>();

                if (model != null && model.getBgcEdit() == GSConstBookmark.EDIT_POW_GROUP) {

                    BmkGroupEditDao daoGrp = new BmkGroupEditDao(con);
                    List<BmkGroupEditModel> modelList = daoGrp.select(paramMdl.getBmk010groupSid());

                    if (modelList != null) {
                        for (BmkGroupEditModel addModel : modelList) {
                            sidList.add(Integer.toString(addModel.getBgeGrpSid()));
                        }
                    }
                }
                paramMdl.setBmk090GrpSid(sidList.toArray(new String[0]));
            }

            paramMdl.setBmk090initFlg(1);
        }
    }
    
    /**
     * <br>[機  能] 権限設定をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk090ParamModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setBmkGconf(Bmk090ParamModel paramMdl,
            int usrSid, Connection con) throws SQLException {

      boolean commitFlg = false;

      try {
          //DBの存在確認
          BmkGconfDao gConDao = new BmkGconfDao(con);
          BmkGconfModel gConModel = gConDao.selectGConf(paramMdl.getBmk010groupSid());
          //画面値セット
          BmkGconfModel gConMdl = createBmkGconfData(paramMdl, usrSid);

          if (gConModel == null) {
              gConDao.insert(gConMdl);
          } else {
              gConDao.update(gConMdl);
          }
          BmkGroupEditDao grpDao = new BmkGroupEditDao(con);
          //フィールド削除
          grpDao.delete(paramMdl.getBmk010groupSid());

          if (paramMdl.getBmk090GrpEditKbn() == GSConstBookmark.EDIT_POW_GROUP) {

              //グループブックマーク編集権限：グループ指定
              BmkGroupEditModel grpMdl = createBmkGroupEditData(paramMdl, usrSid);

              if (paramMdl.getBmk090GrpSid() != null) {

                  for (int i = 0; i < paramMdl.getBmk090GrpSid().length; i++) {
                      grpMdl.setBgeGrpSid(Integer.parseInt(paramMdl.getBmk090GrpSid()[i]));
                      grpDao.insert(grpMdl);
                  }
              }
          } else if (paramMdl.getBmk090GrpEditKbn() == GSConstBookmark.EDIT_POW_USER) {

              //グループブックマーク編集権限：ユーザ指定
              BmkGroupEditModel grpMdl = createBmkGroupEditData(paramMdl, usrSid);

              if (paramMdl.getBmk090UserSid() != null) {

                  for (int i = 0; i < paramMdl.getBmk090UserSid().length; i++) {
                      grpMdl.setBgeUsrSid(Integer.parseInt(paramMdl.getBmk090UserSid()[i]));
                      grpDao.insert(grpMdl);
                  }
              }
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
     * <br>[機  能] 管理者設定情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk090ParamModel
     * @param usrSid ユーザSID
     * @return BmkGconfModel
     */
    public BmkGconfModel createBmkGconfData(
            Bmk090ParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        BmkGconfModel mdl = new BmkGconfModel();

        mdl.setGrpSid(paramMdl.getBmk010groupSid());
        mdl.setBgcEdit(paramMdl.getBmk090GrpEditKbn());
        mdl.setBgcAuid(usrSid);
        mdl.setBgcAdate(nowDate);
        mdl.setBgcEuid(usrSid);
        mdl.setBgcEdate(nowDate);

        return mdl;
    }
    /**
     * <br>[機  能] グループブックマーク編集権限情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk090ParamModel
     * @param usrSid ユーザSID
     * @return BmkGroupEditModel
     */
    public BmkGroupEditModel createBmkGroupEditData(
            Bmk090ParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        BmkGroupEditModel mdl = new BmkGroupEditModel();

        mdl.setGrpSid(paramMdl.getBmk010groupSid());
        mdl.setBgeGrpSid(-1);
        mdl.setBgeUsrSid(-1);
        mdl.setBgeAuid(usrSid);
        mdl.setBgeAdate(nowDate);
        mdl.setBgeEuid(usrSid);
        mdl.setBgeEdate(nowDate);

        return mdl;
    }
}
