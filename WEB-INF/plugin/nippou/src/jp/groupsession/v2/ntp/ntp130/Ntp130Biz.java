package jp.groupsession.v2.ntp.ntp130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpShohinCategoryDao;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.model.NtpShohinCategoryModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] 日報 商品一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp130Biz {

    /** 画面ID */
    public static final String SCR_ID = "ntp130";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp130Biz.class);

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /** 価格検索条件 以上 */
    public static final int PRICE_MORE = 0;
    /** 価格検索条件 以下 */
    public static final int PRICE_LESS = 1;

    /** 商品ポップアップ最大件数 */
    private static final int SHOHIN_POP_SIZE = 10;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Ntp130Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp130ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getNtp130DspKbn() == 0) {
            //商品一覧

            //カテゴリコンボ作成
            ArrayList<LabelValueBean> ntpShohinCatList = new ArrayList<LabelValueBean>();
            NtpShohinCategoryDao catDao = new NtpShohinCategoryDao(con);
            List<NtpShohinCategoryModel> catMdlList = catDao.select();
            ntpShohinCatList.add(new LabelValueBean("すべて", String.valueOf(-1)));
            for (NtpShohinCategoryModel mdl : catMdlList) {
                String catName = mdl.getNscName();
                String catSid = String.valueOf(mdl.getNscSid());
                ntpShohinCatList.add(new LabelValueBean(catName, catSid));
            }

            paramMdl.setNtp130CategoryList(ntpShohinCatList);

            //ソートキーコンボ設定
            paramMdl.setNtp130SortList(__getSortList());

            if (paramMdl.getNtp130InitFlg() == 0) {
                paramMdl.setNtp130SvChkShohinSidList(paramMdl.getNtp061ChkShohinSidList());
                paramMdl.setNtp130InitFlg(1);
            } else {
                ArrayList<Ntp130ShohinModel> sList = paramMdl.getNtp130ShohinList();
                //選択チェックボックスを設定
                String[] chkSid = paramMdl.getNtp130ChkShohinSidList();
                ArrayList<String> saveList = null;

                if (chkSid != null && sList != null) {

                    saveList = new ArrayList<String>();

                    for (int i = 0; i < chkSid.length; i++) {
                        String nhnSid = NullDefault.getString(chkSid[i], "");
                        boolean addFlg = true;
                        for (int j = 0; j < sList.size(); j++) {
                            NtpShohinModel sMdl = sList.get(j);
                            if (nhnSid.equals(String.valueOf(sMdl.getNhnSid()))) {
                                addFlg = false;
                                break;
                            }
                        }
                        if (addFlg) {
                            saveList.add(String.valueOf(chkSid[i]));
                        }
                    }
                }
                if (saveList != null) {
                    for (String sid : saveList) {
                    log__.debug("*****セーブリスト:" + sid);
                    }
                }
                //saveリスト(現在ページ以外でチェックされている値)
                paramMdl.setNtp130SelectedSid(saveList);
            }
        } else {

            //カテゴリ一覧を取得
            NtpShohinCategoryDao categoryDao = new NtpShohinCategoryDao(con);
            List<NtpShohinCategoryModel> categoryList = categoryDao.select();

            //カテゴリ一覧をセット
            ArrayList<NtpShohinCategoryModel> list = new ArrayList<NtpShohinCategoryModel>();
            for (NtpShohinCategoryModel model : categoryList) {
                String biko = NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(model.getNscBiko()), "");
                NtpShohinCategoryModel ulcMdl = new NtpShohinCategoryModel();
                ulcMdl.setNscAuid(model.getNscAuid());
                ulcMdl.setNscName(model.getNscName());
                ulcMdl.setNscBiko(biko);
                ulcMdl.setNscSid(model.getNscSid());
                ulcMdl.setNscValue(String.valueOf(model.getNscSid()));
                list.add(ulcMdl);
            }
            paramMdl.setNtp130CatList(list);

            //チェックされているラジオがNULLの場合、初期値設定
            if (StringUtil.isNullZeroString(paramMdl.getNtp130SortRadio())
            && categoryList.size() > 0) {
                NtpShohinCategoryModel model = categoryList.get(0);
                paramMdl.setNtp130SortRadio(String.valueOf(model.getNscSid()));
            }

        }
    }

    /**
     * <br>[機  能] 検索処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void doSearch(
            Ntp130ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        //検索モデルの設定
        Ntp130SearchModel searchMdl = new Ntp130SearchModel();
        searchMdl.setNscSid(paramMdl.getNtp130CatSid());
        searchMdl.setNhnCode(paramMdl.getNtp130NhnCode());
        searchMdl.setNhnName(paramMdl.getNtp130NhnName());

        String price = NullDefault.getStringZeroLength(paramMdl.getNtp130NhnPriceSale(), "-1");
        searchMdl.setNhnPriceSale(Integer.parseInt(price.replaceAll(",", "")));
        searchMdl.setNhnPriceSaleKbn(paramMdl.getNtp130NhnPriceSaleKbn());

        String cost = NullDefault.getStringZeroLength(paramMdl.getNtp130NhnPriceCost(), "-1");
        searchMdl.setNhnPriceCost(Integer.parseInt(cost.replaceAll(",", "")));
        searchMdl.setNhnPriceCostKbn(paramMdl.getNtp130NhnPriceCostKbn());

        searchMdl.setSortKey1(paramMdl.getNtp130SortKey1());
        searchMdl.setSortKey2(paramMdl.getNtp130SortKey2());
        searchMdl.setOrderKey1(paramMdl.getNtp130OrderKey1());
        searchMdl.setOrderKey2(paramMdl.getNtp130OrderKey2());

        int maxCnt = 10;
        NtpCommonBiz biz = new NtpCommonBiz(con, reqMdl__);
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con, sessionUserSid);
        if (pconf != null) {
            maxCnt = pconf.getNprDspList();
        }

        //最大件数
        Ntp130ShohinDao shohinDao = new Ntp130ShohinDao(con, reqMdl__);
        int searchCnt = shohinDao.getShohinCount(searchMdl);

        //ページ調整
        int maxPage = searchCnt / maxCnt;
        if ((searchCnt % maxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getNtp130PageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setNtp130PageTop(page);
        paramMdl.setNtp130PageBottom(page);
        paramMdl.setNtp130Page((page - 1) * maxCnt);

        //ページコンボ設定
        paramMdl.setNtp130PageCmbList(PageUtil.createPageOptions(searchCnt, maxCnt));

        //検索モデルにて商品一覧の取得・設定
        paramMdl.setNtp130ShohinList(
                (ArrayList<Ntp130ShohinModel>) shohinDao.select(searchMdl, page, maxCnt));
    }

    /**
     * <br>[機  能] ソートキーリストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ソートキーリストリスト
     */
    private List<LabelValueBean> __getSortList() {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        //labelList.add(new LabelValueBean("", String.valueOf(-1)));

        for (int i = 0; i < 6; i++) {
            labelList.add(
                    new LabelValueBean(GSConstNippou.SORT_KEY_NHK_ALL_TEXT[i],
                        String.valueOf(GSConstNippou.SORT_KEY_NHK_ALL[i])));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 商品一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param categorySid カテゴリSID
     * @param pageNum ページ番号
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONObject getJsonShohinList(Connection con,
                            int categorySid, int pageNum) throws Exception {


        NtpShohinDao nhnDao = new NtpShohinDao(con);

        JSONObject jsonData = new JSONObject();
        List<NtpShohinModel> nhnList = null;
        List<NtpShohinModel> newNhnList = null;

        nhnList = nhnDao.selectCategory(categorySid);

        if (pageNum == 0) {
            pageNum = 1;
        }

        //Jsonデータ成形
        if (!nhnList.isEmpty()) {

            int maxPageSize = PageUtil.getPageCount(nhnList.size(), SHOHIN_POP_SIZE);

            if (pageNum > maxPageSize) {
                pageNum = maxPageSize;
            }

            int maxSize = pageNum * SHOHIN_POP_SIZE;

            Ntp130ShohinModel ntp130ShohinMdl = new Ntp130ShohinModel();
            NtpShohinModel shohinMdl = null;
            newNhnList = new ArrayList<NtpShohinModel>();

            ntp130ShohinMdl.setMaxPageSize(maxPageSize);
            ntp130ShohinMdl.setPageNum(pageNum);

            for (int i = (pageNum - 1) * SHOHIN_POP_SIZE; i < nhnList.size(); i++) {
                if (i < maxSize) {
                    shohinMdl = nhnList.get(i);
                    shohinMdl.setNhnAdate(null);
                    shohinMdl.setNhnEdate(null);
                    newNhnList.add(shohinMdl);
                } else {
                    break;
                }
            }
            ntp130ShohinMdl.setShohinDataList(newNhnList);
            jsonData = JSONObject.fromObject(ntp130ShohinMdl);
        }

        return jsonData;

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Ntp130ParamModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Ntp130ParamModel paramMdl, int changeKbn)
        throws SQLException {

        SortResult<NtpShohinCategoryModel> result = null;
        final NtpShohinCategoryDao dao = new NtpShohinCategoryDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getNtp130SortRadio();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }


        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<NtpShohinCategoryModel> sortBiz =
                SortChangeBiz.<NtpShohinCategoryModel> builder()
                .setFuncTargetList(() -> {
                    return dao.select();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getNscSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getNscSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getNscSid() == m2.getNscSid()) {
                        return 0;
                    } else {
                        return (m1.getNscSid() - m2.getNscSid()) 
                                / Math.abs(m1.getNscSid() - m2.getNscSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    dao.updateSort(m.getNscSid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getNscSid());
            paramMdl.setNtp130SortRadio(newSortRadio);
        }
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return テンポラリディレクトリパス
     */
    public String getTempDir() {
       NtpCommonBiz ntpCmnBiz = new NtpCommonBiz();
        return ntpCmnBiz.getTempDir(reqMdl__, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void deleteTempDir() {
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz();
        ntpCmnBiz.deleteTempDir(reqMdl__, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを初期化
     * <br>[解  説]
     * <br>[備  考]
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir() throws IOToolsException {
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz();
        ntpCmnBiz.clearTempDir(reqMdl__, SCR_ID);
    }
}
