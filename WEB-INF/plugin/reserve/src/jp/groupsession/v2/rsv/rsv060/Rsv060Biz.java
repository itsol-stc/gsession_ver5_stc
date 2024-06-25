package jp.groupsession.v2.rsv.rsv060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvUsedDataBiz;
import jp.groupsession.v2.rsv.dao.RsvAccessConfDao;
import jp.groupsession.v2.rsv.dao.RsvBinDao;
import jp.groupsession.v2.rsv.dao.RsvSisAdmDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvAccessConfModel;
import jp.groupsession.v2.rsv.model.RsvSisAdmModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設グループ登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv060Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv060Biz.class);
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
    public Rsv060Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 処理権限判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv060ParamModel
     * @return true:処理実行可 false:処理実行負荷
     * @throws SQLException SQL実行時例外
     */
    public boolean isPossibleToProcess(Rsv060ParamModel paramMdl)
        throws SQLException {

        //管理者である or この施設グループの管理者である
        return _isGroupEditAuthority(reqMdl_,
                                      con_,
                                      paramMdl.getRsv060ProcMode(),
                                      paramMdl.getRsv060EditGrpSid());
    }

    /**
     * <br>[機  能] 施設グループ編集可否フラグを取得する
     * <br>[解  説]
     * <br>[備  考] 施設グループの編集が可能か判定しフラグをセットする。
     *              (※下記のいずれかの条件を満たすか)
     *              1:管理者グループに所属している。
     *              2:いずれかの施設グループの管理者として設定されている。
     *              3:「権限設定をしない」となっている施設グループ1つでも存在する。
     *
     * @return true:編集可 false:編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean getGroupEditFlg() throws SQLException {

        return _isAllGroupEditAuthority(reqMdl_, con_);

    }

    /**
     * <br>[機  能] 画面表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv060ParamModel
     * @param ctrl 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    public void setGroupData(
        Rsv060ParamModel paramMdl, MlCountMtController ctrl) throws SQLException {

        boolean initFlg = paramMdl.isRsv060InitFlg();
        String procMode = paramMdl.getRsv060ProcMode();
        log__.debug("***Rsv060procMode = " + procMode);

        //初期表示 & 編集モード
        if (initFlg && procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {

            //施設グループデータ取得
            RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
            RsvSisGrpModel grpMdl = grpDao.select(paramMdl.getRsv060EditGrpSid());
            if (grpMdl != null) {
                paramMdl.setRsv060GrpId(grpMdl.getRsgId());
                paramMdl.setRsv060GrpName(grpMdl.getRsgName());
                paramMdl.setRsv060SelectedSisetuKbn(grpMdl.getRskSid());
                paramMdl.setRsv060GrpAdmKbn(grpMdl.getRsgAdmKbn());
                paramMdl.setRsv060apprKbn(grpMdl.getRsgApprKbn());
                paramMdl.setRsv060DataExists(true);

                //【権限設定を行う】に設定されている
                if (grpMdl.getRsgAdmKbn() == GSConstReserve.RSG_ADM_KBN_OK) {

                    //管理者ユーザ一覧取得
                    ArrayList<String> selectUser =
                        grpDao.getDefaultAdmUser(paramMdl.getRsv060EditGrpSid());

                    if (!selectUser.isEmpty()) {
                        String[] saveUserWork = new String[selectUser.size()];
                        for (int i = 0; i < selectUser.size(); i++) {
                            saveUserWork[i] = selectUser.get(i);
                        }
                        paramMdl.setSaveUser(saveUserWork);
                    }
                }


                if (grpMdl.getRsgAcsLimitKbn() == GSConstReserve.RSV_ACCESS_MODE_FREE) {
                    //アクセス権限を設定いない
                    paramMdl.setRsv060AccessDspFlg(false);

                } else {
                    //アクセス権限を設定している
                    paramMdl.setRsv060AccessDspFlg(true);
                    paramMdl.setRsv060AccessKbn(grpMdl.getRsgAcsLimitKbn());
                    RsvAccessConfDao accessDao = new RsvAccessConfDao(con_);
                    ArrayList<String> editList = new ArrayList<String>();
                    ArrayList<String> readList = new ArrayList<String>();
                    List<RsvAccessConfModel> acsConfList
                            = accessDao.getUsrData(paramMdl.getRsv060EditGrpSid(), -1);

                    for (int i = 0; i < acsConfList.size(); i++) {
                        if (acsConfList.get(i).getRacAuth()
                                == GSConstReserve.RSV_ACCESS_KBN_WRITE) {
                            if (acsConfList.get(i).getUsrSid() != -1) {
                                editList.add(String.valueOf(acsConfList.get(i).getUsrSid()));
                            }
                            if (acsConfList.get(i).getGrpSid() != -1) {
                                editList.add(String.valueOf(GSConstReserve.GROUP_HEADSTR
                                                        + acsConfList.get(i).getGrpSid()));
                            }

                        } else {
                            if (acsConfList.get(i).getUsrSid() != -1) {
                                readList.add(String.valueOf(acsConfList.get(i).getUsrSid()));
                            }
                            if (acsConfList.get(i).getGrpSid() != -1) {
                                readList.add(String.valueOf(GSConstReserve.GROUP_HEADSTR
                                                        + acsConfList.get(i).getGrpSid()));
                            }

                        }

                    }
                    String[] usrGrpSids = (String[]) editList.toArray(new String[editList.size()]);
                    String[] usrGrpSidsRead
                        = (String[]) readList.toArray(new String[readList.size()]);
                    paramMdl.setRsv060memberSid(usrGrpSids);
                    paramMdl.setRsv060memberSidRead(usrGrpSidsRead);

                }

            } else {
                paramMdl.setRsv060DataExists(false);
            }
        }

        if (paramMdl.getRsv060ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)
            && !initFlg) {

            RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
            RsvSisGrpModel grpMdl = grpDao.select(paramMdl.getRsv060EditGrpSid());
            if (grpMdl != null) {
                paramMdl.setRsv060DataExists(true);
            } else {
                paramMdl.setRsv060DataExists(false);
            }
        }

        if (paramMdl.getRsv060ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)
            && initFlg) {
            //新規登録かつ、画面の初期表示時に自動でグループIDを設定
            String grpId = __getNewGrpId(ctrl, con_);
            paramMdl.setRsv060GrpId(grpId);
        }

        //施設区分コンボ生成
        ArrayList<LabelValueBean> grpKbnList = _getGroupKbnComboList(con_);
        paramMdl.setRsv060SisetuLabelList(grpKbnList);
        if (paramMdl.getRsv060SelectedSisetuKbn() < 0) {
            LabelValueBean lbl = grpKbnList.get(0);
            paramMdl.setRsv060SelectedSisetuKbn(Integer.parseInt(lbl.getValue()));
        }

        //編集モード時は所属施設リストを取得
        if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
            RsvSisDataDao dataDao = new RsvSisDataDao(con_);
            ArrayList<RsvSisDataModel> rsv060SyozokuList =
                dataDao.selectGrpSisetuList(paramMdl.getRsv060EditGrpSid());
            paramMdl.setRsv060SyozokuList(rsv060SyozokuList);
        }

        if (paramMdl.getRsv060GrpAdmKbn() < 0) {
            //権限設定デフォルト値
            paramMdl.setRsv060GrpAdmKbn(GSConstReserve.RSG_ADM_KBN_OK);
        }
        //初期表示フラグOFF
        paramMdl.setRsv060InitFlg(false);

    }

    /**
     * <br>[機  能] 施設グループ削除処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv060ParamModel
     * @throws SQLException SQL実行時例外
     */
    public void doGrpDelete(Rsv060ParamModel paramMdl) throws SQLException {

        int grpSid = paramMdl.getRsv060EditGrpSid();

        //施設情報から削除対象の施設SIDを取得
        RsvSisDataDao dataDao = new RsvSisDataDao(con_);
        ArrayList<Integer> delRsdSidList = dataDao.getDeleteGrpSisetuList(grpSid);

        //削除対象の施設に予約チェックがあれば除外
        ArrayList<String> convKeyArray = new ArrayList<String>();
        String[] ikkatuKey = paramMdl.getRsvIkkatuTorokuKey();

        if (ikkatuKey != null && ikkatuKey.length > 0) {
            for (String key : ikkatuKey) {
                String keySid = key.substring(key.indexOf("-") + 1);
                for (int delKey : delRsdSidList) {
                    if (Integer.parseInt(keySid) != delKey) {
                        convKeyArray.add(key);
                    }
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

        //施設グループを削除
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
        RsvSisGrpModel grpParam = new RsvSisGrpModel();
        grpParam.setRsgSid(grpSid);
        grpDao.delete(grpParam);

        //施設グループ管理者を削除
        RsvSisAdmDao admDao = new RsvSisAdmDao(con_);
        admDao.delete(grpSid);

        //施設情報のデータ使用量を登録
        RsvUsedDataBiz usedDataBiz = new RsvUsedDataBiz(con_);
        usedDataBiz.insertRsvDataSize(delRsdSidList, false);

        //施設情報を削除
        dataDao.delete(grpSid);

        if (!delRsdSidList.isEmpty()) {
            //施設予約情報を削除
            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con_);
            ArrayList<Integer> rsySidList = yrkDao.getRsySidListSetSisSids(delRsdSidList);
            yrkDao.delete(delRsdSidList);
            //施設予約拡張別情報削除
            if (rsySidList != null && rsySidList.size() > 0) {
                RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con_);
                kyrkDao.delete(rsySidList);
            }

            for (int i = 0; i < delRsdSidList.size(); i++) {

                //バイナリ情報を削除
                //施設・設備
                __delSisetuTempData(GSConstReserve.TEMP_IMG_KBN_SISETU,
                                    delRsdSidList.get(i));

                //場所・地図
                __delSisetuTempData(GSConstReserve.TEMP_IMG_KBN_PLACE,
                                    delRsdSidList.get(i));
            }
        }
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
     * <br>[機  能] 処理対象の施設グループ名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv060ParamModel
     * @return grpName 施設グループ名称
     * @throws SQLException SQL実行時例外
     */
    public String getGroupName(Rsv060ParamModel paramMdl) throws SQLException {

        String grpName = "";

        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
        RsvSisGrpModel grpMdl = grpDao.select(paramMdl.getRsv060EditGrpSid());
        if (grpMdl != null) {
            grpName = grpMdl.getRsgName();
        }

        return grpName;
    }

    /**
     * <br>[機  能] ログメッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sidList SIDリスト
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return メッセージ
     * @throws SQLException SQL実行時例外
     */
    public String getLogMessage(String[] sidList, Connection con,
            RequestModel reqMdl) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = "[" + gsMsg.getMessage("reserve.53") + "]";

        CmnUsrmInfDao usrmdao = new CmnUsrmInfDao(con);
        CmnGroupmDao groupmDao = new CmnGroupmDao(con);
        boolean multiFlg = false;
        for (String sid : sidList) {
            if (sid.substring(0, 1).equals("G")) {
                CmnGroupmModel mdl = groupmDao.select(Integer.parseInt(sid.substring(1)));
                if (multiFlg) {
                    msg += "\r\n";
                } else {
                    multiFlg = true;
                }
                msg += mdl.getGrpName();
            } else {
                CmnUsrmInfModel mdl = usrmdao.select(Integer.parseInt(sid));
                if (multiFlg) {
                    msg += "\r\n";
                } else {
                    multiFlg = true;
                }
                msg += mdl.getUsiSei() + " " + mdl.getUsiMei();
            }
        }
        return msg;

    }

    /**
     * <br>[機  能] 削除用ログメッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sid SID
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return メッセージ
     * @throws SQLException SQL実行時例外
     */
    public String getDeleteLogMessage(int sid, Connection con,
            RequestModel reqMdl) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = "[" + gsMsg.getMessage("cmn.group.name") + "]";
        RsvSisGrpDao grpDao = new RsvSisGrpDao(con);
        RsvSisGrpModel mdl = grpDao.select(sid);
        msg += mdl.getRsgName();

        return msg;

    }
    
    /**
     * <br>[機  能] 入力内容をDBに反映する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv060ParamModel
     * @param ctrl 採番用コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateGrpData(Rsv060ParamModel paramMdl, MlCountMtController ctrl)
        throws SQLException {

        int grpSid = -1;
        UDate now = new UDate();
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();

        RsvSisGrpDao grpDao = new RsvSisGrpDao(con_);
        RsvSisAdmDao admDao = new RsvSisAdmDao(con_);
        RsvAccessConfDao accessDao = new RsvAccessConfDao(con_);

        String procMode = paramMdl.getRsv060ProcMode();

        if (procMode.equals(GSConstReserve.PROC_MODE_SINKI)) {

            log__.debug("新規モード");

            //施設グループSID採番
            grpSid =
                (int) ctrl.getSaibanNumber(
                        GSConstReserve.SBNSID_RESERVE,
                        GSConstReserve.SBNSID_SUB_GROUP,
                        usrSid);

            RsvSisGrpModel grpParam = new RsvSisGrpModel();
            grpParam.setRsgSid(grpSid);
            grpParam.setRskSid(paramMdl.getRsv060SelectedSisetuKbn());
            grpParam.setRsgId(paramMdl.getRsv060GrpId());
            grpParam.setRsgName(paramMdl.getRsv060GrpName());
            grpParam.setRsgAdmKbn(paramMdl.getRsv060GrpAdmKbn());
            grpParam.setRsgApprKbn(paramMdl.getRsv060apprKbn());
            grpParam.setRsgAuid(usrSid);
            grpParam.setRsgAdate(now);
            grpParam.setRsgEuid(usrSid);
            grpParam.setRsgEdate(now);
            if (paramMdl.isRsv060AccessDspFlg()) {
                grpParam.setRsgAcsLimitKbn(paramMdl.getRsv060AccessKbn());
            } else {
                grpParam.setRsgAcsLimitKbn(GSConstReserve.RSV_ACCESS_MODE_FREE);
            }

            //施設グループ登録
            grpDao.insertNewGrp(grpParam);

        } else if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {

            log__.debug("編集モード");

            grpSid = paramMdl.getRsv060EditGrpSid();

            RsvSisGrpModel grpParam = new RsvSisGrpModel();
            grpParam.setRsgSid(grpSid);
            grpParam.setRskSid(paramMdl.getRsv060SelectedSisetuKbn());
            grpParam.setRsgId(paramMdl.getRsv060GrpId());
            grpParam.setRsgName(paramMdl.getRsv060GrpName());
            grpParam.setRsgAdmKbn(paramMdl.getRsv060GrpAdmKbn());
            grpParam.setRsgApprKbn(paramMdl.getRsv060apprKbn());
            grpParam.setRsgEuid(usrSid);
            grpParam.setRsgEdate(now);
            if (paramMdl.isRsv060AccessDspFlg()) {
                grpParam.setRsgAcsLimitKbn(paramMdl.getRsv060AccessKbn());
            } else {
                grpParam.setRsgAcsLimitKbn(GSConstReserve.RSV_ACCESS_MODE_FREE);
            }

            //施設グループ更新
            grpDao.updateGrp(grpParam);

            //施設グループ管理者削除
            admDao.delete(grpSid);

            //アクセス権限を削除
            accessDao.deleteRsvGrpConf(grpSid);
        }

        //施設グループ管理者登録
        String[] saveUser = paramMdl.getSaveUser();
        int grpAdmKbn = paramMdl.getRsv060GrpAdmKbn();
        if (saveUser != null && saveUser.length > 0
                && grpAdmKbn == GSConstReserve.RSG_ADM_KBN_OK) {
            for (String strUsrGrpSid : saveUser) {
                //登録
                RsvSisAdmModel admParam =
                    __getAdmBaseModel(grpSid, usrSid, now);

                //グループ
                if (strUsrGrpSid.startsWith(GSConstReserve.GROUP_HEADSTR)) {
                    admParam.setUsrSid(-1);
                    admParam.setGrpSid(Integer.parseInt(strUsrGrpSid.substring(1)));
                } else {
                    //ユーザ
                    admParam.setUsrSid(Integer.parseInt(strUsrGrpSid));
                    admParam.setGrpSid(-1);
                }

                admDao.insert(admParam);
            }
        }


        if (paramMdl.isRsv060AccessDspFlg()) {
            //予約可能ユーザ登録
            String[] saveYoyakuUser = paramMdl.getRsv060memberSid();
            if (saveYoyakuUser != null && saveYoyakuUser.length > 0
                    && paramMdl.isRsv060AccessDspFlg()) {
                RsvAccessConfModel accessConfModel = null;

                for (String strUsrSid : saveYoyakuUser) {
                    //登録

                    if (StringUtil.isNullZeroString(strUsrSid)) {
                        continue;
                    }

                    if (strUsrSid.startsWith(GSConstReserve.GROUP_HEADSTR)) {
                        //グループ
                        accessConfModel = __getAccessConfModel(
                                                           grpSid,
                                                           -1,
                                                           Integer.parseInt(strUsrSid.substring(1)),
                                                           -1,
                                                           GSConstReserve.RSV_ACCESS_KBN_WRITE);

                    } else {
                        //ユーザ
                        accessConfModel = __getAccessConfModel(
                                                           grpSid,
                                                           -1,
                                                           -1,
                                                           Integer.parseInt(strUsrSid),
                                                           GSConstReserve.RSV_ACCESS_KBN_WRITE);
                    }

                    accessDao.insert(accessConfModel);
                }
            }


            //閲覧ユーザ登録
            String[] saveReadUser = paramMdl.getRsv060memberSidRead();
            if (saveReadUser != null && saveReadUser.length > 0
                    && paramMdl.isRsv060AccessDspFlg()) {
                RsvAccessConfModel accessConfModel = null;

                for (String strUsrSid : saveReadUser) {
                    //登録

                    if (StringUtil.isNullZeroString(strUsrSid)) {
                        continue;
                    }

                    if (strUsrSid.startsWith(GSConstReserve.GROUP_HEADSTR)) {
                        //グループ
                        accessConfModel = __getAccessConfModel(
                                                           grpSid,
                                                           -1,
                                                           Integer.parseInt(strUsrSid.substring(1)),
                                                           -1,
                                                           GSConstReserve.RSV_ACCESS_KBN_READ);

                    } else {
                        //ユーザ
                        accessConfModel = __getAccessConfModel(
                                                           grpSid,
                                                           -1,
                                                           -1,
                                                           Integer.parseInt(strUsrSid),
                                                           GSConstReserve.RSV_ACCESS_KBN_READ);
                    }

                    accessDao.insert(accessConfModel);
                }
            }
        }

    }

    /**
     * <br>[機  能] 
     * <br>[解  説]
     * <br>[備  考]
     * @param ctrl 採番コントローラ
     * @param con コネクション
     * @return ret 新規グループID
     * @throws SQLException 
     */
    private String __getNewGrpId(MlCountMtController ctrl, Connection con) throws SQLException {
    
        String ret = "";
        
        //グループID一覧の取得
        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con);
        List<String> grpIdList = rsgDao.getAllRsvGrpId();

        //グループID一覧が空の場合はグループIDが重複することがないため、SIDをグループIDとする
        int userSid = reqMdl_.getSmodel().getUsrsid();
        int grpIdInt = (int) ctrl.getSaibanNumberNotCommit(
            con, GSConstReserve.SBNSID_RESERVE, GSConstReserve.SBNSID_SUB_GROUP, userSid);
        if (grpIdList.isEmpty()) {
            return (String.valueOf(grpIdInt));
        }

        //既存のグループIDと重複しないグループIDを取得する
        while (true) {
            if (!grpIdList.contains(String.valueOf(grpIdInt))) {
                ret = String.valueOf(grpIdInt);
                break;
            }
            grpIdInt++;
        }

        return ret;
    }

    /**
     * <br>[機  能] 施設グループ管理者のベースを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @param usrSid セッションユーザSID
     * @param now システム日付
     * @return ret ベースモデル
     */
    private RsvSisAdmModel __getAdmBaseModel(int grpSid,
                                              int usrSid,
                                              UDate now) {

        RsvSisAdmModel ret = new RsvSisAdmModel();
        ret.setRsgSid(grpSid);
        ret.setRsaAuid(usrSid);
        ret.setRsaAdate(now);
        ret.setRsaEuid(usrSid);
        ret.setRsaEdate(now);
        return ret;
    }

    /**
     * <br>[機  能] 施設アクセス権限モデルを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsgSid 施設グループSID
     * @param rsdSid 施設SID
     * @param grpSid グループSID
     * @param usrSid ユーザSID
     * @param racAuth 権限種別
     * @return ret ベースモデル
     */
    private RsvAccessConfModel __getAccessConfModel(int rsgSid,
                                              int rsdSid,
                                              int grpSid,
                                              int usrSid,
                                              int racAuth) {

        RsvAccessConfModel model = new RsvAccessConfModel();
        model.setRsgSid(rsgSid);
        model.setRsdSid(rsdSid);
        model.setGrpSid(grpSid);
        model.setUsrSid(usrSid);
        model.setRacAuth(racAuth);

        return model;
    }

}