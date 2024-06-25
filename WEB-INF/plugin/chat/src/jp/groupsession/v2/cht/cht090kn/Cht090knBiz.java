package jp.groupsession.v2.cht.cht090kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht080.Cht080Const;
import jp.groupsession.v2.cht.dao.ChtSpaccessDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessPermitDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessTargetDao;
import jp.groupsession.v2.cht.model.ChtSpaccessModel;
import jp.groupsession.v2.cht.model.ChtSpaccessPermitModel;
import jp.groupsession.v2.cht.model.ChtSpaccessTargetModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;

/**
 * <br>[機  能] チャット 特例アクセス登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht090knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht090knBiz.class);
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
    public void setInitData(Connection con, Cht090knParamModel paramMdl, RequestModel reqMdl,
                                    String tempRootDir)
    throws SQLException, IOToolsException {
        //備考(表示用)を設定
        paramMdl.setCht090knBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getCht090biko(), "")));

        //役職名(表示用)を設定
        CmnPositionDao positionDao = new CmnPositionDao(con);
        CmnPositionModel posData = positionDao.getPosInfo(paramMdl.getCht090position());
        if (posData != null) {
            paramMdl.setCht090knpositionName(posData.getPosName());
            if (posData.getPosSid() != GSConst.POS_DEFAULT) {
                paramMdl.setCht090knpositionName(
                        paramMdl.getCht090knpositionName() + "以上");
            }
        } else {
            paramMdl.setCht090position(0);
        }

        paramMdl.setCht090positionAuth(GSConst.SP_AUTH_VIEWONLY);

        //制限対象を設定
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setCht090knsubjectList(
                cmnBiz.getUserLabelList(con, paramMdl.getCht090subject()));

        //アクセス権限を設定
        paramMdl.setCht090knaccessList(
                cmnBiz.getUserLabelList(con, paramMdl.getCht090accessUser()));
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
    public void registerAccessData(Connection con, Cht090knParamModel paramMdl,
            MlCountMtController mtCon, RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        int editMode = paramMdl.getCht080editMode();
        int chsSid = paramMdl.getCht080editData();
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        UDate now = new UDate();

        //特例アクセス情報の登録
        ChtSpaccessDao accessDao = new ChtSpaccessDao(con);
        ChtSpaccessModel accessMdl = new ChtSpaccessModel();
        accessMdl.setChsSid(chsSid);
        accessMdl.setChsName(paramMdl.getCht090name());
        accessMdl.setChsBiko(paramMdl.getCht090biko());
        accessMdl.setChsEuid(sessionUserSid);
        accessMdl.setChsEdate(now);
        if (editMode == Cht080Const.EDITMODE_EDIT) {
            accessDao.update(accessMdl);
        } else {
            chsSid = (int) mtCon.getSaibanNumber(GSConstChat.SBNSID_CHAT,
                                                                Cht080Const.SBNSID_SUB_CHT_SPACCESS,
                                                                sessionUserSid);
            accessMdl.setChsSid(chsSid);
            accessMdl.setChsAuid(sessionUserSid);
            accessMdl.setChsAdate(now);
            accessDao.insert(accessMdl);
        }

        //特例アクセス_制限対象、特例アクセス_許可対象の変更前登録情報を削除
        ChtSpaccessTargetDao accessTargetDao = new ChtSpaccessTargetDao(con);
        ChtSpaccessPermitDao accessPermitDao = new ChtSpaccessPermitDao(con);
        if (editMode == Cht080Const.EDITMODE_EDIT) {
            accessTargetDao.delete(chsSid);
            accessPermitDao.delete(chsSid);
        }

        //特例アクセス_制限対象の登録
        ChtSpaccessTargetModel accessTargetMdl = new ChtSpaccessTargetModel();
        accessTargetMdl.setChsSid(chsSid);
        for (String targetSid : paramMdl.getCht090subject()) {
            if (targetSid.startsWith("G")) {
                accessTargetMdl.setCstType(Cht080Const.SP_TYPE_GROUP);
                accessTargetMdl.setCstPsid(Integer.parseInt(targetSid.substring(1)));
            } else {
                accessTargetMdl.setCstType(Cht080Const.SP_TYPE_USER);
                accessTargetMdl.setCstPsid(Integer.parseInt(targetSid));
            }
            accessTargetDao.insert(accessTargetMdl);
        }

        //特例アクセス_許可対象の登録
        __registSpaccessPermit(accessPermitDao, chsSid, paramMdl.getCht090accessUser());

        int positionSid = paramMdl.getCht090position();
        if (positionSid > 0) {
            ChtSpaccessPermitModel accessPermitMdl = new ChtSpaccessPermitModel();
            accessPermitMdl.setChsSid(chsSid);
            accessPermitMdl.setCspType(Cht080Const.SP_TYPE_POSITION);
            accessPermitMdl.setCspPsid(positionSid);
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
     * @throws SQLException SQL実行時例外
     */
    private void __registSpaccessPermit(ChtSpaccessPermitDao accessPermitDao,
                                                    int ssaSid, String[] permitList)
    throws SQLException {
        ChtSpaccessPermitModel accessPermitMdl = new ChtSpaccessPermitModel();
        accessPermitMdl.setChsSid(ssaSid);
        for (String permitSid : permitList) {
            if (permitSid.startsWith("G")) {
                accessPermitMdl.setCspType(Cht080Const.SP_TYPE_GROUP);
                accessPermitMdl.setCspPsid(Integer.parseInt(permitSid.substring(1)));
            } else {
                accessPermitMdl.setCspType(Cht080Const.SP_TYPE_USER);
                accessPermitMdl.setCspPsid(Integer.parseInt(permitSid));
            }
            accessPermitDao.insert(accessPermitMdl);
        }
    }
}