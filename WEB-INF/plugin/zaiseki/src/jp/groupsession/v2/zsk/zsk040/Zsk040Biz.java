package jp.groupsession.v2.zsk.zsk040;

import java.io.IOException;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.zsk.GSConstZaiseki;

/**
 * <br>[機  能] 在席管理 座席表登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk040Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、スレッド情報を設定する
     * <br>[備  考]
     * @param paramMdl Zsk040ParamModel
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void setInitData(Zsk040ParamModel paramMdl, Connection con,
                            String appRoot, String tempDir)
    throws IOException, IOToolsException {
        log__.debug("START");

        //添付ファイル一覧を設定
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setZsk040FileLabelList(cmnBiz.getTempFileLabelList(tempDir));

        //初期表示フラグの切り替え
        if (paramMdl.getZsk040initFlg() != 1) {
            paramMdl.setZsk040initFlg(1);
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        String tempDir = tempPathUtil.getTempPath(reqMdl,
                                                GSConstZaiseki.PLUGIN_ID_ZAISEKI,
                                                "zsk040");
        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(reqMdl,
                                    GSConstZaiseki.PLUGIN_ID_ZAISEKI,
                                    "zsk040");
    }
}
