package jp.groupsession.v2.sch.sch092;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAdmConfDao;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 日間表示時間帯設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch092Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch092Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch092Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch092ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Sch092ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {
        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, umodel.getUsrsid());
        SchAdmConfModel aconf = biz.getAdmConfModel(con);
        UDate ifr = pconf.getSccFrDate();
        UDate ito = pconf.getSccToDate();

        //開始 時
        log__.debug("開始 時 = " + ifr.getIntHour());
        if (paramMdl.getSch092FrH() < 0) {
            paramMdl.setSch092FrH(ifr.getIntHour());
        }
        //終了 時
        log__.debug("終了 時 = " + ito.getIntHour());
        if (paramMdl.getSch092ToH() < 0) {
            paramMdl.setSch092ToH(ito.getIntHour());
        }

        //デフォルトグループ
        if (pconf.getSccDspMygroup() != 0) {
            paramMdl.setSch092DefGroup(
                    NullDefault.getString(paramMdl.getSch092DefGroup(),
                            GSConstSchedule.MY_GROUP_STRING
                            + String.valueOf(pconf.getSccDspMygroup())));
        } else if (pconf.getSccDspViewlist() != 0) {
            paramMdl.setSch092DefGroup(
                    NullDefault.getString(paramMdl.getSch092DefGroup(),
                            GSConstSchedule.DSP_LIST_STRING
                            + String.valueOf(pconf.getSccDspViewlist())));
        } else {
            paramMdl.setSch092DefGroup(
                    NullDefault.getString(paramMdl.getSch092DefGroup(),
                            String.valueOf(pconf.getSccDspGroup())));
        }

        //各ユーザが設定したメンバー表示設定を反映
        if (pconf.getSccSortEdit() == GSConstSchedule.MEM_EDIT_EXECUTE) {
            log__.debug("***ユーザが設定したソート順で表示します***");
            //ソート1
            if (paramMdl.getSch092SortKey1() < 0) {
                paramMdl.setSch092SortKey1(pconf.getSccSortKey1());
            }
            if (paramMdl.getSch092SortOrder1() < 0) {
                paramMdl.setSch092SortOrder1(pconf.getSccSortOrder1());
            }
            //ソート2
            if (paramMdl.getSch092SortKey2() < 0) {
                paramMdl.setSch092SortKey2(pconf.getSccSortKey2());
            }
            if (paramMdl.getSch092SortOrder2() < 0) {
                paramMdl.setSch092SortOrder2(pconf.getSccSortOrder2());
            }

        //管理者が設定したデフォルトメンバー表示設定を反映
        } else {
            log__.debug("***管理者が設定したデフォルトのソート順で表示します***");
            SchPriConfModel pconfDf = new SchPriConfModel();
            pconfDf = biz.getSchPriConfModel(con, 0);

            //ソート1
            if (paramMdl.getSch092SortKey1() < 0) {
                paramMdl.setSch092SortKey1(pconfDf.getSccSortKey1());
            }
            if (paramMdl.getSch092SortOrder1() < 0) {
                paramMdl.setSch092SortOrder1(pconfDf.getSccSortOrder1());
            }
            //ソート2
            if (paramMdl.getSch092SortKey2() < 0) {
                paramMdl.setSch092SortKey2(pconfDf.getSccSortKey2());
            }
            if (paramMdl.getSch092SortOrder2() < 0) {
                paramMdl.setSch092SortOrder2(pconfDf.getSccSortOrder2());
            }
        }

        //ユーザごとにメンバ表示順変更可能か
        paramMdl.setSch092MemDspConfFlg(aconf.getSadSortKbn());

        //トップメニュー設定
        paramMdl.setSch092DefDsp(NullDefault.getString(paramMdl.getSch092DefDsp(),
                                     String.valueOf(pconf.getSccGrpShowKbn())));

        //件数
        if (paramMdl.getSch092DefLine() < 0) {
            paramMdl.setSch092DefLine(pconf.getSccDspList());
        }

        //自動リロード時間
        paramMdl.setSch092ReloadTime(NullDefault.getString(
                paramMdl.getSch092ReloadTime(), String.valueOf(pconf.getSccReload())));
        paramMdl.setSch092TimeLabelList(__getTimeLabel());

        //週間表示開始曜日
        paramMdl.setSch092SelWeek(NullDefault.getString(
                paramMdl.getSch092SelWeek(), String.valueOf(pconf.getSccIniWeek())));
        paramMdl.setSch092WeekList(__getWeekLabel());

        //表示情報をFormにセットする
        setDspData(paramMdl, con, umodel.getUsrsid());

        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** 氏名 **/
        String textName = gsMsg.getMessage("cmn.name");
        /** 社員/職員番号 **/
        String textStaffNumber = gsMsg.getMessage("cmn.employee.staff.number");
        /** 役職 **/
        String textPost = gsMsg.getMessage("cmn.post");
        /** 生年月日 **/
        String textBirthDay = gsMsg.getMessage("cmn.birthday");
        /**ソートキー1 **/
        String textSortkey1 = gsMsg.getMessage("cmn.sortkey") + 1;
        /**ソートキー2 **/
        String textSortkey2 = gsMsg.getMessage("cmn.sortkey") + 2;

        String[] arrayLabel = {textName, textStaffNumber, textPost,
                                     textBirthDay, textSortkey1, textSortkey2};
        //ソートキーラベル
        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstSchedule.SORT_KEY_ALL.length; i++) {
            String label = arrayLabel[i];
            String value = Integer.toString(GSConstSchedule.SORT_KEY_ALL[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        paramMdl.setSch092SortKeyLabel(sortLabel);

    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch092ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Sch092ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, umodel.getUsrsid());

        //開始時刻
        UDate fromUd = new UDate();
        fromUd.setHour(paramMdl.getSch092FrH());
        //終了時刻
        UDate toUd = new UDate();
        toUd.setHour(paramMdl.getSch092ToH());
        pconf.setSccFrDate(fromUd);
        pconf.setSccToDate(toUd);

        boolean memberDspFlg = isMemberDspConf(con);
        //各ユーザ設定可
        if (memberDspFlg) {
            //ソート1
            pconf.setSccSortKey1(paramMdl.getSch092SortKey1());
            pconf.setSccSortOrder1(paramMdl.getSch092SortOrder1());
            //ソート2
            pconf.setSccSortKey2(paramMdl.getSch092SortKey2());
            pconf.setSccSortOrder2(paramMdl.getSch092SortOrder2());

            //ユーザ毎にメンバー表示順の変更を行った
            pconf.setSccSortEdit(GSConstSchedule.MEM_EDIT_EXECUTE);
        }

        //デフォルトグループ
        SchCommonBiz cBiz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel aconf = cBiz.getAdmConfModel(con);
        if (aconf.getSadDspGroup() != GSConstSchedule.SAD_DSP_GROUP_DEFGROUP) {
            if (SchCommonBiz.isMyGroupSid(paramMdl.getSch092DefGroup())) {
                //マイグループをデフォルト表示とした場合
                GroupBiz gpBiz = new GroupBiz();
                int gsid = gpBiz.getDefaultGroupSid(umodel.getUsrsid(), con);
                pconf.setSccDspGroup(gsid);
                pconf.setSccDspMygroup(SchCommonBiz.getDspGroupSid(paramMdl.getSch092DefGroup()));
                pconf.setSccDspViewlist(0);
            } else if (SchCommonBiz.isDspListSid(paramMdl.getSch092DefGroup())) {
                //表示リストをデフォルト表示とした場合
                GroupBiz gpBiz = new GroupBiz();
                int gsid = gpBiz.getDefaultGroupSid(umodel.getUsrsid(), con);
                pconf.setSccDspGroup(gsid);
                pconf.setSccDspViewlist(SchCommonBiz.getDspListSid(paramMdl.getSch092DefGroup()));
                pconf.setSccDspMygroup(0);
            } else {
                pconf.setSccDspGroup(Integer.parseInt(paramMdl.getSch092DefGroup()));
                pconf.setSccDspMygroup(0);
                pconf.setSccDspViewlist(0);
            }
        }

        //画面
        pconf.setSccGrpShowKbn(NullDefault.getInt(paramMdl.getSch092DefDsp(), 0));

        //件数
        pconf.setSccDspList(paramMdl.getSch092DefLine());
        pconf.setSccReload(NullDefault.getInt(
                paramMdl.getSch092ReloadTime(), GSConstSchedule.AUTO_RELOAD_10MIN));
        pconf.setSccIniWeek(NullDefault.getInt(
                paramMdl.getSch092SelWeek(), GSConstSchedule.CHANGE_WEEK_TODAY));

        pconf.setSccEuid(umodel.getUsrsid());
        pconf.setSccEdate(new UDate());

        boolean commitFlg = false;
        try {
            SchPriConfDao dao = new SchPriConfDao(con);
            int count = dao.updateDsp(pconf);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(pconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }

    /**
     * <br>[機  能] 表示情報をFormにセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch093ParamModel
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL実行エラー
     */
    public void setDspData(Sch092ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //グループラベルの作成
        SchCommonBiz cBiz = new SchCommonBiz(reqMdl__);
        List<SchLabelValueModel> glabel
                            = cBiz.getGroupLabelForScheduleMygrpExist(userSid, con, false, true);
        paramMdl.setSch092GroupLabel(glabel);

        //デフォルト表示グループフラグ
        SchAdmConfModel aconf = cBiz.getAdmConfModel(con);
        paramMdl.setSch092DefGroupFlg(aconf.getSadDspGroup());

        SchPriConfDao dao = new SchPriConfDao(con);
        SchPriConfModel conf = dao.select(userSid);
        //昇順
        String textOrderAsc = gsMsg.getMessage("cmn.order.asc");
        String textOrderDesc = gsMsg.getMessage("cmn.order.desc");

        /** メッセージ ソートキーALLテキスト **/
        String[] sortText = {gsMsg.getMessage("cmn.name4"),
                             gsMsg.getMessage("schedule.sch100.11"),
                             gsMsg.getMessage("schedule.sch100.16"),
                             gsMsg.getMessage("schedule.sch100.7"),
                             gsMsg.getMessage("cmn.sortkey") + 1,
                             gsMsg.getMessage("cmn.sortkey") + 2};

        //メンバー表示順を設定(各ユーザが設定したデータ)
        if (conf.getSccSortEdit() == GSConstSchedule.MEM_EDIT_EXECUTE) {

            //第一キー
            //氏名
            if (conf.getSccSortKey1() == GSConstSchedule.SORT_KEY_NAME) {
                paramMdl.setSch092DspSortKey1(sortText[0]);
            //社員
            } else if (conf.getSccSortKey1() == GSConstSchedule.SORT_KEY_SNO) {
                paramMdl.setSch092DspSortKey1(sortText[1]);
            //役職
            } else if (conf.getSccSortKey1() == GSConstSchedule.SORT_KEY_YKSK) {
                paramMdl.setSch092DspSortKey1(sortText[2]);
            //生年月日
            } else if (conf.getSccSortKey1() == GSConstSchedule.SORT_KEY_BDATE) {
                paramMdl.setSch092DspSortKey1(sortText[3]);
            //ソートキー1
            } else if (conf.getSccSortKey1() == GSConstSchedule.SORT_KEY_SORTKEY1) {
                paramMdl.setSch092DspSortKey1(sortText[4]);
            //ソートキー2
            } else if (conf.getSccSortKey1() == GSConstSchedule.SORT_KEY_SORTKEY2) {
                paramMdl.setSch092DspSortKey1(sortText[5]);
            }

            if (conf.getSccSortOrder1() == GSConstSchedule.ORDER_KEY_ASC) {
                paramMdl.setSch092DspSortOrder1(textOrderAsc);
            } else if (conf.getSccSortOrder1() == GSConstSchedule.ORDER_KEY_DESC) {
                paramMdl.setSch092DspSortOrder1(textOrderDesc);
            }

            //第二キー
            //氏名
            if (conf.getSccSortKey2() == GSConstSchedule.SORT_KEY_NAME) {
                paramMdl.setSch092DspSortKey2(sortText[0]);
            //社員
            } else if (conf.getSccSortKey2() == GSConstSchedule.SORT_KEY_SNO) {
                paramMdl.setSch092DspSortKey2(sortText[1]);
            //役職
            } else if (conf.getSccSortKey2() == GSConstSchedule.SORT_KEY_YKSK) {
                paramMdl.setSch092DspSortKey2(sortText[2]);
            //生年月日
            } else if (conf.getSccSortKey2() == GSConstSchedule.SORT_KEY_BDATE) {
                paramMdl.setSch092DspSortKey2(sortText[3]);
            //ソートキー1
            } else if (conf.getSccSortKey2() == GSConstSchedule.SORT_KEY_SORTKEY1) {
                paramMdl.setSch092DspSortKey2(sortText[4]);
            //ソートキー2
            } else if (conf.getSccSortKey2() == GSConstSchedule.SORT_KEY_SORTKEY2) {
                paramMdl.setSch092DspSortKey2(sortText[5]);
            }

            if (conf.getSccSortOrder2() == GSConstSchedule.ORDER_KEY_ASC) {
                paramMdl.setSch092DspSortOrder2(textOrderAsc);
            } else if (conf.getSccSortOrder2() == GSConstSchedule.ORDER_KEY_DESC) {
                paramMdl.setSch092DspSortOrder2(textOrderDesc);
            }

        //管理者設定で設定したデフォルト値
        } else {

            //第一キー
            //氏名
            if (aconf.getSadSortKey1() == GSConstSchedule.SORT_KEY_NAME) {
                paramMdl.setSch092DspSortKey1(sortText[0]);
            //社員
            } else if (aconf.getSadSortKey1() == GSConstSchedule.SORT_KEY_SNO) {
                paramMdl.setSch092DspSortKey1(sortText[1]);
            //役職
            } else if (aconf.getSadSortKey1() == GSConstSchedule.SORT_KEY_YKSK) {
                paramMdl.setSch092DspSortKey1(sortText[2]);
            //生年月日
            } else if (aconf.getSadSortKey1() == GSConstSchedule.SORT_KEY_BDATE) {
                paramMdl.setSch092DspSortKey1(sortText[3]);
            //ソートキー1
            } else if (aconf.getSadSortKey1() == GSConstSchedule.SORT_KEY_SORTKEY1) {
                paramMdl.setSch092DspSortKey1(sortText[4]);
            //ソートキー2
            } else if (aconf.getSadSortKey1() == GSConstSchedule.SORT_KEY_SORTKEY2) {
                paramMdl.setSch092DspSortKey1(sortText[5]);
            }

            if (aconf.getSadSortOrder1() == GSConstSchedule.ORDER_KEY_ASC) {
                paramMdl.setSch092DspSortOrder1(textOrderAsc);
            } else if (aconf.getSadSortOrder1() == GSConstSchedule.ORDER_KEY_DESC) {
                paramMdl.setSch092DspSortOrder1(textOrderDesc);
            }

            //第二キー
            //氏名
            if (aconf.getSadSortKey2() == GSConstSchedule.SORT_KEY_NAME) {
                paramMdl.setSch092DspSortKey2(sortText[0]);
            //社員
            } else if (aconf.getSadSortKey2() == GSConstSchedule.SORT_KEY_SNO) {
                paramMdl.setSch092DspSortKey2(sortText[1]);
            //役職
            } else if (aconf.getSadSortKey2() == GSConstSchedule.SORT_KEY_YKSK) {
                paramMdl.setSch092DspSortKey2(sortText[2]);
            //生年月日
            } else if (aconf.getSadSortKey2() == GSConstSchedule.SORT_KEY_BDATE) {
                paramMdl.setSch092DspSortKey2(sortText[3]);
            //ソートキー1
            } else if (aconf.getSadSortKey2() == GSConstSchedule.SORT_KEY_SORTKEY1) {
                paramMdl.setSch092DspSortKey2(sortText[4]);
            //ソートキー2
            } else if (aconf.getSadSortKey2() == GSConstSchedule.SORT_KEY_SORTKEY2) {
                paramMdl.setSch092DspSortKey2(sortText[5]);
            }

            if (aconf.getSadSortOrder2() == GSConstSchedule.ORDER_KEY_ASC) {
                paramMdl.setSch092DspSortOrder2(textOrderAsc);
            } else if (aconf.getSadSortOrder2() == GSConstSchedule.ORDER_KEY_DESC) {
                paramMdl.setSch092DspSortOrder2(textOrderDesc);
            }
        }
    }

    /**
     * <br>[機  能] 自動リロード時間コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getTimeLabel() {
        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ 分 **/
        String minute = gsMsg.getMessage("cmn.minute");
        /** メッセージ リロードしない **/
        String noReroad = gsMsg.getMessage("cmn.without.reloading");

        labelList.add(new LabelValueBean("1" + minute, "60000"));
        labelList.add(new LabelValueBean("3" + minute, "180000"));
        labelList.add(new LabelValueBean("5" + minute, "300000"));
        labelList.add(new LabelValueBean("10" + minute, "600000"));
        labelList.add(new LabelValueBean("20" + minute, "1200000"));
        labelList.add(new LabelValueBean("30" + minute, "1800000"));
        labelList.add(new LabelValueBean("40" + minute, "2400000"));
        labelList.add(new LabelValueBean("50" + minute, "3000000"));
        labelList.add(new LabelValueBean("60" + minute, "3600000"));
        labelList.add(new LabelValueBean(noReroad, "0"));
        return labelList;
    }

    /**
     * <br>[機  能] 週間表示開始曜日コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getWeekLabel() {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //今日
        String textToday = gsMsg.getMessage("cmn.today");
        //木曜日
        String textThursday = gsMsg.getMessage("cmn.thursday3");
        //金曜日
        String textFriday = gsMsg.getMessage("schedule.src.101");
        //土曜日
        String textSaturday = gsMsg.getMessage("cmn.saturday3");
        //日曜日
        String textSunday = gsMsg.getMessage("cmn.sunday3");
        //月曜日
        String textMonday = gsMsg.getMessage("cmn.monday3");
        //火曜日
        String textTuesday = gsMsg.getMessage("cmn.tuesday3");
        //水曜日
        String textWednesday = gsMsg.getMessage("cmn.wednesday3");

        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();
        labelList.add(new LabelValueBean(textToday,
                String.valueOf(GSConstSchedule.CHANGE_WEEK_TODAY)));
        labelList.add(new LabelValueBean(textSunday,
                String.valueOf(GSConstSchedule.CHANGE_WEEK_SUN)));
        labelList.add(new LabelValueBean(textMonday,
                String.valueOf(GSConstSchedule.CHANGE_WEEK_MON)));
        labelList.add(new LabelValueBean(textTuesday,
                String.valueOf(GSConstSchedule.CHANGE_WEEK_TUE)));
        labelList.add(new LabelValueBean(textWednesday,
                String.valueOf(GSConstSchedule.CHANGE_WEEK_WED)));
        labelList.add(new LabelValueBean(textThursday,
                String.valueOf(GSConstSchedule.CHANGE_WEEK_THU)));
        labelList.add(new LabelValueBean(textFriday,
                String.valueOf(GSConstSchedule.CHANGE_WEEK_FRI)));
        labelList.add(new LabelValueBean(textSaturday,
                String.valueOf(GSConstSchedule.CHANGE_WEEK_SAT)));
        return labelList;
    }

    /**
     * <br>[機  能] 個人設定でメンバー表示設定が可能か
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return true:メンバー表示設定可能,false:メンバー表示設定不可
     */
    public boolean isMemberDspConf(Connection con)
    throws SQLException {
        SchAdmConfDao sacDao = new SchAdmConfDao(con);
        boolean memberDspFlg = true;

        if (sacDao.select() != null) {
            memberDspFlg = (sacDao.select().getSadSortKbn() == GSConstSchedule.MEM_DSP_USR);
        }
        return memberDspFlg;
    }
}
