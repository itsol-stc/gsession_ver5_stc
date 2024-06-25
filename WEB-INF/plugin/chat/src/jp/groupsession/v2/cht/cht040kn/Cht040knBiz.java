package jp.groupsession.v2.cht.cht040kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.dao.ChtAdmConfDao;
import jp.groupsession.v2.cht.dao.ChtGroupTargetDao;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cht.model.ChtGroupTargetModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャット 基本設定確認のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht040knBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht040knBiz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * */
    public Cht040knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * 初期表示処理
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行例外
     *
     * */
    public void setInitData(Cht040knParamModel paramMdl)
            throws SQLException {
        log__.debug("init");
        // 制限対象項目
        GsMessage gsmsg = new GsMessage(reqMdl__);
        UserGroupSelectModel ugsTargetModel = paramMdl.getCht040knTargetUser();
        ugsTargetModel.setSelected(GSConstChat.GROUP_USE_LIMIT, paramMdl.getCht040TargetUserList());
        ugsTargetModel.init(con__, reqMdl__,
                new String[] {GSConstChat.GROUP_USE_LIMIT},
                new String[] {gsmsg.getMessage("main.can.use.user"), },
                "", null, null);
    }


    /**
     * <br>[機  能] 設定値をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException 実行例外
     */
    public void entryAdmConf(
            Cht040knParamModel paramMdl,
            int userSid) throws SQLException {
        UDate now = new UDate();
        ChtAdmConfModel aconfMdl = new ChtAdmConfModel();
        aconfMdl.initData(userSid, now);
        aconfMdl.setCacChatFlg(paramMdl.getCht040BetweenUsers());
        aconfMdl.setCacGroupFlg(paramMdl.getCht040CreateGroup());
        aconfMdl.setCacGroupKbn(paramMdl.getCht040HowToLimit());
        aconfMdl.setCacReadFlg(paramMdl.getCht040AlreadyRead());
        ChtAdmConfDao aconfDao = new ChtAdmConfDao(con__);
        if (aconfDao.update(aconfMdl) <= 0) {
            aconfDao.insert(aconfMdl);
        }
    }


    /**
     * <br>[機  能] 制限対象を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException 実行例外
     */
    public void entryTargetConf(
            Cht040knParamModel paramMdl) throws SQLException {
        // 削除して追加
        ChtGroupTargetDao dao = new ChtGroupTargetDao(con__);
        dao.delete();
        ArrayList<ChtGroupTargetModel> useMdlList = __createTargetUseMdl(paramMdl);
        if (paramMdl.getCht040CreateGroup() == GSConstChat.LIMIT_CREATE_GROUP
                && useMdlList != null) {
                for (ChtGroupTargetModel useMdl:useMdlList) {
                    dao.insert(useMdl);
                }
        }
    }

    /**
    *
    * <br>[機  能]制限対象リストを作成する
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
    *@return 制限対象
    */
   private ArrayList<ChtGroupTargetModel> __createTargetUseMdl(
           Cht040knParamModel paramMdl) {
       //使用制限対象ユーザ・グループのSIDを取得
       String[] useList = paramMdl.getCht040TargetUserList();
       if (useList == null) {
           return null;
       }
       ArrayList<ChtGroupTargetModel> useMdlList
       = new ArrayList<ChtGroupTargetModel>();
       for (String useSidStr:useList) {
           ChtGroupTargetModel useMdl =  new ChtGroupTargetModel();

           if (useSidStr.startsWith("G")) {
               int useSid = NullDefault.getInt(useSidStr.substring(1), -1);
               useMdl.setCgtType(GSConstChat.LIMIT_TARGETTYPE_GRP);
               useMdl.setCgtSsid(useSid);
           } else {
               int useSid = NullDefault.getInt(useSidStr, -1);
               useMdl.setCgtType(GSConstChat.LIMIT_TARGET_TYPE_USR);
               useMdl.setCgtSsid(useSid);
           }
           useMdlList.add(useMdl);

       }
       return useMdlList;
   }
}
