package jp.groupsession.v2.tcd.tcd200kn;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;
import jp.groupsession.v2.tcd.model.TcdYukyudataModel;

/**
 * <br>[機  能] タイムカード 管理者設定有休日数登録,編集確認画面のビジネスロジッククラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd200knBiz {

    /**
     * <br>[機  能] 表示内容の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    protected void _setDisplayData(Tcd200knParamModel paramMdl, Connection con) throws SQLException {

        //画面表示項目を取得
        CmnUsrmInfDao cmnDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel cmnMdl = cmnDao.selectUserNameAndJtkbn(paramMdl.getTcd200Name());
        StringBuilder strBld = new StringBuilder();
        strBld.append(cmnMdl.getUsiSei());
        strBld.append(" ");
        strBld.append(cmnMdl.getUsiMei());
        paramMdl.setTcd200editUserName(strBld.toString());
        paramMdl.setTcd200editUserUkoFlg(cmnMdl.getUsrUkoFlg());
    }

    /**
     * <br>[機  能] 登録，編集モードを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    protected void _setMode(Tcd200knParamModel paramMdl, Connection con) throws SQLException {
        TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
        int targetSid = paramMdl.getTcd200Name();
        int nendo = paramMdl.getTcd200Nendo();

        TcdYukyudataModel tydMdl = tydDao.getYukyuData(targetSid, nendo);
        if (tydMdl == null) {
            paramMdl.setTcd200mode(GSConstTimecard.YUKYU_MODE_INSERT);
        } else {
            paramMdl.setTcd200mode(GSConstTimecard.YUKYU_MODE_UPDATE);
        }
    }

    /**
     * <br>[機  能] データの編集または登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    protected void _upsertYukyu(
            ActionMapping map,
            RequestModel reqMdl,
            Tcd200knParamModel paramMdl,
            Connection con) throws SQLException {

        TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
        int usrSid = reqMdl.getSmodel().getUsrsid();
        TcdYukyudataModel tydMdl = __getTcdYukyudataModel(usrSid, paramMdl);
        int mode = paramMdl.getTcd200mode();

        //登録，編集の実行
        boolean commitFlg = false;
        try {
            if (mode == GSConstTimecard.YUKYU_MODE_INSERT) {
                //有休日数情報の登録
                tydDao.insert(tydMdl);
            } else if (mode == GSConstTimecard.YUKYU_MODE_UPDATE) {
                //有休日数情報の編集
                tydDao.update(tydMdl);
            }
            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //オペレーションログの登録
        TimecardBiz tcdBiz = new TimecardBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode;
        if (mode == GSConstTimecard.YUKYU_MODE_INSERT) {
            opCode = gsMsg.getMessage("cmn.add");
        } else {
            opCode = gsMsg.getMessage("cmn.edit");
        }
        StringBuilder buf = new StringBuilder();
        buf.append("[" + gsMsg.getMessage("cmn.user.name") + "] ");
        CmnUsrmInfDao cmnDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel cmnMdl = cmnDao.select(paramMdl.getTcd200Name());
        buf.append(cmnMdl.getUsiName());
        buf.append("\r\n[" + gsMsg.getMessage("tcd.209") + "] ");
        buf.append(paramMdl.getTcd200Nendo());
        buf.append("\r\n[" + gsMsg.getMessage("tcd.210") + "] ");
        buf.append(paramMdl.getTcd200YukyuDays());
        String value = buf.toString();

        tcdBiz.outPutTimecardLog(map, reqMdl, con, opCode, GSConstLog.LEVEL_INFO, value);
    }

    /**
     * <br>[機  能] パラメータ情報, ユーザSIDから登録用モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @param paramMdl パラメータ情報
     * @return 登録用モデル
     */
    private TcdYukyudataModel __getTcdYukyudataModel(int usrSid, Tcd200knParamModel paramMdl) {
        TcdYukyudataModel ret = new TcdYukyudataModel();
        ret.setUsrSid(paramMdl.getTcd200Name());
        ret.setTcyYukyu(new BigDecimal(paramMdl.getTcd200YukyuDays()));
        ret.setTcyNendo(paramMdl.getTcd200Nendo());
        ret.setTcyEuid(usrSid);
        ret.setTcyAuid(usrSid);
        UDate now = new UDate();
        ret.setTcyEdate(now);
        ret.setTcyAdate(now);
        return ret;
    }
}
