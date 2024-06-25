package jp.groupsession.v2.sch.ui.parts.reserve;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 施設選択UI
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ShisetuSelector extends AbstractSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected ShisetuSelector(ParamForm param) {
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
    public static class ParamForm extends SelectorFactory<ShisetuSelector, ParamForm> {

        public ParamForm(Class<ShisetuSelector> targetClz) {
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
        ParamForm ret = new ParamForm(ShisetuSelector.class);
        return ret;
    }

    /** 施設予約の管理者*/
    Boolean rsvAdmin__;

    /**
     * <p>rsvAdmin を取得します。
     *
     * @param reqMdl
     * @param con
     * @return rsvAdmin
     * @throws SQLException
     * @see jp.groupsession.v2.sch.ui.parts.reserve.ShisetuSelector#rsvAdmin__
     */
    private boolean __isRsvAdmin(RequestModel reqMdl, Connection con) throws SQLException {
        if (rsvAdmin__ == null) {
            CommonBiz cmnBiz = new CommonBiz();
            //施設予約の管理者
            rsvAdmin__ = cmnBiz.isPluginAdmin(
                    con, reqMdl.getSmodel(), GSConstSchedule.PLUGIN_ID_RESERVE);

        }
        return rsvAdmin__;
    }
    @Override
    public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
            ParameterObject    paramModel) throws SQLException {
        boolean rsvAdmin = __isRsvAdmin(reqMdl, con);
        //グループ選択
        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<IChild> grpList = new ArrayList<>();
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        if (rsvAdmin) {
            grpList.addAll(
                    dao.selectAllGroupData()
                        .stream()
                        .map(grp -> new Child(
                                grp.getRsgName(),
                                String.valueOf(grp.getRsgSid())
                                )
                             )
                        .collect(Collectors.toList())
                    );
        } else {
            grpList.addAll(
                    dao.getCanEditData(sessionUsrSid)
                        .stream()
                        .map(grp -> new Child(
                                grp.getRsgName(),
                                String.valueOf(grp.getRsgSid())
                                )
                             )
                        .collect(Collectors.toList())
                    );
        }
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textAll = gsMsg.getMessage("cmn.all");
        grpList.add(0, new Child(textAll,
                String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE)));
       return grpList;
    }
    @Override
    public String answerDefaultGroup(RequestModel reqMdl,
            Connection con, ParameterObject paramModel,
            List<String> groupSidList) throws SQLException {
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();

        //デフォルト表示グループ
        RsvUserModel rsvUserConf = rsvCmnBiz.getRevUserModel(sessionUsrSid, con);
        String dfReservGpSid = Optional.ofNullable(rsvUserConf)
                                .map(conf -> String.valueOf(conf.getRsgSid()))
                                .orElse(String.valueOf(GSConstReserve.COMBO_DEFAULT_VALUE));
        if (groupSidList.contains(dfReservGpSid) == false) {
            return groupSidList.get(0);
        }
        return dfReservGpSid;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList) throws SQLException {
        ArrayList<RsvSisDataModel> selectResList = new ArrayList<>();
        ArrayList<Integer> resList = new ArrayList<Integer>();
        RsvSisDataDao dataDao = new RsvSisDataDao(con);
        resList.addAll(
                selectedSidList.stream()
                    .map(sid -> Integer.valueOf(sid))
                    .collect(Collectors.toList())
                );
        if (resList.size() > 0) {
             selectResList.addAll(
                        dataDao.selectGrpSisetuList(resList));
        }
        List<IChild> ret = new ArrayList<>();
        ret.addAll(selectResList
                    .stream()
                    .map(sis -> new Shisetu(sis))
                    .collect(Collectors.toList())
                );
        return ret;
    }
    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();

        //施設一覧の取得
        ArrayList<RsvSisDataModel> belongResList = null;
        RsvSisDataDao dataDao = new RsvSisDataDao(con);
        ArrayList<Integer> resSids = new ArrayList<>();
        resSids.addAll(selectedSidList.stream()
                .map(sid -> Integer.valueOf(sid))
                .collect(Collectors.toList())
                );

        if (__isRsvAdmin(reqMdl, con)) {
            //全施設を取得する
            belongResList =
                    dataDao.selectGrpSisetuList(
                            NullDefault.getInt(selectedGrpSid, 0),
                            resSids
                            );
        } else {
            //アクセス権限のある施設を取得
            belongResList =
                    dataDao.selectGrpSisetuCanEditList(
                            NullDefault.getInt(selectedGrpSid, 0),
                            resSids, sessionUsrSid);
        }
        return belongResList
                .stream()
                .map(sis -> new Shisetu(sis))
                .collect(Collectors.toList());
    }

}
