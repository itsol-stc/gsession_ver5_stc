package jp.groupsession.v2.ntp.ntp060;

import java.io.PrintWriter;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 案件情報のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp060CsvRecordListenerIppanImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    public Ntp060CsvRecordListenerIppanImpl(PrintWriter pw) {
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

        Ntp060AnkenModel csvModel = (Ntp060AnkenModel) model;

        //1行分出力
        StringBuffer sb = new StringBuffer();
        //案件コード
        sb.append(CsvEncode.encString(csvModel.getNanCode()));
        sb.append(",");
        //案件名
        sb.append(CsvEncode.encString(csvModel.getNanName()));
        sb.append(",");
        //案件詳細
        sb.append(CsvEncode.encString(csvModel.getNanDetial()));
        sb.append(",");
        //企業コード
        sb.append(CsvEncode.encString(csvModel.getNtp060CompanyCode()));
        sb.append(",");
        //業務コード
        sb.append(CsvEncode.encString(csvModel.getNtp060GyomuCode()));
        sb.append(",");
        //プロセスコード
        sb.append(CsvEncode.encString(csvModel.getNtp060ProcessCode()));
        sb.append(",");
        //見込度
        String mikomiStr;
        switch (csvModel.getNanMikomi()) {
        case 0:
            mikomiStr = "10";
            break;
        case 1:
            mikomiStr = "30";
            break;
        case 2:
            mikomiStr = "50";
            break;
        case 3:
            mikomiStr = "70";
            break;
        case 4:
            mikomiStr = "100";
            break;
        default:
            mikomiStr = "10";
            break;
        }
        sb.append(mikomiStr);
        sb.append(",");
        //見積金額
        sb.append(CsvEncode.encString(csvModel.getNtp060KinMitumori()));
        sb.append(",");
        //提出日付
        sb.append(CsvEncode.encString(csvModel.getNtp060MitumoriDate()));
        sb.append(",");
        //受注金額
        sb.append(CsvEncode.encString(csvModel.getNtp060KinJutyu()));
        sb.append(",");
        //受注日付
        sb.append(CsvEncode.encString(csvModel.getNtp060JutyuDate()));
        sb.append(",");
        //商談結果
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getNanSyodan())));
        sb.append(",");
        //顧客源泉コード
        sb.append(CsvEncode.encString(csvModel.getNtp060ContactName()));
        sb.append(",");
        //状態
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getNanState())));

        pw__.println(sb.toString());
    }
}