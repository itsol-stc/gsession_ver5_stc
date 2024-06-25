package jp.groupsession.v2.fil.fil250kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.dao.FileCallConfDao;
import jp.groupsession.v2.fil.fil250.Fil250Biz;
import jp.groupsession.v2.fil.fil250.Fil250ParamModel;
import jp.groupsession.v2.fil.model.FileCallConfModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 管理者設定 更新通知一括設定確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil250knBiz extends Fil250Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil250knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil250knBiz(Connection con, RequestModel reqMdl) {
        super(con, reqMdl);
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil250knParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil250knParamModel paramMdl) throws SQLException {

        log__.debug("fil250knBiz Start");

        //フォルダパスを設定する。
        _setDirPath(con__, paramMdl);

        //更新通知対象者 ユーザリスト
        paramMdl.setFil250knCallUserList(__getCallUserList(con__, paramMdl));
    }

    /**
     * <br>[機  能] 更新通知設定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil250knParamModel
     * @throws Exception 実行例外
     */
    public void entryCallUser(Fil250knParamModel paramMdl) throws Exception {

        int fdrSid = NullDefault.getInt(paramMdl.getFil250DirSid(), -1);

        FileCallConfDao confDao = new FileCallConfDao(con__);
        //既存登録リストを取得
        ArrayList<FileCallConfModel> oldEntryList = confDao.select(fdrSid);

        //新規更新通知ユーザのSIDを取得
        String[] callUser = paramMdl.getFil250SvCallUser();

        //新規更新通知ユーザリスト作成
        ArrayList<FileCallConfModel> selectList = new ArrayList<FileCallConfModel>();
        if (callUser != null && callUser.length > 0) {
            for (String userSid : callUser) {
                FileCallConfModel confMdl = new FileCallConfModel();
                confMdl.setFdrSid(fdrSid);
                confMdl.setUsrSid(Integer.parseInt(userSid));
                selectList.add(confMdl);
            }
        }

        //DBへ登録するユーザリスト作成
        ArrayList<FileCallConfModel> entryList  = new ArrayList<FileCallConfModel>();

        //既存登録ユーザと同じユーザが新規更新通知に選択されている場合
        //登録ユーザリストに追加しない
        for (FileCallConfModel selectMdl:selectList) {
            boolean entryFlg = true;
            for (FileCallConfModel oldEntry:oldEntryList) {
                if (selectMdl.getUsrSid() == oldEntry.getUsrSid()) {
                    entryFlg = false;
                    break;
                }
            }
            if (entryFlg) {
                entryList.add(selectMdl);
            }
        }

        //既存ユーザ＋新規登録の登録を行う
        for (FileCallConfModel entryMdl:entryList) {
            confDao.insert(entryMdl);
        }

    }

    /**
     * <br>[機  能] 更新通知対象者 ユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Fil250ParamModel
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getCallUserList(
            Connection con, Fil250ParamModel paramMdl)
    throws SQLException {

        String[] usrSids = paramMdl.getFil250SvCallUser();
        UserGroupSelectBiz selBiz = new UserGroupSelectBiz();
        return selBiz.getSelectedLabel(usrSids, con);
    }
}