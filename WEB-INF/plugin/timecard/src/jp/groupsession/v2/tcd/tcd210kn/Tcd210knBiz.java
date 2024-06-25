package jp.groupsession.v2.tcd.tcd210kn;

import java.sql.Connection;

import jp.co.sjts.util.io.IOToolsException;

/**
 * <br>[機  能] タイムカード 有休日数インポート確認画面のビジネスロジッククラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210knBiz {

    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリID
     * @throws IOToolsException 取込みファイル操作時例外
     */
    protected void _setInitData(Tcd210knParamModel paramMdl, String tempDir, Connection con) throws Exception {
        
        Tcd210knReadCsv read = new Tcd210knReadCsv(con);
        read._importCsv(tempDir);
        paramMdl.setDspList(read.getInsList());
        paramMdl.setTcd210knFileName(read.getFileName());
    }
}
