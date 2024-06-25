package jp.groupsession.v2.tcd.tcd130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.tcd030.Tcd030Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] タイムカード 管理者設定ユーザ別時間帯設定一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd130Form extends Tcd030Form {

    /**  グループSid */
    private int tcd130GroupSid__ = -1;
    /**  ユーザSid */
    private int tcd130UserSid__ = -1;
    /**  時間帯Sid */
    private int tcd130TimezoneSid__ = -1;
    /**  デフォルト時間帯Sid */
    private int tcd130DefaultTimezoneSid__ = -1;

    /**  グループSid(検索条件保持用) */
    private int tcd130SvGroupSid__ = -1;
    /**  ユーザSid (検索条件保持用)*/
    private int tcd130SvUserSid__ = -1;
    /**  時間帯Sid(検索条件保持用) */
    private int tcd130SvTimezoneSid__ = -1;
    /**  デフォルト時間帯Sid(検索条件保持用) */
    private int tcd130SvDefaultTimezoneSid__ = -1;

    /**  検索Flg */
    private int tcd130SearchFlg__ = 0;

    /** 検索結果リスト */
    private List<Tcd130DispModel> tcd130SearchList__;
    /** 時間帯リスト */
    private List<LabelValueBean> tcd130TimezoneNameList__;
    /** グループリスト */
    private List<LabelValueBean> tcd130GroupList__;
    /** ユーザリスト */
    private List<UsrLabelValueBean> tcd130UserList__;

    /** 選択ユーザリスト */
    private String[] tcdSelectUserlist__ = null;
    /** saveリスト*/
    ArrayList < String > tcdSvSelectUserlist__;

    /** 選択ユーザ */
    private String tcdSelectedUser__ = null;

    /** ページコンボ上段 */
    private int tcd130pageTop__ = 1;
    /** ページコンボ下段 */
    private int tcd130pageBottom__ = 1;
    /** ページコンボリスト */
    private List < LabelValueBean > tcd130PageList__ = null;

    /** tcd060遷移時 時間帯情報SID */
    private int timezoneSid__ = -1;

    /**
     * 検索条件パラメータをSAVEフィールドへ移行します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void saveSearchParm() {
        tcd130SvGroupSid__ = tcd130GroupSid__;
        tcd130SvUserSid__  = tcd130UserSid__;
        tcd130SvTimezoneSid__ = tcd130TimezoneSid__;
        tcd130SvDefaultTimezoneSid__ = tcd130DefaultTimezoneSid__;

    }



    /**
     * <p>tcd130GroupSid を取得します。
     * @return tcd130GroupSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130GroupSid__
     */
    public int getTcd130GroupSid() {
        return tcd130GroupSid__;
    }

    /**
     * <p>tcd130GroupSid をセットします。
     * @param tcd130GroupSid tcd130GroupSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130GroupSid__
     */
    public void setTcd130GroupSid(int tcd130GroupSid) {
        tcd130GroupSid__ = tcd130GroupSid;
    }

    /**
     * <p>tcd130UserSid を取得します。
     * @return tcd130UserSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130UserSid__
     */
    public int getTcd130UserSid() {
        return tcd130UserSid__;
    }

    /**
     * <p>tcd130UserSid をセットします。
     * @param tcd130UserSid tcd130UserSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130UserSid__
     */
    public void setTcd130UserSid(int tcd130UserSid) {
        tcd130UserSid__ = tcd130UserSid;
    }

    /**
     * <p>tcd130TimezoneSid を取得します。
     * @return tcd130TimezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130TimezoneSid__
     */
    public int getTcd130TimezoneSid() {
        return tcd130TimezoneSid__;
    }

    /**
     * <p>tcd130TimezoneSid をセットします。
     * @param tcd130TimezoneSid tcd130TimezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130TimezoneSid__
     */
    public void setTcd130TimezoneSid(int tcd130TimezoneSid) {
        tcd130TimezoneSid__ = tcd130TimezoneSid;
    }

    /**
     * <p>tcd130DefaultTimezoneSid を取得します。
     * @return tcd130DefaultTimezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130DefaultTimezoneSid__
     */
    public int getTcd130DefaultTimezoneSid() {
        return tcd130DefaultTimezoneSid__;
    }

    /**
     * <p>tcd130DefaultTimezoneSid をセットします。
     * @param tcd130DefaultTimezoneSid tcd130DefaultTimezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130DefaultTimezoneSid__
     */
    public void setTcd130DefaultTimezoneSid(int tcd130DefaultTimezoneSid) {
        tcd130DefaultTimezoneSid__ = tcd130DefaultTimezoneSid;
    }

    /**
     * <p>tcd130SvGroupSid を取得します。
     * @return tcd130SvGroupSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel#tcd130SvGroupSid__
     */
    public int getTcd130SvGroupSid() {
        return tcd130SvGroupSid__;
    }

    /**
     * <p>tcd130SvGroupSid をセットします。
     * @param tcd130SvGroupSid tcd130SvGroupSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel#tcd130SvGroupSid__
     */
    public void setTcd130SvGroupSid(int tcd130SvGroupSid) {
        tcd130SvGroupSid__ = tcd130SvGroupSid;
    }

    /**
     * <p>tcd130SvUserSid を取得します。
     * @return tcd130SvUserSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel#tcd130SvUserSid__
     */
    public int getTcd130SvUserSid() {
        return tcd130SvUserSid__;
    }

    /**
     * <p>tcd130SvUserSid をセットします。
     * @param tcd130SvUserSid tcd130SvUserSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel#tcd130SvUserSid__
     */
    public void setTcd130SvUserSid(int tcd130SvUserSid) {
        tcd130SvUserSid__ = tcd130SvUserSid;
    }

    /**
     * <p>tcd130SvTimezoneSid を取得します。
     * @return tcd130SvTimezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel#tcd130SvTimezoneSid__
     */
    public int getTcd130SvTimezoneSid() {
        return tcd130SvTimezoneSid__;
    }

    /**
     * <p>tcd130SvTimezoneSid をセットします。
     * @param tcd130SvTimezoneSid tcd130SvTimezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel#tcd130SvTimezoneSid__
     */
    public void setTcd130SvTimezoneSid(int tcd130SvTimezoneSid) {
        tcd130SvTimezoneSid__ = tcd130SvTimezoneSid;
    }

    /**
     * <p>tcd130SvDefaultTimezoneSid を取得します。
     * @return tcd130SvDefaultTimezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel#tcd130SvDefaultTimezoneSid__
     */
    public int getTcd130SvDefaultTimezoneSid() {
        return tcd130SvDefaultTimezoneSid__;
    }

    /**
     * <p>tcd130SvDefaultTimezoneSid をセットします。
     * @param tcd130SvDefaultTimezoneSid tcd130SvDefaultTimezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel#tcd130SvDefaultTimezoneSid__
     */
    public void setTcd130SvDefaultTimezoneSid(int tcd130SvDefaultTimezoneSid) {
        tcd130SvDefaultTimezoneSid__ = tcd130SvDefaultTimezoneSid;
    }

    /**
     * <p>tcd130SearchFlg を取得します。
     * @return tcd130SearchFlg
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130SearchFlg__
     */
    public int getTcd130SearchFlg() {
        return tcd130SearchFlg__;
    }

    /**
     * <p>tcd130SearchFlg をセットします。
     * @param tcd130SearchFlg tcd130SearchFlg
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130SearchFlg__
     */
    public void setTcd130SearchFlg(int tcd130SearchFlg) {
        tcd130SearchFlg__ = tcd130SearchFlg;
    }

    /**
     * <p>tcd130SearchList を取得します。
     * @return tcd130SearchList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130SearchList__
     */
    public List<Tcd130DispModel> getTcd130SearchList() {
        return tcd130SearchList__;
    }

    /**
     * <p>tcd130SearchList をセットします。
     * @param tcd130SearchList tcd130SearchList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130SearchList__
     */
    public void setTcd130SearchList(List<Tcd130DispModel> tcd130SearchList) {
        tcd130SearchList__ = tcd130SearchList;
    }



    /**
     * <p>tcd130TimezoneNameList を取得します。
     * @return tcd130TimezoneNameList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130TimezoneNameList__
     */
    public List<LabelValueBean> getTcd130TimezoneNameList() {
        return tcd130TimezoneNameList__;
    }

    /**
     * <p>tcd130TimezoneNameList をセットします。
     * @param tcd130TimezoneNameList tcd130TimezoneNameList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130TimezoneNameList__
     */
    public void setTcd130TimezoneNameList(List<LabelValueBean> tcd130TimezoneNameList) {
        tcd130TimezoneNameList__ = tcd130TimezoneNameList;
    }

    /**
     * <p>tcd130GroupList を取得します。
     * @return tcd130GroupList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130GroupList__
     */
    public List<LabelValueBean> getTcd130GroupList() {
        return tcd130GroupList__;
    }

    /**
     * <p>tcd130GroupList をセットします。
     * @param tcd130GroupList tcd130GroupList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130GroupList__
     */
    public void setTcd130GroupList(List<LabelValueBean> tcd130GroupList) {
        tcd130GroupList__ = tcd130GroupList;
    }

    /**
     * <p>tcd130UserList を取得します。
     * @return tcd130UserList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130UserList__
     */
    public List<UsrLabelValueBean> getTcd130UserList() {
        return tcd130UserList__;
    }

    /**
     * <p>tcd130UserList をセットします。
     * @param tcd130UserList tcd130UserList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130UserList__
     */
    public void setTcd130UserList(List<UsrLabelValueBean> tcd130UserList) {
        tcd130UserList__ = tcd130UserList;
    }

    /**
     * <p>tcdSelectUserlist を取得します。
     * @return tcd130Selectedlist
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcdSelectUserlist__
     */
    public String[] getTcdSelectUserlist() {
        return tcdSelectUserlist__;
    }

    /**
     * <p>tcdSelectUserlist をセットします。
     * @param tcd130Selectedlist tcdSelectUserlist
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcdSelectUserlist__
     */
    public void setTcdSelectUserlist(String[] tcdSelectUserlist) {
        tcdSelectUserlist__ = tcdSelectUserlist;
    }

    /**
     * <p>tcdSvSelectUserlist を取得します。
     * @return tcdSvSelectUserlist
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcdSvSelectUserlist__
     */
    public ArrayList<String> getTcdSvSelectUserlist() {
        return tcdSvSelectUserlist__;
    }

    /**
     * <p>tcdSvSelectUserlist をセットします。
     * @param tcd130SvSelectedlist tcdSvSelectUserlist
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcdSvSelectUserlist__
     */
    public void setTcdSvSelectUserlist(ArrayList<String> tcdSvSelectUserlist) {
        tcdSvSelectUserlist__ = tcdSvSelectUserlist;
    }

    /**
     * <p>tcdSelectedUser を取得します。
     * @return tcdSelectedUser
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcdSelectedUser__
     */
    public String getTcdSelectedUser() {
        return tcdSelectedUser__;
    }

    /**
     * <p>tcdSelectedUser をセットします。
     * @param tcdSelectedUser tcdSelectedUser
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcdSelectedUser__
     */
    public void setTcdSelectedUser(String tcdSelectedUser) {
        tcdSelectedUser__ = tcdSelectedUser;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     * @param ishiddenSelected チェックボックスの値を保持するか
     */
    public void setHiddenParamTcd130(Cmn999Form msgForm) {
        msgForm.addHiddenParam("tcd130SearchFlg", tcd130SearchFlg__);
        msgForm.addHiddenParam("tcd130GroupSid", tcd130GroupSid__);
        msgForm.addHiddenParam("tcd130UserSid", tcd130UserSid__);
        msgForm.addHiddenParam("tcd130TimezoneSid", tcd130TimezoneSid__);
        msgForm.addHiddenParam("tcd130DefaultTimezoneSid", tcd130DefaultTimezoneSid__);
        msgForm.addHiddenParam("tcd130SvGroupSid", tcd130SvGroupSid__);
        msgForm.addHiddenParam("tcd130SvUserSid", tcd130SvUserSid__);
        msgForm.addHiddenParam("tcd130SvTimezoneSid", tcd130SvTimezoneSid__);
        msgForm.addHiddenParam("tcd130SvDefaultTimezoneSid", tcd130SvDefaultTimezoneSid__);
        msgForm.addHiddenParam("tcdSelectedUser", tcdSelectedUser__);
        if (tcdSelectUserlist__ != null && tcdSelectUserlist__.length > 0) {
            for (String value : tcdSelectUserlist__) {
                msgForm.addHiddenParam("tcdSelectUserlist", value);
            }
        }
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con Connection
     * @param multiFlg 複数編集
     * @return errors エラー
     * @throws SQLException 例外
     */
    public ActionErrors validateCheck(
            HttpServletRequest req,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        List<Integer> usrList = new ArrayList<Integer>();

        if ((tcdSelectUserlist__ == null || tcdSelectUserlist__.length < 1)
                && StringUtil.isNullZeroString(tcdSelectedUser__)) {
            //未選択チェック
            String msg2 = gsMsg.getMessage(req, "cmn.user");
            ActionMessage msg = new ActionMessage("error.select.required.text", msg2);
            StrutsUtil.addMessage(errors, msg, "tcd130Selectedlist");

            return errors;
        }

        if (tcdSelectUserlist__ != null && tcdSelectUserlist__.length > 0) {
            for (String sid: tcdSelectUserlist__) {
                usrList.add(Integer.parseInt(sid));
            }
        }
        if (!StringUtil.isNullZeroString(tcdSelectedUser__)) {
            usrList.add(Integer.parseInt(tcdSelectedUser__));
        }

        // 編集対象が存在しない
        if (!usrList.isEmpty()) {
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            int count = cuDao.getCountActiveUser(usrList);
            if (count <= 0) {
                String msg2 = gsMsg.getMessage(req, "cmn.user");
                ActionMessage msg = new ActionMessage("error.select.required.text", msg2);
                StrutsUtil.addMessage(errors, msg, "tcd130Selectedlist");
            }
        }

        return errors;
    }



    /**
     * <p>tcd130pageTop を取得します。
     * @return tcd130pageTop
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130pageTop__
     */
    public int getTcd130pageTop() {
        return tcd130pageTop__;
    }



    /**
     * <p>tcd130pageTop をセットします。
     * @param tcd130pageTop tcd130pageTop
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130pageTop__
     */
    public void setTcd130pageTop(int tcd130pageTop) {
        tcd130pageTop__ = tcd130pageTop;
    }



    /**
     * <p>tcd130pageBottom を取得します。
     * @return tcd130pageBottom
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130pageBottom__
     */
    public int getTcd130pageBottom() {
        return tcd130pageBottom__;
    }



    /**
     * <p>tcd130pageBottom をセットします。
     * @param tcd130pageBottom tcd130pageBottom
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130pageBottom__
     */
    public void setTcd130pageBottom(int tcd130pageBottom) {
        tcd130pageBottom__ = tcd130pageBottom;
    }



    /**
     * <p>tcd130PageList を取得します。
     * @return tcd130PageList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130PageList__
     */
    public List<LabelValueBean> getTcd130PageList() {
        return tcd130PageList__;
    }



    /**
     * <p>tcd130PageList をセットします。
     * @param tcd130PageList tcd130PageList
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#tcd130PageList__
     */
    public void setTcd130PageList(List<LabelValueBean> tcd130PageList) {
        tcd130PageList__ = tcd130PageList;
    }



    /**
     * <p>timezoneSid を取得します。
     * @return timezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#timezoneSid__
     */
    public int getTimezoneSid() {
        return timezoneSid__;
    }



    /**
     * <p>timezoneSid をセットします。
     * @param timezoneSid timezoneSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130Form#timezoneSid__
     */
    public void setTimezoneSid(int timezoneSid) {
        timezoneSid__ = timezoneSid;
    }
}
