package jp.groupsession.v2.ntp.ntp086;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] 日報 日報テンプレート一覧画面の表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp086UsrInfModel implements Serializable {

    /** 最大ページサイズ */
    private int maxPageSize__ = 0;
    /** ページ番号 */
    private int pageNum__ = 0;
    /** ユーザ情報 */
    private List<CmnUsrmInfModel> usrInfDataList__ = new ArrayList<CmnUsrmInfModel>();
    /**
     * <p>maxPageSize を取得します。
     * @return maxPageSize
     */
    public int getMaxPageSize() {
        return maxPageSize__;
    }
    /**
     * <p>maxPageSize をセットします。
     * @param maxPageSize maxPageSize
     */
    public void setMaxPageSize(int maxPageSize) {
        maxPageSize__ = maxPageSize;
    }
    /**
     * <p>pageNum を取得します。
     * @return pageNum
     */
    public int getPageNum() {
        return pageNum__;
    }
    /**
     * <p>pageNum をセットします。
     * @param pageNum pageNum
     */
    public void setPageNum(int pageNum) {
        pageNum__ = pageNum;
    }
    /**
     * <p>usrInfDataList を取得します。
     * @return usrInfDataList
     */
    public List<CmnUsrmInfModel> getUsrInfDataList() {
        return usrInfDataList__;
    }
    /**
     * <p>usrInfDataList をセットします。
     * @param usrInfDataList usrInfDataList
     */
    public void setUsrInfDataList(List<CmnUsrmInfModel> usrInfDataList) {
        usrInfDataList__ = usrInfDataList;
    }

}
