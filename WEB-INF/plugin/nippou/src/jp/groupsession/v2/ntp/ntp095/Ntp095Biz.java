package jp.groupsession.v2.ntp.ntp095;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.dao.NtpPriConfDao;
import jp.groupsession.v2.ntp.dao.NtpSmlMemberDao;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.model.NtpSmlMemberModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 ショートメール通知設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp095Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp095Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Ntp095Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp095ParamModel
     * @param umodel ユーザ基本情報モデル
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Ntp095ParamModel paramMdl,
            BaseUserModel umodel) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con__, umodel.getUsrsid());
        NtpAdmConfModel admConf = biz.getAdmConfModel(con__);
        NippouDao ntpDao = new NippouDao(con__);
        int sessionUsrSid = umodel.getUsrsid();

        //画面表示区分を設定
        if (admConf.getNacSmlKbn() == GSConstNippou.SML_NOTICE_USR) {
            paramMdl.setNtp095NtpDspKbn(1);
        }
        if (admConf.getNacCsmlKbn() == GSConstNippou.SML_NOTICE_USR) {
            paramMdl.setNtp095CmtDspKbn(1);
        }
        if (admConf.getNacGsmlKbn() == GSConstNippou.SML_NOTICE_USR) {
            paramMdl.setNtp095GoodDspKbn(1);
        }

        if (paramMdl.getNtp095InitFlg().equals(String.valueOf(GSConstSchedule.INIT_FLG))) {
            //ショートメール通知メンバーを取得
            NtpSmlMemberDao nsmDao = new NtpSmlMemberDao(con__);
            ArrayList<NtpSmlMemberModel> nsmList = null;
            //閲覧不可のグループはメンバーから削除
            List<Integer> notAccessList = ntpDao.getNotAccessGrpList(sessionUsrSid);
            for (int grpSid : notAccessList) {
                nsmDao.deleteForGroup(grpSid);
            }
            nsmList = nsmDao.select(sessionUsrSid);

            if (!nsmList.isEmpty()) {
                ArrayList<String> memList = new ArrayList<String>();
                for (NtpSmlMemberModel nsmMdl : nsmList) {

                    if (nsmMdl.getGrpSid() != -1 && nsmMdl.getNsmGrpKbn() == 0) {
                        memList.add("G" + String.valueOf(nsmMdl.getGrpSid()));
                    } else if (nsmMdl.getUsrSid() != -1) {
                        memList.add(String.valueOf(nsmMdl.getUsrSid()));
                    }

                }
                paramMdl.setNtp095memberSid(memList.toArray(new String[memList.size()]));
            }
        }


        //共有区分を設定
        paramMdl.setNtp095KyoyuKbn(admConf.getNacCrange());

        //日報通知
        paramMdl.setNtp095Smail(
                NullDefault.getString(
                        paramMdl.getNtp095Smail(), String.valueOf(pconf.getNprSmail())));
        //コメント通知
        paramMdl.setNtp095CmtSmail(
                NullDefault.getString(
                        paramMdl.getNtp095CmtSmail(), String.valueOf(pconf.getNprCmtSmail())));

        //いいね通知
        paramMdl.setNtp095GoodSmail(
                NullDefault.getString(
                        paramMdl.getNtp095GoodSmail(), String.valueOf(pconf.getNprGoodSmail())));


        paramMdl.setNtp095InitFlg(String.valueOf(GSConstNippou.NOT_INIT_FLG));
    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp095ParamModel
     * @param umodel ユーザ基本情報モデル
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Ntp095ParamModel paramMdl,
            BaseUserModel umodel) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con__, umodel.getUsrsid());

        pconf.setNprSmail(
                NullDefault.getInt(paramMdl.getNtp095Smail(), GSConstNippou.SMAIL_USE));
        pconf.setNprCmtSmail(
                NullDefault.getInt(paramMdl.getNtp095CmtSmail(), GSConstNippou.SMAIL_USE));
        pconf.setNprGoodSmail(
                NullDefault.getInt(paramMdl.getNtp095GoodSmail(), GSConstNippou.SMAIL_USE));
        pconf.setNprEuid(umodel.getUsrsid());
        pconf.setNprEdate(new UDate());

        //ショートメール通知グループ、ユーザ登録
        ArrayList<NtpSmlMemberModel> nsmMdlList = new ArrayList<NtpSmlMemberModel>();
        if (paramMdl.getNtp095NtpDspKbn() == 1) {
            nsmMdlList
            = __getNtpSmlMemMdl(paramMdl.getNtp095memberSid(), con__, umodel.getUsrsid());
        }

        boolean commitFlg = false;
        try {
            //個人設定
            NtpPriConfDao dao = new NtpPriConfDao(con__);
            int count = dao.updateSmail(pconf);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(pconf);
            }

            //ショートメール通知メンバー設定
            NtpSmlMemberDao nsmDao = new NtpSmlMemberDao(con__);
            //閲覧不可のユーザを取得
            NippouDao ntpDao = new NippouDao(con__);
            List<Integer> notAccessList = ntpDao.getNotAccessUserList(umodel.getUsrsid());

            if (paramMdl.getNtp095NtpDspKbn() == 1) {
                nsmDao.delete(umodel.getUsrsid());
                if (paramMdl.getNtp095Smail().equals(String.valueOf(GSConstNippou.SMAIL_USE))) {
                    for (NtpSmlMemberModel nsmMdl : nsmMdlList) {
                        if (!notAccessList.contains(nsmMdl.getUsrSid())) {
                            //閲覧不可ユーザ以外をDBへ登録
                            nsmDao.insert(nsmMdl);
                        }
                    }
                }
            }

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            }
        }
    }

    /**
     * <br>[機  能] メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param left グループ ユーザ SID
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @return メンバー一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<NtpSmlMemberModel> __getNtpSmlMemMdl(
            String[] left, Connection con, int sessionUsrSid)
            throws SQLException {

        ArrayList<NtpSmlMemberModel> ret = new ArrayList<NtpSmlMemberModel>();
        ArrayList<String> mygrpSids = new ArrayList<String>();
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (left != null) {
            for (int i = 0; i < left.length; i++) {
                String str = NullDefault.getString(left[i], "-1");

                if (NtpCommonBiz.isMyGroupSid(str)) {
                    mygrpSids.add(str.substring(1, str.length()));
                } else if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(Integer.valueOf(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }

        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        for (GroupModel gmodel : glist) {
            NtpSmlMemberModel smlMemMdl = new NtpSmlMemberModel();
            smlMemMdl.setNsmUsrSid(sessionUsrSid);
            smlMemMdl.setUsrSid(-1);
            smlMemMdl.setGrpSid(gmodel.getGroupSid());
            smlMemMdl.setNsmGrpKbn(0);
            ret.add(smlMemMdl);
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (BaseUserModel umodel : ulist) {
            NtpSmlMemberModel smlMemMdl = new NtpSmlMemberModel();
            smlMemMdl.setNsmUsrSid(sessionUsrSid);
            smlMemMdl.setUsrSid(umodel.getUsrsid());
            smlMemMdl.setGrpSid(-1);
            smlMemMdl.setNsmGrpKbn(0);
            ret.add(smlMemMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] グループ追加チェック
     * <br>[解  説] 閲覧権限のないグループを通知対象に追加しようとしている場合、エラーを返す
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param addSelectSid 追加側のコンボで選択中の値
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors addGroupCheck(int sessionUsrSid, String[] addSelectSid)
            throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        //閲覧不可グループを追加しようとしていないか確認
        NippouDao ntpDao = new NippouDao(con__);
        List<Integer> notAccessGrpList = ntpDao.getNotAccessGrpList(sessionUsrSid);
        for (String addSid : addSelectSid) {
            addSid = addSid.replaceAll("[^0-9]", "");
            if (notAccessGrpList.contains(Integer.parseInt(addSid))) {
                String errorMessage = gsMsg.getMessage("ntp.179");
                ActionMessage msg = new ActionMessage("errors.free.msg", errorMessage);

                StrutsUtil.addMessage(errors, msg, "error.add.failed.ntp095");
            }
        }

        return errors;
    }
}