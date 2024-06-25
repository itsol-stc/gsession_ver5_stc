package jp.groupsession.v2.rsv.rsv280kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvInitPubDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvInitPubModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約初期値設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv280knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv280knBiz.class);
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
    public Rsv280knBiz(RequestModel reqMdl, Connection con) {
        super();
        reqMdl_ = reqMdl;
        con_ = con;
    }
    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv280knParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Rsv280knParamModel paramMdl, Connection con) throws SQLException {

        //期間を設定。
        paramMdl.setRsv280knDefFrHDsp(StringUtil.toDecFormat(paramMdl.getRsv280DefFrH(), "00"));
        paramMdl.setRsv280knDefFrMDsp(StringUtil.toDecFormat(paramMdl.getRsv280DefFrM(), "00"));
        paramMdl.setRsv280knDefToHDsp(StringUtil.toDecFormat(paramMdl.getRsv280DefToH(), "00"));
        paramMdl.setRsv280knDefToMDsp(StringUtil.toDecFormat(paramMdl.getRsv280DefToM(), "00"));

        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setRsv280knPubUsrGrpList(
                cmnBiz.getUserLabelList(con, paramMdl.getRsv280PubUsrGrpSid()));
    }

    /**
     * <br>[機  能] 設定された初期値設定をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv280knParamModel
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行エラー
     */
    public void entryInitConfig(Rsv280knParamModel paramMdl, Connection con,
                            int sessionUserSid) throws SQLException {

        RsvAdmConfModel admModel = RsvCommonBiz.getDefaultAdmConfModel();
        //開始時刻
        UDate fromUd = new UDate();
        fromUd.setZeroHhMmSs();
        fromUd.setHour(paramMdl.getRsv280DefFrH());
        fromUd.setMinute(paramMdl.getRsv280DefFrM());
        //終了時刻
        UDate toUd = new UDate();
        toUd.setZeroHhMmSs();
        toUd.setHour(paramMdl.getRsv280DefToH());
        toUd.setMinute(paramMdl.getRsv280DefToM());
        admModel.setRacIniPeriodKbn(paramMdl.getRsv280PeriodKbn());
        if (paramMdl.getRsv280PeriodKbn() == GSConstReserve.RAC_INIPERIODKBN_ADM) {
            admModel.setRacIniFrH(fromUd.getIntHour());
            admModel.setRacIniFrM(fromUd.getIntMinute());
            admModel.setRacIniToH(toUd.getIntHour());
            admModel.setRacIniToM(toUd.getIntMinute());
        } else {
            admModel.setRacIniFrH(9);
            admModel.setRacIniFrM(0);
            admModel.setRacIniToH(18);
            admModel.setRacIniToM(0);
        }

        //編集権限
        admModel.setRacIniEditKbn(paramMdl.getRsv280EditKbn());
        if (paramMdl.getRsv280EditKbn() == GSConstReserve.RAC_INIEDITKBN_ADM) {
            admModel.setRacIniEdit(paramMdl.getRsv280Edit());
        } else {
            admModel.setRacIniEdit(GSConstReserve.EDIT_AUTH_NONE);
        }

        //変更前の公開対象を削除
        RsvInitPubDao ripDao = new RsvInitPubDao(con);
        ripDao.delete(GSConst.SYSTEM_USER_ADMIN);

        //公開区分
        admModel.setRacIniPublicKbn(paramMdl.getRsv280PublicKbn());
        if (paramMdl.getRsv280PublicKbn() == GSConstReserve.RAC_INIPERIODKBN_ADM) {
            admModel.setRacIniPublic(paramMdl.getRsv280Public());
            //公開区分が指定ユーザグループのみ公開の場合
            if (paramMdl.getRsv280Public() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                UserBiz userBiz = new UserBiz();

                ArrayList<Integer> grpSids = new ArrayList<Integer>();
                List<String> usrSids = new ArrayList<String>();
                for (String target : paramMdl.getRsv280PubUsrGrpSid()) {
                    if (target.startsWith("G")) {
                        grpSids.add(NullDefault.getInt(
                                target.substring(1), -1));
                    } else {
                        if (NullDefault.getInt(
                                target, -1) > GSConstUser.USER_RESERV_SID) {
                            usrSids.add(target);
                        }
                    }
                }
                ArrayList<GroupModel> glist = new ArrayList<GroupModel>();
                ArrayList<BaseUserModel> ulist = new ArrayList<BaseUserModel>();
                //グループ存在チェック
                if (!grpSids.isEmpty()) {
                    glist = gdao.selectGroupNmListOrderbyClass(grpSids);
                }
                //ユーザ存在チェック
                if (!usrSids.isEmpty()) {
                    ulist = userBiz.getBaseUserList(con,
                                                    usrSids.toArray(new String[usrSids.size()]));
                }
                RsvInitPubModel ripMdl = new RsvInitPubModel();
                for (GroupModel gMdl : glist) {
                    ripMdl.setRipPsid(gMdl.getGroupSid());
                    ripMdl.setRipType(GSConst.TYPE_GROUP);
                    ripMdl.setUsrSid(GSConst.SYSTEM_USER_ADMIN);
                    ripDao.insert(ripMdl);
                }
                for (BaseUserModel uMdl : ulist) {
                    ripMdl.setRipPsid(uMdl.getUsrsid());
                    ripMdl.setRipType(GSConst.TYPE_USER);
                    ripMdl.setUsrSid(GSConst.SYSTEM_USER_ADMIN);
                    ripDao.insert(ripMdl);
                }
            }
        } else {
            admModel.setRacIniPublic(GSConstReserve.PUBLIC_KBN_ALL);
        }

        admModel.setRacEuid(sessionUserSid);
        admModel.setRacEdate(new UDate());

        boolean commitFlg = false;
        try {
            RsvAdmConfDao dao = new RsvAdmConfDao(con);
            if (dao.updateInitConf(admModel) <= 0) {
                //レコードがない場合は新規登録
                admModel.setRacAuid(sessionUserSid);
                admModel.setRacAdate(admModel.getRacEdate());
                dao.insert(admModel);
            }
            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (!commitFlg) {
                con.rollback();
            }
        }
    }
}
