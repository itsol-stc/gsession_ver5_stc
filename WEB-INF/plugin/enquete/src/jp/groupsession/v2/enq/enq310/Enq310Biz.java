package jp.groupsession.v2.enq.enq310;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.csv.EnqCsvDao;
import jp.groupsession.v2.enq.csv.EnqCsvModel;
import jp.groupsession.v2.enq.csv.EnqCsvSubModel;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.enq.dao.EnqQueMainDao;
import jp.groupsession.v2.enq.dao.EnqQueSubDao;
import jp.groupsession.v2.enq.enq210.Enq210Biz;
import jp.groupsession.v2.enq.model.EnqMainModel;
import jp.groupsession.v2.enq.model.EnqQueMainModel;
import jp.groupsession.v2.enq.model.EnqQueSubModel;
import jp.groupsession.v2.enq.pdf.EnqPdfModel;
import jp.groupsession.v2.enq.pdf.EnqPdfUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート 結果確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq310Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq310Biz.class);

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq310ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con)
    throws SQLException {

        long emnSid = paramMdl.getAnsEnqSid();

        //アンケート情報を設定する
        setEnqueteData(reqMdl, con, paramMdl);

        EnqQueMainDao queMainDao = new EnqQueMainDao(con);
        ArrayList<EnqQueMainModel> eqmList = queMainDao.select(emnSid);
        if (eqmList != null) {
            Enq310Dao dao310 = new Enq310Dao(con);
            EnqQueSubDao queSubDao = new EnqQueSubDao(con);
            List<Enq310QuestionModel> queList = new ArrayList<Enq310QuestionModel>();

            for (EnqQueMainModel eqmData : eqmList) {
                //コメント行は設問に含めない
                if (eqmData.getEqmQueKbn() == GSConstEnquete.SYURUI_COMMENT) {
                    continue;
                }

                Enq310QuestionModel queData = new Enq310QuestionModel();
                //設問SID
                queData.setEmnSid(eqmData.getEmnSid());
                //設問連番
                int eqmSeq = eqmData.getEqmSeq();
                queData.setQueSeq(eqmSeq);
                //設問番号
                queData.setNo(eqmData.getEqmQueSec());
                //必須
                queData.setRequire(eqmData.getEqmRequire());
                //設問種別
                queData.setQueKbn(eqmData.getEqmQueKbn());
                queData.setQueKbnName(Enq210Biz.getDspQueType(reqMdl, queData.getQueKbn()));
                //設問
                queData.setQuestion(eqmData.getEqmQuestion());

                int[] ansCount = dao310.getAnswerCount(emnSid, eqmSeq);
                //対象人数 回答人数
                queData.setAnswerCountAr(StringUtil.toCommaFormat(String.valueOf(ansCount[1])));
                //対象人数 回答人数 割合
                int arPer = this.getRatio(ansCount[0], ansCount[1]);
                queData.setAnswerCountArPer(String.valueOf(arPer));
                //対象人数 未回答人数
                queData.setAnswerCountUn(StringUtil.toCommaFormat(String.valueOf(ansCount[2])));
                //対象人数 未回答人数 割合
                queData.setAnswerCountUnPer(String.valueOf(100 - arPer));

                //回答情報(選択肢)
                if (eqmData.getEqmQueKbn() == GSConstEnquete.SYURUI_SINGLE
                || eqmData.getEqmQueKbn() == GSConstEnquete.SYURUI_MULTIPLE) {
                    List<EnqQueSubModel> queSubList
                        = queSubDao.select(emnSid, queData.getQueSeq());
                    if (queSubList != null) {
                        List<Enq310QuestionSubModel> subList
                            = dao310.getAnswerSubList(
                                    reqMdl, emnSid, eqmSeq, eqmData.getEqmQueKbn());
                        queData.setSubList(subList);
                    }
                }

                queList.add(queData);
            }
            paramMdl.setQueList(queList);
        }

    }

    /**
     * <br>[機  能] アンケート情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setEnqueteData(RequestModel reqMdl, Connection con, Enq310ParamModel paramMdl)
    throws SQLException {

        EnqCommonBiz enqBiz = new EnqCommonBiz();
        long emnSid = paramMdl.getAnsEnqSid();

        // 設問情報を取得
        EnqMainDao enqMainDao = new EnqMainDao(con);
        EnqMainModel enqMainMdl = enqMainDao.select(emnSid);

        //重要度
        paramMdl.setEnq310priority(enqMainMdl.getEmnPriKbn());
        //発信者
        if (enqMainMdl.getEmnSendGrp() > 0) {
            CmnGroupmDao grpDao = new CmnGroupmDao(con);
            CmnGroupmModel grpMdl = grpDao.selectGroup((int) enqMainMdl.getEmnSendGrp());
            paramMdl.setEnq310sender(grpMdl.getGrpName());
            paramMdl.setEnq310senderDelFlg(grpMdl.getGrpJkbn() == GSConst.JTKBN_DELETE);
        } else {
            int userSid = (int) enqMainMdl.getEmnSendUsr();
            CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usrInfMdl
                = usrInfDao.select(userSid);
            paramMdl.setEnq310sender(
                    usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());

            CmnUsrmModel usrmModel = new CmnUsrmModel();
            CmnUsrmDao usrmDao = new CmnUsrmDao(con);
            usrmModel = usrmDao.select(userSid);
            paramMdl.setEnq310senderDelFlg(usrmModel.getUsrJkbn() == GSConst.JTKBN_DELETE);
            paramMdl.setEnq310senderUkoFlg(usrmModel.getUsrUkoFlg());

        }

        //アンケートタイトル
        paramMdl.setEnq310enqTitle(enqMainMdl.getEmnTitle());
        //アンケート内容
        paramMdl.setEnq310enqContent(enqMainMdl.getEmnDesc());

        //アンケート内容から不要なタグを除去する
        paramMdl.setEnq310enqContent(StringUtilHtml.removeIllegalTag(paramMdl.getEnq310enqContent()));

        //回答期限
        paramMdl.setEnq310ansLimitDate(enqBiz.getStrDate(reqMdl, enqMainMdl.getEmnResEnd()));
        //回答期限 曜日
        paramMdl.setEnq310ansDayOfWeek(enqMainMdl.getEmnResEnd().getStrWeekJ(reqMdl));
        if (enqMainMdl.getEmnOpenEndKbn() != GSConstEnquete.EMN_OPEN_END_KBN_NON) {
            //結果公開期限
            paramMdl.setEnq310pubLimitDate(enqBiz.getStrDate(reqMdl, enqMainMdl.getEmnOpenEnd()));
            //結果公開期限 曜日
            paramMdl.setEnq310pubDayOfWeek(enqMainMdl.getEmnOpenEnd().getStrWeekJ(reqMdl));
        }
        //結果公開期限 開始
        paramMdl.setEnq310ansPubFrDate(enqBiz.getStrDate(reqMdl, enqMainMdl.getEmnAnsPubStr()));
        //結果公開期限 開始 曜日
        paramMdl.setEnq310ansPubFrDayOfWeek(enqMainMdl.getEmnAnsPubStr().getStrWeekJ(reqMdl));
        //匿名
        paramMdl.setEnq310anony(enqMainMdl.getEmnAnony());
        //回答結果
        paramMdl.setEnq310ansOpen(enqMainMdl.getEmnAnsOpen());

        //対象人数を取得する
        Enq310Dao dao310 = new Enq310Dao(con);
        int[] ansCount = dao310.getAnswerCount(emnSid);

        //対象人数 全体
        paramMdl.setEnq310answerCountAll(StringUtil.toCommaFormat(String.valueOf(ansCount[0])));
        //対象人数 回答人数
        paramMdl.setEnq310answerCountAr(StringUtil.toCommaFormat(String.valueOf(ansCount[1])));
        //対象人数 回答人数 割合
        int arRatio = getRatio(ansCount[0], ansCount[1]);
        paramMdl.setEnq310answerCountArPer(String.valueOf(arRatio));
        //対象人数 未回答人数
        paramMdl.setEnq310answerCountUn(StringUtil.toCommaFormat(String.valueOf(ansCount[2])));
        paramMdl.setEnq310answerCountUnNum(String.valueOf(ansCount[2]));
        //対象人数 回答人数 割合
        paramMdl.setEnq310answerCountUnPer(String.valueOf(100 - arRatio));
    }

    /**
     * <br>[機  能] 割合を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param allCnt 全体件数
     * @param value 対象件数
     * @return 対象件数の割合
     */
    public int getRatio(int allCnt, int value) {
        if (value <= 0) {
            return 0;
        }
        BigDecimal result = new BigDecimal(value);
        result = result.multiply(new BigDecimal(100));
        result = result.divide(new BigDecimal(allCnt), 0, BigDecimal.ROUND_DOWN);
        return result.intValue();
    }

    /**
     * <br>[機  能] アンケート結果確認画面をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @param pconfig プラグイン情報
     * @param reqMdl リクエスト情報
     * @return アンケート結果確認PDFモデル
     * @throws SQLException SQL実行時例外
     * @throws IOException 入出力時例外
     */
    public EnqPdfModel createEnqPdf(
            Enq310ParamModel paramMdl,
            Connection con,
            int userSid,
            String appRootPath,
            String outTempDir,
            PluginConfig pconfig,
            RequestModel reqMdl) throws SQLException, IOException {

        OutputStream oStream = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

      //アンケート結果確認PDF用モデルに値をセット
        EnqPdfModel pdfModel = new EnqPdfModel();
        pdfModel = getEnqPdfDataList(paramMdl, con, reqMdl);

        //ファイル名
        String fileName = gsMsg.getMessage("enq.plugin") + gsMsg.getMessage("enq.47")
                + "_" + NullDefault.getString(pdfModel.getEnq310enqTitle(), "");

        String encFileName = fileNameCheck(fileName) + ".pdf";
        pdfModel.setFileName(encFileName);

        //セーブ用ファイル名
        String saveFileName = "enq" + pdfModel.getEnqPdfSid() + ".pdf";
        pdfModel.setSvFileName(saveFileName);

        //PDF生成
        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);

            EnqPdfUtil pdfUtil = new EnqPdfUtil();
            pdfUtil.createEnqPdf(pdfModel, appRootPath, oStream);

        } catch (Exception e) {
            log__.error("アンケート結果確認PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }

        return pdfModel;
    }

    /**
     *
     * <br>[機  能] アンケート結果のPDFモデルを取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param reqMdl request情報
     * @return pdfModel
     * @throws SQLException SQL実行時例外
     */
    public EnqPdfModel getEnqPdfDataList(
            Enq310ParamModel paramMdl, Connection con, RequestModel reqMdl) throws SQLException {

        EnqPdfModel pdfMdl = new EnqPdfModel();

        //アンケート基本情報
        long emnSid = paramMdl.getAnsEnqSid();
        EnqMainDao enqMainDao = new EnqMainDao(con);
        EnqMainModel enqMainMdl = enqMainDao.select(emnSid);
        Enq310Dao dao310 = getEnqPdfData(con, reqMdl, pdfMdl, emnSid, enqMainMdl);

        //アンケート設問情報
        EnqQueMainDao queMainDao = new EnqQueMainDao(con);
        ArrayList<EnqQueMainModel> eqmList = queMainDao.select(emnSid);
        if (eqmList != null) {
            EnqQueSubDao queSubDao = new EnqQueSubDao(con);
            List<Enq310QuestionModel> queList = new ArrayList<Enq310QuestionModel>();

            for (EnqQueMainModel eqmData : eqmList) {
                Enq310QuestionModel queData = new Enq310QuestionModel();
                //設問SID
                queData.setEmnSid(eqmData.getEmnSid());
                //設問連番
                int eqmSeq = eqmData.getEqmSeq();
                queData.setQueSeq(eqmSeq);
                //設問番号
                queData.setNo(eqmData.getEqmQueSec());
                //必須
                queData.setRequire(eqmData.getEqmRequire());
                //設問種別
                queData.setQueKbn(eqmData.getEqmQueKbn());
                queData.setQueKbnName(Enq210Biz.getDspQueType(reqMdl, queData.getQueKbn()));
                //設問
                queData.setQuestion(eqmData.getEqmQuestion());

                int[] ansCount = dao310.getAnswerCount(emnSid, eqmSeq);
                //対象人数 回答人数
                queData.setAnswerCountAr(StringUtil.toCommaFormat(String.valueOf(ansCount[1])));
                //対象人数 回答人数 割合
                int arPer = this.getRatio(ansCount[0], ansCount[1]);
                queData.setAnswerCountArPer(String.valueOf(arPer));
                //対象人数 未回答人数
                queData.setAnswerCountUn(StringUtil.toCommaFormat(String.valueOf(ansCount[2])));
                //対象人数 未回答人数 割合
                queData.setAnswerCountUnPer(String.valueOf(100 - arPer));

                //回答情報
                List<EnqQueSubModel> queSubList
                    = queSubDao.select(emnSid, queData.getQueSeq());
                if (queSubList != null) {
                    List<Enq310QuestionSubModel> subList
                        = dao310.getAnswerSubList(reqMdl, emnSid, eqmSeq, eqmData.getEqmQueKbn());
                    queData.setSubList(subList);
                }
                queList.add(queData);
            }
            pdfMdl.setQueList(queList);
        }

        return pdfMdl;
    }

    /**
    * <br>[機  能] アンケート基本情報のPDFモデルを取得します。
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl request情報
    * @param pdfMdl PDFモデル
    * @param emnSid 回答対象アンケートSID
    * @param enqMainMdl アンケートメインモデル
    * @return dao310
    * @throws SQLException SQL実行時例外
    */
    public Enq310Dao getEnqPdfData(
            Connection con,
            RequestModel reqMdl,
            EnqPdfModel pdfMdl,
            long emnSid,
            EnqMainModel enqMainMdl) throws SQLException {

        EnqCommonBiz enqBiz = new EnqCommonBiz();

        //アンケートSID
        pdfMdl.setEnqPdfSid(enqMainMdl.getEmnSid());
        //重要度
        pdfMdl.setEnq310priority(enqMainMdl.getEmnPriKbn());
        //発信者
        if (enqMainMdl.getEmnSendGrp() > 0) {
            CmnGroupmDao grpDao = new CmnGroupmDao(con);
            CmnGroupmModel grpMdl = grpDao.selectGroup((int) enqMainMdl.getEmnSendGrp());
            pdfMdl.setEnq310sender(grpMdl.getGrpName());
            pdfMdl.setEnq310senderDelFlg(grpMdl.getGrpJkbn() == GSConst.JTKBN_DELETE);
        } else {
            int userSid = (int) enqMainMdl.getEmnSendUsr();
            CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
            CmnUsrmInfModel usrInfMdl = usrInfDao.select(userSid);
            pdfMdl.setEnq310sender(
                    usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());

            CmnUsrmModel usrmModel = new CmnUsrmModel();
            CmnUsrmDao usrmDao = new CmnUsrmDao(con);
            usrmModel = usrmDao.select(userSid);
            pdfMdl.setEnq310senderDelFlg(usrmModel.getUsrJkbn() == GSConst.JTKBN_DELETE);
            pdfMdl.setEnq310senderUkoFlg(usrmModel.getUsrUkoFlg());
        }

        //アンケートタイトル
        pdfMdl.setEnq310enqTitle(enqMainMdl.getEmnTitle());
        //アンケート内容
        pdfMdl.setEnq310enqContent(NullDefault.getString(enqMainMdl.getEmnDescPlain(), ""));
        //回答期限
        pdfMdl.setEnq310ansLimitDate(enqBiz.getStrDate(reqMdl, enqMainMdl.getEmnResEnd()));
        //回答期限 曜日
        pdfMdl.setEnq310ansDayOfWeek(enqMainMdl.getEmnResEnd().getStrWeekJ(reqMdl));
        //結果公開区分
        pdfMdl.setEnq310ansOpen(enqMainMdl.getEmnAnsOpen());

        if (enqMainMdl.getEmnOpenEndKbn() != GSConstEnquete.EMN_OPEN_END_KBN_NON) {
            //結果公開期限
            pdfMdl.setEnq310pubLimitDate(enqBiz.getStrDate(reqMdl, enqMainMdl.getEmnOpenEnd()));
            //結果公開期限 曜日
            pdfMdl.setEnq310pubDayOfWeek(enqMainMdl.getEmnOpenEnd().getStrWeekJ(reqMdl));
        }
        //結果公開期限 開始
        pdfMdl.setEnq310ansPubFrDate(enqBiz.getStrDate(reqMdl, enqMainMdl.getEmnAnsPubStr()));
        //結果公開期限 開始 曜日
        pdfMdl.setEnq310ansPubFrDayOfWeek(enqMainMdl.getEmnAnsPubStr().getStrWeekJ(reqMdl));

        //対象人数を取得する
        Enq310Dao dao310 = new Enq310Dao(con);
        int[] ansCount = dao310.getAnswerCount(emnSid);

        //対象人数 全体
        pdfMdl.setEnq310answerCountAll(StringUtil.toCommaFormat(String.valueOf(ansCount[0])));
        //対象人数 回答人数
        pdfMdl.setEnq310answerCountAr(StringUtil.toCommaFormat(String.valueOf(ansCount[1])));

        int arRatio = getRatio(ansCount[0], ansCount[1]);
        //対象人数 回答人数 割合
        pdfMdl.setEnq310answerCountArPer(String.valueOf(arRatio));
        //対象人数 未回答人数
        pdfMdl.setEnq310answerCountUn(StringUtil.toCommaFormat(String.valueOf(ansCount[2])));
        //対象人数 未回答人数 割合
        pdfMdl.setEnq310answerCountUnPer(String.valueOf(100 - arRatio));
        //注意事項
        GsMessage gsMsg = new GsMessage(reqMdl);
        // --匿名＆結果公開アンケートの時
        if (enqMainMdl.getEmnAnony() == GSConstEnquete.ANONYMUS_ON
         && enqMainMdl.getEmnAnsOpen() == GSConstEnquete.EMN_ANS_OPEN_PUBLIC) {
            pdfMdl.setEnq310notes(gsMsg.getMessage("enq.69"));
        // --匿名アンケートの時
        } else if (enqMainMdl.getEmnAnony() == GSConstEnquete.ANONYMUS_ON) {
            pdfMdl.setEnq310notes(gsMsg.getMessage("enq.31"));
        // --匿名アンケート以外の時
        } else if (enqMainMdl.getEmnAnsOpen() == GSConstEnquete.EMN_ANS_OPEN_PUBLIC) {
            pdfMdl.setEnq310notes(gsMsg.getMessage("enq.32"));
        }
        return dao310;
    }

    /**
    *
    * <br>[機  能]CSVモデルに明細行の値をセットします。
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl アクションフォーム
    * @param con コネクション
    * @param reqMdl リクエスト情報
    * @return EnqCsvModel
    * @throws SQLException 実行時例外
    */
    public ArrayList<EnqCsvModel> getCsvMdlList(
            Enq310ParamModel paramMdl, Connection con, RequestModel reqMdl) throws SQLException {
        long emnSid = paramMdl.getAnsEnqSid();
        ArrayList<EnqCsvModel> enqCsvList = new ArrayList<EnqCsvModel>();
        EnqCsvDao enqCsvDao = new EnqCsvDao(con);
        enqCsvList = enqCsvDao.createEnqCsv(emnSid, reqMdl);
        return enqCsvList;
    }

    /**
    *
    * <br>[機  能]CSVサブモデルに値をセットします。
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl アクションフォーム
    * @param con コネクション
    * @return EnqCsvModel
    * @throws SQLException 実行時例外
    */
    public EnqCsvSubModel getCsvSubMdl(
            Enq310ParamModel paramMdl, Connection con) throws SQLException {
        long emnSid = paramMdl.getAnsEnqSid();
        EnqCsvDao enqCsvDao = new EnqCsvDao(con);
        EnqCsvSubModel enqCsvSubMdl = enqCsvDao.selectEnqCsvSub(emnSid);
        return enqCsvSubMdl;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    public String fileNameCheck(String fileName) {
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
