package jp.groupsession.v2.fil.fil330;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;
import jp.groupsession.v2.fil.fil300.Fil300Biz;
import jp.groupsession.v2.fil.fil300.FilErrlAdddataException;
import jp.groupsession.v2.fil.fil300.FileErrlAdddataBizCash;
import jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;

/**
 *
 * <br>[機  能] 仮ファイル削除画面 ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil330Biz {
    /** リクエストモデル*/
    private final RequestModel reqMdl__;
    /** コネクション*/
    private final Connection con__;
    /** キャッシュデータ*/
    private final FileErrlAdddataBizCash cash__;


    /**
     * コンストラクタ
     * @param reqMdl
     * @param con
     */
    public Fil330Biz(RequestModel reqMdl, Connection con) {
        super();
        reqMdl__ = reqMdl;
        con__ = con;
        cash__ = FileErrlAdddataBizCash.getInstance(reqMdl, con);
    }

    /**
     *
     * <br>[機  能] 仮ファイル残数の取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @return 仮ファイル残数
     */
    public long getErrlFileCount(
            Fil330ParamModel paramMdl
            ) throws SQLException, FilErrlAdddataException {
        if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
            FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
            List<Integer> feaSidList = Stream.of(paramMdl.getFil300BeforeInsertFile())
                    .map(str -> NullDefault.getInt(str, -1))
                    .collect(Collectors.toList());
            List<FileErrlAdddataModel> feaList =
                    feaDao.getErrlAddDataList(feaSidList, reqMdl__.getSmodel());
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
     *
     * <br>[機  能] 画面モデル初期化処理を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @throws SQLException
     * @throws FilErrlAdddataException
     */
    public void setInitDsp(
            Fil330ParamModel paramMdl
            ) throws SQLException, FilErrlAdddataException {
        //取引情報登録からの遷移時の初回設定
        if (paramMdl.getFil330SelectCabinet() == null
                && paramMdl.getFil330SelectDsp() == null
                && paramMdl.getFil300SelectCabinet() != null) {
            FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
            if (paramMdl.getFil300SelectDir() > 0) {
                paramMdl.setFil330SelectDsp(String.valueOf(paramMdl.getFil300SelectDir()));
                FileDirectoryModel fdrMdl = fdrDao.getNewDirectory(paramMdl.getFil300SelectDir());
                if (fdrMdl != null) {
                    paramMdl.setFil330SelectCabinet(String.valueOf(fdrMdl.getFcbSid()));
                }

            } else {
                //キャビネットSIDでルートディレクトリSIDを取ってfil300SelectDirにセットする
                FileDirectoryModel fdrMdl = fdrDao.getRootDirectory(
                        NullDefault.getInt(paramMdl.getFil300SelectCabinet(), -1));
                if (fdrMdl != null) {
                    paramMdl.setFil330SelectCabinet(String.valueOf(fdrMdl.getFcbSid()));
                    paramMdl.setFil300SelectDir(fdrMdl.getFdrSid());
                    paramMdl.setFil330SelectDsp(String.valueOf(paramMdl.getFil300SelectDir()));
                }

            }
        }


        //フォルダツリー設定
        Fil300Biz fil300Biz = new Fil300Biz(reqMdl__, con__);

        //fil080からの遷移
        if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
            List<FileErrlAdddataModel> errlList =
                    cash__.getFil080AddFileList(
                            paramMdl.getFil300BeforeInsertFile());

            if (errlList.isEmpty()) {
                throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_ALL);
            }


            if (fil300Biz.setTreeMultiFromFil080(paramMdl) == false) {
                throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_ALL);
            }
        //その他
        } else {

            if (fil300Biz.setTreeMulti(paramMdl) == false) {
                throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_ALL);
            }
        }

        //ツリーを全て開く
        __setTreeOpen(paramMdl);

        //ディレクトリ一覧設定
        __setDirectoryDsp(paramMdl);



    }
    private void __setTreeOpen(Fil330ParamModel paramMdl) {
        Set<String> treeNames =
            __createSelDirList(paramMdl)
                .stream()
                .map(tree -> tree.split(paramMdl.getSepKey())[0])
                .collect(Collectors.toSet());
        final JSONObject jobj = new JSONObject();
        for (String name: treeNames) {
            jobj.put(name, "block");
        }
        paramMdl.setFil330Tree(jobj.toString());

        //ツリーに初期選択状態を設定
        for (String[] tree : Stream.of(
                Optional.ofNullable(paramMdl.getTreeFormLv1()),
                Optional.ofNullable(paramMdl.getTreeFormLv2()),
                Optional.ofNullable(paramMdl.getTreeFormLv3()),
                Optional.ofNullable(paramMdl.getTreeFormLv4()),
                Optional.ofNullable(paramMdl.getTreeFormLv5()),
                Optional.ofNullable(paramMdl.getTreeFormLv6()),
                Optional.ofNullable(paramMdl.getTreeFormLv7()),
                Optional.ofNullable(paramMdl.getTreeFormLv8()),
                Optional.ofNullable(paramMdl.getTreeFormLv9()),
                Optional.ofNullable(paramMdl.getTreeFormLv10()),
                Optional.ofNullable(paramMdl.getTreeFormLv11())
                )
                .map(opt -> opt.orElse(new String[] {}))
                .collect(Collectors.toList())
                ) {

            for (int i = 0; i < tree.length; i++) {
                String row = tree[i];
                String[] sp = row.split(paramMdl.getSepKey());
                if (sp.length < 6) {
                    sp = Stream.concat(
                            Stream.of(sp),
                            Stream.of(
                                    paramMdl.getFil330SelectCabinet(),
                                    "1"
                                    )
                            )
                            .toArray(String[]::new);
                    row = Stream.of(sp)
                            .collect(Collectors.joining(paramMdl.getSepKey()));
                }
                int sel = 0;
                if (Objects.equals(sp[0], paramMdl.getFil330SelectDsp())
                        && Objects.equals(sp[5], paramMdl.getFil330SelectCabinet())) {
                    sel = 1;
                }
                tree[i] = String.format("%s%s%d",
                        row,
                        paramMdl.getSepKey(),
                        sel);

            }

        }


    }

    /**
     *
     * <br>[機  能] 画面モデルに仮ファイル一覧を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @throws SQLException
     * @throws FilErrlAdddataException
     */
    private void __setDirectoryDsp(
            Fil330ParamModel paramMdl
            ) throws SQLException, FilErrlAdddataException {
        List<FileErrlAdddataModel> dataList = new ArrayList<FileErrlAdddataModel>();
        //表示対象ファイル一覧の取得
        if (paramMdl.getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {

            dataList.addAll(cash__.getFil080AddFileList(
                    paramMdl.getFil300BeforeInsertFile()
                    ));
            //仮登録ファイルが存在しない
            if (dataList.size() == 0) {
                throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_ALL);
            }

        } else {
            if (paramMdl.getFil330SelectDsp() == null) {
                throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_SELDIR);
            }

            int page = paramMdl.getFil330FileListPageNo();
            int limit = GSConstFile.MAX_DSP_ERRL_ADDDATA * 2;
            FileErrlAdddataGetter addDataGetter =
                    FileErrlAdddataGetter.builder(reqMdl__, con__)
                        .chainFcbSid(NullDefault.getInt(paramMdl.getFil330SelectCabinet(), -1))
                        .chainFdrSid(NullDefault.getInt(paramMdl.getFil330SelectDsp(), -1))
                        .chainNoSavePathFlg(
                                Objects.equals(
                                        paramMdl.getFil330SelectDsp(),
                                        String.valueOf(GSConstFile.TREE_NO_SAVEPATH_DIR)
                                        )
                                )
                        .chainPage(page)
                        .chainLimitSize(limit)
                        .build();
            dataList.addAll(addDataGetter.getDataList());

            long maxCount = addDataGetter.getMaxCount();
            if (maxCount <= 0) {
                throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_SELDIR);
            }
            if (maxCount > limit) {
                paramMdl.setFil330FileListPageLabel(
                        PageUtil.createPageOptions(maxCount, limit)
                        );
            }

            paramMdl.setFil330FileListPageNo(page);



        }
        int cabSid =
                dataList.stream()
                    .findAny()
                    .map(mdl -> mdl.getFcbSid())
                    .orElse(-1);

        paramMdl.setFil330FileList(
                createFileDspList(dataList,
                        cabSid
                        )
                );
    }
    /**
     *
     * <br>[機  能] 階層LV毎のディレクトリ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @return ディレクトリ一覧の取得
     */
    private List<String> __createSelDirList(Fil330ParamModel paramMdl) {
        return Stream.of(
                Optional.ofNullable(paramMdl.getTreeFormLv0()),
                Optional.ofNullable(paramMdl.getTreeFormLv1()),
                Optional.ofNullable(paramMdl.getTreeFormLv2()),
                Optional.ofNullable(paramMdl.getTreeFormLv3()),
                Optional.ofNullable(paramMdl.getTreeFormLv4()),
                Optional.ofNullable(paramMdl.getTreeFormLv5()),
                Optional.ofNullable(paramMdl.getTreeFormLv6()),
                Optional.ofNullable(paramMdl.getTreeFormLv7()),
                Optional.ofNullable(paramMdl.getTreeFormLv8()),
                Optional.ofNullable(paramMdl.getTreeFormLv9()),
                Optional.ofNullable(paramMdl.getTreeFormLv10()),
                Optional.ofNullable(paramMdl.getTreeFormLv11())
                )
            .flatMap(opt ->
                opt.map(Arrays::stream)
                    .orElse(Stream.empty())
                            )
            .collect(Collectors.toList());

    }

    /**
     *
     * <br>[機  能] ファイルリストの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param dataList
     * @param cabSid
     * @return 表示用ファイルリスト
     * @throws SQLException
     */
    public List<Fil330DspFileModel> createFileDspList(
            List<FileErrlAdddataModel> dataList,
            int cabSid) throws SQLException {

        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        //キャビネットルートディレクトリMap取得
        Map<Integer, FileDirectoryModel> rootDirMap =
                dirDao.getRootDirectory(
                        dataList.stream()
                            .map(errl -> errl.getFcbSid())
                            .collect(Collectors.toSet())
                        )
                .stream()
                .collect(Collectors.toMap(
                        mdl -> mdl.getFcbSid(),
                        mdl -> mdl));



        //親ディレクトリが削除済みはキャビネット直下として扱う
        Map<Integer, FileDirectoryModel> firstParentsMap =
                dirDao.getNewDirectoryMap(
                        dataList.stream()
                            .map(dir -> dir.getFdrParentSid())
                            .map(sid -> String.valueOf(sid))
                            .collect(Collectors.toList())
                        );
        dataList =
            dataList.stream()
                .map(errl -> {
                    if (firstParentsMap.containsKey(errl.getFdrParentSid()) == false) {
                        FileDirectoryModel rootDir = rootDirMap.get(errl.getFcbSid());
                        if (rootDir == null) {
                            return null;
                        }
                        errl.setFdrParentSid(rootDir.getFdrSid());
                    }
                    return errl;
                })
                .collect(Collectors.toList());


        //親ディレクトリMapの生成
        Map<Integer, FileDirectoryModel> parentsMap =
                __createParentsMap(
                        dataList
                    );


        //ファイルパスMapの生成
        Map<Integer, String> pathMap =
                __createPathMap(
                        parentsMap,
                        cash__.getCabinetList().stream()
                        .filter(cab ->
                            Objects.equals(
                                    cab.getFcbSid(),
                                    cabSid
                                    )
                                )
                        .findAny()
                        .orElse(null));

        //更新者Mapの生成
        UserSearchDao usrDao = new UserSearchDao(con__);
        Map<Integer, CmnUserModel> usrMap =
                usrDao.getUsersDataList(
                        dataList.stream()
                            .map(dir -> dir.getFdrAuid())
                            .collect(Collectors.toSet())
                        ).stream()
                        .collect(Collectors.toMap(
                                usr -> usr.getUsrSid(),
                                usr -> usr));


       return dataList.stream()
                    .map(mdl ->
                        new Fil330DspFileModel(
                                mdl,
                                parentsMap.get(mdl.getFdrParentSid()),
                                pathMap.get(mdl.getFdrParentSid()),
                                usrMap.get(mdl.getFdrAuid())
                                )
                            )
                    .collect(Collectors.toList());
    }

    /**
     *
     * <br>[機  能] 全ての親ディレクトリを含むMapを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param dataList
     * @return 全ての親ディレクトリを含むMap
     * @throws SQLException
     */
    private Map<Integer, FileDirectoryModel> __createParentsMap(
            List<FileErrlAdddataModel> dataList) throws SQLException {
        FileDirectoryDao dirDao = new FileDirectoryDao(con__);
        Map<Integer, FileDirectoryModel> ret = new HashMap<>();

        Set<Integer> check = dataList.stream()
                .map(dir -> dir.getFdrParentSid())
                .collect(Collectors.toSet());



        //FileDirectoryの構造上無限ループが発生しうるため 10回までの回数制限
        for (int i = 10; i >= 0; i--) {
            Map<Integer, FileDirectoryModel> parentsMap =
                    dirDao.getNewDirectoryMap(
                            check.stream()
                                .map(sid -> String.valueOf(sid))
                                .collect(Collectors.toList())
                            );

            ret.putAll(parentsMap);

            check = parentsMap.values().stream()
                        .filter(mdl ->
                            Objects.equals(mdl.getFdrJtkbn(), GSConstFile.JTKBN_DELETE)
                                == false)
                        .filter(mdl ->
                            Objects.equals(mdl.getFdrParentSid(), 0)
                            == false)
                        .filter(mdl ->
                            ret.containsKey(mdl.getFdrParentSid()) == false)
                        .map(mdl -> mdl.getFdrParentSid())
                        .collect(Collectors.toSet());
            //取得する親ディレクトリがなくなった時点で終了
            if (check.size() <= 0) {
                break;
            }
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] ファイルパスのMapを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param parentsMap 全ての親ディレクトリを含むMap
     * @param fileCabinetDspModel
     * @return 作成対象ディレクトリSIDに対するファイルパスのMap
     */
    private Map<Integer, String> __createPathMap(
            Map<Integer, FileDirectoryModel> parentsMap,
            FileCabinetDspModel fileCabinetDspModel) {
        Map<Integer, String> ret = new HashMap<>();

        //階層が浅い順にパスを取得する
        List<FileDirectoryModel> check = parentsMap.values()
                    .stream()
                    .sorted(Comparator.comparing(FileDirectoryModel::getFdrLevel))
                    .collect(Collectors.toList());

        Map<Integer, String> noPathFile = new HashMap<>();
        Map<Integer, String> autoFuriwakeFile = new HashMap<>();

        String cabName = Optional.ofNullable(fileCabinetDspModel)
                            .map(cab -> cab.getFcbName())
                            .orElse("");

        for (FileDirectoryModel mdl : check) {

            StringBuilder sb = new StringBuilder();
            //FileDirectoryの構造上無限ループが発生しうるため 10回までの回数制限
            FileDirectoryModel parent = mdl;
            Set<Integer> rootSids = new HashSet<Integer>();
            if (Optional.ofNullable(fileCabinetDspModel)
                    .filter(cab -> cab.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE)
                    .isPresent()) {
                //自動振り分けキャビネットの場合パスは空
                autoFuriwakeFile.put(mdl.getFdrSid(), "");
                continue;
            }

            for (int i = 10; i >= 0; i--) {
                //親ディレクトリが削除済みは「保存先が存在しないファイル」
                if (Objects.equals(
                        Optional.ofNullable(parent)
                            .map(pd -> pd.getFdrJtkbn())
                            .orElse(GSConstFile.JTKBN_DELETE),
                        GSConstFile.JTKBN_DELETE)) {
                    noPathFile.put(mdl.getFdrSid(), "");
                    break;
                }
                //ディレクトリがループしてる
                if (rootSids.contains(parent.getFdrParentSid())) {
                    noPathFile.put(mdl.getFdrSid(), "");
                    break;
                }
                sb.insert(0, "/");
                sb.insert(0, parent.getFdrName());

                rootSids.add(mdl.getFdrSid());

                parent = parentsMap.get(parent.getFdrParentSid());
                //最上部(キャビネット)まで取得完了
                if (parent == null) {
                    ret.put(mdl.getFdrSid(), sb.toString());
                    break;
                }
                //すでに作成ずみのパスを再利用
                if (ret.containsKey(parent.getFdrSid())) {
                    sb.insert(0, ret.get(parent.getFdrSid()));
                    ret.put(mdl.getFdrSid(), sb.toString());
                    break;
                }
            }

        }
        final String finalCabName = cabName + "/";
        ret.putAll(noPathFile.keySet().stream()
                    .collect(Collectors.toMap(
                            sid -> sid,
                            sid -> finalCabName))
                    );
        ret.putAll(autoFuriwakeFile.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> finalCabName + entry.getValue()))
                );

        return ret;
    }
    /**
     *
     * <br>[機  能] キャビネット一覧の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return キャビネット一覧
     * @throws SQLException
     */
    public List<FileCabinetDspModel> getCabinetList() throws SQLException {
        return cash__.getCabinetList();
    }


}
