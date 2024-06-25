package jp.groupsession.v2.fil.fil290;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FilePcbOwnerDao;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FilePcbOwnerModel;

/**
 * <br>[機  能] 管理者設定 基本設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil290Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil290Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil290Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil290ParamModel paramMdl, HttpServletRequest req)
    throws SQLException {

        log__.debug("fil290Biz Start");

        //初期表示かどうか
        if (paramMdl.getFil290initFlg() == 0) {
            __setData(paramMdl);
        }

        //画面表示項目を設定する。
        __setDsp(paramMdl, req);


    }

    /**
     * <br>[機  能] 画面表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     */
    private void __setDsp(Fil290ParamModel paramMdl, HttpServletRequest req) throws SQLException {

        //バージョン世代ラベル設定
        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl__, con__);

        //使用率ラベル
        paramMdl.setFil290CapaWarnLavel(cmnBiz.getCapaWarnLabelList());

        //バージョン世代ラベル設定
        paramMdl.setFil290VerKbnLavel(cmnBiz.getVersionLabelList());
    }

    /**
     * <br>[機  能] DBからデータを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setData(Fil290ParamModel paramMdl) throws SQLException {


        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconf = aconfDao.select();
        if (aconf == null) {
            aconf = new FileAconfModel();
            aconf.init();
        }

        ArrayList<String> userSidList  = new ArrayList<String>();
        if (aconf.getFacUseKbn() == GSConstFile.CABINET_AUTH_USER) {
            // キャビネット使用許可が指定ユーザの場合 → 許可されたユーザ一覧取得
            FilePcbOwnerDao pcbUsrDao = new FilePcbOwnerDao(con__);
            List <FilePcbOwnerModel> pcbUsrList = pcbUsrDao.select();
            if (pcbUsrList != null && pcbUsrList.size() > 0) {
                for (FilePcbOwnerModel pcbUsr : pcbUsrList) {
                    String userSid = String.valueOf(pcbUsr.getUsrSid());
                    if (pcbUsr.getUsrKbn() == GSConstFile.USER_KBN_GROUP) {
                        userSid = UserGroupSelectBiz.GROUP_PREFIX + userSid;
                    }
                    userSidList.add(userSid);
                }
            }
        }

        if (aconf.getFacPersonalCapa() != GSConstFile.CAPA_KBN_ON) {
            // 容量制限が無い場合、関連する値を初期化
            aconf.setFacPersonalSize(0);
            aconf.setFacPersonalWarn(0);
        }

        paramMdl.setFil290CabinetUseKbn(String.valueOf(aconf.getFacPersonalKbn()));
        paramMdl.setFil290CabinetAuthKbn(String.valueOf(aconf.getFacUseKbn()));
        paramMdl.setFil290CapaKbn(String.valueOf(aconf.getFacPersonalCapa()));
        paramMdl.setFil290CapaSize(String.valueOf(aconf.getFacPersonalSize()));
        paramMdl.setFil290CapaWarn(String.valueOf(aconf.getFacPersonalWarn()));
        paramMdl.setFil290VerVisible(String.valueOf(aconf.getFacVerKbn()));
        paramMdl.setFil290VerKbn(String.valueOf(aconf.getFacPersonalVer()));
        paramMdl.setFil290CabinetSv(
                (String[]) userSidList.toArray(new String[userSidList.size()]));

        // 容量制限
        paramMdl.setFil290CapaKbn(NullDefault.getString(
                                  paramMdl.getFil290CapaKbn(),
                                  String.valueOf(GSConstFile.CAPA_KBN_OFF)));

        paramMdl.setFil290initFlg(1); // 初期データ取得完了
    }
}
