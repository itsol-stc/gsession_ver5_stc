package jp.groupsession.v2.cht.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.dao.ChatDao;
import jp.groupsession.v2.cht.dao.ChtAdmConfDao;
import jp.groupsession.v2.cht.dao.ChtCategoryDao;
import jp.groupsession.v2.cht.dao.ChtGroupDataDao;
import jp.groupsession.v2.cht.dao.ChtGroupDataSumDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.dao.ChtGroupTargetDao;
import jp.groupsession.v2.cht.dao.ChtGroupViewDao;
import jp.groupsession.v2.cht.dao.ChtPriConfDao;
import jp.groupsession.v2.cht.dao.ChtUserDataDao;
import jp.groupsession.v2.cht.dao.ChtUserDataSumDao;
import jp.groupsession.v2.cht.dao.ChtUserViewDao;
import jp.groupsession.v2.cht.model.ChatDeleteModel;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cht.model.ChtPriConfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] チャットプラグインで使用される共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(ChtBiz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * */
    public ChtBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     * <p>コンストラクタ
     * @param con コネクション
     */
    public ChtBiz(Connection con) {
        con__ = con;
    }
    /**
     * <p>コンストラクタ
     * @param reqMdl リクエスト情報
     */
    public ChtBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>コンストラクタ
     */
    public ChtBiz() {

    }



    /**
     * <br>[機  能] チャット 管理者設定を取得する
     * <br>[解  説] チャットが未登録の場合、初期値を返す
     * <br>[備  考]
     * @return チャット 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public ChtAdmConfModel getChtAconf() throws SQLException {
        ChtAdmConfModel aconfMdl = null;
        ChtAdmConfDao aconfDao = new ChtAdmConfDao(con__);
        List<ChtAdmConfModel> aconfList = aconfDao.select();
        if (aconfList != null && !aconfList.isEmpty()) {
            aconfMdl = aconfList.get(0);
        } else {
            aconfMdl = new ChtAdmConfModel();
            aconfMdl.initData();
        }
        return aconfMdl;
    }

    /**
     * <br>[機  能] チャット 個人設定を取得する
     * <br>[解  説] チャットが未登録の場合、初期値を返す
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return チャット 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public ChtPriConfModel getChtUconf(int usrSid) throws SQLException {
        ChtPriConfModel uconfMdl = null;
        ChtPriConfDao uconfDao = new ChtPriConfDao(con__);
        ChtPriConfModel uconPriMdl = uconfDao.select(usrSid);
        if (uconPriMdl != null) {
            uconfMdl = uconPriMdl;
        } else {
            uconfMdl = new ChtPriConfModel();
            uconfMdl.initData(usrSid);
        }
        return uconfMdl;
    }

    /**
     * チャット全般のログ出力を行う
     * @param map マップ
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param reqMdl リクエスト情報
     * @param fileId 添付ファイルID
     */
    public void outPutLog(
            ActionMapping map,
            String opCode,
            String level,
            String value,
            RequestModel reqMdl,
            String fileId
            ) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cht.01");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstChat.PLUGIN_ID_CHAT);
        logMdl.setLogPluginName(msg);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType()));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl.getDomain();
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

        GsMessage gsMsg = new GsMessage();
        String logName = "";

        if (id.equals("jp.groupsession.v2.cht.cht010.Cht010Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht010");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht020.Cht020Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht020");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht030.Cht030Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht030");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht040.Cht040Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht040");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht040kn.Cht040knAction")) {
            logName = gsMsg.getMessage("cht.logmsg.cht040kn");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht050.Cht050Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht050");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht050kn.Cht050knAction")) {
            logName = gsMsg.getMessage("cht.logmsg.cht050kn");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht060.Cht060Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht060");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht060kn.Cht060knAction")) {
            logName = gsMsg.getMessage("cht.logmsg.cht060kn");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht070.Cht070Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht070");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht080.Cht080Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht080");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht090.Cht090Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht090");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht090kn.Cht090knAction")) {
            logName = gsMsg.getMessage("cht.logmsg.cht090kn");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht100.Cht100Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht100");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht110.Cht110Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht110");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht110kn.Cht110knAction")) {
            logName = gsMsg.getMessage("cht.logmsg.cht110kn");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht120.Cht120Action")) {
            logName = gsMsg.getMessage("cmn.preferences2") + " "
                        + gsMsg.getMessage("cmn.preferences");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht130.Cht130Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht130");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht140.Cht140Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht140");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht150.Cht150Action")) {
            logName = gsMsg.getMessage("cht.logmsg.cht150");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.cht.cht150kn.Cht150knAction")) {
            logName = gsMsg.getMessage("cht.logmsg.cht150kn");
            return logName;
        }
        return ret;
    }

    /**
     * <br>[機  能] 削除設定画面の年コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 年コンボ
     */
    public  List<LabelValueBean> createDelYearCombo() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //年ラベル
        ArrayList<LabelValueBean> yearCombo = new ArrayList<LabelValueBean>();

        for (int year: GSConst.DEL_YEAR_DATE) {
            yearCombo.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {String.valueOf(year)}),
                                    String.valueOf(year)));
        }
        return yearCombo;
    }

    /**
     * <br>[機  能] 削除設定画面の月コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 月コンボ
     */
    public  List<LabelValueBean> createDelMonthCombo() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ArrayList<LabelValueBean> monthCombo = new ArrayList<LabelValueBean>();
        for (int month: GSConst.DEL_MONTH_DATE) {
                monthCombo.add(new LabelValueBean(
                        gsMsg.getMessage("cmn.months", new String[] {String.valueOf(month)}),
                                                String.valueOf(month)));
        }

        return monthCombo;
    }
    /**
     * <br>[機  能] 指定した削除条件に従いチャットの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param delData 削除条件データ
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteChtData(ChatDeleteModel delData, int userSid)
    throws SQLException {

        // グループチャット:投稿削除
        deleteGrpChtData(delData, userSid);
        // ユーザチャット:投稿削除
        deleteUsrChtData(delData, userSid);

    }

    /**
     * <br>[機  能] 指定したグループチャット(関連情報含む)の削除を行う
     * <br>[解  説] チャット既読数更新処理のためチャットグループごとに処理を行う
     * <br>[備  考]
     * @param delData 削除条件データ
     * @param userSid 削除ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteGrpChtData(ChatDeleteModel delData, int userSid)
    throws SQLException {
        ChtGroupDataDao chtDao = new ChtGroupDataDao(con__);
        ChtGroupViewDao cgvDao = new ChtGroupViewDao(con__);
        ChtGroupDataSumDao cgsDao = new ChtGroupDataSumDao(con__);
        ChtUsedDataBiz usedDataBiz = new ChtUsedDataBiz(con__);

        Map<Integer, List<Long>> delChtMap = chtDao.getDelGroupChatSidList(delData);
        for (Entry<Integer, List<Long>> entry:delChtMap.entrySet()) {
            List<Long> delGrpChtList = entry.getValue();
            if (delGrpChtList.isEmpty()) {
                return;
            }

            //チャット情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            usedDataBiz.insertChtDataSize(delGrpChtList, ChtUsedDataBiz.TYPE_GROUP, false);

            UDate now = new UDate();
            //バイナリー情報の論理削除
            chtDao.removeBinData(delGrpChtList,  -1, userSid, now);
            //チャットの削除
            chtDao.deleteGroup(delGrpChtList);
            //既読情報の更新
            cgvDao.subtractViewCnt(entry.getKey(), delGrpChtList.size());
            //投稿集計情報の更新
            cgsDao.subtractCnt(entry.getKey(), delGrpChtList.size());
            boolean commit = false;
            try {
                con__.commit();
                commit = true;
            } catch (SQLException e) {
            } finally {
                if (!commit) {
                    con__.rollback();
                }
            }
        }

    }

    /**
     * <br>[機  能] 指定したユーザチャット(関連情報含む)の削除を行う
     * <br>[解  説] チャット既読数更新処理のためチャットペアごとに処理を行う
     * <br>[備  考]
     * @param delData 削除条件データ
     * @param userSid 削除ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteUsrChtData(ChatDeleteModel delData, int userSid)
    throws SQLException {

        ChtUserDataDao chtDao = new ChtUserDataDao(con__);
        ChtUserViewDao cuvDao = new ChtUserViewDao(con__);
        ChtUserDataSumDao cusDao = new ChtUserDataSumDao(con__);
        ChtUsedDataBiz usedDataBiz = new ChtUsedDataBiz(con__);

        Map<Integer, List<Long>> delChtMap = chtDao.getDelUserChatSidList(delData);
        for (Entry<Integer, List<Long>> entry:delChtMap.entrySet()) {
            List<Long> delUsrChtList = entry.getValue();
            if (delUsrChtList.isEmpty()) {
                continue;
            }

            //チャット情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            usedDataBiz.insertChtDataSize(delUsrChtList, ChtUsedDataBiz.TYPE_USER, false);

            UDate now = new UDate();
            //バイナリー情報の論理削除
            chtDao.removeBinData(delUsrChtList,  -1, userSid, now);
            //チャットの削除
            chtDao.deleteUser(delUsrChtList);
            //既読情報の更新
            cuvDao.subtractViewCnt(entry.getKey(), delUsrChtList.size());
            //投稿集計情報の更新
            cusDao.subtractCnt(entry.getKey(), delUsrChtList.size());
            boolean commit = false;
            try {
                con__.commit();
                commit = true;
            } catch (SQLException e) {
            } finally {
                if (!commit) {
                    con__.rollback();
                }
            }
        }

    }

    /**
     * <br>[機  能] カテゴリコンボを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param allFlg コンボに『全て』を追加するか
     * @throws SQLException SQL実行時例外
     * @return カテゴリコンボ
     */
    public List<LabelValueBean>  getCategory(boolean allFlg)
    throws SQLException {

        ChtCategoryDao chtDao = new ChtCategoryDao(con__);
        List<LabelValueBean> categoryList = new ArrayList<LabelValueBean>();
        // 追加項目 => [全て]
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (allFlg) {
        categoryList.add(new LabelValueBean(gsMsg.getMessage("cmn.all"),
                String.valueOf(GSConstChat.CHAT_CHC_SID_ALL)));
        }

        chtDao.getCategoryLabel(categoryList);
        return categoryList;
    }

    /**
     * <br>年コンボを生成します
     * チャット情報が最も古い年から現在の年までの値を取得します
     * @param year 基準年
     * @return ArrayList (in LabelValueBean)  年コンボ
     * @throws SQLException SQL例外
     */
    public ArrayList<LabelValueBean> getYearList(int year) throws SQLException {
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", "-1"));
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        UDate minDate = infDao.getMinDate();
        int minYear = year;
        if (minDate != null) {
            minYear = minDate.getYear();
        }
        for (int i = minYear; i <= year; i++) {
            labelList.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.year",
                                    new String[]{String.valueOf(i)}),
                                                String.valueOf(i)));
        }
        return labelList;
    }

    /**
     * <br>月コンボを生成します
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    public ArrayList<LabelValueBean> getMonthList() {
        int month = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", "-1"));
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msgMonth = gsMsg.getMessage("cmn.month");
        for (int i = 0; i < 12; i++) {
            labelList.add(
                    new LabelValueBean(month + msgMonth, String.valueOf(month)));
            month++;
        }
        return labelList;
    }

    /**
     * <br>日コンボを生成します
     * @return ArrayList (in LabelValueBean)  日コンボ
     */
    public ArrayList<LabelValueBean> getDayList() {
        int day = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("　", "-1"));
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msgDay = gsMsg.getMessage("cmn.day");
        for (int i = 0; i < 31; i++) {
            labelList.add(
                    new LabelValueBean(day + msgDay, String.valueOf(day)));
            day++;
        }
        return labelList;
    }
    /**
     * <br>グループIDを自動生成します
     * @return グループID
     * @throws SQLException SQLException
     */
    public String getAutoGeneratedId() throws SQLException {
        String ret = null;
        int id = -1;
        ChatDao dao = new ChatDao(con__);
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        id = dao.getSaibanCgiSid();
        int maxNum = infDao.getGroupCount();
        if (maxNum == 0) {
            ret = String.valueOf(id);
        }
        for (int i = 0; i <= maxNum; i++) {
            String chkStr = String.valueOf(id);
            if (!infDao.isExitGroupId(chkStr, -1)) {
                ret = chkStr;
                break;
            }
            id++;
        }
        return ret;
    }

    /**
     * <br>[機  能] チャットグループが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考] 全チャットグループから判定
     * @param sid グループSID
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existChtGroup(int sid) throws SQLException {
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        return infDao.isExitGroup(sid);
    }

    /**
     * <br>[機  能] グループチャットを論理削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param cgiSid パラメータ情報
     * @param usrSid ユーザSID
     * @throws Exception 例外
     */
    public void logicDeleteChtGroup(int cgiSid, int usrSid) throws Exception {
        log__.debug("deleteGroupChat");
        UDate now = new UDate();
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        infDao.updateGroupLocgicDelete(cgiSid, usrSid, now);
    }

    /**
     * <br>[機  能] グループ作成権限があるか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid グループSID
     * @param adminFlg システム管理者 or プラグイン管理者
     * @return true:ある false:ない
     * @throws SQLException SQL実行時例外
     */
    public boolean isCreateChtGroup(int usrSid, boolean adminFlg) throws SQLException {
        //管理者設定の取得
        ChtBiz chtBiz = new ChtBiz(con__);
        ChtAdmConfModel adminMdl = chtBiz.getChtAconf();

        // システム管理者又はグループ作成の制限をしない
        if (adminFlg || adminMdl.getCacGroupFlg() == GSConstChat.PERMIT_CREATE_GROUP) {
            return true;
        }
        int cnt = -1;
        ChtGroupTargetDao cgtDao = new ChtGroupTargetDao(con__);
        cnt = cgtDao.select(usrSid);
        if (adminMdl.getCacGroupKbn() == GSConstChat.TARGET_PERMIT) {
            if (cnt > 0) {
                return true;
            }
        } else {
            if (cnt <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能]Groupの編集時に存在しなくなったユーザを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cgiSid グループSID
     * @param usrSidList ユーザリスト
     * @return 削除件数
     * @throws SQLException sqlexception
     */
    public int groupViewDataDelete(int cgiSid, List<Integer>usrSidList)
            throws SQLException {
        int ret = 0;

        ChtGroupViewDao cgvDao = new ChtGroupViewDao(con__);
        ret = cgvDao.deleteUser(cgiSid, usrSidList);

        return ret;
    }

    /**
     * メンバーSIDからユーザSIDを生成
     * @param adminMembers 管理者メンバーSID
     * @param generalMembers 一般メンバーSID
     * @return ユーザSID
     * @throws SQLException SQL実行例外
     * */
    public String[] createMemberUserSid(
            String[] adminMembers,
            String[] generalMembers) throws SQLException {
        // メンバーユーザSID
        List<String> memberUsers = new ArrayList<String>();


        // 管理者メンバーおよび一般メンバーを取得
        // メンバーにグループが指定されている場合、グループSIDを取得
        List<String> groupSidList = new ArrayList<String>();
        for (String admin : adminMembers) {
            if (admin.startsWith("G")) {
                groupSidList.add(admin.replace("G", ""));
            } else {
                memberUsers.add(admin);
            }
        }
        for (String general : generalMembers) {
            if (general.startsWith("G")) {
                groupSidList.add(general.replace("G", ""));
            } else {
                memberUsers.add(general);
            }
        }
        // 選択されたグループの所属ユーザの中で重複のないようメンバーを取得する
        if (groupSidList != null && groupSidList.size() > 0) {
            CmnBelongmDao belongDao = new CmnBelongmDao(con__);
            String[] groupSids = groupSidList.toArray(new String[groupSidList.size()]);
            List<String> belongUserSidList = belongDao.select(groupSids);
            for (String userSid : belongUserSidList) {
                if (!memberUsers.contains(userSid) && Integer.parseInt(userSid) > 100) {
                    memberUsers.add(userSid);
                }
            }
        }
        return memberUsers.toArray(new String[memberUsers.size()]);
    }


}
