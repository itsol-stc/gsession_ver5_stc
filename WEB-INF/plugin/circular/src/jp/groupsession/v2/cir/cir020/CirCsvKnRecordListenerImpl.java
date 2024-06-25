package jp.groupsession.v2.cir.cir020;

import java.io.PrintWriter;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cir.cir020.model.CirCsvKnExpModel;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 回覧板一括ダウンロード用の回覧先一覧(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirCsvKnRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public CirCsvKnRecordListenerImpl(PrintWriter pw) {
        pw__ = pw;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {

        CirCsvKnExpModel csvModel = (CirCsvKnExpModel) model;

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //社員/職員番号
        sb.append(CsvEncode.encString(
                NullDefault.getString(csvModel.getCirCsvKnSyainNum(), "")));
        sb.append(",");

        //氏名
        sb.append(CsvEncode.encString(csvModel.getCirCsvKnName()));
        sb.append(",");

        //役職
        sb.append(CsvEncode.encString(
                NullDefault.getString(csvModel.getCirCsvKnPost(), "")));
        sb.append(",");

        //確認
        sb.append(CsvEncode.encString(
                NullDefault.getString(csvModel.getCirCsvKnDate(), "")));
        sb.append(",");

        //メモ
        sb.append(CsvEncode.encString(
                NullDefault.getString(csvModel.getCirCsvKnMemo(), "")));
        sb.append(",");

        //添付ファイル
        sb.append(CsvEncode.encString(
                NullDefault.getString(csvModel.getCirCsvKnTmpFile(), "")));
        sb.append(",");

        //最終更新日時
        sb.append(CsvEncode.encString(
                NullDefault.getString(csvModel.getCirCsvKnEditDate(), "")));

        pw__.println(sb.toString());
    }
}