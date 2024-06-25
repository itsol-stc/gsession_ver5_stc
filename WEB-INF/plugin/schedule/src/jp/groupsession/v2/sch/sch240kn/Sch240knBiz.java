package jp.groupsession.v2.sch.sch240kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.sch.dao.SchSpaccessDao;
import jp.groupsession.v2.sch.dao.SchSpaccessPermitDao;
import jp.groupsession.v2.sch.dao.SchSpaccessTargetDao;
import jp.groupsession.v2.sch.model.SchSpaccessModel;
import jp.groupsession.v2.sch.model.SchSpaccessPermitModel;
import jp.groupsession.v2.sch.model.SchSpaccessTargetModel;
import jp.groupsession.v2.sch.sch230.Sch230Const;
import jp.groupsession.v2.sch.sch240.Sch240Biz;
import jp.groupsession.v2.sch.sch240.Sch240Form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] スケジュール 特例アクセス登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch240knBiz extends Sch240Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch240knBiz.class);

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
    public void setInitData(Connection con, Sch240knParamModel paramMdl, RequestModel reqMdl,
                                    String tempRootDir)
    throws SQLException, IOToolsException {
        //備考(表示用)を設定
        paramMdl.setSch240knBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getSch240biko(), "")));

        //役職名(表示用)を設定
        CmnPositionDao positionDao = new CmnPositionDao(con);
        CmnPositionModel posData = positionDao.getPosInfo(paramMdl.getSch240position());
        if (posData != null) {
            paramMdl.setSch240knpositionName(posData.getPosName());
            if (posData.getPosSid() != GSConst.POS_DEFAULT) {
                paramMdl.setSch240knpositionName(
                        paramMdl.getSch240knpositionName() + "以上");
            }
        } else {
            paramMdl.setSch240position(0);
        }

        //制限対象、許可ユーザを設定
        _setSelectAccessCombo(con, paramMdl);
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
    public void entryAccessData(Connection con, Sch240knParamModel paramMdl,
            MlCountMtController mtCon, RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        int editMode = paramMdl.getSch230editMode();
        int ssaSid = paramMdl.getSch230editData();
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        UDate now = new UDate();

        //特例アクセス情報の登録
        SchSpaccessDao accessDao = new SchSpaccessDao(con);
        SchSpaccessModel accessMdl = new SchSpaccessModel();
        accessMdl.setSsaSid(ssaSid);
        accessMdl.setSsaName(paramMdl.getSch240name());
        accessMdl.setSsaBiko(paramMdl.getSch240biko());
        accessMdl.setSsaEuid(sessionUserSid);
        accessMdl.setSsaEdate(now);
        if (editMode == Sch230Const.EDITMODE_EDIT) {
            accessDao.update(accessMdl);
        } else {
            ssaSid = (int) mtCon.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                                                                SaibanModel.SBNSID_SUB_SCH_SPACCESS,
                                                                sessionUserSid);
            accessMdl.setSsaSid(ssaSid);
            accessMdl.setSsaAuid(sessionUserSid);
            accessMdl.setSsaAdate(now);
            accessDao.insert(accessMdl);
        }

        //特例アクセス_制限対象、特例アクセス_許可対象の変更前登録情報を削除
        SchSpaccessTargetDao accessTargetDao = new SchSpaccessTargetDao(con);
        SchSpaccessPermitDao accessPermitDao = new SchSpaccessPermitDao(con);
        if (editMode == Sch230Const.EDITMODE_EDIT) {
            accessTargetDao.delete(ssaSid);
            accessPermitDao.delete(ssaSid);
        }

        //特例アクセス_制限対象の登録
        SchSpaccessTargetModel accessTargetMdl = new SchSpaccessTargetModel();
        accessTargetMdl.setSsaSid(ssaSid);
        for (String targetSid : paramMdl.getSch240subject()) {
            if (targetSid.startsWith("G")) {
                accessTargetMdl.setSstType(GSConstSchedule.SST_TYPE_GROUP);
                accessTargetMdl.setSstTsid(Integer.parseInt(targetSid.substring(1)));
            } else {
                accessTargetMdl.setSstType(GSConstSchedule.SST_TYPE_USER);
                accessTargetMdl.setSstTsid(Integer.parseInt(targetSid));
            }
            accessTargetDao.insert(accessTargetMdl);
        }

        //特例アクセス_許可対象の登録
        __registSpaccessPermit(accessPermitDao, ssaSid, paramMdl.getSch240accessUser(),
                                            GSConstSchedule.SSP_AUTH_VIEWONLY);
        __registSpaccessPermit(accessPermitDao, ssaSid, paramMdl.getSch240editUser(),
                                            GSConstSchedule.SSP_AUTH_EDIT);

        int positionSid = paramMdl.getSch240position();
        if (positionSid > 0) {
            SchSpaccessPermitModel accessPermitMdl = new SchSpaccessPermitModel();
            accessPermitMdl.setSsaSid(ssaSid);
            accessPermitMdl.setSspType(GSConstSchedule.SSP_TYPE_POSITION);
            accessPermitMdl.setSspPsid(positionSid);
            if (paramMdl.getSch240positionAuth() == Sch240Form.POTION_AUTH_EDIT) {
                accessPermitMdl.setSspAuth(GSConstSchedule.SSP_AUTH_EDIT);
            } else {
                accessPermitMdl.setSspAuth(GSConstSchedule.SSP_AUTH_VIEWONLY);
            }
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
    private void __registSpaccessPermit(SchSpaccessPermitDao accessPermitDao,
                                                    int ssaSid, String[] permitList, int sspAuth)
    throws SQLException {
        SchSpaccessPermitModel accessPermitMdl = new SchSpaccessPermitModel();
        accessPermitMdl.setSsaSid(ssaSid);
        accessPermitMdl.setSspAuth(sspAuth);
        for (String permitSid : permitList) {
            if (permitSid.startsWith("G")) {
                accessPermitMdl.setSspType(GSConstSchedule.SSP_TYPE_GROUP);
                accessPermitMdl.setSspPsid(Integer.parseInt(permitSid.substring(1)));
            } else {
                accessPermitMdl.setSspType(GSConstSchedule.SSP_TYPE_USER);
                accessPermitMdl.setSspPsid(Integer.parseInt(permitSid));
            }
            accessPermitDao.insert(accessPermitMdl);
        }
    }
}
