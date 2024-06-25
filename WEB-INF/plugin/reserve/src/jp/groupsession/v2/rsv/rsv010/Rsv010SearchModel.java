package jp.groupsession.v2.rsv.rsv010;

import java.io.Serializable;

/**
 * <br>[機  能] 施設予約 施設利用状況照会画面の検索結果を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv010SearchModel implements Serializable {

    /** キーワード*/
    private String sisetuKeyword__ = null;
    /** キーワード区分*/
    private int keyWordkbn__;
    /** キーワード対象（資産管理番号)*/
    private int sisetuKeywordSisan__;
    /** キーワード対象（施設名)*/
    private int sisetuKeywordSisetu__;
    /** キーワード対象（備考)*/
    private int sisetuKeywordBiko__;
    /** キーワード対象（ナンバー)*/
    private int sisetuKeywordNo__;
    /** キーワード対象（ISBN)*/
    private int sisetuKeywordIsbn__;
    /** 空き状況*/
    private int sisetuFree__;
    /** 空き範囲開始年*/
    private int sisetuFreeFromY__;
    /** 空き範囲開始月*/
    private int sisetuFreeFromMo__;
    /** 空き範囲開始日*/
    private int sisetuFreeFromD__;
    /** 空き範囲開始時*/
    private int sisetuFreeFromH__;
    /** 空き範囲開始分*/
    private int sisetuFreeFromMi__;
    /** 空き範囲終了年*/
    private int sisetuFreeToY__;
    /** 空き範囲終了月*/
    private int sisetuFreeToMo__;
    /** 空き範囲終了日*/
    private int sisetuFreeToD__;
    /** 空き範囲終了時*/
    private int sisetuFreeToH__;
    /** 空き範囲終了分*/
    private int sisetuFreeToMi__;
    /** 施設区分*/
    private int sisetuKbn__;
    /** グループ*/
    private int grpNarrowDown__;
    /** 喫煙*/
    private int sisetuSmoky__;
    /** 座席数*/
    private String sisetuChere__;
    /** 社外持ち出し*/
    private int sisetuTakeout__;

    /**
     * <p>sisetuKeyword を取得します。
     * @return sisetuKeyword
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeyword__
     */
    public String getSisetuKeyword() {
        return sisetuKeyword__;
    }
    /**
     * <p>sisetuKeyword をセットします。
     * @param sisetuKeyword sisetuKeyword
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeyword__
     */
    public void setSisetuKeyword(String sisetuKeyword) {
        sisetuKeyword__ = sisetuKeyword;
    }
    /**
     * <p>keyWordkbn を取得します。
     * @return keyWordkbn
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#keyWordkbn__
     */
    public int getKeyWordkbn() {
        return keyWordkbn__;
    }
    /**
     * <p>keyWordkbn をセットします。
     * @param keyWordkbn keyWordkbn
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#keyWordkbn__
     */
    public void setKeyWordkbn(int keyWordkbn) {
        keyWordkbn__ = keyWordkbn;
    }
    /**
     * <p>sisetuKeywordSisan を取得します。
     * @return sisetuKeywordSisan
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordSisan__
     */
    public int getSisetuKeywordSisan() {
        return sisetuKeywordSisan__;
    }
    /**
     * <p>sisetuKeywordSisan をセットします。
     * @param sisetuKeywordSisan sisetuKeywordSisan
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordSisan__
     */
    public void setSisetuKeywordSisan(int sisetuKeywordSisan) {
        sisetuKeywordSisan__ = sisetuKeywordSisan;
    }
    /**
     * <p>sisetuKeywordSisetu を取得します。
     * @return sisetuKeywordSisetu
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordSisetu__
     */
    public int getSisetuKeywordSisetu() {
        return sisetuKeywordSisetu__;
    }
    /**
     * <p>sisetuKeywordSisetu をセットします。
     * @param sisetuKeywordSisetu sisetuKeywordSisetu
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordSisetu__
     */
    public void setSisetuKeywordSisetu(int sisetuKeywordSisetu) {
        sisetuKeywordSisetu__ = sisetuKeywordSisetu;
    }
    /**
     * <p>sisetuKeywordBiko を取得します。
     * @return sisetuKeywordBiko
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordBiko__
     */
    public int getSisetuKeywordBiko() {
        return sisetuKeywordBiko__;
    }
    /**
     * <p>sisetuKeywordBiko をセットします。
     * @param sisetuKeywordBiko sisetuKeywordBiko
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordBiko__
     */
    public void setSisetuKeywordBiko(int sisetuKeywordBiko) {
        sisetuKeywordBiko__ = sisetuKeywordBiko;
    }
    /**
     * <p>sisetuKeywordNo を取得します。
     * @return sisetuKeywordNo
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordNo__
     */
    public int getSisetuKeywordNo() {
        return sisetuKeywordNo__;
    }
    /**
     * <p>sisetuKeywordNo をセットします。
     * @param sisetuKeywordNo sisetuKeywordNo
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordNo__
     */
    public void setSisetuKeywordNo(int sisetuKeywordNo) {
        sisetuKeywordNo__ = sisetuKeywordNo;
    }
    /**
     * <p>sisetuKeywordIsbn を取得します。
     * @return sisetuKeywordIsbn
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordIsbn__
     */
    public int getSisetuKeywordIsbn() {
        return sisetuKeywordIsbn__;
    }
    /**
     * <p>sisetuKeywordIsbn をセットします。
     * @param sisetuKeywordIsbn sisetuKeywordIsbn
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKeywordIsbn__
     */
    public void setSisetuKeywordIsbn(int sisetuKeywordIsbn) {
        sisetuKeywordIsbn__ = sisetuKeywordIsbn;
    }
    /**
     * <p>sisetuFree を取得します。
     * @return sisetuFree
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFree__
     */
    public int getSisetuFree() {
        return sisetuFree__;
    }
    /**
     * <p>sisetuFree をセットします。
     * @param sisetuFree sisetuFree
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFree__
     */
    public void setSisetuFree(int sisetuFree) {
        sisetuFree__ = sisetuFree;
    }
    /**
     * <p>sisetuFreeFromY を取得します。
     * @return sisetuFreeFromY
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromY__
     */
    public int getSisetuFreeFromY() {
        return sisetuFreeFromY__;
    }
    /**
     * <p>sisetuFreeFromY をセットします。
     * @param sisetuFreeFromY sisetuFreeFromY
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromY__
     */
    public void setSisetuFreeFromY(int sisetuFreeFromY) {
        sisetuFreeFromY__ = sisetuFreeFromY;
    }
    /**
     * <p>sisetuFreeFromMo を取得します。
     * @return sisetuFreeFromMo
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromMo__
     */
    public int getSisetuFreeFromMo() {
        return sisetuFreeFromMo__;
    }
    /**
     * <p>sisetuFreeFromMo をセットします。
     * @param sisetuFreeFromMo sisetuFreeFromMo
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromMo__
     */
    public void setSisetuFreeFromMo(int sisetuFreeFromMo) {
        sisetuFreeFromMo__ = sisetuFreeFromMo;
    }
    /**
     * <p>sisetuFreeFromD を取得します。
     * @return sisetuFreeFromD
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromD__
     */
    public int getSisetuFreeFromD() {
        return sisetuFreeFromD__;
    }
    /**
     * <p>sisetuFreeFromD をセットします。
     * @param sisetuFreeFromD sisetuFreeFromD
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromD__
     */
    public void setSisetuFreeFromD(int sisetuFreeFromD) {
        sisetuFreeFromD__ = sisetuFreeFromD;
    }
    /**
     * <p>sisetuFreeFromH を取得します。
     * @return sisetuFreeFromH
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromH__
     */
    public int getSisetuFreeFromH() {
        return sisetuFreeFromH__;
    }
    /**
     * <p>sisetuFreeFromH をセットします。
     * @param sisetuFreeFromH sisetuFreeFromH
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromH__
     */
    public void setSisetuFreeFromH(int sisetuFreeFromH) {
        sisetuFreeFromH__ = sisetuFreeFromH;
    }
    /**
     * <p>sisetuFreeFromMi を取得します。
     * @return sisetuFreeFromMi
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromMi__
     */
    public int getSisetuFreeFromMi() {
        return sisetuFreeFromMi__;
    }
    /**
     * <p>sisetuFreeFromMi をセットします。
     * @param sisetuFreeFromMi sisetuFreeFromMi
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeFromMi__
     */
    public void setSisetuFreeFromMi(int sisetuFreeFromMi) {
        sisetuFreeFromMi__ = sisetuFreeFromMi;
    }
    /**
     * <p>sisetuFreeToY を取得します。
     * @return sisetuFreeToY
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToY__
     */
    public int getSisetuFreeToY() {
        return sisetuFreeToY__;
    }
    /**
     * <p>sisetuFreeToY をセットします。
     * @param sisetuFreeToY sisetuFreeToY
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToY__
     */
    public void setSisetuFreeToY(int sisetuFreeToY) {
        sisetuFreeToY__ = sisetuFreeToY;
    }
    /**
     * <p>sisetuFreeToMo を取得します。
     * @return sisetuFreeToMo
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToMo__
     */
    public int getSisetuFreeToMo() {
        return sisetuFreeToMo__;
    }
    /**
     * <p>sisetuFreeToMo をセットします。
     * @param sisetuFreeToMo sisetuFreeToMo
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToMo__
     */
    public void setSisetuFreeToMo(int sisetuFreeToMo) {
        sisetuFreeToMo__ = sisetuFreeToMo;
    }
    /**
     * <p>sisetuFreeToD を取得します。
     * @return sisetuFreeToD
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToD__
     */
    public int getSisetuFreeToD() {
        return sisetuFreeToD__;
    }
    /**
     * <p>sisetuFreeToD をセットします。
     * @param sisetuFreeToD sisetuFreeToD
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToD__
     */
    public void setSisetuFreeToD(int sisetuFreeToD) {
        sisetuFreeToD__ = sisetuFreeToD;
    }
    /**
     * <p>sisetuFreeToH を取得します。
     * @return sisetuFreeToH
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToH__
     */
    public int getSisetuFreeToH() {
        return sisetuFreeToH__;
    }
    /**
     * <p>sisetuFreeToH をセットします。
     * @param sisetuFreeToH sisetuFreeToH
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToH__
     */
    public void setSisetuFreeToH(int sisetuFreeToH) {
        sisetuFreeToH__ = sisetuFreeToH;
    }
    /**
     * <p>sisetuFreeToMi を取得します。
     * @return sisetuFreeToMi
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToMi__
     */
    public int getSisetuFreeToMi() {
        return sisetuFreeToMi__;
    }
    /**
     * <p>sisetuFreeToMi をセットします。
     * @param sisetuFreeToMi sisetuFreeToMi
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuFreeToMi__
     */
    public void setSisetuFreeToMi(int sisetuFreeToMi) {
        sisetuFreeToMi__ = sisetuFreeToMi;
    }
    /**
     * <p>sisetuKbn を取得します。
     * @return sisetuKbn
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKbn__
     */
    public int getSisetuKbn() {
        return sisetuKbn__;
    }
    /**
     * <p>sisetuKbn をセットします。
     * @param sisetuKbn sisetuKbn
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuKbn__
     */
    public void setSisetuKbn(int sisetuKbn) {
        sisetuKbn__ = sisetuKbn;
    }
    /**
     * <p>grpNarrowDown を取得します。
     * @return grpNarrowDown
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#grpNarrowDown__
     */
    public int getGrpNarrowDown() {
        return grpNarrowDown__;
    }
    /**
     * <p>grpNarrowDown をセットします。
     * @param grpNarrowDown grpNarrowDown
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#grpNarrowDown__
     */
    public void setGrpNarrowDown(int grpNarrowDown) {
        grpNarrowDown__ = grpNarrowDown;
    }
    /**
     * <p>sisetuSmoky を取得します。
     * @return sisetuSmoky
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuSmoky__
     */
    public int getSisetuSmoky() {
        return sisetuSmoky__;
    }
    /**
     * <p>sisetuSmoky をセットします。
     * @param sisetuSmoky sisetuSmoky
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuSmoky__
     */
    public void setSisetuSmoky(int sisetuSmoky) {
        sisetuSmoky__ = sisetuSmoky;
    }
    /**
     * <p>sisetuChere を取得します。
     * @return sisetuChere
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuChere__
     */
    public String getSisetuChere() {
        return sisetuChere__;
    }
    /**
     * <p>sisetuChere をセットします。
     * @param sisetuChere sisetuChere
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuChere__
     */
    public void setSisetuChere(String sisetuChere) {
        sisetuChere__ = sisetuChere;
    }
    /**
     * <p>sisetuTakeout を取得します。
     * @return sisetuTakeout
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuTakeout__
     */
    public int getSisetuTakeout() {
        return sisetuTakeout__;
    }
    /**
     * <p>sisetuTakeout をセットします。
     * @param sisetuTakeout sisetuTakeout
     * @see jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel#sisetuTakeout__
     */
    public void setSisetuTakeout(int sisetuTakeout) {
        sisetuTakeout__ = sisetuTakeout;
    }



}