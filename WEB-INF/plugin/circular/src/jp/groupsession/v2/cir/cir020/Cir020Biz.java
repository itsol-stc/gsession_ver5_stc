package jp.groupsession.v2.cir.cir020;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.FileNameUtil;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.archive.ZipUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.biz.CirFileEditCheckBiz;
import jp.groupsession.v2.cir.biz.CirNotifyBiz;
import jp.groupsession.v2.cir.biz.CirUsedDataBiz;
import jp.groupsession.v2.cir.cir020.model.Cir020AllTempDataModel;
import jp.groupsession.v2.cir.cir020.model.Cir020KnDataSearchModel;
import jp.groupsession.v2.cir.cir020.model.Cir020UserTempDataModel;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirUserBinDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CirSearchModel;
import jp.groupsession.v2.cir.model.CirUserBinModel;
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cir.pdf.CirDtlPdfMemModel;
import jp.groupsession.v2.cir.pdf.CirDtlPdfModel;
import jp.groupsession.v2.cir.pdf.CirDtlPdfUtil;
import jp.groupsession.v2.cmn.FileNameComparator;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.MyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 回覧板 受信確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir020Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @param initFlg 初期表示フラグ
     * @param pconfig PluginConfig
     * @param reqMdl リクエスト情報
     * @param appRoot アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイル操作時実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException ファイル操作時実行例外
     */
    @SuppressWarnings("unchecked")
    public void setInitData(
            Cir020ParamModel paramMdl, Connection con, int userSid,
            boolean initFlg, PluginConfig pconfig, RequestModel reqMdl,
            String appRoot, String tempDir)
                    throws SQLException, IOToolsException, TempFileException, IOException {

        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = null;

        //アカウントを取得
        if (paramMdl.getCirViewAccount() <= 0) {
            //デフォルトのアカウントを取得
            cacMdl = cacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        } else {
            //選択されたアカウントを取得
            cacMdl = cacDao.select(paramMdl.getCirViewAccount());
        }

        if (cacMdl != null) {
            //アカウント
            paramMdl.setCirViewAccount(cacMdl.getCacSid());
            //アカウント名
            paramMdl.setCirViewAccountName(cacMdl.getCacName());

            if (cacMdl.getCacTheme() !=  GSConstCircular.CAC_THEME_NOSET) {
                CmnThemeDao dao = new CmnThemeDao(con);
                CmnThemeModel model = dao.select(cacMdl.getCacTheme());
                if (model.getCtmPath() != null) {
                    //アカウントテーマ
                    paramMdl.setCir010AccountTheme(model.getCtmPath());
                }
            }
            paramMdl.setCirViewAccountUko(cacMdl.getUsrUkoFlg());
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();


        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        //** 回覧板情報を取得する ************************************************/
        //検索用Modelを作成
        Cir020SearchModel searchMdl = new Cir020SearchModel();
        searchMdl.setCirSid(cirSid);
        searchMdl.setCacSid(paramMdl.getCirViewAccount());

        //回覧板情報(受信)を取得する
        CircularDao cDao = new CircularDao(con);
        CircularDspModel cdMdl = cDao.getJusinView(searchMdl);

        //日付(文字列)をセット
        cdMdl.setDspCifAdate(UDateUtil.getSlashYYMD(cdMdl.getCifAdate()) + "  "
                + UDateUtil.getSeparateHMS(cdMdl.getCifAdate()));

        if (cdMdl.getCifEkbn() == GSConstCircular.CIR_EDIT) {

            cdMdl.setDspCifEditDate(UDateUtil.getSlashYYMD(cdMdl.getCifEditDate()) + "  "
                    + UDateUtil.getSeparateHMS(cdMdl.getCifEditDate()));
        }

        //内容(html表示用)をセット
        cdMdl.setCifValue(StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                NullDefault.getString(cdMdl.getCifValue(), "")));

        //メモ欄修正期限(文字列)をセット
        if (cdMdl.getCifMemoDate() != null) {
            cdMdl.setDspCifMemoDate(
                    gsMsg.getMessage("cir.56",
                            new String[] {UDateUtil.getSlashYYMD(cdMdl.getCifMemoDate())
                                    + " "}));
        } else {
            cdMdl.setDspCifMemoDate("");
        }

        //フォームにセット
        paramMdl.setCir020dspMdl(cdMdl);

        //確認・未確認
        String kakunin = "";
        if (cdMdl.getCvwConf() == GSConstCircular.CONF_UNOPEN) {
            kakunin = gsMsg.getMessage("cir.49");
        } else {
            kakunin = gsMsg.getMessage("cmn.confirmed");

            //確認後のメモ欄の修正期限が過ぎているかどうか
            UDate now = new UDate();
            UDate limit = NullDefault.getUDate(cdMdl.getCifMemoDate(), now);
            if (!now.equals(limit) && now.compareDateYMD(limit) >= 0) {
                paramMdl.setMemoFlg(0);
            } else {
                paramMdl.setMemoFlg(1);
            }
        }
        paramMdl.setKakuninStr(kakunin);

        //初期表示のみメモをセット
        if (initFlg && paramMdl.isCirDelFlg()) {
            paramMdl.setCir020memo(NullDefault.getString(cdMdl.getCvwMemo(), ""));

            //添付ファイルリスト
            CircularDao dao = new CircularDao(con);
            List < CmnBinfModel > uTmpFileList = dao.getUserTempFileInfo(searchMdl);
            if (uTmpFileList != null && uTmpFileList.size() > 0) {
                //添付ファイルをテンポラリディレクトリへ移動する
                String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
                String [] binSids = new String[uTmpFileList.size()];

                //バイナリSIDの取得
                for (int i = 0; i < uTmpFileList.size(); i++) {
                    binSids[i] = String.valueOf(uTmpFileList.get(i).getBinSid());
                }

                //取得したバイナリSIDからバイナリ情報を取得
                List<CmnBinfModel> binList = cmnBiz.getBinInfo(con, binSids, reqMdl.getDomain());
                int fileNum = 1;
                for (CmnBinfModel binData : binList) {
                    cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
                    //テンポラリファイルコピー後はキャッシュを削除
                    binData.removeBinFileData();
                    fileNum++;
                }
            }
            CirFileEditCheckBiz fchkBiz = new CirFileEditCheckBiz();
            fchkBiz.createMokulokuFile(tempDir);
        }

        //添付ファイル一覧を設定
        List<LabelValueBean> sortList = cmnBiz.getTempFileLabelList(tempDir);
        Collections.sort(sortList);
        paramMdl.setCir020UserTmpFileLabelList(sortList);

        //フラグをリセット
        paramMdl.setCirDelFlg(true);

        /** 添付ファイル情報を取得する *********************************************/
        paramMdl.setCir020fileList(cDao.getFileInfo(cirSid));

        /** 回覧先情報を取得する ***************************************************/

        //回覧先データ検索モデルをセット
        Cir020KnDataSearchModel cirUsrSearchMdl = new Cir020KnDataSearchModel();
        cirUsrSearchMdl.setCirSid(cirSid);

        cirUsrSearchMdl.setSortKey(paramMdl.getCir030sortKey());
        cirUsrSearchMdl.setOrderKey(paramMdl.getCir030orderKey());

        List < CircularDspModel > cdList = new ArrayList<CircularDspModel>();

        if (isMyGroupSid(paramMdl.getCirMemListGroup())) {
            //マイグループ
            MyGroupDao mgDao = new MyGroupDao(con);
            if (mgDao.isAbleViewMyGroup(getDspGroupSid(paramMdl.getCirMemListGroup()),
                    userSid)) {
                cirUsrSearchMdl.setSelectGrp(getDspGroupSid(paramMdl.getCirMemListGroup()));
                cdList = cDao.getMemberInfoMyGrp(cirUsrSearchMdl);
            }
        } else if (isCirAccount(paramMdl.getCirMemListGroup())) {
            //代表アカウント
            cdList = cDao.getMemberInfoAccount(cirUsrSearchMdl);

        } else {
            //通常グループ
            cirUsrSearchMdl.setSelectGrp(getDspGroupSid(paramMdl.getCirMemListGroup()));
            cdList = cDao.getMemberInfo(cirUsrSearchMdl);
        }

        HashMap <Integer, ArrayList<CmnBinfModel>> userTmpBinHash =
                cDao.getUserTempFileNameHash(cirSid);

        for (int i = 0; i < cdList.size(); i++) {
            CircularDspModel clMdl = cdList.get(i);

            //最終更新日時(文字列)をセット
            clMdl.setDspCvwEdate(UDateUtil.getSlashYYMD(clMdl.getCvwEdate()) + "  "
                    + UDateUtil.getSeparateHMS(clMdl.getCvwEdate()));
            clMdl.setDspCvwEdateDate(UDateUtil.getSlashYYMD(clMdl.getCvwEdate()));
            clMdl.setDspCvwEdateTime(UDateUtil.getSeparateHMS(clMdl.getCvwEdate()));

            //確認時間をセット
            if (clMdl.getCvwConf() == GSConstCircular.CONF_OPEN) {
                clMdl.setOpenTime(UDateUtil.getSlashYYMD(clMdl.getCvwConfDate()) + "  "
                        + UDateUtil.getSeparateHMS(clMdl.getCvwConfDate()));
                clMdl.setOpenTimeDate(UDateUtil.getSlashYYMD(clMdl.getCvwConfDate()));
                clMdl.setOpenTimeTime(UDateUtil.getSeparateHMS(clMdl.getCvwConfDate()));
            }

            //メモ(html表示用)をセット
            clMdl.setCvwMemo(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(clMdl.getCvwMemo()), ""));


            //ユーザ添付ファイルのバイナリSIDを取得
            ArrayList<CmnBinfModel> binList = userTmpBinHash.get(clMdl.getCacSid());
            if (binList != null && binList.size() > 0) {
                Collections.sort(binList, new FileNameComparator());
                if (binList != null && binList.size() > 0) {
                    clMdl.setDspUserTmpFileList(binList);
                }
            }
        }
        paramMdl.setCir030memList(cdList);

        //グループコンボを設定
        CirCommonBiz cirBiz = new CirCommonBiz();
        paramMdl.setCirMemListGroupCombo(cirBiz.getGrpFilterCombo(con, reqMdl));
    }

    /**
     * <br>[機  能] 「前へ」「次へ」ボタンの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param jsFlg 受信送信フラグ
     * @throws SQLException SQL実行例外
     */
    public void setPrevNext(Cir020ParamModel paramMdl, Connection con, int cacSid, String jsFlg)
            throws SQLException {
        setPrevNext(paramMdl, con, cacSid, jsFlg, GSConstCommon.NUM_INIT);
    }
    /**
     * <br>[機  能] 「前へ」「次へ」ボタンの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param jsFlg 受信送信フラグ
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setPrevNext(Cir020ParamModel paramMdl,
            Connection con,
            int cacSid,
            String jsFlg,
            int usrSid)
                    throws SQLException {

        //検索用Modelを作成
        CirSearchModel bean = __getSearchModel(paramMdl, cacSid, usrSid);

        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        //回覧板情報(受信)の回覧板SIDを全て取得する
        CircularDao cDao = new CircularDao(con);

        CirHashControlModel chcMdl = null;

        //処理モードを取得
        String cmdMode = __getCmdMode(paramMdl);

        //処理モードで処理を分岐
        if (cmdMode.equals(GSConstCircular.MODE_JUSIN)
                || cmdMode.equals(GSConstCircular.MODE_JUSIN_MAIN)) {
            //受信 or メイン
            chcMdl = cDao.getJusinAllList(bean, cirSid);

        } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
            //送信済み
            chcMdl = cDao.getSousinAllList(bean, cirSid);

        } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱
            chcMdl = cDao.getGomiAllList(bean, cirSid, jsFlg);
        } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {
            //ラベル
            chcMdl = cDao.getLabelAllList(bean, cirSid, jsFlg);
        }

        HashMap < Integer, CircularDspModel > sidMap = chcMdl.getMap();

        if (sidMap.isEmpty()) {
            paramMdl.setCir020PrevBound(false);
            paramMdl.setCir020NextBound(false);
            return;
        }

        //現在表示中のindex
        int selectedRow = chcMdl.getRowNum();

        //1件目データの場合は前へボタン使用不可
        if (selectedRow <= 1) {
            paramMdl.setCir020PrevBound(false);
        } else {
            paramMdl.setCir020PrevBound(true);
        }

        //最終データの場合は次へボタン使用不可
        if (selectedRow >= sidMap.size()) {
            paramMdl.setCir020NextBound(false);
        } else {
            paramMdl.setCir020NextBound(true);
        }
    }

    /**
     * <br>[機  能] 処理モードを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return String 処理モード
     */
    private String __getCmdMode(Cir020ParamModel paramMdl) {

        //遷移元画面ID
        String cir060dspId = NullDefault.getString(paramMdl.getCir060dspId(), "");

        if (cir060dspId.equals("")) {
            //回覧板一覧・メインから遷移
            return NullDefault.getString(paramMdl.getCir010cmdMode(), "");
        }

        //回覧板詳細検索から遷移
        return String.valueOf(paramMdl.getCir060svSyubetsu());
    }

    /**
     * <br>[機  能] 検索用Modelを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cacSid アカウントSID
     * @param usrSid セッションユーザSID
     * @return CirSearchModel
     */
    private CirSearchModel __getSearchModel(Cir020ParamModel paramMdl, int cacSid, int usrSid) {

        //遷移元画面ID
        String cir060dspId = NullDefault.getString(paramMdl.getCir060dspId(), "");

        //検索用Modelを作成
        CirSearchModel bean = new CirSearchModel();

        if (cir060dspId.equals("")) {
            //回覧板一覧・メインから遷移

            bean.setCacSid(cacSid);
            bean.setSortKey(paramMdl.getCir010sortKey());
            bean.setOrderKey(paramMdl.getCir010orderKey());
            bean.setLabelSid(paramMdl.getCir010SelectLabelSid());

        } else {
            //回覧板詳細検索から遷移

            //セーブ 検索キーワード
            String searchWord = NullDefault.getString(paramMdl.getCir010svSearchWord(), "");
            //セーブ 検索対象
            String[] targets = paramMdl.getCir060svSearchTarget();
            boolean targetTitle = false;
            boolean targetBody = false;
            if (targets != null && targets.length > 0) {
                for (String target : targets) {
                    if (String.valueOf(GSConstCircular.SEARCH_TARGET_TITLE).equals(target)) {
                        targetTitle = true;
                    }
                    if (String.valueOf(GSConstCircular.SEARCH_TARGET_BODY).equals(target)) {
                        targetBody = true;
                    }
                }
            }

            CommonBiz cBiz = new CommonBiz();

            bean.setCacSid(cacSid);
            bean.setSessionUserSid(usrSid);
            bean.setOrderKey(paramMdl.getCir060svOrder1());
            bean.setSortKey(paramMdl.getCir060svSort1());
            bean.setOrderKey2(paramMdl.getCir060svOrder2());
            bean.setSortKey2(paramMdl.getCir060svSort2());
            bean.setGroupSid(paramMdl.getCir060svGroupSid());
            bean.setHassinSid(paramMdl.getCir060svUserSid());
            bean.setKairansakiSid(paramMdl.getCir060svSelUserSid());
            bean.setKeyWord(cBiz.setKeyword(searchWord));
            bean.setWordKbn(paramMdl.getCir060svWordKbn());
            bean.setTargetTitle(targetTitle);
            bean.setTargetBody(targetBody);
        }
        return bean;
    }
    /**
     * <br>[機  能] 「前」または「次」の回覧板SID、「前へ」「次へ」ボタンの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param mode 前へ、次へボタン区分
     * @param jsFlg 受信送信フラグ
     * @return String 「前」または「次」の回覧板受信送信フラグ
     * @throws SQLException SQL実行例外
     */
    public String changePrevNext(
            Cir020ParamModel paramMdl,
            Connection con,
            int cacSid,
            int mode,
            String jsFlg) throws SQLException {
        return changePrevNext(paramMdl, con, cacSid, mode, jsFlg, GSConstCommon.NUM_INIT);
    }

    /**
     * <br>[機  能] 「前」または「次」の回覧板SID、「前へ」「次へ」ボタンの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @param mode 前へ、次へボタン区分
     * @param jsFlg 受信送信フラグ
     * @param usrSid セッションユーザSID
     * @return String 「前」または「次」の回覧板受信送信フラグ
     * @throws SQLException SQL実行例外
     */
    public String changePrevNext(
            Cir020ParamModel paramMdl,
            Connection con,
            int cacSid,
            int mode,
            String jsFlg,
            int usrSid) throws SQLException {

        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        //検索用Modelを作成
        CirSearchModel bean = __getSearchModel(paramMdl, cacSid, usrSid);

        //回覧板情報(受信)の回覧板SIDを全て取得する
        CircularDao cDao = new CircularDao(con);

        CirHashControlModel chcMdl = null;

        //処理モードを取得
        String cmdMode = __getCmdMode(paramMdl);

        if (cmdMode.equals(GSConstCircular.MODE_JUSIN)
                || cmdMode.equals(GSConstCircular.MODE_JUSIN_MAIN)) {
            //受信
            chcMdl = cDao.getJusinAllList(bean, cirSid);

        } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
            //送信済み
            chcMdl = cDao.getSousinAllList(bean, cirSid);

        } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
            //ゴミ箱
            chcMdl = cDao.getGomiAllList(bean, cirSid, jsFlg);
        } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {
            //ラベル
            chcMdl = cDao.getLabelAllList(bean, cirSid, jsFlg);
        }

        //一覧の全データ
        HashMap < Integer, CircularDspModel > sidMap = chcMdl.getMap();

        if (sidMap.isEmpty()) {
            paramMdl.setCir020PrevBound(false);
            paramMdl.setCir020NextBound(false);
            return jsFlg;
        }

        //現在表示中のindex
        int selectedRow = chcMdl.getRowNum();

        //前へボタン
        if (mode == GSConstCircular.VIEW_PREV) {
            selectedRow -= 1;
            //次へボタン
        } else if (mode == GSConstCircular.VIEW_NEXT) {
            selectedRow += 1;
        }

        //1件目データの場合は前へボタン使用不可
        if (selectedRow <= 1) {
            paramMdl.setCir020PrevBound(false);
        } else {
            paramMdl.setCir020PrevBound(true);
        }

        //最終データの場合は次へボタン使用不可
        if (selectedRow >= sidMap.size()) {
            paramMdl.setCir020NextBound(false);
        } else {
            paramMdl.setCir020NextBound(true);
        }

        if (selectedRow == 0 || selectedRow == sidMap.size() + 1) {
            return jsFlg;
        }

        //前、または次のデータの回覧板SID取得
        Object obj = sidMap.get(selectedRow);
        if (obj == null) {
            return jsFlg;
        }

        CircularDspModel cdMdl = (CircularDspModel) obj;
        paramMdl.setCir010selectInfSid(String.valueOf(cdMdl.getCifSid()));

        if (cmdMode.equals(GSConstCircular.MODE_GOMI)
                || cmdMode.equals(GSConstCircular.MODE_LABEL)) {
            return cdMdl.getJsFlg();
        }

        return jsFlg;
    }

    /**
     * <br>[機  能] 受信回覧板情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cacSid アカウントSID
     * @param userSid ユーザSID
     * @param cntCon 採番コントローラ
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @throws Exception 実行時例外
     */
    public void doUpdate(
            Cir020ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl,
            int cacSid,
            int userSid,
            MlCountMtController cntCon,
            String tempDir,
            String appRootPath)
                    throws Exception {

        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);
        //システム日付
        UDate now = new UDate();

        boolean commit = false;
        boolean memoEdit = false;
        CirViewModel cvMdl = new CirViewModel();
        boolean autoCommit = con.getAutoCommit();

        try {
            con.setAutoCommit(false);

            //回覧板情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            CirUsedDataBiz usedDataBiz = new CirUsedDataBiz(con);
            usedDataBiz.insertCirDataSize(cirSid, false);

            //テンポラリディレクトリパスにある添付ファイルを全て登録
            CommonBiz biz = new CommonBiz();
            List < String > binList =
                    biz.insertBinInfo(con, tempDir, appRootPath, cntCon, userSid, now);

            cvMdl.setCvwMemo(NullDefault.getString(paramMdl.getCir020memo(), ""));
            cvMdl.setCvwConf(GSConstCircular.CONF_OPEN);
            cvMdl.setCvwConfDate(now);
            cvMdl.setCvwEuid(cacSid);
            cvMdl.setCvwEdate(now);
            cvMdl.setCacSid(cacSid);
            cvMdl.setCifSid(cirSid);

            //受信回覧板情報の更新を行う
            CirViewDao cvDao = new CirViewDao(con);
            cvDao.updateView(cvMdl);

            //ユーザ添付情報の登録
            CirUserBinDao uBinDao = new CirUserBinDao(con);
            CirUserBinModel uBinMdl = new CirUserBinModel();
            uBinMdl.setCifSid(cirSid);
            uBinMdl.setCacSid(cacSid);
            uBinDao.insertCubBinList(uBinMdl, binList);

            //回覧板情報のデータ使用量を登録
            usedDataBiz.insertCirDataSize(cirSid, true);

            con.commit();
            commit = true;

            if (!StringUtil.isNullZeroString(cvMdl.getCvwMemo())
                    || binList.size() > 0) {
                memoEdit = true;
            }

        } catch (SQLException e) {
            log__.warn("回覧板登録に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
            con.setAutoCommit(autoCommit);
        }
        //回覧板SIDを指定して、未確認の回覧板の件数をカウントする
        CirViewDao cvDao = new CirViewDao(con);
        int count = cvDao.getUnopenedAllCnt(cirSid);

        CirNotifyBiz notifyBiz = new CirNotifyBiz(con, reqMdl);
        if (count == 0) {
            //ショートメール通知がONの場合、回覧板確認完了通知メールを送信する
            int smlCnt = notifyBiz.doNotifyKanryo(cntCon, con, cirSid, appRootPath, reqMdl);
            if (smlCnt > 0) {
                return;
            }
        }
        if (memoEdit) {
            notifyBiz.doNotifyMemo(cntCon, con, cvMdl, appRootPath, reqMdl);

        }

    }

    /**
     * <br>[機  能] 受信回覧板情報の更新(メモ欄・回覧先ユーザ添付)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cacSid アカウントSID
     * @param userSid ユーザSID
     * @param cntCon 採番コントローラ
     * @param tempDir テンポラリディレクトリ
     * @param appRootPath アプリケーションルートパス
     * @throws Exception 実行例外
     */
    public void doUpdateAns(
            Cir020ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl,
            int cacSid,
            int userSid,
            MlCountMtController cntCon,
            String tempDir,
            String appRootPath)
                    throws Exception {

        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);
        //システム日付
        UDate now = new UDate();
        boolean autoCommit = con.getAutoCommit();
        boolean commit = false;
        boolean memoEdit = false;
        CirViewDao cvDao = new CirViewDao(con);

        CirViewModel cvMdl = new CirViewModel();
        cvMdl.setCacSid(cacSid);
        cvMdl.setCifSid(cirSid);
        CirViewModel oldMdl = cvDao.select(cvMdl);

        try {
            con.setAutoCommit(false);

            //回覧板情報のデータ使用量を登録(変更対象のデータ使用量を減算)
            CirUsedDataBiz usedDataBiz = new CirUsedDataBiz(con);
            usedDataBiz.insertCirDataSize(cirSid, false);

            cvMdl.setCvwMemo(NullDefault.getString(paramMdl.getCir020memo(), ""));
            cvMdl.setCvwEuid(cacSid);
            cvMdl.setCvwEdate(now);

            //受信回覧板情報の更新(メモ欄のみ)を行う
            cvDao.updateMemo(cvMdl);

            //添付ファイル情報の更新を行う
            CircularDao cirDao = new CircularDao(con);
            //既存の添付ファイルを論理削除
            cirDao.deleteBinfUsrTmp(cirSid, cacSid);
            //ユーザ添付ファイルバイナリ情報を削除
            CirUserBinDao uBinDao = new CirUserBinDao(con);
            uBinDao.deleteBins(cirSid, cacSid);
            //テンポラリディレクトリパスにある添付ファイルを全て登録
            CommonBiz biz = new CommonBiz();
            List < String > binList =
                    biz.insertBinInfo(con, tempDir, appRootPath, cntCon, userSid, now);
            //ユーザ添付情報の登録
            CirUserBinModel uBinMdl = new CirUserBinModel();
            uBinMdl.setCifSid(cirSid);
            uBinMdl.setCacSid(cacSid);
            uBinDao.insertCubBinList(uBinMdl, binList);

            //回覧板情報のデータ使用量を登録
            usedDataBiz.insertCirDataSize(cirSid, true);

            con.commit();
            commit = true;

            CirFileEditCheckBiz fchkBiz = new CirFileEditCheckBiz();
            if (!NullDefault.getString(cvMdl.getCvwMemo(), "").equals(
                    NullDefault.getString(oldMdl.getCvwMemo(), ""))
                    || fchkBiz.isFileEdit(tempDir)) {
                memoEdit = true;
            }

        } catch (SQLException e) {
            log__.warn("回覧板登録に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
            con.setAutoCommit(autoCommit);
        }
        CirNotifyBiz notifyBiz = new CirNotifyBiz(con, reqMdl);
        if (memoEdit) {
            notifyBiz.doNotifyMemo(cntCon, con, cvMdl, appRootPath, reqMdl);

        }

    }

    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @return セッションユーザSID
     */
    public int getSessionUserSid(RequestModel reqMdl) {
        return reqMdl.getSmodel().getUsrsid();
    }

    /**
     * <br>[機  能] 回覧板名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return String 回覧板名称
     * @throws SQLException SQL実行例外
     */
    public String getCirName(Cir020ParamModel paramMdl, int userSid, Connection con,
            RequestModel reqMdl)
                    throws SQLException {

        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        CirInfDao ciDao = new CirInfDao(con);
        CirInfModel ciMdl = ciDao.getCirInfo(cirSid);

        StringBuilder cirName = new StringBuilder();
        String cmdMode = NullDefault.getString(paramMdl.getCir010cmdMode(), "");

        GsMessage gsMsg = new GsMessage(reqMdl);

        if (cmdMode.equals(GSConstCircular.MODE_JUSIN)
                || cmdMode.equals(GSConstCircular.MODE_JUSIN_MAIN)) {
            //受信
            if (paramMdl.getCir020dspMdl().getCvwConf() == GSConstCircular.CONF_UNOPEN) {
                //未確認
                cirName.append("・");
                cirName.append(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
                cirName.append(gsMsg.getMessage("schedule.44") + gsMsg.getMessage("cir.49"));

            } else {
                //確認済み
                cirName.append("・");
                cirName.append(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
            }

        } else if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)) {
            //送信済み
            cirName.append("・");
            cirName.append(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));

        } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {

            //ゴミ箱
            cirName.append("・");

            if (paramMdl.getCir020dspMdl().getJsFlg().equals(GSConstCircular.MODE_JUSIN)) {
                String textJusin = gsMsg.getMessage("cmn.receive2");
                cirName.append("[ " + textJusin + " ] ");
            } else if (paramMdl.getCir020dspMdl().getJsFlg().equals(GSConstCircular.MODE_SOUSIN)) {
                String textSosin = gsMsg.getMessage("cmn.sent2");
                cirName.append("[ " + textSosin + " ] ");
            }
            cirName.append(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
        } else if (cmdMode.equals(GSConstCircular.MODE_LABEL)) {

            //ラベル
            cirName.append("・");

            if (paramMdl.getCir020dspMdl().getJsFlg().equals(GSConstCircular.MODE_JUSIN)) {
                String textJusin = gsMsg.getMessage("cmn.receive2");
                cirName.append("[ " + textJusin + " ] ");
            } else if (paramMdl.getCir020dspMdl().getJsFlg().equals(GSConstCircular.MODE_SOUSIN)) {
                String textSosin = gsMsg.getMessage("cmn.sent2");
                cirName.append("[ " + textSosin + " ] ");
            }
            cirName.append(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(ciMdl.getCifTitle()), ""));
        }

        return cirName.toString();

    }

    /**
     * <br>[機  能] 選択された回覧板を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void deleteCir(Cir020ParamModel paramMdl, Connection con, int cacSid)
            throws SQLException {

        //受信回覧板SID
        String[] juSid = {NullDefault.getString(paramMdl.getCir010selectInfSid(), "")};
        UDate now = new UDate();

        boolean commit = false;
        try {
            con.setAutoCommit(false);
            //処理モードで処理を分岐
            String cmdMode = NullDefault.getString(paramMdl.getCir010cmdMode(), "");

            if (cmdMode.equals(GSConstCircular.MODE_JUSIN)
                    || cmdMode.equals(GSConstCircular.MODE_JUSIN_MAIN)
                    || cmdMode.equals(GSConstCircular.MODE_LABEL)) {
                //受信
                CirViewModel bean = new CirViewModel();
                bean.setCvwDsp(GSConstCircular.DSPKBN_DSP_NG);
                bean.setCvwEuid(cacSid);
                bean.setCvwEdate(now);
                bean.setCacSid(cacSid);

                //選択された回覧板の状態区分を更新する(論理削除)
                CirViewDao cvDao = new CirViewDao(con);
                cvDao.updateDspFlg(bean, juSid);

            } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
                //ゴミ箱
                CirViewModel juBean = new CirViewModel();
                juBean.setCvwDsp(GSConstCircular.DSPKBN_DSP_DEL);
                juBean.setCvwEuid(cacSid);
                juBean.setCvwEdate(now);
                juBean.setCacSid(cacSid);
                //選択された受信回覧板の状態区分を更新する(論理削除)
                CirViewDao cvDao = new CirViewDao(con);
                cvDao.updateDspFlg(juBean, juSid);
                //受信ラベルを削除する
                CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
                labelViewDao.deleteGomiCircularLabel(juBean);
            }

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.warn("回覧板削除に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 選択された回覧板を元に戻す
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void comeBackCir(Cir020ParamModel paramMdl, Connection con, int cacSid)
            throws SQLException {

        //受信回覧板SID
        String[] juSid = {NullDefault.getString(paramMdl.getCir010selectInfSid(), "")};
        UDate now = new UDate();

        boolean commit = false;
        try {
            con.setAutoCommit(false);

            //ゴミ箱
            CirViewModel juBean = new CirViewModel();
            juBean.setCvwDsp(GSConstCircular.DSPKBN_DSP_OK);
            juBean.setCvwEuid(cacSid);
            juBean.setCvwEdate(now);
            juBean.setCacSid(cacSid);
            //選択された受信回覧板の状態区分を更新する(復帰)
            CirViewDao cvDao = new CirViewDao(con);
            cvDao.updateDspFlg(juBean, juSid);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.warn("回覧板復帰に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 回覧板情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void getCirInf(Cir020ParamModel paramMdl, Connection con, int cacSid)
            throws SQLException {

        //** 回覧板情報を取得する ************************************************/

        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        //検索用Modelを作成
        Cir020SearchModel bean = new Cir020SearchModel();
        bean.setCirSid(cirSid);
        bean.setCacSid(cacSid);

        //回覧板情報(受信)を取得する
        CircularDao cDao = new CircularDao(con);
        CircularDspModel cdMdl = cDao.getJusinView(bean);

        //受信フラグをセット
        cdMdl.setJsFlg(GSConstCircular.MODE_JUSIN);

        //フォームにセット
        paramMdl.setCir020dspMdl(cdMdl);
    }

    /**
     * <br>[機  能] 回覧板が存在するか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cacSid アカウントSID
     * @return existFlg 回覧板存在フラグ
     * @throws SQLException SQL実行例外
     */
    public boolean isExistCir(Cir020ParamModel paramMdl, Connection con, int cacSid)
            throws SQLException {

        boolean existFlg = true;

        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        //検索用Modelを作成
        Cir020SearchModel bean = new Cir020SearchModel();
        bean.setCirSid(cirSid);
        bean.setCacSid(cacSid);


        //回覧板情報(受信)を取得する
        CircularDao cDao = new CircularDao(con);
        CircularDspModel cdMdl = cDao.getJusinView(bean);
        if (cdMdl == null) {
            existFlg = false;
        } else if (cdMdl.getCvwDsp() == GSConstCircular.DSPKBN_DSP_DEL) {
            //論理削除判定
            existFlg = false;
        }

        return existFlg;
    }

    /**
     * <br>[機  能] 回覧板のSIDからアカウントを選択
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl RequestModelSID
     * @throws SQLException SQL実行例外
     */
    public void getViewAccount(Cir020ParamModel paramMdl, Connection con, RequestModel reqMdl)
            throws SQLException {

        CircularDao cirDao = new CircularDao(con);
        paramMdl.setCirViewAccount(cirDao.getViewAccountSid(
                reqMdl.getSmodel().getUsrsid(), Integer.valueOf(paramMdl.getCir010selectInfSid())));
    }


    /**
     * <br>[機  能] 添付一括ダウンロード用のZIPデータを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param usrSid セッションユーザSID
     * @param cacSid アカウントSID
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行例外
     * @throws IOException 入出力時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws CSVException CSV出力時例外
     * @return [0]テンポラリディレクトリ内保存ファイル名
     *              [1]ダウンロード時保存時の表示ファイル名
     */
    public String [] makeAllTmpFile(
            Cir020ParamModel paramMdl, Connection con,
            String appRootPath, String tempDir, int usrSid, int cacSid, RequestModel reqMdl)
                    throws SQLException, IOException, IOToolsException,
                    TempFileException, CSVException {

        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        Cir020KnDataSearchModel cirKnDataSearchMdl = new Cir020KnDataSearchModel();
        cirKnDataSearchMdl.setCirSid(cirSid);
        cirKnDataSearchMdl.setSortKey(paramMdl.getCir030sortKey());
        cirKnDataSearchMdl.setOrderKey(paramMdl.getCir030orderKey());

        //添付ダウンロードデータを取得する
        Cir020AllTempDataModel allDataMdl =
                __getAllTmpData(
                        cirKnDataSearchMdl, cirSid, usrSid, cacSid,
                        paramMdl.getCirMemListGroup(), con, reqMdl.getDomain());

        //フォルダ名のエスケープ処理
        String escTitle = FileNameUtil.getTempFileNameTabRemoveNoExt(allDataMdl.getTopTitle());
        String srmEscTitle = escTitle.trim();
        String cirTitle = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (StringUtil.isNullZeroStringSpace(srmEscTitle)) {
            cirTitle = gsMsg.getMessage("cir.allTmep.download");
        } else {
            cirTitle = srmEscTitle;
        }

        ArrayList<Cir020UserTempDataModel> allFileList = allDataMdl.getAllUserFileList();

        //ディレクトリ内にタイトルのフォルダを作成
        IOTools.isDirCheck(tempDir, true);

        CommonBiz cmnBiz = new CommonBiz();

        //出力対象ディレクトリパス
        String outFileDir = tempDir + "/" + cirTitle;

        for (Cir020UserTempDataModel uDataMdl : allFileList) {
            String uName = FileNameUtil.getTempFileNameTabRemoveNoExt(uDataMdl.getUserDirName());
            List<CmnBinfModel> fileList = uDataMdl.getUserTmpFileList();
            File directoryUser = new File(outFileDir, uName);
            directoryUser.mkdir();
            if (fileList != null && fileList.size() > 0) {
                for (CmnBinfModel binMdl : fileList) {
                    String usrTmpDir = outFileDir + "/" + uName + "/";

                    //テンポラリディレクトリに保存できる字数になるようにファイル名のをカットする
                    String escFileName =  __fileLengthCheck(
                            usrTmpDir, binMdl.getBinFileName(),
                            binMdl.getBinFileExtension(), cirTitle);

                    //ZIPで解凍できるバイト数のタイトルに変換する
                    escFileName = __zipBufferCheck(
                            escFileName, cirTitle, uName, binMdl.getBinFileExtension());

                    binMdl.setBinFileName(escFileName);

                    cmnBiz.saveTempFile(binMdl, appRootPath, usrTmpDir);
                    //テンポラリファイルコピー後はキャッシュを削除
                    binMdl.removeBinFileData();
                }
            }
        }

        //CSVファイルを作成
        String fileName = "cirKnList.csv";
        //CSVファイルを作成する
        CirCsvKnWriter write = new CirCsvKnWriter(cirKnDataSearchMdl, reqMdl);
        write.outputCsv(con, outFileDir, fileName);


        String saveFilePath = tempDir + "/" + "cirAllExp.zip";
        ZipUtil.zipDir(outFileDir, saveFilePath);

        String bookName = cirTitle + ".zip";
        String [] ret = {saveFilePath, bookName};

        return ret;
    }

    /**
     * <br>[機  能] 添付一括ダウンロード用のZIPデータを作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cirKnDataSearchMdl 回覧先一覧の取得条件モデル
     * @param cirSid 回覧板SID
     * @param con コネクション
     * @param userSid ユーザSID
     * @param cacSid アカウントSID
     * @param grpSid グループSID
     * @param domain ドメイン
     * @return 添付ファイル一括ダウンロード用のデータモデル
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private Cir020AllTempDataModel __getAllTmpData(
            Cir020KnDataSearchModel cirKnDataSearchMdl,
            int cirSid,
            int userSid,
            int cacSid,
            String grpSid,
            Connection con,
            String domain
            )
                    throws SQLException, TempFileException {

        Cir020AllTempDataModel ret = new Cir020AllTempDataModel();


        //** 回覧板情報を取得する ************************************************/
        //検索用Modelを作成
        Cir020SearchModel searchMdl = new Cir020SearchModel();
        searchMdl.setCirSid(cirSid);
        searchMdl.setCacSid(cacSid);

        //回覧板情報(受信)を取得する
        CirInfDao cirInfDao = new CirInfDao(con);
        String title = cirInfDao.getCirInfo(cirSid).getCifTitle();

        CircularDao cDao = new CircularDao(con);

        /** 回覧先情報を取得する ***************************************************/
        ArrayList<Cir020UserTempDataModel> allUserFileList =
                new ArrayList<Cir020UserTempDataModel>();
        Cir020UserTempDataModel usrTmpDataMdl = null;

        List < CircularDspModel > cdList = cDao.getMemberInfo(cirKnDataSearchMdl);
        if (isMyGroupSid(grpSid)) {
            //マイグループ
            MyGroupDao mgDao = new MyGroupDao(con);
            if (mgDao.isAbleViewMyGroup(getDspGroupSid(grpSid),
                    userSid)) {
                cirKnDataSearchMdl.setSelectGrp(getDspGroupSid(grpSid));
                cdList = cDao.getMemberInfoMyGrp(cirKnDataSearchMdl);
            }
        } else if (isCirAccount(grpSid)) {
            //代表アカウント
            cdList = cDao.getMemberInfoAccount(cirKnDataSearchMdl);

        } else {
            //通常グループ
            cirKnDataSearchMdl.setSelectGrp(getDspGroupSid(grpSid));
            cdList = cDao.getMemberInfo(cirKnDataSearchMdl);
        }

        HashMap <Integer, ArrayList<String>> userTmpBinHash =
                cDao.getUserTempFileHash(cirSid);
        int count = 1;
        for (CircularDspModel clMdl : cdList) {
            usrTmpDataMdl = new Cir020UserTempDataModel();
            String firstNum = __getHeadNumber(count, cdList.size());
            String uName = firstNum + "_" + clMdl.getCacName();

            usrTmpDataMdl.setUserDirName(uName);

            //ユーザ添付ファイルのバイナリSIDを取得
            ArrayList<String> binSidList = userTmpBinHash.get(clMdl.getCacSid());
            if (binSidList != null && binSidList.size() > 0) {
                String [] binSids = (String[]) binSidList.toArray(new String[binSidList.size()]);
                //添付ファイル情報をセット
                CommonBiz cmnBiz = new CommonBiz();
                List<CmnBinfModel> binList = cmnBiz.getBinInfo(con, binSids, domain);
                if (binList != null && binList.size() > 0) {
                    usrTmpDataMdl.setUserTmpFileList(binList);
                }
            }

            allUserFileList.add(usrTmpDataMdl);
            count++;
        }

        //タイトル
        ret.setTopTitle(title);
        //全ファイルデータリスト
        ret.setAllUserFileList(allUserFileList);

        return ret;
    }

    /**
     * <br>[機  能] 指定した数字を指定した最大桁までゼロ埋めをして返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param num 数字
     * @param maxLength 最大桁数
     * @return ゼロ埋めされた数字
     */
    private String __getHeadNumber(int num, int maxLength) {
        StringBuilder ret = new StringBuilder();

        String strNum = String.valueOf(num);
        int numLength = strNum.length();
        int zeroCnt = String.valueOf(maxLength).length() - numLength;

        for (int i = 0; i < zeroCnt; i++) {
            ret.append("0");
        }
        ret.append(strNum);

        return ret.toString();
    }

    /**
     * <br>[機  能] テンポラリディレクトリに保存できるようにファイル名をカットとする。
     * <br>[解  説] テンポラリ + ファイル名 + 拡張子 + (一括ダウンロード時のファイル名) <= 255 になるようにする。
     *                    直接ZIPを開いたときに一括ダウンロード時のファイル名が付与される。
     * <br>[備  考]
     * @param tempdir テンポラリディレクトリ
     * @param fileName ファイル名
     * @param fileExtension 拡張子
     * @param topFolderName 一括ダウンロード時のファイル名
     * @return 字数カットされたファイル名
     */
    private String __fileLengthCheck(String tempdir, String fileName, String fileExtension,
            String topFolderName) {

        //最大文字数
        int maxLength = 255;

        String fullPath = tempdir + fileName;
        if (fullPath.length() <= maxLength) {
            return fileName;
        }

        //可能文字数
        int okLength = maxLength - tempdir.length() - fileExtension.length();

        //カット
        String escName = StringUtil.trimRengeString(fileName, okLength);
        //拡張子を追加
        escName = escName + fileExtension;

        return escName;
    }

    /**
     * <br>[機  能] ZIPで解凍できるファイル名にする。
     * <br>[解  説] ディレクトリ名+ファイル名が259バイト以内に収まるようにする
     * <br>[備  考]
     * @param fileName ファイル名
     * @param topDirectName 最上位のフォルダ名(回覧板タイトル名)
     * @param userDirectName ユーザごとのフォルダ名
     * @param fileExtension 拡張子名
     * @return 259バイト以内にカットしたファイル名
     * @throws UnsupportedEncodingException URLのエンコード時エラー
     */
    private String __zipBufferCheck(String fileName, String topDirectName,
            String userDirectName, String fileExtension) throws UnsupportedEncodingException {

        String cpFileName = fileName;

        String escFileName = null;

        //使用済バイト数 回覧板タイトル + / + ユーザディレクトリ名 + /
        int usedzipPathByteNoExt =
                topDirectName.getBytes("Windows-31J").length + 1
                + userDirectName.getBytes("Windows-31J").length + 1;

        //使用可能バイト数 拡張子有り
        int zipPathByteExt = 0;
        if (fileExtension == null) {
            zipPathByteExt = 259 - usedzipPathByteNoExt;
        } else {
            zipPathByteExt = 259 - (usedzipPathByteNoExt
                    + fileExtension.getBytes("Windows-31J").length);
        }

        if (usedzipPathByteNoExt + fileName.getBytes("Windows-31J").length > 259) {
            String formatFileName = "";
            int cntByte = 0;

            while (cntByte < zipPathByteExt) {
                String value = cpFileName.substring(0, 1);
                cntByte += value.getBytes("Windows-31J").length;

                formatFileName += value;
                cpFileName = cpFileName.substring(1);

                if (zipPathByteExt - cntByte == 1) {
                    break;
                }
            }

            if (fileExtension == null) {
                escFileName = formatFileName;
            } else {
                escFileName = formatFileName + fileExtension;
            }

        } else {
            escFileName = fileName;
        }

        return escFileName;
    }

    /**
     * <br>[機  能] 回覧先ユーザの添付ファイルがダウンロード可能かチェックする。
     * <br>[解  説] 各ユーザの添付ファイル
     * <br>[備  考]
     * @param con コネクション
     * @param cifSid 回覧板SID
     * @param binSid バイナリ―SID
     * @param userSid ユーザSID
     * @param selAccountSid 選択アカウントSID
     * @param dlAccountSid ユーザ添付ファイルダウンロード時選択アカウントSID
     * @return true: 参照可能 false: 参照不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canDownloadCirBindata(
            Connection con, int cifSid, Long binSid,
            int userSid, int selAccountSid, int dlAccountSid)
                    throws SQLException {


        CirCommonBiz biz = new CirCommonBiz();
        //アカウントが使用可能かチェックする
        if (!biz.canUseAccount(con, userSid, selAccountSid)) {
            return false;
        }

        //指定した回覧板の送信者か、送信者出なければその回覧板は「公開」になっているか
        if (!biz.canBrowseCirRoute(con, cifSid, selAccountSid)) {
            return false;
        }

        //添付ファイルがユーザ添付のものかチェックする。
        CirUserBinDao cubDao = new CirUserBinDao(con);
        return cubDao.canViewCirUsrTempfile(cifSid, binSid, dlAccountSid);
    }

    /**
     * <br>[機  能] 添付ファイル一括ダウンロードファイルが取得可能かチェックする
     * <br>[解  説] 各ユーザの添付ファイル
     * <br>[備  考]
     * @param con コネクション
     * @param cifSid 回覧板SID
     * @param userSid ユーザSID
     * @param selAccountSid 選択アカウントSID
     * @return true: 参照可能 false: 参照不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canDownloadAllTmp(
            Connection con, int cifSid,
            int userSid, int selAccountSid)
                    throws SQLException {

        CirCommonBiz biz = new CirCommonBiz();
        //アカウントが使用可能かチェックする
        //指定した回覧板の送信者か、送信者でなければその回覧板は「公開」になっているか
        if (biz.canUseAccount(con, userSid, selAccountSid)
                && (biz.canBrowseCirRoute(con, cifSid, selAccountSid)
                        || biz.checkExistence(con, cifSid, selAccountSid))) {
            return true;
        }

        return false;
    }

    /**
     * パラメータ.グループコンボ値が代表アカウントかを判定する
     * <br>[機  能]先頭文字に"sac"が有る場合は代表アカウント
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isCirAccount(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf(GSConstCircular.CIR_ACCOUNT_STR);

        // 先頭文字に"cac"が有る場合は代表アカウント
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * パラメータ.グループコンボ値からグループSID又はマイグループSIDを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又はマイグループSID
     */
    public static int getDspGroupSid(String gpSid) {
        int ret = 0;
        if (gpSid == null) {
            return ret;
        }

        if (isMyGroupSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }

    /**
     * パラメータ.グループコンボ値がグループSIDかマイグループSIDかを判定する
     * <br>[機  能]先頭文字に"M"が有る場合、マイグループSID
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isMyGroupSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf("M");

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * <br>[機  能] 回覧板受信画面をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @param pconfig プラグイン情報
     * @param reqMdl リクエスト情報
     * @return 回覧板詳細PDFモデル
     * @throws SQLException SQL実行時例外
     * @throws IOException 入出力時例外
     */
    public CirDtlPdfModel createCirRecvPdf(
            Cir020ParamModel paramMdl,
            Connection con,
            int userSid,
            String appRootPath,
            String outTempDir,
            PluginConfig pconfig,
            RequestModel reqMdl) throws SQLException, IOException {
        OutputStream oStream = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //受信モード
        String cirMode = GSConstCircular.MODE_JUSIN;

        //回覧板PDF用モデル
        CirDtlPdfModel pdfModel =
                getCirPdfDataList(paramMdl, con, userSid, pconfig, reqMdl, cirMode);

        //ファイル名をセット
        String fileName = gsMsg.getMessage("cir.5") + "_"
                + NullDefault.getString(pdfModel.getCifTitle(), "");
        String encFileName = __fileNameCheck(fileName) + ".pdf";
        pdfModel.setFileName(encFileName);
        //セーブ用ファイル名をセット
        String saveFileName = "cirrecv" + pdfModel.getCifSid() + ".pdf";
        pdfModel.setSaveFileName(saveFileName);
        //回覧板モードを受信回覧板とセット
        pdfModel.setCirMode(cirMode);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            CirDtlPdfUtil pdfUtil = new CirDtlPdfUtil(reqMdl);
            log__.debug("回覧板受信PDFの生成開始");
            pdfUtil.createCirDtlPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("回覧板受信PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("回覧板受信PDF出力を終了します。");

        return pdfModel;
    }

    /**
     * <br>[機  能] 回覧板詳細PDFモデルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param pconfig プラグイン情報
     * @param reqMdl リクエスト情報
     * @param cirMode 回覧板モード
     * @return pdfModel 回覧板詳細PDFモデル
     * @throws SQLException SQL実行時例外
     */
    public CirDtlPdfModel getCirPdfDataList(
            Cir020ParamModel paramMdl,
            Connection con,
            int userSid,
            PluginConfig pconfig,
            RequestModel reqMdl,
            String cirMode)
                    throws SQLException {
        CircularDao cDao = new CircularDao(con);
        CircularDspModel cdMdl = null;
        CirDtlPdfModel pdfModel = new CirDtlPdfModel();

        //回覧板SID
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        //処理モードをセット
        pdfModel.setCmdMode(__getCmdMode(paramMdl));

        /** 回覧板情報をセットする ***************************************************/
        //アカウントを取得
        CirAccountModel cacMdl = null;
        CirAccountDao cacDao = new CirAccountDao(con);
        if (paramMdl.getCirViewAccount() <= 0) {
            //デフォルトのアカウントを取得
            cacMdl = cacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        } else {
            //選択されたアカウントを取得
            cacMdl = cacDao.select(paramMdl.getCirViewAccount());
        }

        //回覧板情報を取得
        if (cirMode.equals(GSConstCircular.MODE_JUSIN)) {
            //受信モード

            //回覧板情報(受信)を取得する
            Cir020SearchModel searchMdl = new Cir020SearchModel();
            searchMdl.setCirSid(cirSid);
            searchMdl.setCacSid(paramMdl.getCirViewAccount());
            cdMdl = cDao.getJusinView(searchMdl);

            //確認状態をセット
            pdfModel.setCvwConf(cdMdl.getCvwConf());

            //アカウント名をセット
            if (cacMdl != null) {
                pdfModel.setCirViewAccountName(cacMdl.getCacName());
            }

            //発信者をセット
            pdfModel.setCacName(cdMdl.getCacName());

        } else if (cirMode.equals(GSConstCircular.MODE_SOUSIN)) {
            //送信モード

            //回覧板情報(送信)を取得する
            cdMdl = cDao.getSousinView(cirSid);

            //発信者をセット
            if (cacMdl != null) {
                pdfModel.setCacName(cacMdl.getCacName());
            }
        }

        //回覧板SIDをセット
        pdfModel.setCifSid(cirSid);

        //タイトルをセット
        pdfModel.setCifTitle(cdMdl.getCifTitle());

        //発信日時(文字列)をセット
        pdfModel.setDspCifAdate(UDateUtil.getSlashYYMD(cdMdl.getCifAdate()) + " "
                + UDateUtil.getSeparateHMS(cdMdl.getCifAdate()));

        //修正区分をセット
        int cifEkbn = cdMdl.getCifEkbn();
        pdfModel.setCifEkbn(cifEkbn);

        //修正日時(文字列)をセット
        if (cifEkbn == GSConstCircular.CIR_EDIT) {
            pdfModel.setDspCifEditDate(UDateUtil.getSlashYYMD(cdMdl.getCifEditDate()) + " "
                    + UDateUtil.getSeparateHMS(cdMdl.getCifEditDate()));
        }

        //内容をセット
        pdfModel.setCifValue(NullDefault.getString(cdMdl.getCifValue(), ""));

        //回覧板の添付ファイル情報をセット
        pdfModel.setBinfMdlList(cDao.getFileInfo(cirSid));

        //回覧先確認状況の公開/非公開をセット
        pdfModel.setPrivateLevel(cdMdl.getCifShow());

        //回覧板モードが受信かつ回覧先確認状況が非公開ならここまで
        if (cirMode.equals(GSConstCircular.MODE_JUSIN)
                && pdfModel.getPrivateLevel() == GSConstCircular.CIR_INIT_SAKI_PRIVATE) {
            return pdfModel;
        }

        /** 回覧先情報をセットする ***************************************************/
        //回覧先データ検索モデルをセット
        Cir020KnDataSearchModel cirUsrSearchMdl = new Cir020KnDataSearchModel();
        cirUsrSearchMdl.setCirSid(cirSid);

        //ソートキーとオーダーキー
        int cir030sortKey = paramMdl.getCir030sortKey();
        int cir030orderKey = paramMdl.getCir030orderKey();
        //PDF用モデルにセット
        pdfModel.setPdfSortKey(cir030sortKey);
        pdfModel.setPdfOrderKey(cir030orderKey);
        //検索モデルにセット
        cirUsrSearchMdl.setSortKey(cir030sortKey);
        cirUsrSearchMdl.setOrderKey(cir030orderKey);

        //グループフィルタ
        String cirMemListGroup = paramMdl.getCirMemListGroup();
        //グループフィルタのグループ名をセット
        CirCommonBiz cirBiz = new CirCommonBiz();
        List<CmnLabelValueModel> memGroupList = cirBiz.getGrpFilterCombo(con, reqMdl);
        for (CmnLabelValueModel memGroup : memGroupList) {
            if (memGroup.getValue().equals(cirMemListGroup)) {
                pdfModel.setCirMemListGroupName(memGroup.getLabel());
                break;
            }
        }

        //回覧先確認状況のリスト
        List < CircularDspModel > cdList = new ArrayList<CircularDspModel>();
        if (isMyGroupSid(cirMemListGroup)) {
            //マイグループの閲覧権限を失った場合はリストを取得しない
            if (!StringUtil.isNullZeroString(pdfModel.getCirMemListGroupName())) {
                //マイグループ
                cirUsrSearchMdl.setSelectGrp(getDspGroupSid(cirMemListGroup));
                cdList = cDao.getMemberInfoMyGrp(cirUsrSearchMdl);
            }

        } else if (isCirAccount(cirMemListGroup)) {
            //代表アカウント
            cdList = cDao.getMemberInfoAccount(cirUsrSearchMdl);

        } else {
            //通常グループ
            cirUsrSearchMdl.setSelectGrp(getDspGroupSid(cirMemListGroup));
            cdList = cDao.getMemberInfo(cirUsrSearchMdl);
        }

        //回覧先確認状況をセット
        List<CirDtlPdfMemModel> memMdlList = new ArrayList<CirDtlPdfMemModel>();
        for (CircularDspModel clMdl : cdList) {
            CirDtlPdfMemModel memMdl = new CirDtlPdfMemModel();

            //アカウントSIDをセット
            memMdl.setCacSid(clMdl.getCacSid());
            //社員/職員番号をセット
            memMdl.setSyainNo(clMdl.getSyainNo());
            //氏名をセット
            memMdl.setCacName(clMdl.getCacName());
            //役職をセット
            memMdl.setPosName(clMdl.getPosName());
            //確認フラグをセット
            memMdl.setCvwConf(clMdl.getCvwConf());
            //確認日時(文字列)をセット
            if (clMdl.getCvwConf() == GSConstCircular.CONF_OPEN) {
                memMdl.setOpenTime(UDateUtil.getSlashYYMD(clMdl.getCvwConfDate()) + " "
                        + UDateUtil.getSeparateHMS(clMdl.getCvwConfDate()));
            }
            //メモをセット
            memMdl.setCvwMemo(NullDefault.getString(clMdl.getCvwMemo(), ""));
            //最終更新日時(文字列)をセット
            memMdl.setDspCvwEdate(UDateUtil.getSlashYYMD(clMdl.getCvwEdate()) + " "
                    + UDateUtil.getSeparateHMS(clMdl.getCvwEdate()));

            memMdlList.add(memMdl);
        }
        pdfModel.setPdfMemList(memMdlList);

        //受信者添付ファイル情報をセット
        pdfModel.setMemFileNameList(cDao.getUserTempFileNameHash(cirSid));

        return pdfModel;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    private String __fileNameCheck(String fileName) {
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
     * <br>[機  能] アカウント名を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param con コネクション
     * @return アカウント名
     * @throws SQLException SQL実行例外
     */
    public String getAccountName(int accountSid, Connection con) throws SQLException {
        CirAccountDao dao = new CirAccountDao(con);
        return dao.getCirAccountName(accountSid);
    }

    /**
     *
     * <br>[機  能]指定された回覧板がゴミ箱内にあるかを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param selectInfSid 回覧板SID
     * @param viewAccountSid アカウントSID
     * @throws SQLException SQL実行時例外
     * @return 削除済みかどうか(true:削除されていない false:削除済み)
     *
     */
    public int cirGomiConf(Connection con, String selectInfSid, int viewAccountSid)
            throws SQLException {

        CirViewModel viewMdl = new CirViewModel();
        viewMdl.setCifSid(Integer.valueOf(selectInfSid));
        viewMdl.setCacSid(viewAccountSid);

        //指定したアカウントの指定した回覧板の状態を取得
        CirViewDao viewDao = new CirViewDao(con);
        viewMdl =  viewDao.select(viewMdl);

        //回覧板のモードを取得
        return viewMdl.getCvwDsp();
    }
    /**
     *
     * <br>[機  能] オペレーションログ内容を出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return オペレーションログ内容
     * @throws SQLException SQL実行時例外
     */
    public String getOperationLogForCommit(Cir020ParamModel paramMdl,
            RequestModel reqMdl, Connection con) throws SQLException {
        StringBuilder sb = new StringBuilder();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //送信者
        CirAccountDao cacDao = new CirAccountDao(con);
        CirAccountModel cacMdl = null;
        cacMdl = cacDao.select(paramMdl.getCirViewAccount());

        if (cacMdl != null) {
            sb.append(gsMsg.getMessage("wml.102")).append(":").append(cacMdl.getCacName());
            sb.append("\n");
        }
        int cirSid = NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);

        if (cirSid > 0) {
            CirInfDao ciDao = new CirInfDao(con);
            CirInfModel ciMdl = ciDao.getCirInfo(cirSid);
            //タイトル
            sb.append(gsMsg.getMessage("cmn.title")).append(":").append(ciMdl.getCifTitle());
        }
        return sb.toString();
    }
}