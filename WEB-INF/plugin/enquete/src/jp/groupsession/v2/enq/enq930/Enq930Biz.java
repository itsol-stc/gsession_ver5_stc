package jp.groupsession.v2.enq.enq930;

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
import jp.groupsession.v2.enq.dao.EnqAdmConfDao;
import jp.groupsession.v2.enq.model.EnqAdmConfModel;

/**
 * <br>[機  能] 管理者設定 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq930Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq930Biz.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq930Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq930Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq930ParamModel enq930Model,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("初期表示情報を取得");

        EnqAdmConfModel conf = new EnqAdmConfModel();
        LabelValueBean labelBean = null;
        ArrayList<LabelValueBean> cntList = new ArrayList<LabelValueBean>();

        // セッションユーザ情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 管理者設定を取得
        EnqCommonBiz biz = new EnqCommonBiz();
        conf = biz.getAdmConfData(con, sessionUsrSid);

        // 表示項目値の設定
        enq930Model.setEnq930DspKbn(conf.getEacListCntUse());
        enq930Model.setEnq930SelectCnt(conf.getEacListCnt());
        for (int cnt : GSConstEnquete.CONF_LIST_CNT) {
            labelBean = new LabelValueBean();
            labelBean.setLabel(String.valueOf(cnt));
            labelBean.setValue(String.valueOf(cnt));
            cntList.add(labelBean);
        }
        enq930Model.setEnq930CntListLabel(cntList);

        enq930Model.setEnq930SelectFolder(String.valueOf(conf.getEacFolderSelect()));
        String sLabel = null;
        String sValue = null;
        cntList = new ArrayList<LabelValueBean>();
        for (int nIdx = 0; nIdx < GSConstEnquete.CONF_LIST_FOLDER_NO.length; nIdx++) {
            sLabel = GSConstEnquete.CONF_LIST_FOLDER[nIdx];
            sValue = String.valueOf(GSConstEnquete.CONF_LIST_FOLDER_NO[nIdx]);
            labelBean = new LabelValueBean();
            labelBean.setLabel(sLabel);
            labelBean.setValue(sValue);
            cntList.add(labelBean);
        }
        if (conf.getEacCanAnswer() == 1) {
            enq930Model.setEnq930CanAnswer(true);
        }
        else {
            enq930Model.setEnq930CanAnswer(false);
        }
        enq930Model.setEnq930FolderListLabel(cntList);

        // メイン表示件数コンボボックスを作成
        List<LabelValueBean> dspCntComb = new ArrayList<LabelValueBean>();
        dspCntComb.add(new LabelValueBean("5", "5"));
        for (int i = 10; i <= 50; i = i + 10) {
            dspCntComb.add(new LabelValueBean(Integer.toString(i),
                                           Integer.toString(i)));
        }
        enq930Model.setDspCntComb(dspCntComb);

        // 表示項目値の設定
        enq930Model.setEnq930MainDspKbn(conf.getEacMainDspUse());
        enq930Model.setEnq930MainDsp(conf.getEacMainDsp());
        enq930Model.setEnq930DspCntMain(conf.getEacDspcntMain());
        enq930Model.setEnq930DspChecked(conf.getEacDspChecked());
    }

    /**
     * <br>[機  能] 表示設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq930Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void doCommit(Enq930ParamModel enq930Model,
                         RequestModel reqMdl,
                         Connection con) throws SQLException {

        log__.debug("表示設定更新処理");

        boolean commitFlg = false;

        // セッションユーザ情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 更新処理
        try {
            con.setAutoCommit(false);

            // 管理者設定の存在チェック
            EnqCommonBiz biz = new EnqCommonBiz();
            biz.getAdmConfData(con, sessionUsrSid);

            // 表示設定を更新
            EnqAdmConfDao adao = new EnqAdmConfDao(con);
            adao.updateListDsp(__getAdmConfModel(enq930Model, sessionUsrSid),
                              enq930Model.getEnq930DspKbn(), enq930Model.getEnq930MainDspKbn());

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
            con.setAutoCommit(true);
        }
    }

    /**
     * <br>[機  能] 管理者設定 表示設定更新用のモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq930Model パラメータモデル
     * @param usrSid ユーザSID
     * @return 管理者設定 表示設定の更新用モデル
     */
    private EnqAdmConfModel __getAdmConfModel(Enq930ParamModel enq930Model, int usrSid) {

        log__.debug("管理者設定 表示設定更新用モデルの取得処理");

        EnqAdmConfModel aconf = new EnqAdmConfModel();

        // 一覧表示件数区分
        aconf.setEacListCntUse(enq930Model.getEnq930DspKbn());
        // 一覧表示件数
        aconf.setEacListCnt(enq930Model.getEnq930SelectCnt());
        // 初期表示フォルダ表示区分
        aconf.setEacFolderUse(enq930Model.getEnq930DspKbn());
        // 初期表示フォルダ
        aconf.setEacFolderSelect(Integer.parseInt(enq930Model.getEnq930SelectFolder()));
        // 回答可能のみ表示
        if (enq930Model.getEnq930CanAnswer()) {
            aconf.setEacCanAnswer(1);
        }
        else {
            aconf.setEacCanAnswer(0);
        }

        // メイン表示区分
        aconf.setEacMainDspUse(enq930Model.getEnq930MainDspKbn());
        // メイン表示フラグ
        aconf.setEacMainDsp(enq930Model.getEnq930MainDsp());
        // メイン表示件数
        aconf.setEacDspcntMain(enq930Model.getEnq930DspCntMain());
        //回答済みアンケートの表示
        aconf.setEacDspChecked(enq930Model.getEnq930DspChecked());

        // 更新者ID
        aconf.setEacEuid(usrSid);
        // 更新日
        aconf.setEacEdate(new UDate());

        return aconf;
    }
}
