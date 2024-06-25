package jp.groupsession.v2.zsk.zsk130kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.dao.ZaiGpriConfDao;
import jp.groupsession.v2.zsk.dao.ZaiPriConfDao;
import jp.groupsession.v2.zsk.model.ZaiGpriConfModel;
import jp.groupsession.v2.zsk.model.ZaiPriConfModel;

/**
 * <br>[機  能] 在席管理 個人設定 座席表メンバー表示設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk130knBiz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public Zsk130knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
   }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Zsk130knParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Zsk130knParamModel paramMdl,
            Connection con) throws SQLException {


        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(reqMdl__);

        paramMdl.setZsk130SortConfAble(cmnBiz.canEditViewMember(con));

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String name = gsMsg.getMessage("cmn.name");
        String number = gsMsg.getMessage("cmn.employee.staff.number");
        String post = gsMsg.getMessage("cmn.post");
        String birth = gsMsg.getMessage("cmn.birthday");
        String zaiseki = gsMsg.getMessage("zsk.20");
        String comment = gsMsg.getMessage("zsk.23");
        String sortkey1 = gsMsg.getMessage("cmn.sortkey") + "1";
        String sortkey2 = gsMsg.getMessage("cmn.sortkey") + "2";

        String[] sortZskText = {name, number, post, birth, zaiseki, comment,
                sortkey1, sortkey2};

        int sort1 = paramMdl.getZsk130SortKey1();
        int sort2 = paramMdl.getZsk130SortKey2();
        int idx = 0;
        for (int key : GSConstZaiseki.SORT_KEY_ZSK) {
            if (key == sort1) {
                sort1 = idx;
            }
            if (key == sort2) {
                sort2 = idx;
            }
            idx++;
        }

        paramMdl.setZsk130knSortKey1Name(
                sortZskText[sort1]);

        paramMdl.setZsk130knSortKey2Name(
                sortZskText[sort2]);

        if (paramMdl.getZsk130mainDspFlg() == GSConstZaiseki.MAINGRP_NOT_DSP) {
            return;
        }

        if (SchCommonBiz.isMyGroupSid(paramMdl.getZsk130mainDspGpSid())) {
            //マイグループ名の取得
            CmnMyGroupDao mygroupDao = new CmnMyGroupDao(con);
            CmnMyGroupModel mygroupName = mygroupDao.getMyGroupInfo(
                    SchCommonBiz.getDspGroupSid(paramMdl.getZsk130mainDspGpSid()));
            paramMdl.setZsk130mainDspGrpName(mygroupName.getMgpName());
        } else {
            //通常グループ名の取得
            CmnGroupmDao groupDao = new CmnGroupmDao(con);
            CmnGroupmModel groupName
                        = groupDao.select(Integer.parseInt(paramMdl.getZsk130mainDspGpSid()));
            paramMdl.setZsk130mainDspGrpName(groupName.getGrpName());
        }

        //ソート順を設定する。
        Map<Integer, String> mainDspSortMap = Map.of(
                GSConstZaiseki.SORT_KEY_NAME, gsMsg.getMessage("cmn.name"),
                GSConstZaiseki.SORT_KEY_SNO, gsMsg.getMessage("cmn.employee.staff.number"),
                GSConstZaiseki.SORT_KEY_YKSK, gsMsg.getMessage("cmn.post"),
                GSConstZaiseki.SORT_KEY_BDATE, gsMsg.getMessage("cmn.birthday")
            );

        paramMdl.setZsk130mainDspSortKey1Name(
                mainDspSortMap.get(paramMdl.getZsk130mainDspSortKey1()));
        paramMdl.setZsk130mainDspSortKey2Name(
                mainDspSortMap.get(paramMdl.getZsk130mainDspSortKey2()));

    }
    /**
     * <br>[機  能] ソートキー名の取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sortKey int  ソートキー
     * @return sortName ソート名
     * @throws SQLException SQL実行エラー
     */
    public String __getSortName(int sortKey) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = "";

        String sortName = "";
        if (sortKey == GSConstZaiseki.SORT_KEY_NAME) {
            msg = gsMsg.getMessage("cmn.name");
            sortName = msg;
        } else if (sortKey == GSConstZaiseki.SORT_KEY_SNO) {
            msg = gsMsg.getMessage("cmn.employee.staff.number");
            sortName = msg;
        } else if (sortKey == GSConstZaiseki.SORT_KEY_YKSK) {
            msg = gsMsg.getMessage("cmn.post");
            sortName = msg;
        } else if (sortKey == GSConstZaiseki.SORT_KEY_BDATE) {
            msg = gsMsg.getMessage("cmn.birthday");
            sortName = msg;
        }
        return sortName;
    }

    /**
     * <br>[機  能] 個人設定更新処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Zsk130knParamModel
     * @param con コネクション
     * @param usrMdl セッションユーザModel
     * @throws SQLException SQL実行時例外
     */
    public void updatePriConf(Zsk130knParamModel paramMdl,
                               Connection con,
                               BaseUserModel usrMdl) throws SQLException {
        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(reqMdl__);

        if (cmnBiz.canEditViewMember(con)) {

            ZaiPriConfModel param = new ZaiPriConfModel();
            UDate now = new UDate();
            int usrSid = usrMdl.getUsrsid();
            param.setUsrSid(usrSid);
            param.setZpcEid(usrSid);
            param.setZpcEdate(now);
            param.setZpcSortKey1(paramMdl.getZsk130SortKey1());
            param.setZpcSortOrder1(paramMdl.getZsk130SortOrder1());
            param.setZpcSortKey2(paramMdl.getZsk130SortKey2());
            param.setZpcSortOrder2(paramMdl.getZsk130SortOrder2());

            ZaiPriConfDao dao = new ZaiPriConfDao(con);
            int count = dao.updateZskMemberSetting(param);
            if (count < 1) {
                param.setZifSid(GSConstZaiseki.ZASEKI_NOT_SELECT);
                param.setZpcReload(GSConstZaiseki.AUTO_RELOAD_10MIN);
                param.setZpcAid(usrSid);
                param.setZpcAdate(now);
                dao.insert(param);
            }
        }
        //DBの存在確認
        ZaiGpriConfDao gPriDao = new ZaiGpriConfDao(con);
        ZaiGpriConfModel model = gPriDao.select(usrMdl.getUsrsid());
        //画面値セット
        ZaiGpriConfModel iocMdl = createZaiGpriConfData(paramMdl, usrMdl);

        if (model == null) {
            gPriDao.insert(iocMdl);
        } else {
            gPriDao.update(iocMdl);
        }

    }
    /**
     * <br>[機  能] 在席個人設定をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSid グループSID
     * @param con コネクション
     * @return グループ名
     * @throws SQLException SQL実行エラー
     */
    public String getSelectGroupName(String groupSid,
            Connection con) throws SQLException {

        String grpName = "";
        if (SchCommonBiz.isMyGroupSid(groupSid)) {
            //マイグループ名の取得
            CmnMyGroupDao mygroupDao = new CmnMyGroupDao(con);
            CmnMyGroupModel mygroupName = mygroupDao.getMyGroupInfo(
                    SchCommonBiz.getDspGroupSid(groupSid));
            grpName = mygroupName.getMgpName();
        } else {
            //通常グループ名の取得
            CmnGroupmDao groupDao = new CmnGroupmDao(con);
            CmnGroupmModel groupName
                        = groupDao.select(Integer.parseInt(groupSid));
            grpName = groupName.getGrpName();
        }
        return grpName;

    }
    /**
     * <br>[機  能] 在席個人設定情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk130knParamModel
     * @param umodel ユーザ基本情報モデル
     * @return KhmSyaikbnModel 社員区分情報
     */
    public ZaiGpriConfModel createZaiGpriConfData(
            Zsk130knParamModel paramMdl, BaseUserModel umodel) {

        UDate nowDate = new UDate();
        ZaiGpriConfModel mdl = new ZaiGpriConfModel();

        if (paramMdl.getZsk130mainDspFlg() == GSConstZaiseki.MAINGRP_DSP) {
            mdl.setZgcGrp(SchCommonBiz.getDspGroupSid(paramMdl.getZsk130mainDspGpSid()));
        } else {
            mdl.setZgcGrp(-1);
        }
        mdl.setUsrSid(umodel.getUsrsid());
        mdl.setZgcSortKey1(paramMdl.getZsk130mainDspSortKey1());
        mdl.setZgcSortKey2(paramMdl.getZsk130mainDspSortKey2());
        mdl.setZgcSortOrder1(paramMdl.getZsk130mainDspSortOrder1());
        mdl.setZgcSortOrder2(paramMdl.getZsk130mainDspSortOrder2());
        mdl.setZgcSchViewDf(paramMdl.getZsk130mainDspSchViewDf());
        mdl.setZgcViewKbn(paramMdl.getZsk130mainDspFlg());

        if (SchCommonBiz.isMyGroupSid(paramMdl.getZsk130mainDspGpSid())) {
            mdl.setZgcGkbn(GSConstZaiseki.DSP_MYGROUP);
        } else {
            mdl.setZgcGkbn(GSConstZaiseki.DSP_GROUP);
        }

        mdl.setZgcAuid(umodel.getUsrsid());
        mdl.setZgcAdate(nowDate);
        mdl.setZgcEuid(umodel.getUsrsid());
        mdl.setZgcEdate(nowDate);

        return mdl;
    }

}