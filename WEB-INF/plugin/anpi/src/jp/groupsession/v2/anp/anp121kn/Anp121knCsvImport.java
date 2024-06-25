package jp.groupsession.v2.anp.anp121kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.dao.AnpPriConfDao;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;


/**
 * <br>[機  能] 管理者設定・緊急連絡先CSVファイル取り込み処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class Anp121knCsvImport extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp121knCsvImport.class);

    /** コネクション */
    private Connection con__ = null;
    /** セッションユーザ */
    private int sessionUser__;

    /** 取込みユーザ情報リスト*/
    private ArrayList<AnpPriConfModel> importList__ = null;

    /**
     * <p>コネクションを取得する
     * @return con__ を戻す。
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>コネクションを設定する
     * @param con con__ をセット。
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>セッションユーザSIDを取得する
     * @return sessionUser__ を戻す。
     */
    public int getSessionUser() {
        return sessionUser__;
    }

    /**
     * <p>セッションユーザSIDを設定する
     * @param sessionUser sessionUser__ をセット。
     */
    public void setSessionUser(int sessionUser) {
        sessionUser__ = sessionUser;
    }

    /**
     * <p>取り込みリストを取得する
     * @return importList
     */
    public ArrayList<AnpPriConfModel> getImportList() {
        return importList__;
    }

    /**
     * <p>取り込みリストを設定する
     * @param importList セットする importList
     */
    public void setImportList(ArrayList<AnpPriConfModel> importList) {
        importList__ = importList;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con     コネクション
     * @param userSid セッションユーザSid
     */
    public Anp121knCsvImport(Connection con,
                             int userSid) {

        setSessionUser(userSid);
        setCon(con);
        setImportList(new ArrayList<AnpPriConfModel>());
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param   saveFileName  テンポラリディレクトリファイルパス
     * @return  userInfList__
     * @throws  Exception   実行時例外
     */
    public ArrayList<AnpPriConfModel> importCsv(String saveFileName)
                                         throws Exception {

        //CSVファイル取込
        log__.debug("CSVファイル取り込み file名 = " + saveFileName);
        readFile(new File(saveFileName), Encoding.WINDOWS_31J);

        //登録・登録予定内容を設定
        return importList__;
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

        //1行分の処理
        __import(num, lineStr);
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    private void __import(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        AnpPriConfModel impModel = new AnpPriConfModel();
        AnpPriConfDao cnfDao = new AnpPriConfDao(con__);
        CmnUsrmModel umodel = new CmnUsrmModel();
        CmnUsrmDao udao = new CmnUsrmDao(con__);

        //区切り文字がある間処理を実行
        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

            //（共通）ログインユーザIDを元にユーザSIDを取得する
            if (j == 1) {
                umodel = udao.getUserSid(buff);
                log__.debug("ログインユーザIDを元にユーザSIDを取得  = " + umodel.getUsrSid());
            }

            //ユーザ情報をセットする。
            __setUsrInfModel(j, impModel, buff);

        }

        //更新情報セット
        impModel.setAppEuid(sessionUser__);

        AnpiCommonBiz biz = new AnpiCommonBiz();
        biz.getAnpPriConfModel(con__, umodel.getUsrSid());
        
        impModel.setUsrSid(umodel.getUsrSid());
        log__.debug("緊急連絡先登録処理  = " + impModel.getUsrSid());
        //緊急連絡先登録処理
        cnfDao.doUpdateAnp050kn(impModel);

        //（安否確認個人設定）ユーザ存在チェック
        AnpPriConfModel aModel = cnfDao.select(umodel.getUsrSid());
        if (aModel != null) {
            impModel.setUsrSid(aModel.getUsrSid());
            log__.debug("緊急連絡先登録処理  = " + impModel.getUsrSid());
            //緊急連絡先登録処理
            cnfDao.doUpdateAnp050kn(impModel);
        }

        importList__.add(impModel);
    }

    /**
     * <br>[機  能] ユーザ情報をセットする（ユーザSID以外）
     * <br>[解  説]
     * <br>[備  考]
     * @param index    項目index
     * @param impModel Anp121knImportModel
     * @param buff     読込文字列
     */
    private void __setUsrInfModel(int index, AnpPriConfModel impModel, String buff) {

        //メールアドレス
        if (index == 2) {
            impModel.setAppMailadr(NullDefault.getStringZeroLength(buff, ""));
        }
        //電話番号
        if (index == 3) {
            impModel.setAppTelno(NullDefault.getStringZeroLength(buff, ""));
        }
    }

}