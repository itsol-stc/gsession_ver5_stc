package jp.groupsession.v2.sch.sch250;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sch.model.SchMyviewlistModel;
import jp.groupsession.v2.sch.sch100.Sch100ParamModel;

/**
 * <br>[機  能] 表示リスト設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch250ParamModel extends Sch100ParamModel {

    /** 表示リスト一覧 */
    private List<SchMyviewlistModel> sch250ViewList__ = null;
    /** ラジオ選択値 */
    private String sch250SortRadio__ = null;
    /** 編集モード */
    private int sch250editMode__ = GSConst.CMDMODE_ADD;
    /** 編集対象SID */
    private int sch250editData__ = 0;

    /**
     * <p>sch250ViewList を取得します。
     * @return sch250ViewList
     * @see jp.groupsession.v2.sch.sch250.Sch250Form#sch250ViewList__
     */
    public List<SchMyviewlistModel> getSch250ViewList() {
        return sch250ViewList__;
    }

    /**
     * <p>sch250ViewList をセットします。
     * @param sch250ViewList sch250ViewList
     * @see jp.groupsession.v2.sch.sch250.Sch250Form#sch250ViewList__
     */
    public void setSch250ViewList(List<SchMyviewlistModel> sch250ViewList) {
        sch250ViewList__ = sch250ViewList;
    }
    
    /**
     * <p>sch250SortRadio を取得します。
     * @return sch250SortRadio
     * @see jp.groupsession.v2.sch.sch250.Sch250Form#sch250SortRadio__
     */
    public String getSch250SortRadio() {
        return sch250SortRadio__;
    }

    /**
     * <p>sch250SortRadio をセットします。
     * @param sch250SortRadio sch250SortRadio
     * @see jp.groupsession.v2.sch.sch250.Sch250Form#sch250SortRadio__
     */
    public void setSch250SortRadio(String sch250SortRadio) {
        sch250SortRadio__ = sch250SortRadio;
    }

    /**
     * <p>sch250editMode を取得します。
     * @return sch250editMode
     * @see jp.groupsession.v2.sch.sch250.Sch250ParamModel#sch250editMode__
     */
    public int getSch250editMode() {
        return sch250editMode__;
    }

    /**
     * <p>sch250editMode をセットします。
     * @param sch250editMode sch250editMode
     * @see jp.groupsession.v2.sch.sch250.Sch250ParamModel#sch250editMode__
     */
    public void setSch250editMode(int sch250editMode) {
        sch250editMode__ = sch250editMode;
    }

    /**
     * <p>sch250editData を取得します。
     * @return sch250editData
     * @see jp.groupsession.v2.sch.sch250.Sch250ParamModel#sch250editData__
     */
    public int getSch250editData() {
        return sch250editData__;
    }

    /**
     * <p>sch250editData をセットします。
     * @param sch250editData sch250editData
     * @see jp.groupsession.v2.sch.sch250.Sch250ParamModel#sch250editData__
     */
    public void setSch250editData(int sch250editData) {
        sch250editData__ = sch250editData;
    }
    
}
