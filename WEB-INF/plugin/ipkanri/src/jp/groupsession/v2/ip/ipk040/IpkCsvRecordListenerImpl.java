package jp.groupsession.v2.ip.ipk040;

import java.io.PrintWriter;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.model.IpkanriCsvModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] IP管理 IPアドレス情報一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkCsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @param reqMdl リクエスト情報
     */
    public IpkCsvRecordListenerImpl(PrintWriter pw, RequestModel reqMdl) {
        pw__ = pw;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model)
    throws CSVException {

        IpkanriCsvModel csvModel = (IpkanriCsvModel) model;
        String name = "";
        //1行分出力
        StringBuilder sb = new StringBuilder();

        //ネットワーク名
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getNetName())));
        sb.append(",");

        //ネットワークアドレス
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getNetNetad())));
        sb.append(",");

        //サブネットマスク
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getNetSabnet())));
        sb.append(",");

        //IPアドレス
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getIadIpad())));
        sb.append(",");

        //マシン名
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getIadName())));
        sb.append(",");

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textSiyou = gsMsg.getMessage("cmn.use");
        String textMisiyou = gsMsg.getMessage("cmn.unused");

        //使用状況
        if (csvModel.getIadUseKbn().equals(IpkConst.USEDKBN_MISIYOU)) {
            sb.append(CsvEncode.encString(textMisiyou));
        } else if (csvModel.getIadUseKbn().equals(IpkConst.USEDKBN_SIYOU)) {
            sb.append(CsvEncode.encString(textSiyou));
        }
        sb.append(",");

        //使用者
        for (int i = 0; i < csvModel.getUserSeiMei().size(); i++) {
            if (i == 0) {
                name += csvModel.getUserSeiMei().get(i);
            } else {
                name += "," + csvModel.getUserSeiMei().get(i);
            }
        }
        sb.append(CsvEncode.encString(name));
        sb.append(",");
        //コメント
                StringUtilHtml.transToText(csvModel.getIadMsg());
        sb.append(CsvEncode.encString(String.valueOf(
                StringUtilHtml.transToText(csvModel.getIadMsg()))));
        sb.append(",");
        //資産管理番号
        sb.append(CsvEncode.encString(csvModel.getIadPrtMngNum()));

        pw__.println(sb.toString());
    }

    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }

    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

}