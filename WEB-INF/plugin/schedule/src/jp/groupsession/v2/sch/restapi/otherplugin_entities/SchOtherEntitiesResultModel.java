package jp.groupsession.v2.sch.restapi.otherplugin_entities;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.sch.model.SchDataModel;

public class SchOtherEntitiesResultModel {
    /**スケジュール登録対象のID                                         */
    private String targetId__ = "";
    /**スケジュール登録対象の名前                                         */
    private String targetName__ = "";
    /**スケジュール登録対象の姓                                          */
    private String targetSeiText__ = "";
    /**スケジュール登録対象の名                                          */
    private String targetMeiText__ = "";
    /**スケジュール登録対象の姓（カナ）                                          */
    private String targetSeiKanaText__ = "";
    /**スケジュール登録対象の名（カナ）                                          */
    private String targetMeiKanaText__ = "";
    /**スケジュール登録対象のログイン停止フラグ   0:有効 1:停止 */
    private int targetLoginStopFlg__;
    /**開始日時                                         yyyy/MM/dd hh:mm:ss */
    @UDateFormat(Format.DATETIME_SLUSH_HHMM)
    private UDate startDateTime__;
    /**終了日時                                         yyyy/MM/dd hh:mm:ss */
    @UDateFormat(Format.DATETIME_SLUSH_HHMM)
    private UDate endDateTime__;
    /**時間指定区分                                       0:有り,1:無し */
    private int useTimeFlg__;
    /**タイトル                                          */
    private String titleText__  = "";
    /**文字色                                      １:青 2:赤 3:緑 4:黄 5:黒 */
    private int colorType__;
    /**内容                                        */
    private String bodyText__ = "";
    /**公開区分                                          */
    private int publicType__;
    /**プラグインID                                       */
    private String pluginId__ = "";
    /**プラグイン名                                        */
    private String usageName__ = "";
    /**詳細URL                                         */
    private String urlText__ = "";
    /**
     *
     * コンストラクタ
     * @param base スケジュール
     * @param targetUser 対象ユーザ情報
     */
    public SchOtherEntitiesResultModel(SchDataModel base,
            CmnUserModel targetUser) {
        super();
        if (targetUser != null) {
            targetId__ = targetUser.getUsrLgid();
            targetName__ = targetUser.getUsiName();
            targetSeiText__ = targetUser.getUsiSei();
            targetMeiText__ = targetUser.getUsiMei();
            targetSeiKanaText__ = targetUser.getUsiSeiKn();
            targetMeiKanaText__ = targetUser.getUsiMeiKn();
            targetLoginStopFlg__ = targetUser.getUsrUkoFlg();
        }
        if (base != null) {
            startDateTime__ = base.getScdFrDate();
            endDateTime__ = base.getScdToDate();
            useTimeFlg__ = base.getScdDaily();
            titleText__ = base.getScdTitle();
            colorType__ = base.getScdBgcolor();
            bodyText__ = base.getScdValue();
            publicType__ = base.getScdPublic();

            pluginId__ = base.getScdAppendId();
            usageName__ = base.getScdAppendDspName();
            urlText__ = base.getScdAppendUrl();

        }

    }
    /**
     * <p>targetId を取得します。
     * @return targetId
     * @see SchOtherEntitiesResultModel#targetId__
     */
    public String getTargetId() {

        return targetId__;
    }
    /**
     * <p>targetId をセットします。
     * @param targetId targetId
     * @see SchOtherEntitiesResultModel#targetId__
     */
    public void setTargetId(String targetId) {
        targetId__ = targetId;
    }
    /**
     * <p>targetName を取得します。
     * @return targetName
     * @see SchOtherEntitiesResultModel#targetName__
     */
    public String getTargetName() {
        return targetName__;
    }
    /**
     * <p>targetName をセットします。
     * @param targetName targetName
     * @see SchOtherEntitiesResultModel#targetName__
     */
    public void setTargetName(String targetName) {
        targetName__ = targetName;
    }
    /**
     * <p>targetSei を取得します。
     * @return targetSei
     * @see SchOtherEntitiesResultModel#targetSei__
     */
    public String getTargetSeiText() {
        return targetSeiText__;
    }
    /**
     * <p>targetSei をセットします。
     * @param targetSei targetSei
     * @see SchOtherEntitiesResultModel#targetSei__
     */
    public void setTargetSeiText(String targetSei) {
        targetSeiText__ = targetSei;
    }
    /**
     * <p>targetMei を取得します。
     * @return targetMei
     * @see SchOtherEntitiesResultModel#targetMei__
     */
    public String getTargetMeiText() {
        return targetMeiText__;
    }
    /**
     * <p>targetMei をセットします。
     * @param targetMei targetMei
     * @see SchOtherEntitiesResultModel#targetMei__
     */
    public void setTargetMeiText(String targetMei) {
        targetMeiText__ = targetMei;
    }
    /**
     * <p>targetSeiKana を取得します。
     * @return targetSeiKana
     * @see SchOtherEntitiesResultModel#targetSeiKana__
     */
    public String getTargetSeiKanaText() {
        return targetSeiKanaText__;
    }
    /**
     * <p>targetSeiKana をセットします。
     * @param targetSeiKana targetSeiKana
     * @see SchOtherEntitiesResultModel#targetSeiKana__
     */
    public void setTargetSeiKanaText(String targetSeiKana) {
        targetSeiKanaText__ = targetSeiKana;
    }
    /**
     * <p>targetMeiKana を取得します。
     * @return targetMeiKana
     * @see SchOtherEntitiesResultModel#targetMeiKana__
     */
    public String getTargetMeiKanaText() {
        return targetMeiKanaText__;
    }
    /**
     * <p>targetMeiKana をセットします。
     * @param targetMeiKana targetMeiKana
     * @see SchOtherEntitiesResultModel#targetMeiKana__
     */
    public void setTargetMeiKanaText(String targetMeiKana) {
        targetMeiKanaText__ = targetMeiKana;
    }
    /**
     * <p>targetLoginStopFlg を取得します。
     * @return targetLoginStopFlg
     * @see SchOtherEntitiesResultModel#targetLoginStopFlg__
     */
    public int getTargetLoginStopFlg() {
        return targetLoginStopFlg__;
    }
    /**
     * <p>targetLoginStopFlg をセットします。
     * @param targetLoginStopFlg targetLoginStopFlg
     * @see SchOtherEntitiesResultModel#targetLoginStopFlg__
     */
    public void setTargetLoginStopFlg(int targetLoginStopFlg) {
        targetLoginStopFlg__ = targetLoginStopFlg;
    }
    /**
     * <p>startDateTime を取得します。
     * @return startDateTime
     * @see SchOtherEntitiesResultModel#startDateTime__
     */
    public UDate getStartDateTime() {
        return startDateTime__;
    }
    /**
     * <p>startDateTime をセットします。
     * @param startDateTime startDateTime
     * @see SchOtherEntitiesResultModel#startDateTime__
     */
    public void setStartDateTime(UDate startDateTime) {
        startDateTime__ = startDateTime;
    }
    /**
     * <p>endDateTime を取得します。
     * @return endDateTime
     * @see SchOtherEntitiesResultModel#endDateTime__
     */
    public UDate getEndDateTime() {
        return endDateTime__;
    }
    /**
     * <p>endDateTime をセットします。
     * @param endDateTime endDateTime
     * @see SchOtherEntitiesResultModel#endDateTime__
     */
    public void setEndDateTime(UDate endDateTime) {
        endDateTime__ = endDateTime;
    }
    /**
     * <p>useTimeFlg を取得します。
     * @return useTimeFlg
     * @see SchOtherEntitiesResultModel#useTimeFlg__
     */
    public int getUseTimeFlg() {
        return useTimeFlg__;
    }
    /**
     * <p>useTimeFlg をセットします。
     * @param useTimeFlg useTimeFlg
     * @see SchOtherEntitiesResultModel#useTimeFlg__
     */
    public void setUseTimeFlg(int useTimeFlg) {
        useTimeFlg__ = useTimeFlg;
    }
    /**
     * <p>titleText を取得します。
     * @return titleText
     * @see SchOtherEntitiesResultModel#titleText__
     */
    public String getTitleText() {
        return titleText__;
    }
    /**
     * <p>titleText をセットします。
     * @param titleText titleText
     * @see SchOtherEntitiesResultModel#titleText__
     */
    public void setTitleText(String titleText) {
        titleText__ = titleText;
    }
    /**
     * <p>colorType を取得します。
     * @return colorType
     * @see SchOtherEntitiesResultModel#colorType__
     */
    public int getColorType() {
        return colorType__;
    }
    /**
     * <p>colorType をセットします。
     * @param colorType colorType
     * @see SchOtherEntitiesResultModel#colorType__
     */
    public void setColorType(int colorType) {
        colorType__ = colorType;
    }
    /**
     * <p>bodyText を取得します。
     * @return bodyText
     * @see SchOtherEntitiesResultModel#bodyText__
     */
    public String getBodyText() {
        return bodyText__;
    }
    /**
     * <p>bodyText をセットします。
     * @param bodyText bodyText
     * @see SchOtherEntitiesResultModel#bodyText__
     */
    public void setBodyText(String bodyText) {
        bodyText__ = bodyText;
    }
    /**
     * <p>publicType を取得します。
     * @return publicType
     * @see SchOtherEntitiesResultModel#publicType__
     */
    public int getPublicType() {
        return publicType__;
    }
    /**
     * <p>publicType をセットします。
     * @param publicType publicType
     * @see SchOtherEntitiesResultModel#publicType__
     */
    public void setPublicType(int publicType) {
        publicType__ = publicType;
    }
    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see SchOtherEntitiesResultModel#pluginId__
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     * @see SchOtherEntitiesResultModel#pluginId__
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * <p>pluginName を取得します。
     * @return pluginName
     * @see SchOtherEntitiesResultModel#usageName__
     */
    public String getUsageName() {
        return usageName__;
    }
    /**
     * <p>pluginName をセットします。
     * @param pluginName pluginName
     * @see SchOtherEntitiesResultModel#usageName__
     */
    public void setUsageName(String pluginName) {
        usageName__ = pluginName;
    }
    /**
     * <p>urlText を取得します。
     * @return urlText
     * @see SchOtherEntitiesResultModel#urlText__
     */
    public String getUrlText() {
        return urlText__;
    }
    /**
     * <p>urlText をセットします。
     * @param urlText urlText
     * @see SchOtherEntitiesResultModel#urlText__
     */
    public void setUrlText(String urlText) {
        urlText__ = urlText;
    }



}
