package jp.groupsession.v2.fil.fil290kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FilePcbOwnerDao;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FilePcbOwnerModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 管理者設定 個人キャビネット使用許可設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil290knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil290knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil290knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil290knParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil290knParamModel paramMdl) throws SQLException {

        log__.debug("fil290knBiz Start");

        //アクセス権限 フルアクセス一覧
        ArrayList<UsrLabelValueBean> cabinetAuthLabel = null;
        String[] svUsers = paramMdl.getFil290CabinetSv();
        if (svUsers != null && svUsers.length >= 0) {
            //取得するユーザSID・グループSID
            UserGroupSelectBiz selBiz = new UserGroupSelectBiz();
            cabinetAuthLabel = selBiz.getSelectedLabel(svUsers, con__);
        }
        paramMdl.setFil290knCabinetAuthLabel(cabinetAuthLabel);
    }

    /**
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil290knParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行例外
     */
    public void registerData(Fil290knParamModel paramMdl, BaseUserModel buMdl) throws SQLException {

        log__.debug("fil290knBiz Regist");

        int sessionUsrSid = buMdl.getUsrsid();
        String[] usrSidList = paramMdl.getFil290CabinetSv();
        UDate now = new UDate();

        FilePcbOwnerDao pcbDao = new FilePcbOwnerDao(con__);
        FileAconfModel aconf = new FileAconfModel();
        aconf.init(); // 初期値入力(新規作成時に対応する為)

        //キャビネット作成権限情報を削除する。
        pcbDao.delete(); // 一旦、更新の為に全削除

        // 登録する個人キャビネット設定情報を入力
        int personalKbn = NullDefault.getInt(paramMdl.getFil290CabinetUseKbn(),
                                             GSConstFile.CABINET_PRIVATE_NOT_USE);
        aconf.setFacPersonalKbn(personalKbn);
        if (personalKbn == GSConstFile.CABINET_PRIVATE_USE) {
            // 個人キャビネット使用時のみ設定情報を反映(それ以外は初期設定値)
            aconf.setFacUseKbn(NullDefault.getInt(paramMdl.getFil290CabinetAuthKbn(),
                                                  GSConstFile.CABINET_AUTH_ALL));
            aconf.setFacPersonalCapa(NullDefault.getInt(paramMdl.getFil290CapaKbn(),
                                                        GSConstFile.CAPA_KBN_OFF));
            aconf.setFacPersonalSize(NullDefault.getInt(paramMdl.getFil290CapaSize(), 0));
            aconf.setFacPersonalWarn(NullDefault.getInt(paramMdl.getFil290CapaWarn(), 0));
            aconf.setFacPersonalVer(NullDefault.getInt(paramMdl.getFil290VerKbn(), 0));

            // マイキャビネット使用許可ユーザを追加
            if (usrSidList != null) {
                for (int i = 0; i < usrSidList.length; i++) {
                    String usrSid = usrSidList[i];

                    int usrKbn = GSConstFile.USER_KBN_USER; // 通常はユーザSIDとして扱う

                    if (usrSid.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                        // 先頭文字が「G」の場合、グループSID として扱う
                        usrKbn = GSConstFile.USER_KBN_GROUP;
                        usrSid = usrSid.substring(UserGroupSelectBiz.GROUP_PREFIX.length());
                    }

                    FilePcbOwnerModel pcbModel = new FilePcbOwnerModel();
                    pcbModel.setUsrKbn(usrKbn);
                    pcbModel.setUsrSid(Integer.parseInt(usrSid));
                    pcbDao.insert(pcbModel);
                }
            }
            //個人キャビネットのバージョン管理世代を更新
            FileCabinetDao fcbDao = new FileCabinetDao(con__);
            fcbDao.updatePersonalVerKbn(aconf.getFacPersonalVer());
        }
        aconf.setFacPersonalEdate(now);
        aconf.setFacEdate(now);
        aconf.setFacEuid(sessionUsrSid);

        //管理者設定を更新
        FileAconfDao aconfDao = new FileAconfDao(con__);
        if (aconfDao.updatePersonal(aconf) < 1) {
            aconf.setFacAdate(now);
            aconf.setFacAuid(sessionUsrSid);
            aconf.setFacWarnCnt(0);
            aconfDao.insert(aconf);
        }
    }
}