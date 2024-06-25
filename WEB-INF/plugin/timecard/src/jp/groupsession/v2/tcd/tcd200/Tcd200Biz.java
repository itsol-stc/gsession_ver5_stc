package jp.groupsession.v2.tcd.tcd200;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardYukyuConfBiz;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;
import jp.groupsession.v2.tcd.model.TcdYukyudataModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] タイムカード管理者設定 有休日数登録画面のビジネスロジッククラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd200Biz {
    
    /**
     * <br>[機  能] 表示内容の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    protected void _setInitData(
            Tcd200ParamModel paramMdl,
            RequestModel reqMdl,
            Connection con) throws SQLException {
        
        BaseUserModel usMdl = reqMdl.getSmodel();
        int usrSid = usMdl.getUsrsid();
        
        //有休日数情報の存在チェック
        TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
        TcdYukyudataModel tydMdl = tydDao.getYukyuData(paramMdl.getTcd200Name(),
                paramMdl.getTcd200Nendo());
        int mode;
        if (tydMdl == null) {
            mode = GSConstTimecard.YUKYU_MODE_INSERT;
        } else {
            mode = GSConstTimecard.YUKYU_MODE_UPDATE;
        }
        paramMdl.setTcd200mode(mode);
        
        //画面表示項目を取得
        TimecardYukyuConfBiz tycBiz = new TimecardYukyuConfBiz();
        TimecardBiz tcdBiz = new TimecardBiz();
        int usrKbn = tcdBiz.getUserKbn(con, usMdl);
        //グループコンボの設定
        List<LabelValueBean> groupLabel = tcdBiz.getGroupLabelList(con, usrSid, usrKbn, reqMdl);
        paramMdl.setTcdGroupList(groupLabel);
        
        //初期表示フラグの設定
        int initFlg = paramMdl.getTcd200initFlg();
        if (initFlg != GSConstTimecard.DSP_RE) {
            usMdl = new BaseUserModel();
            if (mode == GSConstTimecard.YUKYU_MODE_INSERT) {
                paramMdl.setTcd200YukyuDays(null);
            } else if (mode == GSConstTimecard.YUKYU_MODE_UPDATE) {
                paramMdl.setTcd200YukyuDays(tcdBiz.dispSyukei(
                        String.valueOf(tydMdl.getTcyYukyu())));
            }
        }
        paramMdl.setTcd200initFlg(GSConstTimecard.DSP_RE);
        
        //ユーザコンボの設定
        UserBiz usrBiz = new UserBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<UsrLabelValueBean> userLabel = usrBiz.getNormalAllUserLabelList(con, 
                gsMsg, paramMdl.getTcd200Group(), null, true);
        paramMdl.setTcdUserList(userLabel);
        
        //年度コンボの設定
        List<LabelValueBean> nendoList = tycBiz.getNendoList(con);
        paramMdl.setTcdNendoList(nendoList);
    }
}
