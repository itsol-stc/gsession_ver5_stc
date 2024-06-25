package jp.groupsession.v2.ntp.ntp231;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpTargetDao;
import jp.groupsession.v2.ntp.dao.NtpTemplateDao;
import jp.groupsession.v2.ntp.dao.NtpTmpTargetDao;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.ntp.model.NtpTmpTargetModel;

/**
 * <br>[機  能] 日報 目標登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp231Biz {

    /** 採番コントローラ */
    public MlCountMtController cntCon__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <p>デフォルトコンストラクタ
     * @param reqMdl RequestModel
     */
    public Ntp231Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <p>コンストラクタ
     * @param cntCon MlCountMtController
     * @param reqMdl RequestModel
     */
    public Ntp231Biz(MlCountMtController cntCon,
                     RequestModel reqMdl) {
        reqMdl__ = reqMdl;
        cntCon__ = cntCon;
    }


    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp231ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp231ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        if (paramMdl.getNtp231InitFlg().equals(String.valueOf(GSConstNippou.INIT_FLG))) {

            if (paramMdl.getNtp230ProcMode().equals(GSConstNippou.CMD_EDIT)) {
                //変更処理
                NtpTargetDao targetDao = new NtpTargetDao(con);
                NtpTargetModel targetMdl = targetDao.select(paramMdl.getNtp230NtgSid());
                if (targetDao != null) {
                    paramMdl.setNtp231TargetName(targetMdl.getNtgName());
                    paramMdl.setNtp231TargetDetail(targetMdl.getNtgDetail());
                    paramMdl.setNtp231TargetUnit(targetMdl.getNtgUnit());
                    paramMdl.setNtp231TargetDef(String.valueOf(targetMdl.getNtgDef()));
                }

                //テンプレート取得
                NtpTmpTargetDao nptDao = new NtpTmpTargetDao(con);
                ArrayList<NtpTmpTargetModel> nptList = null;
                nptList = nptDao.selectForTarget(paramMdl.getNtp230NtgSid());
                if (!nptList.isEmpty()) {
                    ArrayList<String> tmplateList = new ArrayList<String>();
                    for (NtpTmpTargetModel nptMdl : nptList) {
                        tmplateList.add(String.valueOf(nptMdl.getNttSid()));
                    }
                    paramMdl.setNtp231DspTemplate(
                            tmplateList.toArray(new String[tmplateList.size()]));
                }
            }

        }

        //テンプレート取得
        List<NtpTemplateModel> tmplateList = new ArrayList<NtpTemplateModel>();
        NtpTemplateDao tmpDao = new NtpTemplateDao(con);
        tmplateList = tmpDao.select();
        if (!tmplateList.isEmpty()) {
            paramMdl.setNtp231TemplateList(tmplateList);
        }

        paramMdl.setNtp231InitFlg(String.valueOf(GSConstNippou.NOT_INIT_FLG));
    }
}
