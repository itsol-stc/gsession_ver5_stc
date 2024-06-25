package jp.groupsession.v2.bmk.bmk110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkAconfDao;
import jp.groupsession.v2.bmk.dao.BmkPublicEditDao;
import jp.groupsession.v2.bmk.model.BmkAconfModel;
import jp.groupsession.v2.bmk.model.BmkPublicEditModel;
import jp.groupsession.v2.cmn.biz.GroupBiz;

/**
 * <br>[機  能] 管理者設定 権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk110Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk110Biz.class);

    /**
     * <br>[機  能] 表示データの初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk110ParamModel
     * @param model 管理者設定モデル
     */
    public void setDspData(Bmk110ParamModel paramMdl, BmkAconfModel model) {

        int pubEditKbn = GSConstBookmark.EDIT_POW_ADMIN;
        int grpEditKbn = GSConstBookmark.GROUP_EDIT_ADMIN;

        if (model != null) {
            pubEditKbn = model.getBacPubEdit();
            grpEditKbn = model.getBacGrpEdit();
        }
        log__.debug("共有ブックマーク編集権限 : " + paramMdl.getBmk110PubEditKbn());
        log__.debug("グループブックマーク編集権限 : " + paramMdl.getBmk110GrpEditKbn());
        if (paramMdl.getBmk110PubEditKbn() == -1) {
            //共有ブックマーク編集権限の初期化
            paramMdl.setBmk110PubEditKbn(pubEditKbn);
        }
        if (paramMdl.getBmk110GrpEditKbn() == -1) {
            ////グループブックマーク編集権限の初期化
            paramMdl.setBmk110GrpEditKbn(grpEditKbn);
        }
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk110ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Bmk110ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getBmk110initFlg() != 1) {
            BmkAconfDao dao = new BmkAconfDao(con);
            BmkAconfModel model = dao.selectAConf();

            setDspData(paramMdl, model);

            if (paramMdl.getBmk110PubEditKbn() == GSConstBookmark.EDIT_POW_USER) {
                //共有ブックマーク編集権限：ユーザ指定

                //デフォルトグループの設定
                GroupBiz grpBiz = new GroupBiz();
                paramMdl.setBmk110GroupSid(
                        grpBiz.getDefaultGroupSid(sessionUserSid, con));

                ArrayList<String> sidList = new ArrayList<String>();

                if (model != null && model.getBacPubEdit() == GSConstBookmark.EDIT_POW_USER) {

                    //編集ユーザ取得
                    BmkPublicEditDao daoPub = new BmkPublicEditDao(con);
                    List<BmkPublicEditModel> modelList = daoPub.select();

                    if (modelList != null) {
                        for (BmkPublicEditModel addModel : modelList) {
                            sidList.add(Integer.toString(addModel.getUsrSid()));
                        }
                    }
                }
                paramMdl.setBmk110UserSid(sidList.toArray(new String[0]));

            } else if (paramMdl.getBmk110PubEditKbn() == GSConstBookmark.EDIT_POW_GROUP) {
                //共有ブックマーク編集権限：グループ指定

                //編集グループ取得
                ArrayList<String> sidList = new ArrayList<String>();

                if (model != null && model.getBacPubEdit() == GSConstBookmark.EDIT_POW_GROUP) {

                    BmkPublicEditDao daoPub = new BmkPublicEditDao(con);
                    List<BmkPublicEditModel> modelList = daoPub.select();

                    if (modelList != null) {
                        for (BmkPublicEditModel addModel : modelList) {
                            sidList.add(Integer.toString(addModel.getGrpSid()));
                        }
                    }
                }
                paramMdl.setBmk110GrpSid(sidList.toArray(new String[0]));
            }

            paramMdl.setBmk110initFlg(1);
        }
    }
}
