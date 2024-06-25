package jp.groupsession.v2.rsv.rsv100;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.pdf.RsvListPdfModel;
import jp.groupsession.v2.rsv.pdf.RsvListPdfUtil;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] 施設予約 施設利用状況照会画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv100Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv100Biz.class);

    /** コネクション */
    protected Connection con_ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Rsv100Biz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv100ParamModel
     * @param reqMdl リクエスト情報
     * @throws SQLException 例外
     */
    public void initDsp(Rsv100ParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {

        //管理者フラグを設定する
        __setAdmFlg(paramMdl, reqMdl);

        //施設グループの編集が可能か判定しフラグをセットする。
        __setGroupEditFlg(paramMdl, reqMdl);

        //コンボをセット
        setCombo(paramMdl);

        //CSV出力項目をセット
        if (paramMdl.isRsv100SearchFlg() && !paramMdl.isRsv100SearchSvFlg()) {

            paramMdl.setRsv100CsvOutField(getCsvOut(paramMdl.getRsv100svSelectSisKbn()));
            paramMdl.setRsv100SearchSvFlg(true);
        }
    }

    /**
     * <br>[機  能] 管理者フラグを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv100ParamModel
     * @throws SQLException 例外
     */
    public void setAdmFlg(Rsv100ParamModel paramMdl)
        throws SQLException {

        //管理者フラグを設定する
        __setAdmFlg(paramMdl, reqMdl_);

    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv100ParamModel
     * @throws SQLException 例外
     */
    public void setSearchList(Rsv100ParamModel paramMdl)
        throws SQLException {

        //最大表示件数の個人設定を取得
        int maxDsp = getMaxDsp(paramMdl);
        //検索結果件数
        int searchCnt = searchCount(paramMdl);
        //ページ調整
        int maxPage = searchCnt / maxDsp;
        if ((searchCnt % maxDsp) > 0) {
            maxPage++;
        }
        int page = paramMdl.getRsv100PageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setRsv100PageTop(page);
        paramMdl.setRsv100PageBottom(page);
        //ページコンボ設定
        paramMdl.setRsv100PageList(
                PageUtil.createPageOptions(searchCnt, maxDsp));

        //検索実行
        search(paramMdl);

        if (paramMdl.isRsv100SearchFlg() && !paramMdl.isRsv100SearchSvFlg()) {

            paramMdl.setRsv100CsvOutField(getCsvOut(paramMdl.getRsv100svSelectSisKbn()));
            paramMdl.setRsv100SearchSvFlg(true);
        }
    }

    /**
     * <br>[機  能] 表示用コンボを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv100ParamModel
     * @throws SQLException 例外
     */
    public void setCombo(Rsv100ParamModel paramMdl) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);

        /* グループコンボ **/
        if (paramMdl.getRsvSelectedGrpSid() == -1) {
            paramMdl.setRsvSelectedGrpSid(GSConstReserve.COMBO_DEFAULT_VALUE);
        }
        if (paramMdl.getRsvSelectedSisetuSid() == -1) {
            paramMdl.setRsvSelectedSisetuSid(GSConstReserve.COMBO_DEFAULT_VALUE);
        }
        paramMdl.setRsv100LabelListGrp1(_getGroupComboList(true, con_, reqMdl_));
        paramMdl.setRsv100LabelListGrp2(
                _getGroupSubComboList(
                        true, con_, paramMdl.getRsvSelectedGrpSid(), reqMdl_));

        /* ソート順コンボ **/
        List<LabelValueBean> sortLabelList = new ArrayList<LabelValueBean>();
        sortLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.73"), "1"));
        sortLabelList.add(new LabelValueBean(gsMsg.getMessage("cmn.facility"), "2"));
        sortLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.rsv100.14"), "3"));
        sortLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.rsv100.15"), "4"));
        sortLabelList.add(new LabelValueBean(gsMsg.getMessage("reserve.72"), "5"));

        paramMdl.setRsv100KeyList(sortLabelList);
    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv100ParamModel
     * @throws SQLException 例外
     */
    public void search(Rsv100ParamModel paramMdl) throws SQLException {

        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        ArrayList<Rsv100SisYrkModel> list = null;

        //検索結果リストを取得
        list = dao.getYrkReferenceList(setSearchData(paramMdl), _isAdmin(reqMdl_, con_), reqMdl_);

        if (list.size() <= 0) {
            log__.debug("検索結果がありません");
        }
        //検索結果をフォームにセット
        paramMdl.setRsv100resultList(list);
    }

    /**
     *  施設予約情報の公開範囲を設定する。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param model RsvSisYrkModelを拡張したModel
     * @param publicFlg 権限範囲判定結果
     * @throws SQLException SQL実行例外
     */
    public void setPublicArea(Rsv100SisYrkModel model, int publicFlg)
    throws SQLException {

            //「予定あり」メッセージ
            GsMessage gsMsg = new GsMessage(reqMdl_);
            String textPlans = gsMsg.getMessage("reserve.177");

            //公開区分別表示
            if (publicFlg == GSConstReserve.POWRANGE_IN) {
                model.setPublic(GSConstReserve.PUBLIC_KBN_ALL);
            } else {
                model.setRsyContent(textPlans);
                model.setPublic(GSConstReserve.PUBLIC_KBN_PLANS);
            }
    }

    /**
     * <br>[機  能] 検索結果数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv100ParamModel
     * @return int 検索結果数
     * @throws SQLException 例外
     */
    public int searchCount(Rsv100ParamModel paramMdl) throws SQLException {

        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        //検索結果数を取得
        int count = dao.getYrkReferenceCount(setSearchData(paramMdl), _isAdmin(reqMdl_, con_));

        return count;
    }

    /**
     * <br>[機  能] 施設利用状況照会の検索条件modelを作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv100ParamModel
     * @return Rsv100searchModel 検索条件model
     * @throws SQLException 例外
     */
    public Rsv100searchModel setSearchData(Rsv100ParamModel paramMdl) throws SQLException {
        Rsv100searchModel mdl = new Rsv100searchModel();

        boolean dateKbn = paramMdl.getRsv100svDateKbn() != Rsv100Form.DATEKBN_SELECT;
        mdl.setRsvDateKbn(dateKbn);
        if (dateKbn) {
            //fromの作成
            UDate from = new UDate();
            from.setZeroHhMmSs();
            from.setDate(paramMdl.getRsv100svFromYear(), paramMdl.getRsv100svFromMonth(),
                        paramMdl.getRsv100svFromDay());
            //toの作成
            UDate to = new UDate();
            to.setMaxHhMmSs();
            to.setDate(paramMdl.getRsv100svToYear(), paramMdl.getRsv100svToMonth(),
                    paramMdl.getRsv100svToDay());
            mdl.setRsvFrom(from);
            mdl.setRsvTo(to);
        }

        mdl.setRsvGrp1(paramMdl.getRsv100svGrp1());

        int svGrp2 = -1;
        if (paramMdl.getRsv100svGrp1() == 0) {
            svGrp2 = 0;
        } else {
            svGrp2 = paramMdl.getRsv100svGrp2();
        }
        mdl.setRsvGrp2(svGrp2);
        mdl.setRsvOrderKey(paramMdl.getRsv100OrderKey());
        mdl.setRsvSortKey(paramMdl.getRsv100SortKey());
        mdl.setRsvPageTop(paramMdl.getRsv100PageTop());
        mdl.setRsvMaxPage(getMaxDsp(paramMdl));

        //キーワード
        if (!StringUtil.isNullZeroString(paramMdl.getRsv100svKeyWord())) {
            List<String> keywordList = new ArrayList<String>();
            String searchKey =
                StringUtil.substitute(paramMdl.getRsv100svKeyWord(), "　", " ");
            StringTokenizer st = new StringTokenizer(searchKey, " ");
            while (st.hasMoreTokens()) {
                String str = st.nextToken();
                if (!StringUtil.isNullZeroString(str)) {
                    keywordList.add(str);
                }
            }
            mdl.setRsvKeyWord(keywordList);

        }

        //検索条件
        mdl.setRsvSearchCondition(paramMdl.getRsv100svSearchCondition());

        //検索対象(利用目的)
        mdl.setRsvTargetMok(paramMdl.getRsv100svTargetMok());

        //検索対象(内容)
        mdl.setRsvTargetNiyo(paramMdl.getRsv100svTargetNiyo());

        //承認状況
        mdl.setRsvApprStatus(paramMdl.getRsv100svApprStatus());

        //第一ソートキー
        mdl.setRsvSelectedKey1(paramMdl.getRsv100svSelectedKey1());

        //第二ソートキー
        mdl.setRsvSelectedKey2(paramMdl.getRsv100svSelectedKey2());

        //第一ソートキー ソート
        mdl.setRsvSelectedKey1Sort(paramMdl.getRsv100svSelectedKey1Sort());

        //第二ソートキー ソート
        mdl.setRsvSelectedKey2Sort(paramMdl.getRsv100svSelectedKey2Sort());

        //セッションユーザSIDを設定
        mdl.setUsrSid(reqMdl_.getSmodel().getUsrsid());

        return mdl;
    }

    /**
     * <br>[機  能] 最大表示件数を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv100ParamModel
     * @return 最大表示件数
     * @throws SQLException 例外
     */
    public int getMaxDsp(Rsv100ParamModel paramMdl) throws SQLException {
        //個人設定を取得
        RsvUserDao rudao = new RsvUserDao(con_);
        RsvUserModel rumodel = rudao.select(paramMdl.getRsv100userSid());

        int maxDsp = GSConstReserve.RSV_DEFAULT_DSP;
        if (rumodel != null) {
            maxDsp = rumodel.getRsuMaxDsp();
        }

        return maxDsp;
    }

    /**
     * <br>[機  能] 管理者フラグを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv100ParamModel
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setAdmFlg(Rsv100ParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {

        paramMdl.setRsvAdmFlg(_isAdmin(reqMdl, con_));

    }

    /**
     * <br>[機  能] 施設グループ編集可否フラグをセットする
     * <br>[解  説]
     * <br>[備  考] 施設グループの編集が可能か判定しフラグをセットする。
     *              (※下記のいずれかの条件を満たすか)
     *              1:管理者グループに所属している。
     *              2:いずれかの施設グループの管理者として設定されている。
     *              3:「権限設定をしない」となっている施設グループ1つでも存在する。
     *
     * @param paramMdl Rsv100ParamModel
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setGroupEditFlg(Rsv100ParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {

        paramMdl.setRsvGroupEditFlg(_isAllGroupEditAuthority(reqMdl, con_));

    }

    /**
     * <br>[機  能] 期間Fromで選択されている日付を
     *              yyyyMMdd形式の文字列に変換する
     * <br>[解  説]
     * <br>[備  考] 存在しない日付の場合、繰り上がり・下がりの
     *              調節が行われる
     *
     * @param paramMdl Rsv100ParamModel
     * @return ret 変換後
     */
    public String convDspDate(Rsv100ParamModel paramMdl) {

        String ret = "";

        if (paramMdl.getRsv100dateKbn() == Rsv100Form.DATEKBN_SELECT) {
            ret = (new UDate()).getDateString();
        } else {
            if (!StringUtil.isNullZeroString(paramMdl.getRsv100selectedFromDate())) {
                ret = paramMdl.getRsv100selectedFromDate().replaceAll("/", "");
                UDate convUd = new UDate();
                convUd.setDate(ret);
                ret = convUd.getDateString();
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] CSV出力のデフォルト値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトCSV出力項目
     */
    public static String[] getDefultCsvOut() {
        String[] csvOut = {
                GSConstReserve.CSV_OUT_ROOMID,
                GSConstReserve.CSV_OUT_ROOM,
                GSConstReserve.CSV_OUT_USERID,
                GSConstReserve.CSV_OUT_USER,
                GSConstReserve.CSV_OUT_PURPOSE,
                GSConstReserve.CSV_OUT_BIKO,
                GSConstReserve.CSV_OUT_USEFR_DATE,
                GSConstReserve.CSV_OUT_USEFR_TIME,
                GSConstReserve.CSV_OUT_USETO_DATE,
                GSConstReserve.CSV_OUT_USETO_TIME,
                GSConstReserve.CSV_OUT_EDPERM,
                GSConstReserve.CSV_OUT_PUBLIC
        };
        return csvOut;
    }

    /**
     * <br>[機  能] CSV出力の値を取得する(施設区分 部屋)
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトCSV出力項目
     */
    public static String[] getHeyaCsvOut() {
        String[] csvOut = {
                GSConstReserve.CSV_OUT_ROOMID,
                GSConstReserve.CSV_OUT_ROOM,
                GSConstReserve.CSV_OUT_USERID,
                GSConstReserve.CSV_OUT_USER,
                GSConstReserve.CSV_OUT_PURPOSE,
                GSConstReserve.CSV_OUT_BIKO,
                GSConstReserve.CSV_OUT_USEFR_DATE,
                GSConstReserve.CSV_OUT_USEFR_TIME,
                GSConstReserve.CSV_OUT_USETO_DATE,
                GSConstReserve.CSV_OUT_USETO_TIME,
                GSConstReserve.CSV_OUT_EDPERM,
                GSConstReserve.CSV_OUT_PUBLIC,
                GSConstReserve.CSV_OUT_USE_KBN,
                GSConstReserve.CSV_OUT_CONTACT,
                GSConstReserve.CSV_OUT_GUIDE,
                GSConstReserve.CSV_OUT_PARKNUM,
                GSConstReserve.CSV_OUT_BUSYO,
                GSConstReserve.CSV_OUT_USENAME,
                GSConstReserve.CSV_OUT_USENUM
        };
        return csvOut;
    }

    /**
     * <br>[機  能] CSV出力の値を取得する(施設区分 車)
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルトCSV出力項目
     */
    public static String[] getCarCsvOut() {
        String[] csvOut = {
                GSConstReserve.CSV_OUT_ROOMID,
                GSConstReserve.CSV_OUT_ROOM,
                GSConstReserve.CSV_OUT_USERID,
                GSConstReserve.CSV_OUT_USER,
                GSConstReserve.CSV_OUT_PURPOSE,
                GSConstReserve.CSV_OUT_BIKO,
                GSConstReserve.CSV_OUT_USEFR_DATE,
                GSConstReserve.CSV_OUT_USEFR_TIME,
                GSConstReserve.CSV_OUT_USETO_DATE,
                GSConstReserve.CSV_OUT_USETO_TIME,
                GSConstReserve.CSV_OUT_EDPERM,
                GSConstReserve.CSV_OUT_PUBLIC,
                GSConstReserve.CSV_OUT_CONTACT,
                GSConstReserve.CSV_OUT_DEST,
                GSConstReserve.CSV_OUT_BUSYO,
                GSConstReserve.CSV_OUT_USENAME,
                GSConstReserve.CSV_OUT_USENUM
        };
        return csvOut;
    }


    /**
     * <br>[機  能] 施設予約一覧(施設利用状況照会)をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータモデル
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @return pdfModel 施設予約一覧PDFモデル
     * @throws IOException IO実行時例外
     * @throws SQLException SQL実行例外
     */
    public RsvListPdfModel createRsvListPdf(
            Rsv100ParamModel paramMdl,
            String appRootPath,
            String outTempDir)
        throws IOException, SQLException {
        OutputStream oStream = null;
        GsMessage msg = new GsMessage(reqMdl_);

        //施設予約一覧(施設利用状況照会)PDF出力用モデル
        RsvListPdfModel pdfModel = __getRsvPdfDataList(paramMdl);
        String outBookName = msg.getMessage("reserve.rsvmain.4")
                + "(" + msg.getMessage("reserve.rsv100.1") + ")";
        String encOutBookName = fileNameCheck(outBookName) + ".pdf";
        pdfModel.setFileName(encOutBookName);

        String saveFileName = "rsvlist" + reqMdl_.getSmodel().getUsrsid() + ".pdf";
        pdfModel.setSaveFileName(saveFileName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            RsvListPdfUtil pdfUtil = new RsvListPdfUtil(reqMdl_);
            pdfUtil.createRsvListPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("施設予約一覧(施設利用状況照会)PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("施設予約一覧(施設利用状況照会)PDF出力を終了します。");

        return pdfModel;
    }

    /**
     * <br>[機  能] PDF出力用のデータモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return PDF出力用モデル
     * @throws SQLException SQL実行例外
     */
    private RsvListPdfModel __getRsvPdfDataList(
            Rsv100ParamModel paramMdl) throws SQLException {
        RsvListPdfModel ret = new RsvListPdfModel();

        //期間指定区分
        ret.setPdfDateKbn(paramMdl.getRsv100svDateKbn());

        UDate frDate = new UDate();
        frDate.setZeroHhMmSs();
        frDate.setDate(paramMdl.getRsv100svFromYear(), paramMdl.getRsv100svFromMonth(),
                    paramMdl.getRsv100svFromDay());
        //toの作成
        UDate toDate = new UDate();
        toDate.setMaxHhMmSs();
        toDate.setDate(paramMdl.getRsv100svToYear(), paramMdl.getRsv100svToMonth(),
                paramMdl.getRsv100svToDay());

        //期間From
        ret.setPdfFromDate(UDateUtil.getYymdJ(frDate, reqMdl_));
        //期間To
        ret.setPdfToDate(UDateUtil.getYymdJ(toDate, reqMdl_));
        //キーワード
        ret.setPdfKeyWord(paramMdl.getRsv100svKeyWord());
        //キーワード区分
        ret.setPdfKeyWordKbn(paramMdl.getRsv100svSearchCondition());

        //検索対象1 利用目的
        ret.setPdfSearchTarget1(paramMdl.getRsv100svTargetMok());
        // 検索対象2 内容
        ret.setPdfSearchTarget2(paramMdl.getRsv100svTargetNiyo());

        //施設グループ
        if (paramMdl.getRsv100svGrp1() > 0) {
            RsvSisGrpDao sisGrpDao = new RsvSisGrpDao(con_);
            RsvSisGrpModel rsvGrpMdl = sisGrpDao.select(paramMdl.getRsv100svGrp1());
            if (rsvGrpMdl != null) {
                ret.setPdfSelectGrp(rsvGrpMdl.getRsgName());
            }
        } else {
            ret.setPdfSelectGrp("全て");
        }

        //施設
        if (paramMdl.getRsv100svGrp1() > 0 && paramMdl.getRsv100svGrp2() > 0) {
            RsvSisDataDao sidDataDao = new RsvSisDataDao(con_);
            RsvSisDataModel sidMdl = new RsvSisDataModel();
            sidMdl.setRsdSid(paramMdl.getRsv100svGrp2());
            RsvSisDataModel dspMdl = sidDataDao.select(sidMdl);
            if (dspMdl != null) {
                ret.setPdfSelectSisetsu(dspMdl.getRsdName());
            }
        } else {
            ret.setPdfSelectSisetsu("全て");
        }

        // 承認状況
        ret.setPdfSyoninKbn(paramMdl.getRsv100svApprStatus());

        // ソートキー１
        ret.setPdfSortKey1(__getSelectKeyLabel(paramMdl.getRsv100svSelectedKey1()));
        // オーダーキー1
        ret.setPdfOrderKey1(paramMdl.getRsv100svSelectedKey1Sort());
        /** ソートキー2 */
        ret.setPdfSortKey2(__getSelectKeyLabel(paramMdl.getRsv100svSelectedKey2()));
        /** オーダーキー2  */
        ret.setPdfOrderKey2(paramMdl.getRsv100svSelectedKey2Sort());

        //一覧表示の施設予約データを全件分取得する
        RsvSisYrkDao sisDao = new RsvSisYrkDao(con_);
        ArrayList<Rsv100SisYrkModel> list = new ArrayList<Rsv100SisYrkModel>();
        //検索結果リストを取得
        list = sisDao.getAllYrkReferenceList(setSearchData(paramMdl),
                _isAdmin(reqMdl_, con_), reqMdl_);

        ret.setRsvDataList(list);

        return ret;
    }

    /**
     * <br>[機  能] 指定したソートキーのラベルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param key ソートキー
     * @return ソートキーラベル
     */
    private String __getSelectKeyLabel(int key) {
        GsMessage msg = new GsMessage(reqMdl_);

        String ret = null;

        switch (key) {
        case 1:
            ret = msg.getMessage("reserve.73");
            break;
        case 2:
            ret = msg.getMessage("cmn.facility");
            break;
        case 3:
            ret = msg.getMessage("reserve.rsv100.14");
            break;
        case 4:
            ret = msg.getMessage("reserve.rsv100.15");
            break;
        case 5:
            ret = msg.getMessage("reserve.72");
            break;
        default :
            ret = "";
        }

        return ret;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    private String fileNameCheck(String fileName) {
            String escName = fileName;

            escName = StringUtilHtml.replaceString(escName, "/", "");
            escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
            escName = StringUtilHtml.replaceString(escName, "?", "");
            escName = StringUtilHtml.replaceString(escName, "*", "");
            escName = StringUtilHtml.replaceString(escName, ":", "");
            escName = StringUtilHtml.replaceString(escName, "|", "");
            escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
            escName = StringUtilHtml.replaceString(escName, "<", "");
            escName = StringUtilHtml.replaceString(escName, ">", "");
            escName = StringUtilHtml.replaceString(escName, ".", "");
            escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内

        return escName;
    }

    /**
     * <br>[機  能] 選択された施設グループの施設SIDを取得する
     * <br>[解  説] 「全て」を選択した場合は0を返す
     * <br>[備  考]
     * @param con コネクション
     * @param sisGrpSid 施設グループSID
     * @throws SQLException SQL実行時例外
     * @return 施設区分
     */
    public int getSisetsuKbn(Connection con, int sisGrpSid) throws SQLException {

        //施設グループが0より小さかった場合
        if (sisGrpSid <= 0) {
            return 0;
        }

        int sisKbn = 0;
        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        RsvSisGrpModel grpMdl = dao.select(sisGrpSid);
        if (grpMdl != null) {
            sisKbn = grpMdl.getRskSid();
        }
        return sisKbn;
    }

    /**
     * <br>[機  能] 指定した施設区分のCSV出力の項目を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sisKbn 施設区分
     * @return CSV項目
     *
     */
    public String[] getCsvOut(int sisKbn) {

        String [] ret = null;
        //施設区分 部屋
        if (sisKbn == GSConstReserve.RSK_KBN_HEYA) {
            ret = getHeyaCsvOut();
            //施設区分 車
        } else if (sisKbn == GSConstReserve.RSK_KBN_CAR) {
            ret = getCarCsvOut();
        } else {
            ret = getDefultCsvOut();
        }

        return ret;
    }

}