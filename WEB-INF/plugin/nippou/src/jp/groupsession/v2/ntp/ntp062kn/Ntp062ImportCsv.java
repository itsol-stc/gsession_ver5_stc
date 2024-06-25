package jp.groupsession.v2.ntp.ntp062kn;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpAnMemberDao;
import jp.groupsession.v2.ntp.dao.NtpAnMemberHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpAnShohinHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenHistoryDao;
import jp.groupsession.v2.ntp.dao.NtpAnkenPermitDao;
import jp.groupsession.v2.ntp.dao.NtpContactDao;
import jp.groupsession.v2.ntp.dao.NtpGyomuDao;
import jp.groupsession.v2.ntp.dao.NtpProcessDao;
import jp.groupsession.v2.ntp.model.NtpAnMemberHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnMemberModel;
import jp.groupsession.v2.ntp.model.NtpAnShohinHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnShohinModel;
import jp.groupsession.v2.ntp.model.NtpAnkenHistoryModel;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpAnkenPermitModel;
import jp.groupsession.v2.ntp.model.NtpContactModel;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;
import jp.groupsession.v2.ntp.model.NtpProcessModel;

/**
 * <br>[機  能] 日報インポート CSVファイルの取り込み処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp062ImportCsv extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp062ImportCsv.class);
    /** リクエスト */
    HttpServletRequest req__ = null;
    /** コネクション */
    private Connection con__ = null;

    /** セッションユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** ユーザSID */
    private int id__ = -1;

    /** 担当者SIDリスト */
    String[] userSidList__ = null;
    /** 商品SIDリスト */
    String[] shohinSidList__ = null;

    /** 権限区分 閲覧*/
    private int ntp061NanPermitView__ = GSConstNippou.NAP_KBN_ALL;
    /** 権限区分 編集*/
    private int ntp061NanPermitEdit__ = GSConstNippou.NAP_KBN_ALL;

    /** 閲覧権限 */
    private String[] nanPermitTargetView__ = null;
    /** 編集権限 */
    private String[] nanPermitTargetEdit__ = null;

    /**
     * <p>id を取得します。
     * @return id
     */
    public int getId() {
        return id__;
    }
    /**
     * <p>id をセットします。
     * @param id id
     */
    public void setId(int id) {
        id__ = id;
    }
    /**
     * <p>con__ を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con__ をセットします。
     * @param con con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>userSid__ を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid__ をセットします。
     * @param userSid userSid__
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>now__ を取得します。
     * @return now
     */
    public UDate getNow() {
        return now__;
    }
    /**
     * <p>now__ をセットします。
     * @param now now__
     */
    public void setNow(UDate now) {
        now__ = now;
    }
    /**
     * <p>cntCon__ を取得します。
     * @return cntCon
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }
    /**
     * <p>cntCon__ をセットします。
     * @param cntCon cntCon__
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @param userSid セッションユーザSID
     * @param now 取込み日時
     * @param cntCon 採番用コネクション
     * @param id 登録対象ID
     * @param userSidList ユーザSIDリスト
     * @param shohinSidList 商品SIDリスト
     */
    public Ntp062ImportCsv(Connection con,
                         HttpServletRequest req,
                         int userSid,
                         UDate now,
                         MlCountMtController cntCon,
                         int id,
                         String[] userSidList,
                         String[] shohinSidList) {
        setCon(con);
        setUserSid(userSid);
        setNow(now);
        setCntCon(cntCon);
        setId(id);
        req__ = req;
        userSidList__ = userSidList;
        shohinSidList__ = shohinSidList;
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @throws Exception 実行時例外
     */
    public void importCsv(String tmpFileDir) throws Exception {

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
        readFile(new File(csvFile), Encoding.WINDOWS_31J);
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

        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        if (num > 1) {

            try {
                int usrSid = userSid__;
                NtpAnkenDao ankenDao = new NtpAnkenDao(con__);
                NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con__);
                NtpAnMemberDao anMemberDao = new NtpAnMemberDao(con__);
                NtpAnkenHistoryDao hisDao = new NtpAnkenHistoryDao(con__);
                NtpAnShohinHistoryDao shohinHisDao = new NtpAnShohinHistoryDao(con__);
                NtpAnMemberHistoryDao memberHisDao = new NtpAnMemberHistoryDao(con__);

                //案件
                NtpAnkenModel ankenMdl = null;
                ankenMdl = new NtpAnkenModel();
                ankenMdl = __createNtpAnken(usrSid);
                UDate date = new UDate();
                date.setZeroHhMmSs();
                ankenMdl.setNanDate(date);


                int j = 0;

                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();


                    //案件コード
                    if (j == 1 && !StringUtil.isNullZeroStringSpace(buff)) {
                        ankenMdl.setNanCode(buff);
                    }

                    //案件名
                    if (j == 2 && !StringUtil.isNullZeroStringSpace(buff)) {
                        ankenMdl.setNanName(buff);
                    }

                    //案件詳細
                    if (j == 3) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ankenMdl.setNanDetial(buff);
                        } else {
                            ankenMdl.setNanDetial("");
                        }

                    }

                    //企業コード
                    if (j == 4 && !StringUtil.isNullZeroStringSpace(buff)) {
                        //企業コードから企業SIDを取得
                        AdrCompanyModel model = null;
                        AdrCompanyDao dao = new AdrCompanyDao(con__);
                        model = dao.select(buff);
                        if (model != null) {
                            ankenMdl.setAcoSid(model.getAcoSid());
                        } else {
                            ankenMdl.setAcoSid(-1);
                        }
                        ankenMdl.setAbaSid(-1);
                    }

                    //業種コード
                    if (j == 6 && !StringUtil.isNullZeroStringSpace(buff)) {
                        NtpGyomuModel model = null;
                        NtpGyomuDao dao = new NtpGyomuDao(con__);
                        model = dao.selectCode(buff);
                        if (model != null) {

                        }
                    }

                    //プロセスコード
                    if (j == 6 && !StringUtil.isNullZeroStringSpace(buff)) {
                        NtpProcessModel model = null;
                        NtpProcessDao dao = new NtpProcessDao(con__);
                        model = dao.selectCode(buff);
                        if (model != null) {
                            ankenMdl.setNgpSid(model.getNgySid());
                        }
                    }

                    //見込み度
                    if (j == 7) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            int mikomido = 0;
                            switch (Integer.valueOf(buff)) {
                            case 10:
                                mikomido = 0;
                                break;
                            case 30:
                                mikomido = 1;
                                break;
                            case 50:
                                mikomido = 2;
                                break;
                            case 70:
                                mikomido = 3;
                                break;
                            case 100:
                                mikomido = 4;
                                break;
                            default:
                                mikomido = 0;
                                break;
                            }
                            ankenMdl.setNanMikomi(mikomido);
                        } else {
                            ankenMdl.setNanMikomi(0);
                        }

                    }

                    //見積もり金額
                    if (j == 8) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ankenMdl.setNanKinMitumori(
                                    new Integer(buff.replaceAll(",", "")).intValue());
                        } else {
                            ankenMdl.setNanKinMitumori(0);
                        }
                    }

                    //提出日付
                    if (j == 9) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            UDate repDate = new UDate();
                            repDate.setZeroHhMmSs();
                            ArrayList<String> list = StringUtil.split("/", buff);

                            String sptYear = "";
                            String sptMonth = "";
                            String sptDay = "";

                            if (list.size() == 3) {
                                sptYear = list.get(0);
                                sptMonth = list.get(1);
                                sptDay = list.get(2);
                                try {
                                    repDate.setDate(
                                     StringUtil.getStrYyyyMmDd(sptYear, sptMonth, sptDay));
                                } catch (NumberFormatException ne) {
                                    ankenMdl.setNanMitumoriDate(date);
                                }
                            } else {
                                ankenMdl.setNanMitumoriDate(date);
                            }
                            ankenMdl.setNanMitumoriDate(repDate);
                        } else {
                            ankenMdl.setNanMitumoriDate(date);
                        }
                    }

                    //受注金額
                    if (j == 10) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ankenMdl.setNanKinJutyu(
                                    new Integer(buff.replaceAll(",", "")).intValue());
                        } else {
                            ankenMdl.setNanKinJutyu(0);
                        }
                    }

                    //受注日付
                    if (j == 11) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            UDate jutyuDate = new UDate();
                            jutyuDate.setZeroHhMmSs();
                            ArrayList<String> list = StringUtil.split("/", buff);

                            String sptYear = "";
                            String sptMonth = "";
                            String sptDay = "";

                            if (list.size() == 3) {
                                sptYear = list.get(0);
                                sptMonth = list.get(1);
                                sptDay = list.get(2);
                                try {
                                    jutyuDate.setDate(
                                     StringUtil.getStrYyyyMmDd(sptYear, sptMonth, sptDay));
                                } catch (NumberFormatException ne) {
                                    ankenMdl.setNanMitumoriDate(date);
                                }
                            } else {
                                ankenMdl.setNanJutyuDate(date);
                            }
                            ankenMdl.setNanJutyuDate(jutyuDate);
                        } else {
                            ankenMdl.setNanJutyuDate(date);
                        }
                    }

                    //商談結果
                    if (j == 12) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ankenMdl.setNanSyodan(new Integer(buff).intValue());
                        } else {
                            ankenMdl.setNanSyodan(0);
                        }

                    }

                    //顧客源泉
                    if (j == 13) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            NtpContactModel model = null;
                            NtpContactDao dao = new NtpContactDao(con__);
                            model = dao.selectCode(buff);
                            if (model != null) {
                                ankenMdl.setNcnSid(model.getNcnSid());
                            }
                        } else {
                            ankenMdl.setNcnSid(-1);
                        }

                    }

                    //状態
                    if (j == 14) {
                        if (!StringUtil.isNullZeroStringSpace(buff)) {
                            ankenMdl.setNanState(new Integer(buff).intValue());
                        } else {
                            ankenMdl.setNanState(0);
                        }
                    }
                }

                int nanSid = -1;
                //SID採番
                nanSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                    GSConstNippou.SBNSID_SUB_ANKEN, usrSid);
                ankenMdl.setNanSid(nanSid);
                ankenMdl.setNanPermitView(ntp061NanPermitView__);
                if (ntp061NanPermitView__ == GSConstNippou.NAP_KBN_USERGROUP
                        || ntp061NanPermitView__ == GSConstNippou.NAP_KBN_TANTO) {
                    ankenMdl.setNanPermitEdit(ntp061NanPermitView__);
                } else {
                    ankenMdl.setNanPermitEdit(ntp061NanPermitEdit__);
                }
                ankenDao.insert(ankenMdl);


                //商品
                NtpAnShohinModel anShohinMdl = null;
                if (shohinSidList__ != null
                    && shohinSidList__.length > 0) {
                    for (String shohinSid : shohinSidList__) {
                        anShohinMdl = __createNtpAnShohin(usrSid);
                        anShohinMdl.setNanSid(nanSid);
                        anShohinMdl.setNhnSid(Integer.parseInt(shohinSid));
                        anShohinDao.insert(anShohinMdl);
                    }
                }

                //担当者
                NtpAnMemberModel anMemberMdl = null;
                String[] svUsers = userSidList__;
                if (svUsers != null) {
                    for (int i = 0; i < svUsers.length; i++) {
                        if (GSValidateUtil.isNumber(svUsers[i])) {
                            anMemberMdl = __createNtpAnMember(usrSid);
                            anMemberMdl.setNanSid(nanSid);
                            anMemberMdl.setUsrSid(Integer.parseInt(svUsers[i]));
                            anMemberDao.insert(anMemberMdl);
                        }
                    }
                }
                //案件権限の登録
                if (GSConstNippou.NAP_KBN_USERGROUP == ankenMdl.getNanPermitView()
                && nanPermitTargetView__ != null) {
                    for (String selected : nanPermitTargetView__) {
                        __insertPermitUsrGrp(nanSid, selected, GSConst.SP_AUTH_VIEWONLY);
                    }
                }
                if (GSConstNippou.NAP_KBN_USERGROUP == ankenMdl.getNanPermitEdit()
                && nanPermitTargetEdit__ != null) {
                    for (String selected : nanPermitTargetEdit__) {
                        __insertPermitUsrGrp(nanSid, selected, GSConst.SP_AUTH_EDIT);
                    }
                }

                //今日の履歴があるかないか
                int nahSid = -1;
                nahSid = hisDao.checkData(nanSid, date);

                NtpAnkenHistoryModel hisMdl = new NtpAnkenHistoryModel();
                BeanUtils.copyProperties(hisMdl, ankenMdl);
                UDate nanMonth = new UDate();
                nanMonth.setYear(hisMdl.getNanDate().getYear());
                nanMonth.setMonth(hisMdl.getNanDate().getMonth());
                nanMonth.setDay(nanMonth.getMaxDayOfMonth());
                nanMonth.setZeroHhMmSs();
                hisMdl.setNanMonth(nanMonth);

                //履歴は見積もり金額、受注金額を0に設定
                hisMdl.setNanKinJutyu(0);
                hisMdl.setNanKinMitumori(0);

                if (nahSid == -1) {
                    //履歴SID採番
                    nahSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                        GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
                    hisMdl.setNahSid(nahSid);
                    hisDao.insert(hisMdl);

                    //商品履歴
                    NtpAnShohinHistoryModel shohinHisMdl = null;
                    if (shohinSidList__ != null
                            && shohinSidList__.length > 0) {
                        for (String shohinSid : shohinSidList__) {
                            shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                            shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                            shohinHisMdl.setNahSid(nahSid);
                            shohinHisMdl.setNanSid(nanSid);
                            shohinHisDao.insert(shohinHisMdl);
                        }
                    }

                    //担当者履歴
                    NtpAnMemberHistoryModel memberHisMdl = null;
                    if (anMemberMdl != null) {
                        if (userSidList__ != null
                                && userSidList__.length > 0) {
                            for (String uSid : userSidList__) {
                                if (GSValidateUtil.isNumber(uSid)) {
                                    memberHisMdl = __createNtpAnMemberHistory(usrSid);
                                    memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                                    memberHisMdl.setNahSid(nahSid);
                                    memberHisMdl.setNanSid(nanSid);
                                    memberHisDao.insert(memberHisMdl);
                                }
                            }
                        }
                    }



                }

                //見積もり日の履歴があるかないか
                nahSid = -1;
                nahSid = hisDao.checkData(nanSid, ankenMdl.getNanMitumoriDate());
                nanMonth = new UDate();
                nanMonth.setYear(ankenMdl.getNanMitumoriDate().getYear());
                nanMonth.setMonth(ankenMdl.getNanMitumoriDate().getMonth());
                nanMonth.setDay(nanMonth.getMaxDayOfMonth());
                nanMonth.setZeroHhMmSs();
                hisMdl.setNanMonth(nanMonth);
                ankenMdl.getNanMitumoriDate().setZeroHhMmSs();
                hisMdl.setNanDate(ankenMdl.getNanMitumoriDate());


                if (nahSid == -1) {
                    //履歴の新規登録

                    //履歴SID採番
                    nahSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                        GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
                    hisMdl.setNahSid(nahSid);
                    hisDao.insert(hisMdl);

                    //商品履歴
                    NtpAnShohinHistoryModel shohinHisMdl = null;
                    if (shohinSidList__ != null
                            && shohinSidList__.length > 0) {
                        for (String shohinSid : shohinSidList__) {
                            shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                            shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                            shohinHisMdl.setNahSid(nahSid);
                            shohinHisMdl.setNanSid(nanSid);
                            shohinHisDao.insert(shohinHisMdl);
                        }
                    }

                    //担当者履歴
                    NtpAnMemberHistoryModel memberHisMdl = null;
                    if (anMemberMdl != null) {
                        if (userSidList__ != null
                                && userSidList__.length > 0) {
                            for (String uSid : userSidList__) {
                                if (GSValidateUtil.isNumber(uSid)) {
                                    memberHisMdl = __createNtpAnMemberHistory(usrSid);
                                    memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                                    memberHisMdl.setNahSid(nahSid);
                                    memberHisMdl.setNanSid(nanSid);
                                    memberHisDao.insert(memberHisMdl);
                                }
                            }
                        }
                    }
                }

                //受注日の履歴があるかないか
                nahSid = -1;
                nahSid = hisDao.checkData(nanSid, ankenMdl.getNanJutyuDate());
                nanMonth = new UDate();
                nanMonth.setYear(ankenMdl.getNanJutyuDate().getYear());
                nanMonth.setMonth(ankenMdl.getNanJutyuDate().getMonth());
                nanMonth.setDay(nanMonth.getMaxDayOfMonth());
                nanMonth.setZeroHhMmSs();
                hisMdl.setNanMonth(nanMonth);
                ankenMdl.getNanJutyuDate().setZeroHhMmSs();
                hisMdl.setNanDate(ankenMdl.getNanJutyuDate());


                if (nahSid == -1) {
                    //履歴の新規登録

                    //履歴SID採番
                    nahSid = (int) cntCon__.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                        GSConstNippou.SBNSID_SUB_ANKEN_HISTORY, usrSid);
                    hisMdl.setNahSid(nahSid);
                    hisDao.insert(hisMdl);

                    //商品履歴
                    NtpAnShohinHistoryModel shohinHisMdl = null;
                    if (shohinSidList__ != null
                            && shohinSidList__.length > 0) {
                        for (String shohinSid : shohinSidList__) {
                            shohinHisMdl = __createNtpAnShohinHistory(usrSid);
                            shohinHisMdl.setNhnSid(Integer.parseInt(shohinSid));
                            shohinHisMdl.setNahSid(nahSid);
                            shohinHisMdl.setNanSid(nanSid);
                            shohinHisDao.insert(shohinHisMdl);
                        }
                    }

                    //担当者履歴
                    NtpAnMemberHistoryModel memberHisMdl = null;
                    if (anMemberMdl != null) {
                        if (userSidList__ != null
                                && userSidList__.length > 0) {
                            for (String uSid : userSidList__) {
                                if (GSValidateUtil.isNumber(uSid)) {
                                    memberHisMdl = __createNtpAnMemberHistory(usrSid);
                                    memberHisMdl.setUsrSid(Integer.parseInt(uSid));
                                    memberHisMdl.setNahSid(nahSid);
                                    memberHisMdl.setNanSid(nanSid);
                                    memberHisDao.insert(memberHisMdl);
                                }
                            }
                        }
                    }
                }


            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }
        }
    }

    /**
     * <br>[機  能] 案件情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnkenModel __createNtpAnken(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnkenModel mdl = new NtpAnkenModel();
        mdl.setNanAuid(usrSid);
        mdl.setNanAdate(nowDate);
        mdl.setNanEuid(usrSid);
        mdl.setNanEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 案件商品情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnShohinModel __createNtpAnShohin(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnShohinModel mdl = new NtpAnShohinModel();
        mdl.setNasAuid(usrSid);
        mdl.setNasAdate(nowDate);
        mdl.setNasEuid(usrSid);
        mdl.setNasEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 案件商品情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnShohinHistoryModel __createNtpAnShohinHistory(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnShohinHistoryModel mdl = new NtpAnShohinHistoryModel();
        mdl.setNasAuid(usrSid);
        mdl.setNasAdate(nowDate);
        mdl.setNasEuid(usrSid);
        mdl.setNasEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 担当者情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnMemberModel __createNtpAnMember(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnMemberModel mdl = new NtpAnMemberModel();
        mdl.setNamAuid(usrSid);
        mdl.setNamAdate(nowDate);
        mdl.setNamEuid(usrSid);
        mdl.setNamEdate(nowDate);
        return mdl;
    }

    /**
     * <br>[機  能] 担当者履歴情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpAnkenModel
     */
    private NtpAnMemberHistoryModel __createNtpAnMemberHistory(int usrSid) {

        UDate nowDate = new UDate();
        NtpAnMemberHistoryModel mdl = new NtpAnMemberHistoryModel();
        mdl.setNamAuid(usrSid);
        mdl.setNamAdate(nowDate);
        mdl.setNamEuid(usrSid);
        mdl.setNamEdate(nowDate);
        return mdl;
    }
    /**
     * <p>ntp061NanPermitView を取得します。
     * @return ntp061NanPermitView
     */
    public int getNtp061NanPermitView() {
        return ntp061NanPermitView__;
    }
    /**
     * <p>ntp061NanPermitView をセットします。
     * @param ntp061NanPermitView ntp061NanPermitView
     */
    public void setNtp061NanPermitView(int ntp061NanPermitView) {
        ntp061NanPermitView__ = ntp061NanPermitView;
    }
    /**
     * <p>ntp061NanPermitEdit を取得します。
     * @return ntp061NanPermitEdit
     */
    public int getNtp061NanPermitEdit() {
        return ntp061NanPermitEdit__;
    }
    /**
     * <p>ntp061NanPermitEdit をセットします。
     * @param ntp061NanPermitEdit ntp061NanPermitEdit
     */
    public void setNtp061NanPermitEdit(int ntp061NanPermitEdit) {
        ntp061NanPermitEdit__ = ntp061NanPermitEdit;
    }
    /**
     * <p>nanPermitTargetView を取得します。
     * @return nanPermitTargetView
     * @see jp.groupsession.v2.ntp.ntp062kn.Ntp062ImportCsv#nanPermitTargetView__
     */
    public String[] getNanPermitTargetView() {
        return nanPermitTargetView__;
    }
    /**
     * <p>nanPermitTargetView をセットします。
     * @param nanPermitTargetView nanPermitTargetView
     * @see jp.groupsession.v2.ntp.ntp062kn.Ntp062ImportCsv#nanPermitTargetView__
     */
    public void setNanPermitTargetView(String[] nanPermitTargetView) {
        nanPermitTargetView__ = nanPermitTargetView;
    }
    /**
     * <p>nanPermitTargetEdit を取得します。
     * @return nanPermitTargetEdit
     * @see jp.groupsession.v2.ntp.ntp062kn.Ntp062ImportCsv#nanPermitTargetEdit__
     */
    public String[] getNanPermitTargetEdit() {
        return nanPermitTargetEdit__;
    }
    /**
     * <p>nanPermitTargetEdit をセットします。
     * @param nanPermitTargetEdit nanPermitTargetEdit
     * @see jp.groupsession.v2.ntp.ntp062kn.Ntp062ImportCsv#nanPermitTargetEdit__
     */
    public void setNanPermitTargetEdit(String[] nanPermitTargetEdit) {
        nanPermitTargetEdit__ = nanPermitTargetEdit;
    }

    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param nanSid 案件SID
     * @param selected 選択済みID文字列（ユーザSID or G+グループSID）
     * @param kbn 権限区分 0:閲覧のみ 1:登録・編集
     * @throws SQLException SQL実行時例外
     */
    private void __insertPermitUsrGrp(int nanSid, String selected, int kbn) throws SQLException {
        if (selected == null) {
            return;
        }
        NtpAnkenPermitDao napDao = new NtpAnkenPermitDao(con__);
        NtpAnkenPermitModel model = new NtpAnkenPermitModel();
        model.setNanSid(nanSid);
        model.setNapKbn(kbn);
        int sid = NullDefault.getInt(selected, -1);
        if (selected.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
            //グループ
            sid = NullDefault.getInt(selected.substring(
                    UserGroupSelectBiz.GROUP_PREFIX.length()), -1);
            if (sid < 0) {
                return;
            }
            model.setGrpSid(sid);
        } else if (sid >= 0) {
            //ユーザ
            model.setUsrSid(sid);
        }
        napDao.insert(model);
    }
}