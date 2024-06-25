package jp.groupsession.v2.fil.fil090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil080.Fil080ParamModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] フォルダ・ファイル移動画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil090ParamModel extends Fil080ParamModel {

    /** 画面モード 0:フォルダ 1:ファイル */
    private String fil090Mode__ = null;

    /** 選択キャビネットSID */
    private String fil090CabinetSid__ = null;
    /** ディレクトリSID */
    private String fil090DirSid__ = null;
    /** ディレクトリ名 */
    private String fil090DirName__ = null;
    /** ディレクトリ備考 */
    private String fil090Biko__ = null;
    /** バージョン管理区分 */
    private String fil090VerKbn__ = null;

    /** バイナリSID */
    private String fil090BinSid__ = null;
    /** ファイルコンボ */
    private List<LabelValueBean> fil090FileLabelList__ = null;

    /** 移動先ディレクトリパス */
    private String fil090SltDirPath__ = null;
    /** 階層リスト */
    private List<LabelValueBean> fil090cabinetList__ = new ArrayList<LabelValueBean>();

    /** フォルダ名リスト(一括移動時使用) */
    private List<String> fil090FolderNameList__ = null;

    /** 更新者ID */
    private String fil090EditId__ = null;
    /** グループ一覧 */
    private List<LabelValueBean> fil090groupList__ = null;

    /** 選択キャビネットSID */
    private int fil090SelectCabinetSid__ = -1;
    /** キャビネットコンボリスト */
    private List<FileCabinetModel> fil090CabinetCombo__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors fil090validateCheck(Connection con, RequestModel reqMdl)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textMovefolder = gsMsg.getMessage("fil.75");

        //移動先未選択
        if (StringUtil.isNullZeroString(fil090SltDirPath__) || fil090SltDirPath__.equals("/")) {
            msg = new ActionMessage("error.select.required.text", textMovefolder);
            StrutsUtil.addMessage(errors, msg, "selectDir");
            return errors;
        }


        //移動後ディレクトリ11階層以上
        if (isOverLevel(con, reqMdl)) {
            msg = new ActionMessage("error.over.level.dir", GSConstFile.MAX_LEVEL);
            StrutsUtil.addMessage(errors, msg, "fil090DirSid");
            return errors;
        }

        return errors;
    }

    /**
     * <br>[機  能] ディレクトリ移動後に11階層以上になるか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return true:11階層以上　false:10階層以下
     * @throws SQLException SQL実行例外
     */
    public boolean isOverLevel(Connection con, RequestModel reqMdl) throws SQLException {
        boolean ret = false;

        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        Fil090Biz biz = new Fil090Biz(reqMdl, con);
        int dirLevel = 0;
        int sleDirLevel = 0;

        //移動先ディレクトリの階層数を取得する。
        FileDirectoryModel dirModel
                = dirDao.getNewDirectory(NullDefault.getInt(getMoveToDir(), -1));
        if (dirModel != null) {
            sleDirLevel = dirModel.getFdrLevel();
        }

        if (getFil090SelectPluralKbn() == Fil090Biz.MOVE_PLURAL_NO) {

            //移動するディレクトリの下階層数を取得する。
            dirLevel = biz.getMaxLevel(NullDefault.getInt(fil090DirSid__, -1));

            if ((sleDirLevel + dirLevel) > GSConstFile.MAX_LEVEL) {
                ret = true;
            }

        } else {
            String[] fil040select = getFil040SelectDel();
            for (String fdrSid : fil040select) {
                dirLevel = biz.getMaxLevel(NullDefault.getInt(fdrSid, -1));

                if ((sleDirLevel + dirLevel) > GSConstFile.MAX_LEVEL) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * <p>fil090Biko を取得します。
     * @return fil090Biko
     */
    public String getFil090Biko() {
        return fil090Biko__;
    }


    /**
     * <p>fil090Biko をセットします。
     * @param fil090Biko fil090Biko
     */
    public void setFil090Biko(String fil090Biko) {
        fil090Biko__ = fil090Biko;
    }


    /**
     * <p>fil090BinSid を取得します。
     * @return fil090BinSid
     */
    public String getFil090BinSid() {
        return fil090BinSid__;
    }


    /**
     * <p>fil090BinSid をセットします。
     * @param fil090BinSid fil090BinSid
     */
    public void setFil090BinSid(String fil090BinSid) {
        fil090BinSid__ = fil090BinSid;
    }


    /**
     * <p>fil090cabinetList を取得します。
     * @return fil090cabinetList
     */
    public List<LabelValueBean> getFil090cabinetList() {
        return fil090cabinetList__;
    }


    /**
     * <p>fil090cabinetList をセットします。
     * @param fil090cabinetList fil090cabinetList
     */
    public void setFil090cabinetList(List<LabelValueBean> fil090cabinetList) {
        fil090cabinetList__ = fil090cabinetList;
    }


    /**
     * <p>fil090CabinetSid を取得します。
     * @return fil090CabinetSid
     */
    public String getFil090CabinetSid() {
        return fil090CabinetSid__;
    }


    /**
     * <p>fil090CabinetSid をセットします。
     * @param fil090CabinetSid fil090CabinetSid
     */
    public void setFil090CabinetSid(String fil090CabinetSid) {
        fil090CabinetSid__ = fil090CabinetSid;
    }


    /**
     * <p>fil090DirName を取得します。
     * @return fil090DirName
     */
    public String getFil090DirName() {
        return fil090DirName__;
    }


    /**
     * <p>fil090DirName をセットします。
     * @param fil090DirName fil090DirName
     */
    public void setFil090DirName(String fil090DirName) {
        fil090DirName__ = fil090DirName;
    }


    /**
     * <p>fil090DirSid を取得します。
     * @return fil090DirSid
     */
    public String getFil090DirSid() {
        return fil090DirSid__;
    }


    /**
     * <p>fil090DirSid をセットします。
     * @param fil090DirSid fil090DirSid
     */
    public void setFil090DirSid(String fil090DirSid) {
        fil090DirSid__ = fil090DirSid;
    }


    /**
     * <p>fil090FileLabelList を取得します。
     * @return fil090FileLabelList
     */
    public List<LabelValueBean> getFil090FileLabelList() {
        return fil090FileLabelList__;
    }


    /**
     * <p>fil090FileLabelList をセットします。
     * @param fil090FileLabelList fil090FileLabelList
     */
    public void setFil090FileLabelList(List<LabelValueBean> fil090FileLabelList) {
        fil090FileLabelList__ = fil090FileLabelList;
    }


    /**
     * <p>fil090SltDirPath を取得します。
     * @return fil090SltDirPath
     */
    public String getFil090SltDirPath() {
        return fil090SltDirPath__;
    }


    /**
     * <p>fil090SltDirPath をセットします。
     * @param fil090SltDirPath fil090SltDirPath
     */
    public void setFil090SltDirPath(String fil090SltDirPath) {
        fil090SltDirPath__ = fil090SltDirPath;
    }


    /**
     * <p>fil090Mode を取得します。
     * @return fil090Mode
     */
    public String getFil090Mode() {
        return fil090Mode__;
    }


    /**
     * <p>fil090Mode をセットします。
     * @param fil090Mode fil090Mode
     */
    public void setFil090Mode(String fil090Mode) {
        fil090Mode__ = fil090Mode;
    }

    /**
     * <p>fil090VerKbn を取得します。
     * @return fil090VerKbn
     */
    public String getFil090VerKbn() {
        return fil090VerKbn__;
    }

    /**
     * <p>fil090VerKbn をセットします。
     * @param fil090VerKbn fil090VerKbn
     */
    public void setFil090VerKbn(String fil090VerKbn) {
        fil090VerKbn__ = fil090VerKbn;
    }

    /**
     * <p>fil090FolderNameList を取得します。
     * @return fil090FolderNameList
     */
    public List<String> getFil090FolderNameList() {
        return fil090FolderNameList__;
    }

    /**
     * <p>fil090FolderNameList をセットします。
     * @param fil090FolderNameList fil090FolderNameList
     */
    public void setFil090FolderNameList(List<String> fil090FolderNameList) {
        fil090FolderNameList__ = fil090FolderNameList;
    }

    /**
     * <p>fil090EditId を取得します。
     * @return fil090EditId
     */
    public String getFil090EditId() {
        return fil090EditId__;
    }

    /**
     * <p>fil090EditId をセットします。
     * @param fil090EditId fil090EditId
     */
    public void setFil090EditId(String fil090EditId) {
        fil090EditId__ = fil090EditId;
    }

    /**
     * <p>fil090groupList を取得します。
     * @return fil090groupList
     */
    public List<LabelValueBean> getFil090groupList() {
        return fil090groupList__;
    }

    /**
     * <p>fil090groupList をセットします。
     * @param fil090groupList fil090groupList
     */
    public void setFil090groupList(List<LabelValueBean> fil090groupList) {
        fil090groupList__ = fil090groupList;
    }

    /**
     * <p>fil090SelectCabinetSid を取得します。
     * @return fil090SelectCabinetSid
     * @see jp.groupsession.v2.fil.fil090.Fil090ParamModel#fil090SelectCabinetSid__
     */
    public int getFil090SelectCabinetSid() {
        return fil090SelectCabinetSid__;
    }

    /**
     * <p>fil090SelectCabinetSid をセットします。
     * @param fil090SelectCabinetSid fil090SelectCabinetSid
     * @see jp.groupsession.v2.fil.fil090.Fil090ParamModel#fil090SelectCabinetSid__
     */
    public void setFil090SelectCabinetSid(int fil090SelectCabinetSid) {
        fil090SelectCabinetSid__ = fil090SelectCabinetSid;
    }

    /**
     * <p>fil090CabinetCombo を取得します。
     * @return fil090CabinetCombo
     * @see jp.groupsession.v2.fil.fil090.Fil090ParamModel#fil090CabinetCombo__
     */
    public List<FileCabinetModel> getFil090CabinetCombo() {
        return fil090CabinetCombo__;
    }

    /**
     * <p>fil090CabinetCombo をセットします。
     * @param fil090CabinetCombo fil090CabinetCombo
     * @see jp.groupsession.v2.fil.fil090.Fil090ParamModel#fil090CabinetCombo__
     */
    public void setFil090CabinetCombo(List<FileCabinetModel> fil090CabinetCombo) {
        fil090CabinetCombo__ = fil090CabinetCombo;
    }



}