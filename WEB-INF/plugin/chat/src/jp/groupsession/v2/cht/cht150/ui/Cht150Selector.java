package jp.groupsession.v2.cht.cht150.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.dao.ChtCategoryDao;
import jp.groupsession.v2.cht.dao.ChtGroupInfDao;
import jp.groupsession.v2.cht.model.ChtCategoryModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;

/*
 * チャットカテゴリ登録画面 チャットグループ登録UIで使用するSelector
 */
public class Cht150Selector extends AbstractSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected Cht150Selector(ParamForm param) {
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
    public static class ParamForm extends SelectorFactory<Cht150Selector, ParamForm> {
        public ParamForm(Class<Cht150Selector> targetClz) {
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
        ParamForm ret = new ParamForm(Cht150Selector.class);
        return ret;
    }

    @Override
    public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
                                        ParameterObject paramModel) throws SQLException {

        //編集対象カテゴリSID
        int editCategorySid = (Integer) paramModel.get("cht140CategoryLink");
        // 編集フラグ
        boolean editFlg
            = ((Integer) paramModel.get("cht140ProcMode")) == GSConstChat.CHAT_MODE_EDIT;

        ChtCategoryDao catDao = new ChtCategoryDao(con);
        List<ChtCategoryModel> categoryList = catDao.select();

        List<IChild> grpList = new ArrayList<>();
        for (ChtCategoryModel mdl:categoryList) {
            //編集対象のカテゴリはカテゴリコンボボックスに表示しない
            boolean addFlg = editFlg && mdl.getChcSid() == editCategorySid;
            if (!addFlg) {
                grpList.add(
                        new Child(
                                mdl.getChcName(),
                                String.valueOf(mdl.getChcSid())));
            }

        }

        return grpList;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
           ParameterObject paramModel, List<String> selectedSidList) throws SQLException {

        ChtGroupInfDao infDao = new ChtGroupInfDao(con);
        List<ChtGroupInfModel> chtGroupList
            = infDao.selectGroupWhereCgiSid(selectedSidList.toArray(new String[0]));

        List<IChild> ret = new ArrayList<>();
        ret.addAll(chtGroupList
                    .stream()
                    .map(mdl -> __createChtGroupChild(mdl))
                    .collect(Collectors.toList())
               );
        return ret;
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {

        //コンボボックス選択カテゴリSID
        int categorySid = NullDefault.getInt(selectedGrpSid, -1);
        //編集対象テゴリSID
        int editCategorySid = (Integer) paramModel.get("cht140CategoryLink");
        //編集フラグ
        boolean editFlg
            = ((Integer) paramModel.get("cht140ProcMode")) == GSConstChat.CHAT_MODE_EDIT;

        //編集中でカテゴリなしを選択した場合、表示中のカテゴリ内のグループ情報を追加
        List<Integer> chcSidList = new ArrayList<Integer>();
        chcSidList.add(categorySid);
        if (editFlg &&  categorySid == GSConstChat.CHAT_CHC_SID_NO) {
            chcSidList.add(editCategorySid);
        }

        //チャットグループを取得
        ChtGroupInfDao infDao = new ChtGroupInfDao(con);
        List<ChtGroupInfModel> chtGroupList = infDao.selectGroupWhereChcSid(chcSidList);

        //選択済みのチャットグループを除外
        List<IChild> ret = new ArrayList<>();
        ret.addAll(chtGroupList
                    .stream()
                    .filter(mdl -> !selectedSidList.contains(String.valueOf(mdl.getCgiSid())))
                    .map(mdl -> __createChtGroupChild(mdl))
                    .collect(Collectors.toList())
                );

        return ret;
    }

    /**
     * <br>[機  能] チャットグループ情報から選択UIの子要素を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl アドレス帳情報
     * @return 選択UIの子要素
     */
    private Child __createChtGroupChild(ChtGroupInfModel mdl) {
        return new Child(
                mdl.getCgiName(),
                String.valueOf(mdl.getCgiSid())
                );
    }
}
