package jp.groupsession.v2.zsk.maingrp;

import java.sql.Connection;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.sch.dao.SchAdmConfDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.dao.ZaiGpriConfDao;
import jp.groupsession.v2.zsk.model.ZaiGpriConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 在席管理(メイン画面表示用 メンバー) ユーザの所属グループに関するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskMaingrpCommonBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZskMaingrpCommonBiz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public ZskMaingrpCommonBiz() {
    }

    /**
     * <br>[機  能] メイン画面に表示する在席管理のグループSIDを取得します。
     * <br>[解  説]
     * <br>[備  考] マイグループは先頭"M"付とする
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return String
     * @throws Exception SQL実行時例外
     */
    public String selectGroupSid(int usrSid, Connection con) throws Exception {

        log__.debug("-- selectGroupSid START --");

        //グループコード
        int groupCode = 0;
        //グループ区分
        int groupKbn = GSConstZaiseki.DSP_GROUP;
        //共有範囲フラグ
        boolean crangeChk = false;

        //DBより設定グループ情報を取得
        ZaiGpriConfDao dao = new ZaiGpriConfDao(con);
        ZaiGpriConfModel model = dao.select(usrSid);

        //共有範囲確認
        if (model != null) {
            crangeChk = __crangeCheck(usrSid, model.getZgcGrp(), model.getZgcGkbn(), con);
        }

        //グループ取得
        if (model != null && crangeChk == true) {
            //DBのグループ
            groupCode = model.getZgcGrp();
            groupKbn = model.getZgcGkbn();
        } else {
            //デフォルトグループ
            CmnBelongmDao cmnbelongDao = new CmnBelongmDao(con);
            groupCode = cmnbelongDao.selectUserBelongGroupDef(usrSid);
        }

        //マイグループ処理
        if (groupKbn == GSConstZaiseki.DSP_MYGROUP) {
            return "M" + String.valueOf(groupCode);
        }

        return String.valueOf(groupCode);
    }

    /**
     * <br>[機  能] メイン画面に表示する在席管理のグループSIDで共有範囲を確認します。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param groupCode グループコード
     * @param groupKbn グループ区分
     * @param con コネクション
     * @return true:OK false:NG
     * @throws Exception SQL実行時例外
     */
    private static boolean __crangeCheck(int userSid, int groupCode,
                                            int groupKbn, Connection con) throws Exception {

        //DBより現在のスケジュール管理者設定情報を取得
        SchAdmConfDao schdao = new SchAdmConfDao(con);
        SchAdmConfModel conf = schdao.select();

        //スケジュール管理者設定なし、又は共有範囲=「全員で共有」のときtrue
        if (conf == null || conf.getSadCrange() == GSConstSchedule.CRANGE_SHARE_ALL) {
            return true;
        }

        //共有範囲=「所属グループ」且つマイグループのときfalse
        if (groupKbn == GSConstZaiseki.DSP_MYGROUP) {
            return false;
        }

        //共有範囲=「所属グループ」且つグループに所属していないときfalse
        CmnBelongmDao dao = new CmnBelongmDao(con);
        CmnBelongmModel bmodel = dao.select(userSid, groupCode);
        if (bmodel == null) {
            return false;
        }

        return true;
    }
}