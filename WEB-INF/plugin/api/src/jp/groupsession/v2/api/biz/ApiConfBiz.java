package jp.groupsession.v2.api.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.api.dao.ApiConfDao;
import jp.groupsession.v2.api.model.ApiConfModel;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.IApiConfDao;
import jp.groupsession.v2.cmn.model.base.IApiConfModel;
import jp.groupsession.v2.cmn.plugin.api.biz.IApiConfBiz;
/**
 *
 * <br>[機  能] Api基本設定を扱うBizクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiConfBiz implements IApiConfBiz {
    /** Api基本設定*/
    private IApiConfModel conf__;
    /**
     *
     * コンストラクタ
     */
    public ApiConfBiz() {
    }

    /**
     * コンストラクタ
     * @param conf API基本設定情報
     */
    public ApiConfBiz(IApiConfModel conf) {
        super();
        conf__ = conf;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.api.biz.IApiConfBiz#getConf(java.sql.Connection)
     */
    @Override
    public IApiConfModel getConf(Connection con) throws SQLException {
        if (conf__ != null) {
            return conf__;
        }
        IApiConfDao dao = new ApiConfDao(con);
        List<IApiConfModel> list = dao.select();
        if (list.isEmpty()) {
            conf__ = new ApiConfModel();
        } else {
            conf__ = list.get(0);
        }
        return conf__;
    }
    @Override
    public void saveConf(Connection con, IApiConfModel conf) throws SQLException {
       if (conf == null) {
           return;
       }
       conf__ = conf;
       IApiConfDao dao = new ApiConfDao(con);
       if (dao.update(conf) <= 0) {
           dao.insert(conf);
       }
    }
    /**
     *
     * <br>[機  能] トークン認証使用判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return true:使用する
     * @throws SQLException SQL実行時例外
     */
    public boolean isAbleTokenAuth(
            HttpServletRequest req, Connection con) throws SQLException {
        IApiConfModel conf = getConf(con);
        int useKbn = conf.getApcToakenUse();
        if (useKbn == GSConstApi.USEKBN_AUTH_NOUSE) {
            return false;
        }
        if (useKbn == GSConstApi.USEKBN_AUTH_USE) {
            return true;
        }
        String address = CommonBiz.getRemoteAddr(req);
        String ipStr = conf.getApcToakenIp();
        String[] chkArr = ipStr.split("\\r\\n");
        for (String chkStr : chkArr) {
            if (chkStr.indexOf("*") >= 0) {
                chkStr = chkStr.substring(0, chkStr.indexOf("*"));
            } else {
                if (address.equals(chkStr)) {
                    return true;
                }
                continue;
            }
            if (StringUtil.isNullZeroString(chkStr)) {
                return true;
            }
            if (address.startsWith(chkStr)) {
                return true;
            }
        }
        return false;
    }
    /**
    *
    * <br>[機  能] トークン認証使用判定を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param req リクエスト
    * @param con コネクション
    * @return true:使用する
    * @throws SQLException SQL実行時例外
    */
   public boolean isAbleBasicAuth(
           HttpServletRequest req, Connection con) throws SQLException {
       IApiConfModel conf = getConf(con);
       int useKbn = conf.getApcBasicUse();
       if (useKbn == GSConstApi.USEKBN_AUTH_NOUSE) {
           return false;
       }
       if (useKbn == GSConstApi.USEKBN_AUTH_USE) {
           return true;
       }
       String address = CommonBiz.getRemoteAddr(req);
       String ipStr = conf.getApcBasicIp();
       String[] chkArr = ipStr.split("\\r\\n");
       for (String chkStr : chkArr) {
           if (chkStr.indexOf("*") >= 0) {
               chkStr = chkStr.substring(0, chkStr.indexOf("*"));
           } else {
               if (address.equals(chkStr)) {
                   return true;
               }
               continue;
           }
           if (StringUtil.isNullZeroString(chkStr)) {
               return true;
           }
           if (address.startsWith(chkStr)) {
               return true;
           }
       }
       return false;
   }

    /**
     *
     * <br>[機  能] トークン期限設定に基づき、有効期限を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param now 発効日
     * @param con コネクション
     * @return 期限日時
     * @throws SQLException SQL実行時例外
     */
    public UDate getLimitDate(UDate now, Connection con) throws SQLException {
        IApiConfModel conf = getConf(con);
        UDate ret = now.cloneUDate();

        switch (conf.getApcToakenLife()) {

        case GSConstApi.TOKEN_LIMIT_30M:
            ret.addMinute(30);
            return ret;
        case GSConstApi.TOKEN_LIMIT_1H:
            ret.addHour(1);
            return ret;
        case GSConstApi.TOKEN_LIMIT_2H:
            ret.addHour(2);
            return ret;
        case GSConstApi.TOKEN_LIMIT_3H:
            ret.addHour(3);
            return ret;
        case GSConstApi.TOKEN_LIMIT_5H:
            ret.addHour(5);
            return ret;
        case GSConstApi.TOKEN_LIMIT_8H:
            ret.addHour(8);
            return ret;
        case GSConstApi.TOKEN_LIMIT_10H:
            ret.addHour(10);
            return ret;
        case GSConstApi.TOKEN_LIMIT_12H:
            ret.addHour(12);
            return ret;
        case GSConstApi.TOKEN_LIMIT_15H:
            ret.addHour(15);
            return ret;
        case GSConstApi.TOKEN_LIMIT_20H:
            ret.addHour(20);
            return ret;
        case GSConstApi.TOKEN_LIMIT_1D:
            ret.addDay(1);
            return ret;
        case GSConstApi.TOKEN_LIMIT_1W:
            ret.addDay(7);
            return ret;
        case GSConstApi.TOKEN_LIMIT_2W:
            ret.addDay(14);
            return ret;
        case GSConstApi.TOKEN_LIMIT_1MONTH:
            ret.addMonth(1);
            return ret;
        case GSConstApi.TOKEN_LIMIT_FREE:
            ret.setMaxHhMmSs();
            ret.setDate("99999999");
            return ret;
        default:
            break;
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] Api基本設定の有効期限が1日以上かを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:1日以上
     * @throws SQLException SQL実行時例外
     */
    public boolean isTokenLifeDayBatch(Connection con) throws SQLException {
        IApiConfModel conf = getConf(con);

        switch (conf.getApcToakenLife()) {

            case GSConstApi.TOKEN_LIMIT_30M:
            case GSConstApi.TOKEN_LIMIT_1H:
            case GSConstApi.TOKEN_LIMIT_2H:
            case GSConstApi.TOKEN_LIMIT_3H:
            case GSConstApi.TOKEN_LIMIT_5H:
            case GSConstApi.TOKEN_LIMIT_8H:
            case GSConstApi.TOKEN_LIMIT_10H:
            case GSConstApi.TOKEN_LIMIT_12H:
            case GSConstApi.TOKEN_LIMIT_15H:
            case GSConstApi.TOKEN_LIMIT_20H:
                 return false;
            case GSConstApi.TOKEN_LIMIT_1D:
            case GSConstApi.TOKEN_LIMIT_1W:
            case GSConstApi.TOKEN_LIMIT_2W:
            case GSConstApi.TOKEN_LIMIT_1MONTH:
            case GSConstApi.TOKEN_LIMIT_FREE:
            default:
                 return true;
        }
    }
    
    /**
    *
    * <br>[機  能] Api基本設定の有効期限が無期限かを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @return true:1日以上
    * @throws SQLException SQL実行時例外
    */
   public boolean isTokenLifeDayBatchFree(Connection con) throws SQLException {
       IApiConfModel conf = getConf(con);

       switch (conf.getApcToakenLife()) {

           case GSConstApi.TOKEN_LIMIT_FREE:
               return true;
           case GSConstApi.TOKEN_LIMIT_30M:
           case GSConstApi.TOKEN_LIMIT_1H:
           case GSConstApi.TOKEN_LIMIT_2H:
           case GSConstApi.TOKEN_LIMIT_3H:
           case GSConstApi.TOKEN_LIMIT_5H:
           case GSConstApi.TOKEN_LIMIT_8H:
           case GSConstApi.TOKEN_LIMIT_10H:
           case GSConstApi.TOKEN_LIMIT_12H:
           case GSConstApi.TOKEN_LIMIT_15H:
           case GSConstApi.TOKEN_LIMIT_20H:
           case GSConstApi.TOKEN_LIMIT_1D:
           case GSConstApi.TOKEN_LIMIT_1W:
           case GSConstApi.TOKEN_LIMIT_2W:
           case GSConstApi.TOKEN_LIMIT_1MONTH:
           default:
                return false;
       }
   }
   
   /**
   *
   * <br>[機  能] Api基本設定にて無期限時自動削除するかを返す
   * <br>[解  説]
   * <br>[備  考]
   * @param con コネクション
   * @return true:自動削除
   * @throws SQLException SQL実行時例外
   */
  public boolean isTokenAutoDelete(Connection con) throws SQLException {
 
      IApiConfModel conf = getConf(con);
      if (conf.getApcAutoDel() == 1) {
          return true;
      }
      return false;
  }



}
