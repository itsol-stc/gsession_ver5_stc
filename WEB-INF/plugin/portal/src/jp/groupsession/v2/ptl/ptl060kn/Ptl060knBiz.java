package jp.groupsession.v2.ptl.ptl060kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalDao;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalModel;

/**
 * <br>[機  能] ポータル レイアウト設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl060knBiz {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ptl060knBiz() {
    }

    /**
     * <br>[機  能] レイアウト情報の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void updateData(Ptl060knParamModel paramMdl,
                        Connection con,
                        int userSid)
    throws SQLException {

        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);

        UDate now = new UDate();
        int ptlSid = paramMdl.getPtlPortalSid();

        PtlPortalLayoutModel model = new PtlPortalLayoutModel();
        model.setPtlSid(ptlSid);
        model.setPlyEuid(userSid);
        model.setPlyEdate(now);

        model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_TOP);
        model.setPtsView(__getLayoutDbValue(paramMdl.getPtl060area1()));
        layoutDao.update(model);

        model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_BOTTOM);
        model.setPtsView(__getLayoutDbValue(paramMdl.getPtl060area2()));
        layoutDao.update(model);

        model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_LEFT);
        model.setPtsView(__getLayoutDbValue(paramMdl.getPtl060area3()));
        layoutDao.update(model);

        model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_CENTER);
        model.setPtsView(__getLayoutDbValue(paramMdl.getPtl060area4()));
        layoutDao.update(model);

        model.setPlyPosition(GSConstPortal.LAYOUT_POSITION_RIGHT);
        model.setPtsView(__getLayoutDbValue(paramMdl.getPtl060area5()));
        layoutDao.update(model);

        //非表示のレイアウトに設定されている位置情報を移動する。
        __moveHideData(paramMdl, con);
    }

    /**
     * <br>[機  能] 画面表示のチェックボックスの値からDB登録用レイアウト表示区分を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param checkboxValue レイアウト表示区分
     * @return checkboxValue レイアウト表示チェックボックスの値
     */
    private int __getLayoutDbValue(String checkboxValue) {

        int ptsView = GSConstPortal.LAYOUT_VIEW_OFF;
        if (checkboxValue == null) {
            return ptsView;
        }
        if (checkboxValue.equals("1")) {
            ptsView = GSConstPortal.LAYOUT_VIEW_ON;
        }
        return ptsView;
    }

    /**
     * <br>[機  能] 非表示のレイアウトに設定されている位置情報を移動する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __moveHideData(Ptl060knParamModel paramMdl, Connection con)
    throws SQLException {


        PtlPortalPositionDao positionDao = new PtlPortalPositionDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();

        List<Integer> delPositionList = new ArrayList<Integer>();

        int topView = __getLayoutDbValue(paramMdl.getPtl060area1());
        int bottomView = __getLayoutDbValue(paramMdl.getPtl060area2());
        int leftView = __getLayoutDbValue(paramMdl.getPtl060area3());
        int centerView = __getLayoutDbValue(paramMdl.getPtl060area4());
        int rightView = __getLayoutDbValue(paramMdl.getPtl060area5());

        //削除されたレイアウトの移動先情報を取得する。
        int position = 0;
        if (leftView == GSConstPortal.LAYOUT_VIEW_ON) {
            position = GSConstPortal.LAYOUT_POSITION_LEFT;

        } else if (centerView == GSConstPortal.LAYOUT_VIEW_ON) {
            position = GSConstPortal.LAYOUT_POSITION_CENTER;

        } else if (rightView == GSConstPortal.LAYOUT_VIEW_ON) {
            position = GSConstPortal.LAYOUT_POSITION_RIGHT;

        } else if (topView == GSConstPortal.LAYOUT_VIEW_ON) {
            position = GSConstPortal.LAYOUT_POSITION_TOP;

        } else if (bottomView == GSConstPortal.LAYOUT_VIEW_ON) {
            position = GSConstPortal.LAYOUT_POSITION_BOTTOM;
        }

        //削除されるレイアウト情報リストを作成する。
        if (leftView == GSConstPortal.LAYOUT_VIEW_OFF) {
            delPositionList.add(GSConstPortal.LAYOUT_POSITION_LEFT);
        }
        if (centerView == GSConstPortal.LAYOUT_VIEW_OFF) {
            delPositionList.add(GSConstPortal.LAYOUT_POSITION_CENTER);
        }
        if (rightView == GSConstPortal.LAYOUT_VIEW_OFF) {
            delPositionList.add(GSConstPortal.LAYOUT_POSITION_RIGHT);
        }

        if (topView == GSConstPortal.LAYOUT_VIEW_OFF) {
            delPositionList.add(GSConstPortal.LAYOUT_POSITION_TOP);
        }
        if (bottomView == GSConstPortal.LAYOUT_VIEW_OFF) {
            delPositionList.add(GSConstPortal.LAYOUT_POSITION_BOTTOM);
        }

        //位置情報を更新
        positionDao.update(ptlSid, position, delPositionList);
    }

    /**
     * <br>[機  能] ポータル名を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return ポータル名
     * @throws SQLException SQL実行例外
     */
    public String getPortalName(Connection con, Ptl060knParamModel paramMdl) throws SQLException {

        PtlPortalDao dao = new PtlPortalDao(con);
        String ptlName = "";

        int ptlSid = paramMdl.getPtlPortalSid();
        PtlPortalModel model = dao.select(ptlSid);
        if (model != null) {
            ptlName = model.getPtlName();
        }

        return ptlName;
    }
}
