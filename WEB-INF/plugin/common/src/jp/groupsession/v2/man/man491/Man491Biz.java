package jp.groupsession.v2.man.man491;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.man440.CybCsvImportDao;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisKyrkDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.rsv070.Rsv070Model;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;

/**
 * <br>[機  能] サイボウズLiveデータ移行 マイカレンダーインポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man491Biz {
    /** リクエストモデル*/
    private RequestModel reqMdl__;
    /** コネクション*/
    private Connection con__;
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man491Biz.class);
    /**
     *
     * コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public Man491Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 描画設定処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータモデル
     * @param tempDir テンポラリディレクトリ
     * @throws IOToolsException IO例外
     */
    public void doDsp(Man491ParamModel param, String tempDir) throws IOToolsException {

        // TODO 自動生成されたメソッド・スタブ
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        List<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(fMdl.getFileName(), value[0]));
                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
            }
        }
        param.setMan491FileLabelList(fileLblList);
    }


    /**
     *
     * <br>[機  能] スケジュール一覧登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param schList スケジュール一覧
     * @param appRootPath ルートパス
     * @throws Exception 例外
     */
    public void importScheduleList(ArrayList<CybCsvSchModel> schList, String appRootPath)
            throws Exception {

        // 取得したスケジュール一覧から他の必要な情報を調査
        HashMap<String, Integer> unameMap = new HashMap<String, Integer>(); // ユーザ名マップ
        HashMap<String, Integer> snameMap = new HashMap<String, Integer>(); // 施設名マップ
        for (CybCsvSchModel mdl : schList) {
            // スケジュール一覧から使用されるユーザ名・施設名一覧を取得
            for (String uname : mdl.getUnames()) {
                unameMap.put(uname, Integer.valueOf(0)); // 初期値として 0 をセット
            }
            for (String sname : mdl.getSnames()) {
                snameMap.put(sname, Integer.valueOf(0)); // 初期値として 0 をセット
            }
        }

        // ログインユーザSID取得
        int loginUserSid = reqMdl__.getSmodel().getUsrsid();

        CybCsvImportDao dao = new CybCsvImportDao(con__);
        dao.setUserSidToNameMap(unameMap);    // ユーザ名一覧からユーザSIDを取得
        dao.setRsdSidToSisetsuMap(snameMap);  // 施設名一覧から施設SIDを取得

        // リストからログインユーザがアクセス権限が無いものを除外
        __removeNotAccessAuth(unameMap, snameMap, loginUserSid);

        // 登録予定のスケジュールと同一と思われるスケジュール一覧取得
        ArrayList<SchDataModel> matchList = dao.getMatchSchList(schList);

        //採番コントローラー取得
        MlCountMtController cntCon =
                GroupSession.getResourceManager().getCountController(reqMdl__);

        UDate now = new UDate();

        // -----------------------------------------------------------
        // スケジュール一覧から同時登録データ＋施設予約データを取得

        // 最終的に登録するスケジュール一覧
        ArrayList<SchDataModel> addList = new ArrayList<SchDataModel>(); // スケジュール毎の同時登録データ一覧
        ArrayList<SchDataModel> inserSchtList = new ArrayList<SchDataModel>(); // 登録スケジュール一覧
        ArrayList<RsvSisYrkModel> insertRsvList = new ArrayList<RsvSisYrkModel>(); // 登録施設予約一覧

        for (CybCsvSchModel schMdl : schList) {
            addList.clear();

            if (!__isScheduleMatching(schMdl, schMdl.getScdUsrSid(), matchList)) {
                addList.add(schMdl);
            }

            // 同時登録ユーザを確認
            for (String uname : schMdl.getUnames()) {
                int userSid = 0;
                if (unameMap.containsKey(uname)) {
                    // ユーザ名から該当するユーザSIDを取得
                    userSid = unameMap.get(uname).intValue();
                }

                // 該当するユーザSIDあり＋一致するスケジュールが無い場合 → 登録スケジュールへ追加
                if (userSid > 0 && !__isScheduleMatching(schMdl, userSid, matchList)) {
                    SchDataModel cpMdl = schMdl.cloneSchData();
                    cpMdl.setScdUsrSid(userSid); // スケジュールを書き換える
                    addList.add(cpMdl);
                }
            }

            if (schMdl.getSnames().size() > 0
             && schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                // 施設名一覧あり＋時間指定区分あり → 施設予約情報作成

                int scdResSid = -1;
                for (String sname : schMdl.getSnames()) {
                    int rsvSid = 0;
                    if (snameMap.containsKey(sname)) {
                        rsvSid = snameMap.get(sname).intValue();
                    }
                    if (rsvSid > 0) {
                        if (scdResSid < 0) {
                            //スケジュール施設予約SID作成(同じスケジュール登録で共通)
                            scdResSid = (int) cntCon.getSaibanNumber(
                                    SaibanModel.SBNSID_SCHEDULE,
                                    SaibanModel.SBNSID_SUB_SCH_RES, loginUserSid);
                        }

                        //施設予約を登録
                        int yoyakuSid = (int) cntCon.getSaibanNumber(
                                GSConstReserve.SBNSID_RESERVE,
                                GSConstReserve.SBNSID_SUB_YOYAKU,
                                loginUserSid);

                        RsvSisYrkModel yrkParam = new RsvSisYrkModel();
                        yrkParam.setRsySid(yoyakuSid);
                        yrkParam.setRsdSid(rsvSid);   // 施設SID
                        yrkParam.setRsyMok(NullDefault.getString(schMdl.getScdTitle(), ""));
                        yrkParam.setRsyFrDate(schMdl.getScdFrDate());
                        yrkParam.setRsyToDate(schMdl.getScdToDate());
                        yrkParam.setRsyBiko(NullDefault.getString(schMdl.getScdValue(), ""));
                        yrkParam.setRsyAuid(loginUserSid);
                        yrkParam.setRsyAdate(now);
                        yrkParam.setRsyEuid(loginUserSid);
                        yrkParam.setRsyEdate(now);
                        yrkParam.setScdRsSid(scdResSid);
                        yrkParam.setRsyEdit(schMdl.getScdEdit()); // 編集権限: スケジュールと同じ
                        yrkParam.setRsyPublic(GSConstReserve.PUBLIC_KBN_PLANS); // 公開区分: 予定あり

                        insertRsvList.add(yrkParam);
                    }
                }
                schMdl.setScdRsSid(scdResSid);
            }

            if (addList.size() > 0) {
                int scdGpSid = -1;

                // 2件以上の登録スケジュールがある(同時登録するスケジュールがある)場合
                if (addList.size() > 1) {
                    //スケジュールグルプSID発行
                    scdGpSid = (int) cntCon.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                                                 SaibanModel.SBNSID_SUB_SCH_GP, loginUserSid);
                }
                for (SchDataModel mdl : addList) {
                    // スケジュールSID発行
                    int schSid = (int) cntCon.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                                            SaibanModel.SBNSID_SUB_SCH, loginUserSid);
                    mdl.setScdSid(schSid);
                    if (scdGpSid >= 0) {
                        mdl.setScdGrpSid(scdGpSid);
                    }
                }
                inserSchtList.addAll(addList);
            }
        }

        //スケジュール登録
        SchDataDao schDao = new SchDataDao(con__);
        schDao.insert(inserSchtList);

        // -----------------------------------------------------------
        // 施設予約データ一覧を登録
        if (insertRsvList.size() > 0) {
            //デフォルトグループの情報を取得
            GroupDao grpDao = new GroupDao(con__);
            CmnGroupmModel grpMdl = grpDao.getDefaultGroup(loginUserSid);

            //連絡先
            CmnUsrmInfDao uInfDao = new CmnUsrmInfDao(con__);
            CmnUsrmInfModel uInfMdl = uInfDao.select(loginUserSid);

            ArrayList<RsvSisKyrkModel> insertKyrkList = new ArrayList<RsvSisKyrkModel>();

            for (RsvSisYrkModel rsvMdl : insertRsvList) {
                // 施設予約情報
                int rsvSid = rsvMdl.getRsdSid();

                //承認状況
                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                rsvCmnBiz.setSisYrkApprData(con__, rsvSid, rsvMdl, loginUserSid);

                //施設予約区分別情報を登録（スケジュールからの場合は全て初期値）
                RsvSisDataDao dataDao = new RsvSisDataDao(con__);
                Rsv070Model mdl = dataDao.getPopUpSisetuData(rsvSid);
                if (mdl != null && RsvCommonBiz.isRskKbnRegCheck(mdl.getRskSid())) {
                    int rskSid = mdl.getRskSid();

                    RsvSisKyrkModel kyrkMdl = new RsvSisKyrkModel();
                    //共通項目 担当部署
                    kyrkMdl.setRkyBusyo(grpMdl.getGrpName());
                    //共通項目 担当・使用者名
                    kyrkMdl.setRkyName(uInfMdl.getUsiSei() + "  " + uInfMdl.getUsiMei());
                    kyrkMdl.setRkyContact(NullDefault.getString(uInfMdl.getUsiTelNai1(), ""));

                    //施設区分 部屋
                    if (rskSid == GSConstReserve.RSK_KBN_HEYA) {
                        //利用区分
                        kyrkMdl.setRkyUseKbn(GSConstReserve.RSY_USE_KBN_NOSET);
                    } else if (rskSid == GSConstReserve.RSK_KBN_CAR) {
                        //施設区分 車
                        //印刷区分
                        if (RsvCommonBiz.isUsePrintKbn(appRootPath)) {
                            kyrkMdl.setRkyPrintKbn(GSConstReserve.RSY_PRINT_KBN_YES);
                        } else {
                            kyrkMdl.setRkyPrintKbn(GSConstReserve.RSY_PRINT_KBN_NO);
                        }
                    }
                    kyrkMdl.setRsySid(rsvMdl.getRsySid());
                    kyrkMdl.setRkyAuid(loginUserSid);
                    kyrkMdl.setRkyAdate(now);
                    kyrkMdl.setRkyEuid(loginUserSid);
                    kyrkMdl.setRkyEdate(now);
                    insertKyrkList.add(kyrkMdl);
                }
            }

            // 施設予約情報登録
            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con__);
            yrkDao.insertNewYoyakuPlural2(insertRsvList);

            // 施設予約区分別情報登録
            RsvSisKyrkDao kyrkDao = new RsvSisKyrkDao(con__);
            kyrkDao.insert(insertKyrkList);
        }
        // -----------------------------------------------------------
    }

    /**
     * [機  能] 重複するスケジュールがあるか判定<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param schMdl 調査するスケジュール情報
     * @param userSid スケジュール対象のユーザSID
     * @param matchList 比較するスケジュール一覧
     * @return アクションフォーワード
     */
    private boolean __isScheduleMatching(SchDataModel schMdl, int userSid,
                                        ArrayList<SchDataModel> matchList) {
        for (SchDataModel mdl : matchList) {
            // 重複チェック
            if (mdl.getScdUsrSid() == userSid
             && mdl.getScdTitle().equals(schMdl.getScdTitle())
             && mdl.getScdFrDate().equalsDate(schMdl.getScdFrDate())
             && mdl.getScdToDate().equalsDate(schMdl.getScdToDate())) {
                matchList.remove(mdl);
                return true;
            }
        }
        return false;
    }

    /**
     * [機  能] 同時登録ユーザ及び施設情報のリストからアクセス権限がないものを除外する<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param usrMap 調査するユーザSID一覧
     * @param rsdMap 調査する施設SID一覧
     * @param userSid スケジュール対象のユーザSID
     * @throws SQLException SQLException
     */
    private void __removeNotAccessAuth(HashMap<String, Integer> usrMap,
                                         HashMap<String, Integer> rsdMap,
                                         int userSid) throws SQLException {

        // 同時登録ユーザ一覧
        if (usrMap != null && !usrMap.isEmpty()) {
            SchDao schDao = new SchDao(con__);
            // 特例アクセス権限でアクセスできないユーザSID一覧を取得
            List<Integer> notAcsUsrList = schDao.getNotRegistUserList(userSid);
            for (Iterator<String> i = usrMap.keySet().iterator(); i.hasNext();) {
                Integer usrSid = usrMap.get(i.next());
                if (notAcsUsrList.contains(usrSid)) {
                    i.remove(); // アクセス不可ユーザSIDをリストから除外
                } else if (userSid == usrSid) {
                    i.remove(); // ログインユーザがあれば重複する為、リストから除外
                }
            }
        }

        // 施設予約登録一覧
        if (rsdMap != null && !rsdMap.isEmpty()) {
            //アクセス権限のある施設を取得
            RsvSisDataDao dataDao = new RsvSisDataDao(con__);
            ArrayList<RsvSisDataModel> belongResList =
                dataDao.selectGrpSisetuCanEditList(0, null, userSid);

            List<Integer> acsRsvList = new ArrayList<Integer>();
            for (RsvSisDataModel mdl : belongResList) {
                acsRsvList.add(Integer.valueOf(mdl.getRsdSid())); // 施設SIDのみを一覧として取得
            }

            for (Iterator<String> i = rsdMap.keySet().iterator(); i.hasNext();) {
                Integer rsvSid = rsdMap.get(i.next());
                if (!acsRsvList.contains(rsvSid)) {
                    i.remove(); // アクセス不可施設SIDをリストから除外
                }
            }
        }
    }
}
