package jp.groupsession.v2.rsv.rsv280;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvInitPubDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvInitPubModel;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv280Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv280Biz.class);

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv280ParamModel
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行エラー
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public void setInitData(Rsv280ParamModel paramMdl, Connection con, RequestModel reqMdl)
            throws SQLException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {
        log__.debug("初期表示");

        //ラベル(分)
        RsvAdmConfDao aconfDao = new RsvAdmConfDao(con);
        RsvAdmConfModel aconfModel = aconfDao.select();

        _setUserCombo(con, paramMdl, reqMdl);

        //施設予約管理者設定より、時間間隔を取得する。
        int houDiv = GSConstReserve.DF_HOUR_DIVISION;
        if (aconfModel != null) {
            houDiv = aconfModel.getRacHourDiv();
        }
        paramMdl.setRsv280HourDiv(houDiv);

        if (paramMdl.getRsv280initFlg() == 0) {
            //DBより設定情報を取得。なければデフォルト値とする。
            if (aconfModel != null) {
                paramMdl.setRsv280PeriodKbn(aconfModel.getRacIniPeriodKbn());
                paramMdl.setRsv280DefFrH(aconfModel.getRacIniFrH());
                paramMdl.setRsv280DefFrM(aconfModel.getRacIniFrM());
                paramMdl.setRsv280DefToH(aconfModel.getRacIniToH());
                paramMdl.setRsv280DefToM(aconfModel.getRacIniToM());
                paramMdl.setRsv280EditKbn(aconfModel.getRacIniEditKbn());
                paramMdl.setRsv280Edit(aconfModel.getRacIniEdit());
                paramMdl.setRsv280PublicKbn(aconfModel.getRacIniPublicKbn());
                paramMdl.setRsv280Public(aconfModel.getRacIniPublic());
            }
            if (paramMdl.getRsv280Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                RsvInitPubDao ripDao = new RsvInitPubDao(con);
                ArrayList<String> usrGrpList = new ArrayList<String>();
                List<RsvInitPubModel> ripList = ripDao.select(GSConst.SYSTEM_USER_ADMIN);
                for (RsvInitPubModel ripMdl : ripList) {
                    if (ripMdl.getRipType() == GSConst.TYPE_GROUP) {
                        usrGrpList.add("G" + String.valueOf(ripMdl.getRipPsid()));
                    } else {
                        usrGrpList.add(String.valueOf(ripMdl.getRipPsid()));
                    }
                }
                String[] sidList = usrGrpList.toArray(new String[0]);
                paramMdl.setRsv280PubUsrGrpSid(sidList);
                _setUserCombo(con, paramMdl, reqMdl);
            }
            paramMdl.setRsv280initFlg(1);
        }
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getRsv280DefFrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv280DefFrTime", "rsv280DefFrH", "rsv280DefFrM", null);
        }
        
        if (paramMdl.getRsv280DefToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "rsv280DefToTime", "rsv280DefToH", "rsv280DefToM", null);
        }

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
            Rsv280ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {

        String[] selectUserSid = paramMdl.getRsv280PubUsrGrpSid();
        if (selectUserSid == null) {
            selectUserSid = new String[0];
        }

        //デフォルトユーザを設定
        if (paramMdl.getRsv280PubDefUsrSid() > 0) {
            boolean defFlg = false;
            ArrayList<String> usrSidList = new ArrayList<String>();
            for (String usid : selectUserSid) {
                usrSidList.add(usid);
                if (usid.equals(String.valueOf(paramMdl.getRsv280PubDefUsrSid()))) {
                    defFlg = true;
                }
            }
            if (!defFlg) {
                usrSidList.add(String.valueOf(paramMdl.getRsv280PubDefUsrSid()));
            }
            paramMdl.setRsv280PubUsrGrpSid(
                    (String[]) usrSidList.toArray(new String[usrSidList.size()]));
        }
    }
}