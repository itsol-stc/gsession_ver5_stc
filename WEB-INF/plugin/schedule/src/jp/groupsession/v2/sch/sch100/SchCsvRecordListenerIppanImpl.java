package jp.groupsession.v2.sch.sch100;

import java.io.PrintWriter;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュール一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchCsvRecordListenerIppanImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;
    /** フォーム */
    private Sch100Form form__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @param form フォーム
     */
    public SchCsvRecordListenerIppanImpl(PrintWriter pw, Sch100Form form) {
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

        if (form__.getSch100CsvOutField() == null) {
            return;
        }

        ScheduleCsvModel csvModel = (ScheduleCsvModel) model;
//        GsMessage gsMsg = new GsMessage();

        //1行分出力
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < form__.getSch100CsvOutField().length; i++) {

            switch(Integer.parseInt(form__.getSch100CsvOutField()[i])) {

            case 1:
                //ログインID
                sb.append(CsvEncode.encString(csvModel.getLoginId()));
                break;
            case 2:
                //グループID
                sb.append(CsvEncode.encString(csvModel.getGroupId()));
                break;
            case 3:
                //氏名
                sb.append(CsvEncode.encString(csvModel.getUsrName()));
                break;
            case 4:
                //開始日付
                sb.append(CsvEncode.encString(csvModel.getScdFrDateStr()));
                break;
            case 5:
                String scdFrTimeStr = "";
                if (csvModel.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                    //時間指定区分ありのときのみ出力
                    scdFrTimeStr = csvModel.getScdFrTimeStr();
                }
                //開始時刻
                sb.append(CsvEncode.encString(scdFrTimeStr));
                break;
            case 6:
                //終了日付
                sb.append(CsvEncode.encString(csvModel.getScdToDateStr()));
                break;
            case 7:
                String scdToTimeStr = "";
                if (csvModel.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                    //時間指定区分ありのときのみ出力
                    scdToTimeStr = csvModel.getScdToTimeStr();
                }
                //終了時刻
                sb.append(CsvEncode.encString(scdToTimeStr));
                break;
            case 8:
                //タイトル
                sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdTitle())));
                break;
            case 9:
                //タイトル色
                sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdBgcolor())));
                break;
            case 10:
                //内容
                sb.append(CsvEncode.encString(csvModel.getScdValue()));
                break;
            case 11:
                //備考
                sb.append(CsvEncode.encString(csvModel.getScdBiko()));
                break;
            case 12:
                //編集権限
                sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdEdit())));

//                switch (csvModel.getScdEdit()) {
//                case GSConstSchedule.EDIT_CONF_NONE:
//                    //制限無し
//                    String textNolimit = gsMsg.getMessage(req__, "cmn.nolimit");
//                    sb.append(CsvEncode.encString(textNolimit));
//                    break;
//                case GSConstSchedule.EDIT_CONF_OWN:
//                    //本人・登録者のみ
//                    String textMyself = gsMsg.getMessage(req__,
//                            "cmn.only.principal.or.registant");
//                    sb.append(CsvEncode.encString(textMyself));
//                    break;
//                case GSConstSchedule.EDIT_CONF_GROUP:
//                    //所属グループ・登録者のみ
//                    String textGroup = gsMsg.getMessage(req__,
//                                                        "cmn.only.affiliation.group.membership");
//                    sb.append(CsvEncode.encString(textGroup));
//                    break;
//                default:
//                    break;
//                }
                break;
            case 13:
                //公開
                sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdPublic())));

//                switch (csvModel.getScdPublic()) {
//                case GSConstSchedule.DSP_PUBLIC:
//                    //公開する
//                    String textPublish = gsMsg.getMessage(req__, "cmn.publish");
//                    sb.append(CsvEncode.encString(textPublish));
//                    break;
//                case GSConstSchedule.DSP_NOT_PUBLIC:
//                    //公開しない
//                    String textNotPublish = gsMsg.getMessage(req__, "cmn.not.publish");
//                    sb.append(CsvEncode.encString(textNotPublish));
//                    break;
//                case GSConstSchedule.DSP_YOTEIARI:
//                    String textYoteiari;
//                    textYoteiari = gsMsg.getMessage(req__, "schedule.src.9");
//
//                    sb.append(CsvEncode.encString(textYoteiari));
//                    break;
//                default:
//                    break;
//                }
                break;
            case 14:
                //時間指定区分
                sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdDaily())));

//                switch (csvModel.getScdDaily()) {
//                case GSConstSchedule.TIME_EXIST:
//                    //区分あり
//                    String textPublish = gsMsg.getMessage(req__, "address.adr010.contact.5");
//                    sb.append(CsvEncode.encString(textPublish));
//                    break;
//                case GSConstSchedule.TIME_NOT_EXIST:
//                    //区分なし
//                    String textNotPublish = gsMsg.getMessage(req__, "cmn.no");
//                    sb.append(CsvEncode.encString(textNotPublish));
//                    break;
//                default:
//                    break;
//                }
                break;
            case 15:
                //登録者氏名
                sb.append(CsvEncode.encString(csvModel.getAddUsrName()));
                break;
            case 16:
                //更新者氏名
                sb.append(CsvEncode.encString(csvModel.getEdtUsrName()));
                break;
            default:
                break;
            }

            if (i < form__.getSch100CsvOutField().length - 1) {
                sb.append(",");
            }
        }

        //スケジュールSID
//        sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdSid())));
//        sb.append(",");

        //ユーザSID
//        sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdUsrSid())));
//        sb.append(",");

        //スケジュールグループID
//        sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdGrpSid())));
//        sb.append(",");

        //ユーザ区分
//        sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdUsrKbn())));
//        sb.append(",");

        //背景区分
//        sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdBgcolor())));
//        sb.append(",");

        //登録者ID
//        sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdAuid())));
//        sb.append(",");

        //更新者ID
//        sb.append(CsvEncode.encString(String.valueOf(csvModel.getScdEuid())));
//        sb.append(",");

        pw__.println(sb.toString());
    }
}