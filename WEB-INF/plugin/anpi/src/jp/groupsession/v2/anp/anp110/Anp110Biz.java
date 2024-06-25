package jp.groupsession.v2.anp.anp110;

import java.sql.Connection;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.anp110.dao.Anp110Dao;
import jp.groupsession.v2.anp.anp110.model.Anp110SearchModel;
import jp.groupsession.v2.anp.anp110.model.Anp110SenderModel;
import jp.groupsession.v2.anp.model.AnpLabelValueModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 管理者設定・緊急連絡先設定状況画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp110Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp110Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param anp110Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp110ParamModel anp110Model,
            RequestModel reqMdl,
                            Connection con)
                            throws Exception {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        Anp110Dao dao = new Anp110Dao(con);

        //個人設定情報を取得
        log__.debug("個人設定情報を取得 usrSid = " + sessionUsrSid);
        AnpPriConfModel priConf = anpiBiz.getAnpPriConfModel(con, sessionUsrSid);

        //全て表示区分(コンボボックス)
        boolean allGroupUserFlg = true;

        //表示グループリストを取得
        List<AnpLabelValueModel> gpLabel =
                anpiBiz.getGroupLabel(reqMdl, con, sessionUsrSid, allGroupUserFlg);
        anp110Model.setAnp110GroupLabel(gpLabel);

        //グループリストに初期表示するデフォルトグループを取得
        log__.debug("デフォルトグループ dspGpSidStr = " + anp110Model.getAnp110SelectGroupSid());
        String dspGpSidStr = anp110Model.getAnp110SelectGroupSid();
        if (dspGpSidStr == null) {
            dspGpSidStr = anpiBiz.getDefaultGroupSid(con, sessionUsrSid, allGroupUserFlg);
        }
        dspGpSidStr = anpiBiz.getEnableSelectGroup(gpLabel, dspGpSidStr,
                anpiBiz.getDefaultGroupSid(con, sessionUsrSid, allGroupUserFlg));
        anp110Model.setAnp110SelectGroupSid(dspGpSidStr);

        //緊急連絡先一覧の取得条件をセット
        Anp110SearchModel joken = new Anp110SearchModel();
        joken.setUserSid(sessionUsrSid);
        joken.setGroupSid(anp110Model.getAnp110SelectGroupSid());
        joken.setMailFlg(anp110Model.getAnp110SelectMailFilter());
        joken.setTellFlg(anp110Model.getAnp110SelectTellFilter());

        //緊急連絡先一覧のページ内容をセット
        int limit = priConf.getAppListCount();  //表示行数
        int maxCount = dao.getListCount(joken); //全データ数

        int nowPage = anp110Model.getAnp110NowPage();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPage = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPage, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPage;
            start = maxPageStartRow;
        }

        anp110Model.setAnp110NowPage(nowPage);
        anp110Model.setAnp110DspPage1(nowPage);
        anp110Model.setAnp110DspPage2(nowPage);
        anp110Model.setAnp110PageLabel(PageUtil.createPageOptions(maxCount, limit));

        //送信者一覧をセット
        List<Anp110SenderModel> list = dao.getListInfo(
                                           joken,
                                           anp110Model.getAnp110SortKeyIndex(),
                                           anp110Model.getAnp110OrderKey(),
                                           start,
                                           limit);
        anp110Model.setAnp110List(list);

    }


    /**
     * <br>[機  能] 初期表示のソートキー情報を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param anp110Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitSortData(Anp110ParamModel anp110Model,
            RequestModel reqMdl,
                            Connection con)
                            throws Exception {
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        int sortKeyIndex = -1;

        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sortKeyIndex = Anp110ParamModel.SORT_KEY_NAME;
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sortKeyIndex = Anp110ParamModel.SORT_KEY_SYAIN;
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sortKeyIndex = Anp110ParamModel.SORT_KEY_POST;
                break;
            default:
                sortKeyIndex = -1;
                break;
        }

        anp110Model.setAnp110SortKeyIndex(sortKeyIndex);
        anp110Model.setAnp110OrderKey(sortMdl.getCscUserOrder1());
    }

}
