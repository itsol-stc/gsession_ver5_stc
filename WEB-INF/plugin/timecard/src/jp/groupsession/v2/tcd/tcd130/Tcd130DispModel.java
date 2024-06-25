package jp.groupsession.v2.tcd.tcd130;

import java.util.List;

import jp.groupsession.v2.tcd.model.TcdTimezonePriModel;

/**
 * <p>Tcd130DispModel Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Tcd130DispModel extends TcdTimezonePriModel {

    /** ユーザ名 */
    private String userName__ = null;
    /** ユーザ有効フラグ */
    private int userUkoFLg__ = -1;
    /** 時間帯選択名 */
    private  String  timeZoneName__ = null;
    /** 時間帯選択名 */
    private  List<Tcd130DispModel>  timeZoneNames__ = null;
    /** 時間帯選択可能フラグ */
    private int timeZoneUkoFlg__ = -1;
    /** デフォルト時間帯選択名 */
    private String defaultTimeZoneName__ = null;


    /**
     * <p>Default Constructor
     */
    public Tcd130DispModel() {
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
     * <p>userUkoFLg を取得します。
     * @return userUkoFLg
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#userUkoFLg__
     */
    public int getUserUkoFLg() {
        return userUkoFLg__;
    }


    /**
     * <p>userUkoFLg をセットします。
     * @param userUkoFLg userUkoFLg
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#userUkoFLg__
     */
    public void setUserUkoFLg(int userUkoFLg) {
        userUkoFLg__ = userUkoFLg;
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
     * <p>timeZoneNames を取得します。
     * @return timeZoneNames
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#timeZoneNames__
     */
    public List<Tcd130DispModel> getTimeZoneNames() {
        return timeZoneNames__;
    }


    /**
     * <p>timeZoneNames をセットします。
     * @param timeZoneNames timeZoneNames
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#timeZoneNames__
     */
    public void setTimeZoneNames(List<Tcd130DispModel> timeZoneNames) {
        timeZoneNames__ = timeZoneNames;
    }


    /**
     * <p>timeZoneUkoFlg を取得します。
     * @return timeZoneUkoFlg
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#timeZoneUkoFlg__
     */
    public int getTimeZoneUkoFlg() {
        return timeZoneUkoFlg__;
    }


    /**
     * <p>timeZoneUkoFlg をセットします。
     * @param timeZoneUkoFlg timeZoneUkoFlg
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#timeZoneUkoFlg__
     */
    public void setTimeZoneUkoFlg(int timeZoneUkoFlg) {
        timeZoneUkoFlg__ = timeZoneUkoFlg;
    }


    /**
     * <p>defaultTimeZoneName を取得します。
     * @return defaultTimeZoneName
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#defaultTimeZoneName__
     */
    public String getDefaultTimeZoneName() {
        return defaultTimeZoneName__;
    }


    /**
     * <p>defaultTimeZoneName をセットします。
     * @param defaultTimeZoneName defaultTimeZoneName
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130DispModel#defaultTimeZoneName__
     */
    public void setDefaultTimeZoneName(String defaultTimeZoneName) {
        defaultTimeZoneName__ = defaultTimeZoneName;
    }



}
