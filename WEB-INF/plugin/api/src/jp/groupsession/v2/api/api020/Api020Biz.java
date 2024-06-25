package jp.groupsession.v2.api.api020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.api.biz.ApiConfBiz;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;
import jp.groupsession.v2.cmn.model.base.IApiConfModel;
import jp.groupsession.v2.cmn.plugin.api.biz.IApiConfBiz;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] API基本設定 biz
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api020Biz {
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /**
     * コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public Api020Biz(RequestModel reqMdl, Connection con) {
        super();
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 初期設定
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @throws SQLException SQL実行時例外
     */
    public void doInit(Api020ParamModel param) throws SQLException {
        //DB値を読み込み
        IApiConfBiz confBiz = new ApiConfBiz();
        IApiConfModel conf = confBiz.getConf(con__);
        /* トークン認証 使用*/
        param.setApi020useToken(conf.getApcToakenUse());
        /* トークン 使用IP*/
        param.setApi020tokenIpArea(conf.getApcToakenIp());
        /* トークン 有効期限*/
        param.setApi020tokenLimit(conf.getApcToakenLife());
        /* ベーシック認証 使用*/
        param.setApi020useBasic(conf.getApcBasicUse());
        /* ベーシック 使用IP*/
        param.setApi020basicIpArea(conf.getApcBasicIp());
        /* 自動削除判定*/
        param.setApi020autoDel(conf.getApcAutoDel());

    }
    /**
    *
    * <br>[機  能] 表示前設定
    * <br>[解  説]
    * <br>[備  考]
    * @param param パラメータ
    * @throws SQLException 実行時例外
    */
    public void doDsp(Api020ParamModel param) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** トークン有効期限 コレクション*/
        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        String msgMin = gsMsg.getMessage("cmn.minute");
        String msgDay = gsMsg.getMessage("cmn.day");
        String msgWeek = gsMsg.getMessage("cmn.weeks");
        String msgFree = gsMsg.getMessage("main.man200.9");

        labelList.add(new LabelValueBean("30" + msgMin,
                String.valueOf(GSConstApi.TOKEN_LIMIT_30M)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"1"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_1H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"2"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_2H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"3"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_3H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"5"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_5H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"8"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_8H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"10"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_10H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"12"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_12H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"15"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_15H)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.hours", new String[] {"20"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_20H)));
        labelList.add(new LabelValueBean("1" + msgDay,
                String.valueOf(GSConstApi.TOKEN_LIMIT_1D)));
        labelList.add(new LabelValueBean("1" + msgWeek,
                String.valueOf(GSConstApi.TOKEN_LIMIT_1W)));
        labelList.add(new LabelValueBean("2" + msgWeek,
                String.valueOf(GSConstApi.TOKEN_LIMIT_2W)));
        labelList.add(
                new LabelValueBean(gsMsg.getMessage("cmn.months", new String[] {"1"}),
                        String.valueOf(GSConstApi.TOKEN_LIMIT_1MONTH)));
        labelList.add(new LabelValueBean(msgFree, String.valueOf(GSConstApi.TOKEN_LIMIT_FREE)));

        param.setApi020tokenLimitOption(labelList);
        //トークン有効期限 表示値
        for (LabelValueBean bean : labelList) {
            if (bean.getValue().equals(String.valueOf(param.getApi020tokenLimit()))) {
                param.setApi020tokenLimitDsp(bean.getLabel());
            }
        }


        //トークンIP指定 表示値
        param.setApi020tokenIpAreaDsp(
                StringUtilHtml.transToHTml(
                        param.getApi020tokenIpArea()));

        //ベーシックIP指定 表示値
        param.setApi020basicIpAreaDsp(
                StringUtilHtml.transToHTml(
                        param.getApi020basicIpArea()));

    }
    /**
     *
     * <br>[機  能] 前提設定
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @throws SQLException 実行時例外
     */
    public void doPrepareSetting(Api020ParamModel param) throws SQLException {
        OnetimePasswordBiz otpBiz = new OnetimePasswordBiz();
        CmnOtpConfModel otpConf = otpBiz.getOtpConf(con__);
        param.setApi020useOtp((otpConf.getCocUseOtp() == GSConstMain.OTP_USE));
    }
    /**
     *
     * <br>[機  能] 保管処理
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @throws SQLException 実行時例外
     */
    public void doSaveConf(Api020ParamModel param) throws SQLException {
        //DB値に書き込み
        ApiConfBiz confBiz = new ApiConfBiz();
        IApiConfModel conf = confBiz.getConf(con__);

        /* トークン認証 使用*/
        conf.setApcToakenUse(param.getApi020useToken());
        /* トークン 使用IP*/
        if (param.getApi020useToken() == GSConstApi.USEKBN_AUTH_USEIP) {
            //改行を統一
            String value = param.getApi020tokenIpArea()
                    .replaceAll("\\r\\n|\\r|\\n", "\r\n");
            conf.setApcToakenIp(value);
        } else {
            conf.setApcToakenIp("");
        }
        /* トークン 有効期限*/
        conf.setApcToakenLife(param.getApi020tokenLimit());
        /* ベーシック認証 使用*/
        conf.setApcBasicUse(param.getApi020useBasic());
        /* ベーシック 使用IP*/
        if (param.getApi020useBasic() == GSConstApi.USEKBN_AUTH_USEIP) {
            //改行を統一
            String value = param.getApi020basicIpArea()
                    .replaceAll("\\r\\n|\\r|\\n", "\r\n");
            conf.setApcBasicIp(value);
        } else {
            conf.setApcBasicIp("");
        }
        /* トークン 自動削除*/
        if (param.getApi020tokenLimit() == GSConstApi.TOKEN_LIMIT_FREE) {
            conf.setApcAutoDel(1);            
        } else {
            conf.setApcAutoDel(0);
        }
        confBiz.saveConf(con__, conf);
    }
    /**
     *
     * <br>[機  能] ログメッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @return ログメッセージ
     */
    public String outputLogMessage(Api020ParamModel param) {
        doDsp(param);

        GsMessage gsMsg = new GsMessage(reqMdl__);
        StringBuilder sb = new StringBuilder();
        sb.append(gsMsg.getMessage("api.api020.3") + ":");
        if (param.getApi020useToken() == GSConstApi.USEKBN_AUTH_NOUSE) {
            sb.append(gsMsg.getMessage("api.api020.8"));
        }
        if (param.getApi020useToken() == GSConstApi.USEKBN_AUTH_USE) {
            sb.append(gsMsg.getMessage("api.api020.9"));
        }
        if (param.getApi020useToken() == GSConstApi.USEKBN_AUTH_USEIP) {
            sb.append(gsMsg.getMessage("api.api020.10"));
        }
        sb.append(System.lineSeparator());
        sb.append(gsMsg.getMessage("api.api020.11") + ":" + param.getApi020tokenLimitDsp());
        sb.append(System.lineSeparator());

        sb.append(gsMsg.getMessage("api.api020.12") + ":");
        if (param.getApi020useBasic() == GSConstApi.USEKBN_AUTH_NOUSE) {
            sb.append(gsMsg.getMessage("api.api020.8"));
        }
        if (param.getApi020useBasic() == GSConstApi.USEKBN_AUTH_USE) {
            sb.append(gsMsg.getMessage("api.api020.9"));
        }
        if (param.getApi020useBasic() == GSConstApi.USEKBN_AUTH_USEIP) {
            sb.append(gsMsg.getMessage("api.api020.10"));
        }


        return sb.toString();
    }


}
