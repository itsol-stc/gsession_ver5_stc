package jp.groupsession.v2.tcd.tcd120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.dao.TimecardDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.tcd030.Tcd030Form;


/**
 * <br>[機  能] タイムカード 管理者設定 時間設定一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd120Form extends Tcd030Form {

    /**  時間帯情報編集モード */
    private int timezoneCmdMode__ = GSConst.CMDMODE_ADD;
    /** 編集対象 時間帯情報SID */
    private int timezoneSid__ = -1;

    /** 時間帯リスト */
    private List<TcdTimezoneInfoModel> tcd120TimezoneList__;
    /** チェックされている並び順 */
    private String tcd120SortRadio__ = null;
    /** 並び替え対象の時間帯名称 */
    private String[] tcd120sortLabel__ = null;

    /**
     * <p>timezoneCmdMode を取得します。
     * @return timezoneCmdMode
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#timezoneCmdMode__
     */
    public int getTimezoneCmdMode() {
        return timezoneCmdMode__;
    }
    /**
     * <p>timezoneCmdMode をセットします。
     * @param timezoneCmdMode timezoneCmdMode
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#timezoneCmdMode__
     */
    public void setTimezoneCmdMode(int timezoneCmdMode) {
        timezoneCmdMode__ = timezoneCmdMode;
    }
    /**
     * <p>tcd120TimezoneSid を取得します。
     * @return tcd120TimezoneSid
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#timezoneSid__
     */
    public int getTimezoneSid() {
        return timezoneSid__;
    }
    /**
     * <p>tcd120TimezoneSid をセットします。
     * @param tcd120TimezoneSid tcd120TimezoneSid
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#timezoneSid__
     */
    public void setTimezoneSid(int timezoneSid) {
        timezoneSid__ = timezoneSid;
    }
    /**
     * <p>tcd120TimezoneList を取得します。
     * @return tcd120TimezoneList
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#tcd120TimezoneList__
     */
    public List<TcdTimezoneInfoModel> getTcd120TimezoneList() {
        return tcd120TimezoneList__;
    }
    /**
     * <p>tcd120TimezoneList をセットします。
     * @param tcd120TimezoneList tcd120TimezoneList
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#tcd120TimezoneList__
     */
    public void setTcd120TimezoneList(
            List<TcdTimezoneInfoModel> tcd120TimezoneList) {
        tcd120TimezoneList__ = tcd120TimezoneList;
    }
    /**
     * <p>tcd120SortRadio を取得します。
     * @return tcd120SortRadio
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#tcd120SortRadio__
     */
    public String getTcd120SortRadio() {
        return tcd120SortRadio__;
    }
    /**
     * <p>tcd120SortRadio をセットします。
     * @param tcd120SortRadio tcd120SortRadio
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#tcd120SortRadio__
     */
    public void setTcd120SortRadio(String tcd120SortRadio) {
        tcd120SortRadio__ = tcd120SortRadio;
    }
    /**
     * <p>tcd120sortLabel を取得します。
     * @return tcd120sortLabel
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#tcd120sortLabel__
     */
    public String[] getTcd120sortLabel() {
        return tcd120sortLabel__;
    }
    /**
     * <p>tcd120sortLabel をセットします。
     * @param tcd120sortLabel tcd120sortLabel
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120Form#tcd120sortLabel__
     */
    public void setTcd120sortLabel(String[] tcd120sortLabel) {
        tcd120sortLabel__ = tcd120sortLabel;
    }

    /**
     * タイムカード編集画面の入力チェックを行います
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param admConf 基本設定
     * @return ActionErrors
     * @throws SQLException
     */
    public ActionErrors validateCheck(RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        TcdTimezoneInfoDao infDao = new TcdTimezoneInfoDao(con);
        //デフォルトチェック
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //DBより設定情報を取得。なければデフォルト値とする。
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
        TcdAdmConfModel admMdl = tcdBiz.getTcdAdmConfModel(sessionUsrSid, con);
        if (NullDefault.getInt(tcd120SortRadio__, 0) == admMdl.getTacDefTimezone()) {
            msg = new ActionMessage("tcd120.notdelete.default.timezone");
            StrutsUtil.addMessage(errors, msg, "tcd120SortRadio__");
        }


        //存在チェック
        int exCnt = infDao.timezoneCount(NullDefault.getInt(tcd120SortRadio__, 0));
        if (exCnt == 0) {
            msg = new ActionMessage("tcd120.notfound.timezone");
            StrutsUtil.addMessage(errors, msg, "tcd120SortRadio__");
        }
        //タイムカード使用チェック
        TimecardDao tDao = new TimecardDao(con);
        int useCnt = tDao.tcdataCount(NullDefault.getInt(tcd120SortRadio__, 0));
        if (useCnt != 0) {
            msg = new ActionMessage("tcd120.notdelete.timezone");
            StrutsUtil.addMessage(errors, msg, "tcd120SortRadio__");
        }
        //使用ユーザチェック
        TcdTimezonePriDao ttpDao = new TcdTimezonePriDao(con);
        int useUserCnt = ttpDao.selectUseUserCount(NullDefault.getInt(tcd120SortRadio__, 0));
        if (useUserCnt != 0) {
            msg = new ActionMessage("tcd120.notdelete.user");
            StrutsUtil.addMessage(errors, msg, "tcd120SortRadio__");
        }

        return errors;
    }

    /**
     * <br>[機  能] 時間帯順序変更時の入力チェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return
     * @throws SQLException
     */
    public ActionErrors validateSortCheck(RequestModel reqMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        //radio未選択チェックを行う
        if (StringUtil.isNullZeroString(tcd120SortRadio__)) {
            msg = new ActionMessage("tcd120.notselect.timezone");
            StrutsUtil.addMessage(errors, msg, "tcd120SortRadio__");
        }
        return errors;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParamTcd120(Cmn999Form msgForm) {
        msgForm.addHiddenParam("timezoneCmdMode", timezoneCmdMode__);
        msgForm.addHiddenParam("timezoneSid", timezoneSid__);
        msgForm.addHiddenParam("tcd120SortRadio", tcd120SortRadio__);
    }
}
