package jp.groupsession.v2.ntp.ntp132kn;

import java.util.List;

import jp.groupsession.v2.ntp.model.NtpShohinModel;
import jp.groupsession.v2.ntp.ntp132.Ntp132Form;

/**
 * <br>[機  能] 日報 商品インポート確認画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp132knForm extends Ntp132Form {

    /** 取込みファイル名 */
    private String ntp132knFileName__ = null;
    /** 取込み予定グループ情報 */
    private List<NtpShohinModel> ntp132knImpList__ = null;
    /** カテゴリ名 */
    private String ntp132knCategoryName__ = null;
    /**
     * <p>ntp132knFileName を取得します。
     * @return ntp132knFileName
     */
    public String getNtp132knFileName() {
        return ntp132knFileName__;
    }
    /**
     * <p>ntp132knFileName をセットします。
     * @param ntp132knFileName ntp132knFileName
     */
    public void setNtp132knFileName(String ntp132knFileName) {
        ntp132knFileName__ = ntp132knFileName;
    }
    /**
     * <p>ntp132knImpList を取得します。
     * @return ntp132knImpList
     */
    public List<NtpShohinModel> getNtp132knImpList() {
        return ntp132knImpList__;
    }
    /**
     * <p>ntp132knImpList をセットします。
     * @param ntp132knImpList ntp132knImpList
     */
    public void setNtp132knImpList(List<NtpShohinModel> ntp132knImpList) {
        ntp132knImpList__ = ntp132knImpList;
    }
    /**
     * <p>ntp132knCategoryName を取得します。
     * @return ntp132knCategoryName
     */
    public String getNtp132knCategoryName() {
        return ntp132knCategoryName__;
    }
    /**
     * <p>ntp132knCategoryName をセットします。
     * @param ntp132knCategoryName ntp132knCategoryName
     */
    public void setNtp132knCategoryName(String ntp132knCategoryName) {
        ntp132knCategoryName__ = ntp132knCategoryName;
    }

}
