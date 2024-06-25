package jp.groupsession.v2.fil.fil080;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.WmlMailDataModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.FilTreeBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
import jp.groupsession.v2.fil.dao.FileDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileFileRekiDao;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.fil010.Fil010Biz;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.fil.model.FileFileRekiModel;
import jp.groupsession.v2.fil.model.FileModel;
import jp.groupsession.v2.fil.model.FileMoneyMasterModel;
import jp.groupsession.v2.fil.model.FileParentAccessDspModel;
import jp.groupsession.v2.fil.util.FilStringUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイル登録画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil080Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil080Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** ユーザSID */
    private int usrSid__;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil080Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl リクエスト情報
     * @param usrSid ユーザSID
     */
    public Fil080Biz(Connection con, RequestModel reqMdl, int usrSid) {
        con__ = con;
        reqMdl__ = reqMdl;
        usrSid__ = usrSid;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションのルートパス
     * @param buMdl セッションユーザモデル
     * @throws Exception  実行時例外
     */
    public void setInitData(
        Fil080ParamModel paramMdl,
        String tempDir,
        String appRoot,
        BaseUserModel buMdl) throws Exception {

        log__.debug("Fil080Biz Start");
        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);

        if (dirSid == -1) {
            paramMdl.setFil080Mode(GSConstFile.MODE_ADD);
            paramMdl.setFil080PluralKbn("1");
        } else {
            paramMdl.setFil080Mode(GSConstFile.MODE_EDIT);
            paramMdl.setFil080PluralKbn("0");
        }

        if (paramMdl.getFil080InitFlg() == 0) {
            paramMdl.setFil080InitFlg(1);
        }
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        CommonBiz commonBiz = new CommonBiz();

        paramMdl.setFil080SvPluralKbn(paramMdl.getFil080PluralKbn());

        if (paramMdl.getFil080PluginId() == null) {
            //初期表示
            if (paramMdl.getFil080Mode() == GSConstFile.MODE_EDIT) {
                //編集モードの場合DBより各項目を設定する。
                __setData(paramMdl, tempDir, appRoot, buMdl);
            }

            //プラグインIDを設定
            paramMdl.setFil080PluginId(GSConstFile.PLUGIN_ID_FILE);

            //個人設定が無い場合は初期値で登録する。
            filBiz.getUserConf(buMdl.getUsrsid());
        }


        //親ディレクトリSIDを取得する。
        int parentDirSid = NullDefault.getInt(paramMdl.getFil070ParentDirSid(), -1);
        if (parentDirSid < 1) {
            parentDirSid = filBiz.getParentDirSid(dirSid);
        }
        paramMdl.setFil070ParentDirSid(String.valueOf(parentDirSid));
        if (parentDirSid > 0) {
            //バージョン管理区分表示設定を行う。
            __setVersion(paramMdl);

        }
        //フォルダパスを設定する。
        paramMdl.setFil080DirPath(filBiz.getDirctoryPath(parentDirSid));

        //ファイルロックユーザ名設定（セッションユーザ）
        paramMdl.setFil080LockUsrName(buMdl.getUsiseimei());

        //添付ファイルのラベルを設定する。
        List<LabelValueBean> fileList = commonBiz.getTempFileLabelList(tempDir);
        paramMdl.setFil080FileLabelList(fileList);

        //個人キャビネット内か判定
        int personalFlg = paramMdl.getFil040PersonalFlg();

        //キャビネットSID
        int fcbSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1);
        if (paramMdl.getFil080webmail() != 1) {
            fcbSid = filBiz.getCabinetSid(dirSid);
        }
        FileCabinetModel fcbMdl = null;
        if (fcbSid > 0) {
            FileCabinetDao fcbDao = new FileCabinetDao(con__);
            fcbMdl = fcbDao.select(fcbSid);
            if (fcbMdl != null) {
                // キャビネット情報からフラグ更新
                personalFlg = fcbMdl.getFcbPersonalFlg();
                if (personalFlg == 1) {
                    paramMdl.setFil010DspCabinetKbn(
                                String.valueOf(GSConstFile.CABINET_KBN_PRIVATE));

                } else if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
                    paramMdl.setFil010DspCabinetKbn(
                            String.valueOf(GSConstFile.CABINET_KBN_ERRL));

                    int sortFolder = fcbMdl.getFcbSortFolder();
                    paramMdl.setFil080FcbSortFolder(sortFolder);
                    if (sortFolder == GSConstFile.SORT_FOLDER_USE) {
                        paramMdl.setFil080FcbSortFolderName(
                                __getSortFolderPath(fcbMdl));
                    }
                } else {
                    paramMdl.setFil010DspCabinetKbn(
                            String.valueOf(GSConstFile.CABINET_KBN_PUBLIC));
                }
            }
        }

        //編集モードの場合、更新前のファイル名を取得
        if (paramMdl.getFil080Mode() == GSConstFile.MODE_EDIT) {
            paramMdl.setFil080EditFileName(filBiz.getDirctoryName(dirSid));
        }

        List<LabelValueBean> lvList = new ArrayList<LabelValueBean>();

        //更新者設定
        paramMdl.setFil080EditId(NullDefault.getString(
                paramMdl.getFil080EditId(), String.valueOf(buMdl.getUsrsid())));

        lvList.add(new LabelValueBean(
                buMdl.getUsiseimei(), String.valueOf(buMdl.getUsrsid())));

        //ユーザの所属グループを取得
        ArrayList<GroupModel> gpList = null;
        UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con__);
        gpList = gpDao.selectGroupNmListOrderbyClass(buMdl.getUsrsid());
        if (gpList != null && !gpList.isEmpty()) {
            for (GroupModel gpMdl : gpList) {
                lvList.add(new LabelValueBean(
                        gpMdl.getGroupName(), "G" + String.valueOf(gpMdl.getGroupSid())));
            }
        }
        paramMdl.setFil080groupList(lvList);

        //WEBメール連携の場合、キャビネットコンボとディレクトリツリー情報を設定する
        if (paramMdl.getFil080webmail() == 1) {
            __setCabinetList(buMdl, paramMdl, con__, personalFlg);

            //Tree情報取得
            Map<Integer, String[]> treeMap = __getFileTreeMap(fcbSid);

            //前回のフォルダツリー情報を初期化
            paramMdl.setTreeFormLv0(null);
            paramMdl.setTreeFormLv1(null);
            paramMdl.setTreeFormLv2(null);
            paramMdl.setTreeFormLv3(null);
            paramMdl.setTreeFormLv4(null);
            paramMdl.setTreeFormLv5(null);
            paramMdl.setTreeFormLv6(null);
            paramMdl.setTreeFormLv7(null);
            paramMdl.setTreeFormLv8(null);
            paramMdl.setTreeFormLv9(null);
            paramMdl.setTreeFormLv10(null);

            paramMdl.setTreeFormLv0(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_0));
            if (paramMdl.getTreeFormLv0() == null || paramMdl.getTreeFormLv0().length <= 0) {
                return;
            }

            //電帳法キャビネット かつ自動振り分け有りの場合、最上位フォルダのみ表示
            if (fcbMdl != null
            && fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON
            && fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                return;
            }

            paramMdl.setTreeFormLv1(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_1));
            if (paramMdl.getTreeFormLv1() == null || paramMdl.getTreeFormLv1().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv2(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_2));
            if (paramMdl.getTreeFormLv2() == null || paramMdl.getTreeFormLv2().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv3(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_3));
            if (paramMdl.getTreeFormLv3() == null || paramMdl.getTreeFormLv3().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv4(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_4));
            if (paramMdl.getTreeFormLv4() == null || paramMdl.getTreeFormLv4().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv5(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_5));
            if (paramMdl.getTreeFormLv5() == null || paramMdl.getTreeFormLv5().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv6(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_6));
            if (paramMdl.getTreeFormLv6() == null || paramMdl.getTreeFormLv6().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv7(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_7));
            if (paramMdl.getTreeFormLv7() == null || paramMdl.getTreeFormLv7().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv8(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_8));
            if (paramMdl.getTreeFormLv8() == null || paramMdl.getTreeFormLv8().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv9(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_9));
            if (paramMdl.getTreeFormLv9() == null || paramMdl.getTreeFormLv9().length <= 0) {
                return;
            }
            paramMdl.setTreeFormLv10(
                    treeMap.get(GSConstFile.DIRECTORY_LEVEL_10));
        }

        //取引金額 外貨
        ArrayList<LabelValueBean> moneyMasterList = new ArrayList<LabelValueBean>();
        FileMoneyMasterDao dao = new FileMoneyMasterDao(con__);
        List<FileMoneyMasterModel> list = dao.select();
        for (FileMoneyMasterModel mdl : list) {
            LabelValueBean bean = new LabelValueBean();
            bean.setLabel(mdl.getFmmName());
            bean.setValue(String.valueOf(mdl.getFmmSid()));
            moneyMasterList.add(bean);
        }
        paramMdl.setFil080TradeMoneyMasterList(moneyMasterList);

        if (__isParentZeroUser(parentDirSid)) {
            //親がゼロユーザ
            paramMdl.setFil080ParentZeroUser("1");
            return;
        } else {
            paramMdl.setFil080ParentZeroUser("0");
        }

        //親ディレクトリ（追加・変更・削除）アクセス制限リスト
        paramMdl.setFil080ParentEditList(__getParentAccessUser(
                paramMdl, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)));
        //親ディレクトリ（閲覧）アクセス制限リスト
        paramMdl.setFil080ParentReadList(__getParentAccessUser(
                paramMdl, Integer.parseInt(GSConstFile.ACCESS_KBN_READ)));
        if (paramMdl.getFil080ParentEditList().size()
                > Integer.parseInt(GSConstFile.MAXCOUNT_PARENT_ACCESS)
         || paramMdl.getFil080ParentReadList().size()
                > Integer.parseInt(GSConstFile.MAXCOUNT_PARENT_ACCESS)) {
            paramMdl.setFil080ParentAccessAllDspFlg(1);
        }
    }

    /**
     * <br>[機  能] キャビネット 保存先振り分けフォルダの表示名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortFolder 保存先振り分けフォルダ(フォルダ1 or フォルダ2)
     * @return 保存先振り分けフォルダの表示名称
     */
    private String __getSortFolderName(int sortFolder) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String sortFolderName = "";
        switch (sortFolder) {
            case GSConstFile.SORT_FOLDER_UPLOAD_DATE:
                sortFolderName = gsMsg.getMessage("fil.fil080.10");
                break;
            case GSConstFile.SORT_FOLDER_TRADE_DATE:
                sortFolderName = gsMsg.getMessage("fil.fil080.11");
                break;
            case GSConstFile.SORT_FOLDER_TRADE_TARGET:
                sortFolderName = gsMsg.getMessage("fil.fil030.18");
                break;
            case GSConstFile.SORT_FOLDER_TRADE_DEFGROUP:
                sortFolderName = gsMsg.getMessage("user.35");
                break;
            default:
        }

        if (sortFolderName.length() > 0) {
            sortFolderName = "{" + sortFolderName + "}";
        }

        return sortFolderName;
    }

    /**
     * <br>[機  能] 保存先振り分けフォルダのフォルダパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbMdl キャビネット情報Model
     * @return フォルダパス
     */
    private String __getSortFolderPath(FileCabinetModel fcbMdl) {

        String sortFolderPath = fcbMdl.getFcbName() + "/";
        sortFolderPath += __getSortFolderName(fcbMdl.getFcbSortFolder1()) + "/";

        String sortFolderName2 = __getSortFolderName(fcbMdl.getFcbSortFolder2());
        if (!StringUtil.isNullZeroString(sortFolderName2)) {
            sortFolderPath += sortFolderName2 + "/";
        }

        return sortFolderPath;
    }

    /**
     * <br>[機  能] DBよりファイル情報を取得しを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションのルートパス
     * @param buMdl セッションユーザモデル
     * @throws Exception 実行時例外
     */
    private void __setData(Fil080ParamModel paramMdl,
                           String tempDir,
                           String appRoot,
                           BaseUserModel buMdl)
                           throws Exception {

        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        FileDao filDao = new FileDao(con__);

        //最新のファイル情報を取得する。
        FileModel fileModel = filDao.getFileInf(dirSid, buMdl.getUsrsid(), false);

        if (fileModel == null) {
            return;
        }

        paramMdl.setFil080Biko(fileModel.getFdrBiko());
        paramMdl.setFil080VerKbn(String.valueOf(fileModel.getFdrVerKbn()));
        paramMdl.setFil080EditId(filBiz.setUpdateSid(buMdl.getUsrsid(),
                                                    fileModel.getFdrEgid()));

        //添付ファイルをテンポラリディレクトリへ設定する
        filBiz.setTempFile(appRoot, tempDir, dirSid, fileModel.getFdrVersion());

        int version = filBiz.getNewVersion(dirSid);

        //ファイルロックを行う。
        filBiz.updateLockKbnCommit(
                dirSid, version, GSConstFile.LOCK_KBN_ON, buMdl.getUsrsid());

        //アクセス制限情報
        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
        paramMdl.setFil080AccessKbn(String.valueOf(GSConstFile.ACCESS_KBN_OFF));
        if (daConfDao.getCount(dirSid) > 0) {
            //追加・変更・削除
            String[] leftFull = daConfDao.getAccessUser(
                    dirSid, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
            if (leftFull != null && leftFull.length > 0) {
                //0ユーザチェック
                List<String> leftList = new ArrayList<String>();
                leftList.addAll(Arrays.asList(leftFull));
                if (leftList.contains("0")) {
                    leftList.remove("0");
                    leftFull = leftList.toArray(new String[leftList.size()]);
                }
            }
            paramMdl.setFil080SvAcFull(leftFull);
            //閲覧
            String[] leftRead = daConfDao.getAccessUser(
                    dirSid, Integer.parseInt(GSConstFile.ACCESS_KBN_READ));
            paramMdl.setFil080SvAcRead(leftRead);
            //個人キャビネット内で閲覧制限対象がいなければ、アクセス区分は「制限しない」
            if (paramMdl.getFil040PersonalFlg() == GSConstFile.CABINET_KBN_PUBLIC
                    || leftRead.length > 0) {
                paramMdl.setFil080AccessKbn(String.valueOf(GSConstFile.ACCESS_KBN_ON));
            }
        }

        if (paramMdl.getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)
                && paramMdl.getFil040PersonalFlg() == 0) {
            //電帳法情報
            if (fileModel.getFdrTradeDate() != null) {
                paramMdl.setFil080TradeDate(fileModel.getFdrTradeDate().getStrYear() + "/"
                                          + fileModel.getFdrTradeDate().getStrMonth() + "/"
                                          + fileModel.getFdrTradeDate().getStrDay());
            }
            paramMdl.setFil080TradeTarget(fileModel.getFdrTradeTarget());
            String money = String.valueOf(fileModel.getFdrTradeMoney());
            if (money.indexOf(".") != -1) {
                String money_top = StringUtil.toCommaFormat(money.substring(0, money.indexOf(".")));
                String money_bottom = money.substring(money.indexOf(".") + 1);
                int money_bottom_length = money_bottom.length();
                for (int i = 0; i < money_bottom_length; i++) {
                    String check = money_bottom.substring(money_bottom_length - i - 1);
                    if (check.equals("0")) {
                        money_bottom = money_bottom.substring(0, (money_bottom_length - i - 1));
                    }
                }
                if (money_bottom.length() > 0) {
                    money_bottom = "." + money_bottom;
                }
                money = money_top + money_bottom;
            } else {
                money = StringUtil.toCommaFormat(money);
            }

            //画面上は「金額無し」チェックであるため、取引金額有りの場合は未チェックとする
            if (fileModel.getFdrTradeMoneyKbn() == GSConstFile.MONEY_KBN_ON) {
                paramMdl.setFil080TradeMoneyKbn(GSConstFile.MONEY_KBN_OFF);
                paramMdl.setFil080TradeMoney(
                        FilStringUtil.getDspErrlTradeMoney(
                                String.valueOf(fileModel.getFdrTradeMoney())
                                )
                        );
            } else {
                paramMdl.setFil080TradeMoneyKbn(GSConstFile.MONEY_KBN_ON);
            }

            paramMdl.setFil080TradeMoneyType(fileModel.getEmtSid());
        }
    }

    /**
     * <br>[機  能] バージョン管理の表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __setVersion(Fil080ParamModel paramMdl) throws SQLException {


        //管理者設定バージョン管理区分の設定
        int admVerKbn = __setVerKbnAdmin(paramMdl);
        if (admVerKbn == GSConstFile.VERSION_KBN_OFF) {
            return;
        }

        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);

        //キャビネットSIDを取得する。
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        int fcbSid = 0;
        if (dirSid == -1) {
            fcbSid = filBiz.getCabinetSid(
                    NullDefault.getInt(paramMdl.getFil070ParentDirSid(), -2));
        } else {
            fcbSid = filBiz.getCabinetSid(dirSid);
        }

        FileCabinetDao cabnetDao = new FileCabinetDao(con__);
        FileCabinetModel cabModel = cabnetDao.select(fcbSid);

        if (cabModel.getFcbPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            FileAconfModel aconf = filBiz.getFileAconfModel();
            cabModel.setFileAconf(aconf);
        }

        if (cabModel.getFcbVerallKbn() == GSConstFile.VERSION_ALL_KBN_ON) {
            paramMdl.setFil080VerKbn(String.valueOf(cabModel.getFcbVerKbn()));
            paramMdl.setFil080VerallKbn(String.valueOf(GSConstFile.VERSION_ALL_KBN_ON));
            return;
        }

        //バージョンコンボ設定
        paramMdl.setFil080VerKbnLabelList(filBiz.getVerKbnLabelList());
        paramMdl.setFil080VerallKbn(String.valueOf(GSConstFile.VERSION_ALL_KBN_OFF));

    }

    /**
     * <br>[機  能] ロック解除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void fileUnLock(Fil080ParamModel paramMdl, int usrSid) throws SQLException {

        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);
        //最新のバージョンを取得
        int version = filBiz.getNewVersion(dirSid);

        boolean commitFlg = false;
        con__.setAutoCommit(false);
        try {
            //ロックを解除する。
            filBiz.updateLockKbn(dirSid, version, GSConstFile.LOCK_KBN_OFF, usrSid);

            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] キャビネット変更時設定。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setChangeCabinet(Fil080ParamModel paramMdl, Connection con)
    throws SQLException {

        if (paramMdl.getFil080SelectCabinetKbn() == GSConstFile.SELECT_CABINET_ERRL) {
            paramMdl.setFil010DspCabinetKbn(GSConstFile.DSP_CABINET_ERRL);
        } else {
            paramMdl.setFil010DspCabinetKbn(GSConstFile.DSP_CABINET_PUBLIC);
        }
        paramMdl.setFil010SelectCabinet(paramMdl.getFil040SelectCabinet());
    }

    /**
     * <br>[機  能] WEBメール メール情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws Exception 実行例外
     */
    public void setWebmailData(
        Fil080ParamModel paramMdl,
        String appRootPath,
        String tempDir) throws Exception {

        long mailNum = paramMdl.getFil080webmailId();
        WmlDao wmlDao = new WmlDao(con__);
        WmlMailDataModel mailData = wmlDao.getMailData(mailNum, reqMdl__.getDomain());

        if (mailData.getTempFileList() != null) {
            UDate now = new UDate();
            String dateStr = now.getDateString();
            CommonBiz cmnBiz = new CommonBiz();
            int fileNum = 1;
            for (WmlTempfileModel fileMdl : mailData.getTempFileList()) {
                cmnBiz.saveTempFileForWebmail(dateStr, fileMdl, appRootPath, tempDir, fileNum);
                fileNum++;
            }

        }
    }

    /**
     * <br>[機  能] キャビネットコンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param buMdl ユーザーモデル
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param personalFlg 個人キャビネットフラグ
     * @throws SQLException SQL実行時例外
     */
    private void __setCabinetList(BaseUserModel buMdl, Fil080ParamModel paramMdl, Connection con,
            int personalFlg)
    throws SQLException {

        Fil010Biz fil010Biz = new Fil010Biz(reqMdl__);
        GsMessage gsMsg = new GsMessage(reqMdl__);
        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"),
                String.valueOf(-1)));

        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con);
        if (paramMdl.getFil080SelectCabinetKbn() == GSConstFile.SELECT_CABINET_ERRL) {
            ArrayList<FileCabinetDspModel> cabinetList = fil010Biz.getCabinetList(
                    buMdl, con, false, false, GSConstFile.CABINET_KBN_ERRL);
            if (cabinetList != null && cabinetList.size() > 0) {
                boolean checkResult = true;
                for (FileCabinetDspModel model : cabinetList) {
                    if (model.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                        //自動振り分け = あり の場合、編集権限チェック
                        checkResult = filCmnBiz.checkPowEditRootDir(model.getFcbSid())
                                        == FilCommonBiz.ERR_NONE;
                    } else {
                        checkResult = true;
                    }

                    //仮登録ファイルが登録可能なキャビネットのみを選択
                    if (checkResult) {
                        labelList.add(new LabelValueBean(model.getFcbName(),
                                String.valueOf(model.getFcbSid())));
                    }
                }
            }
        } else {
            ArrayList<FileCabinetDspModel> cabinetList = fil010Biz.getCabinetList(
                    buMdl, con, false, false, personalFlg);
            if (cabinetList != null && cabinetList.size() > 0) {
                for (FileCabinetDspModel model : cabinetList) {
                    labelList.add(new LabelValueBean(model.getFcbName(),
                            String.valueOf(model.getFcbSid())));
                }
            }
        }


        paramMdl.setFil040CabinetList(labelList);
    }

    /**
     * <br>[機  能] 指定したキャビネットのファイルツリー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @return ファイルツリー情報
     * @throws SQLException SQL実行時例外
     */
    private Map<Integer, String[]> __getFileTreeMap(int fcbSid)
    throws SQLException {
        //特権ユーザ判定
        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);
        boolean superUser = filCmnBiz.isEditCabinetUser(fcbSid);

        //ファイルツリー情報取得
        FilTreeBiz treeBiz = new FilTreeBiz(con__);
        return treeBiz.getFileTreeMapForMove(
                reqMdl__,
                fcbSid,
                List.of(),
                superUser
                );
    }

    /**
     * <br>管理者設定のバージョン管理区分を取得します。
     * @param paramMdl Fil080ParamModel
     * @return verKbn バージョン管理区分（管理者設定）
     * @throws SQLException SQL実行時例外
     */
    private int __setVerKbnAdmin(Fil080ParamModel paramMdl) throws SQLException {

        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconfMdl = aconfDao.select();

        if (aconfMdl == null) {
            return GSConstFile.VERSION_KBN_ON;
        }
        paramMdl.setAdmVerKbn(aconfMdl.getFacVerKbn());

        return aconfMdl.getFacVerKbn();
    }

    /**
     * 親ディレクトリのアクセス制限ユーザリストを取得する
     * @param paramMdl パラメータモデル
     * @param auth 権限区分
     * @return アクセス制限ユーザリスト
     * @throws SQLException 実行例外
     */
    private ArrayList<FileParentAccessDspModel> __getParentAccessUser(
            Fil080ParamModel paramMdl,
            int auth) throws SQLException {

        ArrayList<FileParentAccessDspModel> ret = new ArrayList<FileParentAccessDspModel>();

        GroupDao dao = new GroupDao(con__);
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con__);

        //グループを全て取得
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        ArrayList<GroupModel> allGpList = dao.getGroupTree(sortMdl);

        //親ディレクトリのアクセス権限を取得
        List<String> parentSids = new ArrayList<String>();
        String[] sids = null;
        int fdrSid = NullDefault.getInt(paramMdl.getFil070ParentDirSid(), -1);
        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
        sids = daConfDao.getAccessUser(fdrSid, auth);
        if (sids != null) {
            parentSids.addAll(Arrays.asList(sids));
        }

        //アクセス制限ユーザリスト作成（グループ）
        if (parentSids != null && parentSids.size() > 0) {
            for (GroupModel bean : allGpList) {
                if (!parentSids.contains(String.valueOf("G" + bean.getGroupSid()))) {
                    continue;
                }
                FileParentAccessDspModel dspBean = new FileParentAccessDspModel();
                dspBean.setUserName(bean.getGroupName());
                dspBean.setGrpFlg(1);
                ret.add(dspBean);
            }
        }

        //アクセス制限ユーザリスト作成（ユーザ）
        if (parentSids != null && parentSids.size() > 0) {
            for (int i = 0; i < parentSids.size(); i++) {
                if (parentSids.get(i).substring(0, 1).equals("G")
                 || parentSids.get(i).equals("0")) {
                    parentSids.remove(i);
                    i--;
                }
            }
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> usList
                = userBiz.getUserList(con__, (String[]) parentSids.toArray(new String[0]));
            for (int i = 0; i < usList.size(); i++) {
                CmnUsrmInfModel cuiMdl = usList.get(i);
                FileParentAccessDspModel dspBean = new FileParentAccessDspModel();
                dspBean.setUserName(cuiMdl.getUsiSei() + " " + cuiMdl.getUsiMei());
                dspBean.setGrpFlg(0);
                dspBean.setUsrUkoFlg(cuiMdl.getUsrUkoFlg());
                ret.add(dspBean);
            }
        }

        return ret;
    }

    /**
     * 親ディレクトリのアクセス権限がゼロユーザか判定する
     * @param parentSid 親ディレクトリSID
     * @return true:ゼロユーザ false:ゼロユーザでない
     * @throws SQLException 実行例外
     */
    private boolean __isParentZeroUser(int parentSid) throws SQLException {

        String[] sids = null;
        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con__);
        sids = daConfDao.getAccessUser(
                parentSid, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        if (sids != null && sids.length > 0) {
            List<String> parentSids = new ArrayList<String>();
            parentSids.addAll(Arrays.asList(sids));
            if (parentSids.contains("0")) {
                //ゼロユーザ
                return true;
            }
        }

        return false;
    }

    /**
     * <br>[機  能] ファイルを登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションルートパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @return dirSid ディレクトリSID
     * @throws Exception 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public int registerData(
            Fil080ParamModel paramMdl,
            String tempDir,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pconfig,
            boolean smailPluginUseFlg) throws Exception, TempFileException {

        CommonBiz cmnbiz = new CommonBiz();
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        int parentDirSid = NullDefault.getInt(paramMdl.getFil070ParentDirSid(), -1);
        UDate now = new UDate();
        int dirSid = -1;

        //テンポラリディレクトリパスにある添付ファイルを全て登録
        List<CmnBinfModel> binList = cmnbiz.insertSameBinInfoForFileKanri(
                con__, tempDir, appRootPath, cntCon, usrSid__, now);
        paramMdl.setBinList(binList);

        //親ディレクトリ情報を取得する。
        FileDirectoryModel parDirModel = dirDao.getNewDirectory(parentDirSid);
        int level = parDirModel.getFdrLevel() + 1;

        if (paramMdl.getFil080Mode() == GSConstFile.MODE_ADD) {
            //新規登録
            dirSid = __insertFileInf(
                    paramMdl,
                    binList,
                    level,
                    cntCon,
                    appRootPath,
                    pconfig,
                    smailPluginUseFlg);
        } else {
            //編集
            dirSid = __updateFileInf(
                    paramMdl,
                    binList,
                    level,
                    cntCon,
                    appRootPath,
                    pconfig,
                    smailPluginUseFlg);
        }

        return dirSid;
    }

    /**
     * <br>[機  能] ファイルを登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションルートパス
     * @return dirSid ディレクトリSID
     * @throws Exception 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<Integer> registerErrlData(
            Fil080ParamModel paramMdl,
            String tempDir,
            MlCountMtController cntCon,
            String appRootPath) throws Exception, TempFileException {

        CommonBiz cmnbiz = new CommonBiz();
        UDate now = new UDate();

        //テンポラリディレクトリパスにある添付ファイルを全て登録
        List<CmnBinfModel> binList = cmnbiz.insertSameBinInfoForFileKanri(
                con__, tempDir, appRootPath, cntCon, usrSid__, now);
        paramMdl.setBinList(binList);

        //電子帳簿保存法 新規登録
        List<Integer> result =
                __insertErrlFileInf(paramMdl, binList, cntCon);

        return result;
    }

    /**
     * <br>[機  能] 新規登録の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param binList バイナリリスト
     * @param level ディレクトリ階層
     * @param cntCon MlCountMtController
     * @param appRoot ROOTパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @return fileName
     * @throws Exception 実行例外
     */
    private int __insertFileInf(
            Fil080ParamModel paramMdl,
            List<CmnBinfModel> binList,
            int level,
            MlCountMtController cntCon,
            String appRoot,
            PluginConfig pconfig,
            boolean smailPluginUseFlg)
    throws Exception {

        int dirSid = -1;
        if (binList == null) {
            return dirSid;
        }

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        FileFileRekiDao rekiDao = new FileFileRekiDao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        int version = 1;
        UDate now = new UDate();
        int parentDirSid = NullDefault.getInt(paramMdl.getFil070ParentDirSid(), -1);
        int cabinetSid = filBiz.getCabinetSid(parentDirSid);

        //ディレクトリ情報モデルを設定する。
        FileDirectoryModel dirModel = new FileDirectoryModel();
        dirModel.setFdrVersion(version);
        dirModel.setFcbSid(cabinetSid);
        dirModel.setFdrParentSid(parentDirSid);
        dirModel.setFdrKbn(GSConstFile.DIRECTORY_FILE);
        dirModel.setFdrVerKbn(NullDefault.getInt(paramMdl.getFil080VerKbn(), 0));
        dirModel.setFdrLevel(level);
        dirModel.setFdrBiko(paramMdl.getFil080Biko());
        dirModel.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
        dirModel.setFdrAuid(usrSid__);
        dirModel.setFdrAdate(now);
        dirModel.setFdrEuid(usrSid__);
        dirModel.setFdrEdate(now);
        dirModel.setFdrEgid(filBiz.getGroupSid(paramMdl.getFil080EditId()));

        //ファイル情報モデルを設定する。
        FileFileBinModel fileBinModel = new FileFileBinModel();
        fileBinModel.setFdrVersion(version);
        fileBinModel.setFflLockKbn(GSConstFile.LOCK_KBN_OFF);
        fileBinModel.setFflLockUser(usrSid__);

        //ディレクトリ情報モデルを設定する。
        FileFileRekiModel rekiModel = new FileFileRekiModel();
        rekiModel.setFdrVersion(version);
        rekiModel.setFfrKbn(GSConstFile.REKI_KBN_NEW);
        rekiModel.setFfrEuid(usrSid__);
        rekiModel.setFfrEdate(now);
        rekiModel.setFfrParentSid(parentDirSid);
        rekiModel.setFfrUpCmt(paramMdl.getFil080UpCmt());
        rekiModel.setFfrEgid(filBiz.getGroupSid(paramMdl.getFil080EditId()));

        ArrayList<Integer> sidList = new ArrayList<Integer>();
        for (CmnBinfModel binMdl : binList) {
            //ディレクトリSIDを採番する。
            dirSid = (int) cntCon.getSaibanNumber(
                    GSConstFile.PLUGIN_ID_FILE,
                    GSConstFile.SBNSID_SUB_DIRECTORY,
                    usrSid__);

            sidList.add(dirSid);

            dirModel.setFdrSid(dirSid);
            dirModel.setFdrName(binMdl.getBinFileName());
            dirDao.insert(dirModel);

            fileBinModel.setFdrSid(dirSid);
            fileBinModel.setBinSid(binMdl.getBinSid());
            fileBinModel.setFflExt(binMdl.getBinFileExtension());
            fileBinModel.setFflFileSize(binMdl.getBinFileSize());
            fileBinDao.insert(fileBinModel);

            rekiModel.setFdrSid(dirSid);
            rekiModel.setFfrFname(binMdl.getBinFileName());
            rekiDao.insert(rekiModel);

            //個人キャビネット内か判定
            int personalFlg = paramMdl.getFil040PersonalFlg();

            //ディレクトリアクセス設定
            __insertDaccessConf(dirSid, paramMdl, personalFlg);
            FileDirectoryDao fdDao = new FileDirectoryDao(con__);

            //アクセス設定SIDを更新
            fdDao.updateAccessSid(dirSid, Integer.parseInt(paramMdl.getFil080AccessKbn()), 0);

            //集計データを登録する
            int fulUsrSid = 0;
            int fulGrpSid = 0;
            if (filBiz.getGroupSid(paramMdl.getFil080EditId()) == 0) {
                fulUsrSid = usrSid__;
                fulGrpSid = -1;
            } else {
                fulUsrSid = -1;
                fulGrpSid = filBiz.getGroupSid(paramMdl.getFil080EditId());
            }
            filBiz.regFileUploadLogCnt(
                    fulUsrSid, fulGrpSid, GSConstFile.LOG_COUNT_KBN_NEW,
                    cabinetSid, dirSid, now);
        }

        //親ディレクトリ配下のアクセス制限を更新
        //更新通知を設定する。
        for (int sid : sidList) {
            dirDao.updateAccessSid(sid);
            filBiz.updateCall(sid, cntCon, appRoot, pconfig, smailPluginUseFlg,
                    usrSid__);
        }

        return dirSid;
    }

    /**
     * <br>[機  能] 新規登録の処理
     * <br>[解  説]　電子帳簿保存法
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param binList バイナリリスト
     * @param cntCon MlCountMtController
     * @return 追加した仮登録ファイルSID
     * @throws Exception 実行例外
     */
    private List<Integer> __insertErrlFileInf(
            Fil080ParamModel paramMdl,
            List<CmnBinfModel> binList,
            MlCountMtController cntCon) throws Exception {

        List<Integer> ret = new ArrayList<Integer>();
        int dirSid = -1;
        if (binList == null) {
            return ret;
        }

        FileErrlAdddataDao errlDao = new FileErrlAdddataDao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        UDate now = new UDate();
        int parentDirSid = NullDefault.getInt(paramMdl.getFil070ParentDirSid(), -1);
        int cabinetSid = filBiz.getCabinetSid(parentDirSid);

        //登録者のデフォルトグループ名を取得
        GroupDao grpDao = new GroupDao(con__);
        CmnGroupmModel grpMdl = grpDao.getDefaultGroup(usrSid__);
        String defGrpName = grpMdl.getGrpName();

        for (CmnBinfModel binMdl : binList) {
            //仮登録用SIDを採番する。
            int feaSid = (int) cntCon.getSaibanNumber(
                    GSConstFile.PLUGIN_ID_FILE,
                    GSConstFile.SBNSID_SUB_MONEY,
                    usrSid__);
            ret.add(feaSid);

            FileErrlAdddataModel mdl = new FileErrlAdddataModel();
            mdl.setFeaSid(feaSid);
            mdl.setBinSid(binMdl.getBinSid());
            mdl.setFflExt(binMdl.getBinFileExtension());
            mdl.setFflFileSize(binMdl.getBinFileSize());
            mdl.setFcbSid(cabinetSid);
            mdl.setFdrParentSid(parentDirSid);
            mdl.setFdrName(binMdl.getBinFileName());
            mdl.setFdrBiko(paramMdl.getFil080Biko());
            mdl.setFfrUpCmt(paramMdl.getFil080UpCmt());
            mdl.setFdrAuid(usrSid__);
            mdl.setFdrAdate(now);
            mdl.setFeaDefgrpName(defGrpName);
            errlDao.insert(mdl);

            //集計データを登録する
            int fulUsrSid = 0;
            int fulGrpSid = 0;
            if (filBiz.getGroupSid(paramMdl.getFil080EditId()) == 0) {
                fulUsrSid = usrSid__;
                fulGrpSid = -1;
            } else {
                fulUsrSid = -1;
                fulGrpSid = filBiz.getGroupSid(paramMdl.getFil080EditId());
            }
            filBiz.regFileUploadLogCnt(
                    fulUsrSid, fulGrpSid, GSConstFile.LOG_COUNT_KBN_NEW,
                    cabinetSid, dirSid, now);
        }

        return ret;
    }

    /**
     * <br>[機  能] 編集時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param binList バイナリリスト
     * @param level ディレクトリ階層
     * @param cntCon MlCountMtController
     * @param appRoot ROOTパス
     * @param pconfig PluginConfig
     * @param smailPluginUseFlg ショートメール利用可能フラグ
     * @return fileName
     * @throws Exception 実行例外
     */
    private int __updateFileInf(
            Fil080ParamModel paramMdl,
            List<CmnBinfModel> binList,
            int level,
            MlCountMtController cntCon,
            String appRoot,
            PluginConfig pconfig,
            boolean smailPluginUseFlg)
    throws Exception {

        int dirSid = -1;
        if (binList == null || binList.size() < 1) {
            return dirSid;
        }
        CmnBinfModel binMdl = null;
        for (CmnBinfModel mdl : binList) {
            binMdl = mdl;
        }

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FileFileBinDao fileBinDao = new FileFileBinDao(con__);
        FileFileRekiDao rekiDao = new FileFileRekiDao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);
        int cabinetSid = filBiz.getCabinetSid(dirSid);
        int parentDirSid = NullDefault.getInt(paramMdl.getFil070ParentDirSid(), -1);
        //最新のバージョンを取得
        FileDirectoryModel dirModel = dirDao.getNewDirectory(dirSid);
        if (dirModel == null) {
            dirModel = new FileDirectoryModel();
            dirModel.setFdrVersion(-1);
            dirModel.setFdrLevel(level);
            dirModel.setFdrParentSid(parentDirSid);
        }
        int version = dirModel.getFdrVersion();
        int nextVersion = version + 1;
        UDate now = new UDate();

        //ディレクトリ情報モデルを設定する。
        dirModel.setFdrSid(dirSid);
        dirModel.setFdrVersion(nextVersion);
        dirModel.setFcbSid(cabinetSid);
        dirModel.setFdrKbn(GSConstFile.DIRECTORY_FILE);
        dirModel.setFdrVerKbn(NullDefault.getInt(paramMdl.getFil080VerKbn(), 0));
        dirModel.setFdrBiko(paramMdl.getFil080Biko());
        dirModel.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
        dirModel.setFdrAuid(usrSid__);
        dirModel.setFdrAdate(now);
        dirModel.setFdrEuid(usrSid__);
        dirModel.setFdrEdate(now);
        dirModel.setFdrName(binMdl.getBinFileName());
        dirModel.setFdrEgid(filBiz.getGroupSid(paramMdl.getFil080EditId()));

        if (paramMdl.getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)
                && paramMdl.getFil040PersonalFlg() == 0) {
            UDate date = new UDate();
            date.setDate(paramMdl.getFil080TradeDate().replaceAll("/", ""));
            date.setZeroHhMmSs();
            dirModel.setFdrTradeDate(date);
            dirModel.setFdrTradeTarget(paramMdl.getFil080TradeTarget());

            //取引金額 「金額無し」チェックボックスがチェックされている場合、
            //取引金額無しとして登録する
            if (paramMdl.getFil080TradeMoneyKbn() == GSConstFile.MONEY_KBN_ON) {
                dirModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
                dirModel.setFdrTradeMoney(new BigDecimal(0));
                dirModel.setEmtSid(0);
            } else {
                dirModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);
                dirModel.setFdrTradeMoney(new BigDecimal(
                    paramMdl.getFil080TradeMoney().replaceAll(",", "")));
                dirModel.setEmtSid(paramMdl.getFil080TradeMoneyType());
            }
        }

        dirDao.insert(dirModel);

        //ファイル情報モデルを設定する。
        FileFileBinModel fileBinModel = new FileFileBinModel();
        fileBinModel.setFdrVersion(nextVersion);
        fileBinModel.setFflLockKbn(GSConstFile.LOCK_KBN_OFF);
        fileBinModel.setFflLockUser(usrSid__);
        fileBinModel.setFdrSid(dirSid);
        fileBinModel.setBinSid(binMdl.getBinSid());
        fileBinModel.setFflExt(binMdl.getBinFileExtension());
        fileBinModel.setFflFileSize(binMdl.getBinFileSize());
        fileBinDao.insert(fileBinModel);

        //ディレクトリ履歴情報モデルを設定する。
        FileFileRekiModel rekiModel = new FileFileRekiModel();
        rekiModel.setFdrVersion(nextVersion);
        rekiModel.setFfrKbn(GSConstFile.REKI_KBN_UPDATE);
        rekiModel.setFfrEuid(usrSid__);
        rekiModel.setFfrEdate(now);
        rekiModel.setFdrSid(dirSid);
        rekiModel.setFfrParentSid(parentDirSid);
        rekiModel.setFfrFname(binMdl.getBinFileName());
        rekiModel.setFfrUpCmt(paramMdl.getFil080UpCmt());
        rekiModel.setFfrEgid(filBiz.getGroupSid(paramMdl.getFil080EditId()));

        if (paramMdl.getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)
                && paramMdl.getFil040PersonalFlg() == 0) {
            rekiModel.setFdrTradeDate(dirModel.getFdrTradeDate());
            rekiModel.setFdrTradeTarget(dirModel.getFdrTradeTarget());
            rekiModel.setFdrTradeMoneykbn(dirModel.getFdrTradeMoneykbn());
            rekiModel.setFdrTradeMoney(dirModel.getFdrTradeMoney());
            rekiModel.setEmtSid(dirModel.getEmtSid());
        }
        rekiDao.insert(rekiModel);

        //バージョン管理外のファイルを削除する。
        //※電帳法キャビネットは全世代管理の為、削除しない。
        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(cabinetSid);
        if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_OFF) {
            __deleteOldVersion(paramMdl, version);
        }

        //個人キャビネット内か判定
        int personalFlg = filBiz.getPersonalFlg(
                Integer.parseInt(paramMdl.getFil010SelectCabinet()));

        //ディレクトリアクセス設定
        __insertDaccessConf(dirSid, paramMdl, personalFlg);
        FileDirectoryDao fdDao = new FileDirectoryDao(con__);

        //アクセス設定SIDを更新
        fdDao.updateAccessSid(dirSid, Integer.parseInt(paramMdl.getFil080AccessKbn()), 0);

        //集計データを登録する
        int fulUsrSid = 0;
        int fulGrpSid = 0;
        if (filBiz.getGroupSid(paramMdl.getFil080EditId()) == 0) {
            fulUsrSid = usrSid__;
            fulGrpSid = -1;
        } else {
            fulUsrSid = -1;
            fulGrpSid = filBiz.getGroupSid(paramMdl.getFil080EditId());
        }
        filBiz.regFileUploadLogCnt(
                fulUsrSid, fulGrpSid, GSConstFile.LOG_COUNT_KBN_EDIT,
                cabinetSid, dirSid, now);

        //更新通知を設定する。
        filBiz.updateCall(dirSid, cntCon, appRoot, pconfig, smailPluginUseFlg,
                        usrSid__);

        //ロックを解除する。
        if (filBiz.getLockKbnAdmin() == GSConstFile.LOCK_KBN_ON) {
            filBiz.updateLockKbn(dirSid, version, GSConstFile.LOCK_KBN_OFF, usrSid__);
        }

        return dirSid;
    }

    /**
     * ディレクトリアクセス設定を登録する
     * @param dirSid ディレクトリSID
     * @param paramMdl パラメータモデル
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @throws SQLException 実行例外
     */
    private void __insertDaccessConf(int dirSid, Fil080ParamModel paramMdl, int personalFlg)
            throws SQLException {

        FileDAccessConfDao dao = new FileDAccessConfDao(con__);
        dao.delete(dirSid);


        if (paramMdl.getFil080AccessKbn().equals(String.valueOf(GSConstFile.ACCESS_KBN_OFF))) {
            //個別設定しない
            //共有キャビネットの場合、ここで終了
            if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                //個別設定しない
                return;
            //個人キャビネットの場合、閲覧アクセスユーザセレクトボックスを空にする
            } else if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                paramMdl.setFil080SvAcRead(new String[0]);
            }
        }

        //フルアクセス
        ArrayList<Integer> userFullList = new ArrayList<Integer>();
        ArrayList<Integer> groupFullList = new ArrayList<Integer>();
        String[] full = paramMdl.getFil080SvAcFull();
        if (full != null) {
            for (int i = 0; i < full.length; i++) {
                String str = NullDefault.getString(full[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    groupFullList.add(Integer.valueOf(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    userFullList.add(Integer.valueOf(str));
                }
            }
        }


        //個人キャビネットの場合、個人キャビネット所有者のみフルアクセス権限を与えられる
        if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
            BaseUserModel usModel = reqMdl__.getSmodel();
            int ownerSid = usModel.getUsrsid();
            dao.insert(dirSid, ownerSid,
                    GSConstFile.USER_KBN_USER, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        //共有キャビネットの場合、選択したユーザ・グループに対してフルアクセス権限を与える
        } else if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
            //フルアクセスグループ登録
            dao.insert(dirSid, groupFullList,
                    GSConstFile.USER_KBN_GROUP, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
            //フルアクセスユーザ登録
            dao.insert(dirSid, userFullList,
                    GSConstFile.USER_KBN_USER, Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
        }

        //閲覧アクセス
        ArrayList<Integer> userReadList = new ArrayList<Integer>();
        ArrayList<Integer> groupReadList = new ArrayList<Integer>();
        String[] read = paramMdl.getFil080SvAcRead();
        if (read != null) {
            for (int i = 0; i < read.length; i++) {
                String str = NullDefault.getString(read[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    groupReadList.add(Integer.valueOf(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    userReadList.add(Integer.valueOf(str));
                }
            }
        }

        //閲覧アクセスグループ登録
        dao.insert(dirSid, groupReadList,
                GSConstFile.USER_KBN_GROUP, Integer.parseInt(GSConstFile.ACCESS_KBN_READ));
        //閲覧アクセスユーザ登録
        dao.insert(dirSid, userReadList,
                GSConstFile.USER_KBN_USER, Integer.parseInt(GSConstFile.ACCESS_KBN_READ));

    }

    /**
     * <br>[機  能] バージョン管理外のファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param newVersion 最新バージョン
     * @throws SQLException SQL実行例外
     */
    private void __deleteOldVersion(Fil080ParamModel paramMdl, int newVersion)
    throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);

        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);

        //キャビネットSIDを取得する。
        int cabinetSid = filBiz.getCabinetSid(dirSid);

        //バージョン管理区分を取得する。
        int verKbn = filBiz.getVerKbn(cabinetSid, dirSid);
        int delVersion = newVersion - verKbn + 1;
        if (delVersion < 1) {
            //削除データ無し
            return;
        }

        //管理しないディレクトリ情報を削除する。
        dirDao.deleteOldVersion(dirSid, delVersion);

        //ファイル情報を削除する。
        __deleteOldFile(paramMdl, delVersion);
    }

    /**
     * <br>[機  能] バージョン管理外のファイル情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil080ParamModel
     * @param delVersion 削除基準バージョン
     * @throws SQLException SQL実行例外
     */
    private void __deleteOldFile(Fil080ParamModel paramMdl, int delVersion)
    throws SQLException {

        FileFileBinDao fileDao = new FileFileBinDao(con__);
        CmnBinfDao cbDao = new CmnBinfDao(con__);
        CmnBinfModel cmnBinModel = new CmnBinfModel();
        UDate now = new UDate();
        int dirSid = NullDefault.getInt(paramMdl.getFil070DirSid(), -1);

        //削除対象ファイルリスト
        List<FileFileBinModel> delList = fileDao.getOldVersion(dirSid, delVersion);

        if (delList != null && delList.size() > 0) {
            List<Long> binSidList = new ArrayList<Long>();
            for (FileFileBinModel binModel : delList) {
                binSidList.add(binModel.getBinSid());
            }
            cmnBinModel.setBinJkbn(GSConst.JTKBN_DELETE);
            cmnBinModel.setBinUpuser(usrSid__);
            cmnBinModel.setBinUpdate(now);

            //バイナリー情報を論理削除する
            cbDao.updateJKbn(cmnBinModel, binSidList);
        }

        //ファイル情報を削除する。
        fileDao.deleteOldVersion(dirSid, delVersion);
    }

    /**
     * <br>[機  能] 仮登録時、オペレーションログに出力するファイルパスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return ファイルパス
     * @throws SQLException SQL実行時例外
     */
    public String getErrlDirectoryPath(Fil080ParamModel paramMdl)
    throws SQLException {

        //キャビネットSID
        int fcbSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1);

        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(fcbSid);

        return fcbMdl.getFcbName() + "/" + paramMdl.getBinList().get(0).getBinFileName();
    }

    /**
     * <br>[機  能] 仮登録時のディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return ディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public String getErrlCabinetDirSid(Fil080ParamModel paramMdl)
    throws SQLException {
        int fdrSid = 0;

        int fcbSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1);
        if (fcbSid > 0) {
            FileCabinetDao fcbDao = new FileCabinetDao(con__);
            FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
            if (fcbMdl != null && fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                //自動振り分けの場合、キャビネットディレクトリ
                FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
                fdrSid = fdrDao.getCabinetDirSid(fcbSid);
            } else {
                fdrSid = NullDefault.getInt(paramMdl.getFil070ParentDirSid(), -1);
            }
        }

        if (fdrSid <= 0) {
            return null;
        }

        return String.valueOf(fdrSid);
    }
    /**
     * <br>[機  能] 指定したキャビネットに対し、ファイル情報を登録可能かを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @return true: 登録可能、false: 登録不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canFileEntryCabinet(int fcbSid) throws SQLException {

        //キャビネットの存在チェック
        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(fcbSid);
        if (fcbMdl == null) {
            return false;
        }
        //キャビネットへのアクセス権限があるか判定する。
        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);
        
        boolean result = false;
        if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON
            && fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
            //電帳法キャビネット かつ自動振り分け = あり の場合、編集権限チェック
            result = filCmnBiz.checkPowEditRootDir(fcbSid) == FilCommonBiz.ERR_NONE;
        } else {
            //その他キャビネットの場合、閲覧権限チェック
            result = filCmnBiz.isAccessAuthUser(fcbSid);
        }
        return result;
    }
}
