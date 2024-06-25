package jp.groupsession.v2.ip.ipk040;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.dao.IpkAddAdmDao;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.ipk050.Ipk050Biz;
import jp.groupsession.v2.ip.model.IpkAddAdmModel;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.IpkanriCsvModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] IPアドレス情報一覧(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkCsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkCsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** IPアドレス情報一覧ファイル名 */
    public static final String FILE_NAME = "ipkanriList.csv";

    /** 検索条件 */
    private IpkAddModel searchAddModel__ = null;

    /** 検索条件 */
    private IpkAddAdmModel searchAddAdmModel__ = null;

    /** 実行者SID */
    private int sessionUserSid__;

    /** リクエスト情報 */
    private RequestModel reqMdl__;

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @param reqMdl リクエスト情報
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath, RequestModel reqMdl)
    throws CSVException {

        setCon(con);
        setReqMdl(reqMdl);

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
        String fileName = FILE_NAME;
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
        __writeHeader(pw, reqMdl__);

        //明細
        __writeItem(pw, reqMdl__);
    }

    /**
     * <br>[機  能] ヘッダ部分を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @param reqMdl リクエスト情報
     */
    private void __writeHeader(PrintWriter pw, RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textNetName = gsMsg.getMessage("ipk.1");
        String textNetAd = gsMsg.getMessage("ipk.2");
        String textSabnet = gsMsg.getMessage("ipk.3");
        String textIpAd = gsMsg.getMessage("ipk.6");
        String textMachine = gsMsg.getMessage("ipk.7");
        String textUsedKbn = gsMsg.getMessage("ipk.11");
        String textSiyousya = gsMsg.getMessage("cmn.employer");
        String textMsg = gsMsg.getMessage("cmn.comment");
        String textSisankanribangou = gsMsg.getMessage("cmn.asset.register.num");

        String strHeader = textNetName + ","
                         + textNetAd + ","
                         + textSabnet + ","
                         + textIpAd + ","
                         + textMachine + ","
                         + textUsedKbn + ","
                         + textSiyousya + ","
                         + textMsg + ","
                         + textSisankanribangou + ",";

        pw.println(strHeader);
    }

    /**
     * <br>[機  能] 明細部分を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * <p>
     * @param pw PrintWriter
     * @param reqMdl リクエスト情報
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw, RequestModel reqMdl) throws CSVException {

        IpkCsvRecordListenerImpl rl = new IpkCsvRecordListenerImpl(pw, reqMdl);
        Connection con = getCon();

        try {
            IpkAddDao addDao = new IpkAddDao(con);
            IpkAddModel ipkAddModel = getSearchAddModel();
            IpkAddAdmDao addAdmDao = new IpkAddAdmDao(con);
            IpkanriCsvModel csvModel = new IpkanriCsvModel();
            ArrayList<String> iadAdmList = null;
            //詳細
            csvModel.setNetSid(ipkAddModel.getNetSid());
            csvModel.setNetNetad(ipkAddModel.getNetNetad());
            csvModel.setNetSabnet(ipkAddModel.getNetSabnet());
            csvModel.setNetName(ipkAddModel.getNetName());
            ArrayList<IpkAddModel> iadList = addDao.selectExport(ipkAddModel);
            ArrayList<Integer> iadSidList = new ArrayList<Integer>();
            for (int i = 0; i < iadList.size(); i++) {
                iadSidList.add(iadList.get(i).getIadSid());
            }
            //IPアドレスの使用者情報を取得する。
            ArrayList <IpkAddAdmModel> adminUserList = addAdmDao.selectIadAdminUsr(iadSidList);

            for (IpkAddModel mdl : iadList) {
                iadAdmList = new ArrayList<String>();
                ipkAddModel = new IpkAddModel();
                csvModel.setIadName(mdl.getIadName());
                csvModel.setIadPrtMngNum(mdl.getIadPrtMngNum());
                String ipad = Ipk050Biz.getDecFormat(mdl.getIadIpad(), IpkConst.IPAD_FORMAT);
                int ipad1 = Integer.parseInt(ipad.substring(0, 3));
                int ipad2 = Integer.parseInt(ipad.substring(3, 6));
                int ipad3 = Integer.parseInt(ipad.substring(6, 9));
                int ipad4 = Integer.parseInt(ipad.substring(9, 12));
                csvModel.setIadIpad(ipad1 + "." + ipad2 + "." + ipad3 + "." + ipad4);
                csvModel.setIadUseKbn(mdl.getIadUseKbn());
                //コメントをhtml表示用に変換
                csvModel.setIadMsg(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(
                                mdl.getIadMsg()), ""));

                //各IPアドレスの使用者の名前をセットする。
                for (IpkAddAdmModel admModel : adminUserList) {
                    if (admModel.getIadSid() == mdl.getIadSid()) {
                        iadAdmList.add(admModel.getUsiSei() + " " + admModel.getUsiMei());
                    }
                }
                csvModel.setUserSeiMei(iadAdmList);
                rl.setRecord(csvModel);
            }
        } catch (SQLException e) {
            throw new CSVException("SQLの実行に失敗", e);
        }
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
     * <p>sessionUserSid を取得します。
     * @return sessionUserSid
     */
    public int getSessionUserSid() {
        return sessionUserSid__;
    }

    /**
     * <p>sessionUserSid をセットします。
     * @param sessionUserSid sessionUserSid
     */
    public void setSessionUserSid(int sessionUserSid) {
        sessionUserSid__ = sessionUserSid;
    }

    /**
     * <p>searchAddAdmModel を取得します。
     * @return searchAddAdmModel
     */
    public IpkAddAdmModel getSearchAddAdmModel() {
        return searchAddAdmModel__;
    }

    /**
     * <p>searchAddAdmModel をセットします。
     * @param searchAddAdmModel searchAddAdmModel
     */
    public void setSearchAddAdmModel(IpkAddAdmModel searchAddAdmModel) {
        searchAddAdmModel__ = searchAddAdmModel;
    }

    /**
     * <p>searchAddModel を取得します。
     * @return searchAddModel
     */
    public IpkAddModel getSearchAddModel() {
        return searchAddModel__;
    }

    /**
     * <p>searchAddModel をセットします。
     * @param searchAddModel searchAddModel
     */
    public void setSearchAddModel(IpkAddModel searchAddModel) {
        searchAddModel__ = searchAddModel;
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