package jp.groupsession.v2.sch.restapi.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat.Format;
import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ResponceModel(targetField = {
        "sid",
        "targetType",
        "targetId",
        "targetName",
        "targetSeiText",
        "targetMeiText",
        "targetSeiKanaText",
        "targetMeiKanaText",
        "targetLoginStopFlg",
        "startDateTime",
        "endDateTime",
        "useTimeFlg",
        "titleText",
        "colorType",
        "bodyText",
        "memoText",
        "tmpFileArray",
        "editType",
        "publicType",
        "publicTargetUserArray",
        "publicTargetGroupArray",
        "useAttendFlg",
        "ableEditFlg",
        "registName",
        "registSeiText",
        "registMeiText",
        "registSeiKanaText",
        "registMeiKanaText",
        "registUserDeleteFlg",
        "registLoginStopFlg",
        "addDateTime",
        "editDateTime",
        "attendAnsType",
        "attendResponderFlg",
        "attendAnsDateTime",
        "remindGrpFlg",
        "remindTimingType",
        "sameScheduledArray",
        "facilityArray",
        "clientArray"
})
public class SchEntitiesResultModel {
    /** 予定あり時タイトル*/
    private String yoteiariText__;

    /**
     * <p>yoteiariText をセットします。
     * @param yoteiariText yoteiariText
     * @see jp.groupsession.v2.sch.restapi.entities.SchEntitiesResultModel#yoteiariText__
     */
    public void setYoteiariText(String yoteiariText) {
        yoteiariText__ = yoteiariText;
    }
    /** 添付ファイル配列*/
    @ChildElementName("tmpFileInfo")
    private List<TmpFileInfo> tmpFileArray__;
    public static class TmpFileInfo {
        /** 含有モデル*/
        private final CmnBinfModel base__;
        /**
         *
         * コンストラクタ
         * @param base モデル
         */
        public TmpFileInfo(CmnBinfModel base) {
            base__ = base;
        }
        /** @return binSid */
        public long getBinSid() {
            return base__.getBinSid();
        }
        /** @return fileName */
        public String getFileName() {
            return base__.getBinFileName();
        }
        /** @return fileSizeByteNum */
        public long getFileSizeByteNum() {
            return base__.getBinFileSize();
        }
    }

    /** 個別公開対象   ユーザ                                    */
    @ChildElementName("publicTargetUserInfo")
    private List<PublicTargetUserInf> publicTargetUserArray__ = new ArrayList<>();
    /** 個別公開対象 グループ                                */
    @ChildElementName("publicTargetGroupInfo")
    private List<PublicTargetGroupInf> publicTargetGroupArray__ = new ArrayList<>();
    /** 同時登録スケジュール配列                                         */
    @ResponceModel(targetField = {
        "targetSid",
        "targetId",
        "targetName",
        "targetSeiText",
        "targetMeiText",
        "targetSeiKanaText",
        "targetMeiKanaText",
        "targetLoginStopFlg",
        "attendAnsType",
        "attendResponderFlg",
        "attendAnsDateTime",
        "ableEditFlg"
        })
    @ChildElementName("sameScheduledInfo")
    private List<SchEntitiesResultModel> sameScheduledArray__ = new ArrayList<>();
    /** 同時登録施設情報配列                                       */
    @ChildElementName("facilityInfo")
    private List<FacilityInfo> facilityArray__;
    /** 施設情報                                         */
    public static class FacilityInfo {
        /** 施設情報*/
        private RsvSisDataModel rsv__;
        /**
         *
         * コンストラクタ
         * @param rsv
         */
        public FacilityInfo(RsvSisDataModel rsv) {
            rsv__ = rsv;
            // TODO 自動生成されたコンストラクター・スタブ
        }
        /** @return 施設SID                                         */
        public int getSid() {
            return rsv__.getRsdSid();
        }
        /** @return 施設ID                                         */
        public String getId() {
            return rsv__.getRsdId();
        }
        /** @return  施設名                                      */
        public String getName() {
            return rsv__.getRsdName();
        }

    }
    /** 会社拠点情報配列                                         */
    @ChildElementName("clientInfo")
    private List<ClientInfo> clientArray__ = new ArrayList<>();
    /** 表示情報 */
    private EnumDispMode dispMode__ = EnumDispMode.SECRET;
    /** 登録者 */
    private CmnUserModel addUser__;
    /** 対象グループ */
    private CmnGroupmModel targetGroup__;
    /** 対象ユーザ情報 */
    private CmnUserModel targetUser__;
    /** スケジュール情報 */
    private SchDataModel baseSch__;

    /** 編集権限フラグ*/
    private int ableEditFlg__ = 1;

    /** 会社拠点情報                                       */
    @ResponceModel(targetField = {
            "companyId",
            "baseSid",
            "baseId",
            "companyName",
            "baseName",
            "baseTypeText",
            "addressArray"})
    public static class ClientInfo {
        /** 企業情報*/
        private AdrCompanyModel aco__;
        /** 企業拠点情報*/
        private AdrCompanyBaseModel aba__;
        /**  連絡先情報配列                                      */
        @ChildElementName("addressInfo")
        private List<AddressInfo> addressArray__ = new ArrayList<>();
        /** メッセージリソース */
        private GsMessage gsMsg__;
        /**
         *
         * コンストラクタ
         * @param aco 企業情報
         * @param aba 企業拠点情報
         * @param gsMsg メッセージリソース
         */
        public ClientInfo(AdrCompanyModel aco, AdrCompanyBaseModel aba, GsMessage gsMsg) {
            aco__ = aco;
            aba__ = aba;
            gsMsg__ = gsMsg;
        }
        /** @return  企業コード                                        */
        public String getCompanyId() {
            if (aco__ == null) {
                return "";
            }
            return aco__.getAcoCode();
        }
        /** @return 会社拠点SID                                      */
        public Integer getBaseSid() {
            if (aba__ == null) {
                return 0;
            }
            return aba__.getAbaSid();
        }
        /** @return 会社拠点コード                                      */
        public String getBaseId() {
            if (aco__ == null) {
                return "";
            }
            return String.format("%s:%d",
                    getCompanyId(),
                    getBaseSid());
        }

        /** @return 企業名                                      */
        public String getCompanyName() {
            if (aco__ == null) {
                return gsMsg__.getMessage("schedule.src.87");
            }
            return aco__.getAcoName();
        }
        /** @return 会社拠点名                                        */
        public String getBaseName() {
            if (aba__ == null) {
                return "";
            }
            return aba__.getAbaName();
        }
        /** @return 会社拠点種類名                                        */
        public String getBaseTypeText() {
            if (aba__ == null) {
                return "";
            }
            switch (aba__.getAbaType()) {
            case GSConstAddress.ABATYPE_HEADOFFICE:
                return gsMsg__.getMessage("address.122");
            case GSConstAddress.ABATYPE_BRANCH:
                return gsMsg__.getMessage("address.123");
            case GSConstAddress.ABATYPE_BUSINESSOFFICE:
                return gsMsg__.getMessage("address.124");
            default:
            }
            return "";
        }

        /**
         * <p>addressArray を取得します。
         * @return addressArray
         * @see SchEntitiesResultModel.ClientInfo#addressArray__
         */
        public List<AddressInfo> getAddressArray() {
            return addressArray__;
        }
        /**
         * <p>addressArray をセットします。
         * @param addressArray addressArray
         * @see SchEntitiesResultModel.ClientInfo#addressArray__
         */
        public void setAddressArray(List<AddressInfo> addressArray) {
            addressArray__ = addressArray;
        }
    }
    /** 連絡先情報                                        */
    public static class AddressInfo {
        /** アドレス情報 */
        private AdrAddressModel adrData__;
        public AddressInfo(AdrAddressModel adrData) {
            adrData__ = adrData;
        }
        /**
         * <p>sid を取得します。
         * @return sid
         * @see SchEntitiesResultModel.AddressInfo#sid__
         */
        public Integer getSid() {
            return adrData__.getAdrSid();
        }
        /**
         * <p>name を取得します。
         * @return name
         * @see SchEntitiesResultModel.AddressInfo#name__
         */
        public String getName() {
            return String.format("%s　%s", adrData__.getAdrSei(), adrData__.getAdrMei());
        }
    }
    /**
     * <p>sid を取得します。
     * @return sid
     * @see SchEntitiesResultModel#sid__
     */
    public Integer getSid() {
        return baseSch__.getScdSid();
    }
    /**
     * <p>targetFlg を取得します。
     * @return targetFlg
     * @see SchEntitiesResultModel#targetFlg__
     */
    public Integer getTargetType() {
        return baseSch__.getScdUsrKbn();
    }
    /**
     * <p>targetSid を取得します。
     * @return targetSid
     * @see SchEntitiesResultModel#targetSid__
     */
    public int getTargetSid() {
        if (baseSch__.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            return targetUser__.getUsrSid();
        } else {
            return targetGroup__.getGrpSid();
        }
    }
    /**
     * <p>targetId を取得します。
     * @return targetId
     * @see SchEntitiesResultModel#targetId__
     */
    public String getTargetId() {
        if (baseSch__.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            return targetUser__.getUsrLgid();
        } else {
            return targetGroup__.getGrpId();
        }
    }
    /**
     * <p>targetName を取得します。
     * @return targetName
     * @see SchEntitiesResultModel#targetName__
     */
    public String getTargetName() {
        if (baseSch__.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            return targetUser__.getUsiName();
        } else {
            return targetGroup__.getGrpName();
        }

    }
    /**
     * <p>targetSei を取得します。
     * @return targetSei
     * @see SchEntitiesResultModel#targetSei__
     */
    public String getTargetSeiText() {
        if (baseSch__.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            return targetUser__.getUsiSei();
        } else {
            return "";
        }
    }
    /**
     * <p>targetMei を取得します。
     * @return targetMei
     * @see SchEntitiesResultModel#targetMei__
     */
    public String getTargetMeiText() {
        if (baseSch__.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            return targetUser__.getUsiMei();
        } else {
            return "";
        }
    }
    /**
     * <p>targetSeiKana を取得します。
     * @return targetSeiKana
     * @see SchEntitiesResultModel#targetSeiKana__
     */
    public String getTargetSeiKanaText() {
        if (baseSch__.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            return targetUser__.getUsiSeiKn();
        } else {
            return "";
        }
    }
    /**
     * <p>targetMeiKana を取得します。
     * @return targetMeiKana
     * @see SchEntitiesResultModel#targetMeiKana__
     */
    public String getTargetMeiKanaText() {
        if (baseSch__.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            return targetUser__.getUsiMeiKn();
        } else {
            return "";
        }
    }
    /**
     * <p>targetLoginStopFlg を取得します。
     * @return targetLoginStopFlg
     * @see SchEntitiesResultModel#targetLoginStopFlg__
     */
    public Integer getTargetLoginStopFlg() {
        if (baseSch__.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            return targetUser__.getUsrUkoFlg();
        } else {
            return null;
        }
    }
    /**
     * <p>startDateTime を取得します。
     * @return startDateTime
     * @see SchEntitiesResultModel#startDateTime__
     */
    @UDateFormat(Format.DATETIME_SLUSH_HHMM)
    public UDate getStartDateTime() {
        return baseSch__.getScdFrDate();
    }
    /**
     * <p>endDateTime を取得します。
     * @return endDateTime
     * @see SchEntitiesResultModel#endDateTime__
     */
    @UDateFormat(Format.DATETIME_SLUSH_HHMM)
    public UDate getEndDateTime() {
        return baseSch__.getScdToDate();
    }
    /**
     * <p>useTimeFlg を取得します。
     * @return useTimeFlg
     * @see SchEntitiesResultModel#useTimeFlg__
     */
    public Integer getUseTimeFlg() {
        return baseSch__.getScdDaily();
    }
    /**
     * <p>titleText を取得します。
     * @return titleText
     * @see SchEntitiesResultModel#titleText__
     */
    public String getTitleText() {
        switch (dispMode__) {
            case SECRET:
                return "";
            case YOTEIARI:
                return yoteiariText__;
            default:
        }

        return baseSch__.getScdTitle();
    }

    /**
     * <p>colorType を取得します。
     * @return colorType
     * @see SchEntitiesResultModel#colorType__
     */
    public Integer getColorType() {
        switch (dispMode__) {
            case SECRET:
            case YOTEIARI:
                return 0;
            default:
        }

        return baseSch__.getScdBgcolor();
    }
    /**
     * <p>bodyText を取得します。
     * @return bodyText
     * @see SchEntitiesResultModel#bodyText__
     */
    public String getBodyText() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return "";
        default:
        }
        return baseSch__.getScdValue();
    }
    /**
     * <p>memo を取得します。
     * @return memo
     * @see SchEntitiesResultModel#memo__
     */
    public String getMemoText() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return "";
        default:
        }
        return baseSch__.getScdBiko();
    }
    /**
     * <p>tmpFileArray を取得します。
     * @return tmpFileArray
     * @see SchEntitiesResultModel#tmpFileArray__
     */
    public List<TmpFileInfo> getTmpFileArray() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return List.of();
        default:
        }
        return tmpFileArray__;
    }
    /**
     * <p>tmpFileArray をセットします。
     * @param tmpFileArray tmpFileArray
     * @see SchEntitiesResultModel#tmpFileArray__
     */
    public void setTmpFileArray(List<TmpFileInfo> tmpFileArray) {
        tmpFileArray__ = tmpFileArray;
    }
    /**
     * <p>editType を取得します。
     * @return editType
     * @see SchEntitiesResultModel#editType__
     */
    public Integer getEditType() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return 0;
        default:
        }
        return baseSch__.getScdEdit();
    }
    /**
     * <p>publicType を取得します。
     * @return publicType
     * @see SchEntitiesResultModel#publicType__
     */
    public Integer getPublicType() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return GSConstSchedule.DSP_NOT_PUBLIC;
        default:
        }
        return baseSch__.getScdPublic();
    }

    /**
     * <p>publicTargetUserArray を取得します。
     * @return publicTargetUserArray
     * @see SchEntitiesResultModel#publicTargetUserArray__
     */
    public List<PublicTargetUserInf> getPublicTargetUserArray() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return List.of();
        default:
        }
        return publicTargetUserArray__;
    }
    /**
     * <p>publicTargetUserArray をセットします。
     * @param publicTargetUserArray publicTargetUserArray
     * @see SchEntitiesResultModel#publicTargetUserArray__
     */
    public void setPublicTargetUserArray(
            List<PublicTargetUserInf> publicTargetUserArray) {
        publicTargetUserArray__ = publicTargetUserArray;
    }
    /**
     * <p>publicTargetGroupArray を取得します。
     * @return publicTargetGroupArray
     * @see SchEntitiesResultModel#publicTargetGroupArray__
     */
    public List<PublicTargetGroupInf> getPublicTargetGroupArray() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return List.of();
        default:
        }
        return publicTargetGroupArray__;
    }
    /**
     * <p>publicTargetGroupArray をセットします。
     * @param publicTargetGroupArray publicTargetGroupArray
     * @see SchEntitiesResultModel#publicTargetGroupArray__
     */
    public void setPublicTargetGroupArray(
            List<PublicTargetGroupInf> publicTargetGroupArray) {
        publicTargetGroupArray__ = publicTargetGroupArray;
    }
    /**
     * <p>useAttendFlg を取得します。
     * @return useAttendFlg
     * @see SchEntitiesResultModel#useAttendFlg__
     */
    public Integer getUseAttendFlg() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return GSConstSchedule.ATTEND_KBN_NO;
        default:
        }
        return baseSch__.getScdAttendKbn();
    }
    /**
     * <p>ableEditFlg を取得します。
     * @return ableEditFlg
     * @see SchEntitiesResultModel#ableEditFlg__
     */
    public Integer getAbleEditFlg() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return 0;
        default:
        }
        return ableEditFlg__;
    }
    /**
     * <p>ableEditFlg をセットします。
     * @param ableEditFlg ableEditFlg
     * @see SchEntitiesResultModel#ableEditFlg__
     */
    public void setAbleEditFlg(Integer ableEditFlg) {
        ableEditFlg__ = ableEditFlg;
    }
    /**
     * <p>registSei を取得します。
     * @return registSei
     * @see SchEntitiesResultModel#registSei__
     */
    public String getRegistName() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return "";
        default:
        }
        return addUser__.getUsiName();
    }
    /**
     * <p>registSei を取得します。
     * @return registSei
     * @see SchEntitiesResultModel#registSei__
     */
    public String getRegistSeiText() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return "";
        default:
        }
        return addUser__.getUsiSei();
    }
    /**
     * <p>registMei を取得します。
     * @return registMei
     * @see SchEntitiesResultModel#registMei__
     */
    public String getRegistMeiText() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return "";
        default:
        }
        return addUser__.getUsiMei();
    }
    /**
     * <p>registSeiKana を取得します。
     * @return registSeiKana
     * @see SchEntitiesResultModel#registSeiKana__
     */
    public String getRegistSeiKanaText() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return "";
        default:
        }
        return addUser__.getUsiSeiKn();
    }
    /**
     * <p>registMeiKana を取得します。
     * @return registMeiKana
     * @see SchEntitiesResultModel#registMeiKana__
     */
    public String getRegistMeiKanaText() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return "";
        default:
        }
        return addUser__.getUsiMeiKn();
    }
    /**
     * <p>registUserJkbn を取得します。
     * @return registUserJkbn
     * @see SchEntitiesResultModel#registUserJkbn__
     */
    public Integer getRegistUserDeleteFlg() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return 0;
        default:
        }
        if (addUser__.getUsrJkbn() == GSConst.JTKBN_DELETE) {
            return 1;
        }
        return addUser__.getUsrJkbn();
    }
    /**
     * <p>registLoginStopFlg を取得します。
     * @return registLoginStopFlg
     * @see SchEntitiesResultModel#registLoginStopFlg__
     */
    public Integer getRegistLoginStopFlg() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return 0;
        default:
        }

        return addUser__.getUsrUkoFlg();
    }
    /**
     * <p>addDateTime を取得します。
     * @return addDateTime
     * @see SchEntitiesResultModel#addDateTime__
     */
    @UDateFormat(Format.DATETIME_SLUSH)
    public UDate getAddDateTime() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return null;
        default:
        }

        return baseSch__.getScdAdate();
    }

    /**
     * <p>editDateTime を取得します。
     * @return editDateTime
     * @see SchEntitiesResultModel#editDateTime__
     */
    @UDateFormat(Format.DATETIME_SLUSH)
    public UDate getEditDateTime() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return null;
        default:
        }
        return baseSch__.getScdEdate();
    }
    /**
     * <p>attendAnsType を取得します。
     * @return attendAnsType
     * @see SchEntitiesResultModel#attendAnsType__
     */
    public Integer getAttendAnsType() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return 0;
        default:
        }
        return baseSch__.getScdAttendAns();
    }
    /**
     * <p>attendResponderFlg を取得します。
     * @return attendResponderFlg
     * @see SchEntitiesResultModel#attendResponderFlg__
     */
    public Integer getAttendResponderFlg() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return 0;
        default:
        }
        return baseSch__.getScdAttendAuKbn();
    }
    /**
     * <p>attendAnsDateTime を取得します。
     * @return attendAnsDateTime
     * @see SchEntitiesResultModel#attendAnsDateTime__
     */
    public String getAttendAnsDateTime() {
        if (Objects.equals(getUseAttendFlg(), GSConstSchedule.ATTEND_KBN_NO)) {
            return "-";
        }
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return "-";
        default:
        }
        if (Objects.equals(getAttendAnsType(), GSConstSchedule.ATTEND_ANS_NONE)) {
            return "-";
        }

        String format = Format.DATETIME_SLUSH.value();

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format((baseSch__.getScdEdate()).toJavaUtilDate());
    }
    /**
     * <p>remindGrpFlg を取得します。
     * @return remindGrpFlg
     * @see SchEntitiesResultModel#remindGrpFlg__
     */
    public Integer getRemindGrpFlg() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return 0;
        default:
        }
        return baseSch__.getScdTargetGrp();
    }
    /**
     * <p>remindTimingType を取得します。
     * @return remindTimingType
     * @see SchEntitiesResultModel#remindTimingType__
     */
    public Integer getRemindTimingType() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return 0;
        default:
        }
        return baseSch__.getScdReminder();
    }
    /**
     * <p>sameScheduledArray を取得します。
     * @return sameScheduledArray
     * @see SchEntitiesResultModel#sameScheduledArray__
     */
    public List<SchEntitiesResultModel> getSameScheduledArray() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return List.of();
        default:
        }
        return sameScheduledArray__;
    }
    /**
     * <p>sameScheduledArray をセットします。
     * @param sameScheduledArray sameScheduledArray
     * @see SchEntitiesResultModel#sameScheduledArray__
     */
    public void setSameScheduledArray(
            List<SchEntitiesResultModel> sameScheduledArray) {
        sameScheduledArray__ = sameScheduledArray;
    }
    /**
     * <p>facilityArray を取得します。
     * @return facilityArray
     * @see SchEntitiesResultModel#facilityArray__
     */
    public List<FacilityInfo> getFacilityArray() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return List.of();
        default:
        }
        return facilityArray__;
    }
    /**
     * <p>facilityArray をセットします。
     * @param facilityArray facilityArray
     * @see SchEntitiesResultModel#facilityArray__
     */
    public void setFacilityArray(List<FacilityInfo> facilityArray) {
        facilityArray__ = facilityArray;
    }
    /**
     * <p>clientArray を取得します。
     * @return clientArray
     * @see SchEntitiesResultModel#clientArray__
     */
    public List<ClientInfo> getClientArray() {
        switch (dispMode__) {
        case SECRET:
        case YOTEIARI:
        case TITLE_ONLY:
            return List.of();
        default:
        }
        return clientArray__;
    }
    /**
     * <p>clientArray をセットします。
     * @param clientArray clientArray
     * @see SchEntitiesResultModel#clientArray__
     */
    public void setClientArray(List<ClientInfo> clientArray) {
        clientArray__ = clientArray;
    }

    /**
     *
     * <br>[機  能] 元のスケジュール情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param scData
     */
    public void setSchDate(SchDataModel scData) {
        baseSch__ = scData;

    }
    /**
     *
     * <br>[機  能] 対象ユーザ情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmnUserModel
     */
    public void setTargetUser(CmnUserModel cmnUserModel) {
        targetUser__ = cmnUserModel;

    }
    /**
     *
     * <br>[機  能] 対象グループ名を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmnGroupmModel
     */
    public void setTargetGroup(CmnGroupmModel cmnGroupmModel) {
        targetGroup__ = cmnGroupmModel;

    }
    /**
     *
     * <br>[機  能] 登録者情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmnUserModel
     */
    public void setAddUser(CmnUserModel cmnUserModel) {
        addUser__ = cmnUserModel;
    }
    /**
     *
     * <br>[機  能] 顧客情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param map
     */
    public void setCompanyMap(Map<String, ClientInfo> map) {
        if (map == null) {
            return;
        }
        setClientArray(
                map.values().stream()
                .collect(Collectors.toList())
                );

    }
    /**
     *
     * <br>[機  能] 施設予約を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param list 連動施設予約情報
     */
    public void setReserveList(List<RsvSisDataModel> list) {
        if (list == null) {
            return;
        }
        setFacilityArray(
                list.stream()
                .map(rsv -> new FacilityInfo(rsv))
                .collect(Collectors.toList())
                );
    }
    /**
     *
     * <br>[機  能] 表示モードをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param dispMode 表示モード
     */
    public void setDispMode(EnumDispMode dispMode) {
        dispMode__ = dispMode;
    }
}
