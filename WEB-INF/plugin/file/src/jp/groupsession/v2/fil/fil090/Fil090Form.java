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
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.GSValidateFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.fil080.Fil080Form;
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
public class Fil090Form extends Fil080Form {

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
     * <br>[機  能] 移動選択フォルダの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return true:エラーなし false:エラー有り
     * @throws NumberFormatException 実行例外
     * @throws SQLException 実行例外
     */
    public boolean checkSltDirAccess(Connection con, RequestModel reqMdl)
            throws NumberFormatException, SQLException {

        boolean errorFlg = true;
        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        int fcbSid = NullDefault.getInt(getFil010SelectCabinet(), -1);

        if (getFil090SelectPluralKbn() == 0) {
            //単一移動モード
            errorFlg = cmnBiz.isDirAccessAuthUser(fcbSid,
                                                  NullDefault.getInt(fil090DirSid__, -1),
                                                  Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        } else {
            //一括移動モード
            errorFlg = cmnBiz.isDirAccessAuthUser(fcbSid,
                                                  getFil040SelectDel(),
                                                  Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        }

        return errorFlg;
    }

    /**
     * <br>[機  能] 移動先フォルダの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @return true:エラーなし false:エラー有り
     * @throws NumberFormatException 実行例外
     * @throws SQLException 実行例外
     */
    public boolean checkSltMoveDirAccess(Connection con, RequestModel reqMdl)
            throws NumberFormatException, SQLException {

        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        int ecode = cmnBiz.checkPowEditDir(NullDefault.getInt(getMoveToDir(), -1),
                GSConstFile.DIRECTORY_FOLDER);

        return (ecode == FilCommonBiz.ERR_NONE);
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @param usrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ロックファイル確認時に例外発生
     */
    public ActionErrors fil090validateCheck(Connection con, RequestModel reqMdl, int usrSid)
    throws SQLException, IOToolsException {
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
        //編集権限チェック
        //ディレクトリ存在チェック
        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        int dirSid = NullDefault.getInt(getMoveToDir(), -1);
        int ecode = cmnBiz.checkPowEditDir(dirSid, GSConstFile.DIRECTORY_FOLDER);
        String targetName = "";
        int mode = NullDefault.getInt(getFil090Mode(), 0);
        if (mode == 0) {
            targetName = gsMsg.getMessage("fil.118");
        }
        if (mode == 1) {
            targetName = gsMsg.getMessage("cmn.file");
        }
        if (mode == 2) {
            targetName = gsMsg.getMessage("cmn.folder");
        }

        if (ecode == FilCommonBiz.ERR_NONE_CAB_EDIT_POWER || ecode == FilCommonBiz.ERR_NOT_DIRKBN) {
            msg = new ActionMessage("error.cant.mode.filekanri.todir", targetName);
            StrutsUtil.addMessage(errors, msg, "selectDir");
            return errors;
        }

        if (ecode == FilCommonBiz.ERR_NOT_EXIST) {
            msg = new ActionMessage("error.not.exitst.myself.dir2", targetName);
            StrutsUtil.addMessage(errors, msg, "selectDir");
            return errors;
        }

        //個人・共通キャビネットと電帳法キャビネット間の移動は禁止
        validateMoveToCabinet(con, dirSid, errors, gsMsg);
        if (!errors.isEmpty()) {
            return errors;
        }

        // 移動元ディレクトリキャビネット確認
        FileDirectoryDao dao = new FileDirectoryDao(con);
        if (getFil090SelectPluralKbn() == Fil090Biz.MOVE_PLURAL_NO) {
            FileDirectoryModel fromDirMdl = dao.getNewDirectory(
                    NullDefault.getInt(getFil090DirSid(), -1));
            if (fromDirMdl.getFdrParentSid() == GSConstFile.DIRECTORY_ROOT) {
                msg = new ActionMessage("error.file.move.cabinet");
                StrutsUtil.addMessage(errors, msg, "fdrSid");
                return errors;
            }

            // 移動元ディレクトリ:ロックファイル確認
            FilCommonBiz biz = new FilCommonBiz(reqMdl, con);
            int fcbSid = biz.getCabinetSid(NullDefault.getInt(getFil090DirSid(), -1));
            boolean fileErr = false;
            fileErr = GSValidateFile.validateLockFile(errors,
                    con,
                    reqMdl,
                    NullDefault.getInt(getFil090DirSid(), -1),
                    fcbSid,
                    usrSid,
                    fromDirMdl);

            if (fileErr) {
                return errors;
            }

            //移動先/移動元キャビネットの種類が異なっていないか確認
            ArrayList<Integer> dirSids = new ArrayList<Integer>();
            dirSids.add(dirSid);
            dirSids.add(NullDefault.getInt(getFil090DirSid(), -1));

            FileDao fileDao = new FileDao(con);
            fileErr = fileDao.isNotSameCabinetType(dirSids);
            if (fileErr) {
                return errors;
            }
        } else {
            ArrayList<Integer> dirSids = new ArrayList<Integer>();
            dirSids.add(dirSid);
            for (String selectDel : getFil040SelectDel()) {
                FileDirectoryModel fromDirMdl = dao.getNewDirectory(
                        NullDefault.getInt(selectDel, -1));
                if (fromDirMdl.getFdrParentSid() == GSConstFile.DIRECTORY_ROOT) {
                    msg = new ActionMessage("error.file.move.cabinet");
                    StrutsUtil.addMessage(errors, msg, "fdrSid");
                    return errors;
                }

                // 移動元ディレクトリ:ロックファイル確認
                FilCommonBiz biz = new FilCommonBiz(reqMdl, con);
                int fcbSid = biz.getCabinetSid(NullDefault.getInt(selectDel, -1));
                boolean fileErr = false;
                fileErr = GSValidateFile.validateLockFile(errors,
                        con,
                        reqMdl,
                        NullDefault.getInt(selectDel, -1),
                        fcbSid,
                        usrSid,
                        fromDirMdl);

                if (fileErr) {
                    return errors;
                }
                dirSids.add(NullDefault.getInt(selectDel, -1));
            }
            //移動先/移動元キャビネットの種類が異なっていないか確認
            FileDao fileDao = new FileDao(con);
            boolean fileErr = fileDao.isNotSameCabinetType(dirSids);
            if (fileErr) {
                return errors;
            }

        }

        //移動後ディレクトリ11階層以上
        //移動先ディレクトリが自分ではないかを確認
        //移動先ディレクトリの親ディレクトリを確認
        __setValidateTree(con, reqMdl, errors);
        return errors;
    }



    /**
     * <br>[機  能] フォルダ選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @param usrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors fil090validateDirbSelectCheck(
            Connection con,
            RequestModel reqMdl,
            int usrSid)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);

        //編集権限チェック
        //ディレクトリ存在チェック
        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        int dirSid = NullDefault.getInt(getMoveToDir(), -1);
        int ecode = cmnBiz.checkPowEditDir(dirSid, GSConstFile.DIRECTORY_FOLDER);
        String targetName = "";
        int mode = NullDefault.getInt(getFil090Mode(), 0);
        if (mode == 0) {
            targetName = gsMsg.getMessage("fil.118");
        }
        if (mode == 1) {
            targetName = gsMsg.getMessage("cmn.file");
        }
        if (mode == 2) {
            targetName = gsMsg.getMessage("cmn.folder");
        }

        if (ecode == FilCommonBiz.ERR_NONE_CAB_EDIT_POWER || ecode == FilCommonBiz.ERR_NOT_DIRKBN) {
            msg = new ActionMessage("error.cant.mode.filekanri.todir", targetName);
            StrutsUtil.addMessage(errors, msg, "selectDir");
            return errors;
        }

        if (ecode == FilCommonBiz.ERR_NOT_EXIST) {
            msg = new ActionMessage("error.not.exitst.myself.dir2", targetName);
            StrutsUtil.addMessage(errors, msg, "selectDir");
            return errors;
        }

        //個人・共通キャビネットと電帳法キャビネット間の移動は禁止
        validateMoveToCabinet(con, dirSid, errors, gsMsg);

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @param umodel BaseUserModel
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors fil090validateCabSelectCheck(
            Connection con, RequestModel reqMdl,
            BaseUserModel umodel)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);

        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl, con);
        int ecode = cmnBiz.checkPowViewCab(fil090SelectCabinetSid__);
        if (ecode != FilCommonBiz.ERR_NONE) {
            msg = new ActionMessage("search.data.notfound", gsMsg.getMessage("fil.23"));
            StrutsUtil.addMessage(errors, msg, "fil090SelectCabinetSid__");
            return errors;
        }
        return errors;
    }


    /**
     * <br>[機  能] ディレクトリ移動後の構造をチェックする。
     * <br>[解  説] 以下はエラーとする。
     * <br>        移動後ディレクトリ11階層以上
     * <br>        移動先ディレクトリが自分
     * <br>        移動先ディレクトリの親ディレクトリ
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @param errors アクションエラー
     * @throws SQLException SQL実行例外
     */
    private void __setValidateTree(Connection con, RequestModel reqMdl,
            ActionErrors errors) throws SQLException {
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        Fil090Biz biz = new Fil090Biz(reqMdl, con);
        ActionMessage msg = null;

        FileDirectoryModel dirModel
        = dirDao.getNewDirectory(NullDefault.getInt(getMoveToDir(), -1));

        int moveDirSid = NullDefault.getInt(fil090DirSid__, -1);
        int ecode = Fil090Biz.ECODE_NONE;

        if (getFil090SelectPluralKbn() == Fil090Biz.MOVE_PLURAL_NO) {

            ecode = biz.validateTree(dirModel, moveDirSid);

        } else {
            String[] fil040select = getFil040SelectDel();
            for (String fdrSid : fil040select) {
                moveDirSid = NullDefault.getInt(fdrSid, -1);
                ecode = biz.validateTree(dirModel, moveDirSid);
                if (ecode != Fil090Biz.ECODE_NONE) {
                    break;
                }
            }
        }
        switch (ecode) {
        case Fil090Biz.ECODE_OVER_LEVEL:
            msg = new ActionMessage("error.over.level.dir", GSConstFile.MAX_LEVEL);
            StrutsUtil.addMessage(errors, msg, "fil090DirSid");
            break;
        case Fil090Biz.ECODE_MOVETOSELF:
            msg = new ActionMessage("error.dir.move3");
            StrutsUtil.addMessage(errors, msg, "itSelf");
            break;

        case Fil090Biz.ECODE_MOVETOCHILD:
            msg = new ActionMessage("error.dir.move1");
            StrutsUtil.addMessage(errors, msg, "loopDir");
        default:
            break;
        }
        return;
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
     * <br>[機  能] 移動先フォルダのキャビネットが正常かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param moveToDirSid 移動先フォルダのディレクトリSID
     * @param errors ActionErrors
     * @param gsMsg GsMessage
     * @throws SQLException SQL実行時例外
     */
    public void validateMoveToCabinet(Connection con,
                                        int moveToDirSid,
                                        ActionErrors errors,
                                        GsMessage gsMsg)
    throws SQLException {

        //個人・共通キャビネットと電帳法キャビネット間の移動は禁止
        FileCabinetDao cabDao = new FileCabinetDao(con);
        FileDirectoryDao dirDao = new FileDirectoryDao(con);
        FileDirectoryModel dirModel = dirDao.getNewDirectory(moveToDirSid);

        FileCabinetModel cabMdl = cabDao.select(NullDefault.getInt(getFil010SelectCabinet(), -1));
        FileCabinetModel moveToCabMdl = cabDao.select(dirModel.getFcbSid());
        if (cabMdl == null
        || cabMdl.getFcbJkbn() != GSConst.JTKBN_TOROKU
        || moveToCabMdl.getFcbJkbn() != GSConst.JTKBN_TOROKU
        || cabMdl.getFcbErrl() != moveToCabMdl.getFcbErrl()) {
            ActionMessage msg = new ActionMessage("error.not.exitst.myself.dir2",
                                                    gsMsg.getMessage("cmn.folder"));
            StrutsUtil.addMessage(errors, msg, "selectDir");
        }
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
     * @see jp.groupsession.v2.fil.fil090.Fil090Form#fil090SelectCabinetSid__
     */
    public int getFil090SelectCabinetSid() {
        return fil090SelectCabinetSid__;
    }

    /**
     * <p>fil090SelectCabinetSid をセットします。
     * @param fil090SelectCabinetSid fil090SelectCabinetSid
     * @see jp.groupsession.v2.fil.fil090.Fil090Form#fil090SelectCabinetSid__
     */
    public void setFil090SelectCabinetSid(int fil090SelectCabinetSid) {
        fil090SelectCabinetSid__ = fil090SelectCabinetSid;
    }

    /**
     * <p>fil090CabinetCombo を取得します。
     * @return fil090CabinetCombo
     * @see jp.groupsession.v2.fil.fil090.Fil090Form#fil090CabinetCombo__
     */
    public List<FileCabinetModel> getFil090CabinetCombo() {
        return fil090CabinetCombo__;
    }

    /**
     * <p>fil090CabinetCombo をセットします。
     * @param fil090CabinetCombo fil090CabinetCombo
     * @see jp.groupsession.v2.fil.fil090.Fil090Form#fil090CabinetCombo__
     */
    public void setFil090CabinetCombo(List<FileCabinetModel> fil090CabinetCombo) {
        fil090CabinetCombo__ = fil090CabinetCombo;
    }




}