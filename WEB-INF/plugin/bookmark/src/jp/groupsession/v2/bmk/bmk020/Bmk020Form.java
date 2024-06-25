package jp.groupsession.v2.bmk.bmk020;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.GSValidateBookmark;
import jp.groupsession.v2.bmk.bmk010.Bmk010Form;
import jp.groupsession.v2.bmk.dao.BmkSchemeDao;
import jp.groupsession.v2.bmk.model.BmkSchemeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


/**
 * <br>[機  能] ブックマーク登録_URL入力画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk020Form extends Bmk010Form {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk020Form.class);

    /** ＵＲＬ */
    private String bmk020url__;

    /** コメント・評価画面から遷移してきたとき */
    private String bmk070ReturnPage__ = "";
    /** ページ(コメント・評価画面の値保持用) */
    private int bmk070Page__;
    /** ページ上段(コメント・評価画面の値保持用) */
    private int bmk070PageTop__;
    /** ページ下段(コメント・評価画面の値保持用) */
    private int bmk070PageBottom__;
    /** 並び順(コメント・評価画面の値保持用) */
    private int bmk070OrderKey__ = GSConstBookmark.ORDERKEY_ASC;
    /** ソートキー(コメント・評価画面の値保持用) */
    private int bmk070SortKey__ = GSConstBookmark.SORTKEY_ADATE;

    /** ページ(ランキング画面の値保持用) */
    private int bmk080Page__;
    /** ページ上段(ランキング画面の値保持用) */
    private int bmk080PageTop__;
    /** ページ下段(ランキング画面の値保持用) */
    private int bmk080PageBottom__;


    /** 初期表示フラグ */
    private boolean bmk030InitFlg__ = false;

    /**
     * <p>bmk030InitFlg を取得します。
     * @return bmk030InitFlg
     */
    public boolean isBmk030InitFlg() {
        return bmk030InitFlg__;
    }

    /**
     * <p>bmk030InitFlg をセットします。
     * @param bmk030InitFlg bmk030InitFlg
     */
    public void setBmk030InitFlg(boolean bmk030InitFlg) {
        bmk030InitFlg__ = bmk030InitFlg;
    }

    /**
     * <p>bmk070ReturnPage を取得します。
     * @return bmk070ReturnPage
     */
    public String getBmk070ReturnPage() {
        return bmk070ReturnPage__;
    }

    /**
     * <p>bmk070ReturnPage をセットします。
     * @param bmk070ReturnPage bmk070ReturnPage
     */
    public void setBmk070ReturnPage(String bmk070ReturnPage) {
        bmk070ReturnPage__ = bmk070ReturnPage;
    }

    /**
     * <p>bmk020url を取得します。
     * @return bmk020url
     */
    public String getBmk020url() {
        return bmk020url__;
    }

    /**
     * <p>bmk020url をセットします。
     * @param bmk020url bmk020url
     */
    public void setBmk020url(String bmk020url) {
        bmk020url__ = bmk020url;
    }

    /**
     * <p>bmk080Page を取得します。
     * @return bmk080Page
     */
    public int getBmk080Page() {
        return bmk080Page__;
    }

    /**
     * <p>bmk080Page をセットします。
     * @param bmk080Page bmk080Page
     */
    public void setBmk080Page(int bmk080Page) {
        bmk080Page__ = bmk080Page;
    }

    /**
     * <p>bmk080PageBottom を取得します。
     * @return bmk080PageBottom
     */
    public int getBmk080PageBottom() {
        return bmk080PageBottom__;
    }

    /**
     * <p>bmk080PageBottom をセットします。
     * @param bmk080PageBottom bmk080PageBottom
     */
    public void setBmk080PageBottom(int bmk080PageBottom) {
        bmk080PageBottom__ = bmk080PageBottom;
    }

    /**
     * <p>bmk080PageTop を取得します。
     * @return bmk080PageTop
     */
    public int getBmk080PageTop() {
        return bmk080PageTop__;
    }

    /**
     * <p>bmk080PageTop をセットします。
     * @param bmk080PageTop bmk080PageTop
     */
    public void setBmk080PageTop(int bmk080PageTop) {
        bmk080PageTop__ = bmk080PageTop;
    }

    /**
     * <p>bmk070OrderKey を取得します。
     * @return bmk070OrderKey
     */
    public int getBmk070OrderKey() {
        return bmk070OrderKey__;
    }

    /**
     * <p>bmk070OrderKey をセットします。
     * @param bmk070OrderKey bmk070OrderKey
     */
    public void setBmk070OrderKey(int bmk070OrderKey) {
        bmk070OrderKey__ = bmk070OrderKey;
    }

    /**
     * <p>bmk070Page を取得します。
     * @return bmk070Page
     */
    public int getBmk070Page() {
        return bmk070Page__;
    }

    /**
     * <p>bmk070Page をセットします。
     * @param bmk070Page bmk070Page
     */
    public void setBmk070Page(int bmk070Page) {
        bmk070Page__ = bmk070Page;
    }

    /**
     * <p>bmk070PageBottom を取得します。
     * @return bmk070PageBottom
     */
    public int getBmk070PageBottom() {
        return bmk070PageBottom__;
    }

    /**
     * <p>bmk070PageBottom をセットします。
     * @param bmk070PageBottom bmk070PageBottom
     */
    public void setBmk070PageBottom(int bmk070PageBottom) {
        bmk070PageBottom__ = bmk070PageBottom;
    }

    /**
     * <p>bmk070PageTop を取得します。
     * @return bmk070PageTop
     */
    public int getBmk070PageTop() {
        return bmk070PageTop__;
    }

    /**
     * <p>bmk070PageTop をセットします。
     * @param bmk070PageTop bmk070PageTop
     */
    public void setBmk070PageTop(int bmk070PageTop) {
        bmk070PageTop__ = bmk070PageTop;
    }

    /**
     * <p>bmk070SortKey を取得します。
     * @return bmk070SortKey
     */
    public int getBmk070SortKey() {
        return bmk070SortKey__;
    }

    /**
     * <p>bmk070SortKey をセットします。
     * @param bmk070SortKey bmk070SortKey
     */
    public void setBmk070SortKey(int bmk070SortKey) {
        bmk070SortKey__ = bmk070SortKey;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return エラー
     * @exception SQLException SQL実行例外
     */
    public ActionErrors validateBmk020(Connection con)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        log__.debug("*****validateBmk020");
        //URL文字チェック
        GSValidateBookmark.validateCmnFieldText(errors,
                                               "URL",
                                                bmk020url__,
                                               "bmk020url",
                                                GSConstBookmark.MAX_LENGTH_URL,
                                                true);
        if (bmk020url__.startsWith("\\")) {
            //ファイルパスは以下、入力チェックは不要
            return errors;
        }
        //URL形式チェック
        boolean urlFlg = true;
        int pos = bmk020url__.indexOf("://");
        String scheme = null;
        String host = null;

        //スキーマの取得
        if (pos < 0) {
            urlFlg = false;
        } else {
            scheme = bmk020url__.substring(0, pos);
        }

        //「://」以降の文字列を取得
        if (urlFlg) {
            host = bmk020url__.substring(pos + 3);
        }

        log__.debug("scheme ===> " + scheme);
        log__.debug("host   ===> " + host);

        //スキーマ存在チェック
        if (urlFlg) {
            BmkSchemeDao sDao = new BmkSchemeDao(con);
            BmkSchemeModel sModel = sDao.select(scheme);
            if (sModel == null) {
                urlFlg = false;
            }
        }

        //「://」以降の文字列チェック
        if (StringUtil.isNullZeroString(host) || ValidateUtil.isSpace(host)) {
            urlFlg = false;
        }

        if (!urlFlg) {
            ActionMessage msg = null;
            String eprefix = "bookmark";
            String fieldfix = "URL" + ".";
            msg = new ActionMessage("error.input.format.text", "URL");
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "bmk020url");
        }


        return errors;
    }
}