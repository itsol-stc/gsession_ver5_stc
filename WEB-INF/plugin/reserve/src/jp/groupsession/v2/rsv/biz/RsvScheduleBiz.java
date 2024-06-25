package jp.groupsession.v2.rsv.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.rsv.dao.RsvScdOperationDao;
import jp.groupsession.v2.rsv.dao.RsvUserGrpDao;
import jp.groupsession.v2.rsv.model.RsvScdOperationModel;
import jp.groupsession.v2.rsv.model.other.ExtendedLabelValueModel;
import jp.groupsession.v2.rsv.model.other.RsvSchAdmConfModel;
import jp.groupsession.v2.rsv.model.other.RsvSchPriConfModel;
import jp.groupsession.v2.rsv.rsv110.model.Rsv110Model;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約で使用するスケジュール関連のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvScheduleBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvScheduleBiz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvScheduleBiz() {
    }

    /**
     * <br>[機  能] スケジュール管理者設定を取得し、取得できない場合はデフォルト値を返します。
     * <br>[解  説]
     * <br>[備  考] DBから個人設定情報が取得できない場合に使用してください。
     * @param con DBコネクション
     * @return スケジュール個人設定のデフォルト値
     * @throws SQLException SQL実行エラー
     */
    public RsvSchAdmConfModel getAdmConfModel(Connection con) throws SQLException {
        //DBより現在の設定を取得する。
        RsvScdOperationDao rsvSchDao = new RsvScdOperationDao(con);
        RsvSchAdmConfModel conf = rsvSchDao.getAdmConf();

        if (conf == null) {
            //データがない場合
            conf = new RsvSchAdmConfModel();
            //共有範囲
            conf.setSadCrange(GSConstSchedule.CRANGE_SHARE_ALL);
            //自動削除
            conf.setSadAtdelFlg(GSConstSchedule.AUTO_DELETE_OFF);
            conf.setSadAtdelY(-1);
            conf.setSadAtdelM(-1);
            //時間間隔
            conf.setSadHourDiv(GSConstSchedule.DF_HOUR_DIVISION);

            //登録者・更新者
            UDate now = new UDate();
            conf.setSadAuid(0);
            conf.setSadAdate(now);
            conf.setSadEuid(0);
            conf.setSadEdate(now);

            //メンバ表示順
            conf.setSadSortKbn(GSConstSchedule.MEM_DSP_USR);
            conf.setSadSortKey1(GSConstSchedule.SORT_KEY_YKSK);
            conf.setSadSortOrder1(GSConstSchedule.ORDER_KEY_ASC);
            conf.setSadSortKey2(GSConstSchedule.SORT_KEY_NAME);
            conf.setSadSortOrder2(GSConstSchedule.ORDER_KEY_ASC);

        }

        log__.debug(conf.toCsvString());
        return conf;
    }

    /**
     * <br>[機  能] スケジュール個人設定SchPriConfModelを取得します。
     * <br>[解  説] DBに登録がない場合デフォルト値を返します。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return スケジュール個人設定SchPriConfModel
     * @throws SQLException SQL実行エラー
     */
    public RsvSchPriConfModel getSchPriConfModel(Connection con, int usrSid) throws SQLException {
        //
        RsvScdOperationDao rsvSchDao = new RsvScdOperationDao(con);
        RsvSchPriConfModel pconf = rsvSchDao.getPriConf(usrSid);
        if (pconf == null) {
            pconf = new RsvSchPriConfModel();
            //ユーザSID
            pconf.setUsrSid(usrSid);
            //開始時間 9時で作成
            UDate frDate = new UDate();
            frDate.setHour(GSConstSchedule.DF_FROM_HOUR);
            frDate.setMinute(GSConstSchedule.DF_FROM_MINUTES);
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            pconf.setSccFrDate(frDate);
            //終了時間 18時で作成
            UDate toDate = new UDate();
            toDate.setHour(GSConstSchedule.DF_TO_HOUR);
            toDate.setMinute(GSConstSchedule.DF_TO_MINUTES);
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
            pconf.setSccToDate(toDate);
            //デフォルト表示グループ
            GroupBiz gpBiz = new GroupBiz();
            int gsid = gpBiz.getDefaultGroupSid(usrSid, con);
            pconf.setSccDspGroup(gsid);
            //一覧表示件数
            pconf.setSccDspList(GSConstSchedule.DEFAULT_LIST_CNT);
            //自動リロード
            pconf.setSccReload(GSConstSchedule.AUTO_RELOAD_10MIN);
            //表示開始曜日
            pconf.setSccIniWeek(GSConstSchedule.CHANGE_WEEK_TODAY);

            pconf.setSccAuid(usrSid);
            pconf.setSccAdate(new UDate());
            pconf.setSccEuid(usrSid);
            pconf.setSccEdate(new UDate());

            //初期値 公開フラグ
            pconf.setSccIniPublic(GSConstSchedule.DSP_PUBLIC);
            //初期値 編集権限
            pconf.setSccIniEdit(GSConstSchedule.EDIT_CONF_NONE);
            //初期値 公開区分
            pconf.setSccIniPublic(GSConstSchedule.DSP_PUBLIC);
            //初期値 タイトルカラー
            pconf.setSccIniFcolor(GSConstSchedule.DF_BG_COLOR);
            //初期値 開始時刻 9時
            pconf.setSccIniFrDate(frDate);
            //初期値 終了時刻 18時
            pconf.setSccIniToDate(toDate);

            //ソート1
            pconf.setSccSortKey1(GSConstSchedule.SORT_KEY_YKSK);
            pconf.setSccSortOrder1(GSConst.ORDER_KEY_ASC);
            //ソート2
            pconf.setSccSortKey2(GSConstSchedule.SORT_KEY_NAME);
            pconf.setSccSortOrder2(GSConst.ORDER_KEY_ASC);
            //一覧表示件数
            pconf.setSccDspList(GSConstSchedule.DEFAULT_LIST_CNT);
            //マイグループ
            pconf.setSccDspMygroup(0);
            //メンバー表示順編集
            pconf.setSccSortEdit(GSConstSchedule.MEM_EDIT_NOT_EXECUTE);
            //初期表示画面
            pconf.setSccDefDsp(GSConstSchedule.DSP_WEEK);
        }
        log__.debug("pconf = " + pconf.toCsvString());

        return pconf;
    }

    /**
     * スケジュール同時登録時に使用するグループコンボを取得する
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param userSid ユーザSID
     * @return グループコンボ
     * @throws SQLException SQL実行時例外
     */
    public List<ExtendedLabelValueModel> getSchGroupCombo(Connection con,
            RequestModel reqMdl, int userSid) throws SQLException {

        List<ExtendedLabelValueModel> grpCombo = new ArrayList<ExtendedLabelValueModel>();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //閲覧不可グループを取得
        SchDao schDao = new SchDao(con);
        List<Integer> notAccessGrpList =
                schDao.getNotRegistGrpList(userSid);

        GroupBiz grpBiz = new GroupBiz();
        ArrayList <LabelValueBean>grpCmbList = grpBiz.getGroupCombLabelList(con, true, gsMsg);
        if (grpCmbList != null && !grpCmbList.isEmpty()) {
            for (LabelValueBean bean : grpCmbList) {
                grpCombo.add(new ExtendedLabelValueModel(
                        bean.getLabel(), bean.getValue(),
                        notAccessGrpList.indexOf(NullDefault.getInt(bean.getValue(), -1)) < 0));

            }
        }

        return grpCombo;
    }

    /**
     * 表示グループ用のグループリストを取得する(所属グループのみ)
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param userSid ユーザSID
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<ExtendedLabelValueModel> getGroupLabelForSchedule(Connection con,
            RequestModel reqMdl, int userSid, boolean sentakuFlg) throws SQLException {
        //管理者設定
        RsvSchAdmConfModel admConf = getAdmConfModel(con);

        ArrayList<ExtendedLabelValueModel> labelList = new ArrayList<ExtendedLabelValueModel>();

        //グループリスト取得
        ArrayList<GroupModel> gpList = null;
        if (admConf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
            //全員で共有
            GroupBiz groupBiz = new GroupBiz();
            gpList = groupBiz.getGroupCombList(con);
        } else {
            //所属グループのみで共有
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

            GroupDao dao = new GroupDao(con);
            ArrayList<GroupModel> gpBaseList = dao.getGroupList(sortMdl);


            SchDao schDao = new SchDao(con);
//          //編集可能ユーザが所属するグループを取得
            List<Integer> belongGrpList
              = schDao.getGroupBelongSpRegistUser(userSid);
//          //ユーザが所属するグループを取得
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            List<Integer> belongGroupMdlList = belongDao.selectUserBelongGroupSid(userSid);
            List<Integer> accessGrpList
            = schDao.getAccessGrpList(userSid, GSConstSchedule.SSP_AUTH_EDIT);

            gpList = new ArrayList<GroupModel>();
            for (GroupModel gpMdl : gpBaseList) {
                int grpSid = gpMdl.getGroupSid();
                if (belongGroupMdlList.contains(grpSid)
                || accessGrpList.contains(grpSid)
                || belongGrpList.contains(grpSid)) {
                    gpList.add(gpMdl);
                }
            }
        }

        for (GroupModel gmodel : gpList) {
            labelList.add(new ExtendedLabelValueModel(gmodel.getGroupName(), String
                    .valueOf(gmodel.getGroupSid()), "0", true));
        }

        //共有範囲設定が「全て共有」の場合マイグループを追加
        if (admConf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
            List<ExtendedLabelValueModel> mylabelList = getMyGroupLabel(userSid, con);
            labelList.addAll(0, mylabelList);
        }
        //グループ
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textHide = gsMsg.getMessage("cmn.hide");
        if (sentakuFlg) {
            labelList.add(new ExtendedLabelValueModel(textHide, String.valueOf(-1), "0"));
        }

        return labelList;
    }

    /**
     * ユーザIDを指定しマイグループラベルを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @return List
     * @throws SQLException SQL実行時例外
     */
    public List<ExtendedLabelValueModel> getMyGroupLabel(int userSid, Connection con)
    throws SQLException {
        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(userSid);

        //マイグループリストをセット
        List<ExtendedLabelValueModel> cmgLabelList = new ArrayList<ExtendedLabelValueModel>();
        for (CmnMyGroupModel cmgMdl : cmgList) {

            cmgLabelList.add(
                    new ExtendedLabelValueModel(
                            cmgMdl.getMgpName(),
                            GSConstSchedule.MY_GROUP_STRING
                            + String.valueOf(cmgMdl.getMgpSid()), "1")
                            );
        }
        return cmgLabelList;
    }

    /**
     * <br>[機  能] スケジュールのデフォルト表示で表示するグループSIDを取得する。
     * <br>[解  説] DBに登録された個人設定情報を取得しその表示グループを返す。
     * <br>グループが削除されていた場合は、デフォルトグループを返す。
     * <br>[備  考]
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @return グループSID
     * @throws SQLException SQL実行エラー
     */
    public String getDispDefaultGroupSidStr(Connection con, int usrSid) throws SQLException {

        GroupBiz gbiz = null;
        //管理者設定 デフォルト表示グループ = 1:デフォルトグループに強制 の場合、ユーザのデフォルトグループを返す
        SchCommonBiz schCmnBiz = new SchCommonBiz();
        SchAdmConfModel schAconf = schCmnBiz.getAdmConfModel(con);
        if (schAconf.getSadDspGroup() == GSConstSchedule.SAD_DSP_GROUP_DEFGROUP) {
            gbiz = new GroupBiz();
            return String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
        }

        RsvSchAdmConfModel aconf = getAdmConfModel(con);
        RsvSchPriConfModel pconf = getSchPriConfModel(con, usrSid);

        String gsid = null;
        if (aconf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_GROUP) {
            gsid = String.valueOf(pconf.getSccDspGroup());
            //個人設定値が閲覧可能なグループで無い場合はデフォルトグループを表示
            if (!isDspOkGroup(pconf.getSccDspGroup(), usrSid, con)) {
                gbiz = new GroupBiz();
                gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
            }
        } else {
            if (pconf.getSccDspMygroup() != 0) {
                gsid = String.valueOf(pconf.getSccDspMygroup());
                //マイグループ存在チェック
                CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
                if (cmgDao.getMyGroupList(usrSid,
                        String.valueOf(gsid)).size() < 1) {
                    //マイグループが存在しない場合はデフォルトグループを返す
                    gbiz = new GroupBiz();
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    gsid = GSConstSchedule.MY_GROUP_STRING + gsid;
                }
            } else {
                gsid = String.valueOf(pconf.getSccDspGroup());
                //グループ存在チェック
                GroupDao gdao = new GroupDao(con);
                CmnGroupmModel group = gdao.getGroup(Integer.parseInt(gsid));
                if (group == null) {
                    //個人設定未設定 or 不正データの場合、ユーザマネージャのデフォルトグループ
                    gbiz = new GroupBiz();
                    gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                } else {
                    if (GSConst.JTKBN_DELETE == group.getGrpJkbn()) {
                        //状態区分が削除の場合はデフォルトグループを返す
                        gbiz = new GroupBiz();
                        gsid = String.valueOf(gbiz.getDefaultGroupSid(usrSid, con));
                    }
                }
            }
        }

        return gsid;
    }

    /**
     * ユーザが閲覧可能なグループか判定する。
     * 所属グループ、グループ外ユーザとして設定されているグループは閲覧可能
     * @param gpSid グループSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return boolean true=可能 false=不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isDspOkGroup(int gpSid, int usrSid, Connection con) throws SQLException {

        //管理者設定
        RsvSchAdmConfModel admConf = getAdmConfModel(con);
        //グループリスト取得
        ArrayList<GroupModel> gpList = null;
        if (admConf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
            //全員で共有
            GroupBiz groupBiz = new GroupBiz();
            gpList = groupBiz.getGroupList(con);
        } else {
            //所属グループのみで共有
            UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con);
            gpList = gpDao.selectGroupNmListOrderbyClass(usrSid);
        }

        for (GroupModel gmdl : gpList) {
            if (gmdl.getGroupSid() == gpSid) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] 指定したグループコンボ値がグループSIDかマイグループSIDかを判定する
     * <br>[解  説] 先頭文字に"M"が有る場合、マイグループSID
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isMyGroupSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf(GSConstSchedule.MY_GROUP_STRING);

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }
    /**
     * <br>[機  能] フォーム情報のグループコンボ値からグループSID又はマイグループSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又はマイグループSID
     */
    public static int getDspGroupSid(String gpSid) {
        int ret = 0;
        if (gpSid == null) {
            return ret;
        }

        if (isMyGroupSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }

    /**
     * <br>[機  能] スケジュールモデルから表示用のタイトルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param schModel SchDataModel
     * @param sessionUsrSid ユーザSID
     * @return 表示用スケジュールタイトル
     * @throws SQLException SQL実行例外
     */
    public String getDspTitle(
            RequestModel reqMdl,
            Connection con,
            RsvScdOperationModel schModel,
            int sessionUsrSid) throws SQLException {
        String title = "";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textYoteiari = gsMsg.getMessage("schedule.src.9");

        //自分の予定の場合
        if (schModel.getScdUsrSid() == sessionUsrSid) {
            return schModel.getScdTitle();
        }

        //公開区分
        if (schModel.getScdPublic() == GSConstSchedule.DSP_YOTEIARI) {
            //予定あり
            title = textYoteiari;
        } else if (schModel.getScdPublic() == GSConstSchedule.DSP_NOT_PUBLIC) {
            //非公開

        } else if (schModel.getScdPublic() == GSConstSchedule.DSP_BELONG_GROUP) {
            //所属グループのみ公開

            //表示グループに所属しているか判定
            GroupBiz gpBiz = new GroupBiz();
            boolean belongFlg
                = gpBiz.isBothBelongGroup(sessionUsrSid, schModel.getScdUsrSid(), con);
            if (belongFlg) {
                //所属グループのみ公開 所属している
                title = schModel.getScdTitle();
            } else {
                //所属グループのみ公開 未所属の場合は予定ありを表示
                title = textYoteiari;
            }
        } else {
            //公開
            title = schModel.getScdTitle();
        }
        return title;
    }

    /**
     * 『所属グループのみで共有』の場合のグループリストを返す
     * @param con コネクション
     * @param userSid ユーザSID
     * @param sentakuFlg true:「非表示」のラベルを作成する, false:作成しない
     * @return GroupModel in ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> getGroupLabelForOnlyBelong(Connection con,
                                    int userSid, boolean sentakuFlg) throws SQLException {

        ArrayList<GroupModel> labelList = new ArrayList<GroupModel>();
        RsvUserGrpDao gpDao = new RsvUserGrpDao(con);

        //グループリストを取得
        List<Rsv110Model> list = gpDao.selectGroupDataListOrderbyClass(userSid);

        if (list == null || list.isEmpty()) {
            return null;
        }
        Iterator<Rsv110Model> it = list.iterator();

        while (it.hasNext()) {
            Rsv110Model gcmodel = it.next();
            GroupModel gtModel = new GroupModel();

            //グループSIDをセット
            gtModel.setGroupSid(gcmodel.getGroupSid());
            //グループ階層レベル
            gtModel.setClassLevel(gcmodel.getLowGrpLevel());
            //グループ名
            gtModel.setGroupName(__getGroupName(gtModel.getClassLevel(), gcmodel.getGroupName()));

            log__.debug("グループSID = " + gtModel.getGroupSid());
            log__.debug("グループ名(階層反映済) = " + gtModel.getGroupName());
            log__.debug("階層Lv = " + gtModel.getClassLevel());
            labelList.add(gtModel);
        }

        return labelList;
    }

    /**
     * グループ階層を反映させたグループ名を返す
     * @param classLevel グループ階層
     * @param groupName グループ名
     * @return グループ階層を反映させたグループ名
     */
    private String __getGroupName(int classLevel, String groupName) {

        String gpName = "";
        gpName = getCombSpace(classLevel) + groupName;

        return gpName;
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する(コンボボックス用)
     * <br>[解  説] 指定したユーザが属するグループの一覧を取得する
     * <br>         ユーザSID < 0 の場合は全グループの一覧を取得する
     * <br>[備  考]
     * @param level 階層ﾚﾍﾞﾙ
     * @return グループ情報一覧
     */
    public String getCombSpace(int level) {

        String space = "";

        switch (level) {
            case 1:
            default :
                space = "";
                break;
            case 2:
                space = "　";
                break;
            case 3:
                space = "　　";
                break;
            case 4:
                space = "　　　";
                break;
            case 5:
                space = "　　　　";
                break;
            case 6:
                space = "　　　　　";
                break;
            case 7:
                space = "　　　　　　";
                break;
            case 8:
                space = "　　　　　　　";
                break;
            case 9:
                space = "　　　　　　　　";
                break;
            case 10:
                space = "　　　　　　　　　";
                break;
        }


        return space;
    }

    /**
     * <br>[機  能] 施設予約の編集権限を該当するスケジュール編集権限へ変換して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param rsvEdit 施設予約情報 編集権限
     * @return スケジュール 編集権限
     */
    public static int getScdEditKbn(int rsvEdit) {
        int scdEdit = GSConstSchedule.EDIT_CONF_OWN;

        switch (rsvEdit) {
            case GSConstReserve.EDIT_AUTH_NONE:
                scdEdit = GSConstSchedule.EDIT_CONF_NONE;
                break;
            case GSConstReserve.EDIT_AUTH_PER_AND_ADU:
                scdEdit = GSConstSchedule.EDIT_CONF_OWN;
                break;
            case GSConstReserve.EDIT_AUTH_GRP_AND_ADU:
                scdEdit = GSConstSchedule.EDIT_CONF_GROUP;
                break;
            default:
                scdEdit = GSConstSchedule.EDIT_CONF_OWN;
        }

        return scdEdit;
    }

    /**
     * 施設予約の公開区分を該当するスケジュール公開区分へ変換して返す
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param rsvPublic 施設予約情報 公開区分
     * @param schKbn 同時登録スケジュール区分 0:ユーザスケジュール 1:グループスケジュール
     * @return scdPubli スケジュール 公開区分
     *
     */
    public static int getScdPublicKbn(int rsvPublic, int schKbn) {
        int scdPublic = GSConstSchedule.DSP_PUBLIC;

        //同時登録スケジュールがユーザスケジュールの場合
        if (schKbn == GSConstReserve.RSV_SCHKBN_USER) {
            switch (rsvPublic) {
            case GSConstReserve.PUBLIC_KBN_GROUP:
                scdPublic = GSConstSchedule.DSP_BELONG_GROUP;
                break;
            case GSConstReserve.PUBLIC_KBN_PLANS:
                scdPublic = GSConstSchedule.DSP_YOTEIARI;
                break;
            case GSConstReserve.PUBLIC_KBN_USRGRP:
                scdPublic = GSConstSchedule.DSP_USRGRP;
                break;
            case GSConstReserve.PUBLIC_KBN_TITLE:
                scdPublic = GSConstSchedule.DSP_TITLE;
                break;
            default:
                scdPublic = GSConstSchedule.DSP_PUBLIC;
            }
        //同時登録スケジュールがグループの場合
        } else {
            if (rsvPublic != GSConstReserve.PUBLIC_KBN_ALL) {
                scdPublic = GSConstSchedule.DSP_NOT_PUBLIC;
            }
        }

        return scdPublic;
    }

    
}
