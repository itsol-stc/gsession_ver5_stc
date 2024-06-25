package jp.groupsession.v2.api.biz;

import java.sql.Connection;

import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] WEBAPI 共通ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCommonBiz {
    /**
     * 共通部　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutApiLog(
            ActionMapping map,
            RequestModel reqMdl,
            GsMessage gsMsg,
            Connection con,
            String opCode,
            String level,
            String value) {
        outPutApiLog(map, reqMdl, gsMsg, con, opCode, level, value, null);
    }

    /**
     * 共通部　個別ログ出力を行う
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param gsMsg GSメッセージ
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param fileId 添付ファイルID
     */
    public void outPutApiLog(
            ActionMapping map,
            RequestModel reqMdl,
            GsMessage gsMsg,
            Connection con,
            String opCode,
            String level,
            String value,
            String fileId) {

        /** メッセージ システム共通 **/
        String sys = gsMsg.getMessage("api.1");

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConst.PLUGINID_API);
        logMdl.setLogPluginName(sys);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(type));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(
                StringUtil.trimRengeString(value, GSConstCommon.MAX_LENGTH_LOG_OP_VALUE));
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con);

        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * 共通部　個別ログ出力を行う
     * @param pgid バッチプログラムID
     * @param con コネクション
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param domain ドメイン
     */
    public void outPutBatchLog(
            String pgid,
            Connection con,
            String opCode,
            String level,
            String value,
            String domain) {

        int usrSid = -1;
        GsMessage gsMsg = new GsMessage();
        /** メッセージ システム共通 **/
        String sys = gsMsg.getMessage("api.1", null);

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConst.PLUGINID_API);
        logMdl.setLogPluginName(sys);
        logMdl.setLogPgId(pgid);
        logMdl.setLogPgName(getPgName(pgid));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(
                StringUtil.trimRengeString(value, GSConstCommon.MAX_LENGTH_LOG_OP_VALUE));
        logMdl.setLogIp(null);
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con);
        logBiz.outPutLog(logMdl, domain);
    }
    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @return String
     */
    public String getPgName(String id) {
        String ret = new String();
        if (id == null) {
            return ret;
        }

        GsMessage gsMsg = new GsMessage();

        if (id.equals("jp.groupsession.v2.api.ApiBatchListenerImpl")) {
            //日次バッチ
            String text = gsMsg.getMessage("cmn.batch.day", null);
            return text;
        }
        if (id.equals("jp.groupsession.v2.api.api020kn.Api020knAction")) {
            //基本設定
            String text = gsMsg.getMessage("api.api020.1", null)
                    + gsMsg.getMessage("cmn.check", null);;
            return text;
        }
        if (id.equals("jp.groupsession.v2.api.api030.Api030Action")) {
            //トークン管理
            String text = gsMsg.getMessage("api.api030.1", null);
            return text;
        }

        return ret;
    }
    
}
