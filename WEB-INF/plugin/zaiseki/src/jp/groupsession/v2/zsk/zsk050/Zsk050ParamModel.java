package jp.groupsession.v2.zsk.zsk050;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.GSValidateZaiseki;
import jp.groupsession.v2.zsk.zsk030.Zsk030ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理 座席表編集画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk050ParamModel extends Zsk030ParamModel {
    /** 初期表示フラグ */
    private String initFlg__ = GSConstZaiseki.INIT_FLG_ON;
    /** 座席表名称 */
    private String zasekiMapName__;
    /** 選択表示グループ */
    private String selectGroup__;
    /** 選択表示グループ */
    private String selectRsvGroup__;
    /** コメント */
    private String commentValue__;
    /** 座席表表示順 */
    private String zasekiSortNum__;

    //Ajax用通信用
    /** 移動したエレメントのX座標*/
    private String indexx__;
    /** 移動したエレメントのY座標*/
    private String indexy__;
    /** 移動したエレメントKEY*/
    private String elKey__;
    /** 追加したエレメント区分*/
    private String addElKbn__;
    /** 追加したエレメントSID*/
    private String addElSid__;
    /** 追加したエレメントコメント*/
    private String addElMsg__;


    //表示用
    /** グループコンボ */
    private List<LabelValueBean> groupLabelList__ = null;
    /** 施設グループコンボ */
    private List<LabelValueBean> rsvGroupLabelList__ = null;
    /** 所属ユーザリスト */
    private ArrayList<UserSearchModel> belongUserList__ = null;
    /** 所属施設リスト */
    private ArrayList<RsvSisDataModel> belongRsvList__ = null;

    /** 添付ファイル */
    private String[] zsk050file__ = null;
    /** ファイルコンボ */
    private List < LabelValueBean > zsk050FileLabelList__ = null;

    /**
     * <p>zsk050file を取得します。
     * @return zsk050file
     */
    public String[] getZsk050file() {
        return zsk050file__;
    }

    /**
     * <p>zsk050file をセットします。
     * @param zsk050file zsk050file
     */
    public void setZsk050file(String[] zsk050file) {
        zsk050file__ = zsk050file;
    }

    /**
     * <p>zsk050FileLabelList を取得します。
     * @return zsk050FileLabelList
     */
    public List<LabelValueBean> getZsk050FileLabelList() {
        return zsk050FileLabelList__;
    }

    /**
     * <p>zsk050FileLabelList をセットします。
     * @param zsk050FileLabelList zsk050FileLabelList
     */
    public void setZsk050FileLabelList(List<LabelValueBean> zsk050FileLabelList) {
        zsk050FileLabelList__ = zsk050FileLabelList;
    }

    /**
     * <p>zasekiSortNum を取得します。
     * @return zasekiSortNum
     */
    public String getZasekiSortNum() {
        return zasekiSortNum__;
    }

    /**
     * <p>zasekiSortNum をセットします。
     * @param zasekiSortNum zasekiSortNum
     */
    public void setZasekiSortNum(String zasekiSortNum) {
        zasekiSortNum__ = zasekiSortNum;
    }

    /**
     * <p>elKey を取得します。
     * @return elKey
     */
    public String getElKey() {
        return elKey__;
    }

    /**
     * <p>elKey をセットします。
     * @param elKey elKey
     */
    public void setElKey(String elKey) {
        elKey__ = elKey;
    }


    /**
     * <p>indexx を取得します。
     * @return indexx
     */
    public String getIndexx() {
        return indexx__;
    }

    /**
     * <p>indexx をセットします。
     * @param indexx indexx
     */
    public void setIndexx(String indexx) {
        indexx__ = indexx;
    }

    /**
     * <p>indexy を取得します。
     * @return indexy
     */
    public String getIndexy() {
        return indexy__;
    }

    /**
     * <p>indexy をセットします。
     * @param indexy indexy
     */
    public void setIndexy(String indexy) {
        indexy__ = indexy;
    }

    /**
     * <p>belongRsvList を取得します。
     * @return belongRsvList
     */
    public ArrayList<RsvSisDataModel> getBelongRsvList() {
        return belongRsvList__;
    }

    /**
     * <p>belongRsvList をセットします。
     * @param belongRsvList belongRsvList
     */
    public void setBelongRsvList(ArrayList<RsvSisDataModel> belongRsvList) {
        belongRsvList__ = belongRsvList;
    }

    /**
     * <p>belongUserList を取得します。
     * @return belongUserList
     */
    public ArrayList<UserSearchModel> getBelongUserList() {
        return belongUserList__;
    }

    /**
     * <p>belongUserList をセットします。
     * @param belongUserList belongUserList
     */
    public void setBelongUserList(ArrayList<UserSearchModel> belongUserList) {
        belongUserList__ = belongUserList;
    }

    /**
     * <p>commentValue を取得します。
     * @return commentValue
     */
    public String getCommentValue() {
        return commentValue__;
    }

    /**
     * <p>commentValue をセットします。
     * @param commentValue commentValue
     */
    public void setCommentValue(String commentValue) {
        commentValue__ = commentValue;
    }

    /**
     * <p>groupLabelList を取得します。
     * @return groupLabelList
     */
    public List<LabelValueBean> getGroupLabelList() {
        return groupLabelList__;
    }

    /**
     * <p>groupLabelList をセットします。
     * @param groupLabelList groupLabelList
     */
    public void setGroupLabelList(List<LabelValueBean> groupLabelList) {
        groupLabelList__ = groupLabelList;
    }

    /**
     * <p>rsvGroupLabelList を取得します。
     * @return rsvGroupLabelList
     */
    public List<LabelValueBean> getRsvGroupLabelList() {
        return rsvGroupLabelList__;
    }

    /**
     * <p>rsvGroupLabelList をセットします。
     * @param rsvGroupLabelList rsvGroupLabelList
     */
    public void setRsvGroupLabelList(List<LabelValueBean> rsvGroupLabelList) {
        rsvGroupLabelList__ = rsvGroupLabelList;
    }

    /**
     * <p>selectGroup を取得します。
     * @return selectGroup
     */
    public String getSelectGroup() {
        return selectGroup__;
    }

    /**
     * <p>selectGroup をセットします。
     * @param selectGroup selectGroup
     */
    public void setSelectGroup(String selectGroup) {
        selectGroup__ = selectGroup;
    }

    /**
     * <p>selectRsvGroup を取得します。
     * @return selectRsvGroup
     */
    public String getSelectRsvGroup() {
        return selectRsvGroup__;
    }

    /**
     * <p>selectRsvGroup をセットします。
     * @param selectRsvGroup selectRsvGroup
     */
    public void setSelectRsvGroup(String selectRsvGroup) {
        selectRsvGroup__ = selectRsvGroup;
    }

    /**
     * <p>zasekiMapName を取得します。
     * @return zasekiMapName
     */
    public String getZasekiMapName() {
        return zasekiMapName__;
    }

    /**
     * <p>zasekiMapName をセットします。
     * @param zasekiMapName zasekiMapName
     */
    public void setZasekiMapName(String zasekiMapName) {
        zasekiMapName__ = zasekiMapName;
    }

    /**
     * <p>initFlg を取得します。
     * @return initFlg
     */
    public String getInitFlg() {
        return initFlg__;
    }

    /**
     * <p>initFlg をセットします。
     * @param initFlg initFlg
     */
    public void setInitFlg(String initFlg) {
        initFlg__ = initFlg;
    }

    /**
     * <p>addElKbn を取得します。
     * @return addElKbn
     */
    public String getAddElKbn() {
        return addElKbn__;
    }

    /**
     * <p>addElKbn をセットします。
     * @param addElKbn addElKbn
     */
    public void setAddElKbn(String addElKbn) {
        addElKbn__ = addElKbn;
    }

    /**
     * <p>addElMsg を取得します。
     * @return addElMsg
     */
    public String getAddElMsg() {
        return addElMsg__;
    }

    /**
     * <p>addElMsg をセットします。
     * @param addElMsg addElMsg
     */
    public void setAddElMsg(String addElMsg) {
        addElMsg__ = addElMsg;
    }

    /**
     * <p>addElSid を取得します。
     * @return addElSid
     */
    public String getAddElSid() {
        return addElSid__;
    }

    /**
     * <p>addElSid をセットします。
     * @param addElSid addElSid
     */
    public void setAddElSid(String addElSid) {
        addElSid__ = addElSid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fileName ファイル名
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(String fileName, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "zsk.27");
        String msg2 = gsMsg.getMessage(req, "cmn.sort");
        String msg3 = gsMsg.getMessage(req, "zsk.08");

        //表タイトル
        GSValidateZaiseki.validateName(errors,
                zasekiMapName__,
                msg3,
                GSConstZaiseki.MAX_LENGTH_MAPTITLE);
        //表示順
        GSValidateZaiseki.validateSortNumber(errors,
                zasekiSortNum__,
                msg2,
                GSConstZaiseki.MAX_LENGTH_SORTNUM,
                req);
        //添付ファイル
        GSValidateZaiseki.validateFile(errors,
                fileName,
                msg);

        return errors;
    }
}
