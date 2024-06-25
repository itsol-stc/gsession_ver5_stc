package jp.groupsession.v2.bmk.bmk130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkBookmarkDao;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.model.BmkBookmarkModel;
import jp.groupsession.v2.bmk.model.BmkUconfModel;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] 個人設定 表示件数設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk130Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk130Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Bmk130Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk130ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Bmk130ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        log__.debug("start");

        //表示件数コンボを設定
        paramMdl.setBmk130DspCountList(__getDspCount());

        //DBより現在の設定を取得する。(なければデフォルト)
        BmkUconfDao dao = new BmkUconfDao(con);
        BmkUconfModel model = dao.select(sessionUserSid);

        if (model == null) {
            paramMdl.setBmk130DspCount(GSConstBookmark.DEFAULT_BMKCOUNT);
        } else {
            paramMdl.setBmk130DspCount(
                    NullDefault.getString(
                            paramMdl.getBmk130DspCount(),
                            (String.valueOf(model.getBucCount()))));
        }

        if (paramMdl.getBmk130MyKbn() == -1) {
            //個人ブックマーク　メイン表示区分初期化
            if (model == null) {
                paramMdl.setBmk130MyKbn(GSConstBookmark.DSP_YES);
            } else {
                paramMdl.setBmk130MyKbn(model.getBucMainMy());
            }
        }
        if (paramMdl.getBmk130NewKbn() == -1) {
            //新着ブックマーク　メイン表示区分初期化
            if (model == null) {
                paramMdl.setBmk130NewKbn(GSConstBookmark.DSP_YES);
            } else {
                paramMdl.setBmk130NewKbn(model.getBucMainNew());
            }
        }

        //新着ブックマーク表示日数初期化
        if (paramMdl.getBmk130newCnt() == 0) {
            if (model == null) {
                paramMdl.setBmk130newCnt(GSConstBookmark.NEW_DEFO_DSP_COUNT);
            } else {
                paramMdl.setBmk130newCnt(model.getBucNewCnt());
            }
        }

        //個人ブックマークメイン表示区分：表示
        if (paramMdl.getBmk130MyKbn() == GSConstBookmark.DSP_YES) {
            //DBより現在の設定を取得する。
            BmkBookmarkDao bmkDao = new BmkBookmarkDao(con);
            List<BmkBookmarkModel> bookmarkList = bmkDao.getUsrBookmark(sessionUserSid);
            List<String> viewBmkList = new ArrayList<String>();
            if (!(bookmarkList == null || bookmarkList.isEmpty())) {
                for (BmkBookmarkModel bmkMdl : bookmarkList) {
                    if (bmkMdl.getBmkMain() == GSConstBookmark.DSP_YES) {
                        viewBmkList.add(bmkMdl.getBmkSort() + ":" + bmkMdl.getBmkSid());
                    }
                }
            }
            paramMdl.setBmk130ViewBmkList(viewBmkList.toArray(new String[viewBmkList.size()]));
        }

        paramMdl.setNewCntLabel(reqMdl__);

    }

    /**
     * <br>[機  能] 表示件数コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getDspCount() {

        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();

        for (int count = 10; count <= 50; count += 10) {
            String strCount = String.valueOf(count);
            labelList.add(new LabelValueBean(strCount, strCount));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 表示件数設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk130ParamModel
     * @param sessionUserSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return addEditFlg 登録モード(0:登録 1:編集)
     */
    public int setBmkUconfSetting(
            Bmk130ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        boolean commitFlg = false;

        int addEditFlg = 1;
        try {
            //DBの存在確認
            BmkUconfDao dao = new BmkUconfDao(con);
            //画面値セット
            BmkUconfModel crtMdl = createBmkUconfData(paramMdl, sessionUserSid);

            if (dao.update(crtMdl) == 0) {
                dao.insert(crtMdl);
                addEditFlg = 0;
            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
        return addEditFlg;
    }

    /**
     * <br>[機  能] 表示件数設定情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk130ParamModel
     * @param usrSid ユーザSID
     * @return KhmSyaikbnModel 社員区分情報
     */
    public BmkUconfModel createBmkUconfData(
            Bmk130ParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        BmkUconfModel mdl = new BmkUconfModel();

        mdl.setUsrSid(usrSid);

        mdl.setBucCount(Integer.parseInt(paramMdl.getBmk130DspCount()));
        mdl.setBucMainMy(paramMdl.getBmk130MyKbn());
        mdl.setBucMainNew(paramMdl.getBmk130NewKbn());
        mdl.setBucNewCnt(paramMdl.getBmk130newCnt());

        mdl.setBucAuid(usrSid);
        mdl.setBucAdate(nowDate);
        mdl.setBucEuid(usrSid);
        mdl.setBucEdate(nowDate);

        return mdl;
    }

    /**
     * <br>[機  能] 個人ブックマークのメイン表示区分の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void updateMaindisp(Connection con, Bmk130ParamModel paramMdl)
    throws SQLException {

        boolean commit = false;
        try {
            int sessionUserSid = reqMdl__.getSmodel().getUsrsid();

            BmkBookmarkDao dao = new BmkBookmarkDao(con);
            List<BmkBookmarkModel> dispList = dao.getUsrBookmark(sessionUserSid);

            BmkBookmarkModel model = createBmkData(sessionUserSid);
            int order = 1;

            List<String> viewList = Arrays.asList(paramMdl.getBmk130ViewBmkList());
            List<Integer> notViewList = new ArrayList<Integer>();
            List<Integer> changeNotViewList = new ArrayList<Integer>();
            for (BmkBookmarkModel bmlMdl : dispList) {
                if (!viewList.contains(String.valueOf(bmlMdl.getBmkSid()))) {
                    //表示から非表示に変更したブックマークの並び順は
                    //非表示ブックマークの一番後ろとする
                    if (bmlMdl.getBmkMain() == GSConstBookmark.DSP_YES) {
                        changeNotViewList.add(bmlMdl.getBmkSid());
                    } else {
                        notViewList.add(bmlMdl.getBmkSid());
                    }
                }
            }
            notViewList.addAll(changeNotViewList);

            //表示ブックマーク
            for (String addId : viewList) {
                model.setBmkSid(Integer.parseInt(addId));
                model.setBmkMain(GSConstBookmark.DSP_YES);
                model.setBmkSort(order++);
                dao.updateMainKbn(model, sessionUserSid);
            }
            //非表示ブックマーク
            for (int deleteId : notViewList) {
                model.setBmkSid(deleteId);
                model.setBmkMain(GSConstBookmark.DSP_NO);
                model.setBmkSort(order++);
                dao.updateMainKbn(model, sessionUserSid);
            }

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("メイン表示設定の登録", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
    }
    /**
     * <br>[機  能] ブックマーク情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return BmkBookmarkModel ブックマーク情報
     */
    public BmkBookmarkModel createBmkData(int usrSid) {

        UDate nowDate = new UDate();
        BmkBookmarkModel mdl = new BmkBookmarkModel();
        mdl.setUsrSid(usrSid);
        mdl.setBmkAuid(usrSid);
        mdl.setBmkAdate(nowDate);
        mdl.setBmkEuid(usrSid);
        mdl.setBmkEdate(nowDate);

        return mdl;
    }


}
