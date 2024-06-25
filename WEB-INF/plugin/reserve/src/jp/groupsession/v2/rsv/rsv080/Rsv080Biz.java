package jp.groupsession.v2.rsv.rsv080;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.biz.RsvUsedDataBiz;
import jp.groupsession.v2.rsv.dao.RsvBinDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設情報設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv080Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv080Biz.class);
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
    public Rsv080Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 処理権限判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv080ParamModel
     * @return ret true:処理実行可 false:処理実行負荷
     * @throws SQLException SQL実行時例外
     */
    public boolean isPossibleToProcess(Rsv080ParamModel paramMdl)
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
     * <br>[機  能] 施設グループ、施設一覧をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv080ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Rsv080ParamModel paramMdl) throws SQLException {

        int grpSid = paramMdl.getRsv080EditGrpSid();

        //施設グループ情報を取得
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
        Rsv080Model grpMdl = grpDao.getGrpBaseData(grpSid);
        paramMdl.setRsv080RsgName(grpMdl.getRsgName());
        paramMdl.setRsv080RskName(grpMdl.getRskName());

        int rskSid = grpMdl.getRskSid();

        //施設区分によって表示する項目を決定する
        __setSisetuListHeader(paramMdl, rskSid);

        //施設情報を取得する
        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        ArrayList<RsvSisDataModel> dataList = dataDao.selectSisetuList(grpSid);

        int index = 1;
        String[] sortKey = new String[dataList.size()];
        ArrayList<Rsv080Model> retList = new ArrayList<Rsv080Model>();
        String firstRecordKey = null;

        //画面表示の設定 + 並び順変更用のキー作成
        for (RsvSisDataModel mdl : dataList) {
            Rsv080Model retMdl = new Rsv080Model();
            retMdl.setRsgSid(mdl.getRsgSid());
            retMdl.setRsdSid(mdl.getRsdSid());
            retMdl.setRsdId(mdl.getRsdId());
            retMdl.setRsdName(mdl.getRsdName());
            retMdl.setRsdSnum(NullDefault.getString(mdl.getRsdSnum(), ""));

            //設定項目1
            if (!StringUtil.isNullZeroString(
                    paramMdl.getRsv080PropHeaderName1())) {
                retMdl.setRsdProp1(NullDefault.getString(mdl.getRsdProp1(), ""));
            }
            //設定項目2
            if (!StringUtil.isNullZeroString(
                    paramMdl.getRsv080PropHeaderName2())) {
                retMdl.setRsdProp2(
                        __convertKahukaFlg(
                                NullDefault.getStringZeroLength(mdl.getRsdProp2(),
                                        String.valueOf(GSConstReserve.PROP_KBN_KA))));
            }
            //設定項目3
            if (!StringUtil.isNullZeroString(
                    paramMdl.getRsv080PropHeaderName3())) {
                retMdl.setRsdProp3(
                        __convertKahukaFlg(
                                NullDefault.getStringZeroLength(mdl.getRsdProp3(),
                                        String.valueOf(GSConstReserve.PROP_KBN_KA))));
            }
            //設定項目4
            if (!StringUtil.isNullZeroString(
                    paramMdl.getRsv080PropHeaderName4())) {
                retMdl.setRsdProp4(NullDefault.getString(mdl.getRsdProp4(), ""));
            }
            //設定項目5
            if (!StringUtil.isNullZeroString(
                    paramMdl.getRsv080PropHeaderName5())) {
                retMdl.setRsdProp5(NullDefault.getString(mdl.getRsdProp5(), ""));
            }
            //設定項目6
            if (!StringUtil.isNullZeroString(
                    paramMdl.getRsv080PropHeaderName6())) {
                retMdl.setRsdProp6(NullDefault.getString(mdl.getRsdProp6(), ""));
            }
            //設定項目7
            if (!StringUtil.isNullZeroString(
                    paramMdl.getRsv080PropHeaderName7())) {
                retMdl.setRsdProp7(
                        __convertKahukaFlg(
                                NullDefault.getStringZeroLength(mdl.getRsdProp7(),
                                        String.valueOf(GSConstReserve.PROP_KBN_KA))));
            }

            //施設ごとに施設予約の承認設定が可能かを判定
            RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
            if (rsvCmnBiz.isApprSisGroup(con_, grpSid)) {
                //不可 全て承認必要を設定
                retMdl.setRsdApprKbn(GSConstReserve.RSD_APPR_KBN_APPR);
            } else {
                //可 各施設の承認情報を設定
                retMdl.setRsdApprKbn(mdl.getRsdApprKbn());
            }
            String radioKey = String.valueOf(mdl.getRsdSid());
            sortKey[index - 1] = radioKey;
            retMdl.setRadioKey(radioKey);
            retList.add(retMdl);

            if (index == 1) {
                firstRecordKey = radioKey;
            }
            index += 1;
        }

        //ソート順ラジオが未選択の場合は1レコード目を選択済みにする
        if (StringUtil.isNullZeroString(paramMdl.getRsv080SortRadio())) {
            paramMdl.setRsv080SortRadio(firstRecordKey);
        } else {
            //ソート順ラジオが選択済みの場合はキー値が存在するか精査
            if (sortKey != null && sortKey.length > 0) {
                boolean exists = false;
                String selectKey = paramMdl.getRsv080SortRadio();
                for (String key : sortKey) {
                    if (key.equals(selectKey)) {
                        exists = true;
                        break;
                    }
                }
                //キー値が見つからない(他のユーザにより削除された等)の場合
                if (!exists) {
                    paramMdl.setRsv080SortRadio(firstRecordKey);
                }
            }
        }

        paramMdl.setRsv080KeyList(sortKey);
        paramMdl.setRsv080SisetuList(retList);
    }

    /**
     * <br>[機  能] 施設区分に応じて施設一覧のヘッダ文字列をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv080ParamModel
     * @param rskSid 施設区分SID
     */
    private void __setSisetuListHeader(Rsv080ParamModel paramMdl, int rskSid) {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                paramMdl.setRsv080PropHeaderName1(gsMsg.getMessage("reserve.128"));
                paramMdl.setRsv080PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv080PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv080PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                paramMdl.setRsv080PropHeaderName1(gsMsg.getMessage("reserve.130"));
                paramMdl.setRsv080PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv080PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv080PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                paramMdl.setRsv080PropHeaderName1(gsMsg.getMessage("reserve.129"));
                paramMdl.setRsv080PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv080PropHeaderName4(gsMsg.getMessage("reserve.134"));
                paramMdl.setRsv080PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv080PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                paramMdl.setRsv080PropHeaderName1(gsMsg.getMessage("reserve.131"));
                paramMdl.setRsv080PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv080PropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
                paramMdl.setRsv080PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv080PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                paramMdl.setRsv080PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv080PropHeaderName7(gsMsg.getMessage("reserve.136"));
                break;
            default:
                break;
        }
    }

    /**
     * <br>[機  能] フラグ値から「可」「不可」に変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param flg 日本語に変換するフラグ
     * @return flgString 日本語変換結果
     */
    private String __convertKahukaFlg(String flg) {

        String flgString = "";

        if (!ValidateUtil.isNumber(flg)) {
            return flgString;
        }

        int intFlgVal = Integer.parseInt(flg);
        GsMessage gsMsg = new GsMessage(reqMdl_);
        switch (intFlgVal) {
            //可
            case GSConstReserve.PROP_KBN_KA:
                flgString = gsMsg.getMessage("cmn.accepted");
                break;
            //不可
            case GSConstReserve.PROP_KBN_HUKA:
                flgString = gsMsg.getMessage("cmn.not");
                break;
            default:
                break;
        }

        return flgString;
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv080ParamModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Rsv080ParamModel paramMdl, int changeKbn)
        throws SQLException {

        final RsvSisDataDao dao = new RsvSisDataDao(con_);

        //ラジオ選択値取得
        String selectSid = paramMdl.getRsv080SortRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        int motoSid = Integer.parseInt(selectSid);

        SortChangeBiz<RsvSisDataModel> sortBiz =
                SortChangeBiz.<RsvSisDataModel> builder()
                .setFuncTargetList(() -> {
                    return dao.selectSisetuList(paramMdl.getRsv080EditGrpSid());
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getRsdSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getRsdSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getRsdSid() == m2.getRsdSid()) {
                        return 0;
                    } else {
                        return (m1.getRsdSid() - m2.getRsdSid())
                                / Math.abs(m1.getRsdSid() - m2.getRsdSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setRsdSort(newSort);
                    dao.update(m);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }

    /**
     * <br>[機  能] 処理対象の施設名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv080ParamModel
     * @return sisetuName 施設名称
     * @throws SQLException SQL実行時例外
     */
    public String getSisetuName(Rsv080ParamModel paramMdl) throws SQLException {

        String sisetuName = "";
        String radioKey = paramMdl.getRsv080SortRadio();
        int sisetuSid = Integer.parseInt(radioKey);

        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        RsvSisDataModel dataParam = new RsvSisDataModel();
        dataParam.setRsdSid(sisetuSid);
        RsvSisDataModel ret = dataDao.select(dataParam);
        if (ret != null) {
            sisetuName = ret.getRsdName();
        }

        return sisetuName;
    }

    /**
     * <br>[機  能] 施設情報削除処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv080ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void doSisetuDelete(Rsv080ParamModel paramMdl) throws SQLException {

        //ラジオ選択値取得
        String selectSid = paramMdl.getRsv080SortRadio();
        int sisetuSid = Integer.parseInt(selectSid);

        //削除対象の施設に予約チェックがあれば除外
        ArrayList<String> convKeyArray = new ArrayList<String>();
        String[] ikkatuKey = paramMdl.getRsvIkkatuTorokuKey();

        if (ikkatuKey != null && ikkatuKey.length > 0) {
            for (String key : ikkatuKey) {
                String keySid = key.substring(key.indexOf("-") + 1);
                if (Integer.parseInt(keySid) != sisetuSid) {
                    convKeyArray.add(key);
                }
            }
            String[] convKeyStr = null;
            if (convKeyArray.isEmpty()) {
                convKeyStr = new String[0];
            } else {
                convKeyStr =
                    (String[]) convKeyArray.toArray(
                            new String[convKeyArray.size()]);
            }
            paramMdl.setRsvIkkatuTorokuKey(convKeyStr);
        }

        //施設情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        RsvUsedDataBiz usedDataBiz = new RsvUsedDataBiz(con_);
        usedDataBiz.insertRsvDataSize(Arrays.asList(sisetuSid), false);

        //施設情報を削除
        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        RsvSisDataModel dataParam = new RsvSisDataModel();
        dataParam.setRsdSid(sisetuSid);
        dataDao.delete(dataParam);

        //施設予約情報を削除
        RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
        ArrayList<Integer> rsySidList = yrkDao.getRsySidListSetRsdSid(sisetuSid);
        yrkDao.delete(sisetuSid);

        if (rsySidList.size() > 0) {
            RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con_);
            kyrkDao.delete(rsySidList);
        }

        //バイナリ情報を削除
        //施設・設備
        __delSisetuTempData(GSConstReserve.TEMP_IMG_KBN_SISETU, sisetuSid);

        //場所・地図
        __delSisetuTempData(GSConstReserve.TEMP_IMG_KBN_PLACE, sisetuSid);

    }

    /**
     * <br>[機  能] 施設添付情報を編集する
     * <br>[解  説]
     * <br>[備  考]
     * @param imgKbn 画像区分
     * @param sisetuSid 施設SID
     * @throws SQLException SQL実行例外
     */
    private void __delSisetuTempData(int imgKbn,
                                      int sisetuSid) throws SQLException {


        //バイナリー情報の論理削除
        RsvBinDao rsvBinDao = new RsvBinDao(con_);
        rsvBinDao.deleteBinfForSisetu(sisetuSid, imgKbn);

        //施設添付情報の削除
        rsvBinDao.deleteSisetuBin(sisetuSid, imgKbn);
    }

}