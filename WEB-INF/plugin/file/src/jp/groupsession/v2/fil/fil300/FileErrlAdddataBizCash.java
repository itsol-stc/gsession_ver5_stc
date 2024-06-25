package jp.groupsession.v2.fil.fil300;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.fil010.Fil010Biz;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;

public class FileErrlAdddataBizCash {
    /** キャビネット一覧*/
    private List<FileCabinetDspModel> cabinetList__;

    /** 080で登録した仮登録ファイルリスト*/
    private List<FileErrlAdddataModel> fil080AddFileList__;

    /** リクエストモデル*/
    private RequestModel reqMdl__;
    /** コネクション*/
    private Connection con__;

    /**
     * コンストラクタ
     */
    private FileErrlAdddataBizCash() {
        super();
    }

    /**
     *
     * <br>[機  能] インスタンス取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con
     * @param reqMdl
     * @return インスタンス
     */
    public static FileErrlAdddataBizCash getInstance(RequestModel reqMdl, Connection con) {
        FileErrlAdddataBizCash ret = RequestLocal.get(
                FileErrlAdddataBizCash.class,
                FileErrlAdddataBizCash.class);
        if (ret == null) {
            ret = new FileErrlAdddataBizCash();
            RequestLocal.put(FileErrlAdddataBizCash.class, ret);
        }
        ret.reqMdl__ = reqMdl;
        ret.con__ = con;
        return ret;
    }

    /**
     * <p>cabinetList を取得します。
     * @return cabinetList
     * @throws SQLException
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataBizCash#cabinetList__
     */
    public List<FileCabinetDspModel> getCabinetList() throws SQLException {
        if (cabinetList__ == null) {
            //編集可能キャビネットから仮登録ファイルのあるものを抽出
            Fil010Biz fil010Biz = new Fil010Biz(reqMdl__);
            cabinetList__ = fil010Biz.getCabinetList(
                    reqMdl__.getSmodel(), con__, false, false, GSConstFile.CABINET_KBN_ERRL)
                    .stream()
                    .filter(mdl -> mdl.getNotEntryIconKbn() == GSConstFile.NOT_ENTRY_FILE_EXIST)
                    .collect(Collectors.toList());
        }
        return cabinetList__;
    }

    /**
     * <p>fil080AddFileList を取得します。
     * @param fil300BeforeInsertFile 仮登録ファイルSID
     * @return fil080AddFileList
     * @throws SQLException
     * @see jp.groupsession.v2.fil.fil300.FileErrlAdddataBizCash#fil080AddFileList__
     */
    public List<FileErrlAdddataModel> getFil080AddFileList(
            String[] fil300BeforeInsertFile) throws SQLException {
        if (fil080AddFileList__ == null) {
            //080で登録した仮登録ファイルのあるものを抽出
            FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
            List<FileErrlAdddataModel> errlList =
                    feaDao.getErrlAddDataList(
                    Optional.ofNullable(
                            fil300BeforeInsertFile
                            )
                    .map(arr -> Stream.of(arr))
                    .orElse(Stream.empty())
                    .map(str -> NullDefault.getInt(str, 0))
                    .collect(Collectors.toSet()),
                    reqMdl__.getSmodel());
            fil080AddFileList__ = errlList;
        }

        return fil080AddFileList__;
    }

}
