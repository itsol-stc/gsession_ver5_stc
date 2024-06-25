package jp.groupsession.v2.rsv.rsv100;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CsvEncode;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設利用状況照会画面の検査結果をCSVファイルへ出力する機能を実装したクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv100CsvWriter extends AbstractCSVWriter {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv100CsvWriter.class);
    /** コネクション */
    protected Connection con_ = null;
    /** アクションフォーム */
    protected Rsv100ParamModel paramMdl_ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param paramMdl Rsv100ParamModel
     * @param reqMdl リクエスト情報
     */
    public Rsv100CsvWriter(Connection con, Rsv100ParamModel paramMdl, RequestModel reqMdl) {
        con_ = con;
        paramMdl_ = paramMdl;
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param csvPath 出力先
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(String csvPath) throws CSVException {
        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
        String fileName = GSConstReserve.RSV_CSV_NAME;
        String fileFullPath = IOTools.replaceFileSep(csvPath + File.separator + fileName);
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

        if (paramMdl_.getRsv100CsvOutField() == null) {
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl_);
        //1行分出力
        StringBuilder sb = new StringBuilder();

        int fieldCnt = paramMdl_.getRsv100CsvOutField().length;
        log__.debug("***ヘッダ出力フィールド数:" + fieldCnt);

        for (int i = 0; i < paramMdl_.getRsv100CsvOutField().length; i++) {

            switch (Integer.parseInt(paramMdl_.getRsv100CsvOutField()[i])) {

            case 1:
                //施設ID
                sb.append(gsMsg.getMessage("reserve.55"));
                break;
            case 2:
                //施設
                sb.append(gsMsg.getMessage("cmn.facility"));
                break;
            case 3:
                //ユーザID
                if (paramMdl_.isRsvAdmFlg()) {
                    //システム管理者のみ出力
                    sb.append(gsMsg.getMessage("cmn.user.id"));
                } else {
                    sb.append("");
                }
                break;
            case 4:
                //利用者
                sb.append(gsMsg.getMessage("reserve.73"));
                break;
            case 5:
                //利用目的
                sb.append(gsMsg.getMessage("reserve.72"));
                break;
            case 6:
                //開始日付
                sb.append(gsMsg.getMessage("reserve.rsv100.14"));
                break;
            case 7:
                //開始時刻
                sb.append(gsMsg.getMessage("cmn.starttime"));
                break;
            case 8:
                //終了日付
                sb.append(gsMsg.getMessage("reserve.rsv100.15"));
                break;
            case 9:
                //終了時刻
                sb.append(gsMsg.getMessage("cmn.endtime"));
                break;
            case 10:
                //内容
                sb.append(gsMsg.getMessage("cmn.content"));
                break;
            case 11:
                //編集権限
                sb.append(gsMsg.getMessage("cmn.edit.permissions"));
                break;
            case 12:
                //公開区分
                sb.append(gsMsg.getMessage("cmn.public.kbn"));
                break;
            case 13:
                //利用区分
                sb.append(gsMsg.getMessage("reserve.use.kbn"));
                break;
            case 14:
                //連絡先
                sb.append(gsMsg.getMessage("reserve.contact"));
                break;
            case 15:
                //会議名案内
                sb.append(gsMsg.getMessage("reserve.guide"));
                break;
            case 16:
                //駐車場見込み台数
                sb.append(gsMsg.getMessage("reserve.park.num"));
                break;
            case 17:
                //行先
                sb.append(gsMsg.getMessage("reserve.dest"));
                break;
            case 18:
                //担当部署
                sb.append(gsMsg.getMessage("reserve.busyo"));
                break;
            case 19:
                //担当者名・使用者名
                if (paramMdl_.getRsv100svSelectSisKbn() == GSConstReserve.RSK_KBN_HEYA) {
                    sb.append(gsMsg.getMessage("reserve.use.name.1"));
                } else if (paramMdl_.getRsv100svSelectSisKbn() == GSConstReserve.RSK_KBN_CAR) {
                    sb.append(gsMsg.getMessage("reserve.use.name.2"));
                }
                break;
            case 20:
                //人数
                sb.append(gsMsg.getMessage("reserve.use.num"));
                break;
            default:
                break;
            }

            if (i < paramMdl_.getRsv100CsvOutField().length - 1) {
                sb.append(",");
            }
        }
        pw.println(sb.toString());
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        if (paramMdl_.getRsv100CsvOutField() == null) {
            return;
        }

        try {
            Rsv100Biz biz = new Rsv100Biz(con_, reqMdl_);
            RsvSisYrkDao dao = new RsvSisYrkDao(con_);
            ArrayList<Rsv100SisYrkModel> list = new ArrayList<Rsv100SisYrkModel>();

            //セッション情報を取得
            BaseUserModel usModel = reqMdl_.getSmodel();

            //システム管理者フラグ
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(
                    con_, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

            //検索結果リストを取得
            list = dao.getAllYrkReferenceList(biz.setSearchData(paramMdl_), adminUser, reqMdl_);

            for (Rsv100SisYrkModel mdl : list) {
                //1行分出力
                StringBuilder sb = new StringBuilder();
                RsvCommonBiz rsvBiz = new RsvCommonBiz();
                int powrange = rsvBiz.isWithinPubilicRange(
                        con_, mdl.getRsySisetuSid(), usModel.getUsrsid());
                if (powrange == GSConstReserve.POWRANGE_DEL) {
                    continue;
                }
                for (int i = 0; i < paramMdl_.getRsv100CsvOutField().length; i++) {
                    switch (Integer.parseInt(paramMdl_.getRsv100CsvOutField()[i])) {

                    case 1:
                        //施設ID
                        sb.append(CsvEncode.encString(mdl.getSisetuId()));
                        break;
                    case 2:
                        //施設
                        sb.append(CsvEncode.encString(mdl.getRsySisetu()));
                        break;
                    case 3:
                        //ユーザID
                        if (paramMdl_.isRsvAdmFlg()) {
                            //システム管理者のみ出力
                            sb.append(CsvEncode.encString(mdl.getUserId()));
                        } else {
                            sb.append("");
                        }
                        break;
                    case 4:
                        //利用者
                        sb.append(CsvEncode.encString(mdl.getRsySeiMei()));
                        break;
                    case 5:
                        //利用目的
                        sb.append(CsvEncode.encString(mdl.getRsyContent()));
                        break;
                    case 6:
                        //開始日付
                        sb.append(CsvEncode.encString(mdl.getRsyFrom()));
                        break;
                    case 7:
                        //開始時刻
                        sb.append(CsvEncode.encString(mdl.getRsyFromTime()));
                        break;
                    case 8:
                        //終了日付
                        sb.append(CsvEncode.encString(mdl.getRsyTo()));
                        break;
                    case 9:
                        //終了時刻
                        sb.append(CsvEncode.encString(mdl.getRsyToTime()));
                        break;
                    case 10:
                        //内容
                        if (powrange == GSConstReserve.POWRANGE_IN) {
                            sb.append(CsvEncode.encString(mdl.getRsyBiko()));
                        }
                        break;
                    case 11:
                        //編集権限
                        sb.append(CsvEncode.encString(String.valueOf(mdl.getRsyEdit())));
                        break;
                    case 12:
                        //公開区分
                        sb.append(CsvEncode.encString(String.valueOf(mdl.getRsyPublic())));
                        break;
                    case 13:
                        //利用区分
                        sb.append(CsvEncode.encString(String.valueOf(mdl.getRkyUseKbn())));
                        break;
                    case 14:
                        //連絡先
                        sb.append(CsvEncode.encString(mdl.getRkyContact()));
                        break;
                    case 15:
                        //会議名案内
                        sb.append(CsvEncode.encString(mdl.getRkyGuide()));
                        break;
                    case 16:
                        //駐車場見込み台数
                        sb.append(CsvEncode.encString(mdl.getRkyParkNum()));
                        break;
                    case 17:
                        //行先
                        sb.append(CsvEncode.encString(mdl.getRkyDest()));
                        break;
                    case 18:
                        //担当部署
                        sb.append(CsvEncode.encString(mdl.getRkyBusyo()));
                        break;
                    case 19:
                        //担当者名・使用者名
                        sb.append(CsvEncode.encString(mdl.getRkyName()));
                        break;
                    case 20:
                        //人数
                        sb.append(CsvEncode.encString(mdl.getRkyNum()));
                        break;
                    default:
                        break;
                    }

                    if (i < paramMdl_.getRsv100CsvOutField().length - 1) {
                        sb.append(",");
                    }
                }

                pw.println(sb.toString());
            }



        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            throw new CSVException("SQLの実行に失敗", e);
        }
    }
}
