package jp.groupsession.v2.fil.fil280;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FilePcbOwnerDao;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 管理者設定 キャビネット管理設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil280Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil280Biz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /** DBコネクション */
    private HttpServletRequest reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl reqMdl
     */
    public Fil280Biz(Connection con, HttpServletRequest reqMdl) {
        con__    = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil280ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil280ParamModel paramMdl) throws SQLException {

        log__.debug("fil280Biz Start");

        GsMessage gsMsg = new GsMessage(reqMdl__);

        GroupBiz grpBiz = new GroupBiz();
        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();

        UserBiz userBiz = new UserBiz();
        List<LabelValueBean> userCombo = new ArrayList<LabelValueBean>();
        List<UsrLabelValueBean> usrList;

        //グループコンボ初期設定
        groupCombo.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        if (paramMdl.getFil280searchFlg() == 0) {
            // 初回表示 or 検索実行時のみ検索条件を設定
            paramMdl.setFil280keyword(paramMdl.getFil280svKeyword());
            paramMdl.setFil280group(paramMdl.getFil280svGroup());
            paramMdl.setFil280user(paramMdl.getFil280svUser());
            paramMdl.setFil280searchFlg(1);
        }

        // 個人キャビネット許可設定を確認
        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconf = aconfDao.select();
        if (aconf == null || aconf.getFacPersonalKbn() == GSConstFile.CABINET_PRIVATE_NOT_USE) {
            // 個人キャビネットは使用不可 → コンボボックスは未指定のみ表示
            usrList = userBiz.getUserLabelListNoSysUser(con__, gsMsg, -1);
            userCombo.addAll(usrList);
            paramMdl.setGroupCombo(groupCombo);
            paramMdl.setUserCombo(userCombo);
            return;
        }

        //グループコンボを設定
        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con__);
        for (GroupModel grpMdl : grpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                                                    String.valueOf(grpMdl.getGroupSid()));
            groupCombo.add(label);
        }
        paramMdl.setGroupCombo(groupCombo);

        //ユーザコンボを設定(グループ指定)
        usrList = userBiz.getUserLabelListNoSysUser(con__, gsMsg, paramMdl.getFil280group());
        userCombo.addAll(usrList);
        paramMdl.setUserCombo(userCombo);

        //検索条件を設定
        Fil280SearchModel searchMdl = new Fil280SearchModel();
        if (paramMdl != null) {
            searchMdl.setKeyword(paramMdl.getFil280svKeyword());
            searchMdl.setGrpSid(paramMdl.getFil280svGroup());
            searchMdl.setUserSid(paramMdl.getFil280svUser());
        }

        if (aconf.getFacUseKbn() == GSConstFile.CABINET_AUTH_USER) {
            // 使用ユーザ・グループが指定されている場合
            FilePcbOwnerDao pcbDao = new FilePcbOwnerDao(con__);
            ArrayList<Integer> permitUsrSids = pcbDao.getUserSidList();

            if (permitUsrSids == null || permitUsrSids.size() == 0) {
                log__.debug("NO PERMIT USER ERROR");
                return; // 指定ユーザが見つからない場合、データ不正合
            }
            log__.debug("PERMIT USER CNT: " + permitUsrSids.size());
            searchMdl.setPermitUsrSids(permitUsrSids);
        }

        // キャビネット一覧を取得
        Fil280Dao cabiDao = new Fil280Dao(con__);
        List<Fil280DspModel> cabiList = cabiDao.getCabinetList(searchMdl);
        paramMdl.setFil280cabinetList(cabiList);
    }

    /**
     * <br>[機  能] 表示順を移動する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil280ParamModel
     * @param userSid ユーザSID
     * @param isUp  true: 上へ移動 / false: 下へ移動
     * @return ソート順が変更された場合true
     * @throws SQLException SQL実行例外
     */
    public boolean updateSort(Fil280ParamModel paramMdl, int userSid, boolean isUp)
            throws SQLException {
        
        boolean ret = false;
        SortResult<Fil280DspModel> result = null;
        Fil280Dao dao = new Fil280Dao(con__);
        final FileCabinetDao fcDao = new FileCabinetDao(con__);
        
        //検索条件を設定
        Fil280SearchModel searchMdl = new Fil280SearchModel();
        if (paramMdl != null) {
            searchMdl.setKeyword(paramMdl.getFil280svKeyword());
            searchMdl.setGrpSid(paramMdl.getFil280svGroup());
            searchMdl.setUserSid(paramMdl.getFil280svUser());
        }

        int motoSid = NullDefault.getInt(paramMdl.getFil280sltRadio(), -1);
        
        SortChangeBiz<Fil280DspModel> sortBiz =
                SortChangeBiz.<Fil280DspModel> searchedSortChangeBuilder()
                .setFuncTargetList(() -> {
                    return dao.getCabinetList(searchMdl);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getFcbSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getFcbSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getFcbSid() == m2.getFcbSid()) {
                        return 0;
                    } else {
                        return (m1.getFcbSid() - m2.getFcbSid()) 
                                / Math.abs(m1.getFcbSid() - m2.getFcbSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    FileCabinetModel fcMdl = new FileCabinetModel();
                    fcMdl.setFcbSort(newSort);
                    fcMdl.setFcbEuid(userSid);
                    fcMdl.setFcbEdate(new UDate());
                    fcMdl.setFcbSid(m.getFcbSid());
                    fcDao.updateSort(fcMdl);
                })
                .setFuncTargetListAll(() -> {
                    searchMdl.setKeyword(null);
                    searchMdl.setGrpSid(-1);
                    searchMdl.setUserSid(-1);
                    return dao.getCabinetList(searchMdl);
                })
                .build();
        
        if (isUp) {
            result = sortBiz.up();
        } else {
            result = sortBiz.down();
        }
        
        if (result != null) {
            ret = true;
        }
        return ret;
    }
}
