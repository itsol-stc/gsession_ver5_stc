package jp.groupsession.v2.zsk.zsk090kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.dao.ZaiPriConfDao;
import jp.groupsession.v2.zsk.model.ZaiPriConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 在席管理 個人設定 自動リロード時間設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk090knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk090knBiz.class);

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
    public Zsk090knBiz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk090knParamModel
     * @throws SQLException 例外
     */
    public void initDsp(Zsk090knParamModel paramMdl) throws SQLException {

        //自動リロード時間のセット
        paramMdl.setZsk090knReloadTime(__getReloadTimeStr(paramMdl.getZsk090ReloadTime()));
    }

    /**
     * <br>[機  能] 個人設定の登録処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk090knParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    public void registPrivate(Zsk090knParamModel paramMdl, int userSid) throws SQLException {

        ZaiPriConfModel model = new ZaiPriConfModel();

        //モデルを取得
        model = __getModel(model, paramMdl, userSid);
        //更新処理
        int count = __updatePrivate(model, paramMdl, userSid);
        //更新件数が0なら新規登録を行う
        if (count <= 0) {
            log__.debug("新規登録を行います。count = " + count);
            __insertPrivate(model, paramMdl, userSid);
        }
    }
    /**
     * <br>[機  能] 個人設定モデルを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param model ZaiPriConfModel model\
     * @param paramMdl Zsk090knParamModel
     * @param userSid ログインユーザSID
     * @return model
     */
    private ZaiPriConfModel __getModel(
            ZaiPriConfModel model, Zsk090knParamModel paramMdl, int userSid) {
        UDate now = new UDate();
        model.setUsrSid(userSid);
        model.setZifSid(GSConstZaiseki.ZASEKI_NOT_SELECT);
        model.setZpcReload(NullDefault.getInt(
                paramMdl.getZsk090ReloadTime(), GSConstZaiseki.AUTO_RELOAD_10MIN));
        model.setZpcAid(userSid);
        model.setZpcAdate(now);
        model.setZpcEid(userSid);
        model.setZpcEdate(now);
        model.setZpcSortKey1(GSConstZaiseki.SORT_KEY_NAME);
        model.setZpcSortOrder1(GSConst.ORDER_KEY_ASC);
        model.setZpcSortKey2(GSConstZaiseki.SORT_KEY_NAME);
        model.setZpcSortOrder2(GSConst.ORDER_KEY_ASC);
        return model;
    }

    /**
     * <br>[機  能] 個人設定を更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param model ZaiPriConfModel
     * @param paramMdl Zsk090knParamModel
     * @param userSid ログインユーザSID
     * @return int 更新件数
     * @throws SQLException 例外
     */
    private int __updatePrivate(
            ZaiPriConfModel model, Zsk090knParamModel paramMdl, int userSid) throws SQLException {
        ZaiPriConfDao dao = new ZaiPriConfDao(con_);
        return dao.updateZpcReload(model);
    }

    /**
     * <br>[機  能] 個人設定を追加します
     * <br>[解  説]
     * <br>[備  考]
     * @param model ZaiPriConfModel
     * @param paramMdl Zsk090knParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    private void __insertPrivate(
            ZaiPriConfModel model, Zsk090knParamModel paramMdl, int userSid) throws SQLException {
        ZaiPriConfDao dao = new ZaiPriConfDao(con_);
        dao.insert(model);
    }

    /**
     * <br>[機  能] 自動リロード時間コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reloadTime 自動リロード時間
     * @return labelList 自動リロード時間（表示用）
     */
    private String __getReloadTimeStr(String reloadTime) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String noReload = gsMsg.getMessage("cmn.without.reloading");
        String msg = gsMsg.getMessage("cmn.minute");

        String ret = "";
        if (reloadTime.equals("0")) {
            ret = noReload;
        } else if (reloadTime.equals("60000")) {
            ret = "1" + msg;
        } else if (reloadTime.equals("180000")) {
            ret = "3" + msg;
        } else if (reloadTime.equals("300000")) {
            ret = "5" + msg;
        } else if (reloadTime.equals("600000")) {
            ret = "10" + msg;
        } else if (reloadTime.equals("1200000")) {
            ret = "20" + msg;
        } else if (reloadTime.equals("1800000")) {
            ret = "30" + msg;
        } else if (reloadTime.equals("2400000")) {
            ret = "40" + msg;
        } else if (reloadTime.equals("3000000")) {
            ret = "50" + msg;
        } else if (reloadTime.equals("3600000")) {
            ret = "60" + msg;
        }

        return ret;
    }
}
