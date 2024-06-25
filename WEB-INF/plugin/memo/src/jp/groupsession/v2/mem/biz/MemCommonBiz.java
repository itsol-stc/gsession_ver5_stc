package jp.groupsession.v2.mem.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.dao.MemoAdmConfDao;
import jp.groupsession.v2.mem.dao.MemoBatchDao;
import jp.groupsession.v2.mem.dao.MemoBelongLabelDao;
import jp.groupsession.v2.mem.dao.MemoBinDao;
import jp.groupsession.v2.mem.dao.MemoDataDao;
import jp.groupsession.v2.mem.dao.MemoDataPeriodDao;
import jp.groupsession.v2.mem.dao.MemoDatausedSumDao;
import jp.groupsession.v2.mem.model.MemoAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メモプラグインに関する共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MemCommonBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemCommonBiz.class);

    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /** コネクション */
    private Connection con__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public MemCommonBiz() {
    }
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public MemCommonBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     */
    public MemCommonBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] メモ管理者設定を取得し、取得できない場合はデフォルト値を返します
     * <br>[解  説]
     * <br>[備  考] DBから管理者設定情報が取得できない場合に使用
     * @param con DBコネクション
     * @return メモ管理者設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public MemoAdmConfModel getAdmConfModel(Connection con) throws SQLException {
        //DBより現在の設定を取得する。
        MemoAdmConfDao dao = new MemoAdmConfDao(con);
        MemoAdmConfModel conf = dao.select();
        if (conf == null) {
            //データがない場合
            conf = new MemoAdmConfModel();
            //自動削除
            conf.setMacAtdelKbn(GSConstMemo.AUTO_DELETE_KBN_OFF);
            conf.setMacAtdelY(-1);
            conf.setMacAtdelM(-1);
        }
        log__.debug(conf.toCsvString());
        return conf;
    }

    /**
     * <br>[機  能] 基準日を指定し、更新日時 <= 基準日であるメモデータを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param bdate 基準日
     * @throws SQLException SQL実行エラー
     */
    public void deleteOldMemo(Connection con, UDate bdate) throws SQLException {

        MemoDataDao mmdDao = new MemoDataDao(con);

        //削除するメモSIDを取得する。
        List<Long> allDelList = mmdDao.getDelMemoList(bdate);

        deleteMemo(con, allDelList);
    }

    /**
     * <br>[機  能] 削除期限 <= 現在の日付であるメモデータを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param tdate 本日の日付
     * @throws SQLException SQL実行エラー
     */
    public void deletePeriodMemo(Connection con, UDate tdate) throws SQLException {

        MemoDataPeriodDao mdpDao = new MemoDataPeriodDao(con);

        //削除するメモSIDを取得する。
        List<Long> allDelList = mdpDao.getPeriodMemoList(tdate);

        deleteMemo(con, allDelList);
    }

    /**
     * <br>[機  能] 削除対象のメモを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param allDelList 削除対象のメモSID一覧
     * @throws SQLException SQL実行エラー
     */
    public void deleteMemo(Connection con, List<Long> allDelList) throws SQLException {

        MemoDataDao mmdDao = new MemoDataDao(con);
        MemoBinDao mmbDao = new MemoBinDao(con);
        MemoDataPeriodDao mdpDao = new MemoDataPeriodDao(con);
        MemoBelongLabelDao mblDao = new MemoBelongLabelDao(con);
        MemoBatchDao bchDao = new MemoBatchDao(con);
        MemoDatausedSumDao musDao = new MemoDatausedSumDao(con);
        if (allDelList == null || allDelList.size() < 1) {
            return;
        }

        //メモを何度かに分けて削除する。
        int i = 0;
        ArrayList<Long> delList = new ArrayList<Long>();

        for (long memSid : allDelList) {
            delList.add(memSid);
            i++;

            if (i >= GSConstMemo.MEM_BATCH_DELETE_COUNT) {
                //削除する。
                musDao.insertDelDiff(delList);
                bchDao.deleteMemoBin(delList);
                mmdDao.delete(delList);
                mmbDao.delete(delList);
                mdpDao.delete(delList);
                mblDao.delete(delList);
                delList = new ArrayList<Long>();


                i = 0;
            }
        }
        deleteRemainMemo(mmdDao, mmbDao, mdpDao, mblDao, bchDao, musDao, delList);
    }

    /**
     * <br>[機  能] 削除対象のメモを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param mmdDao メモ情報Dao
     * @param mmbDao メモ添付ファイルDao
     * @param mdpDao メモ削除期限情報Dao
     * @param mblDao メモラベル付与情報Dao
     * @param bchDao バッチ処理Dao
     * @param musDao データ使用量集計Dao
     * @param delList 削除対象メモリスト
     * @throws SQLException SQL実行エラー
     */
    public void deleteRemainMemo(
            MemoDataDao mmdDao, MemoBinDao mmbDao,
            MemoDataPeriodDao mdpDao, MemoBelongLabelDao mblDao,
            MemoBatchDao bchDao, MemoDatausedSumDao musDao,
            ArrayList<Long> delList) throws SQLException {

        if (delList != null && delList.size() > 0) {
            //削除する。
            musDao.insertDelDiff(delList);
            bchDao.deleteMemoBin(delList);
            mmdDao.delete(delList);
            mmbDao.delete(delList);
            mdpDao.delete(delList);
            mblDao.delete(delList);
        }
    }

    /**
     * <br>[機  能] メモ全般のログ出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            String opCode,
            String level,
            String value) {
        outPutLog(map, req, opCode, level, value, null);
    }

    /**
     * <br>[機  能] メモ全般のログ出力を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param req リクエスト
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param logCode ログコード
     */
    public void outPutLog(
            ActionMapping map,
            HttpServletRequest req,
            String opCode,
            String level,
            String value,
            String logCode) {

        HttpSession session = req.getSession();
        BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }
        GsMessage gsMsg = new GsMessage();
        /** メッセージ メモ **/
        String memo = gsMsg.getMessage(req, "memo.01");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstMemo.PLUGIN_ID_MEMO);
        logMdl.setLogPluginName(memo);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType()));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(CommonBiz.getRemoteAddr(req));
        logMdl.setVerVersion(GSConst.VERSION);
        if (logCode != null) {
            logMdl.setLogCode(logCode);
        }
        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl__.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * <br>[機  能] プログラムIDからプログラム名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param id アクションID
     * @return プログラム名称
     */
    public String getPgName(String id) {
        String ret = new String();
        if (StringUtil.isNullZeroString(id)) {
            return ret;
        }
        GsMessage gsMsg = new GsMessage(reqMdl__);
        log__.info("プログラムID==>" + id);
        if (id.equals("jp.groupsession.v2.mem.mem010.Mem010Action")) {
            return gsMsg.getMessage("memo.mem010.1");
        }
        if (id.equals("jp.groupsession.v2.mem.mem020.Mem020Action")) {
            return gsMsg.getMessage("memo.mem020.1");
        }
        if (id.equals("jp.groupsession.v2.mem.mem030.Mem030Action")) {
            return gsMsg.getMessage("memo.mem030.1");
        }
        if (id.equals("jp.groupsession.v2.mem.mem040.Mem040Action")) {
            return gsMsg.getMessage("memo.mem040.1");
        }
        if (id.equals("jp.groupsession.v2.mem.mem050.Mem050Action")) {
            return gsMsg.getMessage("memo.mem050.1");
        }
        if (id.equals("jp.groupsession.v2.mem.mem060.Mem060Action")) {
            return gsMsg.getMessage("memo.mem060.1");
        }
        if (id.equals("jp.groupsession.v2.mem.mem060kn.Mem060knAction")) {
            return gsMsg.getMessage("memo.mem060kn.1");
        }
        return ret;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl, String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl,
                GSConstMemo.PLUGIN_ID_MEMO,
                dirId);
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     */
    public void deleteTempDir(RequestModel reqMdl, String dirId) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(reqMdl,
                GSConstMemo.PLUGIN_ID_MEMO,
                dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void createTempDir(RequestModel reqMdl, String dirId)
            throws IOToolsException {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.createTempDir(reqMdl,
                GSConstMemo.PLUGIN_ID_MEMO,
                dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリの初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param dirId ディレクトリID
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir(RequestModel reqMdl, String dirId)
            throws IOToolsException {
        //テンポラリディレクトリの削除後、再作成
        deleteTempDir(reqMdl, dirId);
        createTempDir(reqMdl, dirId);
    }

    /**
     * <br>[機  能] 長整数型配列を整数型リストに変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param array 長整数型配列
     * @return 長整数型リスト
     */
    public List<Long> arrayToList(long[] array) {
        List<Long> ret = new ArrayList<Long>();
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                ret.add(array[i]);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 長整数型リストを整数型配列に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param list 長整数型リスト
     * @return ret 長整数型配列
     */
    public long[] listToArray(List<Long> list) {
        long[] ret = new long[list.size()];
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                ret[i] = list.get(i);
            }
        }
        return ret;
    }
}