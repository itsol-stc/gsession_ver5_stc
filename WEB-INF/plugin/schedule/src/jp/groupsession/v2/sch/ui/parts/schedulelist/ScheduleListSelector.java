package jp.groupsession.v2.sch.ui.parts.schedulelist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.EnumModeType;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ChildSearch;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class ScheduleListSelector extends AbstractSelector {
    /** 含有クラス ユーザ選択機能実装*/
    private final UserGroupSelector base__;
    /**
    *
    * コンストラクタ
    * @param param
    */
   protected ScheduleListSelector(ParamForm param) {
       super(param);
       base__ = UserGroupSelector.builder()
               .chainGrpType(EnumGroupSelectType.GROUPONLY)
               .chainType(EnumSelectType.USERGROUP)
               .chainOnlyPluginUser("schedule")
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
   public static class ParamForm extends SelectorFactory<ScheduleListSelector, ParamForm> {
       public ParamForm(Class<ScheduleListSelector> targetClz) {
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
      ParamForm ret = new ParamForm(ScheduleListSelector.class);
      return ret;
  }

  @Override
  public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
          ParameterObject paramModel, List<String> selectedSidList) throws SQLException {
      List<IChild> ret = new ArrayList<>();
      List<IChild> childList = new ArrayList<>();
      //選択グループ
      int[] grpSids =
              selectedSidList.stream()
                 .filter(sid -> sid.startsWith("G"))
                 .mapToInt(sid -> Integer.valueOf(sid.substring(1)))
                 .toArray();

      ArrayList<GroupModel> grpList = null;
      UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
      grpList = gdao.selectGroupNmListOrderbyConf(
              Arrays.stream(grpSids)
              .mapToObj(sid -> sid)
              .collect(Collectors.toList())
                  );
      childList.addAll(
              grpList.stream()
              .map(grp -> new Child(grp))
              .collect(Collectors.toList())
              );

      //選択ユーザ
      List<Integer> usrSidList =
              selectedSidList.stream()
              .filter(sid -> ValidateUtil.isNumberHaifun(sid))
              .map(sid -> Integer.valueOf(sid))
              .collect(Collectors.toList());

      CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
      CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
      LinkedHashMap<Integer, CmnUsrmInfModel> usrmInfMap = new LinkedHashMap<>();

      new CmnUsrmInfDao(con)
      .getUsersInfList(usrSidList
              .stream()
              .map(sid -> String.valueOf(sid))
              .toArray(String[]::new),
              sortMdl)
      .stream()
      .forEach(u -> usrmInfMap.put(u.getUsrSid(), u));

      Map<Integer, CmnUsrmModel> usrmMap = new CmnUsrmDao(con)
              .select(usrmInfMap.keySet())
              .stream()
              .collect(Collectors.toMap(
                      u -> u.getUsrSid(),
                      u -> u
                      ));
      //詳細検索時、追加情報を含むChildに置き換える
      if (getMode() == EnumModeType.EDIT_POPUP) {
          GroupDao grpDao = new GroupDao(con);

          //デフォルトグループマップ取得
          Map<Integer, String> defGroupMap = grpDao.getDefaultGroups(
                  usrmInfMap.values()
                      .stream()
                      .map(usrInf -> String.valueOf(usrInf.getUsrSid()))
                      .mapToInt(Integer::parseInt)
                      .toArray());

          childList.addAll(
              usrmInfMap.values()
                  .stream()
                  .map(usrInf -> new CmnUserModel(
                          usrInf, usrmMap.get(usrInf.getUsrSid())))
                  .map(usr -> new ChildSearch(usr, usr.getBinSid(), usr.getUsiPictKf(),
                          defGroupMap.get(usr.getUsrSid())))
                  .collect(Collectors.toList())
                  );

      } else {
          childList.addAll(
                  usrmInfMap.values()
                      .stream()
                      .map(usrInf -> new CmnUserModel(
                              usrInf, usrmMap.get(usrInf.getUsrSid())))
                      .map(usr -> new Child(usr))
                      .collect(Collectors.toList())
                      );
      }

      //ソート
      for (String sid : selectedSidList) {
          for (int i = 0; i < childList.size(); i++) {
              if (childList.get(i).getValue().equals(sid)) {
                  ret.add(childList.get(i));
                  childList.remove(i);
                  break;
              }
          }
      }
      return ret;
  }
  @Override
  public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
          ParameterObject paramModel, String selectedGrpSid,
          List<String> selectedSidList) throws SQLException {
      return base__.answerSelectionList(reqMdl, con, paramModel, selectedGrpSid, selectedSidList);
  }
  @Override
  public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
          ParameterObject paramModel) throws SQLException {
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
      return base__.isUseDetailSearch();
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