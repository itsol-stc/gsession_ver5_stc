package jp.groupsession.v2.rsv.rsv230;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvInitPubDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvInitPubModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;

/**
 * <br>[機  能] 施設予約 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv230Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv230Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Rsv230Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv230ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public void setInitData(Rsv230ParamModel paramMdl, BaseUserModel umodel, Connection con)
            throws SQLException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {

        if (umodel == null) {
            return;
        }
        log__.debug("初期表示");

        //ラベル(時・分)を設定
        setDspData(paramMdl, con);

        _setUserCombo(con, paramMdl, reqMdl_);

        //DBより設定情報を取得。なければデフォルト値とする。
        RsvUserDao dao = new RsvUserDao(con);
        int userSid = umodel.getUsrsid();
        RsvUserModel model = dao.select(userSid);
        if (model == null) {
            return;
        }

        if (paramMdl.getRsv230initFlg() == 0) {
            if (model != null) {
                UDate ifr = model.getRsuIniFrDate();
                UDate ito = model.getRsuIniToDate();
                if (ifr == null) {
                    ifr = new UDate();
                    ifr.setHour(GSConstReserve.YRK_DEFAULT_START_HOUR);
                    ifr.setMinute(GSConstReserve.YRK_DEFAULT_START_MINUTE);
                }
                if (ito == null) {
                    ito = new UDate();
                    ito.setHour(GSConstReserve.YRK_DEFAULT_END_HOUR);
                    ito.setMinute(GSConstReserve.YRK_DEFAULT_END_MINUTE);
                }
                paramMdl.setRsv230DefFrH(ifr.getIntHour());
                paramMdl.setRsv230DefFrM(ifr.getIntMinute());
                paramMdl.setRsv230DefToH(ito.getIntHour());
                paramMdl.setRsv230DefToM(ito.getIntMinute());
                paramMdl.setRsv230Edit(model.getRsuIniEdit());
                paramMdl.setRsv230Public(model.getRsuIniPublic());
            }
            if (paramMdl.getRsv230Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                RsvInitPubDao ripDao = new RsvInitPubDao(con);
                ArrayList<String> usrGrpList = new ArrayList<String>();
                List<RsvInitPubModel> ripList = ripDao.select(userSid);
                for (RsvInitPubModel ripMdl : ripList) {
                    if (ripMdl.getRipType() == GSConst.TYPE_GROUP) {
                        usrGrpList.add("G" + String.valueOf(ripMdl.getRipPsid()));
                    } else {
                        usrGrpList.add(String.valueOf(ripMdl.getRipPsid()));
                    }
                }
                String[] sidList = usrGrpList.toArray(new String[0]);
                paramMdl.setRsv230PubUsrGrpSid(sidList);
                _setUserCombo(con, paramMdl, reqMdl_);
            }
            paramMdl.setRsv230initFlg(1);
        }
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getRsv230DefFrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv230DefFrTime", "rsv230DefFrH", "rsv230DefFrM", null);
        }
        if (paramMdl.getRsv230DefToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv230DefToTime", "rsv230DefToH", "rsv230DefToM", null);
        }
    }

    /**
     * <br>[機  能] 画面表示に必要な情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv230ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setDspData(Rsv230ParamModel paramMdl, Connection con) throws SQLException {

        RsvAdmConfDao aconfDao = new RsvAdmConfDao(con);
        RsvAdmConfModel aconf = aconfDao.select();
        //施設予約管理者設定より、時間間隔を取得する。
        int houDiv = GSConstReserve.DF_HOUR_DIVISION;
        if (aconf != null) {
            houDiv = aconf.getRacHourDiv();
        }
        paramMdl.setRsv230HourDiv(houDiv);

        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        //期間 編集許可
        paramMdl.setRsv230PeriodFlg(rsvBiz.canPeriodInitConf(con));
        //編集権限 編集許可
        paramMdl.setRsv230EditFlg(rsvBiz.canEditInitConf(con));
        //公開区分 編集許可
        paramMdl.setRsv230PublicFlg(rsvBiz.canPublicInitConf(con));
    }

    /**
     * <br>[機  能] ユーザコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setUserCombo(
            Connection con, 
            Rsv230ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {

        String[] selectUserSid = paramMdl.getRsv230PubUsrGrpSid();
        if (selectUserSid == null) {
            selectUserSid = new String[0];
        }

        //デフォルトユーザを設定
        if (paramMdl.getRsv230PubDefUsrSid() > 0) {
            boolean defFlg = false;
            ArrayList<String> usrSidList = new ArrayList<String>();
            for (String usid : selectUserSid) {
                usrSidList.add(usid);
                if (usid.equals(String.valueOf(paramMdl.getRsv230PubDefUsrSid()))) {
                    defFlg = true;
                }
            }
            if (!defFlg) {
                usrSidList.add(String.valueOf(paramMdl.getRsv230PubDefUsrSid()));
            }
            paramMdl.setRsv230PubUsrGrpSid(
                    (String[]) usrSidList.toArray(new String[usrSidList.size()]));
        }
    }
}