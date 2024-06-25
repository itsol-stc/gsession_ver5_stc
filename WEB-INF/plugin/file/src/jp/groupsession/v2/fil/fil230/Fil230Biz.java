package jp.groupsession.v2.fil.fil230;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <br>[機  能] 管理者設定 ファイル一括削除画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil230Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil230Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil230Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil230ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil230ParamModel paramMdl) throws SQLException {

        log__.debug("fil230Biz Start");

        //画面表示を設定する。
        setDsp(paramMdl);


        if (paramMdl.getFil230DeleteOpt() == null) {
            //初期値を設定する。
            paramMdl.setFil230SltCabinetSid("-1");
            paramMdl.setFil230DeleteOpt(String.valueOf(GSConstFile.DELETE_OPTION_FILE));
        } else {
            //ディレクトリツリーデータを設定する。
            __setTreeData(paramMdl);

            //削除するフォルダパスを設定する。
            __setDeleteDir(paramMdl);
        }

    }

    /**
     * <br>[機  能] 画面表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil230ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setDsp(Fil230ParamModel paramMdl) throws SQLException {

        //キャビネットコンボを設定する。
        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);
        paramMdl.setFil230cabinetList(
                filCmnBiz.getCabinetLabel(true, paramMdl.getFil230cabinetKbn()));
    }

    /**
     * <br>[機  能] 削除するフォルダパスを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil230ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setDeleteDir(Fil230ParamModel paramMdl) throws SQLException {

        if (StringUtil.isNullZeroString(paramMdl.getFil230DeleteDirSid())) {
            return;
        }

        int dirPath = NullDefault.getInt(paramMdl.getFil230DeleteDirSid(), 0);

        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);

        String delPath = filCmnBiz.getDirctoryPath(dirPath);
        paramMdl.setFil230DeleteDir(delPath);

    }

    /**
     * <br>[機  能] フォルダ選択のディレクトリツリーを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil230ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setTreeData(Fil230ParamModel paramMdl) throws SQLException {

        int cabiSid = NullDefault.getInt(paramMdl.getFil230SltCabinetSid(), 0);

        paramMdl.setTreeFormLv0(null);
        paramMdl.setTreeFormLv1(null);
        paramMdl.setTreeFormLv2(null);
        paramMdl.setTreeFormLv3(null);
        paramMdl.setTreeFormLv4(null);
        paramMdl.setTreeFormLv5(null);
        paramMdl.setTreeFormLv6(null);
        paramMdl.setTreeFormLv7(null);
        paramMdl.setTreeFormLv8(null);
        paramMdl.setTreeFormLv9(null);
        paramMdl.setTreeFormLv10(null);

        //ルートディレクトリSIDを設定する。
        FileDirectoryDao dirdao = new FileDirectoryDao(con__);
        FileDirectoryModel rootModel = dirdao.getRootDirectory(cabiSid);
        if (rootModel == null) {
            return;
        }
        paramMdl.setFil230RootDirSid(String.valueOf(rootModel.getFdrSid()));
        paramMdl.setFil230RootDirName(rootModel.getFdrName());

        //ツリー情報を取得する
        FilTreeBiz treeBiz = new FilTreeBiz(con__);
        paramMdl.setTreeFormLv0(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_0));
        paramMdl.setTreeFormLv1(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_1));
        paramMdl.setTreeFormLv2(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_2));
        paramMdl.setTreeFormLv3(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_3));
        paramMdl.setTreeFormLv4(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_4));
        paramMdl.setTreeFormLv5(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_5));
        paramMdl.setTreeFormLv6(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_6));
        paramMdl.setTreeFormLv7(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_7));
        paramMdl.setTreeFormLv8(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_8));
        paramMdl.setTreeFormLv9(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_9));
        paramMdl.setTreeFormLv10(treeBiz.getFileTree(cabiSid, GSConstFile.DIRECTORY_LEVEL_10));
    }
}