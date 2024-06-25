package jp.groupsession.v2.api.schedule.attend;

import java.sql.Connection;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.api.schedule.edit.ApiSchEditBiz;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;

/**
 * <br>[機  能] スケジュール 出欠確認応答編集 WEBAPI biz
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchChangeAttendBiz {
    /** コネクション */
    public Connection con__ = null;
    /** リクエストモデル*/
    public RequestModel reqMdl__ = null;
    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;

    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     * @param cntCon MlCountMtController
     */
    public ApiSchChangeAttendBiz(Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon) {
        super();
        con__ = con;
        reqMdl__ = reqMdl;
        cntCon__ = cntCon;
    }

    /**
     * <br>[機  能] スケジュールを更新します(出欠回答者の場合)
     * <br>[解  説] 回答区分、更新日時のみ変更
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl アクションフォーム
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @throws Exception SQL実行時例外
     */
    public void updateScheduleDateAns(RequestModel reqMdl, ApiSchChangeAttendParamModel paramMdl,
            int userSid,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg) throws Exception {
        SchDataDao dao  = new SchDataDao(con__);
        SchDataModel scdMdl = new SchDataModel();

        //スケジュールSID
        int schSid = Integer.valueOf(paramMdl.getSchSid());

        //更新前 未回答数取得
        int cntBefore = dao.countAnsNone(schSid);

        UDate date = new UDate();

        //更新モデルの作成
        scdMdl.setScdSid(schSid);
        scdMdl.setScdAttendAns(
                NullDefault.getInt(
                        paramMdl.getAnser(), GSConstSchedule.ATTEND_ANS_NONE));
        scdMdl.setScdAttendComment("");
        scdMdl.setScdEuid(userSid);
        scdMdl.setScdEdate(date);

        dao.updateAnsKbn(scdMdl);

        //更新後 未回答数取得
        int cntAfter = dao.countAnsNone(schSid);
        //出欠確認 依頼者に更新完了通知を行う
        //未回答件数が無い場合、且つカウンタが1→0になった場合
        if (cntAfter == 0 && cntAfter != cntBefore) {
            boolean delFlg = dao.isCheckAttendAuSchDelete(schSid);
            //依頼者のスケジュールが削除された場合、完了通知メールを送信しない
            if (!delFlg) {
                //回答者のスケジュールSIDより紐付いている出欠確認 依頼者のスケジュールデータを取得する
                SchDataModel parSchMdl = dao.getAttendRegistSch(schSid);
                if (parSchMdl != null) {
                    ApiSchEditBiz editBiz = new ApiSchEditBiz(con__, reqMdl__, cntCon__);

                    SchCommonBiz cmnBiz = new SchCommonBiz(reqMdl);
                    //URL取得
                    String url = editBiz.createScheduleUrlDefo(GSConstSchedule.CMD_EDIT,
                            String.valueOf(parSchMdl.getScdSid()),
                            String.valueOf(parSchMdl.getScdUsrSid()),
                            parSchMdl.getScdFrDate().getDateString(),
                            String.valueOf(parSchMdl.getScdUsrKbn()));
                    cmnBiz.sendAttendCompSmail(
                            con__, cntCon__, parSchMdl, appRootPath,
                            plconf, smailPluginUseFlg, url, date);
                }
            }
        }

    }



}
