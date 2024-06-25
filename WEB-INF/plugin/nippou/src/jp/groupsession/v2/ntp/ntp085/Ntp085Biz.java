package jp.groupsession.v2.ntp.ntp085;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpAdmConfDao;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;

/**
 * <br>[機  能] 日報 ショートメール通知設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp085Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp085Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Ntp085Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp085ParamModel
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Ntp085ParamModel paramMdl) throws SQLException {
        log__.debug("setInitData START");

        //日報管理者設定の値を取得
        NtpCommonBiz ncbiz = new NtpCommonBiz(con__, reqMdl__);
        NtpAdmConfModel adminModel = ncbiz.getAdminConfiModel(con__);
        if (adminModel != null) {
            paramMdl.setNtp085NoticeKbn(adminModel.getNacSmlKbn());
            paramMdl.setNtp085SmlNoticeKbn(adminModel.getNacSmlNoticeKbn());
            paramMdl.setNtp085SmlNoticePlace(adminModel.getNacSmlNoticeGrp());
            paramMdl.setNtp085CmtNoticeKbn(adminModel.getNacCsmlKbn());
            paramMdl.setNtp085CmtSmlNoticeKbn(adminModel.getNacCsmlNoticeKbn());
            paramMdl.setNtp085GoodNoticeKbn(adminModel.getNacGsmlKbn());
            paramMdl.setNtp085GoodSmlNoticeKbn(adminModel.getNacGsmlNoticeKbn());
        }
    }

    /**
     * <br>[機  能] 設定された管理者設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp085ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setAconfSetting(Ntp085ParamModel paramMdl,
            BaseUserModel umodel) throws SQLException {

        boolean commitFlg = false;
        try {
            //管理者設定の更新用モデル取得
            NtpAdmConfModel adminModel = __getNtpAdmConfModel(paramMdl, umodel, con__);

            //管理者設定更新
            NtpAdmConfDao dao = new NtpAdmConfDao(con__);
            int count = dao.update(adminModel);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(adminModel);
            }

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            }
        }
    }



    /**
     * <br>[機  能] 日報データを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp085ParamModel
     * @param umodel ユーザモデル
     * @param con コネクション
     * @return model 管理者設定モデル
     * @throws SQLException SQL実行エラー
     */
    public NtpAdmConfModel __getNtpAdmConfModel(Ntp085ParamModel paramMdl,
           BaseUserModel umodel, Connection con) throws SQLException {
        log__.debug("日報データ 削除開始");

        //管理者設定より設定情報を取得。なければデフォルト値とする。
        NtpCommonBiz ncbiz = new NtpCommonBiz(con, reqMdl__);
        NtpAdmConfModel model = ncbiz.getAdminConfiModel(con);

        //デフォルト値をセットする。
        if (model == null) {
            model = new NtpAdmConfModel();
            model.setNacCrange(GSConstNippou.CRANGE_SHARE_ALL);
            model.setNacAtdelFlg(GSConstNippou.AUTO_DELETE_OFF);
            model.setNacHourDiv(GSConstNippou.DF_HOUR_DIVISION);
            model.setNacAtdelY(-1);
            model.setNacAtdelM(-1);
            model.setNacAuid(umodel.getUsrsid());
            model.setNacAdate(new UDate());
        }

        //更新する値をセットする。
        model.setNacSmlKbn(paramMdl.getNtp085NoticeKbn());
        model.setNacSmlNoticeKbn(paramMdl.getNtp085SmlNoticeKbn());
        model.setNacSmlNoticeGrp(paramMdl.getNtp085SmlNoticePlace());
        model.setNacCsmlKbn(paramMdl.getNtp085CmtNoticeKbn());
        model.setNacCsmlNoticeKbn(paramMdl.getNtp085CmtSmlNoticeKbn());
        model.setNacGsmlKbn(paramMdl.getNtp085GoodNoticeKbn());
        model.setNacGsmlNoticeKbn(paramMdl.getNtp085GoodSmlNoticeKbn());
        model.setNacEuid(umodel.getUsrsid());
        model.setNacEdate(new UDate());

        return model;
    }
}
