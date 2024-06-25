package jp.groupsession.v2.ptl.ptl130;

import jp.groupsession.v2.ptl.ptl020.Ptl020Form;

/**
 * <br>[機  能] ポータル 権限設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl130Form extends Ptl020Form {

    /** ポータルタブ順序権限 */
    private int ptl130editKbn__ = 0;
    /** 初期表示フラグ */
    private int ptl130init__ = 0;

    /**
     * @return ptl130editKbn
     */
    public int getPtl130editKbn() {
        return ptl130editKbn__;
    }

    /**
     * @param ptl130editKbn セットする ptl130editKbn
     */
    public void setPtl130editKbn(int ptl130editKbn) {
        ptl130editKbn__ = ptl130editKbn;
    }

    /**
     * @return ptl130init
     */
    public int getPtl130init() {
        return ptl130init__;
    }

    /**
     * @param ptl130init セットする ptl130init
     */
    public void setPtl130init(int ptl130init) {
        ptl130init__ = ptl130init;
    }

}