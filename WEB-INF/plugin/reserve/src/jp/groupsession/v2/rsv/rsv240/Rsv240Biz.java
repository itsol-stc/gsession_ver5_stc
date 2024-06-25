package jp.groupsession.v2.rsv.rsv240;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 施設予約 メイン表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv240Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv240Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Rsv240Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }
    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv240ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Rsv240ParamModel paramMdl, BaseUserModel umodel, Connection con)
            throws SQLException {

        if (umodel == null) {
            return;
        }
        log__.debug("初期表示");

        RsvSisGrpDao dao = new RsvSisGrpDao(con);

        if (paramMdl.isRsv240InitFlg()) {
            //DBより設定情報を取得。
            List<Rsv240DspModel> grpList = dao.mainDspGrpData(umodel.getUsrsid());
            String[] checkGrp = null;
            if (grpList != null && grpList.size() > 0) {
                checkGrp = new String[grpList.size()];
                int i = 0;
                for (Rsv240DspModel mdl : grpList) {
                    checkGrp[i] = String.valueOf(mdl.getRsgSid());
                    i++;
                }

                //予約時間経過表示区分をセット
                if (grpList.get(0).getRsmDspKbn() == GSConstReserve.RSV_OVERTIME_DSP_ON) {
                    paramMdl.setRsv240overTimeDspKbn(GSConstReserve.RSV_OVERTIME_DSP_ON);
                } else {
                    paramMdl.setRsv240overTimeDspKbn(GSConstReserve.RSV_OVERTIME_DSP_OFF);
                }
            }
            paramMdl.setRsv240RsgSids(checkGrp);
            paramMdl.setRsv240InitFlg(false);

        }

        //システム管理者か
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, umodel, GSConstReserve.PLUGIN_ID_RESERVE);

        List<RsvSisGrpModel> allGrpList = null;

        //グループリスト
        if (adminUser) {
            //全グループ取得
            allGrpList = dao.selectAllGroupData();
        } else {
            //閲覧可能グループ取得
            allGrpList = dao.getCanReadData(umodel.getUsrsid());
        }
        paramMdl.setRsv240DspList(allGrpList);

    }
}
