package jp.groupsession.v2.ptl.ptl050kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalDao;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalSortDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.man.model.base.PtlPortalSortModel;
import jp.groupsession.v2.ptl.PtlCommonBiz;
import jp.groupsession.v2.ptl.dao.PtlPortalConfReadDao;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル ポータル登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl050knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl050knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Ptl050knParamModel paramMdl, Connection con)
    throws SQLException {
        log__.debug("START");

        //説明（表示用）
        paramMdl.setPtl050knviewDescription(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getPtl050description()), ""));

        //閲覧メンバリストを設定する
        paramMdl.setPtl050knMemNameList(__getForumFullLabel(paramMdl, con));

        log__.debug("End");
    }

    /**
     * <br>[機  能] ポータル情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void insertData(Ptl050knParamModel paramMdl,
                        Connection con,
                        MlCountMtController cntCon,
                        int userSid)
    throws SQLException {

        PtlPortalDao ptlDao = new PtlPortalDao(con);
        PtlPortalSortDao ptlSortDao = new PtlPortalSortDao(con);
        PtlPortalLayoutDao ptlLayoutDao = new PtlPortalLayoutDao(con);
        PtlPortalConfReadDao ptlConfReadDao = new PtlPortalConfReadDao(con);
        PtlCommonBiz ptlBiz = new PtlCommonBiz(con);

        UDate now = new UDate();

        //ポータルSID採番
        int ptlSid = (int) cntCon.getSaibanNumber(GSConstPortal.SBNSID_SUB_PORTAL,
                                                  GSConstPortal.SBNSID_SUB_PORTAL,
                                                  userSid);

        //ポータル情報の登録
        PtlPortalModel ptlModel = new PtlPortalModel();
        ptlModel.setPtlSid(ptlSid);
        ptlModel.setPtlName(NullDefault.getString(paramMdl.getPtl050name(), ""));
        ptlModel.setPtlOpen(paramMdl.getPtl050openKbn());
        ptlModel.setPtlDescription(NullDefault.getString(paramMdl.getPtl050description(), ""));
        ptlModel.setPtlAccess(paramMdl.getPtl050accessKbn());
        ptlModel.setPtlAuid(userSid);
        ptlModel.setPtlAdate(now);
        ptlModel.setPtlEuid(userSid);
        ptlModel.setPtlEdate(now);
        ptlDao.insert(ptlModel);


        //並び順の最大値を取得する。
        int maxSort = ptlSortDao.getMaxSort(GSConstPortal.PTS_KBN_COMMON);

        //ポータル並び順の登録
        PtlPortalSortModel ptlSortModel = new PtlPortalSortModel();
        ptlSortModel.setPtlSid(ptlSid);
        ptlSortModel.setPtsKbn(GSConstPortal.PTS_KBN_COMMON);
        ptlSortModel.setPtsSort(maxSort + 1);
        ptlSortModel.setUsrSid(0);
        ptlSortDao.insert(ptlSortModel);


        //ポータルレイアウトの登録
        PtlPortalLayoutModel ptlLayoutModel = new PtlPortalLayoutModel();
        ptlLayoutModel.setPtlSid(ptlSid);
        ptlLayoutModel.setPtsView(GSConstPortal.PTL_OPENKBN_OK);
        ptlLayoutModel.setPlyAuid(userSid);
        ptlLayoutModel.setPlyAdate(now);
        ptlLayoutModel.setPlyEuid(userSid);
        ptlLayoutModel.setPlyEdate(now);

        List<Integer> positionList = new ArrayList<Integer>();
        positionList.add(GSConstPortal.LAYOUT_POSITION_TOP);
        positionList.add(GSConstPortal.LAYOUT_POSITION_BOTTOM);
        positionList.add(GSConstPortal.LAYOUT_POSITION_LEFT);
        positionList.add(GSConstPortal.LAYOUT_POSITION_CENTER);
        positionList.add(GSConstPortal.LAYOUT_POSITION_RIGHT);

        for (Integer position : positionList) {
            ptlLayoutModel.setPlyPosition(position);
            ptlLayoutDao.insert(ptlLayoutModel);
        }

        //ポータル閲覧設定の登録
        if (paramMdl.getPtl050accessKbn() == GSConstPortal.PTL_ACCESS_ON) {

            String[] memberSids = paramMdl.getPtl050memberSid();
            if (memberSids != null && memberSids.length > 0) {
                ptlConfReadDao.insert(ptlSid, memberSids);
            }
        }

        //インフォメーションの登録
        int ptpSort = 1;
        ptlBiz.insertInfomation(ptlSid, userSid, GSConstPortal.LAYOUT_POSITION_TOP, ptpSort);
    }

    /**
     * <br>[機  能] ポータル情報の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param cntCon MlCountMtController
     * @throws SQLException SQL実行例外
     */
    public void updateData(Ptl050knParamModel paramMdl,
                        Connection con,
                        MlCountMtController cntCon,
                        int userSid)
    throws SQLException {

        PtlPortalDao ptlDao = new PtlPortalDao(con);
        PtlPortalConfReadDao ptlConfReadDao = new PtlPortalConfReadDao(con);

        UDate now = new UDate();

        //ポータルSID採番
        int ptlSid = paramMdl.getPtlPortalSid();

        //ポータル情報の登録
        PtlPortalModel ptlModel = new PtlPortalModel();
        ptlModel.setPtlSid(ptlSid);
        ptlModel.setPtlName(NullDefault.getString(paramMdl.getPtl050name(), ""));
        ptlModel.setPtlOpen(paramMdl.getPtl050openKbn());
        ptlModel.setPtlDescription(NullDefault.getString(paramMdl.getPtl050description(), ""));
        ptlModel.setPtlAccess(paramMdl.getPtl050accessKbn());
        ptlModel.setPtlEuid(userSid);
        ptlModel.setPtlEdate(now);
        ptlDao.update(ptlModel);

        //ポータル閲覧設定の削除
        ptlConfReadDao.delete(ptlSid);

        //ポータル閲覧設定の登録
        if (paramMdl.getPtl050accessKbn() == GSConstPortal.PTL_ACCESS_ON) {
            String[] memberSids = paramMdl.getPtl050memberSid();
            if (memberSids != null && memberSids.length > 0) {
                ptlConfReadDao.insert(ptlSid, memberSids);
            }
        }

    }

    /**
     * <br>[機  能] 閲覧メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumFullLabel(Ptl050knParamModel paramMdl,
                                                        Connection con)
    throws SQLException {

        //取得するユーザSID・グループSID
        String[] leftFull = paramMdl.getPtl050memberSid();
        UserGroupSelectBiz selBiz = new UserGroupSelectBiz();
        return selBiz.getSelectedLabel(leftFull, con);
    }
}