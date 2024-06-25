package jp.groupsession.v2.tcd.csv;

import java.io.PrintWriter;
import java.math.RoundingMode;
import java.util.List;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardUtil;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;

/**
 * <br>[機  能] タイムカード情報一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdCsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;
    /** 入力単位 */
    private int interval__ = 1;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 休日区分情報一覧 */
    List<TcdHolidayInfModel> holList__ = null;


    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @param interval 入力単位
     * @param reqMdl リクエスト情報
     */
    public TcdCsvRecordListenerImpl(PrintWriter pw, int interval, RequestModel reqMdl) {
        pw__ = pw;
        interval__ = interval;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {
        TcdCsvModel csvModel = (TcdCsvModel) model;
//        //1行分出力
        StringBuilder sb = new StringBuilder();
        //氏名
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getUserName())));
        sb.append(",");
        //社員/職員番号
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getSyainNo())));
        sb.append(",");
        //日付
        sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(csvModel.getTcdDate())));
        sb.append(",");
        //時間帯
        if (csvModel.getTtiRyaku() != null) {
            sb.append(CsvEncode.encString(String.valueOf(csvModel.getTtiRyaku())));
        } else {
            sb.append("");
        }

        sb.append(",");
//      打刻始業時刻
        sb.append(CsvEncode.encString(TimecardUtil.getTimeString(csvModel.getTcdStrikeIntime())));
        sb.append(",");
//      打刻終業時刻
        if (csvModel.getTcdStrikeIntime() != null && csvModel.getTcdStrikeOuttime() != null
                && csvModel.getTcdStrikeIntime().compareTo(
                        csvModel.getTcdStrikeOuttime()) == UDate.LARGE) {
            sb.append(CsvEncode.encString(
                    TimecardUtil.getTimeString(csvModel.getTcdStrikeOuttime(), 24)));
        } else {
            sb.append(CsvEncode.encString(
                    TimecardUtil.getTimeString(csvModel.getTcdStrikeOuttime())));
        }
        sb.append(",");

//      始業時刻
        sb.append(CsvEncode.encString(
                TimecardUtil.getTimeString(
                        TimecardBiz.adjustIntime(csvModel.getTcdIntime(), interval__))));
        sb.append(",");
//      終業時刻
        if (csvModel.getTcdOuttime() != null
                && csvModel.getTcdIntime().compareTo(csvModel.getTcdOuttime()) == UDate.LARGE) {
            sb.append(CsvEncode.encString(
                    TimecardUtil.getTimeString(
                            TimecardBiz.adjustOuttime(csvModel.getTcdOuttime(), interval__), 24)));
        } else {
            sb.append(CsvEncode.encString(
                    TimecardUtil.getTimeString(
                            TimecardBiz.adjustOuttime(csvModel.getTcdOuttime(), interval__))));
        }
        sb.append(",");
//      備考
        sb.append(CsvEncode.encString(csvModel.getTcdBiko()));
        sb.append(",");

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String chikoku = gsMsg.getMessage("tcd.18");
        String soutai = gsMsg.getMessage("tcd.22");

//      遅刻
        if (csvModel.getTcdChkkbn() == GSConstTimecard.CHK_KBN_SELECT) {
            sb.append(CsvEncode.encString(chikoku));
        } else {
            sb.append("");
        }
        sb.append(",");
//      早退
        if (csvModel.getTcdSoukbn() == GSConstTimecard.SOU_KBN_SELECT) {
            sb.append(CsvEncode.encString(soutai));
        } else {
            sb.append("");
        }
        sb.append(",");

//      休暇理由
        sb.append(CsvEncode.encString(csvModel.getThiName()));
            sb.append(",");
        if (csvModel.getThiHoltotalKbn() == GSConstTimecard.HOL_KBN_UNSELECT) {
            sb.append(CsvEncode.encString(csvModel.getTcdHolother()));
        } else {
            sb.append("");
        }
        sb.append(",");

//      休暇日数
        if (csvModel.getTcdHolcnt() != null) {
            sb.append(CsvEncode.encString(TimecardBiz.convertDispBigDecimal(
                    csvModel.getTcdHolcnt(), 1, RoundingMode.UNNECESSARY).toString()));

        } else {
            sb.append("");
        }

        pw__.println(sb.toString());
    }
}