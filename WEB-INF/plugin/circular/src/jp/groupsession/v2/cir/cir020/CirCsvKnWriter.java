package jp.groupsession.v2.cir.cir020;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir020.model.Cir020KnDataSearchModel;
import jp.groupsession.v2.cir.cir020.model.CirCsvKnExpModel;
import jp.groupsession.v2.cir.dao.CircularDao;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板一括ダウンロード用の回覧先一覧(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirCsvKnWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirCsvKnWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** リクエストモデル */
    public RequestModel reqMdl__ = null;

    /** 回覧先一覧検索モデル */
    private Cir020KnDataSearchModel mdl__ = null;

    /**
     * <p>コンストラクタ
     * @param mdl 回覧先一覧検索モデル
     * @param reqMdl リクエストモデル
     */
    public CirCsvKnWriter(Cir020KnDataSearchModel mdl, RequestModel reqMdl) {
        mdl__ = mdl;
        reqMdl__ = reqMdl;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @param fileName ファイル名
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath, String fileName)
    throws CSVException {

        setCon(con);

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
        String fileFullPath = IOTools.replaceFileSep(csvPath + File.separator + fileName);
        log__.debug("CSVファイルのパス = " + fileFullPath);

        //出力初期セット
        setCsvPath(fileFullPath);

        log__.debug("開始");
        write();
        log__.debug("終了");
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

        GsMessage gsMsg = new GsMessage(reqMdl__);

        String strHeader =
                gsMsg.getMessage("cmn.employee.staff.number")
                + ","
                + gsMsg.getMessage("cmn.name")
                + ","
                + gsMsg.getMessage("cmn.post")
                + ","
                + gsMsg.getMessage("cmn.check")
                + ","
                + gsMsg.getMessage("cir.11")
                + ","
                + gsMsg.getMessage("cmn.attach.file")
                + ","
                + gsMsg.getMessage("cmn.last.modified");

        pw.println(strHeader);
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        CirCsvKnRecordListenerImpl rl = new CirCsvKnRecordListenerImpl(pw);
        Connection con = getCon();
        try {

            CircularDao dao = new CircularDao(con);
            List<CircularDspModel> cirKnDataList = dao.getMemberInfo(mdl__);

            HashMap <Integer, ArrayList<CmnBinfModel>> userTmpBinHash =
                    dao.getUserTempFileNameHash(mdl__.getCirSid());

            //ユーザごとのデータ
            for (CircularDspModel dspMdl : cirKnDataList) {
                //CSV用のモデルにセットする
                CirCsvKnExpModel csvMdl = __getCsvExpMdl(dspMdl, userTmpBinHash);
                rl.setRecord(csvMdl);
            }

        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            throw new CSVException("SQLの実行に失敗", e);
        }
    }

    /**
     * <br>[機  能] エクスポート用の回覧先データモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirMdl 回覧板データ
     * @param userTmpBinHash 添付ファイル情報
     * @return エクスポートデータモデル
     */
    private CirCsvKnExpModel __getCsvExpMdl(
            CircularDspModel cirMdl,
            HashMap <Integer, ArrayList<CmnBinfModel>> userTmpBinHash) {

        CirCsvKnExpModel ret = new CirCsvKnExpModel();

        //社員・職員番号
        ret.setCirCsvKnSyainNum(cirMdl.getSyainNo());
        //氏名
//        ret.setCirCsvKnName(cirMdl.getUsiSei() + " " + cirMdl.getUsiMei());
        ret.setCirCsvKnName(cirMdl.getCacName());
        //役職
        ret.setCirCsvKnPost(cirMdl.getPosName());

        //確認フラグ
        if (cirMdl.getCvwConf() == GSConstCircular.CONF_OPEN) {
            //確認
            ret.setCirCsvKnDate(UDateUtil.getSlashYYMD(cirMdl.getCvwConfDate()) + "  "
                    + UDateUtil.getSeparateHMS(cirMdl.getCvwConfDate()));

            //最終更新日時
            ret.setCirCsvKnEditDate(UDateUtil.getSlashYYMD(cirMdl.getCvwEdate()) + "  "
                    + UDateUtil.getSeparateHMS(cirMdl.getCvwEdate()));
        }

        //メモ
        ret.setCirCsvKnMemo(cirMdl.getCvwMemo());

        //添付ファイル情報をセット
        List<CmnBinfModel> binList = userTmpBinHash.get(cirMdl.getCacSid());
        StringBuilder strFileName = new StringBuilder();
        if (binList != null && binList.size() > 0) {
            int firstFlg = 0;
            for (CmnBinfModel binMdl : binList) {
                if (firstFlg != 0) {
                    strFileName.append("\n");
                }
                strFileName.append(binMdl.getBinFileName());
                strFileName.append(binMdl.getBinFileSizeDsp());
                firstFlg = 1;
            }
        }
        //添付ファイル
        ret.setCirCsvKnTmpFile(strFileName.toString());

        return ret;
    }
}