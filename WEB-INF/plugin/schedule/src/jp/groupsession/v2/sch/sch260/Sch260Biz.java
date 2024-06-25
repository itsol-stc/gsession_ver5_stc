package jp.groupsession.v2.sch.sch260;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.sch.dao.SchMyviewlistBelongDao;
import jp.groupsession.v2.sch.dao.SchMyviewlistDao;
import jp.groupsession.v2.sch.model.MyViewListBelongModel;
import jp.groupsession.v2.sch.model.SchMyviewlistBelongModel;
import jp.groupsession.v2.sch.model.SchMyviewlistModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 表示リスト登録画面のビジネスロジックを提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch260Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, Sch260ParamModel paramMdl, RequestModel reqMdl)
            throws Exception {

        //初期表示
        int usrSid = reqMdl.getSmodel().getUsrsid();
        if (paramMdl.getSch260initFlg() == GSConstSchedule.DSP_FIRST) {
            if (paramMdl.getSch250editMode() == GSConst.CMDMODE_EDIT) {
                //編集
                __setListData(con, paramMdl, usrSid);
            }

            paramMdl.setSch260initFlg(GSConstSchedule.DSP_ALREADY);
        }

        //初期表示でない場合、対象の表示順を修正
        if (paramMdl.getSch260initFlg() == GSConstSchedule.DSP_ALREADY) {
            __setTargetOrder(paramMdl);
        }

        //グループが未選択の場合、「グループ一覧」を設定
        GroupBiz grpBiz = new GroupBiz();
        if (paramMdl.getSch260subjectGroup() == -1) {
            paramMdl.setSch260subjectGroup(GSConst.GROUP_COMBO_VALUE);
        }

        //グループコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
        groupCombo.add(
                new LabelValueBean(gsMsg.getMessage("cmn.grouplist"),
                                                String.valueOf(GSConst.GROUP_COMBO_VALUE)));

        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con);
        for (GroupModel grpMdl : grpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                                                    String.valueOf(grpMdl.getGroupSid()));
            groupCombo.add(label);
        }
        paramMdl.setGroupCombo(groupCombo);

        //対象ユーザを設定
//        _setSelectTargetCombo(con, paramMdl);
//        paramMdl.setSch260subjectNoSelectCombo(__getSubjectRightLabel(con, paramMdl));
    }

    /**
     * <br>[機  能] 表示リストに設定されている対象の並び順を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    private void __setTargetOrder(Sch260ParamModel paramMdl) {

        int newOrder = 0;
        Map<Integer, String> orderMap = new HashMap<Integer, String>();
        List<String> orderList = new ArrayList<String>();
        if (paramMdl.getSch260orderList() != null) {
            orderList.addAll(Arrays.asList(paramMdl.getSch260orderList()));
        }
        List<String> subjectList = new ArrayList<String>();
        if (paramMdl.getSch260subject() != null) {
            subjectList.addAll(Arrays.asList(paramMdl.getSch260subject()));
        }
        //既に選択されていた対象の並び順をmapに格納する
        if (orderList != null && subjectList != null) {
            for (String sidOrder : orderList) {
                int sepIdx = sidOrder.indexOf(":");
                if (sepIdx == -1) {
                    continue;
                }
                String sid = sidOrder.substring(0, sepIdx);
                int order = Integer.parseInt(
                        sidOrder.substring(sepIdx + 1, sidOrder.length()));
                if (subjectList.contains(sid)) {
                    orderMap.put(order, sid);
                    //新規で追加された対象としてMapに格納されないよう、リストから排除する
                    subjectList.remove(subjectList.indexOf(sid));
                    if (newOrder < order) {
                        newOrder = order;
                    }
                }
            }
        }

        //新規で追加された対象の並び順をMapに格納する
        newOrder = newOrder + 1;
        if (subjectList != null) {
            for (String sid : subjectList) {
                orderMap.put(newOrder, sid);
                newOrder++;
            }
        }

        List<String> newSubjectList = new ArrayList<String>();
        List<String> newOrderList = new ArrayList<String>();
        //Mapを表示順の昇順でソートする
        Object[] mapkey = orderMap.keySet().toArray();
        Arrays.sort(mapkey);

        int order = 1;
        for (int key : orderMap.keySet()) {
            //対象が削除されていた場合
            if (orderMap.get(key) == null) {
                continue;
            }
            newSubjectList.add(orderMap.get(key));
            StringBuilder builder = new StringBuilder();
            builder.append(orderMap.get(key));
            builder.append(":");
            builder.append(order);
            newOrderList.add(builder.toString());
            order++;
        }

        paramMdl.setSch260subject(
                (String[]) newSubjectList.toArray(new String[newSubjectList.size()]));
        paramMdl.setSch260orderList(
                (String[]) newOrderList.toArray(new String[newOrderList.size()]));
    }

    /**
     * <br>[機  能] 表示リスト情報をパラメータ情報へ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __setListData(
            Connection con, Sch260ParamModel paramMdl, int usrSid) throws SQLException {

        int smySid = paramMdl.getSch250editData();
        //表示リスト情報の設定
        SchMyviewlistDao smyDao = new SchMyviewlistDao(con);
        SchMyviewlistModel smyMdl = smyDao.select(smySid, usrSid);
        paramMdl.setSch260scheduleName(smyMdl.getSmyName());
        paramMdl.setSch260biko(smyMdl.getSmyBiko());

        //表示リスト_対象の設定
        List<String> targetList = new ArrayList<String>();
        List<String> orderList = new ArrayList<String>();
        SchMyviewlistBelongDao smbDao = new SchMyviewlistBelongDao(con);
        List<SchMyviewlistBelongModel> smyBelongList = smbDao.select(smySid);
        String targetSid;
        for (SchMyviewlistBelongModel belongMdl : smyBelongList) {
            if (belongMdl.getGrpSid() != -1) {
                targetSid = "G" + belongMdl.getGrpSid();
            } else {
                targetSid = String.valueOf(belongMdl.getUsrSid());
            }
            targetList.add(targetSid);
            orderList.add(targetSid + ":" + belongMdl.getSmvOrder());
        }
        String[] subject = (String[]) targetList.toArray(new String[targetList.size()]);
        paramMdl.setSch260subject(subject);

        paramMdl.setSch260orderList((String[]) orderList.toArray(new String[orderList.size()]));
    }

    /**
     * <br>[機  能] 表示設定が存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param smySid 削除対象表示設定SID
     * @return true:存在する, false:存在しない
     * @throws SQLException SQL実行時例外
     */
    protected boolean _existList(Connection con, int usrSid, int smySid) throws SQLException {

        boolean ret = false;
        SchMyviewlistDao smyDao = new SchMyviewlistDao(con);
        SchMyviewlistModel smyMdl = smyDao.select(smySid, usrSid);
        if (smyMdl != null) {
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] 表示リスト情報の登録を行う
     * <br>[解  説]
     * <br>[備  考] オペレーションログ内容を出力します
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param mtCon 採番コントローラ
     * @param reqMdl リクエスト情報
     * @return オペレーションログ内容
     * @throws Exception 実行時例外
     */
    public String entryListData(Connection con, Sch260ParamModel paramMdl,
            MlCountMtController mtCon, RequestModel reqMdl) throws Exception {

        int editMode = paramMdl.getSch250editMode();
        int smySid = paramMdl.getSch250editData();
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        UDate now = new UDate();

        //表示リストの登録
        SchMyviewlistDao smyDao = new SchMyviewlistDao(con);
        SchMyviewlistModel smyMdl = new SchMyviewlistModel();
        smyMdl.setUsrSid(sessionUserSid);
        smyMdl.setSmyName(paramMdl.getSch260scheduleName());
        smyMdl.setSmyBiko(paramMdl.getSch260biko());

        GsMessage gsMsg = new GsMessage(reqMdl);
        StringBuilder sb = new StringBuilder();
        SchMyviewlistBelongDao smbDao = new SchMyviewlistBelongDao(con);

        if (editMode == GSConst.CMDMODE_EDIT) {
            //変更前のオペレーションログ内容取得
            SchMyviewlistModel oldMdl = smyDao.select(smySid, sessionUserSid);
            sb.append(gsMsg.getMessage("schedule.sch260.07"));
            sb.append("\r\n");
            sb.append("[" + gsMsg.getMessage("cmn.name5") + "] ");
            sb.append(oldMdl.getSmyName());
            sb.append("\r\n");
            sb.append("[" + gsMsg.getMessage("cmn.target") + "] ");
            List<MyViewListBelongModel> beforeList = smbDao.getBelongDataList(smySid);
            boolean firstFlg = true;
            for (MyViewListBelongModel viewMdl : beforeList) {
                if (!firstFlg) {
                    sb.append("、");
                }
                if (viewMdl.getGrpSid() != -1) {
                    sb.append(viewMdl.getGrpName());
                } else {
                    sb.append(viewMdl.getUsrNameSei() + viewMdl.getUsrNameMei());
                }
                firstFlg = false;
            }
            sb.append("\r\n");

            smyMdl.setSmySid(smySid);
            smyMdl.setMgpEuid(sessionUserSid);
            smyMdl.setMgpEdate(now);
            smyDao.update(smyMdl);

        } else {
            smySid = (int) mtCon.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                                                                SaibanModel.SBNSID_SUB_SCH_DISPLIST,
                                                                sessionUserSid);

            int smySort = smyDao.getMaxSort(sessionUserSid) + 1;
            smyMdl.setSmySid(smySid);
            smyMdl.setSmySort(smySort);
            smyMdl.setMgpAuid(sessionUserSid);
            smyMdl.setMgpAdate(now);
            smyMdl.setMgpEuid(sessionUserSid);
            smyMdl.setMgpEdate(now);
            smyDao.insert(smyMdl);
        }

        //表示リスト対象の変更前登録情報を削除
        SchMyviewlistBelongDao belongDao = new SchMyviewlistBelongDao(con);
        if (editMode == GSConst.CMDMODE_EDIT) {
            belongDao.delete(smySid);
        }

        //表示リスト対象の登録
        SchMyviewlistBelongModel belongMdl = new SchMyviewlistBelongModel();
        belongMdl.setSmySid(smySid);
        String[] subjectList = paramMdl.getSch260subject();
        for (int smvSort = 1; smvSort <= subjectList.length; smvSort++) {
            belongMdl.setSmvOrder(smvSort);
            String targetSid = paramMdl.getSch260subject()[smvSort - 1];
            if (targetSid.startsWith("G")) {
                belongMdl.setUsrSid(Integer.parseInt(GSConstSchedule.USER_NOT_SELECT));
                belongMdl.setGrpSid(Integer.parseInt(targetSid.substring(1)));
            } else {
                belongMdl.setUsrSid(Integer.parseInt(targetSid));
                belongMdl.setGrpSid(Integer.parseInt(GSConstSchedule.USER_NOT_SELECT));
            }
            belongDao.insert(belongMdl);
        }

        //変更後のオペレーションログ内容取得
        sb.append(gsMsg.getMessage("schedule.sch260.08"));
        sb.append("\r\n");
        sb.append("[" + gsMsg.getMessage("cmn.name5") + "] ");
        sb.append(paramMdl.getSch260scheduleName());
        sb.append("\r\n");
        sb.append("[" + gsMsg.getMessage("cmn.target") + "] ");
        List<MyViewListBelongModel> afterList = smbDao.getBelongDataList(smySid);
        boolean firstFlg = true;
        for (MyViewListBelongModel viewMdl : afterList) {
            if (!firstFlg) {
                sb.append("、");
            }
            if (viewMdl.getGrpSid() != -1) {
                sb.append(viewMdl.getGrpName());
            } else {
                sb.append(viewMdl.getUsrNameSei() + viewMdl.getUsrNameMei());
            }
            firstFlg = false;
        }

        return sb.toString();

    }

    /**
     * <br>[機  能] 選択中の対象の順序を1つ繰り上げる
     * <br>[解  説]
     * <br>[備  考]
     * @param subject 対象リスト
     * @param subjectSelet コンボで選択中の対象
     * @return 並び替え済みの対象
     */
    public String[] getUpSubject(String[] subject, String[] subjectSelet) {

        if (subjectSelet == null || subjectSelet.length < 1
        || subject == null || subjectSelet.length >= subject.length) {
            return subject;
        }

        ArrayList<String> userList = new ArrayList<String>();
        userList.addAll(Arrays.asList(subject));

        for (String userSid : subjectSelet) {
            int index = userList.indexOf(userSid);
            if (index > 0) {
                userList.remove(index);
                userList.add(index - 1, userSid);
            }
        }

        return userList.toArray(new String[userList.size()]);
    }

    /**
     * <br>[機  能] 選択中の対象の順序を1つ繰り下げる
     * <br>[解  説]
     * <br>[備  考]
     * @param subject 対象リスト
     * @param downSelectSid コンボで選択中の対象
     * @return 並び替え済みの対象
     */
    public String[] getDownSubject(String[] subject, String[] downSelectSid) {

        if (downSelectSid == null || downSelectSid.length < 1
        || subject == null || downSelectSid.length >= subject.length) {
            return subject;
        }

        ArrayList<String> userList = new ArrayList<String>();
        userList.addAll(Arrays.asList(subject));
        int lastIndex = userList.size() - 1;

        for (int i = downSelectSid.length - 1; i >= 0; i--) {
            String userSid = downSelectSid[i];

            int index = userList.indexOf(userSid);
            if (index < lastIndex) {
                userList.remove(index);
                userList.add(index + 1, userSid);
            }
        }

        return userList.toArray(new String[userList.size()]);
    }

    /**
     * <br>[機  能] 表示リストの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエストモデル
     * @return オペレーションログ内容
     * @throws SQLException SQL実行時例外
     */
    public String deleteList(
            Connection con,
            int usrSid,
            Sch260ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {

        SchMyviewlistDao smyDao = new SchMyviewlistDao(con);
        SchMyviewlistBelongDao belongDao = new SchMyviewlistBelongDao(con);
        //表示リスト削除
        int smySid = paramMdl.getSch250editData();
        SchMyviewlistModel smyMdl = smyDao.select(smySid, usrSid);
        smyDao.delete(smySid);

        //変更前のオペレーションログ内容取得
        GsMessage gsMsg = new GsMessage(reqMdl);
        StringBuffer sb = new StringBuffer();
        sb.append(gsMsg.getMessage("schedule.sch260.07"));
        sb.append("\r\n");
        sb.append("[" + gsMsg.getMessage("cmn.name5") + "] " + smyMdl.getSmyName());
        sb.append("\r\n");
        sb.append("[" + gsMsg.getMessage("cmn.target") + "] ");
        SchMyviewlistBelongDao smbDao = new SchMyviewlistBelongDao(con);
        List<MyViewListBelongModel> beforeList = smbDao.getBelongDataList(smySid);
        boolean firstFlg = true;
        for (MyViewListBelongModel viewMdl : beforeList) {
            if (!firstFlg) {
                sb.append("、");
            }
            if (viewMdl.getGrpSid() != -1) {
                sb.append(viewMdl.getGrpName());
            } else {
                sb.append(viewMdl.getUsrNameSei() + viewMdl.getUsrNameMei());
            }
            firstFlg = false;
        }

        //表示リスト所属情報の削除
        belongDao.delete(smySid);

        return sb.toString();
   }

    /**
     * <br>[機  能] 表示リストの表示順を再度取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param subject 選択済み対象
     * @return 選択済み対象の表示順
     */
    protected String[] _getNewOrder(String[] subject) {

        if (subject == null) {
            return null;
        }

        String[] orderList = new String[subject.length];
        int order = 1;
        for (int idx = 0; idx < subject.length; idx++) {
            String sid = subject[idx];
            StringBuilder  builder = new StringBuilder();
            builder.append(sid);
            builder.append(":");
            builder.append(order);
            orderList[idx] = builder.toString();
            order++;
        }

        return orderList;
   }
}
