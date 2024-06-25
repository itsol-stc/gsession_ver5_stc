package jp.groupsession.v2.ip.ipk060;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] IPアドレスのインポートCSVファイル取り込み処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class IadCsvImport extends AbstractCsvRecordReader {

    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** セッションユーザ */
    private int sessionUser__;
    /** システム日付 */
    private UDate sysUd__;
    /** ネットワークSID */
    private int netSid__;
    /** 取込みIPアドレス情報リスト*/
    private ArrayList<IpkAddModel> iadInfList__ = null;

    /**
     * @return cntCon を戻します。
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }
    /**
     * @param cntCon 設定する cntCon。
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }
    /**
     * @return con__ を戻す。
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * @param con con__ をセット。
     */
    public void setCon(Connection con) {
        con__ = con;
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
    /**
     * @return sessionUser__ を戻す。
     */
    public int getSessionUser() {
        return sessionUser__;
    }
    /**
     * @param sessionUser sessionUser__ をセット。
     */
    public void setSessionUser(int sessionUser) {
        sessionUser__ = sessionUser;
    }
    /**
     * @return sysUd__ を戻す。
     */
    public UDate getSysUd() {
        return sysUd__;
    }
    /**
     * @param sysUd sysUd__ をセット。
     */
    public void setSysUd(UDate sysUd) {
        sysUd__ = sysUd;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param cntCon 採番コントローラ
     * @param userSid セッションユーザSID
     * @param netSid ネットワークSID
     * @param con コネクション
     */
    public IadCsvImport(RequestModel reqMdl,
                          MlCountMtController cntCon,
                          int userSid,
                          int netSid,
                          Connection con) {

        setReqMdl(reqMdl);
        setCntCon(cntCon);
        setSessionUser(userSid);
        setNetSid(netSid);
        setCon(con);
        setSysUd(new UDate());
        setIadInfList(new ArrayList<IpkAddModel>());
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param mode 実行モード 0=上書きモード1=追加モード
     * @param con コネクション
     * @param netSid ネットワークSID
     */
    public IadCsvImport(RequestModel reqMdl,
                          String mode,
                          Connection con, int netSid) {
        setReqMdl(reqMdl);
        setCon(con);
        setNetSid(netSid);
        setSysUd(new UDate());
        setIadInfList(new ArrayList<IpkAddModel>());
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @return num
     * @throws  Exception   実行時例外
     */
    public long importCsv(String tmpFileDir)
        throws Exception {

        //テンポラリディレクトリにあるファイル名称を取得
        String saveFileName = "";
        List<String> fileList = IOTools.getFileNames(tmpFileDir);
        for (int i = 0; i < fileList.size(); i++) {
            //ファイル名を取得
            String fileName = fileList.get(i);
            if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                continue;
            }

            //オブジェクトファイルを取得
            ObjectFile objFile = new ObjectFile(tmpFileDir, fileName);
            Object fObj = objFile.load();
            if (fObj == null) {
                continue;
            }
            Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
            saveFileName = fMdl.getSaveFileName();
        }
        String csvFile = tmpFileDir + saveFileName;
        //ファイル取込
        Long num = readFile(new File(csvFile), Encoding.WINDOWS_31J);
        //登録・登録予定内容を設定
        return num;
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }
        __import(num, lineStr, reqMdl__);
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @param reqMdl リクエスト情報
     * @throws Exception csv読込時例外
     */
    private void __import(long num, String lineStr, RequestModel reqMdl) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //IPアドレスSidの採番をする。
        int newIadSid = (int) cntCon__.getSaibanNumber("ipkanri", "ipAddress", sessionUser__);

        IpkAddModel addModel = new IpkAddModel();
        IpkAddDao dao = new IpkAddDao(con__);
        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();
            addModel.setNetSid(netSid__);
            addModel.setNewIadSid(newIadSid);
            addModel.setIadAuid(sessionUser__);
            addModel.setIadAdate(sysUd__);
            addModel.setIadEuid(sessionUser__);
            addModel.setIadEdate(sysUd__);

            //IPアドレス情報をセットする
            __setIadInfModel(j, addModel, buff, reqMdl);
        }
        //IPアドレスを登録する。
        dao.insert(addModel);
        iadInfList__.add(addModel);
    }

    /**
     * <br>[機  能] ユーザ情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param index 項目index
     * @param model IpkAddModel
     * @param buff 読込文字列
     * @param reqMdl リクエストジョイ法
     */
    private void __setIadInfModel(int index, IpkAddModel model, String buff,
                                RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textSiyou = gsMsg.getMessage("cmn.use");
        String textMisiyou = gsMsg.getMessage("cmn.unused");

        //ネットワーク名
        if (index == 1) {
            model.setNetName(NullDefault.getString(buff, ""));
        }
        //ネットワークアドレス
        if (index == 2) {
            model.setNetNetad(NullDefault.getString(buff, ""));
        }
        //サブネットマスク
        if (index == 3) {
            model.setNetSabnet(NullDefault.getString(buff, ""));
        }
        //IPアドレス
        if (index == 4) {
            String strIpad = "";
            //IPアドレスのネットワークアドレス部を変更する。
            String[] ipadAry = buff.replaceAll("\\.", ",").split(",");
            strIpad = StringUtil.toDecFormat(ipadAry[0], IpkConst.IPAD_FORMAT_PART)
                + StringUtil.toDecFormat(ipadAry[1], IpkConst.IPAD_FORMAT_PART)
                + StringUtil.toDecFormat(ipadAry[2], IpkConst.IPAD_FORMAT_PART)
                + StringUtil.toDecFormat(ipadAry[3], IpkConst.IPAD_FORMAT_PART);
            model.setIadIpad(Long.parseLong(strIpad));
        }
        //マシン名
        if (index == 5) {
            model.setIadName(NullDefault.getString(buff, ""));
        }
        //使用区分
        if (index == 6) {
            String useKbn = NullDefault.getString(buff, IpkConst.USEDKBN_MISIYOU);
            if (useKbn.equals(textSiyou)) {
                model.setIadUseKbn(IpkConst.USEDKBN_SIYOU);
            } else if (useKbn.equals(textMisiyou)) {
                model.setIadUseKbn(IpkConst.USEDKBN_MISIYOU);
            }
        }
        //コメント
        if (index == 8) {
            model.setIadMsg(NullDefault.getString(buff, ""));
        }
        //資産管理番号
        if (index == 9) {
            model.setIadPrtMngNum(NullDefault.getString(buff, ""));
        }
    }
    /**
     * <p>iadInfList を取得します。
     * @return iadInfList
     */
    public ArrayList<IpkAddModel> getIadInfList() {
        return iadInfList__;
    }
    /**
     * <p>iadInfList をセットします。
     * @param iadInfList iadInfList
     */
    public void setIadInfList(ArrayList<IpkAddModel> iadInfList) {
        iadInfList__ = iadInfList;
    }
    /**
     * <p>netSid を取得します。
     * @return netSid
     */
    public int getNetSid() {
        return netSid__;
    }
    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(int netSid) {
        netSid__ = netSid;
    }
}