package jp.groupsession.v2.rsv.rsv260;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 施設予約 グループ・施設一括登録画面の取込ファイルチェッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvImportCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvImportCheck.class);
    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** インポート対象施設区分SID */
    private int rskSid__ = -1;
    /** フォーマットエラーフラグ */
    private boolean formatError__ = false;
    /** 登録済みグループIDリスト */
    private List<String> rsgIdDbList__ = null;
    /** 登録済み施設IDリスト */
    private List<String> rsdIdDbList__ = null;
    /** 施設グループ グループ新規作成許可フラグ */
    private boolean createGrpFlg__ = false;
    /** 施設IDリスト 取り込み重複チェック */
    private List<String> rsdIdFileList__ = null;
    /** 施設IDリスト 取り込み上書き許可フラグ */
    private boolean rsdIdUpdateFlg__ = false;
    /** リクエスト */
    private HttpServletRequest req__ = null;

    /**
     * <p>rsdIdUpdateFlg を取得します。
     * @return rsdIdUpdateFlg
     */
    public boolean isRsdIdUpdateFlg() {
        return rsdIdUpdateFlg__;
    }
    /**
     * <p>rsdIdUpdateFlg をセットします。
     * @param rsdIdUpdateFlg rsdIdUpdateFlg
     */
    public void setRsdIdUpdateFlg(boolean rsdIdUpdateFlg) {
        rsdIdUpdateFlg__ = rsdIdUpdateFlg;
    }
    /**
     * @return rsdIdFileList
     */
    public List<String> getRsdIdFileList() {
        return rsdIdFileList__;
    }
    /**
     * @param rsdIdFileList 設定する rsdIdFileList
     */
    public void setRsdIdFileList(List<String> rsdIdFileList) {
        rsdIdFileList__ = rsdIdFileList;
    }
    /**
     * @return rsdIdDbList
     */
    public List<String> getRsdIdDbList() {
        return rsdIdDbList__;
    }
    /**
     * @param rsdIdDbList 設定する rsdIdDbList
     */
    public void setRsdIdDbList(List<String> rsdIdDbList) {
        rsdIdDbList__ = rsdIdDbList;
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
     * <p>count__ を取得します。
     * @return count
     */
    public int getCount() {
        return count__;
    }
    /**
     * <p>count__ をセットします。
     * @param count count__
     */
    public void setCount(int count) {
        count__ = count;
    }
    /**
     * <p>errorFlg__ を取得します。
     * @return errorFlg
     */
    public boolean isErrorFlg() {
        return errorFlg__;
    }
    /**
     * <p>errorFlg__ をセットします。
     * @param errorFlg errorFlg__
     */
    public void setErrorFlg(boolean errorFlg) {
        errorFlg__ = errorFlg;
    }
    /**
     * <p>errors__ を取得します。
     * @return errors
     */
    public ActionErrors getErrors() {
        return errors__;
    }
    /**
     * <p>errors__ をセットします。
     * @param errors errors__
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
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
     * <p>formatError__ を取得します。
     * @return formatError
     */
    public boolean isFormatError() {
        return formatError__;
    }
    /**
     * <p>formatError__ をセットします。
     * @param formatError formatError__
     */
    public void setFormatError(boolean formatError) {
        formatError__ = formatError;
    }
    /**
     * <p>rsgIdDbList を取得します。
     * @return rsgIdDbList
     */
    public List<String> getRsgIdDbList() {
        return rsgIdDbList__;
    }
    /**
     * <p>rsgIdDbList をセットします。
     * @param rsgIdDbList rsgIdDbList
     */
    public void setRsgIdDbList(List<String> rsgIdDbList) {
        rsgIdDbList__ = rsgIdDbList;
    }
    /**
     * <p>createGrpFlg を取得します。
     * @return createGrpFlg
     */
    public boolean isCreateGrpFlg() {
        return createGrpFlg__;
    }
    /**
     * <p>createGrpFlg をセットします。
     * @param createGrpFlg createGrpFlg
     */
    public void setCreateGrpFlg(boolean createGrpFlg) {
        createGrpFlg__ = createGrpFlg;
    }
    /**
     * <p>req を取得します。
     * @return req
     */
    public HttpServletRequest getReq() {
        return req__;
    }
    /**
     * <p>req をセットします。
     * @param req req
     */
    public void setReq(HttpServletRequest req) {
        req__ = req;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param error アクションエラー
     * @param con コネクション
     * @param rskSid 取込み先グループの施設区分
     * @param sisetuIdUpdateFlg 施設ID更新フラグ
     * @param createGrpFlg 新規グループ作成フラグ
     * @param req リクエスト
     */
    public RsvImportCheck(ActionErrors error,
                           Connection con,
                           int rskSid,
                           String sisetuIdUpdateFlg,
                           String createGrpFlg,
                           HttpServletRequest req) {
        setErrors(error);
        setCon(con);
        setRskSid(rskSid);
        setReq(req);
        //施設情報更新フラグ
        if (sisetuIdUpdateFlg.equals("1")) {
            setRsdIdUpdateFlg(true);
        }

        //新規グループ作成フラグ
        if (createGrpFlg.equals("1")) {
            setCreateGrpFlg(true);
        }
    }

    /**
     * <br>[機　能] CSVファイルのチェックを行なう
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param csvFile 入力ファイル名
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     public boolean isCsvDataOk(String csvFile) throws Exception {

         boolean ret = false;
         File file = new File(csvFile);
         if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT)) {
             if (errors__ != null) {
                 GsMessage gsMsg = new GsMessage(req__);
                 String eprefix = "inputFile.";
                 String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
                 ActionMessage msg =
                     new ActionMessage("error.over.row.csvdata",
                             textCaptureFile,
                             String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT));
                 StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             }
             ret = true;
             return ret;
         }

         //登録済みグループIDリストを取得
         RsvSisGrpDao rsgDao = new RsvSisGrpDao(con__);
         rsgIdDbList__ = rsgDao.getAllRsvGrpId();

         //登録済み施設IDリストを取得
         RsvSisDataDao rsdDao = new RsvSisDataDao(con__);
         rsdIdDbList__ = rsdDao.getRsdSidList();
         rsdIdFileList__ = new ArrayList<String>();

         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);
         log__.debug("有効データ件数 " + getCount());

         ret = isErrorFlg();

         //有効データ無し
         if (getCount() == 0) {
             ret = true;
         }
         return ret;
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

                int j = 0;
                String eprefix = "inputFile.";
                int ecnt = errors__.size();
                GsMessage gsMsg = new GsMessage();
                log__.debug("項目数=" + stringTokenizer.length());
                if (stringTokenizer.length() != __getCsvItemCount(getRskSid())) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.input.format.file",
                                gsMsg.getMessage(getReq(), "cmn.capture.file"),
                                gsMsg.getMessage(getReq(), "cmn.csv.number.items")
                                + "(" + gsMsg.getMessage(
                                        getReq(), "cmn.line", String.valueOf(num)) + ")");
                    StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
                } else {

                    while (stringTokenizer.hasMoreTokens()) {

                        j++;
                        buff = stringTokenizer.nextToken();

                        //グループID
                        if (j == 1) {
                            __isOkSisetuGrpId(errors__, buff, num);

                        }

                        //グループ名
                        if (j == 2) {
                            __isOkSisetuGrpName(errors__, buff, num);

                        }

                        //施設ID
                        if (j == 3) {
                            __isOkSisetuId(errors__, buff, num);

                        }

                        //施設名称
                        if (j == 4) {
                            __isOkSisetuName(errors__, buff, num);
                        }

                        //資産管理番号
                        if (j == 5) {
                            __isOkSisanKanari(errors__, buff, num);
                        }

                        if (j == 6) {
                            //施設区分 = 部屋
                            if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                                //座席数
                                __isOkKosu(errors__, buff, num);
                            //施設区分 = 物品
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                                //個数
                                __isOkKosu(errors__, buff, num);
                            //施設区分 = 車
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                                //ナンバー
                                __isOkCarNum(errors__, buff, num);
                            //施設区分 = 書籍
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                                //ISBN
                                __isOkISBN(errors__, buff, num);
                            //施設区分 = その他
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                                //重複登録
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.136"), num);
                            }
                        }

                        if (j == 7) {
                            //施設区分 = 部屋
                            if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                                //喫煙
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.132"), num);
                            //施設区分 = 物品
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                                //社外持出し
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.133"), num);
                            //施設区分 = 車
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                                //乗員数
                                __isOkKosu(errors__, buff, num);
                            //施設区分 = 書籍
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                                //冊数
                                __isOkKosu(errors__, buff, num);
                            //施設区分 = その他
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                                //予約可能期限
                                __isOkYrkKigen(errors__, buff, num);
                            }
                        }

                        if (j == 8) {
                            //施設区分 = 部屋
                            if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                                //重複登録
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.136"), num);
                            //施設区分 = 物品
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                                //重複登録
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.136"), num);
                            //施設区分 = 車
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                                //喫煙
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.132"), num);
                            //施設区分 = 書籍
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                                //社外持出し
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.133"), num);
                            //施設区分 = その他
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                                //承認
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.appr.set.title"), num);
                            }
                        }

                        if (j == 9) {
                            //施設区分 = 部屋
                            if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                                //予約可能期限
                                __isOkYrkKigen(errors__, buff, num);
                            //施設区分 = 物品
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                                //予約可能期限
                                __isOkYrkKigen(errors__, buff, num);
                            //施設区分 = 車
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                                //重複登録
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.136"), num);
                            //施設区分 = 書籍
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                                //重複登録
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.136"), num);
                            //施設区分 = その他
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                                //備考
                                __isOkBiko(errors__, buff, num);
                            }
                        }

                        if (j == 10) {
                            //施設区分 = 部屋
                            if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                                //承認
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.appr.set.title"), num);
                            //施設区分 = 物品
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                                //承認
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.appr.set.title"), num);
                            //施設区分 = 車
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                                //予約可能期限
                                __isOkYrkKigen(errors__, buff, num);
                            //施設区分 = 書籍
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                                //予約可能期限
                                __isOkYrkKigen(errors__, buff, num);
                            //施設区分 = その他
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            }
                        }

                        if (j == 11) {
                            //施設区分 = 部屋
                            if (getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                                //備考
                                __isOkBiko(errors__, buff, num);
                            //施設区分 = 物品
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BUPPIN) {
                                //備考
                                __isOkBiko(errors__, buff, num);
                            //施設区分 = 車
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_CAR) {
                                //承認
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.appr.set.title"), num);
                            //施設区分 = 書籍
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                                //承認
                                __isOkFlg(errors__, buff,
                                        gsMsg.getMessage(getReq(), "reserve.appr.set.title"), num);
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
                                __isOkBiko(errors__, buff, num);
                            //施設区分 = 書籍
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_BOOK) {
                                //備考
                                __isOkBiko(errors__, buff, num);
                            //施設区分 = その他
                            } else if (getRskSid() == GSConstReserve.RSK_KBN_OTHER) {
                            }
                        }
                    }
                }

                //エラー有り
                if (ecnt < errors__.size()) {
                    //エラー存在フラグON
                    setErrorFlg(true);
                } else {
                    //明細データ以降
                    if (num > 1) {
                        //有効データ件数カウントアップ
                        int cnt = getCount();
                        cnt += 1;
                        setCount(cnt);
                    }
                }

            } catch (Exception e) {
                log__.error("CSVファイル読込み時例外");
                throw e;
            }

        }
    }

    /**
     * <br>[機  能] 施設区分施設区分毎に項目数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rskSid 施設区分SID
     * @return num csvファイル項目数
     */
    private int __getCsvItemCount(int rskSid) {

        int count = -1;

        switch (rskSid) {
            //施設区分 = 部屋
            case GSConstReserve.RSK_KBN_HEYA:
                count = GSConstReserve.GROUP_SISETU_HEYA_ITEM_NUM;
                break;
            //施設区分 = 物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                count = GSConstReserve.GROUP_SISETU_BUPPIN_ITEM_NUM;
                break;
            //施設区分 = 車
            case GSConstReserve.RSK_KBN_CAR:
                count = GSConstReserve.GROUP_SISETU_CAR_ITEM_NUM;
                break;
            //施設区分 = 書籍
            case GSConstReserve.RSK_KBN_BOOK:
                count = GSConstReserve.GROUP_SISETU_BOOK_ITEM_NUM;
                break;
            //施設区分 = その他
            case GSConstReserve.RSK_KBN_OTHER:
                count = GSConstReserve.GROUP_SISETU_ETC_ITEM_NUM;
                break;
            default:
                break;
        }
        return count;
    }

    /**
     * <br>[機  能] 施設グループIDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param sisetuGrpId 施設グループID
     * @param num 行数
     * @return ActionErrors
     * @throws Exception 実行時例外
     */
    private ActionErrors __isOkSisetuGrpId(ActionErrors errors,
                                         String sisetuGrpId,
                                         long num)
    throws Exception {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "sisetuGrpId.";
        GsMessage gsMsg = new GsMessage();


        if (StringUtil.isNullZeroString(sisetuGrpId)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(getReq(), "cmn.line", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.group.id"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (sisetuGrpId.length() > GSConstReserve.MAX_LENGTH_GROUP_ID) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.group.id"),
                    GSConstReserve.MAX_LENGTH_GROUP_ID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

        } else if (!rsgIdDbList__.contains(sisetuGrpId) && !isCreateGrpFlg()) {
            //グループIDが存在しない、かつ新規グループ作成を許可しない場合
            msg = new ActionMessage("search.notfound.tdfkcode",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.group.id"));
            StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
        }
        return errors;
    }

    /**
     * <br>[機  能] 施設グループ名のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param sisetuGrpName 施設グループ名
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkSisetuGrpName(ActionErrors errors, String sisetuGrpName, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "sisetuGrpName.";
        GsMessage gsMsg = new GsMessage();

        //施設グループ名 未入力チェック
        if (StringUtil.isNullZeroString(sisetuGrpName)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.group.name"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //施設グループ名 桁数チェック
        } else if (sisetuGrpName.length() > GSConstReserve.MAX_LENGTH_GROUP_NAME) {
            msg =
                new ActionMessage("error.input.length.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.group.name"),
                                String.valueOf(GSConstReserve.MAX_LENGTH_GROUP_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuGrpName");
        //施設グループ名 スペースのみチェック
        } else if (ValidateUtil.isSpace(sisetuGrpName)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.group.name"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuGrpName");
        //施設グループ名 先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(sisetuGrpName)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.group.name"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuGrpName");
        //施設グループ名 タブスペースチェック
        } else if (ValidateUtil.isTab(sisetuGrpName)) {
            msg = new ActionMessage("error.input.tab.text",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.group.name"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuGrpName");

        //施設グループ名 JIS第2水準チェック
        } else if (!GSValidateUtil.isGsJapaneaseString(sisetuGrpName)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(sisetuGrpName);
            msg =
                new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.group.name"),
                        nstr);
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuGrpName");
        }

        return errors;
    }

    /**
     * <br>[機  能] 施設IDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param sisetuId 施設ID
     * @param num 行数
     * @return ActionErrors
     * @throws Exception 実行時例外
     */
    private ActionErrors __isOkSisetuId(ActionErrors errors,
                                         String sisetuId,
                                         long num)
    throws Exception {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "sisetuId.";
        GsMessage gsMsg = new GsMessage();

        if (StringUtil.isNullZeroString(sisetuId)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "reserve.55"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (sisetuId.length() > GSConstReserve.MAX_LENGTH_SISETU_ID) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "reserve.55"),
                    GSConstReserve.MAX_LENGTH_SISETU_ID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

        } else if (!GSValidateUtil.isOtheridFormat(sisetuId)) {
            //ユーザＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "reserve.55"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
        } else if (rsdIdDbList__.contains(sisetuId)) {

            //施設ID更新フラグがONの場合、チェックを行わない
            if (!isRsdIdUpdateFlg()) {
                //存在チェック
                msg = new ActionMessage("error.input.timecard.exist",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "reserve.55"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.timecard.exist");
            }
        }

        //ファイル内重複チェック
        if (rsdIdFileList__.contains(sisetuId)) {
            msg = new ActionMessage("error.select.dup.list2",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "reserve.55"),
                                     gsMsg.getMessage(getReq(), "reserve.src.35"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.dup.list2");
        }
        rsdIdFileList__.add(sisetuId);

        return errors;
    }

    /**
     * <br>[機  能] 施設名称のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param sisetuName 施設名称
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkSisetuName(ActionErrors errors, String sisetuName, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "sisetuName.";
        GsMessage gsMsg = new GsMessage();
        //施設名 未入力チェック
        if (StringUtil.isNullZeroString(sisetuName)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.facility.name"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //施設名 桁数チェック
        } else if (sisetuName.length() > GSConstReserve.MAX_LENGTH_SISETU_NAME) {
            msg =
                new ActionMessage("error.input.length.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.facility.name"),
                                String.valueOf(GSConstReserve.MAX_LENGTH_SISETU_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuName");
        //施設名 スペースのみチェック
        } else if (ValidateUtil.isSpace(sisetuName)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.facility.name"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuName");
        //施設名 先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(sisetuName)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.facility.name"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuName");
        //施設名 タブスペースチェック
        } else if (ValidateUtil.isTab(sisetuName)) {
            msg = new ActionMessage("error.input.tab.text",
                    gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                    + gsMsg.getMessage(getReq(), "cmn.facility.name"));
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuName");
        //施設名 JIS第2水準チェック
        } else if (!GSValidateUtil.isGsJapaneaseString(sisetuName)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseString(sisetuName);
            msg =
                new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.facility.name"),
                        nstr);
            StrutsUtil.addMessage(errors, msg, eprefix + "sisetuName");
        }

        return errors;
    }

    /**
     * <br>[機  能] 資産管理番号のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param kanriNum 資産管理番号
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkSisanKanari(ActionErrors errors, String kanriNum, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "kanriNum.";
        GsMessage gsMsg = new GsMessage();
        //資産管理番号
        if (!StringUtil.isNullZeroString(kanriNum)) {
            //資産管理番号 桁数チェック
            if (kanriNum.length() > GSConstReserve.MAX_LENGTH_SISAN_KANRI) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + gsMsg.getMessage(getReq(), "cmn.asset.register.num"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_SISAN_KANRI));
                StrutsUtil.addMessage(errors, msg, eprefix + "kanriNum");
            //資産管理番号 スペースのみチェック
            } else if (ValidateUtil.isSpace(kanriNum)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.asset.register.num"));
                StrutsUtil.addMessage(errors, msg, eprefix + "kanriNum");
            //資産管理番号 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(kanriNum)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.asset.register.num"));
                StrutsUtil.addMessage(errors, msg, eprefix + "kanriNum");
            //施設名 JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(kanriNum)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(kanriNum);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + gsMsg.getMessage(getReq(), "cmn.asset.register.num"),
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "kanriNum");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 座席数、個数、乗員数、冊数のチェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param kosu チェック数
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkKosu(ActionErrors errors,
                                     String kosu,
                                     long num) {

        //可変項目のチェック名称
        String propName = null;
        GsMessage gsMsg = new GsMessage();
        switch (getRskSid()) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                propName = gsMsg.getMessage(getReq(), "reserve.128");
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                propName = gsMsg.getMessage(getReq(), "reserve.130");
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                propName = gsMsg.getMessage(getReq(), "reserve.129");
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                propName = gsMsg.getMessage(getReq(), "reserve.131");
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                break;
            default:
                break;
        }

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "kosu.";

        //資産管理番号
        if (!StringUtil.isNullZeroString(kosu)) {
            //可変項目1 文字数
            if (kosu.length() > GSConstReserve.MAX_LENGTH_PROP1) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num)) + propName,
                                    String.valueOf(GSConstReserve.MAX_LENGTH_PROP1));
                StrutsUtil.addMessage(errors, msg, eprefix + "kosu");
            //可変項目1 半角数字
            } else if (!ValidateUtil.isNumber(kosu)) {
                msg = new ActionMessage("error.input.length.number2",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num)) + propName,
                        String.valueOf(GSConstReserve.MAX_LENGTH_PROP1));
                StrutsUtil.addMessage(errors, msg, eprefix + "kosu");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] カーナンバーのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param carNum カーナンバー
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkCarNum(ActionErrors errors, String carNum, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "carNum.";
        GsMessage gsMsg = new GsMessage();
        if (!StringUtil.isNullZeroString(carNum)) {
            //可変項目4 桁数チェック
            if (carNum.length() > GSConstReserve.MAX_LENGTH_PROP4) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + gsMsg.getMessage(getReq(), "reserve.134"),
                            String.valueOf(GSConstReserve.MAX_LENGTH_PROP4));
                StrutsUtil.addMessage(errors, msg, eprefix + "carNum");
            //可変項目4 スペースのみチェック
            } else if (ValidateUtil.isSpace(carNum)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "reserve.134"));
                StrutsUtil.addMessage(errors, msg, eprefix + "carNum");
            //可変項目4 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(carNum)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "reserve.134"));
                StrutsUtil.addMessage(errors, msg, eprefix + "carNum");
            //可変項目4 JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(carNum)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(carNum);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + gsMsg.getMessage(getReq(), "reserve.134"),
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "carNum");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] ISBNのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param isbn ISBN
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkISBN(ActionErrors errors, String isbn, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "isbn";
        GsMessage gsMsg = new GsMessage();
        //可変項目5
        if (!StringUtil.isNullZeroString(isbn)) {
            //可変項目5 桁数チェック
            if (isbn.length() > GSConstReserve.MAX_LENGTH_PROP5) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + GSConstReserve.RSK_TEXT_ISBN,
                            String.valueOf(GSConstReserve.MAX_LENGTH_PROP5));
                StrutsUtil.addMessage(errors, msg, eprefix + "isbn");
            //可変項目5 スペースのみチェック
            } else if (ValidateUtil.isSpace(isbn)) {
                msg =
                    new ActionMessage(
                            "error.input.spase.only",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + GSConstReserve.RSK_TEXT_ISBN);
                StrutsUtil.addMessage(errors, msg, eprefix + "isbn");
            //可変項目5 先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(isbn)) {
                msg =
                    new ActionMessage(
                            "error.input.spase.start",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + GSConstReserve.RSK_TEXT_ISBN);
                StrutsUtil.addMessage(errors, msg, eprefix + "isbn");
            //半角英数字とハイフンチェック
            } else if (!ValidateUtil.isAlpOrNumberOrHaifun(isbn)) {
                    msg =
                        new ActionMessage(
                                "error.format.isbn",
                                gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                                + GSConstReserve.RSK_TEXT_ISBN);
                    StrutsUtil.addMessage(errors, msg, eprefix + "isbn");
            //可変項目5 JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(isbn)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(isbn);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + GSConstReserve.RSK_TEXT_ISBN,
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "isbn");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 備考のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param biko 備考
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkBiko(ActionErrors errors, String biko, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "biko";
        GsMessage gsMsg = new GsMessage();
        //備考
        if (!StringUtil.isNullZeroString(biko)) {
            //備考 桁数チェック
            if (biko.length() > GSConstReserve.MAX_LENGTH_BIKO) {
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.memo"),
                            String.valueOf(GSConstReserve.MAX_LENGTH_BIKO));
                StrutsUtil.addMessage(errors, msg, eprefix + "biko");
            }
            //備考 スペース・改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(biko)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.memo"));
                StrutsUtil.addMessage(errors, msg, eprefix + "biko");
            }
            //備考 JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(biko)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(biko);
                msg = new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "cmn.memo"),
                        nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "biko");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 0、1フラグのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param flg フラグ
     * @param flgName フラグ名称
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkFlg(ActionErrors errors, String flg, String flgName, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "flg";
        GsMessage gsMsg = new GsMessage();
        //可変項目2
        if (StringUtil.isNullZeroString(flg)) {
            msg =
                new ActionMessage(
                        "error.select.required.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num)) + flgName);
            StrutsUtil.addMessage(errors, msg, eprefix + "flg");
        } else if (!flg.equals("0") && !flg.equals("1")) {
            msg =
                new ActionMessage(
                        "error.input.comp.text",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num)) + flgName,
                        gsMsg.getMessage(getReq(), "reserve.src.47"));
            StrutsUtil.addMessage(errors, msg, eprefix + "flg");
        }

        return errors;
    }

    /**
     * <br>[機  能] 予約可能期限のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param kigen 予約可能期限
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkYrkKigen(ActionErrors errors, String kigen, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "kigen";
        GsMessage gsMsg = new GsMessage();
        if (!StringUtil.isNullZeroString(kigen)) {
            //可変項目6 文字数
            if (kigen.length() > GSConstReserve.MAX_LENGTH_PROP6) {
                msg =
                    new ActionMessage("error.input.length.text",
                            gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                            + gsMsg.getMessage(getReq(), "reserve.135"),
                                    String.valueOf(GSConstReserve.MAX_LENGTH_PROP6));
                StrutsUtil.addMessage(errors, msg, eprefix + "kigen");
            //可変項目6 半角数字
            } else if (!ValidateUtil.isNumber(kigen)) {
                msg = new ActionMessage("error.input.length.number2",
                        gsMsg.getMessage(getReq(), "cmn.line2", String.valueOf(num))
                        + gsMsg.getMessage(getReq(), "reserve.135"),
                        String.valueOf(GSConstReserve.MAX_LENGTH_PROP6));
                StrutsUtil.addMessage(errors, msg, eprefix + "kigen");
            }
        }

        return errors;
    }
}