package jp.groupsession.v2.rsv.rsv290;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.rsv150.Rsv150Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 管理者設定 日間表示時間帯デフォルト設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv290Biz extends AbstractReserveBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv150Biz.class);

    /** コネクション */
    protected Connection con_ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Rsv290Biz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl_ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv160Form
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    public void initDsp(Rsv290ParamModel paramMdl, int userSid) throws SQLException {
        //コンボデータセット
        setRsv290CombData(paramMdl);

        //初期表示フラグを判定
        if (paramMdl.getRsv290initDspFlg() == 0) {
            paramMdl.setRsv290initDspFlg(1);
            paramMdl.setRsv290DateKbn(GSConstReserve.RAC_DTMKBN_USER);
            paramMdl.setRsv290SelectedFromSid(
                    String.valueOf(GSConstReserve.YRK_DEFAULT_START_HOUR));
            paramMdl.setRsv290SelectedToSid(String.valueOf(GSConstReserve.YRK_DEFAULT_END_HOUR));

            //検索結果があった場合のみセットをパラメータセットを行う
            RsvAdmConfDao dao = new RsvAdmConfDao(con_);
            RsvAdmConfModel model = dao.select();
            if (model != null) {
                log__.debug("モデルから表示項目値を取得します");
                paramMdl.setRsv290DateKbn(model.getRacDtmKbn());
                paramMdl.setRsv290SelectedFromSid(String.valueOf(model.getRacDtmFr()));
                paramMdl.setRsv290SelectedToSid(String.valueOf(model.getRacDtmTo()));
            }
        }
    }
    /**
     * <br>[機  能] コンボデータをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv290ParamModel
     */
    public void setRsv290CombData(Rsv290ParamModel paramMdl) {
        ArrayList<LabelValueBean> rsv290ourLabelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        for (int i = 0; i < 24; i++) {
            rsv290ourLabelList.add(
                  new LabelValueBean(i + gsMsg.getMessage("cmn.hour"), Integer.toString(i)));
        }
        paramMdl.setRsv290HourLabelList(rsv290ourLabelList);
    }

    /**
     * <br>[機  能] 管理者設定を更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv290ParamModel
     * @param sessionUserSid ログインユーザSID
     * @throws SQLException 例外
     */
    public void updateAdmConf(Rsv290ParamModel paramMdl, int sessionUserSid) throws SQLException {
        UDate now = new UDate();
        RsvAdmConfModel model = RsvCommonBiz.getDefaultAdmConfModel();
        model.setRacDtmKbn(paramMdl.getRsv290DateKbn());
        model.setRacDtmFr(Integer.parseInt(paramMdl.getRsv290SelectedFromSid()));
        model.setRacDtmTo(Integer.parseInt(paramMdl.getRsv290SelectedToSid()));
        model.setRacAuid(sessionUserSid);
        model.setRacAdate(now);
        model.setRacEuid(sessionUserSid);
        model.setRacEdate(now);

        RsvAdmConfDao dao = new RsvAdmConfDao(con_);
        if (dao.updateDayTimearea(model) == 0) {
            dao.insert(model);
        }
    }
}
