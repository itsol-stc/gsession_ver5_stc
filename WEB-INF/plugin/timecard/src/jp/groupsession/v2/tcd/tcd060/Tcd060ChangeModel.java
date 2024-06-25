package jp.groupsession.v2.tcd.tcd060;

import jp.groupsession.v2.tcd.model.TcdTimezonePriModel;

/**
 * <p>TCD_TIMEZONE_INFO Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Tcd060ChangeModel extends TcdTimezonePriModel {

    /** ユーザ名 */
    private String userName__ = null;
    /** 時間帯変更名 */
    private  String  timeZoneName__ = null;
    /** 変更方法区分 0:デフォルト時間帯変更 1:使用時間帯変更*/
    private int changeKbn = 0;


    /**
     * <p>Default Constructor
     */
    public Tcd060ChangeModel() {
    }


    /**
     * <p>userName を取得します。
     * @return userName
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#userName__
     */
    public String getUserName() {
        return userName__;
    }


    /**
     * <p>userName をセットします。
     * @param userName userName
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#userName__
     */
    public void setUserName(String userName) {
        userName__ = userName;
    }

    /**
     * <p>timeZoneName を取得します。
     * @return timeZoneName
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#timeZoneName__
     */
    public String getTimeZoneName() {
        return timeZoneName__;
    }


    /**
     * <p>timeZoneName をセットします。
     * @param timeZoneName timeZoneName
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#timeZoneName__
     */
    public void setTimeZoneName(String timeZoneName) {
        timeZoneName__ = timeZoneName;
    }


    /**
     * <p>changeKbn を取得します。
     * @return changeKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ChangeModel#changeKbn
     */
    public int getChangeKbn() {
        return changeKbn;
    }


    /**
     * <p>changeKbn をセットします。
     * @param changeKbn changeKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ChangeModel#changeKbn
     */
    public void setChangeKbn(int changeKbn) {
        this.changeKbn = changeKbn;
    }

}
