package jp.groupsession.v2.ptl.ptl120;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.dao.PtlPortletCategoryDao;
import jp.groupsession.v2.ptl.dao.PtlPortletCategorySortDao;
import jp.groupsession.v2.ptl.dao.PtlPortletDao;
import jp.groupsession.v2.ptl.model.PtlPortletCategoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] ポータル ポートレットカテゴリ登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl120Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl120Biz.class);

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @param cmd コマンド
     * @throws Exception 例外
     */
    public void init(Ptl120ParamModel paramMdl,
                     Connection con,
                     int sessionUserSid,
                     String cmd)
                     throws Exception {

        if (paramMdl.getPtl120init() != 1) {
            log__.debug("初期表示");
            //編集の場合、データ取得
            if (paramMdl.getPtlCmdMode() == GSConstPortal.CMD_MODE_EDIT) {
                log__.debug("編集モード");
                PtlPortletCategoryDao dao = new PtlPortletCategoryDao(con);
                PtlPortletCategoryModel model = dao.select(paramMdl.getPtlPtlCategorytSid());
                paramMdl.setPtl120name(model.getPlcName());
                paramMdl.setPtl120biko(model.getPlcBiko());
            }
            paramMdl.setPtl120init(1);
        }
    }

    /**
     * <br>[機  能] カテゴリSIDからカテゴリを取得し、メッセージを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param catSid ラベルSID
     * @param msgRes MessageResources
     * @param reqMdl リクエスト情報
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeleteCatMsg(Connection con, int catSid, MessageResources msgRes,
                                RequestModel reqMdl)
    throws SQLException {

        String msg = "";

        GsMessage gsMsg = new GsMessage(reqMdl);
        String category = gsMsg.getMessage("cmn.category");

        //カテゴリ名取得
        PtlPortletCategoryDao catDao = new PtlPortletCategoryDao(con);
        PtlPortletCategoryModel catMdl = catDao.select(catSid);
        String catName = NullDefault.getString(catMdl.getPlcName(), "");

        msg = msgRes.getMessage("sakujo.kakunin.list", category,
                StringUtilHtml.transToHTmlPlusAmparsant(catName));

        return msg;
    }

    /**
     * <br>[機  能] ポートレットカテゴリの削除を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param plcSid ポートレットカテゴリSID
     * @param con Connection
     * @throws Exception 例外
     */
    public void deleteCategory(Ptl120ParamModel paramMdl, int plcSid, Connection con)
    throws Exception {

        PtlPortletCategoryDao plcDao = new PtlPortletCategoryDao(con);
        PtlPortletCategorySortDao plcSortDao = new PtlPortletCategorySortDao(con);
        PtlPortletDao ptlDao = new PtlPortletDao(con);

        //カテゴリ内のポートレットの更新を行う(カテゴリ未設定にする)
        ptlDao.updateInCategory(plcSid);
        //カテゴリソート順の削除を行う
        plcSortDao.delete(plcSid);
        //カテゴリの削除
        plcDao.delete(plcSid);
    }

}
