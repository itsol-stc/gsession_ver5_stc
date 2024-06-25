package jp.groupsession.v2.man.man440;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能]サイボウズLiveデータ取り込み機能ガイド画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man440Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man440Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Man440Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     *
     * <br>[機  能]初期表示用のデータを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOTools例外
     */
    public void setInitData(RequestModel reqMdl,
            Man440ParamModel paramMdl,
            Connection con,
            String appRootPath) throws SQLException, IOToolsException {

        //初回表示はDBから設定を取得する(DBに存在しない場合は初期値をセットする)
        this.setGroupList(paramMdl, con);
    }

    /**
     * <br>[機  能] テンポラリディレクトリのファイル削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param tempMdl テンポラリディレクトリモデル
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void doDeleteFile(GSTemporaryPathModel tempMdl) throws IOToolsException {

        //テンポラリディレクトリのファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(tempMdl);
        log__.debug("テンポラリディレクトリのファイル削除");
    }


    /**
     * <br>[機  能] グループコンボのリストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr040ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setGroupList(Man440ParamModel paramMdl,
            Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        GroupBiz biz = new GroupBiz(); //
        ArrayList <LabelValueBean> grpLabel = biz.getGroupCombLabelList(con, false, gsMsg);
        paramMdl.setGrpLabelList(grpLabel);
    }

    /**
     * <br>[機  能] グループ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Man440ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return グループ名
     */
    public String getGroupName(Man440ParamModel paramMdl,
            Connection con) throws SQLException {
        return this.getGroupName(paramMdl.getMan440GrpSid(), con);
    }

    /**
     * <br>[機  能] グループ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return グループ名
     */
    public String getGroupName(int grpSid,
            Connection con) throws SQLException {
        GroupDao grpDao = new GroupDao(con);
        CmnGroupmModel grpMdl = grpDao.getGroup(grpSid);
        if (grpMdl != null) {
            return grpMdl.getGrpName();
        }
        return null;
    }

    /**
     * <br>[機  能] 添付ファイルの名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @return String ファイル名
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    public String getFileName(String tempDir) throws IOToolsException {
        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
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
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                ret = fMdl.getFileName();
                if (ret != null) {
                    return ret;
                }
            }
        }
        return ret;
    }
}
