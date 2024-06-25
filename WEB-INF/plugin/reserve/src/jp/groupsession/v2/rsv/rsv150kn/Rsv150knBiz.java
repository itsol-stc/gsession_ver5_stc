package jp.groupsession.v2.rsv.rsv150kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.rsv150.Rsv150Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 個人設定 表示設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv150knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv150Biz.class);

    /** コネクション */
    protected Connection con_ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Rsv150knBiz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl_ = reqMdl;
    }


    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv150knParamModel
     * @throws SQLException 例外
     */
    public void initDsp(Rsv150knParamModel paramMdl) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl_);
        //初期表示画面のセット
        paramMdl.setRsv150knDefDsp(__getIniDspName(paramMdl.getRsv150DefDsp()));
        //グループ名称のセット
        int grpSid = paramMdl.getRsv150SelectedGrpSid();
        paramMdl.setRsv150knDispGroup(getSisetuGroupName(grpSid));

        log__.debug("paramMdl.getRsv150DispItem1() : " + paramMdl.getRsv150DispItem1());
        log__.debug("paramMdl.getRsv150DispItem2() : " + paramMdl.getRsv150DispItem2());
        paramMdl.setRsv150DispItem1(NullDefault.getString(
                paramMdl.getRsv150DispItem1(), String.valueOf(GSConstReserve.KOJN_SETTEI_DSP_NO)));
        paramMdl.setRsv150DispItem2(NullDefault.getString(
                paramMdl.getRsv150DispItem2(), String.valueOf(GSConstReserve.KOJN_SETTEI_DSP_NO)));
        //表示項目1のセット
        if (paramMdl.getRsv150DispItem1().equals(
                String.valueOf(GSConstReserve.KOJN_SETTEI_DSP_OK))) {
            paramMdl.setRsv150knDispItem1(gsMsg.getMessage("reserve.72"));
        }
        //表示項目2のセット
        if (paramMdl.getRsv150DispItem2().equals(
                String.valueOf(GSConstReserve.KOJN_SETTEI_DSP_OK))) {
            paramMdl.setRsv150knDispItem2(gsMsg.getMessage("reserve.137"));
        }
        //自動リロード時間のセット
        paramMdl.setRsv150knReloadTime(__getReloadTimeStr(paramMdl.getRsv150ReloadTime()));


    }

    /**
     * <br>[機  能] 個人設定の登録処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv150knParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    public void registPrivate(Rsv150knParamModel paramMdl, int userSid) throws SQLException {
        RsvUserModel model = new RsvUserModel();
        //モデルを取得
        model = getModel(model, paramMdl, userSid);
        //更新処理
        int count = updatePrivate(model, paramMdl, userSid);
        //更新件数が0なら新規登録を行う
        if (count <= 0) {
            log__.debug("新規登録を行います。count = " + count);
            insertPrivate(model, paramMdl, userSid);
        }
    }
    /**
     * <br>[機  能] 個人設定モデルを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param model RsvUserModel model, Rsv150knParamModel paramMdl, int userSid
     * @param paramMdl Rsv150knParamModel
     * @param userSid ログインユーザSID
     * @return model
     */
    private RsvUserModel getModel(RsvUserModel model, Rsv150knParamModel paramMdl, int userSid) {
        UDate now = new UDate();
        model.setUsrSid(userSid);
        model.setRsgSid(paramMdl.getRsv150SelectedGrpSid());
        model.setRsuDit1(Integer.parseInt(paramMdl.getRsv150DispItem1()));
        model.setRsuDit2(Integer.parseInt(paramMdl.getRsv150DispItem2()));
        model.setRsuMaxDsp(paramMdl.getRsv150ViewCnt());
        model.setRsuReload(NullDefault.getInt(
                paramMdl.getRsv150ReloadTime(), GSConstReserve.AUTO_RELOAD_10MIN));

        if (paramMdl.getRsv150DateKbn() == GSConstReserve.AUTH_ALL_USER) {
            model.setRsuDtmFr(Integer.parseInt(paramMdl.getRsv150SelectedFromSid()));
            model.setRsuDtmTo(Integer.parseInt(paramMdl.getRsv150SelectedToSid()));
        }
        UDate frDate = new UDate();
        frDate.setZeroHhMmSs();
        frDate.setHour(GSConstReserve.YRK_DEFAULT_START_HOUR);
        frDate.setMinute(GSConstReserve.YRK_DEFAULT_START_MINUTE);
        model.setRsuIniFrDate(frDate);
        UDate toDate = new UDate();
        toDate.setZeroHhMmSs();
        toDate.setHour(GSConstReserve.YRK_DEFAULT_END_HOUR);
        toDate.setMinute(GSConstReserve.YRK_DEFAULT_END_MINUTE);
        model.setRsuIniToDate(toDate);
        model.setRsuIniEdit(GSConstReserve.EDIT_AUTH_NONE);
        model.setRsuImgDsp(paramMdl.getRsv150ImgDspKbn());
        model.setRsuIniDsp(paramMdl.getRsv150DefDsp());
        model.setRsuAuid(userSid);
        model.setRsuAdate(now);
        model.setRsuEuid(userSid);
        model.setRsuEdate(now);
        return model;
    }

    /**
     * <br>[機  能] 個人設定を更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param model RsvUserModel
     * @param paramMdl Rsv150knParamModel
     * @param userSid ログインユーザSID
     * @return int 更新件数
     * @throws SQLException 例外
     */
    private int updatePrivate(
            RsvUserModel model, Rsv150knParamModel paramMdl, int userSid) throws SQLException {
        RsvUserDao dao = new RsvUserDao(con_);
        boolean dateUpdFlg = false;
        if (paramMdl.getRsv150DateKbn() == GSConstReserve.AUTH_ALL_USER) {
            dateUpdFlg = true;
        }
        return dao.updatePre(model, dateUpdFlg);
    }

    /**
     * <br>[機  能] 個人設定を追加します
     * <br>[解  説]
     * <br>[備  考]
     * @param model RsvUserModel
     * @param paramMdl Rsv150knParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    private void insertPrivate(
            RsvUserModel model, Rsv150knParamModel paramMdl, int userSid) throws SQLException {
        RsvUserDao dao = new RsvUserDao(con_);
        dao.insert(model);
    }
    /**
     * <br>[機  能] 指定された施設グループSIDの施設グループ名を返します
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid 施設グループSID
     * @return 施設グループ名
     * @throws SQLException SQL実行時例外
     */
    private String getSisetuGroupName(int grpSid) throws SQLException {
        String grpName = null;
        RsvSisGrpDao dao = new RsvSisGrpDao(con_);
        RsvSisGrpModel model = dao.select(grpSid);
        GsMessage gsMsg = new GsMessage(reqMdl_);
        if (model != null) {
            grpName = model.getRsgName();
        } else if (grpSid == 0) {
            grpName = gsMsg.getMessage("cmn.all");
        } else {
            grpName = gsMsg.getMessage("cmn.select.plz");
        }
        return grpName;
    }

    /**
     * <br>[機  能] 自動リロード時間コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reloadTime 自動リロード時間
     * @return labelList 自動リロード時間（表示用）
     */
    private String __getReloadTimeStr(String reloadTime) {

        String timeValue = NullDefault.getString(reloadTime, "600000");
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String ret = "";
        if (timeValue.equals("0")) {
            ret = gsMsg.getMessage("cmn.without.reloading");
        } else if (timeValue.equals("60000")) {
            ret = gsMsg.getMessage("cmn.1minute");
        } else if (timeValue.equals("180000")) {
            ret = "3" + gsMsg.getMessage("cmn.minute");
        } else if (timeValue.equals("300000")) {
            ret = "5" + gsMsg.getMessage("cmn.minute");
        } else if (timeValue.equals("600000")) {
            ret = "10" + gsMsg.getMessage("cmn.minute");
        } else if (timeValue.equals("1200000")) {
            ret = "20" + gsMsg.getMessage("cmn.minute");
        } else if (timeValue.equals("1800000")) {
            ret = "30" + gsMsg.getMessage("cmn.minute");
        } else if (timeValue.equals("2400000")) {
            ret = "40" + gsMsg.getMessage("cmn.minute");
        } else if (timeValue.equals("3000000")) {
            ret = "50" + gsMsg.getMessage("cmn.minute");
        } else if (timeValue.equals("3600000")) {
            ret = "60" + gsMsg.getMessage("cmn.minute");
        } else {
            ret = gsMsg.getMessage("cmn.without.reloading");
        }

        return ret;
    }
    /**
     * <br>[機  能] 画面名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return dspName　画面名
     * @param dspNum 画面
     */
    public String __getIniDspName(int dspNum) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String dspName = null;

        switch (dspNum) {
        case GSConstReserve.DSP_WEEK : //週間
        default:
            dspName = gsMsg.getMessage("cmn.weeks");
            break;
        case GSConstReserve.DSP_DAY : //日間
            dspName = gsMsg.getMessage("cmn.days2");
            break;
        }
        return dspName;
    }

    /**
     * <br>[機  能] ログ用 グループ名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param sid SID
     * @param con コネクション
     * @return グループ名
     * @throws SQLException SQLエラー
     */
    public String getGroupName(int sid, Connection con) throws SQLException {
        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        RsvSisGrpModel mdl = dao.select(sid);
        String grpName = mdl.getRsgName();
        return grpName;
    }
}
