package jp.groupsession.v2.anp.anp150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.anp070.Anp070Form;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 安否確認 管理者設定 緊急連絡先一括設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp150Form extends Anp070Form implements ISelectorUseForm {


    /** 対象 全ユーザ */
    public static final int TAISYO_ALL = 0;
    /** 対象 ユーザ指定 */
    public static final int TAISYO_SELECT = 1;
    /** アドレス未登録ユーザパス区分 0:エラーとする 1:登録しない */
    private int anp150PassKbn__ = 0;

    /** 対象 */
    private int anp150TargetKbn__;

    /** グループコンボボックス選択SID */
    private String anp150SelectGroupSid__ = null;

    /** 設定対象ユーザ・グループSIDリスト */
    private String[] anp150TargetList__ = null;

    /** 設定対象ユーザ・グループ UI */
    private UserGroupSelector anp150TargetListUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.target", null))
                .chainType(EnumSelectType.USERGROUP)
                .chainSelect(Select.builder()
                        .chainParameterName(
                                "anp150TargetList")
                        )
                .chainGroupSelectionParamName("anp150SelectGroupSid")
                .chainGrpType(EnumGroupSelectType.WITHMYGROUP)
                .build();

    /** アドレスコンボボックス選択アドレス */
    private String anp150SelectMail__;
    /** アドレスリスト*/
    private List <LabelValueBean> anp150MailLabel__ = null;
    /** その他のアドレス */
    private String anp150OtherMail__;

    /** 上書きフラグ */
    private int anp150UpdateFlg__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
            ActionMapping map,
            RequestModel reqMdl,
            Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);

        //対象
        if (anp150TargetKbn__ == TAISYO_SELECT) {
            if (anp150TargetList__ == null || anp150TargetList__.length < 1) {
                // 未選択チェック
                msg = new ActionMessage(
                        "error.select.required.text", gsMsg.getMessage("cmn.target"));
                StrutsUtil.addMessage(errors, msg, "sml180userSid"
                        + "error.select.required.text");
            }
        }

        //登録メールアドレス その他メール転送先アドレス
        if (anp150SelectMail__.equals("0")) {
            String otherMail = anp150OtherMail__;
            if (StringUtil.isNullZeroString(otherMail)) {
                // 未入力チェック
                msg = new ActionMessage(
                        "error.input.required.text", gsMsg.getMessage("anp.anp150.07"));
                StrutsUtil.addMessage(errors, msg, "sml180Zmail3"
                        + "error.input.required.text");
            } else {
                errors = validateMail(
                        con,
                        errors,
                        otherMail,
                        "sml180Zmail3",
                        gsMsg.getMessage("anp.anp150.07"));
            }
        }

        //有効データ件数チェック
        if ((anp150PassKbn__ == 1) && (!anp150SelectMail__.equals("0"))) {
            //有効データ件数チェック
            __validateCheckUsrCount(con, errors, reqMdl);
        }

        if (anp150PassKbn__ != 1) {
            //各ユーザのメールアドレス登録チェック
            errors = __validateCheckUsrAddress(con, errors, reqMdl);

        }

        return errors;

    }

    /**
     * <p>メールアドレスの入力チェックを行う
     * @param con コネクション
     * @param errors ActionErrors
     * @param mail メールアドレス
     * @param eprefix メッセージサフィックス
     * @param text 項目名
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateMail(Connection con, ActionErrors errors,
            String mail, String eprefix, String text)
    throws SQLException {
        ActionMessage msg = null;

        if (!StringUtil.isNullZeroString(mail)) {
            if (mail.length() > GSConstUser.MAX_LENGTH_MAIL) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        text,
                        GSConstUser.MAX_LENGTH_MAIL);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");

            } else if (!GSValidateUtil.isMailFormat(mail)) {
                //メールフォーマットチェック
                msg = new ActionMessage("error.input.format.text", text);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.format.text");

            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 有効データ件数のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param errors アクションエラー
     * @param reqMdl リクエストモデル
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __validateCheckUsrCount(
            Connection con,
            ActionErrors errors,
            RequestModel reqMdl) throws SQLException {

        ActionMessage msg = null;
        //チェックするユーザ一覧
        ArrayList<Integer> usrSids = new ArrayList<Integer>();

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        ArrayList<Integer> svGrpSidList = new ArrayList<Integer>();
        ArrayList<Integer> svUsrSidList = new ArrayList<Integer>();
        if (anp150TargetKbn__ == TAISYO_SELECT) {

            if (anp150TargetList__ == null || anp150TargetList__.length < 1) {
                return errors;
            }

            //ユーザSIDとグループSIDを分離する
            for (String sid : anp150TargetList__) {
                sid = NullDefault.getString(sid, "-1");
                if (sid.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    svGrpSidList.add(Integer.valueOf(sid.substring(1, sid.length())));
                } else {
                    //ユーザ
                    svUsrSidList.add(Integer.valueOf(sid));
                }
            }

            if (svGrpSidList != null) {
                //グループに所属しているユーザの情報を取得する
                for (Integer grpSid : svGrpSidList) {
                    List<CmnUsrmInfModel> belongList =
                           anpiBiz.getBelongUserList(con, Integer.valueOf(grpSid), null, -1, false);
                    //ユーザ情報からSIDのリストを生成
                    for (CmnUsrmInfModel usrMdl : belongList) {
                        usrSids.add(usrMdl.getUsrSid());
                    }
                }
            }

            //グループSIDから読み込んだユーザに選択していたユーザSIDを追加する
            if (svUsrSidList != null) {
                usrSids.addAll(svUsrSidList);
            }
        }

        //対象ユーザ情報を取得する
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        ArrayList<CmnUsrmInfModel> usrmInfList = null;
        usrmInfList = usrmInfDao.getUserList(usrSids);
        if (usrmInfList == null || usrmInfList.size() < 1) {
            return errors;
        }

        int count = 0;
        boolean okFlg = true;
        for (CmnUsrmInfModel model : usrmInfList) {

            if (model.getUsrSid() < 100) {
                continue;
            }
            okFlg = true;
            if (anp150SelectMail__.equals("1")) {
                if (StringUtil.isNullZeroString(model.getUsiMail1())) {
                    okFlg = false;
                }
            } else if (anp150SelectMail__.equals("2")) {
                if (StringUtil.isNullZeroString(model.getUsiMail2())) {
                    okFlg = false;
                }
            } else if (anp150SelectMail__.equals("3")) {
                if (StringUtil.isNullZeroString(model.getUsiMail3())) {
                    okFlg = false;
                }
            }
            if (okFlg) {
                count++;
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);

        if (count < 1) {
            //対象ユーザなし
            msg = new ActionMessage("search.data.notfound", gsMsg.getMessage("cmn.target"));
            StrutsUtil.addMessage(errors, msg, "search.data.notfound");
        }

        return errors;
    }

    /**
     * <br>[機  能] 各ユーザのメールアドレスチェックを行う
     * <br>[解  説]
     * <br>[備  考]

     * @param con コネクション
     * @param errors アクションエラー
     * @param reqMdl リクエストモデル
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __validateCheckUsrAddress(
            Connection con,
            ActionErrors errors,
            RequestModel reqMdl) throws SQLException {

        ActionMessage msg = null;
        //チェックするユーザ一覧
        ArrayList<Integer> usrSids = new ArrayList<Integer>();

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        ArrayList<Integer> svGrpSidList = new ArrayList<Integer>();
        ArrayList<Integer> svUsrSidList = new ArrayList<Integer>();
        if (anp150TargetKbn__ == TAISYO_SELECT) {

            if (anp150TargetList__ == null || anp150TargetList__.length < 1) {
                return errors;
            }

            //ユーザSIDとグループSIDを分離する
            for (String sid : anp150TargetList__) {
                sid = NullDefault.getString(sid, "-1");
                if (sid.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    svGrpSidList.add(Integer.valueOf(sid.substring(1, sid.length())));
                } else {
                    //ユーザ
                    svUsrSidList.add(Integer.valueOf(sid));
                }
            }

            //SIDは重複不可
            HashSet<Integer> hashUsrSids = new HashSet<Integer>();
            if (svGrpSidList != null) {
                //グループに所属しているユーザの情報を取得する
                for (Integer grpSid : svGrpSidList) {
                    List<CmnUsrmInfModel> belongList =
                           anpiBiz.getBelongUserList(con, Integer.valueOf(grpSid), null, -1, false);
                    //ユーザ情報からSIDのリストを生成
                    for (CmnUsrmInfModel usrMdl : belongList) {
                        hashUsrSids.add(usrMdl.getUsrSid());
                    }
                }
            }

            //グループSIDから読み込んだユーザに選択していたユーザSIDを追加する
            if (svUsrSidList != null) {
                hashUsrSids.addAll(svUsrSidList);
            }

            //set から list へ戻す
            usrSids.addAll(hashUsrSids);
        }

        //対象ユーザ情報を取得する
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        ArrayList<CmnUsrmInfModel> usrmInfList = null;

        usrmInfList = usrmInfDao.getUserList(usrSids);
        if (usrmInfList == null || usrmInfList.size() < 1) {
            return errors;
        }

        int count = 0;
        String text = "";
        GsMessage gsMsg = new GsMessage(reqMdl);

        for (CmnUsrmInfModel model : usrmInfList) {

            if (model.getUsrSid() < 100) {
                continue;
            }

            if (anp150SelectMail__.equals("1")) {
                if (StringUtil.isNullZeroString(model.getUsiMail1())) {
                    text = gsMsg.getMessage("cmn.mailaddress1.user",
                            new String[]{model.getUsiSei() + " " + model.getUsiMei()});
                    msg = new ActionMessage("error.touroku.required.data", text);
                    StrutsUtil.addMessage(errors, msg, count + "error.touroku.required.data");
                }

            } else if (anp150SelectMail__.equals("2")) {
                if (StringUtil.isNullZeroString(model.getUsiMail2())) {
                    text = gsMsg.getMessage("cmn.mailaddress2.user",
                            new String[] {model.getUsiSei() + " " + model.getUsiMei()});
                    msg = new ActionMessage("error.touroku.required.data", text);
                    StrutsUtil.addMessage(errors, msg, count + "error.touroku.required.data");
                }

            } else if (anp150SelectMail__.equals("3")) {
                if (StringUtil.isNullZeroString(model.getUsiMail3())) {
                    text = gsMsg.getMessage("cmn.mailaddress3.user",
                            new String[] {model.getUsiSei() + " " + model.getUsiMei()});
                    msg = new ActionMessage("error.touroku.required.data", text);
                    StrutsUtil.addMessage(errors, msg, count + "error.touroku.required.data");
                }
            }
            count++;

        }

        return errors;
    }


    /**
     * <p>anp150TargetKbn を取得します。
     * @return anp150TargetKbn
     */
    public int getAnp150TargetKbn() {
        return anp150TargetKbn__;
    }

    /**
     * <p>anp150TargetKbn をセットします。
     * @param anp150TargetKbn anp150TargetKbn
     */
    public void setAnp150TargetKbn(int anp150TargetKbn) {
        anp150TargetKbn__ = anp150TargetKbn;
    }

    /**
     * <p>anp150SelectGroupSid を取得します。
     * @return anp150SelectGroupSid
     */
    public String getAnp150SelectGroupSid() {
        return anp150SelectGroupSid__;
    }

    /**
     * <p>anp150SelectGroupSid をセットします。
     * @param anp150SelectGroupSid anp150SelectGroupSid
     */
    public void setAnp150SelectGroupSid(String anp150SelectGroupSid) {
        anp150SelectGroupSid__ = anp150SelectGroupSid;
    }

    /**
     * <p>anp150TargetList を取得します。
     * @return anp150TargetList
     */
    public String[] getAnp150TargetList() {
        return anp150TargetList__;
    }

    /**
     * <p>anp150TargetList をセットします。
     * @param anp150TargetList anp150TargetList
     */
    public void setAnp150TargetList(String[] anp150TargetList) {
        anp150TargetList__ = anp150TargetList;
    }

    /**
     * <p>anp150TargetListUI を取得します。
     * @return anp150TargetListUI
     */
    public UserGroupSelector getAnp150TargetListUI() {
        return anp150TargetListUI__;
    }

    /**
     * <p>anp150TargetListUI をセットします。
     * @param anp150TargetListUI anp150TargetListUI
     */
    public void setAnp150TargetListUI(UserGroupSelector anp150TargetListUI) {
        anp150TargetListUI__ = anp150TargetListUI;
    }

    /**
     * <p>anp150SelectMail を取得します。
     * @return anp150SelectMail
     */
    public String getAnp150SelectMail() {
        return anp150SelectMail__;
    }

    /**
     * <p>anp150SelectMail をセットします。
     * @param anp150SelectMail anp150SelectMail
     */
    public void setAnp150SelectMail(String anp150SelectMail) {
        anp150SelectMail__ = anp150SelectMail;
    }

    /**
     * <p>anp150MailLabel を取得します。
     * @return anp150MailLabel
     */
    public List<LabelValueBean> getAnp150MailLabel() {
        return anp150MailLabel__;
    }

    /**
     * <p>anp150MailLabel をセットします。
     * @param anp150MailLabel anp150MailLabel
     */
    public void setAnp150MailLabel(List<LabelValueBean> anp150MailLabel) {
        anp150MailLabel__ = anp150MailLabel;
    }

    /**
     * <p>anp150OtherMail を取得します。
     * @return anp150OtherMail
     */
    public String getAnp150OtherMail() {
        return anp150OtherMail__;
    }

    /**
     * <p>anp150OtherMail をセットします。
     * @param anp150OtherMail anp150OtherMail
     */
    public void setAnp150OtherMail(String anp150OtherMail) {
        anp150OtherMail__ = anp150OtherMail;
    }

    /**
     * <p>anp150UpdateFlg を取得します。
     * @return anp150UpdateFlg
     */
    public int getAnp150UpdateFlg() {
        return anp150UpdateFlg__;
    }

    /**
     * <p>anp150UpdateFlg をセットします。
     * @param anp150UpdateFlg anp150UpdateFlg
     */
    public void setAnp150UpdateFlg(int anp150UpdateFlg) {
        anp150UpdateFlg__ = anp150UpdateFlg;
    }

    /**
     * <p>anp150PassKbn を取得します。
     * @return anp150PassKbn
     */
    public int getAnp150PassKbn() {
        return anp150PassKbn__;
    }

    /**
     * <p>anp150PassKbn をセットします。
     * @param anp150PassKbn anp150PassKbn
     */
    public void setAnp150PassKbn(int anp150PassKbn) {
        anp150PassKbn__ = anp150PassKbn;
    }
}