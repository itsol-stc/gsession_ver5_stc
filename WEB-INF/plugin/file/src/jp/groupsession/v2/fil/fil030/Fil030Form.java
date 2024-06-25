package jp.groupsession.v2.fil.fil030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.fil020.Fil020Form;
import jp.groupsession.v2.fil.fil030.ui.Fil030PrivateUserGroupSelector;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.util.FilValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] キャビネット登録・編集画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil030Form extends Fil020Form implements ISelectorUseForm {

    /** プラグインID */
    private String fil030pluginId__ = GSConstFile.PLUGIN_ID_FILE;
    //表示用
    /** キャビネット名称 */
    private String fil030CabinetName__;

    /** アクセス制御　有無*/
    private String fil030AccessKbn__;
    /** アクセス候補　グループ*/
    private String fil030AcSltGroup__ = null;
    /** セーブ フルアクセスユーザ */
    private String[] fil030SvAcFull__ = null;
    /** セーブ 閲覧アクセスユーザ */
    private String[] fil030SvAcRead__ = null;
    /** アクセス制御　UI */
    private UserGroupSelector fil030AccessUserUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("cmn.access.auth", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("cmn.add.edit.delete", null))
                    .chainParameterName(
                            "fil030SvAcFull")
                    )
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("cmn.reading", null))
                    .chainParameterName(
                            "fil030SvAcRead")
                    )
            .chainGroupSelectionParamName("fil030AcSltGroup")
            .build();
    /** アクセス制御　UI(個人キャビネット) */
    private Fil030PrivateUserGroupSelector fil030PrivateAccessUserUI__ =
            Fil030PrivateUserGroupSelector.builder()
            .chainLabel(new GsMessageReq("fil.153", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("cmn.reading", null))
                    .chainParameterName(
                            "fil030SvAcRead")
                    )
            .chainGroupSelectionParamName("fil030AcSltGroup")
            .build();

    /** 選択したキャビネットSID */
    private String fil030SelectCabinet__;
    /** 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット */
    private int fil030PersonalFlg__ = Integer.parseInt(GSConstFile.DSP_CABINET_PUBLIC);
    /** 個人キャビネット所有ユーザ */
    private String ownerSid__;

    /** セーブ キャビネット管理者*/
    private String[] fil030SvAdm__ = null;
    /** キャビネット候補　グループ*/
    private String fil030AdmSltGroup__ = null;
    /** キャビネット管理者　UI */
    private UserGroupSelector fil030AdminUserUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("fil.14", null))
            .chainType(EnumSelectType.USER)
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("cmn.admin", null))
                    .chainParameterName(
                            "fil030SvAdm")
                    )
            .chainGroupSelectionParamName("fil030AdmSltGroup")
            .build();

    /** 容量制限区分*/
    private String fil030CapaKbn__ = null;
    /** 容量サイズ*/
    private String fil030CapaSize__ = null;
    /** 容量警告パーセント*/
    private String fil030CapaWarn__ = null;

    /** バージョン管理区分*/
    private String fil030VerKbn__ = null;
    /** バージョン管理 キャビネットで世代を統一*/
    private String fil030VerAllKbn__ = null;

    /** 備考*/
    private String fil030Biko__ = null;

    /** マーク添付ファイル(コンボで選択中) */
    private String[] fil030SelectTempFilesMark__ = null;
    /** マークファイルコンボ */
    private List<LabelValueBean> fil030FileLabelListMark__ = null;

    /** キャビネット使用率コンボ用リスト */
    private ArrayList< LabelValueBean > fil030CapaWarnLavel__ = null;
    /** キャビネット世代管理数コンボ用リスト */
    private ArrayList< LabelValueBean > fil030VerKbnLavel__ = null;

    /** キャビネット管理機能 キャビネット複数選択multibox */
    private String[] fil220sltCheck__ = null;
    /** キャビネット管理機能 キャビネット区分 */
    private int fil220cabinetKbn__ = GSConstFile.CABINET_KBN_PUBLIC;
    /** 個人キャビネット 検索キーワード */
    private String fil280svKeyword__ = null;
    /** 個人キャビネット 検索グループ */
    private int fil280svGroup__ = -1;
    /** 個人キャビネット 検索ユーザ */
    private int fil280svUser__ = -1;

    /** 削除用チェックボックス パラメータ保持用 */
    private String[] fil040SelectDel__;

    /** アイコンSID */
    private String fil030binSid__;
    /** アイコンファイル名 */
    private String fil030ImageName__;
    /** アイコンファイル保存名 */
    private String fil030ImageSaveName__;
    /** アイコン選択時の初期表示フラグ */
    private String fil030InitFlg__ = "0";

    /** 含まれるサブフォルダ・ファイルにも適応*/
    private String file030AdaptIncFile__ = "0";

    /** 電子帳簿保存法 自動振り分け機能 使用区分*/
    private int file030ErrlAutoKbn__ = -1;
    /** 電子帳簿保存法 自動振り分け機能 第1フォルダ*/
    private int file030ErrlAutoFolder1__ = -1;
    /** 電子帳簿保存法 自動振り分け機能 第2フォルダ*/
    private int file030ErrlAutoFolder2__ = -1;
    /** 電子帳簿保存法 自動振り分け機能 第3フォルダ*/
    private int file030ErrlAutoFolder3__ = -1;
    /** 電子帳簿保存法 自動振り分け機能 第1フォルダ コンボ */
    private ArrayList<LabelValueBean> file030ErrlAutoFolderList1__
            = new ArrayList<LabelValueBean>();
    /** 電子帳簿保存法 自動振り分け機能 第2フォルダ コンボ */
    private ArrayList<LabelValueBean> file030ErrlAutoFolderList2__
            = new ArrayList<LabelValueBean>();
    /** 電子帳簿保存法 自動振り分け機能 第3フォルダ コンボ */
    private ArrayList<LabelValueBean> file030ErrlAutoFolderList3__
            = new ArrayList<LabelValueBean>();

    /**
     * @return fil030pluginId
     */
    public String getFil030pluginId() {
        return fil030pluginId__;
    }

    /**
     * @param fil030pluginId 設定する fil030pluginId
     */
    public void setFil030pluginId(String fil030pluginId) {
        fil030pluginId__ = fil030pluginId;
    }

    /**
     * @return fil030AccessKbn
     */
    public String getFil030AccessKbn() {
        return fil030AccessKbn__;
    }

    /**
     * @param fil030AccessKbn 設定する fil030AccessKbn
     */
    public void setFil030AccessKbn(String fil030AccessKbn) {
        fil030AccessKbn__ = fil030AccessKbn;
    }

    /**
     * @return fil030AcSltGroup
     */
    public String getFil030AcSltGroup() {
        return fil030AcSltGroup__;
    }

    /**
     * @param fil030AcSltGroup 設定する fil030AcSltGroup
     */
    public void setFil030AcSltGroup(String fil030AcSltGroup) {
        fil030AcSltGroup__ = fil030AcSltGroup;
    }

    /**
     * @return fil030AdmSltGroup
     */
    public String getFil030AdmSltGroup() {
        return fil030AdmSltGroup__;
    }

    /**
     * @param fil030AdmSltGroup 設定する fil030AdmSltGroup
     */
    public void setFil030AdmSltGroup(String fil030AdmSltGroup) {
        fil030AdmSltGroup__ = fil030AdmSltGroup;
    }

    /**
     * @return fil030Biko
     */
    public String getFil030Biko() {
        return fil030Biko__;
    }

    /**
     * @param fil030Biko 設定する fil030Biko
     */
    public void setFil030Biko(String fil030Biko) {
        fil030Biko__ = fil030Biko;
    }

    /**
     * @return fil030CabinetName
     */
    public String getFil030CabinetName() {
        return fil030CabinetName__;
    }

    /**
     * @param fil030CabinetName 設定する fil030CabinetName
     */
    public void setFil030CabinetName(String fil030CabinetName) {
        fil030CabinetName__ = fil030CabinetName;
    }

    /**
     * @return fil030CapaKbn
     */
    public String getFil030CapaKbn() {
        return fil030CapaKbn__;
    }

    /**
     * @param fil030CapaKbn 設定する fil030CapaKbn
     */
    public void setFil030CapaKbn(String fil030CapaKbn) {
        fil030CapaKbn__ = fil030CapaKbn;
    }

    /**
     * @return fil030CapaSize
     */
    public String getFil030CapaSize() {
        return fil030CapaSize__;
    }

    /**
     * @param fil030CapaSize 設定する fil030CapaSize
     */
    public void setFil030CapaSize(String fil030CapaSize) {
        fil030CapaSize__ = fil030CapaSize;
    }

    /**
     * @return fil030CapaWarn
     */
    public String getFil030CapaWarn() {
        return fil030CapaWarn__;
    }

    /**
     * @param fil030CapaWarn 設定する fil030CapaWarn
     */
    public void setFil030CapaWarn(String fil030CapaWarn) {
        fil030CapaWarn__ = fil030CapaWarn;
    }

    /**
     * @return fil030CapaWarnLavel
     */
    public ArrayList<LabelValueBean> getFil030CapaWarnLavel() {
        return fil030CapaWarnLavel__;
    }

    /**
     * @param fil030CapaWarnLavel 設定する fil030CapaWarnLavel
     */
    public void setFil030CapaWarnLavel(ArrayList<LabelValueBean> fil030CapaWarnLavel) {
        fil030CapaWarnLavel__ = fil030CapaWarnLavel;
    }

    /**
     * @return fil030SvAcFull
     */
    public String[] getFil030SvAcFull() {
        return fil030SvAcFull__;
    }

    /**
     * @param fil030SvAcFull 設定する fil030SvAcFull
     */
    public void setFil030SvAcFull(String[] fil030SvAcFull) {
        fil030SvAcFull__ = fil030SvAcFull;
    }

    /**
     * @return fil030SvAcRead
     */
    public String[] getFil030SvAcRead() {
        return fil030SvAcRead__;
    }

    /**
     * @param fil030SvAcRead 設定する fil030SvAcRead
     */
    public void setFil030SvAcRead(String[] fil030SvAcRead) {
        fil030SvAcRead__ = fil030SvAcRead;
    }

    /**
     * @return fil030SvAdm
     */
    public String[] getFil030SvAdm() {
        return fil030SvAdm__;
    }

    /**
     * @param fil030SvAdm 設定する fil030SvAdm
     */
    public void setFil030SvAdm(String[] fil030SvAdm) {
        fil030SvAdm__ = fil030SvAdm;
    }

    /**
     * @return fil030VerAllKbn
     */
    public String getFil030VerAllKbn() {
        return fil030VerAllKbn__;
    }

    /**
     * @param fil030VerAllKbn 設定する fil030VerAllKbn
     */
    public void setFil030VerAllKbn(String fil030VerAllKbn) {
        fil030VerAllKbn__ = fil030VerAllKbn;
    }

    /**
     * @return fil030VerKbn
     */
    public String getFil030VerKbn() {
        return fil030VerKbn__;
    }

    /**
     * @param fil030VerKbn 設定する fil030VerKbn
     */
    public void setFil030VerKbn(String fil030VerKbn) {
        fil030VerKbn__ = fil030VerKbn;
    }

    /**
     * @return fil030VerKbnLavel
     */
    public ArrayList<LabelValueBean> getFil030VerKbnLavel() {
        return fil030VerKbnLavel__;
    }

    /**
     * @param fil030VerKbnLavel 設定する fil030VerKbnLavel
     */
    public void setFil030VerKbnLavel(ArrayList<LabelValueBean> fil030VerKbnLavel) {
        fil030VerKbnLavel__ = fil030VerKbnLavel;
    }

    /**
     * <p>fil030AccessUserUI を取得します。
     * @return fil030AccessUserUI
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#fil030AccessUserUI__
     */
    public UserGroupSelector getFil030AccessUserUI() {
        return fil030AccessUserUI__;
    }

    /**
     * <p>fil030AccessUserUI をセットします。
     * @param fil030AccessUserUI fil030AccessUserUI
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#fil030AccessUserUI__
     */
    public void setFil030AccessUserUI(UserGroupSelector fil030AccessUserUI) {
        fil030AccessUserUI__ = fil030AccessUserUI;
    }

    /**
     * <p>fil030PrivateAccessUserUI を取得します。
     * @return fil030PrivateAccessUserUI
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#fil030PrivateAccessUserUI__
     */
    public Fil030PrivateUserGroupSelector getFil030PrivateAccessUserUI() {
        return fil030PrivateAccessUserUI__;
    }

    /**
     * <p>fil030PrivateAccessUserUI をセットします。
     * @param fil030PrivateAccessUserUI fil030PrivateAccessUserUI
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#fil030PrivateAccessUserUI__
     */
    public void setFil030PrivateAccessUserUI(
            Fil030PrivateUserGroupSelector fil030PrivateAccessUserUI) {
        fil030PrivateAccessUserUI__ = fil030PrivateAccessUserUI;
    }

    /**
     * <p>fil030AdminUserUI を取得します。
     * @return fil030AdminUserUI
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#fil030AdminUserUI__
     */
    public UserGroupSelector getFil030AdminUserUI() {
        return fil030AdminUserUI__;
    }

    /**
     * <p>fil030AdminUserUI をセットします。
     * @param fil030AdminUserUI fil030AdminUserUI
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#fil030AdminUserUI__
     */
    public void setFil030AdminUserUI(UserGroupSelector fil030AdminUserUI) {
        fil030AdminUserUI__ = fil030AdminUserUI;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @param usModel ログインユーザモデル
     * @param tempDir テンポラリディレクトリパス
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 例外処理
     */
    public ActionErrors validateCheck(
            ActionMapping map,
            HttpServletRequest req,
            Connection con,
            BaseUserModel usModel,
            String tempDir) throws SQLException, IOToolsException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();

        if (fil030PersonalFlg__ == GSConstFile.CABINET_KBN_PRIVATE) {
            //アクセス権限 制限する場合は必須
            int accsessKbn = NullDefault.getInt(fil030AccessKbn__, -1);
            if (accsessKbn == GSConstFile.ACCESS_KBN_ON) {
                //閲覧アクセスユーザを選択チェック
                if ((fil030SvAcRead__ == null || fil030SvAcRead__.length < 1)) {
                    String textCabinetAcFull = gsMsg.getMessage(req, "fil.102");
                    msg = new ActionMessage(
                                    "error.select.required.text", textCabinetAcFull);
                    StrutsUtil.addMessage(errors, msg, "fil030SvAcFull");
                } else if (!getCmnMode().equals(GSConstFile.CMN_MODE_MLT)) {

                    // 編集権限ユーザと閲覧権限ユーザの重複チェック
                    int ownerSid = -1;
                    if (getCmnMode().equals(GSConstFile.CMN_MODE_ADD)) {
                        // 登録モード → キャビネット所有ユーザは常にログインユーザSIDでチェック
                        ownerSid = usModel.getUsrsid();
                    } else if (getCmnMode().equals(GSConstFile.CMN_MODE_EDT)) {
                        // 編集モード → アクセス権限にキャビネット所有ユーザSIDがあるかチェック
                        int cabSid = NullDefault.getInt(fil030SelectCabinet__, -1);
                        FileCabinetDao cabDao = new FileCabinetDao(con);
                        FileCabinetModel cabModel = cabDao.select(cabSid);
                        if (cabModel != null) {
                            ownerSid = cabModel.getFcbOwnerSid();
                        }
                    }
                    ArrayList<String> readList = new ArrayList<String>(
                            Arrays.asList(fil030SvAcRead__));
                    if (readList.contains(String.valueOf(ownerSid))) {
                        String textCabinetAcFull = gsMsg.getMessage(req, "fil.146");
                        String textCabinetAcRead = gsMsg.getMessage(req, "cmn.reading")
                                                 + gsMsg.getMessage(req, "cmn.user");
                        msg = new ActionMessage("error.select.dup.list2",
                                                textCabinetAcRead, textCabinetAcFull);
                        StrutsUtil.addMessage(errors, msg, "fil030SvAcFullRead");
                    }
                }
            } else if (accsessKbn == GSConstFile.ACCESS_KBN_OFF) {
                // アクセス制限なし
            } else {
                // アクセス制限の値が不正
                String textCabinetAcFull = gsMsg.getMessage(req, "fil.102");
                msg = new ActionMessage(
                                "error.input.format.text", textCabinetAcFull);
                StrutsUtil.addMessage(errors, msg, "fil030AccessKbn");
            }
        } else {
            FileAconfDao aconfDao = new FileAconfDao(con);
            FileAconfModel aconf = aconfDao.select();
            if (aconf == null) {
                aconf = new FileAconfModel();
                aconf.init();
            }

            //キャビネット名称
            if (getCmnMode().equals(GSConstFile.CMN_MODE_MLT) == false) {
                String textCabinetName = gsMsg.getMessage(req, "fil.13");
                FilValidateUtil.validateTextField(errors, fil030CabinetName__, "fil030CabinetName",
                        textCabinetName,
                        GSConstFile.MAX_LENGTH_NAME, true);
            }

            //アクセス権限 制限する場合は必須
            int accsessKbn = NullDefault.getInt(fil030AccessKbn__, -1);
            if (accsessKbn == GSConstFile.ACCESS_KBN_ON) {
                //フルアクセスユーザを選択チェック
                if ((fil030SvAcFull__ == null || fil030SvAcFull__.length < 1)
                 && (fil030SvAcRead__ == null || fil030SvAcRead__.length < 1)) {
                    String textCabinetAcFull = gsMsg.getMessage(req, "fil.102");
                    msg = new ActionMessage(
                                    "error.select.required.text", textCabinetAcFull);
                    StrutsUtil.addMessage(errors, msg, "fil030SvAcFull");
                } else if (fil030SvAcFull__ != null && fil030SvAcFull__.length > 0
                        && fil030SvAcRead__ != null && fil030SvAcRead__.length > 0) {
                    // 編集権限と閲覧権限において、重複があるかチェック
                    ArrayList<String> readList = new ArrayList<String>(
                                                        Arrays.asList(fil030SvAcRead__));
                    for (int i = 0; i < fil030SvAcFull__.length; i++) {
                        if (readList.contains(fil030SvAcFull__[i])) {
                            // 重複エラー
                            String textCabinetAcFull = gsMsg.getMessage(req, "cmn.add.edit.delete")
                                                     + gsMsg.getMessage(req, "cmn.user");
                            String textCabinetAcRead = gsMsg.getMessage(req, "cmn.reading")
                                                     + gsMsg.getMessage(req, "cmn.user");
                            msg = new ActionMessage("error.select.dup.list2",
                                                    textCabinetAcRead, textCabinetAcFull);
                            StrutsUtil.addMessage(errors, msg, "fil030SvAcFullRead");
                            break;
                        }
                    }
                }
            } else if (accsessKbn == GSConstFile.ACCESS_KBN_OFF) {
                // アクセス制限なし
            } else {
                // アクセス制限の値が不正
                String textCabinetAcFull = gsMsg.getMessage(req, "fil.102");
                msg = new ActionMessage(
                                "error.input.format.tex", textCabinetAcFull);
                StrutsUtil.addMessage(errors, msg, "fil030AccessKbn");
            }

            //容量設定
            int capaKbn = NullDefault.getInt(fil030CapaKbn__, -1);
            if (capaKbn == GSConstFile.CAPA_KBN_ON) {
                String textCabinetCapaSize = gsMsg.getMessage(req, "fil.4");
                //キャビネット容量上限
                FilValidateUtil.validateTextFieldOfNumber(errors, fil030CapaSize__,
                        "fil030CapaSize",
                        textCabinetCapaSize, GSConstFile.MAX_LENGTH_CAPA, true);

                // キャビネット警告値の不正値チェック
                int warnKbn = NullDefault.getInt(fil030CapaWarn__, -1);
                if (warnKbn < 0 || warnKbn > 100) {
                    String textCabinetAcFull = gsMsg.getMessage(req, "fil.fil030kn.1");
                    msg = new ActionMessage(
                                    "error.input.format.text", textCabinetAcFull);
                    StrutsUtil.addMessage(errors, msg, "fil030CapaWarn");
                }
            } else if (capaKbn == GSConstFile.CAPA_KBN_OFF) {
                // キャビネット容量制限しない
            } else {
                // キャビネット容量制限の値が不正
                String textCabinetAcFull = gsMsg.getMessage(req, "fil.3");
                msg = new ActionMessage(
                                "error.input.format.text", textCabinetAcFull);
                StrutsUtil.addMessage(errors, msg, "fil030CapaKbn");
            }

            // バージョン管理
            if (aconf.getFacVerKbn() == GSConstFile.VERSION_KBN_ON) {
                // バージョン管理数
                int verLvKbn = NullDefault.getInt(fil030VerKbn__, -1);
                if (verLvKbn < 0 && verLvKbn > GSConstFile.MAX_LEVEL) {
                    String textCabinetAcFull = gsMsg.getMessage(req, "fil.68");
                    msg = new ActionMessage(
                                    "error.input.format.text", textCabinetAcFull);
                    StrutsUtil.addMessage(errors, msg, "fil030VerKbn__");
                }
            }

            if (getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)) {
                // 自動振り分け機能
                if (file030ErrlAutoKbn__ != GSConstFile.SORT_FOLDER_NOT_USE
                        && file030ErrlAutoKbn__ != GSConstFile.SORT_FOLDER_USE) {
                    String target = gsMsg.getMessage(req, "fil.19");
                    msg = new ActionMessage(
                                    "error.select.forum.radio", target);
                    StrutsUtil.addMessage(errors, msg, "file030ErrlAutoKbn__");
                }
                if (file030ErrlAutoKbn__ == GSConstFile.SORT_FOLDER_USE) {
                    // 自動振り分けフォルダ1
                    __checkErrlAutoFolder(errors, req,
                                        file030ErrlAutoFolder1__,
                                        gsMsg.getMessage(req, "fil.fil030.10"),
                                        1);
                    // 自動振り分けフォルダ2
                    __checkErrlAutoFolder(errors, req,
                                        file030ErrlAutoFolder2__,
                                        gsMsg.getMessage(req, "fil.fil030.11"),
                                        2);
                    if (file030ErrlAutoFolder1__ == file030ErrlAutoFolder2__) {
                        String target1 = gsMsg.getMessage("fil.190") + " " + gsMsg.getMessage(req, "fil.fil030.10");
                        String target2 = gsMsg.getMessage("fil.190") + " " + gsMsg.getMessage(req, "fil.fil030.11");
                        msg = new ActionMessage(
                                        "error.same.sort.folder", target1, target2);
                        StrutsUtil.addMessage(errors, msg, "file030ErrlAutoFolder2");
                    }
                    // 自動振り分けフォルダ3
                    // (自動振り分けフォルダ2 != なし の場合、チェックする)
                    if (file030ErrlAutoFolder2__ != GSConstFile.SORT_FOLDER_NONE) {
                        __checkErrlAutoFolder(errors, req,
                                            file030ErrlAutoFolder3__,
                                            gsMsg.getMessage(req, "fil.fil030.24"),
                                            3);
                        if (file030ErrlAutoFolder1__ == file030ErrlAutoFolder3__) {
                            String target1 = gsMsg.getMessage("fil.190") + " " + gsMsg.getMessage(req, "fil.fil030.10");
                            String target2 = gsMsg.getMessage("fil.190") + " " + gsMsg.getMessage(req, "fil.fil030.24");
                            msg = new ActionMessage(
                                            "error.same.sort.folder", target1, target2);
                            StrutsUtil.addMessage(errors, msg, "file030ErrlAutoFolder3");

                        } else if(file030ErrlAutoFolder2__ == file030ErrlAutoFolder3__) {
                            String target1 = gsMsg.getMessage("fil.190") + " " + gsMsg.getMessage(req, "fil.fil030.11");
                            String target2 = gsMsg.getMessage("fil.190") + " " + gsMsg.getMessage(req, "fil.fil030.24");
                            msg = new ActionMessage(
                                            "error.same.sort.folder", target1, target2);
                            StrutsUtil.addMessage(errors, msg, "file030ErrlAutoFolder3");
                        }
                    }
                }
            }
        }

        //備考
        String textBiko = gsMsg.getMessage(req, "cmn.memo");
        FilValidateUtil.validateTextAreaField(errors, fil030Biko__, "fil030Biko__",
                textBiko, GSConstFile.MAX_LENGTH_BIKO, false);

        //アイコン画像
        CommonBiz commonBiz = new CommonBiz();
        if (!commonBiz.checkTempPhotoFile(tempDir)) {
            //BMP,JPG,JPEG,GIF,PNG以外のファイルならばエラー
            msg = new ActionMessage("error.select2.required.extent");
            StrutsUtil.addMessage(errors, msg, "fil030binSid");
        }


        return errors;
    }

    /**
     * 振り分けフォルダの不正な値チェックを行う
     * @param errors ActionErrors
     * @param req リクエスト
     * @param autoFolder 振り分けフォルダ 階層
     * @param existNoneKbn true: 選択値に「なし」を含む
     * @param targetName 項目名
     * @param level フォルダ階層
     */
    private void __checkErrlAutoFolder(ActionErrors errors, HttpServletRequest req,
                                        int autoFolder, String targetName,
                                        int level) {

        if (autoFolder != GSConstFile.SORT_FOLDER_UPLOAD_DATE
        && autoFolder != GSConstFile.SORT_FOLDER_TRADE_DATE
        && autoFolder != GSConstFile.SORT_FOLDER_TRADE_TARGET
        && autoFolder != GSConstFile.SORT_FOLDER_TRADE_DEFGROUP) {

            //第2階層以降は「なし」を許可する
            if (level > 1 && autoFolder == GSConstFile.SORT_FOLDER_NONE) {
                return;
            }

            GsMessage gsMsg = new GsMessage(req);
            String target = gsMsg.getMessage("fil.190") + " " + targetName;
            ActionMessage msg = new ActionMessage(
                            "error.select3.required.text", target);
            StrutsUtil.addMessage(errors, msg,
                                "file030ErrlAutoFolder" + level);
        }
    }

    /**
     * <p>fil220sltCheck を取得します。
     * @return fil220sltCheck
     */
    public String[] getFil220sltCheck() {
        return fil220sltCheck__;
    }

    /**
     * <p>fil220sltCheck をセットします。
     * @param fil220sltCheck fil220sltCheck
     */
    public void setFil220sltCheck(String[] fil220sltCheck) {
        fil220sltCheck__ = fil220sltCheck;
    }

    /**
     * <p>fil220cabinetKbn を取得します。
     * @return fil220cabinetKbn
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#fil220cabinetKbn__
     */
    public int getFil220cabinetKbn() {
        return fil220cabinetKbn__;
    }

    /**
     * <p>fil220cabinetKbn をセットします。
     * @param fil220cabinetKbn fil220cabinetKbn
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#fil220cabinetKbn__
     */
    public void setFil220cabinetKbn(int fil220cabinetKbn) {
        fil220cabinetKbn__ = fil220cabinetKbn;
    }

    /**
     * <p>fil280svKeyword を取得します。
     * @return fil280svKeyword
     */
    public String getFil280svKeyword() {
        return fil280svKeyword__;
    }

    /**
     * <p>fil280svKeyword をセットします。
     * @param fil280svKeyword fil280svKeyword
     */
    public void setFil280svKeyword(String fil280svKeyword) {
        fil280svKeyword__ = fil280svKeyword;
    }

    /**
     * <p>fil280svgroup を取得します。
     * @return fil280svGroup
     */
    public int getFil280svGroup() {
        return fil280svGroup__;
    }

    /**
     * <p>fil280svGroup をセットします。
     * @param fil280svGroup fil280svGroup
     */
    public void setFil280svGroup(int fil280svGroup) {
        fil280svGroup__ = fil280svGroup;
    }

    /**
     * <p>fil280svUser を取得します。
     * @return fil280svUser
     */
    public int getFil280svUser() {
        return fil280svUser__;
    }

    /**
     * <p>fil280svUser をセットします。
     * @param fil280svUser fil280svUser
     */
    public void setFil280svUser(int fil280svUser) {
        fil280svUser__ = fil280svUser;
    }

    /**
     * <p>fil030SelectCabinet を取得します。
     * @return fil030SelectCabinet
     */
    public String getFil030SelectCabinet() {
        return fil030SelectCabinet__;
    }

    /**
     * <p>fil030SelectCabinet をセットします。
     * @param fil030SelectCabinet fil030SelectCabinet
     */
    public void setFil030SelectCabinet(String fil030SelectCabinet) {
        fil030SelectCabinet__ = fil030SelectCabinet;
    }

    /**
     * <p> fil030PersonalFlgを取得します。
     * @return fil030PersonalFlg
     * */
    public int getFil030PersonalFlg() {
        return fil030PersonalFlg__;
    }
    /**
     * <p> fil030PersonalFlgをセットします。
     * @param fil030PersonalFlg fil030PersonalFlg
     * */
    public void setFil030PersonalFlg(int fil030PersonalFlg) {
        fil030PersonalFlg__ = fil030PersonalFlg;
    }

    /**
     * <p> ownerSidを取得します。
     * @return ownerSid
     * */
    public String getOwnerSid() {
        return ownerSid__;
    }
    /**
     * <p> ownerSidをセットします。
     * @param ownerSid ownerSid
     * */
    public void setOwnerSid(String ownerSid) {
        ownerSid__ = ownerSid;
    }

    /**
     * <p>fil040SelectDel を取得します。
     * @return fil040SelectDel
     */
    public String[] getFil040SelectDel() {
        return fil040SelectDel__;
    }

    /**
     * <p>fil040SelectDel をセットします。
     * @param fil040SelectDel fil040SelectDel
     */
    public void setFil040SelectDel(String[] fil040SelectDel) {
        fil040SelectDel__ = fil040SelectDel;
    }

    /**
     * <p>fil030SelectTempFilesMark を取得します。
     * @return fil030SelectTempFilesMark
     */
    public String[] getFil030SelectTempFilesMark() {
        return fil030SelectTempFilesMark__;
    }

    /**
     * <p>fil030SelectTempFilesMark をセットします。
     * @param fil030SelectTempFilesMark fil030SelectTempFilesMark
     */
    public void setFil030SelectTempFilesMark(String[] fil030SelectTempFilesMark) {
        fil030SelectTempFilesMark__ = fil030SelectTempFilesMark;
    }

    /**
     * <p>fil030FileLabelListMark を取得します。
     * @return fil030FileLabelListMark
     */
    public List<LabelValueBean> getFil030FileLabelListMark() {
        return fil030FileLabelListMark__;
    }

    /**
     * <p>fil030FileLabelListMark をセットします。
     * @param fil030FileLabelListMark fil030FileLabelListMark
     */
    public void setFil030FileLabelListMark(
            List<LabelValueBean> fil030FileLabelListMark) {
        fil030FileLabelListMark__ = fil030FileLabelListMark;
    }

    /**
     * <p>fil030binSid を取得します。
     * @return fil030binSid
     */
    public String getFil030binSid() {
        return fil030binSid__;
    }

    /**
     * <p>fil030binSid をセットします。
     * @param fil030binSid fil030binSid
     */
    public void setFil030binSid(String fil030binSid) {
        fil030binSid__ = fil030binSid;
    }

    /**
     * <p>fil030ImageName を取得します。
     * @return fil030ImageName
     */
    public String getFil030ImageName() {
        return fil030ImageName__;
    }

    /**
     * <p>fil030ImageName をセットします。
     * @param fil030ImageName fil030ImageName
     */
    public void setFil030ImageName(String fil030ImageName) {
        fil030ImageName__ = fil030ImageName;
    }

    /**
     * <p>fil030ImageSaveName を取得します。
     * @return fil030ImageSaveName
     */
    public String getFil030ImageSaveName() {
        return fil030ImageSaveName__;
    }

    /**
     * <p>fil030ImageSaveName をセットします。
     * @param fil030ImageSaveName fil030ImageSaveName
     */
    public void setFil030ImageSaveName(String fil030ImageSaveName) {
        fil030ImageSaveName__ = fil030ImageSaveName;
    }

    /**
     * <p>fil030InitFlg を取得します。
     * @return fil030InitFlg
     */
    public String getFil030InitFlg() {
        return fil030InitFlg__;
    }

    /**
     * <p>fil030InitFlg をセットします。
     * @param fil030InitFlg fil030InitFlg
     */
    public void setFil030InitFlg(String fil030InitFlg) {
        fil030InitFlg__ = fil030InitFlg;
    }

    /**
     * <p>file030AdaptIncFile を取得します。
     * @return file030AdaptIncFile
     */
    public String getFile030AdaptIncFile() {
        return file030AdaptIncFile__;
    }

    /**
     * <p>file030AdaptIncFile をセットします。
     * @param file030AdaptIncFile file030AdaptIncFile
     */
    public void setFile030AdaptIncFile(String file030AdaptIncFile) {
        this.file030AdaptIncFile__ = file030AdaptIncFile;
    }

    /**
     * <p>file030ErrlAutoKbn を取得します。
     * @return file030ErrlAutoKbn
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoKbn__
     */
    public int getFile030ErrlAutoKbn() {
        return file030ErrlAutoKbn__;
    }

    /**
     * <p>file030ErrlAutoKbn をセットします。
     * @param file030ErrlAutoKbn file030ErrlAutoKbn
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoKbn__
     */
    public void setFile030ErrlAutoKbn(int file030ErrlAutoKbn) {
        file030ErrlAutoKbn__ = file030ErrlAutoKbn;
    }

    /**
     * <p>file030ErrlAutoFolder1 を取得します。
     * @return file030ErrlAutoFolder1
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolder1__
     */
    public int getFile030ErrlAutoFolder1() {
        return file030ErrlAutoFolder1__;
    }

    /**
     * <p>file030ErrlAutoFolder1 をセットします。
     * @param file030ErrlAutoFolder1 file030ErrlAutoFolder1
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolder1__
     */
    public void setFile030ErrlAutoFolder1(int file030ErrlAutoFolder1) {
        file030ErrlAutoFolder1__ = file030ErrlAutoFolder1;
    }

    /**
     * <p>file030ErrlAutoFolder2 を取得します。
     * @return file030ErrlAutoFolder2
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolder2__
     */
    public int getFile030ErrlAutoFolder2() {
        return file030ErrlAutoFolder2__;
    }

    /**
     * <p>file030ErrlAutoFolder2 をセットします。
     * @param file030ErrlAutoFolder2 file030ErrlAutoFolder2
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolder2__
     */
    public void setFile030ErrlAutoFolder2(int file030ErrlAutoFolder2) {
        file030ErrlAutoFolder2__ = file030ErrlAutoFolder2;
    }

    /**
     * <p>file030ErrlAutoFolder3 を取得します。
     * @return file030ErrlAutoFolder3
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolder3__
     */
    public int getFile030ErrlAutoFolder3() {
        return file030ErrlAutoFolder3__;
    }

    /**
     * <p>file030ErrlAutoFolder3 をセットします。
     * @param file030ErrlAutoFolder3 file030ErrlAutoFolder3
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolder3__
     */
    public void setFile030ErrlAutoFolder3(int file030ErrlAutoFolder3) {
        file030ErrlAutoFolder3__ = file030ErrlAutoFolder3;
    }

    /**
     * <p>file030ErrlAutoFolderList1 を取得します。
     * @return file030ErrlAutoFolderList1
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolderList1__
     */
    public ArrayList<LabelValueBean> getFile030ErrlAutoFolderList1() {
        return file030ErrlAutoFolderList1__;
    }

    /**
     * <p>file030ErrlAutoFolderList1 をセットします。
     * @param file030ErrlAutoFolderList1 file030ErrlAutoFolderList1
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolderList1__
     */
    public void setFile030ErrlAutoFolderList1(
            ArrayList<LabelValueBean> file030ErrlAutoFolderList1) {
        file030ErrlAutoFolderList1__ = file030ErrlAutoFolderList1;
    }

    /**
     * <p>file030ErrlAutoFolderList2 を取得します。
     * @return file030ErrlAutoFolderList2
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolderList2__
     */
    public ArrayList<LabelValueBean> getFile030ErrlAutoFolderList2() {
        return file030ErrlAutoFolderList2__;
    }

    /**
     * <p>file030ErrlAutoFolderList2 をセットします。
     * @param file030ErrlAutoFolderList2 file030ErrlAutoFolderList2
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolderList2__
     */
    public void setFile030ErrlAutoFolderList2(
            ArrayList<LabelValueBean> file030ErrlAutoFolderList2) {
        file030ErrlAutoFolderList2__ = file030ErrlAutoFolderList2;
    }

    /**
     * <p>file030ErrlAutoFolderList3 を取得します。
     * @return file030ErrlAutoFolderList3
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolderList3__
     */
    public ArrayList<LabelValueBean> getFile030ErrlAutoFolderList3() {
        return file030ErrlAutoFolderList3__;
    }

    /**
     * <p>file030ErrlAutoFolderList3 をセットします。
     * @param file030ErrlAutoFolderList3 file030ErrlAutoFolderList3
     * @see jp.groupsession.v2.fil.fil030.Fil030Form#file030ErrlAutoFolderList3__
     */
    public void setFile030ErrlAutoFolderList3(
            ArrayList<LabelValueBean> file030ErrlAutoFolderList3) {
        file030ErrlAutoFolderList3__ = file030ErrlAutoFolderList3;
    }
}
