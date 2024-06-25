package jp.groupsession.v2.fil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.dao.FileDirectoryDao;
import jp.groupsession.v2.fil.model.FilChildTreeModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;

/**
 * <br>[機  能] ファイルツリーBusinessLogic
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilTreeBiz {

    /** DBコネクション */
    private Connection con__ = null;
    /** OPENディレクトリ */
    private String openDirSid__ = "";

    /**
     * <p>openDirSid を取得します。
     * @return openDirSid
     */
    public String getOpenDirSid() {
        return openDirSid__;
    }
    /**
     * <p>openDirSid をセットします。
     * @param openDirSid openDirSid
     */
    public void setOpenDirSid(String openDirSid) {
        openDirSid__ = openDirSid;
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con Connection
     */
    public FilTreeBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] ツリーを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param cabiSid キャビネットSID
     * @param level 階層LV
     * @param usrSid ユーザSID
     * @param auth 権限区分
     * @param superUser 特権ユーザ
     * @return ret ツリー配列
     * @throws SQLException SQL実行時例外
     */
     public String[] getFileTree(int cabiSid, int level, int usrSid, int auth, boolean superUser)
         throws SQLException {

         FileDirectoryDao dao = new FileDirectoryDao(con__);

         //階層取得
         String[] ret = null;
         if (superUser) {
             ret = dao.getTreeList(cabiSid, level);
         } else {
             ret = dao.getTreeListAccessLimit(cabiSid, level, usrSid, auth);
         }

         return ret;
     }

   /**
    * <br>[機  能] ツリーを取得する
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param cabiSid キャビネットSID
    * @param level 階層LV
    * @return ret ツリー配列
    * @throws SQLException SQL実行時例外
    */
    public String[] getFileTree(int cabiSid, int level)
        throws SQLException {

        FileDirectoryDao dao = new FileDirectoryDao(con__);

        //階層取得
        String[] ret = dao.getTreeList(cabiSid, level);

        return ret;
    }


     /**
      * <br>[機  能] ツリーを取得する
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param reqMdl リクエストモデル
      * @param cabiSid キャビネットSID
      * @param fdrSidList 移動元のディレクトリSIDリスト
      * @param superUser 特権ユーザかどうか
      * @return ツリー配列Map
      * @throws SQLException SQL実行時例外
      */
      public Map<Integer, String[]> getFileTreeMapForMove(
              RequestModel reqMdl,
              int cabiSid,
              List<Integer> fdrSidList,
              boolean superUser)
                      throws SQLException {

          FilCommonBiz filCommonBiz = new FilCommonBiz(reqMdl, con__);
          int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
          Map<Integer, String[]> treeMap = new HashMap<Integer, String[]>();

          for (int level = 0; level <= GSConstFile.DIRECTORY_LEVEL_10; level++) {
              boolean adminUser = superUser;
              if (!adminUser) {
                  adminUser = filCommonBiz.isCabinetAdminUser(cabiSid, sessionUsrSid);
              }
              String[] tree = getFileTreeForMove(
                      cabiSid,
                      level,
                      List.of(),
                      sessionUsrSid,
                      adminUser);
              if (tree == null || tree.length == 0) {
                  break;
              }
              treeMap.put(level, tree);
          }
          //子の存在しない権限のないディレクトリの除外
          Set<Integer> parentDirSidSet = Set.of();
          String sep = GSConst.GSESSION2_ID + GSConst.GSESSION2_ID;
          for (int level = GSConstFile.DIRECTORY_LEVEL_10; level >= 0; level--) {
              if (treeMap.containsKey(level) == false) {
                  continue;
              }
              final Set<Integer> liveDirSid = parentDirSidSet;
              String[] tree = treeMap.get(level);
              List<String[]> spList =
                      Stream.of(tree)
                      .map(str -> str.split(sep))
                      .filter(sp -> liveDirSid.contains(Integer.valueOf(sp[0]))
                                  || sp.length <= 3
                                  || Integer.valueOf(sp[3]) == 1)
                      .collect(Collectors.toList());
              tree = spList.stream()
                      .map(sp -> {
                          if (sp.length == 3) {
                              return Stream.concat(Stream.of(sp),
                                      Stream.of(0))
                                      .toArray(String[]::new);
                          }
                          return sp;
                      })
                      .map(sp ->
                          Stream.of(sp)
                              .collect(Collectors.joining(sep))
                              )
                      .toArray(String[]::new);

              treeMap.put(level, tree);
              parentDirSidSet = spList.stream()
                  .map(sp -> Integer.valueOf(sp[1]))
                  .collect(Collectors.toSet());
          }
          return treeMap;
     }


    /**
     * <br>[機  能] ツリーを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param cabiSid キャビネットSID
     * @param level 階層LV
     * @param fdrSidList 移動元のディレクトリSIDリスト
     * @param usrSid ユーザSID
     * @param superUser 特権ユーザ
     * @return ret ツリー配列
     * @throws SQLException SQL実行時例外
     */
    public String[] getFileTreeForMove(
              int cabiSid, int level, List<Integer> fdrSidList, int usrSid, boolean superUser)
                      throws SQLException {

        FileDirectoryDao dao = new FileDirectoryDao(con__);

        //階層取得
        String[] ret = null;

        if (superUser) {
            ret = dao.getTreeListForMove(cabiSid, level, fdrSidList);
        } else {
            ret = dao.getTreeListForMoveAccessLimit(cabiSid, level, fdrSidList, usrSid);
        }
        return ret;
    }

    /**
    * <br>[機  能] 指定されたディレクトリ以下を全て取得する
    * <br>[解  説] 自ディレクトリを結果セットに含める
    * <br>[備  考]
    *
    * @param bean 自ディレクトリModel
    * @return ret 指定されたディレクトリ以下全てのディレクトリ情報
    * @throws SQLException SQL実行時例外
    */
    public FilChildTreeModel getChildOfTarget(FileDirectoryModel bean)
        throws SQLException {

        FilChildTreeModel ret = new FilChildTreeModel();

        //階層(ディレクトリ)
        ArrayList<FileDirectoryModel> listOfDir = new ArrayList<FileDirectoryModel>();

        //階層(ファイル)
        ArrayList<FileDirectoryModel> listOfFile = new ArrayList<FileDirectoryModel>();

        //階層取得
        getChildOfLevelX(bean, listOfDir, listOfFile);

        ret.setListOfDir(listOfDir);
        ret.setListOfFile(listOfFile);

        return ret;
    }

   /**
    * <br>[機  能] 指定された子階層を取得する
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param bean 自ディレクトリModel
    * @param listOfDir ディレクトリ(フォルダ)リスト
    * @param listOfFile ディレクトリ(ファイル)リスト
    * @throws SQLException SQL実行時例外
    */
    public void getChildOfLevelX(FileDirectoryModel bean,
                                  ArrayList<FileDirectoryModel> listOfDir,
                                  ArrayList<FileDirectoryModel> listOfFile)
        throws SQLException {

        //自ディレクトリ追加
        if (bean.getFdrParentSid() != GSConstFile.DIRECTORY_ROOT) {
            listOfDir.add(bean);
        }

        ArrayList<FileDirectoryModel> listOfParent = new ArrayList<FileDirectoryModel>();
        listOfParent.add(bean);

        int level = bean.getFdrLevel();

        FileDirectoryDao dao = new FileDirectoryDao(con__);
        for (int i = level + 1; i <= GSConstFile.DIRECTORY_LEVEL_10 + 1; i++) {
            if (!listOfParent.isEmpty()) {
                listOfParent =
                    dao.setChildDirList(listOfParent, listOfDir, listOfFile);
            }
        }
    }
}