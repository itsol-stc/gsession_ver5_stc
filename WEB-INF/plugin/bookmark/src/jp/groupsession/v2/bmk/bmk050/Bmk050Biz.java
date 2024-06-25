package jp.groupsession.v2.bmk.bmk050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkLabelDao;
import jp.groupsession.v2.bmk.dao.BmkLabelDataDao;
import jp.groupsession.v2.bmk.model.BmkLabelDataModel;
import jp.groupsession.v2.bmk.model.BmkLabelModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;

/**
 * <br>[機  能] ラベル管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk050Biz {

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk050ParamModel
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Bmk050ParamModel paramMdl,
            int userSid,
            Connection con) throws SQLException {

        //指定ラベル情報取得
        BmkLabelDataDao dao = new BmkLabelDataDao(con);
        ArrayList<BmkLabelDataModel> lblMdl
            = dao.select(paramMdl.getBmk010mode(), userSid, paramMdl.getBmk010groupSid());

        paramMdl.setBmk050LabelList(lblMdl);

        if (paramMdl.getBmk010mode() == GSConstBookmark.BMK_KBN_GROUP) {
            //グループブックマーク
            //グループ名の設定
            GroupDao grpDao = new GroupDao(con);
            CmnGroupmModel grpMdl = grpDao.getGroup(paramMdl.getBmk010groupSid());
            paramMdl.setBmk050GrpName(
                    StringUtilHtml.transToHTmlPlusAmparsant(grpMdl.getGrpName()));
        }
    }

    /**
     * <br>[機  能] 指定ラベルSIDのリスト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk050ParamModel
     * @param userSid セッションユーザSID
     * @param targetList ターゲットラベルSIDリスト
     * @param con コネクション
     * @return ターゲットリスト
     * @throws SQLException SQL実行エラー
     */
    public ArrayList<BmkLabelModel>  getTargetLabelList(Bmk050ParamModel paramMdl,
        int userSid,
        String[] targetList,
        Connection con) throws SQLException {

        BmkLabelDao dao = new BmkLabelDao(con);
        ArrayList<BmkLabelModel>  lblMdl = dao.select(targetList);
        return lblMdl;
    }
}
