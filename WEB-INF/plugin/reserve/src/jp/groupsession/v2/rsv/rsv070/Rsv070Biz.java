package jp.groupsession.v2.rsv.rsv070;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvBinDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100searchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設情報画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv070Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv070Biz.class);
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
    public Rsv070Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv070ParamModel
     * @param userSid ユーザSID
     * @return ret true:データ取得成功 false:データ取得失敗
     * @throws SQLException SQL実行時例外
     */
    public boolean setInitData(Rsv070ParamModel paramMdl, int userSid) throws SQLException {

        boolean ret = false;

        //施設情報を取得する
        int rsdSid = paramMdl.getRsv070RsdSid();
        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        Rsv070Model dataRet = dataDao.getPopUpSisetuData(rsdSid);
        if (dataRet == null) {
            log__.debug("該当データ無し");
            return ret;
        }

        //システム管理者フラグセット
        paramMdl.setRsv070AdmFlg(_isAdmin(reqMdl_, con_));

        int rskSid = dataRet.getRskSid();

        //施設区分によって表示する項目を決定する
        __setSisetuListHeader(paramMdl, rskSid);

        paramMdl.setRsv070GrpName(NullDefault.getString(dataRet.getRsgName(), ""));
        paramMdl.setRsv070SisetuKbnName(NullDefault.getString(dataRet.getRskName(), ""));
        paramMdl.setRsv070SisetuId(NullDefault.getString(dataRet.getRsdId(), ""));
        paramMdl.setRsv070SisetuName(NullDefault.getString(dataRet.getRsdName(), ""));
        paramMdl.setRsv070SisanKanri(NullDefault.getString(dataRet.getRsdSnum(), ""));

        //設定項目1
        if (!StringUtil.isNullZeroString(paramMdl.getRsv070PropHeaderName1())) {
            paramMdl.setRsv070Prop1Value(NullDefault.getString(dataRet.getRsdProp1Value(), ""));
        }
        //設定項目2
        if (!StringUtil.isNullZeroString(paramMdl.getRsv070PropHeaderName2())) {
            paramMdl.setRsv070Prop2Value(
                    NullDefault.getStringZeroLength(
                            dataRet.getRsdProp2Value(),
                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
        }
        //設定項目3
        if (!StringUtil.isNullZeroString(paramMdl.getRsv070PropHeaderName3())) {
            paramMdl.setRsv070Prop3Value(
                    NullDefault.getStringZeroLength(
                            dataRet.getRsdProp3Value(),
                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
        }
        //設定項目4
        if (!StringUtil.isNullZeroString(paramMdl.getRsv070PropHeaderName4())) {
            paramMdl.setRsv070Prop4Value(NullDefault.getString(dataRet.getRsdProp4Value(), ""));
        }
        //設定項目5
        if (!StringUtil.isNullZeroString(paramMdl.getRsv070PropHeaderName5())) {
            paramMdl.setRsv070Prop5Value(NullDefault.getString(dataRet.getRsdProp5Value(), ""));
        }
        //設定項目6
        if (!StringUtil.isNullZeroString(paramMdl.getRsv070PropHeaderName6())) {
            paramMdl.setRsv070Prop6Value(NullDefault.getString(dataRet.getRsdProp6Value(), ""));
        }
        //設定項目7
        if (!StringUtil.isNullZeroString(paramMdl.getRsv070PropHeaderName7())) {
            paramMdl.setRsv070Prop7Value(
                    NullDefault.getStringZeroLength(
                            dataRet.getRsdProp7Value(),
                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
        }

        //施設予約の承認
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        paramMdl.setRsv070apprKbnFlg(rsvCmnBiz.isApprSis(con_, rsdSid, -1));
        paramMdl.setRsv070apprDspFlg(
                dataRet.getRsdApprKbnDf() == GSConstReserve.SISETU_DATA_DSP_ON);

        //場所コメント
        paramMdl.setRsv070PlaComment(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdPlaCmt(), ""));

        //場所画像 コメント１
        paramMdl.setRsv070PlaceFileComment1(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt1(), ""));
        //場所画像 コメント２
        paramMdl.setRsv070PlaceFileComment2(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt2(), ""));
        //場所画像 コメント３
        paramMdl.setRsv070PlaceFileComment3(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt3(), ""));
        //場所画像 コメント４
        paramMdl.setRsv070PlaceFileComment4(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt4(), ""));
        //場所画像 コメント５
        paramMdl.setRsv070PlaceFileComment5(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt5(), ""));
        //場所画像 コメント６
        paramMdl.setRsv070PlaceFileComment6(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt6(), ""));
        //場所画像 コメント７
        paramMdl.setRsv070PlaceFileComment7(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt7(), ""));
        //場所画像 コメント８
        paramMdl.setRsv070PlaceFileComment8(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt8(), ""));
        //場所画像 コメント９
        paramMdl.setRsv070PlaceFileComment9(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt9(), ""));
        //場所画像 コメント１０
        paramMdl.setRsv070PlaceFileComment10(
                NullDefault.getStringZeroLength(
                        dataRet.getRsdImgCmt10(), ""));

        paramMdl.setRsv070Biko(StringUtilHtml.transToHTmlPlusAmparsant(
                NullDefault.getString(dataRet.getRsdBiko(), "")));
        if (paramMdl.getRsv070RsdMode() == GSConstReserve.POPUP_MODE_LIST_ON) {
            //今日の予約情報を取得
            paramMdl.setRsv070RsvList(
                    __getRsvDataList(dataRet.getRsgSid(), paramMdl.getRsv070RsdSid(), con_));
        }

        //施設予約個人設定で画像表示を許可しているか確認
        RsvUserDao ruDao = new RsvUserDao(con_);
        RsvUserModel ruMdl = ruDao.select(userSid);

        if (ruMdl == null) {
            //施設画像
            __setRsvBinData(paramMdl);
        } else {
            if (ruMdl.getRsuImgDsp() == GSConstReserve.SISETU_IMG_ON) {
                //施設画像
                __setRsvBinData(paramMdl);
            }
        }

        ret = true;
        return ret;
    }

    /**
     * <br>[機  能]施設画像情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv070ParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __setRsvBinData(Rsv070ParamModel paramMdl)
    throws SQLException {

        RsvBinDao rbDao = new RsvBinDao(con_);

        //施設・設備画像
        paramMdl.setRsv070SisetuBinDataList(
                rbDao.getWriteTmpFileListData(paramMdl.getRsv070RsdSid(),
                                              GSConstReserve.TEMP_IMG_KBN_SISETU,
                                              true));
        //施設・設備画像
        paramMdl.setRsv070PlaceBinDataList(
                rbDao.getWriteTmpFileListData(paramMdl.getRsv070RsdSid(),
                                              GSConstReserve.TEMP_IMG_KBN_PLACE,
                                              true));
    }

    /**
     * 施設SIDを指定し今日の予約情報を文字列として格納したリストを取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid 施設グループSID
     * @param sid 施設SID
     * @param con コネクション
     * @return ArrayList 予約情報を文字列として格納したリスト
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<String> __getRsvDataList(int gpSid, int sid, Connection con)
    throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl_.getSmodel();

        //システム管理者フラグ
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(
                con_, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        ArrayList<String> ret = new ArrayList<String>();
        //予約情報検索
        RsvSisYrkDao dao = new RsvSisYrkDao(con_);
        ArrayList<Rsv100SisYrkModel> list = new ArrayList<Rsv100SisYrkModel>();
        list = dao.getYrkReferenceList(setSearchData(gpSid, sid), adminUser, reqMdl_);
        for (Rsv100SisYrkModel model : list) {
            //表示用文字列へ変換
            ret.add(__getRsvString(model, con));
        }
        return ret;
    }

    /**
     * POPUP表示用の予約状況文字列を生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param model Rsv100SisYrkModel
     * @param con コネクション
     * @return String
     */
    private String __getRsvString(Rsv100SisYrkModel model, Connection con) {
        StringBuilder buf = new StringBuilder();
        UDate fr = model.getRsyFrDate();
        UDate to = model.getRsyToDate();
        String mok = model.getRsyContent();
        //開始日-終了日　利用目的
        buf.append(UDateUtil.getSlashYYMD(fr) + "　" + UDateUtil.getSeparateHM(fr));
        buf.append(" - ");
        buf.append(UDateUtil.getSlashYYMD(to) + "　" + UDateUtil.getSeparateHM(to));
        //空
        buf.append("　");
        //利用目的
        buf.append(mok);

        return buf.toString();
    }


    /**
     * <br>[機  能] 施設利用照会の検索条件modelを作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid 施設グループSID
     * @param sid 施設SID
     * @return Rsv100searchModel 検索条件model
     * @throws SQLException 例外
     */
    public Rsv100searchModel setSearchData(int gpSid, int sid) throws SQLException {
        //fromの作成
        UDate from = new UDate();
        from.setZeroHhMmSs();
        //toの作成
        UDate to = new UDate();
        to.setMaxHhMmSs();

        Rsv100searchModel mdl = new Rsv100searchModel();
        mdl.setRsvGrp1(gpSid);
        mdl.setRsvGrp2(sid);
        mdl.setRsvFrom(from);
        mdl.setRsvTo(to);
        mdl.setRsvOrderKey(GSConst.ORDER_KEY_ASC);
        mdl.setRsvSortKey(GSConstReserve.RSV_SORT_FROM);
        mdl.setRsvPageTop(1);
        mdl.setRsvMaxPage(Integer.MAX_VALUE);
        //検索条件
        mdl.setRsvSearchCondition(0);
        //検索対象(利用目的)
        mdl.setRsvTargetMok(0);
        //検索対象(内容)
        mdl.setRsvTargetNiyo(0);
        //第一ソートキー
        mdl.setRsvSelectedKey1(3);
        //第二ソートキー
        mdl.setRsvSelectedKey2(4);
        //第一ソートキー ソート
        mdl.setRsvSelectedKey1Sort(GSConst.ORDER_KEY_ASC);
        //第二ソートキー ソート
        mdl.setRsvSelectedKey2Sort(GSConst.ORDER_KEY_ASC);

        return mdl;
    }

    /**
     * <br>[機  能] 施設区分に応じて施設一覧のヘッダ文字列をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv070ParamModel
     * @param rskSid 施設区分SID
     */
    private void __setSisetuListHeader(Rsv070ParamModel paramMdl, int rskSid) {
        GsMessage gsMsg = new GsMessage(reqMdl_);

        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                paramMdl.setRsv070PropHeaderName1(gsMsg.getMessage("reserve.128"));
                paramMdl.setRsv070PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv070PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv070PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                paramMdl.setRsv070PropHeaderName1(gsMsg.getMessage("reserve.130"));
                paramMdl.setRsv070PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv070PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv070PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                paramMdl.setRsv070PropHeaderName1(gsMsg.getMessage("reserve.129"));
                paramMdl.setRsv070PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv070PropHeaderName4(gsMsg.getMessage("reserve.134"));
                paramMdl.setRsv070PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv070PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                paramMdl.setRsv070PropHeaderName1(gsMsg.getMessage("reserve.131"));
                paramMdl.setRsv070PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv070PropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
                paramMdl.setRsv070PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv070PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                paramMdl.setRsv070PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv070PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            default:
                break;
        }
    }
}