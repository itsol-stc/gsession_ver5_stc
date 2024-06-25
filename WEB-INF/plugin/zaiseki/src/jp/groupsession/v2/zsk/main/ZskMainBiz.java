package jp.groupsession.v2.zsk.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrInoutDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理(メイン画面表示用 本人)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskMainBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZskMainBiz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public ZskMainBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * 初期表示画面情報を取得します
     * @param paramMdl ZskMainParamModel
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param umodel ユーザ基本情報
     * @return Man001Form アクションフォーム
     * @throws Exception SQL実行時例外
     */
    public ZskMainParamModel getInitData(ZskMainParamModel paramMdl,
            Connection con,
            PluginConfig pconfig, BaseUserModel umodel) throws Exception {

        log__.debug("-- getInitData START --");

        //在席ステータスセット
        __setZskStatus(con, paramMdl, umodel.getUsrsid());
        log__.debug("-- getInitData START --");
        return paramMdl;
    }

    /**
     * <br>[機  能] 在席ステータスをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl ZskMainParamModel
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __setZskStatus(Connection con, ZskMainParamModel paramMdl, int userSid)
        throws SQLException {

        log__.debug("在席ステータスセット");

        CmnUsrInoutModel param = new CmnUsrInoutModel();
        param.setUioSid(userSid);

        CmnUsrInoutDao ioDao = new CmnUsrInoutDao(con);
        CmnUsrInoutModel ret = ioDao.select(param);

        //レコード無し(初ログイン or 初ログインから一度も在席を更新していない)
        if (ret == null) {
            paramMdl.setZskUioStatus(GSConst.UIOSTS_IN);
            paramMdl.setZskUioBiko("");
        //レコード有り
        } else {
            paramMdl.setZskUioStatus(ret.getUioStatus());
            paramMdl.setZskUioBiko(NullDefault.getString(ret.getUioBiko(), ""));
        }

        //在席リストボックスのラベル作成
        setZskUioStatusLabel(paramMdl);
    }
    /**
     * <br>[機  能] 在席ステータスリストラベルをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl ZskMainParamModel
     */
    public void setZskUioStatusLabel(ZskMainParamModel paramMdl) {
        ZsjCommonBiz zbiz = new ZsjCommonBiz(reqMdl__);
        List<LabelValueBean> label = zbiz.createZskUioStatusLabel();
        paramMdl.setZskUioStatusLabel(label);
    }

    /**
     * <br>[機  能] 在席ステータスを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl ZskMainParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateZskStatus(ZskMainParamModel paramMdl, Connection con)
        throws SQLException {

        log__.debug("在席ステータス更新");

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int userSid = usModel.getUsrsid();

        UDate now = new UDate();
        CmnUsrInoutModel param = new CmnUsrInoutModel();
        param.setUioSid(userSid);
        param.setUioStatus(paramMdl.getZskUioStatus());
        param.setUioBiko(NullDefault.getString(paramMdl.getZskUioBiko(), ""));
        param.setUioAuid(userSid);
        param.setUioAdate(now);
        param.setUioEuid(userSid);
        param.setUioEdate(now);

        ZsjCommonBiz zbiz = new ZsjCommonBiz(reqMdl__);
        zbiz.updateZskStatus(con, param);
    }
}
