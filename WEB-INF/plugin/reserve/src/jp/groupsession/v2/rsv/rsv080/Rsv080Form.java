package jp.groupsession.v2.rsv.rsv080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.rsv050.Rsv050Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 施設予約 施設情報設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv080Form extends Rsv050Form {

    /** 処理対象の施設グループSID */
    private int rsv080EditGrpSid__ = -1;
    /** 処理対象の施設SID */
    private int rsv080EditSisetuSid__ = -1;

    /** 画面表示全キーリスト */
    private String[] rsv080KeyList__ = null;
    /** グループ名称 */
    private String rsv080RsgName__ = null;
    /** 施設区分名称 */
    private String rsv080RskName__ = null;
    /** 施設一覧 */
    private ArrayList<Rsv080Model> rsv080SisetuList__ = null;
    /** ラジオ選択値 */
    private String rsv080SortRadio__ = null;

    /** 表示項目1項目名称 */
    private String rsv080PropHeaderName1__ = null;
    /** 表示項目2項目名称 */
    private String rsv080PropHeaderName2__ = null;
    /** 表示項目3項目名称 */
    private String rsv080PropHeaderName3__ = null;
    /** 表示項目4項目名称 */
    private String rsv080PropHeaderName4__ = null;
    /** 表示項目5項目名称 */
    private String rsv080PropHeaderName5__ = null;
    /** 表示項目6項目名称 */
    private String rsv080PropHeaderName6__ = null;
    /** 表示項目7項目名称 */
    private String rsv080PropHeaderName7__ = null;

    /**
     * <p>rsv080EditSisetuSid__ を取得します。
     * @return rsv080EditSisetuSid
     */
    public int getRsv080EditSisetuSid() {
        return rsv080EditSisetuSid__;
    }
    /**
     * <p>rsv080EditSisetuSid__ をセットします。
     * @param rsv080EditSisetuSid rsv080EditSisetuSid__
     */
    public void setRsv080EditSisetuSid(int rsv080EditSisetuSid) {
        rsv080EditSisetuSid__ = rsv080EditSisetuSid;
    }
    /**
     * <p>rsv080EditGrpSid__ を取得します。
     * @return rsv080EditGrpSid
     */
    public int getRsv080EditGrpSid() {
        return rsv080EditGrpSid__;
    }
    /**
     * <p>rsv080EditGrpSid__ をセットします。
     * @param rsv080EditGrpSid rsv080EditGrpSid__
     */
    public void setRsv080EditGrpSid(int rsv080EditGrpSid) {
        rsv080EditGrpSid__ = rsv080EditGrpSid;
    }
    /**
     * <p>rsv080KeyList__ を取得します。
     * @return rsv080KeyList
     */
    public String[] getRsv080KeyList() {
        return rsv080KeyList__;
    }
    /**
     * <p>rsv080KeyList__ をセットします。
     * @param rsv080KeyList rsv080KeyList__
     */
    public void setRsv080KeyList(String[] rsv080KeyList) {
        rsv080KeyList__ = rsv080KeyList;
    }
    /**
     * <p>rsv080RsgName__ を取得します。
     * @return rsv080RsgName
     */
    public String getRsv080RsgName() {
        return rsv080RsgName__;
    }
    /**
     * <p>rsv080RsgName__ をセットします。
     * @param rsv080RsgName rsv080RsgName__
     */
    public void setRsv080RsgName(String rsv080RsgName) {
        rsv080RsgName__ = rsv080RsgName;
    }
    /**
     * <p>rsv080RskName__ を取得します。
     * @return rsv080RskName
     */
    public String getRsv080RskName() {
        return rsv080RskName__;
    }
    /**
     * <p>rsv080RskName__ をセットします。
     * @param rsv080RskName rsv080RskName__
     */
    public void setRsv080RskName(String rsv080RskName) {
        rsv080RskName__ = rsv080RskName;
    }
    /**
     * <p>rsv080SisetuList__ を取得します。
     * @return rsv080SisetuList
     */
    public ArrayList<Rsv080Model> getRsv080SisetuList() {
        return rsv080SisetuList__;
    }
    /**
     * <p>rsv080SisetuList__ をセットします。
     * @param rsv080SisetuList rsv080SisetuList__
     */
    public void setRsv080SisetuList(ArrayList<Rsv080Model> rsv080SisetuList) {
        rsv080SisetuList__ = rsv080SisetuList;
    }
    /**
     * <p>rsv080SortRadio__ を取得します。
     * @return rsv080SortRadio
     */
    public String getRsv080SortRadio() {
        return rsv080SortRadio__;
    }
    /**
     * <p>rsv080SortRadio__ をセットします。
     * @param rsv080SortRadio rsv080SortRadio__
     */
    public void setRsv080SortRadio(String rsv080SortRadio) {
        rsv080SortRadio__ = rsv080SortRadio;
    }
    /**
     * <p>rsv080PropHeaderName1__ を取得します。
     * @return rsv080PropHeaderName1
     */
    public String getRsv080PropHeaderName1() {
        return rsv080PropHeaderName1__;
    }
    /**
     * <p>rsv080PropHeaderName1__ をセットします。
     * @param rsv080PropHeaderName1 rsv080PropHeaderName1__
     */
    public void setRsv080PropHeaderName1(String rsv080PropHeaderName1) {
        rsv080PropHeaderName1__ = rsv080PropHeaderName1;
    }
    /**
     * <p>rsv080PropHeaderName2__ を取得します。
     * @return rsv080PropHeaderName2
     */
    public String getRsv080PropHeaderName2() {
        return rsv080PropHeaderName2__;
    }
    /**
     * <p>rsv080PropHeaderName2__ をセットします。
     * @param rsv080PropHeaderName2 rsv080PropHeaderName2__
     */
    public void setRsv080PropHeaderName2(String rsv080PropHeaderName2) {
        rsv080PropHeaderName2__ = rsv080PropHeaderName2;
    }
    /**
     * <p>rsv080PropHeaderName3__ を取得します。
     * @return rsv080PropHeaderName3
     */
    public String getRsv080PropHeaderName3() {
        return rsv080PropHeaderName3__;
    }
    /**
     * <p>rsv080PropHeaderName3__ をセットします。
     * @param rsv080PropHeaderName3 rsv080PropHeaderName3__
     */
    public void setRsv080PropHeaderName3(String rsv080PropHeaderName3) {
        rsv080PropHeaderName3__ = rsv080PropHeaderName3;
    }
    /**
     * <p>rsv080PropHeaderName4__ を取得します。
     * @return rsv080PropHeaderName4
     */
    public String getRsv080PropHeaderName4() {
        return rsv080PropHeaderName4__;
    }
    /**
     * <p>rsv080PropHeaderName4__ をセットします。
     * @param rsv080PropHeaderName4 rsv080PropHeaderName4__
     */
    public void setRsv080PropHeaderName4(String rsv080PropHeaderName4) {
        rsv080PropHeaderName4__ = rsv080PropHeaderName4;
    }
    /**
     * <p>rsv080PropHeaderName5__ を取得します。
     * @return rsv080PropHeaderName5
     */
    public String getRsv080PropHeaderName5() {
        return rsv080PropHeaderName5__;
    }
    /**
     * <p>rsv080PropHeaderName5__ をセットします。
     * @param rsv080PropHeaderName5 rsv080PropHeaderName5__
     */
    public void setRsv080PropHeaderName5(String rsv080PropHeaderName5) {
        rsv080PropHeaderName5__ = rsv080PropHeaderName5;
    }
    /**
     * <p>rsv080PropHeaderName6__ を取得します。
     * @return rsv080PropHeaderName6
     */
    public String getRsv080PropHeaderName6() {
        return rsv080PropHeaderName6__;
    }
    /**
     * <p>rsv080PropHeaderName6__ をセットします。
     * @param rsv080PropHeaderName6 rsv080PropHeaderName6__
     */
    public void setRsv080PropHeaderName6(String rsv080PropHeaderName6) {
        rsv080PropHeaderName6__ = rsv080PropHeaderName6;
    }
    /**
     * <p>rsv080PropHeaderName7__ を取得します。
     * @return rsv080PropHeaderName7
     */
    public String getRsv080PropHeaderName7() {
        return rsv080PropHeaderName7__;
    }
    /**
     * <p>rsv080PropHeaderName7__ をセットします。
     * @param rsv080PropHeaderName7 rsv080PropHeaderName7__
     */
    public void setRsv080PropHeaderName7(String rsv080PropHeaderName7) {
        rsv080PropHeaderName7__ = rsv080PropHeaderName7;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(HttpServletRequest req) throws Exception {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;

        if (StringUtil.isNullZeroStringSpace(rsv080SortRadio__)) {
            msg =
                new ActionMessage("error.select.required.text",
                        gsMsg.getMessage(req, "reserve.src.7"));
            StrutsUtil.addMessage(errors, msg, "rsv080SortRadio");
            return errors;
        }

        return errors;
    }

}