package jp.groupsession.v2.ntp.ntp089kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpSpaccessDao;
import jp.groupsession.v2.ntp.dao.NtpSpaccessPermitDao;
import jp.groupsession.v2.ntp.dao.NtpSpaccessTargetDao;
import jp.groupsession.v2.ntp.model.NtpSpaccessModel;
import jp.groupsession.v2.ntp.model.NtpSpaccessPermitModel;
import jp.groupsession.v2.ntp.model.NtpSpaccessTargetModel;
import jp.groupsession.v2.ntp.ntp088.Ntp088Const;

/**
 * <br>[機  能] 日報 テンプレート登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp089knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp089knBiz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param tempRootDir テンポラリルートディレクトリ
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 署名情報の取得に失敗
     */
    public void setInitData(Connection con, Ntp089knParamModel paramMdl, RequestModel reqMdl,
                                    String tempRootDir)
    throws SQLException, IOToolsException {
        //備考(表示用)を設定
        paramMdl.setNtp089knBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getNtp089biko(), "")));

        //役職名(表示用)を設定
        CmnPositionDao positionDao = new CmnPositionDao(con);
        CmnPositionModel posData = positionDao.getPosInfo(paramMdl.getNtp089position());
        if (posData != null) {
            paramMdl.setNtp089knpositionName(posData.getPosName());
            if (posData.getPosSid() != GSConst.POS_DEFAULT) {
                paramMdl.setNtp089knpositionName(
                        paramMdl.getNtp089knpositionName() + "以上");
            }
        } else {
            paramMdl.setNtp089position(0);
        }

        paramMdl.setNtp089positionAuth(GSConst.SP_AUTH_VIEWONLY);

        //制限対象、許可ユーザを設定
        __setSelectUserList(con, paramMdl);
    }

    /**
     * <br>[機  能] ユーザ一覧を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    private void __setSelectUserList(Connection con, Ntp089knParamModel paramMdl)
    throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //制限対象を設定
        paramMdl.setNtp089knSubjectList(
                cmnBiz.getUserLabelList(con, paramMdl.getNtp089subject()));

        //アクセス権限を設定
        paramMdl.setNtp089knAccessList(
                cmnBiz.getUserLabelList(con, paramMdl.getNtp089accessUser()));
    }

    /**
     * <br>[機  能] 特例アクセス情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param mtCon 採番コントローラ
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void registerAccessData(Connection con, Ntp089knParamModel paramMdl,
            MlCountMtController mtCon, RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        int editMode = paramMdl.getNtp088editMode();
        int nsaSid = paramMdl.getNtp088editData();
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        UDate now = new UDate();

        //特例アクセス情報の登録
        NtpSpaccessDao accessDao = new NtpSpaccessDao(con);
        NtpSpaccessModel accessMdl = new NtpSpaccessModel();
        accessMdl.setNsaSid(nsaSid);
        accessMdl.setNsaName(paramMdl.getNtp089name());
        accessMdl.setNsaBiko(paramMdl.getNtp089biko());
        accessMdl.setNsaEuid(sessionUserSid);
        accessMdl.setNsaEdate(now);
        if (editMode == Ntp088Const.EDITMODE_EDIT) {
            accessDao.update(accessMdl);
        } else {
            nsaSid = (int) mtCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                                                                SaibanModel.SBNSID_SUB_SCH_SPACCESS,
                                                                sessionUserSid);
            accessMdl.setNsaSid(nsaSid);
            accessMdl.setNsaAuid(sessionUserSid);
            accessMdl.setNsaAdate(now);
            accessDao.insert(accessMdl);
        }

        //特例アクセス_制限対象、特例アクセス_許可対象の変更前登録情報を削除
        NtpSpaccessTargetDao accessTargetDao = new NtpSpaccessTargetDao(con);
        NtpSpaccessPermitDao accessPermitDao = new NtpSpaccessPermitDao(con);
        if (editMode == Ntp088Const.EDITMODE_EDIT) {
            accessTargetDao.delete(nsaSid);
            accessPermitDao.delete(nsaSid);
        }

        //特例アクセス_制限対象の登録
        NtpSpaccessTargetModel accessTargetMdl = new NtpSpaccessTargetModel();
        accessTargetMdl.setNsaSid(nsaSid);
        for (String targetSid : paramMdl.getNtp089subject()) {
            if (targetSid.startsWith("G")) {
                accessTargetMdl.setNstType(GSConst.ST_TYPE_GROUP);
                accessTargetMdl.setNstTsid(Integer.parseInt(targetSid.substring(1)));
            } else {
                accessTargetMdl.setNstType(GSConst.ST_TYPE_USER);
                accessTargetMdl.setNstTsid(Integer.parseInt(targetSid));
            }
            accessTargetDao.insert(accessTargetMdl);
        }

        //特例アクセス_許可対象の登録
        __registSpaccessPermit(accessPermitDao, nsaSid, paramMdl.getNtp089accessUser(),
                                            GSConst.SP_AUTH_VIEWONLY);
        __registSpaccessPermit(accessPermitDao, nsaSid, paramMdl.getNtp089editUser(),
                                            GSConst.SP_AUTH_EDIT);

        int positionSid = paramMdl.getNtp089position();
        if (positionSid > 0) {
            NtpSpaccessPermitModel accessPermitMdl = new NtpSpaccessPermitModel();
            accessPermitMdl.setNsaSid(nsaSid);
            accessPermitMdl.setNspType(GSConst.SP_TYPE_POSITION);
            accessPermitMdl.setNspPsid(positionSid);
            accessPermitMdl.setNspAuth(GSConst.SP_AUTH_VIEWONLY);
            accessPermitDao.insert(accessPermitMdl);
        }
    }

    /**
     * <br>[機  能] スケジュール特例アクセス_許可対象を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param accessPermitDao スケジュール特例アクセス_許可対象DAO
     * @param ssaSid スケジュール特例アクセスSID
     * @param permitList 許可対象
     * @param sspAuth 権限区分
     * @throws SQLException SQL実行時例外
     */
    private void __registSpaccessPermit(NtpSpaccessPermitDao accessPermitDao,
                                                    int ssaSid, String[] permitList, int sspAuth)
    throws SQLException {
        NtpSpaccessPermitModel accessPermitMdl = new NtpSpaccessPermitModel();
        accessPermitMdl.setNsaSid(ssaSid);
        accessPermitMdl.setNspAuth(sspAuth);
        for (String permitSid : permitList) {
            if (permitSid.startsWith("G")) {
                accessPermitMdl.setNspType(GSConst.SP_TYPE_GROUP);
                accessPermitMdl.setNspPsid(Integer.parseInt(permitSid.substring(1)));
            } else {
                accessPermitMdl.setNspType(GSConst.SP_TYPE_USER);
                accessPermitMdl.setNspPsid(Integer.parseInt(permitSid));
            }
            accessPermitDao.insert(accessPermitMdl);
        }
    }
}