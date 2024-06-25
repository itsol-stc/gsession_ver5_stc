package jp.groupsession.v2.rsv.rsv270;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CsvEncode;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 施設予約 グループ・施設一括出力画面 施設グループ、施設のエクスポートを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv270CsvWriter extends AbstractCSVWriter {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv270CsvWriter.class);
    /** コネクション */
    protected Connection con_ = null;
    /** アクションフォーム */
    protected Rsv270Form form_ = null;
    /** リクエスト */
    protected HttpServletRequest req_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param form Rsv270Form
     * @param req リクエスト
     */
    public Rsv270CsvWriter(Connection con, Rsv270Form form, HttpServletRequest req) {
        con_ = con;
        form_ = form;
        req_ = req;
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
        String fileName = GSConstReserve.RSV_CSV_NAME_GROUP_ALL;
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

        GsMessage gsMsg = new GsMessage();
        //1行分出力
        StringBuilder sb = new StringBuilder();
        //グループID
        sb.append(gsMsg.getMessage(req_, "cmn.group.id"));
        sb.append(",");
        //グループ名
        sb.append(gsMsg.getMessage(req_, "cmn.group.name"));
        sb.append(",");
        //施設ID
        sb.append(gsMsg.getMessage(req_, "reserve.55"));
        sb.append(",");
        //施設名
        sb.append(gsMsg.getMessage(req_, "cmn.facility.name"));
        sb.append(",");
        //資産管理番号
        sb.append(gsMsg.getMessage(req_, "cmn.asset.register.num"));
        sb.append(",");

        if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_HEYA) {
            //座席数
            sb.append(gsMsg.getMessage(req_, "reserve.128"));
            sb.append(",");
            //喫煙
            sb.append(gsMsg.getMessage(req_, "reserve.132"));
            sb.append(",");
            //重複登録
            sb.append(gsMsg.getMessage(req_, "reserve.136"));
            sb.append(",");
            //予約可能期限
            sb.append(gsMsg.getMessage(req_, "reserve.135"));
            sb.append(",");
            //承認
            sb.append(gsMsg.getMessage(req_, "reserve.appr.set.title"));
            sb.append(",");
            //備考
            sb.append(gsMsg.getMessage(req_, "cmn.memo"));

        } else if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_BUPPIN) {
            //個数
            sb.append(gsMsg.getMessage(req_, "reserve.130"));
            sb.append(",");
            //社外持出し
            sb.append(gsMsg.getMessage(req_, "reserve.133"));
            sb.append(",");
            //重複登録
            sb.append(gsMsg.getMessage(req_, "reserve.136"));
            sb.append(",");
            //予約可能期限
            sb.append(gsMsg.getMessage(req_, "reserve.135"));
            sb.append(",");
            //承認
            sb.append(gsMsg.getMessage(req_, "reserve.appr.set.title"));
            sb.append(",");
            //備考
            sb.append(gsMsg.getMessage(req_, "cmn.memo"));

        } else if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_CAR) {
            //ナンバー
            sb.append(gsMsg.getMessage(req_, "reserve.134"));
            sb.append(",");
            //乗員数
            sb.append(gsMsg.getMessage(req_, "reserve.129"));
            sb.append(",");
            //喫煙
            sb.append(gsMsg.getMessage(req_, "reserve.132"));
            sb.append(",");
            //重複登録
            sb.append(gsMsg.getMessage(req_, "reserve.136"));
            sb.append(",");
            //予約可能期限
            sb.append(gsMsg.getMessage(req_, "reserve.135"));
            sb.append(",");
            //承認
            sb.append(gsMsg.getMessage(req_, "reserve.appr.set.title"));
            sb.append(",");
            //備考
            sb.append(gsMsg.getMessage(req_, "cmn.memo"));

        } else if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_BOOK) {
            //ISBN
            sb.append("ISBN");
            sb.append(",");
            //冊数
            sb.append(gsMsg.getMessage(req_, "reserve.131"));
            sb.append(",");
            //社外持出し
            sb.append(gsMsg.getMessage(req_, "reserve.133"));
            sb.append(",");
            //重複登録
            sb.append(gsMsg.getMessage(req_, "reserve.136"));
            sb.append(",");
            //予約可能期限
            sb.append(gsMsg.getMessage(req_, "reserve.135"));
            sb.append(",");
            //承認
            sb.append(gsMsg.getMessage(req_, "reserve.appr.set.title"));
            sb.append(",");
            //備考
            sb.append(gsMsg.getMessage(req_, "cmn.memo"));

        } else if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_OTHER) {
            //重複登録
            sb.append(gsMsg.getMessage(req_, "reserve.136"));
            sb.append(",");
            //予約可能期限
            sb.append(gsMsg.getMessage(req_, "reserve.135"));
            sb.append(",");
            //承認
            sb.append(gsMsg.getMessage(req_, "reserve.appr.set.title"));
            sb.append(",");
            //備考
            sb.append(gsMsg.getMessage(req_, "cmn.memo"));
        }

        pw.println(sb.toString());
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        try {
            RsvSisDataDao dao = new RsvSisDataDao(con_);
            ArrayList<Rsv270SisGrpModel> list = new ArrayList<Rsv270SisGrpModel>();

            //検索結果リストを取得
            list = dao.getCsvOutGrpSisetuData(form_.getRsv270SelectedSisetuKbn());

            for (Rsv270SisGrpModel mdl : list) {
                //1行分出力
                StringBuilder sb = new StringBuilder();

                //グループID
                sb.append(CsvEncode.encString(mdl.getRsgId()));
                sb.append(",");
                //グループ名
                sb.append(CsvEncode.encString(mdl.getRsgName()));
                sb.append(",");
                //施設ID
                sb.append(CsvEncode.encString(mdl.getRsdId()));
                sb.append(",");
                //施設名
                sb.append(CsvEncode.encString(mdl.getRsdName()));
                sb.append(",");
                //資産管理番号
                sb.append(CsvEncode.encString(mdl.getRsdSnum()));
                sb.append(",");

                if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_HEYA) {
                    //座席数
                    sb.append(CsvEncode.encString(mdl.getRsdProp1()));
                    sb.append(",");
                    //喫煙
                    sb.append(CsvEncode.encString(mdl.getRsdProp2()));
                    sb.append(",");
                    //重複登録
                    sb.append(CsvEncode.encString(mdl.getRsdProp7()));
                    sb.append(",");
                    //予約可能期限
                    sb.append(CsvEncode.encString(mdl.getRsdProp6()));
                    sb.append(",");
                    //承認
                    sb.append(CsvEncode.encString(String.valueOf(mdl.getRsdApprKbn())));
                    sb.append(",");
                    //備考
                    sb.append(CsvEncode.encString(mdl.getRsdBiko()));

                } else if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_BUPPIN) {
                    //個数
                    sb.append(CsvEncode.encString(mdl.getRsdProp1()));
                    sb.append(",");
                    //社外持出し
                    sb.append(CsvEncode.encString(mdl.getRsdProp3()));
                    sb.append(",");
                    //重複登録
                    sb.append(CsvEncode.encString(mdl.getRsdProp7()));
                    sb.append(",");
                    //予約可能期限
                    sb.append(CsvEncode.encString(mdl.getRsdProp6()));
                    sb.append(",");
                    //承認
                    sb.append(CsvEncode.encString(String.valueOf(mdl.getRsdApprKbn())));
                    sb.append(",");
                    //備考
                    sb.append(CsvEncode.encString(mdl.getRsdBiko()));

                } else if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_CAR) {
                    //ナンバー
                    sb.append(CsvEncode.encString(mdl.getRsdProp4()));
                    sb.append(",");
                    //乗員数
                    sb.append(CsvEncode.encString(mdl.getRsdProp1()));
                    sb.append(",");
                    //喫煙
                    sb.append(CsvEncode.encString(mdl.getRsdProp2()));
                    sb.append(",");
                    //重複登録
                    sb.append(CsvEncode.encString(mdl.getRsdProp7()));
                    sb.append(",");
                    //予約可能期限
                    sb.append(CsvEncode.encString(mdl.getRsdProp6()));
                    sb.append(",");
                    //承認
                    sb.append(CsvEncode.encString(String.valueOf(mdl.getRsdApprKbn())));
                    sb.append(",");
                    //備考
                    sb.append(CsvEncode.encString(mdl.getRsdBiko()));

                } else if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_BOOK) {
                    //ISBN
                    sb.append(CsvEncode.encString(mdl.getRsdProp5()));
                    sb.append(",");
                    //冊数
                    sb.append(CsvEncode.encString(mdl.getRsdProp1()));
                    sb.append(",");
                    //社外持出し
                    sb.append(CsvEncode.encString(mdl.getRsdProp3()));
                    sb.append(",");
                    //重複登録
                    sb.append(CsvEncode.encString(mdl.getRsdProp7()));
                    sb.append(",");
                    //予約可能期限
                    sb.append(CsvEncode.encString(mdl.getRsdProp6()));
                    sb.append(",");
                    //承認
                    sb.append(CsvEncode.encString(String.valueOf(mdl.getRsdApprKbn())));
                    sb.append(",");
                    //備考
                    sb.append(CsvEncode.encString(mdl.getRsdBiko()));

                } else if (form_.getRsv270SelectedSisetuKbn() == GSConstReserve.RSK_KBN_OTHER) {
                    //重複登録
                    sb.append(CsvEncode.encString(mdl.getRsdProp7()));
                    sb.append(",");
                    //予約可能期限
                    sb.append(CsvEncode.encString(mdl.getRsdProp6()));
                    sb.append(",");
                    //承認
                    sb.append(CsvEncode.encString(String.valueOf(mdl.getRsdApprKbn())));
                    sb.append(",");
                    //備考
                    sb.append(CsvEncode.encString(mdl.getRsdBiko()));
                }

                pw.println(sb.toString());
            }

        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            throw new CSVException("SQLの実行に失敗", e);
        }
    }
}
