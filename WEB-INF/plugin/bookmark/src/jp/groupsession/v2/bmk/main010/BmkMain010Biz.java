package jp.groupsession.v2.bmk.main010;

import java.sql.Connection;
import java.util.List;

import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.dao.BmkBookmarkDataDao;
import jp.groupsession.v2.bmk.dao.BmkLabelDao;
import jp.groupsession.v2.bmk.dao.BmkUconfDao;
import jp.groupsession.v2.bmk.model.BmkBookmarkDataModel;
import jp.groupsession.v2.bmk.model.BmkUconfModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>メイン画面個人ブックマーク表示用のビジネスロジック
 * @author JTS
 */
public class BmkMain010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkMain010Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public BmkMain010Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl BmkMain010ParamModel
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(BmkMain010ParamModel paramMdl, Connection con, int userSid)
    throws Exception {
        log__.debug("START");
        //DBより現在の設定を取得する。(なければデフォルト)
        BmkUconfDao uConfdao = new BmkUconfDao(con);
        BmkUconfModel uConfmodel = uConfdao.select(userSid);

        if (paramMdl.getDspFlg() == -1) {
            //個人ブックマーク　メイン表示区分初期化
            if (uConfmodel == null) {
                paramMdl.setDspFlg(GSConstBookmark.DSP_YES);
            } else {
                paramMdl.setDspFlg(uConfmodel.getBucMainMy());
            }
        }

        //ユーザブックマーク一覧を取得
        BmkBookmarkDataDao bmkDataDao = new BmkBookmarkDataDao(con, reqMdl__);
        List<BmkBookmarkDataModel> resultList
            = bmkDataDao.getBookMarkList(userSid);

        BmkLabelDao lblDao = new BmkLabelDao(con);
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.no");

        //取得ブックマーク一覧のラベル情報設定
        for (BmkBookmarkDataModel list : resultList) {
            String lblName = lblDao.getBookmarkLabel(list.getBmkSid());
            if (lblName.equals("")) {
                lblName = msg;
            }
            list.setLabelName(lblName);
        }
        paramMdl.setBmkMain010List(resultList);
        log__.debug("End");
    }
}
