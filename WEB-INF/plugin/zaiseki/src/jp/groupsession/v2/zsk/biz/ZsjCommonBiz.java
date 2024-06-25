package jp.groupsession.v2.zsk.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrInoutDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.dao.WkZaiIndexDao;
import jp.groupsession.v2.zsk.dao.ZaiAdmConfDao;
import jp.groupsession.v2.zsk.dao.ZaiInfoDao;
import jp.groupsession.v2.zsk.dao.ZaiPriConfDao;
import jp.groupsession.v2.zsk.model.ZaiAdmConfModel;
import jp.groupsession.v2.zsk.model.ZaiIndexModel;
import jp.groupsession.v2.zsk.model.ZaiInfoPlusModel;
import jp.groupsession.v2.zsk.model.ZaiPriConfModel;
import jp.groupsession.v2.zsk.model.ZskSortModel;

/**
 * <br>[機  能] 在席管理プラグインで使用される共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZsjCommonBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZsjCommonBiz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public ZsjCommonBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

//    /**
//     * <br>[機  能] コンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     *
//     */
//    public ZsjCommonBiz() {
//    }
    /**
     * <br>[機  能] 在席管理：指定ユーザの在席ステータスを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usid ユーザSID
     * @param con コネクション
     * @return UserSearchModel
     * @throws SQLException SQL実行時例外
     */
    public UserSearchModel getZskStatusData(int usid, Connection con) throws SQLException {

        UserSearchDao dao = new UserSearchDao(con);
        UserSearchModel bean = dao.getUserInfoJtkb(usid, GSConstUser.USER_JTKBN_ACTIVE);

        return bean;
    }
    /**
     * <br>[機  能] 在席管理：指定ユーザの在席ステータス更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usid ユーザSID
     * @param sts 在席ステータス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setZskStatus(int usid, int sts, Connection con) throws SQLException {

        log__.debug("在席ステータス更新：開始");

        CmnUsrInoutDao dao = new CmnUsrInoutDao(con);
        CmnUsrInoutModel bean = new CmnUsrInoutModel();
        UDate now = new UDate();
        bean.setUioSid(usid);
        bean.setUioStatus(sts);
        bean.setUioAuid(usid);
        bean.setUioAdate(now);
        bean.setUioEuid(usid);
        bean.setUioEdate(now);
        if (dao.selCnt(usid) == 0) {
            dao.insert(bean);
        } else {
            dao.update(bean);
        }
        con.commit();

        log__.debug("在席ステータス更新：終了");

        return;
    }
    /**
     * <br>[機  能] 在席ステータスを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid 対象ユーザSID
     * @param status 在席状況
     * @param msg コメント
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateUserZskStatus(
            int userSid,
            int status,
            String msg,
            Connection con) throws SQLException {

        log__.debug("在席ステータス更新");

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUserSid = usModel.getUsrsid();

        UDate now = new UDate();
        CmnUsrInoutModel param = new CmnUsrInoutModel();
        param.setUioSid(userSid);
        param.setUioStatus(status);
        param.setUioBiko(msg);
        param.setUioAuid(sessionUserSid);
        param.setUioAdate(now);
        param.setUioEuid(sessionUserSid);
        param.setUioEdate(now);

        updateZskStatus(con, param);
    }

    /**
     * <br>[機  能] 在席ステータスのみを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid 対象ユーザSID
     * @param status 在席状況
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateUserZskStatusOnly(
            int userSid,
            int status,
            Connection con) throws SQLException {

        log__.debug("在席ステータス更新");

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUserSid = usModel.getUsrsid();

        UDate now = new UDate();
        CmnUsrInoutModel param = new CmnUsrInoutModel();
        param.setUioSid(userSid);
        param.setUioStatus(status);
        param.setUioAuid(sessionUserSid);
        param.setUioAdate(now);
        param.setUioEuid(sessionUserSid);
        param.setUioEdate(now);

        updateZskStatusOnly(con, param);
    }

    /**
     * <br>[機  能] 在席ステータスを更新する。
     * <br>[解  説] レコードが存在しない場合は追加を行う。
     * <br>[備  考]
     *
     * @param con コネクション
     * @param model 更新用のモデル
     * @throws SQLException SQL実行時例外
     */
    public void updateZskStatus(Connection con, CmnUsrInoutModel model)
        throws SQLException {

        log__.debug("在席ステータス更新");
        CmnUsrInoutDao ioDao = new CmnUsrInoutDao(con);
        //update処理実行 更新できなければinsert処理を行う
        if (ioDao.update(model) < 1) {
            ioDao.insert(model);
        }
    }

    /**
     * <br>[機  能] 在席ステータスを更新する。
     * <br>[解  説] レコードが存在しない場合は追加を行う。
     * <br>[備  考]
     *
     * @param con コネクション
     * @param model 更新用のモデル
     * @throws SQLException SQL実行時例外
     */
    public void updateZskStatusOnly(Connection con, CmnUsrInoutModel model)
        throws SQLException {

        log__.debug("在席ステータス更新");
        CmnUsrInoutDao ioDao = new CmnUsrInoutDao(con);
        //update処理実行 更新できなければinsert処理を行う
        if (ioDao.updateStatusOnly(model) < 1) {
            ioDao.insert(model);
        }
    }

    /**
     * <br>[機  能] 在席ステータスリストラベルを作成する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 作成したラベル
     */
    public List<LabelValueBean> createZskUioStatusLabel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String zaiseki = gsMsg.getMessage("cmn.zaiseki");
        String fuzai = gsMsg.getMessage("cmn.absence");
        String other = gsMsg.getMessage("cmn.other");

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(
                new LabelValueBean(
                        zaiseki, String.valueOf(GSConst.UIOSTS_IN)));
        labelList.add(
                new LabelValueBean(
                        fuzai, String.valueOf(GSConst.UIOSTS_LEAVE)));
        labelList.add(
                new LabelValueBean(
                        other, String.valueOf(GSConst.UIOSTS_ETC)));

        return labelList;
    }

    /**
     * 座席表項目のKEYを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cnt 座席表内でユニークな連番
     * @return String
     */
    public String createElementKey(int cnt) {

        StringBuilder buf = new StringBuilder();
        buf.append(GSConstZaiseki.ELEMENT_KEY);
        buf.append(GSConstZaiseki.ELEMENT_SEPARATOR);
        buf.append(cnt);

        return buf.toString();
    }

    /**
     * 在席項目から指定した区分のSID配列を取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param indexList 在席項目リスト
     * @param kbn 抽出する区分
     * @param usrSid 除外するユーザSID
     * @return ArrayList in Integer
     */
    public ArrayList<Integer> getElementSids(
            ArrayList<ZaiIndexModel> indexList, int kbn, int usrSid) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (ZaiIndexModel model : indexList) {
            if (model.getZinLinkkbn() != kbn) {
                continue;
            }
            if (kbn == GSConstZaiseki.ELEMENT_KBN_USR && model.getZinLinksid() == usrSid) {
                continue;
            }
            ret.add(new Integer(model.getZinLinksid()));
        }
        return ret;
    }

    /**
     * リンク区分、リンクSIDを指定し表示名称を取得する。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn リンク区分
     * @param sid リンクSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return String
     */
    public UsrLabelValueBean getDspElementName(int kbn, int sid, Connection con)
    throws SQLException {
        UsrLabelValueBean ret = new UsrLabelValueBean("", "-1");
        switch (kbn) {
        case GSConstZaiseki.ELEMENT_KBN_USR:
            //ユーザ姓+名を取得
            CmnUsrmInfDao uDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel mdl = uDao.getUsersInf(sid);
            if (mdl != null) {
                return new UsrLabelValueBean(mdl);
            }
            break;
        case GSConstZaiseki.ELEMENT_KBN_RSV:
            //施設名称を取得
            RsvSisDataModel bean = new RsvSisDataModel();
            bean.setRsdSid(sid);
            RsvSisDataDao rDao = new RsvSisDataDao(con);
            RsvSisDataModel rMdl = rDao.select(bean);
            if (rMdl != null) {
                ret = new UsrLabelValueBean(rMdl.getRsdName(), "RSV" + String.valueOf(sid));
            }
            break;
        case GSConstZaiseki.ELEMENT_KBN_ETC:
            //空でリターン
            break;
        default:
            break;
        }
        return ret;
    }


    /**
     * 座席表SIDを指定し座席表情報を取得します
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param zaiSid 座席表SID
     * @param con コネクション
     * @throws SQLException 座席表情報の取得に失敗
     * @return ZaiInfoPlusModel
     */
    public ZaiInfoPlusModel getZaiInfoPlusModel(int zaiSid, Connection con)
    throws SQLException {
       ZaiInfoDao infoDao = new ZaiInfoDao(con);
       ZaiInfoPlusModel infoPlus = infoDao.getZaiInfoPlusModel(zaiSid);
       return infoPlus;
   }

    /**
     * セッションIDと座席表SIDを指定しワークテーブルから一時データを削除する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionId セッションID
     * @param zifSid 座席表SID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void deleteWkTable(String sessionId, int zifSid, Connection con)
    throws SQLException {
        WkZaiIndexDao wkDao = new WkZaiIndexDao();
        wkDao.deleteSessionWk(sessionId);
    }
    /**
     * 在席管理全般のログ出力を行う
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param type タイプ
     */
    public void outPutLog(
            Connection con,
            String opCode,
            String level,
            String value,
            String type) {
        outPutLog(con, opCode, level, value, type, null, GSConstZaiseki.ZSK_LOG_FLG_NONE);
    }
    /**
     * 在席管理全般のログ出力を行う
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param type タイプ
     * @param fileId 添付ファイルSID
     * @param logFlg ログ出力判別フラグ
     */
    public void outPutLog(
            Connection con,
            String opCode,
            String level,
            String value,
            String type,
            String fileId,
            int logFlg) {

        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.zaiseki.management");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstZaiseki.PLUGIN_ID_ZAISEKI);
        logMdl.setLogPluginName(msg);
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(type));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl__.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (logFlg == GSConstZaiseki.ZSK_LOG_FLG_DOWNLOAD) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con);
        String domain = reqMdl__.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * 在席管理ＡＰＩ全般全般のログ出力を行う
     * @param con コネクション
     * @param usid ユーザSID
     * @param pgId プログラムID
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutApiLog(
            Connection con,
            String usid,
            String pgId,
            String opCode,
            String level,
            String value) {

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.zaiseki.management");

        logMdl.setLogDate(now);
        logMdl.setUsrSid(NullDefault.getInt(usid, -1));
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstZaiseki.PLUGIN_ID_ZAISEKI);
        logMdl.setLogPluginName(msg);
        logMdl.setLogPgId(pgId);
        logMdl.setLogPgName(getPgName(pgId));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl__.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);
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
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String message = "";

        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.zsk.main.ZskMainAction")) {
            message = gsMsg.getMessage("zsk.64");
            return message;
        }

        if (id.equals("jp.groupsession.v2.zsk.zsk040kn.Zsk040knAction")) {
            message = gsMsg.getMessage("zsk.56");
            return message;
        }
        if (id.equals("jp.groupsession.v2.zsk.zsk050.Zsk050Action")) {
            message = gsMsg.getMessage("zsk.57");
            return message;
        }
        if (id.equals("jp.groupsession.v2.zsk.zsk050kn.Zsk050knAction")) {
            message = gsMsg.getMessage("zsk.58");
            return message;
        }

        if (id.equals("jp.groupsession.v2.zsk.zsk110kn.Zsk110knAction")) {
            message = gsMsg.getMessage("cmn.admin.setting") + " "
                        + gsMsg.getMessage("cmn.preferences.kn");
            return message;
        }

        if (id.equals("jp.groupsession.v2.zsk.zsk080kn.Zsk080knAction")) {
            message = gsMsg.getMessage("zsk.62");
            return message;
        }
        if (id.equals("jp.groupsession.v2.zsk.zsk090kn.Zsk090knAction")) {
            message = gsMsg.getMessage("zsk.63");
            return message;
        }
        if (id.equals("jp.groupsession.v2.zsk.zsk130kn.Zsk130knAction")) {
            message = String.format("%s %s%s",
                    gsMsg.getMessage("cmn.preferences2"),
                    gsMsg.getMessage("cmn.display.settings"),
                    gsMsg.getMessage("cmn.check"));
            return message;
        }
        if (id.equals("jp.groupsession.v2.zsk.zsk140.Zsk140Action")) {
            message = gsMsg.getMessage("cmn.admin.setting") + " "
                        + gsMsg.getMessage("cmn.default.setting");
            return message;
        }
        if (id.equals("jp.groupsession.v2.zsk.ptl020.ZskPtl020Action")) {
            message = gsMsg.getMessage("ptl.3") + " " + gsMsg.getMessage("zsk.ptl020.1");
            return message;
        }

        if (id.equals("jp.groupsession.v2.zsk.zsk011.Zsk011Action")) {
            message = gsMsg.getMessage("zsk.67");
            return message;
        }

        if (id.equals("jp.groupsession.v2.zsk.zsk011.Zsk011Action")) {
            message = gsMsg.getMessage("zsk.zsk011.03");
            return message;
        }

        if (id.equals("jp.groupsession.v2.api.zaiseki.edit.ApiZaisekiStatusEditAction")) {
            message = gsMsg.getMessage("zsk.65");
            return message;
        }

        if (id.equals("jp.groupsession.v2.zsk.zsk010.Zsk010Action")) {
            message = gsMsg.getMessage("zsk.20");
            return message;
        }
        return ret;
    }

    /**
     * <br>[機  能] 各ユーザが座席表メンバー表示を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditViewMember(Connection con) throws SQLException {

        ZaiAdmConfModel admMdl = getZaiAdminData(con);
        return admMdl == null || admMdl.getZacSortKbn() == GSConstZaiseki.ADM_SORTKBN_PRI;
    }

    /**
     * <br>[機  能] 指定したユーザのソート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return ソート情報
     * @throws SQLException SQL実行時例外
     */
    public ZskSortModel getSortData(Connection con, int userSid) throws SQLException {
        int sort1 = GSConstZaiseki.SORT_KEY_NAME;
        int sort2 = GSConstZaiseki.SORT_KEY_NAME;
        int order1 = GSConst.ORDER_KEY_ASC;
        int order2 = GSConst.ORDER_KEY_ASC;

        ZaiAdmConfModel admMdl = getZaiAdminData(con);
        int sortKbn = GSConstZaiseki.ADM_SORTKBN_PRI;
        if (admMdl != null) {
            sortKbn = admMdl.getZacSortKbn();
        }

        if (sortKbn == GSConstZaiseki.ADM_SORTKBN_ADM) {
            sort1 = admMdl.getZacSortKey1();
            sort2 = admMdl.getZacSortKey2();
            order1 = admMdl.getZacSortOrder1();
            order2 = admMdl.getZacSortOrder2();
        } else {
            ZaiPriConfDao priDao = new ZaiPriConfDao(con);
            ZaiPriConfModel priMdl = priDao.select(userSid);

            if (priMdl != null) {
                sort1 = priMdl.getZpcSortKey1();
                sort2 = priMdl.getZpcSortKey2();
                order1 = priMdl.getZpcSortOrder1();
                order2 = priMdl.getZpcSortOrder2();
            } else if (admMdl != null) {
                sort1 = admMdl.getZacSortKey1();
                sort2 = admMdl.getZacSortKey2();
                order1 = admMdl.getZacSortOrder1();
                order2 = admMdl.getZacSortOrder2();
            }
        }

        ZskSortModel sortMdl = new ZskSortModel();
        sortMdl.setSort1(sort1);
        sortMdl.setOrder1(order1);
        sortMdl.setSort2(sort2);
        sortMdl.setOrder2(order2);

        return sortMdl;
    }

    /**
     * <br>[機  能] 指定したバイナリSIDが座席表の画像かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSid バイナリSID
     * @return true:座席票のbinSid false:それ以外
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckZaiImage(Connection con, Long binSid) throws SQLException {

        ZaiInfoDao dao = new ZaiInfoDao(con);
        return dao.isCheckZaiImage(binSid);
    }

    /**
     * <br>[機  能] 在席管理管理者設定情報を取得する
     * <br>[解  説] 管理者設定が無かった場合はデフォルト値で作成する
     * <br>[備  考]
     * @param con コネクション
     * @return 在席管理管理者設定
     * @throws SQLException SQL実行時例外
     */
    public ZaiAdmConfModel getZaiAdminData(Connection con) throws SQLException {

        ZaiAdmConfDao dao = new ZaiAdmConfDao(con);
        ZaiAdmConfModel adminMdl = dao.getData();

        if (adminMdl == null) {
            adminMdl = new ZaiAdmConfModel();
            UDate now = new UDate();

            adminMdl.setZacNaisenKbn(1);
            adminMdl.setZacAid(0);
            adminMdl.setZacAdate(now);
            adminMdl.setZacEid(0);
            adminMdl.setZacEdate(now);
            adminMdl.setZacSortKbn(0);
            adminMdl.setZacSortKey1(3);
            adminMdl.setZacSortOrder1(0);
            adminMdl.setZacSortKey2(1);
            adminMdl.setZacSortOrder2(0);

            boolean commit = false;
            try {
                dao.insert(adminMdl);
                commit = true;
            } catch (SQLException e) {
                log__.error("在席管理-管理者設定の登録に失敗しました");
                throw e;
            } finally {
                if (commit) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        }

        return adminMdl;
    }

    /**
     * <br>[機  能]オペレーションログ内容部分の作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param status 1:在席 2:不在 3:その他
     * @param con Connection
     * @return 内容
     * @throws SQLException SQLException
     */
    public String opLogContent(int usrSid, int status, Connection con)
            throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        StringBuilder sbValue = new StringBuilder();
        List<Integer> usrList = new ArrayList<Integer>();
        usrList.add(usrSid);
        UserBiz uBiz = new UserBiz();
        List<CmnUsrmInfModel> uMdlList = uBiz.getUserList(con, usrList);
        String usrName = "";
        if (!(uMdlList == null || uMdlList.size() == 0)) {
            usrName = uMdlList.get(0).getUsiSei() + " " + uMdlList.get(0).getUsiMei();
        }

        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("cmn.target.user"));
        sbValue.append("]");
        sbValue.append(usrName);
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("zsk.20"));
        sbValue.append("]");
        if (status == 1) {
            sbValue.append(gsMsg.getMessage("cmn.zaiseki"));
        } else if (status == 2) {
            sbValue.append(gsMsg.getMessage("cmn.absence"));
        } else {
            sbValue.append(gsMsg.getMessage("cmn.other"));
        }
        return sbValue.toString();
    }
}
