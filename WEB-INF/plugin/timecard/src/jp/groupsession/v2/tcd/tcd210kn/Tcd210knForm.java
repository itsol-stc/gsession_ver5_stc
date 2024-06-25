package jp.groupsession.v2.tcd.tcd210kn;

import java.util.List;

import jp.groupsession.v2.tcd.tcd210.Tcd210Form;

/**
 * <br>[機  能] タイムカード 有休日数インポート確認画面のフォームクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210knForm extends Tcd210Form {

    /** 取り込みファイル名 */
    private String tcd210knFileName__ = null;
    
    /** 画面表示情報 */
    List<Tcd210knInsertModel> dspList = null;
    
    /**
     * <p>tcd210knFileName を取得します。
     * @return tcd210knFileName
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knForm#tcd210knFileName__
     */
    public String getTcd210knFileName() {
        return tcd210knFileName__;
    }

    /**
     * <p>tcd210knFileName をセットします。
     * @param tcd210knFileName tcd210knFileName
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knForm#tcd210knFileName__
     */
    public void setTcd210knFileName(String tcd210knFileName) {
        tcd210knFileName__ = tcd210knFileName;
    }

    /**
     * <p>dspList を取得します。
     * @return dspList
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knForm#dspList
     */
    public List<Tcd210knInsertModel> getDspList() {
        return dspList;
    }

    /**
     * <p>dspList をセットします。
     * @param dspList dspList
     * @see jp.groupsession.v2.tcd.tcd210kn.Tcd210knForm#dspList
     */
    public void setDspList(List<Tcd210knInsertModel> dspList) {
        this.dspList = dspList;
    }
}
