package jp.groupsession.v2.enq.csv;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CsvEncode;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケートのCSV出力を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqCsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqCsvWriter.class);
    /** コネクション */
    protected Connection con_ = null;
    /** CSVモデル */
    protected ArrayList<EnqCsvModel> csvMdlList_ = null;
    /** CSVサブモデル */
    protected EnqCsvSubModel csvSubMdl_ = null;
    /** リクエストモデル */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param enqCsvMdListl EnqCsvModel
     * @param enqCsvSubMdl EnqCsvSubModel
     * @param reqMdl リクエスト
     */
    public EnqCsvWriter(Connection con,
            ArrayList<EnqCsvModel> enqCsvMdListl,
            EnqCsvSubModel enqCsvSubMdl,
            RequestModel reqMdl) {
        con_ = con;
        csvMdlList_ = enqCsvMdListl;
        csvSubMdl_ = enqCsvSubMdl;
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param csvPath 出力先
     * @param csvFilePath ファイルパス
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(String csvPath, String csvFilePath) throws CSVException {

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
        String fileFullPath = IOTools.replaceFileSep(csvPath + File.separator + csvFilePath);

        //出力初期セット
        setCsvPath(fileFullPath);
        log__.debug("CSVファイルのパス = " + fileFullPath);

        log__.debug("CSV作成開始 --");
        write();
        log__.debug("-- CSV作成終了");
    }

    /**
     * <br>[機  能] CSV生成 値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    public void create(PrintWriter pw) throws CSVException {

        //ヘッダ
        __writeHeader(pw);

        //明細
        __writeItem(pw);
    }

    /**
     * <p>ヘッダ部分を生成します。
     * @param pw PrintWriter
     */
    private void __writeHeader(PrintWriter pw) {

        GsMessage gsMsg = new GsMessage();
        //1行分出力
        StringBuilder sb = new StringBuilder();
        //タイトル
        sb.append(CsvEncode.encString(gsMsg.getMessage("cmn.title")));
        sb.append(",");
        //グループ
        sb.append(CsvEncode.encString(gsMsg.getMessage("cmn.group")));
        sb.append(",");
        //ユーザ
        sb.append(CsvEncode.encString(gsMsg.getMessage("cmn.user")));
        sb.append(",");
        //回答/未回答
        sb.append(gsMsg.getMessage("enq.22") + "/" + gsMsg.getMessage("enq.21"));
        sb.append(",");
        pw.print(sb.toString());
        //設問
        for (int idx = 0;
                idx < csvSubMdl_.getQuestion().size(); idx++) {
            if (csvSubMdl_.getQuestion().get(idx) != null) {
                sb = new StringBuilder();
                sb.append(CsvEncode.encString(
                        gsMsg.getMessage("enq.12") + csvSubMdl_.getQuestion().get(idx)));
                if (idx < csvSubMdl_.getQuestion().size()) {
                    sb.append(",");
                }
                pw.print(sb.toString());
            }
        }
        pw.println("");
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        GsMessage gsMsg = new GsMessage(reqMdl_);

        for (EnqCsvModel enqCsvMdl: csvMdlList_) {

                StringBuilder sb = new StringBuilder();
                //タイトル
                sb.append(CsvEncode.encString(csvSubMdl_.getEnqTitle()));
                sb.append(",");

                if (csvSubMdl_.getAnonyFlg() == 0) {
                //グループ
                sb.append(CsvEncode.encString(enqCsvMdl.getGroup()));
                sb.append(",");
                //ユーザ
                sb.append(CsvEncode.encString(enqCsvMdl.getUser()));
                sb.append(",");
                } else {
                    //グループ
                    sb.append(gsMsg.getMessage("wml.wml040kn.06"));
                    sb.append(",");
                    //ユーザ
                    sb.append(gsMsg.getMessage("wml.wml040kn.06"));
                    sb.append(",");
                }
                //回答 or 未回答
                if (enqCsvMdl.getStatusFlg() == 1) {
                    sb.append(gsMsg.getMessage("enq.22"));
                } else {
                    sb.append(gsMsg.getMessage("enq.21"));
                }
                sb.append(",");
                pw.print(sb.toString());

            //回答
            for (int idx = 0;
                    idx < enqCsvMdl.getAnsValue().size(); idx++) {
                sb = new StringBuilder();
                sb.append(CsvEncode.encString(enqCsvMdl.getAnsValue().get(idx)));
                if (idx < enqCsvMdl.getAnsValue().size()) {
                    sb.append(",");
                }
                pw.print(sb.toString());
            }
                pw.println("");
        }

    }

}
