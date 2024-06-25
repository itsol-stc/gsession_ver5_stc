package jp.groupsession.v2.cht.cht120;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020ParamModel;

/**
 *
 * <br>[機  能] チャット 通知設定のパラメータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht120ParamModel extends Cht020ParamModel {
    /** 受信時プッシュ通知 */
    private int cht120Push__ = GSConstChat.CHAT_PUSH_FLG_YES;
    /** 通知表示時間 */
    private int cht120DspSecond__ = GSConstChat.CHAT_PUSH_DEFAULT_TIME;
    /** 時間リスト */
    private List<LabelValueBean> cht120TimeList__ = null;

    /**
     * <p>cht120Push を取得します。
     * @return cht120Push
     * @see jp.groupsession.v2.cht.cht120.Cht120ParamModel#cht120Push__
     */
    public int getCht120Push() {
        return cht120Push__;
    }
    /**
     * <p>cht120Push をセットします。
     * @param cht120Push cht120Push
     * @see jp.groupsession.v2.cht.cht120.Cht120ParamModel#cht120Push__
     */
    public void setCht120Push(int cht120Push) {
        cht120Push__ = cht120Push;
    }
    /**
     * <p>cht120DspSecond を取得します。
     * @return cht120DspSecond
     * @see jp.groupsession.v2.cht.cht120.Cht120ParamModel#cht120DspSecond__
     */
    public int getCht120DspSecond() {
        return cht120DspSecond__;
    }
    /**
     * <p>cht120DspSecond をセットします。
     * @param cht120DspSecond cht120DspSecond
     * @see jp.groupsession.v2.cht.cht120.Cht120ParamModel#cht120DspSecond__
     */
    public void setCht120DspSecond(int cht120DspSecond) {
        cht120DspSecond__ = cht120DspSecond;
    }
    /**
     * <p>cht120TimeList を取得します。
     * @return cht120TimeList
     * @see jp.groupsession.v2.cht.cht120.Cht120ParamModel#cht120TimeList__
     */
    public List<LabelValueBean> getCht120TimeList() {
        return cht120TimeList__;
    }
    /**
     * <p>cht120TimeList をセットします。
     * @param cht120TimeList cht120TimeList
     * @see jp.groupsession.v2.cht.cht120.Cht120ParamModel#cht120TimeList__
     */
    public void setCht120TimeList(List<LabelValueBean> cht120TimeList) {
        cht120TimeList__ = cht120TimeList;
    }
}
