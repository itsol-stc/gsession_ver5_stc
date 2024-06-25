package jp.groupsession.v2.rsv.rsv090;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.biz.RsvUsedDataBiz;
import jp.groupsession.v2.rsv.dao.RsvBinDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.rsv080.Rsv080Model;
import jp.groupsession.v2.rsv.rsv090.model.Rsv090BinfModel;
import jp.groupsession.v2.rsv.rsv090.model.Rsv090DspModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv090Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv090Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "rsv090";

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv090Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 処理権限判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv090ParamModel
     * @return ret true:処理実行可 false:処理実行負荷
     * @throws SQLException SQL実行時例外
     */
    public boolean isPossibleToProcess(Rsv090ParamModel paramMdl)
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
        boolean ret = _isSisetuEditAuthority(reqMdl_, con_, paramMdl.getRsv090EditGrpSid());
        log__.debug("処理権限 = " + ret);

        return ret;

    }

    /**
     * <br>[機  能] 画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv090ParamModel
     * @param appRoot アプリケーションルートパス
     * @param cmd コマンドパラメータ
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @return Rsv090DspModel 表示用モデル
     */
    public Rsv090DspModel setSisetuData(Rsv090ParamModel paramMdl,
                               String appRoot,
                               String cmd,
                               String domain)
    throws SQLException, TempFileException, IOException, IOToolsException {

        boolean initFlg = paramMdl.isRsv090InitFlg();
        String procMode = paramMdl.getRsv090ProcMode();
        Rsv090DspModel dataRet = null;

        log__.debug("***Rsv090procMode = " + procMode);

        //初期表示 & 編集モード
        if (initFlg && procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
            //施設データ取得
            RsvSisDataDao dataDao = new RsvSisDataDao(con_);
            dataRet = dataDao.getSisetuData(paramMdl.getRsv090EditSisetuSid());
        }

        int rsvGrpSid = Integer.valueOf(NullDefault.getString(paramMdl.getRsv090SelectRsvGrp(),
                String.valueOf(paramMdl.getRsv090EditGrpSid())));
        paramMdl.setRsv090EditGrpSid(rsvGrpSid);
        paramMdl.setRsv090SelectRsvGrp(String.valueOf(paramMdl.getRsv090EditGrpSid()));

        //確認画面・画像設定画面からの遷移時は削除しない
        //初回新規登録時のみ削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        if ((!cmd.equals("back_to_sisetu_settei_input") && !cmd.equals("sisetu_img_touroku"))
                && (initFlg && procMode.equals(GSConstReserve.PROC_MODE_SINKI))) {
            log__.debug("***ファイル削除を実行します***");
            //テンポラリディレクトリの削除を行う
            temp.deleteTempPath(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_PLACE_UPLOAD);
            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_PLACE_DATA);
            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_UPLOAD);
            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_DSP);
        } else if ((!cmd.equals("back_to_sisetu_settei_input") && !cmd.equals("sisetu_img_touroku"))
                    && ((initFlg && procMode.equals(GSConstReserve.PROC_MODE_EDIT))
                    || (initFlg && procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD)))) {
            temp.deleteTempPath(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_PLACE_UPLOAD);
            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_PLACE_DATA);
            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_UPLOAD);
            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_DSP);
            temp.createTempDir(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_EDIT);

        }

        //施設ごとに施設予約の承認設定が可能かを判定
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        paramMdl.setRsv090sisGrpApprFlg(rsvCmnBiz.isApprSisGroup(con_, rsvGrpSid));

        if (dataRet != null) {
            paramMdl.setRsv090SisetuId(NullDefault.getString(dataRet.getRsdId(), ""));
            paramMdl.setRsv090SisetuName(NullDefault.getString(dataRet.getRsdName(), ""));
            paramMdl.setRsv090SisanKanri(NullDefault.getString(dataRet.getRsdSnum(), ""));
            paramMdl.setRsv090Prop1Value(NullDefault.getString(dataRet.getRsdProp1(), ""));
            paramMdl.setRsv090Prop2Value(
                    NullDefault.getStringZeroLength(
                            dataRet.getRsdProp2(),
                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
            paramMdl.setRsv090Prop3Value(
                    NullDefault.getStringZeroLength(
                            dataRet.getRsdProp3(),
                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
            paramMdl.setRsv090Prop4Value(NullDefault.getString(dataRet.getRsdProp4(), ""));
            paramMdl.setRsv090Prop5Value(NullDefault.getString(dataRet.getRsdProp5(), ""));
            paramMdl.setRsv090Prop6Value(NullDefault.getString(dataRet.getRsdProp6(), ""));
            paramMdl.setRsv090Prop7Value(
                    NullDefault.getStringZeroLength(
                            dataRet.getRsdProp7(),
                            String.valueOf(GSConstReserve.PROP_KBN_KA)));
            paramMdl.setRsv090Biko(NullDefault.getString(dataRet.getRsdBiko(), ""));
            paramMdl.setRsv090PlaceComment(NullDefault.getString(dataRet.getRsdPlaCmt(), ""));
            paramMdl.setRsv090apprKbn(dataRet.getRsdApprKbn());

            //表示区分
            paramMdl.setRsv090SisetuIdDspKbn(String.valueOf(dataRet.getRsdIdDf()));
            paramMdl.setRsv090SisanKanriDspKbn(String.valueOf(dataRet.getRsdSnumDf()));
            paramMdl.setRsv090Prop1ValueDspKbn(String.valueOf(dataRet.getRsdProp1Df()));
            paramMdl.setRsv090Prop2ValueDspKbn(String.valueOf(dataRet.getRsdProp2Df()));
            paramMdl.setRsv090Prop3ValueDspKbn(String.valueOf(dataRet.getRsdProp3Df()));
            paramMdl.setRsv090Prop4ValueDspKbn(String.valueOf(dataRet.getRsdProp4Df()));
            paramMdl.setRsv090Prop5ValueDspKbn(String.valueOf(dataRet.getRsdProp5Df()));
            paramMdl.setRsv090Prop6ValueDspKbn(String.valueOf(dataRet.getRsdProp6Df()));
            paramMdl.setRsv090Prop7ValueDspKbn(String.valueOf(dataRet.getRsdProp7Df()));
            paramMdl.setRsv090BikoDspKbn(String.valueOf(dataRet.getRsdBikoDf()));
            paramMdl.setRsv090PlaceCommentDspKbn(String.valueOf(dataRet.getRsdPlaCmtDf()));
            paramMdl.setRsv090SisetuImgDefoDspKbn(String.valueOf(dataRet.getRsdImgDf()));
            paramMdl.setRsv090apprKbnDspKbn(String.valueOf(dataRet.getRsdApprKbnDf()));

            //施設画像
            __setTempImgData(appRoot,
                    dataRet.getTmpFileSisetuList(),
                    GSConstReserve.TEMP_IMG_KBN_SISETU, paramMdl, domain);

            //場所画像
            __setTempImgData(appRoot,
                    dataRet.getTmpFilePlaceList(),
                    GSConstReserve.TEMP_IMG_KBN_PLACE, paramMdl, domain);

            //週間・日間表示用
            String editTempDir = temp.getTempPath(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_EDIT);
            List<LabelValueBean> dspData = getTempFileLabelList(paramMdl, editTempDir);

            if (dspData != null && dspData.size() > 0) {
                paramMdl.setRsv090SisetuImgDefoValue(dspData.get(0).getValue());
            }
        }

        return dataRet;
    }

    /**
     * <br>[機  能] 施設グループ情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv090ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setSisetuGroup(Rsv090ParamModel paramMdl) throws SQLException {

        //施設グループ情報を取得
        int rsgSid = paramMdl.getRsv090EditGrpSid();
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
        Rsv080Model grpMdl = grpDao.getGrpBaseData(rsgSid);
        paramMdl.setRsv090GrpName(grpMdl.getRsgName());
        paramMdl.setRsv090SisetuKbnName(grpMdl.getRskName());
        int rskSid = grpMdl.getRskSid();

        //施設区分毎に入力可能な項目を設定
        __setSisetuHeader(paramMdl, rskSid);

        //施設グループに管理者が設定されているかを判定
        ArrayList<String> grpAdmUsers = grpDao.getDefaultAdmUser(rsgSid);
        paramMdl.setRsv090sisGrpAdmFlg((grpAdmUsers != null && !grpAdmUsers.isEmpty()));
    }

    /**
     * <br>[機  能] 施設区分に応じて施設のヘッダ文字列をセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv090ParamModel
     * @param rskSid 施設区分SID
     */
    private void __setSisetuHeader(Rsv090ParamModel paramMdl, int rskSid) {
        GsMessage gsMsg = new GsMessage(reqMdl_);

        String[] msgSisetuId = new String[]{gsMsg.getMessage("reserve.55")};
        String[] msgSisankanri = new String[]{gsMsg.getMessage("cmn.asset.register.num")};
        String[] msgDefoImg = new String[]{gsMsg.getMessage("reserve.59")};
        String[] msgBiko = new String[]{gsMsg.getMessage("cmn.memo")};
        String[] msgPlace = new String[]{gsMsg.getMessage("reserve.location.comments")};

        paramMdl.setRsv090StrSisetuIdDspKbn(gsMsg.getMessage("reserve.view.item", msgSisetuId));
        paramMdl.setRsv090StrSisankanriDspKbn(
                gsMsg.getMessage("reserve.view.item", msgSisankanri));
        paramMdl.setRsv090StrDefoImgDspKbn(gsMsg.getMessage("reserve.view.item", msgDefoImg));
        paramMdl.setRsv090StrBikoDspKbn(gsMsg.getMessage("reserve.view.item", msgBiko));
        paramMdl.setRsv090StrPlaceDspKbn(gsMsg.getMessage("reserve.view.item", msgPlace));

        switch (rskSid) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                paramMdl.setRsv090PropHeaderName1(gsMsg.getMessage("reserve.128"));
                paramMdl.setRsv090PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv090PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv090PropHeaderName7(gsMsg.getMessage("reserve.136"));

                String[] msgHeya1 = new String[]{gsMsg.getMessage("reserve.128")};
                String[] msgHeya2 = new String[]{gsMsg.getMessage("reserve.132")};
                String[] msgHeya6 = new String[]{gsMsg.getMessage("reserve.135")};
                String[] msgHeya7 = new String[]{gsMsg.getMessage("reserve.136")};

                paramMdl.setRsv090ItemDspKbn1(gsMsg.getMessage("reserve.view.item", msgHeya1));
                paramMdl.setRsv090ItemDspKbn2(gsMsg.getMessage("reserve.view.item", msgHeya2));
                paramMdl.setRsv090ItemDspKbn6(gsMsg.getMessage("reserve.view.item", msgHeya6));
                paramMdl.setRsv090ItemDspKbn7(gsMsg.getMessage("reserve.view.item", msgHeya7));

                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                paramMdl.setRsv090PropHeaderName1(gsMsg.getMessage("reserve.130"));
                paramMdl.setRsv090PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv090PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv090PropHeaderName7(gsMsg.getMessage("reserve.136"));

                String[] msgBpn1 = new String[]{gsMsg.getMessage("reserve.130")};
                String[] msgBpn3 = new String[]{gsMsg.getMessage("reserve.133")};
                String[] msgBpn6 = new String[]{gsMsg.getMessage("reserve.135")};
                String[] msgBpn7 = new String[]{gsMsg.getMessage("reserve.136")};

                paramMdl.setRsv090ItemDspKbn1(gsMsg.getMessage("reserve.view.item", msgBpn1));
                paramMdl.setRsv090ItemDspKbn3(gsMsg.getMessage("reserve.view.item", msgBpn3));
                paramMdl.setRsv090ItemDspKbn6(gsMsg.getMessage("reserve.view.item", msgBpn6));
                paramMdl.setRsv090ItemDspKbn7(gsMsg.getMessage("reserve.view.item", msgBpn7));

                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                paramMdl.setRsv090PropHeaderName1(gsMsg.getMessage("reserve.129"));
                paramMdl.setRsv090PropHeaderName2(gsMsg.getMessage("reserve.132"));
                paramMdl.setRsv090PropHeaderName4(gsMsg.getMessage("reserve.134"));
                paramMdl.setRsv090PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv090PropHeaderName7(gsMsg.getMessage("reserve.136"));

                String[] msgCar1 = new String[]{gsMsg.getMessage("reserve.129")};
                String[] msgCar2 = new String[]{gsMsg.getMessage("reserve.132")};
                String[] msgCar4 = new String[]{gsMsg.getMessage("reserve.134")};
                String[] msgCar6 = new String[]{gsMsg.getMessage("reserve.135")};
                String[] msgCar7 = new String[]{gsMsg.getMessage("reserve.136")};

                paramMdl.setRsv090ItemDspKbn1(gsMsg.getMessage("reserve.view.item", msgCar1));
                paramMdl.setRsv090ItemDspKbn2(gsMsg.getMessage("reserve.view.item", msgCar2));
                paramMdl.setRsv090ItemDspKbn4(gsMsg.getMessage("reserve.view.item", msgCar4));
                paramMdl.setRsv090ItemDspKbn6(gsMsg.getMessage("reserve.view.item", msgCar6));
                paramMdl.setRsv090ItemDspKbn7(gsMsg.getMessage("reserve.view.item", msgCar7));

                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                paramMdl.setRsv090PropHeaderName1(gsMsg.getMessage("reserve.131"));
                paramMdl.setRsv090PropHeaderName3(gsMsg.getMessage("reserve.133"));
                paramMdl.setRsv090PropHeaderName5(GSConstReserve.RSK_TEXT_ISBN);
                paramMdl.setRsv090PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv090PropHeaderName7(gsMsg.getMessage("reserve.136"));

                String[] msgBok1 = new String[]{gsMsg.getMessage("reserve.131")};
                String[] msgBok3 = new String[]{gsMsg.getMessage("reserve.133")};
                String[] msgBok5 = new String[]{GSConstReserve.RSK_TEXT_ISBN};
                String[] msgBok6 = new String[]{gsMsg.getMessage("reserve.135")};
                String[] msgBok7 = new String[]{gsMsg.getMessage("reserve.136")};

                paramMdl.setRsv090ItemDspKbn1(gsMsg.getMessage("reserve.view.item", msgBok1));
                paramMdl.setRsv090ItemDspKbn3(gsMsg.getMessage("reserve.view.item", msgBok3));
                paramMdl.setRsv090ItemDspKbn5(gsMsg.getMessage("reserve.view.item", msgBok5));
                paramMdl.setRsv090ItemDspKbn6(gsMsg.getMessage("reserve.view.item", msgBok6));
                paramMdl.setRsv090ItemDspKbn7(gsMsg.getMessage("reserve.view.item", msgBok7));

                break;
            //その他
            case GSConstReserve.RSK_KBN_OTHER:
                paramMdl.setRsv090PropHeaderName6(gsMsg.getMessage("reserve.135"));
                paramMdl.setRsv090PropHeaderName7(gsMsg.getMessage("reserve.136"));

                String[] msgAth6 = new String[]{gsMsg.getMessage("reserve.135")};
                String[] msgAth7 = new String[]{gsMsg.getMessage("reserve.136")};

                paramMdl.setRsv090ItemDspKbn6(gsMsg.getMessage("reserve.view.item", msgAth6));
                paramMdl.setRsv090ItemDspKbn7(gsMsg.getMessage("reserve.view.item", msgAth7));

                break;
            default:
                break;
        }
    }

    /**
     * <br>[機  能] 処理対象の施設名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv090ParamModel
     * @return sisetuName 施設名称
     * @throws SQLException SQL実行時例外
     */
    public String getSisetuName(Rsv090ParamModel paramMdl) throws SQLException {

        String sisetuName = "";
        int sisetuSid = paramMdl.getRsv090EditSisetuSid();

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
     * @param paramMdl Rsv090ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void doSisetuDelete(Rsv090ParamModel paramMdl) throws SQLException {

        //ラジオ選択値取得
        int sisetuSid = paramMdl.getRsv090EditSisetuSid();

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

        //施設情報のデータ使用量を登録(削除する施設情報のデータ使用量を減算)
        RsvUsedDataBiz usedDataBiz = new RsvUsedDataBiz(con_);
        usedDataBiz.insertRsvDataSize(sisetuSid, false);

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

    /**
     * <br>[機  能] 添付ファイル情報をセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv090ParamModel
     * @param tempDirSisetu 施設/設備画像ファイルテンポラリディレクトリ
     * @param placeImgDataDir 場所/地図画像データ保存用テンポラリディレクトリ
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setTempFiles(Rsv090ParamModel paramMdl,
                              String tempDirSisetu,
                              String placeImgDataDir)

    throws IOToolsException {

        //施設・設備画像ファイル
        paramMdl.setRsv090SisetuFileLabelList(getTempFileLabelList(paramMdl, tempDirSisetu));

        //場所・地図画像ファイル
        getPlaceTempFileLabelList(paramMdl, placeImgDataDir);
    }

    /**
     * <br>[機  能] フォームに入力した場所画像データをテンポラリディレクトリに保存
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv090ParamModel
     * @param plaImgDataDir 場所/地図画像データ保存用テンポラリディレクトリ
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void savePlaceTempFilesData(Rsv090ParamModel paramMdl,
                                        String plaImgDataDir)

    throws IOToolsException {

        if (paramMdl.getPlaceFormList().isEmpty()) {
            return;
        }

        int cnt = 1;
        for (Rsv090PlaceForm rsv090PlaceBean : paramMdl.getPlaceFormList()) {
            saveObjFile(rsv090PlaceBean, plaImgDataDir, cnt);
            cnt++;
        }
    }

    /**
     * <br>[機  能] 施設の画像をテンポラリディレクトリにセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param appRoot アプリケーションルートパス
     * @param imgDataList 施設画像データ
     * @param imgKbn 画像区分
     * @param paramMdl Rsv090ParamModel
     * @param domain ドメイン
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    private void __setTempImgData(String appRoot,
                                   List <Rsv090BinfModel> imgDataList,
                                   int imgKbn,
                                   Rsv090ParamModel paramMdl,
                                   String domain)
    throws TempFileException, IOException, IOToolsException {

        //施設画像添付ファイルをテンポラリディレクトリへ移動する
        String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
        if (imgDataList != null && imgDataList.size() > 0) {
            List <Rsv090BinfModel> tmpFileList = imgDataList;
            String[] binSids = new String[tmpFileList.size()];
            CommonBiz cmnBiz = new CommonBiz();

            String dspBinSid = "0";
            //バイナリSIDの取得
            for (int i = 0; i < tmpFileList.size(); i++) {
                binSids[i] = String.valueOf(tmpFileList.get(i).getBinSid());

                if (tmpFileList.get(i).getImgDspKbn() == GSConstReserve.IMG_DSP_KBN) {
                    //週間・日間表示用
                    dspBinSid = binSids[i];
                }
            }

            //取得したバイナリSIDからバイナリ情報を取得
            List<CmnBinfModel> binList = cmnBiz.getBinInfo(con_, binSids, domain);

            //週間・日間施設画像(編集画面用)
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            String editTempDir = temp.getTempPath(reqMdl_,
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                    GSConstReserve.TEMP_IMG_SISETU_EDIT);


            String tempDir = "";
            //施設画像
            if (imgKbn == GSConstReserve.TEMP_IMG_KBN_SISETU) {
                tempDir = temp.getTempPath(reqMdl_,
                        GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                        GSConstReserve.TEMP_IMG_SISETU_UPLOAD);

            //場所画像
            } else if (imgKbn == GSConstReserve.TEMP_IMG_KBN_PLACE) {
                tempDir = temp.getTempPath(reqMdl_,
                        GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                        GSConstReserve.TEMP_IMG_PLACE_UPLOAD);
            }

            int fileNum = 1;
            for (CmnBinfModel binData : binList) {

                if (binData.getBinSid() == Long.parseLong(dspBinSid)) {
                    //週間・日間編集画面用
                    cmnBiz.saveTempFile(dateStr, binData, appRoot, editTempDir, fileNum);
                }
                cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
                fileNum++;
            }
        }
    }

    /**
     * <br>[機  能] 画面に表示する添付ファイル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv090ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @return 画面表示用添付ファイル一覧
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public List<LabelValueBean> getTempFileLabelList(Rsv090ParamModel paramMdl, String tempDir)
    throws IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List < String > fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        List<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            String[] fileNames = new String[fileList.size()];

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                String name = fileName.replaceFirst(
                        GSConstCommon.ENDSTR_OBJFILE, GSConstCommon.ENDSTR_SAVEFILE);
                long atattiSize = new File(tempDir, name).length();
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(
                        CommonBiz.addAtattiSizeForName(fMdl.getFileName(), atattiSize), value[0]));

                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
                log__.debug("ファイルサイズ(byte) =" + fMdl.getAtattiSize());

                fileNames[i] = fMdl.getSaveFileName();
            }
            paramMdl.setRsv090selectFiles(fileNames);
        }

        return fileLblList;
    }

    /**
     * <br>[機  能] DBを元に場所画像データを作成しテンポラリに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv090ParamModel
     * @param placeImgDir 場所画像ディレクトリ
     * @param placeImgDataDir 場所画像データ保存先ディレクトリ
     * @param rsv090DspModel 画面表示用モデル
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public void setPlaceImgData(Rsv090ParamModel paramMdl,
                                 String placeImgDir,
                                 String placeImgDataDir,
                                 Rsv090DspModel rsv090DspModel)
    throws IOToolsException {

        //場所画像ディレクトリにあるファイル名称を取得
        List < String > fileList = IOTools.getFileNames(placeImgDir);

        Rsv090PlaceForm rsv090PlaceBean = null;

        if (fileList != null) {

            int cmtCnt = 1;

            for (int i = 0; i < fileList.size(); i++) {
                rsv090PlaceBean = new Rsv090PlaceForm();

                //ファイル名を取得
                String fileName = fileList.get(i);

                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                String name = fileName.replaceFirst(
                        GSConstCommon.ENDSTR_OBJFILE, GSConstCommon.ENDSTR_SAVEFILE);
                long atattiSize = new File(placeImgDir, name).length();
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(placeImgDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

                //ラベル
                rsv090PlaceBean.setRsv090PlaceFileLabel(
                        CommonBiz.addAtattiSizeForName(fMdl.getFileName(), atattiSize));

                //バリュー
                rsv090PlaceBean.setRsv090PlaceFileValue(value[0]);

                //画像コメント
                if (cmtCnt == 1) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt1(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt1Df()));
                } else if (cmtCnt == 2) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt2(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt2Df()));
                } else if (cmtCnt == 3) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt3(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt3Df()));
                } else if (cmtCnt == 4) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt4(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt4Df()));
                } else if (cmtCnt == 5) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt5(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt5Df()));
                } else if (cmtCnt == 6) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt6(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt6Df()));
                } else if (cmtCnt == 7) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt7(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt7Df()));
                } else if (cmtCnt == 8) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt8(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt8Df()));
                } else if (cmtCnt == 9) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt9(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt9Df()));
                } else if (cmtCnt == 10) {
                    rsv090PlaceBean.setRsv090PlaceFileComment(
                            NullDefault.getString(rsv090DspModel.getRsdImgCmt10(), ""));
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(rsv090DspModel.getRsdImgCmt10Df()));
                }

                //場所画像データ保存
                saveObjFile(rsv090PlaceBean, placeImgDataDir, cmtCnt);
                cmtCnt++;

                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
                log__.debug("ファイルサイズ(byte) =" + fMdl.getAtattiSize());

            }
        }
    }

    /**
     * <br>[機  能] テンポラリに保存した場所画像データをフォームにセット
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv090ParamModel
     * @param placeImgDataDir 場所画像データディレクトリ
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public void getPlaceTempFileLabelList(Rsv090ParamModel paramMdl,
                                           String placeImgDataDir)
    throws IOToolsException {

        //場所画像データディレクトリにあるファイル名称を取得
        List < String > placeImgList = IOTools.getFileNames(placeImgDataDir);
        if (placeImgList == null) {
            return;
        }

        int count = 1;
        for (int i = 0; i < placeImgList.size(); i++) {
            
            Rsv090PlaceForm placeImageData = getPlaceImgData(placeImgDataDir, count);

            //ラベル
            paramMdl.setPlaceFileLabel(i, placeImageData.getRsv090PlaceFileLabel());

            //バリュー
            paramMdl.setPlaceFileValue(i, placeImageData.getRsv090PlaceFileValue());

            //場所画像コメント
            paramMdl.setPlaceFileComment(i, placeImageData.getRsv090PlaceFileComment());

            //場所画像コメント表示区分
            paramMdl.setPlaceFileCommentDspKbn(i, placeImageData.getRsv090PlaceFileCommentDspKbn());

            count++;
        }
    }

    /**
     * <br>[機  能] 施設グループコンボを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv090ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setSisetuGrpCombo(Rsv090ParamModel paramMdl) throws SQLException {

        String procMode = paramMdl.getRsv090ProcMode();

        log__.debug("***Rsv090procMode = " + procMode);

        //編集モードの場合は施設コンボを作成
        if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
            //グループコンボ生成
            RsvSisGrpDao dao = new RsvSisGrpDao(con_);
            ArrayList<RsvSisGrpModel> grpList = new ArrayList<RsvSisGrpModel>();

            //管理者
//            if (admFlg) {
//                log__.debug("管理者なので全データ取得");
                grpList = dao.selectAllGroupData();
            //管理者以外
//            } else {
//                log__.debug("管理者ではないので修正可能なデータのみ取得");
//                BaseUserModel usrMdl = _getSessionUserModel(req_);
//                grpList = dao.selectGrpListNotAdmin(usrMdl.getUsrsid());
//            }

            ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
            for (RsvSisGrpModel mdl : grpList) {
                labelList.add(
                        new LabelValueBean(mdl.getRsgName(),
                                String.valueOf(mdl.getRsgSid())));
            }
            if (!labelList.isEmpty()) {
                paramMdl.setRsv090RsvGrpLabelList(labelList);
            }
        }
    }

    /**
     * <br>[機  能] 画像を別のテンポラリディレクトリに移動させる
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param oldDir 保存元ディレクトリ
     * @param newDir 保存先ディレクトリ
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 添付ファイルの操作に失敗
     */
    public void copyImgOthTemp(String oldDir,
                                String newDir)
    throws IOToolsException, IOException {

        //保存元のファイル
        log__.debug("******保存元ディレクトリパス:" + oldDir);
        log__.debug("******保存先ディレクトリパス:" + newDir);

        //ディレクトリ存在チェック(なければ作成)
        IOTools.isDirCheck(newDir, true);

        File oldFileDir = new File(oldDir);
        File newFileDir = new File(newDir);

        //添付ファイルをコピー
        IOTools.copyDir(oldFileDir, newFileDir);

    }

    /**
     * <br>[機  能] Rsv090PlaceFormをオブジェクトファイルに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param placeData 場所画像データ保存用ディレクトリ
     * @param placeImgDir 場所画像データ保存ディレクトリ
     * @param index インデックス
     * @throws IOToolsException IOエラー
     */
    public static void saveObjFile(
        Rsv090PlaceForm placeData,
        String placeImgDir,
        int index) throws IOToolsException {

        //ディレクトリ存在チェック(なければ作成)
        IOTools.isDirCheck(placeImgDir, true);

        //ファイル保存
        ObjectFile objFile = new ObjectFile(placeImgDir, GSConstReserve.SAVE_FILENAME + index);
        objFile.save(placeData);
    }

    /**
     * <br>[機  能] オブジェクトファイルから場所画像データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param filePath ファイルパス
     * @param index インデックス
     * @return Rsv090PlaceForm
     * @throws IOToolsException IOエラー
     */
    public Rsv090PlaceForm getPlaceImgData(
        String filePath, int index) throws IOToolsException {

        Rsv090PlaceForm bean = new Rsv090PlaceForm();

        //オブジェクトファイルを取得
        if (!IOTools.isFileCheck(filePath, GSConstReserve.SAVE_FILENAME + index, false)) {
            return bean;
        }

        ObjectFile objFile = new ObjectFile(filePath, GSConstReserve.SAVE_FILENAME + index);
        Object paramMdlData = objFile.load();
        if (paramMdlData == null) {
            return bean;
        }
        
        //場所画像データ
        return (Rsv090PlaceForm) paramMdlData;
    }
    
    /**
     * <br>[機  能] 入力内容をDBに反映する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv090ParamModel
     * @param ctrl 採番用コネクション
     * @param appRoot アプリケーションルートパス
     * @param tempDirSisetu テンポラリディレクトリパス(施設画像用)
     * @param weekDayDsptempDir テンポラリディレクトリパス(施設週間・日間画像用)
     * @param tempDirPlace  テンポラリディレクトリパス(場所画像用)
     * @param plaTempDataDir テンポラリディレクトリパス(場所画像データ用)
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void updateSisetuData(Rsv090ParamModel paramMdl,
                                  MlCountMtController ctrl,
                                  String appRoot,
                                  String tempDirSisetu,
                                  String weekDayDsptempDir,
                                  String tempDirPlace,
                                  String plaTempDataDir)
        throws SQLException, TempFileException, IOToolsException {

        int sisetuSid = -1;
        UDate now = new UDate();
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();

        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        RsvUsedDataBiz usedDataBiz = new RsvUsedDataBiz(con_);

        //新規
        if (paramMdl.getRsv090ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)) {

            log__.debug("新規モード");

            //施設SID採番
            sisetuSid =
                (int) ctrl.getSaibanNumber(
                        GSConstReserve.SBNSID_RESERVE,
                        GSConstReserve.SBNSID_SUB_SISETU,
                        usrSid);

            RsvSisDataModel dataParam = new RsvSisDataModel();
            dataParam.setRsgSid(paramMdl.getRsv090EditGrpSid());
            dataParam.setRsdSid(sisetuSid);
            dataParam.setRsdId(NullDefault.getString(paramMdl.getRsv090SisetuId(), ""));
            dataParam.setRsdName(NullDefault.getString(paramMdl.getRsv090SisetuName(), ""));
            dataParam.setRsdSnum(NullDefault.getString(paramMdl.getRsv090SisanKanri(), ""));
            dataParam.setRsdProp1(NullDefault.getString(paramMdl.getRsv090Prop1Value(), ""));
            dataParam.setRsdProp2(NullDefault.getString(paramMdl.getRsv090Prop2Value(), ""));
            dataParam.setRsdProp3(NullDefault.getString(paramMdl.getRsv090Prop3Value(), ""));
            dataParam.setRsdProp4(NullDefault.getString(paramMdl.getRsv090Prop4Value(), ""));
            dataParam.setRsdProp5(NullDefault.getString(paramMdl.getRsv090Prop5Value(), ""));
            dataParam.setRsdProp6(NullDefault.getString(paramMdl.getRsv090Prop6Value(), ""));
            dataParam.setRsdProp7(NullDefault.getString(paramMdl.getRsv090Prop7Value(), ""));
            dataParam.setRsdProp8Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdProp9Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdProp10Df(GSConstReserve.SISETU_DATA_DSP_OFF);

            //場所コメント
            dataParam.setRsdPlaCmt(NullDefault.getString(paramMdl.getRsv090PlaceComment(), ""));

            //場所画像コメント
            dataParam.setRsdImgCmt1(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 1).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt2(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 2).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt3(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 3).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt4(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 4).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt5(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 5).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt6(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 6).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt7(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 7).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt8(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 8).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt9(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 9).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt10(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 10).getRsv090PlaceFileComment(), ""));

            dataParam.setRsdBiko(NullDefault.getString(paramMdl.getRsv090Biko(), ""));
            dataParam.setRsdAuid(usrSid);
            dataParam.setRsdAdate(now);
            dataParam.setRsdEuid(usrSid);
            dataParam.setRsdEdate(now);


            //日間・週間施設情報表示区分設定
            dataParam.setRsdIdDf(Integer.parseInt(paramMdl.getRsv090SisetuIdDspKbn()));
            dataParam.setRsdSnumDf(Integer.parseInt(paramMdl.getRsv090SisanKanriDspKbn()));
            dataParam.setRsdProp1Df(Integer.parseInt(paramMdl.getRsv090Prop1ValueDspKbn()));
            dataParam.setRsdProp2Df(Integer.parseInt(paramMdl.getRsv090Prop2ValueDspKbn()));
            dataParam.setRsdProp3Df(Integer.parseInt(paramMdl.getRsv090Prop3ValueDspKbn()));
            dataParam.setRsdProp4Df(Integer.parseInt(paramMdl.getRsv090Prop4ValueDspKbn()));
            dataParam.setRsdProp5Df(Integer.parseInt(paramMdl.getRsv090Prop5ValueDspKbn()));
            dataParam.setRsdProp6Df(Integer.parseInt(paramMdl.getRsv090Prop6ValueDspKbn()));
            dataParam.setRsdProp7Df(Integer.parseInt(paramMdl.getRsv090Prop7ValueDspKbn()));
            dataParam.setRsdProp8Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdProp9Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdProp10Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdImgCmt1Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 1).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt2Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 2).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt3Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 3).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt4Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 4).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt5Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 5).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt6Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 6).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt7Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 7).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt8Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 8).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt9Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 9).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt10Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 10).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));

            dataParam.setRsdBikoDf(Integer.parseInt(paramMdl.getRsv090BikoDspKbn()));
            dataParam.setRsdPlaCmtDf(Integer.parseInt(paramMdl.getRsv090PlaceCommentDspKbn()));
            dataParam.setRsdImgDf(Integer.parseInt(paramMdl.getRsv090SisetuImgDefoDspKbn()));

            if (__canEditApprKbn(paramMdl)) {
                dataParam.setRsdApprKbn(paramMdl.getRsv090apprKbn());
            } else {
                dataParam.setRsdApprKbn(GSConstReserve.RSD_APPR_KBN_NOSET);
            }
            dataParam.setRsdApprKbnDf(Integer.parseInt(paramMdl.getRsv090apprKbnDspKbn()));


            //情報を登録
            dataDao.insertNewSisetu(dataParam);

            paramMdl.setRsv090EditSisetuSid(sisetuSid);

            //週間・日間表示用画像
            __setWeekDayDspSisetuTempData(weekDayDsptempDir, appRoot, ctrl, usrSid,
                                          now, GSConstReserve.TEMP_IMG_KBN_SISETU, paramMdl);

            //施設用画像
            __setSisetuTempData(tempDirSisetu, appRoot, ctrl, usrSid,
                                now, GSConstReserve.TEMP_IMG_KBN_SISETU, paramMdl);

            //場所用画像
            __setSisetuTempData(tempDirPlace, appRoot, ctrl, usrSid,
                                now, GSConstReserve.TEMP_IMG_KBN_PLACE, paramMdl);

            //施設情報のデータ使用量を登録
            usedDataBiz.insertRsvDataSize(sisetuSid, true);

        //編集
        } else if (paramMdl.getRsv090ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {

            log__.debug("編集モード");

            sisetuSid = paramMdl.getRsv090EditSisetuSid();

            //施設情報のデータ使用量を登録(更新前データのデータ使用量を減算)
            usedDataBiz.insertRsvDataSize(sisetuSid, false);

            RsvSisDataModel dataParam = new RsvSisDataModel();
            dataParam.setRsgSid(paramMdl.getRsv090EditGrpSid());
            dataParam.setRsdSid(sisetuSid);
            dataParam.setRsdId(NullDefault.getString(paramMdl.getRsv090SisetuId(), ""));
            dataParam.setRsdName(NullDefault.getString(paramMdl.getRsv090SisetuName(), ""));
            dataParam.setRsdSnum(NullDefault.getString(paramMdl.getRsv090SisanKanri(), ""));
            dataParam.setRsdProp1(NullDefault.getString(paramMdl.getRsv090Prop1Value(), ""));
            dataParam.setRsdProp2(NullDefault.getString(paramMdl.getRsv090Prop2Value(), ""));
            dataParam.setRsdProp3(NullDefault.getString(paramMdl.getRsv090Prop3Value(), ""));
            dataParam.setRsdProp4(NullDefault.getString(paramMdl.getRsv090Prop4Value(), ""));
            dataParam.setRsdProp5(NullDefault.getString(paramMdl.getRsv090Prop5Value(), ""));
            dataParam.setRsdProp6(NullDefault.getString(paramMdl.getRsv090Prop6Value(), ""));
            dataParam.setRsdProp7(NullDefault.getString(paramMdl.getRsv090Prop7Value(), ""));
            dataParam.setRsdProp8Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdProp9Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdProp10Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            //場所コメント
            dataParam.setRsdPlaCmt(NullDefault.getString(paramMdl.getRsv090PlaceComment(), ""));

            //場所画像コメント
            dataParam.setRsdImgCmt1(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 1).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt2(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 2).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt3(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 3).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt4(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 4).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt5(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 5).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt6(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 6).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt7(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 7).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt8(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 8).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt9(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 9).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdImgCmt10(
                    NullDefault.getString(
                            getPlaceImgData(plaTempDataDir, 10).getRsv090PlaceFileComment(), ""));
            dataParam.setRsdBiko(NullDefault.getString(paramMdl.getRsv090Biko(), ""));

            if (__canEditApprKbn(paramMdl)) {
                dataParam.setRsdApprKbn(paramMdl.getRsv090apprKbn());
            } else {
                dataParam.setRsdApprKbn(-1);
            }
            dataParam.setRsdApprKbnDf(Integer.parseInt(paramMdl.getRsv090apprKbnDspKbn()));
            dataParam.setRsdEuid(usrSid);
            dataParam.setRsdEdate(now);

            //日間・週間施設情報表示区分設定
            dataParam.setRsdIdDf(Integer.parseInt(paramMdl.getRsv090SisetuIdDspKbn()));
            dataParam.setRsdSnumDf(Integer.parseInt(paramMdl.getRsv090SisanKanriDspKbn()));
            dataParam.setRsdProp1Df(Integer.parseInt(paramMdl.getRsv090Prop1ValueDspKbn()));
            dataParam.setRsdProp2Df(Integer.parseInt(paramMdl.getRsv090Prop2ValueDspKbn()));
            dataParam.setRsdProp3Df(Integer.parseInt(paramMdl.getRsv090Prop3ValueDspKbn()));
            dataParam.setRsdProp4Df(Integer.parseInt(paramMdl.getRsv090Prop4ValueDspKbn()));
            dataParam.setRsdProp5Df(Integer.parseInt(paramMdl.getRsv090Prop5ValueDspKbn()));
            dataParam.setRsdProp6Df(Integer.parseInt(paramMdl.getRsv090Prop6ValueDspKbn()));
            dataParam.setRsdProp7Df(Integer.parseInt(paramMdl.getRsv090Prop7ValueDspKbn()));
            dataParam.setRsdProp8Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdProp9Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdProp10Df(GSConstReserve.SISETU_DATA_DSP_OFF);
            dataParam.setRsdImgCmt1Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 1).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt2Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 2).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt3Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 3).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt4Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 4).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt5Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 5).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt6Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 6).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt7Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 7).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt8Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 8).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt9Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 9).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));
            dataParam.setRsdImgCmt10Df(
                    NullDefault.getInt(
                            getPlaceImgData(plaTempDataDir, 10).getRsv090PlaceFileCommentDspKbn(),
                            GSConstReserve.SISETU_DATA_DSP_OFF));

            dataParam.setRsdBikoDf(Integer.parseInt(paramMdl.getRsv090BikoDspKbn()));
            dataParam.setRsdPlaCmtDf(Integer.parseInt(paramMdl.getRsv090PlaceCommentDspKbn()));
            dataParam.setRsdImgDf(Integer.parseInt(paramMdl.getRsv090SisetuImgDefoDspKbn()));

            paramMdl.setRsv090EditSisetuSid(sisetuSid);

            RsvBinDao rsvBinDao = new RsvBinDao(con_);
            //ソート順編集判定のために現在のデータを取得して情報を更新
            Rsv090DspModel nowMdl = dataDao.getSisetuData(sisetuSid);
            //施設グループを変更した際は変更先の並び順を設定しなおす
            if (nowMdl.getRsgSid() != dataParam.getRsgSid()) {

                ArrayList<RsvSisDataModel> rsdMdlList
                = dataDao.selectSisetuList(dataParam.getRsgSid());
                int newSort = 1;
                for (RsvSisDataModel rsdMdl:rsdMdlList) {
                    rsdMdl.setRsdSort(newSort);
                    dataDao.updateSisetuData(rsdMdl);
                    newSort++;
                }
                //新しいソート順を割り振りグループを移動させる
                dataParam.setRsdSort(newSort);
            } else {
                dataParam.setRsdSort(nowMdl.getRsdSort());
            }

            //データの編集を行う
            dataDao.updateSisetuData(dataParam);

            //バイナリー情報の削除更新
            rsvBinDao.deleteBinfForSisetu(paramMdl.getRsv090EditSisetuSid(),
                    GSConstReserve.TEMP_IMG_KBN_SISETU);
            //RSV_BINテーブルの添付データを削除(施設/設備)
            rsvBinDao.deleteSisetuBin(paramMdl.getRsv090EditSisetuSid(),
                    GSConstReserve.TEMP_IMG_KBN_SISETU);

            //週間・日間表示用画像
            __editWeekDaySisetuTempData(weekDayDsptempDir, appRoot, ctrl, usrSid,
                                          now, GSConstReserve.TEMP_IMG_KBN_SISETU, paramMdl);

            //施設画像
            __editSisetuTempData(tempDirSisetu, appRoot, ctrl, usrSid,
                    now, GSConstReserve.TEMP_IMG_KBN_SISETU, paramMdl);

            //場所画像
            //バイナリー情報の削除更新
            rsvBinDao.deleteBinfForSisetu(paramMdl.getRsv090EditSisetuSid(),
                                          GSConstReserve.TEMP_IMG_KBN_PLACE);
            //RSV_BINテーブルの添付データを削除(場所/地図)
            rsvBinDao.deleteSisetuBin(paramMdl.getRsv090EditSisetuSid(),
                                      GSConstReserve.TEMP_IMG_KBN_PLACE);
            __editSisetuTempData(tempDirPlace, appRoot, ctrl, usrSid,
                    now, GSConstReserve.TEMP_IMG_KBN_PLACE, paramMdl);

            //施設情報のデータ使用量を登録
            usedDataBiz.insertRsvDataSize(sisetuSid, true);
        }
    }
    
    /**
     * <br>[機  能] 施設添付情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @param ctrl 採番コントローラ
     * @param usrSid ユーザSID
     * @param now 現在日付
     * @param imgKbn 画像区分
     * @param paramMdl Rsv090ParamModel
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    private void __setSisetuTempData(String tempDir,
                                      String appRoot,
                                      MlCountMtController ctrl,
                                      int usrSid,
                                      UDate now,
                                      int imgKbn,
                                      Rsv090ParamModel paramMdl)
    throws SQLException, TempFileException, IOToolsException {

        //バイナリー情報の登録
        CommonBiz cmnBiz = new CommonBiz();
        List<String> binSids = cmnBiz.insertBinInfo(con_, tempDir, appRoot,
                ctrl, usrSid, now);

        //施設添付情報の登録
        RsvBinDao rsvBinDao = new RsvBinDao(con_);
        rsvBinDao.insertRsvBinData(paramMdl.getRsv090EditSisetuSid(),
                                   binSids,
                                   imgKbn,
                                   GSConstReserve.IMG_NOT_DSP_KBN);
    }

    /**
     * <br>[機  能] 週間・日間に表示する施設添付情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @param ctrl 採番コントローラ
     * @param usrSid ユーザSID
     * @param now 現在日付
     * @param imgKbn 画像区分
     * @param paramMdl Rsv090ParamModel
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    private void __setWeekDayDspSisetuTempData(String tempDir,
                                      String appRoot,
                                      MlCountMtController ctrl,
                                      int usrSid,
                                      UDate now,
                                      int imgKbn,
                                      Rsv090ParamModel paramMdl)
    throws SQLException, TempFileException, IOToolsException {

        //バイナリー情報の登録
        CommonBiz cmnBiz = new CommonBiz();
        List <String> binSid = cmnBiz.insertBinInfo(con_, tempDir, appRoot,
                ctrl, usrSid, now);

        //施設添付情報の登録
        RsvBinDao rsvBinDao = new RsvBinDao(con_);
        rsvBinDao.insertRsvBinData(paramMdl.getRsv090EditSisetuSid(),
                                   binSid,
                                   imgKbn,
                                   GSConstReserve.IMG_DSP_KBN);
    }

    /**
     * <br>[機  能] 施設添付情報を編集する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @param ctrl 採番コントローラ
     * @param usrSid ユーザSID
     * @param now 現在日付
     * @param imgKbn 画像区分
     * @param paramMdl Rsv090ParamModel
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __editSisetuTempData(String tempDir,
                                      String appRoot,
                                      MlCountMtController ctrl,
                                      int usrSid,
                                      UDate now,
                                      int imgKbn,
                                      Rsv090ParamModel paramMdl)
                                      throws SQLException, TempFileException {


        //バイナリー情報の登録
        CommonBiz cmnBiz = new CommonBiz();
        List <String> binSid = cmnBiz.insertBinInfo(con_, tempDir, appRoot,
                ctrl, usrSid, now);

        //施設添付情報の登録
        RsvBinDao rsvBinDao = new RsvBinDao(con_);
        rsvBinDao.insertRsvBinData(paramMdl.getRsv090EditSisetuSid(),
                                   binSid,
                                   imgKbn,
                                   GSConstReserve.IMG_NOT_DSP_KBN);
    }

    /**
     * <br>[機  能] 週間・日間に表示する施設添付情報を編集する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @param ctrl 採番コントローラ
     * @param usrSid ユーザSID
     * @param now 現在日付
     * @param imgKbn 画像区分
     * @param paramMdl Rsv090ParamModel
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __editWeekDaySisetuTempData(String tempDir,
                                      String appRoot,
                                      MlCountMtController ctrl,
                                      int usrSid,
                                      UDate now,
                                      int imgKbn,
                                      Rsv090ParamModel paramMdl)
                                      throws SQLException, TempFileException {


        //バイナリー情報の登録
        CommonBiz cmnBiz = new CommonBiz();
        List <String> binSid = cmnBiz.insertBinInfo(con_, tempDir, appRoot,
                ctrl, usrSid, now);

        //施設添付情報の登録
        RsvBinDao rsvBinDao = new RsvBinDao(con_);
        rsvBinDao.insertRsvBinData(paramMdl.getRsv090EditSisetuSid(),
                                   binSid,
                                   imgKbn,
                                   GSConstReserve.IMG_DSP_KBN);


    }
    
    /**
     * <br>[機  能] 週間・日間で表示する画像を別のテンポラリディレクトリに移動させる
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv090ParamModel
     * @param sisetuTempDir 施設画像テンポラリディレクトリ
     * @param weekDayDsptempDir 週間・日間表示用画像テンポラリディレクトリ
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 添付ファイルの操作に失敗
     */
    public void setWeekDayImgOthTemp(Rsv090ParamModel paramMdl,
                                      GSTemporaryPathModel sisetuTempDir,
                                      GSTemporaryPathModel weekDayDsptempDir)
    throws IOToolsException, IOException {

        //週間・日間表示用画像ファイルのフルパス(オブジェクト)

        String dspPathObj = new GSTemporaryPathModel(sisetuTempDir,
                paramMdl.getRsv090SisetuImgDefoValue()
                  + GSConstCommon.ENDSTR_OBJFILE).getTempPath();
        dspPathObj = IOTools.replaceFileSep(dspPathObj);
        log__.debug("週間・日間表示用画像ファイルのフルパス(オブジェクト) = " + dspPathObj);

        //週間・日間表示用画像ファイルのフルパス(本体)
        String dspPathFile =  new GSTemporaryPathModel(sisetuTempDir,
                paramMdl.getRsv090SisetuImgDefoValue()
                           + GSConstCommon.ENDSTR_SAVEFILE).getTempPath();

        dspPathFile = IOTools.replaceFileSep(dspPathFile);
        log__.debug("週間・日間表示用画像ファイルのフルパス(本体) = " + dspPathFile);

        //保存元のファイル
        File exObjFile = new File(dspPathObj);
        File exRealFile = new File(dspPathFile);

        //保存先のファイル
        File dspObjFile = new File(
                new GSTemporaryPathModel(weekDayDsptempDir,
                        paramMdl.getRsv090SisetuImgDefoValue()
                                  + GSConstCommon.ENDSTR_OBJFILE).getTempPath());
        File dspRealFile = new File(
                new GSTemporaryPathModel(weekDayDsptempDir,
                        paramMdl.getRsv090SisetuImgDefoValue()
                                  + GSConstCommon.ENDSTR_SAVEFILE).getTempPath());

        //添付ファイルをコピー
        IOTools.copyBinFile(exObjFile, dspObjFile);
        IOTools.copyBinFile(exRealFile, dspRealFile);

        //保存元ファイルを削除
        IOTools.deleteFile(exObjFile);
        IOTools.deleteFile(exRealFile);
    }
    
    /**
     * <br>[機  能] [施設予約の承認]を設定可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv090ParamModel
     * @return true:設定可能 false:設定不可
     * @throws SQLException SQL実行時例外
     */
    private boolean __canEditApprKbn(Rsv090ParamModel paramMdl) throws SQLException {
        boolean result = false;

        int rsgSid = paramMdl.getRsv090EditGrpSid();
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);

        //施設グループに管理者が設定されているかを判定
        ArrayList<String> grpAdmUsers = grpDao.getDefaultAdmUser(rsgSid);
        if (grpAdmUsers != null && !grpAdmUsers.isEmpty()) {
            //施設ごとに施設予約の承認設定が可能かを判定
            RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
            result = !rsvCmnBiz.isApprSisGroup(con_, rsgSid);
        }

        return result;
    }
    
    /**
     * <br>[機  能] 場所画像データを設定し保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param oldDir 保存元ディレクトリパス
     * @param newDir 保存先ディレクトリパス
     * @param plaImgDir 場所・地図画像ディレクトリ
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setSavePlaImgData(String oldDir, String newDir, String plaImgDir)
    throws IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List < String > plaImgList = IOTools.getFileNames(plaImgDir);

        //画面に表示するファイルのリストを作成
        Rsv090PlaceForm rsv090PlaceBean = null;

        if (plaImgList != null) {

            int cmtCnt = 1;

            for (int i = 0; i < plaImgList.size(); i++) {
                rsv090PlaceBean = new Rsv090PlaceForm();

                //ファイル名を取得
                String fileName = plaImgList.get(i);

                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                String name = fileName.replaceFirst(
                        GSConstCommon.ENDSTR_OBJFILE, GSConstCommon.ENDSTR_SAVEFILE);
                long atattiSize = new File(plaImgDir, name).length();
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(plaImgDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;

                if (getPlaceImgData(oldDir, cmtCnt).getRsv090PlaceFileLabel() == null
                        || getPlaceImgData(oldDir, cmtCnt).getRsv090PlaceFileValue() == null) {

                    //ラベル
                    rsv090PlaceBean.setRsv090PlaceFileLabel(
                            CommonBiz.addAtattiSizeForName(fMdl.getFileName(), atattiSize));
                    
                    //バリュー
                    rsv090PlaceBean.setRsv090PlaceFileValue(value[0]);

                    //コメント
                    rsv090PlaceBean.setRsv090PlaceFileComment("");

                    //コメント表示区分
                    rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                            String.valueOf(GSConstReserve.SISETU_DATA_DSP_OFF));

                } else {
                    //場所画像のデータと添付した場所画像データが一致するかチェック
                    if (getPlaceImgData(oldDir, cmtCnt).getRsv090PlaceFileLabel().equals(
                        CommonBiz.addAtattiSizeForName(fMdl.getFileName(), atattiSize))
                     && getPlaceImgData(oldDir, cmtCnt).getRsv090PlaceFileValue().equals(
                        value[0])) {

                        //ラベル
                        rsv090PlaceBean.setRsv090PlaceFileLabel(
                                getPlaceImgData(oldDir, cmtCnt).getRsv090PlaceFileLabel());

                        //バリュー
                        rsv090PlaceBean.setRsv090PlaceFileValue(
                                getPlaceImgData(oldDir, cmtCnt).getRsv090PlaceFileValue());

                        //コメント
                        rsv090PlaceBean.setRsv090PlaceFileComment(
                                getPlaceImgData(oldDir, cmtCnt).getRsv090PlaceFileComment());

                        //コメント表示区分
                        rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                                getPlaceImgData(oldDir, cmtCnt).getRsv090PlaceFileCommentDspKbn());

                    } else {
                        //ラベル
                        rsv090PlaceBean.setRsv090PlaceFileLabel(
                                CommonBiz.addAtattiSizeForName(fMdl.getFileName(), atattiSize));

                        //バリュー
                        rsv090PlaceBean.setRsv090PlaceFileValue(value[0]);

                        //コメント
                        rsv090PlaceBean.setRsv090PlaceFileComment("");

                        //コメント表示区分
                        rsv090PlaceBean.setRsv090PlaceFileCommentDspKbn(
                                String.valueOf(GSConstReserve.SISETU_DATA_DSP_OFF));

                    }
                }

                //場所画像データ保存
                Rsv090Biz.saveObjFile(rsv090PlaceBean, newDir, cmtCnt);
                cmtCnt++;

                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
                log__.debug("ファイルサイズ(byte) =" + fMdl.getAtattiSize());

            }
        }
    }
    
    /**
     * <br>[機  能] 選択した場所画像データを削除し、
     *              削除しない場所画像データをテンポラリに保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv090ParamModel
     * @param tempPlaDataDir 場所画像用データ参照用ディレクトリ
     * @throws IOToolsException 添付ファイルの読み込みに失敗
     */
    public void detPlaceData(
            Rsv090ParamModel paramMdl, String tempPlaDataDir) throws IOToolsException {

        //テンポラリディレクトリにあるファイル名称を取得
        List <String> fileList = null;

        //削除実行によって参照するディレクトリを分ける
        fileList = IOTools.getFileNames(tempPlaDataDir);

        if (fileList == null) {
            return;
        }

        //削除するファイルナンバーを取得
        String strDelNum = paramMdl.getRsv090BinSid();
        String[] strDelAry = new String[] {strDelNum};
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        
        Map<String, String> commentMap = new HashMap<String, String>();
        for (Rsv090PlaceForm place : paramMdl.getPlace()) {
            commentMap.put(place.getRsv090PlaceFileValue(), place.getRsv090PlaceFileComment());
        }

        //削除する画像を除去する
        List<Rsv090PlaceForm> nowPlaceImgDataList = new ArrayList<Rsv090PlaceForm>();
        for (int m = 1; m <= fileList.size(); m++) {

            Rsv090PlaceForm placeImgData = getPlaceImgData(tempPlaDataDir, m);
            String fileValue = placeImgData.getRsv090PlaceFileValue();
            if (strDelNum.equals(fileValue)) {
                temp.deleteFile(strDelAry, reqMdl_,
                        GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                        GSConstReserve.TEMP_IMG_PLACE_UPLOAD);
                continue;
            }
            
            placeImgData.setRsv090PlaceFileComment(commentMap.get(fileValue));
            nowPlaceImgDataList.add(placeImgData);
        }
        
        //テンポラリディレクトリの削除を行う
        temp.deleteTempPath(reqMdl_,
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);

        //ディレクトリを作成する
        temp.createTempDir(reqMdl_,
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID,
                GSConstReserve.TEMP_IMG_PLACE_DATA);
        
        int count = 1;
        for (Rsv090PlaceForm nowPlaceImgData : nowPlaceImgDataList) {
            //保存
            Rsv090Biz.saveObjFile(nowPlaceImgData, tempPlaDataDir, count);
            count++;
        }
    }
}