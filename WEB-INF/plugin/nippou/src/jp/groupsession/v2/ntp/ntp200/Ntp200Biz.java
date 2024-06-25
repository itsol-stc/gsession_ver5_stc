package jp.groupsession.v2.ntp.ntp200;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 案件検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp200Biz {
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** 価格検索条件 以上 */
    public static final int PRICE_MORE = 0;
    /** 価格検索条件 以下 */
    public static final int PRICE_LESS = 1;
    /** 検索件数 */
    public static final int SEARCH_CNT = 10;
    /** 検索呼呼び出し元画面ID 日報検索*/
    public static final String PARENT_GAMEN_ID_KENSAKU = "ntp100";
    /** 検索呼呼び出し元画面ID 日報登録 */
    public static final String PARENT_GAMEN_ID_TOUROKU = "ntp040";


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Ntp200Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp200ParamModel
     * @param userMdl ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp200ParamModel paramMdl,
            BaseUserModel userMdl,
            Connection con) throws SQLException {

        /** 案件選択画面処理 */
        if (paramMdl.getNtp200InitFlg() == 0) {
            //開始登録日の設定（現在日）
            UDate nowDate = new UDate();

            paramMdl.setNtp200FrYear(nowDate.getStrYear());
            paramMdl.setNtp200FrMonth(Integer.toString(nowDate.getMonth()));
            paramMdl.setNtp200FrDay(nowDate.getStrDay());

            paramMdl.setNtp200ToYear(nowDate.getStrYear());
            paramMdl.setNtp200ToMonth(Integer.toString(nowDate.getMonth()));
            paramMdl.setNtp200ToDay(nowDate.getStrDay());
            paramMdl.setNtp200InitFlg(1);

            //NtpCommonBiz cBiz = new NtpCommonBiz(con__, reqMdl__);
        }
        __setDspData(paramMdl, con);
    }

    /**
     * <br>[機  能] 検索処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp200ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void doSearch(
            Ntp200ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        //検索モデルの設定
        Ntp200SearchModel searchModel = setAnkenSearchModel(paramMdl);

        int maxCnt = SEARCH_CNT;

        //最大件数
        Ntp200AnkenDao ankenDao = new Ntp200AnkenDao(con);
        int searchCnt = ankenDao.getAnkenCount(searchModel);

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getNtp200PageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setNtp200PageTop(page);
        paramMdl.setNtp200PageBottom(page);
        paramMdl.setNtp200Page((page - 1) * maxCnt);

        //ページコンボ設定
        paramMdl.setNtp200PageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));

        //案件一覧の取得・設定
        paramMdl.setNtp200AnkenList(ankenDao.select(searchModel, page, maxCnt, reqMdl__));
    }

    /**
     * フォーム情報から検索モデルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp200ParamModel
     * @return Ntp200SearchModel 検索条件モデル
     * @throws SQLException SQL実行時例外
     */
    public Ntp200SearchModel setAnkenSearchModel(Ntp200ParamModel paramMdl) throws SQLException {
        //検索モデルの設定
        Ntp200SearchModel searchModel = new Ntp200SearchModel();

        searchModel.setNanCode(paramMdl.getNtp200NanCode());
        searchModel.setNanName(paramMdl.getNtp200NanName());
        searchModel.setNtp200AcoCode(paramMdl.getNtp200AcoCode());
        searchModel.setNtp200AcoName(paramMdl.getNtp200AcoName());
        searchModel.setNtp200AcoNameKana(paramMdl.getNtp200AcoNameKana());
        searchModel.setNtp200AbaName(paramMdl.getNtp200AbaName());
        searchModel.setNtp200State(paramMdl.getNtp200State());
        searchModel.setNtp200Syodan(paramMdl.getNtp200AnkenState());
        searchModel.setNtp200ShohinCategory(paramMdl.getNtp200CatSid());
        searchModel.setNtp200ShohinName(paramMdl.getNtp200ShohinName());
        searchModel.setSortKey1(paramMdl.getNtp200SortKey1());
        searchModel.setOrderKey1(paramMdl.getNtp200OrderKey1());
        //管理者権限がある場合
        CommonBiz commonBiz = new CommonBiz();
        searchModel.setAdminSearch(
                commonBiz.isPluginAdmin(con__,
                        reqMdl__.getSmodel(),
                        GSConstNippou.PLUGIN_ID_NIPPOU)
                );
        searchModel.setSessionUsrSid(reqMdl__.getSmodel().getUsrsid());
        if (PARENT_GAMEN_ID_TOUROKU.equals(paramMdl.getNtp200parentPageId())) {
           searchModel.setPermitKbn(GSConst.SP_AUTH_EDIT);
        }
        if (PARENT_GAMEN_ID_KENSAKU.equals(paramMdl.getNtp200parentPageId())) {
            searchModel.setPermitKbn(GSConst.SP_AUTH_VIEWONLY);
         }
        return searchModel;
    }

    /**
     * <br>[機  能] 画面表示データの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp200ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    private void __setDspData(Ntp200ParamModel paramMdl, Connection con) throws SQLException {
        NtpCommonBiz cBiz = new NtpCommonBiz(con, reqMdl__);
        //業務リスト取得
        paramMdl.setNtp200GyomuList(cBiz.getGyomuList(con, ""));
        String mes = "";
        if (paramMdl.getNtp200NgySid() != -1) {
            mes = "選択してください";
        }
        //プロセスリスト取得
        paramMdl.setNtp200ProcessList(cBiz.getProcessList(con, mes, paramMdl.getNtp200NgySid()));
        //コンタクトリスト取得
        paramMdl.setNtp200ContactList(cBiz.getContactList(con, ""));
        //状態リスト取得
        paramMdl.setNtp200StateList(cBiz.getStateList());
        //案件状態リスト取得
        paramMdl.setNtp200AnkenStateList(cBiz.getAnkenStateList());
        //ソートキーリスト取得
        paramMdl.setNtp200SortList(__getSortList());
        //年リスト取得
        paramMdl.setNtp200YearList(cBiz.getYearLavel(2009));
        //月リスト取得
        paramMdl.setNtp200MonthList(cBiz.getMonthLavel());
        //日リスト取得
        paramMdl.setNtp200DayList(cBiz.getDayLavel());
        //カテゴリリスト取得
        paramMdl.setNtp200CategoryList(cBiz.getCategoryLavel());
    }
    /**
     * <br>[機  能] ソートキーリストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ソートキーリストリスト
     */
    private List<LabelValueBean> __getSortList() {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("", String.valueOf(-1)));

        for (int i = 0; i < 11; i++) {
            labelList.add(
                    new LabelValueBean(GSConstNippou.SORT_KEY_NAN_ALL_TEXT[i],
                        String.valueOf(GSConstNippou.SORT_KEY_NAN_ALL[i])));
        }
        return labelList;
    }

}
