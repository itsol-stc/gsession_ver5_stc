package jp.groupsession.v2.tcd.tcd200;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardYukyuConfBiz;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;
import jp.groupsession.v2.tcd.model.TcdYukyudataModel;
import jp.groupsession.v2.tcd.tcd190.Tcd190Form;

/**
 * <br>[機  能] タイムカード管理者設定 有休日数登録画面のフォームクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd200Form extends Tcd190Form {

    /** グループ */
    private int tcd200Group__ = 0;
    /** ユーザ */
    private int tcd200Name__ = 0;
    /** 年度 */
    private int tcd200Nendo__;
    /** 有休日数 */
    private String tcd200YukyuDays__ = null;

    /** 初期表示フラグ */
    private int tcd200initFlg__;

    /**
     * <p>tcd200Group を取得します。
     * @return tcd200Group
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Group__
     */
    public int getTcd200Group() {
        return tcd200Group__;
    }

    /**
     * <p>tcd200Group をセットします。
     * @param tcd200Group tcd200Group
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Group__
     */
    public void setTcd200Group(int tcd200Group) {
        tcd200Group__ = tcd200Group;
    }

    /**
     * <p>tcd200Name を取得します。
     * @return tcd200Name
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Name__
     */
    public int getTcd200Name() {
        return tcd200Name__;
    }

    /**
     * <p>tcd200Name をセットします。
     * @param tcd200Name tcd200Name
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Name__
     */
    public void setTcd200Name(int tcd200Name) {
        tcd200Name__ = tcd200Name;
    }

    /**
     * <p>tcd200Nendo を取得します。
     * @return tcd200Nendo
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Nendo__
     */
    public int getTcd200Nendo() {
        return tcd200Nendo__;
    }

    /**
     * <p>tcd200Nendo をセットします。
     * @param tcd200Nendo tcd200Nendo
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200Nendo__
     */
    public void setTcd200Nendo(int tcd200Nendo) {
        tcd200Nendo__ = tcd200Nendo;
    }

    /**
     * <p>tcd200YukyuDays を取得します。
     * @return tcd200YukyuDays
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200YukyuDays__
     */
    public String getTcd200YukyuDays() {
        return tcd200YukyuDays__;
    }

    /**
     * <p>tcd200YukyuDays をセットします。
     * @param tcd200YukyuDays tcd200YukyuDays
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200YukyuDays__
     */
    public void setTcd200YukyuDays(String tcd200YukyuDays) {
        tcd200YukyuDays__ = tcd200YukyuDays;
    }

    /**
     * <p>tcd200initFlg を取得します。
     * @return tcd200initFlg
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200initFlg__
     */
    public int getTcd200initFlg() {
        return tcd200initFlg__;
    }

    /**
     * <p>tcd200initFlg をセットします。
     * @param tcd200initFlg tcd200initFlg
     * @see jp.groupsession.v2.tcd.tcd200.Tcd200Form#tcd200initFlg__
     */
    public void setTcd200initFlg(int tcd200initFlg) {
        tcd200initFlg__ = tcd200initFlg;
    }

    /**
     * <br>[機  能] 入力された値が正しいかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con DBコネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateSubmit(
            RequestModel reqMdl,
            Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        CmnUsrmDao cuDao = new CmnUsrmDao(con);
        List<Integer> nameList = new ArrayList<Integer>();
        nameList.add(tcd200Name__);

        //ユーザチェック
        String textUser = gsMsg.getMessage("cmn.user");

        //未選択
        if (tcd200Name__ == 0 || cuDao.getCountActiveUser(nameList) != 1) {
            ActionMessage msg = new ActionMessage("error.select.required.text", textUser);
            StrutsUtil.addMessage(errors, msg, "error.select.required.user");
        }

        TimecardBiz tcdBiz = new TimecardBiz();
        TimecardYukyuConfBiz tycBiz = new TimecardYukyuConfBiz();
        int usrKbn = tcdBiz.getUserKbn(con, reqMdl.getSmodel());

        if (usrKbn == GSConstTimecard.USER_KBN_GRPADM) {
            //管理者として設定されているグループの所属ユーザか
            GroupBiz grpBiz = new GroupBiz();
            int usrSid = reqMdl.getSmodel().getUsrsid();
            //管理者として設定されているグループの取得
            List<LabelValueBean> groupList = tcdBiz.getGroupLabel(con, reqMdl, usrSid, false, 0);
            boolean belongFlg = false;
            for (LabelValueBean groupBean : groupList) {
                int groupSid = Integer.parseInt(groupBean.getValue());
                if (grpBiz.isBelongGroup(tcd200Name__, groupSid, con)) {
                    belongFlg = true;
                    break;
                }
            }
            if (!belongFlg) {
                ActionMessage msg = new ActionMessage("error.input.notvalidate.data", textUser);
                StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
            }
        }
        
        //年度チェック
        int minNendo = tycBiz.getMinYear(con);
        int maxNendo = tycBiz.getMaxYear(con);
        if (minNendo > tcd200Nendo__ || maxNendo < tcd200Nendo__) {
            String textNendo = gsMsg.getMessage("tcd.209");
            ActionMessage msg = new ActionMessage("error.input.notvalidate.data", textNendo);
            StrutsUtil.addMessage(errors, msg, "error.input.format.nendo");
        }
        

        //有休日数チェック
        String textYukyuDays = gsMsg.getMessage("tcd.210");
        //未選択チェック
        if (StringUtil.isNullZeroString(tcd200YukyuDays__)) {
            ActionMessage msg = new ActionMessage("error.input.required.text", textYukyuDays);
            StrutsUtil.addMessage(errors, msg, "error.select.yukyudays.text");
        } else if (!ValidateUtil.isNumberDot(tcd200YukyuDays__)) {
            //半角数字チェック
            ActionMessage msg = new ActionMessage("error.input.number.hankaku", textYukyuDays);
            StrutsUtil.addMessage(errors, msg, "error.input.yukydays.hankaku");
        } else if (!ValidateUtil.isNumberDot(tcd200YukyuDays__, 3, 3)) {
            //桁チェック
            String gsmsg = gsMsg.getMessage("tcd.tcd020.09");
            ActionMessage msg = new ActionMessage("error.input.comp.text", textYukyuDays, gsmsg);
            StrutsUtil.addMessage(errors, msg, "error.input.yukydays.hankaku");
        } else {
            //フォーマットチェック
            BigDecimal dYukyu = new BigDecimal(tcd200YukyuDays__);
            int maxDay = 365;
            if (tycBiz.isUruYear(tcd200Nendo__)) {
                maxDay = 366;
            }
            BigDecimal bOne = BigDecimal.valueOf(1);
            BigDecimal bMaxDay = BigDecimal.valueOf(maxDay);
            if (dYukyu.compareTo(bOne) == -1 || dYukyu.compareTo(bMaxDay) == 1) {
                ActionMessage msg = new ActionMessage("error.input.notvalidate.data",
                        textYukyuDays);
                StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 有休日数情報が存在するかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @return true:有休日数情報が存在する false:有休日数情報が存在しない
     * @throws SQLException SQL実行例外
     */
    protected boolean _validateDelete(Connection con) throws SQLException {

        TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
        TcdYukyudataModel tydMdl = tydDao.getYukyuData(tcd200Name__, tcd200Nendo__);
        if (tydMdl == null) {
            return false;
        }
        return true;
    }
}
