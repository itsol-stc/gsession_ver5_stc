package jp.groupsession.v2.bmk.bmk020;

import java.sql.Connection;

import jp.groupsession.v2.bmk.dao.BmkUrlDao;
import jp.groupsession.v2.bmk.model.BmkUrlModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ブックマーク登録_URL入力画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk020Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Bmk020ParamModel
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @throws Exception 実行例外
     */
    public void setInitData(Bmk020ParamModel paramMdl, Connection con, BaseUserModel userMdl)
    throws Exception {
        log__.debug("初期表示処理");

        //追加モードのとき、URLを初期表示
        if (paramMdl.getEditBmuSid() != -1) {
            BmkUrlDao uDao = new BmkUrlDao(con);
            BmkUrlModel uModel = uDao.select(paramMdl.getEditBmuSid());
            if (uModel != null) {
                paramMdl.setBmk020url(uModel.getBmuUrl());
            } else {
                paramMdl.setEditBmuSid(-1);
            }
        }
    }
}