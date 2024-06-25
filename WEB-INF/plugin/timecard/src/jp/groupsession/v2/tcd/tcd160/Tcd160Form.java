package jp.groupsession.v2.tcd.tcd160;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.tcd030.Tcd030Form;


/**
 * <br>[機  能] タイムカード 管理者設定 休日区分設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd160Form extends Tcd030Form {

    /**  休日区分情報編集モード */
    private int tcd170Mode__ = GSConst.CMDMODE_ADD;
    /** 編集対象 休日区分SID */
    private int tcd170EditSid__ = -1;

    /** 時間帯リスト */
    private List<TcdHolidayInfModel> tcd160HolidayList__;
    /** チェックされている並び順 */
    private String tcd160Order__= null;

    /**
     * <p>tcd170Mode を取得します。
     * @return tcd170Mode
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd170Mode__
     */
    public int getTcd170Mode() {
        return tcd170Mode__;
    }

    /**
     * <p>tcd170Mode をセットします。
     * @param tcd170Mode tcd170Mode
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd170Mode__
     */
    public void setTcd170Mode(int tcd170Mode) {
        tcd170Mode__ = tcd170Mode;
    }

    /**
     * <p>tcd170EditSid を取得します。
     * @return tcd170EditSid
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd170EditSid__
     */
    public int getTcd170EditSid() {
        return tcd170EditSid__;
    }

    /**
     * <p>tcd170EditSid をセットします。
     * @param tcd170EditSid tcd170EditSid
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd170EditSid__
     */
    public void setTcd170EditSid(int tcd170EditSid) {
        tcd170EditSid__ = tcd170EditSid;
    }

    /**
     * <p>tcd160HolidayList を取得します。
     * @return tcd160HolidayList
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd160HolidayList__
     */
    public List<TcdHolidayInfModel> getTcd160HolidayList() {
        return tcd160HolidayList__;
    }

    /**
     * <p>tcd160HolidayList をセットします。
     * @param tcd160HolidayList tcd160HolidayList
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd160HolidayList__
     */
    public void setTcd160HolidayList(List<TcdHolidayInfModel> tcd160HolidayList) {
        tcd160HolidayList__ = tcd160HolidayList;
    }

    /**
     * <p>tcd160Order を取得します。
     * @return tcd160Order
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd160Order
     */
    public String getTcd160Order() {
        return tcd160Order__;
    }

    /**
     * <p>tcd160Order をセットします。
     * @param tcd160Order tcd160Order
     * @see jp.groupsession.v2.tcd.tcd160.Tcd160Form#tcd160Order
     */
    public void setTcd160Order(String tcd160Order) {
        tcd160Order__ = tcd160Order;
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
        if (StringUtil.isNullZeroString(tcd160Order__)) {
            msg = new ActionMessage("tcd160.notselect.timezone");
            StrutsUtil.addMessage(errors, msg, "tcd160Order__");
        }
        return errors;
    }

    /**
     * 休日区分の存在チェックを行います
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param admConf 基本設定
     * @return ActionErrors
     * @throws SQLException
     */
    public ActionErrors existsHolidayOrder(RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        int cnt = dao.existsHoliday(NullDefault.getInt(tcd160Order__, 0), false);
        if (cnt == 0) {
            String msg2 = gsMsg.getMessage("tcd.tcd140.04");
            msg = new ActionMessage(
                    "error.nothing.selected", msg2);
            StrutsUtil.addMessage(errors, msg, tcd160Order__);
        }

        return errors;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParamTcd160(Cmn999Form msgForm) {
        msgForm.addHiddenParam("tcd170Mode", tcd170Mode__);
        msgForm.addHiddenParam("tcd170EditSid", tcd170EditSid__);
    }
}
