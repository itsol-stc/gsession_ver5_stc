package jp.groupsession.v2.sch.sch240;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sch.dao.SchSpaccessDao;
import jp.groupsession.v2.sch.dao.SchSpaccessPermitDao;
import jp.groupsession.v2.sch.dao.SchSpaccessTargetDao;
import jp.groupsession.v2.sch.model.SchSpaccessModel;
import jp.groupsession.v2.sch.model.SchSpaccessPermitModel;
import jp.groupsession.v2.sch.model.SchSpaccessTargetModel;
import jp.groupsession.v2.sch.sch230.Sch230Const;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール 特例アクセス登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch240Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch240Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, Sch240ParamModel paramMdl, RequestModel reqMdl)
    throws Exception {

        //初期表示
        if (paramMdl.getSch240initFlg() == Sch230Const.DSP_FIRST) {
            if (paramMdl.getSch230editMode() == Sch230Const.EDITMODE_EDIT) {
                //編集
                _setAccessData(con, paramMdl);
            }

            paramMdl.setSch240initFlg(Sch230Const.DSP_ALREADY);
        }

        //グループが未選択の場合、デフォルトグループを設定
        GroupBiz grpBiz = new GroupBiz();
        if (paramMdl.getSch240subjectGroup() == -1) {
            int defGrp = grpBiz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setSch240subjectGroup(defGrp);
        }
        if (paramMdl.getSch240accessGroup() == -1) {
            int defGrp = grpBiz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setSch240accessGroup(defGrp);
        }

        //グループコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
        groupCombo.add(
                new LabelValueBean(gsMsg.getMessage("cmn.grouplist"),
                                                String.valueOf(Sch240Form.GROUP_COMBO_VALUE)));

        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con);
        for (GroupModel grpMdl : grpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                                                    String.valueOf(grpMdl.getGroupSid()));
            groupCombo.add(label);
        }

        //役職を設定
        List<LabelValueBean> positionCombo = new ArrayList<LabelValueBean>();
        CmnPositionDao positionDao = new CmnPositionDao(con);
        List<CmnPositionModel>positionList = positionDao.getPosList(true);
        for (CmnPositionModel positionData : positionList) {
            LabelValueBean label
                = new LabelValueBean(positionData.getPosName(),
                                                String.valueOf(positionData.getPosSid()));
            if (positionData.getPosSid() != GSConst.POS_DEFAULT) {
                label.setLabel(label.getLabel() + "以上");
            }
            positionCombo.add(label);
        }
        paramMdl.setSch240positionCombo(positionCombo);
    }

    /**
     * <br>[機  能] 特例アクセス情報をパラメータ情報へ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setAccessData(Connection con, Sch240ParamModel paramMdl) throws SQLException {
        int ssaSid = paramMdl.getSch230editData();

        //特例アクセス情報の設定
        SchSpaccessDao accessDao = new SchSpaccessDao(con);
        SchSpaccessModel accessMdl = accessDao.select(ssaSid);
        paramMdl.setSch240name(accessMdl.getSsaName());
        paramMdl.setSch240biko(accessMdl.getSsaBiko());

        //特例アクセス_制限対象の設定
        List<String> targetList = new ArrayList<String>();
        SchSpaccessTargetDao accessTargetDao = new SchSpaccessTargetDao(con);
        List<SchSpaccessTargetModel> sstTargetList = accessTargetDao.getTargetList(ssaSid);
        for (SchSpaccessTargetModel targetMdl : sstTargetList) {
            String targetSid = Integer.toString(targetMdl.getSstTsid());
            if (targetMdl.getSstType() == GSConstSchedule.SST_TYPE_GROUP) {
                //グループ
                targetSid = "G" + targetSid;
            }
            targetList.add(targetSid);
        }
        paramMdl.setSch240subject((String[]) targetList.toArray(new String[targetList.size()]));


        //特例アクセス_許可対象の設定
        List<String> editList = new ArrayList<String>();
        List<String> viewList = new ArrayList<String>();
        SchSpaccessPermitDao accessPermitDao = new SchSpaccessPermitDao(con);
        List<SchSpaccessPermitModel> sspPermitList = accessPermitDao.getPermitList(ssaSid);
        for (SchSpaccessPermitModel permitMdl : sspPermitList) {
            String permitSid = Integer.toString(permitMdl.getSspPsid());
            if (permitMdl.getSspType() == GSConstSchedule.SSP_TYPE_POSITION) {
                //役職
                paramMdl.setSch240position(Integer.parseInt(permitSid));
                if (permitMdl.getSspAuth() == GSConstSchedule.SSP_AUTH_EDIT) {
                    paramMdl.setSch240positionAuth(Sch240Form.POTION_AUTH_EDIT);
                } else {
                    paramMdl.setSch240positionAuth(Sch240Form.POTION_AUTH_VIEW);
                }

            } else {
                if (permitMdl.getSspType() == GSConstSchedule.SSP_TYPE_GROUP) {
                    //グループ
                    permitSid = "G" + permitSid;
                }
                if (permitMdl.getSspAuth() == GSConstSchedule.SSP_AUTH_EDIT) {
                    editList.add(permitSid);
                } else {
                    viewList.add(permitSid);
                }
            }
        }

        paramMdl.setSch240editUser((String[]) editList.toArray(new String[editList.size()]));
        paramMdl.setSch240accessUser((String[]) viewList.toArray(new String[viewList.size()]));

    }

    /**
     * <br>[機  能] ユーザコンボを設定する
     * <br>[解  説] 特例ユーザを設定する
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setSelectAccessCombo(Connection con, Sch240ParamModel paramMdl)
    throws SQLException {
        CommonBiz cmnBiz = new CommonBiz();
        //制限対象を設定
        paramMdl.setSch240subjectSelectCombo(
                cmnBiz.getUserLabelList(con, paramMdl.getSch240subject()));

        //アクセス権限を設定
        paramMdl.setSch240editUserSelectCombo(
                cmnBiz.getUserLabelList(con, paramMdl.getSch240editUser()));
        paramMdl.setSch240accessSelectCombo(
                cmnBiz.getUserLabelList(con, paramMdl.getSch240accessUser()));
    }


    /**
     * <br>[機  能]  ユーザコンボを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param selectUserSid ユーザSID
     * @param gsMsg GsMessage
     * @return ユーザコンボ
     * @throws SQLException SQL実行時例外
     */
    protected List<LabelValueBean> __createUserCombo(Connection con, String[] selectUserSid,
                                                                                    GsMessage gsMsg)
    throws SQLException {

        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con, selectUserSid);
        LabelValueBean labelBean = null;
        List <LabelValueBean> selectUserList = new ArrayList<LabelValueBean>();
        for (BaseUserModel umodel : ulist) {
            labelBean = new LabelValueBean();
            labelBean.setLabel(umodel.getUsisei() + " " + umodel.getUsimei());
            labelBean.setValue(String.valueOf(umodel.getUsrsid()));
            selectUserList.add(labelBean);
        }

        return selectUserList;
    }

    /**
     * <br>[機  能] アクセス権限の候補一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return アクセス権限の候補一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getAccessRightLabel(
            Connection con, Sch240ParamModel paramMdl)
    throws SQLException {

        //アクセス権限 グループコンボ選択値
        int accessGrpSid = paramMdl.getSch240accessGroup();
        //除外するSID(ユーザ or グループ)
        String[] leftUser = paramMdl.getSch240editUser();
        if (leftUser == null) {
            leftUser = new String[0];
        }
        String[] accessUser = paramMdl.getSch240accessUser();
        if (accessUser != null && accessUser.length > 0) {
            String[] allLeftUser = new String[leftUser.length + accessUser.length];
            System.arraycopy(leftUser, 0, allLeftUser, 0, leftUser.length);
            System.arraycopy(accessUser, 0, allLeftUser, leftUser.length, accessUser.length);
            leftUser = allLeftUser;
        }

        return __getRightLabel(con, leftUser, accessGrpSid);
    }

    /**
     * <br>[機  能] ユーザ情報、アクセス権限の候補一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param left 除外するSID
     * @param groupSid グループコンボの選択値
     * @return 候補一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getRightLabel(
            Connection con, String[] left, int groupSid)
    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();
        if (groupSid == Sch240Form.GROUP_COMBO_VALUE) {
            //グループを全て取得
            GroupDao dao = new GroupDao(con);
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            ArrayList<GroupModel> allGpList = dao.getGroupTree(sortMdl);

            //選択済みのSIDを除外する
            List<String> excludeList = new ArrayList<String>();
            if (left != null && left.length > 0) {
                excludeList.addAll(Arrays.asList(left));
            }

            for (GroupModel bean : allGpList) {
                if (!excludeList.contains(String.valueOf("G" + bean.getGroupSid()))) {
                    ret.add(new UsrLabelValueBean(
                            bean.getGroupName(), String.valueOf("G" + bean.getGroupSid())));
                }
            }

        } else {

            //除外するユーザSID
            ArrayList<Integer> excludeList = new ArrayList<Integer>();
            if (left != null) {
                for (String usrSid : left) {
                    excludeList.add(new Integer(NullDefault.getInt(usrSid, -1)));
                }
            }
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> userList
                = userBiz.getBelongUserList(con, groupSid, excludeList);

            UsrLabelValueBean label = null;
            for (CmnUsrmInfModel userData : userList) {
                label = new UsrLabelValueBean();
                label.setLabel(userData.getUsiSei() + " " + userData.getUsiMei());
                label.setValue(String.valueOf(userData.getUsrSid()));
                label.setUsrUkoFlg(userData.getUsrUkoFlg());
                ret.add(label);
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 特例アクセスの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return 削除対象の特例アクセス設定モデル
     * @throws SQLException SQL実行時例外
     */
    public SchSpaccessModel deleteAccess(Connection con, Sch240ParamModel paramMdl)
    throws SQLException {

        SchSpaccessDao accessDao = new SchSpaccessDao(con);
        SchSpaccessTargetDao accessTargetDao = new SchSpaccessTargetDao(con);
        SchSpaccessPermitDao accessPermitDao = new SchSpaccessPermitDao(con);

        int ssaSid = paramMdl.getSch230editData();
        SchSpaccessModel accessMdl = accessDao.select(ssaSid);
        accessDao.delete(ssaSid);
        accessTargetDao.delete(ssaSid);
        accessPermitDao.delete(ssaSid);

        return accessMdl;
   }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    public String[] getAddMember(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }


        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

}
