package jp.groupsession.v2.cht.cht150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.dao.ChtCategoryDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.model.ChtCategoryModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 *
 * <br>[機  能] チャット カテゴリ作成編集のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht150Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht150Biz.class);
    /** コネクション */
    private Connection con__ = null;


    /**コンストラクタ
     * @param con コネクション
     * */
    public Cht150Biz(Connection con) {
        con__ = con;
    }

    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行例外
     *
     * */
    public void setInitData(Cht150ParamModel paramMdl)
            throws SQLException {
        log__.debug("init");
        //編集の場合、データ取得
        if (paramMdl.getCht140ProcMode() == GSConstChat.CHAT_MODE_EDIT
                && paramMdl.getCht150InitFlg() == GSConstMain.DSP_FIRST) {
            ChtCategoryDao catDao = new ChtCategoryDao(con__);
            ChtCategoryModel mdl = catDao.select(paramMdl.getCht140CategoryLink());
            paramMdl.setCht150CategoryName(mdl.getChcName());
            List<ChtGroupInfModel> leftGrpList =
                    __getLeftGroupInfList(paramMdl.getCht140CategoryLink());
            String[] selectGroup = __getLeftGroupSid(leftGrpList);
            paramMdl.setCht150ChtGroupSid(selectGroup);
            paramMdl.setCht150InitFlg(GSConstMain.DSP_ALREADY);
        }
        setCategory(paramMdl);
    }


    /**
     * <br>[機  能] カテゴリ名を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param chcSid カテゴリSID
     * @throws SQLException 実行例外
     * @return カテゴリ名
     */
    public String getCategoryName(int chcSid)
                    throws SQLException {

        ChtCategoryDao catDao = new ChtCategoryDao(con__);
        ChtCategoryModel mdl = catDao.select(chcSid);
        return mdl.getChcName();
    }

    /**
     * <br>[機  能] 所属チャットグループを設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht150ParamModel
     * @throws SQLException 実行例外
     */
    public void setCategory(Cht150ParamModel paramMdl)
                    throws SQLException {

        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);

        // グループフォーム表示
        __setDspGroupForm(paramMdl, infDao);
    }

    /**
     * <br>[機  能] グループコンボ表示判定設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht150ParamModel
     * @param infDao ChtGroupInfDao
     * @throws SQLException SQL例外
     */
    private void __setDspGroupForm(Cht150ParamModel paramMdl, ChtGroupInfDao infDao)
                    throws SQLException {
        paramMdl.setCht150DspGroupForm(infDao.isExitGroup());
    }

    /**
     * <br>[機  能] 所属グループを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return メンバーSIDリスト
     * @param sid String
     * @throws SQLException SQL実行時例外
     */
    private List<ChtGroupInfModel> __getLeftGroupInfList(int sid)
                    throws SQLException {
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        List<ChtGroupInfModel> infList = infDao.selectGroupWhereChcSid(sid);
        return infList;
    }

    /**
     * <br>[機  能] 選択グループを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return メンバーSIDリスト
     * @param selectedList グループ
     */
    private String[] __getLeftGroupSid(
            List<ChtGroupInfModel> selectedList) {
        List<String> ret = new ArrayList<String>();

        for (ChtGroupInfModel mdl:selectedList) {
            ret.add(String.valueOf(mdl.getCgiSid()));
        }

        return ret.toArray(new String[ret.size()]);
    }

    /**
     * <br>[機  能] カテゴリを削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @throws Exception 例外
     * @return 削除件数
     */
    public int deleteCategory(Cht150ParamModel paramMdl, int usrSid) throws Exception {
        log__.debug("deleteCategory");
        UDate now = new UDate();
        int count = 0;
        ChtCategoryDao catDao = new ChtCategoryDao(con__);
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        infDao.updateCategoryNone(paramMdl.getCht140CategoryLink(), usrSid, now);
        count = catDao.delete(paramMdl.getCht140CategoryLink());
        return count;

    }
}
