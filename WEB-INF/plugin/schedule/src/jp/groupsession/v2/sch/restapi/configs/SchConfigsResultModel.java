package jp.groupsession.v2.sch.restapi.configs;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.sch.restapi.entities.PublicTargetGroupInf;
import jp.groupsession.v2.sch.restapi.entities.PublicTargetUserInf;

public class SchConfigsResultModel {
    /**日間表示時間 FROM                                       */
    private int dayFromTime__;
    /**日間表示時間 TO                                         */
    private int dayToTime__;
    /**初期値設定 開始時間                                        */
    @UDateFormat(Format.TIME_CORON_HHMM)
    private UDate defaultFromTime__;
    /**初期値設定 終了時間                                        */
    @UDateFormat(Format.TIME_CORON_HHMM)
    private UDate defaultToTime__;
    /**初期値設定 色区分                                         */
    private int defaultColorType__;
    /**初期値設定 公開区分                                        */
    private int defaultPublicType__;
    /**初期値設定 編集区分                                        */
    private int defaultEditType__;
    /**個別公開対象                                       指定グループ・ユーザのみに公開時の公開先 */
    @ChildElementName("defaultPublicTargetUserInfo")
    private List<PublicTargetUserInf> defaultPublicTargetUserArray__;
    /**個別公開対象                                       指定グループ・ユーザのみに公開時の公開先 */
    @ChildElementName("defaultPublicTargetGroupInfo")
    private List<PublicTargetGroupInf>  defaultPublicTargetGroupArray__;
    /**初期値設定 同時修正フラグ                                         */
    private int defaultSameEditFlg__;
    /**初期値設定 リマインダー時間                                        */
    private int defaultRemindTimingType__;
    /**時間間隔                                          */
    private int minutesLengeNum__;
    /**タイトル色配列                                       */
    @ChildElementName("colorInfo")
    private List<ColorInf> colorArray__;
    public static class ColorInf {
        /**カラー区分                                         */
        private int type__;
        /**カラーコメント                                       */
        private String commentText__;
        /**色情報(RGB値)                                         */
        private String valueText__;
        /**使用フラグ                                         */
        private int useFlg__;
        /**
         * <p>type を取得します。
         * @return type
         * @see SchConfigsResultModel.ColorInf#type__
         */
        public int getType() {
            return type__;
        }
        /**
         * <p>type をセットします。
         * @param type type
         * @see SchConfigsResultModel.ColorInf#type__
         */
        public void setType(int type) {
            type__ = type;
        }
        /**
         * <p>commentText を取得します。
         * @return commentText
         * @see SchConfigsResultModel.ColorInf#commentText__
         */
        public String getCommentText() {
            return commentText__;
        }
        /**
         * <p>commentText をセットします。
         * @param commentText commentText
         * @see SchConfigsResultModel.ColorInf#commentText__
         */
        public void setCommentText(String commentText) {
            commentText__ = commentText;
        }
        /**
         * <p>valueText を取得します。
         * @return valueText
         * @see SchConfigsResultModel.ColorInf#valueText__
         */
        public String getValueText() {
            return valueText__;
        }
        /**
         * <p>valueText をセットします。
         * @param valueText valueText
         * @see SchConfigsResultModel.ColorInf#valueText__
         */
        public void setValueText(String valueText) {
            valueText__ = valueText;
        }
        /**
         * <p>useFlg を取得します。
         * @return useFlg
         * @see SchConfigsResultModel.ColorInf#useFlg__
         */
        public int getUseFlg() {
            return useFlg__;
        }
        /**
         * <p>useFlg をセットします。
         * @param useFlg useFlg
         * @see SchConfigsResultModel.ColorInf#useFlg__
         */
        public void setUseFlg(int useFlg) {
            useFlg__ = useFlg;
        }
    }
    /**
     * <p>dayFromTime を取得します。
     * @return dayFromTime
     * @see SchConfigsResultModel#dayFromTime__
     */
    public int getDayFromTime() {
        return dayFromTime__;
    }
    /**
     * <p>dayFromTime をセットします。
     * @param dayFromTime dayFromTime
     * @see SchConfigsResultModel#dayFromTime__
     */
    public void setDayFromTime(int dayFromTime) {
        dayFromTime__ = dayFromTime;
    }
    /**
     * <p>dayToTime を取得します。
     * @return dayToTime
     * @see SchConfigsResultModel#dayToTime__
     */
    public int getDayToTime() {
        return dayToTime__;
    }
    /**
     * <p>dayToTime をセットします。
     * @param dayToTime dayToTime
     * @see SchConfigsResultModel#dayToTime__
     */
    public void setDayToTime(int dayToTime) {
        dayToTime__ = dayToTime;
    }
    /**
     * <p>defaultFromTime を取得します。
     * @return defaultFromTime
     * @see SchConfigsResultModel#defaultFromTime__
     */
    public UDate getDefaultFromTime() {
        return defaultFromTime__;
    }
    /**
     * <p>defaultFromTime をセットします。
     * @param defaultFromTime defaultFromTime
     * @see SchConfigsResultModel#defaultFromTime__
     */
    public void setDefaultFromTime(UDate defaultFromTime) {
        defaultFromTime__ = defaultFromTime;
    }
    /**
     * <p>defaultToTime を取得します。
     * @return defaultToTime
     * @see SchConfigsResultModel#defaultToTime__
     */
    public UDate getDefaultToTime() {
        return defaultToTime__;
    }
    /**
     * <p>defaultToTime をセットします。
     * @param defaultToTime defaultToTime
     * @see SchConfigsResultModel#defaultToTime__
     */
    public void setDefaultToTime(UDate defaultToTime) {
        defaultToTime__ = defaultToTime;
    }
    /**
     * <p>defaultColorType を取得します。
     * @return defaultColorType
     * @see SchConfigsResultModel#defaultColorType__
     */
    public int getDefaultColorType() {
        return defaultColorType__;
    }
    /**
     * <p>defaultColorType をセットします。
     * @param defaultColorType defaultColorType
     * @see SchConfigsResultModel#defaultColorType__
     */
    public void setDefaultColorType(int defaultColorType) {
        defaultColorType__ = defaultColorType;
    }
    /**
     * <p>defaultPublicType を取得します。
     * @return defaultPublicType
     * @see SchConfigsResultModel#defaultPublicType__
     */
    public int getDefaultPublicType() {
        return defaultPublicType__;
    }
    /**
     * <p>defaultPublicType をセットします。
     * @param defaultPublicType defaultPublicType
     * @see SchConfigsResultModel#defaultPublicType__
     */
    public void setDefaultPublicType(int defaultPublicType) {
        defaultPublicType__ = defaultPublicType;
    }
    /**
     * <p>defaultEditType を取得します。
     * @return defaultEditType
     * @see SchConfigsResultModel#defaultEditType__
     */
    public int getDefaultEditType() {
        return defaultEditType__;
    }
    /**
     * <p>defaultEditType をセットします。
     * @param defaultEditType defaultEditType
     * @see SchConfigsResultModel#defaultEditType__
     */
    public void setDefaultEditType(int defaultEditType) {
        defaultEditType__ = defaultEditType;
    }
    /**
     * <p>defaultPublicTargetUserArray を取得します。
     * @return defaultPublicTargetUserArray
     * @see SchConfigsResultModel#defaultPublicTargetUserArray__
     */
    public List<PublicTargetUserInf> getDefaultPublicTargetUserArray() {
        return defaultPublicTargetUserArray__;
    }
    /**
     * <p>defaultPublicTargetUserArray をセットします。
     * @param defaultPublicTargetUserArray defaultPublicTargetUserArray
     * @see SchConfigsResultModel#defaultPublicTargetUserArray__
     */
    public void setDefaultPublicTargetUserArray(
            List<PublicTargetUserInf> defaultPublicTargetUserArray) {
        defaultPublicTargetUserArray__ = defaultPublicTargetUserArray;
    }
    /**
     * <p>defaultPublicTargetGroupArray を取得します。
     * @return defaultPublicTargetGroupArray
     * @see SchConfigsResultModel#defaultPublicTargetGroupArray__
     */
    public List<PublicTargetGroupInf> getDefaultPublicTargetGroupArray() {
        return defaultPublicTargetGroupArray__;
    }
    /**
     * <p>defaultPublicTargetGroupArray をセットします。
     * @param defaultPublicTargetGroupArray defaultPublicTargetGroupArray
     * @see SchConfigsResultModel#defaultPublicTargetGroupArray__
     */
    public void setDefaultPublicTargetGroupArray(
            List<PublicTargetGroupInf> defaultPublicTargetGroupArray) {
        defaultPublicTargetGroupArray__ = defaultPublicTargetGroupArray;
    }
    /**
     * <p>defaultSameEditFlg を取得します。
     * @return defaultSameEditFlg
     * @see SchConfigsResultModel#defaultSameEditFlg__
     */
    public int getDefaultSameEditFlg() {
        return defaultSameEditFlg__;
    }
    /**
     * <p>defaultSameEditFlg をセットします。
     * @param defaultSameEditFlg defaultSameEditFlg
     * @see SchConfigsResultModel#defaultSameEditFlg__
     */
    public void setDefaultSameEditFlg(int defaultSameEditFlg) {
        defaultSameEditFlg__ = defaultSameEditFlg;
    }
    /**
     * <p>defaultRemindTimingType を取得します。
     * @return defaultRemindTimingType
     * @see SchConfigsResultModel#defaultRemindTimingType__
     */
    public int getDefaultRemindTimingType() {
        return defaultRemindTimingType__;
    }
    /**
     * <p>defaultRemindTimingType をセットします。
     * @param defaultRemindTimingType defaultRemindTimingType
     * @see SchConfigsResultModel#defaultRemindTimingType__
     */
    public void setDefaultRemindTimingType(int defaultRemindTimingType) {
        defaultRemindTimingType__ = defaultRemindTimingType;
    }
    /**
     * <p>minutesLengeNum を取得します。
     * @return minutesLengeNum
     * @see SchConfigsResultModel#minutesLengeNum__
     */
    public int getMinutesLengeNum() {
        return minutesLengeNum__;
    }
    /**
     * <p>minutesLengeNum をセットします。
     * @param minutesLengeNum minutesLengeNum
     * @see SchConfigsResultModel#minutesLengeNum__
     */
    public void setMinutesLengeNum(int minutesLengeNum) {
        minutesLengeNum__ = minutesLengeNum;
    }
    /**
     * <p>colorArray を取得します。
     * @return colorArray
     * @see SchConfigsResultModel#colorArray__
     */
    public List<ColorInf> getColorArray() {
        return colorArray__;
    }
    /**
     * <p>colorArray をセットします。
     * @param colorArray colorArray
     * @see SchConfigsResultModel#colorArray__
     */
    public void setColorArray(List<ColorInf> colorArray) {
        colorArray__ = colorArray;
    }



}
