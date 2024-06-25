package jp.groupsession.v2.fil.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <br>[機  能] ツリー階層Model
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilChildTreeModel implements Serializable {

    /** 階層(ディレクトリ) */
    private ArrayList<FileDirectoryModel> listOfDir__;
    /** 階層(ファイル) */
    private ArrayList<FileDirectoryModel> listOfFile__;

    /**
     * <p>listOfDir を取得します。
     * @return listOfDir
     */
    public ArrayList<FileDirectoryModel> getListOfDir() {
        return listOfDir__;
    }
    /**
     * <p>listOfDir をセットします。
     * @param listOfDir listOfDir
     */
    public void setListOfDir(ArrayList<FileDirectoryModel> listOfDir) {
        listOfDir__ = listOfDir;
    }
    /**
     * <p>listOfFile を取得します。
     * @return listOfFile
     */
    public ArrayList<FileDirectoryModel> getListOfFile() {
        return listOfFile__;
    }
    /**
     * <p>listOfFile をセットします。
     * @param listOfFile listOfFile
     */
    public void setListOfFile(ArrayList<FileDirectoryModel> listOfFile) {
        listOfFile__ = listOfFile;
    }
}