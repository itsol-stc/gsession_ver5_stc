package jp.groupsession.v2.mem.mem010;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.biz.MemCommonBiz;
import jp.groupsession.v2.mem.dao.MemoBelongLabelDao;
import jp.groupsession.v2.mem.dao.MemoBinDao;
import jp.groupsession.v2.mem.dao.MemoDataDao;
import jp.groupsession.v2.mem.dao.MemoDataPeriodDao;
import jp.groupsession.v2.mem.dao.MemoDatausedSumDao;
import jp.groupsession.v2.mem.dao.MemoLabelDao;
import jp.groupsession.v2.mem.dao.MemoPriConfDao;
import jp.groupsession.v2.mem.model.MemoBelongLabelModel;
import jp.groupsession.v2.mem.model.MemoBinModel;
import jp.groupsession.v2.mem.model.MemoDataModel;
import jp.groupsession.v2.mem.model.MemoDataPeriodModel;
import jp.groupsession.v2.mem.model.MemoLabelModel;
import jp.groupsession.v2.mem.model.MemoPriConfModel;

/**
 * <br>[機  能] メモ メモ画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem010Biz {

    /** コネクション */
    private Connection con__;

    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエストモデル
     */
    public Mem010Biz(Connection con, RequestModel reqMdl) {
        this.con__ = con;
        this.reqMdl__ = reqMdl;
    }

    /**
     * <br>[機 能]初期表示設定を行う
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    protected void _setInitData(Mem010ParamModel paramMdl) throws SQLException {
        _setMemoData(paramMdl);
        __setLabelList(paramMdl);
    }

    /**
     * <br>[機 能]ユーザが作成したラベル一覧を取得し、パラメータ情報に設定する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    private void __setLabelList(Mem010ParamModel paramMdl) throws SQLException {

        int usrSid = reqMdl__.getSmodel().getUsrsid();
        // ラベルコンボボックスに表示するラベルの設定
        MemoLabelDao labelDao = new MemoLabelDao(con__);
        paramMdl.setLabelList(labelDao.getLabelList(usrSid));
    }

    /**
     * <br>[機 能]指定された条件に従って取得したメモをパラメータ情報に設定する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    protected void _setMemoData(Mem010ParamModel paramMdl) throws SQLException {

        int usrSid = reqMdl__.getSmodel().getUsrsid();
        List<Long> targetSidList = new ArrayList<Long>();

        //メモ一覧の取得
        Mem010Dao memDao = new Mem010Dao(con__);
        Mem010SearchModel searchMdl = __getMem010SearchModel(paramMdl, usrSid);
        paramMdl.setMemList(memDao.select(searchMdl, targetSidList));

        //ラベル一覧の取得
        MemoLabelDao labelDao = new MemoLabelDao(con__);
        Map<Long, String> map = labelDao.getMemoLabelList(usrSid, targetSidList);

        //添付ファイル有無の取得
        MemoBinDao mmbDao = new MemoBinDao(con__);
        List<Long> memBinList = mmbDao.getMemoBinList(targetSidList);

        for (Mem010DisplayModel model : paramMdl.getMemList()) {
            if (map.get(model.getMemSid()) != null) {
                model.setMmlName(map.get(model.getMemSid()));
            }
            if (memBinList.contains(model.getMemSid())) {
                model.setMemBin(GSConstMemo.TENPU_KBN_YES);
            } else {
                model.setMemBin(GSConstMemo.TENPU_KBN_NONE);
            }
        }

    }

    /**
     * <br>[機 能]ラベルを選択し、メモに設定する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    protected void _setLabelData(Mem010ParamModel paramMdl) throws SQLException {

        int usrSid = reqMdl__.getSmodel().getUsrsid();
        MemoLabelDao dao = new MemoLabelDao(con__);
        MemoLabelModel model = null;
        int labelSid = paramMdl.getMem010targetLabelSid();
        model = dao.selectLabelData(usrSid, labelSid);
        paramMdl.setAddLabelModel(model);
    }

    /**
     * <br>[機 能]ラベルを登録し、メモに追加する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    protected void _insertLabelData(
            Mem010ParamModel paramMdl,
            MlCountMtController cntCon) throws SQLException {

        int usrSid = reqMdl__.getSmodel().getUsrsid();
        MemoLabelDao dao = new MemoLabelDao(con__);

        // 採番番号の取得
        int mmlSid = 0;
        String sbnSid = GSConstMemo.SBN_SID;
        String sbnSidSub = GSConstMemo.SBN_SID_SUB;
        mmlSid = (int) cntCon.getSaibanNumber(sbnSid, sbnSidSub, usrSid);

        int maxSort = dao.getMaxSort(usrSid);

        //モデルの取得
        MemoLabelModel mmlMdl = __getMemoLabelModel(paramMdl, usrSid, maxSort, mmlSid);

        // ラベルの登録
        dao.insert(mmlMdl);

        paramMdl.setAddLabelModel(mmlMdl);
    }

    /**
     * <br>[機 能]メモを登録する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @param cntCon 採番コントローラ
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    protected void _insertMemoData(
            Mem010ParamModel paramMdl,
            MlCountMtController cntCon,
            String appRootPath) throws SQLException, IOToolsException, TempFileException {

        //登録情報の取得
        int usrSid = reqMdl__.getSmodel().getUsrsid();
        UDate now = new UDate();

        // 採番番号の取得
        long memSid = 0;
        String sbnSid = GSConstMemo.SBN_SID;
        String sbnSidSub = GSConstMemo.SBN_SID;
        memSid = cntCon.getSaibanNumber(sbnSid, sbnSidSub, usrSid);

        //メモ件数の取得
        MemoDataDao memDao = new MemoDataDao(con__);
        Mem010SearchModel searchMdl = __getMem010SearchModel(paramMdl, usrSid);
        int count = memDao.getMemoCount(searchMdl);

        // メモの登録
        MemoDataModel mmdMdl = __getMemoDataModel(paramMdl, usrSid, now, memSid);
        memDao.insert(mmdMdl);

        __insertMemoAttach(paramMdl, cntCon, appRootPath, count, memSid);

        MemoDatausedSumDao musDao = new MemoDatausedSumDao(con__);
        musDao.insertAddDiff(Arrays.asList(memSid));


    }

    /**
     * <br>[機 能]メモの付属情報を登録する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @param cntCon 採番コントローラ
     * @param appRootPath アプリケーションルートパス
     * @param count DB内データ数
     * @param memSid 登録，更新するメモのSID
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __insertMemoAttach(
            Mem010ParamModel paramMdl,
            MlCountMtController cntCon,
            String appRootPath,
            int count,
            long memSid) throws SQLException, TempFileException {

        int usrSid = reqMdl__.getSmodel().getUsrsid();
        UDate now = new UDate();

        // テンポラリディレクトリパスを取得
        String tempDir = _getTempDir();

        // 添付ファイルを登録
        CommonBiz biz = new CommonBiz();
        List<String> binList = biz.insertBinInfo(con__, tempDir, appRootPath, cntCon, usrSid, now);

        // メモにファイルの登録
        MemoBinDao binDao = new MemoBinDao(con__);
        for (String strBinSid : binList) {
            MemoBinModel binMdl = __getMemoBinModel(memSid, strBinSid);
            binDao.insert(binMdl);
        }

        // メモにラベルの登録
        MemoBelongLabelDao mmlDao = new MemoBelongLabelDao(con__);
        int[] memLabel = paramMdl.getMem010Label();
        if (memLabel != null && memLabel.length > 0) {
            for (int mmlSid : paramMdl.getMem010Label()) {
                MemoBelongLabelModel mblMdl = __getMemoBelongLabelModel(memSid, mmlSid);
                mmlDao.insert(mblMdl);
            }
        }

        // メモに自動削除日付の登録
        if (paramMdl.getMem010AtdelFlg() == 1) {
            MemoDataPeriodDao mdpDao = new MemoDataPeriodDao(con__);
            UDate atdelDate = UDate.getInstanceStr(paramMdl.getMem010AtdelDate());
            MemoDataPeriodModel mdpMdl = __getMemoDataPeriodModel(memSid, atdelDate);
            mdpDao.insert(mdpMdl);
        }

        boolean flg = __getInsertFlg(paramMdl, usrSid, memSid);

        if (flg && (paramMdl.getMem010SvSort() == GSConst.ORDER_KEY_DESC
                    || (paramMdl.getMem010SvSort() == GSConst.ORDER_KEY_ASC
                        && count <= paramMdl.getMem010MemoCount()))) {

            //メモ一覧に表示するメモ情報の取得
            Mem010DisplayModel dspMdl = __getMem010DisplayModel(paramMdl, memSid, now, binList);

            List<Mem010DisplayModel> dspList = new ArrayList<Mem010DisplayModel>();
            dspList.add(dspMdl);
            paramMdl.setMemList(dspList);
        }
    }

    /**
     * <br>[機  能] メモ一覧にメモを追加するかの判定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid セッションユーザSID
     * @param memSid メモSID
     * @return true:メモ一覧に追加する false:メモ一覧に追加しない
     * @throws SQLException SQL実行例外
     */
    private boolean __getInsertFlg(
            Mem010ParamModel paramMdl,
            int usrSid,
            long memSid) throws SQLException {

        boolean ret = false;
        Mem010Dao memDao = new Mem010Dao(con__);
        Mem010SearchModel searchMdl = __getMem010SearchModel(paramMdl, usrSid);
        ret = memDao.isMatchRequirement(searchMdl, memSid);

        return ret;
    }

    /**
     * <br>[機 能]メモの明細表示データを設定する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @param appRootPath アプリケーションルートパス
     * @param domain ドメイン
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    protected void _setMemoDetail(
            Mem010ParamModel paramMdl,
            String appRootPath,
            String domain,
            RequestModel reqMdl)
                    throws SQLException, IOToolsException, IOException, TempFileException {

        int usrSid = reqMdl__.getSmodel().getUsrsid();
        Mem010Dao dao = new Mem010Dao(con__);
        long memSid = paramMdl.getMem010TargetMemoSid();

        //メモ情報の取得
        Mem010DisplayModel model = dao.getMemoMeisai(usrSid, memSid);
        List<Mem010DisplayModel> disList = new ArrayList<Mem010DisplayModel>();
        disList.add(model);

        // ラベルリストの取得
        List<MemoLabelModel> mmlModelList = new ArrayList<MemoLabelModel>();
        mmlModelList = dao.getMemoMeisaiLabel(usrSid, memSid);

        // 添付ファイルリストの取得
        List<CmnBinfModel> binList = dao.getMemoMeisaiTenpu(usrSid, memSid);

        // テンポラリディレクトリの初期化とファイルの配置
        MemCommonBiz cmnBiz = new MemCommonBiz();
        cmnBiz.clearTempDir(reqMdl, GSConstMemo.DIRID_MEM010);
        String tempDir = _getTempDir();
        __tempFileCopy(binList, appRootPath, tempDir, con__, domain);

        paramMdl.setMemList(disList);
        paramMdl.setLabelList(mmlModelList);
        __setTempFiles(paramMdl);
    }

    /**
     * <br>[機 能]メモとメモに付随する情報を削除する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _deleteMemoData(Mem010ParamModel paramMdl) throws SQLException {
        long[] delArray = paramMdl.getMem010DeleteMemoSidArray();
        MemCommonBiz memCmnBiz = new MemCommonBiz(con__, reqMdl__);
        List<Long> delSidList = memCmnBiz.arrayToList(delArray);
        //メモ情報の取得
        memCmnBiz.deleteMemo(con__, delSidList);
    }

    /**
     * <br>[機 能]メモとメモに付随する情報を更新する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @param cntCon 採番コントローラ
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    protected void _updateMemoData(
            Mem010ParamModel paramMdl,
            int usrSid,
            Connection con,
            MlCountMtController cntCon,
            String appRootPath) throws SQLException, TempFileException, IOToolsException {

        long targetMemoSid = paramMdl.getMem010TargetMemoSid();

        //メモ件数の取得
        MemoDataDao memDao = new MemoDataDao(con__);
        Mem010SearchModel searchMdl = __getMem010SearchModel(paramMdl, usrSid);
        int count = memDao.getMemoCount(searchMdl);

        // メモの更新
        UDate now = new UDate();
        MemoDataModel mmdMdl = __getMemoDataModel(paramMdl, usrSid, now, targetMemoSid);
        Mem010Dao mem010Dao = new Mem010Dao(con);
        MemoDatausedSumDao musDao = new MemoDatausedSumDao(con);

        musDao.insertDelDiff(Arrays.asList(targetMemoSid));

        mem010Dao.updateMemoData(mmdMdl);

        // メモの付属情報の削除
        mem010Dao.deleteCmnBinData(usrSid, targetMemoSid);
        mem010Dao.deleteMemoBinData(usrSid, targetMemoSid);
        mem010Dao.deleteMemoLabelData(usrSid, targetMemoSid);
        mem010Dao.deleteMemoPeriodData(usrSid, targetMemoSid);

        // メモの付属情報の登録
        __insertMemoAttach(paramMdl, cntCon, appRootPath, count, targetMemoSid);


        musDao.insertAddDiff(Arrays.asList(targetMemoSid));


        // テンポラリディレクトリからファイルの削除
        MemCommonBiz memCmnBiz = new MemCommonBiz();
        memCmnBiz.clearTempDir(reqMdl__, GSConstMemo.DIRID_MEM010);
    }

    /**
     * <br>[機  能] メモ画面の表示状態を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 表示状態
     * @throws SQLException SQL実行例外
     */
    protected int _getDisplayMode(Connection con) throws SQLException {

        int usrSid = reqMdl__.getSmodel().getUsrsid();
        MemoPriConfDao mpcDao = new MemoPriConfDao(con__);
        MemoPriConfModel mpcMdl = mpcDao.select(usrSid);
        int ret;
        if (mpcMdl == null) {
            ret = 0;
        } else {
            ret = mpcMdl.getMpcDspMode();
        }

        return ret;
    }

    /**
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @param mode 表示モード
     * @throws SQLException SQL実行例外
     */
    protected void _saveDisplayMode(int usrSid, int mode) throws SQLException {
        MemoPriConfDao mpcDao = new MemoPriConfDao(con__);
        MemoPriConfModel mpcMdl = __getMemoPriConfModel(usrSid, mode);

        if (mpcDao.updateDspMode(mpcMdl) == 0) {
            mpcDao.insert(mpcMdl);
        }
    }

    /**
     * <br>[機  能] 検索条件に従っているメモの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid セッションユーザSID
     * @return 検索条件に従っているメモ件数
     * @throws SQLException SQL実行例外
     */
    protected int _getMemoCount(Mem010ParamModel paramMdl, int usrSid) throws SQLException {

        int ret = 0;

        MemoDataDao mmdDao = new MemoDataDao(con__);

        Mem010SearchModel searchMdl = __getMem010SearchModel(paramMdl, usrSid);
        ret = mmdDao.getMemoCount(searchMdl);

        return ret;
    }

    /**
     * <br>[機 能] メモの存在チェックを行う
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param targetSidAry 存在を確認するメモSIDの配列
     * @return 存在するメモのSIDリスト
     * @throws SQLException SQL実行例外
     */
    protected List<Long> _getExistSidList(
            int usrSid,
            long[] targetSidAry) throws SQLException {

        List<Long> ret = null;

        List<Long> targetSidList = new ArrayList<Long>();
        if (targetSidAry != null) {
            for (long memSid : targetSidAry) {
                targetSidList.add(memSid);
            }
        }
        Mem010Dao dao = new Mem010Dao(con__);
        ret = dao.getExistMemoSid(usrSid, targetSidList);

        return ret;
    }

    /**
     * <br>[機 能] Mem010SearchModelオブジェクトを取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @param usrSid セッションユーザSID
     * @return Mem010SearchModelオブジェクト
     */
    private Mem010SearchModel __getMem010SearchModel(
            Mem010ParamModel paramMdl,
            int usrSid) {

        String searchNaiyo = NullDefault.getString(paramMdl.getMem010SvSearchNaiyo(), "");
        String dateFr = NullDefault.getString(paramMdl.getMem010SvSearchDateFr(), "");
        String dateTo = NullDefault.getString(paramMdl.getMem010SvSearchDateTo(), "");
        Mem010SearchModel searchMdl = new Mem010SearchModel();
        searchMdl.setUsrSid(usrSid);

        if (searchNaiyo.length() > 0) {
            searchMdl.setMmdContent(searchNaiyo);
        }
        if (dateFr.length() > 0) {
            UDate udateFr = UDate.getInstanceStr(dateFr);
            udateFr.setZeroHhMmSs();
            searchMdl.setDateFr(udateFr);
        }
        if (dateTo.length() > 0) {
            UDate udateTo = UDate.getInstanceStr(dateTo);
            udateTo.setMaxHhMmSs();
            searchMdl.setDateTo(udateTo);
        }
        searchMdl.setMmlSid(paramMdl.getMem010SvSearchLabel());
        searchMdl.setSortOrder(paramMdl.getMem010SvSort());
        searchMdl.setTenpu(paramMdl.getMem010SvSearchTenpu());
        searchMdl.setHyouziKensu(paramMdl.getMem010MemoCount());
        return searchMdl;
    }

    /**
     * <br>[機 能]MemoDataModelオブジェクトを取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @param usrSid セッションユーザSID
     * @param now Udateオブジェクト
     * @param memSid メモSID
     * @return MemoDataModelオブジェクト
     */
    private MemoDataModel __getMemoDataModel(
            Mem010ParamModel paramMdl,
            int usrSid,
            UDate now,
            long memSid) {

        MemoDataModel mmdMdl = new MemoDataModel();
        mmdMdl.setMemSid(memSid);
        mmdMdl.setMmdContent(paramMdl.getMem010Naiyo());
        mmdMdl.setUsrSid(usrSid);
        mmdMdl.setMmdAuid(usrSid);
        mmdMdl.setMmdEuid(usrSid);
        mmdMdl.setMmdAdate(now);
        mmdMdl.setMmdEdate(now);
        mmdMdl.setMmdDelConf(paramMdl.getMem010AtdelFlg());
        return mmdMdl;
    }


    /**
     * <br>[機  能] MemoLabelModelオブジェクトを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid セッションユーザSID
     * @param maxSort DBに登録されているソート順の最大値
     * @param mmlSid メモラベルSID
     * @return MemoLabelModelオブジェクト
     */
    private MemoLabelModel __getMemoLabelModel(
            Mem010ParamModel paramMdl,
            int usrSid,
            int maxSort,
            int mmlSid) {

        // モデルの設定
        MemoLabelModel mmlMdl = new MemoLabelModel();
        mmlMdl.setMmlSid(mmlSid);
        mmlMdl.setMmlName(paramMdl.getMem010addLabelName());
        mmlMdl.setUsrSid(usrSid);
        mmlMdl.setMmlAuid(usrSid);
        mmlMdl.setMmlSort(maxSort + 1);
        UDate now = new UDate();
        mmlMdl.setMmlAdate(now);
        mmlMdl.setMmlEuid(usrSid);
        mmlMdl.setMmlEdate(now);
        return mmlMdl;
    }

    /**
     * <br>[機 能] MemoBinModelオブジェクトを取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param memSid メモSID
     * @param strBinSid バイナリSID
     * @return MemoBinModelオブジェクト
     */
    private MemoBinModel __getMemoBinModel(long memSid, String strBinSid) {
        MemoBinModel binMdl = new MemoBinModel();
        binMdl.setBinSid(Long.parseLong(strBinSid));
        binMdl.setMemSid(memSid);
        return binMdl;
    }

    /**
     * <br>[機 能]MemoBelongLabelModelオブジェクトを取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param memSid メモSID
     * @param mmlSid メモラベルSID
     * @return MemoBelongLabelModelオブジェクト
     */
    private MemoBelongLabelModel __getMemoBelongLabelModel(long memSid, int mmlSid) {
        MemoBelongLabelModel mblMdl = new MemoBelongLabelModel();
        mblMdl.setMemSid(memSid);
        mblMdl.setMmlSid(mmlSid);
        return mblMdl;
    }

    /**
     * <br>[機 能] MemoDataPeriodModelオブジェクトを取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param memSid メモSID
     * @param atdelDate UDateオブジェクト
     * @return MemoDataPeriodModelオブジェクト
     */
    private MemoDataPeriodModel __getMemoDataPeriodModel(long memSid, UDate atdelDate) {
        MemoDataPeriodModel mdpMdl = new MemoDataPeriodModel();
        mdpMdl.setMemSid(memSid);
        mdpMdl.setMdpDelDate(atdelDate);
        return mdpMdl;
    }

    /**
     * <br>[機 能] Mem010DisplayModelオブジェクトを取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param paramMdl パラメータ情報
     * @param memSid メモSID
     * @param now UDateオブジェクト
     * @param binList 添付ファイル名リスト
     * @return Mem010DisplayModelオブジェクト
     */
    private Mem010DisplayModel __getMem010DisplayModel(
            Mem010ParamModel paramMdl,
            long memSid,
            UDate now,
            List<String> binList) {

        //メモ一覧に追加するメモ情報の設定
        Mem010DisplayModel dspMdl = new Mem010DisplayModel();
        dspMdl.setMemSid(memSid);

        //更新日の設定
        String mmdEdate = now.getStrMonth() + "/" + now.getStrDay();
        dspMdl.setMmdEdate(mmdEdate);

        //内容の設定
        String naiyo = paramMdl.getMem010Naiyo();
        String[] splitStr = naiyo.split("\n");
        naiyo = splitStr[0];
        naiyo = naiyo.replaceAll("[\n]+", "");

        dspMdl.setMmdContent(naiyo);

        if (!binList.isEmpty()) {
            dspMdl.setMemBin(GSConstMemo.TENPU_FILE_YES);
        }

        //ラベル名の設定
        String[] labelName = paramMdl.getMem010LabelName();
        if (labelName != null && labelName.length > 0) {
            String mmlName = "";
            for (int i = 0; i < paramMdl.getMem010LabelName().length; i++) {
                mmlName += paramMdl.getMem010LabelName()[i];
                if (i < paramMdl.getMem010LabelName().length - 1) {
                    mmlName += ",";
                }
            }
            dspMdl.setMmlName(mmlName);
        }
        return dspMdl;
    }

    /**
     * <br>[機  能] MemoPriConfModelオブジェクトを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @param mode 表示モード
     * @return MemoPriConfModelオブジェクト
     */
    private MemoPriConfModel __getMemoPriConfModel(int usrSid, int mode) {
        MemoPriConfModel mpcMdl = new MemoPriConfModel();
        UDate now = new UDate();
        mpcMdl.setUsrSid(usrSid);
        mpcMdl.setMpcAuid(usrSid);
        mpcMdl.setMpcAdate(now);
        mpcMdl.setMpcDspMode(mode);
        mpcMdl.setMpcEdate(now);
        mpcMdl.setMpcEuid(usrSid);

        return mpcMdl;
    }


    /**
     * <br>[機 能] テンポラリディレクトリパスを取得する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @return テンポラリディレクトリパス
     */
    protected String _getTempDir() {
        MemCommonBiz memCmnBiz = new MemCommonBiz();
        return memCmnBiz.getTempDir(reqMdl__, GSConstMemo.DIRID_MEM010);
    }

    /**
     * <br>[機 能] 添付ファイルをテンポラリディレクトリにコピーする
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param binList 添付ファイルリスト
     * @param appRootPath アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param con コネクション
     * @param domain ドメイン
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __tempFileCopy(
            List<CmnBinfModel> binList,
            String appRootPath,
            String tempDir,
            Connection con,
            String domain) throws IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        String dateStr = now.getDateString();
        int i = 1;
        for (CmnBinfModel cbMdl : binList) {
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con, cbMdl.getBinSid(), domain);
            if (binMdl != null) {
                // 添付ファイルをテンポラリディレクトリにコピーする。
                cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, tempDir, i);
                i++;
            }
        }
    }

    /**
     * <br>[機 能] 添付ファイル情報をテンポラリディレクトリに設定する
     * <br>[解 説]
     * <br>[備 考]
     *
     * @param paramMdl パラメータ情報
     * @throws IOToolsException ファイルアクセス時例外
     */
    private void __setTempFiles(Mem010ParamModel paramMdl) throws IOToolsException {
        String tempDir = _getTempDir();
        CommonBiz commonBiz = new CommonBiz();
        paramMdl.setFileList(commonBiz.getTempFileLabelList(tempDir));
    }

}
