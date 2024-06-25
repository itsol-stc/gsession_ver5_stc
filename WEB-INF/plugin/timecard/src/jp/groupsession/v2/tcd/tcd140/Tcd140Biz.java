package jp.groupsession.v2.tcd.tcd140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.model.TcdTimezonePriModel;
import jp.groupsession.v2.usr.GSConstUser;


/**
 * <br>[機  能] タイムカード 管理者設定 ユーザ別時間帯設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd140Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd140Biz.class);
    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd140Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(Tcd140ParamModel paramMdl, Connection con)
    throws SQLException {

        log__.debug("STRAT");

        //ユーザ情報を取得
        List<CmnUsrmInfModel> uList = new ArrayList<CmnUsrmInfModel>();
        UserBiz usrBiz = new UserBiz();
        String[] targetUser = null;
        if (paramMdl.getTcdSelectUserlist() == null
                || paramMdl.getTcdSelectUserlist().length == 0) {
            targetUser = new String[] {paramMdl.getTcdSelectedUser()};
        } else {
            targetUser = paramMdl.getTcdSelectUserlist();
        }
        uList = usrBiz.getUserList(con, targetUser);

        paramMdl.setTcd140SelectedUser(uList);
        if (paramMdl.getTcd140initFlg() == 0 && uList.size() == 1) {
            // ユーザ個人編集の場合、使用時間帯情報の取得
            TcdTimezonePriDao priDao = new TcdTimezonePriDao(con);
            List<TcdTimezonePriModel> ttpList = priDao.select(uList.get(0).getUsrSid());
            if (ttpList != null && !ttpList.isEmpty()) {
                List<String> selectList = new ArrayList<String>();
                for (TcdTimezonePriModel ttpMdl : ttpList) {
                    int ttiSid = ttpMdl.getTtiSid();
                    if (ttpMdl.getTtpDefault() == GSConstTimecard.USED_TIMEZONE_DEFAULT) {
                        paramMdl.setTcd140DefaultTimeZone(ttiSid);
                    }
                    selectList.add(String.valueOf(ttiSid));
                }
                paramMdl.setTcd140SelectedTimeZone(selectList.toArray(new String[selectList.size()]));
            } else {
                // 管理者設定からデフォルト時間帯を取得
                TimecardBiz tcdBiz = new TimecardBiz(reqMdl__);
                TcdAdmConfModel admMdl =
                        tcdBiz.getTcdAdmConfModel(reqMdl__.getSmodel().getUsrsid(), con);
                paramMdl.setTcd140DefaultTimeZone(admMdl.getTacDefTimezone());
                paramMdl.setTcd140SelectedTimeZone(
                        new String[]{String.valueOf(admMdl.getTacDefTimezone())});
            }
        }
        paramMdl.setTcd140initFlg(1);
        //時間帯設定チェックコンボの設定
        TcdTimezoneInfoDao dao = new TcdTimezoneInfoDao(con);
        List<TcdTimezoneInfoModel> timezoneAllList = dao.selectTimeZoneInfoList();
        paramMdl.setTcd140TimeZoneCheck(timezoneAllList);

        //デフォルト時間帯設定セレクトボックスの設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        ArrayList<TcdTimezoneInfoModel> defaultList = new ArrayList<TcdTimezoneInfoModel>();

        //表示するセレクトボックスの中身を設定
        String[] selectTimezone = paramMdl.getTcd140SelectedTimeZone();
        if (selectTimezone != null && selectTimezone.length > 0) {
            for (String sSelectSid: selectTimezone) {
                int nSelectSid = NullDefault.getInt(sSelectSid, 0);
                for (TcdTimezoneInfoModel mdl: timezoneAllList) {
                    if (mdl.getTtiUse() == GSConstTimecard.TIMEZONE_USE_KBN_NG) {
                        continue;
                    }
                    int ttiSid = mdl.getTtiSid();
                    if (nSelectSid == ttiSid) {
                        defaultList.add(mdl);
                        break;
                    }
                }
            }
        }

        //何も選択されていないまたは選択されているものが選択不可の場合
        if (defaultList.isEmpty()) {
            TcdTimezoneInfoModel bean = new TcdTimezoneInfoModel();
            String textSelect = gsMsg.getMessage("cmn.select.plz");
            bean.setTtiSid(-1);
            bean.setTtiName(textSelect);
            defaultList.add(bean);
        }
        paramMdl.setTcd140TimeZoneDefaultList(defaultList);

        log__.debug("end");
    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説] delete insertを行う
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    protected void _setInsertData(Tcd140ParamModel paramMdl, Connection con)
            throws SQLException {
        log__.debug("STRAT");

        String[] targetUser = null;
        if (paramMdl.getTcdSelectUserlist() == null
                || paramMdl.getTcdSelectUserlist().length == 0) {
            targetUser = new String[] {paramMdl.getTcdSelectedUser()};
        } else {
            targetUser = paramMdl.getTcdSelectUserlist();
        }
        TcdTimezonePriDao priDao = new TcdTimezonePriDao(con);
        // 削除
        priDao.delete(targetUser);
        // 登録
        String[] selectTtiSids = paramMdl.getTcd140SelectedTimeZone();
        for (String usrSidStr : targetUser) {
            int usrSid = NullDefault.getInt(usrSidStr, -1);
            if (usrSid < GSConstUser.USER_RESERV_SID) {
                continue;
            }
            for (String ttiSidStr : selectTtiSids) {
                int ttiSid = NullDefault.getInt(ttiSidStr, 0);
                priDao.insert(__createInsertModel(ttiSid, paramMdl.getTcd140DefaultTimeZone(), usrSid));
            }
        }
        log__.debug("end");
    }

    /**
     * <br>[機  能]データ登録用モデルの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param ttiSid 選択時間帯SID
     * @param defaultTtiSid デフォルト時間帯SID
     * @return usrSid 登録ユーザSID
     */
    private TcdTimezonePriModel __createInsertModel(int ttiSid, int defaultTtiSid, int usrSid) {

        TcdTimezonePriModel ret = new TcdTimezonePriModel();
        UDate now = new UDate();
        ret.setTtiSid(ttiSid);
        ret.setUsrSid(usrSid);
        ret.setTtpAuid(reqMdl__.getSmodel().getUsrsid());
        ret.setTtpAdate(now);
        if (defaultTtiSid == ttiSid) {
            ret.setTtpDefault(1);
        } else {
            ret.setTtpDefault(0);
        }
        return ret;
    }
}
