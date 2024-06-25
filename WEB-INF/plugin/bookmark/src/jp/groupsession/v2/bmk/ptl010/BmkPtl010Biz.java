package jp.groupsession.v2.bmk.ptl010;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.bmk010.dao.Bmk010Dao;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010BodyModel;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010SearchModel;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.model.BmkUconfModel;
import jp.groupsession.v2.bmk.ptl020.BmkPtl020Biz;
import jp.groupsession.v2.cmn.biz.PortletBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ポータル グループブックマーク表示用のビジネスロジック
 * @author JTS
 */
public class BmkPtl010Biz implements PortletBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkPtl010Biz.class);

    /** グループ、ユーザ、ラベル選択値の初期値 */
    public static final int INIT_VALUE = -9;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public BmkPtl010Biz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public BmkPtl010Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl BmkPtl010ParamModel
     * @param con コネクション
     * @param userMdl ユーザmodel
     * @throws Exception 実行例外
     */
    public void setInitData(BmkPtl010ParamModel paramMdl, Connection con,
                            BaseUserModel userMdl)
    throws Exception {
        log__.debug("START");

        int groupSid = paramMdl.getBmkGrpSid();

        //ブックマーク一覧を設定
        List<Bmk010BodyModel> bList = __getBookmarkList(con, userMdl, paramMdl);
        if (bList == null) {
            bList = new ArrayList<Bmk010BodyModel>();
        }
        paramMdl.setBmkPtl010List(bList);

        //グループ名を取得
        CmnGroupmDao groupDao = new CmnGroupmDao(con);
        CmnGroupmModel groupModel = groupDao.select(groupSid);
        if (groupModel == null) {
            groupModel = new CmnGroupmModel();
        }
        String groupName = groupModel.getGrpName();
        paramMdl.setGroupName(groupName);

    }


    /**
     * <br>[機  能] ブックマーク一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @param paramMdl BmkPtl010ParamModel
     * @return ブックマーク一覧
     * @throws Exception 実行例外
     */
    private List<Bmk010BodyModel> __getBookmarkList(Connection con,  BaseUserModel userMdl,
                                                     BmkPtl010ParamModel paramMdl)
    throws Exception {

        Bmk010Dao dao = new Bmk010Dao(con, reqMdl__);
        Bmk010SearchModel searchMdl = new Bmk010SearchModel();

        searchMdl.setUserMdl(userMdl);
        searchMdl.setBmkMode(GSConstBookmark.BMK_KBN_GROUP);
        searchMdl.setSortKey(GSConstBookmark.SORTKEY_ADATE);
        searchMdl.setOrderKey(GSConstBookmark.ORDERKEY_DESC);
        searchMdl.setGroup(paramMdl.getBmkGrpSid());
        searchMdl.setUser(userMdl.getUsrsid());
        searchMdl.setLabel(INIT_VALUE);

        //ブックマークの件数
        int searchCnt = dao.selectBmkCount(searchMdl);

        //１ページに表示する件数
        BmkUconfDao uConfDao = new BmkUconfDao(con);
        BmkUconfModel uConfModel = uConfDao.select(userMdl.getUsrsid());
        int maxCnt = Integer.parseInt(GSConstBookmark.DEFAULT_BMKCOUNT);
        if (uConfModel != null) {
            maxCnt = uConfModel.getBucCount();
        }

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        log__.debug("maxPage ===> " + maxPage);
        int page = paramMdl.getBmkptl010page();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        log__.debug("page ===> " + page);

        paramMdl.setBmkptl010page(page);

        //ページコンボ作成
        paramMdl.setBmkptl010pageCmb(PageUtil.createPageOptions(searchCnt, maxCnt));

        searchMdl.setPage(page);
        searchMdl.setMaxViewCount(maxCnt);

        List<Bmk010BodyModel> labelList = dao.selectBmkList(searchMdl);
        return labelList;
    }

    /**
     * <br>プラグインポートレットタイトルを取得する。
     * @param con コネクション
     * @param paramMap パラメータマップ
     * @return title ポートレットプラグインタイトル
     * @throws Exception 実行時例外
     */
    public String getPortletTitle(Connection con, HashMap<String, String> paramMap)
    throws Exception {

        String title = "";

        if (paramMap == null) {
            return title;
        }

        //マップからパラメータを取得
        String paramGrpSid = paramMap.get(BmkPtl020Biz.BMK_PORTLET_PARAM1);

        //グループ名
        CmnGroupmDao groupDao = new CmnGroupmDao(con);
        int grepSid = NullDefault.getInt(paramGrpSid, 0);
        CmnGroupmModel groupModel = groupDao.select(grepSid);
        if (groupModel == null) {
            groupModel = new CmnGroupmModel();
        }
        title = groupModel.getGrpName();

        return title;
    }
}
