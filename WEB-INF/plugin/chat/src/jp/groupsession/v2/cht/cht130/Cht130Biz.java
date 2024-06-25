package jp.groupsession.v2.cht.cht130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtPriConfDao;
import jp.groupsession.v2.cht.model.ChatGroupInfModel;
import jp.groupsession.v2.cht.model.ChtPriConfModel;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャット  表示設定のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht130Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht130Biz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * */
    public Cht130Biz(Connection con, RequestModel reqMdl) {
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
    public void setInitData(Cht130ParamModel paramMdl, int usrSid)
            throws SQLException {
        log__.debug("init");
        int initFlg = paramMdl.getCht130InitFlg();
        if (initFlg == GSConstChat.DSP_FIRST) {
            ChtBiz chtBiz = new ChtBiz(con__);
            ChtPriConfModel confMdl = chtBiz.getChtUconf(usrSid);
            if (__chkDelete(confMdl, usrSid)) {
                paramMdl.setCht130DefFlg(confMdl.getCpcDefFlg());
                paramMdl.setCht130SelectSid(confMdl.getCpcSelSid());
            } else {
                paramMdl.setCht130DefFlg(GSConstChat.CHAT_DSP_USER);
                paramMdl.setCht130SelectSid(usrSid);
            }
            paramMdl.setCht130InitFlg(GSConstChat.DSP_ALREADY);
        }
        switch (paramMdl.getCht130DefFlg()) {
            case GSConstChat.CHAT_DSP_USER:
                __getGroupUserLabel(paramMdl, initFlg);
                break;
            case GSConstChat.CHAT_DSP_CHATGROUP:
                __getChatGroupLabel(paramMdl, usrSid, initFlg);
                break;
            default:

                break;
        }
    }

    /**
     * <br>[機  能] グループコンボ、ユーザコンボを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht130ParamModel
     * @param initFlg 初期フラグ
     * @throws SQLException SQL例外
     */
    private void __getGroupUserLabel(Cht130ParamModel paramMdl, int initFlg) throws SQLException {
        GroupBiz gBiz = new GroupBiz();
        UserBiz uBiz = new UserBiz();
        if (initFlg == GSConstChat.DSP_FIRST) {
            int selectSid = paramMdl.getCht130SelectSid();
            paramMdl.setCht130UsrGroup(gBiz.getDefaultGroupSid(selectSid, con__));
            paramMdl.setCht130User(paramMdl.getCht130SelectSid());
        }
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setCht130GroupList(gBiz.getGroupCombLabelList(con__, true, gsMsg));
        paramMdl.setCht130UserList(
                uBiz.getNormalUserLabelList(con__,
                        paramMdl.getCht130UsrGroup(), null, true, gsMsg));
    }

    /**
     * <br>[機  能] チャットグループコンボを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cht130ParamModel
     * @param usrSid ユーザSID
     * @param initFlg  初期フラグ
     * @throws SQLException SQL例外
     */
    private void __getChatGroupLabel(Cht130ParamModel paramMdl,
            int usrSid, int initFlg) throws SQLException {
        ChtGroupInfDao dao = new ChtGroupInfDao(con__);
        List<ChatGroupInfModel> groupList = dao.selectGroup(usrSid);
        List<LabelValueBean> groupNameList = new ArrayList<LabelValueBean>();
        if (initFlg == GSConstChat.DSP_FIRST) {
            int selectSid = paramMdl.getCht130SelectSid();
            paramMdl.setCht130GrpGroup(selectSid);
        }
        GsMessage gsMsg = new GsMessage();
        String textSelect = gsMsg.getMessage("cmn.select.plz");
        groupNameList.add(new LabelValueBean(textSelect, String.valueOf(-1)));
        if (!groupList.isEmpty()) {
            for (ChatGroupInfModel mdl: groupList) {
                LabelValueBean bean = new LabelValueBean();
                bean.setLabel(mdl.getCgiName());
                bean.setValue(String.valueOf(mdl.getCgiSid()));
                groupNameList.add(bean);
            }
        }
        paramMdl.setCht130ChtGroupList(groupNameList);
    }


    /**
     * <br>[機  能]表示設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void updateChatUserConf(Cht130ParamModel paramMdl, int userSid)
    throws Exception {
        log__.debug("START");
        ChtPriConfModel confMdl = __setConfMdl(paramMdl, userSid);
        ChtPriConfDao confDao = new ChtPriConfDao(con__);
        if (confDao.updateDefaultDsp(confMdl) == 0) {
            confDao.insert(confMdl);
        }
        log__.debug("End");
    }

    /**
     * <br>[機  能]表示設定を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     * @return ChtPriConfModel
     */
    public ChtPriConfModel __setConfMdl(Cht130ParamModel paramMdl, int userSid)
    throws Exception {
        ChtPriConfModel confMdl = new ChtPriConfModel();
        confMdl.initData(userSid);
        UDate now = new UDate();
        confMdl.setCpcDefFlg(paramMdl.getCht130DefFlg());
        confMdl.setCpcChatKbn(paramMdl.getCht130SelectKbn());
        confMdl.setCpcSelSid(paramMdl.getCht130SelectSid());
        confMdl.setCpcAuid(userSid);
        confMdl.setCpcAdate(now);
        confMdl.setCpcEuid(userSid);
        confMdl.setCpcEdate(now);
        return confMdl;
    }

    /**
     * <br>[機  能]ユーザorチャットグループが削除されていないか確認
     * <br>[解  説]
     * <br>[備  考]
     * @param confMdl 個人設定情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return true 存在する false 存在しない
     */
    private boolean __chkDelete(ChtPriConfModel confMdl, int userSid)
    throws SQLException {

        // ユーザ存在判定
        if (confMdl.getCpcDefFlg() == GSConstChat.CHAT_DSP_USER) {

            CmnUsrmDao cuDao = new CmnUsrmDao(con__);
            if (!cuDao.isExistUser(confMdl.getCpcSelSid())) {
                return false;
            }
        }

        // チャットグループ存在判定
        if (confMdl.getCpcDefFlg() == GSConstChat.CHAT_DSP_CHATGROUP) {
            ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
            if (!infDao.isExitGroup(confMdl.getCpcSelSid())) {
                return false;
            }
        }
        return true;
    }
}
