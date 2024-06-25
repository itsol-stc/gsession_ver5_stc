package jp.groupsession.v2.enq.enq920kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqTypeDao;
import jp.groupsession.v2.enq.enq920.model.Enq920DelListModel;
import jp.groupsession.v2.enq.model.EnqTypeListModel;
import jp.groupsession.v2.enq.model.EnqTypeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 管理者設定 アンケート種類設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq920knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq920knBiz.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** 採番用コネクション */
    private MlCountMtController cntCon__ = null;

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq920knBiz() {
    }


    /**
     * <p>コンストラクタ
     * @param reqMdl リクエスト藻です
     * @param con コネクション
     * @param cntCon 採番用コネクション
     */
    public Enq920knBiz(RequestModel reqMdl, Connection con, MlCountMtController cntCon) {
        reqMdl__ = reqMdl;
        con__ = con;
        cntCon__ = cntCon;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq920knModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq920knParamModel enq920knModel,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報取得処理");

        // アンケート種類を表示順でソートする
        EnqCommonBiz enqBiz = new EnqCommonBiz();
        ArrayList<EnqTypeListModel> list =
                enqBiz.getSortTypeList(enq920knModel.getEnq920TypeListToList());
        enq920knModel.setEnq920TypeListForm(list);

        // アンケート種類名をセット
        __setEnqTypeName(enq920knModel, reqMdl, con);
    }

    /**
     * <br>[機  能] アンケート種類を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq920knModel パラメータモデル
     * @return true:更新成功、false:更新失敗
     * @throws SQLException SQL実行例外
     */
    public boolean doCommit(Enq920knParamModel enq920knModel) throws SQLException {

        log__.debug("アンケート種類更新処理");

        boolean commitFlg = false;
        EnqTypeListModel[] typeList = enq920knModel.getEnq920TypeList();

        // セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        con__.setAutoCommit(false);

        // 更新処理
        try {
            EnqTypeDao dao = new EnqTypeDao(con__);

            dao.lockTable();

            // アンケート種類を削除する
            for (Enq920DelListModel delMdl : enq920knModel.getEnq920DelList()) {
                if (!__enqTypeDelete(delMdl.getEtpSid(), dao)) {
                    return false;
                }
            }

            // アンケート種類を更新する
            for (EnqTypeListModel bean : typeList) {
                if (!__enqTypeTouroku(bean, dao, sessionUsrSid)) {
                    return false;
                }
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("アンケート種類の更新に失敗しました。" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
            con__.setAutoCommit(true);
        }

        return commitFlg;
    }

    /**
     * <br>[機  能] アンケート種類名をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param enq920knModel パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setEnqTypeName(Enq920knParamModel enq920knModel,
                                  RequestModel reqMdl,
                                  Connection con) throws SQLException {

        log__.debug("アンケート種類名設定");

        ArrayList<EnqTypeListModel> typeList = new ArrayList<EnqTypeListModel>();
        EnqTypeListModel typeMdl = null;

        for (EnqTypeListModel bean : enq920knModel.getEnq920TypeListToList()) {
            typeMdl = new EnqTypeListModel();
            typeMdl.setEtpSid(bean.getEtpSid());
            typeMdl.setEtpDspSeq(bean.getEtpDspSeq());
            typeMdl.setEtpName(bean.getEtpName());
            typeList.add(typeMdl);
        }
        enq920knModel.setEnq920knTypeList(typeList);

    }

    /**
     * <br>[機  能] アンケート種類削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param sid アンケート種類SID
     * @param dao アンケート種類DAO
     * @return true:更新成功、false:更新失敗
     * @throws SQLException SQL実行例外
     */
    private boolean __enqTypeDelete(long sid, EnqTypeDao dao) throws SQLException {

        log__.debug("アンケート種類削除処理");

        boolean ret = false;
        log__.debug("deleteのSID:" + sid);
        int cnt = dao.delete(sid);
        if (cnt == 1) { ret = true; }

        return ret;
    }

    /**
     * <br>[機  能] アンケート種類更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl アンケート種類Listモデル
     * @param dao アンケート種類DAO
     * @param usrSid セッションユーザSID
     * @return true:更新成功、false:更新失敗
     * @throws SQLException SQL実行例外
     */
    private boolean __enqTypeTouroku(EnqTypeListModel mdl, EnqTypeDao dao, int usrSid)
        throws SQLException {

        log__.debug("アンケート種類更新処理");

        boolean ret = false;

        // 新規追加
        if (mdl.getEtpSid() == -1) {
            //同名のアンケート種別が未登録の場合のみ登録を行う
            if (!dao.existEnqTypeName(mdl.getEtpName())) {
                dao.insert(__getInsertModel(mdl.getEtpDspSeq(), mdl.getEtpName(), usrSid));
                ret = true;
            }

        // 更新
        } else {
            EnqTypeModel typeMdl = __getUpdateModel(
                    mdl.getEtpSid(), mdl.getEtpDspSeq(), mdl.getEtpName(), usrSid);
            // ロックする
            //dao.selectForUpdate(mdl.getEtpSid());
            int cnt = dao.update(typeMdl);
            if (cnt == 1) { ret = true; }
        }

        return ret;
    }

    /**
     * <br>[機  能] アンケート種類の登録用モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dspSeq 表示順
     * @param name 種類名
     * @param usrSid セッションユーザSID
     * @return アンケート種類の登録用モデル
     * @throws SQLException SQL実行例外
     */
    private EnqTypeModel __getInsertModel(int dspSeq, String name, int usrSid)
        throws SQLException {

        log__.debug("アンケート種類の登録用モデル取得処理");

        EnqTypeModel tlist = new EnqTypeModel();
        UDate now = new UDate();

        // 採番マスタから、アンケート種類SIDを取得する
        long sid = cntCon__.getSaibanNumber(GSConstEnquete.SBNSID_ENQUETE,
                                            GSConstEnquete.SBNSID_SUB_ENQUETE_TYPE,
                                            usrSid);

        // アンケート種類SID
        tlist.setEtpSid(sid);
        // 表示順
        tlist.setEtpDspSeq(dspSeq);
        // 名称
        tlist.setEtpName(name);
        // 登録者ID
        tlist.setEtpAuid(usrSid);
        // 登録日時
        tlist.setEtpAdate(now);
        // 更新者ID
        tlist.setEtpEuid(usrSid);
        // 更新日時
        tlist.setEtpEdate(now);

        return tlist;
    }

    /**
     * <br>[機  能] アンケート種類の更新用モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sid アンケート種類SID
     * @param dspSeq 表示順
     * @param name 種類名
     * @param usrSid セッションユーザSID
     * @return アンケート種類の登録用モデル
     * @throws SQLException SQL実行例外
     */
    private EnqTypeModel __getUpdateModel(long sid, int dspSeq, String name, int usrSid)
        throws SQLException {

        log__.debug("アンケート種類の登録用モデル取得処理");

        EnqTypeModel tlist = new EnqTypeModel();
        UDate now = new UDate();

        // アンケート種類SID
        tlist.setEtpSid(sid);
        // 表示順
        tlist.setEtpDspSeq(dspSeq);
        // 名称
        tlist.setEtpName(name);
        // 更新者ID
        tlist.setEtpEuid(usrSid);
        // 更新日時
        tlist.setEtpEdate(now);

        return tlist;
    }
}
