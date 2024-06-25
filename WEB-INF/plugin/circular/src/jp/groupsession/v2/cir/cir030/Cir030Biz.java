package jp.groupsession.v2.cir.cir030;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir020.Cir020Biz;
import jp.groupsession.v2.cir.cir020.model.Cir020KnDataSearchModel;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cir.dao.CirInfDao;
import jp.groupsession.v2.cir.dao.CirInfLabelDao;
import jp.groupsession.v2.cir.dao.CirViewDao;
import jp.groupsession.v2.cir.dao.CirViewLabelDao;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cir.pdf.CirDtlPdfModel;
import jp.groupsession.v2.cir.pdf.CirDtlPdfUtil;
import jp.groupsession.v2.cmn.FileNameComparator;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 送信状況確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir030Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig PluignConfig
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(Cir030ParamModel paramMdl, Connection con, PluginConfig pconfig,
                            RequestModel reqMdl)
    throws SQLException, TempFileException {

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
            //ログイン有効フラグ
            paramMdl.setCirViewAccountUko(cacMdl.getUsrUkoFlg());
        }

        //回覧板作成可能ユーザ区分を設定
        CirCommonBiz cirBiz = new CirCommonBiz();
        paramMdl.setCirCreateFlg(cirBiz.canCreateUserCir(con, reqMdl));

        //回覧板SID
        int cirSid = getCirSid(paramMdl);

        /** 回覧板情報を取得する ************************************************/
        //回覧板情報(送信済み)を取得する
        CircularDao cDao = new CircularDao(con);
        CircularDspModel cdMdl = cDao.getSousinView(cirSid);

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

        //フォームにセット
        paramMdl.setCir020dspMdl(cdMdl);


        /** 添付ファイル情報を取得する *********************************************/
        paramMdl.setCir020fileList(cDao.getFileInfo(cirSid));


        /** 回覧先情報を取得する ***************************************************/
        //回覧先データ検索モデルをセット
        Cir020KnDataSearchModel cirKnDataSearchMdl = new Cir020KnDataSearchModel();
        cirKnDataSearchMdl.setCirSid(cirSid);
        cirKnDataSearchMdl.setSortKey(paramMdl.getCir030sortKey());
        cirKnDataSearchMdl.setOrderKey(paramMdl.getCir030orderKey());

        List < CircularDspModel > cdList = new ArrayList<CircularDspModel>();

        if (isMyGroupSid(paramMdl.getCirMemListGroup())) {
            //マイグループ
            MyGroupDao mgDao = new MyGroupDao(con);
            if (mgDao.isAbleViewMyGroup(getDspGroupSid(paramMdl.getCirMemListGroup()),
                    reqMdl.getSmodel().getUsrsid())) {
                cirKnDataSearchMdl.setSelectGrp(getDspGroupSid(paramMdl.getCirMemListGroup()));
                cdList = cDao.getMemberInfoMyGrp(cirKnDataSearchMdl);
            }
        } else if (isCirAccount(paramMdl.getCirMemListGroup())) {
            //代表アカウント
            cdList = cDao.getMemberInfoAccount(cirKnDataSearchMdl);

        } else {
            //通常グループ
            cirKnDataSearchMdl.setSelectGrp(getDspGroupSid(paramMdl.getCirMemListGroup()));
            cdList = cDao.getMemberInfo(cirKnDataSearchMdl);
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

        //WEB検索プラグインの使用可/不可を設定
        paramMdl.setCir020searchUse(CommonBiz.getWebSearchUse(pconfig));

        //グループコンボを設定
        paramMdl.setCirMemListGroupCombo(cirBiz.getGrpFilterCombo(con, reqMdl));
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
    public void deleteCir(Cir030ParamModel paramMdl, Connection con, int cacSid)
    throws SQLException {

        //送信回覧板SID
        int cifSid = getCirSid(paramMdl);
        String[] soSid = {Integer.toString(cifSid)};
        UDate now = new UDate();

        boolean commit = false;
        try {
            con.setAutoCommit(false);
            //処理モードで処理を分岐
            String cmdMode = NullDefault.getString(paramMdl.getCir010cmdMode(), "");

            if (cmdMode.equals(GSConstCircular.MODE_SOUSIN)
                    || cmdMode.equals(GSConstCircular.MODE_LABEL)) {
                //送信済
                CirInfModel soBean = new CirInfModel();
                soBean.setCifJkbn(GSConstCircular.DSPKBN_DSP_NG);
                soBean.setCifEuid(cacSid);
                soBean.setCifEdate(now);
                //選択された送信回覧板の状態区分を更新する(論理削除)
                CirInfDao cDao = new CirInfDao(con);
                cDao.updateJkbn(soBean, soSid);

                //回覧先の状態区分を更新する(削除)
                CirViewDao cirViewDao = new CirViewDao(con);
                cirViewDao.deleteAllView(cifSid, cacSid, now);
                CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
                labelViewDao.deleteCircularLabel(cifSid);

            } else if (cmdMode.equals(GSConstCircular.MODE_GOMI)) {
                //ゴミ箱
                CirInfModel soBean = new CirInfModel();
                soBean.setCifJkbn(GSConstCircular.DSPKBN_DSP_DEL);
                soBean.setCifEuid(cacSid);
                soBean.setCifEdate(now);
                //選択された送信回覧板の状態区分を更新する(論理削除)
                CirInfDao cDao = new CirInfDao(con);
                cDao.updateJkbn(soBean, soSid);
                // 回覧板送信ラベル削除
                CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
                labelInfDao.deleteGomiCircularLabel(soBean);
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
    public void comeBackCir(Cir030ParamModel paramMdl, Connection con, int cacSid)
    throws SQLException {

        //送信回覧板SID
        String[] soSid = {NullDefault.getString(paramMdl.getCir010selectInfSid(), "")};
        UDate now = new UDate();

        boolean commit = false;
        try {
            con.setAutoCommit(false);

            CirInfModel soBean = new CirInfModel();
            soBean.setCifJkbn(GSConstCircular.DSPKBN_DSP_OK);
            soBean.setCifEuid(cacSid);
            soBean.setCifEdate(now);
            //選択された送信回覧板の状態区分を更新する(復帰)
            CirInfDao cDao = new CirInfDao(con);
            cDao.updateJkbn(soBean, soSid);

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
    public void getCirInf(Cir030ParamModel paramMdl, Connection con, int cacSid)
    throws SQLException {

        /** 回覧板情報を取得する ************************************************/

        //回覧板SID
        int cirSid = getCirSid(paramMdl);

        //回覧板情報(送信済み)を取得する
        CircularDao cDao = new CircularDao(con);
        CircularDspModel cdMdl = cDao.getSousinView(cirSid);

        //送信フラグをセット
        cdMdl.setJsFlg(GSConstCircular.MODE_SOUSIN);

        //フォームにセット
        paramMdl.setCir020dspMdl(cdMdl);

    }

    /**
     * <br>[機  能] 閲覧する回覧板が存在するか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param viewAccount 現在回覧板を開いているアカウント
     * @return existFlg 回覧板存在フラグ
     * @throws SQLException SQL実行例外
     */
    public boolean isExistCir(Cir030ParamModel paramMdl, Connection con, int viewAccount)
    throws SQLException {

        boolean existFlg = true;

        if (!StringUtil.isNullZeroStringSpace(paramMdl.getCirEditInfSid())) {
            paramMdl.setCir010selectInfSid(paramMdl.getCirEditInfSid());
        }

        //回覧板SID
        int cirSid = getCirSid(paramMdl);

        /** 回覧板情報を取得する ************************************************/
        //回覧板情報(送信済み)を取得する
        CircularDao cDao = new CircularDao(con);
        CirInfModel infMdl = cDao.checkSendCirExist(cirSid, viewAccount);
        if (infMdl == null) {
            existFlg = false;
        } else if (infMdl.getCifJkbn() == GSConstCircular.DSPKBN_DSP_DEL) {
            //論理削除判定
            existFlg = false;
        }

        return existFlg;
     }

    /**
     * <br>[機  能] 回覧板SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return 回覧板SID
     */
    public static int getCirSid(Cir030ParamModel paramMdl) {
        return NullDefault.getInt(paramMdl.getCir010selectInfSid(), -1);
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
     * <br>[機  能] 回覧板送信済画面をPDF出力します。
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
     * @throws SQLException SQL実行例外
     * @throws IOException 入出力時例外
     */
    public CirDtlPdfModel createCirSendPdf(
            Cir030ParamModel paramMdl,
            Connection con,
            int userSid,
            String appRootPath,
            String outTempDir,
            PluginConfig pconfig,
            RequestModel reqMdl) throws SQLException, IOException {
        OutputStream oStream = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //送信モード
        String cirMode = GSConstCircular.MODE_SOUSIN;

        //回覧板PDF用モデル
        Cir020Biz cir020Biz = new Cir020Biz();
        CirDtlPdfModel pdfModel =
                cir020Biz.getCirPdfDataList(paramMdl, con, userSid, pconfig, reqMdl, cirMode);

        //ファイル名をセット
        String fileName = gsMsg.getMessage("cir.5") + "_"
                + NullDefault.getString(pdfModel.getCifTitle(), "");
        String encFileName = __fileNameCheck(fileName) + ".pdf";
        pdfModel.setFileName(encFileName);
        //セーブ用ファイル名をセット
        String saveFileName = "cirsend" + pdfModel.getCifSid() + ".pdf";
        pdfModel.setSaveFileName(saveFileName);
        //回覧板モードを送信済回覧板とセット
        pdfModel.setCirMode(cirMode);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            CirDtlPdfUtil pdfUtil = new CirDtlPdfUtil(reqMdl);
            log__.debug("回覧板送信済PDFの生成開始");
            pdfUtil.createCirDtlPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("回覧板送信済PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("回覧板送信済PDF出力を終了します。");

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
}
