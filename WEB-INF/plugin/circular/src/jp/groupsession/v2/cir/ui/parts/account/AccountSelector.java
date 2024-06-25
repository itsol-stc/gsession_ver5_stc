package jp.groupsession.v2.cir.ui.parts.account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.EnumModeType;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.IDefaultGroupSelectListner;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class AccountSelector extends AbstractSelector implements IDefaultGroupSelectListner  {
    /** 含有クラス ユーザ選択機能実装*/
    private final UserGroupSelector base__;
    /** 表示区分 1:グループ 2:マイグループ 3:代表アカウント */
    private int dspKbn__ = 1;

    /** 代表アカウント一覧キャッシュ*/
    private List<LabelValueBean> cacLabelCash__ = null;



    protected AccountSelector(ParamForm param) {
        super(param);
        base__ = UserGroupSelector.builder()
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainType(EnumSelectType.USER)
                .chainOnlyPluginUser(GSConstCircular.PLUGIN_ID_CIRCULAR)
                .build();

    }
    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<AccountSelector, ParamForm> {

        public ParamForm(Class<AccountSelector> targetClz) {
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
        ParamForm ret = new ParamForm(AccountSelector.class);
        return ret;
    }
    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
                    throws SQLException {

        //ユーザアカウントを取得
        List<IChild> ret = new ArrayList<IChild>();
        ret.addAll(base__.answerSelectedList(
                reqMdl,
                con,
                paramModel,
                selectedSidList));

        //代表アカウントを取得
        ret.addAll(
            __getCacLabelCash(con)
                .stream()
                .filter(lv -> selectedSidList.contains(lv.getValue()))
                .map(lv -> new Child(
                        lv.getLabel(),
                        lv.getValue()
                        )
                    )
                .collect(Collectors.toList())
        );
        return ret;
    }
    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        List<IChild> ret = new ArrayList<IChild>();
        //グループ選択
        //マイグループ選択
        if (dspKbn__ == 1 || dspKbn__ == 2) {

            //ユーザアカウントを取得
            ret.addAll(base__.answerSelectionList(
                    reqMdl,
                    con,
                    paramModel,
                    selectedGrpSid,
                    selectedSidList));

            return ret;
        }
        //代表アカウント選択
        if (dspKbn__ == 3) {
            //代表アカウントを取得
            ret.addAll(
                __getCacLabelCash(con)
                    .stream()
                    .filter(lv -> selectedSidList.contains(lv.getValue()) == false)
                    .map(lv -> new Child(
                            lv.getLabel(),
                            lv.getValue()
                            )
                        )
                    .collect(Collectors.toList())
            );
            return ret;
        }
        return ret;
    }
    @Override
    public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {
        if (dspKbn__ == 3) {
            return List.of();
        }
        if (dspKbn__ == 2) {
            //ユーザSIDからマイグループ情報を取得する
            int userSid = reqMdl.getSmodel().getUsrsid();
            CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
            List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(userSid);

            return cmgList.stream()
                    .map(cmg -> new GroupChild(cmg))
                    .collect(Collectors.toList());
        }
        return base__.answerGroupList(reqMdl, con, paramModel);
    }
    @Override
    public String answerDefaultGroup(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> groupSidList)
            throws SQLException {
        return base__.answerDefaultGroup(reqMdl, con, paramModel, groupSidList);
    }
    @Override
    public boolean isUseDetailSearch() {
        if (dspKbn__ == 1) {
            return base__.isUseDetailSearch();
        }
        return false;
    }
    /**
     * <p>dspKbn を取得します。
     * @return dspKbn
     * @see jp.groupsession.v2.cir.ui.parts.account.AccountSelector#dspKbn__
     */
    public int getDspKbn() {
        return dspKbn__;
    }
    /**
     * <p>dspKbn をセットします。
     * @param dspKbn dspKbn
     * @see jp.groupsession.v2.cir.ui.parts.account.AccountSelector#dspKbn__
     */
    public void setDspKbn(int dspKbn) {
        dspKbn__ = dspKbn;
    }
    /**
     * <p>cacLabelCash を取得します。
     * @param con
     * @return cacLabelCash
     * @throws SQLException
     * @see cacLabelCash__
     */
    private List<LabelValueBean> __getCacLabelCash(Connection con) throws SQLException {
        if (cacLabelCash__ != null) {
            return cacLabelCash__;
        }
        CirAccountDao cacDao = new CirAccountDao(con);
        cacLabelCash__ = cacDao.selectCirAccountLv()
                            .stream()
                            .map(lv -> new LabelValueBean(
                                    lv.getLabel(),
                                    GSConstCircular.CIR_ACCOUNT_STR + lv.getValue()))
                            .collect(Collectors.toList());
        return cacLabelCash__;
    }
    @Override
    public void setMode(EnumModeType mode) {
        super.setMode(mode);
        base__.setMode(mode);
    }
    @Override
    public void setSelectGroupSid(String selectGroupSid) {
        super.setSelectGroupSid(selectGroupSid);
        base__.setSelectGroupSid(selectGroupSid);
    }
    @Override
    public void setSelectTargetKey(String selectTargetKey) {
        super.setSelectTargetKey(selectTargetKey);
        base__.setSelectTargetKey(selectTargetKey);
    }
    @Override
    public void setModeText(String mode) {
        super.setModeText(mode);
        base__.setModeText(mode);
    }
    @Override
    public void setMultiselectorFilter(String[] multiselectorFilter) {
        super.setMultiselectorFilter(multiselectorFilter);
        base__.setMultiselectorFilter(multiselectorFilter);
    }
}
