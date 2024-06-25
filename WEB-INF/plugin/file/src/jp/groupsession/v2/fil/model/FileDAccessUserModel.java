package jp.groupsession.v2.fil.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * キャビネット詳細アクセス制御一覧表示用のモデル
 * @author JTS
 *
 */
public class FileDAccessUserModel extends CmnUsrmInfModel {

    /** ディレクトリアクセス区分 */
    private int accessKbn__;

    /**
     * @return accessKbn
     */
    public int getAccessKbn() {
        return accessKbn__;
    }
    /**
     * @param accessKbn 設定する accessKbn
     */
    public void setAccessKbn(int accessKbn) {
        accessKbn__ = accessKbn;
    }
}
