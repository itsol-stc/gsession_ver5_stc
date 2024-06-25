package jp.groupsession.v2.ntp.ntp130;

import java.io.PrintWriter;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;

/**
 * <br>[機  能] 商品情報のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ShohinCsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public ShohinCsvRecordListenerImpl(PrintWriter pw) {
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

        NtpShohinModel csvModel = (NtpShohinModel) model;

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //商品コード
        sb.append(CsvEncode.encString(csvModel.getNhnCode()));
        sb.append(",");

        //商品名
        sb.append(CsvEncode.encString(csvModel.getNhnName()));
        sb.append(",");

        //販売価格
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getNhnPriceSale())));
        sb.append(",");

        //原価価格
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getNhnPriceCost())));
        sb.append(",");

        //補足事項
        sb.append(CsvEncode.encString(csvModel.getNhnHosoku()));

        pw__.println(sb.toString());
    }
}