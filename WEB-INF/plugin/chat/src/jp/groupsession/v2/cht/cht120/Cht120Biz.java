package jp.groupsession.v2.cht.cht120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.dao.ChtPriConfDao;
import jp.groupsession.v2.cht.model.ChtPriConfModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャット 通知設定のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht120Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht120Biz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * */
    public Cht120Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     *
     * */
    public void setInitData(Cht120ParamModel paramMdl, int usrSid)
            throws SQLException {
        log__.debug("init");
        ChtBiz chtBiz = new ChtBiz(con__);
        ChtPriConfModel confMdl = chtBiz.getChtUconf(usrSid);
        paramMdl.setCht120Push(confMdl.getCpcPushFlg());
        paramMdl.setCht120DspSecond(confMdl.getCpcPushTime());
        // 時間コンボセット
        paramMdl.setCht120TimeList(__getSecondLabel());
    }

    /**
     * <br>[機  能] 秒コンボを生成
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  秒コンボ
     */
    private ArrayList<LabelValueBean> __getSecondLabel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int sec = 1; sec <= GSConstChat.CHAT_PUSH_MAX_TIME; sec++) {
            labelList.add(
                    new LabelValueBean(String.valueOf(sec)
                            + gsMsg.getMessage("cmn.second"), String.valueOf(sec)));
        }
        return labelList;
    }


    /**
     * <br>[機  能]通知設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void updateChatUserConf(Cht120ParamModel paramMdl, int userSid)
    throws Exception {
        log__.debug("START");
        ChtPriConfModel confMdl = __setConfMdl(paramMdl, userSid);
        ChtPriConfDao confDao = new ChtPriConfDao(con__);
        if (confDao.updateTuuti(confMdl) == 0) {
            confDao.insert(confMdl);
        }
        log__.debug("End");
    }

    /**
     * <br>[機  能]通知設定を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     * @return ChtPriConfModel
     */
    public ChtPriConfModel __setConfMdl(Cht120ParamModel paramMdl, int userSid)
    throws Exception {
        ChtPriConfModel confMdl = new ChtPriConfModel();
        confMdl.initData(userSid);
        UDate now = new UDate();
        confMdl.setCpcPushFlg(paramMdl.getCht120Push());
        confMdl.setCpcPushTime(paramMdl.getCht120DspSecond());
        confMdl.setCpcAuid(userSid);
        confMdl.setCpcAdate(now);
        confMdl.setCpcEuid(userSid);
        confMdl.setCpcEdate(now);
        return confMdl;
    }
}
