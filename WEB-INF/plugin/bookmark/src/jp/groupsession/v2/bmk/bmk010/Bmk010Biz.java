package jp.groupsession.v2.bmk.bmk010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.bmk010.dao.Bmk010Dao;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010BodyModel;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010LabelModel;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010SearchModel;
import jp.groupsession.v2.bmk.bmk030.dao.Bmk030Dao;
import jp.groupsession.v2.bmk.dao.BmkBelongLabelDao;
import jp.groupsession.v2.bmk.dao.BmkBookmarkDao;
import jp.groupsession.v2.bmk.dao.BmkDatausedSumDao;
import jp.groupsession.v2.bmk.dao.BmkLabelDao;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.dao.BmkUrlDao;
import jp.groupsession.v2.bmk.model.BmkBookmarkModel;
import jp.groupsession.v2.bmk.model.BmkLabelModel;
import jp.groupsession.v2.bmk.model.BmkUconfModel;
import jp.groupsession.v2.bmk.model.BmkUrlModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ブックマーク画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk010Biz.class);

    /** グループ、ユーザ、ラベル選択値の初期値 */
    public static final int INIT_VALUE = -9;
    /** ラベルなし選択時のラベルSID */
    public static final int NO_LABEL_SID = -1;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Bmk010Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

//    /**
//     * <br>[機  能] デフォルトコンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public Bmk010Biz() {
//    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk010ParamModel
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Bmk010ParamModel paramMdl, Connection con)
    throws Exception {

        log__.debug("初期表示処理");

        HttpSession session = reqMdl__.getSession();
        BaseUserModel userMdl = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        //管理者設定ボタン表示フラグを設定
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, GSConstBookmark.PLUGIN_ID_BOOKMARK);

        paramMdl.setBmk010viewAdminBtn(GSConstBookmark.POW_NO);
        if (adminUser) {
            paramMdl.setBmk010viewAdminBtn(GSConstBookmark.POW_YES);
        }

        //セッションユーザSID
        int usrSid = userMdl.getUsrsid();
        //デフォルトグループ
        CmnBelongmDao belongmDao = new CmnBelongmDao(con);
        int defGrpSid = belongmDao.selectUserBelongGroupDef(usrSid);

        //コンボ選択値の初期値を設定
        if (paramMdl.getBmk010groupSid() == INIT_VALUE) {
            paramMdl.setBmk010groupSid(defGrpSid);
        }
        if (paramMdl.getBmk010userSid() == INIT_VALUE) {
            paramMdl.setBmk010userSid(usrSid);
        }

        //グループコンボを設定
        BookmarkBiz bmkBiz = new BookmarkBiz(con, reqMdl__);
        paramMdl.setBmk010groupCmbList(bmkBiz.getGroupLabelList(con));
        //ユーザコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        UserBiz userBiz = new UserBiz();
        paramMdl.setBmk010userCmbList(
                userBiz.getNormalUserLabelList(
                        con, paramMdl.getBmk010groupSid(), null, true, gsMsg));

        //選択ユーザSIDがユーザコンボに存在しないとき、選択ユーザSID＝"選択してください"をセット
        boolean usrFlg = false;
        for (LabelValueBean labelValue : paramMdl.getBmk010userCmbList()) {
            if (Integer.parseInt(labelValue.getValue()) == paramMdl.getBmk010userSid()) {
                usrFlg = true;
                break;
            }
        }
        if (!usrFlg) {
            paramMdl.setBmk010userSid(-1);
        }

        //ブックマーク区分
        int bmkKbn = paramMdl.getBmk010mode();
        //選択ユーザSID
        int selUsrSid = paramMdl.getBmk010userSid();
        //選択グループSID
        int selGrpSid = paramMdl.getBmk010groupSid();

        int targetSid = -1;
        if (bmkKbn == GSConstBookmark.BMK_KBN_KOJIN) {
            targetSid = selUsrSid;
        } else if (bmkKbn == GSConstBookmark.BMK_KBN_GROUP) {
            targetSid = selGrpSid;
        }

        //グループ編集権限設定ボタン表示フラグを設定
        paramMdl.setBmk010viewGroupBtn(GSConstBookmark.POW_NO);
        if (bmkKbn == GSConstBookmark.BMK_KBN_GROUP
                && bmkBiz.isGrpAdmin(con, selGrpSid)) {
            paramMdl.setBmk010viewGroupBtn(GSConstBookmark.POW_YES);
        }

        //編集権限フラグを設定
        paramMdl.setBmk010editPow(GSConstBookmark.POW_NO);
        if (bmkBiz.isEditBookmark(
                con, userMdl, bmkKbn, targetSid, GSConstBookmark.BMK_MODE_TOUROKU, null)) {
            paramMdl.setBmk010editPow(GSConstBookmark.POW_YES);
        }

        //選択ラベル名を設定
        paramMdl.setBmk010searchLabelName(__getLabelName(con, paramMdl.getBmk010searchLabel()));

        //ブックマーク一覧を設定
        List<Bmk010BodyModel> bList = __getBookmarkList(con, userMdl, paramMdl);
        paramMdl.setBmk010BookmarkList(bList);

        //ラベル一覧を設定
        paramMdl.setBmk010LabelList(__getLabelList(con, bmkKbn, selUsrSid, selGrpSid));
        //新着ブックマーク一覧を設定
        Bmk010Dao bmkDao = new Bmk010Dao(con, reqMdl__);

        //個人設定情報取得
        BmkUconfDao ucDao = new BmkUconfDao(con);
        BmkUconfModel ucMdl = new BmkUconfModel();

        //新着ブックマーク表示日数を設定
        ucMdl = ucDao.select(userMdl.getUsrsid());
        int newBmkDspCnt = GSConstBookmark.NEW_DEFO_DSP_COUNT;
        if (ucMdl != null) {
            newBmkDspCnt = ucMdl.getBucNewCnt();
        }
        paramMdl.setBmk010NewList(bmkDao.selectNewBmk(usrSid, newBmkDspCnt));
        //ランキング一覧を設定
        paramMdl.setBmk010RankingList(bmkDao.selectRankingList(usrSid));

        //削除チェックボックスを設定
        String[] delInfSid = paramMdl.getBmk010delInfSid();
        ArrayList<String> saveList = new ArrayList<String>();

        if (delInfSid != null) {
            log__.debug("delInfSid.length = " + delInfSid.length);
            for (int i = 0; i < delInfSid.length; i++) {
                String bmkSid = NullDefault.getString(delInfSid[i], "");

                boolean addFlg = true;
                for (int j = 0; j < bList.size(); j++) {
                    Bmk010BodyModel bMdl = bList.get(j);
                    if (bmkSid.equals(String.valueOf(bMdl.getBmkSid()))) {
                        addFlg = false;
                        break;
                    }
                }
                if (addFlg) {
                    saveList.add(String.valueOf(delInfSid[i]));
                    log__.debug("save SID = " + delInfSid[i]);
                }
            }
        }
        //saveリスト(現在ページ以外でチェックされている値)
        paramMdl.setBmk010SelectedDelSid(saveList);
    }

    /**
     * <br>[機  能] ブックマーク一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @param paramMdl Bmk010ParamModel
     * @return ブックマーク一覧
     * @throws Exception 実行例外
     */
    private List<Bmk010BodyModel> __getBookmarkList(Connection con,  BaseUserModel userMdl,
                                                     Bmk010ParamModel paramMdl)
    throws Exception {

        Bmk010Dao dao = new Bmk010Dao(con, reqMdl__);
        Bmk010SearchModel searchMdl = new Bmk010SearchModel();

        searchMdl.setUserMdl(userMdl);
        searchMdl.setBmkMode(paramMdl.getBmk010mode());
        searchMdl.setSortKey(paramMdl.getBmk010sortKey());
        searchMdl.setOrderKey(paramMdl.getBmk010orderKey());
        searchMdl.setGroup(paramMdl.getBmk010groupSid());
        searchMdl.setUser(paramMdl.getBmk010userSid());
        searchMdl.setLabel(paramMdl.getBmk010searchLabel());

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
        int page = paramMdl.getBmk010page();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        log__.debug("page ===> " + page);
        paramMdl.setBmk010page(page);
        paramMdl.setBmk010pageTop(page);
        paramMdl.setBmk010pageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setBmk010pageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));
        }
        searchMdl.setPage(page);
        searchMdl.setMaxViewCount(maxCnt);

        List<Bmk010BodyModel> labelList = dao.selectBmkList(searchMdl);
        return labelList;
    }

    /**
     * <br>[機  能] 抽出条件の選択ラベル名を取得する
     * <br>[解  説]
     * <br>[備  考] 「ラベルなし」のラベルSIDは"-1"とする
     * @param con コネクション
     * @param blbSid ラベルSID
     * @return ラベル名
     * @throws Exception 実行例外
     */
    private String __getLabelName(Connection con, int blbSid)
    throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.no");
        BmkLabelDao labelDao = new BmkLabelDao(con);
        BmkLabelModel labelModel = labelDao.select(blbSid);
        if (blbSid == NO_LABEL_SID) {
            return msg;
        } else if (labelModel != null) {
            return labelModel.getBlbName();
        }
        return "";
    }

    /**
     * <br>[機  能] ラベル一覧を取得する
     * <br>[解  説]
     * <br>[備  考] 「ラベルなし」のラベルSIDは"-1"とする
     * @param con コネクション
     * @param bmkKbn ブックマーク区分
     * @param usrSid 選択ユーザSID
     * @param grpSid 選択グループSID
     * @return ラベル一覧
     * @throws Exception 実行例外
     */
    private List<Bmk010LabelModel> __getLabelList(Connection con, int bmkKbn,
                                                   int usrSid, int grpSid)
    throws Exception {

        Bmk010Dao labelDao = new Bmk010Dao(con, reqMdl__);
        List<Bmk010LabelModel> labelList = labelDao.selectLabelList(bmkKbn, usrSid, grpSid, 0);
        Bmk010LabelModel labelModel = new Bmk010LabelModel();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("bmk.55");

        labelModel.setBlbSid(NO_LABEL_SID);
        labelModel.setBlbName(msg);
        int noLabelCount = labelDao.selectNoLabelCount(bmkKbn, usrSid, grpSid);
        labelModel.setBmkLabelCount(noLabelCount);
        labelList.add(labelModel);

        return labelList;
    }
    
    /**
     * <br>[機  能] 削除対象から存在しないブックマークを取り除く
     * <br>[解  説] 
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void removeNotExistBmk(Bmk010ParamModel paramMdl, Connection con)
    throws SQLException {

        List<String> existSidList = new ArrayList<String>();
        //ブックマーク存在チェック
        BmkBookmarkDao bmkDao = new BmkBookmarkDao(con);
        for (String delSidStr : paramMdl.getBmk010delInfSid()) {
            int delSid = Integer.parseInt(delSidStr);
            if (bmkDao.select(delSid) != null) {
                existSidList.add(delSidStr);
            }
        }
        paramMdl.setBmk010delInfSid(existSidList.toArray(new String[existSidList.size()]));
    }

    /**
     * <br>[機  能] 削除するブックマークのタイトルを取得する
     * <br>[解  説] 複数存在する場合は改行を挿入する
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param userSid ユーザSID
     * @param con コネクション
     * @return String 削除するブックマーク名称
     * @throws SQLException SQL実行例外
     */
    public String getDeleteBmkName(Bmk010ParamModel paramMdl, int userSid, Connection con)
    throws SQLException {

        //ブックマーク名称取得
        StringBuilder deleteBmk = new StringBuilder();
        List <BmkBookmarkModel> bList = __getConfList(paramMdl, userSid, con);

        for (int i = 0; i < bList.size(); i++) {
            BmkBookmarkModel model = bList.get(i);

            //分割処理
            BookmarkBiz bBiz = new BookmarkBiz(con, reqMdl__);
            List<String> titleDspList = bBiz.getStringCutList(40, model.getBmkTitle());
            int count = 0;
            for (String title : titleDspList) {
                if (count > 0) {
                    deleteBmk.append(GSConst.NEW_LINE_STR);
                }
                deleteBmk.append(title);
                count++;
            }

            //改行を挿入
            if (i < bList.size() - 1) {
                deleteBmk.append(GSConst.NEW_LINE_STR);
            }
        }
        return deleteBmk.toString();
    }

    /**
     * <br>[機  能] 選択したブックマークSID(複数)からブックマーク情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param userSid ユーザSID
     * @param con コネクション
     * @return ブックマーク情報
     * @throws SQLException SQL実行例外
     */
    private List<BmkBookmarkModel> __getConfList(
            Bmk010ParamModel paramMdl, int userSid, Connection con)
    throws SQLException {

        String[] delInfSid = paramMdl.getBmk010delInfSid();
        List<BmkBookmarkModel> ret = new ArrayList<BmkBookmarkModel>();
        
        for (int i = 0; i < delInfSid.length; i++) {
            //ブックマークSID
            int bmkSid = Integer.parseInt(delInfSid[i]);
            BmkBookmarkDao dao = new BmkBookmarkDao(con);
            ret.add(dao.select(bmkSid));
        }
        return ret;
    }

    /**
     * <br>[機  能] 削除実行
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteBmk(Bmk010ParamModel paramMdl, int userSid, Connection con)
    throws SQLException {

        String[] delInfSid = paramMdl.getBmk010delInfSid();
        int delCnt = 0;

        try {
            con.setAutoCommit(false);

            for (int i = 0; i < delInfSid.length; i++) {
                //ブックマークSID
                int bmkSid = Integer.parseInt(delInfSid[i]);
                //削除処理
                deleteBookmark(bmkSid, userSid, con);

                //削除チェックボックスクリア
                paramMdl.setBmk010delInfSid(null);
                delCnt++;
            }

            con.commit();

        } catch (SQLException e) {
            log__.warn("ブックマーク削除に失敗", e);
            JDBCUtil.rollback(con);
            throw e;
        }
        return delCnt;
    }

    /**
     * <br>[機  能] 削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bmkSid ブックマークSID
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void deleteBookmark(int bmkSid, int userSid, Connection con)
    throws SQLException {

        //ブックマーク情報取得
        BmkBookmarkDao bDao = new BmkBookmarkDao(con);
        BmkBookmarkModel bModel = bDao.select(bmkSid);

        int bmuSid = bModel.getBmuSid();
        
        BmkDatausedSumDao bdsDao = new BmkDatausedSumDao(con);
        bdsDao.insertDelDiff(Arrays.asList(bmkSid));

        //URLマスタ削除
        Bmk010Dao b010Dao = new Bmk010Dao(con, reqMdl__);
        if (!b010Dao.isBmkUrlCheck(bmkSid)) {
            BmkUrlDao uDao = new BmkUrlDao(con);
            uDao.delete(bModel.getBmuSid());
        }

        //ブックマーク情報削除
        bDao.delete(bmkSid);

        //ラベル付与情報削除
        BmkBelongLabelDao lDao = new BmkBelongLabelDao(con);
        lDao.deleteBmkSid(bmkSid);

        //URLマスタのタイトル、公開日を更新
        updateUrlPub(bmuSid, userSid, con);

    }

    /**
     * <br>[機  能] URLマスタのタイトル、公開日を更新
     * <br>[解  説]
     * <br>[備  考]
     * @param bmuSid URLSID
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void updateUrlPub(int bmuSid, int userSid, Connection con)
    throws SQLException {

        //URLマスタ更新
        BmkUrlDao uDao = new BmkUrlDao(con);
        BmkUrlModel uModel = uDao.select(bmuSid);
        if (uModel == null) {
            return;
        }

        //公開しているブックマークの有無確認
        Bmk030Dao b030Dao = new Bmk030Dao(con);
        String title = b030Dao.getPublicTitle(uModel.getBmuSid());
        if (title == null) {
            uModel.setBmuPubDate(null);
        } else {
            uModel.setBmuTitle(title);
        }
        uModel.setBmuEuid(userSid);
        uModel.setBmuEdate(new UDate());

        //更新処理
        uDao.update(uModel);
    }

    /**
     * <br>[機  能] 値クリア
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk010ParamModel
     */
    public void initData(Bmk010ParamModel paramMdl) {
        paramMdl.setBmk010orderKey(GSConstBookmark.ORDERKEY_DESC);
        paramMdl.setBmk010sortKey(GSConstBookmark.SORTKEY_ADATE);
        paramMdl.setBmk010searchLabel(INIT_VALUE);
        paramMdl.setBmk010groupSid(INIT_VALUE);
        paramMdl.setBmk010userSid(INIT_VALUE);
        paramMdl.setBmk010delInfSid(null);
    }

    /**
     * <br>[機  能] 抽出条件クリア
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk010ParamModel
     */
    public void initSelectData(Bmk010ParamModel paramMdl) {
        paramMdl.setBmk010orderKey(GSConstBookmark.ORDERKEY_DESC);
        paramMdl.setBmk010sortKey(GSConstBookmark.SORTKEY_ADATE);
        paramMdl.setBmk010searchLabel(INIT_VALUE);
        paramMdl.setBmk010delInfSid(null);
    }

    /**
     * <br>[機  能] 削除チェックボックスクリア
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk010ParamModel
     */
    public void initDelCheckBox(Bmk010ParamModel paramMdl) {
        paramMdl.setBmk010delInfSid(null);
    }
}