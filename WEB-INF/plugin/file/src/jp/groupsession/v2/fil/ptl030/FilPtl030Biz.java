package jp.groupsession.v2.fil.ptl030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil010.Fil010Biz;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ポータル キャビネット一覧画面のビジネスロジッククラス
 * @author JTS
 */
public class FilPtl030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FilPtl030Biz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public FilPtl030Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl FilPtl020ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(FilPtl030ParamModel paramMdl)
    throws SQLException {
        log__.debug("START");

        BaseUserModel usModel = reqMdl__.getSmodel();
        Fil010Biz biz = new Fil010Biz(reqMdl__);
        //アクセス可能なキャビネット一覧を取得
        ArrayList<FileCabinetDspModel> cabList =
                biz.getAccessCabinetList(usModel, con__, true, GSConstFile.CABINET_KBN_PUBLIC); 
        cabList.addAll(biz.getAccessCabinetList(
                usModel, con__, true, GSConstFile.CABINET_KBN_ERRL));
        paramMdl.setCabinetList(cabList);

        log__.debug("End");
    }

}