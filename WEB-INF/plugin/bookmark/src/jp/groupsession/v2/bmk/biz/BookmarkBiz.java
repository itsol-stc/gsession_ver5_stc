package jp.groupsession.v2.bmk.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkAconfDao;
import jp.groupsession.v2.bmk.dao.BmkBookmarkDao;
import jp.groupsession.v2.bmk.dao.BmkGconfDao;
import jp.groupsession.v2.bmk.dao.BmkGroupEditDao;
import jp.groupsession.v2.bmk.dao.BmkLabelDao;
import jp.groupsession.v2.bmk.dao.BmkPublicEditDao;
import jp.groupsession.v2.bmk.model.BmkAconfModel;
import jp.groupsession.v2.bmk.model.BmkBookmarkModel;
import jp.groupsession.v2.bmk.model.BmkGconfModel;
import jp.groupsession.v2.bmk.model.BmkLabelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] ブックマークの共通機能実装クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BookmarkBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BookmarkBiz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

//    /**
//     * <br>[機  能] デフォルトコンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public BookmarkBiz() {
//    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public BookmarkBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] グループコンボに設定する状報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabelList(Connection con)
    throws SQLException {
        return getGroupLabelList(con, true, true, null);
    }

    /**
     * <br>[機  能] グループブックマーク編集権限があるグループコンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getEditGroupList(Connection con, BaseUserModel userMdl)
    throws SQLException {
        return getGroupLabelList(con, true, false, userMdl);
    }

    /**
     * <br>[機  能] グループコンボに設定する状報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param defGroup "選択してください"をコンボに含めるか
     * @param editKbn  true:編集権限なしも含める false:編集権限ありのみ
     * @param userMdl セッションユーザ情報
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getGroupLabelList(Connection con, boolean defGroup,
                                                        boolean editKbn, BaseUserModel userMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.select.plz");

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(msg, "-1"));

        //グループリスト取得
//        GroupDao dao = new GroupDao(con);
//        ArrayList<GroupModel> gpList = dao.getGroupTree();
        GroupBiz groupBiz = new GroupBiz();
        ArrayList<GroupModel> gpList = groupBiz.getGroupCombList(con);

        GroupModel gpMdl = null;
        for (int i = 0; i < gpList.size(); i++) {
            gpMdl = gpList.get(i);

            if (!editKbn
              && !__isEditPow(con, userMdl, GSConstBookmark.BMK_KBN_GROUP, gpMdl.getGroupSid())) {
                continue;
            }

            labelList.add(
                    new LabelValueBean(gpMdl.getGroupName(), String.valueOf(gpMdl.getGroupSid())));
        }

        return labelList;
    }
    /**
     * <br>[機  能] グループ名称一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param grpSidList グループSID
     * @return グループ名称一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getGroupNameList(Connection con, String[] grpSidList)
    throws SQLException {

        List<String> grpNameList = new ArrayList<String>();

        if (grpSidList != null && grpSidList.length > 0) {

            GroupDao grpDao = new GroupDao(con);
            int[] intGrpSidList = new int[grpSidList.length];
            int index = 0;
            for (String grpSid : grpSidList) {
                intGrpSidList[index] = Integer.parseInt(grpSid);
                index++;
            }
            List <CmnGroupmModel> grpDataList = grpDao.getGroups(intGrpSidList);
            for (CmnGroupmModel grpData : grpDataList) {
                grpNameList.add(grpData.getGrpName());
            }
        }

        return grpNameList;
    }

    /**
     * <br>[機  能] ユーザ名称一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSidList ユーザSID
     * @return ユーザ名称一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getUserNameList(Connection con, String[] usrSidList)
    throws SQLException {

        List<String> userNameList = new ArrayList<String>();

        if (usrSidList != null && usrSidList.length > 0) {

            UserBiz userBiz = new UserBiz();
            List<UsrLabelValueBean> userLabelList = userBiz.getUserLabelList(con, usrSidList);
            for (LabelValueBean userLabel : userLabelList) {
                userNameList.add(userLabel.getLabel());
            }
        }

        return userNameList;
    }

    /**
     * <br>[機  能] グループ管理者判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gSid グループSID
     * @return true:グループ管理者 false:NG
     * @throws SQLException SQL実行時例外
     */
    public boolean isGrpAdmin(Connection con, int gSid)
    throws SQLException {

        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        BmkAconfDao daoAconf = new BmkAconfDao(con);
        BmkAconfModel mdlAconf = daoAconf.selectAConf();

        //グループブックマーク編集権限：グループ別に権限を設定する
        if (mdlAconf != null
            && mdlAconf.getBacGrpEdit() == GSConstBookmark.GROUP_EDIT_GROUP) {
            //システム管理者はtrue
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(con, usModel,
                                                     GSConstBookmark.PLUGIN_ID_BOOKMARK);

            if (adminUser) {
                return true;
            }
            // グループ管理者はtrue
            CmnBelongmDao dao = new CmnBelongmDao(con);
            CmnBelongmModel mdl = dao.select(usModel.getUsrsid(), gSid);
            if (mdl != null
                && mdl.getBegGrpkbn() == GSConst.USER_ADMIN) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 編集権限判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @param bmkKbn ブックマーク区分
     * @param targetSid 選択ユーザ/グループSID
     * @return true:権限あり false:権限なし
     * @throws SQLException SQL実行時例外
     */
    private boolean __isEditPow(Connection con, BaseUserModel userMdl, int bmkKbn, int targetSid)
                    throws SQLException {

        //セッションユーザSID
        int usrSid = userMdl.getUsrsid();

        //判定１　個人ブックマーク
        if (bmkKbn == GSConstBookmark.BMK_KBN_KOJIN && usrSid == targetSid) {
            return true;
        }

        //個人ブックマークのとき処理終了
        if (bmkKbn == GSConstBookmark.BMK_KBN_KOJIN) {
            return false;
        }

        //管理者設定の値を取得する
        BmkAconfDao daoAconf = new BmkAconfDao(con);
        BmkAconfModel mdlAconf = daoAconf.selectAConf();
        //共有ブックマーク編集権限
        int pubEdit = GSConstBookmark.EDIT_POW_ADMIN;
        if (mdlAconf != null) {
            pubEdit = mdlAconf.getBacPubEdit();
        }
        //グループブックマーク編集権限
        int grpEdit = GSConstBookmark.GROUP_EDIT_ADMIN;
        if (mdlAconf != null) {
            grpEdit = mdlAconf.getBacGrpEdit();
        }

        //判定２　共有ブックマーク、システム管理者のみ
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, GSConstBookmark.PLUGIN_ID_BOOKMARK);

        if (bmkKbn == GSConstBookmark.BMK_KBN_KYOYU && pubEdit == GSConstBookmark.EDIT_POW_ADMIN
                && adminUser) {
            return true;
        }

        //判定３　共有ブックマーク、権限設定なし
        if (bmkKbn == GSConstBookmark.BMK_KBN_KYOYU && pubEdit == GSConstBookmark.EDIT_POW_ALL) {
            return true;
        }

        BmkPublicEditDao pEditdao = new BmkPublicEditDao(con);

        //判定４　共有ブックマーク、ユーザ指定
        if (bmkKbn == GSConstBookmark.BMK_KBN_KYOYU && pubEdit == GSConstBookmark.EDIT_POW_USER) {
            return pEditdao.selectPow(pubEdit, -1, usrSid);
        }

        //判定５　共有ブックマーク、グループ指定
        if (bmkKbn == GSConstBookmark.BMK_KBN_KYOYU && pubEdit == GSConstBookmark.EDIT_POW_GROUP) {
            //セッションユーザSIDが所属するグループを取得
            UsidSelectGrpNameDao dao = new UsidSelectGrpNameDao(con);
            ArrayList<GroupModel> grpList = dao.selectGroupNmList(usrSid);
            //権限確認
            for (GroupModel grpModel : grpList) {
                if (pEditdao.selectPow(pubEdit, grpModel.getGroupSid(), -1)) {
                    return true;
                }
            }
        }

        //共有ブックマークのとき処理終了
        if (bmkKbn == GSConstBookmark.BMK_KBN_KYOYU) {
            return false;
        }

        //判定６　グループブックマーク　グループSIDの存在チェック
        if (bmkKbn == GSConstBookmark.BMK_KBN_GROUP) {
            CmnGroupmDao gDao = new CmnGroupmDao(con);
            CmnGroupmModel gModel = gDao.select(targetSid);
            if (gModel == null) {
                return false;
            }
        }

        //判定７　グループブックマーク、システム管理者のみ
        if (bmkKbn == GSConstBookmark.BMK_KBN_GROUP && grpEdit == GSConstBookmark.GROUP_EDIT_ADMIN
                && adminUser) {
            return true;
        }

        //判定８　グループブックマーク、権限設定なし
        if (bmkKbn == GSConstBookmark.BMK_KBN_GROUP && grpEdit == GSConstBookmark.GROUP_EDIT_ALL) {
            return true;
        }

        //判定９　グループブックマーク、グループ別に設定
        if (bmkKbn == GSConstBookmark.BMK_KBN_GROUP
                && grpEdit == GSConstBookmark.GROUP_EDIT_GROUP) {
            return __isEditGroupPow(con, usrSid, targetSid);
        }

        return false;
    }

    /**
     * <br>[機  能] グループ編集権限判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid セッションユーザSID
     * @param selectGrpSid 選択グループSID
     * @return true:権限あり false:権限なし
     * @throws SQLException SQL実行時例外
     */
    private boolean __isEditGroupPow(Connection con, int usrSid, int selectGrpSid)
    throws SQLException {

        //グループ設定の値を取得する
        BmkGconfDao daoGconf = new BmkGconfDao(con);
        BmkGconfModel mdlGconf = daoGconf.selectGConf(selectGrpSid);
        //グループ別編集権限
        int grpEdit = GSConstBookmark.EDIT_POW_ADMIN;
        if (mdlGconf != null) {
            grpEdit = mdlGconf.getBgcEdit();
        }

        //判定１　グループ管理者のみ
        if (grpEdit == GSConstBookmark.EDIT_POW_ADMIN) {
            //グループ管理者かどうかの判定
            CmnBelongmDao dao = new CmnBelongmDao(con);
            CmnBelongmModel mdl = dao.select(usrSid, selectGrpSid);
            if (mdl != null && mdl.getBegGrpkbn() == GSConst.USER_ADMIN) {
                return true;
            }
        }

        //判定２　権限設定なし
        if (grpEdit == GSConstBookmark.EDIT_POW_ALL) {
            return true;
        }

        BmkGroupEditDao gEditdao = new BmkGroupEditDao(con);

        //判定３　ユーザ指定
        if (grpEdit == GSConstBookmark.EDIT_POW_USER) {
            return gEditdao.selectPow(grpEdit, selectGrpSid, -1, usrSid);
        }

        //判定４　グループ指定
        if (grpEdit == GSConstBookmark.EDIT_POW_GROUP) {
            //セッションユーザSIDが所属するグループを取得
            UsidSelectGrpNameDao dao = new UsidSelectGrpNameDao(con);
            ArrayList<GroupModel> grpList = dao.selectGroupNmList(usrSid);
            //権限確認
            for (GroupModel grpModel : grpList) {
                if (gEditdao.selectPow(grpEdit, selectGrpSid, grpModel.getGroupSid(), -1)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * <br>[機  能] 文字列をバイト数で区切ってリストに格納する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cutByte 区切る長さ(バイト数)
     * @param text 文字列
     * @return 区切った文字列リスト
     */
    public List<String> getStringCutList(int cutByte, String text) {

        List<String> ret = new ArrayList<String>();

        String str = text;
        byte[] bytes = str.getBytes();
        //バイト数
        int txtByt = bytes.length;

        if (txtByt <= cutByte) {
            ret.add(text);
            return ret;
        }

        String textWrk = text;

        for (int i = 0; i + cutByte <= txtByt; i += cutByte) {
            //１文字ずつ検査
            int dstlen = 0;
            int txtWrkLen = textWrk.length();
            for (int j = 0; j < txtWrkLen; j++) {
                dstlen += (textWrk.charAt(j) <= 0xff ? 1 : 2);
                if (dstlen > cutByte) {
                    ret.add(textWrk.substring(0, j));
                    textWrk = textWrk.substring(j);
                    break;
                }
            }
        }
        if (textWrk.length() > 0) {
            ret.add(textWrk);
        }

        return ret;
    }

    /**
     * ブックマーク全般のログ出力を行う
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param type type
     */
    public void outPutLog(
            String opCode,
            String level,
            String value,
            String type) {

        BaseUserModel usModel = reqMdl__.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("bmk.43");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstBookmark.PLUGIN_ID_BOOKMARK);
        logMdl.setLogPluginName(msg);
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(type));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl__.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl__.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getPgName(String id) {
        String ret = new String();
        if (id == null) {
            return ret;
        }
        log__.info("プログラムID==>" + id);

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = "";


        if (id.equals("jp.groupsession.v2.bmk.bmk010.Bmk010Action")) {
            msg = gsMsg.getMessage("bmk.60");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.bmk.bmk030.Bmk030Action")) {
            msg = gsMsg.getMessage("bmk.61");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.bmk.bmk050.Bmk050Action")) {
            msg = gsMsg.getMessage("cmn.labellist");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.bmk.bmk060.Bmk060Action")) {
            msg = gsMsg.getMessage("cmn.label.for.editing");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.bmk.bmk110kn.Bmk110knAction")) {
            msg = gsMsg.getMessage("bmk.65");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.bmk.bmk130.Bmk130Action")) {
            msg = String.format("%s %s",
                    gsMsg.getMessage("cmn.preferences2"),
                    gsMsg.getMessage("cmn.display.settings"));
            return msg;
        }
        if (id.equals("jp.groupsession.v2.bmk.ptl020.BmkPtl020Action")) {
            msg = gsMsg.getMessage("ptl.ptl040.1");
            return msg;
        }
        if (id.equals("jp.groupsession.v2.bmk.bmk160kn.Bmk160knAction")) {
            msg = gsMsg.getMessage("bmk.72");
            return msg;
        }

        return ret;
    }

    /**
     * <br>[機  能] HTML特殊文字を変換します。
     * <br>[解  説]
     * <br>[備  考]
     * @param value 文字列
     * @return 変換後の文字列
     */
    public String formatBookmarkTitle(String value) {
        if (StringUtil.isNullZeroString(value)) {
            return value;
        }

        String formatValue = value.toString();
        List<String[]> formatList = __getHtmlFormatList();
        for (String[] format : formatList) {
            formatValue = formatValue.replaceAll(format[2], format[0]);
        }
        return formatValue;
    }

    /**
     * <br>[機  能] HTML特殊文字を変換するための特殊文字/文字列対応リストを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @return 特殊文字/文字列対応リスト
     */
    private List<String[]> __getHtmlFormatList() {
        List<String[]> list = new ArrayList<String[]>();
        list.add(new String[] {"\"", "&quot;", "&#034;"});
        list.add(new String[] {"&", "&amp;", "&#038;"});
        list.add(new String[] {"<", "&lt;", "&#060;"});
        list.add(new String[] {">", "&gt;", "&#062;"});
        list.add(new String[] {" ", "&nbsp;", "&#160;"});
        list.add(new String[] {"§", "&sect;", "&#167;"});
        list.add(new String[] {"¨", "&uml;", "&#168;"});
        list.add(new String[] {"≪", "&laquo;", "&#171;"});
        list.add(new String[] {"￢", "&not;", "&#172;"});
        list.add(new String[] {"￣", "&macr;", "&#175;"});
        list.add(new String[] {"°", "&deg;", "&#176;"});
        list.add(new String[] {"±", "&plusmn;", "&#177;"});
        list.add(new String[] {"´", "&acute;", "&#180;"});
        list.add(new String[] {"μ", "&micro;", "&#181;"});
        list.add(new String[] {"¶", "&para;", "&#182;"});
        list.add(new String[] {"・", "&middot;", "&#183;"});
        list.add(new String[] {"，", "&cedil;", "&#184;"});
        list.add(new String[] {"1", "&sup1;", "&#185;"});
        list.add(new String[] {"o", "&ordm;", "&#186;"});
        list.add(new String[] {"≫", "&raquo;", "&#187;"});
        list.add(new String[] {"‘", "&lsquo;", "&#8216;"});
        list.add(new String[] {"’", "&rsquo;", "&#8217;"});
        list.add(new String[] {"“", "&ldquo;", "&#8220;"});
        list.add(new String[] {"”", "&rdquo;", "&#8221;"});
        list.add(new String[] {"†", "&dagger;", "&#8224;"});
        list.add(new String[] {"‡", "&Dagger;", "&#8225;"});
        list.add(new String[] {"…", "&hellip;", "&#8230;"});
        list.add(new String[] {"‰", "&permil;", "&#8240;"});
        list.add(new String[] {"′", "&prime;", "&#8242;"});
        list.add(new String[] {"″", "&Prime;", "&#8243;"});
        list.add(new String[] {"←", "&larr;", "&#8592;"});
        list.add(new String[] {"↑", "&uarr;", "&#8593;"});
        list.add(new String[] {"→", "&rarr;", "&#8594;"});
        list.add(new String[] {"↓", "&darr;", "&#8595;"});
        list.add(new String[] {"⇒", "&rArr;", "&#8658;"});
        list.add(new String[] {"⇔", "&hArr;", "&#8660;"});
        list.add(new String[] {"∀", "&forall;", "&#8704;"});
        list.add(new String[] {"∂", "&part;", "&#8706;"});
        list.add(new String[] {"∃", "&exist;", "&#8707;"});
        list.add(new String[] {"∇", "&nabla;", "&#8711;"});
        list.add(new String[] {"∈", "&isin;", "&#8712;"});
        list.add(new String[] {"∋", "&ni;", "&#8715;"});
        list.add(new String[] {"∑", "&sum;", "&#8721;"});
        list.add(new String[] {"√", "&radic;", "&#8730;"});
        list.add(new String[] {"∝", "&prop;", "&#8733;"});
        list.add(new String[] {"∞", "&infin;", "&#8734;"});
        list.add(new String[] {"∠", "&ang;", "&#8736;"});
        list.add(new String[] {"∧", "&and;", "&#8743;"});
        list.add(new String[] {"∨", "&or;", "&#8744;"});
        list.add(new String[] {"∩", "&cap;", "&#8745;"});
        list.add(new String[] {"∪", "&cup;", "&#8746;"});
        list.add(new String[] {"∫", "&int;", "&#8747;"});
        list.add(new String[] {"∴", "&there4;", "&#8756;"});
        list.add(new String[] {"≠", "&ne;", "&#8800;"});
        list.add(new String[] {"≡", "&equiv;", "&#8801;"});
        list.add(new String[] {"⊂", "&sub;", "&#8834;"});
        list.add(new String[] {"⊃", "&sup;", "&#8835;"});
        list.add(new String[] {"⊆", "&sube;", "&#8838;"});
        list.add(new String[] {"⊇", "&supe;", "&#8839;"});
        list.add(new String[] {"⊥", "&perp;", "&#8869;"});
        list.add(new String[] {"★", "", "&#9733;"});
        list.add(new String[] {"☆", "", "&#9734;"});
        list.add(new String[] {"♀", "", "&#9792;"});
        list.add(new String[] {"♂", "", "&#9794;"});
        list.add(new String[] {"♪", "", "&#9834;"});
        list.add(new String[] {"♭", "", "&#9837;"});
        list.add(new String[] {"♯", "", "&#9839;"});
        list.add(new String[] {"　", "", "&#12288;"});
        list.add(new String[] {"、", "", "&#12289;"});
        list.add(new String[] {"。", "", "&#12290;"});
        list.add(new String[] {"〃", "", "&#12291;"});
        list.add(new String[] {"々", "", "&#12293;"});
        list.add(new String[] {"〆", "", "&#12294;"});
        list.add(new String[] {"〇", "", "&#12295;"});
        list.add(new String[] {"〈", "", "&#12296;"});
        list.add(new String[] {"〉", "", "&#12297;"});
        list.add(new String[] {"《", "", "&#12298;"});
        list.add(new String[] {"》", "", "&#12299;"});
        list.add(new String[] {"「", "", "&#12300;"});
        list.add(new String[] {"」", "", "&#12301;"});
        list.add(new String[] {"『", "", "&#12302;"});
        list.add(new String[] {"』", "", "&#12303;"});
        list.add(new String[] {"【", "", "&#12304;"});
        list.add(new String[] {"】", "", "&#12305;"});
        list.add(new String[] {"〒", "", "&#12306;"});
        list.add(new String[] {"〓", "", "&#12307;"});
        list.add(new String[] {"〔", "", "&#12308;"});
        list.add(new String[] {"〕", "", "&#12309"});
        list.add(new String[] {"〝", "", "&#12317;"});
        list.add(new String[] {"〟", "", "&#12319;"});
        list.add(new String[] {"ぁ", "", "&#12353;"});
        list.add(new String[] {"ぃ", "", "&#12355;"});
        list.add(new String[] {"ぅ", "", "&#12357;"});
        list.add(new String[] {"ぇ", "", "&#12359;"});
        list.add(new String[] {"ぉ", "", "&#12361;"});
        list.add(new String[] {"ゐ", "", "&#12432;"});
        list.add(new String[] {"ゑ", "", "&#12433;"});
        list.add(new String[] {"゛", "", "&#12443;"});
        list.add(new String[] {"゜", "", "&#12444;"});
        list.add(new String[] {"ゝ", "", "&#12445;"});
        list.add(new String[] {"ゞ", "", "&#12446;"});
        list.add(new String[] {"・", "", "&#12539;"});
        list.add(new String[] {"ー", "", "&#12540;"});
        list.add(new String[] {"ヽ", "", "&#12541;"});
        list.add(new String[] {"ヾ", "", "&#12542;"});
        list.add(new String[] {"㈱", "", "&#12849;"});
        list.add(new String[] {"㈲", "", "&#12850;"});
        list.add(new String[] {"㈹", "", "&#12857;"});
        list.add(new String[] {"㊤", "", "&#12964;"});
        list.add(new String[] {"㊥", "", "&#12965;"});
        list.add(new String[] {"㊦", "", "&#12966;"});
        list.add(new String[] {"㊧", "", "&#12967;"});
        list.add(new String[] {"㊨", "", "&#12968;"});
        list.add(new String[] {"㌃", "", "&#13059;"});
        list.add(new String[] {"㌍", "", "&#13069;"});
        list.add(new String[] {"㌔", "", "&#13076;"});
        list.add(new String[] {"㌘", "", "&#13080;"});
        list.add(new String[] {"㌢", "", "&#13090;"});
        list.add(new String[] {"㌣", "", "&#13091;"});
        list.add(new String[] {"㌦", "", "&#13094;"});
        list.add(new String[] {"㌧", "", "&#13095;"});
        list.add(new String[] {"㌫", "", "&#13099;"});
        list.add(new String[] {"㌶", "", "&#13110;"});
        list.add(new String[] {"㌻", "", "&#13115;"});
        list.add(new String[] {"㍉", "", "&#13129;"});
        list.add(new String[] {"㍊", "", "&#13130;"});
        list.add(new String[] {"㍍", "", "&#13133;"});
        list.add(new String[] {"㍑", "", "&#13137;"});
        list.add(new String[] {"㍗", "", "&#13143;"});
        list.add(new String[] {"㍻", "", "&#13179;"});
        list.add(new String[] {"㍼", "", "&#13180;"});
        list.add(new String[] {"㍽", "", "&#13181;"});
        list.add(new String[] {"㍾", "", "&#13182;"});
        list.add(new String[] {"㎎", "", "&#13198;"});
        list.add(new String[] {"㎏", "", "&#13199;"});
        list.add(new String[] {"㎜", "", "&#13212;"});
        list.add(new String[] {"㎝", "", "&#13213;"});
        list.add(new String[] {"㎞", "", "&#13214;"});
        list.add(new String[] {"㎡", "", "&#13217;"});
        list.add(new String[] {"㏄", "", "&#13252"});
        list.add(new String[] {"㏍", "", "&#13261;"});
        return list;
    }

    /**
     * <br>[機  能] 編集対象チェックと権限チェック
     * <br>[解  説] 編集対象ブックマークチェックと権限チェックを行う
     * <br>[備  考]
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @param bmkKbn ブックマーク区分
     * @param targetSid 選択ユーザ/グループSID
     * @param addEditKbn 登録・編集区分
     * @param bmkSidList ブックマークSIDリスト
     * @return true:権限あり false:権限なし
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditBookmark(Connection con, BaseUserModel userMdl, int bmkKbn,
            int targetSid, int addEditKbn, List<Integer> bmkSidList) throws SQLException {

        if (addEditKbn == GSConstBookmark.BMK_MODE_TOUROKU) {
            return __isEditPow(con, userMdl, bmkKbn, targetSid);
        }
        if (addEditKbn == GSConstBookmark.BMK_MODE_EDIT) {
            if (bmkSidList == null || bmkSidList.size() == 0) {
                return false;
            }

            BmkBookmarkDao bmkDao = new BmkBookmarkDao(con);
            List<BmkBookmarkModel> bmkList = new ArrayList<BmkBookmarkModel>();
            bmkList = bmkDao.select(bmkSidList);
            if (bmkList.size() != bmkSidList.size()) {
                return false;
            }

            int target = -1;
            for (BmkBookmarkModel model : bmkList) {
                if (model.getBmkKbn() == GSConstBookmark.BMK_KBN_KOJIN) {
                    target = model.getUsrSid();
                } else if (model.getBmkKbn() == GSConstBookmark.BMK_KBN_GROUP) {
                    target = model.getGrpSid();
                }
                if (!__isEditPow(con, userMdl, model.getBmkKbn(), target)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 編集ラベルチェックと権限チェック
     * <br>[解  説] 編集対象ラベルチェックと権限チェックを行う
     * <br>[備  考]
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @param bmkKbn ブックマーク区分
     * @param targetSid 選択ユーザ/グループSID
     * @param addEditKbn 登録・編集区分
     * @param lblSidList ブックマークSIDリスト
     * @return true:権限あり false:権限なし
     * @throws SQLException SQL実行時例外
     */
    public boolean isEditLabel(Connection con, BaseUserModel userMdl, int bmkKbn,
            int targetSid, int addEditKbn, List<Integer> lblSidList) throws SQLException {

        if (addEditKbn == GSConstBookmark.BMK_MODE_TOUROKU) {
            return __isEditPow(con, userMdl, bmkKbn, targetSid);
        }
        if (addEditKbn == GSConstBookmark.BMK_MODE_EDIT) {
            if (lblSidList == null || lblSidList.size() == 0) {
                return false;
            }

            BmkLabelDao bmkDao = new BmkLabelDao(con);
            List<BmkLabelModel> bmkList = new ArrayList<BmkLabelModel>();
            bmkList = bmkDao.select(lblSidList);
            if (bmkList.size() != lblSidList.size()) {
                return false;
            }

            int target = -1;
            for (BmkLabelModel model : bmkList) {
                if (model.getBlbKbn() == GSConstBookmark.BMK_KBN_KOJIN) {
                    target = model.getUsrSid();
                } else if (model.getBlbKbn() == GSConstBookmark.BMK_KBN_GROUP) {
                    target = model.getGrpSid();
                }
                if (!__isEditPow(con, userMdl, model.getBlbKbn(), target)) {
                    return false;
                }
            }
        }
        return true;
    }
}