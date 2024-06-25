package jp.groupsession.v2.bmk.bmk060.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.bmk.dao.BmkLabelDataDao;
import jp.groupsession.v2.bmk.model.BmkLabelDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;

public class Bmk060LabelSelector extends AbstractSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected Bmk060LabelSelector(ParamForm param) {
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
    public static class ParamForm extends SelectorFactory<Bmk060LabelSelector, ParamForm> {
        public ParamForm(Class<Bmk060LabelSelector> targetClz) {
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
        ParamForm ret = new ParamForm(Bmk060LabelSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
           ParameterObject paramModel, List<String> selectedSidList) throws SQLException {

        //ラベル情報を取得
        ArrayList<BmkLabelDataModel> labelList = __getLabelList(reqMdl, con, paramModel);

        Integer editLabelSid = __getEditLabelSid(paramModel);
        List<IChild> ret = new ArrayList<>();
        ret.addAll(labelList
                    .stream()
                    .filter(mdl -> (selectedSidList.indexOf(String.valueOf(mdl.getBlbSid())) >= 0)
                                    && mdl.getBlbSid() != editLabelSid.intValue())
                    .map(mdl -> __createLabelChild(mdl))
                    .collect(Collectors.toList())
               );
        return ret;
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {

        //ラベル情報を取得
        ArrayList<BmkLabelDataModel> labelList = __getLabelList(reqMdl, con, paramModel);

        Integer editLabelSid = __getEditLabelSid(paramModel);
        List<IChild> ret = new ArrayList<>();
        ret.addAll(labelList
                    .stream()
                    .filter(mdl -> (selectedSidList.indexOf(String.valueOf(mdl.getBlbSid())) < 0)
                                    && mdl.getBlbSid() != editLabelSid.intValue())
                    .map(mdl -> __createLabelChild(mdl))
                    .collect(Collectors.toList())
                );
        return ret;
    }

    /**
     * <br>[機  能] ラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con DBコネクション
     * @param paramModel パラメータ
     * @return ラベル情報
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<BmkLabelDataModel> __getLabelList(RequestModel reqMdl, Connection con,
           ParameterObject paramModel) throws SQLException {
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        Integer mode = (Integer) paramModel.get("bmk010mode");
        Integer groupSid = (Integer) paramModel.get("bmk010groupSid");

        BmkLabelDataDao dao = new BmkLabelDataDao(con);
        return dao.select(mode, sessionUserSid, groupSid);
    }

    /**
     * <br>[機  能] ラベル情報から選択UIの子要素を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl ラベル情報
     * @return 選択UIの子要素
     */
    private Child __createLabelChild(BmkLabelDataModel mdl) {
        return new Child(
                mdl.getBlbName() + " (" + mdl.getBlbCnt() + ")",
                String.valueOf(mdl.getBlbSid())
                );
    }

    private Integer __getEditLabelSid(ParameterObject paramModel) {

        Integer editLabelSid = (Integer) paramModel.get("bmk050LblSid");
        if (editLabelSid <= 0) {
            editLabelSid = -1;
        }

        return editLabelSid;
    }
}
