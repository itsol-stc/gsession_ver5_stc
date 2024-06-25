package jp.groupsession.v2.tcd.tcd220;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.tcd.tcd030.Tcd030ParamModel;

/**
 * <br>[機  能] タイムカード 有休警告設定画面の情報保持クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd220ParamModel extends Tcd030ParamModel {

    /** 基準日数の最大値 */
    public static final int MAX_DAY = 366;
    /** 基準日数の最小値 */
    public static final int MIN_DAY = 1;
    
    /** 月コンボの選択最大値 */
    public static final int MAX_MONTH = 12;
    /** 月コンボの選択最小値 */
    public static final int MIN_MONTH = 1;
    
    /** 警告メッセージ最大桁数 */
    public static final int MAX_MESSAGE = 50;
    
    /** 月コンボ */
    private List<LabelValueBean> tcd220MonthLabelList__ = null;

    /** 警告開始月1 */
    private String tcd220DispMonth1__ = "";
    /** 警告メッセージ1*/
    private String tcd220Message1__ = "";
    /** 基準日数 */
    private String tcd220Days1__ = "";
    /** 行NO1*/
    private int tcd220Row1__ = 0;

    /** 警告開始月2 */
    private String tcd220DispMonth2__ = "";
    /** 警告メッセージ2*/
    private String tcd220Message2__ = "";
    /** 基準日数 */
    private String tcd220Days2__ = "";
    /** 行NO2*/
    private int tcd220Row2__ = 0;

    /** 警告開始月3 */
    private String tcd220DispMonth3__ = "";
    /** 警告メッセージ3*/
    private String tcd220Message3__ = "";
    /** 基準日数 */
    private String tcd220Days3__ = "";
    /** 行NO3*/
    private int tcd220Row3__ = 0;

    /** 警告開始月4 */
    private String tcd220DispMonth4__ = "";
    /** 警告メッセージ4*/
    private String tcd220Message4__ = "";
    /** 基準日数 */
    private String tcd220Days4__ = "";
    /** 行NO4*/
    private int tcd220Row4__ = 0;

    /** 警告開始月5 */
    private String tcd220DispMonth5__ = "";
    /** 警告メッセージ5*/
    private String tcd220Message5__ = "";
    /** 基準日数 */
    private String tcd220Days5__ = "";
    /** 行NO5*/
    private int tcd220Row5__ = 0;

    /** 警告開始月6 */
    private String tcd220DispMonth6__ = "";
    /** 警告メッセージ6*/
    private String tcd220Message6__ = "";
    /** 基準日数 */
    private String tcd220Days6__ = "";
    /** 行NO6*/
    private int tcd220Row6__ = 0;

    /** 警告開始月7 */
    private String tcd220DispMonth7__ = "";
    /** 警告メッセージ7*/
    private String tcd220Message7__ = "";
    /** 基準日数 */
    private String tcd220Days7__ = "";
    /** 行NO7*/
    private int tcd220Row7__ = 0;

    /** 警告開始月8 */
    private String tcd220DispMonth8__ = "";
    /** 警告メッセージ8*/
    private String tcd220Message8__ = "";
    /** 基準日数 */
    private String tcd220Days8__ = "";
    /** 行NO8*/
    private int tcd220Row8__ = 0;

    /** 警告開始月9 */
    private String tcd220DispMonth9__ = "";
    /** 警告メッセージ9*/
    private String tcd220Message9__ = "";
    /** 基準日数 */
    private String tcd220Days9__ = "";
    /** 行NO9*/
    private int tcd220Row9__ = 0;

    /** 警告開始月10 */
    private String tcd220DispMonth10__ = "";
    /** 警告メッセージ10*/
    private String tcd220Message10__ = "";
    /** 基準日数 */
    private String tcd220Days10__ = "";
    /** 行NO10*/
    private int tcd220Row10__ = 0;
    
    /** 警告開始月11 */
    private String tcd220DispMonth11__ = "";
    /** 警告メッセージ11*/
    private String tcd220Message11__ = "";
    /** 基準日数 */
    private String tcd220Days11__ = "";
    /** 行NO11*/
    private int tcd220Row11__ = 0;
    
    /** 警告開始月12 */
    private String tcd220DispMonth12__ = "";
    /** 警告メッセージ12*/
    private String tcd220Message12__ = "";
    /** 基準日数 */
    private String tcd220Days12__ = "";
    /** 行NO12*/
    private int tcd220Row12__ = 0;
    
    /** 警告開始月13 */
    private String tcd220DispMonth13__ = "";
    /** 警告メッセージ13*/
    private String tcd220Message13__ = "";
    /** 基準日数 */
    private String tcd220Days13__ = "";
    /** 行NO13*/
    private int tcd220Row13__ = 0;
    
    /** 警告開始月14 */
    private String tcd220DispMonth14__ = "";
    /** 警告メッセージ14*/
    private String tcd220Message14__ = "";
    /** 基準日数 */
    private String tcd220Days14__ = "";
    /** 行NO14*/
    private int tcd220Row14__ = 0;
    
    /** 警告開始月15 */
    private String tcd220DispMonth15__ = "";
    /** 警告メッセージ15*/
    private String tcd220Message15__ = "";
    /** 基準日数 */
    private String tcd220Days15__ = "";
    /** 行NO15*/
    private int tcd220Row15__ = 0;
    /**
     * <p>tcd220DispMonth1 を取得します。
     * @return tcd220DispMonth1
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth1__
     */
    public String getTcd220DispMonth1() {
        return tcd220DispMonth1__;
    }
    /**
     * <p>tcd220DispMonth1 をセットします。
     * @param tcd220DispMonth1 tcd220DispMonth1
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth1__
     */
    public void setTcd220DispMonth1(String tcd220DispMonth1) {
        tcd220DispMonth1__ = tcd220DispMonth1;
    }
    /**
     * <p>tcd220Message1 を取得します。
     * @return tcd220Message1
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message1__
     */
    public String getTcd220Message1() {
        return tcd220Message1__;
    }
    /**
     * <p>tcd220Message1 をセットします。
     * @param tcd220Message1 tcd220Message1
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message1__
     */
    public void setTcd220Message1(String tcd220Message1) {
        tcd220Message1__ = tcd220Message1;
    }
    /**
     * <p>tcd220Days1 を取得します。
     * @return tcd220Days1
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days1__
     */
    public String getTcd220Days1() {
        return tcd220Days1__;
    }
    /**
     * <p>tcd220Days1 をセットします。
     * @param tcd220Days1 tcd220Days1
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days1__
     */
    public void setTcd220Days1(String tcd220Days1) {
        tcd220Days1__ = tcd220Days1;
    }
    /**
     * <p>tcd220Row1 を取得します。
     * @return tcd220Row1
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row1__
     */
    public int getTcd220Row1() {
        return tcd220Row1__;
    }
    /**
     * <p>tcd220Row1 をセットします。
     * @param tcd220Row1 tcd220Row1
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row1__
     */
    public void setTcd220Row1(int tcd220Row1) {
        tcd220Row1__ = tcd220Row1;
    }
    /**
     * <p>tcd220DispMonth2 を取得します。
     * @return tcd220DispMonth2
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth2__
     */
    public String getTcd220DispMonth2() {
        return tcd220DispMonth2__;
    }
    /**
     * <p>tcd220DispMonth2 をセットします。
     * @param tcd220DispMonth2 tcd220DispMonth2
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth2__
     */
    public void setTcd220DispMonth2(String tcd220DispMonth2) {
        tcd220DispMonth2__ = tcd220DispMonth2;
    }
    /**
     * <p>tcd220Message2 を取得します。
     * @return tcd220Message2
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message2__
     */
    public String getTcd220Message2() {
        return tcd220Message2__;
    }
    /**
     * <p>tcd220Message2 をセットします。
     * @param tcd220Message2 tcd220Message2
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message2__
     */
    public void setTcd220Message2(String tcd220Message2) {
        tcd220Message2__ = tcd220Message2;
    }
    /**
     * <p>tcd220Days2 を取得します。
     * @return tcd220Days2
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days2__
     */
    public String getTcd220Days2() {
        return tcd220Days2__;
    }
    /**
     * <p>tcd220Days2 をセットします。
     * @param tcd220Days2 tcd220Days2
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days2__
     */
    public void setTcd220Days2(String tcd220Days2) {
        tcd220Days2__ = tcd220Days2;
    }
    /**
     * <p>tcd220Row2 を取得します。
     * @return tcd220Row2
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row2__
     */
    public int getTcd220Row2() {
        return tcd220Row2__;
    }
    /**
     * <p>tcd220Row2 をセットします。
     * @param tcd220Row2 tcd220Row2
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row2__
     */
    public void setTcd220Row2(int tcd220Row2) {
        tcd220Row2__ = tcd220Row2;
    }
    /**
     * <p>tcd220DispMonth3 を取得します。
     * @return tcd220DispMonth3
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth3__
     */
    public String getTcd220DispMonth3() {
        return tcd220DispMonth3__;
    }
    /**
     * <p>tcd220DispMonth3 をセットします。
     * @param tcd220DispMonth3 tcd220DispMonth3
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth3__
     */
    public void setTcd220DispMonth3(String tcd220DispMonth3) {
        tcd220DispMonth3__ = tcd220DispMonth3;
    }
    /**
     * <p>tcd220Message3 を取得します。
     * @return tcd220Message3
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message3__
     */
    public String getTcd220Message3() {
        return tcd220Message3__;
    }
    /**
     * <p>tcd220Message3 をセットします。
     * @param tcd220Message3 tcd220Message3
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message3__
     */
    public void setTcd220Message3(String tcd220Message3) {
        tcd220Message3__ = tcd220Message3;
    }
    /**
     * <p>tcd220Days3 を取得します。
     * @return tcd220Days3
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days3__
     */
    public String getTcd220Days3() {
        return tcd220Days3__;
    }
    /**
     * <p>tcd220Days3 をセットします。
     * @param tcd220Days3 tcd220Days3
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days3__
     */
    public void setTcd220Days3(String tcd220Days3) {
        tcd220Days3__ = tcd220Days3;
    }
    /**
     * <p>tcd220Row3 を取得します。
     * @return tcd220Row3
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row3__
     */
    public int getTcd220Row3() {
        return tcd220Row3__;
    }
    /**
     * <p>tcd220Row3 をセットします。
     * @param tcd220Row3 tcd220Row3
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row3__
     */
    public void setTcd220Row3(int tcd220Row3) {
        tcd220Row3__ = tcd220Row3;
    }
    /**
     * <p>tcd220DispMonth4 を取得します。
     * @return tcd220DispMonth4
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth4__
     */
    public String getTcd220DispMonth4() {
        return tcd220DispMonth4__;
    }
    /**
     * <p>tcd220DispMonth4 をセットします。
     * @param tcd220DispMonth4 tcd220DispMonth4
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth4__
     */
    public void setTcd220DispMonth4(String tcd220DispMonth4) {
        tcd220DispMonth4__ = tcd220DispMonth4;
    }
    /**
     * <p>tcd220Message4 を取得します。
     * @return tcd220Message4
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message4__
     */
    public String getTcd220Message4() {
        return tcd220Message4__;
    }
    /**
     * <p>tcd220Message4 をセットします。
     * @param tcd220Message4 tcd220Message4
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message4__
     */
    public void setTcd220Message4(String tcd220Message4) {
        tcd220Message4__ = tcd220Message4;
    }
    /**
     * <p>tcd220Days4 を取得します。
     * @return tcd220Days4
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days4__
     */
    public String getTcd220Days4() {
        return tcd220Days4__;
    }
    /**
     * <p>tcd220Days4 をセットします。
     * @param tcd220Days4 tcd220Days4
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days4__
     */
    public void setTcd220Days4(String tcd220Days4) {
        tcd220Days4__ = tcd220Days4;
    }
    /**
     * <p>tcd220Row4 を取得します。
     * @return tcd220Row4
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row4__
     */
    public int getTcd220Row4() {
        return tcd220Row4__;
    }
    /**
     * <p>tcd220Row4 をセットします。
     * @param tcd220Row4 tcd220Row4
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row4__
     */
    public void setTcd220Row4(int tcd220Row4) {
        tcd220Row4__ = tcd220Row4;
    }
    /**
     * <p>tcd220DispMonth5 を取得します。
     * @return tcd220DispMonth5
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth5__
     */
    public String getTcd220DispMonth5() {
        return tcd220DispMonth5__;
    }
    /**
     * <p>tcd220DispMonth5 をセットします。
     * @param tcd220DispMonth5 tcd220DispMonth5
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth5__
     */
    public void setTcd220DispMonth5(String tcd220DispMonth5) {
        tcd220DispMonth5__ = tcd220DispMonth5;
    }
    /**
     * <p>tcd220Message5 を取得します。
     * @return tcd220Message5
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message5__
     */
    public String getTcd220Message5() {
        return tcd220Message5__;
    }
    /**
     * <p>tcd220Message5 をセットします。
     * @param tcd220Message5 tcd220Message5
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message5__
     */
    public void setTcd220Message5(String tcd220Message5) {
        tcd220Message5__ = tcd220Message5;
    }
    /**
     * <p>tcd220Days5 を取得します。
     * @return tcd220Days5
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days5__
     */
    public String getTcd220Days5() {
        return tcd220Days5__;
    }
    /**
     * <p>tcd220Days5 をセットします。
     * @param tcd220Days5 tcd220Days5
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days5__
     */
    public void setTcd220Days5(String tcd220Days5) {
        tcd220Days5__ = tcd220Days5;
    }
    /**
     * <p>tcd220Row5 を取得します。
     * @return tcd220Row5
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row5__
     */
    public int getTcd220Row5() {
        return tcd220Row5__;
    }
    /**
     * <p>tcd220Row5 をセットします。
     * @param tcd220Row5 tcd220Row5
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row5__
     */
    public void setTcd220Row5(int tcd220Row5) {
        tcd220Row5__ = tcd220Row5;
    }
    /**
     * <p>tcd220DispMonth6 を取得します。
     * @return tcd220DispMonth6
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth6__
     */
    public String getTcd220DispMonth6() {
        return tcd220DispMonth6__;
    }
    /**
     * <p>tcd220DispMonth6 をセットします。
     * @param tcd220DispMonth6 tcd220DispMonth6
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth6__
     */
    public void setTcd220DispMonth6(String tcd220DispMonth6) {
        tcd220DispMonth6__ = tcd220DispMonth6;
    }
    /**
     * <p>tcd220Message6 を取得します。
     * @return tcd220Message6
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message6__
     */
    public String getTcd220Message6() {
        return tcd220Message6__;
    }
    /**
     * <p>tcd220Message6 をセットします。
     * @param tcd220Message6 tcd220Message6
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message6__
     */
    public void setTcd220Message6(String tcd220Message6) {
        tcd220Message6__ = tcd220Message6;
    }
    /**
     * <p>tcd220Days6 を取得します。
     * @return tcd220Days6
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days6__
     */
    public String getTcd220Days6() {
        return tcd220Days6__;
    }
    /**
     * <p>tcd220Days6 をセットします。
     * @param tcd220Days6 tcd220Days6
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days6__
     */
    public void setTcd220Days6(String tcd220Days6) {
        tcd220Days6__ = tcd220Days6;
    }
    /**
     * <p>tcd220Row6 を取得します。
     * @return tcd220Row6
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row6__
     */
    public int getTcd220Row6() {
        return tcd220Row6__;
    }
    /**
     * <p>tcd220Row6 をセットします。
     * @param tcd220Row6 tcd220Row6
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row6__
     */
    public void setTcd220Row6(int tcd220Row6) {
        tcd220Row6__ = tcd220Row6;
    }
    /**
     * <p>tcd220DispMonth7 を取得します。
     * @return tcd220DispMonth7
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth7__
     */
    public String getTcd220DispMonth7() {
        return tcd220DispMonth7__;
    }
    /**
     * <p>tcd220DispMonth7 をセットします。
     * @param tcd220DispMonth7 tcd220DispMonth7
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth7__
     */
    public void setTcd220DispMonth7(String tcd220DispMonth7) {
        tcd220DispMonth7__ = tcd220DispMonth7;
    }
    /**
     * <p>tcd220Message7 を取得します。
     * @return tcd220Message7
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message7__
     */
    public String getTcd220Message7() {
        return tcd220Message7__;
    }
    /**
     * <p>tcd220Message7 をセットします。
     * @param tcd220Message7 tcd220Message7
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message7__
     */
    public void setTcd220Message7(String tcd220Message7) {
        tcd220Message7__ = tcd220Message7;
    }
    /**
     * <p>tcd220Days7 を取得します。
     * @return tcd220Days7
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days7__
     */
    public String getTcd220Days7() {
        return tcd220Days7__;
    }
    /**
     * <p>tcd220Days7 をセットします。
     * @param tcd220Days7 tcd220Days7
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days7__
     */
    public void setTcd220Days7(String tcd220Days7) {
        tcd220Days7__ = tcd220Days7;
    }
    /**
     * <p>tcd220Row7 を取得します。
     * @return tcd220Row7
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row7__
     */
    public int getTcd220Row7() {
        return tcd220Row7__;
    }
    /**
     * <p>tcd220Row7 をセットします。
     * @param tcd220Row7 tcd220Row7
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row7__
     */
    public void setTcd220Row7(int tcd220Row7) {
        tcd220Row7__ = tcd220Row7;
    }
    /**
     * <p>tcd220DispMonth8 を取得します。
     * @return tcd220DispMonth8
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth8__
     */
    public String getTcd220DispMonth8() {
        return tcd220DispMonth8__;
    }
    /**
     * <p>tcd220DispMonth8 をセットします。
     * @param tcd220DispMonth8 tcd220DispMonth8
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth8__
     */
    public void setTcd220DispMonth8(String tcd220DispMonth8) {
        tcd220DispMonth8__ = tcd220DispMonth8;
    }
    /**
     * <p>tcd220Message8 を取得します。
     * @return tcd220Message8
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message8__
     */
    public String getTcd220Message8() {
        return tcd220Message8__;
    }
    /**
     * <p>tcd220Message8 をセットします。
     * @param tcd220Message8 tcd220Message8
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message8__
     */
    public void setTcd220Message8(String tcd220Message8) {
        tcd220Message8__ = tcd220Message8;
    }
    /**
     * <p>tcd220Days8 を取得します。
     * @return tcd220Days8
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days8__
     */
    public String getTcd220Days8() {
        return tcd220Days8__;
    }
    /**
     * <p>tcd220Days8 をセットします。
     * @param tcd220Days8 tcd220Days8
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days8__
     */
    public void setTcd220Days8(String tcd220Days8) {
        tcd220Days8__ = tcd220Days8;
    }
    /**
     * <p>tcd220Row8 を取得します。
     * @return tcd220Row8
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row8__
     */
    public int getTcd220Row8() {
        return tcd220Row8__;
    }
    /**
     * <p>tcd220Row8 をセットします。
     * @param tcd220Row8 tcd220Row8
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row8__
     */
    public void setTcd220Row8(int tcd220Row8) {
        tcd220Row8__ = tcd220Row8;
    }
    /**
     * <p>tcd220DispMonth9 を取得します。
     * @return tcd220DispMonth9
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth9__
     */
    public String getTcd220DispMonth9() {
        return tcd220DispMonth9__;
    }
    /**
     * <p>tcd220DispMonth9 をセットします。
     * @param tcd220DispMonth9 tcd220DispMonth9
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth9__
     */
    public void setTcd220DispMonth9(String tcd220DispMonth9) {
        tcd220DispMonth9__ = tcd220DispMonth9;
    }
    /**
     * <p>tcd220Message9 を取得します。
     * @return tcd220Message9
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message9__
     */
    public String getTcd220Message9() {
        return tcd220Message9__;
    }
    /**
     * <p>tcd220Message9 をセットします。
     * @param tcd220Message9 tcd220Message9
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message9__
     */
    public void setTcd220Message9(String tcd220Message9) {
        tcd220Message9__ = tcd220Message9;
    }
    /**
     * <p>tcd220Days9 を取得します。
     * @return tcd220Days9
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days9__
     */
    public String getTcd220Days9() {
        return tcd220Days9__;
    }
    /**
     * <p>tcd220Days9 をセットします。
     * @param tcd220Days9 tcd220Days9
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days9__
     */
    public void setTcd220Days9(String tcd220Days9) {
        tcd220Days9__ = tcd220Days9;
    }
    /**
     * <p>tcd220Row9 を取得します。
     * @return tcd220Row9
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row9__
     */
    public int getTcd220Row9() {
        return tcd220Row9__;
    }
    /**
     * <p>tcd220Row9 をセットします。
     * @param tcd220Row9 tcd220Row9
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row9__
     */
    public void setTcd220Row9(int tcd220Row9) {
        tcd220Row9__ = tcd220Row9;
    }
    /**
     * <p>tcd220DispMonth10 を取得します。
     * @return tcd220DispMonth10
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth10__
     */
    public String getTcd220DispMonth10() {
        return tcd220DispMonth10__;
    }
    /**
     * <p>tcd220DispMonth10 をセットします。
     * @param tcd220DispMonth10 tcd220DispMonth10
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth10__
     */
    public void setTcd220DispMonth10(String tcd220DispMonth10) {
        tcd220DispMonth10__ = tcd220DispMonth10;
    }
    /**
     * <p>tcd220Message10 を取得します。
     * @return tcd220Message10
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message10__
     */
    public String getTcd220Message10() {
        return tcd220Message10__;
    }
    /**
     * <p>tcd220Message10 をセットします。
     * @param tcd220Message10 tcd220Message10
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message10__
     */
    public void setTcd220Message10(String tcd220Message10) {
        tcd220Message10__ = tcd220Message10;
    }
    /**
     * <p>tcd220Days10 を取得します。
     * @return tcd220Days10
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days10__
     */
    public String getTcd220Days10() {
        return tcd220Days10__;
    }
    /**
     * <p>tcd220Days10 をセットします。
     * @param tcd220Days10 tcd220Days10
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days10__
     */
    public void setTcd220Days10(String tcd220Days10) {
        tcd220Days10__ = tcd220Days10;
    }
    /**
     * <p>tcd220Row10 を取得します。
     * @return tcd220Row10
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row10__
     */
    public int getTcd220Row10() {
        return tcd220Row10__;
    }
    /**
     * <p>tcd220Row10 をセットします。
     * @param tcd220Row10 tcd220Row10
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row10__
     */
    public void setTcd220Row10(int tcd220Row10) {
        tcd220Row10__ = tcd220Row10;
    }
    /**
     * <p>tcd220DispMonth11 を取得します。
     * @return tcd220DispMonth11
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth11__
     */
    public String getTcd220DispMonth11() {
        return tcd220DispMonth11__;
    }
    /**
     * <p>tcd220DispMonth11 をセットします。
     * @param tcd220DispMonth11 tcd220DispMonth11
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth11__
     */
    public void setTcd220DispMonth11(String tcd220DispMonth11) {
        tcd220DispMonth11__ = tcd220DispMonth11;
    }
    /**
     * <p>tcd220Message11 を取得します。
     * @return tcd220Message11
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message11__
     */
    public String getTcd220Message11() {
        return tcd220Message11__;
    }
    /**
     * <p>tcd220Message11 をセットします。
     * @param tcd220Message11 tcd220Message11
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message11__
     */
    public void setTcd220Message11(String tcd220Message11) {
        tcd220Message11__ = tcd220Message11;
    }
    /**
     * <p>tcd220Days11 を取得します。
     * @return tcd220Days11
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days11__
     */
    public String getTcd220Days11() {
        return tcd220Days11__;
    }
    /**
     * <p>tcd220Days11 をセットします。
     * @param tcd220Days11 tcd220Days11
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days11__
     */
    public void setTcd220Days11(String tcd220Days11) {
        tcd220Days11__ = tcd220Days11;
    }
    /**
     * <p>tcd220Row11 を取得します。
     * @return tcd220Row11
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row11__
     */
    public int getTcd220Row11() {
        return tcd220Row11__;
    }
    /**
     * <p>tcd220Row11 をセットします。
     * @param tcd220Row11 tcd220Row11
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row11__
     */
    public void setTcd220Row11(int tcd220Row11) {
        tcd220Row11__ = tcd220Row11;
    }
    /**
     * <p>tcd220DispMonth12 を取得します。
     * @return tcd220DispMonth12
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth12__
     */
    public String getTcd220DispMonth12() {
        return tcd220DispMonth12__;
    }
    /**
     * <p>tcd220DispMonth12 をセットします。
     * @param tcd220DispMonth12 tcd220DispMonth12
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth12__
     */
    public void setTcd220DispMonth12(String tcd220DispMonth12) {
        tcd220DispMonth12__ = tcd220DispMonth12;
    }
    /**
     * <p>tcd220Message12 を取得します。
     * @return tcd220Message12
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message12__
     */
    public String getTcd220Message12() {
        return tcd220Message12__;
    }
    /**
     * <p>tcd220Message12 をセットします。
     * @param tcd220Message12 tcd220Message12
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message12__
     */
    public void setTcd220Message12(String tcd220Message12) {
        tcd220Message12__ = tcd220Message12;
    }
    /**
     * <p>tcd220Days12 を取得します。
     * @return tcd220Days12
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days12__
     */
    public String getTcd220Days12() {
        return tcd220Days12__;
    }
    /**
     * <p>tcd220Days12 をセットします。
     * @param tcd220Days12 tcd220Days12
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days12__
     */
    public void setTcd220Days12(String tcd220Days12) {
        tcd220Days12__ = tcd220Days12;
    }
    /**
     * <p>tcd220Row12 を取得します。
     * @return tcd220Row12
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row12__
     */
    public int getTcd220Row12() {
        return tcd220Row12__;
    }
    /**
     * <p>tcd220Row12 をセットします。
     * @param tcd220Row12 tcd220Row12
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row12__
     */
    public void setTcd220Row12(int tcd220Row12) {
        tcd220Row12__ = tcd220Row12;
    }
    /**
     * <p>tcd220DispMonth13 を取得します。
     * @return tcd220DispMonth13
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth13__
     */
    public String getTcd220DispMonth13() {
        return tcd220DispMonth13__;
    }
    /**
     * <p>tcd220DispMonth13 をセットします。
     * @param tcd220DispMonth13 tcd220DispMonth13
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth13__
     */
    public void setTcd220DispMonth13(String tcd220DispMonth13) {
        tcd220DispMonth13__ = tcd220DispMonth13;
    }
    /**
     * <p>tcd220Message13 を取得します。
     * @return tcd220Message13
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message13__
     */
    public String getTcd220Message13() {
        return tcd220Message13__;
    }
    /**
     * <p>tcd220Message13 をセットします。
     * @param tcd220Message13 tcd220Message13
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message13__
     */
    public void setTcd220Message13(String tcd220Message13) {
        tcd220Message13__ = tcd220Message13;
    }
    /**
     * <p>tcd220Days13 を取得します。
     * @return tcd220Days13
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days13__
     */
    public String getTcd220Days13() {
        return tcd220Days13__;
    }
    /**
     * <p>tcd220Days13 をセットします。
     * @param tcd220Days13 tcd220Days13
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days13__
     */
    public void setTcd220Days13(String tcd220Days13) {
        tcd220Days13__ = tcd220Days13;
    }
    /**
     * <p>tcd220Row13 を取得します。
     * @return tcd220Row13
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row13__
     */
    public int getTcd220Row13() {
        return tcd220Row13__;
    }
    /**
     * <p>tcd220Row13 をセットします。
     * @param tcd220Row13 tcd220Row13
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row13__
     */
    public void setTcd220Row13(int tcd220Row13) {
        tcd220Row13__ = tcd220Row13;
    }
    /**
     * <p>tcd220DispMonth14 を取得します。
     * @return tcd220DispMonth14
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth14__
     */
    public String getTcd220DispMonth14() {
        return tcd220DispMonth14__;
    }
    /**
     * <p>tcd220DispMonth14 をセットします。
     * @param tcd220DispMonth14 tcd220DispMonth14
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth14__
     */
    public void setTcd220DispMonth14(String tcd220DispMonth14) {
        tcd220DispMonth14__ = tcd220DispMonth14;
    }
    /**
     * <p>tcd220Message14 を取得します。
     * @return tcd220Message14
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message14__
     */
    public String getTcd220Message14() {
        return tcd220Message14__;
    }
    /**
     * <p>tcd220Message14 をセットします。
     * @param tcd220Message14 tcd220Message14
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message14__
     */
    public void setTcd220Message14(String tcd220Message14) {
        tcd220Message14__ = tcd220Message14;
    }
    /**
     * <p>tcd220Days14 を取得します。
     * @return tcd220Days14
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days14__
     */
    public String getTcd220Days14() {
        return tcd220Days14__;
    }
    /**
     * <p>tcd220Days14 をセットします。
     * @param tcd220Days14 tcd220Days14
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days14__
     */
    public void setTcd220Days14(String tcd220Days14) {
        tcd220Days14__ = tcd220Days14;
    }
    /**
     * <p>tcd220Row14 を取得します。
     * @return tcd220Row14
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row14__
     */
    public int getTcd220Row14() {
        return tcd220Row14__;
    }
    /**
     * <p>tcd220Row14 をセットします。
     * @param tcd220Row14 tcd220Row14
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row14__
     */
    public void setTcd220Row14(int tcd220Row14) {
        tcd220Row14__ = tcd220Row14;
    }
    /**
     * <p>tcd220DispMonth15 を取得します。
     * @return tcd220DispMonth15
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth15__
     */
    public String getTcd220DispMonth15() {
        return tcd220DispMonth15__;
    }
    /**
     * <p>tcd220DispMonth15 をセットします。
     * @param tcd220DispMonth15 tcd220DispMonth15
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220DispMonth15__
     */
    public void setTcd220DispMonth15(String tcd220DispMonth15) {
        tcd220DispMonth15__ = tcd220DispMonth15;
    }
    /**
     * <p>tcd220Message15 を取得します。
     * @return tcd220Message15
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message15__
     */
    public String getTcd220Message15() {
        return tcd220Message15__;
    }
    /**
     * <p>tcd220Message15 をセットします。
     * @param tcd220Message15 tcd220Message15
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Message15__
     */
    public void setTcd220Message15(String tcd220Message15) {
        tcd220Message15__ = tcd220Message15;
    }
    /**
     * <p>tcd220Days15 を取得します。
     * @return tcd220Days15
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days15__
     */
    public String getTcd220Days15() {
        return tcd220Days15__;
    }
    /**
     * <p>tcd220Days15 をセットします。
     * @param tcd220Days15 tcd220Days15
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Days15__
     */
    public void setTcd220Days15(String tcd220Days15) {
        tcd220Days15__ = tcd220Days15;
    }
    /**
     * <p>tcd220Row15 を取得します。
     * @return tcd220Row15
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row15__
     */
    public int getTcd220Row15() {
        return tcd220Row15__;
    }
    /**
     * <p>tcd220Row15 をセットします。
     * @param tcd220Row15 tcd220Row15
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220Row15__
     */
    public void setTcd220Row15(int tcd220Row15) {
        tcd220Row15__ = tcd220Row15;
    }
    /**
     * <p>maxDay を取得します。
     * @return maxDay
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#MAX_DAY
     */
    public static int getMaxDay() {
        return MAX_DAY;
    }
    /**
     * <p>minDay を取得します。
     * @return minDay
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#MIN_DAY
     */
    public static int getMinDay() {
        return MIN_DAY;
    }
    /**
     * <p>maxMonth を取得します。
     * @return maxMonth
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#MAX_MONTH
     */
    public static int getMaxMonth() {
        return MAX_MONTH;
    }
    /**
     * <p>minMonth を取得します。
     * @return minMonth
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#MIN_MONTH
     */
    public static int getMinMonth() {
        return MIN_MONTH;
    }
    /**
     * <p>maxMessage を取得します。
     * @return maxMessage
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#MAX_MESSAGE
     */
    public static int getMaxMessage() {
        return MAX_MESSAGE;
    }
    /**
     * <p>tcd220MonthLabelList を取得します。
     * @return tcd220MonthLabelList
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220MonthLabelList__
     */
    public List<LabelValueBean> getTcd220MonthLabelList() {
        return tcd220MonthLabelList__;
    }
    /**
     * <p>tcd220MonthLabelList をセットします。
     * @param tcd220MonthLabelList tcd220MonthLabelList
     * @see jp.groupsession.v2.tcd.tcd220.Tcd220ParamModel#tcd220MonthLabelList__
     */
    public void setTcd220MonthLabelList(List<LabelValueBean> tcd220MonthLabelList) {
        tcd220MonthLabelList__ = tcd220MonthLabelList;
    }

}
