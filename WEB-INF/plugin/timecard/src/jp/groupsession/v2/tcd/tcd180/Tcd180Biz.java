package jp.groupsession.v2.tcd.tcd180;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;

/**
 * <br>[機  能] タイムカード 勤務表フォーマット登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd180Biz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd180Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param tempDir テンポラリディレクトリID
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 取込みファイル操作時例外
     * @throws TempFileException
     * @throws IOException
     */
    public void _setInitData(Tcd180ParamModel paramMdl,
            Connection con, String tempDir) throws 
    SQLException, IOToolsException, TempFileException, IOException {

        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
        //基本設定を取得
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);
        paramMdl.setTcd180FormatDisp(admConf.getTacWorkreportKbn());
        if (admConf.getTacWorkreportSid() != 0) {
            __setFormatData(con, paramMdl, admConf, tempDir);
        }

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        //画面に表示するファイルのリストを作成
        ArrayList<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);
                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(fMdl.getFileName(), value[0]));
            }
        }
        if (paramMdl.getTcd180InitFlg()) {
            paramMdl.setTcd180Use(admConf.getTacWorkreportKbn());
            paramMdl.setTcd180InitFlg(false);
        }
        paramMdl.setTcd180FileLabelList(fileLblList);
    }

    /**
     * <br>[機  能] フォーマットが登録されている場合、画面表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param admConf フォーマット情報モデル
     * @param tempDir テンポラリディレクトリID
     * @throws TempFileException
     * @throws IOException
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 取込みファイル操作時例外
     */
    private void __setFormatData(Connection con, Tcd180ParamModel paramMdl, TcdAdmConfModel admConf,
            String tempDir) throws TempFileException, IOException, IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();
        if (admConf.getTacWorkreportSid() > 0) {
            CmnBinfModel binMdl
            = cmnBiz.getBinInfo(con, admConf.getTacWorkreportSid(), reqMdl__.getDomain());
            if (binMdl != null) {
                //テンポラリディレクトリにバイナリデータから作成したファイルを保存する
                paramMdl.setTcd180FileName(binMdl.getBinFileName());
                paramMdl.setTcd180FormatExistFlg(1);
            }
        }
    }

    /**
     * <br>[機  能] 入力単位を返します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return 進数
     * @throws SQLException SQL実行時例外
     */
    protected int _getKansan(Connection con) throws SQLException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
        //基本設定を取得
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);
        return admConf.getTacKansan();
    }

    /**
     * <br>[機  能] フォーマット存在チェック
     * <br>[解  説] true：ファイルあり false：ファイル無し
     * <br>[備  考]
     * @param con コネクション
     * @return ret boolean true：ファイルあり false：ファイル無し
     * @throws SQLException SQL実行時例外
     */
    protected boolean _formatDataExists(Connection con) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);
        boolean ret = false;
        if (admConf.getTacWorkreportKbn() != 0) {
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] 現在使用されているフォーマットダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param tempDir テンポラリディレクトリID
     * @param appRoot アプリケーションルートパス
     * @return CmnBinfModel
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 取込みファイル操作時例外
     * @throws TempFileException
     * @throws IOException
     */
    protected CmnBinfModel _getDownloadData(Tcd180ParamModel paramMdl,
            Connection con,
            String tempDir, 
            String appRoot) throws SQLException, TempFileException, IOException, IOToolsException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUsrSid, con);
        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfModel binMdl = null;
        if (admConf.getTacWorkreportSid() > 0) {
             binMdl = cmnBiz.getBinInfo(con, admConf.getTacWorkreportSid(), reqMdl__.getDomain());
        }
        return binMdl;
    }

}
