package jp.groupsession.v2.tcd.tcd130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] タイムカード 管理者設定 ユーザ別時間帯設定一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd130Biz {

    /** 画面ID */
    public static final String SCR_ID = "tcd130";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd130Biz.class);
    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd130Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(Tcd130ParamModel paramMdl, Connection con)
    throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //DBより設定情報を取得。なければデフォルト値とする。
        TimecardBiz tcdBiz = new TimecardBiz(reqMdl__);
        TcdAdmConfModel admMdl = tcdBiz.getTcdAdmConfModel(sessionUsrSid, con);

        log__.debug("STRAT");
        //グループ情報・ユーザ情報を設定する
        __setUserGroupCombo(paramMdl, con);
        //時間帯情報を取得する
        __setTimezoneCombo(paramMdl, con);

        List<Integer> dspUsrSidList = new ArrayList<Integer>();
        if (paramMdl.getTcd130SearchFlg() == 1) {
            int searchCnt = __getSearchCount(paramMdl, con, admMdl.getTacDefTimezone());
            //検索結果件数
            if (searchCnt > 0) {
                //1ページ内の表示件数
                int viewCount = 30;

                //ページ調整
                int maxPage = searchCnt / viewCount;
                if ((searchCnt % viewCount) > 0) {
                    maxPage++;
                }
                int page = paramMdl.getTcd130pageTop();
                if (page < 1) {
                    page = 1;
                } else if (page > maxPage) {
                    page = maxPage;
                }
                paramMdl.setTcd130pageTop(page);
                paramMdl.setTcd130pageBottom(page);
                //ページコンボ設定
                List<LabelValueBean> pageList = PageUtil.createPageOptions(searchCnt, viewCount);
                if (pageList != null && pageList.size() > 1) {
                    paramMdl.setTcd130PageList(pageList);
                }

                //検索処理
                dspUsrSidList = __search(paramMdl, con, viewCount, admMdl.getTacDefTimezone());
            }
        }
        // チェック保持
        String[] selectInfSid = paramMdl.getTcdSelectUserlist();
        ArrayList<String> saveList = new ArrayList<String>();
        if (selectInfSid != null) {
            for (int i = 0; i < selectInfSid.length; i++) {
                int sid = NullDefault.getInt(selectInfSid[i], -1);
                boolean addFlg = true;
                for (int j = 0; j < dspUsrSidList.size(); j++) {
                    int dspUsrSid =  dspUsrSidList.get(j);
                    if (sid == dspUsrSid) {
                        addFlg = false;
                        break;
                    }
                }
                if (addFlg) {
                    saveList.add(String.valueOf(selectInfSid[i]));
                }
            }
            paramMdl.setTcdSvSelectUserlist(saveList);
        }
        log__.debug("end");
    }

    /**
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con Connection
     * @param viewCount 表示件数
     * @param defTimezone 管理者設定デフォルト時間帯
     * @throws SQLException SQL実行時例外
     * @return 表示中のユーザ一覧SID
     */
    private List<Integer> __search(Tcd130ParamModel paramMdl,
            Connection con, int viewCount, int defTimezone) throws SQLException {

        TcdTimezonePriDao dao = new TcdTimezonePriDao(con);
        List<Tcd130DispModel> userDataList = new ArrayList<Tcd130DispModel>();
        /* 検索結果によるユーザ情報の取得 */
        userDataList = dao.getSearchDataList(__createSearchModel(paramMdl, viewCount, defTimezone));
        List<Integer> usrSidList = new ArrayList<Integer>();
        for (Tcd130DispModel data: userDataList) {
            usrSidList.add(data.getUsrSid());
        }
        // タイムカード時間帯個人情報を取得
        TcdTimezoneInfoDao infDao = new TcdTimezoneInfoDao(con);
        Map<Integer, List<Tcd130DispModel>> timeZoneMap
                = infDao.getTimeZoneInfoMap(usrSidList);

        // 管理者設定により指定されているデフォルト時間帯情報の取得
        TcdTimezoneInfoModel defaultMdl = infDao.select(defTimezone);

        for (Tcd130DispModel userData: userDataList) {
            List<Tcd130DispModel> timezoneList = timeZoneMap.get(userData.getUsrSid());
            if (timezoneList == null) {
                userData.setDefaultTimeZoneName(defaultMdl.getTtiName());
                userData.setTimeZoneName(defaultMdl.getTtiName());
                userData.setTimeZoneUkoFlg(defaultMdl.getTtiUse());
                continue;
            }
            for (Tcd130DispModel timezoneInfo: timezoneList) {
                if (timezoneInfo.getTtpDefault() == 1) {
                    userData.setDefaultTimeZoneName(timezoneInfo.getTimeZoneName());
                }
            }
            userData.setTimeZoneNames(timezoneList);
        }
        paramMdl.setTcd130SearchList(userDataList);
        return usrSidList;
    }

    /**
     * <br>[機  能] 検索件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con Connection
     * @return 検索件数
     * @throws SQLException 例外
     */
    private int __getSearchCount(Tcd130ParamModel paramMdl, Connection con, int defTimezone) throws SQLException {

        TcdTimezonePriDao dao = new TcdTimezonePriDao(con);
        Tcd130SearchModel searchMdl = __createSearchModel(paramMdl, 0, defTimezone);
        int searchCnt = dao.getSearchDataCount(searchMdl);

        return searchCnt;
    }

    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param viewCount 最大表示件数
     * @return 検索条件
     */
    private Tcd130SearchModel __createSearchModel(Tcd130ParamModel paramMdl, int viewCount, int defTimezone) {

        Tcd130SearchModel searchModel = new Tcd130SearchModel();
        searchModel.setGroupSid(paramMdl.getTcd130SvGroupSid());
        searchModel.setUserSid(paramMdl.getTcd130SvUserSid());
        searchModel.setTimeZone(paramMdl.getTcd130SvTimezoneSid());
        searchModel.setDefaultTimeZone(paramMdl.getTcd130SvDefaultTimezoneSid());
        searchModel.setTacDefaultTimeZone(defTimezone);
        searchModel.setPage(paramMdl.getTcd130pageTop());
        searchModel.setMaxCnt(viewCount);
        return searchModel;
    }

    /**
     * <br>[機  能] グループ・ユーザコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     *
     */
    private void __setUserGroupCombo(Tcd130ParamModel paramMdl, Connection con)
            throws SQLException {

        //グループ
        GsMessage gsMsg = new GsMessage(reqMdl__);
        GroupBiz gbiz = new GroupBiz();
        ArrayList <LabelValueBean> groupComboAll = gbiz.getGroupCombLabelList(con, false, gsMsg);
        ArrayList <LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
        groupCombo.add(new LabelValueBean(
                gsMsg.getMessage("cmn.specified.no"), "-1"));
        groupCombo.addAll(groupComboAll);
        paramMdl.setTcd130GroupList(groupCombo);
        //ユーザ
        UserBiz uBiz = new UserBiz();
        List <UsrLabelValueBean> userComboAll = uBiz.getNormalUserLabelList(
                con, paramMdl.getTcd130GroupSid(), null, false, gsMsg);
        ArrayList <UsrLabelValueBean> userCombo = new ArrayList<UsrLabelValueBean>();
        userCombo.add(new UsrLabelValueBean(
                gsMsg.getMessage("cmn.specified.no"), "-1"));
        userCombo.addAll(userComboAll);
        paramMdl.setTcd130UserList(userCombo);
    }

    /**
     * <br>[機  能] 時間帯コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     *
     */
    private void __setTimezoneCombo(Tcd130ParamModel paramMdl, Connection con)
            throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        TcdTimezoneInfoDao infDao = new TcdTimezoneInfoDao(con);
        List<TcdTimezoneInfoModel> infList = infDao.select();
        List<LabelValueBean> timezoneNameList = new ArrayList<LabelValueBean>();
        timezoneNameList.add(new LabelValueBean(
                gsMsg.getMessage("cmn.specified.no"), "-1"));
        for (TcdTimezoneInfoModel infMdl : infList) {
            timezoneNameList.add(new LabelValueBean(
                    infMdl.getTtiName(),String.valueOf(infMdl.getTtiSid())));
        }
        paramMdl.setTcd130TimezoneNameList(timezoneNameList);
    }
}
