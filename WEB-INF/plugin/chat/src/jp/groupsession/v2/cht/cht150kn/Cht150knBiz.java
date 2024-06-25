package jp.groupsession.v2.cht.cht150kn;

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
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 *
 * <br>[機  能]  カテゴリ作成編集確認のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht150knBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht150knBiz.class);
    /** コネクション */
    private Connection con__ = null;



    /**コンストラクタ
     * @param con コネクション
     * */
    public Cht150knBiz(Connection con) {
        con__ = con;
    }

    /**
     * 表示処理
     * @param paramMdl Cht150knParamModel
     * @throws SQLException SQL実行例外
     *
     * */
    public void setInitData(Cht150knParamModel paramMdl)
            throws SQLException {
        log__.debug("init");
        List<String> groupList = getGroupList(paramMdl.getCht150ChtGroupSid());
        paramMdl.setCht150knGrpNameList(groupList);
    }



    /**
     * <br>[機  能] 登録処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @throws Exception 例外
     */
    public void insert(Cht150knParamModel paramMdl,
                       int usrSid,
                       MlCountMtController cntCon)
                       throws Exception {
        log__.debug("insert");
        UDate now = new UDate();
        // カテゴリの登録
        ChtCategoryDao catDao = new  ChtCategoryDao(con__);
        ChtCategoryModel insertCatMdl = __createCategoryMdl(paramMdl, usrSid, cntCon, now);
        catDao.insert(insertCatMdl);
        String[] sidList = paramMdl.getCht150ChtGroupSid();
        if (sidList == null || sidList.length < 1) {
            return;
        }
        // グループユーザ情報カテゴリ変更
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        ChtGroupInfModel  updateInfMdl
                = __createGroupInfMdl(insertCatMdl.getChcSid(), usrSid, now);
        infDao.updateCategory(updateInfMdl, sidList);
    }

    /**
     * <br>[機  能] 編集処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @throws Exception 例外
     */
    public void update(Cht150knParamModel paramMdl,
                       int usrSid,
                       MlCountMtController cntCon)
                       throws Exception {
        log__.debug("update");
        // カテゴリの編集
        UDate now = new UDate();
        ChtCategoryDao catDao = new  ChtCategoryDao(con__);
        ChtCategoryModel updateCatMdl = __createCategoryMdl(paramMdl, usrSid, cntCon, now);
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        infDao.updateCategoryNone(paramMdl.getCht140CategoryLink(), usrSid, now);
        catDao.update(updateCatMdl);
        String[] sidList = paramMdl.getCht150ChtGroupSid();
        if (sidList == null || sidList.length < 1) {
            return;
        }
        // グループユーザ情報カテゴリ変更
        ChtGroupInfModel  updateInfMdl
                = __createGroupInfMdl(paramMdl.getCht140CategoryLink(), usrSid, now);
        infDao.updateCategory(updateInfMdl, sidList);
    }


    /**
     * <br>[機  能] 登録・更新用のカテゴリmodel作成を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @param now 日時
     * @return RngTemplateCategoryModel 登録model
     * @throws SQLException SQL例外
     */
    private ChtCategoryModel __createCategoryMdl(Cht150knParamModel paramMdl,
                                                int usrSid,
                                                MlCountMtController cntCon,
                                                UDate now) throws SQLException {
        ChtCategoryModel model = new ChtCategoryModel();
        //登録
        if (paramMdl.getCht140ProcMode() == GSConstChat.CHAT_MODE_ADD) {
            //グループSID採番
            int chcSid = (int) cntCon.getSaibanNumber(GSConstChat.SBNSID_CHAT,
                                                       GSConstChat.SBNSID_SUB_CHAT_CATEGORY,
                                                       usrSid);
            model.setChcSid(chcSid);
            model.setChcSort(chcSid);
        //更新
        } else  {
            model.setChcSid(paramMdl.getCht140CategoryLink());
            model.setChcSort(paramMdl.getCht140CategoryLink());
        }
        model.setChcName(paramMdl.getCht150CategoryName());
        model.setChcAdate(now);
        model.setChcEdate(now);
        model.setChcAuid(usrSid);
        model.setChcEuid(usrSid);
        return model;
    }

    /**
     * <br>[機  能] 登録・更新用のカテゴリmodel作成を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param chcSid カテゴリSID
     * @param usrSid ユーザSID
     * @param now 日時
     * @return RngTemplateCategoryModel 登録model
     */
    private ChtGroupInfModel __createGroupInfMdl(int chcSid,
                                                int usrSid,
                                                UDate now) {
        ChtGroupInfModel model = new ChtGroupInfModel();
        model.setChcSid(chcSid);
        model.setCgiEdate(now);
        model.setCgiEuid(usrSid);
        return model;
    }


    /**
     * <br>[機  能] グループ名一覧を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param sid 所属グループSID一覧
     * @return グループ名一覧
     * @throws SQLException SQL例外
     */
    public List<String>  getGroupList(String[] sid) throws SQLException {
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        List<String> ret = new ArrayList<String>();
        if (sid == null || sid.length == 0) {
            return ret;
        }
        List<ChtGroupInfModel> infList = infDao.selectGroupWhereCgiSid(sid);

        for (ChtGroupInfModel mdl: infList) {
            ret.add(mdl.getCgiName());
        }
        return ret;
    }
}
