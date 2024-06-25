package jp.groupsession.v2.ntp.ntp100;

import java.io.PrintWriter;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 日報一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NtpCsvRecordListenerIppanImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;
    /** フォーム */
    private Ntp100Form form__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @param form フォーム
     */
    public NtpCsvRecordListenerIppanImpl(PrintWriter pw, Ntp100Form form) {
        pw__ = pw;

        form__ = form;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {

        if (form__.getNtp100CsvOutField() == null) {
            return;
        }

        NippouCsvModel csvModel = (NippouCsvModel) model;
//        GsMessage gsMsg = new GsMessage();

        //1行分出力
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < form__.getNtp100CsvOutField().length; i++) {

            switch (Integer.parseInt(form__.getNtp100CsvOutField()[i])) {

            case 1:
                //ユーザID
                sb.append(CsvEncode.encString(csvModel.getUsrLgId()));
                break;
            case 2:
                //ユーザ名
                sb.append(CsvEncode.encString(csvModel.getUsrName()));
                break;
            case 3:
                //報告日
                sb.append(CsvEncode.encString(csvModel.getNtpDateStr()));
                break;
            case 4:
               //開始時刻
                sb.append(CsvEncode.encString(csvModel.getNtpFrTimeStr()));
                break;
            case 5:
                //終了時刻
                sb.append(CsvEncode.encString(csvModel.getNtpToTimeStr()));
                break;
            case 6:
                //案件コード
                String nanCode = "";
                if (!StringUtil.isNullZeroStringSpace(csvModel.getAnkenCode())) {
                    nanCode = csvModel.getAnkenCode();
                }
                sb.append(CsvEncode.encString(nanCode));
                break;
            case 7:
                //企業コード
                String acoCode = "";
                if (!StringUtil.isNullZeroStringSpace(csvModel.getAcoCode())) {
                    acoCode = String.valueOf(csvModel.getAcoCode());
                }
                sb.append(CsvEncode.encString(acoCode));
                break;
            case 8:
//                //企業 拠点SID
//                String abaSid = "";
//                if (csvModel.getAbaSid() > 0) {
//                    abaSid = String.valueOf(csvModel.getAbaSid());
//                }
//                sb.append(CsvEncode.encString(abaSid));
//                break;

                //タイトル
                sb.append(CsvEncode.encString(String.valueOf(csvModel.getNipTitle())));
                break;
            case 9:
                //タイトル色
                sb.append(CsvEncode.encString(String.valueOf(csvModel.getNipTitleClo())));
                break;
            case 10:
                //活動分類コード
                String kbunruiCode = "";
                if (!StringUtil.isNullZeroStringSpace(csvModel.getKbunruiCode())) {
                    kbunruiCode = String.valueOf(csvModel.getKbunruiCode());
                }
                sb.append(CsvEncode.encString(kbunruiCode));
                break;
            case 11:
                //活動方法コード
                String khouhouCode = "";
                if (!StringUtil.isNullZeroStringSpace(csvModel.getKhouhouCode())) {
                    khouhouCode = String.valueOf(csvModel.getKhouhouCode());
                }
                sb.append(CsvEncode.encString(khouhouCode));
                break;
            case 12:
                //内容
                sb.append(CsvEncode.encString(csvModel.getNipDetail()));
                break;
            case 13:
                //見込み度
                sb.append(CsvEncode.encString(String.valueOf(csvModel.getNipMikomi())));
                break;
            default:
                break;
            }

            if (i < form__.getNtp100CsvOutField().length - 1) {
                sb.append(",");
            }
        }

        pw__.println(sb.toString());
    }
}