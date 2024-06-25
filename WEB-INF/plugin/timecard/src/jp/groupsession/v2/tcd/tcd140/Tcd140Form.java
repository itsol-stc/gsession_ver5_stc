package jp.groupsession.v2.tcd.tcd140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.tcd130.Tcd130Form;


/**
 * <br>[機  能] タイムカード 管理者設定 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd140Form extends Tcd130Form {

    /** 初期表示フラグ */
    private int tcd140initFlg__ = 0;
    /** 選択時間帯チェックボックス */
    private String[] tcd140SelectedTimeZone__ = null;
    /** デフォルト時間帯設定 */
    private int tcd140DefaultTimeZone__ = 0;
    /** 時間帯設定チェックボックス */
    private List<TcdTimezoneInfoModel> tcd140TimeZoneCheck__ = null;
    /** デフォルト設定セレクトボックス */
    private List<TcdTimezoneInfoModel> tcd140TimeZoneDefaultList__ = null;
    /** ユーザ一覧 */
    private List<CmnUsrmInfModel> tcd140SelectedUser__ = null;

    /**
     * <p>tcd140initFlg を取得します。
     * @return tcd140initFlg
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140initFlg__
     */
    public int getTcd140initFlg() {
        return tcd140initFlg__;
    }

    /**
     * <p>tcd140initFlg をセットします。
     * @param tcd140initFlg tcd140initFlg
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140initFlg__
     */
    public void setTcd140initFlg(int tcd140initFlg) {
        tcd140initFlg__ = tcd140initFlg;
    }

    /**
     * <p>tcd140SelectedTimeZone を取得します。
     * @return tcd140SelectedTimeZone
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140SelectedTimeZone__
     */
    public String[] getTcd140SelectedTimeZone() {
        return tcd140SelectedTimeZone__;
    }

    /**
     * <p>tcd140SelectedTimeZone をセットします。
     * @param tcd140SelectedTimeZone tcd140SelectedTimeZone
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140SelectedTimeZone__
     */
    public void setTcd140SelectedTimeZone(String[] tcd140SelectedTimeZone) {
        tcd140SelectedTimeZone__ = tcd140SelectedTimeZone;
    }

    /**
     * <p>tcd140DefaultTimeZone を取得します。
     * @return tcd140DefaultTimeZone
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140DefaultTimeZone__
     */
    public int getTcd140DefaultTimeZone() {
        return tcd140DefaultTimeZone__;
    }

    /**
     * <p>tcd140DefaultTimeZone をセットします。
     * @param tcd140DefaultTimeZone tcd140DefaultTimeZone
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140DefaultTimeZone__
     */
    public void setTcd140DefaultTimeZone(int tcd140DefaultTimeZone) {
        tcd140DefaultTimeZone__ = tcd140DefaultTimeZone;
    }

    /**
     * <p>tcd140TimeZoneCheck を取得します。
     * @return tcd140TimeZoneCheck
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140TimeZoneCheck__
     */
    public List<TcdTimezoneInfoModel> getTcd140TimeZoneCheck() {
        return tcd140TimeZoneCheck__;
    }

    /**
     * <p>tcd140TimeZoneCheck をセットします。
     * @param tcd140TimeZoneCheck tcd140TimeZoneCheck
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140TimeZoneCheck__
     */
    public void setTcd140TimeZoneCheck(
            List<TcdTimezoneInfoModel> tcd140TimeZoneCheck) {
        tcd140TimeZoneCheck__ = tcd140TimeZoneCheck;
    }

    /**
     * <p>tcd140TimeZoneDefaultList を取得します。
     * @return tcd140TimeZoneDefaultList
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140TimeZoneDefaultList__
     */
    public List<TcdTimezoneInfoModel> getTcd140TimeZoneDefaultList() {
        return tcd140TimeZoneDefaultList__;
    }

    /**
     * <p>tcd140TimeZoneDefaultList をセットします。
     * @param tcd140TimeZoneDefaultList tcd140TimeZoneDefaultList
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140TimeZoneDefaultList__
     */
    public void setTcd140TimeZoneDefaultList(
            List<TcdTimezoneInfoModel> tcd140TimeZoneDefaultList) {
        tcd140TimeZoneDefaultList__ = tcd140TimeZoneDefaultList;
    }

    /**
     * <p>tcd140SelectedUser を取得します。
     * @return tcd140SelectedUser
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140SelectedUser__
     */
    public List<CmnUsrmInfModel> getTcd140SelectedUser() {
        return tcd140SelectedUser__;
    }

    /**
     * <p>tcd140SelectedUser をセットします。
     * @param tcd140SelectedUser tcd140SelectedUser
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140SelectedUser__
     */
    public void setTcd140SelectedUser(List<CmnUsrmInfModel> tcd140SelectedUser) {
        tcd140SelectedUser__ = tcd140SelectedUser;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {


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

       //存在チェック
        List<Integer> usrList = new ArrayList<Integer>();
        String[] selectSids = getTcdSelectUserlist();
        if (selectSids != null && selectSids.length > 0) {
            for (String sid: selectSids) {
                usrList.add(Integer.parseInt(sid));
            }
        }

        // 編集対象が存在しない
        if (!usrList.isEmpty()) {
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            int count = cuDao.getCountActiveUser(usrList);
            if (usrList.size() != count) {
                ActionMessage msg = new ActionMessage(
                        "tcd130.select.not.user");
                errors.add("tcd130.select.not.user", msg);
                return errors;
            }
        }

        //時間帯設定未選択チェック
        if (tcd140SelectedTimeZone__ == null || tcd140SelectedTimeZone__.length < 1) {
            //未選択チェック
            String msg2 = gsMsg.getMessage(req, "tcd.tcd140.02");
            ActionMessage msg = new ActionMessage("error.select.required.text", msg2);
            StrutsUtil.addMessage(errors, msg, "tcd140SelectedTimeZone__");

            return errors;
        }
        //選択可能チェック
        TcdTimezoneInfoDao infDao = new TcdTimezoneInfoDao(con);
        if (!infDao.canSelect(tcd140SelectedTimeZone__)) {
            ActionMessage msg = new ActionMessage("tcd140.can.select.timezone");
            StrutsUtil.addMessage(errors, msg, "tcd140SelectedTimeZone__");

            return errors;
        }
        //デフォルト設定選択チェック
        boolean defaultOk = false;
        for (String chk: tcd140SelectedTimeZone__) {
            int chkInt = NullDefault.getInt(chk, 0);
            if (chkInt == tcd140DefaultTimeZone__) {
                defaultOk = true;
                break;
            }
        }
        if (!defaultOk) {
            String msg2 = gsMsg.getMessage(req, "tcd.tcd140.04");
            ActionMessage msg = new ActionMessage("error.input.notvalidate.data", msg2);
            StrutsUtil.addMessage(errors, msg, "tcd140DefaultTimeZone__");
            return errors;
        } else {
            if (!infDao.canSelect(new String[]{String.valueOf(tcd140DefaultTimeZone__)})) {
                ActionMessage msg = new ActionMessage("tcd140.can.select.default.timezone");
                StrutsUtil.addMessage(errors, msg, "tcd140DefaultTimeZone__");
                return errors;
            }
        }
        return errors;

    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParamTcd140(Cmn999Form msgForm) {
        msgForm.addHiddenParam("tcd140initFlg", tcd140initFlg__);
        msgForm.addHiddenParam("tcd140DefaultTimeZone", tcd140DefaultTimeZone__);
        String [] tcdZoneValues = tcd140SelectedTimeZone__;
        if (tcdZoneValues != null && tcdZoneValues.length > 0) {
            for (String value : tcdZoneValues) {
                msgForm.addHiddenParam("tcd140SelectedTimeZone", value);
            }
        }
    }
}
