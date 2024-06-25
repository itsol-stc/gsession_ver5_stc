package jp.groupsession.v2.zsk.zsk090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.dao.ZaiPriConfDao;
import jp.groupsession.v2.zsk.model.ZaiPriConfModel;

/**
 * <br>[機  能] 在席管理 個人設定 自動リロード時間設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk090Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk090Biz.class);

    /** コネクション */
    protected Connection con_ = null;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Zsk090Biz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl__ = reqMdl;

    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk090ParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    public void initDsp(Zsk090ParamModel paramMdl, int userSid) throws SQLException {

        log__.debug("start");
        //自動リロード時間のコンボを取得
        paramMdl.setZsk090TimeLabelList(__getTimeLabel());

        ZaiPriConfDao dao = new ZaiPriConfDao(con_);
        ZaiPriConfModel model = dao.select(userSid);
        if (model != null) {
            //自動リロード時間
            paramMdl.setZsk090ReloadTime(NullDefault.getString(
                    paramMdl.getZsk090ReloadTime(), String.valueOf(model.getZpcReload())));
        }
        log__.debug("end");
    }

    /**
     * <br>[機  能] 自動リロード時間コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getTimeLabel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.minute");
        String msg2 = gsMsg.getMessage("cmn.without.reloading");

        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();
        labelList.add(new LabelValueBean("1" + msg, "60000"));
        labelList.add(new LabelValueBean("3" + msg, "180000"));
        labelList.add(new LabelValueBean("5" + msg, "300000"));
        labelList.add(new LabelValueBean("10" + msg, "600000"));
        labelList.add(new LabelValueBean("20" + msg, "1200000"));
        labelList.add(new LabelValueBean("30" + msg, "1800000"));
        labelList.add(new LabelValueBean("40" + msg, "2400000"));
        labelList.add(new LabelValueBean("50" + msg, "3000000"));
        labelList.add(new LabelValueBean("60" + msg, "3600000"));
        labelList.add(new LabelValueBean(msg2, "0"));
        return labelList;
    }
}
