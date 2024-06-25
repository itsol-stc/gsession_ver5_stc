package jp.groupsession.v2.rsv.rsv200kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvHidModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.rsv080.Rsv080Model;
import jp.groupsession.v2.rsv.rsv200.Rsv200ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設一括設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv200knBiz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv200knBiz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv200knBiz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 処理権限判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv200knParamModel
     * @return ret true:処理実行可 false:処理実行負荷
     * @throws SQLException SQL実行時例外
     */
    public boolean isPossibleToProcess(Rsv200knParamModel paramMdl)
        throws SQLException {

       /***********************************************
         *
         * 施設の編集が可能か判定する
         *
         * 1:システム管理者である
         * 2:処理対象の施設グループが【権限設定をしない】に
         *   設定されている
         * 3:施設グループの管理者に設定されている
         *
         ***********************************************/
        boolean ret = _isSisetuEditAuthority(reqMdl_, con_, paramMdl.getRsv080EditGrpSid());
        log__.debug("処理権限 = " + ret);

        return ret;

    }

    /**
     * <br>[機  能] 画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv200knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Rsv200knParamModel paramMdl) throws SQLException {

        int grpSid = paramMdl.getRsv080EditGrpSid();

        //施設グループ情報を取得
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
        Rsv080Model grpMdl = grpDao.getGrpBaseData(grpSid);
        int rskSid = grpMdl.getRskSid();

        //施設区分によって表示する項目を決定する
        __setSisetuItem(paramMdl, rskSid);

        paramMdl.setRsv200knBiko(StringUtilHtml.transToHTmlPlusAmparsant(
                NullDefault.getString(paramMdl.getRsv200Biko(), "")));

        //適用対象施設取得
        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        ArrayList<RsvSisDataModel> rsv200SisetuList =
            dataDao.selectGrpSisetuList(paramMdl.getRsv200TargetSisetu());
        paramMdl.setRsv200SisetuList(rsv200SisetuList);
    }

    /**
     * <br>[機  能] 施設区分に応じて施設の入力項目をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv200knParamModel
     * @param rskSid 施設区分SID
     */
    private void __setSisetuItem(Rsv200ParamModel paramMdl, int rskSid) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                paramMdl.setRsv200PropHeaderName1(gsMsg.getMessage("reserve.128"));
                paramMdl.setRsv200PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                paramMdl.setRsv200PropHeaderName1(gsMsg.getMessage("reserve.130"));
                paramMdl.setRsv200PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                paramMdl.setRsv200PropHeaderName1(gsMsg.getMessage("reserve.129"));
                paramMdl.setRsv200PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv200PropHeaderName4(gsMsg.getMessage("reserve.134"));
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                paramMdl.setRsv200PropHeaderName1(gsMsg.getMessage("reserve.131"));
                paramMdl.setRsv200PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv200PropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                paramMdl.setRsv200PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv200PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            default:
                break;
        }
    }

    /**
     * <br>[機  能] 入力内容をDBに反映する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv200knParamModel
     * @throws SQLException SQL実行時例外
     */
    public void updateSisetuData(Rsv200knParamModel paramMdl) throws SQLException {

        //更新モデル設定
        Rsv200knModel param = __getUpdateModel(paramMdl);
        RsvSisDataDao dao = new RsvSisDataDao(con_);
        dao.updateSisetuIkkatu(param);

    }

    /**
     * <br>[機  能] DB更新モデルを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv200knParamModel
     * @return ret 更新モデル
     */
    private Rsv200knModel __getUpdateModel(Rsv200knParamModel paramMdl) {

        UDate now = new UDate();
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();

        Rsv200knModel ret = new Rsv200knModel();
        ret.setRsgSid(paramMdl.getRsv080EditGrpSid());

        if (paramMdl.getRsv200Prop1Check() == GSConstReserve.PROP_CHECK_YES) {
            ret.setRsdProp1UpdFlg(true);
            ret.setRsdProp1(NullDefault.getString(paramMdl.getRsv200Prop1Value(), ""));
        }

        if (paramMdl.getRsv200Prop2Check() == GSConstReserve.PROP_CHECK_YES) {
            ret.setRsdProp2UpdFlg(true);
            ret.setRsdProp2(paramMdl.getRsv200Prop2Value());
        }

        if (paramMdl.getRsv200Prop3Check() == GSConstReserve.PROP_CHECK_YES) {
            ret.setRsdProp3UpdFlg(true);
            ret.setRsdProp3(paramMdl.getRsv200Prop3Value());
        }

        if (paramMdl.getRsv200Prop4Check() == GSConstReserve.PROP_CHECK_YES) {
            ret.setRsdProp4UpdFlg(true);
            ret.setRsdProp4(NullDefault.getString(paramMdl.getRsv200Prop4Value(), ""));
        }

        if (paramMdl.getRsv200Prop5Check() == GSConstReserve.PROP_CHECK_YES) {
            ret.setRsdProp5UpdFlg(true);
            ret.setRsdProp5(NullDefault.getString(paramMdl.getRsv200Prop5Value(), ""));
        }

        if (paramMdl.getRsv200Prop6Check() == GSConstReserve.PROP_CHECK_YES) {
            ret.setRsdProp6UpdFlg(true);
            ret.setRsdProp6(NullDefault.getString(paramMdl.getRsv200Prop6Value(), ""));
        }

        if (paramMdl.getRsv200Prop7Check() == GSConstReserve.PROP_CHECK_YES) {
            ret.setRsdProp7UpdFlg(true);
            ret.setRsdProp7(paramMdl.getRsv200Prop7Value());
        }

        if (paramMdl.getRsv200BikoCheck() == GSConstReserve.PROP_CHECK_YES) {
            ret.setRsdRsdBikoUpdFlg(true);
            ret.setRsdBiko(NullDefault.getString(paramMdl.getRsv200Biko(), ""));
        }

        ret.setTargetSiset(paramMdl.getRsv200TargetSisetu());
        ret.setRsdEuid(usrSid);
        ret.setRsdEdate(now);

        return ret;
    }

    /**
     * <br>[機  能] ログメッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sidList SIDリスト
     * @param con コネクション
     * @return メッセージ
     * @throws SQLException SQLエラー
     */
    public String getLogMessage(String[] sidList, Connection con) throws SQLException {
        String msg = "";
        RsvSisDataDao dao = new RsvSisDataDao(con);
        ArrayList<String> list = new ArrayList<String>();
        for (String sid : sidList) {
            list.add(sid);
        }
        ArrayList<RsvHidModel> mdlList = dao.selectHidSisetuList(list);
        for (RsvHidModel mdl : mdlList) {
            if (msg.length() > 0) {
                msg += "\r\n";
            }
            msg += mdl.getRsdName();
        }
        return msg;
    }
}