package jp.groupsession.v2.ptl.ptl050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalDao;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.dao.base.PtlPortalSortDao;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.ptl.dao.PtlPortalConfReadDao;
import jp.groupsession.v2.ptl.model.PtlPortalConfReadModel;

/**
 * <br>[機  能] ポータル ポータル登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl050Biz {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ptl050Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Ptl050ParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {

        if (paramMdl.getPtl050init() == 0) {
            //初期表示処理

            if (paramMdl.getPtlPortalSid() > 0) {
                //編集モード時の初期値設定
                setEditInitData(paramMdl, con);
            }

            paramMdl.setPtl050init(1);
        }
    }

    /**
     * <br>[機  能] ポータルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deletePortal(Ptl050ParamModel paramMdl, Connection con)
    throws SQLException {

        PtlPortalDao ptlDao = new PtlPortalDao(con);
        PtlPortalSortDao ptlSortDao = new PtlPortalSortDao(con);
        PtlPortalLayoutDao ptlLayoutDao = new PtlPortalLayoutDao(con);
        PtlPortalConfReadDao ptlConfReadDao = new PtlPortalConfReadDao(con);
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        PtlPortalPositionParamDao ptlPositionParamDao = new PtlPortalPositionParamDao(con);

        int ptlSid = paramMdl.getPtlPortalSid();
        if (ptlSid < 1) {
            return;
        }

        //ポータル閲覧設定の削除
        ptlConfReadDao.delete(ptlSid);

        //ポータル表示順の削除
        ptlSortDao.delete(ptlSid);

        //ポータル位置設定の削除
        ptlPositionDao.delete(ptlSid);

        //ポータル位置設定の削除
        ptlPositionParamDao.delete(ptlSid);

        //ポータルレイアウトの削除
        ptlLayoutDao.delete(ptlSid);

        //ポータルの削除を行う。
        ptlDao.delete(ptlSid);

    }

    /**
     * <br>[機  能] 編集時の初期値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setEditInitData(Ptl050ParamModel paramMdl, Connection con)
    throws SQLException {

        PtlPortalDao ptlDao = new PtlPortalDao(con);
        PtlPortalConfReadDao ptlConfReadDao = new PtlPortalConfReadDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();

        //ポータル情報を取得する。
        PtlPortalModel ptlModel = ptlDao.select(ptlSid);

        if (ptlModel == null) {
            return;
        }

        paramMdl.setPtl050name(ptlModel.getPtlName());
        paramMdl.setPtl050openKbn(ptlModel.getPtlOpen());
        paramMdl.setPtl050description(ptlModel.getPtlDescription());
        paramMdl.setPtl050accessKbn(ptlModel.getPtlAccess());

        if (ptlModel.getPtlAccess() == GSConstPortal.PTL_ACCESS_ON) {
            //閲覧グループ・閲覧ユーザ一覧を取得
            List<PtlPortalConfReadModel> readList = ptlConfReadDao.select(ptlSid);

            if (readList != null && readList.size() > 0) {
                //閲覧メンバーを設定
                ArrayList<String> memberList = new ArrayList<String>();

                for (PtlPortalConfReadModel model : readList) {
                    if (model.getUsrSid() != -1) {
                        memberList.add(String.valueOf(model.getUsrSid()));
                    }
                    if (model.getGrpSid() != -1) {
                        memberList.add(String.valueOf(GSConstPortal.MEMBER_GROUP_HEADSTR
                                                + model.getGrpSid()));
                    }
                }
                String[] usrGrpSids = (String[]) memberList.toArray(new String[memberList.size()]);
                paramMdl.setPtl050memberSid(usrGrpSids);
            }
        }
    }
}
