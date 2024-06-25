package jp.groupsession.v2.bmk.bmk130.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.bmk.dao.BmkBookmarkDao;
import jp.groupsession.v2.bmk.model.BmkBookmarkModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;

public class Bmk130BookmarkSelector extends AbstractSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected Bmk130BookmarkSelector(ParamForm param) {
       super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<Bmk130BookmarkSelector, ParamForm> {
        public ParamForm(Class<Bmk130BookmarkSelector> targetClz) {
            super(targetClz);
        }
    }

    /**
     *
     * <br>[機  能] ビルダークラスの生成
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダークラス
     */
    public static ParamForm builder() {
        ParamForm ret = new ParamForm(Bmk130BookmarkSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
           ParameterObject paramModel, List<String> selectedSidList) throws SQLException {

        //ブックマーク情報を取得
        List<BmkBookmarkModel> bookmarkList = __getBookmarkList(reqMdl, con);

        List<String> selectBmkSidList = __getSelectBmkSidList(selectedSidList);

        int order = 1;
        List<IChild> ret = new ArrayList<>();
        for (BmkBookmarkModel bmkMdl : bookmarkList) {
            if (selectBmkSidList.indexOf(String.valueOf(bmkMdl.getBmkSid())) >= 0) {
                ret.add(__createBookmarkChild(bmkMdl, order++));
            }
        }

        return ret;
    }
    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {

        //ブックマーク情報を取得
        List<BmkBookmarkModel> bookmarkList = __getBookmarkList(reqMdl, con);

        List<String> selectBmkSidList = __getSelectBmkSidList(selectedSidList);

        int order = selectedSidList.size() + 1;
        List<IChild> ret = new ArrayList<>();
        for (BmkBookmarkModel bmkMdl : bookmarkList) {
            if (selectBmkSidList.indexOf(String.valueOf(bmkMdl.getBmkSid())) < 0) {
                ret.add(__createBookmarkChild(bmkMdl, order++));
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] ブックマーク情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con DBコネクション
     * @return ブックマーク情報
     * @throws SQLException SQL実行時例外
     */
    private List<BmkBookmarkModel> __getBookmarkList(RequestModel reqMdl, Connection con)
    throws SQLException {
        BmkBookmarkDao bmkDao = new BmkBookmarkDao(con);
        return bmkDao.getUsrBookmark(reqMdl.getSmodel().getUsrsid());
    }

    /**
     * <br>[機  能] ブックマーク情報から選択UIの子要素を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl ブックマーク情報
     * @param order 並び順
     * @return 選択UIの子要素
     */
    private Child __createBookmarkChild(BmkBookmarkModel mdl, int order) {

        return new Child(
                order + ":" + mdl.getBmkTitle(),
                String.valueOf(mdl.getBmkSid())
                );
    }

    private List<String> __getSelectBmkSidList(List<String> selectedSidList) {
        return selectedSidList
                .stream()
                .map(sid -> __getBmkSid(sid))
                .collect(Collectors.toList());
    }

    private String __getBmkSid(String optionValue) {
        String bmkSid = optionValue.toString();
        if (optionValue.contains(":")) {
            bmkSid = optionValue.substring(optionValue.indexOf(":") + 1);
        }
        return bmkSid;
    }
}
