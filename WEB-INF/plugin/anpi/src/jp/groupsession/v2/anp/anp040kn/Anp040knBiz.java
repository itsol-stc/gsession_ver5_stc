package jp.groupsession.v2.anp.anp040kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.dao.AnpPriConfDao;
import jp.groupsession.v2.anp.model.AnpLabelValueModel;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;


/**
 * <br>[機  能] 個人設定・表示設定確認画面ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp040knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp040knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param  anp040knModel パラメータモデル
     * @param  reqMdl リクエストモデル
     * @param  con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp040knParamModel anp040knModel,
            RequestModel reqMdl,
            Connection con)
                    throws Exception {

        log__.debug("///Anp040knBiz * 初期表示データセット///");
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz();
        int sessionUsrSid = anp040knModel.getAnp040UserSid();

        //グループリスト取得
        List<AnpLabelValueModel> gpList = anpiBiz.getGroupLabel(reqMdl, con, sessionUsrSid, true);

        //表示用グループ名取得
        String dspGrpNm = null;
        for (AnpLabelValueModel line : gpList) {
            if (line.getValue().equals(anp040knModel.getAnp040SelectGroupSid())) {
                dspGrpNm = line.getLabel();
            }
        }

        //表示用グループ名
        anp040knModel.setAnp040knDispGrpNm(dspGrpNm);
    }

    /**
     * <br>[機  能] 表示設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param  anp040knModel パラメータモデル
     * @param  reqMdl リクエストモデル
     * @param  con DBコネクション
     * @throws Exception 実行例外
     */
    public void doUpdate(Anp040knParamModel anp040knModel,
            RequestModel reqMdl,
            Connection con)
                         throws Exception {

        log__.debug("///表示設定更新START///");
        AnpPriConfDao dao = new AnpPriConfDao(con);

        //DBに個人設定が無い場合登録
        AnpiCommonBiz biz = new AnpiCommonBiz();
        int usrSid = reqMdl.getSmodel().getUsrsid();
        biz.getAnpPriConfModel(con, usrSid);
        //更新内容をモデルに設定
        AnpPriConfModel pribean = __setUpdateModel(anp040knModel, reqMdl, con);
        dao.doUpdateAnp040kn(pribean);

    }

    /**
     * <br>[機  能] 更新内容モデルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param  anp040knModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return Anp040knSenderModel 更新用データ
     * @throws SQLException SQL実行例外
     */
    private AnpPriConfModel __setUpdateModel(Anp040knParamModel anp040knModel,
            RequestModel reqMdl,
            Connection con) throws SQLException {

        AnpPriConfModel pribean = new AnpPriConfModel();
        pribean.setUsrSid(anp040knModel.getAnp040UserSid());
        pribean.setAppMainKbn(anp040knModel.getAnp040MainDispFlg());
        pribean.setAppListCount(anp040knModel.getAnp040SelectDispCnt());

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        GroupBiz gpBiz = new GroupBiz();
        int gsid = gpBiz.getDefaultGroupSid(sessionUsrSid, con);

        //デフォルトグループ
        if (AnpiCommonBiz.isChackAllGrp(anp040knModel.getAnp040SelectGroupSid())) {
            //全て選択
            pribean.setAppDspGroup(gsid);
            pribean.setAppDspMygroup(0);
            pribean.setAppAllGroupFlg(GSConstAnpi.ALL_GROUP_SELECT);
        } else if (AnpiCommonBiz.isMyGroupSidforDisp(anp040knModel.getAnp040SelectGroupSid())) {
            //マイグループ
            pribean.setAppDspGroup(gsid);
            pribean.setAppDspMygroup(
                    AnpiCommonBiz.getGroupSidfromDisp(anp040knModel.getAnp040SelectGroupSid()));
            pribean.setAppAllGroupFlg(GSConstAnpi.ALL_GROUP_NOT_SELECT);
        } else {
            //ユーザ所属グループ
            pribean.setAppDspGroup(
                    AnpiCommonBiz.getGroupSidfromDisp(anp040knModel.getAnp040SelectGroupSid()));
            pribean.setAppDspMygroup(0);
            pribean.setAppAllGroupFlg(GSConstAnpi.ALL_GROUP_NOT_SELECT);
        }

        pribean.setAppEuid(anp040knModel.getAnp040UserSid());

        return pribean;
    }

    /**
     * <br>[機  能] グループ名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param groupSid グループSID
     * @return アカウント名
     * @throws Exception 実行例外
     */
    public String getGroupName(Connection con, String groupSid)
                        throws Exception {

        CmnGroupmDao grpDao = new CmnGroupmDao(con);
        CmnGroupmModel mdl = grpDao.select(Integer.parseInt(groupSid));
        return mdl.getGrpName();
    }

}