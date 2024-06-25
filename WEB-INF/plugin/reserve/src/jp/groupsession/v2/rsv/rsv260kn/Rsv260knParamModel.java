package jp.groupsession.v2.rsv.rsv260kn;

import java.util.List;

import jp.groupsession.v2.rsv.rsv260.Rsv260ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 施設予約 グループ・施設一括登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv260knParamModel extends Rsv260ParamModel {

    /** 取込みファイル名称 */
    private String rsv260knFileName__ = null;
    /** 施設区分名称 */
    private String rsv260knSelectedSisetuName__ = null;
    /** 管理者ユーザリスト */
    private List<UsrLabelValueBean> rsv260knKanriUser__ = null;

    /**
     * <p>rsv260knKanriUser を取得します。
     * @return rsv260knKanriUser
     */
    public List<UsrLabelValueBean> getRsv260knKanriUser() {
        return rsv260knKanriUser__;
    }
    /**
     * <p>rsv260knKanriUser をセットします。
     * @param rsv260knKanriUser rsv260knKanriUser
     */
    public void setRsv260knKanriUser(List<UsrLabelValueBean> rsv260knKanriUser) {
        rsv260knKanriUser__ = rsv260knKanriUser;
    }
    /**
     * <p>rsv260knSelectedSisetuName を取得します。
     * @return rsv260knSelectedSisetuName
     */
    public String getRsv260knSelectedSisetuName() {
        return rsv260knSelectedSisetuName__;
    }
    /**
     * <p>rsv260knSelectedSisetuName をセットします。
     * @param rsv260knSelectedSisetuName rsv260knSelectedSisetuName
     */
    public void setRsv260knSelectedSisetuName(String rsv260knSelectedSisetuName) {
        rsv260knSelectedSisetuName__ = rsv260knSelectedSisetuName;
    }
    /**
     * <p>rsv260knFileName を取得します。
     * @return rsv260knFileName
     */
    public String getRsv260knFileName() {
        return rsv260knFileName__;
    }
    /**
     * <p>rsv260knFileName をセットします。
     * @param rsv260knFileName rsv260knFileName
     */
    public void setRsv260knFileName(String rsv260knFileName) {
        rsv260knFileName__ = rsv260knFileName;
    }
}