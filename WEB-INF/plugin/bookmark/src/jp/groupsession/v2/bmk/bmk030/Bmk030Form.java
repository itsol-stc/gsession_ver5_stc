package jp.groupsession.v2.bmk.bmk030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.GSValidateBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.bmk020.Bmk020Form;
import jp.groupsession.v2.bmk.bmk030.dao.Bmk030Dao;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] ブックマーク登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk030Form extends Bmk020Form {

    /** 登録先ブックマーク区分 */
    private int bmk030mode__ = -1;

    /** 登録先ブックマーク区分名 */
    private String bmk030modeName__ = null;

    /** グループSID */
    private int bmk030groupSid__ = -1;
    /** グループコンボ */
    private List<LabelValueBean> bmk030groupCmbList__ = null;

    /** ＵＲＬ（表示用） */
    private List<String> bmk030UrlDsp__;

    /** タイトル */
    private String bmk030title__ = null;
    /** ラベル */
    private String bmk030label__ = null;
    /** コメント */
    private String bmk030cmt__ = null;

    /** 評価 */
    private int bmk030score__ = -1;
    /** 評価コンボ */
    private List<LabelValueBean> bmk030scoreCmbList__ = null;

    /** 公開区分 */
    private int bmk030public__ = -1;
    /** 公開区分 */
    private int bmk030main__ = -1;

    /** ラベル選択POPUPの選択値 */
    private String bmk040label__ = null;

    /**
     * <p>bmk030mode を取得します。
     * @return bmk030mode
     */
    public int getBmk030mode() {
        return bmk030mode__;
    }

    /**
     * <p>bmk030mode をセットします。
     * @param bmk030mode bmk030mode
     */
    public void setBmk030mode(int bmk030mode) {
        bmk030mode__ = bmk030mode;
    }

    /**
     * <p>bmk030groupCmbList を取得します。
     * @return bmk030groupCmbList
     */
    public List<LabelValueBean> getBmk030groupCmbList() {
        return bmk030groupCmbList__;
    }

    /**
     * <p>bmk030groupCmbList をセットします。
     * @param bmk030groupCmbList bmk030groupCmbList
     */
    public void setBmk030groupCmbList(List<LabelValueBean> bmk030groupCmbList) {
        bmk030groupCmbList__ = bmk030groupCmbList;
    }

    /**
     * <p>bmk030groupSid を取得します。
     * @return bmk030groupSid
     */
    public int getBmk030groupSid() {
        return bmk030groupSid__;
    }

    /**
     * <p>bmk030groupSid をセットします。
     * @param bmk030groupSid bmk030groupSid
     */
    public void setBmk030groupSid(int bmk030groupSid) {
        bmk030groupSid__ = bmk030groupSid;
    }

    /**
     * <p>bmk030title を取得します。
     * @return bmk030title
     */
    public String getBmk030title() {
        return bmk030title__;
    }

    /**
     * <p>bmk030title をセットします。
     * @param bmk030title bmk030title
     */
    public void setBmk030title(String bmk030title) {
        bmk030title__ = bmk030title;
    }

    /**
     * <p>bmk030label を取得します。
     * @return bmk030label
     */
    public String getBmk030label() {
        return bmk030label__;
    }

    /**
     * <p>bmk030label をセットします。
     * @param bmk030label bmk030label
     */
    public void setBmk030label(String bmk030label) {
        bmk030label__ = bmk030label;
    }

    /**
     * <p>bmk030cmt を取得します。
     * @return bmk030cmt
     */
    public String getBmk030cmt() {
        return bmk030cmt__;
    }

    /**
     * <p>bmk030cmt をセットします。
     * @param bmk030cmt bmk030cmt
     */
    public void setBmk030cmt(String bmk030cmt) {
        bmk030cmt__ = bmk030cmt;
    }

    /**
     * <p>bmk030score を取得します。
     * @return bmk030score
     */
    public int getBmk030score() {
        return bmk030score__;
    }

    /**
     * <p>bmk030score をセットします。
     * @param bmk030score bmk030score
     */
    public void setBmk030score(int bmk030score) {
        bmk030score__ = bmk030score;
    }

    /**
     * <p>bmk030scoreCmbList を取得します。
     * @return bmk030scoreCmbList
     */
    public List<LabelValueBean> getBmk030scoreCmbList() {
        return bmk030scoreCmbList__;
    }

    /**
     * <p>bmk030scoreCmbList をセットします。
     * @param bmk030scoreCmbList bmk030scoreCmbList
     */
    public void setBmk030scoreCmbList(List<LabelValueBean> bmk030scoreCmbList) {
        bmk030scoreCmbList__ = bmk030scoreCmbList;
    }

    /**
     * <p>bmk030public を取得します。
     * @return bmk030public
     */
    public int getBmk030public() {
        return bmk030public__;
    }

    /**
     * <p>bmk030public をセットします。
     * @param bmk030public bmk030public
     */
    public void setBmk030public(int bmk030public) {
        bmk030public__ = bmk030public;
    }

    /**
     * <p>bmk030main を取得します。
     * @return bmk030main
     */
    public int getBmk030main() {
        return bmk030main__;
    }

    /**
     * <p>bmk030main をセットします。
     * @param bmk030main bmk030main
     */
    public void setBmk030main(int bmk030main) {
        bmk030main__ = bmk030main;
    }

    /**
     * <p>bmk030modeName を取得します。
     * @return bmk030modeName
     */
    public String getBmk030modeName() {
        return bmk030modeName__;
    }

    /**
     * <p>bmk030modeName をセットします。
     * @param bmk030modeName bmk030modeName
     */
    public void setBmk030modeName(String bmk030modeName) {
        bmk030modeName__ = bmk030modeName;
    }

    /**
     * <p>bmk040label を取得します。
     * @return bmk040label
     */
    public String getBmk040label() {
        return bmk040label__;
    }

    /**
     * <p>bmk040label をセットします。
     * @param bmk040label bmk040label
     */
    public void setBmk040label(String bmk040label) {
        bmk040label__ = bmk040label;
    }

    /**
     * <p>bmk030UrlDsp を取得します。
     * @return bmk030UrlDsp
     */
    public List<String> getBmk030UrlDsp() {
        return bmk030UrlDsp__;
    }

    /**
     * <p>bmk030UrlDsp をセットします。
     * @param bmk030UrlDsp bmk030UrlDsp
     */
    public void setBmk030UrlDsp(List<String> bmk030UrlDsp) {
        bmk030UrlDsp__ = bmk030UrlDsp;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param buMdl セッションユーザ情報
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateBmk030(Connection con, BaseUserModel buMdl, RequestModel reqMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg2 = "";
        String msg3 = "";

        BookmarkBiz biz = new BookmarkBiz(con, reqMdl);
        int mode = bmk030mode__;
        int targetSid = -1;
        if (mode == GSConstBookmark.BMK_KBN_KOJIN) {
            targetSid = buMdl.getUsrsid();
        }
        if (mode == GSConstBookmark.BMK_KBN_GROUP) {
            targetSid = bmk030groupSid__;
        }

        //グループ選択チェック
        if (bmk030mode__ == GSConstBookmark.BMK_KBN_GROUP && bmk030groupSid__ < 0) {
            ActionMessage msg = null;
            String eprefix = "bookmark";
            msg2 = gsMsg.getMessage("cmn.group");
            String fieldfix = msg2 + ".";
            msg = new ActionMessage("error.select.required.text", msg2);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "bmk030groupSid");
        //編集権限チェック
        } else if (!biz.isEditBookmark(
                con, buMdl, mode, targetSid, GSConstBookmark.BMK_MODE_TOUROKU, null)) {
            ActionMessage msg = null;
            String eprefix = "bookmark";
            msg2 = gsMsg.getMessage("bmk.16");
            String fieldfix = msg2 + ".";
            msg3 = gsMsg.getMessage("bmk.57");
            msg = new ActionMessage("errors.free.msg", msg3);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "bmk030mode");
        }

        //ブックマーク重複チェック
        if (getProcMode() == GSConstBookmark.BMK_MODE_TOUROKU) {
            Bmk030Dao dao = new Bmk030Dao(con);
            String title = dao.getBmkTitleFromUrl(bmk030mode__, buMdl.getUsrsid(),
                                                  bmk030groupSid__, getBmk020url());
            if (title != null) {
                ActionMessage msg = null;
                String eprefix = "bookmark";
                String fieldfix = "URL" + ".";
                msg = new ActionMessage("error.input.double.timezone", "URL",
                        StringUtilHtml.transToHTmlPlusAmparsant(title));
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "bmk020url");
                return errors;
            }
        }

        String title = gsMsg.getMessage("cmn.title");
        String comment = gsMsg.getMessage("cmn.comment");

        //タイトルチェック
        GSValidateBookmark.validateCmnFieldText(errors,
                                                title,
                                                bmk030title__,
                                               "bmk030title",
                                                150,
                                                true);
        //コメントチェック
        GSValidateBookmark.validateFieldTextArea(errors,
                                                 comment,
                                                 bmk030cmt__,
                                                "bmk030cmt",
                                                 GSConstBookmark.MAX_LENGTH_CMT,
                                                 false);

        //ラベル名を半角スペースで分割
        String labelName = new String(bmk030label__);
        String[] labelNameList = labelName.split(" ");

        //ラベル名を取得
        for (int i = 0; i < labelNameList.length; i++) {

            //ラベル名
            String blbName = labelNameList[i];
            if (blbName.equals("")) {
                continue;
            }
            String oneLabel = gsMsg.getMessage("bmk.71");
            //ラベル文字チェック
            GSValidateBookmark.validateCmnFieldText(errors,
                                                    oneLabel,
                                                    blbName,
                                                   "bmk030label",
                                                    20,
                                                    false);
            String label = gsMsg.getMessage("cmn.label");
            //ラベル重複チェック
            for (int j = i - 1; j >= 0; j--) {
                if (blbName.equals(labelNameList[j])) {
                    ActionMessage msg = null;
                    String eprefix = "bookmark";
                    String fieldfix = label + ".";
                    msg = new ActionMessage("error.select.dup.list",
                            label + ":" + StringUtilHtml.transToHTmlPlusAmparsant(blbName));
                    StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "bmk030label");
                    break;
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setHiddenParamBmk030(Cmn999Form msgForm) {

        //登録先ブックマーク区分
        msgForm.addHiddenParam("bmk030mode", bmk030mode__);
        //登録先ブックマーク区分名
        msgForm.addHiddenParam("bmk030modeName", bmk030modeName__);
        //グループSID
        msgForm.addHiddenParam("bmk030groupSid", bmk030groupSid__);
        //タイトル
        msgForm.addHiddenParam("bmk030title", bmk030title__);
        //ラベル
        msgForm.addHiddenParam("bmk030label", bmk030label__);
        //コメント
        msgForm.addHiddenParam("bmk030cmt", bmk030cmt__);
        //評価
        msgForm.addHiddenParam("bmk030score", bmk030score__);
        //公開区分
        msgForm.addHiddenParam("bmk030public", bmk030public__);
        //公開区分
        msgForm.addHiddenParam("bmk030main", bmk030main__);
        //コメント・評価から遷移してきたとき
        msgForm.addHiddenParam("bmk070ReturnPage", getBmk070ReturnPage());
        //新着ブックマークから遷移してきたとき
        msgForm.addHiddenParam("bmk150PageNum", getBmk150PageNum());
        msgForm.addHiddenParam("bmk070ToBmk150DspFlg", String.valueOf(isBmk070ToBmk150DspFlg()));
    }

}