package jp.groupsession.v2.rsv.rsv090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.rsv.RsvValidateUtil;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.rsv080.Rsv080Form;
import jp.groupsession.v2.rsv.rsv080.Rsv080Model;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 施設登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv090Form extends Rsv080Form {
    /** 初期表示フラグ */
    private boolean rsv090InitFlg__ = true;
    /** 処理モード */
    private String rsv090ProcMode__ = null;
    /** 処理対象のグループSID */
    private int rsv090EditGrpSid__ = -1;
    /** 処理対象の施設SID */
    private int rsv090EditSisetuSid__ = -1;
    /** 所属グループ名称 */
    private String rsv090GrpName__ = null;
    /** 施設区分名称 */
    private String rsv090SisetuKbnName__ = null;
    /** 資産管理番号 */
    private String rsv090SisanKanri__ = null;
    /** 施設ID */
    private String rsv090SisetuId__ = null;
    /** 施設名称 */
    private String rsv090SisetuName__ = null;
    /** 表示項目1項目名称 */
    private String rsv090PropHeaderName1__ = null;
    /** 表示項目2項目名称 */
    private String rsv090PropHeaderName2__ = null;
    /** 表示項目3項目名称 */
    private String rsv090PropHeaderName3__ = null;
    /** 表示項目4項目名称 */
    private String rsv090PropHeaderName4__ = null;
    /** 表示項目5項目名称 */
    private String rsv090PropHeaderName5__ = null;
    /** 表示項目6項目名称 */
    private String rsv090PropHeaderName6__ = null;
    /** 表示項目7項目名称 */
    private String rsv090PropHeaderName7__ = null;
    /** 表示項目1項目入力欄 */
    private String rsv090Prop1Value__ = null;
    /** 表示項目2項目入力欄 */
    private String rsv090Prop2Value__ = String.valueOf(GSConstReserve.PROP_KBN_KA);
    /** 表示項目3項目入力欄 */
    private String rsv090Prop3Value__ = String.valueOf(GSConstReserve.PROP_KBN_KA);
    /** 表示項目4項目入力欄 */
    private String rsv090Prop4Value__ = null;
    /** 表示項目5項目入力欄 */
    private String rsv090Prop5Value__ = null;
    /** 表示項目6項目入力欄 */
    private String rsv090Prop6Value__ = null;
    /** 表示項目7項目入力欄 */
    private String rsv090Prop7Value__ = String.valueOf(GSConstReserve.PROP_KBN_KA);
    /** 備考 */
    private String rsv090Biko__ = null;
    /** 施設予約の承認 */
    private int rsv090apprKbn__ = 0;
    /** 施設予約の承認 設定可能フラグ */
    private boolean rsv090apprKbnInput__ = true;

    /** 画像ファイルバイナリSID */
    private String rsv090BinSid__ = null;
    /** 施設/設備ファイルコンボデータ */
    private List<LabelValueBean> rsv090SisetuFileLabelList__ = null;
    /** 場所/地図ファイルコンボデータ */
    private ArrayList<Rsv090PlaceForm> place__ = new ArrayList<Rsv090PlaceForm>();
    /** 施設/設備画像デフォルト表示値 */
    private String rsv090SisetuImgDefoValue__ = String.valueOf(GSConstReserve.IMG_DEFO_VALUE);
    /** 添付ファイル(コンボで選択中) */
    private String[] rsv090selectFiles__ = null;
    /** 施設グループコンボリスト */
    private ArrayList<LabelValueBean> rsv090RsvGrpLabelList__ = null;
    /** 選択施設グループ */
    private String rsv090SelectRsvGrp__ = null;

    /** 場所コメント */
    private String rsv090PlaceComment__ = null;

    /** 表示区分名 施設ID */
    private String rsv090StrSisetuIdDspKbn__ = null;
    /** 表示区分名 資産管理番号 */
    private String rsv090StrSisankanriDspKbn__ = null;
    /** 表示区分名 項目1項目 */
    private String rsv090ItemDspKbn1__ = null;
    /** 表示区分名 項目2項目 */
    private String rsv090ItemDspKbn2__ = null;
    /** 表示区分名 項目3項目 */
    private String rsv090ItemDspKbn3__ = null;
    /** 表示区分名 項目4項目 */
    private String rsv090ItemDspKbn4__ = null;
    /** 表示区分名 項目5項目 */
    private String rsv090ItemDspKbn5__ = null;
    /** 表示区分名 項目6項目 */
    private String rsv090ItemDspKbn6__ = null;
    /** 表示区分名 項目7項目 */
    private String rsv090ItemDspKbn7__ = null;
    /** 表示区分名 デフォルト画像 */
    private String rsv090StrDefoImgDspKbn__ = null;
    /** 表示区分名 備考 */
    private String rsv090StrBikoDspKbn__ = null;
    /** 表示区分名 場所 */
    private String rsv090StrPlaceDspKbn__ = null;

    //*******************表示区分設定********************
    /** 施設ID */
    private String rsv090SisetuIdDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 資産管理番号 */
    private String rsv090SisanKanriDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 表示項目1項目入力欄 */
    private String rsv090Prop1ValueDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 表示項目2項目入力欄 */
    private String rsv090Prop2ValueDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 表示項目3項目入力欄 */
    private String rsv090Prop3ValueDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 表示項目4項目入力欄 */
    private String rsv090Prop4ValueDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 表示項目5項目入力欄 */
    private String rsv090Prop5ValueDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 表示項目6項目入力欄 */
    private String rsv090Prop6ValueDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 表示項目7項目入力欄 */
    private String rsv090Prop7ValueDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 備考 */
    private String rsv090BikoDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 場所コメント */
    private String rsv090PlaceCommentDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** デフォルト施設画像 */
    private String rsv090SisetuImgDefoDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);
    /** 施設予約の承認 */
    private String rsv090apprKbnDspKbn__ = String.valueOf(GSConstReserve.SISETU_IMG_OFF);

    /** 施設グループ管理者フラグ */
    private boolean rsv090sisGrpAdmFlg__ = false;
    /** 施設グループ 承認フラグ */
    private boolean rsv090sisGrpApprFlg__ = false;

    /**
     * @return rsv090PlaceFileLabel__ を戻す
     */
    public ArrayList<Rsv090PlaceForm> getPlaceFormList() {
        return place__;
    }

    /**
     * <p>place をセットします。
     * @param place 場所画像データ
     */
    public void setPlaceFormList(ArrayList<Rsv090PlaceForm> place) {
        place__ = place;
    }

    /**
     * @param iIndex インデックス番号
     * @return rsv090PlaceFileLabel を戻す
     */
    public Rsv090PlaceForm getPlace(int iIndex) {
        while (place__.size() <= iIndex) {
            place__.add(new Rsv090PlaceForm());
        }
        return (Rsv090PlaceForm) place__.get(iIndex);
    }

    /**
     * @return Rsv090PlaceForm[]
     */
    public Rsv090PlaceForm[] getPlace() {
        return (Rsv090PlaceForm[]) place__.toArray(new Rsv090PlaceForm[0]);
    }

    /**
     * @return 表の行数
     */
    public int getPlaceFormSize() {
        return place__.size();
    }

    /**
     * @param pos 行インデックス
     * @return place
     */
    protected Rsv090PlaceForm getPlaceForm(int pos) {
        while (pos >= place__.size()) {
            place__.add(null);
        }
        Rsv090PlaceForm form = (Rsv090PlaceForm) place__.get(pos);
        if (form == null) {
            form = new Rsv090PlaceForm();
            place__.set(pos, form);
        }
        return form;
    }

    /**
     * @param pos インデックス番号 pos
     * @param placeFileLabel 設定する placeFileLabel
     */
    public void setPlaceFileLabel(int pos, String placeFileLabel) {
        getPlaceForm(pos).setRsv090PlaceFileLabel(placeFileLabel);
    }

    /**
     * @param pos インデックス番号 pos
     * @param placeFileValue 設定する placeFileValue
     */
    public void setPlaceFileValue(int pos, String placeFileValue) {
        getPlaceForm(pos).setRsv090PlaceFileValue(placeFileValue);
    }

    /**
     * @param pos インデックス番号 pos
     * @param placeFileComment 設定する placeFileComment
     */
    public void setPlaceFileComment(int pos, String placeFileComment) {
        getPlaceForm(pos).setRsv090PlaceFileComment(placeFileComment);
    }

    /**
     * @param pos インデックス番号 pos
     * @param placeFileCommentDspKbn 設定する placeFileCommentDspKbn
     */
    public void setPlaceFileCommentDspKbn(int pos, String placeFileCommentDspKbn) {
        getPlaceForm(pos).setRsv090PlaceFileCommentDspKbn(placeFileCommentDspKbn);
    }

    /**
     * <p>rsv090SisanKanri__ を取得します。
     * @return rsv090SisanKanri
     */
    public String getRsv090SisanKanri() {
        return rsv090SisanKanri__;
    }
    /**
     * <p>rsv090SisanKanri__ をセットします。
     * @param rsv090SisanKanri rsv090SisanKanri__
     */
    public void setRsv090SisanKanri(String rsv090SisanKanri) {
        rsv090SisanKanri__ = rsv090SisanKanri;
    }
    /**
     * <p>rsv090SisetuName__ を取得します。
     * @return rsv090SisetuName
     */
    public String getRsv090SisetuName() {
        return rsv090SisetuName__;
    }
    /**
     * <p>rsv090SisetuName__をセットします。
     * @param rsv090SisetuName rsv090SisetuName__
     */
    public void setRsv090SisetuName(String rsv090SisetuName) {
        rsv090SisetuName__ = rsv090SisetuName;
    }
    /**
     * <p>rsv090GrpName__ を取得します。
     * @return rsv090GrpName
     */
    public String getRsv090GrpName() {
        return rsv090GrpName__;
    }
    /**
     * <p>rsv090GrpName__ をセットします。
     * @param rsv090GrpName rsv090GrpName__
     */
    public void setRsv090GrpName(String rsv090GrpName) {
        rsv090GrpName__ = rsv090GrpName;
    }
    /**
     * <p>rsv090SisetuKbnName__ を取得します。
     * @return rsv090SisetuKbnName
     */
    public String getRsv090SisetuKbnName() {
        return rsv090SisetuKbnName__;
    }
    /**
     * <p>rsv090SisetuKbnName__ をセットします。
     * @param rsv090SisetuKbnName rsv090SisetuKbnName__
     */
    public void setRsv090SisetuKbnName(String rsv090SisetuKbnName) {
        rsv090SisetuKbnName__ = rsv090SisetuKbnName;
    }
    /**
     * <p>rsv090InitFlg__ を取得します。
     * @return rsv090InitFlg
     */
    public boolean isRsv090InitFlg() {
        return rsv090InitFlg__;
    }
    /**
     * <p>rsv090InitFlg__ をセットします。
     * @param rsv090InitFlg rsv090InitFlg__
     */
    public void setRsv090InitFlg(boolean rsv090InitFlg) {
        rsv090InitFlg__ = rsv090InitFlg;
    }
    /**
     * <p>rsv090EditGrpSid__ を取得します。
     * @return rsv090EditGrpSid
     */
    public int getRsv090EditGrpSid() {
        return rsv090EditGrpSid__;
    }
    /**
     * <p>rsv090EditGrpSid__ をセットします。
     * @param rsv090EditGrpSid rsv090EditGrpSid__
     */
    public void setRsv090EditGrpSid(int rsv090EditGrpSid) {
        rsv090EditGrpSid__ = rsv090EditGrpSid;
    }
    /**
     * <p>rsv090EditSisetuSid__ を取得します。
     * @return rsv090EditSisetuSid
     */
    public int getRsv090EditSisetuSid() {
        return rsv090EditSisetuSid__;
    }
    /**
     * <p>rsv090EditSisetuSid__ をセットします。
     * @param rsv090EditSisetuSid rsv090EditSisetuSid__
     */
    public void setRsv090EditSisetuSid(int rsv090EditSisetuSid) {
        rsv090EditSisetuSid__ = rsv090EditSisetuSid;
    }
    /**
     * <p>rsv090ProcMode__ を取得します。
     * @return rsv090ProcMode
     */
    public String getRsv090ProcMode() {
        return rsv090ProcMode__;
    }
    /**
     * <p>rsv090ProcMode__ をセットします。
     * @param rsv090ProcMode rsv090ProcMode__
     */
    public void setRsv090ProcMode(String rsv090ProcMode) {
        rsv090ProcMode__ = rsv090ProcMode;
    }
    /**
     * <p>rsv090Prop1Value__ を取得します。
     * @return rsv090Prop1Value
     */
    public String getRsv090Prop1Value() {
        return rsv090Prop1Value__;
    }
    /**
     * <p>rsv090Prop1Value__ をセットします。
     * @param rsv090Prop1Value rsv090Prop1Value__
     */
    public void setRsv090Prop1Value(String rsv090Prop1Value) {
        rsv090Prop1Value__ = rsv090Prop1Value;
    }
    /**
     * <p>rsv090Prop2Value__ を取得します。
     * @return rsv090Prop2Value
     */
    public String getRsv090Prop2Value() {
        return rsv090Prop2Value__;
    }
    /**
     * <p>rsv090Prop2Value__ をセットします。
     * @param rsv090Prop2Value rsv090Prop2Value__
     */
    public void setRsv090Prop2Value(String rsv090Prop2Value) {
        rsv090Prop2Value__ = rsv090Prop2Value;
    }
    /**
     * <p>rsv090Prop3Value__ を取得します。
     * @return rsv090Prop3Value
     */
    public String getRsv090Prop3Value() {
        return rsv090Prop3Value__;
    }
    /**
     * <p>rsv090Prop3Value__ をセットします。
     * @param rsv090Prop3Value rsv090Prop3Value__
     */
    public void setRsv090Prop3Value(String rsv090Prop3Value) {
        rsv090Prop3Value__ = rsv090Prop3Value;
    }
    /**
     * <p>rsv090PropHeaderName1__ を取得します。
     * @return rsv090PropHeaderName1
     */
    public String getRsv090PropHeaderName1() {
        return rsv090PropHeaderName1__;
    }
    /**
     * <p>rsv090PropHeaderName1__ をセットします。
     * @param rsv090PropHeaderName1 rsv090PropHeaderName1__
     */
    public void setRsv090PropHeaderName1(String rsv090PropHeaderName1) {
        rsv090PropHeaderName1__ = rsv090PropHeaderName1;
    }
    /**
     * <p>rsv090PropHeaderName2__ を取得します。
     * @return rsv090PropHeaderName2
     */
    public String getRsv090PropHeaderName2() {
        return rsv090PropHeaderName2__;
    }
    /**
     * <p>rsv090PropHeaderName2__ をセットします。
     * @param rsv090PropHeaderName2 rsv090PropHeaderName2__
     */
    public void setRsv090PropHeaderName2(String rsv090PropHeaderName2) {
        rsv090PropHeaderName2__ = rsv090PropHeaderName2;
    }
    /**
     * <p>rsv090PropHeaderName3__ を取得します。
     * @return rsv090PropHeaderName3
     */
    public String getRsv090PropHeaderName3() {
        return rsv090PropHeaderName3__;
    }
    /**
     * <p>rsv090PropHeaderName3__ をセットします。
     * @param rsv090PropHeaderName3 rsv090PropHeaderName3__
     */
    public void setRsv090PropHeaderName3(String rsv090PropHeaderName3) {
        rsv090PropHeaderName3__ = rsv090PropHeaderName3;
    }
    /**
     * <p>rsv090Biko__ を取得します。
     * @return rsv090Biko
     */
    public String getRsv090Biko() {
        return rsv090Biko__;
    }
    /**
     * <p>rsv090Biko__ をセットします。
     * @param rsv090Biko rsv090Biko__
     */
    public void setRsv090Biko(String rsv090Biko) {
        rsv090Biko__ = rsv090Biko;
    }
    /**
     * <p>rsv090Prop4Value__ を取得します。
     * @return rsv090Prop4Value
     */
    public String getRsv090Prop4Value() {
        return rsv090Prop4Value__;
    }
    /**
     * <p>rsv090Prop4Value__ をセットします。
     * @param rsv090Prop4Value rsv090Prop4Value__
     */
    public void setRsv090Prop4Value(String rsv090Prop4Value) {
        rsv090Prop4Value__ = rsv090Prop4Value;
    }
    /**
     * <p>rsv090PropHeaderName4__ を取得します。
     * @return rsv090PropHeaderName4
     */
    public String getRsv090PropHeaderName4() {
        return rsv090PropHeaderName4__;
    }
    /**
     * <p>rsv090PropHeaderName4__ をセットします。
     * @param rsv090PropHeaderName4 rsv090PropHeaderName4__
     */
    public void setRsv090PropHeaderName4(String rsv090PropHeaderName4) {
        rsv090PropHeaderName4__ = rsv090PropHeaderName4;
    }
    /**
     * <p>rsv090Prop5Value__ を取得します。
     * @return rsv090Prop5Value
     */
    public String getRsv090Prop5Value() {
        return rsv090Prop5Value__;
    }
    /**
     * <p>rsv090Prop5Value__ をセットします。
     * @param rsv090Prop5Value rsv090Prop5Value__
     */
    public void setRsv090Prop5Value(String rsv090Prop5Value) {
        rsv090Prop5Value__ = rsv090Prop5Value;
    }
    /**
     * <p>rsv090PropHeaderName5__ を取得します。
     * @return rsv090PropHeaderName5
     */
    public String getRsv090PropHeaderName5() {
        return rsv090PropHeaderName5__;
    }
    /**
     * <p>rsv090PropHeaderName5__ をセットします。
     * @param rsv090PropHeaderName5 rsv090PropHeaderName5__
     */
    public void setRsv090PropHeaderName5(String rsv090PropHeaderName5) {
        rsv090PropHeaderName5__ = rsv090PropHeaderName5;
    }
    /**
     * <p>rsv090PropHeaderName6__ を取得します。
     * @return rsv090PropHeaderName6
     */
    public String getRsv090PropHeaderName6() {
        return rsv090PropHeaderName6__;
    }
    /**
     * <p>rsv090PropHeaderName6__ をセットします。
     * @param rsv090PropHeaderName6 rsv090PropHeaderName6__
     */
    public void setRsv090PropHeaderName6(String rsv090PropHeaderName6) {
        rsv090PropHeaderName6__ = rsv090PropHeaderName6;
    }
    /**
     * <p>rsv090Prop6Value__ を取得します。
     * @return rsv090Prop6Value
     */
    public String getRsv090Prop6Value() {
        return rsv090Prop6Value__;
    }
    /**
     * <p>rsv090Prop6Value__ をセットします。
     * @param rsv090Prop6Value rsv090Prop6Value__
     */
    public void setRsv090Prop6Value(String rsv090Prop6Value) {
        rsv090Prop6Value__ = rsv090Prop6Value;
    }
    /**
     * <p>rsv090Prop7Value__ を取得します。
     * @return rsv090Prop7Value
     */
    public String getRsv090Prop7Value() {
        return rsv090Prop7Value__;
    }
    /**
     * <p>rsv090Prop7Value__ をセットします。
     * @param rsv090Prop7Value rsv090Prop7Value__
     */
    public void setRsv090Prop7Value(String rsv090Prop7Value) {
        rsv090Prop7Value__ = rsv090Prop7Value;
    }
    /**
     * <p>rsv090PropHeaderName7__ を取得します。
     * @return rsv090PropHeaderName7
     */
    public String getRsv090PropHeaderName7() {
        return rsv090PropHeaderName7__;
    }
    /**
     * <p>rsv090PropHeaderName7__ をセットします。
     * @param rsv090PropHeaderName7 rsv090PropHeaderName7__
     */
    public void setRsv090PropHeaderName7(String rsv090PropHeaderName7) {
        rsv090PropHeaderName7__ = rsv090PropHeaderName7;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param tempDir テンポラリディレクトリ
     * @param req リクエスト
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public ActionErrors validateCheck(Connection con, String tempDir, HttpServletRequest req)
    throws SQLException, IOToolsException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;

        //メッセージ:施設ID
        String msgRoomId = gsMsg.getMessage(req, "reserve.55");

        //施設ID 未入力チェック
        if (StringUtil.isNullZeroString(rsv090SisetuId__)) {
            msg =
                new ActionMessage("error.input.required.text",
                        msgRoomId);
            StrutsUtil.addMessage(errors, msg, "rsv090SisetuId");
        //施設ID 桁数チェック
        } else if (rsv090SisetuId__.length() > GSConstReserve.MAX_LENGTH_SISETU_ID) {
            msg =
                new ActionMessage("error.input.length.text",
                                   msgRoomId,
                                   msgRoomId);
            StrutsUtil.addMessage(errors, msg, "rsv090SisetuId");
        //施設ID フォーマットチェック
        } else if (!GSValidateUtil.isOtheridFormat(rsv090SisetuId__)) {
            msg = new ActionMessage("error.input.format.text", msgRoomId);
            StrutsUtil.addMessage(errors, msg, "error.input.format.text");
        } else {
            //存在チェック
            RsvSisDataDao rsdDao = new RsvSisDataDao(con);
            int rsdSid = rsdDao.countRsdId(rsv090SisetuId__, rsv090EditSisetuSid__);
            if (rsdSid > 0) {
                msg = new ActionMessage("error.input.exist.data", msgRoomId);
                StrutsUtil.addMessage(errors, msg, "rsv090SisetuId");
            }

        }

        //施設名 未入力チェック
        if (StringUtil.isNullZeroString(rsv090SisetuName__)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage(req, "cmn.facility.name"));
            StrutsUtil.addMessage(errors, msg, "rsv090SisetuName");
        //施設名 桁数チェック
        } else if (rsv090SisetuName__.length() > GSConstReserve.MAX_LENGTH_SISETU_NAME) {
            msg =
                new ActionMessage("error.input.length.text",
                        gsMsg.getMessage(req, "cmn.facility.name"),
                                String.valueOf(GSConstReserve.MAX_LENGTH_SISETU_NAME));
            StrutsUtil.addMessage(errors, msg, "rsv090SisetuName");
        //施設名 スペースのみチェック
        } else if (ValidateUtil.isSpace(rsv090SisetuName__)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage(req, "cmn.facility.name"));
            StrutsUtil.addMessage(errors, msg, "rsv090SisetuName");
        //施設名 先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(rsv090SisetuName__)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage(req, "cmn.facility.name"));
            StrutsUtil.addMessage(errors, msg, "rsv090SisetuName");
            //施設名 タブチェック
        } else if (ValidateUtil.isTab(rsv090SisetuName__)) {
            String msgKey = "error.input.tab.text";
            msg = new ActionMessage(msgKey,
                    gsMsg.getMessage(req, "cmn.facility.name"));
            StrutsUtil.addMessage(errors, msg, "rsv090SisetuName");

        //施設名 JIS第2水準チェック
        } else if (!GSValidateUtil.isGsJapaneaseString(rsv090SisetuName__)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv090SisetuName__);
            msg =
                new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage(req, "cmn.facility.name"),
                        nstr);
            StrutsUtil.addMessage(errors, msg, "rsv090SisetuName");
        }

        //資産管理番号
        if (!StringUtil.isNullZeroString(rsv090SisanKanri__)) {
            //資産管理番号 桁数チェック
            if (rsv090SisanKanri__.length() > GSConstReserve.MAX_LENGTH_SISAN_KANRI) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(req, "cmn.asset.register.num"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_SISAN_KANRI));
                StrutsUtil.addMessage(errors, msg, "rsv090SisanKanri");
            //資産管理番号 スペースのみチェック
            } else if (ValidateUtil.isSpace(rsv090SisanKanri__)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(req, "cmn.asset.register.num"));
                StrutsUtil.addMessage(errors, msg, "rsv090SisanKanri");
            //資産管理番号 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(rsv090SisanKanri__)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(req, "cmn.asset.register.num"));
                StrutsUtil.addMessage(errors, msg, "rsv090SisanKanri");
            //資産管理番号 JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(rsv090SisanKanri__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv090SisanKanri__);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(req, "cmn.asset.register.num"),
                            nstr);
                StrutsUtil.addMessage(errors, msg, "rsv090SisanKanri");
            }
        }

        //場所コメント
        if (!StringUtil.isNullZeroString(rsv090PlaceComment__)) {
            //場所コメント 桁数チェック
            if (rsv090PlaceComment__.length() > GSConstReserve.MAX_LENGTH_MAP_COMMENT) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(req, "reserve.location.comments"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_MAP_COMMENT));
                StrutsUtil.addMessage(errors, msg, "rsv090PlaceComment");
            //場所コメント スペースのみチェック
            } else if (ValidateUtil.isSpace(rsv090PlaceComment__)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(req, "reserve.location.comments"));
                StrutsUtil.addMessage(errors, msg, "rsv090PlaceComment");
            //場所コメント 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(rsv090PlaceComment__)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(req, "reserve.location.comments"));
                StrutsUtil.addMessage(errors, msg, "rsv090PlaceComment");
            //場所コメント JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(rsv090PlaceComment__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv090PlaceComment__);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(req, "reserve.location.comments"),
                            nstr);
                StrutsUtil.addMessage(errors, msg, "rsv090PlaceComment");
            }
        }

        RsvSisGrpDao grpDao = new RsvSisGrpDao(con);
        Rsv080Model grpMdl = grpDao.getGrpBaseData(rsv090EditGrpSid__);
        int rskSid = grpMdl.getRskSid();

        //可変項目のチェック対象を取得
        String prop1Name = null;
        String prop2Name = null;
        String prop3Name = null;
        String prop4Name = null;
        String prop5Name = null;
        String prop6Name = null;
        String prop7Name = null;

        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                prop1Name = gsMsg.getMessage(req, "reserve.128");
                prop2Name = gsMsg.getMessage(req, "reserve.132");
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                prop1Name = gsMsg.getMessage(req, "reserve.130");
                prop3Name = gsMsg.getMessage(req, "reserve.133");
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                prop1Name = gsMsg.getMessage(req, "reserve.129");
                prop2Name = gsMsg.getMessage(req, "reserve.132");
                prop4Name = gsMsg.getMessage(req, "reserve.134");
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                prop1Name = gsMsg.getMessage(req, "reserve.131");
                prop3Name = gsMsg.getMessage(req, "reserve.133");
                prop5Name = GSConstReserve.RSK_TEXT_ISBN;
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                prop6Name = gsMsg.getMessage(req, "reserve.135");
                prop7Name = gsMsg.getMessage(req, "reserve.136");
                break;
            default:
                break;
        }

        //可変項目4
        if (!StringUtil.isNullZeroString(rsv090Prop4Value__)) {
            //可変項目4 桁数チェック
            if (rsv090Prop4Value__.length() > GSConstReserve.MAX_LENGTH_PROP4) {
                msg =
                    new ActionMessage("error.input.length.text",
                            prop5Name, String.valueOf(GSConstReserve.MAX_LENGTH_PROP4));
                StrutsUtil.addMessage(errors, msg, "rsv090Prop4Value");
            //可変項目4 スペースのみチェック
            } else if (ValidateUtil.isSpace(rsv090Prop4Value__)) {
                msg = new ActionMessage("error.input.spase.only", prop4Name);
                StrutsUtil.addMessage(errors, msg, "rsv090Prop4Value");
            //可変項目4 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(rsv090Prop4Value__)) {
                msg = new ActionMessage("error.input.spase.start", prop4Name);
                StrutsUtil.addMessage(errors, msg, "rsv090Prop4Value");
            //可変項目4 JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(rsv090Prop4Value__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv090Prop4Value__);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            prop4Name,
                            nstr);
                StrutsUtil.addMessage(errors, msg, "rsv090Prop4Value");
            }
        }

        //可変項目5
        if (!StringUtil.isNullZeroString(rsv090Prop5Value__)) {
            //可変項目5 桁数チェック
            if (rsv090Prop5Value__.length() > GSConstReserve.MAX_LENGTH_PROP5) {
                msg =
                    new ActionMessage("error.input.length.text",
                            prop5Name, String.valueOf(GSConstReserve.MAX_LENGTH_PROP5));
                StrutsUtil.addMessage(errors, msg, "rsv090Prop5Value");
            //可変項目5 スペースのみチェック
            } else if (ValidateUtil.isSpace(rsv090Prop5Value__)) {
                msg = new ActionMessage("error.input.spase.only", prop5Name);
                StrutsUtil.addMessage(errors, msg, "rsv090Prop5Value");
            //可変項目5 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(rsv090Prop5Value__)) {
                msg = new ActionMessage("error.input.spase.start", prop5Name);
                StrutsUtil.addMessage(errors, msg, "rsv090Prop5Value");
            //半角英数字とハイフンチェック
            } else if (!ValidateUtil.isAlpOrNumberOrHaifun(rsv090Prop5Value__)) {
                    msg =
                        new ActionMessage("error.format.isbn", prop5Name);
                    StrutsUtil.addMessage(errors, msg, "rsv090Prop5Value");
            //可変項目5 JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(rsv090Prop5Value__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(rsv090Prop5Value__);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            prop5Name,
                            nstr);
                StrutsUtil.addMessage(errors, msg, "rsv090Prop5Value");
            }
        }

        //可変項目1
        if (!StringUtil.isNullZeroString(rsv090Prop1Value__)) {
            //可変項目1 文字数
            if (rsv090Prop1Value__.length() > GSConstReserve.MAX_LENGTH_PROP1) {
                msg =
                    new ActionMessage("error.input.length.text",
                            prop1Name,
                                    String.valueOf(GSConstReserve.MAX_LENGTH_PROP1));
                StrutsUtil.addMessage(errors, msg, "rsv090Prop1Value");
            //可変項目1 半角数字
            } else if (!ValidateUtil.isNumber(rsv090Prop1Value__)) {
                msg = new ActionMessage("error.input.length.number2",
                        prop1Name,
                        String.valueOf(GSConstReserve.MAX_LENGTH_PROP1));
                StrutsUtil.addMessage(errors, msg, "rsv090Prop1Value");
            }
        }

        //可変項目2
        if (StringUtil.isNullZeroString(rsv090Prop2Value__)) {
            msg =
                new ActionMessage("error.select.required.text", prop2Name);
            StrutsUtil.addMessage(errors, msg, "rsv090Prop2Value");
        }

        //可変項目3
        if (StringUtil.isNullZeroString(rsv090Prop3Value__)) {
            msg =
                new ActionMessage("error.select.required.text", prop3Name);
            StrutsUtil.addMessage(errors, msg, "rsv090Prop3Value");
        }

        //可変項目7
        if (StringUtil.isNullZeroString(rsv090Prop7Value__)) {
            msg =
                new ActionMessage("error.select.required.text", prop7Name);
            StrutsUtil.addMessage(errors, msg, "rsv090Prop7Value");
        }

        //可変項目6
        if (!StringUtil.isNullZeroString(rsv090Prop6Value__)) {
            //可変項目6 文字数
            if (rsv090Prop6Value__.length() > GSConstReserve.MAX_LENGTH_PROP6) {
                msg =
                    new ActionMessage("error.input.length.text",
                            prop6Name,
                                    String.valueOf(GSConstReserve.MAX_LENGTH_PROP6));
                StrutsUtil.addMessage(errors, msg, "rsv090Prop6Value");
            //可変項目6 半角数字
            } else if (!ValidateUtil.isNumber(rsv090Prop6Value__)) {
                msg = new ActionMessage("error.input.length.number2",
                        prop6Name,
                        String.valueOf(GSConstReserve.MAX_LENGTH_PROP6));
                StrutsUtil.addMessage(errors, msg, "rsv090Prop6Value");
            }
        }

        //備考
        if (!StringUtil.isNullZeroString(rsv090Biko__)) {
            //備考 桁数チェック
            if (rsv090Biko__.length() > GSConstReserve.MAX_LENGTH_BIKO) {
                msg = new ActionMessage("error.input.length.textarea",
                        gsMsg.getMessage(req, "cmn.memo"),
                            String.valueOf(GSConstReserve.MAX_LENGTH_BIKO));
                StrutsUtil.addMessage(errors, msg, "rsv090Biko");
            }
            //備考 スペース・改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(rsv090Biko__)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                        gsMsg.getMessage(req, "cmn.memo"));
                StrutsUtil.addMessage(errors, msg, "rsv090Biko");
            }
            //備考 JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(rsv090Biko__)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(rsv090Biko__);
                msg = new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage(req, "cmn.memo"),
                        nstr);
                StrutsUtil.addMessage(errors, msg, "rsv090Biko__");
            }
        }

        //週間・日間表示画像区分
        if (rsv090SisetuImgDefoValue__.equals(String.valueOf(GSConstReserve.IMG_DEFO_VALUE))) {
            CommonBiz commonBiz = new CommonBiz();
            List<LabelValueBean> fileLabelList = commonBiz.getTempFileLabelList(tempDir);

            if (fileLabelList != null && fileLabelList.size() > 0) {
                msg = new ActionMessage("error.select.required.text",
                        gsMsg.getMessage(req, "reserve.143"));
                StrutsUtil.addMessage(errors, msg, "rsv090SisetuImgDefoValue__");
            }
        }

        //地図 コメント
        if (place__.isEmpty()) {
            return errors;
        }

        String placeComment = "";
        for (int i = 0; i < place__.size(); i++) {
            Rsv090PlaceForm inputForm = getPlaceForm(i);
            String[] msgNum = new String[]{String.valueOf(i + 1)};
            placeComment = gsMsg.getMessage(req, "reserve.place.comment", msgNum);
            RsvValidateUtil.validateTextField(errors,
                                  inputForm.getRsv090PlaceFileComment(),
                                  "rsv090PlaceFileComment" + i,
                                  placeComment,
                                  GSConstReserve.MAX_LENGTH_MAP_COMMENT, false);
        }

        return errors;
    }
    /**
     * @return rsv090SisetuId
     */
    public String getRsv090SisetuId() {
        return rsv090SisetuId__;
    }
    /**
     * @param rsv090SisetuId 設定する rsv090SisetuId
     */
    public void setRsv090SisetuId(String rsv090SisetuId) {
        rsv090SisetuId__ = rsv090SisetuId;
    }
    /**
     * <p>rsv090BinSid を取得します。
     * @return rsv090BinSid
     */
    public String getRsv090BinSid() {
        return rsv090BinSid__;
    }
    /**
     * <p>rsv090BinSid をセットします。
     * @param rsv090BinSid rsv090BinSid
     */
    public void setRsv090BinSid(String rsv090BinSid) {
        rsv090BinSid__ = rsv090BinSid;
    }
    /**
     * <p>rsv090SisetuImgDefoValue を取得します。
     * @return rsv090SisetuImgDefoValue
     */
    public String getRsv090SisetuImgDefoValue() {
        return rsv090SisetuImgDefoValue__;
    }
    /**
     * <p>rsv090SisetuImgDefoValue をセットします。
     * @param rsv090SisetuImgDefoValue rsv090SisetuImgDefoValue
     */
    public void setRsv090SisetuImgDefoValue(String rsv090SisetuImgDefoValue) {
        rsv090SisetuImgDefoValue__ = rsv090SisetuImgDefoValue;
    }
    /**
     * <p>rsv090selectFiles を取得します。
     * @return rsv090selectFiles
     */
    public String[] getRsv090selectFiles() {
        return rsv090selectFiles__;
    }
    /**
     * <p>rsv090selectFiles をセットします。
     * @param rsv090selectFiles rsv090selectFiles
     */
    public void setRsv090selectFiles(String[] rsv090selectFiles) {
        rsv090selectFiles__ = rsv090selectFiles;
    }

    /**
     * <p>rsv090SisetuFileLabelList を取得します。
     * @return rsv090SisetuFileLabelList
     */
    public List<LabelValueBean> getRsv090SisetuFileLabelList() {
        return rsv090SisetuFileLabelList__;
    }
    /**
     * <p>rsv090SisetuFileLabelList をセットします。
     * @param rsv090SisetuFileLabelList rsv090SisetuFileLabelList
     */
    public void setRsv090SisetuFileLabelList(
            List<LabelValueBean> rsv090SisetuFileLabelList) {
        rsv090SisetuFileLabelList__ = rsv090SisetuFileLabelList;
    }
    /**
     * <p>rsv090RsvGrpLabelList を取得します。
     * @return rsv090RsvGrpLabelList
     */
    public ArrayList<LabelValueBean> getRsv090RsvGrpLabelList() {
        return rsv090RsvGrpLabelList__;
    }
    /**
     * <p>rsv090RsvGrpLabelList をセットします。
     * @param rsv090RsvGrpLabelList rsv090RsvGrpLabelList
     */
    public void setRsv090RsvGrpLabelList(
            ArrayList<LabelValueBean> rsv090RsvGrpLabelList) {
        rsv090RsvGrpLabelList__ = rsv090RsvGrpLabelList;
    }
    /**
     * <p>rsv090SelectRsvGrp を取得します。
     * @return rsv090SelectRsvGrp
     */
    public String getRsv090SelectRsvGrp() {
        return rsv090SelectRsvGrp__;
    }
    /**
     * <p>rsv090SelectRsvGrp をセットします。
     * @param rsv090SelectRsvGrp rsv090SelectRsvGrp
     */
    public void setRsv090SelectRsvGrp(String rsv090SelectRsvGrp) {
        rsv090SelectRsvGrp__ = rsv090SelectRsvGrp;
    }

    /**
     * <p>rsv090BikoDspKbn を取得します。
     * @return rsv090BikoDspKbn
     */
    public String getRsv090BikoDspKbn() {
        return rsv090BikoDspKbn__;
    }

    /**
     * <p>rsv090BikoDspKbn をセットします。
     * @param rsv090BikoDspKbn rsv090BikoDspKbn
     */
    public void setRsv090BikoDspKbn(String rsv090BikoDspKbn) {
        rsv090BikoDspKbn__ = rsv090BikoDspKbn;
    }

    /**
     * <p>rsv090Prop1ValueDspKbn を取得します。
     * @return rsv090Prop1ValueDspKbn
     */
    public String getRsv090Prop1ValueDspKbn() {
        return rsv090Prop1ValueDspKbn__;
    }

    /**
     * <p>rsv090Prop1ValueDspKbn をセットします。
     * @param rsv090Prop1ValueDspKbn rsv090Prop1ValueDspKbn
     */
    public void setRsv090Prop1ValueDspKbn(String rsv090Prop1ValueDspKbn) {
        rsv090Prop1ValueDspKbn__ = rsv090Prop1ValueDspKbn;
    }

    /**
     * <p>rsv090Prop2ValueDspKbn を取得します。
     * @return rsv090Prop2ValueDspKbn
     */
    public String getRsv090Prop2ValueDspKbn() {
        return rsv090Prop2ValueDspKbn__;
    }

    /**
     * <p>rsv090Prop2ValueDspKbn をセットします。
     * @param rsv090Prop2ValueDspKbn rsv090Prop2ValueDspKbn
     */
    public void setRsv090Prop2ValueDspKbn(String rsv090Prop2ValueDspKbn) {
        rsv090Prop2ValueDspKbn__ = rsv090Prop2ValueDspKbn;
    }

    /**
     * <p>rsv090Prop3ValueDspKbn を取得します。
     * @return rsv090Prop3ValueDspKbn
     */
    public String getRsv090Prop3ValueDspKbn() {
        return rsv090Prop3ValueDspKbn__;
    }

    /**
     * <p>rsv090Prop3ValueDspKbn をセットします。
     * @param rsv090Prop3ValueDspKbn rsv090Prop3ValueDspKbn
     */
    public void setRsv090Prop3ValueDspKbn(String rsv090Prop3ValueDspKbn) {
        rsv090Prop3ValueDspKbn__ = rsv090Prop3ValueDspKbn;
    }

    /**
     * <p>rsv090Prop4ValueDspKbn を取得します。
     * @return rsv090Prop4ValueDspKbn
     */
    public String getRsv090Prop4ValueDspKbn() {
        return rsv090Prop4ValueDspKbn__;
    }

    /**
     * <p>rsv090Prop4ValueDspKbn をセットします。
     * @param rsv090Prop4ValueDspKbn rsv090Prop4ValueDspKbn
     */
    public void setRsv090Prop4ValueDspKbn(String rsv090Prop4ValueDspKbn) {
        rsv090Prop4ValueDspKbn__ = rsv090Prop4ValueDspKbn;
    }

    /**
     * <p>rsv090Prop5ValueDspKbn を取得します。
     * @return rsv090Prop5ValueDspKbn
     */
    public String getRsv090Prop5ValueDspKbn() {
        return rsv090Prop5ValueDspKbn__;
    }

    /**
     * <p>rsv090Prop5ValueDspKbn をセットします。
     * @param rsv090Prop5ValueDspKbn rsv090Prop5ValueDspKbn
     */
    public void setRsv090Prop5ValueDspKbn(String rsv090Prop5ValueDspKbn) {
        rsv090Prop5ValueDspKbn__ = rsv090Prop5ValueDspKbn;
    }

    /**
     * <p>rsv090Prop6ValueDspKbn を取得します。
     * @return rsv090Prop6ValueDspKbn
     */
    public String getRsv090Prop6ValueDspKbn() {
        return rsv090Prop6ValueDspKbn__;
    }

    /**
     * <p>rsv090Prop6ValueDspKbn をセットします。
     * @param rsv090Prop6ValueDspKbn rsv090Prop6ValueDspKbn
     */
    public void setRsv090Prop6ValueDspKbn(String rsv090Prop6ValueDspKbn) {
        rsv090Prop6ValueDspKbn__ = rsv090Prop6ValueDspKbn;
    }

    /**
     * <p>rsv090Prop7ValueDspKbn を取得します。
     * @return rsv090Prop7ValueDspKbn
     */
    public String getRsv090Prop7ValueDspKbn() {
        return rsv090Prop7ValueDspKbn__;
    }

    /**
     * <p>rsv090Prop7ValueDspKbn をセットします。
     * @param rsv090Prop7ValueDspKbn rsv090Prop7ValueDspKbn
     */
    public void setRsv090Prop7ValueDspKbn(String rsv090Prop7ValueDspKbn) {
        rsv090Prop7ValueDspKbn__ = rsv090Prop7ValueDspKbn;
    }

    /**
     * <p>rsv090SisanKanriDspKbn を取得します。
     * @return rsv090SisanKanriDspKbn
     */
    public String getRsv090SisanKanriDspKbn() {
        return rsv090SisanKanriDspKbn__;
    }

    /**
     * <p>rsv090SisanKanriDspKbn をセットします。
     * @param rsv090SisanKanriDspKbn rsv090SisanKanriDspKbn
     */
    public void setRsv090SisanKanriDspKbn(String rsv090SisanKanriDspKbn) {
        rsv090SisanKanriDspKbn__ = rsv090SisanKanriDspKbn;
    }

    /**
     * <p>rsv090SisetuIdDspKbn を取得します。
     * @return rsv090SisetuIdDspKbn
     */
    public String getRsv090SisetuIdDspKbn() {
        return rsv090SisetuIdDspKbn__;
    }

    /**
     * <p>rsv090SisetuIdDspKbn をセットします。
     * @param rsv090SisetuIdDspKbn rsv090SisetuIdDspKbn
     */
    public void setRsv090SisetuIdDspKbn(String rsv090SisetuIdDspKbn) {
        rsv090SisetuIdDspKbn__ = rsv090SisetuIdDspKbn;
    }

    /**
     * <p>rsv090SisetuImgDefoDspKbn を取得します。
     * @return rsv090SisetuImgDefoDspKbn
     */
    public String getRsv090SisetuImgDefoDspKbn() {
        return rsv090SisetuImgDefoDspKbn__;
    }

    /**
     * <p>rsv090SisetuImgDefoDspKbn をセットします。
     * @param rsv090SisetuImgDefoDspKbn rsv090SisetuImgDefoDspKbn
     */
    public void setRsv090SisetuImgDefoDspKbn(String rsv090SisetuImgDefoDspKbn) {
        rsv090SisetuImgDefoDspKbn__ = rsv090SisetuImgDefoDspKbn;
    }

    /**
     * <p>rsv090PlaceComment を取得します。
     * @return rsv090PlaceComment
     */
    public String getRsv090PlaceComment() {
        return rsv090PlaceComment__;
    }

    /**
     * <p>rsv090PlaceComment をセットします。
     * @param rsv090PlaceComment rsv090PlaceComment
     */
    public void setRsv090PlaceComment(String rsv090PlaceComment) {
        rsv090PlaceComment__ = rsv090PlaceComment;
    }

    /**
     * <p>rsv090PlaceCommentDspKbn を取得します。
     * @return rsv090PlaceCommentDspKbn
     */
    public String getRsv090PlaceCommentDspKbn() {
        return rsv090PlaceCommentDspKbn__;
    }

    /**
     * <p>rsv090PlaceCommentDspKbn をセットします。
     * @param rsv090PlaceCommentDspKbn rsv090PlaceCommentDspKbn
     */
    public void setRsv090PlaceCommentDspKbn(String rsv090PlaceCommentDspKbn) {
        rsv090PlaceCommentDspKbn__ = rsv090PlaceCommentDspKbn;
    }

    /**
     * <p>rsv090ItemDspKbn1 を取得します。
     * @return rsv090ItemDspKbn1
     */
    public String getRsv090ItemDspKbn1() {
        return rsv090ItemDspKbn1__;
    }

    /**
     * <p>rsv090ItemDspKbn1 をセットします。
     * @param rsv090ItemDspKbn1 rsv090ItemDspKbn1
     */
    public void setRsv090ItemDspKbn1(String rsv090ItemDspKbn1) {
        rsv090ItemDspKbn1__ = rsv090ItemDspKbn1;
    }

    /**
     * <p>rsv090ItemDspKbn2 を取得します。
     * @return rsv090ItemDspKbn2
     */
    public String getRsv090ItemDspKbn2() {
        return rsv090ItemDspKbn2__;
    }

    /**
     * <p>rsv090ItemDspKbn2 をセットします。
     * @param rsv090ItemDspKbn2 rsv090ItemDspKbn2
     */
    public void setRsv090ItemDspKbn2(String rsv090ItemDspKbn2) {
        rsv090ItemDspKbn2__ = rsv090ItemDspKbn2;
    }

    /**
     * <p>rsv090ItemDspKbn3 を取得します。
     * @return rsv090ItemDspKbn3
     */
    public String getRsv090ItemDspKbn3() {
        return rsv090ItemDspKbn3__;
    }

    /**
     * <p>rsv090ItemDspKbn3 をセットします。
     * @param rsv090ItemDspKbn3 rsv090ItemDspKbn3
     */
    public void setRsv090ItemDspKbn3(String rsv090ItemDspKbn3) {
        rsv090ItemDspKbn3__ = rsv090ItemDspKbn3;
    }

    /**
     * <p>rsv090ItemDspKbn4 を取得します。
     * @return rsv090ItemDspKbn4
     */
    public String getRsv090ItemDspKbn4() {
        return rsv090ItemDspKbn4__;
    }

    /**
     * <p>rsv090ItemDspKbn4 をセットします。
     * @param rsv090ItemDspKbn4 rsv090ItemDspKbn4
     */
    public void setRsv090ItemDspKbn4(String rsv090ItemDspKbn4) {
        rsv090ItemDspKbn4__ = rsv090ItemDspKbn4;
    }

    /**
     * <p>rsv090ItemDspKbn5 を取得します。
     * @return rsv090ItemDspKbn5
     */
    public String getRsv090ItemDspKbn5() {
        return rsv090ItemDspKbn5__;
    }

    /**
     * <p>rsv090ItemDspKbn5 をセットします。
     * @param rsv090ItemDspKbn5 rsv090ItemDspKbn5
     */
    public void setRsv090ItemDspKbn5(String rsv090ItemDspKbn5) {
        rsv090ItemDspKbn5__ = rsv090ItemDspKbn5;
    }

    /**
     * <p>rsv090ItemDspKbn6 を取得します。
     * @return rsv090ItemDspKbn6
     */
    public String getRsv090ItemDspKbn6() {
        return rsv090ItemDspKbn6__;
    }

    /**
     * <p>rsv090ItemDspKbn6 をセットします。
     * @param rsv090ItemDspKbn6 rsv090ItemDspKbn6
     */
    public void setRsv090ItemDspKbn6(String rsv090ItemDspKbn6) {
        rsv090ItemDspKbn6__ = rsv090ItemDspKbn6;
    }

    /**
     * <p>rsv090ItemDspKbn7 を取得します。
     * @return rsv090ItemDspKbn7
     */
    public String getRsv090ItemDspKbn7() {
        return rsv090ItemDspKbn7__;
    }

    /**
     * <p>rsv090ItemDspKbn7 をセットします。
     * @param rsv090ItemDspKbn7 rsv090ItemDspKbn7
     */
    public void setRsv090ItemDspKbn7(String rsv090ItemDspKbn7) {
        rsv090ItemDspKbn7__ = rsv090ItemDspKbn7;
    }

    /**
     * <p>rsv090StrSisetuIdDspKbn を取得します。
     * @return rsv090StrSisetuIdDspKbn
     */
    public String getRsv090StrSisetuIdDspKbn() {
        return rsv090StrSisetuIdDspKbn__;
    }

    /**
     * <p>rsv090StrSisetuIdDspKbn をセットします。
     * @param rsv090StrSisetuIdDspKbn rsv090StrSisetuIdDspKbn
     */
    public void setRsv090StrSisetuIdDspKbn(String rsv090StrSisetuIdDspKbn) {
        rsv090StrSisetuIdDspKbn__ = rsv090StrSisetuIdDspKbn;
    }

    /**
     * <p>rsv090StrPlaceDspKbn を取得します。
     * @return rsv090StrPlaceDspKbn
     */
    public String getRsv090StrPlaceDspKbn() {
        return rsv090StrPlaceDspKbn__;
    }

    /**
     * <p>rsv090StrPlaceDspKbn をセットします。
     * @param rsv090StrPlaceDspKbn rsv090StrPlaceDspKbn
     */
    public void setRsv090StrPlaceDspKbn(String rsv090StrPlaceDspKbn) {
        rsv090StrPlaceDspKbn__ = rsv090StrPlaceDspKbn;
    }

    /**
     * <p>rsv090StrSisankanriDspKbn を取得します。
     * @return rsv090StrSisankanriDspKbn
     */
    public String getRsv090StrSisankanriDspKbn() {
        return rsv090StrSisankanriDspKbn__;
    }

    /**
     * <p>rsv090StrSisankanriDspKbn をセットします。
     * @param rsv090StrSisankanriDspKbn rsv090StrSisankanriDspKbn
     */
    public void setRsv090StrSisankanriDspKbn(String rsv090StrSisankanriDspKbn) {
        rsv090StrSisankanriDspKbn__ = rsv090StrSisankanriDspKbn;
    }

    /**
     * <p>rsv090StrBikoDspKbn を取得します。
     * @return rsv090StrBikoDspKbn
     */
    public String getRsv090StrBikoDspKbn() {
        return rsv090StrBikoDspKbn__;
    }

    /**
     * <p>rsv090StrBikoDspKbn をセットします。
     * @param rsv090StrBikoDspKbn rsv090StrBikoDspKbn
     */
    public void setRsv090StrBikoDspKbn(String rsv090StrBikoDspKbn) {
        rsv090StrBikoDspKbn__ = rsv090StrBikoDspKbn;
    }

    /**
     * <p>rsv090StrDefoImgDspKbn を取得します。
     * @return rsv090StrDefoImgDspKbn
     */
    public String getRsv090StrDefoImgDspKbn() {
        return rsv090StrDefoImgDspKbn__;
    }

    /**
     * <p>rsv090StrDefoImgDspKbn をセットします。
     * @param rsv090StrDefoImgDspKbn rsv090StrDefoImgDspKbn
     */
    public void setRsv090StrDefoImgDspKbn(String rsv090StrDefoImgDspKbn) {
        rsv090StrDefoImgDspKbn__ = rsv090StrDefoImgDspKbn;
    }

    /**
     * @return rsv090apprKbn
     */
    public int getRsv090apprKbn() {
        return rsv090apprKbn__;
    }

    /**
     * @param rsv090apprKbn 設定する rsv090apprKbn
     */
    public void setRsv090apprKbn(int rsv090apprKbn) {
        rsv090apprKbn__ = rsv090apprKbn;
    }

    /**
     * @return rsv090apprKbnDspKbn
     */
    public String getRsv090apprKbnDspKbn() {
        return rsv090apprKbnDspKbn__;
    }

    /**
     * @param rsv090apprKbnDspKbn 設定する rsv090apprKbnDspKbn
     */
    public void setRsv090apprKbnDspKbn(String rsv090apprKbnDspKbn) {
        rsv090apprKbnDspKbn__ = rsv090apprKbnDspKbn;
    }

    /**
     * @return rsv090apprKbnInput
     */
    public boolean isRsv090apprKbnInput() {
        return rsv090apprKbnInput__;
    }

    /**
     * @param rsv090apprKbnInput 設定する rsv090apprKbnInput
     */
    public void setRsv090apprKbnInput(boolean rsv090apprKbnInput) {
        rsv090apprKbnInput__ = rsv090apprKbnInput;
    }

    /**
     * <p>rsv090sisGrpAdmFlg を取得します。
     * @return rsv090sisGrpAdmFlg
     */
    public boolean isRsv090sisGrpAdmFlg() {
        return rsv090sisGrpAdmFlg__;
    }

    /**
     * <p>rsv090sisGrpAdmFlg をセットします。
     * @param rsv090sisGrpAdmFlg rsv090sisGrpAdmFlg
     */
    public void setRsv090sisGrpAdmFlg(boolean rsv090sisGrpAdmFlg) {
        rsv090sisGrpAdmFlg__ = rsv090sisGrpAdmFlg;
    }

    /**
     * <p>rsv090sisGrpApprFlg を取得します。
     * @return rsv090sisGrpApprFlg
     */
    public boolean isRsv090sisGrpApprFlg() {
        return rsv090sisGrpApprFlg__;
    }

    /**
     * <p>rsv090sisGrpApprFlg をセットします。
     * @param rsv090sisGrpApprFlg rsv090sisGrpApprFlg
     */
    public void setRsv090sisGrpApprFlg(boolean rsv090sisGrpApprFlg) {
        rsv090sisGrpApprFlg__ = rsv090sisGrpApprFlg;
    }
}