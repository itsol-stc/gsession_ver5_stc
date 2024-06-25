package jp.groupsession.v2.man.man491;

import java.util.ArrayList;

import jp.groupsession.v2.sch.model.SchDataModel;

/**
 * <br>[機  能] サイボウズLive マイカレンダーインポート用スケジュールモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CybCsvSchModel extends SchDataModel {
    /** 同時登録ユーザ名一覧 */
    private ArrayList<String> unames__ = new ArrayList<String>();

    /** 施設名一覧 */
    private ArrayList<String> snames__ = new ArrayList<String>();

    /**
     *  コンストラクタ
     */
    public CybCsvSchModel() {
        super();
    }

    /**
     * @return unames を戻します。
     */
    public ArrayList<String> getUnames() {
        return unames__;
    }

    /**
     * @param uname 追加する uname。
     */
    public void addUname(String uname) {
        if (!unames__.contains(uname)) {
            unames__.add(uname);
        }
    }

    /**
     * @return Snames を戻します。
     */
    public ArrayList<String> getSnames() {
        return snames__;
    }

    /**
     * @param sname 追加する sname。
     */
    public void addSname(String sname) {
        if (!snames__.contains(sname)) {
            snames__.add(sname);
        }
    }
}
