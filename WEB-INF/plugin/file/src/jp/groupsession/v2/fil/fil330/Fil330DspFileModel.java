package jp.groupsession.v2.fil.fil330;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;
/**
 *
 * <br>[機  能] 仮ファイル削除画面用 仮ファイル表示モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil330DspFileModel {
    /** 仮ファイル本体*/
    private final FileErrlAdddataModel base__;
    /** 親フォルダモデル*/
    private final FileDirectoryModel parentModel__;
    /** ファイルパス*/
    private final String path__;
    /** 更新者*/
    private final CmnUserModel updateUser__;
    /** ファイルサイズ（表示）*/
    private final String fileSize__;
    /** 更新日時（表示）*/
    private final String edateString__;
    /** ファイル拡張子（表示）*/
    private final String fileExt__;


    /**
     * コンストラクタ
     * @param base
     * @param parentModel
     * @param path
     * @param updateUser
     */
    public Fil330DspFileModel(FileErrlAdddataModel base,
            FileDirectoryModel parentModel,
            String path,
            CmnUserModel updateUser) {
        super();
        base__ = base;
        parentModel__ = parentModel;
        path__ = path;
        updateUser__ = updateUser;

        BigDecimal bdSize = BigDecimal.valueOf(base.getFflFileSize());
        //B→KBへ変換
        fileSize__ = StringUtil.toCommaFromBigDecimal(
                bdSize.divide(GSConstFile.KB_TO_MB, 1, RoundingMode.HALF_UP)) + " KB";
        UDate edate = base.getFdrAdate();
        edateString__ = UDateUtil.getSlashYYMD(edate) + " " + UDateUtil.getSeparateHMS(edate);

        fileExt__ = Optional.ofNullable(base.getFflExt())
                        .filter(ext -> ext.startsWith("."))
                        .filter(ext -> ext.length() >= 1)
                        .map(ext -> ext.substring(1))
                        .orElse("");


    }

    /**
     * <p>base を取得します。
     * @return base
     * @see jp.groupsession.v2.fil.fil330.Fil330DspFileModel#base__
     */
    public FileErrlAdddataModel getBase() {
        return base__;
    }

    /**
     * <p>parentModel を取得します。
     * @return parentModel
     * @see jp.groupsession.v2.fil.fil330.Fil330DspFileModel#parentModel__
     */
    public FileDirectoryModel getParentModel() {
        return parentModel__;
    }
    /**
     * <p>path を取得します。
     * @return path
     * @see jp.groupsession.v2.fil.fil330.Fil330DspFileModel#path__
     */
    public String getPath() {
        return path__;
    }

    /**
     * <p>updateUser を取得します。
     * @return updateUser
     * @see jp.groupsession.v2.fil.fil330.Fil330DspFileModel#updateUser__
     */
    public CmnUserModel getUpdateUser() {
        return updateUser__;
    }

    /**
     * <p>fileSize を取得します。
     * @return fileSize
     * @see jp.groupsession.v2.fil.fil330.Fil330DspFileModel#fileSize__
     */
    public String getFileSize() {
        return fileSize__;
    }

    /**
     * <p>edateString を取得します。
     * @return edateString
     * @see jp.groupsession.v2.fil.fil330.Fil330DspFileModel#edateString__
     */
    public String getEdateString() {
        return edateString__;
    }

    /**
     * <p>fileExt を取得します。
     * @return fileExt
     * @see jp.groupsession.v2.fil.fil330.Fil330DspFileModel#fileExt__
     */
    public String getFileExt() {
        return fileExt__;
    }


}
