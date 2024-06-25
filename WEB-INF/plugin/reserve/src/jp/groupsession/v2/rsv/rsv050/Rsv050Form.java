package jp.groupsession.v2.rsv.rsv050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.rsv030.Rsv030Form;

/**
 * <br>[機  能] 施設予約 施設グループ情報設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv050Form extends Rsv030Form {

    /** 施設グループリスト */
    private ArrayList<Rsv050Model> rsv050GroupList__ = null;
    /** ラジオ選択値 */
    private String rsv050SortRadio__ = null;
    /** 画面表示全キーリスト */
    private String[] rsv050KeyList__ = null;
    /** 編集対象施設グループSID */
    private int rsv050EditGrpSid__ = -1;

    /**
     * <p>rsv050SortRadio__ を取得します。
     * @return rsv050SortRadio
     */
    public String getRsv050SortRadio() {
        return rsv050SortRadio__;
    }
    /**
     * <p>rsv050SortRadio__ をセットします。
     * @param rsv050SortRadio rsv050SortRadio__
     */
    public void setRsv050SortRadio(String rsv050SortRadio) {
        rsv050SortRadio__ = rsv050SortRadio;
    }
    /**
     * <p>rsv050GroupList__ を取得します。
     * @return rsv050GroupList
     */
    public ArrayList<Rsv050Model> getRsv050GroupList() {
        return rsv050GroupList__;
    }
    /**
     * <p>rsv050GroupList__ をセットします。
     * @param rsv050GroupList rsv050GroupList__
     */
    public void setRsv050GroupList(ArrayList<Rsv050Model> rsv050GroupList) {
        rsv050GroupList__ = rsv050GroupList;
    }
    /**
     * <p>rsv050KeyList__ を取得します。
     * @return rsv050KeyList
     */
    public String[] getRsv050KeyList() {
        return rsv050KeyList__;
    }
    /**
     * <p>rsv050KeyList__ をセットします。
     * @param rsv050KeyList rsv050KeyList__
     */
    public void setRsv050KeyList(String[] rsv050KeyList) {
        rsv050KeyList__ = rsv050KeyList;
    }
    /**
     * <p>rsv050EditGrpSid__ を取得します。
     * @return rsv050EditGrpSid
     */
    public int getRsv050EditGrpSid() {
        return rsv050EditGrpSid__;
    }
    /**
     * <p>rsv050EditGrpSid__ をセットします。
     * @param rsv050EditGrpSid rsv050EditGrpSid__
     */
    public void setRsv050EditGrpSid(int rsv050EditGrpSid) {
        rsv050EditGrpSid__ = rsv050EditGrpSid;
    }
}