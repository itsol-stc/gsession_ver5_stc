package jp.groupsession.v2.zsk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn100.Cmn100AppendInfo;
import jp.groupsession.v2.cmn.cmn100.Cmn100AppendInfoModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ユーザ情報ポップアップで表示する情報を取得する
 * <br>[解  説]
 * <br>[備  考] 在席管理情報を表示する
 *
 * @author JTS
 */
public class ZskCmn100AppendInfo implements Cmn100AppendInfo {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZskCmn100AppendInfo.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public ZskCmn100AppendInfo() {
    }

    /**
     * <br>[機  能] ユーザ情報へ追加する情報を取得する。
     * <br>[解  説]在席情報を付加する
     * <br>
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param gsMsg GSメッセージ
     * @param usid ユーザSID
     * @param sessionUid セッションUID
     * @param con DBコネクション
     * @return メッセージのリスト
     */
    public List<Cmn100AppendInfoModel> getAppendInfo(
            Map<String, Object> paramMap, int usid, int sessionUid,
            Connection con, GsMessage gsMsg) {

        ArrayList<Cmn100AppendInfoModel> appList = null;
        //ユーザの在席状況を取得
        UserSearchModel usrInfMdl = null;
        try {
            UserSearchDao usrDao = new UserSearchDao(con);
            usrInfMdl = usrDao.getUserInfoJtkb(
                    usid, GSConstUser.USER_JTKBN_ACTIVE);
        } catch (SQLException e) {
            log__.error("在席状況の取得に失敗しました。" + e);
        }
        Cmn100AppendInfoModel model = new Cmn100AppendInfoModel();
        String msg = gsMsg.getMessage("zsk.20");

        model.setTitle(msg);
        model.setTitleRow(String.valueOf(1));
        model.setOrder(1);
        model.setFilter(GSConst.BEAN_WRITE_FILTER_NO);
        //メッセージを作成する。
        ArrayList<String> msgList = new ArrayList<String>();
        StringBuilder buf = new StringBuilder();
        buf.append(getZaisekiString(usrInfMdl.getUioStatus(), gsMsg));
        if (usrInfMdl.getUioComment() != null && usrInfMdl.getUioComment().length() > 0) {
            buf.append(" ");
            buf.append("[");
            buf.append(StringUtilHtml.transToHTmlPlusAmparsant(usrInfMdl.getUioComment()));
            buf.append("]");
        }
        msgList.add(buf.toString());
        model.setMessage(msgList);
        appList = new ArrayList<Cmn100AppendInfoModel>();
        appList.add(model);
        return appList;
    }

    /**
     * 在席ステータスから在席文字列を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GSメッセージ
     * @param status 在席ステータス
     * @return String 在席文字列
     */
    public static String getZaisekiString(int status, GsMessage gsMsg) {
        String ret = "";
        String other = gsMsg.getMessage("cmn.other");
        String zaiseki = gsMsg.getMessage("cmn.zaiseki");
        String absence = gsMsg.getMessage("cmn.absence");

        switch (status) {
        case GSConst.UIOSTS_ETC:
            ret = "<td class=\"txt_l bgC_zskOther zskStatus-color\">"
                + other + "</span>";
            break;
        case GSConst.UIOSTS_IN:
            ret = "<td class=\"bgC_zaiseki txt_l zskStatus-color\">"
                + zaiseki + "</span>";
            break;
        case GSConst.UIOSTS_LEAVE:
            ret = "<td class=\"txt_l bgC_huzai zskStatus-color\">"
                + absence + "</span>";
            break;
        default:
            break;
        }
        return ret;
    }
}
