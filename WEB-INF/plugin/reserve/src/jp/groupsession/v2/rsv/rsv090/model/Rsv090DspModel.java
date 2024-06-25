package jp.groupsession.v2.rsv.rsv090.model;

import java.util.List;

import jp.groupsession.v2.rsv.model.RsvSisDataModel;

/**
 * <br>[機  能] 施設予約 施設登録画面の結果情報(表示用)を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv090DspModel extends RsvSisDataModel {

    /** 添付ファイル情報(施設画像用) */
    private List <Rsv090BinfModel> tmpFileSisetuList__ = null;
    /** 添付ファイル情報(場所画像用) */
    private List <Rsv090BinfModel> tmpFilePlaceList__ = null;

    /**
     * <p>Default Constructor
     */
    public Rsv090DspModel() {
    }

    /**
     * <p>tmpFilePlaceList を取得します。
     * @return tmpFilePlaceList
     */
    public List<Rsv090BinfModel> getTmpFilePlaceList() {
        return tmpFilePlaceList__;
    }

    /**
     * <p>tmpFilePlaceList をセットします。
     * @param tmpFilePlaceList tmpFilePlaceList
     */
    public void setTmpFilePlaceList(List<Rsv090BinfModel> tmpFilePlaceList) {
        tmpFilePlaceList__ = tmpFilePlaceList;
    }

    /**
     * <p>tmpFileSisetuList を取得します。
     * @return tmpFileSisetuList
     */
    public List<Rsv090BinfModel> getTmpFileSisetuList() {
        return tmpFileSisetuList__;
    }

    /**
     * <p>tmpFileSisetuList をセットします。
     * @param tmpFileSisetuList tmpFileSisetuList
     */
    public void setTmpFileSisetuList(List<Rsv090BinfModel> tmpFileSisetuList) {
        tmpFileSisetuList__ = tmpFileSisetuList;
    }
}