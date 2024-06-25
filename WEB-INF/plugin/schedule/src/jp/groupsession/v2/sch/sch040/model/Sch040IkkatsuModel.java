package jp.groupsession.v2.sch.sch040.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 一括登録対象表示用情報の格納クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040IkkatsuModel {

    /** 日付 */
    private String dayStr__ = null;
    /** 表示ユーザ名，グループ名 */
    private List<UsrLabelValueBean> targetName__ = null;
    
    /**
     * <p>dayStr を取得します。
     * @return dayStr
     * @see jp.groupsession.v2.sch.sch040.model.Sch040IkkatsuModel#dayStr__
     */
    public String getDayStr() {
        return dayStr__;
    }
    /**
     * <p>dayStr をセットします。
     * @param dayStr dayStr
     * @see jp.groupsession.v2.sch.sch040.model.Sch040IkkatsuModel#dayStr__
     */
    public void setDayStr(String dayStr) {
        dayStr__ = dayStr;
    }
    /**
     * <p>targetName を取得します。
     * @return targetName
     * @see jp.groupsession.v2.sch.sch040.model.Sch040IkkatsuModel#targetName__
     */
    public List<UsrLabelValueBean> getTargetName() {
        return targetName__;
    }
    /**
     * <p>targetName をセットします。
     * @param targetName targetName
     * @see jp.groupsession.v2.sch.sch040.model.Sch040IkkatsuModel#targetName__
     */
    public void setTargetName(List<UsrLabelValueBean> targetName) {
        targetName__ = targetName;
    }
}
