package jp.groupsession.v2.cht.cht110kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.biz.ChtMemberBiz;
import jp.groupsession.v2.cht.dao.ChatDao;
import jp.groupsession.v2.cht.dao.ChtCategoryDao;
import jp.groupsession.v2.cht.dao.ChtGroupDataSumDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtGroupUserDao;
import jp.groupsession.v2.cht.model.ChatGroupComboModel;
import jp.groupsession.v2.cht.model.ChtGroupDataSumModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cht.model.ChtGroupUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 *
 * <br>[機  能] チャットグループ追加/編集のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht110knBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht110knBiz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * */
    public Cht110knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     *
     * */
    public void setInitData(Cht110knParamModel paramMdl, int usrSid)
            throws SQLException {
        log__.debug("init");
        // カテゴリ名(表示用)取得
        ChtCategoryDao cateDao = new ChtCategoryDao(con__);
        String categoryName = cateDao.select(paramMdl.getCht110category()).getChcName();
        paramMdl.setCht110knCategoryName(categoryName);
        //備考(表示用)を設定
        paramMdl.setCht110knBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getCht110biko(), "")));

        //メンバー(表示用)取得
        ChtMemberBiz memberBiz = new ChtMemberBiz(con__);
        ChatGroupComboModel targetList = new ChatGroupComboModel();
        targetList.setMemberAdminSid(paramMdl.getCht110memberAdmin());
        targetList.setMemberGeneralSid(paramMdl.getCht110memberNormal());
        memberBiz.setMember(reqMdl__, targetList, false, usrSid);
        paramMdl.setCht110knAdminMemberList(targetList.getLeftUserAdminList());
        paramMdl.setCht110knGeneralMemberList(targetList.getLeftUserGeneralList());

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
    public void insert(Cht110knParamModel paramMdl,
                       int usrSid,
                       MlCountMtController cntCon)
                       throws Exception {
        log__.debug("insert");
        // グループ情報の登録
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        ChtGroupInfModel insertinfMdl = createGroupInfMdl(paramMdl, usrSid, cntCon);
        infDao.insert(insertinfMdl);

        ChtGroupDataSumDao cgsDao = new ChtGroupDataSumDao(con__);
        ChtGroupDataSumModel cgsModel = new ChtGroupDataSumModel();
        cgsModel.setCgiSid(insertinfMdl.getCgiSid());
        cgsDao.insert(cgsModel);


        // グループユーザ情報の登録
        ChtGroupUserDao userDao = new ChtGroupUserDao(con__);
        List<ChtGroupUserModel> memberList = __createUseMdlList(paramMdl, usrSid);
        for (ChtGroupUserModel model:memberList) {
            userDao.insert(model);
        }
    }

    /**
     * <br>[機  能] 編集処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @return true:成功 false:失敗
     * @throws Exception 例外
     */
    public boolean update(Cht110knParamModel paramMdl,
                       int usrSid,
                       MlCountMtController cntCon)
                       throws Exception {
        log__.debug("insert");
        // グループ情報の登録
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        ChtGroupInfModel insertinfMdl = createGroupInfMdl(paramMdl, usrSid, cntCon);
        int count = infDao.updateGrp(insertinfMdl);
        if (count == 0) {
            return false;
        }
        // グループユーザ情報の登録
        ChtGroupUserDao userDao = new ChtGroupUserDao(con__);
         //削除して登録
        userDao.delete(paramMdl.getCgiSid());
        List<ChtGroupUserModel> memberList = __createUseMdlList(paramMdl, usrSid);
        List<Integer> usrList = new ArrayList<Integer>();
        List<Integer> grpList = new ArrayList<Integer>();
        for (ChtGroupUserModel model:memberList) {
            userDao.insert(model);
            if (model.getCguKbn() == GSConstChat.CHAT_KBN_USER) {
                usrList.add(model.getCguSelectSid());
            } else {
                grpList.add(model.getCguSelectSid());
            }
        }
        //閲覧情報の削除
        int cgiSid = paramMdl.getCgiSid();
        if (grpList.size() > 0) {
            ChatDao cDao = new ChatDao(con__);
            List<Integer> grpUsrList = cDao.getUsrSid(grpList);
            usrList.addAll(grpUsrList);
        }
        ChtBiz cBiz = new ChtBiz(con__);
        cBiz.groupViewDataDelete(cgiSid, usrList);


        return true;
    }



    /**
     * <br>[機  能] 登録・更新用のmodel作成を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @return RngTemplateCategoryModel 登録model
     * @throws Exception 例外
     */
    public ChtGroupInfModel createGroupInfMdl(Cht110knParamModel paramMdl,
                                                int usrSid,
                                                MlCountMtController cntCon)
                                                throws Exception {

        UDate date = new UDate();
        ChtGroupInfModel model = new ChtGroupInfModel();
        //登録
        if (paramMdl.getCht100ProcMode() == GSConstChat.CHAT_MODE_ADD) {
            //グループSID採番
            int cgiSid = (int) cntCon.getSaibanNumber(GSConstChat.SBNSID_CHAT,
                                                       GSConstChat.SBNSID_SUB_CHAT_GROUP,
                                                       usrSid);
            model.setCgiSid(cgiSid);
            model.setCgiCompFlg(GSConstChat.CHAT_ARCHIVE_NOT_MODE);
            paramMdl.setCgiSid(cgiSid);
        //更新
        } else  {
            model.setCgiSid(paramMdl.getCgiSid());
            model.setCgiCompFlg(paramMdl.getCht110compFlg());
        }
        model.setCgiId(paramMdl.getCht110groupId());
        model.setCgiName(paramMdl.getCht110groupName());
        model.setChcSid(paramMdl.getCht110category());
        model.setCgiContent(paramMdl.getCht110biko());
        model.setCgiAdate(date);
        model.setCgiEdate(date);
        model.setCgiAuid(usrSid);
        model.setCgiEuid(usrSid);
        return model;
    }


    /**
    *
    * <br>[機  能]メンバーリストを作成する
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
     * @param usrSid ユーザSID
    * @return ChtGroupUserModel
    */
   private ArrayList<ChtGroupUserModel> __createUseMdlList(
           Cht110knParamModel paramMdl,
           int usrSid) {
       log__.debug("__createUseMdl");
       UDate date = new UDate();
       //使用制限対象ユーザ・グループのSIDを取得
       ArrayList<ChtGroupUserModel> useMdlList = new ArrayList<ChtGroupUserModel>();
       // 管理者
       String[] adminList = paramMdl.getCht110memberAdmin();
       for (String admin:adminList) {
           ChtGroupUserModel mdl = new ChtGroupUserModel();
           if (admin.startsWith("G")) {
               int sid = NullDefault.getInt(admin.substring(1), -1);
               mdl.setCguKbn(GSConstChat.CHAT_KBN_GROUP);
               mdl.setCguSelectSid(sid);
           } else {
               int sid = NullDefault.getInt(admin, -1);
               mdl.setCguKbn(GSConstChat.CHAT_KBN_USER);
               mdl.setCguSelectSid(sid);
           }
           mdl.setCgiSid(paramMdl.getCgiSid());
           mdl.setCguAdminFlg(1);
           mdl.setCguAdate(date);
           mdl.setCguEdate(date);
           mdl.setCguAuid(usrSid);
           mdl.setCguEuid(usrSid);
           useMdlList.add(mdl);
       }
       // 一般
       String[] generalList = paramMdl.getCht110memberNormal();
       for (String general:generalList) {
           ChtGroupUserModel mdl = new ChtGroupUserModel();
           if (general.startsWith("G")) {
               int sid = NullDefault.getInt(general.substring(1), -1);
               mdl.setCguKbn(GSConstChat.CHAT_KBN_GROUP);
               mdl.setCguSelectSid(sid);
           } else {
               int sid = NullDefault.getInt(general, -1);
               mdl.setCguKbn(GSConstChat.CHAT_KBN_USER);
               mdl.setCguSelectSid(sid);
           }
           mdl.setCgiSid(paramMdl.getCgiSid());
           mdl.setCguAdminFlg(0);
           mdl.setCguAdate(date);
           mdl.setCguEdate(date);
           mdl.setCguAuid(usrSid);
           mdl.setCguEuid(usrSid);
           useMdlList.add(mdl);
       }

       return useMdlList;
   }


}
