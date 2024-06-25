package jp.groupsession.v2.zsk.zsk110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.dao.ZaiFixUpdateDao;
import jp.groupsession.v2.zsk.model.ZaiFixUpdateModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理 管理者設定 定時一括更新画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk110Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk110Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public Zsk110Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk110ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Zsk110ParamModel paramMdl, Connection con)
            throws SQLException {

        log__.debug("setInitData START");

        //開始時刻コンボを設定する。
        __setComb(paramMdl);

        //初期値の設定を行う。
        if (paramMdl.getZsk110StartTime() == -1) {
            ZaiFixUpdateDao dao = new ZaiFixUpdateDao(con);
            ZaiFixUpdateModel model = dao.select();
            if (model != null) {
                paramMdl.setZsk110UpdateKbn(model.getZfuUpdateKbn());
                paramMdl.setZsk110StartTime(model.getZfuFixUpdateTime());
                paramMdl.setZsk110Status(model.getZfuStatus());
                paramMdl.setZsk110Msg(model.getZfuMsg());
            }
        }
    }

    /**
     * <br>[機  能] 再表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk110ParamModel
     * @param con コネクション
     */
    public void setDspData(Zsk110ParamModel paramMdl, Connection con) {

        log__.debug("再表示");

        //開始時刻コンボを設定する。
        __setComb(paramMdl);

    }

    /**
     * <br>[機  能] 開始時間コンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk110ParamModel
     */
    public void __setComb(Zsk110ParamModel paramMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.select.plz");

        List<LabelValueBean> timeList = new ArrayList<LabelValueBean>();
        timeList.add(new LabelValueBean(msg, "-1"));
        for (int i = 0; i < 24; i++) {
            timeList.add(new LabelValueBean(
                            gsMsg.getMessage("cmn.hour.only", new String[] {String.valueOf(i)}),
                            String.valueOf(i)));
        }

        paramMdl.setZsk110TimeList(timeList);
    }
}
