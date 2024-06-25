package jp.groupsession.v2.ptl.ptl990;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.dao.PtlPortletImageDao;
import jp.groupsession.v2.ptl.model.PtlPortletImageModel;

/**
 * <br>[機  能] ポータル ポートレット画像表示のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl990Biz {

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ptl990Biz() {
    }

    /**
     * <br>[機  能] ログインユーザが指定されたポートレットを閲覧可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param pconfig PluginConfig
     * @param pltSid ポートレットSID
     * @param usModel ユーザモデル
     * @return true: 閲覧可能 false:閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canViewPortlet(
            Connection con, PluginConfig pconfig, int pltSid, BaseUserModel usModel)
    throws SQLException {
        boolean result = false;

        int userSid = usModel.getUsrsid();
        if (userSid >= 0) {

            CommonBiz cmnBiz = new CommonBiz();
            //システム管理者かプラグイン管理者ならばtrue
            if (cmnBiz.isPortalAdmin(con, usModel, pconfig)) {
                return true;
            }

            //ポータルが使用可能かを判定
            if (cmnBiz.isCanUsePlugin(GSConstPortal.PLUGIN_ID, pconfig)) {

                //ポートレットが閲覧可能かを判定
                if (pltSid > 0) {
                    Ptl990Dao dao990 = new Ptl990Dao(con);
                    result = dao990.canViewPortlet(pltSid, userSid);
                }
            }
        }

        return result;
    }

    /**
     * <br>[機  能] ポートレット画像のバイナリーSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param pltSid ポートレットSID
     * @param pliSid ポートレット画像SID
     * @return バイナリーSID
     * @throws SQLException SQL実行時例外
     */
    public long getPortletBinSid(Connection con, int pltSid, long pliSid)
    throws SQLException {
        long binSid = 0;
        if (pliSid > 0) {
            Ptl990Dao dao990 = new Ptl990Dao(con);
            binSid = dao990.getPortletBinSid(pltSid, pliSid);
        }

        return binSid;
    }


    /**
     * <br>[機  能] ポートレット画像のバイナリーSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param pltSid ポートレットSID
     * @param pliSid ポートレット画像ID
     * @return バイナリーSID
     * @throws SQLException SQL実行時例外
     */
    public long getPortletBodyBinSid(Connection con, int pltSid, int pliSid)
    throws SQLException {
        long binSid = 0;
        if (pltSid > 0 && pliSid > 0) {
            PtlPortletImageDao pltBinDao = new PtlPortletImageDao(con);
            PtlPortletImageModel pltImgMdl =  pltBinDao.select(pltSid, pliSid);
            if (pltImgMdl != null) {
                binSid = pltImgMdl.getBinSid();
            }
        }

        return binSid;
    }
}
