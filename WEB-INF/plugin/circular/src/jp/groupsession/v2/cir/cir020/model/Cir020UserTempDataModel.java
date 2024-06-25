package jp.groupsession.v2.cir.cir020.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 回覧板の添付ファイル一括ダウンロード時の１ユーザ分のデータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir020UserTempDataModel extends AbstractModel {

    /** ユーザディレクトリ名 */
    private String userDirName__ = null;
    /** ユーザ添付情報 */
    private List<CmnBinfModel> userTmpFileList__ = null;
    /**
     * <p>userDirName を取得します。
     * @return userDirName
     */
    public String getUserDirName() {
        return userDirName__;
    }
    /**
     * <p>userDirName をセットします。
     * @param userDirName userDirName
     */
    public void setUserDirName(String userDirName) {
        userDirName__ = userDirName;
    }
    /**
     * <p>userTmpFileList を取得します。
     * @return userTmpFileList
     */
    public List<CmnBinfModel> getUserTmpFileList() {
        return userTmpFileList__;
    }
    /**
     * <p>userTmpFileList をセットします。
     * @param userTmpFileList userTmpFileList
     */
    public void setUserTmpFileList(List<CmnBinfModel> userTmpFileList) {
        userTmpFileList__ = userTmpFileList;
    }
}
