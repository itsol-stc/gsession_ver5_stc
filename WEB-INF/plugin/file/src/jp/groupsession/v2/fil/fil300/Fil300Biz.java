package jp.groupsession.v2.fil.fil300;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.h2.util.StringUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.dao.FileFileRekiDao;
import jp.groupsession.v2.fil.dao.FileMoneyMasterDao;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;
import jp.groupsession.v2.fil.model.FileFileBinModel;
import jp.groupsession.v2.fil.model.FileFileRekiModel;
import jp.groupsession.v2.fil.model.FileMoneyMasterModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil300Biz {

    /** サンプルファイル名 */
    private static final String SAMPLE_FILE_NAME = "fileTransactionImport.xlsx";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil300Biz.class);
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /** BOOK */
    private Workbook workbook__ = null;
    /** サンプル用シート */
    private Sheet sheetSample__ = null;
    /** ヘルプ用シート */
    private Sheet sheetHelp__ = null;
    /** シート種別 : サンプル */
    private static final int SHEET_TYPE_SAMPLE = 0;
    /** シート種別 : ヘルプ */
    private static final int SHEET_TYPE_HELP = 1;
    /** キャッシュデータ*/
    private final FileErrlAdddataBizCash cash__;


    /**
     * <p>コンストラクタ
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Fil300Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
        cash__ = FileErrlAdddataBizCash.getInstance(reqMdl, con);
    }

    /**
     * <br>[機  能] 編集可能な仮登録ファイルが存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:編集可能な仮登録ファイルが存在する, false:編集可能な仮登録ファイルが存在しない
     * @throws SQLException 実行例外
     */
    public boolean isErrlFile(Connection con) throws SQLException {

        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con);
        List<FileErrlAdddataModel> errlList = feaDao.select();
        if (errlList.isEmpty()) {
            return false;
        }

        List<Integer> errlSidList = errlList.stream()
                .map(mdl -> mdl.getFcbSid())
                .collect(Collectors.toList());

        List<FileCabinetDspModel> cabinetList = cash__.getCabinetList();
        List<Integer> cabinetSidList = cabinetList.stream()
                .filter(mdl -> mdl.getNotEntryIconKbn() == GSConstFile.NOT_ENTRY_FILE_EXIST)
                .map(mdl -> mdl.getFcbSid())
                .collect(Collectors.toList());
        boolean ret = false;
        for (int sid : errlSidList) {
            if (cabinetSidList.contains(sid)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] ファイル登録画面から指定された仮登録ファイルのうち、編集可能な仮登録ファイルのみを抽出する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return 編集可能な仮登録ファイルID一覧
     * @throws SQLException 実行例外
     */
    public List<String> getErrlFile080(Fil300ParamModel paramMdl) throws SQLException {

        List<String> resultList = new ArrayList<String>();
        String[] insertErrlList = paramMdl.getFil300BeforeInsertFile();

        if (insertErrlList != null) {
            List<Integer> feaSidList = Stream.of(insertErrlList)
                    .filter(sid -> NullDefault.getInt(sid, -1) > 0)
                    .map(sid -> Integer.parseInt(sid))
                    .distinct()
                    .collect(Collectors.toList());

            FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
            List<FileErrlAdddataModel> errlDataList =
                    feaDao.getErrlAddDataList(feaSidList, reqMdl__.getSmodel());

            if (!errlDataList.isEmpty()) {
                resultList = errlDataList.stream()
                        .map(mdl -> String.valueOf(mdl.getFeaSid()))
                        .distinct()
                        .collect(Collectors.toList());
            }
        }

        return resultList;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Fil300ParamModel
     * @return errorFlg ディレクトリが存在しない場合false
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException IOエラー
     */
    public boolean setInitData(Fil300ParamModel paramMdl)
            throws SQLException, IOToolsException, IOException {

        //外貨コンボ
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
        paramMdl.setFil300GaikaList(filBiz.getMoneyLabel());

        if (paramMdl.getFil300SelectCabinet() != null) {
            if (paramMdl.getFil300SelectDir() == -1) {
                //キャビネットSIDでルートディレクトリSIDを取ってfil300SelectDirにセットする
                FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
                FileDirectoryModel fdrMdl = fdrDao.getRootDirectory(
                        NullDefault.getInt(paramMdl.getFil300SelectCabinet(), -1));
                if (fdrMdl != null) {
                    paramMdl.setFil300SelectDir(fdrMdl.getFdrSid());
                }
            }
        }

        if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
            if (paramMdl.getFil300BeforeInsertFile() == null
                    || paramMdl.getFil300BeforeInsertFile().length == 0) {
                return false;
            }

            if (paramMdl.getFil300insertMode() == GSConstFile.MODE_SINGLE) {
                if (setTreeSingleFromFil080(paramMdl) == false) {
                    return false;
                }
            }

            if (paramMdl.getFil300insertMode() == GSConstFile.MODE_MULTI) {
                if (setTreeMultiFromFil080(paramMdl) == false) {
                    return false;
                }
            }
            paramMdl.setFil300InitFlg(GSConstFile.INIT_FLG_OK);
            return true;
        }

        if (paramMdl.getFil300insertMode() == GSConstFile.MODE_SINGLE) {
            if (setTreeSingle(paramMdl) == false) {
                return false;
            }
        }

        if (paramMdl.getFil300insertMode() == GSConstFile.MODE_MULTI) {
            //保存先振り分け機能が無効になっているフォルダの設定
            if (setTreeMulti(paramMdl) == false) {
                return false;
            }
        }
        return true;

    }
    /**
     *
     * <br>[機  能] 単体操作画面用ツリー表示を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @return errorFlg ディレクトリが存在しない場合false
     * @throws SQLException
     */
    public boolean setTreeSingle(Fil300ParamModel paramMdl) throws SQLException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();

        //読み取りが可能なキャビネットコンボの設定
        __setCabinetList(con__, paramMdl, usModel);

        //キャビネット内フォルダ，ファイル
        int selectCabSid = NullDefault.getInt(paramMdl.getFil300SelectCabinet(), -1);
        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel parentCabMdl = fcbDao.select(selectCabSid);
        if (parentCabMdl == null || parentCabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return false;
        }

        List<Integer> cabSidList = new ArrayList<Integer>();

        //Tree情報取得
        cabSidList.add(selectCabSid);
        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);

        List<FileErrlAdddataModel> errlFileList;
        if (parentCabMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_NOT_USE) {
            errlFileList = feaDao.getFileCabinet(cabSidList, usModel);
        } else {
            errlFileList = feaDao.getFileAutoSortCabinet(cabSidList, usModel);
        }
        //親ディレクトリのアクセス権限チェック
        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl__, con__);
        for (int i = errlFileList.size(); i > 0; i--) {
            int code = cmnBiz.checkPowEditDir(errlFileList.get(i - 1).getFdrParentSid(),
                                   GSConstFile.DIRECTORY_FOLDER, true);
            if (code != FilCommonBiz.ERR_NONE && code != FilCommonBiz.ERR_NOT_EXIST) {
                errlFileList.remove(i - 1);
            }
        }
        __setTreeInfo(paramMdl, con__, errlFileList);
        return false;
    }

    /**
     *
     * <br>[機  能] 一括操作画面用ツリー表示を設定する
     * <br>[解  説]
     * <br>[備  考] fil080からの遷移用
     * @param paramMdl
     * @return errorFlg ディレクトリが存在しない場合false
     * @throws SQLException
     */
    public boolean setTreeMultiFromFil080(Fil300ParamModel paramMdl) throws SQLException {
        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        int cabSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1);
        FileCabinetModel fcbMdl = fcbDao.select(cabSid);

        String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;

        if (fcbMdl.getFcbJkbn() != GSConstFile.JTKBN_DELETE) {
            FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
            //保存先振り分け機能が有効の場合、一番上の"有効"と指定されたキャビネットだけ表示
            if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                paramMdl.setTreeFormLv0(new String[] {__getErrlOnTree()});
                StringBuilder key = new StringBuilder("");
                key.append(filBiz.getRootDirSid(fcbMdl));
                key.append(sep);
                key.append(GSConstFile.TREE_SORT_ON_DIR);
                key.append(sep);
                key.append(StringUtilHtml.transToHTmlPlusAmparsant(fcbMdl.getFcbName()));
                key.append(sep);

                List<Integer> feaSidList = Stream.of(paramMdl.getFil300BeforeInsertFile())
                        .map(str -> NullDefault.getInt(str, -1))
                        .collect(Collectors.toList());
                List<FileErrlAdddataModel> feaList =
                        feaDao.getErrlAddDataList(feaSidList, reqMdl__.getSmodel());
                key.append(feaList.size());

                key.append(sep);
                key.append(GSConstFile.MODE_MULTI);
                paramMdl.setTreeFormLv1(new String[] {key.toString()});
            }

            //保存先振り分け機能が無効の場合、無効キャビネット及び
            if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_NONE) {
                paramMdl.setTreeFormLv0(new String[] {__getErrlOffTree()});

                List<Integer> feaSidList = Stream.of(paramMdl.getFil300BeforeInsertFile())
                        .map(str -> NullDefault.getInt(str, -1))
                        .collect(Collectors.toList());
                List<FileErrlAdddataModel> feaList =
                        feaDao.getErrlAddDataList(feaSidList, reqMdl__.getSmodel());
                Map<Integer, List<String>> treeMap = new HashMap<Integer, List<String>>();
                __setMultiTree(paramMdl, feaList, treeMap);
            }
        }
        return true;
    }

    /**
     *
     * <br>[機  能] 単体操作画面用ツリー表示を設定する
     * <br>[解  説]
     * <br>[備  考] fil080からの遷移用
     * @param paramMdl
     * @return errorFlg ディレクトリが存在しない場合false
     * @throws SQLException
     */
    public boolean setTreeSingleFromFil080(Fil300ParamModel paramMdl) throws SQLException {
        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);

        List<Integer> feaSidList = Stream.of(paramMdl.getFil300BeforeInsertFile())
                .map(str -> NullDefault.getInt(str, -1))
                .collect(Collectors.toList());
        List<FileErrlAdddataModel> feaList =
                feaDao.getErrlAddDataList(feaSidList, reqMdl__.getSmodel());
        if (feaList.isEmpty()) {
            return false;
        }
        __setTreeInfo(paramMdl, con__, feaList);

        //キャビネットコンボ
        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        int cabSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1);
        FileCabinetModel fcbMdl = fcbDao.select(cabSid);
        if (fcbMdl.getFcbJkbn() != GSConstFile.JTKBN_DELETE) {
            List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
            labelList.add(new LabelValueBean(
                    fcbMdl.getFcbName(), String.valueOf(fcbMdl.getFcbSid())));
            paramMdl.setFil300CabinetList(labelList);
        }
        return true;
    }

    /**
     * <br>[機  能] Tree情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param errlFileList 仮登録ファイルリスト
     * @throws SQLException 実行例外
     */
    private void __setTreeInfo(Fil300ParamModel paramMdl,
            Connection con, List<FileErrlAdddataModel> errlFileList) throws SQLException {

        if (errlFileList.isEmpty()) {
            return;
        }

        FileDirectoryDao fdrDao = new FileDirectoryDao(con);
        FileCabinetDao fcbDao = new FileCabinetDao(con);
        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con);

        //自動振り分けが有効になっている場合
        int selectCabSid = NullDefault.getInt(paramMdl.getFil300SelectCabinet(), -1);
        if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
            selectCabSid = NullDefault.getInt(paramMdl.getFil010SelectCabinet(), -1);
        }
        FileCabinetModel fcbMdl = fcbDao.select(selectCabSid);
        if (fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
            return;
        }
        if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
            List<String> fileList;
            if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
                List<Integer> feaSidList = errlFileList.stream()
                        .map(mdl -> mdl.getFeaSid())
                        .collect(Collectors.toList());
                fileList = feaDao.getErrlFileTreeList(selectCabSid, feaSidList);
            } else {
                //キャビネット内のファイルを全て表示
                fileList = fdrDao.getErrlFileTreeList(selectCabSid);
            }
            paramMdl.setTreeFormLv0(fileList.toArray(new String[fileList.size()]));
            return;
        }

        List<String> parentSid = errlFileList.stream()
                .map(mdl -> String.valueOf(mdl.getFdrParentSid()))
                .collect(Collectors.toList());

        List<FileDirectoryModel> parentFolder =
                fdrDao.getNewDirectoryList(parentSid.toArray(new String[parentSid.size()]));

        //ファイル件数の設定
        Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();
        for (String parentSidStr : parentSid) {
            int parentSidInt = Integer.parseInt(parentSidStr);
            if (countMap.get(parentSidInt) == null) {
                countMap.put(parentSidInt, 1);
                continue;
            }
            //ファイル件数の設定
            countMap.put(parentSidInt, countMap.get(parentSidInt) + 1);
        }

        List<Integer> levelList = parentFolder.stream()
                .map(mdl -> mdl.getFdrLevel())
                .collect(Collectors.toList());

        int maxLevel = 0;
        if (levelList != null && !levelList.isEmpty()) {
            maxLevel = Collections.max(levelList);
        }

        //レベル：ツリー一覧
        Map<Integer, List<String>> treeMap = new HashMap<Integer, List<String>>();
        //ディレクトリSID：レベル
        Map<Integer, Integer> levelMap = new HashMap<Integer, Integer>();

        String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
        for (int level = maxLevel; level >= GSConstFile.DIRECTORY_LEVEL_0; level--) {

           List<FileDirectoryModel> folderlist = new ArrayList<FileDirectoryModel>();
           Set<Integer> addedSet = new HashSet<Integer>();
           for (FileDirectoryModel mdl : parentFolder) {
               if (mdl.getFdrLevel() == level) {
                   if (addedSet.add(mdl.getFdrSid())) {
                       folderlist.add(mdl);
                   }
               }
           }

           List<String> fdrSidList = new ArrayList<String>();
           for (FileDirectoryModel folderMdl : folderlist) {
               if (folderMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                   continue;
               }
               if (folderMdl.getFdrLevel() == GSConstFile.DIRECTORY_LEVEL_0) {
                   levelMap.put(folderMdl.getFdrSid(), GSConstFile.DIRECTORY_LEVEL_0);
                   continue;
               }

               Integer fileCount = countMap.get(folderMdl.getFdrSid());
               fdrSidList.add(String.valueOf(folderMdl.getFdrParentSid()));

               //親フォルダに自身のファイル件数を追加
               int parentDirSid = folderMdl.getFdrParentSid();
               Integer parentFileCount = countMap.get(parentDirSid);
               if (parentFileCount == null) {
                   countMap.put(parentDirSid, fileCount);
               } else {
                   countMap.put(parentDirSid, parentFileCount + fileCount);
               }

               //ファイルツリーに必要な階層データの保持
               levelMap.put(folderMdl.getFdrSid(), folderMdl.getFdrLevel());

               StringBuilder key = new StringBuilder("");
               key.append(folderMdl.getFdrSid());
               key.append(sep);
               key.append(folderMdl.getFdrParentSid());
               key.append(sep);
               key.append(StringUtilHtml.transToHTmlPlusAmparsant(folderMdl.getFdrName()));
               key.append(sep);
               key.append(fileCount);
               key.append(sep);
               key.append(GSConstFile.MODE_SINGLE);
               key.append(sep);
               key.append(folderMdl.getFcbSid());

               String addText = key.toString();
               List<String> tree = treeMap.get(level);
               if (tree == null) {
                   tree = new ArrayList<String>();
                   tree.add(addText);
                   treeMap.put(level, tree);
                   continue;
               }
               tree.add(addText);
               treeMap.put(level, tree);
           }
           //親階層のフォルダをフォルダ一覧に追加
           List<FileDirectoryModel> parent =
                   fdrDao.getNewDirectoryList(fdrSidList.toArray(new String[fdrSidList.size()]));
           if (parent != null) {
               parentFolder.addAll(parent);
           }
        }

        //ファイルのみのツリー設定
        List<String> noParentTree = new ArrayList<String>();
        for (FileErrlAdddataModel feaMdl : errlFileList) {
            Integer fileLevel = levelMap.get(feaMdl.getFdrParentSid());
            //親フォルダが存在しない場合
            if (fileLevel == null) {
                StringBuilder key = new StringBuilder("");
                key.append(0);
                key.append(sep);
                key.append(GSConstFile.TREE_NO_SAVEPATH_DIR);
                key.append(sep);
                key.append(StringUtilHtml.transToHTmlPlusAmparsant(feaMdl.getFdrName()));
                key.append(sep);
                key.append(0);
                key.append(sep);
                key.append(GSConstFile.MODE_SINGLE);
                key.append(sep);
                key.append(feaMdl.getFcbSid());
                key.append(sep);
                key.append(feaMdl.getFeaSid());
                key.append(sep);
                key.append(feaMdl.getBinSid());

                noParentTree.add(key.toString());
                continue;
            }

            StringBuilder key = new StringBuilder("");
            key.append(String.valueOf(0));
            key.append(sep);
            key.append(feaMdl.getFdrParentSid());
            key.append(sep);
            key.append(StringUtilHtml.transToHTmlPlusAmparsant(feaMdl.getFdrName()));
            key.append(sep);
            key.append(feaMdl.getFdrParentSid());
            key.append(sep);
            key.append(GSConstFile.MODE_SINGLE);
            key.append(sep);
            key.append(feaMdl.getFcbSid());
            key.append(sep);
            key.append(feaMdl.getFeaSid());
            key.append(sep);
            key.append(feaMdl.getBinSid());

            List<String> tree = treeMap.get(fileLevel + 1);
            if (tree == null) {
                tree = new ArrayList<String>();
            }
            tree.add(key.toString());
            treeMap.put(fileLevel + 1, tree);
        }

        //保存先が存在しないファイルが有る場合、"保存先が存在しないファイル"を作成
        if (!noParentTree.isEmpty()) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            StringBuilder key = new StringBuilder("");
            key.append(GSConstFile.TREE_NO_SAVEPATH_DIR);
            key.append(sep);
            key.append(0);
            key.append(sep);
            key.append(gsMsg.getMessage("fil.180"));
            key.append(sep);
            key.append(noParentTree.size());
            key.append(sep);
            key.append(GSConstFile.MODE_SINGLE);
            key.append(sep);
            key.append(selectCabSid);

            List<String> firstFolder = treeMap.get(GSConstFile.DIRECTORY_LEVEL_1);
            if (firstFolder == null) {
                firstFolder = new ArrayList<String>();
            }
            firstFolder.add(key.toString());
            treeMap.put(GSConstFile.DIRECTORY_LEVEL_1, firstFolder);

            List<String> secondDir = treeMap.get(GSConstFile.DIRECTORY_LEVEL_2);
            if (secondDir == null) {
                secondDir = new ArrayList<String>();
            }
            secondDir.addAll(noParentTree);
            treeMap.put(GSConstFile.DIRECTORY_LEVEL_2, secondDir);
        }
        paramMdl.setTreeFormLv0(new String[0]);
        paramMdl.setTreeFormLv1(new String[0]);
        paramMdl.setTreeFormLv2(new String[0]);
        paramMdl.setTreeFormLv3(new String[0]);
        paramMdl.setTreeFormLv4(new String[0]);
        paramMdl.setTreeFormLv5(new String[0]);
        paramMdl.setTreeFormLv6(new String[0]);
        paramMdl.setTreeFormLv7(new String[0]);
        paramMdl.setTreeFormLv8(new String[0]);
        paramMdl.setTreeFormLv9(new String[0]);
        paramMdl.setTreeFormLv10(new String[0]);

        List<String> treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_1);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_0)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv0(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_2);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_1)
                .collect(Collectors.toList());

        paramMdl.setTreeFormLv1(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_3);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_2)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv2(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_4);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_3)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv3(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_5);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_4)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv4(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_6);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_5)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv5(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_7);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_6)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv6(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_8);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_7)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv7(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_9);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_8)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv8(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_10);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_9)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv9(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_11);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_10)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv10(treeList.toArray(new String[treeList.size()]));
    }


    /**
     * <br>[機  能] 一括操作画面用ツリー表示を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return errorFlg ディレクトリが存在しない場合false
     * @throws SQLException 実行例外
     */
    public boolean setTreeMulti(Fil300ParamModel paramMdl) throws SQLException {

        //自動振り分けが無効なファイルの取得
        ArrayList<FileCabinetDspModel> cabList = new ArrayList<>();
        cabList.addAll(cash__.getCabinetList());

        //自動振り分け = なし のキャビネットを取得
        List<Integer> cabSidSortOff = cabList.stream()
                .filter(mdl -> mdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_NONE)
                .map(mdl -> mdl.getFcbSid())
                .collect(Collectors.toList());

        //自動振り分け = あり のキャビネットを取得
        //※ユーザが権限を持たないキャビネットは除外する
        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);
        List<Integer> cabSidSortOn = new ArrayList<Integer>();
        for (FileCabinetDspModel fcbMdl : cabList) {
            if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                if (filCmnBiz.checkPowEditRootDir(fcbMdl.getFcbSid()) == FilCommonBiz.ERR_NONE) {
                    cabSidSortOn.add(fcbMdl.getFcbSid());
                }
            }
        }

        //Tree情報取得
        String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;

        //レベル：ツリー一覧
        Map<Integer, List<String>> treeMap = new HashMap<Integer, List<String>>();
        //ディレクトリSID：レベル
        Map<Integer, Integer> levelMap = new HashMap<Integer, Integer>();

        //振り分け機能が有効になっているキャビネットの取得
        FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
        List<String> autoSortTree = fdrDao.getErrlTreeList(cabSidSortOn);
        treeMap.put(GSConstFile.DIRECTORY_LEVEL_0, autoSortTree);

        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
        List<FileErrlAdddataModel> errlFileList =
                feaDao.getFileCabinet(
                        cabSidSortOff,
                        reqMdl__.getSmodel()
                );


        //一番上の要素として、保存先振り分け機能：有効，無効を追加
        List<String> topFolder = new ArrayList<String>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (!autoSortTree.isEmpty()) {
            topFolder.add(__getErrlOnTree());
        }
        if (!errlFileList.isEmpty()) {
            topFolder.add(__getErrlOffTree());
        }
        if (topFolder.isEmpty()) {
            return true;
        }
        paramMdl.setTreeFormLv0(topFolder.toArray(new String[topFolder.size()]));

        if (!errlFileList.isEmpty()) {
            List<String> parentSid = errlFileList.stream()
                    .map(mdl -> String.valueOf(mdl.getFdrParentSid()))
                    .collect(Collectors.toList());

            List<FileDirectoryModel> parentFolder =
                    fdrDao.getNewDirectoryList(parentSid.toArray(new String[parentSid.size()]));

            //ファイル件数の設定
            Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();
            for (String parentSidStr : parentSid) {
                int parentSidInt = Integer.parseInt(parentSidStr);
                if (countMap.get(parentSidInt) == null) {
                    countMap.put(parentSidInt, 1);
                    continue;
                }
                //ファイル件数の設定
                countMap.put(parentSidInt, countMap.get(parentSidInt) + 1);
            }

            List<Integer> levelList = parentFolder.stream()
                    .map(mdl -> mdl.getFdrLevel())
                    .collect(Collectors.toList());

            int maxLevel = 0;
            if (levelList != null && !levelList.isEmpty()) {
                maxLevel = Collections.max(levelList);
            }

            //保存先が存在しないファイルのキャビネット設定用
            List<Integer> noParentCabSidList = new ArrayList<Integer>();

            for (int level = maxLevel; level >= GSConstFile.DIRECTORY_LEVEL_0; level--) {

               List<FileDirectoryModel> folderlist = new ArrayList<FileDirectoryModel>();
               Set<Integer> addedSet = new HashSet<Integer>();
               for (FileDirectoryModel mdl : parentFolder) {
                   if (mdl.getFdrLevel() == level) {
                       if (addedSet.add(mdl.getFdrSid())) {
                           folderlist.add(mdl);
                       }
                   }
               }

               List<String> fdrSidList = new ArrayList<String>();
               for (FileDirectoryModel folderMdl : folderlist) {
                   if (folderMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                       continue;
                   }

                   Integer fileCount = countMap.get(folderMdl.getFdrSid());
                   fdrSidList.add(String.valueOf(folderMdl.getFdrParentSid()));

                   //親フォルダに自身のファイル件数を追加
                   int parentDirSid = folderMdl.getFdrParentSid();
                   Integer parentFileCount = countMap.get(parentDirSid);
                   if (parentFileCount == null) {
                       countMap.put(parentDirSid, fileCount);
                   } else {
                       countMap.put(parentDirSid, parentFileCount + fileCount);
                   }

                   //ファイルツリーに必要な階層データの保持
                   levelMap.put(folderMdl.getFdrSid(), folderMdl.getFdrLevel());

                   StringBuilder key = new StringBuilder("");
                   key.append(folderMdl.getFdrSid());
                   key.append(sep);
                   if (level == GSConstFile.DIRECTORY_LEVEL_0) {
                       key.append(-2);
                   } else {
                       key.append(folderMdl.getFdrParentSid());
                   }
                   key.append(sep);
                   key.append(StringUtilHtml.transToHTmlPlusAmparsant(folderMdl.getFdrName()));
                   key.append(sep);
                   if (level == GSConstFile.DIRECTORY_LEVEL_0) {
                       key.append(errlFileList.stream()
                               .filter(mdl -> mdl.getFcbSid() == folderMdl.getFcbSid())
                               .collect(Collectors.toList()).size());
                   } else {
                       key.append(fileCount);
                   }
                   key.append(sep);
                   key.append(GSConstFile.MODE_MULTI);
                   key.append(sep);
                   key.append(folderMdl.getFcbSid());

                   String addText = key.toString();
                   List<String> tree = treeMap.get(level);
                   if (tree == null) {
                       tree = new ArrayList<String>();
                       tree.add(addText);
                       treeMap.put(level, tree);
                       if (level == GSConstFile.DIRECTORY_LEVEL_0) {
                           noParentCabSidList.add(folderMdl.getFcbSid());
                       }
                       continue;
                   }
                   tree.add(addText);
                   treeMap.put(level, tree);
                   if (level == GSConstFile.DIRECTORY_LEVEL_0) {
                       noParentCabSidList.add(folderMdl.getFcbSid());
                   }
               }
               //親階層のフォルダをフォルダ一覧に追加
               List<FileDirectoryModel> parent = fdrDao.getNewDirectoryList(
                       fdrSidList.toArray(new String[fdrSidList.size()]));
               if (parent != null) {
                   parentFolder.addAll(parent);
               }
            }

            //ディレクトリSID：保存先が存在しないファイルの件数を保持
            Map<Integer, Integer> noParentCountMap = new HashMap<Integer, Integer>();
            //ディレクトリSID：キャビネットSIDを保持
            Map<Integer, Integer> dirCabMap = new HashMap<Integer, Integer>();

            FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
            for (FileErrlAdddataModel feaMdl : errlFileList) {
                Integer fileLevel = levelMap.get(feaMdl.getFdrParentSid());
                //親フォルダが存在しない場合
                if (fileLevel == null) {
                    FileCabinetModel cabMdl = new FileCabinetModel();
                    cabMdl.setFcbSid(feaMdl.getFcbSid());
                    int dirSid = filBiz.getRootDirSid(cabMdl);
                    Integer count = noParentCountMap.get(dirSid);
                    if (count == null) {
                        count = 0;
                    }
                    noParentCountMap.put(dirSid, count + 1);
                    dirCabMap.put(dirSid, feaMdl.getFcbSid());
                }
            }

            //保存先が存在しないファイルが有る場合、"保存先が存在しないファイル"を作成
            List<String> noParentTree = new ArrayList<String>();
            for (Map.Entry<Integer, Integer> entry : noParentCountMap.entrySet()) {
                StringBuilder key = new StringBuilder("");

                //保存先が存在しないファイルのため、子フォルダはない
                key.append(GSConstFile.TREE_NO_SAVEPATH_DIR);
                key.append(sep);
                key.append(entry.getKey());
                key.append(sep);
                key.append(gsMsg.getMessage("fil.180"));
                key.append(sep);
                key.append(entry.getValue());
                key.append(sep);
                key.append(GSConstFile.MODE_MULTI);
                key.append(sep);
                key.append(dirCabMap.get(entry.getKey()));
                noParentTree.add(key.toString());
            }
            if (!noParentTree.isEmpty()) {
                List<String> secondDir = treeMap.get(GSConstFile.DIRECTORY_LEVEL_1);
                if (secondDir == null) {
                    secondDir = new ArrayList<String>();
                }
                secondDir.addAll(noParentTree);
                treeMap.put(GSConstFile.DIRECTORY_LEVEL_1, secondDir);
            }

            for (Map.Entry<Integer, Integer> entry : dirCabMap.entrySet()) {
                if (!noParentCabSidList.contains(entry.getValue())) {
                    noParentCabSidList.add(entry.getValue());
                    StringBuilder key = new StringBuilder("");
                    FileDirectoryModel dirMdl = fdrDao.getRootDirectory(entry.getValue());
                    //保存先が存在しないファイルのため、子フォルダはない
                    key.append(dirMdl.getFdrSid());
                    key.append(sep);
                    key.append(GSConstFile.TREE_SORT_OFF_DIR);
                    key.append(sep);
                    key.append(StringUtilHtml.transToHTmlPlusAmparsant(dirMdl.getFdrName()));
                    key.append(sep);
                    key.append(noParentCountMap.get(dirMdl.getFdrSid()));
                    key.append(sep);
                    key.append(GSConstFile.MODE_MULTI);
                    key.append(sep);
                    key.append(entry.getValue());
                    List<String> rootDir = new ArrayList<String>();
                    rootDir.add(key.toString());
                    List<String> secondDir = treeMap.get(GSConstFile.DIRECTORY_LEVEL_0);
                    if (secondDir == null) {
                        secondDir = new ArrayList<String>();
                    }
                    secondDir.addAll(rootDir);
                    treeMap.put(GSConstFile.DIRECTORY_LEVEL_0, secondDir);
                }
            }
        }

        List<String> treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_0);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_1)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv1(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_1);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_2)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv2(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_2);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_3)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv3(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_3);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_4)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv4(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_4);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_5)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv5(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_5);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_6)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv6(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_6);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_7)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv7(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_7);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_8)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv8(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_8);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_9)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv9(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_9);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_10)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv10(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_10);
        if (treeList == null || treeList.isEmpty()) {
            return true;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + 11)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv11(treeList.toArray(new String[treeList.size()]));
        return true;
    }

    /**
     * <br>[機  能] 一括登録時のTree情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param errlFileList 仮登録ファイル
     * @param treeMap ツリー情報マップ
     * @throws SQLException 実行例外
     */
    private void __setMultiTree(Fil300ParamModel paramMdl,
            List<FileErrlAdddataModel> errlFileList,
            Map<Integer, List<String>> treeMap) throws SQLException {

        //ディレクトリSID：レベル
        Map<Integer, Integer> levelMap = new HashMap<Integer, Integer>();
        String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
        if (!errlFileList.isEmpty()) {
            List<String> parentSid = errlFileList.stream()
                    .map(mdl -> String.valueOf(mdl.getFdrParentSid()))
                    .collect(Collectors.toList());

            List<FileDirectoryModel> parentFolder =
                    fdrDao.getNewDirectoryList(parentSid.toArray(new String[parentSid.size()]));

            //ファイル件数の設定
            Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();
            for (String parentSidStr : parentSid) {
                int parentSidInt = Integer.parseInt(parentSidStr);
                if (countMap.get(parentSidInt) == null) {
                    countMap.put(parentSidInt, 1);
                    continue;
                }
                //ファイル件数の設定
                countMap.put(parentSidInt, countMap.get(parentSidInt) + 1);
            }

            List<Integer> levelList = parentFolder.stream()
                    .map(mdl -> mdl.getFdrLevel())
                    .collect(Collectors.toList());

            int maxLevel = 0;
            if (levelList != null && !levelList.isEmpty()) {
                maxLevel = Collections.max(levelList);
            }

            for (int level = maxLevel; level >= GSConstFile.DIRECTORY_LEVEL_0; level--) {
               List<FileDirectoryModel> folderlist = new ArrayList<FileDirectoryModel>();
               Set<Integer> addedSet = new HashSet<Integer>();
               for (FileDirectoryModel mdl : parentFolder) {
                   if (mdl.getFdrLevel() == level) {
                       if (addedSet.add(mdl.getFdrSid())) {
                           folderlist.add(mdl);
                       }
                   }
               }

               List<String> fdrSidList = new ArrayList<String>();
               for (FileDirectoryModel folderMdl : folderlist) {
                   if (folderMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                       continue;
                   }

                   Integer fileCount = countMap.get(folderMdl.getFdrSid());
                   fdrSidList.add(String.valueOf(folderMdl.getFdrParentSid()));

                   //親フォルダに自身のファイル件数を追加
                   int parentDirSid = folderMdl.getFdrParentSid();
                   Integer parentFileCount = countMap.get(parentDirSid);
                   if (parentFileCount == null) {
                       countMap.put(parentDirSid, fileCount);
                   } else {
                       countMap.put(parentDirSid, parentFileCount + fileCount);
                   }

                   //ファイルツリーに必要な階層データの保持
                   levelMap.put(folderMdl.getFdrSid(), folderMdl.getFdrLevel());

                   StringBuilder key = new StringBuilder("");
                   key.append(folderMdl.getFdrSid());
                   key.append(sep);
                   if (level == GSConstFile.DIRECTORY_LEVEL_0) {
                       key.append(-2);
                   } else {
                       key.append(folderMdl.getFdrParentSid());
                   }
                   key.append(sep);
                   key.append(StringUtilHtml.transToHTmlPlusAmparsant(folderMdl.getFdrName()));
                   key.append(sep);
                   key.append(fileCount);
                   key.append(sep);
                   key.append(GSConstFile.MODE_MULTI);
                   key.append(sep);
                   key.append(folderMdl.getFcbSid());

                   String addText = key.toString();
                   List<String> tree = treeMap.get(level);
                   if (tree == null) {
                       tree = new ArrayList<String>();
                       tree.add(addText);
                       treeMap.put(level, tree);
                       continue;
                   }
                   tree.add(addText);
                   treeMap.put(level, tree);
               }
               //親階層のフォルダをフォルダ一覧に追加
               List<FileDirectoryModel> parent = fdrDao.getNewDirectoryList(
                       fdrSidList.toArray(new String[fdrSidList.size()]));
               if (parent != null) {
                   parentFolder.addAll(parent);
               }
            }

            //ディレクトリSID：保存先が存在しないファイルの件数を保持
            Map<Integer, Integer> noParentCountMap = new HashMap<Integer, Integer>();
            //ディレクトリSID：キャビネットSIDを保持
            Map<Integer, Integer> dirCabMap = new HashMap<Integer, Integer>();

            FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
            for (FileErrlAdddataModel feaMdl : errlFileList) {
                Integer fileLevel = levelMap.get(feaMdl.getFdrParentSid());
                //親フォルダが存在しない場合
                if (fileLevel == null) {
                    FileCabinetModel cabMdl = new FileCabinetModel();
                    cabMdl.setFcbSid(feaMdl.getFcbSid());
                    int dirSid = filBiz.getRootDirSid(cabMdl);
                    Integer count = noParentCountMap.get(dirSid);
                    if (count == null) {
                        count = 0;
                    }
                    noParentCountMap.put(dirSid, count + 1);
                    dirCabMap.put(dirSid, feaMdl.getFcbSid());
                }
            }

            //保存先が存在しないファイルが有る場合、"保存先が存在しないファイル"を作成
            List<String> noParentTree = new ArrayList<String>();
            for (Map.Entry<Integer, Integer> entry : noParentCountMap.entrySet()) {
                StringBuilder key = new StringBuilder("");

                //保存先が存在しないファイルのため、子フォルダはない
                key.append(GSConstFile.TREE_NO_SAVEPATH_DIR);
                key.append(sep);
                key.append(entry.getKey());
                key.append(sep);
                key.append(gsMsg.getMessage("fil.180"));
                key.append(sep);
                key.append(entry.getValue());
                key.append(sep);
                key.append(GSConstFile.MODE_MULTI);
                key.append(sep);
                key.append(dirCabMap.get(entry.getKey()));
                noParentTree.add(key.toString());
            }
            if (!noParentTree.isEmpty()) {
                List<String> secondDir = treeMap.get(GSConstFile.DIRECTORY_LEVEL_1);
                if (secondDir == null) {
                    secondDir = new ArrayList<String>();
                }
                secondDir.addAll(noParentTree);
                treeMap.put(GSConstFile.DIRECTORY_LEVEL_1, secondDir);
                if (treeMap.get(GSConstFile.DIRECTORY_LEVEL_0) == null
                        && paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
                    paramMdl.getFil010SelectCabinet();
                    List<String> rootDir = new ArrayList<String>();
                    StringBuilder key = new StringBuilder("");

                    FileDirectoryModel dirMdl = fdrDao.getRootDirectory(NullDefault.getInt(
                            paramMdl.getFil010SelectCabinet(), -1));
                    //保存先が存在しないファイルのため、子フォルダはない
                    key.append(dirMdl.getFdrSid());
                    key.append(sep);
                    key.append(GSConstFile.TREE_SORT_OFF_DIR);
                    key.append(sep);
                    key.append(StringUtilHtml.transToHTmlPlusAmparsant(dirMdl.getFdrName()));
                    key.append(sep);
                    key.append(errlFileList.size());
                    key.append(sep);
                    key.append(GSConstFile.MODE_MULTI);
                    key.append(sep);
                    key.append(paramMdl.getFil010SelectCabinet());
                    rootDir.add(key.toString());
                    treeMap.put(GSConstFile.DIRECTORY_LEVEL_0, rootDir);
                }
            }
        }
        List<String> treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_0);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_1)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv1(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_1);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_2)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv2(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_2);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_3)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv3(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_3);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_4)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv4(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_4);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_5)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv5(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_5);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_6)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv6(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_6);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_7)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv7(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_7);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_8)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv8(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_8);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_9)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv9(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_9);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + GSConstFile.DIRECTORY_LEVEL_10)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv10(treeList.toArray(new String[treeList.size()]));

        treeList = treeMap.get(GSConstFile.DIRECTORY_LEVEL_10);
        if (treeList == null || treeList.isEmpty()) {
            return;
        }
        treeList = treeList.stream()
                .map(mdl -> mdl + sep + 11)
                .collect(Collectors.toList());
        paramMdl.setTreeFormLv11(treeList.toArray(new String[treeList.size()]));
    }

     /**
      * <br>[機  能] 保存先振り分け機能：有効のツリーの取得
      * <br>[解  説]
      * <br>[備  考]
      * @return 保存先振り分け機能：有効のツリー表示用テキスト
      */
     private String __getErrlOnTree() {
         GsMessage gsMsg = new GsMessage(reqMdl__);
         String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
         StringBuilder sbOn = new StringBuilder();
         sbOn.append(GSConstFile.TREE_SORT_ON_DIR);
         sbOn.append(sep);
         sbOn.append(0);
         sbOn.append(sep);
         sbOn.append(gsMsg.getMessage("fil.19") + "：" + gsMsg.getMessage("cmn.effective"));
         sbOn.append(sep);
         sbOn.append(0);
         sbOn.append(sep);
         sbOn.append(GSConstFile.MODE_MULTI);
         return sbOn.toString();
     }

     /**
      * <br>[機  能] 保存先振り分け機能：無効のツリーの取得
      * <br>[解  説]
      * <br>[備  考]
      * @return 保存先振り分け機能：無効のツリー表示用テキスト
      */
     private String __getErrlOffTree() {
         GsMessage gsMsg = new GsMessage(reqMdl__);
         String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
         StringBuilder sbOn = new StringBuilder();
         sbOn.append(GSConstFile.TREE_SORT_OFF_DIR);
         sbOn.append(sep);
         sbOn.append(0);
         sbOn.append(sep);
         sbOn.append(gsMsg.getMessage("fil.19") + "：" + gsMsg.getMessage("cmn.invalid"));
         sbOn.append(sep);
         sbOn.append(0);
         sbOn.append(sep);
         sbOn.append(GSConstFile.MODE_MULTI);
         return sbOn.toString();
     }


     /**
      * <br>[機  能] 取引情報入力欄に表示するファイル一覧を設定する
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param paramMdl パラメータ情報
      * @param savePathFlg true:保存先が存在する，false:保存先が存在しない
      * @return 最大件数
      * @throws SQLException SQL実行時例外
      */
     public long setAddFileList(
             Fil300ParamModel paramMdl, boolean savePathFlg) throws SQLException {

         FileErrlAdddataDao dao = new FileErrlAdddataDao(con__);
         FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
         FileCabinetDao fcbDao = new FileCabinetDao(con__);
         int page = paramMdl.getFil300SelectPage();
         long ret = 0;

         //ファイル登録画面から遷移している場合、ファイルは全て同じ階層にあるため全て表示
         if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {

             int cabSid = 0;
             if (savePathFlg) {
                 int parentSid = paramMdl.getFil300SelectDir();
                 FileDirectoryModel mdl = fdrDao.getNewDirectory(parentSid);
                 cabSid = mdl.getFcbSid();
             } else {
                 cabSid = NullDefault.getInt(paramMdl.getFil300SelectCabinet(), -1);
             }
             FileCabinetModel fcbMdl = fcbDao.select(cabSid);
             if (paramMdl.getFil300BeforeInsertFile() == null
                     || paramMdl.getFil300BeforeInsertFile().length < 1) {
                 return 0;
             }
             List<String> feaSidStrList = Arrays.asList(paramMdl.getFil300BeforeInsertFile());
             List<Integer> feaSidList = feaSidStrList.stream()
                     .map(str -> NullDefault.getInt(str, -1))
                     .collect(Collectors.toList());

             //仮登録モデルリスト
             List<FileErrlAdddataModel> errlMdlList
             = dao.getErrlAddDataList(feaSidList, reqMdl__.getSmodel());

             ret = errlMdlList.size();
             page = PageUtil.getPageAdjustedOverflow(page,
                     ret,
                     GSConstFile.MAX_DSP_ERRL_ADDDATA);

             //表示件数を15件までに変更
             int maxIdx = page * GSConstFile.MAX_DSP_ERRL_ADDDATA;
             if (ret - 1 < page * GSConstFile.MAX_DSP_ERRL_ADDDATA) {
                 maxIdx = (int) ret;
             }

             errlMdlList = errlMdlList.subList(
                     (page - 1) * GSConstFile.MAX_DSP_ERRL_ADDDATA, maxIdx);

             if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                 List<Integer> fcbSidList = new ArrayList<Integer>();
                 fcbSidList.add(cabSid);
                 paramMdl.setFil300AddFileDspList(__customErrlNameAuto(errlMdlList, cabSid));
             } else {
                 paramMdl.setFil300AddFileDspList(__customErrlName(errlMdlList));
             }
             paramMdl.setFil300SelectPage(page);

             return ret;
         }
         int limit = GSConstFile.MAX_DSP_ERRL_ADDDATA;
         int cabSid = NullDefault.getInt(paramMdl.getFil300SelectCabinet(), -1);
         FileErrlAdddataGetter addDataGetter;
         try {
             addDataGetter = FileErrlAdddataGetter.builder(reqMdl__, con__)
                     .chainFcbSid(cabSid)
                     .chainFdrSid(paramMdl.getFil300SelectDir())
                     .chainNoSavePathFlg(
                             savePathFlg == false
                             )
                     .chainPage(page)
                     .chainLimitSize(limit)
                     .build();
             List<FileErrlAdddataModel> errlMdlList = new ArrayList<FileErrlAdddataModel>();

             errlMdlList.addAll(addDataGetter.getDataList());

             long maxCount = addDataGetter.getMaxCount();
             page = addDataGetter.getPage();
             if (maxCount <= 0) {
                 return ret;
             }
             ret = (int) maxCount;
             paramMdl.setFil300SelectPage(page);

             switch (addDataGetter.getDirType()) {
             case AutoSortedCabinet:
                 paramMdl.setFil300AddFileDspList(__customErrlNameAuto(errlMdlList, cabSid));
                 break;
             case Directory:
                 paramMdl.setFil300AddFileDspList(__customErrlName(errlMdlList));
                 break;
             case NoSavePath:
                 List<FileErrlAdddataModel> createErrlMdlList =
                 new ArrayList<FileErrlAdddataModel>();
                 FileCabinetModel fcbMdl = fcbDao.select(cabSid);
                 for (FileErrlAdddataModel errlMdl : errlMdlList) {
                     errlMdl.setFdrName(StringUtilHtml.transToHTmlPlusAmparsant(fcbMdl.getFcbName())
                             + "/" + StringUtilHtml.transToHTmlPlusAmparsant(errlMdl.getFdrName()));
                     createErrlMdlList.add(errlMdl);
                 }
                 paramMdl.setFil300AddFileDspList(createErrlMdlList);
                 break;
             default:
                 return 0;
             }
         } catch (FilErrlAdddataException e) {
             return ret;
         }

         return ret;

     }

      /**
       * <br>[機  能] 仮登録ファイル名にキャビネット名を追加する
       * <br>[解  説]
       * <br>[備  考]
       *
       * @param errlMdlList 仮登録ファイルリスト
       * @param cabSid キャビネットSID
       * @return 最大件数
       * @throws SQLException SQL実行時例外
       */
       public List<FileErrlAdddataModel> __customErrlNameAuto(
               List<FileErrlAdddataModel> errlMdlList, int cabSid) throws SQLException {
           FileCabinetDao fcbDao = new FileCabinetDao(con__);
           FileCabinetModel fcbMdl = fcbDao.select(cabSid);
           List<FileErrlAdddataModel> createErrlMdlList =
                   new ArrayList<FileErrlAdddataModel>();
           for (FileErrlAdddataModel errlMdl : errlMdlList) {
               errlMdl.setFdrName(StringUtilHtml.transToHTmlPlusAmparsant(fcbMdl.getFcbName())
                       + "/" + StringUtilHtml.transToHTmlPlusAmparsant(errlMdl.getFdrName()));
               createErrlMdlList.add(errlMdl);
           }
           return createErrlMdlList;
       }

       /**
        * <br>[機  能] 仮登録ファイル名に親階層のフォルダ名を追加する
        * <br>[解  説]
        * <br>[備  考]
        *
        * @param errlMdlList 仮登録ファイルリスト
        * @return 最大件数
        * @throws SQLException SQL実行時例外
        */
        public List<FileErrlAdddataModel> __customErrlName(
                List<FileErrlAdddataModel> errlMdlList) throws SQLException {

            FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
            List<FileErrlAdddataModel> createErrlMdlList =
                    new ArrayList<FileErrlAdddataModel>();
            List<FileErrlAdddataModel> ret =
                    new ArrayList<FileErrlAdddataModel>();
            for (int i = 10; i >= 0; i--) {

                //チェックする階層から仮登録モデルリストの親ディレクトを取得する。
                List<String> parentSidList = errlMdlList.stream()
                        .map(emdl -> String.valueOf(emdl.getFdrParentSid()))
                        .collect(Collectors.toList());;
                if (parentSidList.size() == 0) {
                    break;
                }

                HashMap<Integer, FileDirectoryModel> dirMap =
                        fdrDao.getNewDirectoryMap(parentSidList);
                createErrlMdlList = new ArrayList<FileErrlAdddataModel>();
                for (FileErrlAdddataModel errlMdl : errlMdlList) {
                    FileDirectoryModel dirMdl = dirMap.get(errlMdl.getFdrParentSid());
                    if (dirMdl != null && dirMdl.getFdrJtkbn() == GSConstFile.JTKBN_NORMAL) {
                        errlMdl.setFdrParentSid(dirMdl.getFdrParentSid());
                        errlMdl.setFdrName(
                                StringUtilHtml.transToHTmlPlusAmparsant(dirMdl.getFdrName())
                                + "/" + errlMdl.getFdrName());

                    } else if (errlMdl.getFdrParentSid() != 0) {
                        //キャビネット取得
                        //キャビネット名 + ファイル名
                        errlMdl.setFdrParentSid(0);
                        FileCabinetDao fcbDao = new FileCabinetDao(con__);
                        FileCabinetModel fcbMdl = fcbDao.select(errlMdl.getFcbSid());
                        errlMdl.setFdrName(
                                StringUtilHtml.transToHTmlPlusAmparsant(fcbMdl.getFcbName())
                                + "/" + errlMdl.getFdrName());
                    }
                    createErrlMdlList.add(errlMdl);
                }
                ret = createErrlMdlList;
            }
            return ret;
        }

       /**
        * <br>[機  能] 取引情報入力欄に表示するファイル一覧を設定する
        * <br>[解  説]
        * <br>[備  考]
        *
        * @param paramMdl パラメータ情報
        * @return 最大件数
        * @throws SQLException SQL実行時例外
        */
        public int setAddFileListNoParent(Fil300ParamModel paramMdl) throws SQLException {

            return 0;
        }

       /**
        * <br>[機  能] 単体登録を行う
        * <br>[解  説]
        * <br>[備  考]
        * @param paramMdl パラメータ情報
        * @param cntCon 採番コントローラ
        * @param appRoot ROOTパス
        * @param pconfig PluginConfig
        * @param smailPluginUseFlg ショートメール利用可能フラグ
        * @return 登録したファイル名
        * @throws Exception 実行時例外
        */
       public String insertFile(Fil300ParamModel paramMdl,
               MlCountMtController cntCon,
               String appRoot,
               PluginConfig pconfig,
               boolean smailPluginUseFlg) throws Exception {

           FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
           FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
           FileFileBinDao ffbDao = new FileFileBinDao(con__);
           FileFileRekiDao ffrDao = new FileFileRekiDao(con__);
           CmnBinfDao binfDao = new CmnBinfDao(con__);
           FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
           FileCabinetDao fcbDao = new FileCabinetDao(con__);

           int feaSid = paramMdl.getFil300SelectFile();
           FileErrlAdddataModel feaMdl = feaDao.select(feaSid);
           if (feaMdl == null) {
               return null;
           }

           UDate now = new UDate();
           int userSid = reqMdl__.getSmodel().getUsrsid();
           int cabSid = feaMdl.getFcbSid();

           FileCabinetModel fcbMdl = fcbDao.select(cabSid);
           if (fcbMdl == null || fcbMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
               return null;
           }

           /** 自動振り分けON */
           UDate tradeDate = UDate.getInstanceStr(paramMdl.getFil300TradeDate());
           tradeDate.setZeroHhMmSs();
           String tradeTarget = paramMdl.getFil300TradeTarget();

           //保存先フォルダを取得
           int parentSid = feaMdl.getFdrParentSid();
           if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
               //自動振り分け機能がある場合、親ディレクトリを取得
               FileDirectoryModel newDirMdl =
                       filBiz.getErrlDirModel(
                               cabSid, now, tradeDate, tradeTarget, cntCon, userSid,
                               feaMdl.getFeaDefgrpName());
               parentSid = newDirMdl.getFdrSid();
           } else {
               //自動振り分け機能がない場合、仮登録時の保存先フォルダを取得
               FileDirectoryModel parentDirMdl = fdrDao.getNewDirectory(parentSid);

               if (parentDirMdl == null || parentDirMdl.getFdrJtkbn() == GSConstFile.JTKBN_DELETE) {
                   if (paramMdl.getFil300SelectDir() > 0) {
                       parentDirMdl = fdrDao.getNewDirectory(paramMdl.getFil300SelectDir());
                   }
               }
               if (parentDirMdl == null) {
                   return null;
               }

               parentSid = parentDirMdl.getFdrSid();
           }

           int fileDirSid = (int) cntCon.getSaibanNumber(GSConstFile.PLUGIN_ID_FILE,
                   GSConstFile.SBNSID_SUB_DIRECTORY,
                   userSid);
           FileDirectoryModel fdrMdl = new FileDirectoryModel();
           fdrMdl.setFdrSid(fileDirSid);
           fdrMdl.setFcbSid(cabSid);
           fdrMdl.setFdrLevel(fdrDao.getNewDirectory(parentSid).getFdrLevel() + 1);
           fdrMdl.setFdrParentSid(parentSid);
           fdrMdl.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
           fdrMdl.setFdrKbn(GSConstFile.DIRECTORY_FILE);
           fdrMdl.setFdrVerKbn(GSConstFile.VERSION_KBN_ALL);
           fdrMdl.setFdrVersion(1);
           fdrMdl.setFdrAuid(userSid);
           fdrMdl.setFdrAdate(now);
           fdrMdl.setFdrEuid(userSid);
           fdrMdl.setFdrEdate(now);
           fdrMdl.setFdrBiko(feaMdl.getFdrBiko());
           String fileName = paramMdl.getFil300FileName() + feaMdl.getFflExt();
           fdrMdl.setFdrName(fileName);
           fdrMdl.setFdrTradeDate(tradeDate);
           fdrMdl.setFdrTradeTarget(tradeTarget);

           String moneyStr = paramMdl.getFil300TradeMoney();
           if (paramMdl.getFil300TradeMoneyKbn() == GSConstFile.MONEY_KBN_ON) {
               BigDecimal money = new BigDecimal(moneyStr.replaceAll(",", ""));
               fdrMdl.setFdrTradeMoney(money);
               fdrMdl.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);
               fdrMdl.setEmtSid(paramMdl.getFil300TradeMoneyGaika());
           } else {
               fdrMdl.setFdrTradeMoney(new BigDecimal(0));
               fdrMdl.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
               fdrMdl.setEmtSid(0);
           }

           //ファイル用バイナリー情報を登録する
           long binSid = feaMdl.getBinSid();
           FileFileBinModel ffbMdl = new FileFileBinModel();
           ffbMdl.setFdrSid(fileDirSid);
           ffbMdl.setFdrVersion(1);
           ffbMdl.setBinSid(binSid);
           ffbMdl.setFflExt(feaMdl.getFflExt());
           ffbMdl.setFflFileSize(feaMdl.getFflFileSize());
           ffbMdl.setFflLockKbn(GSConstFile.LOCK_KBN_OFF);
           ffbMdl.setFflLockUser(userSid);

           //バイナリ情報を修正する
           CmnBinfModel binfMdl = new CmnBinfModel();
           binfMdl.setBinFileName(fileName);
           binfMdl.setBinUpuser(userSid);
           binfMdl.setBinUpdate(now);
           binfMdl.setBinSid(feaMdl.getBinSid());

           //ディレクトリ情報モデルを設定する。
           FileFileRekiModel rekiModel = new FileFileRekiModel();
           rekiModel.setFdrVersion(1);
           rekiModel.setFfrKbn(GSConstFile.REKI_KBN_NEW);
           rekiModel.setFfrEuid(userSid);
           rekiModel.setFfrEdate(now);
           rekiModel.setFfrParentSid(parentSid);
           rekiModel.setFdrSid(fileDirSid);
           rekiModel.setFfrFname(fileName);
           rekiModel.setFfrUpCmt(feaMdl.getFfrUpCmt());
           rekiModel.setFdrTradeDate(fdrMdl.getFdrTradeDate());
           rekiModel.setFdrTradeTarget(fdrMdl.getFdrTradeTarget());
           if (paramMdl.getFil300TradeMoneyKbn() == GSConstFile.MONEY_KBN_ON) {
               BigDecimal money = new BigDecimal(moneyStr.replaceAll(",", ""));
               rekiModel.setFdrTradeMoney(money);
               rekiModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);
               rekiModel.setEmtSid(paramMdl.getFil300TradeMoneyGaika());
           } else {
               rekiModel.setFdrTradeMoney(new BigDecimal(0));
               rekiModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
               rekiModel.setEmtSid(0);
           }

           //データの登録
           fdrDao.insert(fdrMdl);
           ffbDao.insert(ffbMdl);
           ffrDao.insert(rekiModel);

           //ファイル名の更新
           binfDao.updateFileName(binfMdl);

           //仮登録ファイルの削除
           feaDao.delete(feaSid);

           //更新通知を設定する。
           filBiz.updateCall(fileDirSid, cntCon, appRoot, pconfig, smailPluginUseFlg, userSid);

           return fdrMdl.getFdrName();
       }

       /**
        * <br>[機  能] 登録，削除実行前のファイルの情報を取得する
        * <br>[解  説]
        * <br>[備  考]
        * @param paramMdl パラメータ情報
        * @return 登録したファイル名
        * @throws Exception 実行時例外
        */
       public List<Integer> beforeFile(Fil300ParamModel paramMdl) throws Exception {

           int feaSid = paramMdl.getFil300SelectFile();
           FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
           FileErrlAdddataModel feaMdl = feaDao.select(feaSid);
           if (feaMdl == null) {
               return null;
           }

           FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
           FileDirectoryModel fdrMdl = fdrDao.getNewDirectory(feaMdl.getFdrParentSid());

           FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
           FileCabinetModel fcbMdl = filBiz.getCabinetModel(feaMdl.getFdrParentSid());

           List<FileDirectoryModel> dirList =
                   filBiz.getRootToCurrentDirctoryList(fcbMdl, fdrMdl);

           List<Integer> ret = dirList.stream()
                   .map(mdl -> mdl.getFdrSid())
                   .collect(Collectors.toList());

           return ret;

       }


       /**
        * <br>[機  能] ファイルの削除を行う
        * <br>[解  説]
        * <br>[備  考]
        * @param paramMdl パラメータ情報
        * @return 登録したファイル名
        * @throws Exception 実行時例外
        */
       public String deleteFile(Fil300ParamModel paramMdl) throws Exception {

           int feaSid = paramMdl.getFil300SelectFile();
           FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
           FileErrlAdddataModel feaMdl = feaDao.select(feaSid);
           if (feaMdl == null) {
               return null;
           }
           String deleteTarget = feaMdl.getFdrName();
           feaDao.delete(feaSid);

           CmnBinfDao binfDao = new CmnBinfDao(con__);
           long binSid = feaMdl.getBinSid();
           CmnBinfModel binfMdl = new CmnBinfModel();
           binfMdl.setBinSid(binSid);
           binfMdl.setBinUpuser(reqMdl__.getSmodel().getUsrsid());
           binfMdl.setBinUpdate(new UDate());
           binfDao.removeBinData(binfMdl);

           return deleteTarget;
       }

      /**
       * <br>[機  能] 一括登録を行う
       * <br>[解  説]
       * <br>[備  考]
       * @param paramMdl パラメータ情報
       * @param msgRes MessageResources
       * @param tempDir テンポラリディレクトリパス
       * @param cntCon 採番コントローラ
        * @param appRoot ROOTパス
        * @param pconfig PluginConfig
        * @param smailPluginUseFlg ショートメール利用可能フラグ
       * @return 追加件数
    　 * @throws Exception 実行時例外
       */
       public int insertImport(Fil300ParamModel paramMdl,
               MessageResources msgRes,
               String tempDir,
               MlCountMtController cntCon,
               String appRoot,
               PluginConfig pconfig,
               boolean smailPluginUseFlg) throws Exception {

           FileCabinetDao fcbDao = new FileCabinetDao(con__);
           FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
           FileMoneyMasterDao fmmDao = new FileMoneyMasterDao(con__);
           FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
           FileFileBinDao ffbDao = new FileFileBinDao(con__);
           FileFileRekiDao ffrDao = new FileFileRekiDao(con__);
           CmnBinfDao binfDao = new CmnBinfDao(con__);

           //インポートファイルの設定
           List<String> beforeInsertFileList = null;
           if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
               beforeInsertFileList = getErrlFile080(paramMdl);
           }

           Set<Integer> set = new HashSet<Integer>();
           Fil300ReadCsv imp = new Fil300ReadCsv(
                   con__, reqMdl__, msgRes, paramMdl.getFil300SelectDir(), set,
                   beforeInsertFileList);
           imp._importCsv(tempDir);

           //登録対象の取得
           List<FileErrlAdddataDspModel> mdlList = imp.getDspList();
           mdlList = mdlList.stream()
                   .filter(mdl -> mdl.getErrorFlg() != GSConstFile.READ_KBN_ERROR)
                   .collect(Collectors.toList());


           //外貨SID取得用マップを作成
           List<String> gaikaList = mdlList.stream()
                   .map(mdl -> mdl.getTradeGaika())
                   .collect(Collectors.toList());
           Map<String, Integer> gaikaMap = fmmDao.getGaikaSidMap(gaikaList);

           FilCommonBiz filBiz = new FilCommonBiz(reqMdl__, con__);
           int fcbSid = filBiz.getCabinetSid(paramMdl.getFil300SelectDir());
           if (fcbSid == 0) {
               fcbSid = Integer.parseInt(paramMdl.getFil300SelectCabinet());
           }
           GsMessage gsMsg = new GsMessage(reqMdl__);
           FileCabinetModel parentCabMdl = fcbDao.select(fcbSid);
           if (parentCabMdl.getFcbJkbn() == GSConstFile.JTKBN_DELETE) {
               return 0;
           }
           UDate now = new UDate();

           int userSid = reqMdl__.getSmodel().getUsrsid();
           int dirSid = -1;
           List<FileDirectoryModel> insertList = new ArrayList<FileDirectoryModel>();
           List<FileFileBinModel> ffbList = new ArrayList<FileFileBinModel>();
           List<FileFileRekiModel> ffrList = new ArrayList<FileFileRekiModel>();

           /** 自動振り分けON */
           if (parentCabMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
               //自動振り分け機能がある場合の、親ディレクトリを取得
               List<FileDirectoryModel> parentDirList = new ArrayList<FileDirectoryModel>();
               for (FileErrlAdddataDspModel mdl : mdlList) {
                   UDate tradeDate = UDate.getInstanceStr(mdl.getTradeDateInput());
                   parentDirList.add(filBiz.getErrlDirModel(
                           fcbSid, now, tradeDate, mdl.getTradeTarget(), cntCon, userSid,
                           mdl.getFeaDefgrpName()));
               }

               //登録用データの作成
               for (int idx = 0; idx < mdlList.size(); idx++) {
                   FileErrlAdddataDspModel feaMdl = mdlList.get(idx);
                   FileDirectoryModel fdrMdl = new FileDirectoryModel();
                   int fileDirSid = (int) cntCon.getSaibanNumber(GSConstFile.PLUGIN_ID_FILE,
                           GSConstFile.SBNSID_SUB_DIRECTORY,
                           userSid);
                   FileErrlAdddataModel fileData = feaDao.select(feaMdl.getFeaSid());

                   fdrMdl.setFdrSid(fileDirSid);
                   fdrMdl.setFcbSid(fcbSid);
                   int parentSid = parentDirList.get(idx).getFdrSid();
                   fdrMdl.setFdrLevel(fdrDao.getNewDirectory(parentSid).getFdrLevel() + 1);
                   fdrMdl.setFdrParentSid(parentSid);
                   fdrMdl.setFdrName(feaMdl.getFdrName());
                   fdrMdl.setFdrBiko(fileData.getFdrBiko());
                   fdrMdl.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
                   fdrMdl.setFdrKbn(GSConstFile.DIRECTORY_FILE);
                   fdrMdl.setFdrVerKbn(GSConstFile.VERSION_KBN_ALL);
                   fdrMdl.setFdrVersion(1);
                   fdrMdl.setFdrAuid(userSid);
                   fdrMdl.setFdrAdate(now);
                   fdrMdl.setFdrEuid(userSid);
                   fdrMdl.setFdrEdate(now);
                   String dateStr = feaMdl.getTradeDateInput();
                   UDate date = UDate.getInstanceStr(dateStr);
                   date.setZeroHhMmSs();
                   fdrMdl.setFdrTradeDate(date);
                   fdrMdl.setFdrTradeTarget(feaMdl.getTradeTarget());
                   String moneyStr = feaMdl.getTradeMoeyInput();
                   if (!StringUtil.isNullZeroString(moneyStr)) {
                       BigDecimal money = new BigDecimal(
                               feaMdl.getTradeMoeyInput().replaceAll(",", ""));
                       fdrMdl.setFdrTradeMoney(money);
                       fdrMdl.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);
                       fdrMdl.setEmtSid(gaikaMap.get(feaMdl.getTradeGaika()));
                   } else {
                       fdrMdl.setFdrTradeMoney(new BigDecimal(0));
                       fdrMdl.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
                       fdrMdl.setEmtSid(0);
                   }
                   insertList.add(fdrMdl);

                   long binSid = fileData.getBinSid();
                   FileFileBinModel ffbMdl = new FileFileBinModel();
                   ffbMdl.setFdrSid(fileDirSid);
                   ffbMdl.setFdrVersion(1);
                   ffbMdl.setBinSid(binSid);
                   ffbMdl.setFflExt(fileData.getFflExt());
                   ffbMdl.setFflFileSize(fileData.getFflFileSize());
                   ffbMdl.setFflLockKbn(GSConstFile.LOCK_KBN_OFF);
                   ffbMdl.setFflLockUser(userSid);
                   ffbList.add(ffbMdl);

                   //ディレクトリ情報モデルを設定する。
                   FileFileRekiModel rekiModel = new FileFileRekiModel();
                   rekiModel.setFdrVersion(1);
                   rekiModel.setFfrKbn(GSConstFile.REKI_KBN_NEW);
                   rekiModel.setFfrEuid(userSid);
                   rekiModel.setFfrEdate(now);
                   rekiModel.setFfrUpCmt("");
                   rekiModel.setFfrParentSid(fdrMdl.getFdrParentSid());
                   rekiModel.setFdrSid(fileDirSid);
                   rekiModel.setFfrFname(fdrMdl.getFdrName());
                   rekiModel.setFdrTradeDate(fdrMdl.getFdrTradeDate());
                   rekiModel.setFdrTradeTarget(fdrMdl.getFdrTradeTarget());
                   if (!StringUtil.isNullZeroString(moneyStr)) {
                       BigDecimal money = new BigDecimal(
                               feaMdl.getTradeMoeyInput().replaceAll(",", ""));
                       rekiModel.setFdrTradeMoney(money);
                       rekiModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);
                       rekiModel.setEmtSid(gaikaMap.get(feaMdl.getTradeGaika()));
                   } else {
                       rekiModel.setFdrTradeMoney(new BigDecimal(0));
                       rekiModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
                       rekiModel.setEmtSid(0);
                   }
                   ffrList.add(rekiModel);

                   //バイナリ情報を修正する
                   CmnBinfModel binfMdl = new CmnBinfModel();
                   binfMdl.setBinFileName(fdrMdl.getFdrName());
                   binfMdl.setBinUpuser(userSid);
                   binfMdl.setBinUpdate(now);
                   binfMdl.setBinSid(binSid);
                   binfDao.updateFileName(binfMdl);
               }
           }

           /** 自動振り分けOFF */
           if (parentCabMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_NOT_USE) {
               List<Integer> feaSidList = mdlList.stream()
                       .map(mdl -> mdl.getFeaSid())
                       .collect(Collectors.toList());
               Map<Integer, Integer> parentMap = feaDao.getParentExist(feaSidList);
               List<Integer> existParentList = new ArrayList<Integer>(parentMap.keySet());

               //親が存在しないリストを作成
               feaSidList.removeAll(existParentList);

               //存在しないフォルダの作成
               if (feaSidList.size() > 0) {
                   //キャビネット直下に保存先不明フォルダがあるかを確認
                   int dirParentSid = fdrDao.getCabinetDirSid(fcbSid);
                   FileDirectoryModel humeiFolder = fdrDao.getErrlDirModel(
                           fcbSid, dirParentSid, gsMsg.getMessage("fil.179"), true);
                   if (humeiFolder == null) {
                       dirSid = (int) cntCon.getSaibanNumber(GSConstFile.PLUGIN_ID_FILE,
                               GSConstFile.SBNSID_SUB_DIRECTORY,
                               userSid);

                       FileDirectoryModel fdrMdl = new FileDirectoryModel();
                       fdrMdl.setFdrSid(dirSid);
                       fdrMdl.setFcbSid(fcbSid);
                       fdrMdl.setFdrLevel(1);
                       fdrMdl.setFdrParentSid(dirParentSid);
                       fdrMdl.setFdrName(gsMsg.getMessage("fil.179"));
                       fdrMdl.setFdrBiko("");
                       fdrMdl.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
                       fdrMdl.setFdrKbn(GSConstFile.DIRECTORY_FOLDER);
                       fdrMdl.setFdrVerKbn(GSConstFile.VERSION_KBN_ALL);
                       fdrMdl.setFdrVersion(0);
                       fdrMdl.setFdrAuid(userSid);
                       fdrMdl.setFdrAdate(now);
                       fdrMdl.setFdrEuid(userSid);
                       fdrMdl.setFdrEdate(now);
                       fdrDao.insert(fdrMdl);
                       FilCommonBiz biz = new FilCommonBiz(reqMdl__, con__);

                       //更新通知設定
                       biz.insertCallConf(dirSid, dirParentSid);
                   } else {
                       dirSid = humeiFolder.getFdrSid();
                   }
               }

               //登録用データの作成
               for (FileErrlAdddataDspModel feaMdl : mdlList) {
                   int feaSid = feaMdl.getFeaSid();
                   FileDirectoryModel fdrMdl = new FileDirectoryModel();
                   int fileDirSid = (int) cntCon.getSaibanNumber(GSConstFile.PLUGIN_ID_FILE,
                           GSConstFile.SBNSID_SUB_DIRECTORY,
                           userSid);
                   FileErrlAdddataModel fileData = feaDao.select(feaMdl.getFeaSid());

                   if (feaSidList.contains(feaSid)) {
                       //保存先が存在しない仮登録ファイルの登録
                       fdrMdl.setFdrSid(fileDirSid);
                       fdrMdl.setFcbSid(fcbSid);
                       fdrMdl.setFdrLevel(2);
                       fdrMdl.setFdrParentSid(dirSid);
                       fdrMdl.setFdrName(feaMdl.getFdrName());
                       fdrMdl.setFdrBiko(fileData.getFdrBiko());
                       fdrMdl.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
                       fdrMdl.setFdrKbn(GSConstFile.DIRECTORY_FILE);
                       fdrMdl.setFdrVerKbn(GSConstFile.VERSION_KBN_ALL);
                       fdrMdl.setFdrVersion(1);
                       fdrMdl.setFdrAuid(userSid);
                       fdrMdl.setFdrAdate(now);
                       fdrMdl.setFdrEuid(userSid);
                       fdrMdl.setFdrEdate(now);
                       String dateStr = feaMdl.getTradeDateInput();
                       UDate date = UDate.getInstanceStr(dateStr);
                       date.setZeroHhMmSs();
                       fdrMdl.setFdrTradeDate(date);
                       fdrMdl.setFdrTradeTarget(feaMdl.getTradeTarget());
                       String moneyStr = feaMdl.getTradeMoeyInput();
                       if (!StringUtil.isNullZeroString(moneyStr)) {
                           BigDecimal money = new BigDecimal(
                                   feaMdl.getTradeMoeyInput().replaceAll(",", ""));
                           fdrMdl.setFdrTradeMoney(money);
                           fdrMdl.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);
                           fdrMdl.setEmtSid(gaikaMap.get(feaMdl.getTradeGaika()));
                       } else {
                           fdrMdl.setFdrTradeMoney(new BigDecimal(0));
                           fdrMdl.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
                           fdrMdl.setEmtSid(0);
                       }
                   } else {
                       //保存先が存在する仮登録ファイルの登録
                       fdrMdl.setFdrSid(fileDirSid);
                       fdrMdl.setFcbSid(fcbSid);
                       int parentSid = parentMap.get(feaMdl.getFeaSid());
                       fdrMdl.setFdrLevel((fdrDao.getNewDirectory(parentSid).getFdrLevel() + 1));
                       fdrMdl.setFdrParentSid(parentSid);
                       fdrMdl.setFdrName(feaMdl.getFdrName());
                       fdrMdl.setFdrBiko(fileData.getFdrBiko());
                       fdrMdl.setFdrJtkbn(GSConstFile.JTKBN_NORMAL);
                       fdrMdl.setFdrKbn(GSConstFile.DIRECTORY_FILE);
                       fdrMdl.setFdrVerKbn(GSConstFile.VERSION_KBN_ALL);
                       fdrMdl.setFdrVersion(1);
                       fdrMdl.setFdrAuid(userSid);
                       fdrMdl.setFdrAdate(now);
                       fdrMdl.setFdrEuid(userSid);
                       fdrMdl.setFdrEdate(now);
                       String dateStr = feaMdl.getTradeDateInput();
                       UDate date = UDate.getInstanceStr(dateStr);
                       date.setZeroHhMmSs();
                       fdrMdl.setFdrTradeDate(date);
                       fdrMdl.setFdrTradeTarget(feaMdl.getTradeTarget());
                       String moneyStr = feaMdl.getTradeMoeyInput();
                       if (!StringUtil.isNullZeroString(moneyStr)) {
                           BigDecimal money = new BigDecimal(
                                   feaMdl.getTradeMoeyInput().replaceAll(",", ""));
                           fdrMdl.setFdrTradeMoney(money);
                           fdrMdl.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);
                           fdrMdl.setEmtSid(gaikaMap.get(feaMdl.getTradeGaika()));
                       } else {
                           fdrMdl.setFdrTradeMoney(new BigDecimal(0));
                           fdrMdl.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
                           fdrMdl.setEmtSid(0);
                       }
                   }

                   insertList.add(fdrMdl);

                   //バイナリー情報を登録する
                   long binSid = fileData.getBinSid();
                   FileFileBinModel ffbMdl = new FileFileBinModel();
                   ffbMdl.setFdrSid(fileDirSid);
                   ffbMdl.setFdrVersion(1);
                   ffbMdl.setBinSid(binSid);
                   ffbMdl.setFflExt(fileData.getFflExt());
                   ffbMdl.setFflFileSize(fileData.getFflFileSize());
                   ffbMdl.setFflLockKbn(GSConstFile.LOCK_KBN_OFF);
                   ffbMdl.setFflLockUser(userSid);
                   ffbList.add(ffbMdl);

                   //ディレクトリ情報モデルを設定する。
                   FileFileRekiModel rekiModel = new FileFileRekiModel();
                   rekiModel.setFdrVersion(1);
                   rekiModel.setFfrKbn(GSConstFile.REKI_KBN_NEW);
                   rekiModel.setFfrEuid(userSid);
                   rekiModel.setFfrEdate(now);
                   rekiModel.setFfrUpCmt("");
                   rekiModel.setFfrParentSid(fdrMdl.getFdrParentSid());
                   rekiModel.setFdrSid(fileDirSid);
                   rekiModel.setFfrFname(fdrMdl.getFdrName());
                   rekiModel.setFdrTradeDate(fdrMdl.getFdrTradeDate());
                   rekiModel.setFdrTradeTarget(fdrMdl.getFdrTradeTarget());
                   String moneyStr = feaMdl.getTradeMoeyInput();
                   if (!StringUtil.isNullZeroString(moneyStr)) {
                       BigDecimal money = new BigDecimal(
                               feaMdl.getTradeMoeyInput().replaceAll(",", ""));
                       rekiModel.setFdrTradeMoney(money);
                       rekiModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_ON);
                       rekiModel.setEmtSid(gaikaMap.get(feaMdl.getTradeGaika()));
                   } else {
                       rekiModel.setFdrTradeMoney(new BigDecimal(0));
                       rekiModel.setFdrTradeMoneykbn(GSConstFile.MONEY_KBN_OFF);
                       rekiModel.setEmtSid(0);
                   }
                   ffrList.add(rekiModel);

                   //バイナリ情報を修正する
                   CmnBinfModel binfMdl = new CmnBinfModel();
                   binfMdl.setBinFileName(fdrMdl.getFdrName());
                   binfMdl.setBinUpuser(userSid);
                   binfMdl.setBinUpdate(now);
                   binfMdl.setBinSid(binSid);
                   binfDao.updateFileName(binfMdl);
               }
           }

           //データの登録
           fdrDao.insert(insertList);
           ffbDao.insert(ffbList);
           ffrDao.insert(ffrList);

           //仮登録ファイルの削除
           List<Integer> delSidList = mdlList.stream()
                   .map(mdl -> mdl.getFeaSid())
                   .collect(Collectors.toList());
           feaDao.delete(delSidList);

           //更新通知を設定する。
           for (FileDirectoryModel mdl : insertList) {
               filBiz.updateCall(mdl.getFdrSid(), cntCon, appRoot, pconfig, smailPluginUseFlg,
                       userSid);
           }

           return insertList.size();
       }


       /**
        * <br>[機  能] 仮登録ファイルの件数を取得します。
        * <br>[解  説]
        * <br>[備  考]
        *
        * @param paramMdl パラメータ情報
        * @return 仮登録ファイル件数
        * @throws SQLException SQL実行時例外
        */
       public long getErrlFileCount(
               Fil300ParamModel paramMdl) throws SQLException {

           if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
               FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
               List<Integer> feaSidList = Stream.of(paramMdl.getFil300BeforeInsertFile())
                       .map(str -> NullDefault.getInt(str, -1))
                       .collect(Collectors.toList());
               //ファイル登録画面から引き継いだ仮登録ファイルのうち、
               //ユーザが編集可能な情報のみを抽出し、件数を取得する
               List<FileErrlAdddataModel> feaList
                   = feaDao.getErrlAddDataList(feaSidList, reqMdl__.getSmodel());
               if (feaList.isEmpty()) {
                   return 0;
               }
               return feaList.size();
           } else {
               FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
               List<FileCabinetDspModel> cabinetList = cash__.getCabinetList();
               int size = 0;
               size += feaDao.getFileCountCabinet(
                       cabinetList.stream()
                           .filter(mdl -> mdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_NOT_USE)
                           .map(mdl -> mdl.getFcbSid())
                           .collect(Collectors.toList()),
                       reqMdl__.getSmodel()
                       );
               size += feaDao.getFileCountAutoSortCabinet(
                       cabinetList.stream()
                           .filter(mdl -> mdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE)
                           .map(mdl -> mdl.getFcbSid())
                           .collect(Collectors.toList()),
                       reqMdl__.getSmodel()
                       );
               return size;
           }
       }

       /**
        * <br>[機  能] キャビネットコンボを設定する。
        * <br>[解  説]
        * <br>[備  考]
        *
        * @param buMdl ユーザーモデル
        * @param paramMdl Fil040ParamModel
        * @param con コネクション
        * @throws SQLException SQL実行時例外
        */
       private void __setCabinetList(Connection con, Fil300ParamModel paramMdl, BaseUserModel buMdl)
               throws SQLException {

           List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
           List<FileCabinetDspModel> cabinetList = cash__.getCabinetList();

           cabinetList = cabinetList.stream()
                   .filter(mdl -> mdl.getNotEntryIconKbn() == GSConstFile.NOT_ENTRY_FILE_EXIST)
                   .collect(Collectors.toList());

           if (cabinetList != null && cabinetList.size() > 0) {
               for (FileCabinetDspModel model : cabinetList) {
                   labelList.add(new LabelValueBean(model.getFcbName(),
                           String.valueOf(model.getFcbSid())));
               }
           }

           if (paramMdl.getFil300InitFlg() == GSConstFile.INIT_FLG_NO) {
               if (paramMdl.getBackDsp().equals(GSConstFile.MOVE_TO_FIL040)) {
                   paramMdl.setFil300SelectCabinet(paramMdl.getFil010SelectCabinet());
               } else {
                   paramMdl.setFil300SelectCabinet(labelList.get(0).getValue());
               }
           }

           if (!cabinetList.isEmpty()) {
               List<Integer> cabSidList = cabinetList.stream()
                       .map(mdl -> mdl.getFcbSid())
                       .collect(Collectors.toList());
               int selectCabinet = NullDefault.getInt(paramMdl.getFil300SelectCabinet(), -1);
               if (!cabSidList.contains(selectCabinet)) {
                   paramMdl.setFil300SelectCabinet(labelList.get(0).getValue());
               }
           }

           paramMdl.setFil300CabinetList(labelList);
           paramMdl.setFil300InitFlg(GSConstFile.INIT_FLG_OK);
       }


      /**
       * <br>[機  能] インポート用サンプルエクセルを出力します。
       * <br>[解  説]
       * <br>[備  考]
       * @param paramMdl パラメータ
       * @param appRootPath アプリケーションルートパス
       * @param outPath パス
       * @param savePathFlg true:保存先が存在する，保存先が存在しない
       * @return true: サンプルファイル出力成功、false: 出力対象データが存在しない
       * @throws SQLException SQL実行例外
       * @throws IOException 入出力例外
       */
      public boolean createSampleXls(Fil300ParamModel paramMdl,
                                      String appRootPath,
                                      String outPath, boolean savePathFlg)
      throws SQLException, IOException {

          OutputStream oStream = null;
          Connection con = con__;

          List<FileErrlAdddataModel> dataList = new ArrayList<FileErrlAdddataModel>();
          FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con);
          if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
              String[] sids = paramMdl.getFil300BeforeInsertFile();
              List<Integer> sidList = new ArrayList<Integer>();
              for (String sid : sids) {
                  if (StringUtils.isNumber(sid)) {
                      sidList.add(Integer.parseInt(sid));
                  }
              }

              //編集不可の仮登録ファイルを除外
              dataList.addAll(
                      feaDao.getErrlAddDataList(sidList, reqMdl__.getSmodel()));
          } else {
              int limit = 0;
              int cabSid = NullDefault.getInt(paramMdl.getFil300SelectCabinet(), -1);
              FileErrlAdddataGetter addDataGetter;
              try {
                  addDataGetter = FileErrlAdddataGetter.builder(reqMdl__, con__)
                          .chainFcbSid(cabSid)
                          .chainFdrSid(paramMdl.getFil300SelectDir())
                          .chainNoSavePathFlg(
                                  savePathFlg == false
                                  )
                          .chainPage(1)
                          .chainLimitSize(limit)
                          .build();
                  dataList.addAll(addDataGetter.getDataList());
              } catch (FilErrlAdddataException e) {
              }
          }

          if (dataList.isEmpty()) {
              return false;
          }

          FileMoneyMasterDao moneyDao = new FileMoneyMasterDao(con);
          List<FileMoneyMasterModel> moneyMasterList = moneyDao.select();

          try {
              IOTools.isDirCheck(outPath, true);
              String bookName = SAMPLE_FILE_NAME;
              oStream = new FileOutputStream(outPath + bookName);
              createSample(appRootPath, oStream, bookName, dataList, moneyMasterList);
          } catch (Exception e) {
              log__.error("取込み用ファイルの出力に失敗しました。", e);
          } finally {
              oStream.flush();
              oStream.close();
          }
          log__.debug("取込み用ファイルの出力を終了します。");

          return true;
      }

      /**
       * <br>[機  能] 指定されたストリームにデータを設定する
       * <br>[解  説]
       * <br>[備  考]
       * @param appRootPath ディレクトリパス
       * @param oStream 出力先となるストリーム
       * @param fileName ファイル名
       * @param dataList 仮登録ファイルリスト
       * @param moneyMasterList 外貨リスト
       * @throws Exception 例外
       */
      public synchronized void createSample(
              String appRootPath,
              OutputStream oStream,
              String fileName,
              List<FileErrlAdddataModel> dataList,
              List<FileMoneyMasterModel> moneyMasterList) throws Exception {

          FileInputStream in = null;
          try {

              StringBuilder sb = new StringBuilder();
              sb.append(IOTools.replaceFileSep(IOTools.setEndPathChar(appRootPath)));
              sb.append(File.separator);
              sb.append(GSConstFile.PLUGIN_ID_FILE);
              sb.append(File.separator);
              sb.append("template");
              sb.append(File.separator);
              sb.append(fileName);

              //String path = sb.toString();
              String path = IOTools.replaceFileSep(appRootPath
                      + "/file/template/" + SAMPLE_FILE_NAME);
              log__.debug(">>>path :" + path);
              in = new FileInputStream(path);
              workbook__ = WorkbookFactory.create(in);
              sheetSample__ = workbook__.getSheetAt(0);
              sheetHelp__ = workbook__.getSheetAt(2);

              //外貨一覧の記載
              int rowNumber = 2;
              for (FileMoneyMasterModel mMdl : moneyMasterList) {
                  Cell clNo = __getCell(rowNumber, 1, SHEET_TYPE_HELP);
                  __setCellString(clNo, mMdl.getFmmName());
                  rowNumber++;
              }

              //ファイル情報の記載
              rowNumber = 1;
              for (FileErrlAdddataModel dataMdl : dataList) {
                  //No.
                  Cell clNo = __getCell(rowNumber, 0, SHEET_TYPE_SAMPLE);
                  __setCellString(clNo, String.valueOf(dataMdl.getFeaSid()));

                  //ファイル名(拡張子除く)
                  Cell clFileName = __getCell(rowNumber, 1, SHEET_TYPE_SAMPLE);
                  int dotIdx = dataMdl.getFdrName().lastIndexOf(".");
                  __setCellString(clFileName, dataMdl.getFdrName().substring(0, dotIdx));
                  rowNumber++;
              }

              workbook__.write(oStream);

          } catch (IOException e) {
              throw new Exception("WorkReport Set Error", e);
          } finally {
              try {
                  in.close();
              } catch (Exception e) { }
          }

          log__.debug("end");
      }

      /**
       * <p>サンプルシートの行を返す
       * @param index 行数
       * @return 行
       */
      private Row __getSampleRow(int index) {
          Row row = sheetSample__.getRow(index);
          if (row == null) {
              row = sheetSample__.createRow(index);
          }
          return row;
      }

      /**
       * <p>ヘルプシートの行を返す
       * @param index 行数
       * @return 行
       */
      private Row __getHelpRow(int index) {
          Row row = sheetHelp__.getRow(index);
          if (row == null) {
              row = sheetHelp__.createRow(index);
          }
          return row;
      }

      /**
       * <p>セルを返す
       * @param rowIdx 行数
       * @param colIdx 列数
       * @param rowType シート種別
       * @return 行
       */
      private Cell __getCell(int rowIdx, int colIdx, int rowType) {
          if (rowType == SHEET_TYPE_SAMPLE) {
              Row row = __getSampleRow(rowIdx);
              Cell cell = row.createCell((short) colIdx);
              return cell;
          } else {
              Row row = __getHelpRow(rowIdx);
              Cell cell = row.createCell((short) colIdx);
              return cell;
          }
      }

      /**
       * <p>セルにStringをセットする
       * @param cell 対象のセル
       * @param str セットする文字列
       * @return セル
       */
      private Cell __setCellString(Cell cell, String str) {
          cell.setCellValue(str);
          return cell;
      }


    /**
     * <br>[機  能] 指定した仮登録ファイルが編集可能かを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param feaSid 仮登録ID
     * @param paramMdl パラメータ情報
     * @return true: 編集可能, false: 編集不可
     * @throws SQLException 実行例外
     */
    public boolean isErrlAuth(int feaSid, Fil300ParamModel paramMdl) throws SQLException {

        //指定した仮登録ファイルを編集可能かを確認する
        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);
        boolean result = filCmnBiz.checkEditErrlData(feaSid);

        //フォルダ登録画面から遷移した場合、登録対象として指定された仮登録ファイルに含まれるかをチェックする
        if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
            String[] beforeInsert = paramMdl.getFil300BeforeInsertFile();
            if (beforeInsert == null || beforeInsert.length == 0) {
                result = false;
            } else {
                Set<Integer> beforeInsertSet
                    = Stream.of(beforeInsert)
                    .filter(id -> NullDefault.getInt(id, -1) > 0)
                    .map(id -> Integer.parseInt(id))
                    .collect(Collectors.toSet());

                result = beforeInsertSet.contains(feaSid);
            }
        }

        return result;
    }
}
