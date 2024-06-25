package jp.groupsession.v2.man.man491;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
/**
 *
 * <br>[機  能] マイカレンダーインポート画面 param
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man491ParamModel extends AbstractParamModel {
    /** テンポラリファイル選択*/
    private String[] man491selectFiles__;

    /** 表示用ファイル名リスト*/
    private List<LabelValueBean> man491FileLabelList__;

    /**
     * <p>man491selectFiles を取得します。
     * @return man491selectFiles
     * @see jp.groupsession.v2.man.man491.Man491ParamModel#man491selectFiles__
     */
    public String[] getMan491selectFiles() {
        return man491selectFiles__;
    }

    /**
     * <p>man491selectFiles をセットします。
     * @param man491selectFiles man491selectFiles
     * @see jp.groupsession.v2.man.man491.Man491ParamModel#man491selectFiles__
     */
    public void setMan491selectFiles(String[] man491selectFiles) {
        man491selectFiles__ = man491selectFiles;
    }

    /**
     * <p>man491FileLabelList を取得します。
     * @return man491FileLabelList
     * @see jp.groupsession.v2.man.man491.Man491ParamModel#man491FileLabelList__
     */
    public List<LabelValueBean> getMan491FileLabelList() {
        return man491FileLabelList__;
    }

    /**
     * <p>man491FileLabelList をセットします。
     * @param man491FileLabelList man491FileLabelList
     * @see jp.groupsession.v2.man.man491.Man491ParamModel#man491FileLabelList__
     */
    public void setMan491FileLabelList(List<LabelValueBean> man491FileLabelList) {
        man491FileLabelList__ = man491FileLabelList;
    }

}
