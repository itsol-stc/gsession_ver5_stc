package jp.groupsession.v2.sch.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュール 選択済のスケジュール(非表示) を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchHidModel extends AbstractModel {
    /** 日付 */
    private UDate date__ = null;
    /** 日付(表示用) */
    private String dateStr__ = null;
    /** 対象のID(選択用) */
    private String id__ = null;
    /** ユーザ/グループ名称 */
    private String name__ = null;

    /** ユーザ/グループSID */
    private int sid__ = 0;
    /** ユーザ区分(0:ユーザ、1:グループ) */
    private int usrKbn__ = 0;
    /** ユーザ 写真画像表示/非表示区分 */
    private int photoKbn__ = 0;
    /** ユーザ 写真画像 画像ファイルのSID */
    private long photoSid__ = 0;
    /** ユーザ ログイン停止フラグ */
    private int usrUkoFlg__ = 0;

    /**
     * <p>date を取得します。
     * @return date
     * @see jp.groupsession.v2.sch.model.SchHidModel#date__
     */
    public UDate getDate() {
        return date__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     * @see jp.groupsession.v2.sch.model.SchHidModel#date__
     */
    public void setDate(UDate date) {
        date__ = date;
    }
    /**
     * <p>dateStr を取得します。
     * @return dateStr
     * @see jp.groupsession.v2.sch.model.SchHidModel#dateStr__
     */
    public String getDateStr() {
        return dateStr__;
    }
    /**
     * <p>dateStr をセットします。
     * @param dateStr dateStr
     * @see jp.groupsession.v2.sch.model.SchHidModel#dateStr__
     */
    public void setDateStr(String dateStr) {
        dateStr__ = dateStr;
    }
    /**
     * <p>id を取得します。
     * @return id
     * @see jp.groupsession.v2.sch.model.SchHidModel#id__
     */
    public String getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     * @see jp.groupsession.v2.sch.model.SchHidModel#id__
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.sch.model.SchHidModel#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.sch.model.SchHidModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>sid を取得します。
     * @return sid
     * @see jp.groupsession.v2.sch.model.SchHidModel#sid__
     */
    public int getSid() {
        return sid__;
    }
    /**
     * <p>sid をセットします。
     * @param sid sid
     * @see jp.groupsession.v2.sch.model.SchHidModel#sid__
     */
    public void setSid(int sid) {
        sid__ = sid;
    }
    /**
     * <p>usrKbn を取得します。
     * @return usrKbn
     * @see jp.groupsession.v2.sch.model.SchHidModel#usrKbn__
     */
    public int getUsrKbn() {
        return usrKbn__;
    }
    /**
     * <p>usrKbn をセットします。
     * @param usrKbn usrKbn
     * @see jp.groupsession.v2.sch.model.SchHidModel#usrKbn__
     */
    public void setUsrKbn(int usrKbn) {
        usrKbn__ = usrKbn;
    }
    /**
     * <p>photoKbn を取得します。
     * @return photoKbn
     * @see jp.groupsession.v2.sch.model.SchHidModel#photoKbn__
     */
    public int getPhotoKbn() {
        return photoKbn__;
    }
    /**
     * <p>photoKbn をセットします。
     * @param photoKbn photoKbn
     * @see jp.groupsession.v2.sch.model.SchHidModel#photoKbn__
     */
    public void setPhotoKbn(int photoKbn) {
        photoKbn__ = photoKbn;
    }
    /**
     * <p>photoSid を取得します。
     * @return photoSid
     * @see jp.groupsession.v2.sch.model.SchHidModel#photoSid__
     */
    public long getPhotoSid() {
        return photoSid__;
    }
    /**
     * <p>photoSid をセットします。
     * @param photoSid photoSid
     * @see jp.groupsession.v2.sch.model.SchHidModel#photoSid__
     */
    public void setPhotoSid(long photoSid) {
        photoSid__ = photoSid;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     * @see jp.groupsession.v2.sch.model.SchHidModel#usrUkoFlg__
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     * @see jp.groupsession.v2.sch.model.SchHidModel#usrUkoFlg__
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
}