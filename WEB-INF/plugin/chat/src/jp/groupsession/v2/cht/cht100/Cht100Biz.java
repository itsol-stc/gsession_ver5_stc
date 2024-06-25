package jp.groupsession.v2.cht.cht100;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.biz.ChtBiz;
import jp.groupsession.v2.cht.dao.ChatDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.model.ChatInformationModel;
import jp.groupsession.v2.cht.model.ChatSearchModel;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャットグループ管理のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht100Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Cht100Biz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * */
    public Cht100Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * 表示処理
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行例外
     *
     * */
    public void setInitData(Cht100ParamModel paramMdl)
            throws SQLException {
        log__.debug("init");
        if (paramMdl.getCht100InitFlg() == GSConstMain.DSP_FIRST) {
           // 初期表示設定
            paramMdl.setCht100GroupId(GSConstChat.SEARCH_GROUPID_IN);
            paramMdl.setCht100GroupName(GSConstChat.SEARCH_GROUPNAME_IN);
            paramMdl.setCht100GroupInfo(GSConstChat.SEARCH_GROUPINFO_IN);
            paramMdl.setCht100AdminMember(GSConstChat.SEARCH_GROUPADMIN_IN);
            paramMdl.setCht100GeneralMember(GSConstChat.SEARCH_GENERALUSER_IN);
            paramMdl.setCht100InitFlg(GSConstMain.DSP_ALREADY);
        }
        // カテゴリ追加
        ChtBiz biz = new ChtBiz(con__, reqMdl__);
        paramMdl.setCht100CategoryList(biz.getCategory(true));
        // グループ、ユーザコンボの設定
        GroupBiz gBiz = new GroupBiz();
        UserBiz uBiz = new UserBiz();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setCht100GroupList(
                gBiz.getGroupCombLabelList(con__, true, gsMsg));
        paramMdl.setCht100UserList(uBiz.getNormalUserLabelList(con__,
                paramMdl.getCht100SelectGroup(), null, true, gsMsg));
        ChtGroupInfDao infDao = new ChtGroupInfDao(con__);
        UDate minDate = infDao.getMinDate();
        
        if (minDate != null) {
            UDate now = new UDate();
            paramMdl.setCht100OldYear(now.getYear() - minDate.getYear());
        }
        
        if (paramMdl.getCht100SearchFlg() == 1) {
            ChatDao chtDao = new ChatDao(con__);
            // 最大件数
            int maxCnt = chtDao.getChatGroupDataCount(
                    __createSearchModel(paramMdl, 0, false));
            // １ページの最大表示件数
            int viewCnt = GSConstChat.CHAT_MAX_VEIWCOUNT;

            // ページ調整
            int maxPage = maxCnt / viewCnt;
            if ((maxCnt % viewCnt) > 0) {
                maxPage++;
            }
            int page = paramMdl.getCht100PageTop();
            if (page < 1) {
                page = 1;
            } else if (page > maxPage) {
                page = maxPage;
            }
            paramMdl.setCht100PageTop(page);
            paramMdl.setCht100PageBottom(page);
            // ページコンボ設定
            if (maxCnt > viewCnt) {
                paramMdl.setPageList(
                        PageUtil.createPageOptions(maxCnt, viewCnt));
            }
            // 検索結果一覧取得
            List<ChatInformationModel> chtGrpDataList = chtDao
                    .getChtGrpDataList(
                            __createSearchModel(paramMdl, viewCnt, false));
            paramMdl.setCht100GrpDataList(chtGrpDataList);
        } else {
            paramMdl.setCht100PageTop(1);
            paramMdl.setCht100PageBottom(1);
        }
    }


    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param viewCount 最大表示件数
     * @param countFlg 最大件数フラグ
     * @return 検索条件
     */
    private ChatSearchModel __createSearchModel(
            Cht100ParamModel paramMdl, int viewCount, boolean countFlg) {
        ChatSearchModel searchModel = new ChatSearchModel();
        if (countFlg) {
            searchModel.setKeyword(paramMdl.getCht100Keyword());
            searchModel.setKeywordList(__createKeywordList(paramMdl.getCht100Keyword()));
            searchModel.setAndOr(paramMdl.getCht100AndOr());
            searchModel.setGroupId(paramMdl.getCht100GroupId());
            searchModel.setGroupName(paramMdl.getCht100GroupName());
            searchModel.setGroupInfo(paramMdl.getCht100GroupInfo());
            searchModel.setCategory(paramMdl.getCht100Category());
            searchModel.setStatusKbn(paramMdl.getCht100StatusKbn());
            searchModel.setSelectGroup(paramMdl.getCht100SelectGroup());
            searchModel.setSelectUser(paramMdl.getCht100SelectUser());
            searchModel.setAdminMember(paramMdl.getCht100AdminMember());
            searchModel.setGeneralMember(paramMdl.getCht100GeneralMember());
            searchModel.setCreateDateFr(
                    __createUDate(paramMdl.getCht100CreateYearFr(),
                            paramMdl.getCht100CreateMonthFr(),
                            paramMdl.getCht100CreateDayFr()));
            searchModel.setCreateDateTo(
                    __createUDate(paramMdl.getSvCht100CreateYearTo(),
                            paramMdl.getCht100CreateMonthTo(),
                            paramMdl.getCht100CreateDayTo()));
            searchModel
                    .setUpDateFr(__createUDate(paramMdl.getCht100UpdateYearFr(),
                            paramMdl.getCht100UpdateMonthFr(),
                            paramMdl.getCht100UpdateDayFr()));
            searchModel
                    .setUpDateTo(__createUDate(paramMdl.getCht100UpdateYearTo(),
                            paramMdl.getCht100UpdateMonthTo(),
                            paramMdl.getCht100UpdateDayTo()));
            searchModel.setSortKey(paramMdl.getCht100SortKey());
            searchModel.setOrderKey(paramMdl.getCht100OrderKey());
            searchModel.setPage(paramMdl.getCht100PageTop());
            searchModel.setMaxCnt(viewCount);
        } else {
            searchModel.setKeyword(paramMdl.getSvCht100Keyword());
            searchModel.setKeywordList(__createKeywordList(paramMdl.getSvCht100Keyword()));
            searchModel.setAndOr(paramMdl.getSvCht100AndOr());
            searchModel.setGroupId(paramMdl.getSvCht100GroupId());
            searchModel.setGroupName(paramMdl.getSvCht100GroupName());
            searchModel.setGroupInfo(paramMdl.getSvCht100GroupInfo());
            searchModel.setCategory(paramMdl.getSvCht100Category());
            searchModel.setStatusKbn(paramMdl.getSvCht100StatusKbn());
            searchModel.setSelectGroup(paramMdl.getSvCht100SelectGroup());
            searchModel.setSelectUser(paramMdl.getSvCht100SelectUser());
            searchModel.setAdminMember(paramMdl.getSvCht100AdminMember());
            searchModel.setGeneralMember(paramMdl.getSvCht100GeneralMember());
            searchModel.setCreateDateFr(
                    __createUDate(paramMdl.getSvCht100CreateYearFr(),
                            paramMdl.getSvCht100CreateMonthFr(),
                            paramMdl.getSvCht100CreateDayFr()));
            searchModel.setCreateDateTo(
                    __createUDate(paramMdl.getSvCht100CreateYearTo(),
                            paramMdl.getSvCht100CreateMonthTo(),
                            paramMdl.getSvCht100CreateDayTo()));
            searchModel
                    .setUpDateFr(__createUDate(paramMdl.getSvCht100UpdateYearFr(),
                            paramMdl.getSvCht100UpdateMonthFr(),
                            paramMdl.getSvCht100UpdateDayFr()));
            searchModel
                    .setUpDateTo(__createUDate(paramMdl.getSvCht100UpdateYearTo(),
                            paramMdl.getSvCht100UpdateMonthTo(),
                            paramMdl.getSvCht100UpdateDayTo()));

            searchModel.setSortKey(paramMdl.getCht100SortKey());
            searchModel.setOrderKey(paramMdl.getCht100OrderKey());
            searchModel.setPage(paramMdl.getCht100PageTop());
            searchModel.setMaxCnt(viewCount);
        }

        return searchModel;
    }

    /**
     * <br>[機  能] UDateのインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @return UDate
     */
    private UDate __createUDate(int year, int month, int day) {
        if (year <= 0 || month <= 0 || day <= 0) {
            return null;
        }

        UDate date = UDate.getInstance(0);
        date.setDate(year, month, day);
        return date;
    }

    /**
     * <br>[機  能] 検索結果の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return 検索結果の件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchCount(Cht100ParamModel paramMdl) throws SQLException {

        ChatDao chtDao = new ChatDao(con__);
        return chtDao.getChatGroupDataCount(__createSearchModel(paramMdl, 0, true));
    }

    /**
     * <br>[機  能] キーワード一覧を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param keyword キーワード
     * @return キーワード一覧
     */
    private List<String> __createKeywordList(String keyword) {

        if (StringUtil.isNullZeroString(keyword)) {
            return null;
        }
        List <String> keywordList = new ArrayList <String>();
        String searchKey = StringUtil.substitute(keyword, "　", " ");
        StringTokenizer st = new StringTokenizer(searchKey, " ");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (!StringUtil.isNullZeroString(str)) {
                keywordList.add(str);
            }
        }

        return keywordList;
    }


}
