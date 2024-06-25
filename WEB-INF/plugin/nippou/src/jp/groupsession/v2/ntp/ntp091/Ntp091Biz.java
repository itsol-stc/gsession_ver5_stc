package jp.groupsession.v2.ntp.ntp091;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpColMsgDao;
import jp.groupsession.v2.ntp.dao.NtpGyomuDao;
import jp.groupsession.v2.ntp.dao.NtpPriConfDao;
import jp.groupsession.v2.ntp.dao.NtpPriKakuninDao;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.model.NtpPriKakuninModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp091Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp091Biz.class);

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     *
     */
    public Ntp091Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp091ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public void setInitData(Ntp091ParamModel paramMdl, BaseUserModel umodel, Connection con)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        //個人設定情報より設定情報を取得。なければデフォルト値とする。
        NtpCommonBiz biz = new NtpCommonBiz(con, reqMdl__);
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con, umodel.getUsrsid());
        UDate ifr = pconf.getNprIniFrDate();
        UDate ito = pconf.getNprIniToDate();

        //開始 時
        log__.debug("開始 時 = " + ifr.getIntHour());
        paramMdl.setNtp091DefFrH(ifr.getIntHour());
        //開始 分
        log__.debug("開始 分 = " + ifr.getIntMinute());
        paramMdl.setNtp091DefFrM(ifr.getIntMinute());
        //終了 時
        log__.debug("終了 時 = " + ito.getIntHour());
        paramMdl.setNtp091DefToH(ito.getIntHour());
        //終了 分
        log__.debug("終了 分 = " + ito.getIntMinute());
        paramMdl.setNtp091DefToM(ito.getIntMinute());

//        //公開フラグ
//        paramMdl.setNtp091PubFlg(pconf.getNprIniPublic());
//        //編集権限
//        paramMdl.setNtp091Edit(pconf.getNprIniEdit());
        //タイトルカラー
        paramMdl.setNtp091Fcolor(String.valueOf(pconf.getNprIniFcolor()));
        //タイトルカラーコメント
        setTitleColorMsg(paramMdl, con);
//        //業務内容
//        paramMdl.setNtp091DefGyomu(pconf.getNprIniGyomu());
        //グループSID
//        paramMdl.setNtp091groupSid(pconf.getNprDspGroup());
        GroupBiz grpBiz = new GroupBiz();
        paramMdl.setNtp091groupSid(grpBiz.getDefaultGroupSid(umodel.getUsrsid(), con));

        //個人設定確認者情報を取得
        NtpPriKakuninDao dao = new NtpPriKakuninDao(con);
        List <NtpPriKakuninModel> kakuList = dao.select(umodel.getUsrsid());
        if (kakuList != null) {
            String[] kakuSidList = new String[kakuList.size()];
            for (int i = 0; i < kakuList.size(); i++) {
                kakuSidList[i] = String.valueOf(kakuList.get(i).getNpkDftUsr());
            }
            paramMdl.setNtp091userSid(kakuSidList);
        }

        //コンボを設定
        setComboLabel(paramMdl, con);
    }

    /**
     * <br>[機  能] 時間表示及びコンボボックスを設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param con コネクション

     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public void setComboLabel(Ntp091ParamModel paramMdl, Connection con)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //分単位
        NtpCommonBiz cmnBiz = new NtpCommonBiz(con, reqMdl__);
        int hourMemCount = cmnBiz.getDayNippouHourMemoriMin(con);
        paramMdl.setNtp091HourDiv(hourMemCount);
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getNtp091DefFrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "ntp091DefFrTime", "ntp091DefFrH", "ntp091DefFrM", null);
        }
        if (paramMdl.getNtp091DefToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "ntp091DefToTime", "ntp091DefToH", "ntp091DefToM", null);
        }

        //業務内容
        ArrayList<LabelValueBean> ntp091GyomuLabel = new ArrayList<LabelValueBean>();
        ntp091GyomuLabel.add(new LabelValueBean("選択してください", "-1"));
        NtpGyomuDao gDao = new NtpGyomuDao(con);
        List <NtpGyomuModel> gyomuList = gDao.select();
        for (NtpGyomuModel gModel : gyomuList) {
            ntp091GyomuLabel.add(new LabelValueBean(
                    gModel.getNgyName(), Integer.toString(gModel.getNgySid())));
        }
        paramMdl.setNtp091GyomuLabel(ntp091GyomuLabel);

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //グループ
        GroupBiz gpBiz = new GroupBiz();
        List<LabelValueBean> grpLabelList = gpBiz.getGroupLabelList(con, gsMsg);
        paramMdl.setNtp091GroupList(grpLabelList);

//        //追加済みユーザを取得する
//        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
//        List<CmnUsrmInfModel> settledUserList
//            = usiDao.select(paramMdl.getNtp091userSid());
//
//        //追加済みユーザ一覧をセット
//        List<LabelValueBean> rightList = new ArrayList <LabelValueBean>();
//        StringBuffer fullName = null;
//        for (CmnUsrmInfModel usrMdl : settledUserList) {
//            fullName = new StringBuffer("");
//            fullName.append(usrMdl.getUsiSei());
//            fullName.append(" ");
//            fullName.append(usrMdl.getUsiMei());
//            rightList.add(
//                    new LabelValueBean(fullName.toString(), String.valueOf(usrMdl.getUsrSid())));
//        }
//        paramMdl.setNtp091RightUserList(rightList);
//
//        //追加用ユーザ一覧をセット
//        int gpSid = paramMdl.getNtp091groupSid();
//
//        //除外するユーザSID
//        ArrayList<Integer> usrSids = new ArrayList<Integer>();
//        String[] userSids = paramMdl.getNtp091userSid();
//        if (userSids != null) {
//            for (int i = 0; i < userSids.length; i++) {
//                usrSids.add(new Integer(NullDefault.getInt(userSids[i], -1)));
//            }
//        }
//        UserSearchDao usDao = new UserSearchDao(con);
//        List<CmnUsrmInfModel>usList = usDao.getBelongUserList(gpSid, usrSids);
//
//        List<LabelValueBean> labelListAdd = new ArrayList<LabelValueBean>();
//        for (int i = 0; i < usList.size(); i++) {
//            CmnUsrmInfModel cuiMdl = usList.get(i);
//            labelListAdd.add(new LabelValueBean(cuiMdl.getUsiSei() + cuiMdl.getUsiMei(),
//                             String.valueOf(cuiMdl.getUsrSid())));
//        }
//        paramMdl.setNtp091LeftUserList(labelListAdd);
    }

    /**
     * タイトルカラーコメントを設定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setTitleColorMsg(Ntp091ParamModel paramMdl, Connection con) throws SQLException {

        //タイトルカラーコメント
        NtpColMsgDao msgDao = new NtpColMsgDao(con);
        ArrayList<String> msgList = msgDao.selectMsg();
        paramMdl.setNtp091ColorMsgList(msgList);
    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp091ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Ntp091ParamModel paramMdl,
               BaseUserModel umodel, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        NtpCommonBiz biz = new NtpCommonBiz(con, reqMdl__);
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con, umodel.getUsrsid());

        //開始時刻
        UDate fromUd = new UDate();
        fromUd.setHour(paramMdl.getNtp091DefFrH());
        fromUd.setMinute(paramMdl.getNtp091DefFrM());
        //終了時刻
        UDate toUd = new UDate();
        toUd.setHour(paramMdl.getNtp091DefToH());
        toUd.setMinute(paramMdl.getNtp091DefToM());

        pconf.setNprIniFrDate(fromUd);
        pconf.setNprIniToDate(toUd);
        pconf.setNprEuid(umodel.getUsrsid());
        pconf.setNprEdate(new UDate());
//        pconf.setNprIniPublic(paramMdl.getNtp091PubFlg());
//        pconf.setNprIniEdit(paramMdl.getNtp091Edit());
        pconf.setNprIniFcolor(Integer.parseInt(paramMdl.getNtp091Fcolor()));
//        pconf.setNprIniGyomu(paramMdl.getNtp091DefGyomu());

        boolean commitFlg = false;
        try {
            NtpPriConfDao dao = new NtpPriConfDao(con);
            int count = dao.updateInitData(pconf);
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
     * <br>[機  能] 設定された個人設定確認者情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp091ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPkakuninSetting(Ntp091ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        boolean commitFlg = false;
        try {
            //DB削除
            NtpPriKakuninDao dao = new NtpPriKakuninDao(con);
            dao.delete(umodel.getUsrsid());
            //登録
            if (paramMdl.getNtp091userSid() != null) {
                for (String usrSid : paramMdl.getNtp091userSid()) {
                    NtpPriKakuninModel model = new NtpPriKakuninModel();
                    model.setUsrSid(umodel.getUsrsid());
                    model.setNpkDftUsr(Integer.parseInt(usrSid));
                    model.setNpkAuid(umodel.getUsrsid());
                    model.setNpkAdate(new UDate());
                    model.setNpkEuid(umodel.getUsrsid());
                    model.setNpkEdate(new UDate());
                    dao.insert(model);
                }
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
}
