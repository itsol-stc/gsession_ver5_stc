package jp.groupsession.v2.rsv.rsv180kn;

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
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;

/**
 * <br>[機  能] 施設予約 施設インポート確認画面 施設CSVファイルの取り込み処理を行います。
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
    /** 施設SID */
    private int rsgSid__ = -1;
    /** 施設区分SID */
    private int rskSid__ = -1;
    /** セッションユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;

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
     * <p>rsgSid__ を取得します。
     * @return rsgSid
     */
    public int getRsgSid() {
        return rsgSid__;
    }
    /**
     * <p>rsgSid__ をセットします。
     * @param rsgSid rsgSid__
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
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
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param rsgSid 取込み施設グループSID
     * @param rskSid 施設区分SID
     * @param userSid セッションユーザSID
     * @param now 取込み日時
     * @param cntCon 採番用コネクション
     */
    public RsvImportCsv(Connection con,
                         int rsgSid,
                         int rskSid,
                         int userSid,
                         UDate now,
                         MlCountMtController cntCon) {
        setCon(con);
        setRsgSid(rsgSid);
        setRskSid(rskSid);
        setUserSid(userSid);
        setNow(now);
        setCntCon(cntCon);
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

                //施設SID採番
                int sisetuSid =
                    (int) getCntCon().getSaibanNumber(
                            GSConstReserve.SBNSID_RESERVE,
                            GSConstReserve.SBNSID_SUB_SISETU,
                            getUserSid());

                RsvSisDataModel impMdl = new RsvSisDataModel();
                impMdl.setRsdSid(sisetuSid);
                impMdl.setRsgSid(getRsgSid());
                impMdl.setRsdAuid(getUserSid());
                impMdl.setRsdAdate(getNow());
                impMdl.setRsdEuid(getUserSid());
                impMdl.setRsdEdate(getNow());
                int j = 0;

                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();

                    //施設ID
                    if (j == 1) {
                        impMdl.setRsdId(NullDefault.getString(buff, ""));
                    }

                    //施設名称
                    if (j == 2) {
                        impMdl.setRsdName(NullDefault.getString(buff, ""));
                    }

                    //資産管理番号
                    if (j == 3) {
                        impMdl.setRsdSnum(NullDefault.getString(buff, ""));
                    }

                    if (j == 4) {
                        //施設区分 = 部屋 or 物品
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //座席数
                            impMdl.setRsdProp1(NullDefault.getString(buff, ""));
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //個数
                            impMdl.setRsdProp1(NullDefault.getString(buff, ""));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //ナンバー
                            impMdl.setRsdProp4(NullDefault.getString(buff, ""));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //ISBN
                            impMdl.setRsdProp5(NullDefault.getString(buff, ""));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            //重複登録
                            impMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        }
                    }

                    if (j == 5) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //喫煙
                            impMdl.setRsdProp2(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //社外持出し
                            impMdl.setRsdProp3(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //乗員数
                            impMdl.setRsdProp1(NullDefault.getString(buff, ""));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //冊数
                            impMdl.setRsdProp1(NullDefault.getString(buff, ""));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            //予約可能期限
                            impMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        }
                    }

                    if (j == 6) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //重複登録
                            impMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //重複登録
                            impMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //喫煙
                            impMdl.setRsdProp2(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //社外持出し
                            impMdl.setRsdProp3(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            //承認
                            impMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));
                        }
                    }

                    if (j == 7) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //予約可能期限
                            impMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //予約可能期限
                            impMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //重複登録
                            impMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //重複登録
                            impMdl.setRsdProp7(
                                    NullDefault.getString(
                                            buff,
                                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            //備考
                            impMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        }
                    }

                    if (j == 8) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //承認
                            impMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));

                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //承認
                            impMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));

                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //予約可能期限
                            impMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //予約可能期限
                            impMdl.setRsdProp6(NullDefault.getString(buff, ""));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                        }
                    }

                    if (j == 9) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                            //備考
                            impMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                            //備考
                            impMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //承認
                            impMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //承認
                            impMdl.setRsdApprKbn(
                                    NullDefault.getInt(buff, GSConstReserve.RSD_APPR_KBN_NOSET));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                        }
                    }

                    if (j == 10) {
                        //施設区分 = 部屋
                        if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                        //施設区分 = 物品
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                        //施設区分 = 車
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                            //備考
                            impMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        //施設区分 = 書籍
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                            //備考
                            impMdl.setRsdBiko(NullDefault.getString(buff, ""));
                        //施設区分 = その他
                        } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                        }
                    }
                }

                RsvSisDataDao dao = new RsvSisDataDao(getCon());

                //日間・週間施設情報表示区分全てOFFで登録
                impMdl.setRsdIdDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdSnumDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdProp1Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdProp2Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdProp3Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdProp4Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdProp5Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdProp6Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdProp7Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt1Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt2Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt3Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt4Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt5Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt6Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt7Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt8Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt9Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgCmt10Df(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdBikoDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdPlaCmtDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdImgDf(GSConstReserve.SISETU_DATA_DSP_OFF);
                impMdl.setRsdApprKbnDf(GSConstReserve.SISETU_DATA_DSP_OFF);

                dao.insertNewSisetu(impMdl);

                //施設情報のデータ使用量を登録
                RsvUsedDataBiz usedDataBiz = new RsvUsedDataBiz(getCon());
                usedDataBiz.insertRsvDataSize(impMdl.getRsdSid(), true);

            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }

        }
    }
}