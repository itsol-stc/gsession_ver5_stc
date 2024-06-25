package jp.groupsession.v2.rsv.rsv260kn;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.rsv.biz.RsvUsedDataBiz;
import jp.groupsession.v2.rsv.dao.RsvSisAdmDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisAdmModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;

/**
 * <br>[機  能] 施設予約 グループ・施設一括登録確認画面のCSVファイル取り込み処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvImportCsv extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvImportCsv.class);
    /** コネクション */
    private Connection con__ = null;
    /** 施設区分SID */
    private int rskSid__ = -1;
    /** セッションユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** 施設情報更新区分 0:上書きを禁止 1:上書きを許可 */
    private String updateKbn__ = "";
    /** 新規施設グループ作成区分 0:新規施設グループ作成禁止 1:新規施設グループ作成許可 */
    private String createGrpKbn__ = "";
    /** グループ管理者権限区分 0:権限設定を行う 1:権限設定を行わない */
    private int grpAdmKbn__ = 0;
    /** 管理者ユーザリスト */
    private String[] admUsers__ = null;

    /**
     * <p>createGrpKbn を取得します。
     * @return createGrpKbn
     */
    public String getCreateGrpKbn() {
        return createGrpKbn__;
    }
    /**
     * <p>createGrpKbn をセットします。
     * @param createGrpKbn createGrpKbn
     */
    public void setCreateGrpKbn(String createGrpKbn) {
        createGrpKbn__ = createGrpKbn;
    }
    /**
     * <p>updateKbn を取得します。
     * @return updateKbn
     */
    public String getUpdateKbn() {
        return updateKbn__;
    }
    /**
     * <p>updateKbn をセットします。
     * @param updateKbn updateKbn
     */
    public void setUpdateKbn(String updateKbn) {
        updateKbn__ = updateKbn;
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
     * <p>rskSid__ を取得します。
     * @return rskSid
     */
    public int getRskSid() {
        return rskSid__;
    }
    /**
     * <p>rskSid__ をセットします。
     * @param rskSid rskSid__
     */
    public void setRskSid(int rskSid) {
        rskSid__ = rskSid;
    }
    /**
     * <p>grpAdmKbn を取得します。
     * @return grpAdmKbn
     */
    public int getGrpAdmKbn() {
        return grpAdmKbn__;
    }
    /**
     * <p>grpAdmKbn をセットします。
     * @param grpAdmKbn grpAdmKbn
     */
    public void setGrpAdmKbn(int grpAdmKbn) {
        grpAdmKbn__ = grpAdmKbn;
    }
    /**
     * <p>admUsers を取得します。
     * @return admUsers
     */
    public String[] getAdmUsers() {
        return admUsers__;
    }
    /**
     * <p>admUsers をセットします。
     * @param admUsers admUsers
     */
    public void setAdmUsers(String[] admUsers) {
        admUsers__ = admUsers;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvImportCsv() {
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @return num 取込件数
     * @throws Exception 実行時例外
     */
    public long importCsv(String tmpFileDir) throws Exception {

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
        long num = readFile(new File(csvFile), Encoding.WINDOWS_31J);
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

        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        if (num > 1) {

            try {

                RsvSisGrpModel impSisGrpMdl = new RsvSisGrpModel();
                RsvSisDataModel impSisDataMdl = new RsvSisDataModel();

                int j = 0;

                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();

                    //グループID
                    if (j == 1) {
                        impSisGrpMdl.setRsgId(NullDefault.getString(buff, ""));
                    }

                    //グループ名
                    if (j == 2) {
                        impSisGrpMdl.setRsgName(NullDefault.getString(buff, ""));
                    }

                    //施設ID
                    if (j == 3) {
                        impSisDataMdl.setRsdId(NullDefault.getString(buff, ""));
                    }

                    //施設名称
                    if (j == 4) {
                        impSisDataMdl.setRsdName(NullDefault.getString(buff, ""));
                    }

                    //資産管理番号
                    if (j == 5) {
                        impSisDataMdl.setRsdSnum(NullDefault.getString(buff, ""));
                    }

                    if (j == 6) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //座席数
                            impSisDataMdl.setRsdProp1(NullDefault.getString(buff, ""));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //個数
                            impSisDataMdl.setRsdProp1(NullDefault.getString(buff, ""));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //ナンバー
                            impSisDataMdl.setRsdProp4(NullDefault.getString(buff, ""));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //ISBN
                            impSisDataMdl.setRsdProp5(NullDefault.getString(buff, ""));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            //重複登録
                            impSisDataMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        }
                    }

                    if (j == 7) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //喫煙
                            impSisDataMdl.setRsdProp2(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //社外持出し
                            impSisDataMdl.setRsdProp3(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //乗員数
                            impSisDataMdl.setRsdProp1(NullDefault.getString(buff, ""));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //冊数
                            impSisDataMdl.setRsdProp1(NullDefault.getString(buff, ""));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            //予約可能期限
                            impSisDataMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        }
                    }

                    if (j == 8) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //重複登録
                            impSisDataMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //重複登録
                            impSisDataMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //喫煙
                            impSisDataMdl.setRsdProp2(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //社外持出し
                            impSisDataMdl.setRsdProp3(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            //承認
                            impSisDataMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));
                        }
                    }

                    if (j == 9) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //予約可能期限
                            impSisDataMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //予約可能期限
                            impSisDataMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //重複登録
                            impSisDataMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //重複登録
                            impSisDataMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            //備考
                            impSisDataMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        }
                    }

                    if (j == 10) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //承認
                            impSisDataMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //承認
                            impSisDataMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //予約可能期限
                            impSisDataMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //予約可能期限
                            impSisDataMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                        }
                    }

                    if (j == 11) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //備考
                            impSisDataMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //備考
                            impSisDataMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //承認
                            impSisDataMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //承認
                            impSisDataMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                        }
                    }

                    if (j == 12) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //備考
                            impSisDataMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //備考
                            impSisDataMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                        }
                    }
                }

                //施設グループ情報をセット
                RsvSisGrpDao rsgDao = new RsvSisGrpDao(getCon());
                RsvSisGrpModel rsgTourokuMdl = new RsvSisGrpModel();
                rsgTourokuMdl.setRskSid(getRskSid());
                rsgTourokuMdl.setRsgId(impSisGrpMdl.getRsgId());
                rsgTourokuMdl.setRsgName(impSisGrpMdl.getRsgName());
                rsgTourokuMdl.setRsgAdmKbn(getGrpAdmKbn());
                rsgTourokuMdl.setRsgEuid(getUserSid());
                rsgTourokuMdl.setRsgEdate(getNow());

                //グループIDより施設グループデータを取得
                RsvSisGrpModel rsgDataMdl = rsgDao.getExGrpIdData(impSisGrpMdl.getRsgId());

                //施設グループ管理者設定
                RsvSisAdmDao admDao = new RsvSisAdmDao(getCon());
                int admGrpSid = 0;

                //グループが存在せず、新規グループ作成を許可する場合
                if (rsgDataMdl == null
                        && getCreateGrpKbn().equals("1")) {
                    //グループ情報新規登録
                    //施設グループSID採番
                    int grpSid =
                        (int) getCntCon().getSaibanNumber(
                                GSConstReserve.SBNSID_RESERVE,
                                GSConstReserve.SBNSID_SUB_GROUP,
                                getUserSid());

                    rsgTourokuMdl.setRsgSid(grpSid);
                    rsgTourokuMdl.setRsgAuid(getUserSid());
                    rsgTourokuMdl.setRsgAdate(getNow());
                    //新規登録
                    rsgDao.insertNewGrp(rsgTourokuMdl);
                    admGrpSid = grpSid;
                } else {

                    rsgTourokuMdl.setRsgSid(rsgDataMdl.getRsgSid());
                    //更新
                    rsgDao.updateGrp(rsgTourokuMdl);

                    //施設グループ管理者削除
                    admDao.delete(rsgDataMdl.getRsgSid());
                    admGrpSid = rsgDataMdl.getRsgSid();
                }

                String[] saveUser = getAdmUsers();
                if (saveUser != null && saveUser.length > 0
                        && getGrpAdmKbn() == GSConstReserve.RSG_ADM_KBN_OK) {
                    for (String strUsrSid : saveUser) {
                        //施設グループ管理者登録
                        RsvSisAdmModel admParam =
                            __getAdmBaseModel(admGrpSid, getUserSid(), getNow());

                        //グループの場合
                        if (strUsrSid.startsWith(GSConstReserve.GROUP_HEADSTR)) {
                            admParam.setUsrSid(-1);
                            admParam.setGrpSid(Integer.parseInt(strUsrSid.substring(1)));
                        } else {
                            //ユーザの場合
                            admParam.setUsrSid(Integer.parseInt(strUsrSid));
                            admParam.setGrpSid(-1);
                        }

                        admDao.insert(admParam);
                    }
                }

                //施設情報をセット
                RsvSisDataDao rsdDao = new RsvSisDataDao(getCon());
                RsvSisDataModel rsdTourokuMdl = new RsvSisDataModel();
                rsdTourokuMdl.setRsgSid(rsgTourokuMdl.getRsgSid());
                rsdTourokuMdl.setRsdId(impSisDataMdl.getRsdId());
                rsdTourokuMdl.setRsdName(impSisDataMdl.getRsdName());
                rsdTourokuMdl.setRsdSnum(impSisDataMdl.getRsdSnum());

                //施設区分 = 部屋
                if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                    //座席数
                    rsdTourokuMdl.setRsdProp1(impSisDataMdl.getRsdProp1());
                    //喫煙
                    rsdTourokuMdl.setRsdProp2(impSisDataMdl.getRsdProp2());
                    //重複登録
                    rsdTourokuMdl.setRsdProp7(impSisDataMdl.getRsdProp7());
                    //予約可能期限
                    rsdTourokuMdl.setRsdProp6(impSisDataMdl.getRsdProp6());
                    //承認
                    rsdTourokuMdl.setRsdApprKbn(impSisDataMdl.getRsdApprKbn());
                    //備考
                    rsdTourokuMdl.setRsdBiko(impSisDataMdl.getRsdBiko());

                //施設区分 = 物品
                } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                    //個数
                    rsdTourokuMdl.setRsdProp1(impSisDataMdl.getRsdProp1());
                    //社外持出し
                    rsdTourokuMdl.setRsdProp3(impSisDataMdl.getRsdProp3());
                    //重複登録
                    rsdTourokuMdl.setRsdProp7(impSisDataMdl.getRsdProp7());
                    //予約可能期限
                    rsdTourokuMdl.setRsdProp6(impSisDataMdl.getRsdProp6());
                    //承認
                    rsdTourokuMdl.setRsdApprKbn(impSisDataMdl.getRsdApprKbn());
                    //備考
                    rsdTourokuMdl.setRsdBiko(impSisDataMdl.getRsdBiko());

                //施設区分 = 車
                } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                    //ナンバー
                    rsdTourokuMdl.setRsdProp4(impSisDataMdl.getRsdProp4());
                    //乗員数
                    rsdTourokuMdl.setRsdProp1(impSisDataMdl.getRsdProp1());
                    //喫煙
                    rsdTourokuMdl.setRsdProp2(impSisDataMdl.getRsdProp2());
                    //重複登録
                    rsdTourokuMdl.setRsdProp7(impSisDataMdl.getRsdProp7());
                    //予約可能期限
                    rsdTourokuMdl.setRsdProp6(impSisDataMdl.getRsdProp6());
                    //承認
                    rsdTourokuMdl.setRsdApprKbn(impSisDataMdl.getRsdApprKbn());
                    //備考
                    rsdTourokuMdl.setRsdBiko(impSisDataMdl.getRsdBiko());

                //施設区分 = 書籍
                } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                    //ISBN
                    rsdTourokuMdl.setRsdProp5(impSisDataMdl.getRsdProp5());
                    //冊数
                    rsdTourokuMdl.setRsdProp1(impSisDataMdl.getRsdProp1());
                    //社外持出し
                    rsdTourokuMdl.setRsdProp3(impSisDataMdl.getRsdProp3());
                    //重複登録
                    rsdTourokuMdl.setRsdProp7(impSisDataMdl.getRsdProp7());
                    //予約可能期限
                    rsdTourokuMdl.setRsdProp6(impSisDataMdl.getRsdProp6());
                    //承認
                    rsdTourokuMdl.setRsdApprKbn(impSisDataMdl.getRsdApprKbn());
                    //備考
                    rsdTourokuMdl.setRsdBiko(impSisDataMdl.getRsdBiko());

                //施設区分 = その他
                } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                    //重複登録
                    rsdTourokuMdl.setRsdProp7(impSisDataMdl.getRsdProp7());
                    //予約可能期限
                    rsdTourokuMdl.setRsdProp6(impSisDataMdl.getRsdProp6());
                    //承認
                    rsdTourokuMdl.setRsdApprKbn(impSisDataMdl.getRsdApprKbn());
                    //備考
                    rsdTourokuMdl.setRsdBiko(impSisDataMdl.getRsdBiko());
                }

                rsdTourokuMdl.setRsdEuid(getUserSid());
                rsdTourokuMdl.setRsdEdate(getNow());

                //施設IDより施設情報データを取得
                RsvSisDataModel rsdDataMdl = rsdDao.getRsvSisData(impSisDataMdl.getRsdId());
                RsvUsedDataBiz usedDataBiz = new RsvUsedDataBiz(getCon());

                //施設IDが存在し、上書きを許可する場合
                if (rsdDataMdl != null
                        && getUpdateKbn().equals("1")) {

                    //施設情報のデータ使用量を登録(更新前の施設情報のデータ使用量を減算)
                    usedDataBiz.insertRsvDataSize(rsdDataMdl.getRsdSid(), false);

                    rsdTourokuMdl.setRsdSid(rsdDataMdl.getRsdSid());
                    //施設情報更新
                    rsdDao.updateSisetuData2(rsdTourokuMdl);

                    //施設情報のデータ使用量を登録
                    usedDataBiz.insertRsvDataSize(rsdDataMdl.getRsdSid(), true);

                } else {
                    //施設情報新規登録
                    //施設SID採番
                    int sisetuSid =
                        (int) getCntCon().getSaibanNumber(
                                GSConstReserve.SBNSID_RESERVE,
                                GSConstReserve.SBNSID_SUB_SISETU,
                                getUserSid());

                    rsdTourokuMdl.setRsdSid(sisetuSid);
                    rsdTourokuMdl.setRsdAuid(getUserSid());
                    rsdTourokuMdl.setRsdAdate(getNow());

                    //日間・週間施設情報表示区分全てOFFで登録
                    rsdTourokuMdl.setRsdIdDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdSnumDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdProp1Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdProp2Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdProp3Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdProp4Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdProp5Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdProp6Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdProp7Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt1Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt2Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt3Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt4Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt5Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt6Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt7Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt8Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt9Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgCmt10Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdBikoDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdPlaCmtDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdImgDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                    rsdTourokuMdl.setRsdApprKbnDf(GSConstReserve.SISETU_DATA_DSP_OFF);

                    rsdDao.insertNewSisetu(rsdTourokuMdl);

                    //施設情報のデータ使用量を登録
                    usedDataBiz.insertRsvDataSize(sisetuSid, true);
                }


            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }

        }
    }

    /**
     * <br>[機  能] 施設グループ管理者のベースを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @param usrSid セッションユーザSID
     * @param now システム日付
     * @return ret ベースモデル
     */
    private RsvSisAdmModel __getAdmBaseModel(int grpSid,
                                              int usrSid,
                                              UDate now) {

        RsvSisAdmModel ret = new RsvSisAdmModel();
        ret.setRsgSid(grpSid);
        ret.setRsaAuid(usrSid);
        ret.setRsaAdate(now);
        ret.setRsaEuid(usrSid);
        ret.setRsaEdate(now);
        return ret;
    }
}