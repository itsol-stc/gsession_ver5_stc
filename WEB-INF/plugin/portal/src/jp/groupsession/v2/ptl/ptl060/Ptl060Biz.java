package jp.groupsession.v2.ptl.ptl060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル レイアウト設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl060Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl060Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Ptl060Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Ptl060ParamModel paramMdl)
    throws SQLException {
        log__.debug("START");
        if (paramMdl.getPtl060init() == 0) {
            //初期表示
            int ptlSid = paramMdl.getPtlPortalSid();
            PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);

            //レイアウト情報を取得する。
            List<PtlPortalLayoutModel> layoutList = layoutDao.select(ptlSid);

            if (layoutList != null && layoutList.size() > 0) {
                for (PtlPortalLayoutModel layoutModel : layoutList) {

                    switch (layoutModel.getPlyPosition()) {
                        case GSConstPortal.LAYOUT_POSITION_TOP :
                            paramMdl.setPtl060area1(__getCheckboxValue(layoutModel.getPtsView()));
                            break;
                        case GSConstPortal.LAYOUT_POSITION_BOTTOM :
                            paramMdl.setPtl060area2(__getCheckboxValue(layoutModel.getPtsView()));
                            break;
                        case GSConstPortal.LAYOUT_POSITION_LEFT :
                            paramMdl.setPtl060area3(__getCheckboxValue(layoutModel.getPtsView()));
                            break;
                        case GSConstPortal.LAYOUT_POSITION_CENTER :
                            paramMdl.setPtl060area4(__getCheckboxValue(layoutModel.getPtsView()));
                            break;
                        case GSConstPortal.LAYOUT_POSITION_RIGHT :
                            paramMdl.setPtl060area5(__getCheckboxValue(layoutModel.getPtsView()));
                            break;
                        default:
                            break;
                    }
                }
            }
            paramMdl.setPtl060init(1);
        }
        log__.debug("END");
    }

    /**
     * <br>[機  能] レイアウト表示区分より画面表示のチェックボックスの値を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ptsView レイアウト表示区分
     * @return checkboxValue レイアウト表示チェックボックスの値
     */
    private String __getCheckboxValue(int ptsView) {

        String checkboxValue = "0";
        if (ptsView == GSConstPortal.LAYOUT_VIEW_ON) {
            checkboxValue = "1";
        }
        return checkboxValue;
    }
}
