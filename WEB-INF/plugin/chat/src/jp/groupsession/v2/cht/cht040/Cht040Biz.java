package jp.groupsession.v2.cht.cht040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.dao.ChtGroupTargetDao;
import jp.groupsession.v2.cht.model.ChtAdmConfModel;
import jp.groupsession.v2.cht.model.ChtGroupTargetModel;

/**
 *
 * <br>[機  能] チャット 基本設定のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht040Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht040Biz.class);
    /** コネクション */
    private Connection con__ = null;

    /**コンストラクタ
     * @param con コネクション
     * */
    public Cht040Biz(Connection con) {
        con__ = con;
    }

    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行例外
     *
     * */
    public void setInitData(Cht040ParamModel paramMdl)
            throws SQLException {
        log__.debug("init");
        // データ取得
        int initFlg = paramMdl.getCht040InitFlg();
        if (initFlg == GSConstChat.DSP_FIRST) {
            ChtBiz chtBiz = new ChtBiz(con__);
            ChtAdmConfModel aconfMdl = chtBiz.getChtAconf();
            paramMdl.setCht040BetweenUsers(aconfMdl.getCacChatFlg());
            paramMdl.setCht040CreateGroup(aconfMdl.getCacGroupFlg());
            paramMdl.setCht040HowToLimit(aconfMdl.getCacGroupKbn());
            paramMdl.setCht040AlreadyRead(aconfMdl.getCacReadFlg());
            paramMdl.setCht040InitFlg(GSConstChat.DSP_ALREADY);
            //制限対象一覧取得
            String[] useList = __getUseLimitList();

            if (useList != null) {
                paramMdl.setCht040TargetUserList(useList);
            }

        }
    }

    /**
     * <br>[機  能] DBからグループ管理の使用制限ユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 制限対象
     * @throws SQLException SQL実行時例外
     */
    private String[] __getUseLimitList() throws SQLException {
        // チャットグループ対象取得
        ChtGroupTargetDao dao = new ChtGroupTargetDao(con__);
        List<ChtGroupTargetModel> useMdlList = dao.select();
        if (useMdlList == null || useMdlList.isEmpty()) {
            return null;
        }
        // グループとユーザのSIDを判別して取得
        String[] useUsrGrpList = new String[useMdlList.size()];
        int useIndex = 0;
        for (ChtGroupTargetModel use:useMdlList) {
            if (use.getCgtType() == GSConstChat.LIMIT_TARGET_TYPE_USR) {
                useUsrGrpList[useIndex] = String.valueOf(use.getCgtSsid());
            } else {
                useUsrGrpList[useIndex] = String.valueOf("G" + use.getCgtSsid());
            }
            useIndex++;
        }

        return useUsrGrpList;
    }
}
