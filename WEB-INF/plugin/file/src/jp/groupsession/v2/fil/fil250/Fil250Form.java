package jp.groupsession.v2.fil.fil250;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil200.Fil200Form;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 更新通知一括設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil250Form extends Fil200Form implements ISelectorUseForm {

    /** 選択キャビネットSID */
    private String fil250SltCabinetSid__ = null;
    /** 削除ディレクトリSID */
    private String fil250DirSid__ = null;
    /** ディレクトリパス */
    private String fil250DirPath__ = null;
    /** ルートディレクトリパス */
    private String fil250RootDirSid__ = null;
    /** ルートディレクトリ名 */
    private String fil250RootDirName__ = null;

    /** 更新通知対象者 */
    private String[] fil250SvCallUser__ = null;
    /** 更新通知対象者(候補) グループ*/
    private String fil250callUserSltGroup__ = null;
    /** 更新通知対象者 UI */
    private UserGroupSelector fil250CallUserUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("fil.125", null))
            .chainType(EnumSelectType.USER)
            .chainSelect(Select.builder()
                    .chainParameterName(
                            "fil250SvCallUser")
                    )
            .chainGroupSelectionParamName("fil250callUserSltGroup")
            .build();

    /** キャビネット区分 */
    private int fil250cabinetKbn__ = GSConstFile.CABINET_KBN_PUBLIC;

    /** キャビネットリスト */
    private List<LabelValueBean> fil250cabinetList__ = new ArrayList<LabelValueBean>();

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        errors = validateCheckDir(req, con);

        //更新通知対象者
        if (fil250SvCallUser__ == null || fil250SvCallUser__.length < 1) {
            String textCallUser = gsMsg.getMessage(req, "fil.125");
            msg = new ActionMessage("error.select.required.text", textCallUser);
            StrutsUtil.addMessage(errors, msg, "fil250SvCallUser");
        }
        return errors;
    }
    
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheckDir(
            HttpServletRequest req, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();

        //ディレクトリ
        if (StringUtil.isNullZeroString(fil250DirSid__)) {
            String textFolder = gsMsg.getMessage(req, "cmn.folder");
            msg = new ActionMessage("error.select.required.text", textFolder);
            StrutsUtil.addMessage(errors, msg, "fil250DirSid");
        } else {
            FileDirectoryDao fdrDao = new FileDirectoryDao(con);
            FileDirectoryModel fdrMdl = fdrDao.getNewDirectory(Integer.parseInt(fil250DirSid__));
            FileCabinetDao fcbDao = new FileCabinetDao(con);
            FileCabinetModel fcbMdl = null;
            if (fdrMdl != null && fdrMdl.getFdrJtkbn() != GSConstFile.JTKBN_DELETE) {
                fcbMdl = fcbDao.select(fdrMdl.getFcbSid());
            }
            
            if (fdrMdl == null
                    || fdrMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE
                    || fcbMdl == null
                    || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE
                    || fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
                String textFolder = gsMsg.getMessage(req, "cmn.folder");
                msg = new ActionMessage("error.input.notvalidate.data", textFolder);
                StrutsUtil.addMessage(errors, msg, "fil250DirSid");
            }
        }
        return errors;
    }

    /**
     * <p>fil250cabinetList を取得します。
     * @return fil250cabinetList
     */
    public List<LabelValueBean> getFil250cabinetList() {
        return fil250cabinetList__;
    }

    /**
     * <p>fil250cabinetList をセットします。
     * @param fil250cabinetList fil250cabinetList
     */
    public void setFil250cabinetList(List<LabelValueBean> fil250cabinetList) {
        fil250cabinetList__ = fil250cabinetList;
    }

    /**
     * <p>fil250DirSid を取得します。
     * @return fil250DirSid
     */
    public String getFil250DirSid() {
        return fil250DirSid__;
    }

    /**
     * <p>fil250DirSid をセットします。
     * @param fil250DirSid fil250DirSid
     */
    public void setFil250DirSid(String fil250DirSid) {
        fil250DirSid__ = fil250DirSid;
    }

    /**
     * <p>fil250SltCabinetSid を取得します。
     * @return fil250SltCabinetSid
     */
    public String getFil250SltCabinetSid() {
        return fil250SltCabinetSid__;
    }

    /**
     * <p>fil250SltCabinetSid をセットします。
     * @param fil250SltCabinetSid fil250SltCabinetSid
     */
    public void setFil250SltCabinetSid(String fil250SltCabinetSid) {
        fil250SltCabinetSid__ = fil250SltCabinetSid;
    }

    /**
     * <p>fil250DirPath を取得します。
     * @return fil250DirPath
     */
    public String getFil250DirPath() {
        return fil250DirPath__;
    }

    /**
     * <p>fil250DirPath をセットします。
     * @param fil250DirPath fil250DirPath
     */
    public void setFil250DirPath(String fil250DirPath) {
        fil250DirPath__ = fil250DirPath;
    }

    /**
     * <p>fil250RootDirSid を取得します。
     * @return fil250RootDirSid
     */
    public String getFil250RootDirSid() {
        return fil250RootDirSid__;
    }

    /**
     * <p>fil250RootDirSid をセットします。
     * @param fil250RootDirSid fil250RootDirSid
     */
    public void setFil250RootDirSid(String fil250RootDirSid) {
        fil250RootDirSid__ = fil250RootDirSid;
    }

    /**
     * <p>fil250RootDirName を取得します。
     * @return fil250RootDirName
     */
    public String getFil250RootDirName() {
        return fil250RootDirName__;
    }

    /**
     * <p>fil250RootDirName をセットします。
     * @param fil250RootDirName fil250RootDirName
     */
    public void setFil250RootDirName(String fil250RootDirName) {
        fil250RootDirName__ = fil250RootDirName;
    }

    /**
     * <p>fil250cabinetKbn を取得します。
     * @return fil250cabinetKbn
     * @see jp.groupsession.v2.fil.fil250.Fil250Form#fil250cabinetKbn__
     */
    public int getFil250cabinetKbn() {
        return fil250cabinetKbn__;
    }

    /**
     * <p>fil250cabinetKbn をセットします。
     * @param fil250cabinetKbn fil250cabinetKbn
     * @see jp.groupsession.v2.fil.fil250.Fil250Form#fil250cabinetKbn__
     */
    public void setFil250cabinetKbn(int fil250cabinetKbn) {
        fil250cabinetKbn__ = fil250cabinetKbn;
    }

    /**
     * <p>fil250callUserSltGroup を取得します。
     * @return fil250callUserSltGroup
     */
    public String getFil250callUserSltGroup() {
        return fil250callUserSltGroup__;
    }

    /**
     * <p>fil250callUserSltGroup をセットします。
     * @param fil250callUserSltGroup fil250callUserSltGroup
     */
    public void setFil250callUserSltGroup(String fil250callUserSltGroup) {
        fil250callUserSltGroup__ = fil250callUserSltGroup;
    }

    /**
     * <p>fil250SvCallUser を取得します。
     * @return fil250SvCallUser
     */
    public String[] getFil250SvCallUser() {
        return fil250SvCallUser__;
    }

    /**
     * <p>fil250SvCallUser をセットします。
     * @param fil250SvCallUser fil250SvCallUser
     */
    public void setFil250SvCallUser(String[] fil250SvCallUser) {
        fil250SvCallUser__ = fil250SvCallUser;
    }

    /**
     * <p>fil250CallUserUI を取得します。
     * @return fil250CallUserUI
     * @see jp.groupsession.v2.fil.fil250.Fil250Form#fil250CallUserUI__
     */
    public UserGroupSelector getFil250CallUserUI() {
        return fil250CallUserUI__;
    }

    /**
     * <p>fil250CallUserUI をセットします。
     * @param fil250CallUserUI fil250CallUserUI
     * @see jp.groupsession.v2.fil.fil250.Fil250Form#fil250CallUserUI__
     */
    public void setFil250CallUserUI(UserGroupSelector fil250CallUserUI) {
        fil250CallUserUI__ = fil250CallUserUI;
    }
}
