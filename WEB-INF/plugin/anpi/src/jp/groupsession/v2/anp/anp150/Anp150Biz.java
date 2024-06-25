package jp.groupsession.v2.anp.anp150;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 安否確認 管理者設定 緊急連絡先一括設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp150Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp150Biz.class);

    /**
     * <br>[機  能] 初期表示情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param anp150Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp150ParamModel anp150Model, RequestModel reqMdl, Connection con)
                throws Exception {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //全て表示区分(コンボボックス)
        boolean allGroupUserFlg = false;

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();

        //グループリストに初期表示するデフォルトグループを取得
        String dspGpSidStr = anp150Model.getAnp150SelectGroupSid();
        if (StringUtil.isNullZeroString(dspGpSidStr)) {
            dspGpSidStr = anpiBiz.getDefaultGroupSid(con, sessionUsrSid, allGroupUserFlg);
            anp150Model.setAnp150SelectGroupSid(dspGpSidStr);
        }
        anp150Model.setAnp150SelectGroupSid(dspGpSidStr);

        //メールアドレスコンボ設定
        anp150Model.setAnp150MailLabel(__getMailCombo(reqMdl));
    }


    /**
     * <br>[機  能] ユーザリストに選択されているユーザリストを追加して戻します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userList   元のユーザリスト
     * @param selectSids 選択ユーザリスト
     * @return 選択ユーザを追加したユーザリスト
     * @throws Exception 実行例外
     */
    public String[] getUserListAdd(String[] userList, String[] selectSids)
                        throws Exception {
        if (selectSids == null) {
            return userList;
        }
        if (selectSids.length < 1) {
            return userList;
        }

        log__.debug("追加後ユーザを取得");

        //元のユーザリストから、戻り配列を作成
        List<String> newList = new ArrayList<String>();
        if (userList != null && userList.length > 0) {
            for (String sid: userList) {
                newList.add(sid);
            }
        }

        //選択されているユーザSIDを追加
        if (selectSids != null && selectSids.length > 0) {
            for (String sid: selectSids) {
                newList.add(sid);
            }
        }

        String[] ret = null;
        if (newList.size() > 0) {
            ret = (String[]) newList.toArray(new String[newList.size()]);
        }
        return ret;
    }

    /**
     * <br>[機  能] ユーザリストから選択されているユーザを削除して戻します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userList   元のユーザリスト
     * @param selectSids 選択ユーザリスト
     * @return 選択ユーザを削除したユーザリスト
     * @throws Exception 実行例外
     */
    public String[] getUserListDel(String[] userList, String[] selectSids)
                        throws Exception {

        log__.debug("削除後ユーザを取得");

        if (userList == null || userList.length == 0) {
            return null;
        }

        //元のリストから選択されているユーザ以外を追加
        List<String> newList = new ArrayList<String>();
        String[] selects = new String[0];
        if (selectSids != null) {
            selects = Arrays.copyOf(selectSids, selectSids.length);
        }
        Arrays.sort(selects);

        for (String sid: userList) {
            if (Arrays.binarySearch(selects, sid) < 0) {
                newList.add(sid);
            }
        }

        String[] ret = null;
        if (newList.size() > 0) {
            ret = (String[]) newList.toArray(new String[newList.size()]);
        }
        return ret;
    }

    /**
     * <br>[機  能] メールアドレスコンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエストモデル
     * @return List (in LabelValueBean)  メールアドレスコンボ
     * @throws Exception 実行時例外
     */
    private List<LabelValueBean> __getMailCombo(RequestModel reqMdl)
            throws Exception {

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String mailAdr1 = gsMsg.getMessage("cmn.mailaddress1");
        String mailAdr2 = gsMsg.getMessage("cmn.mailaddress2");
        String mailAdr3 = gsMsg.getMessage("cmn.mailaddress3");
        String mailAdr4 = gsMsg.getMessage("sml.122");

        labelList.add(new LabelValueBean(mailAdr1, "1"));
        labelList.add(new LabelValueBean(mailAdr2, "2"));
        labelList.add(new LabelValueBean(mailAdr3, "3"));
        labelList.add(new LabelValueBean(mailAdr4, "0"));

        return labelList;
    }
}