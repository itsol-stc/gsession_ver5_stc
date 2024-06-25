package jp.groupsession.v2.enq.enq210kn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.enq.dao.EnqTypeDao;
import jp.groupsession.v2.enq.enq210.Enq210Biz;
import jp.groupsession.v2.enq.enq210.Enq210Dao;
import jp.groupsession.v2.enq.model.EnqMainModel;
import jp.groupsession.v2.enq.model.EnqTypeModel;

/**
 * <br>[機  能] アンケート 設問登録確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq210knBiz extends Enq210Biz {
    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException 実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(Enq210knParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con, String appRoot, String tempDir)
    throws SQLException, IOException, IOToolsException, TempFileException, Exception {

        //発信アンケート管理画面からの遷移
        if (paramMdl.getEnq970BackPage() == 1) {
            paramMdl.setEnq210initFlg(0);
            super.setInitData(paramMdl, reqMdl, con, appRoot, tempDir);
        }

        //アンケート種類(表示用)を設定
        EnqTypeDao enqTypeDao = new EnqTypeDao(con);
        EnqTypeModel enqTypeMdl = enqTypeDao.select(paramMdl.getEnq210Syurui());
        if (enqTypeMdl != null) {
            paramMdl.setEnq210knViewSyurui(enqTypeMdl.getEtpName());
        } else {
            paramMdl.setEnq210knViewSyurui("");
        }

        //発信者名称を設定
        long emnSid = paramMdl.getEditEnqSid();
        if (paramMdl.getEnq970BackPage() == 1) {
            EnqMainDao enqMainDao = new EnqMainDao(con);
            EnqMainModel enqMainMdl = enqMainDao.select(emnSid);
            paramMdl.setEnq210knSenderName(
                    getSenderName(con, reqMdl, paramMdl.getEnq210Send(),
                                            (int) enqMainMdl.getEmnSendUsr()));
            //発信者が削除されているかを確認
            if (enqMainMdl.getEmnSendGrp() > 0) {
                CmnGroupmDao grpDao = new CmnGroupmDao(con);
                paramMdl.setEnq210knSenderDelFlg(
                        grpDao.isDeleteGroup((int) enqMainMdl.getEmnSendGrp()));
            } else {
                CmnUsrmDao usrmDao = new CmnUsrmDao(con);
                CmnUsrmModel usrmModel = usrmDao.select((int) enqMainMdl.getEmnSendUsr());
                paramMdl.setEnq210knSenderUkoFlg(usrmModel.getUsrUkoFlg());
                paramMdl.setEnq210knSenderDelFlg(
                        usrmModel.getUsrJkbn() == GSConst.JTKBN_DELETE);
            }
        } else {
            paramMdl.setEnq210knSenderName(
                    getSenderName(con, reqMdl, paramMdl.getEnq210Send()));
        }

        //説明(表示用)を設定
        paramMdl.setEnq210knViewDesc(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getEnq210Desc(), "")));

        //説明から不正なHTMLタグを削除
        paramMdl.setEnq210Desc(StringUtilHtml.removeIllegalTag(paramMdl.getEnq210Desc()));

        //添付ファイル名の設定
        setTempFileName(paramMdl, reqMdl);

        //設問一覧を設定
        paramMdl.setEbaList(readQueList(reqMdl));

        //設問一覧説明文から不正なタグを除去
        String queDesc = "";
        for (int idx = 0; idx < paramMdl.getEbaList().size(); idx++) {
            queDesc = paramMdl.getEbaList().get(idx).getEnq210QueDesc();
            paramMdl.getEbaList().get(idx).setEnq210QueDesc(
                    StringUtilHtml.removeIllegalTag(queDesc));
        }

        //対象者を設定
        paramMdl.setSelectAnswerCombo(_createAnswerCombo(paramMdl, con));

        //発信アンケート管理画面からの遷移の場合、削除された対象者(グループ、ユーザ)を表示する
        if (paramMdl.getEnq970BackPage() == 1) {
            List<String> delAnswerList = new ArrayList<String>();
            Enq210Dao dao210 = new Enq210Dao(con);
            List<String> delGrpList = dao210.getDeleteSubjectGroupName(emnSid);
            if (delGrpList != null && !delGrpList.isEmpty()) {
                delAnswerList.addAll(delGrpList);
            }

            List<String> delUsrList = dao210.getDeleteSubjectUserName(emnSid);
            if (delUsrList != null && !delUsrList.isEmpty()) {
                delAnswerList.addAll(delUsrList);
            }
            paramMdl.setEnq210knDelAnswerList(delAnswerList);
        }
    }
}
