package jp.groupsession.v2.anp.anp060kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp060kn.dao.Anp060knDao;
import jp.groupsession.v2.anp.anp060kn.model.Anp060knSenderModel;
import jp.groupsession.v2.anp.dao.AnpDatausedSumDao;
import jp.groupsession.v2.anp.dao.AnpHdataDao;
import jp.groupsession.v2.anp.dao.AnpJdataDao;
import jp.groupsession.v2.anp.dao.AnpSdataDao;
import jp.groupsession.v2.anp.model.AnpCmnConfModel;
import jp.groupsession.v2.anp.model.AnpHdataModel;
import jp.groupsession.v2.anp.model.AnpJdataModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.anp.model.AnpSdataModel;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 安否確認メッセージ配信確認画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp060knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp060knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp060knModel アクションフォーム
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp060knParamModel anp060knModel, RequestModel reqMdl, Connection con)
                        throws Exception {
        log__.debug("初期表示");

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();

        //全件のグループ・ユーザリストの取得
        List<Anp060knSenderModel> sendUserList = __setSenderList(anp060knModel, con);

        if (!anp060knModel.getAnp060ProcMode()
                .equals(GSConstAnpi.MSG_HAISIN_MODE_INFOCONF) && (sendUserList != null)) {
            //ユーザリストにページング情報を設定する
            __setPagingData(anp060knModel, con, sessionUsrSid, sendUserList);
        }

        //配信者の設定
        anp060knModel.setAnp060RegistName(NullDefault.getString(usModel.getUsisei(), "") + " "
                               + NullDefault.getString(usModel.getUsimei(), ""));

        //件名表示用の設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        String title;
        if (anp060knModel.getAnp010KnrenFlg() == GSConstAnpi.KNREN_MODE_ON) {
            title = "【 " + gsMsg.getMessage("anp.knmode") + " 】" + anp060knModel.getAnp060Subject();
        } else {
            title = anp060knModel.getAnp060Subject();
        }
        anp060knModel.setAnp060knDispSubject(title);

        //本文表示用の設定
        anp060knModel.setAnp060knDispMessageBody(anpiBiz.getHaisinMessageBody(
                reqMdl, con, null, null, anp060knModel.getAnp060Text1(),
                anp060knModel.getAnp060Text2(), true, anp060knModel.getAnp010KnrenFlg()));
    }

    /**
     * <br>[機  能] ページング情報を設定する
     * <br>[解  説] ページコンボ設定、ユーザリストの設定
     * <br>[備  考]
     * @param anp060knModel パラメータモデル
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param sendUserList 配信先ユーザリスト
     * @throws SQLException SQL実行例外
     */
    private void __setPagingData(Anp060knParamModel anp060knModel,
            Connection con, int sessionUsrSid,
            List<Anp060knSenderModel> sendUserList) throws SQLException {
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();

        //個人設定情報を取得
        log__.debug("個人設定情報を取得 usrSid = " + sessionUsrSid);
        AnpPriConfModel priConf = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        //個人設定の表示件数
        int limit = priConf.getAppListCount();

        //現在のページ
        int nowPage = anp060knModel.getAnp060knNowPage();
        //開始位置の取得
        int start = __getRowNumber(nowPage, limit);

        //送信先ユーザ情報のセットと最大件数の取得
        int maxCount = sendUserList.size();

        //ページあふれ制御
        int maxPage = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = __getRowNumber(maxPage, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPage;
            start = maxPageStartRow;
        }

        anp060knModel.setAnp060knNowPage(nowPage);
        anp060knModel.setAnp060knDspPage1(nowPage);
        anp060knModel.setAnp060knDspPage2(nowPage);
        anp060knModel.setAnp060knPageLabel(PageUtil.createPageOptions(maxCount, limit));

        int end = start + limit;
        if (end > maxCount) {
            end = maxCount;
        }

        //ページ制御したユーザリストの設定
        anp060knModel.setAnp060knSenderList(sendUserList.subList(start, end));
    }

    /**
     * <br>[機  能] 再送信情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param con DBコネクション
     * @return true:正常 false:配信データがない
     * @throws SQLException SQL実行時例外
     */
    public boolean setReplyData(Anp060knParamModel paramMdl, Connection con)
                throws SQLException {

        int anpiSid = new Integer(paramMdl.getAnpiSid());

        //配信データ取得
        AnpHdataDao hDao = new AnpHdataDao(con);
        AnpHdataModel hModel = hDao.select(anpiSid);
        if (hModel == null) {
            return false;
        }

        paramMdl.setAnp010KnrenFlg(hModel.getAphKnrenFlg());
        paramMdl.setAnp060main(hModel.getAphViewMain());
        paramMdl.setAnp060Subject(hModel.getAphSubject());
        paramMdl.setAnp060Text1(hModel.getAphText1());
        paramMdl.setAnp060Text2(hModel.getAphText2());

        //送信リスト
        String[] senders = null;
        if (!paramMdl.isViewMode()) {
            //ユーザ状況取得
            AnpSdataDao sdDao = new AnpSdataDao(con);
            List <AnpSdataModel> sModelList = sdDao.select(anpiSid);
            List<String> senderList = new ArrayList<String>();
            if (sModelList.size() != 0) {
                for (AnpSdataModel smdl : sModelList) {
                    if (smdl.getApsType() == GSConstAnpi.SEND_TYPE_USER) {
                        senderList.add(String.valueOf(smdl.getUsrSid()));
                    } else if (smdl.getApsType() == GSConstAnpi.SEND_TYPE_GROUP) {
                        senderList.add(0, "G" + String.valueOf(smdl.getGrpSid()));
                    }
                }
                if (!senderList.isEmpty()) {
                    senders = (String[]) senderList.toArray(new String[senderList.size()]);
                }
            }
        }
        paramMdl.setAnp060SenderList(senders);

        return true;
    }

    /**
     * <br>[機  能] 配信、再送信を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param anp060knModel アクションフォーム
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @param cntCon 採番コントローラ
     * @param smtpFlg SMTPサーバー接続フラグ true:接続可  false:接続不可
     * @return true:正常終了 false:一部ユーザにメール送信失敗
     * @throws Exception 実行例外
     */
    public boolean doHaisin(Anp060knParamModel anp060knModel, RequestModel reqMdl, Connection con,
                         MlCountMtController cntCon, boolean smtpFlg)
                        throws Exception {

        String [] usrsList = null;

        boolean commitFlg = false;
        int anpiSid = 0;
        AnpHdataModel hdata = null;

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        try {
            con.setAutoCommit(false);

            //新規追加 or 再送 データ登録処理
            if (anp060knModel.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_MISAISOU)
             || anp060knModel.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_ZENSAISOU)) {

                anpiSid = new Integer(anp060knModel.getAnpiSid());
                //安否確認配信データ
                hdata = __getHdata(anp060knModel, anpiSid);
                hdata.setAphSuid(sessionUsrSid);
                hdata.setAphSdate(new UDate());
                usrsList = __doSaisou(anp060knModel, reqMdl, con, sessionUsrSid, hdata, anpiSid);
            } else {
                //安否SID採番
                anpiSid = (int) cntCon.getSaibanNumber(
                        GSConstAnpi.SBNSID_ANPI,
                        GSConstAnpi.SBNSID_SUB_ANPIHAISIN,
                        sessionUsrSid);

                //安否確認配信データ
                hdata = __getHdata(anp060knModel, anpiSid);
                hdata.setAphHuid(sessionUsrSid);
                hdata.setAphHdate(new UDate());
                hdata.setAphScount(0);
                hdata.setAphEndFlg(0);

                usrsList = __doInsert(anp060knModel, reqMdl, con, sessionUsrSid, hdata, anpiSid);
            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

//配信データ、全安否状況データ登録完了後に対象ユーザにメールを送信する
        boolean isSendErr = false;
        if (commitFlg) {
            AnpiCommonBiz anpibiz = new AnpiCommonBiz();

            //緊急連絡先個人設定を取得
            Anp060knDao dao = new Anp060knDao(con);
            HashMap<String, String> priList = dao.selectConnect(usrsList);

            //共通設定情報を取得
            AnpCmnConfModel cConf = anpibiz.getAnpCmnConfModel(con);

            //配信ユーザを対象にメールを送信する
            for (String uSid : usrsList) {
                UDate now = new UDate();
                AnpJdataModel jdata = new AnpJdataModel();

                //個人設定のメールアドレスを取得
                String madr = null;
                if (priList.containsKey(uSid)) {
                    madr = priList.get(uSid);
                }

                int sendFlg = 1;
                //メール送信
                if (smtpFlg) {
                    if (!StringUtil.isNullZeroString(madr)) {
                        //送信
                        int sendflg = anpibiz.sendMail(reqMdl, cConf, hdata, madr, uSid, con);
                        if (sendflg == GSConstAnpi.SENDMSG_SUCCESS) {
                            jdata.setApdHdate(now);
                            sendFlg = GSConstAnpi.HAISIN_FLG_OK;
                        } else {
                            isSendErr = true;
                            sendFlg = GSConstAnpi.HAISIN_FLG_ERROR;
                        }
                    }
                } else {
                    isSendErr = true;
                    sendFlg = GSConstAnpi.HAISIN_FLG_ERROR;
                }

                jdata.setAphSid(anpiSid);
                jdata.setUsrSid(Integer.valueOf(uSid));
                jdata.setApdHaisinFlg(sendFlg);
                jdata.setApdEuid(sessionUsrSid);
                jdata.setApdEdate(now);

                //配信中から配信済み、配信エラーの状態へ変更する
                AnpJdataDao jDao = new AnpJdataDao(con);

                boolean sendKbnCommitFlg = false;
                try {
                    con.setAutoCommit(false);

                    //更新実行
                    jDao.updateSendFlg(jdata);
                    sendKbnCommitFlg = true;
                } catch (SQLException e) {
                    log__.error("SQLException", e);
                    throw e;
                } finally {
                    if (sendKbnCommitFlg) {
                        con.commit();
                    } else {
                        JDBCUtil.rollback(con);
                    }
                }
            }
        }

        return !isSendErr;
    }

    /**
     * <br>[機  能] 送信者リストを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp060knModel アクションフォーム
     * @param con DBコネクション
     * @return return 送信者件数
     * @throws Exception 実行例外
     */
    private List<Anp060knSenderModel> __setSenderList(
            Anp060knParamModel anp060knModel, Connection con)
                throws Exception {

        String[] senders = anp060knModel.getAnp060SenderList();
        if (senders == null || senders.length == 0) {
            return null;
        }

        //格納リスト
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //送信者一覧からユーザSIDとグループSIDを分離する
        if (senders != null) {
            for (int i = 0; i < senders.length; i++) {
                String str = NullDefault.getString(senders[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }

        //送信先リストにメールの有無情報を設定したデータを作成
        List<Anp060knSenderModel> senderList = new ArrayList<Anp060knSenderModel>();
        int settingCnt = 0;
        Anp060knDao dao = new Anp060knDao(con);

        //グループの情報を取得する
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        for (GroupModel grpMdl : glist) {

            //メッセージ配信モードが未返信者へ再送信の場合
            if (anp060knModel.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_MISAISOU)) {
                //全員返信済みの場合はそのグループは表示しない
                int count = dao.getCntMihensinGrpUsrs(
                        Integer.valueOf(anp060knModel.getAnpiSid()), grpMdl.getGroupSid());
                if (count == 0) {
                    //未返信者無し
                    //送信SIDリストから除外する
                    String [] sids = anp060knModel.getAnp060SenderList();
                    anp060knModel.setAnp060SenderList(
                            __delSenderList(sids, "G" + grpMdl.getGroupSid()));
                    continue;
                }
            }

            //グループに所属しているユーザの情報を取得する
            List<CmnUsrmInfModel> belongList =
                    anpiBiz.getBelongUserList(con, grpMdl.getGroupSid(), null, -1, false);

            ArrayList<String> sids = new ArrayList<String>();
            //ユーザ情報からSIDのリストを生成
            for (CmnUsrmInfModel usrMdl : belongList) {
                sids.add(String.valueOf(usrMdl.getUsrSid()));
            }

            //ユーザの緊急連絡先が設定されているか調べる
            HashMap<String, String> setteiList =
                    dao.selectConnect((String[]) sids.toArray(new String[sids.size()]));
            boolean noSetFlg = false;
            for (CmnUsrmInfModel usrMdl : belongList) {
                //緊急連絡先未設定
                if (!setteiList.containsKey(String.valueOf(usrMdl.getUsrSid()))) {
                    noSetFlg = true;
                    break;
                }
            }

            Anp060knSenderModel bean = new Anp060knSenderModel();
            //設定されていた場合
            if (!noSetFlg) {
                bean.setMailadr(Anp060knParamModel.MAIL_SET_YES);
                settingCnt++;
            }
            bean.setGrpFlg(Anp060knParamModel.GROUP_SELECT_YES);
            bean.setSid(String.valueOf(grpMdl.getGroupSid()));
            bean.setName(grpMdl.getGroupName());

            senderList.add(bean);
        }

        //基本ユーザ情報を取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> userList =
                (ArrayList<CmnUsrmInfModel>) userBiz.getUserList(con,
                        (String[]) usrSids.toArray(new String[usrSids.size()]));

        if (userList != null && !userList.isEmpty()) {
            HashMap<String, String> setteiList =
                    dao.selectConnect((String[]) usrSids.toArray(new String[usrSids.size()]));

            for (CmnUsrmInfModel user: userList) {

                //メッセージ配信モードが未返信者へ再送信の場合
                if (anp060knModel.getAnp060ProcMode().equals(
                        GSConstAnpi.MSG_HAISIN_MODE_MISAISOU)) {
                    //返信済みか調べる 返信済みの場合は表示しない
                    int count = dao.getCntMihensinUsr(
                            Integer.valueOf(anp060knModel.getAnpiSid()), user.getUsrSid());
                    if (count == 0) {
                        //送信SIDリストから除外する
                        String [] sids = anp060knModel.getAnp060SenderList();
                        anp060knModel.setAnp060SenderList(
                                __delSenderList(sids, String.valueOf(user.getUsrSid())));
                        continue;
                    }
                }

                Anp060knSenderModel bean = new Anp060knSenderModel();
                String usid = String.valueOf(user.getUsrSid());

                bean.setGrpFlg(Anp060knParamModel.GROUP_SELECT_NO);
                bean.setSid(usid);
                bean.setName(user.getUsiSei() + " " + user.getUsiMei());
                bean.setUsrUkoFlg(user.getUsrUkoFlg());
                if (setteiList.containsKey(usid)) {
                    bean.setMailadr(Anp060knParamModel.MAIL_SET_YES);
                    settingCnt++;
                }
                senderList.add(bean);
            }
        }

        anp060knModel.setAnp060knSetConCount(settingCnt);
        return senderList;
    }

    /**
     * <br>[機  能] 新規配信を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param anp060knModel アクションフォーム
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @param hdata 配信データモデル
     * @param anpiSid  安否SID採番
     * @return 配信対象ユーザSID一覧
     * @throws Exception 実行例外
     */
    private String [] __doInsert(Anp060knParamModel anp060knModel, RequestModel reqMdl,
            Connection con, int usrSid, AnpHdataModel hdata, int anpiSid)
                    throws Exception {
        log__.debug("新規配信");

        //追加実行
        AnpHdataDao hDao = new AnpHdataDao(con);
        hDao.insert(hdata);
        
        AnpDatausedSumDao adsDao = new AnpDatausedSumDao(con);
        adsDao.insertHdataAddDiff(Arrays.asList(hdata.getAphSid()));

        //安否状況データ追加＆メール送信
        return __doRegistJokyoData(anp060knModel, reqMdl, con, usrSid, hdata, true, anpiSid);
    }

    /**
     * <br>[機  能] 再送信行う
     * <br>[解  説]
     * <br>[備  考]
     * @param anp060knModel アクションフォーム
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @param hdata 配信データモデル
     * @param anpiSid 安否SID
     * @return 配信対象ユーザSID一覧
     * @throws Exception 実行例外
     */
    private String [] __doSaisou(Anp060knParamModel anp060knModel, RequestModel reqMdl,
            Connection con, int usrSid, AnpHdataModel hdata, int anpiSid)
                        throws Exception {

        log__.debug("再送信");
        //再送更新実行
        AnpHdataDao hDao = new AnpHdataDao(con);
        hDao.updateSaisousin(hdata);

        //安否状況データ更新
        return __doRegistJokyoData(anp060knModel, reqMdl, con, usrSid, hdata, false, anpiSid);
    }

    /**
     * <br>[機  能] 安否状況データの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param anp060knModel アクションフォーム
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @param usrSid ユーザSID
     * @param hdata 配信データ
     * @param isAdd true:新規配信 false:再配信
     * @param anpiSid 安否SID
     * @return 配信対象ユーザSID一覧
     * @throws Exception 実行例外
     */
    private String [] __doRegistJokyoData(Anp060knParamModel anp060knModel, RequestModel reqMdl,
            Connection con, int usrSid,
            AnpHdataModel hdata, boolean isAdd, int anpiSid)
                    throws Exception {
        if (isAdd) {
            log__.debug("新規配信");
        } else {
            log__.debug("再配信");
        }

        String [] senders = anp060knModel.getAnp060SenderList();
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();
        //多重送信防止の除外リスト
        ArrayList<Integer> delUsrSids = new ArrayList<Integer>();

        //グループとユーザに分ける
        if (senders != null) {
            for (String sid : senders) {
                //空行選択時
                if (sid.equals("-1")) {
                    continue;
                }
                if (sid.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(sid.substring(1, sid.length())));
                } else {
                    //ユーザ
                    usrSids.add(sid);
                    //ユーザ指定されたSIDはグループ検索から除外
                    delUsrSids.add(Integer.valueOf(sid));
                }
            }
        }

        //新規配信のみ送信者データ作成
        if (isAdd) {
            //送信者データモデルリストの作成
            ArrayList <AnpSdataModel> sDataList = __getSdataList(anpiSid, grpSids, usrSids);
            //送信者データの登録
            AnpSdataDao sDao = new AnpSdataDao(con);
            for (AnpSdataModel sMdl : sDataList) {
                sDao.insert(sMdl);
            }
        }

        //未返信者のみ かつ 再配信時に返信済みユーザを除外リストに追加
        AnpJdataDao jDao = new AnpJdataDao(con);
        if ((anp060knModel.getAnp060ProcMode().equals(GSConstAnpi.MSG_HAISIN_MODE_MISAISOU))
                && (!isAdd)) {
            //返信済みユーザSID取得
            List<AnpJdataModel> zumiJdata = jDao.selectHensinZumi(anpiSid);
            for (AnpJdataModel jmdl : zumiJdata) {
                delUsrSids.add(jmdl.getUsrSid());
            }
        }

        AnpiCommonBiz anpibiz = new AnpiCommonBiz();
        //全ユーザの一覧を作成
        ArrayList<String> allUsrList = new ArrayList<String>();

        //グループ内のユーザの一覧を取得
        for (int grpSid : grpSids) {
            List<CmnUsrmInfModel> belongList =
                    anpibiz.getBelongUserList(con, grpSid, delUsrSids, -1, false);
            for (CmnUsrmInfModel usrMdl : belongList) {
                //グループ指定によるユーザリスト
                allUsrList.add(String.valueOf(usrMdl.getUsrSid()));
            }
        }

        //ユーザ指定によるユーザリスト
        allUsrList.addAll(usrSids);

        //同じ値を除去
        HashSet<String> set = new HashSet<String>();
        for (String str : allUsrList) {
            set.add(str);
        }
        String [] allSender = (String[]) set.toArray(new String[set.size()]);

        //緊急連絡先個人設定を取得
        Anp060knDao dao = new Anp060knDao(con);
        HashMap<String, String> priList = dao.selectConnect(allSender);

        AnpDatausedSumDao adsDao = new AnpDatausedSumDao(con); 
        //対象ユーザの安否状況データを登録
        for (String sid : allSender) {
            AnpJdataModel jdata =
                    __getJdata(anp060knModel, priList, hdata.getAphSid(),
                            new Integer(sid), usrSid, new UDate());
            //登録・更新処理実行
            if (isAdd) {
                jDao.insert(jdata);
                adsDao.insertJdataAddDiff(Arrays.asList(jdata.getUsrSid()), jdata.getAphSid());
            } else {
                adsDao.insertJdataDelDiff(Arrays.asList(jdata.getUsrSid()), jdata.getAphSid());
                jDao.updateSaisousin(jdata);
                adsDao.insertJdataAddDiff(Arrays.asList(jdata.getUsrSid()), jdata.getAphSid());
            }
        }

        return allSender;
    }

    /**
     * <br>[機  能] 配信データの登録内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param anpiSid 安否確認SID
     * @return 登録配信データ
     */
    private AnpHdataModel __getHdata(Anp060knParamModel paramMdl, int anpiSid) {

        AnpHdataModel hModel = new AnpHdataModel();
        if (paramMdl.getAnp060main() == GSConstAnpi.APH_VIEW_MAIN_SENDTO) {
            hModel.setAphViewMain(GSConstAnpi.APH_VIEW_MAIN_SENDTO);
        } else {
            hModel.setAphViewMain(GSConstAnpi.APH_VIEW_MAIN_ALL);
        }
        hModel.setAphKnrenFlg(paramMdl.getAnp010KnrenFlg());
        hModel.setAphSid(anpiSid);
        hModel.setAphSubject(paramMdl.getAnp060Subject());
        hModel.setAphText1(paramMdl.getAnp060Text1());
        hModel.setAphText2(paramMdl.getAnp060Text2());

        return hModel;
    }

    /**
     * <br>[機  能] 状況データの登録内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param anp060knModel アクションフォーム
     * @param priList 個人設定リスト（Key:userSid value:緊急連絡先メールアドレス）
     * @param anpiSid 安否確認SID
     * @param usrSid  キーユーザSID
     * @param auid    更新ユーザSID
     * @param upDate  更新日時
     * @return 登録状況データ
     */
    private AnpJdataModel __getJdata(
            Anp060knParamModel anp060knModel,
            HashMap<String, String> priList,
            int anpiSid, int usrSid, int auid, UDate upDate) {

        //個人設定のメールアドレスを取得
        String madr = null;
        if (priList.containsKey(String.valueOf(usrSid))) {
            madr = priList.get(String.valueOf(usrSid));
        }

        AnpJdataModel jModel = new AnpJdataModel();
        jModel.setAphSid(anpiSid);
        jModel.setUsrSid(usrSid);
        jModel.setApdMailadr(madr);
        jModel.setApdJokyoFlg(GSConstAnpi.JOKYO_FLG_UNSET);
        jModel.setApdPlaceFlg(GSConstAnpi.PLACE_FLG_UNSET);
        jModel.setApdSyusyaFlg(GSConstAnpi.SYUSYA_FLG_UNSET);
        jModel.setApdComment(null);
        jModel.setApdScount(0);
        jModel.setApdHaisinFlg(GSConstAnpi.HAISIN_FLG_UNSET);
        jModel.setApdAuid(auid);
        jModel.setApdAdate(upDate);
        jModel.setApdEuid(auid);
        jModel.setApdEdate(upDate);

        return jModel;
    }

    /**
     *
     * <br>[機  能] グループSID、ユーザSIDより送信先データモデルリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param aphSid 安否SID
     * @param grpSids グループSIDリスト
     * @param usrSids ユーザSIDリスト
     * @return 送信先データモデル一覧
     */
    private ArrayList<AnpSdataModel> __getSdataList(int aphSid,
            List<Integer>grpSids, List<String>usrSids) {
        ArrayList <AnpSdataModel> ret = new ArrayList<AnpSdataModel>();

        AnpSdataModel sMdl = null;
        //グループ
        if (grpSids != null && 0 <= grpSids.size()) {
            for (int grpSid : grpSids) {
                sMdl = new AnpSdataModel();
                sMdl.setAphSid(aphSid);
                sMdl.setApsType(GSConstAnpi.SEND_TYPE_GROUP);
                sMdl.setGrpSid(grpSid);
                sMdl.setUsrSid(-1);
                ret.add(sMdl);
            }
        }

        //ユーザ
        if (usrSids != null && 0 <= usrSids.size()) {
            for (String usrSid : usrSids) {
                sMdl = new AnpSdataModel();
                sMdl.setAphSid(aphSid);
                sMdl.setApsType(GSConstAnpi.SEND_TYPE_USER);
                sMdl.setGrpSid(-1);
                sMdl.setUsrSid(Integer.valueOf(usrSid));
                ret.add(sMdl);
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 指定したSIDを送信先ユーザSIDリストから除外する
     * <br>[解  説]
     * <br>[備  考]
     * @param sids 送信先ユーザSIDリスト
     * @param sid 除外SID
     * @return 除外した送信先ユーザSIDリスト
     * */
    private String[] __delSenderList(String [] sids, String sid) {
        List<String> list = new ArrayList<String>(Arrays.asList(sids));
        list.remove(sid);
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * [機  能] ページから算出したページ開始行数を返します。<br>
     * [解  説]<br>
     * [備  考]<br>
     * @param page 表示ページ数
     * @param onePageLimit 1ページの最大件数
     * @return ページ開始行数
     */
    private int __getRowNumber(int page, int onePageLimit) {
        return ((page - 1) * onePageLimit);
    }
}