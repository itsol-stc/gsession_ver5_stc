package jp.groupsession.v2.man.man420;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.model.CmnImpTimeModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 *
 * <br>[機  能]ユーザ連携自動インポート機能設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man420Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man420Biz.class);

    /**
     *
     * <br>[機  能]初期表示用のデータを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IOTools例外
     */
    public void setInitData(RequestModel reqMdl,
            Man420ParamModel paramMdl,
            Connection con,
            String appRootPath) throws SQLException, IOToolsException {

        //初回表示はDBから設定を取得する(DBに存在しない場合は初期値をセットする)
        if (paramMdl.getMan420InitFlg() == 1) {
            MainCommonBiz mBiz = new MainCommonBiz();
            CmnImpTimeModel citMdl = mBiz.getCmnImpTimeModel(con);

            if (citMdl != null) {
                paramMdl.setMan420UsrImpFlg(citMdl.getCitUsrFlg());
                paramMdl.setMan420UsrImpTimeSelect(citMdl.getCitUsrTimeFlg());
                paramMdl.setMan420UsrFrHour(String.valueOf(citMdl.getCitUsrTime().getIntHour()));
            }

            paramMdl.setMan420InitFlg(0);
        }

        //表示用インポート対象フォルダを取得
        paramMdl.setMan420ImportFolder(
                __getPath(GSConstUser.USER_IMPORT_FILE_DIR, appRootPath));
        //もしもフォルダがなかった場合は作成しておく
        IOTools.isDirCheck(paramMdl.getMan420ImportFolder(), true);
        paramMdl.setMan420ImpSuccessFolder(
                __getPath(GSConstUser.USER_IMPORT_SUCCESS_DIR, appRootPath));
        paramMdl.setMan420ImpFailedFolder(
                __getPath(GSConstUser.USER_IMPORT_FAIL_DIR, appRootPath));

        __dolabelSet(paramMdl);
    }

    /**
     *
     * <br>[機  能]時間設定のラベルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl 時間設定ラベルを作成
     */
    private void __dolabelSet(Man420ParamModel paramMdl) {

        ArrayList<LabelValueBean> lvbList = new ArrayList<>();
        for (int time = 0; time < 24; time++) {
            LabelValueBean lvb = new LabelValueBean();
            lvb.setLabel(String.valueOf(time));
            lvb.setValue(String.valueOf(time));
            lvbList.add(lvb);
        }
        paramMdl.setMan420UsrHourLabel(lvbList);
    }

    /**
     *
     * <br>[機  能]オペレーションログに出力する本文を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param msgRes メッセージリソース
     * @param gsMsg GSメッセージ
     * @return オペレーションログ本文
     */
    public String setLogValue(Man420ParamModel paramMdl,
            MessageResources msgRes, GsMessage gsMsg) {
        StringBuilder sb = new StringBuilder();

        sb.append(msgRes.getMessage("comp.todo.import", gsMsg.getMessage("main.man420.1")));

        return sb.toString();
    }

    /**
    *
    * <br>[機  能]強制実行失敗時のオペレーションログに出力する本文を作成
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
    * @param msgRes メッセージリソース
    * @param gsMsg GSメッセージ
    * @return オペレーションログ本文
    */
   public String setFailLogValue(Man420ParamModel paramMdl,
           MessageResources msgRes, GsMessage gsMsg) {
       StringBuilder sb = new StringBuilder();

       sb.append(msgRes.getMessage("error.fail", gsMsg.getMessage("main.man420.1")));
       return sb.toString();
   }

   /**
   *
   * <br>[機  能]引数で指定された種類のパスを返す
   * <br>[解  説]
   * <br>[備  考]
   * @param dirPath 取得したいパスの種類
   * @param appRootPath アプリケーションルートパス
   * @return 取得したいパス
   */
  private String __getPath(String dirPath, String appRootPath) {

      String path
      = NullDefault.getString(ConfigBundle.getValue(dirPath), "");

      //取得した設定ファイルの値が空の場合はデフォルトパスを渡す
      if (path.equals("")) {
          StringBuilder defPath = new StringBuilder();
          defPath.append(appRootPath);
          defPath.append(GSConstUser.AUTO_IMPORT_DEF_PATH);

          switch (dirPath) {
          case GSConstUser.USER_IMPORT_FILE_DIR:
              defPath.append(GSConstUser.USER_IMPORT);
              break;
          case GSConstUser.USER_IMPORT_SUCCESS_DIR:
              defPath.append(GSConstUser.USER_IMPORT_SUCCESS);
              break;
          case GSConstUser.USER_IMPORT_FAIL_DIR:
              defPath.append(GSConstUser.USER_IMPORT_FAILED);
              break;
          default:
              break;
      }

          path = defPath.toString();
      }

      path = IOTools.replaceFileSep(path);
      path = IOTools.setEndPathChar(path);
      return path;
  }

}
