package jp.groupsession.v2.fil.fil300;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;

public class FileErrlAdddataGetter {
    /** リクエストモデル*/
    private final RequestModel reqMdl__;
    /** コネクション*/
    private final Connection con__;

    /** 1ページのサイズ上限 0以下で無制限*/
    private final int limitSize__;

    /** 表示ページ*/
    private int page__;

    /** ページ数*/
    private int maxPage__;

    /** 最大取得数*/
    private long maxCount__;

    /** 保存先が存在しないフォルダの選択フラグ*/
    private final boolean noSavePathFlg__;

    /** 選択キャビネットSID*/
    private final int fcbSid__;

    /** 選択ディレクトリSID*/
    private final int fdrSid__;

    /** 取得結果 選択ディレクトリ下部の仮登録ファイル一覧*/
    private List<FileErrlAdddataModel> dataList__;

    /** 取得結果 選択フォルダタイプ*/
    private AdddataDirType dirType__;
    /** 選択フォルダタイプ*/
    public enum AdddataDirType {
        /** 自動振り分けキャビネット内*/
        AutoSortedCabinet,
        /** 通常ディレクトリ*/
        Directory,
        /** 保存先が存在しない*/
        NoSavePath
    }

    /**
     *
     * コンストラクタ
     * @param builder
     * @throws FilErrlAdddataException
     * @throws SQLException
     */
    private FileErrlAdddataGetter(Builder builder) throws SQLException, FilErrlAdddataException {
        reqMdl__ = builder.reqMdl__;
        con__ = builder.con__;
        limitSize__ = builder.limitSize__;
        page__ = builder.page__;
        noSavePathFlg__ = builder.noSavePathFlg__;
        fcbSid__ = builder.fcbSid__;
        fdrSid__ = builder.fdrSid__;

        getDataList();
    }
    public static Builder builder(RequestModel reqMdl, Connection con) {
        return new Builder(reqMdl, con);
    }
    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class Builder {
        /** リクエストモデル*/
        private final RequestModel reqMdl__;
        /** コネクション*/
        private final Connection con__;

        /** 1ページのサイズ上限 0以下で無制限*/
        private int limitSize__ = 0;

        /** 表示ページ*/
        private int page__;

        /** 保存先が存在しないフォルダの選択フラグ*/
        private boolean noSavePathFlg__;

        /** 選択キャビネットSID*/
        private int fcbSid__;

        /** 選択ディレクトリSID*/
        private int fdrSid__;
        /**
         *
         * コンストラクタ
         * @param reqMdl
         * @param con
         */
        private Builder(RequestModel reqMdl, Connection con) {
            reqMdl__ = reqMdl;
            con__ = con;
        }
        /**
         * <p>limitSize をセットします。
         * @param limitSize limitSize
         * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#limitSize__
         * @return this
         */
        public Builder chainLimitSize(int limitSize) {
            limitSize__ = limitSize;
            return this;
        }


        /**
         * <p>page をセットします。
         * @param page page
         * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#page__
         * @return this
         */
        public Builder chainPage(int page) {
            page__ = page;
            return this;
        }


        /**
         * <p>savePathFlg をセットします。
         * @param noSavePathFlg savePathFlg
         * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#savePathFlg__
         * @return this
         */
        public Builder chainNoSavePathFlg(boolean noSavePathFlg) {
            noSavePathFlg__ = noSavePathFlg;
            return this;
        }


        /**
         * <p>fcbSid をセットします。
         * @param fcbSid fcbSid
         * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#fcbSid__
         * @return this
         */
        public Builder chainFcbSid(int fcbSid) {
            fcbSid__ = fcbSid;
            return this;
        }


        /**
         * <p>fdrSid をセットします。
         * @param fdrSid fdrSid
         * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#fdrSid__
         * @return this
         */
        public Builder chainFdrSid(int fdrSid) {
            fdrSid__ = fdrSid;
            return this;
        }

        public FileErrlAdddataGetter build() throws SQLException, FilErrlAdddataException {
            return new FileErrlAdddataGetter(this);
        }

    }


    /**
     *
     * <br>[機  能] 選択ディレクトリ下部の仮登録ファイル一覧を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 選択ディレクトリ下部の仮登録ファイル一覧
     * @throws SQLException
     * @throws FilErrlAdddataException
     */
    public List<FileErrlAdddataModel> getDataList() throws SQLException, FilErrlAdddataException {
        if (dataList__ != null) {
            return dataList__;
        }
        dataList__ = new ArrayList<FileErrlAdddataModel>();

        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
        FileDirectoryDao fdrDao = new FileDirectoryDao(con__);
        FileCabinetDao fcbDao = new FileCabinetDao(con__);

        if (noSavePathFlg__ == false) {
            int parentSid = fdrSid__;

            FileDirectoryModel mdl = fdrDao.getNewDirectory(parentSid);
            if (mdl == null) {
                throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_SELDIR);
            }
            int cabSid = mdl.getFcbSid();
            FileCabinetModel fcbMdl = fcbDao.select(cabSid);
            if (fcbMdl == null) {
                throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_SELDIR);
            }

            //ページングコンボの設定
            List<FileErrlAdddataModel> errlMdlList;
            if (fcbMdl.getFcbSortFolder() == GSConstFile.SORT_FOLDER_USE) {
                dirType__ = AdddataDirType.AutoSortedCabinet;


                List<Integer> fcbSidList = new ArrayList<Integer>();
                fcbSidList.add(cabSid);
                maxCount__ = (int) feaDao.getFileCountAutoSortCabinet(
                        fcbSidList,
                        reqMdl__.getSmodel());
                page__ = PageUtil.getPageAdjustedOverflow(page__,
                        maxCount__,
                        limitSize__);

                errlMdlList = feaDao.getFileAutoSortCabinet(
                        fcbSidList,
                        reqMdl__.getSmodel(),
                        page__,
                        limitSize__);
            } else {
                dirType__ = AdddataDirType.Directory;
                FileDirectoryModel parent = fdrDao.getNewDirectory(parentSid);
                if (parent == null) {
                    throw new FilErrlAdddataException(FilErrlAdddataException.INFO_NOFILE_SELDIR);
                }
                //ディレクトリ情報からFDR_LEVELを見て、0ならキャビネットSIDを指定してディレクトリ取得 ページ忘れず
                if (parent.getFdrLevel() == GSConstFile.DIRECTORY_LEVEL_0) {
                    List<Integer> fcbSidList = new ArrayList<Integer>();
                    fcbSidList.add(cabSid);
                    maxCount__ = (int) feaDao.getFileCountCabinet(
                            fcbSidList,
                            reqMdl__.getSmodel());
                    page__ = PageUtil.getPageAdjustedOverflow(page__,
                            maxCount__,
                            limitSize__);

                    errlMdlList = feaDao.getFileCabinet(
                            fcbSidList,
                            reqMdl__.getSmodel(),
                            page__,
                            limitSize__);

                } else {
                    List<Integer> dirList = fdrDao.getLowerDirSid(parentSid);

                    //仮登録モデルリスト
                    maxCount__ = (int) feaDao.getFileCountFolder(
                            dirList,
                            reqMdl__.getSmodel()
                            );
                    page__ = PageUtil.getPageAdjustedOverflow(page__,
                            maxCount__,
                            limitSize__);

                    errlMdlList = feaDao.getFileFolder(
                            dirList,
                            reqMdl__.getSmodel(),
                            page__,
                            limitSize__);

                }


            }
            dataList__.addAll(errlMdlList);

        } else {
            dirType__ = AdddataDirType.NoSavePath;

            int cabSid = fcbSid__;
            maxCount__ = feaDao.getFileCountNoSavePath(cabSid, reqMdl__.getSmodel());

            page__ = PageUtil.getPageAdjustedOverflow(page__, maxCount__, limitSize__);
            dataList__.addAll(
                    feaDao.getFileNoSavePath(
                        cabSid,
                        reqMdl__.getSmodel(),
                        page__,
                        limitSize__
                        )
                    );

        }
        return dataList__;

    }
    /**
     * <p>page を取得します。
     * @return page
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#page__
     */
    public int getPage() {
        return page__;
    }


    /**
     * <p>maxPage を取得します。
     * @return maxPage
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#maxPage__
     */
    public int getMaxPage() {
        return maxPage__;
    }
    /**
     * <p>maxCount を取得します。
     * @return maxCount
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#maxCount__
     */
    public long getMaxCount() {
        return maxCount__;
    }
    /**
     * <p>dirType を取得します。
     * @return dirType
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataGetter#dirType__
     */
    public AdddataDirType getDirType() {
        return dirType__;
    }
}
