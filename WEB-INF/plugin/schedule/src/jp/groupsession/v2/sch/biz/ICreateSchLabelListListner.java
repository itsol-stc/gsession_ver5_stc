package jp.groupsession.v2.sch.biz;

import java.util.List;

import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] グループユーザセレクトのユーザコンボ作成処理リスナ
 * <br>[解  説] グループコンボの選択値からユーザリストを作成する処理で呼ばれる
 * <br>[備  考] リスナ実装クラスでは必要に応じ特例追加選択値を追加する処理を実装すること
 *
 * @author JTS
 */
public interface ICreateSchLabelListListner {
    /**
     *
     * <br>[機  能] グループコンボの選択値から生成されたユーザリストに特例追加選択値を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSidStr グループコンボの選択値（マイグループはM{SID}）
     * @param baseUsrList 選択したグループの所属ユーザ
     * @return 編集後のユーザリスト
     */
    public abstract List<UsrLabelValueBean> addExUserLabelList(String grpSidStr,
            List<UsrLabelValueBean> baseUsrList);

    /**
    *
    * <br>[機  能] 生成されたユーザリストに特例追加選択値を追加する
    * <br>[解  説]
    * <br>[備  考]
    * @param grpSidStr グループコンボの選択値（マイグループはM{SID}）
    * @param baseUsrList 選択したグループの所属ユーザ
    * @return 編集後のユーザリスト
    */
   public abstract List<SchLabelValueModel> addExGroupLabelList(List<SchLabelValueModel> baseGrpList);

}
