package jp.groupsession.v2.enq.enq810;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqPriConfDao;
import jp.groupsession.v2.enq.model.EnqAdmConfModel;
import jp.groupsession.v2.enq.model.EnqPriConfModel;

/**
 * <br>[機  能] アンケート 個人設定 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq810Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq810Biz.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq810Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq810Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq810ParamModel enq810Model,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報を取得");

        __loadDspUse(enq810Model, reqMdl, con);
        __loadDspData(enq810Model, reqMdl, con);
        __loadMainDspData(enq810Model, reqMdl, con);
    }
    /**
     * <br>[機  能] 表示設定情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq810Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void __loadDspData(Enq810ParamModel enq810Model,
            RequestModel reqMdl,
            Connection con) throws SQLException {
        EnqPriConfModel conf = new EnqPriConfModel();

        // セッションユーザ情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 個人設定を取得
        EnqCommonBiz biz = new EnqCommonBiz();
        conf = biz.getPriConfData(con, sessionUsrSid);

        // 表示項目値の設定
        enq810Model.setEnq810SelectCnt(conf.getEpcListCnt());
        enq810Model.setEnq810SelectFolder(String.valueOf(conf.getEpcListFolder()));
        if (conf.getEpcCanAnswer() == 1) {
            enq810Model.setEnq810CanAnswer(true);
        } else {
            enq810Model.setEnq810CanAnswer(false);
        }
        enq810Model.setEnq810CntListLabel(biz.getListCntLabel());
        enq810Model.setEnq810FolderListLabel(biz.getListFolderLabel());
    }


    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq810Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __loadDspUse(Enq810ParamModel enq810Model,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報取得処理");

        EnqAdmConfModel conf = new EnqAdmConfModel();

        // セッションユーザ情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 管理者設定を取得
        EnqCommonBiz biz = new EnqCommonBiz();
        conf = biz.getAdmConfData(con, sessionUsrSid);

        // 各フラグの設定
        enq810Model.setEnq810DspUse(conf.getEacListCntUse());
        enq810Model.setEnq810DspMainUse(conf.getEacMainDspUse());
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq810Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void __loadMainDspData(Enq810ParamModel enq810Model,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報を取得");

        EnqPriConfModel conf = new EnqPriConfModel();

        // セッションユーザ情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 個人設定を取得
        EnqCommonBiz biz = new EnqCommonBiz();
        conf = biz.getPriConfData(con, sessionUsrSid);

        // メイン表示件数コンボボックスを作成
        List<LabelValueBean> dspCntComb = new ArrayList<LabelValueBean>();
        dspCntComb.add(new LabelValueBean("5", "5"));
        for (int i = 10; i <= 50; i = i + 10) {
            dspCntComb.add(new LabelValueBean(Integer.toString(i),
                                           Integer.toString(i)));
        }
        enq810Model.setDspCntComb(dspCntComb);

        // 表示項目値の設定
        enq810Model.setEnq810MainDsp(conf.getEpcMainDsp());
        enq810Model.setEnq810DspCntMain(conf.getEpcDspcntMain());
        enq810Model.setEnq810DspChecked(conf.getEpcDspChecked());
    }

    /**
     * <br>[機  能] 表示設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq810Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void doCommit(Enq810ParamModel enq810Model,
                         RequestModel reqMdl,
                         Connection con) throws SQLException {

        log__.debug("表示設定更新処理");

        boolean commitFlg = false;

        // セッションユーザ情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        __loadDspUse(enq810Model, reqMdl, con);

        // 更新処理
        try {
            con.setAutoCommit(false);

            // 個人設定の存在チェック
            EnqCommonBiz biz = new EnqCommonBiz();
            biz.getPriConfData(con, sessionUsrSid);

            EnqPriConfDao pdao = new EnqPriConfDao(con);

            // 表示設定を更新
            if (enq810Model.getEnq810DspUse() == GSConstEnquete.CONF_LIST_USE_EACH) {
                pdao.updateListCnt(__getPriConfModel(enq810Model, sessionUsrSid), sessionUsrSid);
                pdao.updateListFolder(__getPriConfModel(enq810Model, sessionUsrSid), sessionUsrSid);
            }
            // メイン表示設定を更新
            if (enq810Model.getEnq810DspMainUse() == GSConstEnquete.CONF_MAIN_DSP_USE_EACH) {
                pdao.updateMainDsp(__getPriConfModel(enq810Model, sessionUsrSid), sessionUsrSid);
            }


            commitFlg = true;

        } catch (SQLException e) {
            log__.error("表示設定の更新に失敗しました。" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 個人設定 一覧表示件数更新用のモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq810Model パラメータモデル
     * @param usrSid ユーザSID
     * @return 管理者設定 表示設定の更新用モデル
     */
    private EnqPriConfModel __getPriConfModel(Enq810ParamModel enq810Model, int usrSid) {

        log__.debug("個人設定 一覧表示件数更新用モデルの取得処理");

        EnqPriConfModel pconf = new EnqPriConfModel();
        if (enq810Model.getEnq810DspUse() == GSConstEnquete.CONF_LIST_USE_EACH) {
            // 一覧表示件数
            pconf.setEpcListCnt(enq810Model.getEnq810SelectCnt());
            // 初期表示フォルダ
            pconf.setEpcListFolder(Integer.parseInt(enq810Model.getEnq810SelectFolder()));
            // 回答済み表示
            if (enq810Model.getEnq810CanAnswer()) {
                pconf.setEpcCanAnswer(1);
            } else {
                pconf.setEpcCanAnswer(0);
            }

        }
        if (enq810Model.getEnq810DspMainUse() == GSConstEnquete.CONF_MAIN_DSP_USE_EACH) {
            // メイン表示
            pconf.setEpcMainDsp(enq810Model.getEnq810MainDsp());
            // メイン表示件数
            pconf.setEpcDspcntMain(enq810Model.getEnq810DspCntMain());
            //回答済みアンケートの表示
            pconf.setEpcDspChecked(enq810Model.getEnq810DspChecked());

        }
        // 更新者ID
        pconf.setEpcEuid(usrSid);
        // 更新日
        pconf.setEpcEdate(new UDate());

        return pconf;
    }
}
