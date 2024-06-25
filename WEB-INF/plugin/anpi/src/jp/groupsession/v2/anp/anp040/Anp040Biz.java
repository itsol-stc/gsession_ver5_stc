package jp.groupsession.v2.anp.anp040;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.model.AnpLabelValueModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 個人設定・表示設定画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp040Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param  anp040Model パラメータモデル
     * @param  reqMdl リクエストモデル
     * @param  con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp040ParamModel anp040Model,
            RequestModel reqMdl,
            Connection con)
                    throws Exception {

        log__.debug("///Anp040Biz * 初期表示データセット///");
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //個人設定情報を取得（未登録の場合データを作成）
        AnpPriConfModel pribean = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        //表示件数コンボリストを取得
        anp040Model.setAnp040DsipCntList(__getDspCntLavel());

        //全て表示区分(コンボボックス)
        boolean allGroupUserFlg = true;

        //デフォルト表示グループコンボリストを取得
        List<AnpLabelValueModel> gpLabel =
                anpiBiz.getGroupLabel(reqMdl, con, sessionUsrSid, allGroupUserFlg);
        anp040Model.setAnp040GroupLabel(gpLabel);

        if (anp040Model.getAnp040UserSid() == 0) {

            //グループリストに初期表示するデフォルトグループを取得
            String dspGpSidStr = anp040Model.getAnp040SelectGroupSid();
            if (dspGpSidStr == null) {
                log__.debug("個人設定デフォルトグループ取得");
                dspGpSidStr = anpiBiz.getDefaultGroupSid(con, sessionUsrSid, allGroupUserFlg);
                anp040Model.setAnp040SelectGroupSid(dspGpSidStr);
            }

            anp040Model.setAnp040UserSid(sessionUsrSid);
            anp040Model.setAnp040MainDispFlg(pribean.getAppMainKbn());
            anp040Model.setAnp040SelectDispCnt(pribean.getAppListCount());
        }
    }

    /**
     * <br>[機  能] 表示件数コンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @return List (in LabelValueBean)  表示件数コンボ
     */
    private List<LabelValueBean> __getDspCntLavel() {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int cnt = 10; cnt <= 100; cnt += 10) {
            labelList.add(
                    new LabelValueBean(String.valueOf(cnt), String.valueOf(cnt)));
        }
        return labelList;
    }

}